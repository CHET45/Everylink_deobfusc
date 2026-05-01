package com.github.houbb.heaven.util.util.internal;

import com.tencent.beacon.pack.AbstractJceStruct;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import kotlin.UByte;

/* JADX INFO: loaded from: classes3.dex */
public class Base64 {
    private Base64() {
    }

    public static Encoder getEncoder() {
        return Encoder.RFC4648;
    }

    public static Encoder getUrlEncoder() {
        return Encoder.RFC4648_URLSAFE;
    }

    public static Encoder getMimeEncoder() {
        return Encoder.RFC2045;
    }

    public static Encoder getMimeEncoder(int i, byte[] bArr) {
        Objects.requireNonNull(bArr);
        int[] iArr = Decoder.fromBase64;
        for (byte b : bArr) {
            if (iArr[b & UByte.MAX_VALUE] != -1) {
                throw new IllegalArgumentException("Illegal base64 line separator character 0x" + Integer.toString(b, 16));
            }
        }
        int i2 = i & (-4);
        if (i2 <= 0) {
            return Encoder.RFC4648;
        }
        return new Encoder(false, bArr, i2, true);
    }

    public static Decoder getDecoder() {
        return Decoder.RFC4648;
    }

    public static Decoder getUrlDecoder() {
        return Decoder.RFC4648_URLSAFE;
    }

    public static Decoder getMimeDecoder() {
        return Decoder.RFC2045;
    }

    public static class Encoder {
        private static final byte[] CRLF;
        private static final int MIMELINEMAX = 76;
        static final Encoder RFC2045;
        static final Encoder RFC4648;
        static final Encoder RFC4648_URLSAFE;
        private static final char[] toBase64 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
        private static final char[] toBase64URL = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
        private final boolean doPadding;
        private final boolean isURL;
        private final int linemax;
        private final byte[] newline;

        private Encoder(boolean z, byte[] bArr, int i, boolean z2) {
            this.isURL = z;
            this.newline = bArr;
            this.linemax = i;
            this.doPadding = z2;
        }

        static {
            byte[] bArr = {AbstractJceStruct.SIMPLE_LIST, 10};
            CRLF = bArr;
            RFC4648 = new Encoder(false, null, -1, true);
            RFC4648_URLSAFE = new Encoder(true, null, -1, true);
            RFC2045 = new Encoder(false, bArr, 76, true);
        }

        private final int outLength(int i) {
            int i2;
            if (this.doPadding) {
                i2 = ((i + 2) / 3) * 4;
            } else {
                int i3 = i % 3;
                i2 = ((i / 3) * 4) + (i3 == 0 ? 0 : i3 + 1);
            }
            int i4 = this.linemax;
            return i4 > 0 ? i2 + (((i2 - 1) / i4) * this.newline.length) : i2;
        }

        public byte[] encode(byte[] bArr) {
            int iOutLength = outLength(bArr.length);
            byte[] bArr2 = new byte[iOutLength];
            int iEncode0 = encode0(bArr, 0, bArr.length, bArr2);
            return iEncode0 != iOutLength ? Arrays.copyOf(bArr2, iEncode0) : bArr2;
        }

        public int encode(byte[] bArr, byte[] bArr2) {
            if (bArr2.length < outLength(bArr.length)) {
                throw new IllegalArgumentException("Output byte array is too small for encoding all input bytes");
            }
            return encode0(bArr, 0, bArr.length, bArr2);
        }

        public String encodeToString(byte[] bArr) {
            byte[] bArrEncode = encode(bArr);
            return new String(bArrEncode, 0, 0, bArrEncode.length);
        }

