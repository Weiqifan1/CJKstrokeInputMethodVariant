package main.Models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RadicalExamples {


    public Map<String, RadicalRecord> testBasicRadicals() {
        Map<String, RadicalRecord> radicalMap = new HashMap<>();
        //String code, Integer codeLength, String letter, Set<String> exceptions
        //(122|1212|2112)251125(5|21)4 萬
        //plant radical codeStructure:
        //𡖂 (122|1212|1221|2112)1325111(215|2121)515(34|35)354
        //   s        d         f       j      k      l
        //  bamboo   insect   foot    tree   plant   waterRad
        String plantCodeStructure = "(122|";
        radicalMap.put("122", new RadicalRecord("122", 3, "k" , //plant
                Set.of(), plantCodeStructure));
        radicalMap.put("1212", new RadicalRecord("1212", 4, "k" , //plant
                Set.of(), plantCodeStructure));
        radicalMap.put("2112", new RadicalRecord("2112", 4, "k" , //plant
                Set.of(), plantCodeStructure));
        //foot radical
        String footConway = "251(215|2121"; //251215  2512121
        radicalMap.put("251215", new RadicalRecord("251215", 6, "f" , //foot
                Set.of(), footConway));
        radicalMap.put("2512121", new RadicalRecord("2512121", 7, "f" , //foot
                Set.of(), footConway));
        //䈨
        radicalMap.put("314314", new RadicalRecord("314314", 6, "s" ,  //bamboo
                Set.of(), ""));
        //虳
        radicalMap.put("251214", new RadicalRecord("251214", 6, "d" ,  //insect
                Set.of(), ""));
        //楮
        radicalMap.put("1234", new RadicalRecord("1234", 4, "j" ,  //tree
                Set.of(), ""));
        //滢
        radicalMap.put("441", new RadicalRecord("441", 3, "l" , //waterRadical
                Set.of("斗"), ""));
        return radicalMap;
    }
}
