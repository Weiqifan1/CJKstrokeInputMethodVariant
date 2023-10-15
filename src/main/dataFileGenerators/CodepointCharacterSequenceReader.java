package main.dataFileGenerators;

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

    public Map<String, Integer> jundaMap() {
        List<String> junda = readJunda();
        return null;
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
            result = Arrays.stream(s.split("\\r?\\n")).toList();
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
