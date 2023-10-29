package main.dataFileGenerators.stokeMapGenerators;

import main.Models.CJKChaaar;

import java.util.Map;
import main.Models.CJKChaaar;
import main.Models.CharSmall;
import main.Models.Parameters;
import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;
import main.Models.sortingEnums.InitialRadicals;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    private CharSmall generateChar(CJKChaaar x, Parameters params) {

        Set<String> initialRadicalsFromFull = null; //TODO: create radical functions

        //encoded means that the code might be changed from single stroke to double stroke
        Set<String> codesWithInitialRadicalsToEncoded = null;

        CJKChaaar gold = this.charToInfoCJKMap.get("靶");

        return null;
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

}
