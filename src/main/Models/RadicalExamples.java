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

        String plantCodeStructure = "(122|";

        if (letters.get(0) != "") {
            radicalMap.put("122", new RadicalRecord("122", 3, letters.get(0) , //plant
                    Set.of(), plantCodeStructure, ""));
            radicalMap.put("1212", new RadicalRecord("1212", 4, letters.get(0) , //plant
                    Set.of(), plantCodeStructure, ""));
            radicalMap.put("2112", new RadicalRecord("2112", 4, letters.get(0) , //plant
                    Set.of(), plantCodeStructure, ""));
        }

        //foot radical
        if (letters.get(1) != "") {
            String footConway = "251(215|2121"; //251215  2512121
            radicalMap.put("251215", new RadicalRecord("251215", 6, letters.get(1) , //foot
                    Set.of(), footConway, ""));
            radicalMap.put("2512121", new RadicalRecord("2512121", 7, letters.get(1) , //foot
                    Set.of(), footConway, ""));
        }

        //䈨

        if (letters.get(2) != "") {
            radicalMap.put("314314", new RadicalRecord("314314", 6, letters.get(2) ,  //bamboo
                    Set.of(), "", ""));
        }

        //虳

        if (letters.get(3) != "") {
            radicalMap.put("251214", new RadicalRecord("251214", 6, letters.get(3) ,  //insect
                    Set.of(), "", ""));
        }

        //楮

        if (letters.get(4) != "") {
            radicalMap.put("1234", new RadicalRecord("1234", 4, letters.get(4) ,  //tree
                    Set.of(), "", ""));
        }

        //滢

        if (letters.get(5) != "") {
            radicalMap.put("441", new RadicalRecord("441", 3, letters.get(5) , //waterRadical
                    Set.of("斗"), "", ""));
        }

        // 摞
        if (letters.get(6) != "") {
            radicalMap.put("121", new RadicalRecord("121", 3, letters.get(6) , //hand
                    Set.of(), "121", "扌"));
        }

        return radicalMap;
    }
}
