package main.Models;

import java.util.List;
import java.util.Set;

public class RadicalRecord {

    //codes, numOfCodes, letter, exception
    //List<String> tree = List.of("1234", "4", "A", ""); // 木
    private String code;
    private Integer codeLength;
    private String letter;
    private Set<String> exceptions;
    private String codeStructure;
    private Set<String> radicalAtPositionOne;

    public RadicalRecord(String code,
                         Integer codeLength,
                         String letter,
                         Set<String> exceptions,
                         String codeStructure,
                         Set<String> radicalAtPositionOne) {
        this.code = code;
        this.codeLength = codeLength;
        this.letter = letter;
        this.exceptions = exceptions;
        this.codeStructure = codeStructure;
        this.radicalAtPositionOne = radicalAtPositionOne;
    }

    public String getCodeStructure() {
        return codeStructure;
    }

    public String getCode() {
        return code;
    }

    public Integer getCodeLength() {
        return codeLength;
    }

    public String getLetter() {
        return letter;
    }

    public Set<String> getExceptions() {
        return exceptions;
    }

    public Set<String> getRadicalAtPositionOne() {
        return radicalAtPositionOne;
    }
}
