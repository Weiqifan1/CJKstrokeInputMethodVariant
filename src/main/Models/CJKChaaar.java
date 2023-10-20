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
    private Set<String> firstThreeCode;
    private Set<String> twotwoCode;
    private Set<String> twofourCode;
    private Set<String> fourOneCode;
    private Set<String> fourTwoCode;
    private Set<String> threethreeCode;


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

    public Set<String> getFirstThreeCode() { return firstThreeCode; }

    public Set<String> getTwotwoCode() {
        return twotwoCode;
    }

    public Set<String> getTwofourCode() {
        return twofourCode;
    }

    public Set<String> getFourTwoCode() { return fourTwoCode; }

    public Set<String> getFourOne() { return this.fourOneCode; }

    public Set<String> getThreeThreeCode() { return this.threethreeCode; }

    private void generateCodesFromConwayCode(String conwayCode) {
        ConwayCodeService cs = new ConwayCodeService();
        Set<String> fullCode = cs.codesFromConway(conwayCode);
        this.fullCode = fullCode;
        this.firstThreeCode = cs.fullCodeToFirstThree(fullCode);
        this.twotwoCode = cs.fullCodesToTwoTwoCodes(fullCode);
        this.twofourCode = cs.fullCodeToTwoFour(fullCode);
        this.fourOneCode = cs.fullCodeToFourOne(fullCode);
        this.fourTwoCode = cs.fullCodeToFourTwo(fullCode);
        this.threethreeCode = cs.fullCodeToThreeThree(fullCode);
    }
}
