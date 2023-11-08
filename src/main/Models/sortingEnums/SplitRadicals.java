package main.Models.sortingEnums;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SplitRadicals {

    private String origString;

    private Set<SplitRadicals> following;

    public Set<String> getStringFromSplitRadical() {
        String temp = origString + " code needed";
        Set<String> result = new HashSet<>();
        result.add(temp);
        return result;
    }

    public SplitRadicals(String origString, Map<String, Set<String>> idsMap) {
        this.origString = origString;
        Set<String> followingStr = idsMap.get(origString);
        if (Objects.isNull(followingStr)) {
            following = new HashSet<>();
        } else {
            Set<String> filtered = followingStr.stream()
                    .filter(x -> !origString.equals(x)).collect(Collectors.toSet());
            if (filtered.isEmpty()) {
                following = new HashSet<>();
            } else {
                Set<SplitRadicals> temp = filtered.stream().map(x -> generateNestedValue(x, idsMap)).collect(Collectors.toSet());
                following = temp.stream().filter(x -> Objects.nonNull(x)).collect(Collectors.toSet());
            }
        }
    }

    private SplitRadicals generateNestedValue(String input, Map<String, Set<String>> idsMap) {
        //input is a lookup value in the ids map. this might be a character, but it might also be a longer string
        Set<String> firstResult = idsMap.get(input);
        //if () {

        //}
        return null;
    }
}
