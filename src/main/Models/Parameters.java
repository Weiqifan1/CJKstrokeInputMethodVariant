package main.Models;

import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;
import main.Models.sortingEnums.InitialRadicals;

import java.util.List;
import java.util.Map;

public class Parameters {

    private List<Integer> strokeRange;
    private BasicStroke basicStroke;
    private Freq freq;
    private InitialRadicals initialRadicals;
    private Map<String, RadicalRecord> radicals;
    private Boolean includeLongDoubleStrokes;

    public Parameters(List<Integer> strokeRange,
                      BasicStroke basicStroke,
                      Freq freq,
                      InitialRadicals initialRadicals,
                      Map<String, RadicalRecord> radicals,
                      Boolean includeLongDoubleStrokes) {
        this.strokeRange = strokeRange;
        this.basicStroke = basicStroke;
        this.freq = freq;
        this.initialRadicals = initialRadicals;
        this.radicals = radicals;
        this.includeLongDoubleStrokes = includeLongDoubleStrokes;
    }

    public Boolean getIncludeLongDoubleStrokes() {
        return includeLongDoubleStrokes;
    }

    public List<Integer> getStrokeRange() {
        return strokeRange;
    }

    public BasicStroke getBasicStroke() {
        return basicStroke;
    }

    public Freq getFreq() {
        return freq;
    }

    public InitialRadicals getInitialRadicals() {
        return initialRadicals;
    }

    public Map<String, RadicalRecord> getRadicals() {
        return radicals;
    }
}
