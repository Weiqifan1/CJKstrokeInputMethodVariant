package test.dataFileGeneratorsTests;



import main.Models.*;
import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;
import main.Models.sortingEnums.InitialRadicals;
import main.dataFileGenerators.stokeMapGenerators.CharSmallService;
import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;
import main.dataFileGenerators.stokeMapGenerators.RadicalSplitService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;
import java.util.stream.Collectors;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class maintest_JundaAndRadicalTest {


    private CharSmallService SCS;
    private RadicalSplitService radService = new RadicalSplitService();

    @BeforeAll
    public void init(){
        System.out.println("CharSmallService Init()");
        SCS = new CharSmallService();
    }

    @Test
    void generateCharSmall_jundaFirst() {
        Map<String, String> letters = ConwayCodeService.doubleLetters_n21();
        RadicalExamples examples = new RadicalExamples();
        Parameters params = new Parameters(List.of(6,2),
                BasicStroke.DoubleStrokeOnly,
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                //plant   foot    bamboo  insect    tree   waterradical hand eye,,
                //say誠   thread縱   Gold錶  dor闍  horse馱    eat餞   car 軔
                examples.testBasicRadicals(List.of(
                                        "", "f", "l", "j","k", "", "s", "d",
                                        "v" , "h", "t", "n", "b", "y" ,"g")
                ),
                true);

        Map<String, List<CharSmall>> sortedMap = SCS.generateSortedByFreq(params, letters);

        List<String> BAMBOOflat = generateLetterFlat("l", sortedMap);
        List<String> TREEflat = generateLetterFlat("k", sortedMap);
        List<String> INSECTflat = generateLetterFlat("j", sortedMap);
        List<String> FOOTflat = generateLetterFlat("f", sortedMap);
        List<String> EYEflat = generateLetterFlat("d", sortedMap);
        List<String> HANDflat = generateLetterFlat("s", sortedMap);

        List<String> GOLDflat = generateLetterFlat("t", sortedMap);
        List<String> EATflat = generateLetterFlat("y", sortedMap);
        List<String> CARflat = generateLetterFlat("g", sortedMap);
        List<String> THREADflat = generateLetterFlat("h", sortedMap);
        List<String> SAYflat = generateLetterFlat("v", sortedMap);
        List<String> HORSEflat = generateLetterFlat("b", sortedMap);
        List<String> DOORflat = generateLetterFlat("n", sortedMap);
        List<String> PLANTflat = generateLetterFlat("A", sortedMap);



        Map<String, List<String>> stringifyed = SCS.stringifyMap(sortedMap);
        List<String> toListTest = SCS.getStringsFromIndex(stringifyed, 9);

        Map<Double, String> doubleToCjk = SCS.sortMap(6, sortedMap);

        List<CharSmall> res = sortedMap.get("xgfw");
        
        assertEquals(51663, 123);
    }

    private List<String> generateLetterFlat(String a, Map<String, List<CharSmall>> sortedMap) {
        /*
        List<CharSmall> testHand = flattenNested(values);
        Set<String> shapeCharacters = radService.generateSetCharacters();
        List<String> OneTwoOneNOThand = testHand.stream()
                .filter(x -> isoneTwoNotHand(x, shapeCharacters))
                .map(x -> cjkAndFreq(x)).toList();
*/
        Set<String> Astrings = sortedMap.keySet().stream().filter(x -> x.startsWith(a)).collect(Collectors.toSet());
        List<List<CharSmall>> AstringCodes = Astrings.stream()
                .map(x -> sortedMap.get(x))
                .collect(Collectors.toList());
        List<String> Aflat = flatten(AstringCodes);
        return Aflat;
    }


    private String cjkAndFreq(CharSmall x) {
        String output = "";
        output = x.getCJK() + "-" +  x.getFrequency() + "-" + x.getSecondaryFreq();
        return output;
    }

    private boolean isoneTwoNotHand(CharSmall x, Set<String> shapeCharacters) {
        boolean isHand = false;
        boolean isOneTwo = false;
        if (Objects.nonNull(x.getConverterCodes())) {
            for (CodeConverter convert : x.getConverterCodes()) {
                if (convert.getFinalCodes().startsWith("A")) {
                    isHand = true;
                }
            }
        }
        if (x.getConwayCode().startsWith("121")) {
            isOneTwo = true;
        }
        return isOneTwo && !isHand;
    }

    private List<List<CharSmall>> getValues(Map<String, List<CharSmall>> sortedMap) {
        List<List<CharSmall>> result = new ArrayList<>();
        for (String eachKey : sortedMap.keySet()) {
            List<CharSmall> foreachRes = sortedMap.get(eachKey);
            result.add(foreachRes);
        }
        return result;
    }

    @Test
    void testWhichKobinationOf() {
        Map<String, String> letters = ConwayCodeService.doubleLetters_x21();
        Double lowest = 0.0;
        List<String> combiStrings = new ArrayList<>();

        ArrayList<ArrayList<String>> allCombis = new ArrayList<>();
        ArrayList<String> copy = new ArrayList<>(List.of("s", "d", "f", "j", "k", "l", "m"));
        allCombis = shuffleList(copy);

        for (List<String> subList : allCombis) {
            Double resDouble = getRadicalsFromLetters(subList);
            if (resDouble >= lowest) {
                combiStrings.add(String.join("", subList));
                lowest = resDouble;
            }
        }


        Map<Double, String> first = createDoubleToCjk(List.of("s", "d", "f", "j", "k", "l", ""), letters);


        assertEquals("a", "b");
    }

    // A helper method that swaps two elements in a list
    public static ArrayList<ArrayList<String>> shuffleList(ArrayList<String> input) {
        // Create a new list of lists to store the shufflings
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        // Add the input list as the first element of the result list
        result.add(input);
        // Use a loop to generate 10 shufflings
        for (int i = 0; i < 10; i++) {
            // Create a copy of the input list
            ArrayList<String> copy = new ArrayList<>(List.copyOf(input));
            // Shuffle the copy randomly
            Collections.shuffle(copy);
            // Add the shuffled copy to the result list
            result.add(copy);
        }
        // Return the result list
        return result;
    }

    private Double getRadicalsFromLetters(List<String> radicalletters) {
        Map<String, String> letters = ConwayCodeService.doubleLetters_n21();
        Map<Double, String> doubleToCjk = createDoubleToCjk(radicalletters, letters);
        SortedMap<Double, String> filteredMap = doubleToCjk.entrySet().stream()
                .filter(entry -> isFourLetterOrLess(entry.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1, // merge function, not needed here
                        TreeMap::new // supplier of a new sorted map
                ));
        Double firstDouble = filteredMap.keySet().stream().sorted().toList().get(0);
        return firstDouble;
    }

    private Map<Double, String> createDoubleToCjk(List<String> radicalletters,
                                                  Map<String, String> letters) {
        RadicalExamples examples = new RadicalExamples();
        Parameters params = new Parameters(List.of(6,2),
                BasicStroke.DoubleStrokeOnly,
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                examples.testBasicRadicals(radicalletters),
                true);

        Map<String, List<CharSmall>> sortedMap = SCS.generateSortedByFreq(params, letters);

        Map<String, List<String>> stringifyed = SCS.stringifyMap(sortedMap);
        List<String> toListTest = SCS.getStringsFromIndex(stringifyed, 9);

        Map<Double, String> doubleToCjk = SCS.sortMap(9, sortedMap);
        return doubleToCjk;
    }

    private boolean isFourLetterOrLess(String value) {
        String firstElem = Arrays.stream(value.split(" ")).collect(Collectors.toList()).get(0);
        if (firstElem.length() <= 4) {
            return true;
        } else {
            return false;
        }
    }

    public static List<String> flatten(List<List<CharSmall>> nestedList) {
        List<String> flatList = new ArrayList<>();
        for (List<CharSmall> list : nestedList) {
            for (CharSmall each : list) {
                flatList.add(each.getCJK()+"--"+each.getFrequency()+"--"+each.getSecondaryFreq());
            }
        }
        return flatList.stream().distinct().collect(Collectors.toList());
    }

    public static List<CharSmall> flattenNested(List<List<CharSmall>> nestedList) {
        List<CharSmall> flatList = new ArrayList<>();
        for (List<CharSmall> list : nestedList) {
            for (CharSmall each : list) {
                flatList.add(each);
            }
        }
        return flatList.stream().distinct().collect(Collectors.toList());
    }
}
