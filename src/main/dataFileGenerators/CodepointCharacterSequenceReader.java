package main.dataFileGenerators;

import main.Models.CJKfrequency;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CodepointCharacterSequenceReader {

    public Set<String> codePointCharacterSequencyRawLine() {
        List<String> fileData = readCodepointCharacterSequency();
        List<String> compliantLines = fileData.stream().filter(line -> lineMatchPrescribedRegex(line)).toList();
        List<String> nonCompliant = fileData.stream().filter(line -> !lineMatchPrescribedRegex(line)).toList();
        return compliantLines.stream().collect(Collectors.toSet());
    }

    public Map<String, CJKfrequency> jundaMap() {
        List<String> junda = readJunda();
        Map<String, CJKfrequency> freqmap = new HashMap<>();
        Long allOccurences = 0l;
        for (String rawLine : junda) {
            List<String> splitByTab  = Arrays.stream(rawLine.split("\t")).map(item -> item.trim()).toList();
            if (splitByTab.size() > 3) {
                allOccurences += Long.parseLong(splitByTab.get(2));
                String nums = "";
            }
        }

        for (String rawLine : junda) {
            List<String> splitByTab  = Arrays.stream(rawLine.split("\t")).map(item -> item.trim()).toList();
            if (splitByTab.size() > 3) {
                CJKfrequency itemFreq = new CJKfrequency(
                        splitByTab.get(1),
                        Integer.parseInt(splitByTab.get(0)),
                        Long.parseLong(splitByTab.get(2)),
                        allOccurences);
                freqmap.put(splitByTab.get(1), itemFreq);
                String test = "";
            }
        }

        return freqmap;
    }


    public Map<String, CJKfrequency> tzaiMap() {
        List<String> tzai = readTzai();
        Map<String, CJKfrequency> freqmap = new HashMap<>();
        Long allOccurences = 0l;
        for (String rawLine : tzai) {
            List<String> splitByTab  = Arrays.stream(rawLine.split("[\s]+")).map(item -> item.trim()).toList();
            if (splitByTab.size() > 2) {
                allOccurences += Long.parseLong(splitByTab.get(1));
                String nums = "";
            }
        }

        int oridinalToBeused = 1;
        for (String rawLine : tzai) {
            List<String> splitByTab  = Arrays.stream(rawLine.split("[\s]+")).map(item -> item.trim()).toList();
            if (splitByTab.size() > 2) {
                CJKfrequency itemFreq = new CJKfrequency(
                        splitByTab.get(0),
                        oridinalToBeused,
                        Long.parseLong(splitByTab.get(1)),
                        allOccurences);
                freqmap.put(splitByTab.get(0), itemFreq);
                oridinalToBeused++;
            }
        }
        return freqmap;
    }

    private List<String> readTzai() {
        String filePath = "src/main/staticDataFiles/Tzai2006.txt";
        String errorMessage = "readTzai error:";

        List<String> result = getDataFromRawFiles(filePath, errorMessage);
        return result;
    }

    private static List<String> readJunda() {
        String filePath = "src/main/staticDataFiles/Junda2005.txt";
        String errorMessage = "readJunda error:";

        List<String> result = getDataFromRawFiles(filePath, errorMessage);
        return result;
    }

    private static List<String> readCodepointCharacterSequency() {
        String filePath = "src/main/staticDataFiles/codepoint-character-sequence.txt";
        String errorMessage = "readCodepointCharacterSequency error:";

        List<String> result = getDataFromRawFiles(filePath, errorMessage);
        return result;
    }

    private static List<String> getDataFromRawFiles(String filePath, String errorMessage) {
        List<String> result = new ArrayList<>();
        try {
            String s = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
            String dropInitialChar = s.substring(1);
            result = Arrays.stream(dropInitialChar.split("\\r?\\n")).toList();
        } catch (IOException e) {
            System.out.println(errorMessage + " " + e);
        }
        return result;
    }

    /*
    Each compliant line shall be a tab-separated
    (code point, character, stroke sequence regex) triplet,
    where:
      1. the stroke sequence regex shall match `[1-5|()\\]+`, and
      2. the character may be immediately followed by either
         (a) nothing, to indicate that it is dual, or
         (b) a caret (^), to indicate that it is traditional-only, or
         (c) an asterisk (*), to indicate that it is simplified-only.
    */
    private boolean lineMatchPrescribedRegex(String inputLine) {
        List<String> lineSplit = List.of(inputLine.split("\t"));

        if (lineSplit.size() < 3) {
            return false;
        }

        String codepoint = lineSplit.get(0);
        String charAndSetmarker = lineSplit.get(1);
        String strokes = lineSplit.get(2);

        boolean codepointStartWithUplus = codepoint.startsWith("U+");
        boolean strokesMatch = strokes.matches("[1-5|\\\\()]+");
        if (!(codepointStartWithUplus && strokesMatch)) {
            return false;
        }
        return true;
    }

}
