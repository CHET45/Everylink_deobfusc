package org.minidns.record;

import java.io.DataInputStream;
import java.io.IOException;
import org.minidns.constants.DnssecConstants;
import org.minidns.record.DelegatingDnssecRR;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class DLV extends DelegatingDnssecRR {
    public static DLV parse(DataInputStream dataInputStream, int i) throws IOException {
        DelegatingDnssecRR.SharedData sharedData = DelegatingDnssecRR.parseSharedData(dataInputStream, i);
        return new DLV(sharedData.keyTag, sharedData.algorithm, sharedData.digestType, sharedData.digest);
    }

    public DLV(int i, byte b, byte b2, byte[] bArr) {
        super(i, b, b2, bArr);
    }

    public DLV(int i, DnssecConstants.SignatureAlgorithm signatureAlgorithm, DnssecConstants.DigestAlgorithm digestAlgorithm, byte[] bArr) {
        super(i, signatureAlgorithm, digestAlgorithm, bArr);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.DLV;
    }
}
