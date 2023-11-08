package main.dataFileGenerators.stokeMapGenerators;

import main.Models.CJKChaaar;
import main.Models.CJKfrequency;
import main.dataFileGenerators.CodepointCharacterSequenceReader;

import java.util.*;
import java.util.stream.Collectors;

public class StrokeMapService {

    public Map<Integer, CJKChaaar> tzaiToCJKMap() {
        CodepointCharacterSequenceReader reader = new CodepointCharacterSequenceReader();
        Set<String> rawConwayStrings = reader.codePointCharacterSequencyRawLine();
        RadicalSplitService radicalSplitService = new RadicalSplitService();
        Map<String, CJKfrequency> jundaMap = reader.jundaMap();
        Map<String, CJKfrequency> tzaiMap = reader.tzaiMap();
        Map<String, Set<String>> radicalSplitMap = radicalSplitService.getRadicalSplitMap();

        Set<CJKChaaar> CJKset = rawConwayStrings.stream()
                .map(line -> conwayRawStringToObj(line, jundaMap, tzaiMap, radicalSplitMap))
                .collect(Collectors.toSet());

        Map<Integer, CJKChaaar> result = CJKset.stream()
                .filter(CJK -> !Objects.isNull(CJK.getTzai()))
                .collect(Collectors.toMap(
                        a -> a.getTzai().getOrdinal(),
                        a -> a
                ));
        return result;
    }

    public Map<Integer, CJKChaaar> jundaToCJKMap() {
        CodepointCharacterSequenceReader reader = new CodepointCharacterSequenceReader();
        Set<String> rawConwayStrings = reader.codePointCharacterSequencyRawLine();
        RadicalSplitService radicalSplitService = new RadicalSplitService();
        Map<String, CJKfrequency> jundaMap = reader.jundaMap();
        Map<String, CJKfrequency> tzaiMap = reader.tzaiMap();
        Map<String, Set<String>> radicalSplitMap = radicalSplitService.getRadicalSplitMap();

        Set<CJKChaaar> CJKset = rawConwayStrings.stream()
                .map(line -> conwayRawStringToObj(line, jundaMap, tzaiMap, radicalSplitMap))
                .collect(Collectors.toSet());

        Map<Integer, CJKChaaar> result = CJKset.stream()
                .filter(CJK -> !Objects.isNull(CJK.getJunda()))
                .collect(Collectors.toMap(
                        a -> a.getJunda().getOrdinal(),
                        a -> a
                ));
        return result;
    }


    public Map<String, CJKChaaar> charToInfoCJKMap() {
        CodepointCharacterSequenceReader reader = new CodepointCharacterSequenceReader();
        RadicalSplitService radicalSplitService = new RadicalSplitService();
        Set<String> rawConwayStrings = reader.codePointCharacterSequencyRawLine();
        Map<String, CJKfrequency> jundaMap = reader.jundaMap();
        Map<String, CJKfrequency> tzaiMap = reader.tzaiMap();
        Map<String, Set<String>> radicalSplitMap = radicalSplitService.getRadicalSplitMap();

        Set<CJKChaaar> CJKset = rawConwayStrings.stream()
                .map(line -> conwayRawStringToObj(line, jundaMap, tzaiMap, radicalSplitMap))
                .collect(Collectors.toSet());

        Map<String, CJKChaaar> result = CJKset.stream()
                .collect(Collectors.toMap(
                        a -> a.getCJK(),
                        a -> a
                ));
        return result;
    }

    private CJKChaaar conwayRawStringToObj(String rawConwayLine,
                                           Map<String, CJKfrequency> jundaMap,
                                           Map<String, CJKfrequency> tzaiMap,
                                           Map<String, Set<String>> firstorderSplitMap) {
        List<String> conwayList = List.of(rawConwayLine.split("\t"))
                .stream().map(item -> item.trim()).toList();
        String CJKcharWithSetMark = conwayList.get(1);
        String cleanCJKChar = "";
        String lastChar = "" + CJKcharWithSetMark.charAt(CJKcharWithSetMark.length() - 1);
        if (lastChar.equals("^") || lastChar.equals("*")) {
            cleanCJKChar = CJKcharWithSetMark.substring(0, CJKcharWithSetMark.length() - 1);
        } else {
            cleanCJKChar = CJKcharWithSetMark;
        }
        CJKfrequency jundaLookup = jundaMap.get(cleanCJKChar);
        CJKfrequency tzaiLookup = tzaiMap.get(cleanCJKChar);

        if (cleanCJKChar.equals("一")) {
            String test = "";
        }

        CJKChaaar cjkChar = new CJKChaaar(
                cleanCJKChar,
                CJKcharWithSetMark,
                conwayList.get(0),
                conwayList.get(2),
                jundaLookup,
                tzaiLookup,
                firstorderSplitMap);
        return cjkChar;
    }

    public Map<String, CJKChaaar> charToInfoCJKMapONLYPLANT() {
        CodepointCharacterSequenceReader reader = new CodepointCharacterSequenceReader();
        Set<String> rawConwayStrings = reader.codePointCharacterSequencyRawLine();
        RadicalSplitService radicalSplitService = new RadicalSplitService();
        Map<String, CJKfrequency> jundaMap = reader.jundaMap();
        Map<String, CJKfrequency> tzaiMap = reader.tzaiMap();
        Map<String, Set<String>> radicalSplitMap = radicalSplitService.getRadicalSplitMap();

        Set<CJKChaaar> CJKset = rawConwayStrings.stream()
                .map(line -> conwayRawStringToObj(line, jundaMap, tzaiMap, radicalSplitMap)).collect(Collectors.toSet());

        //onlyPlant
        Set<CJKChaaar> modifiedCJK = CJKset.stream().filter(x -> hasPlantonly(x)).collect(Collectors.toSet());

        Map<String, CJKChaaar> result = modifiedCJK.stream()
                .collect(Collectors.toMap(
                        a -> a.getCJK(),
                        a -> a
                ));
        return result;
    }

    private boolean hasPlantonly(CJKChaaar x) {

        String conway = x.getConwayCode();
        List<String> splittet = Arrays.stream(conway.split("[|)(]")).toList();
        boolean result = splittet.contains("122");
        return result;
    }

}

