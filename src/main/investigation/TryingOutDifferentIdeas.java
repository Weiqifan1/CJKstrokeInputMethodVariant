package main.investigation;

import main.Models.CJKChaaar;
import main.dataFileGenerators.stokeMapGenerators.IntevtigationMapService;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class TryingOutDifferentIdeas {


    public void generateradicalCandidates() {
        System.out.println("generate radical candidates");
        IntevtigationMapService IS = new IntevtigationMapService();
        StrokeMapService strokeMapService = new StrokeMapService();
        Map<String, CJKChaaar> charToInfoCJKMap = strokeMapService.charToInfoCJKMap();

        HashMap<String, List<CJKChaaar>> twoFour = IS.sixSixMap; // go to  public String fullCodeToSixSix(
        Map<String, List<CJKChaaar>> sortedByListLengthAndJunda = getSortedByJundaAndListLength(twoFour);
        Map<String, List<String>> charPlusJundaOrd = getCharPlusJundaord(twoFour);

        Map<String, List<CJKChaaar>> sortedByListLengthAndTzai = getSortedByTzaiAndListLength(twoFour);
        Map<String, List<String>> charPlusTzaiOrd = getCharPlusTzaiord(twoFour);

        Map<String, List<CJKChaaar>> sortByIntersperced = getSortedByInterspercedAndListLength(twoFour);
        Map<String, List<String>> charPlusInterspercedOrd = getCharPlusIntersperced(twoFour);



        String test = "";
    }

    public void generateFrequencyOfAllCharAbove16() {
        System.out.println("hello");

        IntevtigationMapService IS = new IntevtigationMapService();
        StrokeMapService strokeMapService = new StrokeMapService();
        Map<String, CJKChaaar> charToInfoCJKMap = strokeMapService.charToInfoCJKMap();
        Map<Integer, CJKChaaar> jundaMap = strokeMapService.jundaToCJKMap();
        Map<Integer, CJKChaaar> tzaiMap = strokeMapService.tzaiToCJKMap();

        //////////

        List<List<String>> jundaFIRSTTHREE = frequencyOfCodeJUNDA(IS.firstThreeMap, jundaMap,  16);
        List<List<String>> tzaiFIRStTHREE = frequencyOfCodeTZAI(IS.firstThreeMap, tzaiMap,  16);

        CJKChaaar horse1 = IS.charToInfoCJKMap.get("贔");
        CJKChaaar horse2 = IS.charToInfoCJKMap.get("顥");


        Long totalJunda = tzaiMap.get(1).getTzai().getTotalOccurrences();

        //fourOne == 0.011698878521478556
        //twoFour == 0.0010701431533065118
        //FourTwo == 0.001978449873841896
        //threethree == 0.0024097535793804552

        CJKChaaar threeSixFour = jundaMap.get(364);
        CJKChaaar fourSevenThree = jundaMap.get(473);


        //find procenten af tegn skrevet der ligger iden for 32

        Map<String, List<String>> interspercedNoLimit = getInterspercedNoLimit(IS.firstThreeMap);
        Map<String, List<String>> interspercedSixSix = getInterspercedNoLimit(IS.sixSixMap);

        System.out.println("end");
    }

    private Map<String, List<String>> getInterspercedNoLimit(HashMap<String, List<CJKChaaar>> firstThreeMap) {
        Map<String, List<Double>> entryList = new LinkedHashMap<>();
        for (String key : firstThreeMap.keySet()) {
            List<Double> sublistSort = firstThreeMap.get(key).stream()
                    .map(x -> x.getIntersperced()).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            entryList.put(key, sublistSort);
        }

        Map<String, Integer> intMap = new HashMap<>();
        for (String kkey : firstThreeMap.keySet()) {
            intMap.put(kkey, firstThreeMap.get(kkey).size());
        }

        List<Entry<String, Integer>> newMap = new ArrayList<>(intMap.entrySet());
        newMap.sort(Entry.comparingByValue(Comparator.reverseOrder()));


        Map<String, List<String>> result = new LinkedHashMap<>();
        for (Entry<String, Integer> eentry : newMap) {
            List<CJKChaaar> li1 = firstThreeMap.get(eentry.getKey());
            Map<Double, CJKChaaar> ma1 = new HashMap<>();
            for (CJKChaaar ent1 : li1) {
                ma1.put(ent1.getIntersperced(), ent1);
            }
            List<Double> vall = firstThreeMap.get(eentry.getKey()).stream()
                    .map(x -> x.getIntersperced())
                    .sorted().collect(Collectors.toList());
            List<String> busres = new ArrayList<>();
            for (Double inter : vall) {
                CJKChaaar mares = ma1.get(inter);
                busres.add(mares.getCJK() + " " + mares.getIntersperced());
            }
            result.put(eentry.getKey(), busres);
        }

        return result;
    }

    private Map<String, List<String>> getCharPlusIntersperced(HashMap<String, List<CJKChaaar>> twoFour) {
        HashMap<String, Integer> intMap = new HashMap<>();
        for (String shortCode : twoFour.keySet()) {
            intMap.put(shortCode, twoFour.get(shortCode).size());
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(intMap.entrySet());
        entryList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        Map<String, List<String>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            List<CJKChaaar> eachCJklist = twoFour.get(entry.getKey());
            Comparator<CJKChaaar> comparator = Comparator.comparingDouble(a -> a.getIntersperced());
            Collections.sort(eachCJklist, comparator);
            List<String> CJkToString = eachCJklist.stream()
                    .map(x -> x.getCJK() + " " + x.getIntersperced()).collect(Collectors.toList());
            sortedMap.put(entry.getKey(), CJkToString);
        }
        return sortedMap;
    }

    private Map<String, List<String>> getCharPlusTzaiord(HashMap<String, List<CJKChaaar>> twoFour) {
        HashMap<String, Integer> intMap = new HashMap<>();
        for (String shortCode : twoFour.keySet()) {
            intMap.put(shortCode, twoFour.get(shortCode).size());
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(intMap.entrySet());
        entryList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        Map<String, List<String>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            List<CJKChaaar> eachCJklist = twoFour.get(entry.getKey()).stream()
                    .filter(x -> Objects.nonNull(x.getTzai())).collect(Collectors.toList());
            Comparator<CJKChaaar> comparator = Comparator.comparingInt(a -> a.getTzai().getOrdinal());
            Collections.sort(eachCJklist, comparator);
            List<String> CJkToString = eachCJklist.stream()
                    .map(x -> x.getCJK() + " " + x.getTzai().getOrdinal()).collect(Collectors.toList());
            sortedMap.put(entry.getKey(), CJkToString);
        }
        return sortedMap;
    }

    private Map<String, List<String>> getCharPlusJundaord(HashMap<String, List<CJKChaaar>> twoFour) {
        HashMap<String, Integer> intMap = new HashMap<>();
        for (String shortCode : twoFour.keySet()) {
            intMap.put(shortCode, twoFour.get(shortCode).size());
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(intMap.entrySet());
        entryList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        Map<String, List<String>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            List<CJKChaaar> eachCJklist = twoFour.get(entry.getKey()).stream()
                    .filter(x -> Objects.nonNull(x.getJunda())).collect(Collectors.toList());
            Comparator<CJKChaaar> comparator = Comparator.comparingInt(a -> a.getJunda().getOrdinal());
            Collections.sort(eachCJklist, comparator);
            List<String> CJkToString = eachCJklist.stream()
                    .map(x -> x.getCJK() + " " + x.getJunda().getOrdinal()).collect(Collectors.toList());
            sortedMap.put(entry.getKey(), CJkToString);
        }
        return sortedMap;
    }

    private Map<String, List<CJKChaaar>> getSortedByTzaiAndListLength(HashMap<String, List<CJKChaaar>> twoFour) {
        HashMap<String, Integer> intMap = new HashMap<>();
        for (String shortCode : twoFour.keySet()) {
            intMap.put(shortCode, twoFour.get(shortCode).size());
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(intMap.entrySet());
        entryList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        Map<String, List<CJKChaaar>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            List<CJKChaaar> eachCJklist = twoFour.get(entry.getKey()).stream()
                    .filter(x -> Objects.nonNull(x.getTzai())).collect(Collectors.toList());
            Comparator<CJKChaaar> comparator = Comparator.comparingInt(a -> a.getTzai().getOrdinal());
            Collections.sort(eachCJklist, comparator);
            sortedMap.put(entry.getKey(), eachCJklist);
        }
        return sortedMap;
    }

    private Map<String, List<CJKChaaar>> getSortedByInterspercedAndListLength(HashMap<String, List<CJKChaaar>> twoFour) {
        HashMap<String, Integer> intMap = new HashMap<>();
        for (String shortCode : twoFour.keySet()) {
            intMap.put(shortCode, twoFour.get(shortCode).size());
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(intMap.entrySet());
        entryList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        Map<String, List<CJKChaaar>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            List<CJKChaaar> eachCJklist = twoFour.get(entry.getKey());
            Comparator<CJKChaaar> comparator = Comparator.comparingDouble(a -> a.getIntersperced());
            Collections.sort(eachCJklist, comparator);
            sortedMap.put(entry.getKey(), eachCJklist);
        }
        return sortedMap;
    }

    private Map<String, List<CJKChaaar>> getSortedByJundaAndListLength(HashMap<String, List<CJKChaaar>> twoFour) {
        HashMap<String, Integer> intMap = new HashMap<>();
        for (String shortCode : twoFour.keySet()) {
            intMap.put(shortCode, twoFour.get(shortCode).size());
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(intMap.entrySet());
        entryList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        Map<String, List<CJKChaaar>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            List<CJKChaaar> eachCJklist = twoFour.get(entry.getKey()).stream()
                    .filter(x -> Objects.nonNull(x.getJunda())).collect(Collectors.toList());
            Comparator<CJKChaaar> comparator = Comparator.comparingInt(a -> a.getJunda().getOrdinal());
            Collections.sort(eachCJklist, comparator);
            sortedMap.put(entry.getKey(), eachCJklist);
        }
        return sortedMap;
    }

    private List<List<String>>  frequencyOfCodeJUNDA(HashMap<String, List<CJKChaaar>> fourOnehashMap,
                                                     Map<Integer, CJKChaaar> jundaMap, int limit) {
        //among the 2-4 hashmap entries, turn thm into jundaOrdinals, sort them, remove the first 16,
        //remove numbers above 4000, return the longest list.

        List<List<Integer>> jundaNumbersBelow5000 = mapOfCharToNestedListOfIntJUNDA(fourOnehashMap);
        jundaNumbersBelow5000.sort((a, b) -> Integer.compare(b.size(), a.size()));
        //find procenten af tegn skrevet der ligger iden for 16
        List<List<Integer>> percentbelowLimit = jundaNumbersBelow5000.stream().map(sublist -> onlyLast(sublist, limit)).collect(Collectors.toList());
        List<List<Long>> percentbelowLimit2 = percentbelowLimit.stream().map(sub -> getListSetFunctionJUNDA(sub, jundaMap)).collect(Collectors.toList());
        Long percentbelowLimit3 = percentbelowLimit2.stream()
                .flatMap(List::stream).collect(Collectors.toSet()).stream()
                .mapToLong(Long::longValue)
                .sum();

        List<List<String>> percentbelowLimitWITHCHAR = percentbelowLimit.stream()
                .map(numlist -> numlist.stream()
                        .map(item -> jundaMap.get(item).getCJK() + " " + jundaMap.get(item).getJunda().getOrdinal())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return percentbelowLimitWITHCHAR;
    }

    private List<List<String>>  frequencyOfCodeTZAI(HashMap<String, List<CJKChaaar>> fourOnehashMap,
                                                    Map<Integer, CJKChaaar> tzaiMap, int limit) {
        //among the 2-4 hashmap entries, turn thm into jundaOrdinals, sort them, remove the first 16,
        //remove numbers above 4000, return the longest list.

        List<List<Integer>> jundaNumbersBelow5000 = mapOfCharToNestedListOfIntTZAI(fourOnehashMap);
        jundaNumbersBelow5000.sort((a, b) -> Integer.compare(b.size(), a.size()));
        //find procenten af tegn skrevet der ligger iden for 16
        List<List<Integer>> percentbelowLimit = jundaNumbersBelow5000.stream().map(sublist -> onlyLast(sublist, limit)).collect(Collectors.toList());
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
        return percentbelowLimitWITHCHAR;
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

    public void generateLetterKeyFrequency() {
        System.out.println("hello");
        IntevtigationMapService IS = new IntevtigationMapService();
        //Map<String, List<String>> interspercedNoLimit = getInterspercedNoLimit(IS.firstThreeMap);
        Map<String, List<String>> interspercedSixSix = getInterspercedNoLimit(IS.sixSixMap);
        Map<String, List<CJKChaaar>> sortByIntersperced = getSortedByInterspercedAndListLength(IS.sixSixMap);

        Set<String> keys = interspercedSixSix.keySet();
        Map<String, Long> totalJundaFreq = new HashMap<>();

        for (String eachKey : keys) {
            Long res = sortByIntersperced.get(eachKey).stream()
                    .filter(y -> Objects.nonNull(y.getTzai()))
                    .map(x -> x.getTzai().getOccurrences())
                    .mapToLong(Long::longValue).sum();
            totalJundaFreq.put(eachKey, res);
        }

        List<String> allChars = Arrays.stream(keys.stream().collect(Collectors.joining()).split(""))
                .distinct().sorted().collect(Collectors.toList());

        Map<String, Long> myMap = new HashMap<>();
        for (char c = 'a'; c <= 'z'; c++) {
            myMap.put(String.valueOf(c), 0L);
        }

        for (String eachKey : keys) {
            Long res = totalJundaFreq.get(eachKey);
            for (String chh : eachKey.split("")) {
                Long currentVal = myMap.get(chh);
                currentVal += res;
                myMap.put(chh, currentVal);
            }
        }

        List<Map.Entry<String, Long>> list = new ArrayList<>(myMap.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<String, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        long qwert = sortedMap.keySet().stream()
                .filter(z -> Arrays.stream("qwert".split("")).toList().contains(z))
                .map(y -> sortedMap.get(y)).mapToLong(Long::longValue).sum();
        long yuiop = sortedMap.keySet().stream()
                .filter(z -> Arrays.stream("yuiop".split("")).toList().contains(z))
                .map(y -> sortedMap.get(y)).mapToLong(Long::longValue).sum();
        long asdfg = sortedMap.keySet().stream()
                .filter(z -> Arrays.stream("asdfg".split("")).toList().contains(z))
                .map(y -> sortedMap.get(y)).mapToLong(Long::longValue).sum();
        long hjklm = sortedMap.keySet().stream()
                .filter(z -> Arrays.stream("hjklm".split("")).toList().contains(z))
                .map(y -> sortedMap.get(y)).mapToLong(Long::longValue).sum();
        long xcvbn = sortedMap.keySet().stream()
                .filter(z -> Arrays.stream("xcvbn".split("")).toList().contains(z))
                .map(y -> sortedMap.get(y)).mapToLong(Long::longValue).sum();

        long tyghx = sortedMap.keySet().stream()
                .filter(z -> Arrays.stream("qwert".split("")).toList().contains(z))
                .map(y -> sortedMap.get(y)).mapToLong(Long::longValue).sum();
        long rufjc = sortedMap.keySet().stream()
                .filter(z -> Arrays.stream("yuiop".split("")).toList().contains(z))
                .map(y -> sortedMap.get(y)).mapToLong(Long::longValue).sum();
        long eidkv = sortedMap.keySet().stream()
                .filter(z -> Arrays.stream("asdfg".split("")).toList().contains(z))
                .map(y -> sortedMap.get(y)).mapToLong(Long::longValue).sum();
        long woslb = sortedMap.keySet().stream()
                .filter(z -> Arrays.stream("hjklm".split("")).toList().contains(z))
                .map(y -> sortedMap.get(y)).mapToLong(Long::longValue).sum();
        long qpamn = sortedMap.keySet().stream()
                .filter(z -> Arrays.stream("xcvbn".split("")).toList().contains(z))
                .map(y -> sortedMap.get(y)).mapToLong(Long::longValue).sum();


        System.out.println("it looks like the order shoud be leftRight: 3 4 5 1 2 - 2 1 5 4 3");
        System.out.println("and by category: asdfg = 1, hjklm = 5, qwert = 3, yuiop = 4, xcvbn = 2");
    }
}
