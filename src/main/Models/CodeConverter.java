package main.Models;

import java.util.*;
import java.util.stream.Collectors;

import main.Models.sortingEnums.BasicStroke;
public class CodeConverter {
    private String fullCode;
    private Map<String, RadicalRecord> radicals;
    private Parameters params;
    private String substringFromRadical;
    private String substringLeftAfterRadical;
    private String finalCodes;

    public CodeConverter(String fullCode, Map<String, RadicalRecord> radicals, Parameters params) {
        //private Set<String> createCoderange(Set<String> codesWithInitialRadicalsToEncoded, List<Integer> strokeRange)
        String withCodeRange = createCoderange(fullCode, params.getStrokeRange());
        String toEncoded = encodedStrokes(params.getBasicStroke(), withCodeRange);

        this.finalCodes = toEncoded;
        this.fullCode = fullCode;
        this.radicals = radicals;
        this.params = params;
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

    private String createCoderange(String codesWithInitialRadicalsToEncoded, List<Integer> strokeRange) {
        String result = "";
        if (Objects.isNull(strokeRange)) {
            return codesWithInitialRadicalsToEncoded;
        } else {
            String each = codesWithInitialRadicalsToEncoded;
                Integer len = strokeRange.get(0) + strokeRange.get(1);
                if (each.length() < len) {
                    result = each;
                } else if (strokeRange.get(1) == 0) {
                    String newstr = "";
                    try {
                        newstr = each.substring(0, strokeRange.get(0));
                    } catch (Exception e) {
                        String test = "";
                    }
                    result = newstr;
                } else {
                    String newstr = "";
                    try {
                        newstr = each.substring(0, strokeRange.get(0))
                                + each.substring(each.length()-strokeRange.get(1), each.length());
                    } catch (Exception e) {
                        String test = "";
                    }
                    result = newstr;
                }
        }
        return result;
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
