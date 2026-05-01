package org.minidns.record;

import java.io.DataInputStream;
import java.io.IOException;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class PTR extends RRWithTarget {
    public static PTR parse(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        return new PTR(DnsName.parse(dataInputStream, bArr));
    }

    PTR(String str) {
        this(DnsName.from(str));
    }

    PTR(DnsName dnsName) {
        super(dnsName);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.PTR;
    }
}
