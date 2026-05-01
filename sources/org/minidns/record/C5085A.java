package org.minidns.record;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import kotlin.UByte;
import org.minidns.record.Record;
import org.minidns.util.InetAddressUtil;

/* JADX INFO: renamed from: org.minidns.record.A */
/* JADX INFO: loaded from: classes4.dex */
public class C5085A extends InternetAddressRR<Inet4Address> {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.A;
    }

    public C5085A(Inet4Address inet4Address) {
        super(inet4Address);
    }

    public C5085A(int i, int i2, int i3, int i4) {
        super(new byte[]{(byte) i, (byte) i2, (byte) i3, (byte) i4});
        if (i < 0 || i > 255 || i2 < 0 || i2 > 255 || i3 < 0 || i3 > 255 || i4 < 0 || i4 > 255) {
            throw new IllegalArgumentException();
        }
    }

    public C5085A(byte[] bArr) {
        super(bArr);
        if (bArr.length != 4) {
            throw new IllegalArgumentException("IPv4 address in A record is always 4 byte");
        }
    }

    public C5085A(CharSequence charSequence) {
        this(InetAddressUtil.ipv4From(charSequence));
    }

    public static C5085A parse(DataInputStream dataInputStream) throws IOException {
        byte[] bArr = new byte[4];
        dataInputStream.readFully(bArr);
        return new C5085A(bArr);
    }

    public String toString() {
        return Integer.toString(this.f2050ip[0] & UByte.MAX_VALUE) + "." + Integer.toString(this.f2050ip[1] & UByte.MAX_VALUE) + "." + Integer.toString(this.f2050ip[2] & UByte.MAX_VALUE) + "." + Integer.toString(this.f2050ip[3] & UByte.MAX_VALUE);
    }
}
