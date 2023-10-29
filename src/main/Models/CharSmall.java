package main.Models;

import java.util.Set;

public class CharSmall {
    private String CJK;
    private Double frequency;
    private Set<String> codes;
    private String conwayCode;

    public CharSmall(String CJK, Double frequency, Set<String> codes, String conwayCode) {
        this.CJK = CJK;
        this.frequency = frequency;
        this.codes = codes;
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

    public String getConwayCode() {
        return conwayCode;
    }
}
