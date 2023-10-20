package main.investigation;

import main.Models.CJKChaaar;
import main.dataFileGenerators.stokeMapGenerators.IntevtigationMapService;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;

import java.util.*;
import java.util.stream.Collectors;

public class TryingOutDifferentIdeas {


    public void generateradicalCandidates() {
        System.out.println("generate radical candidates");
        IntevtigationMapService IS = new IntevtigationMapService();
        StrokeMapService strokeMapService = new StrokeMapService();
        Map<String, CJKChaaar> charToInfoCJKMap = strokeMapService.charToInfoCJKMap();

        HashMap<String, List<CJKChaaar>> twoFour = IS.twofourhashMap;



        String test = "";
    }

    public void generateFrequencyOfAllCharAbove16() {
        System.out.println("hello");

        IntevtigationMapService IS = new IntevtigationMapService();
        StrokeMapService strokeMapService = new StrokeMapService();
        Map<String, CJKChaaar> charToInfoCJKMap = strokeMapService.charToInfoCJKMap();
        Map<Integer, CJKChaaar> jundaMap = strokeMapService.jundaToCJKMap();
        Map<Integer, CJKChaaar> tzaiMap = strokeMapService.tzaiToCJKMap();

        //among the 2-4 hashmap entries, turn thm into jundaOrdinals, sort them, remove the first 16,
        //remove numbers above 4000, return the longest list.

        List<List<Integer>> jundaNumbersBelow5000 = mapOfCharToNestedListOfIntTZAI(IS.firstThreeMap);
        jundaNumbersBelow5000.sort((a, b) -> Integer.compare(b.size(), a.size()));
        //find procenten af tegn skrevet der ligger iden for 16
        List<List<Integer>> percentbelowLimit = jundaNumbersBelow5000.stream().map(sublist -> onlyLast(sublist, 16)).collect(Collectors.toList());
        List<List<Long>> percentbelowLimit2 = percentbelowLimit.stream().map(sub -> getListSetFunctionTZAI(sub, tzaiMap)).collect(Collectors.toList());
        Long percentbelowLimit3 = percentbelowLimit2.stream()
                .flatMap(List::stream).collect(Collectors.toSet()).stream()
                .mapToLong(Long::longValue)
                .sum();

        List<List<String>> percentbelowLimitWITHCHAR = percentbelowLimit.stream()
                .map(numlist -> numlist.stream()
                        .map(item -> tzaiMap.get(item).getCJK() + " " + tzaiMap.get(item).getTzai().getOrdinal())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        
        Long totalJunda = tzaiMap.get(1).getTzai().getTotalOccurrences();
        Double jundaRatio = (double) percentbelowLimit3 / totalJunda;

        //fourOne == 0.011698878521478556
        //twoFour == 0.0010701431533065118
        //FourTwo == 0.001978449873841896
        //threethree == 0.0024097535793804552

        CJKChaaar threeSixFour = jundaMap.get(364);
        CJKChaaar fourSevenThree = jundaMap.get(473);


        //find procenten af tegn skrevet der ligger iden for 32

        System.out.println("end");


    }

    private static List<List<Integer>> mapOfCharToNestedListOfIntTZAI(HashMap<String, List<CJKChaaar>> fourOnehashMap) {
        return fourOnehashMap.values().stream()
                .map(listOfCJK ->
                        listOfCJK.stream()
                                .filter(CJK ->
                                        Objects.nonNull(CJK.getTzai()) &&
                                                CJK.getTzai().getOrdinal() < 10000)
                                .map(CJK -> CJK.getTzai().getOrdinal()).sorted().collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static List<List<Integer>> mapOfCharToNestedListOfIntJUNDA(HashMap<String, List<CJKChaaar>> fourOnehashMap) {
        return fourOnehashMap.values().stream()
                .map(listOfCJK ->
                        listOfCJK.stream()
                                .filter(CJK ->
                                        Objects.nonNull(CJK.getJunda()) &&
                                                CJK.getJunda().getOrdinal() < 10000)
                                .map(CJK -> CJK.getJunda().getOrdinal()).sorted().collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<Long> getListSetFunctionJUNDA(List<Integer> sub, Map<Integer, CJKChaaar> jundaMap) {
        List<Long> test = sub.stream()
                .map(item -> jundaMap.get(item).getJunda().getOccurrences()).collect(Collectors.toList());
        return test;
    }

    private List<Long> getListSetFunctionTZAI(List<Integer> sub, Map<Integer, CJKChaaar> tzaiMap) {
        List<Long> test = sub.stream()
                .map(item -> tzaiMap.get(item).getTzai().getOccurrences()).collect(Collectors.toList());
        return test;
    }

    private List<Integer> onlyFirst(List<Integer> sublist, int limit) {
        if (sublist.size() < limit) {
            return sublist;
        } else {
            return sublist.subList(0,limit);
        }
    }

    private List<Integer> onlyLast(List<Integer> sublist, int limit) {
        if (sublist.size() < limit) {
            return new ArrayList<>();
        } else {
            return sublist.subList(limit, sublist.size());
        }
    }

}
