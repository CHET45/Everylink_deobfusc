package org.minidns.record;

import java.io.DataInputStream;
import java.io.IOException;
import org.minidns.constants.DnssecConstants;
import org.minidns.record.DelegatingDnssecRR;
import org.minidns.record.Record;

/* JADX INFO: renamed from: org.minidns.record.DS */
/* JADX INFO: loaded from: classes4.dex */
public class C5086DS extends DelegatingDnssecRR {
    public static C5086DS parse(DataInputStream dataInputStream, int i) throws IOException {
        DelegatingDnssecRR.SharedData sharedData = DelegatingDnssecRR.parseSharedData(dataInputStream, i);
        return new C5086DS(sharedData.keyTag, sharedData.algorithm, sharedData.digestType, sharedData.digest);
    }

    public C5086DS(int i, byte b, byte b2, byte[] bArr) {
        super(i, b, b2, bArr);
    }

    public C5086DS(int i, DnssecConstants.SignatureAlgorithm signatureAlgorithm, byte b, byte[] bArr) {
        super(i, signatureAlgorithm, b, bArr);
    }

    public C5086DS(int i, DnssecConstants.SignatureAlgorithm signatureAlgorithm, DnssecConstants.DigestAlgorithm digestAlgorithm, byte[] bArr) {
        super(i, signatureAlgorithm, digestAlgorithm, bArr);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.DS;
    }
}
