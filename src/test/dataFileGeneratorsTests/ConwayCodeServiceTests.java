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

    /* Test cases:
1 U+4FF4	俴	32(1534|1543)\1
6 U+5298	劘	413(1234|1235)\1(2|3)111211122
7 U+535B	卛	(1|4)111251(554234|554444)\212
106 U+7BDB	篛	314314(51533|51541)\1

170 U+98CD	飍	(351251214|353251214)\1\1
171 U+98DD	飝	(534332534|534335342)\1\1
172 U+991E	餞^	34(1|4)(51154|511211)(1534|1543)\3

    */

    @Test
    void SimpleCodeTest() {
        //U+4E0D	不	1324
        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.codesFromConway("1324");
        assertEquals(Set.of("1324"), codeList);
    }

}
