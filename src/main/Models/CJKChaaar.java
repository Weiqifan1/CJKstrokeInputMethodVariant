package main.Models;

import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;

import java.util.Set;
import java.util.stream.Collectors;

public class CJKChaaar {
    private String CJK;
    private String CJKWithSetMark;
    private String UnicodeHex;
    private String ConwayCode;
    private CJKfrequency junda;
    private CJKfrequency tzai;
    private Set<String> fullCode;

    private Set<String> editedFullCode;

    public Set<String> getEditedFullCode() {
        return editedFullCode;
    }

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
        this.editedFullCode = editFullCode(fullCode);
        this.firstThreeCode = cs.fullCodeToFirstThree(editedFullCode);
        this.twotwoCode = cs.fullCodesToTwoTwoCodes(editedFullCode);
        this.twofourCode = cs.fullCodeToTwoFour(editedFullCode);
        this.fourOneCode = cs.fullCodeToFourOne(editedFullCode);
        this.fourTwoCode = cs.fullCodeToFourTwo(editedFullCode);
        this.threethreeCode = cs.fullCodeToThreeThree(editedFullCode);
    }

    private Set<String> editFullCode(Set<String> fullCode) {
        Set<String> result = fullCode.stream().map(x -> editEachFullCodeString(x)).collect(Collectors.toSet());
        return result;
    }

    private String editEachFullCodeString(String x) {
        String partToChange = "";
        if (x.length() < 3) {
            return x;
        } else {
            partToChange = x.substring(0,3);
        }

        String initial = switch (partToChange) {
            case "121" -> "x"; // 土  -> 121, junda = 1218, tzai = 1408
            case "251" -> "c"; // 日 -> 251, junda = 1130, tzai = 1213
            case "122" -> "v"; // 古 -> 122, junda = 789, tzai = 670
            case "211" -> "b"; // 上 -> 211, junda = 672, tzai = 684
            case "311" -> "n"; // 午 -> 311, junda = 362, tzai =
            case "441" -> "m"; // 斗  -> 441, junda = 360, tzai = 562
            //case "341" -> ","; // 金 -> 341, junda = 356, tzai = 466
            //case "123" -> "."; // 木 -> 123, junda = 261, tzai = 454
            default -> partToChange;
        };

        String result = initial + x.substring(3, x.length());
        return result;
    }
}
