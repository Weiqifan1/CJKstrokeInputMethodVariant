package main.dataFileGenerators.stokeMapGenerators;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConwayCodeService {

    public Set<String> codesFromConway(String conwayCode) {
        List<String> listofParens = getParens(conwayCode);
        String replaceparens = replaceParensWithCapitalLetters(conwayCode);
        List<String> conwayList = toConwayStringList(List.of(replaceparens), listofParens);
        return conwayList.stream().collect(Collectors.toSet());
    }

    private List<String> toConwayStringList(List<String> replaceparens, List<String> listofParens) {
        List<String> result = new ArrayList<>();
        if (listofParens.size() == 0) {
            result = replaceparens;
        }else {
            List<String> firstElems = Arrays.asList(listofParens.get(0).split("\\|"));
            List<String> NEWlistOfparens = listofParens.subList(1, listofParens.size());
            List<String> NEWreplaceParens = new ArrayList<>();
            for (String eachelem : firstElems) {
                for (String eachReplace : replaceparens) {
                    String temp = insertParenInCurrentString(eachReplace, eachelem);
                    NEWreplaceParens.add(temp);
                }
            }
            result = toConwayStringList(NEWreplaceParens, NEWlistOfparens);
        }
        return result;
    }

    private String insertParenInCurrentString(String eachReplace, String eachelem) {
        Integer lowest = findAllBackslashIndexes(eachReplace);
        String resultStr = eachReplace.replace("\\"+lowest, eachelem);
        return resultStr;
    }

    public static Integer findAllBackslashIndexes(String str) {
        List<Integer> indexes = new ArrayList<>();
        int index = str.indexOf("\\");
        while (index >= 0) {
            indexes.add(index);
            index = str.indexOf("\\", index + 1);
        }
        if (!indexes.isEmpty()) {
            List<Integer> increase = indexes.stream().map(eachint -> eachint + 1).collect(Collectors.toList());
            List<Integer> intsFromIntegers = increase.stream()
                    .map(eachIndex -> Integer.parseInt(String.valueOf(str.charAt(eachIndex)))).collect(Collectors.toList());
            Integer res = Collections.min(intsFromIntegers);
            return res;
        } else {
            return null;
        }
    }

    private List<String> getParens(String conwayCode) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(conwayCode);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }

    public static String replaceParensWithCapitalLetters(String input) {
        StringBuilder output = new StringBuilder();
        int capitalLetter = 1;
        boolean insideParens = false;
        for (char c : input.toCharArray()) {
            if (c == '(') {
                insideParens = true;
                output.append("\\"+capitalLetter);
                capitalLetter++;
            } else if (c == ')') {
                insideParens = false;
            } else if (!insideParens) {
                output.append(c);
            }
        }
        return output.toString();
    }

    public Set<String> fullCodesToTwoTwoCodes(Set<String> input) {
        Set<String> result = input.stream()
                .map(fullCode -> toTwoTwoCode(fullCode)).collect(Collectors.toSet());
        return result;
    }

    private String toTwoTwoCode(String fullCode) {
        if (fullCode.length() < 4) {
            return fullCode;
        } else {
            String result =
                    fullCode.substring(0,2) +
                    fullCode.substring(fullCode.length()-2, fullCode.length());
            return result;
        }
    }

    public Set<String> fullCodeToThreeThree(Set<String> input) {
        Set<String> result = input.stream()
                .map(fullCode -> toThreeThreeCode(fullCode)).collect(Collectors.toSet());
        return result;
    }

    private String toThreeThreeCode(String fullCode) {
        if (fullCode.length() < 6) {
            return fullCode;
        } else {
            String result =
                    fullCode.substring(0,3) +
                            fullCode.substring(fullCode.length()-3, fullCode.length());
            return result;
        }
    }

    public Set<String> fullCodeToTwoFour(Set<String> input) {
        Set<String> result = input.stream()
                .map(fullCode -> toTwoFourCode(fullCode)).collect(Collectors.toSet());
        return result;
    }


    public Set<String> fullCodeToFourOne(Set<String> input) {
        Set<String> result = input.stream()
                .map(fullCode -> toFourOneCode(fullCode)).collect(Collectors.toSet());
        return result;
    }

    public Set<String> fullCodeToFourTwo(Set<String> input) {
        Set<String> result = input.stream()
                .map(fullCode -> toFourTwoCode(fullCode)).collect(Collectors.toSet());
        return result;
    }

    public Set<String> fullCodeToFirstThree(Set<String> input) {
        Set<String> result = input.stream()
                .map(fullCode -> toFirstThreeCode(fullCode)).collect(Collectors.toSet());
        return result;
    }

    private String toFirstThreeCode(String fullCode) {
        if (fullCode.length() < 3) {
            return fullCode;
        } else {
            String result =
                    fullCode.substring(0,3);
            return result;
        }
    }

    private String toFourTwoCode(String fullCode) {
        if (fullCode.length() < 6) {
            return fullCode;
        } else {
            String result =
                    fullCode.substring(0,4) +
                            fullCode.substring(fullCode.length()-2, fullCode.length());
            return result;
        }
    }

    private String toFourOneCode(String fullCode) {
        if (fullCode.length() < 5) {
            return fullCode;
        } else {
            String result =
                    fullCode.substring(0,4) +
                            fullCode.substring(fullCode.length()-1, fullCode.length());
            return result;
        }
    }

    private String toTwoFourCode(String fullCode) {
        if (fullCode.length() < 6) {
            return fullCode;
        } else {
            String result =
                    fullCode.substring(0,2) +
                            fullCode.substring(fullCode.length()-4, fullCode.length());
            return result;
        }
    }
}
