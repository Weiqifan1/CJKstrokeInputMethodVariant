package main.Models;

import java.util.Set;
import java.util.stream.Collectors;

public class CharSmall {
    private final Double secondaryFreq;
    private String CJK;
    private Double frequency;
    private Set<String> codes;
    private Set<CodeConverter> converterCodes;
    private String conwayCode;

    public Set<String> codesForPrint;
    public SplitRadicals firstOrderSplit;

    public CharSmall(String CJK,
                     Double frequency,
                     Double secondaryFreq,
                     Set<CodeConverter> converterCodes,
                     String conwayCode,
                     SplitRadicals firstOrderSplit) {
        this.CJK = CJK;
        this.frequency = frequency;
        this.secondaryFreq = secondaryFreq;
        this.converterCodes = converterCodes;
        this.codes = converterCodes.stream().map(x -> x.getFinalCodes()).collect(Collectors.toSet());
        this.conwayCode = conwayCode;
        this.firstOrderSplit = firstOrderSplit;
    }

    public Double getSecondaryFreq() {
        return secondaryFreq;
    }
    public String getCJK() {
        return CJK;
    }

    public Double getFrequency() {
        return frequency;
    }

    public Set<String> getCodes() {
        return codes;
    }

    public Set<CodeConverter> getConverterCodes() {
        return converterCodes;
    }

    public String getConwayCode() {
        return conwayCode;
    }
}
