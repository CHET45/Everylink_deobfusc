package org.minidns.record;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: classes4.dex */
public abstract class InternetAddressRR<IA extends InetAddress> extends Data {
    private transient IA inetAddress;

    /* JADX INFO: renamed from: ip */
    protected final byte[] f2050ip;

    protected InternetAddressRR(byte[] bArr) {
        this.f2050ip = bArr;
    }

    protected InternetAddressRR(IA ia) {
        this(ia.getAddress());
        this.inetAddress = ia;
    }

    @Override // org.minidns.record.Data
    public final void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(this.f2050ip);
    }

    public final byte[] getIp() {
        return (byte[]) this.f2050ip.clone();
    }

    public final IA getInetAddress() {
        if (this.inetAddress == null) {
            try {
                this.inetAddress = (IA) InetAddress.getByAddress(this.f2050ip);
            } catch (UnknownHostException e) {
                throw new IllegalStateException(e);
            }
        }
        return this.inetAddress;
    }

    public static InternetAddressRR<? extends InetAddress> from(InetAddress inetAddress) {
        if (inetAddress instanceof Inet4Address) {
            return new C5085A((Inet4Address) inetAddress);
        }
        return new AAAA((Inet6Address) inetAddress);
    }
}
