package main.dataFileGenerators.stokeMapGenerators;

import main.Models.*;

import java.util.Map;

import main.Models.sortingEnums.Freq;

import java.util.*;
import java.util.stream.Collectors;

public class CharSmallService {

    public Map<String, CJKChaaar> charToInfoCJKMap;


    public Map<String, List<CharSmall>> generateSortedByFreq(Parameters params,
                                                             Map<String, String> letters
            //, int indexToSortOuterMap
    ) {

        List<CharSmall> jundaSorted = generateChars(params, letters);
        Map<String, List<CharSmall>> sortByFreq = codeToCharSortetByFreq(jundaSorted);
        //Map<String, List<CharSmall>> sortedMap = sortMapByRarestFreqAtIndex(sortByFreq, 9)
        //2023 - 12 - 03
        // hertil er hvert element liste sorteret korrekt, men selve mappen er ikke sorteret.
        // den skal sorteres efter frekvensen af det index der angives,
        // eller ogaa skal der laves en ny funktion der sortere.
        // jeg tror ikke der er negative aspekter ved at sortere, det er jo bare
        // "strengthening of a promise" om Rich Hickey siger;
        return sortByFreq;
    }

    public CharSmallService() {
        StrokeMapService strokeMapService = new StrokeMapService();
        charToInfoCJKMap = strokeMapService.charToInfoCJKMap();
    }


    public List<CharSmall> generateChars(Parameters params, Map<String, String> letters) {
        Collection<CJKChaaar> chars = charToInfoCJKMap.values();
        List<CharSmall> result = chars.stream()
                .map(x -> generateChar(x, params, letters))
                .collect(Collectors.toList());
        return result;
    }

    private CharSmall generateChar(
            CJKChaaar CJK,
            Parameters params,
            Map<String, String> letters) {
        //TODO: reimpelent the code string to be an object that can handle radicals
        if (CJK.getCJK().equals("硢")) {
            String test = "";
        }
        Double createFrequency = createFrequencyFromParam(CJK, params.getFreq());
        Double secondaryFreq = createFrequencyFromParam2(CJK, params.getFreq());
        if (createFrequency < 1000 && secondaryFreq < 1000) {
            String test = "";
        }
        Set<CodeConverter> codeConverter = new HashSet<>();
        //if (CJK.getCJK().equals("誠")) {
        //    String test2 = "";
        //}
        for (String eachCode : CJK.getFullCode()) {
            if (eachCode.equals("1212251125214")) {
                String test3 = "";
            }
            if (params.getIncludeLongDoubleStrokes()) {
                //generate Code converter object for long - 6 letter codes
                CodeConverter singleConverter1 = new CodeConverter(CJK, eachCode, params, true, letters);
                codeConverter.add(singleConverter1);
                //generate Code converter object for short - 4 - letter codes
                CodeConverter singleConverter2 = new CodeConverter(CJK, eachCode, params, false, letters);
                codeConverter.add(singleConverter2);
                //generate Code converter object for short - 4 letter codes that doesnt use initial radicals
                if (singleConverter2.getSubstringLeftAfterRadical().length() == 0) {
                    Parameters params_noRadicals = new Parameters(
                            params.getStrokeRange(),
                            params.getBasicStroke(),
                            params.getFreq(),
                            params.getInitialRadicals(),
                            new HashMap<>(),
                            params.getIncludeLongDoubleStrokes());
                    CodeConverter singleConverter3 = new CodeConverter(CJK, eachCode, params_noRadicals, false, letters);
                    codeConverter.add(singleConverter3);
                }
            }else {
                //generate Code converter object for short - 4 - letter codes
                CodeConverter singleConverter2 = new CodeConverter(CJK, eachCode, params, false, letters);
                codeConverter.add(singleConverter2);
                //generate Code converter object for short - 4 letter codes that doesnt use initial radicals
                if (singleConverter2.getSubstringLeftAfterRadical().length() == 0) {
                    Parameters params_noRadicals = new Parameters(
                            params.getStrokeRange(),
                            params.getBasicStroke(),
                            params.getFreq(),
                            params.getInitialRadicals(),
                            new HashMap<>(),
                            params.getIncludeLongDoubleStrokes());
                    CodeConverter singleConverter3 = new CodeConverter(CJK, eachCode, params_noRadicals, false, letters);
                    codeConverter.add(singleConverter3);
                }
            }
        }
        CharSmall resultChar = new CharSmall(
                CJK.getCJK(),
                createFrequency,
                secondaryFreq,
                codeConverter,
                CJK.getConwayCode(),
                CJK.getFirstOrderSplit());
        return resultChar;
    }

