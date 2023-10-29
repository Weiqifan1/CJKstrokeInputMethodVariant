package main.dataFileGenerators.stokeMapGenerators;

import main.Models.CJKChaaar;

import java.util.Map;
import main.Models.CJKChaaar;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CharSmallService {

    public Map<String, CJKChaaar> charToInfoCJKMap;

    public CharSmallService() {
        StrokeMapService strokeMapService = new StrokeMapService();
        charToInfoCJKMap = strokeMapService.charToInfoCJKMap();
    }


}
