package main.dataFileGenerators.stokeMapGenerators;

import main.Models.CJKChaaar;
import main.Models.CJKfrequency;
import main.dataFileGenerators.CodepointCharacterSequenceReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConwayCodeService {

    public List<String> codesFromConway(String conwayCode) {

        List<String> listofParens = getParens(conwayCode);
        String replaceparens = replaceParensWithCapitalLetters(conwayCode);

        //lav kode der tager alle parenteserne og laver conway strings af dem.
        List<String> conwayList = toConwayStringList(List.of(replaceparens), listofParens);

        return listofParens;
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
        //getLovestNumber with backslash
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

    public static String getCharacters(String str, List<Integer> indexes) {
        StringBuilder sb = new StringBuilder();
        for (int i : indexes) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
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

}
