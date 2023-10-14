package main.dataFileGenerators.stokeMapGenerators;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static main.dataFileGenerators.staticDataFileReaders.CodepointCharacterSequenceReader.readCodepointCharacterSequency;

public class StrokeMapService {

    public Set<String> codePointCharacterSequencyRawLine() throws IOException {
        List<String> fileData = readCodepointCharacterSequency();
        List<String> compliantLines = fileData.stream().filter(line -> lineMatchPrescribedRegex(line)).toList();
        List<String> nonCompliant = fileData.stream().filter(line -> !lineMatchPrescribedRegex(line)).toList();
        return compliantLines.stream().collect(Collectors.toSet());
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