        public ByteBuffer encode(ByteBuffer byteBuffer) {
            int iEncode0;
            int iOutLength = outLength(byteBuffer.remaining());
            byte[] bArrCopyOf = new byte[iOutLength];
            if (byteBuffer.hasArray()) {
                iEncode0 = encode0(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.arrayOffset() + byteBuffer.limit(), bArrCopyOf);
                byteBuffer.position(byteBuffer.limit());
            } else {
                int iRemaining = byteBuffer.remaining();
                byte[] bArr = new byte[iRemaining];
                byteBuffer.get(bArr);
                iEncode0 = encode0(bArr, 0, iRemaining, bArrCopyOf);
            }
            if (iEncode0 != iOutLength) {
                bArrCopyOf = Arrays.copyOf(bArrCopyOf, iEncode0);
            }
            return ByteBuffer.wrap(bArrCopyOf);
        }

        public OutputStream wrap(OutputStream outputStream) {
            Objects.requireNonNull(outputStream);
            return new EncOutputStream(outputStream, this.isURL ? toBase64URL : toBase64, this.newline, this.linemax, this.doPadding);
        }

        public Encoder withoutPadding() {
            return !this.doPadding ? this : new Encoder(this.isURL, this.newline, this.linemax, false);
        }

        private void encodeBlock(byte[] bArr, int i, int i2, byte[] bArr2, int i3, boolean z) {
            char[] cArr = z ? toBase64URL : toBase64;
            while (i < i2) {
                int i4 = i + 2;
                int i5 = ((bArr[i + 1] & UByte.MAX_VALUE) << 8) | ((bArr[i] & UByte.MAX_VALUE) << 16);
                i += 3;
                int i6 = i5 | (bArr[i4] & UByte.MAX_VALUE);
                bArr2[i3] = (byte) cArr[(i6 >>> 18) & 63];
                bArr2[i3 + 1] = (byte) cArr[(i6 >>> 12) & 63];
                int i7 = i3 + 3;
                bArr2[i3 + 2] = (byte) cArr[(i6 >>> 6) & 63];
                i3 += 4;
                bArr2[i7] = (byte) cArr[i6 & 63];
            }
        }

        private int encode0(byte[] bArr, int i, int i2, byte[] bArr2) {
            char[] cArr = this.isURL ? toBase64URL : toBase64;
            int i3 = ((i2 - i) / 3) * 3;
            int i4 = i + i3;
            int i5 = this.linemax;
            if (i5 > 0 && i3 > (i5 / 4) * 3) {
                i3 = (i5 / 4) * 3;
            }
            int i6 = i3;
            int i7 = i;
            int i8 = 0;
            while (i7 < i4) {
                int iMin = Math.min(i7 + i6, i4);
                encodeBlock(bArr, i7, iMin, bArr2, i8, this.isURL);
                int i9 = ((iMin - i7) / 3) * 4;
                i8 += i9;
                if (i9 == this.linemax && iMin < i2) {
                    byte[] bArr3 = this.newline;
                    int length = bArr3.length;
                    int i10 = 0;
                    while (i10 < length) {
                        bArr2[i8] = bArr3[i10];
                        i10++;
                        i8++;
                    }
                }
                i7 = iMin;
            }
            if (i7 >= i2) {
                return i8;
            }
            int i11 = i7 + 1;
            int i12 = bArr[i7] & UByte.MAX_VALUE;
            int i13 = i8 + 1;
            bArr2[i8] = (byte) cArr[i12 >> 2];
            if (i11 == i2) {
                int i14 = i8 + 2;
                bArr2[i13] = (byte) cArr[(i12 << 4) & 63];
                if (!this.doPadding) {
                    return i14;
                }
                int i15 = i8 + 3;
                bArr2[i14] = kotlin.p036io.encoding.Base64.padSymbol;
                int i16 = i8 + 4;
                bArr2[i15] = kotlin.p036io.encoding.Base64.padSymbol;
                return i16;
            }
            int i17 = bArr[i11] & UByte.MAX_VALUE;
            bArr2[i13] = (byte) cArr[((i12 << 4) & 63) | (i17 >> 4)];
            int i18 = i8 + 3;
            bArr2[i8 + 2] = (byte) cArr[(i17 << 2) & 63];
            if (!this.doPadding) {
                return i18;
            }
            int i19 = i8 + 4;
            bArr2[i18] = kotlin.p036io.encoding.Base64.padSymbol;
            return i19;
        }
    }

