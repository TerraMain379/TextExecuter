package me.terramain.textexecuter;

import me.terramain.textexecuter.characterAction.CharacterAction;
import me.terramain.textexecuter.characterAction.ICharacterAction;
import me.terramain.textexecuter.lineAction.ILineAction;
import me.terramain.textexecuter.lineAction.LineAction;

public class TextBuilder {
    private CharsArrayBuilder charsArray;

    public TextBuilder(CharsArrayBuilder charsArray) {
        this.charsArray = charsArray;
    }
    public TextBuilder(String text) {
        this(new CharsArrayBuilder(text));
    }
    private TextBuilder(){
        this(new CharsArrayBuilder());
    }

    public String getText(){
        return charsArray.getText();
    }
    public int length(){
        return charsArray.getChars().length;
    }

    public char getChar(int index){
        return charsArray.getChar(index);
    }
    public TextBuilder setChar(int index,char c){
        charsArray.setChar(c,index);
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
        String newText = charsArray.getText().substring(0,startIndex-1);
        newText += charsArray.getText().substring(endIndex+1);
        charsArray = new CharsArrayBuilder(newText);
        return this;
    }

    public TextBuilder charAction(ICharacterAction action, int charNumber){
        CharacterAction characterAction = new CharacterAction(getChar(charNumber),charNumber);
        action.action(characterAction);
        if (characterAction.getCharacter()!=null){
            removeChar(charNumber);
        }
        else setChar(charNumber,characterAction.getCharacter());
        return this;
    }
    public TextBuilder charForeach(ICharacterAction action){
        int len = charsArray.getChars().length;
        for (int i = 0; i < len; i++) {
            char c = charsArray.getChar(i);
            charAction(action,i);
            if (len != charsArray.getChars().length) i--;
        }
        return null;
    }
    public TextBuilder lineAction(ILineAction action, int lineNumber){
        String[] lines = charsArray.getText().split("\n");
        LineAction lineAction = new LineAction(lines[lineNumber],lineNumber);
        action.action(lineAction);
        lines[lineNumber] = lineAction.getLine();
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines) {
            if (line!=null) stringBuilder.append(line).append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        charsArray = new CharsArrayBuilder(stringBuilder.toString());
        return this;
    }
    public TextBuilder lineForeach(ILineAction action){
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String line : charsArray.getText().split("\n")) {
            LineAction lineAction = new LineAction(line, i);
            action.action(lineAction);
            if (line!=null) stringBuilder.append(lineAction.getLine()).append("\n");
            i++;
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        charsArray = new CharsArrayBuilder(stringBuilder.toString());
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
    public TextBuilder stripLines(){
        return lineForeach(action -> action.setLine(action.getLine().strip()));
    }
    public TextBuilder addTextToLines(String text, LineLocation location){
        if (location==LineLocation.START)
            charsArray.addChars(0,text.toCharArray());
        else if (location==LineLocation.END)
            charsArray.addChars(text.toCharArray());
        return this;
    }

}
