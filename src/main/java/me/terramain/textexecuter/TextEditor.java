package me.terramain.textexecuter;

public class TextEditor {
    public static String addSpacesToLines(String text, int spaces){
        TextBuilder textBuilder = new TextBuilder(text);
        textBuilder.lineForeach(action ->
            action.setLine(" ".repeat(spaces) + action.getLine())
        );
        return textBuilder.getText();
    }
}
