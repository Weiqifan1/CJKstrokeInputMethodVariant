package test.dataFileGeneratorsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StrokeMapServiceTests {

    private StrokeMapService strokeMapService = null;

    @BeforeAll
    public void init(){
        System.out.println("StrokeMapServiceTests Init()");
        this.strokeMapService = new StrokeMapService();
    }


    @Test
    void codePointCharacterSequencyRawLineTest() {
        assertEquals(2, 1+1);
        assertEquals(this.strokeMapService.test, "123");
    }

}
