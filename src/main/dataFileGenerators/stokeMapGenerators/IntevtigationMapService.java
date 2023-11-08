package main.dataFileGenerators.stokeMapGenerators;

import main.Models.CJKChaaar;

import java.util.Map;

import main.Models.*;

import java.util.Map;

import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;

import java.util.*;
import java.util.stream.Collectors;

import main.Models.CJKChaaar;
import main.Models.sortingEnums.InitialRadicals;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class IntevtigationMapService {

    public Map<String, CJKChaaar> charToInfoCJKMap;
    public List<CJKChaaar> CJKList;

    public HashMap<String, List<CJKChaaar>> sixSixMap;
    public HashMap<String, List<CJKChaaar>> firstThreeMap;
    public HashMap<String, List<CJKChaaar>> twoTwoHashmap;
    public HashMap<String, List<CJKChaaar>> twofourhashMap;
    public HashMap<String, List<CJKChaaar>> fourOnehashMap;
    public HashMap<String, List<CJKChaaar>> fourTwohashMap;
    public HashMap<String, List<CJKChaaar>> threeThreehashMap;

    public IntevtigationMapService() {
        StrokeMapService strokeMapService = new StrokeMapService();
        charToInfoCJKMap = strokeMapService.charToInfoCJKMap();
        sixSixMap = groupBySixSix(charToInfoCJKMap.values().stream().toList());
        firstThreeMap = groupByFirstThree(charToInfoCJKMap.values().stream().toList());
        twoTwoHashmap = groupByTwoTwo(charToInfoCJKMap.values().stream().toList());
        twofourhashMap = groupByTwoFour(charToInfoCJKMap.values().stream().toList());
        fourOnehashMap = groupByFourOne(charToInfoCJKMap.values().stream().toList());
        fourTwohashMap = groupByFourTwo(charToInfoCJKMap.values().stream().toList());
        threeThreehashMap = groupByThreeeThree(charToInfoCJKMap.values().stream().toList());
        CJKList = charToInfoCJKMap.values().stream().toList();
    }

    public List<String> generateMapOfElemsToCreateFile(String separator) {
        RadicalExamples radiClass = new RadicalExamples();
        //the number of elements in the lis must correspond to the number of radicals
        Map<String, RadicalRecord> radicals = radiClass.testBasicRadicals(List.of(
                "", "", "", "", "", "", "", "a"));
        CharSmallService smallService = new CharSmallService();

        Parameters params = new Parameters(List.of(3,1),
                BasicStroke.DoubleStrokeOnly,
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                radicals,
                true);

        Map<String, List<CharSmall>> sortedMap = smallService.generateSortedByFreq(params);

        List<Integer> charLength = differentSizecodes2(sortedMap);
        List<String> result = generateDataForFile2(sortedMap, charLength, separator);
        return result;
    }

    private List<String> generateDataForFile2(Map<String, List<CharSmall>> sortedMap,
                                              List<Integer> charLength,
                                              String separator) {
        Map<Integer, List<List<String>>> result_1 = new HashMap<>();
        Set<String> CJKcharSet = new HashSet<>();
        for (Integer chLength : charLength) {
            List<List<String>> CJKsWithLength = new ArrayList<>();
            //List<CJKChaaar> fullCodeHasLength = CJKsWithFullCodeWithLen(chLength);
            List<CharSmall> fullCodeHasLength = CJKsWithFullCodeWithLen2(sortedMap, chLength);
            for (CharSmall full : fullCodeHasLength) {
                for (String eachFull : full.codesForPrint) {
                    if (full.codesForPrint.contains("d")) {
                        String test = "";
                    }
                    String fileStr = full.getCJK() + separator + eachFull;
                    if (!CJKcharSet.contains(fileStr)) {
                        String doubleStr = full.getFrequency().toString();
                        String CJK = full.getCJK();
                        CJKsWithLength.add(List.of(doubleStr, CJK, fileStr));
                        CJKcharSet.add(fileStr);
                    }
                }
            }
            Comparator<List<String>> comparator = Comparator.comparingDouble(list -> Double.parseDouble(list.get(0)));
            comparator = comparator.thenComparing(list -> list.get(2));
            Collections.sort(CJKsWithLength, comparator);

            result_1.put(chLength, CJKsWithLength);
        }

        List<String> finalStr = new ArrayList<>();
        List<Integer> codeLengths = result_1.keySet().stream().sorted().collect(Collectors.toList());
        for (Integer codeLen : codeLengths) {
            List<List<String>> levelOfCodeLenSprted = result_1.get(codeLen);
            for (List<String> eachCodeEntry : levelOfCodeLenSprted) {
                finalStr.add(eachCodeEntry.get(2));
            }
        }
        return finalStr;
    }

    private List<String> generateDataForFile(List<Integer> charLength, String separator) {
        Map<Integer, List<List<String>>> result_1 = new HashMap<>();
        Set<String> CJKcharSet = new HashSet<>();
        for (Integer chLength : charLength) {
            List<List<String>> CJKsWithLength = new ArrayList<>();
            //List<CJKChaaar> fullCodeHasLength = CJKsWithFullCodeWithLen(chLength);
            List<CJKChaaar> fullCodeHasLength = CJKsWithFullCodeWithLen(chLength);
            for (CJKChaaar full : fullCodeHasLength) {
                for (String eachFull : full.getFullCode()) {
                    if (full.getFullCode().contains("d")) {
                        String test = "";
                    }
                    String fileStr = full.getCJK() + separator + eachFull;
                    if (!CJKcharSet.contains(fileStr)) {
                        String doubleStr = full.getIntersperced().toString();
                        String CJK = full.getCJK();
                        CJKsWithLength.add(List.of(doubleStr, CJK, fileStr));
                        CJKcharSet.add(fileStr);
                    }
                }
            }
            Comparator<List<String>> comparator = Comparator.comparingDouble(list -> Double.parseDouble(list.get(0)));
            comparator = comparator.thenComparing(list -> list.get(2));
            Collections.sort(CJKsWithLength, comparator);

            result_1.put(chLength, CJKsWithLength);
        }

        List<String> finalStr = new ArrayList<>();
        List<Integer> codeLengths = result_1.keySet().stream().sorted().collect(Collectors.toList());
        for (Integer codeLen : codeLengths) {
            List<List<String>> levelOfCodeLenSprted = result_1.get(codeLen);
            for (List<String> eachCodeEntry : levelOfCodeLenSprted) {
                finalStr.add(eachCodeEntry.get(2));
            }
        }
        return finalStr;
    }

    private List<CJKChaaar> CJKsWithPlainThreeThreeWithLen(Integer chLength) {
        List<CJKChaaar> onlyWithFullOfLen = new ArrayList<>();
        for (CJKChaaar value : charToInfoCJKMap.values()) {
            Set<String> fulls = value.getPlainThreeThreeCode();
            Set<String> newFulls = new HashSet<>();
            for (String code : fulls) {
                if (code.length() == chLength) {
                    newFulls.add(code);
                }
            }
            if (newFulls.size() > 0) {
                value.setPlainThreeThreeCode(newFulls);
                onlyWithFullOfLen.add(value);
            }
        }
        return onlyWithFullOfLen;
    }

    private List<CJKChaaar> CJKsWithThreeThreeCodeWithLen(Integer chLength) {
        List<CJKChaaar> onlyWithFullOfLen = new ArrayList<>();
        for (CJKChaaar value : charToInfoCJKMap.values()) {
            Set<String> fulls = value.getThreeThreeCode();
            Set<String> newFulls = new HashSet<>();
            for (String code : fulls) {
                if (code.length() == chLength) {
                    newFulls.add(code);
                }
            }
            if (newFulls.size() > 0) {
                value.setThreethreeCode(newFulls);
                onlyWithFullOfLen.add(value);
            }
        }
        return onlyWithFullOfLen;
    }

    private List<CJKChaaar> CJKsWithEditedFullCodeWithLen(Integer chLength) {
        List<CJKChaaar> onlyWithFullOfLen = new ArrayList<>();
        for (CJKChaaar value : charToInfoCJKMap.values()) {
            Set<String> fulls = value.getEditedFullCode();
            Set<String> newFulls = new HashSet<>();
            for (String code : fulls) {
                if (code.length() == chLength) {
                    newFulls.add(code);
                }
            }
            if (newFulls.size() > 0) {
                value.setEditedFullCode(newFulls);
                onlyWithFullOfLen.add(value);
            }
        }
        return onlyWithFullOfLen;
    }

    private List<CharSmall> CJKsWithFullCodeWithLen2(Map<String, List<CharSmall>> sortedMap, Integer chLength) {
        List<CharSmall> onlyWithFullOfLen = new ArrayList<>();
        List<CharSmall> smallChars = sortedMap.values().stream().flatMap(List::stream).toList();
        for (CharSmall value : smallChars) {
            Set<String> fulls = value.getCodes();
            Set<String> newFulls = new HashSet<>();
            for (String code : fulls) {
                if (code.length() == chLength) {
                    newFulls.add(code);
                }
            }
            if (newFulls.size() > 0) {
                value.codesForPrint = newFulls;
                onlyWithFullOfLen.add(value);
            }
        }
        return onlyWithFullOfLen;
    }

    private List<CJKChaaar> CJKsWithFullCodeWithLen(Integer chLength) {
        List<CJKChaaar> onlyWithFullOfLen = new ArrayList<>();
        for (CJKChaaar value : charToInfoCJKMap.values()) {
            Set<String> fulls = value.getSixSix();
            Set<String> newFulls = new HashSet<>();
            for (String code : fulls) {
                if (code.length() == chLength) {
                    newFulls.add(code);
                }
            }
            if (newFulls.size() > 0) {
                value.setFullCode(newFulls);
                onlyWithFullOfLen.add(value);
            }
        }
        return onlyWithFullOfLen;
    }


    private List<Integer> differentSizecodes2(Map<String, List<CharSmall>> map) {
        Set<String> allStringsToFile = new HashSet<>();
        List<CharSmall> smallChars = map.values().stream().flatMap(List::stream).toList();
                /*
                map.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());*/
        for (CharSmall chaar : smallChars) {
            //allStringsToFile.addAll(chaar.getThreeThreeCode());
            //allStringsToFile.addAll(chaar.getPlainThreeThreeCode());
            //allStringsToFile.addAll(chaar.getFullCode());
            allStringsToFile.addAll(chaar.getCodes());
            String test = "";
        }
        List<Integer> charLength = allStringsToFile.stream()
                .map(x -> x.length()).distinct().sorted().collect(Collectors.toList());
        return charLength;
    }



    private List<Integer> differentSizecodes(Map<String, CJKChaaar> map) {
        Set<String> allStringsToFile = new HashSet<>();
        for (CJKChaaar chaar : map.values()) {
            //allStringsToFile.addAll(chaar.getThreeThreeCode());
            //allStringsToFile.addAll(chaar.getPlainThreeThreeCode());
            //allStringsToFile.addAll(chaar.getFullCode());
            allStringsToFile.addAll(chaar.getSixSix());
            String test = "";
        }
        List<Integer> charLength = allStringsToFile.stream()
                .map(x -> x.length()).distinct().sorted().collect(Collectors.toList());
        return charLength;
    }

    private HashMap<String, List<CJKChaaar>> groupByThreeeThree(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getThreeThreeCode()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

    private HashMap<String, List<CJKChaaar>> groupByFourTwo(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getFourTwoCode()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }


    private HashMap<String, List<CJKChaaar>> groupBySixSix(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getSixSix()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

    //getFirstThreeCode
    private HashMap<String, List<CJKChaaar>> groupByFirstThree(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getFirstThreeCode()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

    public static HashMap<String, List<CJKChaaar>> groupByTwoTwo(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getTwotwoCode()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

    public static HashMap<String, List<CJKChaaar>> groupByTwoFour(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getTwofourCode()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

    public static HashMap<String, List<CJKChaaar>> groupByFourOne(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getFourOne()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

}
