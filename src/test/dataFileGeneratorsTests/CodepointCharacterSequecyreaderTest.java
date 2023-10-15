package test.dataFileGeneratorsTests;


import main.Models.CJKChaaar;
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
        Map<String, Integer> jundaMap = reader.jundaMap();

        assertEquals(jundaMap, null);
    }

    @Test
    void codePointCharacterSequencyRawLineTest() {
        Set<String> result = null;
        CodepointCharacterSequenceReader reader = new CodepointCharacterSequenceReader();
        result = reader.codePointCharacterSequencyRawLine();

        assertEquals(28095, result.size());
    }

}
