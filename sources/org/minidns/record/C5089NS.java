package org.minidns.record;

import java.io.DataInputStream;
import java.io.IOException;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Record;

/* JADX INFO: renamed from: org.minidns.record.NS */
/* JADX INFO: loaded from: classes4.dex */
public class C5089NS extends RRWithTarget {
    public static C5089NS parse(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        return new C5089NS(DnsName.parse(dataInputStream, bArr));
    }

    public C5089NS(DnsName dnsName) {
        super(dnsName);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.NS;
    }
}
