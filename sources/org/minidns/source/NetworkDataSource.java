package org.minidns.source;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.minidns.MiniDnsException;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsqueryresult.DnsQueryResult;
import org.minidns.dnsqueryresult.StandardDnsQueryResult;
import org.minidns.source.AbstractDnsDataSource;
import org.minidns.util.MultipleIoException;

/* JADX INFO: loaded from: classes4.dex */
public class NetworkDataSource extends AbstractDnsDataSource {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final Logger LOGGER = Logger.getLogger(NetworkDataSource.class.getName());

    @Override // org.minidns.source.AbstractDnsDataSource, org.minidns.source.DnsDataSource
    public StandardDnsQueryResult query(DnsMessage dnsMessage, InetAddress inetAddress, int i) throws Throwable {
        AbstractDnsDataSource.QueryMode queryMode = getQueryMode();
        int i2 = C50911.$SwitchMap$org$minidns$source$AbstractDnsDataSource$QueryMode[queryMode.ordinal()];
        boolean z = true;
        if (i2 != 1 && i2 != 2) {
            if (i2 != 3) {
                throw new IllegalStateException("Unsupported query mode: " + queryMode);
            }
            z = false;
        }
        ArrayList arrayList = new ArrayList(2);
        DnsMessage dnsMessageQueryUdp = null;
        if (z) {
            try {
                dnsMessageQueryUdp = queryUdp(dnsMessage, inetAddress, i);
            } catch (IOException e) {
                arrayList.add(e);
            }
            DnsMessage dnsMessage2 = dnsMessageQueryUdp;
            if (dnsMessage2 != null && !dnsMessage2.truncated) {
                return new StandardDnsQueryResult(inetAddress, i, DnsQueryResult.QueryMethod.udp, dnsMessage, dnsMessage2);
            }
            LOGGER.log(Level.FINE, "Fallback to TCP because {0}", new Object[]{dnsMessage2 != null ? "response is truncated" : arrayList.get(0)});
            dnsMessageQueryUdp = dnsMessage2;
        }
        try {
            dnsMessageQueryUdp = queryTcp(dnsMessage, inetAddress, i);
        } catch (IOException e2) {
            arrayList.add(e2);
            MultipleIoException.throwIfRequired(arrayList);
        }
        return new StandardDnsQueryResult(inetAddress, i, DnsQueryResult.QueryMethod.tcp, dnsMessage, dnsMessageQueryUdp);
    }

    /* JADX INFO: renamed from: org.minidns.source.NetworkDataSource$1 */
    static /* synthetic */ class C50911 {
        static final /* synthetic */ int[] $SwitchMap$org$minidns$source$AbstractDnsDataSource$QueryMode;

        static {
            int[] iArr = new int[AbstractDnsDataSource.QueryMode.values().length];
            $SwitchMap$org$minidns$source$AbstractDnsDataSource$QueryMode = iArr;
            try {
                iArr[AbstractDnsDataSource.QueryMode.dontCare.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$minidns$source$AbstractDnsDataSource$QueryMode[AbstractDnsDataSource.QueryMode.udpTcp.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$minidns$source$AbstractDnsDataSource$QueryMode[AbstractDnsDataSource.QueryMode.tcp.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    protected DnsMessage queryUdp(DnsMessage dnsMessage, InetAddress inetAddress, int i) throws Throwable {
        DatagramSocket datagramSocketCreateDatagramSocket;
        DatagramPacket datagramPacketAsDatagram = dnsMessage.asDatagram(inetAddress, i);
        int i2 = this.udpPayloadSize;
        byte[] bArr = new byte[i2];
        try {
            datagramSocketCreateDatagramSocket = createDatagramSocket();
        } catch (Throwable th) {
            th = th;
            datagramSocketCreateDatagramSocket = null;
        }
        try {
            datagramSocketCreateDatagramSocket.setSoTimeout(this.timeout);
            datagramSocketCreateDatagramSocket.send(datagramPacketAsDatagram);
            DatagramPacket datagramPacket = new DatagramPacket(bArr, i2);
            datagramSocketCreateDatagramSocket.receive(datagramPacket);
            DnsMessage dnsMessage2 = new DnsMessage(datagramPacket.getData());
            if (dnsMessage2.f2046id != dnsMessage.f2046id) {
                throw new MiniDnsException.IdMismatch(dnsMessage, dnsMessage2);
            }
            if (datagramSocketCreateDatagramSocket != null) {
                datagramSocketCreateDatagramSocket.close();
            }
            return dnsMessage2;
        } catch (Throwable th2) {
            th = th2;
            if (datagramSocketCreateDatagramSocket != null) {
                datagramSocketCreateDatagramSocket.close();
            }
            throw th;
        }
    }

    protected DnsMessage queryTcp(DnsMessage dnsMessage, InetAddress inetAddress, int i) throws Throwable {
        Socket socketCreateSocket;
        try {
            socketCreateSocket = createSocket();
        } catch (Throwable th) {
            th = th;
            socketCreateSocket = null;
        }
        try {
            socketCreateSocket.connect(new InetSocketAddress(inetAddress, i), this.timeout);
            socketCreateSocket.setSoTimeout(this.timeout);
            DataOutputStream dataOutputStream = new DataOutputStream(socketCreateSocket.getOutputStream());
            dnsMessage.writeTo(dataOutputStream);
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socketCreateSocket.getInputStream());
            int unsignedShort = dataInputStream.readUnsignedShort();
            byte[] bArr = new byte[unsignedShort];
            for (int i2 = 0; i2 < unsignedShort; i2 += dataInputStream.read(bArr, i2, unsignedShort - i2)) {
            }
            DnsMessage dnsMessage2 = new DnsMessage(bArr);
            if (dnsMessage2.f2046id != dnsMessage.f2046id) {
                throw new MiniDnsException.IdMismatch(dnsMessage, dnsMessage2);
            }
            if (socketCreateSocket != null) {
                socketCreateSocket.close();
            }
            return dnsMessage2;
        } catch (Throwable th2) {
            th = th2;
            if (socketCreateSocket != null) {
                socketCreateSocket.close();
            }
            throw th;
        }
    }

    protected Socket createSocket() {
        return new Socket();
    }

    protected DatagramSocket createDatagramSocket() throws SocketException {
        return new DatagramSocket();
    }
}
