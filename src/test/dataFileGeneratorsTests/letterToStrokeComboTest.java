package test.dataFileGeneratorsTests;

import main.Models.CJKChaaar;
import main.Models.CharSmall;
import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;
import main.dataFileGenerators.stokeMapGenerators.IntevtigationMapService;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class letterToStrokeComboTest {
    private IntevtigationMapService IMS;
    private Map<String, CJKChaaar> CJKmap;
    private Set<String> lefthand;
    private Set<String> righthand;

    @BeforeAll
    public void init(){
        System.out.println("Investigation Map service Init()");
        IMS = new IntevtigationMapService();
        StrokeMapService strokeMapService = new StrokeMapService();
        CJKmap = strokeMapService.charToInfoCJKMap();
        lefthand = getLeftHand();
        righthand = getRightHand();
    }

    @Test
    void countHandShifts_x21() {
        Map<String, String> letters_n21 = ConwayCodeService.doubleLetters_n21();
        Map<String, List<CharSmall>> codesN21 =
                IMS.generateMapOfElemsToCreateFile("\t", letters_n21);
        Long junda_n21 = changesFromLettersJUNDA(codesN21);
        Long tzai_n21 = changesFromLettersTZAI(codesN21);

        Map<String, String> letters_x21 = ConwayCodeService.doubleLetters_x21();
        Map<String, List<CharSmall>> codesX21 =
                IMS.generateMapOfElemsToCreateFile("\t", letters_x21);
        Long junda_x21 = changesFromLettersJUNDA(codesX21);
        Long tzai_x21 = changesFromLettersTZAI(codesX21);

        assertEquals(22942997398188L, junda_n21);
        assertEquals( 27930659643458L, tzai_n21);
        assertEquals( 23594331922776L, junda_x21);
        assertEquals(28581109316914L, tzai_x21);
    }

    private Long changesFromLettersTZAI(Map<String, List<CharSmall>> codes) {
        Map<String, Long> occurTzai = getOCCcountTzai(codes);
        Long changes = calculateHandChanges(occurTzai, codes);
        return changes;
    }

    private Long changesFromLettersJUNDA(Map<String, List<CharSmall>> codes) {
        Map<String, Long> occurJunda = getOCCcountJunda(codes);
        Long changes = calculateHandChanges(occurJunda, codes);
        return changes;
    }

    private Long calculateHandChanges(Map<String, Long> codeOccurrences, Map<String, List<CharSmall>> temp) {
        Long result = 0L;
        for (String code : codeOccurrences.keySet()) {
            //List<CharSmall> chs = temp.get(code);
            Long handshifts = calculateHandShifts(code);
            Long totalhandShiftsforcode = handshifts * codeOccurrences.get(code);
            result += totalhandShiftsforcode;
        }
        return result;
    }

    private Long calculateHandShifts(String code) {
        Long shiftCount = 1L;
        List<String> letters = Arrays.stream(code.split("")).toList();
        String previous = "";
        for (int i = 0; i < letters.size(); i++) {
            String let = letters.get(i);
            boolean shift = isShift(previous, let);
            if (shift) {
                shiftCount += 1;
            }
            previous = let;
            if (i == letters.size()-1) {
                //writing almost always ends with the left hand
                //pressing one of the low numbers
                shift = isShift(let, "q");
                if (shift) {
                    shiftCount += 1;
                }
            }
        }
        return shiftCount;
    }

    private boolean isShift(String privious, String current) {
        if (righthand.contains(privious) && lefthand.contains(current)) {
            return true;
        } else if (lefthand.contains(privious) && righthand.contains(current)) {
            return true;
        } else {
            return false;
        }
    }

    private Map<String, Long> getOCCcountJunda(Map<String, List<CharSmall>> temp) {
        Map<String, Long> result = new HashMap<>();
        Set<String> codeSet = temp.keySet();
        for (String code : codeSet) {
            Long totalCount = 0L;
            List<CharSmall> charList = temp.get(code);
            for (CharSmall ch : charList) {
                CJKChaaar fullCh = CJKmap.get(ch.getCJK());
                if (Objects.nonNull(fullCh.getJunda())) {
                    totalCount += fullCh.getJunda().getTotalOccurrences();
                }
            }
            result.put(code, totalCount);
        }
        return result;
    }

    private Map<String, Long> getOCCcountTzai(Map<String, List<CharSmall>> temp) {
        Map<String, Long> result = new HashMap<>();
        Set<String> codeSet = temp.keySet();
        for (String code : codeSet) {
            Long totalCount = 0L;
            List<CharSmall> charList = temp.get(code);
            for (CharSmall ch : charList) {
                CJKChaaar fullCh = CJKmap.get(ch.getCJK());
                if (Objects.nonNull(fullCh.getTzai())) {
                    totalCount += fullCh.getTzai().getTotalOccurrences();
                }
            }
            result.put(code, totalCount);
        }
        return result;
    }

    private Set<String> getLeftHand() {
        String leftHand = "qwertasdfgzxcvb";
        Set<String> result = Arrays.stream(leftHand.split("")).collect(Collectors.toSet());
        return result;
    }

    private Set<String> getRightHand() {
        String rightHand = "yuiophjklmn";
        Set<String> result = Arrays.stream(rightHand.split("")).collect(Collectors.toSet());
        return result;
    }

}

