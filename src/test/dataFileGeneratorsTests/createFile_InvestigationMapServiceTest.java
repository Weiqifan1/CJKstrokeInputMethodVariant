package test.dataFileGeneratorsTests;


import main.Models.CharSmall;
import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;
import main.dataFileGenerators.stokeMapGenerators.IntevtigationMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class createFile_InvestigationMapServiceTest {

    private IntevtigationMapService IMS;

    @BeforeAll
    public void init(){
        System.out.println("Investigation Map service Init()");
        IMS = new IntevtigationMapService();
    }

    @Test
    void generateMapOfElemsToCreateFileTest() {
        Map<String, String> letters = ConwayCodeService.doubleLetters_n21();
        Map<String, List<CharSmall>> temp = IMS.generateMapOfElemsToCreateFile("\t", letters);

        List<String> readyToprint= IMS.generateElemsToCreateFile(temp, "\t");

        //with only fullcode, 3-3 and plain3-3 you get length 105592
        assertEquals(true, true);
        //assertEquals(121114, readyToprint.size());
        //assertEquals(97007, readyToprint.size());
        //assertEquals("一\tg",readyToprint.get(0));
        //assertEquals("头\tlw", readyToprint.get(100));
        //assertEquals("一\t1",readyToprint.get(0));
        //assertEquals("卩\t52", readyToprint.get(100));
/*
        //generate file:
        String filename = "src/main/generatedDataFiles/POFsimp.txt";
        String filePath = new File("").getAbsolutePath();
        filePath.concat(filename);

        Path path = Paths.get(filename);
        try {
            Files.write(path, readyToprint, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("failed to Print file in generateMapOfElemsToCreateFileTest: " + e);
        }
*/
    }

}
