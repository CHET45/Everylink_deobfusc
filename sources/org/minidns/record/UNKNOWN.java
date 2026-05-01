package org.minidns.record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public final class UNKNOWN extends Data {
    private final byte[] data;
    private final Record.TYPE type;

    private UNKNOWN(DataInputStream dataInputStream, int i, Record.TYPE type) throws IOException {
        this.type = type;
        byte[] bArr = new byte[i];
        this.data = bArr;
        dataInputStream.readFully(bArr);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return this.type;
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(this.data);
    }

    public static UNKNOWN parse(DataInputStream dataInputStream, int i, Record.TYPE type) throws IOException {
        return new UNKNOWN(dataInputStream, i, type);
    }
}
