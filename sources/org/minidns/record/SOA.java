package org.minidns.record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class SOA extends Data {
    public final int expire;
    public final long minimum;
    public final DnsName mname;
    public final int refresh;
    public final int retry;
    public final DnsName rname;
    public final long serial;

    public static SOA parse(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        return new SOA(DnsName.parse(dataInputStream, bArr), DnsName.parse(dataInputStream, bArr), ((long) dataInputStream.readInt()) & 4294967295L, dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readInt(), ((long) dataInputStream.readInt()) & 4294967295L);
    }

    public SOA(String str, String str2, long j, int i, int i2, int i3, long j2) {
        this(DnsName.from(str), DnsName.from(str2), j, i, i2, i3, j2);
    }

    public SOA(DnsName dnsName, DnsName dnsName2, long j, int i, int i2, int i3, long j2) {
        this.mname = dnsName;
        this.rname = dnsName2;
        this.serial = j;
        this.refresh = i;
        this.retry = i2;
        this.expire = i3;
        this.minimum = j2;
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.SOA;
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        this.mname.writeToStream(dataOutputStream);
        this.rname.writeToStream(dataOutputStream);
        dataOutputStream.writeInt((int) this.serial);
        dataOutputStream.writeInt(this.refresh);
        dataOutputStream.writeInt(this.retry);
        dataOutputStream.writeInt(this.expire);
        dataOutputStream.writeInt((int) this.minimum);
    }

    public String toString() {
        return ((CharSequence) this.mname) + ". " + ((CharSequence) this.rname) + ". " + this.serial + ' ' + this.refresh + ' ' + this.retry + ' ' + this.expire + ' ' + this.minimum;
    }
}
