package org.minidns.record;

import java.io.DataOutputStream;
import java.io.IOException;
import org.minidns.dnsname.DnsName;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RRWithTarget extends Data {

    @Deprecated
    public final DnsName name;
    public final DnsName target;

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        this.target.writeToStream(dataOutputStream);
    }

    protected RRWithTarget(DnsName dnsName) {
        this.target = dnsName;
        this.name = dnsName;
    }

    public String toString() {
        return ((Object) this.target) + ".";
    }

    public final DnsName getTarget() {
        return this.target;
    }
}
