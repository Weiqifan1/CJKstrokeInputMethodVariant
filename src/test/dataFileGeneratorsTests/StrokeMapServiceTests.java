package test.dataFileGeneratorsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.Models.CJKChaaar;
import main.Models.CJKfrequency;
import main.dataFileGenerators.CodepointCharacterSequenceReader;
import main.dataFileGenerators.stokeMapGenerators.RadicalSplitService;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StrokeMapServiceTests {
    private StrokeMapService strokeMapService = null;
    private Map<String, CJKChaaar> charToInfoCJKMap = null;
    private Map<String, CJKfrequency> jundaMap = null;
    private Map<String, CJKfrequency> tzaiMap = null;

    @BeforeAll
    public void init(){
        System.out.println("StrokeMapServiceTests Init()");
        this.strokeMapService = new StrokeMapService();
        this.charToInfoCJKMap = strokeMapService.charToInfoCJKMap();
        CodepointCharacterSequenceReader reader = new CodepointCharacterSequenceReader();
        this.jundaMap = reader.jundaMap();
        this.tzaiMap = reader.tzaiMap();
    }

    @Test
    void testJundaToCJk() {
        Map<Integer, CJKChaaar> result = strokeMapService.jundaToCJKMap();
        assertEquals(9931, result.size());
        assertEquals("他", result.get(10).getCJK());
    }

    @Test
    void CharToInfoCJKMap_FrequencyOverlapsTest() {
        Set<Integer> jundaNumbers = charToInfoCJKMap.values().stream()
                .map(CJK -> CJK.getJunda())
                .filter(junda -> Objects.nonNull(junda))
                .map(junda -> junda.getOrdinal()).collect(Collectors.toSet());

        Set<Integer> tzaiNumbers = charToInfoCJKMap.values().stream()
                .map(CJK -> CJK.getTzai())
                .filter(tzai -> Objects.nonNull(tzai))
                .map(tzai -> tzai.getOrdinal()).collect(Collectors.toSet());

        Set<Integer> jundaNumbersMissing = jundaMap.values().stream()
                .filter(tzai -> !jundaNumbers.contains(tzai.getOrdinal()))
                .map(junda -> junda.getOrdinal()).collect(Collectors.toSet());
        Set<Integer> tzaiNumbersMissing = tzaiMap.values().stream()
                .filter(tzai -> !tzaiNumbers.contains(tzai.getOrdinal()))
                .map(tzai -> tzai.getOrdinal()).collect(Collectors.toSet());

        assertEquals(9931, jundaNumbers.size());
        assertEquals(13058, tzaiNumbers.size());
        assertEquals(Set.of(8220, 9019), jundaNumbersMissing);
        assertEquals(Set.of(4782, 9574), tzaiNumbersMissing);
    }

    @Test
    void CharToInfoCJKMap_ExampleTest() {
        assertEquals(charToInfoCJKMap.size(), 28095);
        CJKChaaar res1 = charToInfoCJKMap.get("贝");

        CJKfrequency jundaFreq1 = new CJKfrequency(
                "贝", 1133, 24675l, 193504018l);
        CJKChaaar compare1 = new CJKChaaar(
                "贝", "贝*", "U+8D1D", "2534",
                jundaFreq1, null, new HashSet<>());
        assertEquals(compare1.getCJK(), res1.getCJK());
        assertEquals(compare1.getCJKWithSetMark(), res1.getCJKWithSetMark());
        assertEquals(compare1.getConwayCode(), res1.getConwayCode());
        assertEquals(compare1.getUnicodeHex(), res1.getUnicodeHex());
        assertEquals(compare1.getJunda().getCharacter(), res1.getJunda().getCharacter());
        assertEquals(compare1.getJunda().getOrdinal(), res1.getJunda().getOrdinal());
        assertEquals(compare1.getJunda().getOccurrences(), res1.getJunda().getOccurrences());
        assertEquals(compare1.getJunda().getTotalOccurrences(), res1.getJunda().getTotalOccurrences());

        CJKChaaar res2 = charToInfoCJKMap.get("貝");
        CJKfrequency jundaFreq2 = new CJKfrequency(
                "貝", 6260, 43l, 193504018l);
        CJKfrequency tzaiFreq2 = new CJKfrequency(
                "貝", 1228, 15970l, 171894734l);
        CJKChaaar compare2 = new CJKChaaar(
                "貝", "貝^", "U+8C9D", "2511134",
                jundaFreq2, tzaiFreq2 ,new HashSet<>());
        assertEquals(compare2.getCJK(), res2.getCJK());
        assertEquals(compare2.getCJKWithSetMark(), res2.getCJKWithSetMark());
    }


}
