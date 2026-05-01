package org.minidns.record;

import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.UByte;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class TXT extends Data {
    private final byte[] blob;
    private transient List<String> characterStringsCache;
    private transient String textCache;

    public static TXT parse(DataInputStream dataInputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        dataInputStream.readFully(bArr);
        return new TXT(bArr);
    }

    public TXT(byte[] bArr) {
        this.blob = bArr;
    }

    public byte[] getBlob() {
        return (byte[]) this.blob.clone();
    }

    public String getText() {
        if (this.textCache == null) {
            StringBuilder sb = new StringBuilder();
            Iterator<String> it = getCharacterStrings().iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                if (it.hasNext()) {
                    sb.append(" / ");
                }
            }
            this.textCache = sb.toString();
        }
        return this.textCache;
    }

    public List<String> getCharacterStrings() {
        if (this.characterStringsCache == null) {
            List<byte[]> extents = getExtents();
            ArrayList arrayList = new ArrayList(extents.size());
            Iterator<byte[]> it = extents.iterator();
            while (it.hasNext()) {
                try {
                    arrayList.add(new String(it.next(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new AssertionError(e);
                }
            }
            this.characterStringsCache = Collections.unmodifiableList(arrayList);
        }
        return this.characterStringsCache;
    }

    public List<byte[]> getExtents() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            byte[] bArr = this.blob;
            if (i >= bArr.length) {
                return arrayList;
            }
            int i2 = bArr[i] & UByte.MAX_VALUE;
            int i3 = i + 1;
            int i4 = i2 + i3;
            arrayList.add(Arrays.copyOfRange(bArr, i3, i4));
            i = i4;
        }
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(this.blob);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.TXT;
    }

    public String toString() {
        return PunctuationConst.DOUBLE_QUOTES + getText() + PunctuationConst.DOUBLE_QUOTES;
    }
}
