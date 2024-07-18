package me.terramain.textexecuter.characterAction;

public class CharacterAction {
    private Character character;
    private final int index;

    public CharacterAction(Character character, int index) {
        this.character = character;
        this.index = index;
    }

    public Character getCharacter() {return character;}
    public void setCharacter(Character character) {this.character = character;}

    public int getIndex() {return index;}
}
