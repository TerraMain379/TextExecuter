package me.terramain.textexecuter.charsarraybuilder;

import me.terramain.textexecuter.textbuilder.TextBuilder;
import me.terramain.textexecuter.util.CharacterData;

import java.util.ArrayList;

public abstract class CharsArrayBuilderMask {
    public abstract MaskedText executeMask(String text);

    public static class Period extends CharsArrayBuilderMask {
        public final int startIndex;
        public final int endIndex;
        public Period(int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override public MaskedText executeMask(String text) {
            MaskedText maskedText = new MaskedText(new ArrayList<>(),new ArrayList<>());
            for (int i = startIndex; i <= endIndex; i++) {
                maskedText.originCharsIndexes.add(i);
                maskedText.maskedChars.add(text.charAt(i));
            }
            return maskedText;
        }
    }

    public static final class Chars extends CharsArrayBuilderMask {
        public final char[] chars;
        public Chars(char[] chars) {
            this.chars = chars;
        }
        public boolean testChar(char c){
            for (char testC : chars) {
                if (c==testC) return true;
            }
            return false;
        }

        @Override public MaskedText executeMask(String text) {
            MaskedText maskedText = new MaskedText(new ArrayList<>(),new ArrayList<>());
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (testChar(c)) {
                    maskedText.originCharsIndexes.add(i);
                    maskedText.maskedChars.add(c);
                }
            }
            return maskedText;
        }
    }

    public static final class Condition extends CharsArrayBuilderMask {
        public final ICharacterMaskCondition condition;
        public Condition(ICharacterMaskCondition condition) {
            this.condition = condition;
        }

        @Override public MaskedText executeMask(String text) {
            MaskedText maskedText = new MaskedText(new ArrayList<>(),new ArrayList<>());
            TextBuilder lineBuilder = new TextBuilder();
            for (int i = 0, lineNumber = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (condition.condition(new CharacterData(c,i,lineNumber,lineBuilder.getText()))){
                    maskedText.originCharsIndexes.add(i);
                    maskedText.maskedChars.add(c);
                }
                if (c=='\n'){
                    lineBuilder = new TextBuilder();
                    lineNumber++;
                }
            }
            return maskedText;
        }
    }

}
