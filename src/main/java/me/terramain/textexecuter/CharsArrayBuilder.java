package me.terramain.textexecuter;

public class CharsArrayBuilder {
    private int defaultScaleShift;
        public int getDefaultScaleShift() {  return defaultScaleShift;  }
        public void setDefaultScaleShift(int defaultScaleShift) {  this.defaultScaleShift = defaultScaleShift;  }

    private char[] chars;
    private int textSize;

    public CharsArrayBuilder(int defaultScaleShift, char[] chars, int textSize) {
        this.defaultScaleShift = defaultScaleShift;
        this.chars = chars;
        this.textSize = textSize;
    }
    public CharsArrayBuilder(char[] chars) {
        this(10,chars,chars.length);
    }
    public CharsArrayBuilder(String text) {
        this(text.toCharArray());
    }
    public CharsArrayBuilder(){
        this("");
    }

    public void scaleCharsArray(int scaleShift){
        char[] oldChars = new char[chars.length+scaleShift];
        if (scaleShift>=0)  System.arraycopy(chars,0,oldChars,0,chars.length);
        else                System.arraycopy(chars,0,oldChars,0,oldChars.length);
        chars = oldChars;
    }
    public char getChar(int index){
        return chars[index];
    }
    public void setChar(char c, int index){
        if (index>=chars.length){
            scaleCharsArray(index-chars.length+1);
            textSize = index;
        }
        chars[index] = c;
    }
    public void addChar(char c){
        if (textSize>=chars.length){
            scaleCharsArray(defaultScaleShift);
        }
        chars[textSize] = c;
        textSize++;
    }
    public void addChar(int index, char c){
        for (int i = chars.length-1;i>=index;i--){
            setChar(chars[i],i + 1);
        }
        chars[index] = c;
    }
    public void addChars(char[] cs){
        for (char c : cs) {
            addChar(c);
        }
    }
    public void addChars(int index, char[] cs){
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            addChar(index+i,c);
        }
    }
    public void addChars(Iterable<Character> cs){
        for (Character c : cs) addChar(c);
    }
    public void addChars(int index, Iterable<Character> cs){
        int i = 0;
        for (Character c : cs) {
            addChar(index+i,c);
            i++;
        }
    }
    public void removeChar(int index){
        for (int i = index; i<chars.length-1;i++){
            chars[i]=chars[i+1];
        }
        scaleCharsArray(textSize-chars.length);
        scaleCharsArray(-1);
        textSize--;
    }

    public void clear(){
        chars = new char[defaultScaleShift];
        textSize = 0;
    }

    public char[] getChars(){
        return chars;
    }
    public String getText(){
        return new String(chars).substring(0,textSize);
    }
}
