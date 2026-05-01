package com.fasterxml.jackson.core.sym;

import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public final class NameN extends Name {

    /* JADX INFO: renamed from: q */
    private final int[] f474q;

    /* JADX INFO: renamed from: q1 */
    private final int f475q1;

    /* JADX INFO: renamed from: q2 */
    private final int f476q2;

    /* JADX INFO: renamed from: q3 */
    private final int f477q3;

    /* JADX INFO: renamed from: q4 */
    private final int f478q4;
    private final int qlen;

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2, int i3) {
        return false;
    }

    NameN(String str, int i, int i2, int i3, int i4, int i5, int[] iArr, int i6) {
        super(str, i);
        this.f475q1 = i2;
        this.f476q2 = i3;
        this.f477q3 = i4;
        this.f478q4 = i5;
        this.f474q = iArr;
        this.qlen = i6;
    }

    public static NameN construct(String str, int i, int[] iArr, int i2) {
        if (i2 < 4) {
            throw new IllegalArgumentException();
        }
        return new NameN(str, i, iArr[0], iArr[1], iArr[2], iArr[3], i2 + (-4) > 0 ? Arrays.copyOfRange(iArr, 4, i2) : null, i2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int[] iArr, int i) {
        if (i != this.qlen || iArr[0] != this.f475q1 || iArr[1] != this.f476q2 || iArr[2] != this.f477q3 || iArr[3] != this.f478q4) {
            return false;
        }
        switch (i) {
            case 5:
                if (iArr[4] == this.f474q[0]) {
                }
                break;
            case 6:
                if (iArr[5] != this.f474q[1]) {
                }
                if (iArr[4] == this.f474q[0]) {
                }
                break;
            case 7:
                if (iArr[6] != this.f474q[2]) {
                }
                if (iArr[5] != this.f474q[1]) {
                }
                if (iArr[4] == this.f474q[0]) {
                }
                break;
            case 8:
                if (iArr[7] != this.f474q[3]) {
                }
                if (iArr[6] != this.f474q[2]) {
                }
                if (iArr[5] != this.f474q[1]) {
                }
                if (iArr[4] == this.f474q[0]) {
                }
                break;
        }
        return false;
    }

    private final boolean _equals2(int[] iArr) {
        int i = this.qlen - 4;
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2 + 4] != this.f474q[i2]) {
                return false;
            }
        }
        return true;
    }
}
