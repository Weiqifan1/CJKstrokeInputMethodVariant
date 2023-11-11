package test.dataFileGeneratorsTests;



import main.Models.*;
import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;
import main.Models.sortingEnums.InitialRadicals;
import main.dataFileGenerators.stokeMapGenerators.CharSmallService;
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
        RadicalExamples examples = new RadicalExamples();
        Parameters params = new Parameters(List.of(6,2),
                BasicStroke.DoubleStrokeOnly,
                Freq.TzaiFirst,
                InitialRadicals.InitialRadicalsOnly,
                //plant   foot    bamboo  insect    tree   waterradical hand eye,,
                //say
                examples.testBasicRadicals(List.of(
                        "", "B", "C", "D","A", "", "E", "F",
                        "K" , "")),
                false);

        Map<String, List<CharSmall>> sortedMap = SCS.generateSortedByFreq(params);

        List<String> Aflat = generateLetterFlat("A", sortedMap);
        List<String> Bflat = generateLetterFlat("B", sortedMap);
        List<String> Cflat = generateLetterFlat("C", sortedMap);
        List<String> Dflat = generateLetterFlat("D", sortedMap);
        List<String> Eflat = generateLetterFlat("E", sortedMap);
        List<String> Fflat = generateLetterFlat("F", sortedMap);
        List<String> Gflat = generateLetterFlat("G", sortedMap);
        List<String> Hflat = generateLetterFlat("H", sortedMap);

        List<String> Kflat = generateLetterFlat("K", sortedMap);

        //Map<String, List<String>> stringifyed = SCS.stringifyMap(sortedMap);
        //List<String> toListTest = SCS.getStringsFromIndex(stringifyed, 9);

        Map<Double, String> doubleToCjk = SCS.sortMap(9, sortedMap);

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


        Map<Double, String> first = createDoubleToCjk(List.of("s", "d", "f", "j", "k", "l", ""));


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

    private Double getRadicalsFromLetters(List<String> letters) {
        Map<Double, String> doubleToCjk = createDoubleToCjk(letters);
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

    private Map<Double, String> createDoubleToCjk(List<String> letters) {
        RadicalExamples examples = new RadicalExamples();
        Parameters params = new Parameters(List.of(6,2),
                BasicStroke.DoubleStrokeOnly,
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                examples.testBasicRadicals(letters),
                true);

        Map<String, List<CharSmall>> sortedMap = SCS.generateSortedByFreq(params);

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
                flatList.add(each.getCJK());
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
