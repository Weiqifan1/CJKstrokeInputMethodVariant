package test.dataFileGeneratorsTests;



import main.Models.*;
import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;
import main.Models.sortingEnums.InitialRadicals;
import main.dataFileGenerators.CodepointCharacterSequenceReader;
import main.dataFileGenerators.stokeMapGenerators.CharSmallService;
import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;
import main.dataFileGenerators.stokeMapGenerators.IntevtigationMapService;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                examples.testBasicRadicals(),
                true);

        Map<String, List<CharSmall>> sortedMap = SCS.generateSortedByFreq(params);

        Map<String, List<String>> stringifyed = SCS.stringifyMap(sortedMap);
        List<String> toListTest = SCS.getStringsFromIndex(stringifyed, 9);

        Map<Double, String> doubleToCjk = SCS.sortMap(9, sortedMap);
        //   s        d         f       j      k      l
        //  bamboo   insect   foot    tree   plant   waterRad

        assertEquals(51663, 123);
    }


}
