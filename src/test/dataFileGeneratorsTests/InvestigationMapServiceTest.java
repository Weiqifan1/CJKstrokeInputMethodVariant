package test.dataFileGeneratorsTests;


import main.Models.CJKChaaar;
import main.Models.CJKfrequency;
import main.dataFileGenerators.CodepointCharacterSequenceReader;
import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;
import main.dataFileGenerators.stokeMapGenerators.IntevtigationMapService;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvestigationMapServiceTest {

    private IntevtigationMapService IMS;

    @BeforeAll
    public void init(){
        System.out.println("Investigation Map service Init()");
        IMS = new IntevtigationMapService();
    }

    @Test
    void generateMapOfElemsToCreateFileTest() {
        List<String> readyToprint = IMS.generateMapOfElemsToCreateFile("\t");

        assertEquals(105592, readyToprint.size());
        assertEquals("一\t1",readyToprint.get(0));
        assertEquals("卩\t52", readyToprint.get(100));
    }

}
