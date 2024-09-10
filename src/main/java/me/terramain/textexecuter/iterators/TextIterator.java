package me.terramain.textexecuter.iterators;

import me.terramain.textexecuter.characterAction.*;

import java.io.*;
import java.util.Iterator;

public class TextIterator implements Iterator {
    private StringReader stringReader;
    private int index;
    private int lineNumber;

    private int readCode;

    public TextIterator(StringReader stringReader) {
        this.stringReader = stringReader;
        this.index = -1;
        this.lineNumber = 0;
    }
    public TextIterator(String str){
        this(new StringReader(str));
    }
    public TextIterator(FileReader fileReader){
        StringReader stringReader = new StringReader(""){
            @Override
            public int read() throws IOException {
                synchronized (lock) {
                    return fileReader.read();
                }
            }
        };
    }
    public TextIterator(File file) throws FileNotFoundException {
        this(new FileReader(file));
    }

    public int getIndex() {return index;}
    public int getLineNumber() {return lineNumber;}

    private void read(){
        if (readCode==-2) {
            try {
                readCode = stringReader.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override public boolean hasNext() {
        read();
        return readCode != -1;
    }
    @Override public Object next() {
        read();
        int oldReadCode = readCode;
        readCode = -2;
        return oldReadCode;
    }
    public Object nextAt(int shift){
        if (shift<0) throw new IllegalArgumentException();
        for (int i = 0; i < shift - 1; i++) {
            next();
        }
        return next();
    }
    public Character nextChar(){
        int val = (int) next();
        if (val==-1) return null;
        return (char) val;
    }
    public Character nextCharAt(int shift){
        int val = (int) nextAt(shift);
        if (val==-1) return null;
        return (char) val;
    }


    public void foreach(IMicroCharacterAction action) {
        while (hasNext()){
            action.action((char)next());
        }
    }
    public void foreach(ICharacterAction action) {
        while (hasNext()){
            next();
            action.action(new CharacterAction(nextChar(), getIndex(), getLineNumber(), null));
        }
    }
    public void foreach(IMicroStopableCharacterAction action) {
        boolean flag = true;
        while (hasNext() && flag){
            flag = action.action((char)next());
        }
    }
    public void foreach(IStopableCharacterAction action) {
        boolean flag = true;
        while (hasNext() && flag){
            flag = action.action(new CharacterAction(nextChar(), getIndex(), getLineNumber(), null));
        }
    }

}
