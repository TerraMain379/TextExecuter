package me.terramain.textexecuter;

import java.util.function.Consumer;

public class TextIterator {
    private char[] chars;
    private int index;

    public TextIterator(String text) {
        this.chars = text.toCharArray();
        index = 0;
    }
    public TextIterator(String text, int index) {
        this.chars = text.toCharArray();
        this.index = index;
    }
    public TextIterator(char[] chars, int index) {
        this.chars = chars;
        this.index = index;
    }

    public int getIndex(){
        return index;
    }
    public String getText(){
        return new String(chars);
    }

    public boolean hasGetCharAt(int index){
        return index<chars.length && index>=0;
    }
    public Character getCharAt(int index){
        return hasGetCharAt(index)?chars[index]:null;
    }
    public boolean hasGetChar(){
        return hasGetCharAt(index);
    }
    public Character getChar(){
        return getCharAt(index);
    }

    public boolean hasNextAt(int num){
        return hasGetCharAt(index+num);
    }
    public Character getNextAt(int num){
        return getCharAt(index+num);
    }
    public boolean hasNext(){
        return hasNextAt(1);
    }
    public Character getNext(){
        return getNextAt(1);
    }

    public boolean hasPreviousAt(int num){
        return hasGetCharAt(index-num);
    }
    public Character getPreviousAt(int num){
        return getCharAt(index-num);
    }
    public boolean hasPrevious(){
        return hasPreviousAt(1);
    }
    public Character getPrevious(){
        return getPreviousAt(1);
    }

    public Character nextAt(int num){
        index+=num;
        return getChar();
    }
    public Character next(){
        return nextAt(1);
    }
    public Character previousAt(int num){
        return nextAt(-num);
    }
    public Character previous(){
        return previousAt(1);
    }

    public String substring(int startChar, int endChar){
        if (startChar<0) startChar+=chars.length-1;
        if (endChar<0) endChar+=chars.length-1;
        endChar++;
        return getText().substring(startChar,endChar);
    }
    public String substring(int startChar){
        return getText().substring(startChar);
    }

    public void foreach(Consumer<? super Character> action) {
        while (hasGetChar()){
            action.accept(getChar());
            next();
        }
    }

    public String readString(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length && getChar()!=null; i++) {
            stringBuilder.append(getChar());
            next();
        }
        return stringBuilder.toString();
    }

    public String readWholeNumber(){
        StringBuilder number = new StringBuilder();
        while (hasGetChar()){
            char c = getChar();
            next();
            if (Character.isDigit(c)) number.append(c);
            else break;
        }
        return number.toString();
    }
    public int readInt(){
        return Integer.parseInt(readWholeNumber());
    }
    public long readLong(){
        return Long.parseLong(readWholeNumber());
    }
    public String readFractionalNumber(){
        StringBuilder number = new StringBuilder();
        boolean point = false;
        while (hasGetChar()){
            char c = getChar();
            next();
            if (!Character.isDigit(c)) {
                if (c=='.' && !point) point=true;
                else break;
            }
            number.append(c);
        }
        return number.toString();
    }
    public double readDouble(){
        return Double.parseDouble(readFractionalNumber());
    }

    @Override public TextIterator clone(){
        return new TextIterator(chars,index);
    }


    @Deprecated public String readStringFromLength(int length){
        return readString(length);
    }
    @Deprecated public String getSubstring(int startChar, int endChar){
        if (endChar==-1) return getText().substring(startChar);
        else return getText().substring(startChar,endChar);
    }
    @Deprecated public String getSubstring(int startChar){
        return getSubstring(startChar,-1);
    }
}
