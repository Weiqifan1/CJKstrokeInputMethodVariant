package test.dataFileGeneratorsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
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
    void codePointCharacterSequencyRawLineTest() {

        Set<String> result = null;
        try {
            result = strokeMapService.codePointCharacterSequencyRawLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(28095, result.size());
              
        
    }

}