    public static class Decoder {
        static final Decoder RFC2045;
        static final Decoder RFC4648;
        static final Decoder RFC4648_URLSAFE;
        private static final int[] fromBase64;
        private static final int[] fromBase64URL;
        private final boolean isMIME;
        private final boolean isURL;

        private Decoder(boolean z, boolean z2) {
            this.isURL = z;
            this.isMIME = z2;
        }

        static {
            int[] iArr = new int[256];
            fromBase64 = iArr;
            Arrays.fill(iArr, -1);
            for (int i = 0; i < Encoder.toBase64.length; i++) {
                fromBase64[Encoder.toBase64[i]] = i;
            }
            fromBase64[61] = -2;
            int[] iArr2 = new int[256];
            fromBase64URL = iArr2;
            Arrays.fill(iArr2, -1);
            for (int i2 = 0; i2 < Encoder.toBase64URL.length; i2++) {
                fromBase64URL[Encoder.toBase64URL[i2]] = i2;
            }
            fromBase64URL[61] = -2;
            RFC4648 = new Decoder(false, false);
            RFC4648_URLSAFE = new Decoder(true, false);
            RFC2045 = new Decoder(false, true);
        }

        public byte[] decode(byte[] bArr) {
            int iOutLength = outLength(bArr, 0, bArr.length);
            byte[] bArr2 = new byte[iOutLength];
            int iDecode0 = decode0(bArr, 0, bArr.length, bArr2);
            return iDecode0 != iOutLength ? Arrays.copyOf(bArr2, iDecode0) : bArr2;
        }

        public byte[] decode(String str) {
            return decode(str.getBytes(StandardCharsets.UTF_8));
        }

        public int decode(byte[] bArr, byte[] bArr2) {
            if (bArr2.length < outLength(bArr, 0, bArr.length)) {
                throw new IllegalArgumentException("Output byte array is too small for decoding all input bytes");
            }
            return decode0(bArr, 0, bArr.length, bArr2);
        }

        public ByteBuffer decode(ByteBuffer byteBuffer) {
            int iRemaining;
            byte[] bArrArray;
            int iArrayOffset;
            int iPosition = byteBuffer.position();
            try {
                if (byteBuffer.hasArray()) {
                    bArrArray = byteBuffer.array();
                    iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
                    iRemaining = byteBuffer.arrayOffset() + byteBuffer.limit();
                    byteBuffer.position(byteBuffer.limit());
                } else {
                    iRemaining = byteBuffer.remaining();
                    bArrArray = new byte[iRemaining];
                    byteBuffer.get(bArrArray);
                    iArrayOffset = 0;
                }
                byte[] bArr = new byte[outLength(bArrArray, iArrayOffset, iRemaining)];
                return ByteBuffer.wrap(bArr, 0, decode0(bArrArray, iArrayOffset, iRemaining, bArr));
            } catch (IllegalArgumentException e) {
                byteBuffer.position(iPosition);
                throw e;
            }
        }

        public InputStream wrap(InputStream inputStream) {
            Objects.requireNonNull(inputStream);
            return new DecInputStream(inputStream, this.isURL ? fromBase64URL : fromBase64, this.isMIME);
        }

