package main.Models;

import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import main.Models.sortingEnums.BasicStroke;
import main.dataFileGenerators.stokeMapGenerators.RadicalSplitService;

public class CodeConverter {
    private String fullCode;
    private Map<String, RadicalRecord> radicals;
    private Parameters params;
    private String substringFromRadical;
    private String substringLeftAfterRadical;
    private String finalCodes;

    public CodeConverter(CJKChaaar CJK,
                         String fullCode,
                         Parameters params,
                         Boolean includeDoubleLong,
                         Map<String, String> letters) {
        //private Set<String> createCoderange(Set<String> codesWithInitialRadicalsToEncoded, List<Integer> strokeRange)
        if (fullCode.equals("1212251125214")) {
            String test3 = "";
        }
        List<String> substringTripple = getSubstringTupple(CJK, fullCode, params, includeDoubleLong, letters);
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
                                            Boolean includeDoubleLong,
                                            Map<String, String> letters) {
        //should return a list of 3 strings: substringFromRad, substrinAfterrad, fnal code
        //芖
        if (CJK.getCJK().equals("踈") || CJK.getCJK().equals("𢰸")) {
            String test = ""; //{Double@2241} 4299.0 -> ffhw 摞 埙 貢 埚 損 攔 頡 擻 捵 捰 塤 欯 擃 揋 歖 蛓 坱 捑 壧 堁 抰 埧 鼚 撌 㙅 㙉 㙗 㠬 㨛 㩧 㰻 䠭 垻 墤 抧 撔 擑 踈 𨁈
        }
        List<String> result = new ArrayList<>();
        if (includeDoubleLong) {
            result = longCodes(CJK, fullCode, params, List.of(10, 2), letters);
        }else {
            result = noLongCodes(CJK, fullCode, params, letters);
        }
        return result;
    }

