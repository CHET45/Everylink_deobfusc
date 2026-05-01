package org.minidns.record;

import java.io.DataInputStream;
import java.io.IOException;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class CNAME extends RRWithTarget {
    public static CNAME parse(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        return new CNAME(DnsName.parse(dataInputStream, bArr));
    }

    public CNAME(String str) {
        this(DnsName.from(str));
    }

    public CNAME(DnsName dnsName) {
        super(dnsName);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.CNAME;
    }
}
