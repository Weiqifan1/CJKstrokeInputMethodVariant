package main.dataFileGenerators.stokeMapGenerators;

import main.Models.CJKChaaar;

import java.util.Map;

import main.Models.CharSmall;
import main.Models.Parameters;
import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;

import java.util.*;
import java.util.stream.Collectors;

public class CharSmallService {

    public Map<String, CJKChaaar> charToInfoCJKMap;

    public CharSmallService() {
        StrokeMapService strokeMapService = new StrokeMapService();
        charToInfoCJKMap = strokeMapService.charToInfoCJKMap();
    }


    public List<CharSmall> generateChars(Parameters params) {
        Collection<CJKChaaar> chars = charToInfoCJKMap.values();
        List<CharSmall> result = chars.stream()
                .map(x -> generateChar(x, params))
                .collect(Collectors.toList());
        return result;
    }

    private CharSmall generateChar(CJKChaaar CJK, Parameters params) {

        Set<String> initialRadicalsFromFull = CJK.getFullCode();
        //generateCodeWithRadicals(CJK.getFullCode(), ); //TODO: create radical functions

        //encoded means that the code might be changed from single stroke to double stroke
        Set<String> withCodeRange = createCoderange(initialRadicalsFromFull, params.getStrokeRange());
        Set<String> toEncoded = encodedStrokes(params.getBasicStroke(), withCodeRange);
        Double createFrequency = createFrequencyFromParam(CJK, params.getFreq());
        CharSmall resultChar = new CharSmall(CJK.getCJK(), createFrequency, toEncoded, CJK.getConwayCode());
        return resultChar;
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
            result = getIntersperced(jundaOccurRatio, tzaiOccurRatio, doubleHex);
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

    private Double getIntersperced(Double jundaOccurRatio,
                                   Double tzaiOccurRatio,
                                   Double doubleHex) {
        // CJKfrequency(String character, int ordinal, Long occurrences, Long totalOccurrences)
        if (Objects.nonNull(jundaOccurRatio) && Objects.nonNull(tzaiOccurRatio)) {
            if (jundaOccurRatio < tzaiOccurRatio) {
                return jundaOccurRatio;
            } else if (tzaiOccurRatio < jundaOccurRatio) {
                return tzaiOccurRatio;
            }
        } else if (Objects.nonNull(jundaOccurRatio)) {
            return jundaOccurRatio;
        } else if (Objects.nonNull(tzaiOccurRatio)) {
            return tzaiOccurRatio;
        }
        return 100000 + doubleHex;
    }

    private Double getRatio(Long occurrences, Long totalOccurrences) {
        Double result = ((double) occurrences) / ((double) totalOccurrences);
        return result;
    }

    private Set<String> createCoderange(Set<String> codesWithInitialRadicalsToEncoded, List<Integer> strokeRange) {
        Set<String> result = new HashSet<>();
        if (Objects.isNull(strokeRange)) {
            return codesWithInitialRadicalsToEncoded;
        } else {
            for (String each : codesWithInitialRadicalsToEncoded) {
                Integer len = strokeRange.get(0) + strokeRange.get(1);
                if (each.length() < len) {
                    result.add(each);
                } else if (strokeRange.get(1) == 0) {
                    String newstr = "";
                    try {
                        newstr = each.substring(0, strokeRange.get(0));
                    } catch (Exception e) {
                        String test = "";
                    }
                    result.add(newstr);
                } else {
                    String newstr = "";
                    try {
                        newstr = each.substring(0, strokeRange.get(0))
                                + each.substring(each.length()-strokeRange.get(1), each.length());
                    } catch (Exception e) {
                        String test = "";
                    }
                    result.add(newstr);
                }
            }
        }
        return result;
    }

    /*
    private List<Integer> strokeRange;
    private BasicStroke basicStroke;
    private Freq freq;
    private InitialRadicals initialRadicals;*/

    private Set<String> encodedStrokes(BasicStroke basicStroke,
                                       Set<String> initialRadicalsFromFull) {
        Set<String> numbers = java.util.stream.IntStream.range(0, 10)
                .mapToObj(String::valueOf).collect(Collectors.toSet());
        Set<String> result = new HashSet<>();
        for (String eachCode : initialRadicalsFromFull) {
            String resultString = null;
            String strToUse = null;
            String initial = eachCode.substring(0,1);
            if (numbers.contains(initial)) {
                 strToUse = eachCode;
            }else {
                strToUse = eachCode.substring(1, eachCode.length());
            }

            if (BasicStroke.SingleStrokeOnly.equals(basicStroke)) {
                resultString = generateLettersFromSingleStrokes(strToUse, "(?<=\\G.{1})", singleLetters());
            } else if (BasicStroke.DoubleStrokeOnly.equals(basicStroke)) {
                resultString = generateLettersFromSingleStrokes(strToUse, "(?<=\\G.{2})", doubleLetters());
            } else if (BasicStroke.BothSingleAndDouble.equals(basicStroke)) {
                resultString = generateLettersFromSingleStrokes(strToUse, "(?<=\\G.{1})", singleLetters());
                result.add(resultString);
                resultString = generateLettersFromSingleStrokes(strToUse, "(?<=\\G.{2})", doubleLetters());
                result.add(resultString);
            }
            result.add(resultString);
        }
        return result;
    }

    private String generateLettersFromSingleStrokes(String strToUse, String regex, Map<String, String> stringStringMap) {
        List<String> pairs = Arrays.stream(strToUse.split(regex)).toList();
        String pairsWithLetter = pairs.stream().map(x -> stringStringMap.get(x)).collect(Collectors.joining());
        return pairsWithLetter;
    }
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

    private Map<String, String> singleLetters() {
        Map<String, String> numToLetter = new HashMap<>();

        numToLetter.put("1", "g");
        numToLetter.put("2", "n");
        numToLetter.put("3", "t");
        numToLetter.put("4", "y");
        numToLetter.put("5",  "h");

        return numToLetter;
    }

    private Map<String, String> doubleLetters() {
        Map<String, String> numToLetter = new HashMap<>();

        numToLetter.put("1", "g");

        numToLetter.put("11",  "g");
        numToLetter.put("12",  "f");
        numToLetter.put("13",  "d");
        numToLetter.put("14",  "s");
        numToLetter.put("15",  "a");

        numToLetter.put("5", "h");

        numToLetter.put("51", "h");
        numToLetter.put("52", "j");
        numToLetter.put("53", "k");
        numToLetter.put("54", "l");
        numToLetter.put("55", "m");

        numToLetter.put("3", "t");

        numToLetter.put("31", "t");
        numToLetter.put("32", "r");
        numToLetter.put("33", "e");
        numToLetter.put("34", "w");
        numToLetter.put("35", "q");

        numToLetter.put("4", "y");

        numToLetter.put("41", "y");
        numToLetter.put("42", "u");
        numToLetter.put("43", "i");
        numToLetter.put("44", "o");
        numToLetter.put("45", "p");

        numToLetter.put("2", "n");

        numToLetter.put("21", "n");
        numToLetter.put("22", "b");
        numToLetter.put("23", "v");
        numToLetter.put("24", "c");
        numToLetter.put("25", "x");
        return numToLetter;
    }

    private Map<String, String> jundaAndTzaiRadicals() {
        Map<String, String> radicalMap = new HashMap<>();
        radicalMap.put("34112431", "A8"); //錯
        radicalMap.put("31115", "A5"); //错
        radicalMap.put("35251214444", "B11"); //穌
        radicalMap.put("35251211", "B8"); //鲍
        radicalMap.put("1211254444", "C10"); //驗
        radicalMap.put("2111254444", "C10"); //驗
        radicalMap.put("551", "C3"); //验
        radicalMap.put("25112511", "D8"); //問
        radicalMap.put("425", "D3"); //问
        radicalMap.put("245", "D3"); //问
        radicalMap.put("344511211", "E9"); //館
        radicalMap.put("341511211", "E9"); //館
        radicalMap.put("34451154", "E8"); //館
        radicalMap.put("34151154", "E8"); //館
        radicalMap.put("355", "E3"); //馆
        radicalMap.put("12521111", "F8"); //電
        radicalMap.put("12524134", "F8"); //電
        radicalMap.put("12524444", "F8"); //電
        radicalMap.put("14521111", "F8"); //電
        radicalMap.put("14524134", "F8"); //電
        radicalMap.put("14524444", "F8"); //電
        radicalMap.put("4111251", "G7"); //說
        radicalMap.put("1111251", "G7"); //說
        radicalMap.put("45", "G2"); //说
        radicalMap.put("314314", "H6"); //"等"
        radicalMap.put("122125112", "I9"); //靶
        Map<String, String> numToLetter = new HashMap<>();
        numToLetter.put("1", "d");
        return numToLetter;
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


        List<CJKChaaar> candiateChars = List.of(
                        "錯", "穌", "鲍", "驗", "問", //"靶",
                        "館", "電", "說", "等", "鬍", "髟", "路", "結", "蝶" ,"體",
                        "軟").stream()
                .map(x -> charToInfoCJKMap.get(x)).collect(Collectors.toList());
        return null;
    }
    public static Map<String, List<CharSmall>> codeToCharSortetByFreq(List<CharSmall> jundaSorted) {
        Map<String, List<CharSmall>> map = jundaSorted.stream()
                .flatMap(a -> a.getCodes().stream().map(c -> new AbstractMap.SimpleEntry<>(c, a)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
        map.values().forEach(l -> l.sort(Comparator.comparingDouble(CharSmall::getFrequency)));
        return map;
    }

    public static Map<String, List<CharSmall>> sortMapByListLength(Map<String, List<CharSmall>> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<String, List<CharSmall>>comparingByValue(Comparator.comparingInt(List::size)).reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    public static Map<Double, String> sortMap(int X, Map<String, List<CharSmall>> map) {
        Map<String, List<CharSmall>> onlyFew = map.entrySet().stream()
                .filter(z -> z.getValue().size() > X)
                .collect(Collectors.toMap(
                        x -> x.getKey(),
                        y -> y.getValue().subList(X, y.getValue().size())));

        Set<Double> alreadyUsed = new HashSet<>();
        Map<Double, String> result = new HashMap<>();
        for (List<CharSmall> eachOne : onlyFew.values()) {
            String toUse = String.join(" ", eachOne.stream().map(x -> x.getCJK()).toList());
            Double newDouble = eachOne.get(0).getFrequency();
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
        return sortedMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .map(x -> x.getCJK() + " " + x.getFrequency())
                                .collect(Collectors.toList()),
                        (a, b) -> a, LinkedHashMap::new));
    }

    public static List<String> getStringsFromIndex(Map<String, List<String>> map, int x) {
        return map.values().stream()
                .filter(l -> l.size() > x)
                .map(l -> l.get(x))
                .collect(Collectors.toList());
    }
}