    private List<String> longCodes(CJKChaaar cjk,
                                   String fullCode,
                                   Parameters params,
                                   List<Integer> intsToEncodeWithRange,
                                   Map<String, String> letters) {
        List<String> result = new ArrayList<>();
        if (cjk.getCJK().equals("舛")) { // qyjjjj 舛 䢷 夂 夅 夊  影
            String testLastStrokes = "";
        } else if (cjk.getCJK().equals("芖")) {
            String testRadical = "";
        }

        String withCodeRange = createCoderange(
                fullCode,
                intsToEncodeWithRange);
        String toEncoded = encodedStrokes(params.getBasicStroke(), withCodeRange, letters);


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

    private List<String> noLongCodes(CJKChaaar CJK,
                                     String fullCode,
                                     Parameters params,
                                     Map<String, String> letters) {
        List<String> finalEnd = null;
        //I will write this code without using params
        if (CJK.getCJK().equals("目")) {
            //{Double@2241} 4299.0 -> ffhw 摞 埙 貢 茛 埚 菉 芵 損 攔 頡 擻 捵 捰 塤 欯
            String test = "";
        }
        String radCode = getRadCode(CJK, fullCode, params);
        RadicalRecord radLetter = null;
        if (radCode.length() > 0) {
            radLetter = params.getRadicals().get(radCode);
            if (!CJK.getConwayCode().startsWith(radLetter.getCodeStructure())
                    || radLetter.getExceptions().contains(CJK.getCJK())
                    || !fullfillTertieryRequirement(CJK, radLetter)) {
                radLetter = null;
                radCode = "";
            }else {
                String haveRadical = "";
            }
        }
        List<Integer> codeShort = params.getStrokeRange();
        String fullCodeMinusRad = fullCode;
        if (radCode.length() > 0) {
            Integer firstCode = codeShort.get(0);
            codeShort = List.of(firstCode - 2, codeShort.get(1));
            fullCodeMinusRad = fullCode.substring(radCode.length(), fullCode.length());
        }

        String withCodeRange = createCoderange(
                fullCodeMinusRad,
                codeShort);
        String toEncoded = encodedStrokes(params.getBasicStroke(), withCodeRange, letters);

        if (Objects.nonNull(radLetter)) {
            String finalCode = radLetter.getLetter() + toEncoded;
            String substringAfterRad = "";
            if (fullCode.length() > radLetter.getCode().length()) {
                substringAfterRad = fullCode.substring(radLetter.getCode().length(), fullCode.length());
            }
            finalEnd = List.of(radLetter.getCode(),
                    substringAfterRad,
                    finalCode);
        } else {
            String finalCode = toEncoded;
            finalEnd = List.of("", fullCode, finalCode);
        }

        return finalEnd;
        //fullCode = radicalLetter + withCodeRange;
        //result.add(substringsFromRad);
        //result.add(substringAfterRad);
        //result.add(finalCode);
    }

    private boolean fullfillTertieryRequirement(CJKChaaar cjk, RadicalRecord radLetter) {
        //return true if the radLetter is corect,
        //return false if radletter should be null;
        RadicalSplitService radService = new RadicalSplitService();
        String currentCjkStrings = cjk.getFirstOrderSplit().getStringFromSplitRadical();
        List<String> unicode = getUnicodeCharacters(currentCjkStrings);
        Set<String> shapeChars = radService.generateSetCharacters();
        List<String> unicodeNoShape = unicode.stream().filter(x -> !shapeChars.contains(x)).toList();
        if (unicodeNoShape.size() > 0
                && (
                unicodeStartsWithAny(List.of(unicodeNoShape.get(0)), radLetter))
                || unicodeStartsWithAny(unicodeNoShape, radLetter)) {
            return true;
        }
        //if (!cjk.getConwayCode().startsWith("121")) {
        //    return true;
        //}
        return false;
    }

    private boolean unicodeStartsWithAny(List<String> unicodeNoShape, RadicalRecord radLetter) {
        String joined = String.join("" ,unicodeNoShape);
        Set<String> posOne = radLetter.getRadicalAtPositionOne();
        if (Objects.isNull(posOne) || posOne.size() == 0) {
            return true;
        }
        for (String one : posOne) {
            if (joined.startsWith(one)) {
                return true;
            }
        }
        return false;
    }
    /*
        if (radLetter.getRadicalAtPositionOne().length() > 0) {
            //挋   載 㧳䳲鋬鸷挚势紥贽蛰㐝垫踅裚梊䋢䭁烲銴娎蜇焎悊埑逝㿱哲誓硩乴晢䀸㔼
            // jeg skal bruge en liste af mod-undtagelser til radikalerne
            if (radLetter.getRadicalAtPositionOne().equals("扌")
                    //&& cjk.getCJK().equals("挋")
            ) {
                //get second elemn in first order split
            }
        }
        return false;
    */

    private String getRadCode(CJKChaaar cjk, String fullCode, Parameters params) {
        //return the start of a radical code if fullcode start with a radical, return empty string otherwise
        String radCode = "";
        for (String rad : params.getRadicals().keySet()) {
            if (fullCode.startsWith(rad) && rad.length() > radCode.length()) {
                radCode = rad;
            }
        }
        return radCode;
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

    //long code encoded strokes
    private String encodedStrokes(BasicStroke basicStroke,
                                       String initialRadicalsFromFull, Map<String, String> letters) {
        Set<String> numbers = java.util.stream.IntStream.range(0, 10)
                .mapToObj(String::valueOf).collect(Collectors.toSet());
        String result = generateLettersFromSingleStrokes(initialRadicalsFromFull, "(?<=\\G.{2})", letters);
        if (Objects.isNull(result) || result.equals("null")) {
            result = "";
        }
        return result;
    }

    private String generateLettersFromSingleStrokes(String strToUse, String regex, Map<String, String> stringStringMap) {
        List<String> pairs = Arrays.stream(strToUse.split(regex)).toList();
        List<String> listToUse = new ArrayList<>();
        listToUse = pairs;
        /*
        //saveLast pair and take last letter
        if (pairs.size() > 4) {
            String lastPair = pairs.get(pairs.size() - 1);
            String lastStr = lastPair.substring(lastPair.length() - 1);
            //listToUse = pairs.subList(0, pairs.size() - 1);
            //listToUse.add(lastStr);
            listToUse.addAll(pairs);
            listToUse.set(pairs.size() -1, lastStr);
        } else {
            listToUse = pairs;
        }*/
        String pairsWithLetter = listToUse.stream().map(x -> stringStringMap.get(x)).collect(Collectors.joining());
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

}
