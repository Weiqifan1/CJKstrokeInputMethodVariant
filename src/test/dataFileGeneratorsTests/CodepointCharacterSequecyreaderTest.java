package test.dataFileGeneratorsTests;


import main.Models.CJKChaaar;
import main.Models.CJKfrequency;
import main.dataFileGenerators.CodepointCharacterSequenceReader;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodepointCharacterSequecyreaderTest {

    @Test
    void jundaMapTest() {
        CodepointCharacterSequenceReader reader = new CodepointCharacterSequenceReader();
        Map<String, CJKfrequency> jundaMap = reader.jundaMap();

        assertEquals(jundaMap.size(), 9933);
        assertEquals("䁖", jundaMap.get("䁖").getCharacter());
        assertEquals(8946, jundaMap.get("䁖").getOrdinal());
        assertEquals(1l, jundaMap.get("䁖").getOccurrences());
        assertEquals(193504018l, jundaMap.get("䁖").getTotalOccurrences());
    }

    @Test
    void tzaiMapTest() {
        CodepointCharacterSequenceReader reader = new CodepointCharacterSequenceReader();
        Map<String, CJKfrequency> tzaiMap = reader.tzaiMap();

        assertEquals(tzaiMap.size(), 13060);
        assertEquals("股", tzaiMap.get("股").getCharacter());
        assertEquals(1180, tzaiMap.get("股").getOrdinal());
        assertEquals(17617l, tzaiMap.get("股").getOccurrences());
        assertEquals(171894734l, tzaiMap.get("股").getTotalOccurrences());

        assertEquals("的", tzaiMap.get("的").getCharacter());
        assertEquals(1, tzaiMap.get("的").getOrdinal());
        assertEquals(6538132l, tzaiMap.get("的").getOccurrences());
        assertEquals(171894734l, tzaiMap.get("的").getTotalOccurrences());
    }

    @Test
    void codePointCharacterSequencyRawLineTest() {
        Set<String> result = null;
        CodepointCharacterSequenceReader reader = new CodepointCharacterSequenceReader();
        result = reader.codePointCharacterSequencyRawLine();

        assertEquals(28095, result.size());
    }

}
