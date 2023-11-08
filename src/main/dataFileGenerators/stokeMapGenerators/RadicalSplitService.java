package main.dataFileGenerators.stokeMapGenerators;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class RadicalSplitService {


    public String generateFirstOrderSplit(String cjk) {
        return "";
    }

    public Map<String, Set<String>> getRadicalSplitMap() {
        String filePath = "src/main/staticDataFiles/ids.txt";
        Map<String, Set<String>> result = getIdsMap(filePath);
        return result;
    }

    private static Map<String, Set<String>> getIdsMap(String filePath) {
        Map<String, Set<String>> result = new HashMap<>();
        List<String> lines = new ArrayList<>();
        try {
            lines = readLines(filePath);
        } catch (Exception e) {
            System.out.println("read ids: " + e);
        }
        for (String line : lines) {
            List<String> splitLine = Arrays.stream(line.split("\t")).toList();
            String test = "";
            if (splitLine.size() > 2) {
                Set<String> values = splitLine.subList(2, splitLine.size()).stream().collect(Collectors.toSet());
                String key = splitLine.get(1);
                result.put(key, values);
            }
        }
        return result;
    }

    public static List<String> readLines(String fileName) throws Exception {
        Path path = Path.of(fileName);
        return Files.readAllLines(path);
    }
}
