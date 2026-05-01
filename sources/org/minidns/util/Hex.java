package org.minidns.util;

/* JADX INFO: loaded from: classes4.dex */
public class Hex {
    public static StringBuilder from(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(String.format("%02X ", Byte.valueOf(b)));
        }
        return sb;
    }
}