    private Double createFrequencyFromParam2(CJKChaaar cjk, Freq freq) {
        Double result = 0.0;

        Integer jundaOrd = Objects.nonNull(cjk.getJunda()) ? cjk.getJunda().getOrdinal() : null;
        Integer tzaiOrd = Objects.nonNull(cjk.getTzai()) ? cjk.getTzai().getOrdinal() : null;
        Double jundaOccurRatio = Objects.nonNull(cjk.getJunda())
                ? getRatio(cjk.getJunda().getOccurrences(), cjk.getJunda().getTotalOccurrences()) : null;
        Double tzaiOccurRatio = Objects.nonNull(cjk.getTzai())
                ? getRatio(cjk.getTzai().getOccurrences(), cjk.getTzai().getTotalOccurrences()) : null;

        String hex = cjk.getUnicodeHex().substring(2, cjk.getUnicodeHex().length());
        Integer resultHex = Integer.parseUnsignedInt(hex, 16);
        Double doubleHex = Double.parseDouble(String.valueOf(resultHex));

        if (Freq.Intersperced.equals(freq)) {
            result = getIntersperced(jundaOrd, tzaiOrd, doubleHex);
        } else if (Freq.TzaiFirst.equals(freq)) {
            if (Objects.nonNull(jundaOrd)) {
                result = Double.valueOf(jundaOrd);
            } else if (Objects.nonNull(tzaiOrd)) {
                result = Double.valueOf(1000000 + tzaiOrd);
            } else {
                result = 2000000 + doubleHex;
            }
        } else if (Freq.JundaFirst.equals(freq)) {
            if (Objects.nonNull(tzaiOrd)) {
                result = Double.valueOf(tzaiOrd);
            } else if (Objects.nonNull(jundaOrd)) {
                result = Double.valueOf(1000000 + jundaOrd);
            } else {
                result = 2000000 + doubleHex;
            }
        }
        return result;
    }

    private Double createFrequencyFromParam(CJKChaaar cjk, Freq freq) {
        Double result = 0.0;

        Integer jundaOrd = Objects.nonNull(cjk.getJunda()) ? cjk.getJunda().getOrdinal() : null;
        Integer tzaiOrd = Objects.nonNull(cjk.getTzai()) ? cjk.getTzai().getOrdinal() : null;
        Double jundaOccurRatio = Objects.nonNull(cjk.getJunda())
                ? getRatio(cjk.getJunda().getOccurrences(), cjk.getJunda().getTotalOccurrences()) : null;
        Double tzaiOccurRatio = Objects.nonNull(cjk.getTzai())
                ? getRatio(cjk.getTzai().getOccurrences(), cjk.getTzai().getTotalOccurrences()) : null;

        String hex = cjk.getUnicodeHex().substring(2, cjk.getUnicodeHex().length());
        Integer resultHex = Integer.parseUnsignedInt(hex, 16);
        Double doubleHex = Double.parseDouble(String.valueOf(resultHex));

        if (Freq.Intersperced.equals(freq)) {
            result = getIntersperced(jundaOrd, tzaiOrd, doubleHex);
        } else if (Freq.JundaFirst.equals(freq)) {
            if (Objects.nonNull(jundaOrd)) {
                result = Double.valueOf(jundaOrd);
            } else if (Objects.nonNull(tzaiOrd)) {
                result = Double.valueOf(1000000 + tzaiOrd);
            } else {
                result = 2000000 + doubleHex;
            }
        } else if (Freq.TzaiFirst.equals(freq)) {
            if (Objects.nonNull(tzaiOrd)) {
                result = Double.valueOf(tzaiOrd);
            } else if (Objects.nonNull(jundaOrd)) {
                result = Double.valueOf(1000000 + jundaOrd);
            } else {
                result = 2000000 + doubleHex;
            }
        }
        return result;
    }

    private Double getIntersperced(Integer jundaOrd,
                                   Integer tzaiOrd,
                                   Double doubleHex) {
        // CJKfrequency(String character, int ordinal, Long occurrences, Long totalOccurrences)
        if (Objects.nonNull(jundaOrd) && Objects.nonNull(tzaiOrd)) {
            if (jundaOrd < tzaiOrd) {
                return (double) jundaOrd;
            } else {
                return (double) tzaiOrd;
            }
        } else if (Objects.nonNull(jundaOrd)) {
            return (double) jundaOrd;
        } else if (Objects.nonNull(tzaiOrd)) {
            return (double) tzaiOrd;
        }
        return 100000 + doubleHex;
    }

