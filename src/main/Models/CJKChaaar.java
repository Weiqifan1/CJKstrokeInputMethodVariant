package main.Models;

import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;
import main.dataFileGenerators.stokeMapGenerators.IntevtigationMapService;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CJKChaaar {
    private String CJK;
    private String CJKWithSetMark;
    private String UnicodeHex;
    private String ConwayCode;
    private CJKfrequency junda;
    private CJKfrequency tzai;
    
    private String firstOrderSplit;
    
    public Double getIntersperced() {
        return intersperced;
    }

    private Double intersperced;
    private Set<String> fullCode;

    private Set<String> editedFullCode;

    public Set<String> getEditedFullCode() {
        return editedFullCode;
    }

    private Set<String> sixSixCode;
    private Set<String> firstThreeCode;
    private Set<String> twotwoCode;
    private Set<String> twofourCode;
    private Set<String> fourOneCode;
    private Set<String> fourTwoCode;
    private Set<String> plainThreeThreeCode;
    private Set<String> threethreeCode;

    public Set<String> getPlainThreeThreeCode() {
        return plainThreeThreeCode;
    }

    public CJKChaaar(String CJK,
                     String CJKWithSetMark,
                     String unicodeHex,
                     String conwayCode,
                     CJKfrequency junda,
                     CJKfrequency tzai,
                     String firstOrderSplit) {
        this.CJK = CJK;
        this.CJKWithSetMark = CJKWithSetMark;
        this.UnicodeHex = unicodeHex;
        this.ConwayCode = conwayCode;
        this.junda = junda;
        this.tzai = tzai;
        this.intersperced = generateInterspercedFrequency(junda, tzai, UnicodeHex);
        this.firstOrderSplit = firstOrderSplit;
        generateCodesFromConwayCode(conwayCode, CJK);
    }

    private Double generateInterspercedFrequency(CJKfrequency junda, CJKfrequency tzai, String unicodeHex) {
        // CJKfrequency(String character, int ordinal, Long occurrences, Long totalOccurrences)
        if (Objects.nonNull(junda) && Objects.nonNull(tzai)) {
            double jundaInt = junda.getOrdinal();
            double tzaiInt = tzai.getOrdinal();
            if (jundaInt < tzaiInt) {
                return jundaInt;
            } else if (tzaiInt < jundaInt) {
                return tzaiInt + 0.5;
            } else {
                return jundaInt;
            }
        } else if (Objects.nonNull(junda)) {
            return Double.valueOf(junda.getOrdinal());
        } else if (Objects.nonNull(tzai)) {
            return Double.valueOf(tzai.getOrdinal()) + 0.5;
        } else {
            String hex = unicodeHex.substring(2, unicodeHex.length());
            Integer result = Integer.parseUnsignedInt(hex, 16);
            return Double.parseDouble(String.valueOf(result));
        }
    }

    public String getFirstOrderSplit() {
        return firstOrderSplit;
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
    public Set<String> getSixSix() { return sixSixCode; }

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

    private void generateCodesFromConwayCode(String conwayCode, String cjk) {
        ConwayCodeService cs = new ConwayCodeService();
        if (cjk == "英") {
            String test = "";
        }
        Set<String> fullCode = cs.codesFromConway(conwayCode);
        this.fullCode = fullCode;
        this.editedFullCode = editFullCode(fullCode);
        this.sixSixCode = cs.editFullCodeToSix(fullCode, cjk);
        this.firstThreeCode = cs.fullCodeToFirstThree(editedFullCode);
        this.twotwoCode = cs.fullCodesToTwoTwoCodes(editedFullCode);
        this.twofourCode = cs.fullCodeToTwoFour(editedFullCode);
        this.fourOneCode = cs.fullCodeToFourOne(editedFullCode);
        this.fourTwoCode = cs.fullCodeToFourTwo(editedFullCode);
        this.threethreeCode = cs.fullCodeToThreeThree(editedFullCode);
        this.plainThreeThreeCode = cs.fullCodeToThreeThree(fullCode);
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
/*
        String initial = switch (partToChange) {

            case "121" -> "x"; // 土  -> 121, junda = 1218, tzai = 1408
            case "251" -> "c"; // 日 -> 251, junda = 1130, tzai = 1213
            case "122" -> "v"; // 古 -> 122, junda = 789, tzai = 670
            case "211" -> "b"; // 上 -> 211, junda = 672, tzai = 684
            case "311" -> "n"; // 午 -> 311, junda = 362, tzai =
            case "441" -> "m"; // 斗  -> 441, junda = 360, tzai = 562
            case "341" -> ","; // 金 -> 341, junda = 356, tzai = 466
            case "123" -> "."; // 木 -> 123, junda = 261, tzai = 454
            default -> partToChange;
        };
    */
        String initial = switch (partToChange) {

            case "121" -> "v"; // 土  -> 121, junda = 1218, tzai = 1408
            case "251" -> "m"; // 日 -> 251, junda = 1130, tzai = 1213
            case "122" -> "c"; // 古 -> 122, junda = 789, tzai = 670
            case "211" -> "."; // 上 -> 211, junda = 672, tzai = 684
            //case "311" -> "n"; // 午 -> 311, junda = 362, tzai =
            case "441" -> "."; // 斗  -> 441, junda = 360, tzai = 562
            //case "341" -> ","; // 金 -> 341, junda = 356, tzai = 466
            case "123" -> "x"; // 木 -> 123, junda = 261, tzai = 454
            case "112" -> "b"; // 王-> 112, junda = 225, tzai = 276
            case "413" -> "n"; // 文 -> 413, junda = 273, tzai = 313
            default -> partToChange; // x木 c古 v土 b王 n文 m日 ,上 .斗
        };

        String result = initial + x.substring(3, x.length());
        return result;
    }

    public void setCJK(String CJK) {
        this.CJK = CJK;
    }

    public void setCJKWithSetMark(String CJKWithSetMark) {
        this.CJKWithSetMark = CJKWithSetMark;
    }

    public void setUnicodeHex(String unicodeHex) {
        UnicodeHex = unicodeHex;
    }

    public void setConwayCode(String conwayCode) {
        ConwayCode = conwayCode;
    }

    public void setJunda(CJKfrequency junda) {
        this.junda = junda;
    }

    public void setTzai(CJKfrequency tzai) {
        this.tzai = tzai;
    }

    public void setIntersperced(Double intersperced) {
        this.intersperced = intersperced;
    }

    public void setFullCode(Set<String> fullCode) {
        this.fullCode = fullCode;
    }

    public void setEditedFullCode(Set<String> editedFullCode) {
        this.editedFullCode = editedFullCode;
    }

    public void setFirstThreeCode(Set<String> firstThreeCode) {
        this.firstThreeCode = firstThreeCode;
    }

    public void setTwotwoCode(Set<String> twotwoCode) {
        this.twotwoCode = twotwoCode;
    }

    public void setTwofourCode(Set<String> twofourCode) {
        this.twofourCode = twofourCode;
    }

    public void setFourOneCode(Set<String> fourOneCode) {
        this.fourOneCode = fourOneCode;
    }

    public void setFourTwoCode(Set<String> fourTwoCode) {
        this.fourTwoCode = fourTwoCode;
    }

    public void setPlainThreeThreeCode(Set<String> plainThreeThreeCode) {
        this.plainThreeThreeCode = plainThreeThreeCode;
    }

    public void setThreethreeCode(Set<String> threethreeCode) {
        this.threethreeCode = threethreeCode;
    }
}
