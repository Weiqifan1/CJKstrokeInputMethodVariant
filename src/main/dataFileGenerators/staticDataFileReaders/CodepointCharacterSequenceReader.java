package main.dataFileGenerators.staticDataFileReaders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodepointCharacterSequenceReader {
    public static List<String> readCodepointCharacterSequency() {
        String filePath = "src/main/staticDataFiles/codepoint-character-sequence.txt";

        List<String> result = new ArrayList<>();
        try {
            String s = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
            result = Arrays.stream(s.split("\\r?\\n")).toList();
        } catch (IOException e) {
            System.out.println("readCodepointCharacterSequency error: " + e);
        }
        return result;
    }

}
