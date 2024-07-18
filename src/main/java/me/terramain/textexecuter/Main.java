package me.terramain.textexecuter;

public class Main {
    public static void main(String[] args) {
        CharsArrayBuilder charsArrayBuilder = new CharsArrayBuilder("12345");
        charsArrayBuilder.removeChar(3);
        charsArrayBuilder.addChar('4');
        System.out.println(charsArrayBuilder.getText());
    }
}
