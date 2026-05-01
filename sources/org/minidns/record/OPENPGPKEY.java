package org.minidns.record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.minidns.record.Record;
import org.minidns.util.Base64;

/* JADX INFO: loaded from: classes4.dex */
public class OPENPGPKEY extends Data {
    private final byte[] publicKeyPacket;
    private transient String publicKeyPacketBase64Cache;

    public static OPENPGPKEY parse(DataInputStream dataInputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        dataInputStream.readFully(bArr);
        return new OPENPGPKEY(bArr);
    }

    OPENPGPKEY(byte[] bArr) {
        this.publicKeyPacket = bArr;
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.OPENPGPKEY;
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(this.publicKeyPacket);
    }

    public String toString() {
        return getPublicKeyPacketBase64();
    }

    public String getPublicKeyPacketBase64() {
        if (this.publicKeyPacketBase64Cache == null) {
            this.publicKeyPacketBase64Cache = Base64.encodeToString(this.publicKeyPacket);
        }
        return this.publicKeyPacketBase64Cache;
    }

    public byte[] getPublicKeyPacket() {
        return (byte[]) this.publicKeyPacket.clone();
    }
}
