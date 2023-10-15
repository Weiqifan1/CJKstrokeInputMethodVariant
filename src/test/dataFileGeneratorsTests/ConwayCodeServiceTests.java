package test.dataFileGeneratorsTests;

import main.Models.CJKChaaar;
import main.Models.CJKfrequency;
import main.dataFileGenerators.CodepointCharacterSequenceReader;
import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConwayCodeServiceTests {

    @Test
    void SimpleCodeTest() {
        //U+4E0D	不	1324
        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.codesFromConway("1324");
        assertEquals(Set.of("1324"), codeList);
    }

}
