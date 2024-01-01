package main.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RadicalExamples {


    public Map<String, RadicalRecord> testBasicRadicals(List<String> letters) {
        Map<String, RadicalRecord> radicalMap = new HashMap<>();
        //plant-0  foot-1    bamboo-2  insect-3    tree-4   waterradical-5   hand-6
        //String code, Integer codeLength, String letter, Set<String> exceptions
        //(122|1212|2112)251125(5|21)4 萬
        //plant radical codeStructure:
        //𡖂 (122|1212|1221|2112)1325111(215|2121)515(34|35)354

        //plant radical
        if (letters.size() > 0 && letters.get(0) != "") {
            Set plant = Set.of("艹");
            String plantCodeStructure = "(122|";
            radicalMap.put("122", new RadicalRecord("122", 3, letters.get(0) , //plant
                    Set.of(), plantCodeStructure, plant));
            radicalMap.put("1212", new RadicalRecord("1212", 4, letters.get(0) , //plant
                    Set.of(), plantCodeStructure, plant));
            radicalMap.put("2112", new RadicalRecord("2112", 4, letters.get(0) , //plant
                    Set.of(), plantCodeStructure, plant));
        }

        //foot radical
        if (letters.size() > 1 && letters.get(1) != "") {
            Set foot = Set.of("足","𧾷");
            String footConway = "251(215|2121"; //251215  2512121
            radicalMap.put("251215", new RadicalRecord("251215", 6, letters.get(1) , //foot
                    Set.of(), footConway, foot));
            radicalMap.put("2512121", new RadicalRecord("2512121", 7, letters.get(1) , //foot
                    Set.of(), footConway, foot));
        }
        //  Set.of("口止")

        //䈨
        if (letters.size() > 2 && letters.get(2) != "") {
            Set bamboo = Set.of("竹","⺮","ケ");
            radicalMap.put("314314", new RadicalRecord("314314", 6, letters.get(2) ,  //bamboo
                    Set.of(), "314314", bamboo));
        }
        /*Set.of(
                    "𠂉丨丿乛亅", "𠂊亅[GK]𠂉亅[T]" , "𠂉亅[T]丿乛亅",
                    "𠂊亅[GK]丿一亅", "𠂉丨丿一亅", "𠂉亅[T]𠂊亅", "丿一亅丿乛亅",
                    "𠂉亅[T]𠂉亅", "𠂉亅[T]𠂉丨", "𠂉丨𠂉亅", "𠂊亅[GK]𠂉丨")*/

        //虳
        if (letters.size() > 3 && letters.get(3) != "") {
            Set insect = Set.of("虫");
            radicalMap.put("251214", new RadicalRecord("251214", 6, letters.get(3) ,  //insect
                    Set.of(), "251214", insect));
        }

        //楮
        if (letters.size() > 4 && letters.get(4) != "") {
            Set tree = Set.of("木","朩");
            radicalMap.put("1234", new RadicalRecord("1234", 4, letters.get(4) ,  //tree
                    Set.of(), "1234", tree));
        } //Set.of("木")

        //滢
        if (letters.size() > 5 && letters.get(5) != "") {
            Set water = Set.of("水","氵","氺");
            radicalMap.put("441", new RadicalRecord("441", 3, letters.get(5) , //waterRadical
                    Set.of("斗"), "", water));
        }

        // 摞
        if (letters.size() > 6 && letters.get(6) != "") {
            Set hand = Set.of("手","扌");
            radicalMap.put("121", new RadicalRecord("121", 3, letters.get(6) , //hand
                    Set.of(), "121", hand));
        }

        // 瞋
        if (letters.size() > 7 && letters.get(7) != "") {
            Set eye = Set.of("目");
            radicalMap.put("25111", new RadicalRecord("25111", 5, letters.get(7) , //eye
                    Set.of(), "25111", eye));
        }

        //誠
        if (letters.size() > 8 && letters.get(8) != "") {
            Set say = Set.of("言","訁");
            radicalMap.put("1111251", new RadicalRecord("1111251", 7, letters.get(8) , //say
                    Set.of(), "(1|4)111251", say));
            radicalMap.put("4111251", new RadicalRecord("4111251", 7, letters.get(8) , //say
                    Set.of(), "(1|4)111251", say));
        }

        //縱
        if (letters.size() > 9 && letters.get(9) != "") {
            Set thread = Set.of("糸");
            radicalMap.put("554444", new RadicalRecord("554444", 6, letters.get(9) , //thread
                    Set.of(), "(554234|554444)", thread));
            radicalMap.put("554234", new RadicalRecord("554234", 6, letters.get(9) , //thread
                    Set.of(), "(554234|554444)", thread));
        }

        //錶
        if (letters.size() > 10 && letters.get(10) != "") {
            Set gold = Set.of("金");
            radicalMap.put("34112431", new RadicalRecord("34112431", 8, letters.get(10) , //gold
                    Set.of(), "34112431", gold));
        }

        //闍            //2511251112132511(|4)
        if (letters.size() > 11 && letters.get(11) != "") {
            Set door = Set.of("𠁣","𠃛","門");
            radicalMap.put("25112511", new RadicalRecord("25112511", 8, letters.get(11) , //dor
                    Set.of(), "25112511", door));
        }

        //馱
        if (letters.size() > 12 && letters.get(12) != "") {
            Set horse = Set.of("馬");
            radicalMap.put("1211254444", new RadicalRecord("1211254444", 10, letters.get(12) , //horse
                    Set.of(), "(12|21)11254444", horse));
            radicalMap.put("2111254444", new RadicalRecord("2111254444", 10, letters.get(12) , //horse
                    Set.of(), "(12|21)11254444", horse));
        }

        //餞
        if (letters.size() > 13 && letters.get(13) != "") {
            Set eat = Set.of("食","飠");
            radicalMap.put("344511211", new RadicalRecord("", 9, letters.get(13) , //eat
                    Set.of(), "34(1|4)(51154|511211)", eat));
            radicalMap.put("34451154", new RadicalRecord("", 8, letters.get(13) , //eat
                    Set.of(), "34(1|4)(51154|511211)", eat));
            radicalMap.put("34151154", new RadicalRecord("", 8, letters.get(13) , //eat
                    Set.of(), "34(1|4)(51154|511211)", eat));
            radicalMap.put("341511211", new RadicalRecord("", 9, letters.get(13) , //eat
                    Set.of(), "34(1|4)(51154|511211)", eat));
        }

        //軔
        if (letters.size() > 14 && letters.get(14) != "") {
            Set car = Set.of("車");
            radicalMap.put("1251112", new RadicalRecord("1251112", 7, letters.get(14) , //car
                    Set.of(), "1251112", car));
        }

        return radicalMap;
    }
}
