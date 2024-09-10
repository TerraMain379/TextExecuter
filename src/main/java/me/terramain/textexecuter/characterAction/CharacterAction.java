package me.terramain.textexecuter.characterAction;

import me.terramain.textexecuter.textbuilder.TextBuilder;
import me.terramain.textexecuter.util.CharacterData;

public class CharacterAction extends CharacterData {

    public CharacterAction(Character character, int index, int lineNumber, String line) {
        super(character, index, lineNumber, line);
    }
    public CharacterAction(int index, TextBuilder textBuilder){
        this(textBuilder.getChar(index), index, textBuilder.getLineNumberAtIndex(index), textBuilder.getLineAtCharIndex(index));
    }
    public CharacterAction(int index, String text){
        this(index, new TextBuilder(text));
    }

    public void setCharacter(Character character) {this.character = character;}
}
