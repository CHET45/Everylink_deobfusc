package org.minidns.record;

import java.io.DataInputStream;
import java.io.IOException;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class DNAME extends RRWithTarget {
    public static DNAME parse(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        return new DNAME(DnsName.parse(dataInputStream, bArr));
    }

    public DNAME(String str) {
        this(DnsName.from(str));
    }

    public DNAME(DnsName dnsName) {
        super(dnsName);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.DNAME;
    }
}