    private Double getRatio(Long occurrences, Long totalOccurrences) {
        Double result = ((double) occurrences) / ((double) totalOccurrences);
        return result;
    }


    /*
    private List<Integer> strokeRange;
    private BasicStroke basicStroke;
    private Freq freq;
    private InitialRadicals initialRadicals;*/

/*
    private Set<String> fullCodeToLetters(Set<String> fullCode, Map<String, String> stringStringMap) {
        Set<String> result = new HashSet<>();
        for (String code : fullCode) {
            List<String> pairs = Arrays.stream(code.split("(?<=\\G.{2})")).toList();
            String pairsWithLetter = pairs.stream().map(x -> stringStringMap.get(x)).collect(Collectors.joining());
            result.add(pairsWithLetter);
        }
        return result;
    }*/


    public Map<String, List<String>> JundaRadicals() {
        //codes, numOfCodes, letter, exception
        List<String> tree = List.of("1234", "4", "A", ""); // 木
        List<String> foot = List.of("1512121", "7", "B",""); // 跑
        List<String> bamboo = List.of("312312", "6", "C",""); // 签
        List<String> insect = List.of("151214", "6", "D",""); // 蛀
        List<String> handGroundWork = List.of("121", "3", "E", ""); // 扣址工
        List<String> eye = List.of("25111", "5", "F", ""); // 盯

        Map<String, List<String>> result = new HashMap<>();
        result.put(tree.get(0), tree);
        result.put(foot.get(0), foot);
        result.put(bamboo.get(0), bamboo);
        result.put(insect.get(0), insect);
        result.put(handGroundWork.get(0), handGroundWork);
        result.put(eye.get(0), eye);
        return result;
    }

    private Map<String, RadicalRecord> jundaAndTzaiRadicals() {
        Map<String, RadicalRecord> radicalMap = new HashMap<>();
        //String code, Integer codeLength, String letter, Set<String> exceptions
        radicalMap.put("34112431", new RadicalRecord("34112431", 8, "A" ,Set.of(), "", Set.of()));
        radicalMap.put("31115", new RadicalRecord("31115",5, "A", Set.of(), "", Set.of())); //错
        radicalMap.put("35251214444", new RadicalRecord("35251214444", 11, "B", Set.of(), "", Set.of())); //穌
        radicalMap.put("35251211", new RadicalRecord("35251211", 8,  "B" ,Set.of(), "", Set.of())); //鲍
        radicalMap.put("1211254444", new RadicalRecord("1211254444", 10,  "C", Set.of(), "", Set.of())); //驗
        radicalMap.put("2111254444", new RadicalRecord("2111254444",10, "C", Set.of(), "", Set.of())); //驗
        radicalMap.put("551", new RadicalRecord("551",3, "C", Set.of(), "", Set.of())); //验
        radicalMap.put("25112511", new RadicalRecord("25112511",8, "D", Set.of(), "", Set.of())); //問
        radicalMap.put("425", new RadicalRecord("425", 3, "D", Set.of(), "", Set.of())); //问
        radicalMap.put("245", new RadicalRecord("245", 3,"D", Set.of(), "", Set.of())); //问
        radicalMap.put("344511211", new RadicalRecord("344511211", 9, "E", Set.of(), "", Set.of())); //館
        radicalMap.put("341511211", new RadicalRecord("341511211", 9, "E", Set.of(), "", Set.of())); //館
        radicalMap.put("34451154", new RadicalRecord("34451154", 8, "E", Set.of(), "", Set.of())); //館
        radicalMap.put("34151154", new RadicalRecord("34151154", 8,"E", Set.of(), "", Set.of())); //館
        radicalMap.put("355", new RadicalRecord("355", 3, "E", Set.of(), "", Set.of())); //馆
        radicalMap.put("12521111", new RadicalRecord("12521111", 8,  "F", Set.of(), "", Set.of())); //電
        radicalMap.put("12524134", new RadicalRecord("12524134",8, "F", Set.of(), "", Set.of())); //電
        radicalMap.put("12524444", new RadicalRecord("12524444", 8, "F", Set.of(), "", Set.of())); //電
        radicalMap.put("14521111", new RadicalRecord("14521111", 8, "F", Set.of(), "", Set.of())); //電
        radicalMap.put("14524134", new RadicalRecord("14524134", 8, "F", Set.of(), "", Set.of())); //電
        radicalMap.put("14524444", new RadicalRecord("14524444", 8, "F", Set.of(), "", Set.of())); //電
        radicalMap.put("4111251", new RadicalRecord("4111251", 7, "G", Set.of(), "", Set.of())); //說
        radicalMap.put("1111251", new RadicalRecord("1111251", 7, "G", Set.of(), "" ,Set.of())); //說
        radicalMap.put("45", new RadicalRecord("45", 2,  "G", Set.of(), "", Set.of())); //说
        radicalMap.put("314314", new RadicalRecord("314314", 6, "H", Set.of(), "", Set.of())); //"等"
        radicalMap.put("122125112", new RadicalRecord("122125112", 9,"I", Set.of(), "", Set.of())); //靶
        return radicalMap;
    }

