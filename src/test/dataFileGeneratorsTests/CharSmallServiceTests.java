package test.dataFileGeneratorsTests;



import main.Models.CJKChaaar;
import main.Models.CJKfrequency;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
    void generateMapOfElemsToCreateFileTest() {
        Map<String, CJKChaaar> charmap = SCS.charToInfoCJKMap;

        assertEquals(51663, 123);
    }
}
