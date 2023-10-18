package main.Models;

import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;

import java.util.Set;

public class CJKChaaar {
    private String CJK;
    private String CJKWithSetMark;
    private String UnicodeHex;
    private String ConwayCode;
    private CJKfrequency junda;
    private CJKfrequency tzai;
    private Set<String> fullCode;
    private Set<String> twotwoCode;
    private Set<String> threethreeCode;
    private Set<String> twofourCode;


    public CJKChaaar(String CJK,
                     String CJKWithSetMark,
                     String unicodeHex,
                     String conwayCode,
                     CJKfrequency junda,
                     CJKfrequency tzai) {
        this.CJK = CJK;
        this.CJKWithSetMark = CJKWithSetMark;
        this.UnicodeHex = unicodeHex;
        this.ConwayCode = conwayCode;
        this.junda = junda;
        this.tzai = tzai;
        generateCodesFromConwayCode(conwayCode);
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

    public Set<String> getFullCode() {
        return fullCode;
    }

    public Set<String> getTwotwoCode() {
        return twotwoCode;
    }

    public Set<String> getThreethreeCode() {
        return threethreeCode;
    }

    public Set<String> getTwofourCode() {
        return twofourCode;
    }

    private void generateCodesFromConwayCode(String conwayCode) {
        ConwayCodeService cs = new ConwayCodeService();
        Set<String> fullCode = cs.codesFromConway(conwayCode);
        this.fullCode = fullCode;
        this.twotwoCode = cs.fullCodesToTwoTwoCodes(fullCode);
        this.threethreeCode = cs.fullCodeToThreeThree(fullCode);
        this.twofourCode = cs.fullCodeToTwoFour(fullCode);
    }
}
