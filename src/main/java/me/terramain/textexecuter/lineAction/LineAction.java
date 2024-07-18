package me.terramain.textexecuter.lineAction;

public class LineAction {
    private String line;
    private final int index;

    public LineAction(String line, int index) {
        this.line = line;
        this.index = index;
    }

    public String getLine() {return line;}
    public void setLine(String line) {this.line = line;}

    public int getIndex() {return index;}
}
