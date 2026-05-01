package com.github.houbb.heaven.util.trick;

import com.azure.xml.implementation.aalto.util.XmlConsts;
import java.text.DecimalFormat;

/* JADX INFO: loaded from: classes3.dex */
public class ConsoleProgressBar {
    private long barLen;
    private DecimalFormat formater;
    private long maximum;
    private long minimum;
    private char showChar;

    public ConsoleProgressBar() {
        this.minimum = 0L;
        this.maximum = 100L;
        this.barLen = 100L;
        this.showChar = '=';
        this.formater = new DecimalFormat("#.##%");
    }

    public ConsoleProgressBar(long j, long j2, long j3) {
        this(j, j2, j3, '=');
    }

    public ConsoleProgressBar(long j, long j2, long j3, char c) {
        this.minimum = 0L;
        this.maximum = 100L;
        this.barLen = 100L;
        this.showChar = '=';
        this.formater = new DecimalFormat("#.##%");
        this.minimum = j;
        this.maximum = j2;
        this.barLen = j3;
        this.showChar = c;
    }

    public void show(long j) {
        if (j < this.minimum || j > this.maximum) {
            return;
        }
        reset();
        this.minimum = j;
        float f = (float) ((j * 1.0d) / this.maximum);
        draw((long) (this.barLen * f), f);
        if (this.minimum == this.maximum) {
            afterComplete();
        }
    }

    private void draw(long j, float f) {
        for (int i = 0; i < j; i++) {
            System.out.print(this.showChar);
        }
        System.out.print(' ');
        System.out.print(format(f));
    }

    private void reset() {
        System.out.print(XmlConsts.CHAR_CR);
    }

    private void afterComplete() {
        System.out.print('\n');
    }

    private String format(float f) {
        return this.formater.format(f);
    }
}
