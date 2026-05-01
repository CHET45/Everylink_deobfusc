package org.videolan.libvlc.util;

import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import com.github.houbb.heaven.constant.FileOptionConst;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.constant.SystemConst;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes5.dex */
public class VLCUtil {
    private static final String[] CPU_archs = {"*Pre-v4", "*v4", "*v4T", "v5T", "v5TE", "v5TEJ", "v6", "v6KZ", "v6T2", "v6K", "v7", "*v6-M", "*v6S-M", "*v7E-M", "*v8"};
    private static final int ELF_HEADER_SIZE = 52;
    private static final int EM_386 = 3;
    private static final int EM_AARCH64 = 183;
    private static final int EM_ARM = 40;
    private static final int EM_MIPS = 8;
    private static final int EM_X86_64 = 62;
    private static final int SECTION_HEADER_SIZE = 40;
    private static final int SHT_ARM_ATTRIBUTES = 1879048195;
    public static final String TAG = "VLC/LibVLC/Util";
    private static final String URI_AUTHORIZED_CHARS = "'()*";
    private static String errorMsg = null;
    private static boolean isCompatible = false;
    private static MachineSpecs machineSpecs;

    public static class MachineSpecs {
        public float bogoMIPS;
        public float frequency;
        public boolean hasArmV6;
        public boolean hasArmV7;
        public boolean hasFpu;
        public boolean hasMips;
        public boolean hasNeon;
        public boolean hasX86;
        public boolean is64bits;
        public int processors;
    }

    public static String getErrorMsg() {
        return errorMsg;
    }

    public static String[] getABIList21() {
        String[] strArr = Build.SUPPORTED_ABIS;
        return (strArr == null || strArr.length == 0) ? getABIList() : strArr;
    }

    public static String[] getABIList() {
        return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:(23:29|(11:31|(1:37)(1:36)|38|(1:44)(1:43)|45|(1:47)(1:48)|49|(1:51)(1:(1:54))|55|(1:57)(1:58)|59)|212|62|210|63|64|(2:65|(3:208|66|(11:68|(2:74|(1:76)(2:77|(2:83|(1:85)(2:86|(2:91|(1:93))(1:90)))(1:79)))(1:70)|94|(1:98)|99|(1:105)|106|(1:108)|109|(1:230)(7:227|113|114|205|115|116|231)|228)(1:225)))|128|(1:130)(1:131)|132|(7:(2:140|(1:143))(1:(1:139)(1:138))|(1:(1:149))(1:146)|150|(1:155)|156|(2:158|(2:162|(1:165))(1:161))(1:166)|(1:169))|170|207|171|216|172|214|173|(2:175|176)(1:177)|178|190|191)(1:60)|207|171|216|172|214|173|(0)(0)|178|190|191) */
    /* JADX WARN: Can't wrap try/catch for region: R(16:7|(2:8|(3:10|(2:12|220)(4:13|(1:15)(2:17|(2:19|222)(2:20|(2:22|223)(2:23|(1:25)(1:224))))|16|221)|26)(1:219))|27|(11:(23:29|(11:31|(1:37)(1:36)|38|(1:44)(1:43)|45|(1:47)(1:48)|49|(1:51)(1:(1:54))|55|(1:57)(1:58)|59)|212|62|210|63|64|(2:65|(3:208|66|(11:68|(2:74|(1:76)(2:77|(2:83|(1:85)(2:86|(2:91|(1:93))(1:90)))(1:79)))(1:70)|94|(1:98)|99|(1:105)|106|(1:108)|109|(1:230)(7:227|113|114|205|115|116|231)|228)(1:225)))|128|(1:130)(1:131)|132|(7:(2:140|(1:143))(1:(1:139)(1:138))|(1:(1:149))(1:146)|150|(1:155)|156|(2:158|(2:162|(1:165))(1:161))(1:166)|(1:169))|170|207|171|216|172|214|173|(2:175|176)(1:177)|178|190|191)(1:60)|207|171|216|172|214|173|(0)(0)|178|190|191)|61|212|62|210|63|64|(2:65|(4:208|66|(0)(0)|228))|128|(0)(0)|132|(0)|170) */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x01c7, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x01c8, code lost:
    
