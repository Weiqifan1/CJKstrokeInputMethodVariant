package main.dataFileGenerators.stokeMapGenerators;

import main.Models.CJKChaaar;
import main.Models.CJKfrequency;
import main.dataFileGenerators.CodepointCharacterSequenceReader;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StrokeMapService {
// assertEquals(9931, jundaNumbers.size());
//        assertEquals(13058
    public Map<String, CJKChaaar> charToInfoCJKMap() {
        CodepointCharacterSequenceReader reader = new CodepointCharacterSequenceReader();
        Set<String> rawConwayStrings = reader.codePointCharacterSequencyRawLine();
        Map<String, CJKfrequency> jundaMap = reader.jundaMap();
        Map<String, CJKfrequency> tzaiMap = reader.tzaiMap();

        Set<CJKChaaar> CJKset = rawConwayStrings.stream()
                .map(line -> conwayRawStringToObj(line, jundaMap, tzaiMap)).collect(Collectors.toSet());

        Map<String, CJKChaaar> result = CJKset.stream()
                .collect(Collectors.toMap(
                        a -> a.getCJK(),
                        a -> a
                ));
        return result;
    }

    private CJKChaaar conwayRawStringToObj(String rawConwayLine,
                                           Map<String, CJKfrequency> jundaMap,
                                           Map<String, CJKfrequency> tzaiMap) {
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

        CJKChaaar cjkChar = new CJKChaaar(
                cleanCJKChar,
                CJKcharWithSetMark,
                conwayList.get(0),
                conwayList.get(2),
                jundaLookup,
                tzaiLookup);
        return cjkChar;
    }

}

