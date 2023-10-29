package test.dataFileGeneratorsTests;


import main.Models.CJKChaaar;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import main.investigation.TryingOutDifferentIdeas;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TryingOutDifferentIdeasTests {

    @Test
    void generateFrequencyOfAllCharAbove16() {
        TryingOutDifferentIdeas ti = new TryingOutDifferentIdeas();
        ti.generateFrequencyOfAllCharAbove16();
    }

    @Test
    void generateBestCandidatesForRadicals() {
        StrokeMapService strokeMapService = new StrokeMapService();
        Map<String, CJKChaaar> charToInfoCJKMap = strokeMapService.charToInfoCJKMap();

        Map<String, String> radicals = initialRadicals(charToInfoCJKMap); //see public String fullCodeToSixSix(
        
        TryingOutDifferentIdeas ti = new TryingOutDifferentIdeas();
        ti.generateradicalCandidates();
    }


    private Map<String, String> initialRadicals(Map<String, CJKChaaar> charToInfoCJKMap) {

        List<CJKChaaar> tzairadicalList = List.of(
                        "錯", "穌", "鲍", "驗", "問", //"靶",
                        "館", "電", "說", "等", "鬍", "髟", "路", "結", "蝶" ,"體",
                        "軟").stream()
                .map(x -> charToInfoCJKMap.get(x)).collect(Collectors.toList());

        List<CJKChaaar> jundaradicalList = List.of(
                        "勒", "電", "等", "稣", "路", "蝶", "體", "竭", "萌"
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
        radicalMap.put("35251214444", "B11"); //穌
        radicalMap.put("1211254444", "C10"); //驗
        radicalMap.put("2111254444", "C10"); //驗
        radicalMap.put("25112511", "D8"); //問
        radicalMap.put("344511211", "E9"); //館
        radicalMap.put("341511211", "E9"); //館
        radicalMap.put("34451154", "E8"); //館
        radicalMap.put("34151154", "E8"); //館
        radicalMap.put("12521111", "F8"); //電
        radicalMap.put("12524134", "F8"); //電
        radicalMap.put("12524444", "F8"); //電
        radicalMap.put("14521111", "F8"); //電
        radicalMap.put("14524134", "F8"); //電
        radicalMap.put("14524444", "F8"); //電
        radicalMap.put("4111251", "G7"); //說
        radicalMap.put("1111251", "G7"); //說
        radicalMap.put("314314", "H6"); //"等"
        Map<String, String> numToLetter = new HashMap<>();
        numToLetter.put("1", "d");
        return numToLetter;
    }

    @Test
    void generateLetterKeyfrequencyTest() {
        TryingOutDifferentIdeas ti = new TryingOutDifferentIdeas();
        ti.generateLetterKeyFrequency();
    }
}
