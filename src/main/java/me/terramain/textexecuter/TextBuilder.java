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

    public char getChar(int index){
        return charsArray.getChar(index);
    }
    public TextBuilder setChar(char c, int index){
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

    public TextBuilder charForeach(ICharacterAction action){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charsArray.getChars().length; i++) {
            CharacterAction characterAction = new CharacterAction(charsArray.getChar(i),i);
            action.action(characterAction);
            if (characterAction.getCharacter()!=null){
                stringBuilder.append(characterAction.getCharacter());
            }
        }
        charsArray = new CharsArrayBuilder(stringBuilder.toString());
        return this;
    }
    public TextBuilder lineForeach(ILineAction action){
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String line : charsArray.getText().split("\n")) {
            LineAction lineAction = new LineAction(line, i);
            action.action(lineAction);
            stringBuilder.append(lineAction.getLine()).append("\n");
            i++;
        }
        return this;
    }


}
