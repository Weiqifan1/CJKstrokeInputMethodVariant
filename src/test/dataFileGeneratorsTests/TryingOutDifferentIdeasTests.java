package test.dataFileGeneratorsTests;


import main.Models.CJKChaaar;
import main.Models.CharSmall;
import main.Models.Parameters;
import main.Models.RadicalExamples;
import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;
import main.Models.sortingEnums.InitialRadicals;
import main.dataFileGenerators.stokeMapGenerators.CharSmallService;
import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;
import main.dataFileGenerators.stokeMapGenerators.IntevtigationMapService;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import main.investigation.TryingOutDifferentIdeas;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TryingOutDifferentIdeasTests {

    private IntevtigationMapService IMS;

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

    @Test
    void generateLetterKeyfrequencyFromFinishedMapJUNDA() {
        StrokeMapService strokeMapService = new StrokeMapService();
        Map<String, CJKChaaar> charToInfoCJKMap = strokeMapService.charToInfoCJKMap();
        RadicalExamples radiClass = new RadicalExamples();
        CharSmallService smallService = new CharSmallService();

        Map<String, String> letters = ConwayCodeService.doubleLetters_n21();
        Parameters params = new Parameters(List.of(6, 2),
                BasicStroke.DoubleStrokeOnly,
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                //plant   foot    bamboo  insect    tree   waterradical hand eye,,
                //say誠   thread縱   Gold錶  dor闍  horse馱    eat餞   car 軔
                radiClass.testBasicRadicals(List.of(
                        "", "f", "l", "j", "k", "", "s", "d",
                        "v", "h", "t", "n", "b", "y", "g")
                ),
                true);
                /*
                new Parameters(List.of(3,1),
                BasicStroke.DoubleStrokeOnly,
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                radicals,
                true);*/

        Map<String, List<CharSmall>> sortedMap = smallService.generateSortedByFreq(params, letters);
        HashMap<String, CharSmall> cjkToSmallMap = new HashMap<>();
        List<List<CharSmall>> charsmall = sortedMap.values().stream().toList();
        for (List<CharSmall> small : charsmall) {
            for (CharSmall each : small) {
                cjkToSmallMap.put(each.getCJK(), each);
            }
        }

        Map<String, Long> letterMapJunda = new HashMap<>();
        for (char c = 'a'; c <= 'z'; ++c) {
            String letter = String.valueOf(c);
            letterMapJunda.put(letter, 0l);
        }

        for (String strPlusTab : charToInfoCJKMap.keySet()) {
            CJKChaaar cjk = charToInfoCJKMap.get(strPlusTab);
            if (Objects.nonNull(cjk) && Objects.nonNull(cjk.getJunda())) {
                //create letter set
                Set<String> finalletters = new HashSet<>();
                CharSmall objSmall = cjkToSmallMap.get(strPlusTab);
                Set<String> liOf = new HashSet<>();
                if (Objects.nonNull(objSmall)) {
                    liOf = objSmall.getCodes();
                }
                for (String singleCode : liOf) {
                    List<String> splicode = Arrays.stream(singleCode.split("")).toList();
                    finalletters.addAll(splicode);
                }

                for (String eachLetter : finalletters) {
                    Long numberOfLet = cjk.getJunda().getOccurrences();
                    Long previous = letterMapJunda.get(eachLetter);
                    Long updated = previous + numberOfLet;
                    letterMapJunda.put(eachLetter, updated);
                }
            }
        }

        Map<String, Long> firstStrokeSections = new HashMap<>();
        Long topLeft = 0l;
        topLeft += letterMapJunda.get("q");
        topLeft += letterMapJunda.get("w");
        topLeft += letterMapJunda.get("e");
        topLeft += letterMapJunda.get("r");
        topLeft += letterMapJunda.get("t");
        firstStrokeSections.put("topLeft", topLeft);
        Long topRight = 0l;
        topRight += letterMapJunda.get("p");
        topRight += letterMapJunda.get("o");
        topRight += letterMapJunda.get("i");
        topRight += letterMapJunda.get("u");
        topRight += letterMapJunda.get("y");
        firstStrokeSections.put("topRight", topRight);
        Long hori = 0l;
        hori += letterMapJunda.get("a");
        hori += letterMapJunda.get("s");
        hori += letterMapJunda.get("d");
        hori += letterMapJunda.get("f");
        hori += letterMapJunda.get("g");
        firstStrokeSections.put("hori", hori);
        Long bent = 0l;
        bent += letterMapJunda.get("m");
        bent += letterMapJunda.get("l");
        bent += letterMapJunda.get("k");
        bent += letterMapJunda.get("j");
        bent += letterMapJunda.get("h");
        firstStrokeSections.put("bent", bent);
        Long vert = 0l;
        vert += letterMapJunda.get("n");
        vert += letterMapJunda.get("b");
        vert += letterMapJunda.get("v");
        vert += letterMapJunda.get("c");
        vert += letterMapJunda.get("x");
        firstStrokeSections.put("vert", vert);

        String tes = "";


        Map<String, Long> fingerfreq = new HashMap<>();
        Long fingerLeft = 0l;
        fingerLeft += letterMapJunda.get("q");
        fingerLeft += letterMapJunda.get("a");
        fingerfreq.put("left5", fingerLeft);
        Long fingerRight = 0l;
        fingerRight += letterMapJunda.get("w");
        fingerRight += letterMapJunda.get("s");
        fingerRight += letterMapJunda.get("x");
        fingerfreq.put("left4", fingerRight);
        Long fingerhori = 0l;
        fingerhori += letterMapJunda.get("e");
        fingerhori += letterMapJunda.get("d");
        fingerhori += letterMapJunda.get("c");
        fingerfreq.put("left3", fingerhori);
        Long fingerbent = 0l;
        fingerbent += letterMapJunda.get("r");
        fingerbent += letterMapJunda.get("f");
        fingerbent += letterMapJunda.get("v");
        fingerbent += letterMapJunda.get("t");
        fingerbent += letterMapJunda.get("g");
        fingerbent += letterMapJunda.get("b");
        fingerfreq.put("left2", fingerbent);

        Long right5 = 0l;
        right5 += letterMapJunda.get("p");
        fingerfreq.put("right5", right5);
        Long right4 = 0l;
        right4 += letterMapJunda.get("o");
        right4 += letterMapJunda.get("l");
        fingerfreq.put("right4", right4);
        Long right3 = 0l;
        right3 += letterMapJunda.get("i");
        right3 += letterMapJunda.get("k");
        fingerfreq.put("right3", right3);
        Long right2 = 0l;
        right2 += letterMapJunda.get("u");
        right2 += letterMapJunda.get("j");
        right2 += letterMapJunda.get("m");
        right2 += letterMapJunda.get("y");
        right2 += letterMapJunda.get("h");
        right2 += letterMapJunda.get("n");
        fingerfreq.put("right2", right2);


        String tesgfh = "";
    }

}
