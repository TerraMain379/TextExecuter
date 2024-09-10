package me.terramain.textexecuter.textbuilder;

import me.terramain.textexecuter.TextHelper;
import me.terramain.textexecuter.charsarraybuilder.CharsArrayBuilder;
import me.terramain.textexecuter.util.LineLocation;
import me.terramain.textexecuter.characterAction.CharacterAction;
import me.terramain.textexecuter.characterAction.ICharacterAction;
import me.terramain.textexecuter.characterAction.IStopableCharacterAction;
import me.terramain.textexecuter.lineAction.ILineAction;
import me.terramain.textexecuter.lineAction.IStopableLineAction;
import me.terramain.textexecuter.lineAction.LineAction;

public class TextBuilder {
    private CharsArrayBuilder charsArray;

    public TextBuilder(CharsArrayBuilder charsArray) {
        this.charsArray = charsArray;
    }
    public TextBuilder(char[] chars){
        this(new CharsArrayBuilder(chars));
    }
    public TextBuilder(String text) {
        this(new CharsArrayBuilder(text));
    }
    public TextBuilder(){
        this(new CharsArrayBuilder());
    }

    public String getText(){
        return charsArray.getText();
    }
    public int length(){
        return charsArray.getText().length();
    }

    public char getChar(int index){
        return charsArray.getChar(index);
    }
    public TextBuilder setChar(int index,char c){
        charsArray.setChar(index,c);
        StringBuilder stringBuilder = new StringBuilder();
        return this;
    }
    public TextBuilder removeChar(int index){
        charsArray.removeChar(index);
        return this;
    }

    public TextBuilder append(char c){
        charsArray.addChar(c);
        return this;
    }
    public TextBuilder append(char[] cs){
        charsArray.addChars(cs);
        return this;
    }
    public TextBuilder append(String str){
        charsArray.addChars(str.toCharArray());
        return this;
    }
    public TextBuilder append(int num){
        charsArray.addChars((String.valueOf(num)).toCharArray());
        return this;
    }
    public TextBuilder append(long num){
        charsArray.addChars((String.valueOf(num)).toCharArray());
        return this;
    }
    public TextBuilder append(double num){
        charsArray.addChars((String.valueOf(num)).toCharArray());
        return this;
    }
    public TextBuilder append(Object obj){
        charsArray.addChars(obj.toString().toCharArray());
        return this;
    }

    public TextBuilder insert(int index, char c){
        charsArray.addChar(index, c);
        return this;
    }
    public TextBuilder insert(int index, char[] cs){
        charsArray.addChars(index, cs);
        return this;
    }
    public TextBuilder insert(int index, String str){
        charsArray.addChars(index, str.toCharArray());
        return this;
    }
    public TextBuilder insert(int index, int num){
        charsArray.addChars(index, String.valueOf(num).toCharArray());
        return this;
    }
    public TextBuilder insert(int index, long num){
        charsArray.addChars(index, String.valueOf(num).toCharArray());
        return this;
    }
    public TextBuilder insert(int index, double num){
        charsArray.addChars(index, String.valueOf(num).toCharArray());
        return this;
    }
    public TextBuilder insert(int index, Object obj){
        charsArray.addChars(index, obj.toString().toCharArray());
        return this;
    }

    public TextBuilder remove(int startIndex, int endIndex){
        charsArray.removeChars(startIndex,endIndex);
        return this;
    }

    public TextBuilder charAction(int index, ICharacterAction action){
        CharacterAction characterAction = new CharacterAction(getChar(index),index,getLineNumberAtIndex(index), getLineAtCharIndex(index));
        action.action(characterAction);
        if (characterAction.getCharacter()==null) removeChar(index);
        else setChar(index,characterAction.getCharacter());
        return this;
    }
    private boolean charAction(int index, IStopableCharacterAction action){
        final Boolean[] result = {true};
        charAction(index,action2 -> {
            result[0] = action.action(action2);
        });
        return result[0];
    }
    public TextBuilder charForeach(ICharacterAction action){
        int oldSize = charsArray.length();
        for (int i = 0; i < oldSize; i++) {
            charAction(i,action);
            if (oldSize!=charsArray.length()){
                oldSize = charsArray.length();
                i--;
            }
        }
        return this;
    }
    public TextBuilder charForeach(IStopableCharacterAction action){
        int oldSize = charsArray.length();
        boolean flag = true;
        for (int i = 0; i < oldSize && flag; i++) {
            flag = charAction(i,action);
            if (oldSize!=charsArray.length()){
                oldSize = charsArray.length();
                i--;
            }
        }
        return this;
    }

    public String[] getLines(){
        return getText().split("\n");
    }
    public String getLine(int index){
        return TextHelper.getLineAtNumber(getText(), index);
    }

    public int getLineNumberAtIndex(int index){
        return TextHelper.getLineNumberAtCharIndex(getText(),index);
    }
    public String getLineAtCharIndex(int index){
        return getLine(getLineNumberAtIndex(index));
    }

    public TextBuilder lineAction(int index, ILineAction iLineAction){
        String[] lines = getLines();
        LineAction lineAction = new LineAction(lines[index],index);
        iLineAction.action(lineAction);
        lines[index] = lineAction.getLine();
        charsArray.clear();
        for (String line : lines) {
            if (line!=null){
                charsArray.addChars(line.toCharArray());
                charsArray.addChar('\n');
            }
        }
        if (getChar(length()-1)=='\n') removeChar(length()-1);
        return this;
    }
    private boolean lineAction(int index, IStopableLineAction action){
        final Boolean[] result = {true};
        lineAction(index,action2 -> {
            result[0] = action.action(action2);
        });
        return result[0];
    }
    public TextBuilder lineForeach(ILineAction action){
        String[] lines = getText().split("\n");
        int oldSize = lines.length;
        for (int i = 0; i < oldSize; i++) {
            lineAction(i,action);
            lines = getText().split("\n");
            if (oldSize!=lines.length){
                oldSize = lines.length;
                i--;
            }
        }
        return this;
    }
    public TextBuilder lineForeach(IStopableLineAction action){
        String[] lines = getText().split("\n");
        int oldSize = lines.length;
        boolean flag = true;
        for (int i = 0; i < oldSize; i++) {
            flag = lineAction(i,action);
            lines = getText().split("\n");
            if (oldSize!=lines.length){
                oldSize = lines.length;
                i--;
            }
        }
        return this;
    }


    public TextBuilder removeChars(char c){
        return charForeach(charAction -> {
            if (charAction.getCharacter()==c) {
                charAction.setCharacter(null);
            }
        });
    }
    public TextBuilder replaceChars(char c, char replaceChar){
        return charForeach(charAction -> {
            if (charAction.getCharacter()==c) {
                charAction.setCharacter(replaceChar);
            }
        });
    }

    public TextBuilder strip(){
        String text = charsArray.getText().strip();
        charsArray.clear();
        charsArray.addChars(text.toCharArray());
        return this;
    }
    public TextBuilder stripLines(){
        return lineForeach((ILineAction) action -> action.setLine(action.getLine().strip()));
    }

    public TextBuilder addTextToLines(String text, LineLocation location){
        return lineForeach(action -> {
            CharsArrayBuilder lineBuilder = new CharsArrayBuilder(action.getLine());
            if (location==LineLocation.START)
                lineBuilder.addChars(0,text.toCharArray());
            else if (location==LineLocation.END)
                lineBuilder.addChars(text.toCharArray());
            action.setLine(lineBuilder.getText());
        });
    }
}
