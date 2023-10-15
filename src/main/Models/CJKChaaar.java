package main.Models;

public class CJKChaaar {
    private String CJK;
    private String CJKWithSetMark;
    private String UnicodeHex;
    private String ConwayCode;
    private CJKfrequency junda;
    private CJKfrequency tzai;

    public CJKChaaar(String CJK, String CJKWithSetMark, String unicodeHex, String conwayCode, CJKfrequency junda, CJKfrequency tzai) {
        this.CJK = CJK;
        this.CJKWithSetMark = CJKWithSetMark;
        this.UnicodeHex = unicodeHex;
        this.ConwayCode = conwayCode;
        this.junda = junda;
        this.tzai = tzai;
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

    public CJKfrequency getJunda() {
        return junda;
    }

    public CJKfrequency getTzai() {
        return tzai;
    }
}
