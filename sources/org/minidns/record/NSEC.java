package org.minidns.record;

import com.github.houbb.heaven.constant.CharConst;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class NSEC extends Data {
    private static final Logger LOGGER = Logger.getLogger(NSEC.class.getName());
    public final DnsName next;
    private final byte[] typeBitmap;
    public final List<Record.TYPE> types;

    public static NSEC parse(DataInputStream dataInputStream, byte[] bArr, int i) throws IOException {
        DnsName dnsName = DnsName.parse(dataInputStream, bArr);
        int size = i - dnsName.size();
        byte[] bArr2 = new byte[size];
        if (dataInputStream.read(bArr2) != size) {
            throw new IOException();
        }
        return new NSEC(dnsName, readTypeBitMap(bArr2));
    }

    public NSEC(String str, List<Record.TYPE> list) {
        this(DnsName.from(str), list);
    }

    public NSEC(String str, Record.TYPE... typeArr) {
        this(DnsName.from(str), (List<Record.TYPE>) Arrays.asList(typeArr));
    }

    public NSEC(DnsName dnsName, List<Record.TYPE> list) {
        this.next = dnsName;
        this.types = Collections.unmodifiableList(list);
        this.typeBitmap = createTypeBitMap(list);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.NSEC;
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        this.next.writeToStream(dataOutputStream);
        dataOutputStream.write(this.typeBitmap);
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder().append((CharSequence) this.next).append(CharConst.DOT);
        Iterator<Record.TYPE> it = this.types.iterator();
        while (it.hasNext()) {
            sbAppend.append(' ').append(it.next());
        }
        return sbAppend.toString();
    }

    static byte[] createTypeBitMap(List<Record.TYPE> list) {
        ArrayList<Integer> arrayList = new ArrayList(list.size());
        Iterator<Record.TYPE> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getValue()));
        }
        Collections.sort(arrayList);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            byte[] bArr = null;
            int iIntValue = -1;
            for (Integer num : arrayList) {
                if (iIntValue == -1 || (num.intValue() >> 8) != iIntValue) {
                    if (iIntValue != -1) {
                        writeOutBlock(bArr, dataOutputStream);
                    }
                    iIntValue = num.intValue() >> 8;
                    dataOutputStream.writeByte(iIntValue);
                    bArr = new byte[32];
                }
                int iIntValue2 = (num.intValue() >> 3) % 32;
                bArr[iIntValue2] = (byte) ((128 >> (num.intValue() % 8)) | bArr[iIntValue2]);
            }
            if (iIntValue != -1) {
                writeOutBlock(bArr, dataOutputStream);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeOutBlock(byte[] bArr, DataOutputStream dataOutputStream) throws IOException {
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (bArr[i2] != 0) {
                i = i2 + 1;
            }
        }
        dataOutputStream.writeByte(i);
        for (int i3 = 0; i3 < i; i3++) {
            dataOutputStream.writeByte(bArr[i3]);
        }
    }

    static List<Record.TYPE> readTypeBitMap(byte[] bArr) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (bArr.length > i) {
            int unsignedByte = dataInputStream.readUnsignedByte();
            int unsignedByte2 = dataInputStream.readUnsignedByte();
            for (int i2 = 0; i2 < unsignedByte2; i2++) {
                int unsignedByte3 = dataInputStream.readUnsignedByte();
                for (int i3 = 0; i3 < 8; i3++) {
                    if (((unsignedByte3 >> i3) & 1) > 0) {
                        int i4 = (unsignedByte << 8) + (i2 * 8) + (7 - i3);
                        Record.TYPE type = Record.TYPE.getType(i4);
                        if (type == Record.TYPE.UNKNOWN) {
                            LOGGER.warning("Skipping unknown type in type bitmap: " + i4);
                        } else {
                            arrayList.add(type);
                        }
                    }
                }
            }
            i += unsignedByte2 + 2;
        }
        return arrayList;
    }
}
