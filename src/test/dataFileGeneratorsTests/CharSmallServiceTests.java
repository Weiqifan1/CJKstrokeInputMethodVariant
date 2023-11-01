package test.dataFileGeneratorsTests;



import main.Models.CJKChaaar;
import main.Models.CJKfrequency;
import main.Models.CharSmall;
import main.Models.Parameters;
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

        Parameters params = new Parameters(List.of(3,1),
                BasicStroke.DoubleStrokeOnly,
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                SCS.testBasicRadicals());

        List<CharSmall> jundaSorted = SCS.generateChars(params);
        Map<String, List<CharSmall>> sortByFreq = SCS.codeToCharSortetByFreq(jundaSorted);
        Map<String, List<CharSmall>> sortedMap = SCS.sortMapByRarestFreqAtIndex(sortByFreq, 9);

        Map<String, List<String>> stringifyed = SCS.stringifyMap(sortedMap);
        List<String> toListTest = SCS.getStringsFromIndex(stringifyed, 9);

        Map<Double, String> doubleToCjk = SCS.sortMap(9, sortedMap);
        
        assertEquals(51663, 123);
    }


}
