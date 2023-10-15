package test.dataFileGeneratorsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.Models.CJKChaaar;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StrokeMapServiceTests {

    private StrokeMapService strokeMapService = null;

    @BeforeAll
    public void init(){
        System.out.println("StrokeMapServiceTests Init()");
        this.strokeMapService = new StrokeMapService();
    }

    @Test
    void CharToInfoCJKMapTest() {
        Map<String, CJKChaaar> mapOfObj = strokeMapService.charToInfoCJKMap();
        assertEquals(mapOfObj.size(), 28095);

        CJKChaaar res1 = mapOfObj.get("贝");
        CJKChaaar compare1 = new CJKChaaar("贝", "贝*", "U+8D1D", "2534");
        assertEquals(compare1.getCJK(), res1.getCJK());
        assertEquals(compare1.getCJKWithSetMark(), res1.getCJKWithSetMark());
        assertEquals(compare1.getConwayCode(), res1.getConwayCode());
        assertEquals(compare1.getUnicodeHex(), res1.getUnicodeHex());

        CJKChaaar res2 = mapOfObj.get("貝");
        CJKChaaar compare2 = new CJKChaaar("貝", "貝^", "U+8C9D", "2511134");
        assertEquals(compare2.getCJK(), res2.getCJK());
        assertEquals(compare2.getCJKWithSetMark(), res2.getCJKWithSetMark());
    }


}
