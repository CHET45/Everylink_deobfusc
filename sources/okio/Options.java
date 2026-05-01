package okio;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;
import kotlin.UByte;

/* JADX INFO: loaded from: classes4.dex */
public final class Options extends AbstractList<ByteString> implements RandomAccess {
    final ByteString[] byteStrings;
    final int[] trie;

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b8, code lost:
    
        continue;
     */
    /* JADX INFO: renamed from: of */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static okio.Options m1930of(okio.ByteString... r11) {
        /*
            Method dump skipped, instruction units count: 252
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Options.m1930of(okio.ByteString[]):okio.Options");
    }

    private static void buildTrieRecursive(long j, Buffer buffer, int i, List<ByteString> list, int i2, int i3, List<Integer> list2) {
        int iIntValue;
        int i4;
        int i5;
        int i6;
        int i7;
        Buffer buffer2;
        if (i2 >= i3) {
            throw new AssertionError();
        }
        for (int i8 = i2; i8 < i3; i8++) {
            if (list.get(i8).size() < i) {
                throw new AssertionError();
            }
        }
        ByteString byteString = list.get(i2);
        ByteString byteString2 = list.get(i3 - 1);
        if (i == byteString.size()) {
            int i9 = i2 + 1;
            i4 = i9;
            iIntValue = list2.get(i2).intValue();
            byteString = list.get(i9);
        } else {
            iIntValue = -1;
            i4 = i2;
        }
        if (byteString.getByte(i) != byteString2.getByte(i)) {
            int i10 = 1;
            for (int i11 = i4 + 1; i11 < i3; i11++) {
                if (list.get(i11 - 1).getByte(i) != list.get(i11).getByte(i)) {
                    i10++;
                }
            }
            long jIntCount = j + ((long) intCount(buffer)) + 2 + ((long) (i10 * 2));
            buffer.writeInt(i10);
            buffer.writeInt(iIntValue);
            for (int i12 = i4; i12 < i3; i12++) {
                byte b = list.get(i12).getByte(i);
                if (i12 == i4 || b != list.get(i12 - 1).getByte(i)) {
                    buffer.writeInt(b & UByte.MAX_VALUE);
                }
            }
            Buffer buffer3 = new Buffer();
            int i13 = i4;
            while (i13 < i3) {
                byte b2 = list.get(i13).getByte(i);
                int i14 = i13 + 1;
                int i15 = i14;
                while (true) {
                    if (i15 >= i3) {
                        i6 = i3;
                        break;
                    } else {
                        if (b2 != list.get(i15).getByte(i)) {
                            i6 = i15;
                            break;
                        }
                        i15++;
                    }
                }
                if (i14 == i6 && i + 1 == list.get(i13).size()) {
                    buffer.writeInt(list2.get(i13).intValue());
                    i7 = i6;
                    buffer2 = buffer3;
                } else {
                    buffer.writeInt((int) ((((long) intCount(buffer3)) + jIntCount) * (-1)));
                    i7 = i6;
                    buffer2 = buffer3;
                    buildTrieRecursive(jIntCount, buffer3, i + 1, list, i13, i6, list2);
                }
                buffer3 = buffer2;
                i13 = i7;
            }
            Buffer buffer4 = buffer3;
            buffer.write(buffer4, buffer4.size());
            return;
        }
        int iMin = Math.min(byteString.size(), byteString2.size());
        int i16 = 0;
        for (int i17 = i; i17 < iMin && byteString.getByte(i17) == byteString2.getByte(i17); i17++) {
            i16++;
        }
        long jIntCount2 = 1 + j + ((long) intCount(buffer)) + 2 + ((long) i16);
        buffer.writeInt(-i16);
        buffer.writeInt(iIntValue);
        int i18 = i;
        while (true) {
            i5 = i + i16;
            if (i18 >= i5) {
                break;
            }
            buffer.writeInt(byteString.getByte(i18) & UByte.MAX_VALUE);
            i18++;
        }
        if (i4 + 1 == i3) {
            if (i5 != list.get(i4).size()) {
                throw new AssertionError();
            }
            buffer.writeInt(list2.get(i4).intValue());
        } else {
            Buffer buffer5 = new Buffer();
            buffer.writeInt((int) ((((long) intCount(buffer5)) + jIntCount2) * (-1)));
            buildTrieRecursive(jIntCount2, buffer5, i5, list, i4, i3, list2);
            buffer.write(buffer5, buffer5.size());
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public ByteString get(int i) {
        return this.byteStrings[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.byteStrings.length;
    }

    private static int intCount(Buffer buffer) {
        return (int) (buffer.size() / 4);
    }
}
