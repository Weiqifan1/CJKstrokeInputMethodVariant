package test.dataFileGeneratorsTests;

import main.Models.CJKChaaar;
import main.Models.CJKfrequency;
import main.dataFileGenerators.CodepointCharacterSequenceReader;
import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;
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
public class ConwayCodeServiceTests {

    //*******************************************************
    //**********   fullCode set to 2-2 codeSet
    //*******************************************************

    @Test
    void twoTwoTest() {
        Set<String> input = List.of(
                "3415115415431543",
                "3415115415341534",
                "34151121115431543",
                "34151121115341534",
                "3445115415431543",
                "3445115415341534",
                "34451121115431543",
                "34451121115341534"
        ).stream().collect(Collectors.toSet());

        Set<String> testSet = List.of(
                "3443",
                "3434"
        ).stream().collect(Collectors.toSet());

        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.fullCodesToTwoTwoCodes(input);
        assertEquals(testSet, codeList);

    }

    @Test
    void threeThreeTest() {
        Set<String> input = List.of(
                "3415115415431543",
                "3415115415341534",
                "34151121115431543",
                "34151121115341534",
                "3445115415431543",
                "3445115415341534",
                "34451121115431543",
                "34451121115341534"
        ).stream().collect(Collectors.toSet());

        Set<String> testSet = List.of(
                "341543",
                "341534",
                "344543",
                "344534"
        ).stream().collect(Collectors.toSet());

        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.fullCodeToThreeThree(input);
        assertEquals(testSet, codeList);
    }

    @Test
    void twoFourTest() {
        Set<String> input = List.of(
                "3415115415431543",
                "3415115415341534",
                "34151121115431543",
                "34151121115341534",
                "3445115415431543",
                "3445115415341534",
                "34451121115431543",
                "34451121115341534"
        ).stream().collect(Collectors.toSet());

        Set<String> testSet = List.of(
                "341543",
                "341534"
        ).stream().collect(Collectors.toSet());

        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.fullCodeToTwoFour(input);
        assertEquals(testSet, codeList);
    }

    //*******************************************************
    //**********    conwayCode to full code set tests  ******
    //******************************************************
    /* Test cases:
U+4E0D	不	1324
U+4FF4	俴	32(1534|1543)\1
U+535B	卛	(1|4)111251(554234|554444)\212
U+98CD	飍	(351251214|353251214)\1\1
U+991E	餞^	34(1|4)(51154|511211)(1534|1543)\3
    */

    @Test
    void dublicateThirdParen() {
        //U+991E	餞^	34(1|4)(51154|511211)(1534|1543)\3
        String input = "34(1|4)(51154|511211)(1534|1543)\\3";
        Set<String> testSet = List.of(
                "3415115415431543",
                "3415115415341534",
                "34151121115431543",
                "34151121115341534",
                "3445115415431543",
                "3445115415341534",
                "34451121115431543",
                "34451121115341534"
                ).stream().collect(Collectors.toSet());
        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.codesFromConway(input);
        assertEquals(testSet, codeList);
    }

    @Test
    void twoDublicatesOfParens() {
        //U+98CD	飍	(351251214|353251214)\1\1
        String input = "(351251214|353251214)\\1\\1";
        Set<String> testSet = List.of(
                "351251214351251214351251214",
                "353251214353251214353251214").stream().collect(Collectors.toSet());
        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.codesFromConway(input);
        assertEquals(testSet, codeList);
    }

    @Test
    void twoParensSecondRepeatedTest() {
        //U+535B	卛	(1|4)111251(554234|554444)\212
        String input = "(1|4)111251(554234|554444)\\212";
        Set<String> testSet = List.of(
                "111125155423455423412",
                "111125155444455444412",
                "411125155423455423412",
                "411125155444455444412"
                ).stream().collect(Collectors.toSet());
        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.codesFromConway(input);
        assertEquals(testSet, codeList);
    }

    @Test
    void twoParenTest() {
        //U+5298	劘	413(1234|1235)\1(2|3)111211122
        String input = "413(1234|1235)\\1(2|3)111211122";
        Set<String> testSet = List.of(
                "413123412342111211122",
                "413123512352111211122",
                "413123412343111211122",
                "413123512353111211122").stream().collect(Collectors.toSet());
        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.codesFromConway(input);
        assertEquals(testSet, codeList);
    }

    @Test
    void dublicateParenTest() {
        //俴	32(1534|1543)\1
        String input = "32(1534|1543)\\1";
        Set<String> testSet = List.of(
                "3215341534",
                "3215431543").stream().collect(Collectors.toSet());
        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.codesFromConway(input);
        assertEquals(testSet, codeList);
    }

    @Test
    void noParenConwayStringTest() {
        //U+4E0D	不	1324
        String input = "1324";
        Set<String> testSet = List.of(
                "1324").stream().collect(Collectors.toSet());
        ConwayCodeService conwayService = new ConwayCodeService();
        Set<String> codeList = conwayService.codesFromConway(input);
        assertEquals(testSet, codeList);
    }


}
