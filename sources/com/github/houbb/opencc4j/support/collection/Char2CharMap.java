package com.github.houbb.opencc4j.support.collection;

/* JADX INFO: loaded from: classes3.dex */
public final class Char2CharMap {
    private static final char EMPTY_KEY = 0;
    private static final float LOAD_FACTOR = 0.5f;
    private char[] keys;
    private int mask;
    private int maxSize;
    private int size;
    private char[] values;

    private static int tableSizeFor(int i) {
        int i2 = i - 1;
        int i3 = i2 | (i2 >>> 1);
        int i4 = i3 | (i3 >>> 2);
        int i5 = i4 | (i4 >>> 4);
        int i6 = i5 | (i5 >>> 8);
        int i7 = i6 | (i6 >>> 16);
        if (i7 < 2) {
            return 2;
        }
        if (i7 >= 1073741824) {
            return 1073741824;
        }
        return i7 + 1;
    }

    public Char2CharMap(int i) {
        int iTableSizeFor = tableSizeFor(((int) (i / 0.5f)) + 1);
        this.keys = new char[iTableSizeFor];
        this.values = new char[iTableSizeFor];
        this.mask = iTableSizeFor - 1;
        this.maxSize = (int) (iTableSizeFor * 0.5f);
        this.size = 0;
    }

    private int hash(char c) {
        return (c * 31161) & this.mask;
    }

    public void put(char c, char c2) {
        if (c == 0) {
            throw new IllegalArgumentException("Key '\u0000' is reserved as EMPTY_KEY.");
        }
        int iHash = hash(c);
        while (true) {
            char[] cArr = this.keys;
            char c3 = cArr[iHash];
            if (c3 == 0) {
                cArr[iHash] = c;
                this.values[iHash] = c2;
                int i = this.size + 1;
                this.size = i;
                if (i >= this.maxSize) {
                    resize();
                    return;
                }
                return;
            }
            if (c3 == c) {
                this.values[iHash] = c2;
                return;
            }
            iHash = (iHash + 1) & this.mask;
        }
    }

    public char get(char c, char c2) {
        if (c == 0) {
            return c2;
        }
        int iHash = hash(c);
        while (true) {
            char c3 = this.keys[iHash];
            if (c3 == 0) {
                return c2;
            }
            if (c3 == c) {
                return this.values[iHash];
            }
            iHash = (iHash + 1) & this.mask;
        }
    }

    public char get(char c) {
        return get(c, (char) 0);
    }

    private void resize() {
        char[] cArr = this.keys;
        int length = cArr.length << 1;
        char[] cArr2 = this.values;
        this.keys = new char[length];
        this.values = new char[length];
        this.mask = length - 1;
        this.maxSize = (int) (length * 0.5f);
        this.size = 0;
        for (int i = 0; i < cArr.length; i++) {
            char c = cArr[i];
            if (c != 0) {
                put(c, cArr2[i]);
            }
        }
    }

    public int size() {
        return this.size;
    }
}
