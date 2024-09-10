package me.terramain.textexecuter.charsarraybuilder;

public class MaskedCharsArrayBuilder extends CharsArrayBuilder{
    public final CharsArrayBuilderMask mask;
    public MaskedCharsArrayBuilder(CharsArrayBuilderMask mask, int defaultScaleShift, char[] chars, int textSize) {
        super(defaultScaleShift, chars, textSize);
        this.mask = mask;
    }

}
