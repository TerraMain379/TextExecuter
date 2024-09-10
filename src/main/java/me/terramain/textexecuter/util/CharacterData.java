package me.terramain.textexecuter.util;

import me.terramain.textexecuter.textbuilder.TextBuilder;

public class CharacterData {
    protected Character character;
    protected final int index;
    protected final int lineNumber;
    protected final String line;

    public CharacterData(Character character, int index, int lineNumber, String line) {
        this.character = character;
        this.index = index;
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public Character getCharacter() {return character;}
    public int getIndex() {return index;}
    public int getLineNumber() {return lineNumber;}
    public String getLine() {return line;}

    public static CharacterData generate(String text, int index){
        TextBuilder line = new TextBuilder();
        int lineNumber = 0;
        for (int i = 0; i <= index; i++) {
            char c = text.charAt(i);
            line.append(c);
            if (c=='\n'){
                line.remove(0,line.length()-1);
                lineNumber++;
            }
        }
        return new CharacterData(
                text.charAt(index),
                index,
                lineNumber,
                line.getText()
        );
    }
}
