package main.Models;

public class CJKChaaar {
    private String CJK;
    private String CJKWithSetMark;
    private String UnicodeHex;
    private String ConwayCode;

    public CJKChaaar(String CJK, String CJKWithSetMark, String unicodeHex, String conwayCode) {
        this.CJK = CJK;
        this.CJKWithSetMark = CJKWithSetMark;
        UnicodeHex = unicodeHex;
        ConwayCode = conwayCode;
    }

    public boolean isMultiSet() {
        return CJK == CJKWithSetMark;
    }

    public String getCJK() {
        return CJK;
    }

    public String getCJKWithSetMark() {
        return CJKWithSetMark;
    }

    public String getUnicodeHex() {
        return UnicodeHex;
    }

    public String getConwayCode() {
        return ConwayCode;
    }
}
