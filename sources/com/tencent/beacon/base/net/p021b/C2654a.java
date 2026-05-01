package com.tencent.beacon.base.net.p021b;

import com.tencent.beacon.base.net.p021b.p022a.C2659b;
import com.tencent.beacon.base.net.p021b.p022a.p024b.C2661b;
import com.tencent.beacon.base.net.p021b.p022a.p024b.C2662c;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.base.util.C2698f;
import com.tencent.beacon.p015a.p017b.C2624j;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a */
/* JADX INFO: compiled from: CompressUtil.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2654a {

    /* JADX INFO: renamed from: a */
    private static final C2698f<C2662c> f1141a = new C2698f<>(5);

    /* JADX INFO: renamed from: a */
    public static byte[] m1183a(int i, byte[] bArr) throws Exception {
        return i == 1 ? m1190f(bArr) : i == 2 ? m1187c(bArr) : i == 3 ? m1184a(bArr) : m1187c(bArr);
    }

    /* JADX INFO: renamed from: b */
    public static byte[] m1185b(int i, byte[] bArr) throws Exception {
        return i == 1 ? m1189e(bArr) : i == 2 ? m1188d(bArr) : i == 3 ? m1186b(bArr) : m1188d(bArr);
    }

    /* JADX INFO: renamed from: c */
    private static byte[] m1187c(byte[] bArr) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(bArr);
        gZIPOutputStream.finish();
        gZIPOutputStream.close();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return byteArray;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009b  */
    /* JADX WARN: Type inference failed for: r1v4, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX INFO: renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static byte[] m1188d(byte[] r10) throws java.lang.Exception {
        /*
            r0 = 0
            r1 = 0
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4d
            r2.<init>(r10)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4d
            java.util.zip.GZIPInputStream r3 = new java.util.zip.GZIPInputStream     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r4]     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            r6.<init>()     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
        L15:
            int r7 = r3.read(r5, r0, r4)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L36
            r8 = -1
            if (r7 == r8) goto L20
            r6.write(r5, r0, r7)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L36
            goto L15
        L20:
            byte[] r1 = r6.toByteArray()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L36
            r6.flush()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L36
            r6.close()
            r3.close()
            r2.close()
            goto L9f
        L32:
            r10 = move-exception
            r1 = r6
            goto La0
        L36:
            r4 = move-exception
            r5 = r4
            r4 = r1
            r1 = r6
            goto L53
        L3b:
            r10 = move-exception
            goto La0
        L3e:
            r4 = move-exception
            r5 = r4
            r4 = r1
            goto L53
        L42:
            r10 = move-exception
            r3 = r1
            goto La0
        L45:
            r3 = move-exception
            r4 = r3
            r3 = r1
            goto L51
        L49:
            r10 = move-exception
            r2 = r1
            r3 = r2
            goto La0
        L4d:
            r2 = move-exception
            r4 = r2
            r2 = r1
            r3 = r2
        L51:
            r5 = r4
            r4 = r3
        L53:
            com.tencent.beacon.a.b.j r6 = com.tencent.beacon.p015a.p017b.C2624j.m1031e()     // Catch: java.lang.Throwable -> L3b
            java.lang.String r7 = "509"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3b
            r8.<init>()     // Catch: java.lang.Throwable -> L3b
            java.lang.String r9 = "unzipData length: "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch: java.lang.Throwable -> L3b
            int r10 = r10.length     // Catch: java.lang.Throwable -> L3b
            java.lang.StringBuilder r10 = r8.append(r10)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L3b
            r6.m1024a(r7, r10, r5)     // Catch: java.lang.Throwable -> L3b
            com.tencent.beacon.base.util.C2695c.m1465a(r5)     // Catch: java.lang.Throwable -> L3b
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3b
            r10.<init>()     // Catch: java.lang.Throwable -> L3b
            java.lang.String r6 = "unGzip error "
            java.lang.StringBuilder r10 = r10.append(r6)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L3b
            java.lang.StringBuilder r10 = r10.append(r5)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L3b
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L3b
            com.tencent.beacon.base.util.C2695c.m1468b(r10, r0)     // Catch: java.lang.Throwable -> L3b
            if (r1 == 0) goto L94
            r1.close()
        L94:
            if (r3 == 0) goto L99
            r3.close()
        L99:
            if (r2 == 0) goto L9e
            r2.close()
        L9e:
            r1 = r4
        L9f:
            return r1
        La0:
            if (r1 == 0) goto La5
            r1.close()
        La5:
            if (r3 == 0) goto Laa
            r3.close()
        Laa:
            if (r2 == 0) goto Laf
            r2.close()
        Laf:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.base.net.p021b.C2654a.m1188d(byte[]):byte[]");
    }

    /* JADX INFO: renamed from: e */
    private static byte[] m1189e(byte[] bArr) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
        byte[] byteArray = null;
        while (zipInputStream.getNextEntry() != null) {
            byte[] bArr2 = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int i = zipInputStream.read(bArr2, 0, 1024);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr2, 0, i);
                }
            }
            byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        }
        zipInputStream.close();
        byteArrayInputStream.close();
        return byteArray;
    }

    /* JADX INFO: renamed from: f */
    private static byte[] m1190f(byte[] bArr) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        ZipEntry zipEntry = new ZipEntry("zip");
        zipEntry.setSize(bArr.length);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(bArr);
        zipOutputStream.closeEntry();
        zipOutputStream.close();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return byteArray;
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m1184a(byte[] bArr) throws Exception {
        C2659b c2659b = new C2659b();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        try {
            try {
                C2662c c2662cMo1478a = f1141a.mo1478a();
                if (c2662cMo1478a == null) {
                    c2662cMo1478a = new C2662c();
                }
                C2662c c2662c = c2662cMo1478a;
                c2662c.m1263g(c2659b.f1181k);
                c2662c.m1265h(c2659b.f1173c);
                c2662c.m1268j(c2659b.f1178h);
                c2662c.m1267i(c2659b.f1182l);
                c2662c.m1257c(c2659b.f1175e, c2659b.f1176f, c2659b.f1177g);
                c2662c.m1249a(false);
                c2662c.m1254b(bufferedOutputStream);
                long length = bArr.length;
                for (int i = 0; i < 8; i++) {
                    bufferedOutputStream.write(((int) (length >>> (i * 8))) & 255);
                }
                c2662c.m1247a(bufferedInputStream, bufferedOutputStream, -1L, -1L, null);
                bufferedOutputStream.flush();
                f1141a.mo1479a(c2662c);
            } catch (Exception e) {
                String str = "lzma zip error msg: " + e.getMessage();
                C2695c.m1468b(str, new Object[0]);
                C2624j.m1031e().m1023a("521", str);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
            byteArrayInputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            bufferedOutputStream.close();
            bufferedInputStream.close();
            byteArrayInputStream.close();
            byteArrayOutputStream.close();
            throw th;
        }
    }

    /* JADX INFO: renamed from: b */
    public static byte[] m1186b(byte[] bArr) throws Exception {
        byte[] bArr2;
        new C2659b();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        try {
            try {
                bArr2 = new byte[5];
            } catch (Exception e) {
                String str = "lzma unZip error msg: " + e.getMessage();
                C2695c.m1468b(str, new Object[0]);
                C2624j.m1031e().m1023a("521", str);
            }
            if (byteArrayInputStream.read(bArr2, 0, 5) == 5) {
                C2661b c2661b = new C2661b();
                c2661b.m1229a(bArr2);
                long j = 0;
                for (int i = 0; i < 8; i++) {
                    int i2 = bufferedInputStream.read();
                    if (i2 < 0) {
                        throw new Exception("Can't read stream size");
                    }
                    j |= ((long) i2) << (i * 8);
                }
                if (c2661b.m1228a(bufferedInputStream, bufferedOutputStream, j)) {
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    bufferedInputStream.close();
                    byteArrayInputStream.close();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
                throw new Exception("Error in data stream");
            }
            throw new Exception("input file is too short");
        } catch (Throwable th) {
            bufferedOutputStream.close();
            bufferedInputStream.close();
            byteArrayInputStream.close();
            byteArrayOutputStream.close();
            throw th;
        }
    }
}
