package main.Models;

import java.util.*;
import java.util.stream.Collectors;

public class SplitRadicals {

    private String originalInputString;

    private Set<List<SplitRadicals>> splitRadicals;

    private Set<String> shapeCharacters;

    public String getOriginalInputString() {
        return originalInputString;
    }

    public Set<List<SplitRadicals>> getSplitRadicals() {
        return splitRadicals;
    }

    public Set<String> getShapeCharacters() {
        return shapeCharacters;
    }

    private Set<String> generateSetCharacters() {
        Set<String> charSet = new HashSet<>();
        for (int i = 0x2FF0; i <= 0x2FFF; i++) {
            charSet.add(new String(Character.toChars(i)));
        }
        return charSet;
    }

    public Set<String> getStringFromSplitRadical() {
        String temp = originalInputString + " code needed";
        Set<String> result = new HashSet<>();
        result.add(temp);
        return result;
    }

    public SplitRadicals(String originalInputString, Map<String, Set<String>> idsMap) {
        this.originalInputString = originalInputString;
        Set<String> followingStr = idsMap.get(originalInputString);
        if (Objects.isNull(followingStr)) {
            splitRadicals = new HashSet<>();
        } else {
            Set<String> filtered = followingStr.stream()
                    .filter(x -> !originalInputString.equals(x)).collect(Collectors.toSet());
            if (filtered.isEmpty()) {
                splitRadicals = new HashSet<>();
            } else {
                Set<List<SplitRadicals>> temp = filtered.stream()
                        .map(x -> generateNestedValue(originalInputString, x, idsMap))
                        .collect(Collectors.toSet());
                splitRadicals = temp.stream().filter(x -> Objects.nonNull(x)).collect(Collectors.toSet());
            }
        }
        shapeCharacters = generateSetCharacters();
    }

    public static List<String> getUnicodeCharacters(String utf16String) {
        List<String> unicodeChars = new ArrayList<>();
        for (int i = 0; i < utf16String.length(); i++) {
            char c = utf16String.charAt(i);
            if (Character.isHighSurrogate(c) && i + 1 < utf16String.length()) {
                char c2 = utf16String.charAt(i + 1);
                if (Character.isLowSurrogate(c2)) {
                    unicodeChars.add(Character.toString(c) + Character.toString(c2));
                    i++;
                }
            } else {
                unicodeChars.add(Character.toString(c));
            }
        }
        return unicodeChars;
    }

    private List<SplitRadicals> generateNestedValue(String orig, String input, Map<String, Set<String>> idsMap) {
        //input is a lookup value in the ids map. this might be a character, but it might also be a longer string
        if (Objects.isNull(input)) {
            return null;
        }
        List<String> eachUnicode = getUnicodeCharacters(input);
        List<String> roadRadicalLast = moveRoadRadicalToLast(orig, eachUnicode);
        List<SplitRadicals> result = roadRadicalLast.stream().map(x -> new SplitRadicals(x, idsMap)).toList();
        return result;
    }

    private List<String> moveRoadRadicalToLast(String orig, List<String> unicode) {
        //U+2FFA  bottom-left-encircle
        List<String> result = unicode;
        String bottomLeftEncircle = new String(Character.toChars(0x2ffa));
        Set<String> outerElems = Set.of(  ///
          "辶", "廴");
        //䬚鼱魕䴭達䞜延瓲覐尨勉麺㼊尩尅毤
        // 㐤毽㲍亾尫瓟飕䟳㬲屗韑尬
        //"風" ,"鼠", "鬼", "麥", "辶", "走", "廴", "瓦", "見","尤", "屯", "尣", "克", "兊",
        //        "九", "毛", "支", "𠃊", "兀", "瓜", "风", "是", "虎", "足", "更", "尾", "友", "光", "木", "尢", "免", "麦"

        //characters that look like inner-outer but should be treated as left right
        //
        if (result.size() > 2
                && result.get(0).equals(bottomLeftEncircle)
                && outerElems.contains(result.get(1))) {
            String first = result.get(0);
            String second = result.get(1);
            List<String> remains = new ArrayList<>();
            remains.add(first);
            remains.addAll(result.subList(2, result.size()));
            remains.add(second);
            result = remains;
        }
        return result;
    }


}
