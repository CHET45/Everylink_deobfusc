package com.tencent.beacon.base.net.adapter;

import android.text.TextUtils;
import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.NetException;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.HttpRequestEntity;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.base.net.p020a.C2643d;
import com.tencent.beacon.base.net.p020a.C2644e;
import com.tencent.beacon.base.net.p020a.InterfaceC2642c;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.beacon.pack.C2756b;
import com.tencent.beacon.pack.SocketRequestPackage;
import com.tencent.beacon.pack.SocketResponsePackage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.adapter.h */
/* JADX INFO: compiled from: SocketAdapter.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2652h extends AbstractNetAdapter {

    /* JADX INFO: renamed from: a */
    private InterfaceC2642c<byte[], SocketResponsePackage> f1136a = new C2644e();

    /* JADX INFO: renamed from: b */
    private InterfaceC2642c<JceRequestEntity, SocketRequestPackage> f1137b = new C2643d();

    private C2652h() {
    }

    /* JADX INFO: renamed from: a */
    public static AbstractNetAdapter m1176a() {
        return new C2652h();
    }

    @Override // com.tencent.beacon.base.net.adapter.AbstractNetAdapter
    public void request(HttpRequestEntity httpRequestEntity, Callback<BResponse> callback) {
    }

    @Override // com.tencent.beacon.base.net.adapter.AbstractNetAdapter
    public void request(JceRequestEntity jceRequestEntity, Callback<byte[]> callback) {
        byte[] byteArray;
        String domain = jceRequestEntity.getDomain();
        if (TextUtils.isEmpty(domain)) {
            return;
        }
        String strName = jceRequestEntity.getType().name();
        Socket socket = null;
        try {
            try {
                try {
                    try {
                        try {
                            byteArray = this.f1137b.mo1161a(jceRequestEntity).toByteArray();
                        } catch (Throwable th) {
                            C2695c.m1468b("SocketAdapter socket request error: %s", th.getMessage());
                            C2695c.m1465a(th);
                            callback.onFailure(new C2684d(strName, "449", -1, " unknown request error!", th));
                            if (0 == 0 || socket.isClosed()) {
                                return;
                            } else {
                                socket.close();
                            }
                        }
                    } catch (SocketTimeoutException e) {
                        callback.onFailure(new C2684d(strName, "401", -1, " request time more than 30s", e));
                        if (0 == 0 || socket.isClosed()) {
                            return;
                        } else {
                            socket.close();
                        }
                    }
                } catch (ConnectException e2) {
                    callback.onFailure(new C2684d(strName, "401", -1, " connect time more than 30s", e2));
                    if (0 == 0 || socket.isClosed()) {
                        return;
                    } else {
                        socket.close();
                    }
                }
                if (byteArray != null && byteArray.length > 65535) {
                    callback.onFailure(new C2684d(strName, "406", -1, "content length is too long:" + byteArray.length));
                    return;
                }
                Socket socketM1177a = m1177a(domain, jceRequestEntity.getPort());
                C2695c.m1461a("SocketAdapter", 0, "send data size: " + jceRequestEntity.getContent().length, new Object[0]);
                m1179a(socketM1177a, this.f1137b.mo1161a(jceRequestEntity));
                byte[] bArrM1180a = m1180a(socketM1177a, jceRequestEntity.getType() == RequestType.EVENT);
                if (bArrM1180a != null && bArrM1180a.length > 0) {
                    C2695c.m1461a("SocketAdapter", 1, "receivedData: " + bArrM1180a.length, new Object[0]);
                    SocketResponsePackage socketResponsePackageMo1161a = this.f1136a.mo1161a(bArrM1180a);
                    if (socketResponsePackageMo1161a == null) {
                        callback.onFailure(new C2684d(strName, "402", -1, "responsePackage == null"));
                        if (socketM1177a == null || socketM1177a.isClosed()) {
                            return;
                        }
                        try {
                            socketM1177a.close();
                            return;
                        } catch (IOException e3) {
                            C2695c.m1465a(e3);
                            return;
                        }
                    }
                    C2695c.m1461a("SocketAdapter", 2, "socket response code: %s, header: %s, msg: %s", Integer.valueOf(socketResponsePackageMo1161a.statusCode), socketResponsePackageMo1161a.header, socketResponsePackageMo1161a.msg);
                    if (socketResponsePackageMo1161a.statusCode == 200) {
                        m1178a(callback, strName, socketResponsePackageMo1161a);
                    } else {
                        callback.onFailure(new C2684d(strName, "402", socketResponsePackageMo1161a.statusCode, "responsePackage msg: " + socketResponsePackageMo1161a.msg));
                    }
                    if (socketM1177a == null || socketM1177a.isClosed()) {
                        return;
                    }
                    socketM1177a.close();
                    return;
                }
                callback.onFailure(new C2684d(strName, "402", -1, "receiveData == null"));
                if (socketM1177a == null || socketM1177a.isClosed()) {
                    return;
                }
                try {
                    socketM1177a.close();
                } catch (IOException e4) {
                    C2695c.m1465a(e4);
                }
            } catch (Throwable th2) {
                if (0 != 0 && !socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e5) {
                        C2695c.m1465a(e5);
                    }
                }
                throw th2;
            }
        } catch (IOException e6) {
            C2695c.m1465a(e6);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1178a(Callback<byte[]> callback, String str, SocketResponsePackage socketResponsePackage) throws NetException {
        String str2 = socketResponsePackage.msg;
        if (str2 == null || !str2.equals("decrypt Data fail!")) {
            callback.onResponse(socketResponsePackage.body);
        } else {
            callback.onFailure(new C2684d(str, "405", socketResponsePackage.statusCode, "server encrypt-status error!"));
        }
    }

    /* JADX INFO: renamed from: a */
    private byte[] m1180a(Socket socket, boolean z) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream;
        InputStream inputStream2 = null;
        try {
            inputStream = socket.getInputStream();
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Throwable th) {
                th = th;
                byteArrayOutputStream = null;
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
        }
        try {
            byte[] bArr = new byte[2048];
            while (true) {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
            byteArrayOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (!z) {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteArray.length - 4);
                byteBufferAllocate.put(byteArray, 2, byteArray.length - 4);
                byteArray = byteBufferAllocate.array();
            }
            byteArrayOutputStream.close();
            inputStream.close();
            C2694b.m1438a(inputStream, byteArrayOutputStream);
            return byteArray;
        } catch (Throwable th3) {
            th = th3;
            inputStream2 = inputStream;
            try {
                C2695c.m1465a(th);
                C2694b.m1438a(inputStream2, byteArrayOutputStream);
                throw th;
            } catch (Throwable th4) {
                C2694b.m1438a(inputStream2, byteArrayOutputStream);
                throw th4;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1179a(Socket socket, SocketRequestPackage socketRequestPackage) throws IOException {
        C2756b c2756b = new C2756b();
        socketRequestPackage.writeTo(c2756b);
        OutputStream outputStream = socket.getOutputStream();
        byte[] bArrM1842b = c2756b.m1842b();
        outputStream.write(m1181a(bArrM1842b, bArrM1842b.length));
        outputStream.flush();
    }

    /* JADX INFO: renamed from: a */
    private byte[] m1181a(byte[] bArr, int i) {
        int i2 = i + 4;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i2);
        byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
        byteBufferAllocate.putShort((short) (i2 & 65535));
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(AbstractJceStruct.SIMPLE_LIST);
        byteBufferAllocate.put((byte) 10);
        return byteBufferAllocate.array();
    }

    /* JADX INFO: renamed from: a */
    private Socket m1177a(String str, int i) throws IOException {
        C2695c.m1463a("SocketAdapter", "create socket domain: %s, port: %d", str, Integer.valueOf(i));
        Socket socket = new Socket(InetAddress.getByName(str).getHostAddress(), i);
        socket.setSoTimeout(30000);
        return socket;
    }
}
