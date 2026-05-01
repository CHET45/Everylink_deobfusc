package com.aivox.besota.bessdk.utils.sha;

/* JADX INFO: loaded from: classes.dex */
public class besFunc {
    private byte[] key = new byte[16];

    private byte generate_random_algorithm(int i, byte b, byte b2) {
        int i2 = i % 6;
        if (i2 == 0) {
            return (byte) (b + b2);
        }
        if (i2 == 1) {
            return (byte) (b * b2);
        }
        if (i2 == 2) {
            return (byte) (b & b2);
        }
        if (i2 == 3) {
            return (byte) (b | b2);
        }
        if (i2 == 4) {
            return (byte) (b ^ b2);
        }
        if (i2 != 5) {
            return (byte) 0;
        }
        return (byte) ((b * b) + (b2 * b2));
    }

    public byte[] func1(byte[] bArr, byte[] bArr2) {
        if (bArr.length > bArr2.length) {
            byte[] bArr3 = new byte[bArr.length];
            System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
            bArr2 = bArr3;
        } else if (bArr.length < bArr2.length) {
            byte[] bArr4 = new byte[bArr2.length];
            System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
            bArr = bArr4;
        }
        byte[] bArr5 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr5[i] = generate_random_algorithm(i, bArr[i], bArr2[i]);
        }
        return bArr5;
    }

    public byte[] func(byte[] bArr) {
        for (int i = 0; i < 16; i++) {
            this.key[i] = bArr[i * 2];
        }
        return this.key;
    }
}
