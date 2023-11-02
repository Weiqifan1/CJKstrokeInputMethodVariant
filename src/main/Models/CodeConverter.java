package main.Models;

import java.util.*;
import java.util.stream.Collectors;

import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.InitialRadicals;

public class CodeConverter {
    private String fullCode;
    private Map<String, RadicalRecord> radicals;
    private Parameters params;
    private String substringFromRadical;
    private String substringLeftAfterRadical;
    private String finalCodes;

    public CodeConverter(CJKChaaar CJK, String fullCode, Parameters params, Boolean includeDoubleLong) {
        //private Set<String> createCoderange(Set<String> codesWithInitialRadicalsToEncoded, List<Integer> strokeRange)
        if (fullCode.equals("1212251125214")) {
            String test3 = "";
        }
        List<String> substringTripple = getSubstringTupple(CJK, fullCode, params, includeDoubleLong);
        this.substringFromRadical = substringTripple.get(0);
        this.substringLeftAfterRadical = substringTripple.get(1);
        this.finalCodes = substringTripple.get(2);
        this.fullCode = fullCode;
        this.radicals = radicals;
        this.params = params;
    }

    private List<String> getSubstringTupple(CJKChaaar CJK,
                                            String fullCode,
                                            Parameters params,
                                            Boolean includeDoubleLong) {
        //should return a list of 3 strings: substringFromRad, substrinAfterrad, fnal code
        //芖
        if (CJK.getCJK().equals("芖") || CJK.getCJK().equals("𢰸")) {
            String test = "";
        }
        List<String> result = new ArrayList<>();
        if (includeDoubleLong) {
            result = longCodes(CJK, fullCode, params);
        }else {
            result = noLongCodes(CJK, fullCode, params);
        }
        return result;
    }

    private List<String> longCodes(CJKChaaar cjk, String fullCode, Parameters params) {
        List<String> result = new ArrayList<>();
        if (cjk.getCJK().equals("影")) {
            String testLastStrokes = "";
        } else if (cjk.getCJK().equals("芖")) {
            String testRadical = "";
        }

        String withCodeRange = createCoderange(
                fullCode,
                List.of(10,2));
        String toEncoded = encodedStrokes(params.getBasicStroke(), withCodeRange);


        String finalCode = "";
        if (toEncoded.length() == 6) {
            finalCode = toEncoded;
        } else {
            Integer diff = 6 - toEncoded.length();
            finalCode = toEncoded + multiplyString("j", diff);
        }
        //fullCode = radicalLetter + withCodeRange;
        result.add("");
        result.add(fullCode);
        result.add(finalCode);
        return result;
    }

    public static String multiplyString(String str, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    private List<String> noLongCodes(CJKChaaar CJK, String fullCode, Parameters params) {
        if (CJK.getCJK().equals("影")) {
            String testLastStrokes = "";
        } else if (CJK.getCJK().equals("芖")) {
            String testRadical = "";
        }
        List<String> result = new ArrayList<>();
        String chosenRadical = "";
        String substringsFromRad = "";
        String substringAfterRad = "";
        String finalCode = "";
        String radicalLetter = "";
        int radicalCodeLength = 0;
        if (InitialRadicals.InitialRadicalsOnly.equals(params.getInitialRadicals())) {
            if (Objects.nonNull(params.getRadicals()) && params.getRadicals().keySet().size() > 0) {
                for (String radicalCode : params.getRadicals().keySet()) {
                    if (radicalCode.length() > radicalCodeLength
                            && selectRadical(CJK, fullCode, radicalCode, radicalCodeLength, params.getRadicals())) {
                        chosenRadical = radicalCode;
                        radicalCodeLength = chosenRadical.length();
                        substringAfterRad = fullCode.substring(radicalCodeLength, fullCode.length());
                    }
                }
            }
            if (substringAfterRad == "") {
                substringAfterRad = fullCode;
            }


            if (chosenRadical != "") {
                substringsFromRad = chosenRadical;
                RadicalRecord radicalFound = params.getRadicals().get(chosenRadical);
                radicalLetter = radicalFound.getLetter();
            }
            String toEncoded = encodedStrokes(params.getBasicStroke(), substringAfterRad);
            finalCode = radicalLetter + toEncoded;
            String withCodeRange = createCoderange(
                    finalCode,
                    params.getStrokeRange());

            //fullCode = radicalLetter + withCodeRange;
            result.add(substringsFromRad);
            result.add(substringAfterRad);
            result.add(withCodeRange);
        }
        return result;
    }

    private boolean selectRadical(CJKChaaar CJK,
                                  String fullCode,
                                  String radicalCode,
                                  int radicalCodeLength,
                                  Map<String, RadicalRecord> radicals) {
        //fullCode.startsWith(radicalCode)
        if (fullCode.startsWith(radicalCode)) {
            RadicalRecord relevantRadical = radicals.get(radicalCode);
            String conway = CJK.getConwayCode();
            String radicalCodeStructure = relevantRadical.getCodeStructure();
            if (conway.startsWith(radicalCodeStructure) || radicalCodeStructure.equals("")) {
                return true;
            }
        }
        return false;
    }


    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public Map<String, RadicalRecord> getRadicals() {
        return radicals;
    }

    public void setRadicals(Map<String, RadicalRecord> radicals) {
        this.radicals = radicals;
    }

    public Parameters getParams() {
        return params;
    }

    public void setParams(Parameters params) {
        this.params = params;
    }

    public String getSubstringFromRadical() {
        return substringFromRadical;
    }

    public void setSubstringFromRadical(String substringFromRadical) {
        this.substringFromRadical = substringFromRadical;
    }

    public String getSubstringLeftAfterRadical() {
        return substringLeftAfterRadical;
    }

    public void setSubstringLeftAfterRadical(String substringLeftAfterRadical) {
        this.substringLeftAfterRadical = substringLeftAfterRadical;
    }

    public String getFinalCodes() {
        return finalCodes;
    }

    public void setFinalCodes(String finalCodes) {
        this.finalCodes = finalCodes;
    }

    private String createCoderange(String codes, List<Integer> strokeRange) {
        if (Objects.nonNull(strokeRange) && codes.length() <= strokeRange.get(0) + strokeRange.get(1)) {
            return codes;
        } else if (Objects.nonNull(strokeRange)) {
            String firstPart = codes.substring(0, strokeRange.get(0));
            String lastPart = codes.substring(codes.length()-strokeRange.get(1),codes.length());
            return firstPart + lastPart;
        } else {
            return codes;
        }
    }

    private String encodedStrokes(BasicStroke basicStroke,
                                       String initialRadicalsFromFull) {
        Set<String> numbers = java.util.stream.IntStream.range(0, 10)
                .mapToObj(String::valueOf).collect(Collectors.toSet());
        String result = "";
        String eachCode = initialRadicalsFromFull;
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
                result = resultString;
                resultString = generateLettersFromSingleStrokes(strToUse, "(?<=\\G.{2})", doubleLetters());
                result = resultString;
            }
            result = resultString;
        return result;
    }

    private String generateLettersFromSingleStrokes(String strToUse, String regex, Map<String, String> stringStringMap) {
        List<String> pairs = Arrays.stream(strToUse.split(regex)).toList();
        String pairsWithLetter = pairs.stream().map(x -> stringStringMap.get(x)).collect(Collectors.joining());
        return pairsWithLetter;
    }


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

}
