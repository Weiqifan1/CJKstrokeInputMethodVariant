package main.dataFileGenerators.stokeMapGenerators;

import main.Models.CJKChaaar;

import java.util.Map;


import main.Models.CJKChaaar;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class IntevtigationMapService {

    public Map<String, CJKChaaar> charToInfoCJKMap;

    public HashMap<String, List<CJKChaaar>> firstThreeMap;
    public HashMap<String, List<CJKChaaar>> twoTwoHashmap;
    public HashMap<String, List<CJKChaaar>> twofourhashMap;
    public HashMap<String, List<CJKChaaar>> fourOnehashMap;
    public HashMap<String, List<CJKChaaar>> fourTwohashMap;
    public HashMap<String, List<CJKChaaar>> threeThreehashMap;

    public IntevtigationMapService() {
        StrokeMapService strokeMapService = new StrokeMapService();
        charToInfoCJKMap = strokeMapService.charToInfoCJKMap();
        firstThreeMap = groupByFirstThree(charToInfoCJKMap.values().stream().toList());
        twoTwoHashmap = groupByTwoTwo(charToInfoCJKMap.values().stream().toList());
        twofourhashMap = groupByTwoFour(charToInfoCJKMap.values().stream().toList());
        fourOnehashMap = groupByFourOne(charToInfoCJKMap.values().stream().toList());
        fourTwohashMap = groupByFourTwo(charToInfoCJKMap.values().stream().toList());
        threeThreehashMap = groupByThreeeThree(charToInfoCJKMap.values().stream().toList());
    }

    private HashMap<String, List<CJKChaaar>> groupByThreeeThree(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getThreeThreeCode()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

    private HashMap<String, List<CJKChaaar>> groupByFourTwo(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getFourTwoCode()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

    //getFirstThreeCode
    private HashMap<String, List<CJKChaaar>> groupByFirstThree(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getFirstThreeCode()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

    public static HashMap<String, List<CJKChaaar>> groupByTwoTwo(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getTwotwoCode()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

    public static HashMap<String, List<CJKChaaar>> groupByTwoFour(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getTwofourCode()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

    public static HashMap<String, List<CJKChaaar>> groupByFourOne(List<CJKChaaar> list) {
        HashMap<String, List<CJKChaaar>> map = new HashMap<>();
        for (CJKChaaar a : list) {
            for (String b : a.getFourOne()) {
                if (!map.containsKey(b)) {
                    map.put(b, new ArrayList<>());
                }
                map.get(b).add(a);
            }
        }
        return map;
    }

}
