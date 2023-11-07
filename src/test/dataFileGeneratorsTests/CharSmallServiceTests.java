package test.dataFileGeneratorsTests;



import main.Models.*;
import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;
import main.Models.sortingEnums.InitialRadicals;
import main.dataFileGenerators.stokeMapGenerators.CharSmallService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;
import java.util.stream.Collectors;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CharSmallServiceTests {


    private CharSmallService SCS;

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
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                //plant   foot    bamboo  insect    tree   waterradical hand
                examples.testBasicRadicals(List.of("", "", "", "","", "", "A")),
                false);

        Map<String, List<CharSmall>> sortedMap = SCS.generateSortedByFreq(params);

        Set<String> Astrings = sortedMap.keySet().stream().filter(x -> x.startsWith("A")).collect(Collectors.toSet());
        List<List<CharSmall>> AstringCodes = Astrings.stream()
                .map(x -> sortedMap.get(x))
                .collect(Collectors.toList());
        List<String> Aflat = flatten(AstringCodes);

        List<CharSmall> testHand = sortedMap.get("A");
        
        Map<String, List<String>> stringifyed = SCS.stringifyMap(sortedMap);
        List<String> toListTest = SCS.getStringsFromIndex(stringifyed, 9);

        Map<Double, String> doubleToCjk = SCS.sortMap(0, sortedMap);

        assertEquals(51663, 123);
    }

    @Test
    void testWhichKobinationOf() {
        Double lowest = 0.0;
        List<String> combiStrings = new ArrayList<>();

        ArrayList<ArrayList<String>> allCombis = new ArrayList<>();
        ArrayList<String> copy = new ArrayList<>(List.of("s", "d", "f", "j", "k", "l"));
        allCombis = shuffleList(copy);

        for (List<String> subList : allCombis) {
            Double resDouble = getRadicalsFromLetters(subList);
            if (resDouble >= lowest) {
                combiStrings.add(String.join("", subList));
                lowest = resDouble;
            }
        }


        Map<Double, String> first = createDoubleToCjk(List.of("s", "d", "f", "j", "k", "l"));


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

}
