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

    public void setCode(String code) {
        this.code = code;
    }

    public void setCodeLength(Integer codeLength) {
        this.codeLength = codeLength;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setExceptions(Set<String> exceptions) {
        this.exceptions = exceptions;
    }
}