        private int outLength(byte[] bArr, int i, int i2) {
            int i3;
            int[] iArr = this.isURL ? fromBase64URL : fromBase64;
            int i4 = i2 - i;
            int i5 = 0;
            if (i4 == 0) {
                return 0;
            }
            if (i4 < 2) {
                if (this.isMIME && iArr[0] == -1) {
                    return 0;
                }
                throw new IllegalArgumentException("Input byte[] should at least have 2 bytes for base64 bytes");
            }
            if (this.isMIME) {
                int i6 = 0;
                while (true) {
                    if (i >= i2) {
                        break;
                    }
                    int i7 = i + 1;
                    int i8 = bArr[i] & UByte.MAX_VALUE;
                    if (i8 == 61) {
                        i4 -= (i2 - i7) + 1;
                        break;
                    }
                    if (iArr[i8] == -1) {
                        i6++;
                    }
                    i = i7;
                }
                i4 -= i6;
            } else if (bArr[i2 - 1] == 61) {
                i5 = bArr[i2 - 2] == 61 ? 2 : 1;
            }
            if (i5 == 0 && (i3 = i4 & 3) != 0) {
                i5 = 4 - i3;
            }
            return (((i4 + 3) / 4) * 3) - i5;
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x008e, code lost:
        
            if (r6 != 18) goto L44;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x00da, code lost:
        
            if (r6 != 6) goto L46;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x00dc, code lost:
        
            r22[r7] = (byte) (r8 >> 16);
            r7 = r7 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x00e5, code lost:
        
            if (r6 != 0) goto L48;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00e7, code lost:
        
            r3 = r7 + 1;
            r22[r7] = (byte) (r8 >> 16);
            r7 = r7 + 2;
            r22[r3] = (byte) (r8 >> 8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x00f8, code lost:
        
            if (r6 == 12) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00fa, code lost:
        
            if (r5 >= r21) goto L70;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00fe, code lost:
        
            if (r18.isMIME == false) goto L71;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x0100, code lost:
        
            r4 = r2[r19[r5] & kotlin.UByte.MAX_VALUE];
            r5 = r5 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x0109, code lost:
        
            if (r4 >= 0) goto L72;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x0120, code lost:
        
            throw new java.lang.IllegalArgumentException("Input byte array has incorrect ending byte at " + r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x0121, code lost:
        
            return r7;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x0129, code lost:
        
            throw new java.lang.IllegalArgumentException("Last unit does not have enough valid bits");
         */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0073 A[PHI: r5 r7
  0x0073: PHI (r5v6 int) = (r5v1 int), (r5v1 int), (r5v14 int) binds: [B:9:0x001a, B:11:0x001e, B:18:0x006f] A[DONT_GENERATE, DONT_INLINE]
  0x0073: PHI (r7v6 int) = (r7v1 int), (r7v1 int), (r7v9 int) binds: [B:9:0x001a, B:11:0x001e, B:18:0x006f] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private int decode0(byte[] r19, int r20, int r21, byte[] r22) {
            /*
                Method dump skipped, instruction units count: 298
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.houbb.heaven.util.util.internal.Base64.Decoder.decode0(byte[], int, int, byte[]):int");
        }
    }

    private static class EncOutputStream extends FilterOutputStream {

        /* JADX INFO: renamed from: b0 */
        private int f544b0;

        /* JADX INFO: renamed from: b1 */
        private int f545b1;

        /* JADX INFO: renamed from: b2 */
        private int f546b2;
        private final char[] base64;
        private byte[] buf;
        private boolean closed;
        private final boolean doPadding;
        private int leftover;
        private final int linemax;
        private int linepos;
        private final byte[] newline;

        EncOutputStream(OutputStream outputStream, char[] cArr, byte[] bArr, int i, boolean z) {
            super(outputStream);
            this.leftover = 0;
            this.closed = false;
            this.linepos = 0;
            this.base64 = cArr;
            this.newline = bArr;
            this.linemax = i;
            this.doPadding = z;
            this.buf = new byte[i <= 0 ? 8124 : i];
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(int i) throws IOException {
            write(new byte[]{(byte) (i & 255)}, 0, 1);
        }

        private void checkNewline() throws IOException {
            if (this.linepos == this.linemax) {
                this.out.write(this.newline);
                this.linepos = 0;
            }
        }

        private void writeb4(char c, char c2, char c3, char c4) throws IOException {
            byte[] bArr = this.buf;
            bArr[0] = (byte) c;
            bArr[1] = (byte) c2;
            bArr[2] = (byte) c3;
            bArr[3] = (byte) c4;
            this.out.write(this.buf, 0, 4);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
                throw new ArrayIndexOutOfBoundsException();
            }
            if (i2 == 0) {
                return;
            }
            int i3 = this.leftover;
            if (i3 != 0) {
                if (i3 == 1) {
                    int i4 = i + 1;
                    this.f545b1 = bArr[i] & UByte.MAX_VALUE;
                    i2--;
                    if (i2 == 0) {
                        this.leftover = i3 + 1;
                        return;
                    }
                    i = i4;
                }
                int i5 = i + 1;
                this.f546b2 = bArr[i] & UByte.MAX_VALUE;
                i2--;
                checkNewline();
                char[] cArr = this.base64;
                int i6 = this.f544b0;
                char c = cArr[i6 >> 2];
                int i7 = this.f545b1;
                char c2 = cArr[((i6 << 4) & 63) | (i7 >> 4)];
                int i8 = this.f546b2;
                writeb4(c, c2, cArr[((i7 << 2) & 63) | (i8 >> 6)], cArr[i8 & 63]);
                this.linepos += 4;
                i = i5;
            }
            int i9 = i2 / 3;
            this.leftover = i2 - (i9 * 3);
            while (i9 > 0) {
                checkNewline();
                int iMin = (Math.min(i9, (this.linemax <= 0 ? this.buf.length : this.buf.length - this.linepos) / 4) * 3) + i;
                int i10 = 0;
                while (i < iMin) {
                    int i11 = i + 2;
                    int i12 = ((bArr[i + 1] & UByte.MAX_VALUE) << 8) | ((bArr[i] & UByte.MAX_VALUE) << 16);
                    i += 3;
                    int i13 = i12 | (bArr[i11] & UByte.MAX_VALUE);
                    byte[] bArr2 = this.buf;
                    char[] cArr2 = this.base64;
                    bArr2[i10] = (byte) cArr2[(i13 >>> 18) & 63];
                    bArr2[i10 + 1] = (byte) cArr2[(i13 >>> 12) & 63];
                    int i14 = i10 + 3;
                    bArr2[i10 + 2] = (byte) cArr2[(i13 >>> 6) & 63];
                    i10 += 4;
                    bArr2[i14] = (byte) cArr2[i13 & 63];
                }
                this.out.write(this.buf, 0, i10);
                this.linepos += i10;
                i9 -= i10 / 4;
                i = iMin;
            }
            int i15 = this.leftover;
            if (i15 == 1) {
                this.f544b0 = bArr[i] & UByte.MAX_VALUE;
            } else if (i15 == 2) {
                this.f544b0 = bArr[i] & UByte.MAX_VALUE;
                this.f545b1 = bArr[i + 1] & UByte.MAX_VALUE;
            }
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            int i = this.leftover;
            if (i == 1) {
                checkNewline();
                this.out.write(this.base64[this.f544b0 >> 2]);
                this.out.write(this.base64[(this.f544b0 << 4) & 63]);
                if (this.doPadding) {
                    this.out.write(61);
                    this.out.write(61);
                }
            } else if (i == 2) {
                checkNewline();
                this.out.write(this.base64[this.f544b0 >> 2]);
                this.out.write(this.base64[((this.f544b0 << 4) & 63) | (this.f545b1 >> 4)]);
                this.out.write(this.base64[(this.f545b1 << 2) & 63]);
                if (this.doPadding) {
                    this.out.write(61);
                }
            }
            this.leftover = 0;
            this.out.close();
        }
    }

    private static class DecInputStream extends InputStream {
        private final int[] base64;

        /* JADX INFO: renamed from: is */
        private final InputStream f543is;
        private final boolean isMIME;
        private int bits = 0;
        private int nextin = 18;
        private int nextout = -8;
        private boolean eof = false;
        private boolean closed = false;
        private byte[] sbBuf = new byte[1];

        DecInputStream(InputStream inputStream, int[] iArr, boolean z) {
            this.f543is = inputStream;
            this.base64 = iArr;
            this.isMIME = z;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (read(this.sbBuf, 0, 1) == -1) {
                return -1;
            }
            return this.sbBuf[0] & UByte.MAX_VALUE;
        }

        private int eof(byte[] bArr, int i, int i2, int i3) throws IOException {
            this.eof = true;
            int i4 = this.nextin;
            if (i4 != 18) {
                if (i4 == 12) {
                    throw new IOException("Base64 stream has one un-decoded dangling byte.");
                }
                int i5 = i + 1;
                int i6 = this.bits;
                bArr[i] = (byte) (i6 >> 16);
                if (i4 != 0) {
                    i = i5;
                } else if (i2 == 1) {
                    this.bits = i6 >> 8;
                    this.nextout = 0;
                    i = i5;
                } else {
                    i += 2;
                    bArr[i5] = (byte) (i6 >> 8);
                }
            }
            if (i == i3) {
                return -1;
            }
            return i - i3;
        }

        private int padding(byte[] bArr, int i, int i2, int i3) throws IOException {
            int i4 = this.nextin;
            if (i4 == 18 || i4 == 12 || (i4 == 6 && this.f543is.read() != 61)) {
                throw new IOException("Illegal base64 ending sequence:" + this.nextin);
            }
            int i5 = i + 1;
            int i6 = this.bits;
            bArr[i] = (byte) (i6 >> 16);
            if (this.nextin == 0) {
                if (i2 == 1) {
                    this.bits = i6 >> 8;
                    this.nextout = 0;
                } else {
                    bArr[i5] = (byte) (i6 >> 8);
                    i5 = i + 2;
                }
            }
            this.eof = true;
            return i5 - i3;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            if (this.eof && this.nextout < 0) {
                return -1;
            }
            if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
                throw new IndexOutOfBoundsException();
            }
            int i3 = i;
            while (true) {
                int i4 = this.nextout;
                if (i4 < 0) {
                    this.bits = 0;
                    while (true) {
                        if (i2 <= 0) {
                            break;
                        }
                        int i5 = this.f543is.read();
                        if (i5 == -1) {
                            return eof(bArr, i3, i2, i);
                        }
                        int i6 = this.base64[i5];
                        if (i6 < 0) {
                            if (i6 == -2) {
                                return padding(bArr, i3, i2, i);
                            }
                            if (i6 == -1) {
                                if (!this.isMIME) {
                                    throw new IOException("Illegal base64 character " + Integer.toString(i6, 16));
                                }
                            }
                        }
                        int i7 = this.bits;
                        int i8 = this.nextin;
                        int i9 = (i6 << i8) | i7;
                        this.bits = i9;
                        if (i8 == 0) {
                            this.nextin = 18;
                            int i10 = i3 + 1;
                            bArr[i3] = (byte) (i9 >> 16);
                            if (i2 == 1) {
                                this.nextout = 8;
                                i3 = i10;
                                break;
                            }
                            int i11 = i3 + 2;
                            bArr[i10] = (byte) (i9 >> 8);
                            if (i2 == 2) {
                                this.nextout = 0;
                                i3 = i11;
                                break;
                            }
                            i3 += 3;
                            bArr[i11] = (byte) i9;
                            i2 -= 3;
                            this.bits = 0;
                        } else {
                            this.nextin = i8 - 6;
                        }
                    }
                    return i3 - i;
                }
                if (i2 == 0) {
                    return i3 - i;
                }
                bArr[i3] = (byte) (this.bits >> i4);
                i2--;
                this.nextout = i4 - 8;
                i3++;
            }
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            return this.f543is.available();
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.f543is.close();
        }
    }
}
