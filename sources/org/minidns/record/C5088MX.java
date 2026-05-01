package org.minidns.record;

import com.github.houbb.heaven.constant.CharConst;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Record;

/* JADX INFO: renamed from: org.minidns.record.MX */
/* JADX INFO: loaded from: classes4.dex */
public class C5088MX extends Data {

    @Deprecated
    public final DnsName name;
    public final int priority;
    public final DnsName target;

    public static C5088MX parse(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        return new C5088MX(dataInputStream.readUnsignedShort(), DnsName.parse(dataInputStream, bArr));
    }

    public C5088MX(int i, String str) {
        this(i, DnsName.from(str));
    }

    public C5088MX(int i, DnsName dnsName) {
        this.priority = i;
        this.target = dnsName;
        this.name = dnsName;
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.priority);
        this.target.writeToStream(dataOutputStream);
    }

    public String toString() {
        return this.priority + " " + ((Object) this.target) + CharConst.DOT;
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.MX;
    }
}