        r1 = r0;
        r0 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x01cb, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x01cc, code lost:
    
        r1 = r0;
        r0 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x01ce, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x01d6, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x01d7, code lost:
    
        r9 = null;
        r16 = false;
        r17 = false;
        r18 = 0;
        r19 = false;
        r20 = false;
        r21 = -1.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x02a3, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x02a4, code lost:
    
        r3 = 0;
        r10 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x02a7, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x02a8, code lost:
    
        r3 = 0;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x02ac, code lost:
    
        r10 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x02ad, code lost:
    
        r12 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x02ae, code lost:
    
        android.util.Log.w(org.videolan.libvlc.util.VLCUtil.TAG, "Could not parse maximum CPU frequency!");
        android.util.Log.w(org.videolan.libvlc.util.VLCUtil.TAG, "Failed to parse: ");
        r10 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x02ca, code lost:
    
        r10 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x02cb, code lost:
    
        r12 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x02cc, code lost:
    
        android.util.Log.w(org.videolan.libvlc.util.VLCUtil.TAG, "Could not find maximum CPU frequency!");
        r10 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x02d1, code lost:
    
        close(r12);
        close(r10);
        r0 = -1.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x033d, code lost:
    
        close(r3);
        close(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x0343, code lost:
    
        throw r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0292 A[Catch: NumberFormatException -> 0x02ae, IOException -> 0x02cc, all -> 0x033b, TRY_LEAVE, TryCatch #14 {all -> 0x033b, blocks: (B:173:0x028c, B:175:0x0292, B:185:0x02ae, B:188:0x02cc), top: B:207:0x0280 }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x01e4 A[EDGE_INSN: B:225:0x01e4->B:128:0x01e4 BREAK  A[LOOP:1: B:65:0x0104->B:198:0x0104, LOOP_LABEL: LOOP:1: B:65:0x0104->B:198:0x0104], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x010c A[Catch: all -> 0x01c2, IOException -> 0x01e4, TryCatch #16 {IOException -> 0x01e4, all -> 0x01c2, blocks: (B:66:0x0106, B:68:0x010c, B:94:0x0165, B:96:0x016d, B:99:0x0176, B:101:0x017e, B:103:0x0186, B:106:0x0190, B:108:0x0198, B:111:0x019f, B:113:0x01ad, B:115:0x01b4, B:71:0x0117, B:74:0x0120, B:77:0x012a, B:80:0x0134, B:83:0x013d, B:86:0x0148, B:88:0x0150, B:91:0x015b), top: B:208:0x0106 }] */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v45 */
    /* JADX WARN: Type inference failed for: r10v46 */
    /* JADX WARN: Type inference failed for: r10v5, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v17 */
    /* JADX WARN: Type inference failed for: r12v18 */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r12v27 */
    /* JADX WARN: Type inference failed for: r12v28 */
    /* JADX WARN: Type inference failed for: r12v29 */
    /* JADX WARN: Type inference failed for: r12v30 */
    /* JADX WARN: Type inference failed for: r12v31 */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean hasCompatibleCPU(android.content.Context r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 839
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.videolan.libvlc.util.VLCUtil.hasCompatibleCPU(android.content.Context):boolean");
    }

    public static MachineSpecs getMachineSpecs() {
        return machineSpecs;
    }

    private static class ElfData {
        String att_arch;
        boolean att_fpu;
        int e_machine;
        int e_shnum;
        int e_shoff;
        boolean is64bits;
        ByteOrder order;
        int sh_offset;
        int sh_size;

        private ElfData() {
        }
    }

    private static File searchLibrary(ApplicationInfo applicationInfo) {
        String[] strArrSplit;
        if ((applicationInfo.flags & 1) != 0) {
            strArrSplit = System.getProperty(SystemConst.LIBRARY_PATH).split(":");
        } else {
            strArrSplit = new String[]{applicationInfo.nativeLibraryDir};
        }
        if (strArrSplit[0] == null) {
            Log.e(TAG, "can't find library path");
            return null;
        }
        for (String str : strArrSplit) {
            File file = new File(str, "libvlcjni.so");
            if (file.exists() && file.canRead()) {
                return file;
            }
        }
        Log.e(TAG, "WARNING: Can't find shared library");
        return null;
    }

    private static ElfData readLib(File file) throws Throwable {
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2 = null;
        byte b = 0;
        try {
            randomAccessFile = new RandomAccessFile(file, FileOptionConst.READ);
            try {
                try {
                    ElfData elfData = new ElfData();
                    if (!readHeader(randomAccessFile, elfData)) {
                        close(randomAccessFile);
                        return null;
                    }
                    int i = elfData.e_machine;
                    if (i != 3 && i != 8) {
                        if (i == 40) {
                            randomAccessFile.close();
                            RandomAccessFile randomAccessFile3 = new RandomAccessFile(file, FileOptionConst.READ);
                            try {
                                if (!readSection(randomAccessFile3, elfData)) {
                                    close(randomAccessFile3);
                                    return null;
                                }
                                randomAccessFile3.close();
                                randomAccessFile = new RandomAccessFile(file, FileOptionConst.READ);
                                if (readArmAttributes(randomAccessFile, elfData)) {
                                    close(randomAccessFile);
                                    return elfData;
                                }
                                close(randomAccessFile);
                                return null;
                            } catch (IOException e) {
                                e = e;
                                randomAccessFile = randomAccessFile3;
                            } catch (Throwable th) {
                                th = th;
                                randomAccessFile2 = randomAccessFile3;
                                close(randomAccessFile2);
                                throw th;
                            }
                        } else if (i != 62 && i != 183) {
                            close(randomAccessFile);
                            return null;
                        }
                    }
                    close(randomAccessFile);
                    return elfData;
                } catch (Throwable th2) {
                    th = th2;
                    randomAccessFile2 = randomAccessFile;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (IOException e3) {
            e = e3;
            randomAccessFile = null;
        } catch (Throwable th3) {
            th = th3;
        }
        e.printStackTrace();
        close(randomAccessFile);
        return null;
    }

    private static boolean readHeader(RandomAccessFile randomAccessFile, ElfData elfData) throws IOException {
        byte b;
        ByteOrder byteOrder;
        byte[] bArr = new byte[52];
        randomAccessFile.readFully(bArr);
        if (bArr[0] != 127 || bArr[1] != 69 || bArr[2] != 76 || bArr[3] != 70 || ((b = bArr[4]) != 1 && b != 2)) {
            Log.e(TAG, "ELF header invalid");
            return false;
        }
        elfData.is64bits = b == 2;
        if (bArr[5] == 1) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else {
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        elfData.order = byteOrder;
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(elfData.order);
        elfData.e_machine = byteBufferWrap.getShort(18);
        elfData.e_shoff = byteBufferWrap.getInt(32);
        elfData.e_shnum = byteBufferWrap.getShort(48);
        return true;
    }

    private static boolean readSection(RandomAccessFile randomAccessFile, ElfData elfData) throws IOException {
        byte[] bArr = new byte[40];
        randomAccessFile.seek(elfData.e_shoff);
        for (int i = 0; i < elfData.e_shnum; i++) {
            randomAccessFile.readFully(bArr);
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
            byteBufferWrap.order(elfData.order);
            if (byteBufferWrap.getInt(4) == SHT_ARM_ATTRIBUTES) {
                elfData.sh_offset = byteBufferWrap.getInt(16);
                elfData.sh_size = byteBufferWrap.getInt(20);
                return true;
            }
        }
        return false;
    }

    private static boolean readArmAttributes(RandomAccessFile randomAccessFile, ElfData elfData) throws IOException {
        byte[] bArr = new byte[elfData.sh_size];
        randomAccessFile.seek(elfData.sh_offset);
        randomAccessFile.readFully(bArr);
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(elfData.order);
        if (byteBufferWrap.get() != 65) {
            return false;
        }
        while (true) {
            if (byteBufferWrap.remaining() <= 0) {
                break;
            }
            int iPosition = byteBufferWrap.position();
            int i = byteBufferWrap.getInt();
            if (getString(byteBufferWrap).equals("aeabi")) {
                while (byteBufferWrap.position() < iPosition + i) {
                    int iPosition2 = byteBufferWrap.position();
                    byte b = byteBufferWrap.get();
                    int i2 = byteBufferWrap.getInt();
                    if (b != 1) {
                        byteBufferWrap.position(iPosition2 + i2);
                    } else {
                        while (byteBufferWrap.position() < iPosition2 + i2) {
                            int uleb128 = getUleb128(byteBufferWrap);
                            if (uleb128 == 6) {
                                elfData.att_arch = CPU_archs[getUleb128(byteBufferWrap)];
                            } else if (uleb128 == 27) {
                                getUleb128(byteBufferWrap);
                                elfData.att_fpu = true;
                            } else {
                                int i3 = uleb128 % 128;
                                if (i3 == 4 || i3 == 5 || i3 == 32 || (i3 > 32 && (i3 & 1) != 0)) {
                                    getString(byteBufferWrap);
                                } else {
                                    getUleb128(byteBufferWrap);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private static String getString(ByteBuffer byteBuffer) {
        char c;
        StringBuilder sb = new StringBuilder(byteBuffer.limit());
        while (byteBuffer.remaining() > 0 && (c = (char) byteBuffer.get()) != 0) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static int getUleb128(ByteBuffer byteBuffer) {
        byte b;
        int i = 0;
        do {
            b = byteBuffer.get();
            i = (i << 7) | (b & ByteCompanionObject.MAX_VALUE);
        } while ((b & 128) > 0);
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.net.Uri UriFromMrl(java.lang.String r7) {
        /*
            if (r7 != 0) goto L4
            r7 = 0
            return r7
        L4:
            char[] r7 = r7.toCharArray()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            int r1 = r7.length
            r2 = 2
            int r1 = r1 * r2
            r0.<init>(r1)
            r1 = 0
        L11:
            int r3 = r7.length
            if (r1 >= r3) goto L42
            char r3 = r7[r1]
            r4 = 37
            if (r3 != r4) goto L3c
            int r4 = r7.length
            int r4 = r4 - r1
            r5 = 3
            if (r4 < r5) goto L3c
            java.lang.String r4 = new java.lang.String     // Catch: java.lang.NumberFormatException -> L3c
            int r5 = r1 + 1
            r4.<init>(r7, r5, r2)     // Catch: java.lang.NumberFormatException -> L3c
            r5 = 16
            int r4 = java.lang.Integer.parseInt(r4, r5)     // Catch: java.lang.NumberFormatException -> L3c
            java.lang.String r5 = "'()*"
            int r5 = r5.indexOf(r4)     // Catch: java.lang.NumberFormatException -> L3c
            r6 = -1
            if (r5 == r6) goto L3c
            char r4 = (char) r4     // Catch: java.lang.NumberFormatException -> L3c
            r0.append(r4)     // Catch: java.lang.NumberFormatException -> L3c
            int r1 = r1 + 2
            goto L3f
        L3c:
            r0.append(r3)
        L3f:
            int r1 = r1 + 1
            goto L11
        L42:
            java.lang.String r7 = r0.toString()
            android.net.Uri r7 = android.net.Uri.parse(r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.videolan.libvlc.util.VLCUtil.UriFromMrl(java.lang.String):android.net.Uri");
    }

    public static String encodeVLCUri(Uri uri) {
        return encodeVLCString(uri.toString());
    }

    public static String encodeVLCString(String str) {
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder(charArray.length * 2);
        for (char c : charArray) {
            if (URI_AUTHORIZED_CHARS.indexOf(c) != -1) {
                sb.append(PunctuationConst.PERCENT).append(Integer.toHexString(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}
