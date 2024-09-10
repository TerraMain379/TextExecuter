package me.terramain.textexecuter.charsarraybuilder;

import java.util.List;

public class MaskedText {
    public List<Character> maskedChars;
    public List<Integer> originCharsIndexes;

    public MaskedText(List<Character> maskedChars, List<Integer> originCharsIndexes) {
        this.maskedChars = maskedChars;
        this.originCharsIndexes = originCharsIndexes;
    }
}