    private Map<String, String> initialRadicals(Map<String, CJKChaaar> charToInfoCJKMap) {

        List<CJKChaaar> tzairadicalList = List.of(
                        "錯", "穌", "鲍", "驗", "問", //"靶",
                        "館", "電", "說", "等", "鬍", "髟", "路", "結", "蝶" ,"體",
                        "軟").stream()
                .map(x -> charToInfoCJKMap.get(x)).collect(Collectors.toList());

        List<CJKChaaar> jundaradicalList = List.of(
                        "勒", "電", "稣", "路", "體"//"蝶", "等",, "竭", "萌"
                        //, , "稣", , "模", , "軟"
                ).stream()
                .map(x -> charToInfoCJKMap.get(x)).collect(Collectors.toList());

        return null;
    }
    public static Map<String, List<CharSmall>> codeToCharSortetByFreq(List<CharSmall> jundaSorted) {
        Map<String, List<CharSmall>> map = jundaSorted.stream()
                .flatMap(a -> a.getCodes().stream().map(c -> new AbstractMap.SimpleEntry<>(c, a)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
        map.values().forEach(l -> l.sort(Comparator.comparingDouble(CharSmall::getFrequency)));
        //each entry in the map is sorted properly, but the whole map is also supposed to be sorted, such that
        //the first
        return map;
    }

    public static Map<String, List<CharSmall>> sortMapByListLength(Map<String, List<CharSmall>> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<String, List<CharSmall>>comparingByValue(Comparator.comparingInt(List::size)).reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    public static Map<String, List<CharSmall>> sortMapByRarestFreqAtIndex(Map<String, List<CharSmall>> map,
                                                                          Integer X) {
        Comparator<CharSmall> comparator = Comparator.comparingDouble(a -> a.getFrequency());
        return map.entrySet().stream()
                .filter(entry -> entry.getValue().size() > X)
                .peek(entry -> Collections.sort(entry.getValue(), comparator))
                .sorted(Comparator.comparingDouble(entry -> entry.getValue().get(X).getFrequency()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public static Map<Double, String> sortMap(int X, Map<String, List<CharSmall>> map) {
        Map<String, List<CharSmall>> onlyFew = map.entrySet().stream()
                .filter(z -> z.getValue().size() > X)
                .collect(Collectors.toMap(
                        x -> x.getKey(),
                        y -> y.getValue().subList(X, y.getValue().size())));

        Set<Double> alreadyUsed = new HashSet<>();
        Map<Double, String> result = new HashMap<>();
        for (Map.Entry<String, List<CharSmall>> eachOne : onlyFew.entrySet()) {
            String toUse = eachOne.getKey() + " " +String.join(" ", eachOne.getValue().stream()
                    .map(x -> x.getCJK() + "--" + x.getFrequency() + "--" + x.getSecondaryFreq()).toList());
            Double newDouble = eachOne.getValue().get(0).getFrequency();
            while (alreadyUsed.contains(newDouble)) {
                newDouble = newDouble + 0.0001;
            }
            if (!alreadyUsed.contains(newDouble)) {
                result.put(newDouble, toUse);
                alreadyUsed.add(newDouble);
            } else {
                System.out.println("ERROR: dublicate frequency" + newDouble + " "+ toUse);
            }
        }
        Map<Double, String> sortedMap = new TreeMap<>(result);
        return sortedMap;
    }

    public static Map<String, List<String>> stringifyMap(Map<String, List<CharSmall>> sortedMap) {
        LinkedHashMap<String, List<String>> result = sortedMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .map(x -> x.getCJK() + " " + x.getFrequency())
                                .collect(Collectors.toList()),
                        (a, b) -> a, LinkedHashMap::new));
        return result;
    }

    public static List<String> getStringsFromIndex(Map<String, List<String>> map, int x) {
        return map.values().stream()
                .filter(l -> l.size() > x)
                .map(l -> l.get(x))
                .collect(Collectors.toList());
    }
}
