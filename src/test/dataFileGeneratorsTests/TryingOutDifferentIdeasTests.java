package test.dataFileGeneratorsTests;


import main.investigation.TryingOutDifferentIdeas;
import org.junit.jupiter.api.Test;

public class TryingOutDifferentIdeasTests {

    @Test
    void generateFrequencyOfAllCharAbove16() {
        TryingOutDifferentIdeas ti = new TryingOutDifferentIdeas();
        ti.generateFrequencyOfAllCharAbove16();
    }

    @Test
    void generateBestCandidatesForRadicals() {
        TryingOutDifferentIdeas ti = new TryingOutDifferentIdeas();
        ti.generateradicalCandidates();
    }

    @Test
    void generateLetterKeyfrequencyTest() {
        TryingOutDifferentIdeas ti = new TryingOutDifferentIdeas();
        ti.generateLetterKeyFrequency();
    }
}
