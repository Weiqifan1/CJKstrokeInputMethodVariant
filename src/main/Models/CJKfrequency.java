package main.Models;

public class CJKfrequency {

    private String Character;
    private int Ordinal;
    private Long Occurrences;
    private Long TotalOccurrences;

    public CJKfrequency(String character, int ordinal, Long occurrences, Long totalOccurrences) {
        Character = character;
        Ordinal = ordinal;
        Occurrences = occurrences;
        TotalOccurrences = totalOccurrences;
    }

    public String getCharacter() {
        return Character;
    }

    public int getOrdinal() {
        return Ordinal;
    }

    public Long getOccurrences() {
        return Occurrences;
    }

    public Long getTotalOccurrences() {
        return TotalOccurrences;
    }
}
