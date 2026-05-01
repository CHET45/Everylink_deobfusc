package com.tencent.beacon.pack;

import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public final class ValueType implements Serializable {
    public static final int BYTE_VAL = 1;
    public static final int MAP_VAL = 0;
    public static final int UNKNOWN_VAL = 2;

    /* JADX INFO: renamed from: t */
    private String f1745t;
    private int value;
    public static final ValueType MAP = new ValueType(0, 0, "MAP");
    public static final ValueType BYTE = new ValueType(1, 1, "BYTE");
    public static final ValueType UNKNOWN = new ValueType(2, 2, "UNKNOWN");
    private static ValueType[] values = new ValueType[3];

    private ValueType(int i, int i2, String str) {
        this.f1745t = str;
        this.value = i2;
        values[i] = this;
    }

    public static ValueType convert(int i) {
        for (ValueType valueType : values) {
            if (valueType.value() == i) {
                return valueType;
            }
        }
        return null;
    }

    public String toString() {
        return this.f1745t;
    }

    public int value() {
        return this.value;
    }

    public static ValueType convert(String str) {
        for (ValueType valueType : values) {
            if (valueType.toString().equals(str)) {
                return valueType;
            }
        }
        return null;
    }
}
