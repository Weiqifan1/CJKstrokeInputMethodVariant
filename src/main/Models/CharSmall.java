package main.Models;

import java.util.Set;
import java.util.stream.Collectors;

public class CharSmall {
    private String CJK;
    private Double frequency;
    private Set<String> codes;
    private Set<CodeConverter> converterCodes;
    private String conwayCode;

    public CharSmall(String CJK, Double frequency, Set<CodeConverter> converterCodes, String conwayCode) {
        this.CJK = CJK;
        this.frequency = frequency;
        this.converterCodes = converterCodes;
        this.codes = converterCodes.stream().map(x -> x.getFinalCodes()).collect(Collectors.toSet());
        this.conwayCode = conwayCode;
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
