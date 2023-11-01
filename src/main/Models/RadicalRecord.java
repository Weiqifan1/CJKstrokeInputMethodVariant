package main.Models;

import java.util.Set;

public class RadicalRecord {

    //codes, numOfCodes, letter, exception
    //List<String> tree = List.of("1234", "4", "A", ""); // 木
    private String code;
    private Integer codeLength;
    private String letter;
    private Set<String> exceptions;

    public RadicalRecord(String code, Integer codeLength, String letter, Set<String> exceptions) {
        this.code = code;
        this.codeLength = codeLength;
        this.letter = letter;
        this.exceptions = exceptions;
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
}
