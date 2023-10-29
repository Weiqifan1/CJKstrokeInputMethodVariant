package main.Models;

import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;
import main.Models.sortingEnums.InitialRadicals;

import java.util.List;

public class Parameters {

    private List<Integer> strokeRange;
    private BasicStroke basicStroke;
    private Freq freq;
    private InitialRadicals initialRadicals;

    public Parameters(List<Integer> strokeRange, BasicStroke basicStroke, Freq freq, InitialRadicals initialRadicals) {
        this.strokeRange = strokeRange;
        this.basicStroke = basicStroke;
        this.freq = freq;
        this.initialRadicals = initialRadicals;
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
}
