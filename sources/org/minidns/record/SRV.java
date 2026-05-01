package org.minidns.record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class SRV extends Data implements Comparable<SRV> {

    @Deprecated
    public final DnsName name;
    public final int port;
    public final int priority;
    public final DnsName target;
    public final int weight;

    public static SRV parse(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        return new SRV(dataInputStream.readUnsignedShort(), dataInputStream.readUnsignedShort(), dataInputStream.readUnsignedShort(), DnsName.parse(dataInputStream, bArr));
    }

    public SRV(int i, int i2, int i3, String str) {
        this(i, i2, i3, DnsName.from(str));
    }

    public SRV(int i, int i2, int i3, DnsName dnsName) {
        this.priority = i;
        this.weight = i2;
        this.port = i3;
        this.target = dnsName;
        this.name = dnsName;
    }

    public boolean isServiceAvailable() {
        return !this.target.isRootLabel();
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.priority);
        dataOutputStream.writeShort(this.weight);
        dataOutputStream.writeShort(this.port);
        this.target.writeToStream(dataOutputStream);
    }

    public String toString() {
        return this.priority + " " + this.weight + " " + this.port + " " + ((Object) this.target) + ".";
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.SRV;
    }

    @Override // java.lang.Comparable
    public int compareTo(SRV srv) {
        int i = srv.priority - this.priority;
        return i == 0 ? this.weight - srv.weight : i;
    }
}
