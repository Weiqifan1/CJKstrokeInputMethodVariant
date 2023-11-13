package main.dataFileGenerators.stokeMapGenerators;

import main.Models.CJKChaaar;

import java.util.Map;

import main.Models.*;

import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;

import java.util.*;
import java.util.stream.Collectors;

import main.Models.sortingEnums.InitialRadicals;

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
        CharSmallService smallService = new CharSmallService();

        Parameters params = new Parameters(List.of(6,2),
                BasicStroke.DoubleStrokeOnly,
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                //plant   foot    bamboo  insect    tree   waterradical hand eye,,
                //say誠   thread縱   Gold錶  dor闍  horse馱    eat餞   car 軔
                radiClass.testBasicRadicals(List.of(
                        "", "f", "l", "j","k", "", "s", "d",
                        "v" , "h", "t", "n", "b", "y" ,"g")
                ),
                true);
                /*
                new Parameters(List.of(3,1),
                BasicStroke.DoubleStrokeOnly,
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                radicals,
                true);*/

        Map<String, List<CharSmall>> sortedMap = smallService.generateSortedByFreq(params);

        List<Integer> charLength = differentSizecodes2(sortedMap);
        List<String> result = generateDataForFile2(sortedMap, charLength, separator);
        result = addalphabet(result);
        result = addpunctuationECT(result);

        return result;
    }

    private List<String> addalphabet(List<String> input) {
        List<String> result = new ArrayList<>(); // format: char \t letters
        List<String> lowercase = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));
        List<String> uppercase = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
        for (String each : lowercase) {
            result.add(each  + "\t" + each.toLowerCase());
        }
        for (String each : uppercase) {
            result.add(each  + "\t" + each.toLowerCase());
        }
        result.addAll(input);
        return result;
    }

    private List<String> addpunctuationECT(List<String> input) {
        List<String> punct = new ArrayList<>();

        List<String> z1 = basicChiesePunkt("z");
        List<String> z2 = dotsAndSashes("zz");
        List<String> z3 = chineseExtraPunkt("zzz");
        List<String> z4 = generateordinaryPunct("zzzz");

        punct.addAll(z1);
        punct.addAll(z2);
        punct.addAll(z3);
        punct.addAll(z4);
        punct.addAll(input);
        return punct;
    }

    private List<String> generateordinaryPunct(String zlet) {
        List<String> result = new ArrayList<>();
        String separator = "\t";  //.add("【】" + "\t" + "zz");
        String punctuation = "";
        for (int i = 0x20; i <= 0x2F; i++) {
            punctuation += (char) i;
        }
        for (int i = 0x3A; i <= 0x40; i++) {
            punctuation += (char) i;
        }
        for (int i = 0x5B; i <= 0x60; i++) {
            punctuation += (char) i;
        }
        for (int i = 0x7B; i <= 0x7E; i++) {
            punctuation += (char) i;
        }

        List<String> dotsAndQuotes = List.of(
                ",", ".", ":", ";", "'", "`", "\"", "‘","’"
        );
        List<String> numberChars = List.of(
                "&", "~", "@", "#", "¶", "$", "£", "€", "¥"  //²³¤€¼  ¡²³¤5¼½¾‘’¥×
        );
        List<String> math = List.of(
                "×", "%", "‰", "¼","½","¾", "²","³"   //¡¤€¥ç»¬!@#45^&8()_+}|":?><ø´
        );
        List<String> bracketsAndSlash = List.of(
                "()", "[]", "{}", "<>", "^", "/",  "\\", "|"
        );
        List<String> ectChars = List.of(
                "+", "-", "_", "*", "¤", "!", "?", "¡", "¿"
        );

        for (String each : dotsAndQuotes) {
            result.add(each + separator + zlet + "x");
        }
        for (String each : numberChars) {
            result.add(each + separator + zlet + "c");
        }
        for (String each : math) {
            result.add(each + separator + zlet + "v");
        }
        for (String each : bracketsAndSlash) {
            result.add(each + separator + zlet + "b");
        }
        for (String each : ectChars) {
            result.add(each + separator + zlet + "n");
        }

        return result;
    }

    public static String getUnicodeHexValues(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            sb.append(String.format("\\u%04x", input.codePointAt(i)));
        }
        return sb.toString();
    }

    public static List<String> basicChiesePunkt(String zlet) {
        String punctString = "。，"+"\u3000"+"！？：；";
        List<String> toUnicode = getUnicodeCharacters(punctString);
        List<String> result = new ArrayList<>();
        for (String eachElem : toUnicode) {
            result.add(eachElem + "\t" + zlet);
        }
        result.add(toUnicode.get(0) + "\t" + zlet + "x");
        result.add(toUnicode.get(1) + "\t" + zlet + "c");
        result.add(toUnicode.get(2) + "\t" + zlet + "v");
        result.add(toUnicode.get(3) + "\t" + zlet + "b");
        result.add(toUnicode.get(4) + "\t" + zlet + "n");
        result.add(toUnicode.get(5) + "\t" + zlet + "m");
        return result;
    }

    public static List<String> dotsAndSashes(String zlet) {
        String punctString = "⋯…·.⸺–,、";
        List<String> toUnicode = getUnicodeCharacters(punctString);
        List<String> result = new ArrayList<>();
        for (String eachElem : toUnicode) {
            result.add(eachElem + "\t" + zlet);
        }
        result.add(toUnicode.get(0) + "\t" + zlet + "x");
        result.add(toUnicode.get(1) + "\t" + zlet + "c");
        result.add(toUnicode.get(2) + "\t" + zlet + "v");
        result.add(toUnicode.get(3) + "\t" + zlet + "b");
        result.add(toUnicode.get(4) + "\t" + zlet + "n");
        result.add(toUnicode.get(5) + "\t" + zlet + "m");
        return result;
    }

    public static List<String> chineseExtraPunkt(String zlet) {
        List<String> punct = new ArrayList<>();
        punct.add("【】" + "\t" + zlet);
        punct.add("〈〉" + "\t" + zlet);
        punct.add("《》" + "\t" + zlet);
        punct.add("「」" + "\t" + zlet);
        punct.add("『』" + "\t" + zlet);
        punct.add("﹁﹂" + "\t" + zlet);
        punct.add("～" + "\t" + zlet);
        punct.add("﹏" + "\t" + zlet);

        punct.add("【】" + "\t" + zlet + "x");
        punct.add("〈〉" + "\t" + zlet + "c");
        punct.add("《》" + "\t" + zlet + "v");
        punct.add("「」" + "\t" + zlet + "b");
        punct.add("『』" + "\t" + zlet + "n");
        punct.add("﹁﹂" + "\t" + zlet + "m");

        return punct;
    }

    public static List<String> getUnicodeCharacters(String utf16String) {
        List<String> unicodeChars = new ArrayList<>();
        for (int i = 0; i < utf16String.length(); i++) {
            char c = utf16String.charAt(i);
            if (Character.isHighSurrogate(c) && i + 1 < utf16String.length()) {
                char c2 = utf16String.charAt(i + 1);
                if (Character.isLowSurrogate(c2)) {
                    unicodeChars.add(Character.toString(c) + Character.toString(c2));
                    i++;
                }
            } else {
                unicodeChars.add(Character.toString(c));
            }
        }
        return unicodeChars;
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
