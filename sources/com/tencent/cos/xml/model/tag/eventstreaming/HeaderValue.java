package com.tencent.cos.xml.model.tag.eventstreaming;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.cos.xml.p035s3.Base64;
import com.tencent.cos.xml.p035s3.Base64Codec;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public abstract class HeaderValue {
    abstract void encodeValue(DataOutputStream dataOutputStream) throws IOException;

    public abstract HeaderType getType();

    public static HeaderValue fromBoolean(boolean z) {
        return new BooleanValue(z, null);
    }

    public static HeaderValue fromByte(byte b) {
        return new ByteValue(b, null);
    }

    public static HeaderValue fromShort(short s) {
        return new ShortValue(s, null);
    }

    public static HeaderValue fromInteger(int i) {
        return new IntegerValue(i, null);
    }

    public static HeaderValue fromLong(long j) {
        return new LongValue(j, null);
    }

    public static HeaderValue fromByteArray(byte[] bArr) {
        return new ByteArrayValue(bArr, null);
    }

    public static HeaderValue fromByteBuffer(ByteBuffer byteBuffer) {
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        byte[] bArr = new byte[byteBufferDuplicate.remaining()];
        byteBufferDuplicate.get(bArr);
        return fromByteArray(bArr);
    }

    public static HeaderValue fromString(String str) {
        return new StringValue(str, null);
    }

    public static HeaderValue fromTimestamp(long j) {
        return new TimestampValue(new Date(j), null);
    }

    public static HeaderValue fromDate(Date date) {
        return new TimestampValue(date, null);
    }

    public static HeaderValue fromUuid(UUID uuid) {
        return new UuidValue(uuid, null);
    }

    protected HeaderValue() {
    }

    public boolean getBoolean() {
        throw new IllegalStateException();
    }

    public byte getByte() {
        throw new IllegalStateException("Expected byte, but type was " + getType().name());
    }

    public short getShort() {
        throw new IllegalStateException("Expected short, but type was " + getType().name());
    }

    public int getInteger() {
        throw new IllegalStateException("Expected integer, but type was " + getType().name());
    }

    public long getLong() {
        throw new IllegalStateException("Expected long, but type was " + getType().name());
    }

    public byte[] getByteArray() {
        throw new IllegalStateException();
    }

    public final ByteBuffer getByteBuffer() {
        return ByteBuffer.wrap(getByteArray());
    }

    public String getString() {
        throw new IllegalStateException();
    }

    public long getTimestamp() {
        throw new IllegalStateException("Expected timestamp, but type was " + getType().name());
    }

    public Date getDate() {
        return new Date(getTimestamp());
    }

    public UUID getUuid() {
        throw new IllegalStateException("Expected UUID, but type was " + getType().name());
    }

    void encode(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(getType().headerTypeId);
        encodeValue(dataOutputStream);
    }

    /* JADX INFO: renamed from: com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue$1 */
    static /* synthetic */ class C43831 {

        /* JADX INFO: renamed from: $SwitchMap$com$tencent$cos$xml$model$tag$eventstreaming$HeaderType */
        static final /* synthetic */ int[] f1848x8672d452;

        static {
            int[] iArr = new int[HeaderType.values().length];
            f1848x8672d452 = iArr;
            try {
                iArr[HeaderType.TRUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1848x8672d452[HeaderType.FALSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1848x8672d452[HeaderType.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1848x8672d452[HeaderType.SHORT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1848x8672d452[HeaderType.INTEGER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1848x8672d452[HeaderType.LONG.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f1848x8672d452[HeaderType.BYTE_ARRAY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f1848x8672d452[HeaderType.STRING.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f1848x8672d452[HeaderType.TIMESTAMP.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f1848x8672d452[HeaderType.UUID.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    static HeaderValue decode(ByteBuffer byteBuffer) {
        C43831 c43831 = null;
        switch (C43831.f1848x8672d452[HeaderType.fromTypeId(byteBuffer.get()).ordinal()]) {
            case 1:
                return new BooleanValue(true, c43831);
            case 2:
                return new BooleanValue(false, c43831);
            case 3:
                return new ByteValue(byteBuffer.get(), c43831);
            case 4:
                return new ShortValue(byteBuffer.getShort(), c43831);
            case 5:
                return fromInteger(byteBuffer.getInt());
            case 6:
                return new LongValue(byteBuffer.getLong(), c43831);
            case 7:
                return fromByteArray(Utils.readBytes(byteBuffer));
            case 8:
                try {
                    return fromString(Utils.readString(byteBuffer));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw new IllegalStateException();
                }
            case 9:
                return TimestampValue.decode(byteBuffer);
            case 10:
                return UuidValue.decode(byteBuffer);
            default:
                throw new IllegalStateException();
        }
    }

    private static final class BooleanValue extends HeaderValue {
        private final boolean value;

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        void encodeValue(DataOutputStream dataOutputStream) {
        }

        /* synthetic */ BooleanValue(boolean z, C43831 c43831) {
            this(z);
        }

        private BooleanValue(boolean z) {
            this.value = z;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public HeaderType getType() {
            if (this.value) {
                return HeaderType.TRUE;
            }
            return HeaderType.FALSE;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public boolean getBoolean() {
            return this.value;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.value == ((BooleanValue) obj).value;
        }

        public int hashCode() {
            return this.value ? 1 : 0;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }

    private static final class ByteValue extends HeaderValue {
        private final byte value;

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        void encodeValue(DataOutputStream dataOutputStream) {
        }

        /* synthetic */ ByteValue(byte b, C43831 c43831) {
            this(b);
        }

        private ByteValue(byte b) {
            this.value = b;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public HeaderType getType() {
            return HeaderType.BYTE;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public byte getByte() {
            return this.value;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.value == ((ByteValue) obj).value;
        }

        public int hashCode() {
            return this.value;
        }

        public String toString() {
            return String.valueOf((int) this.value);
        }
    }

    private static final class ShortValue extends HeaderValue {
        private final short value;

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        void encodeValue(DataOutputStream dataOutputStream) {
        }

        /* synthetic */ ShortValue(short s, C43831 c43831) {
            this(s);
        }

        private ShortValue(short s) {
            this.value = s;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public HeaderType getType() {
            return HeaderType.SHORT;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public short getShort() {
            return this.value;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.value == ((ShortValue) obj).value;
        }

        public int hashCode() {
            return this.value;
        }

        public String toString() {
            return String.valueOf((int) this.value);
        }
    }

    private static final class IntegerValue extends HeaderValue {
        private final int value;

        /* synthetic */ IntegerValue(int i, C43831 c43831) {
            this(i);
        }

        private IntegerValue(int i) {
            this.value = i;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public HeaderType getType() {
            return HeaderType.INTEGER;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public int getInteger() {
            return this.value;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        void encodeValue(DataOutputStream dataOutputStream) throws IOException {
            dataOutputStream.writeInt(this.value);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.value == ((IntegerValue) obj).value;
        }

        public int hashCode() {
            return this.value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }

    private static final class LongValue extends HeaderValue {
        private final long value;

        /* synthetic */ LongValue(long j, C43831 c43831) {
            this(j);
        }

        private LongValue(long j) {
            this.value = j;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public HeaderType getType() {
            return HeaderType.LONG;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public long getLong() {
            return this.value;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        void encodeValue(DataOutputStream dataOutputStream) throws IOException {
            dataOutputStream.writeLong(this.value);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.value == ((LongValue) obj).value;
        }

        public int hashCode() {
            long j = this.value;
            return (int) (j ^ (j >>> 32));
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }

    private static final class ByteArrayValue extends HeaderValue {
        private Base64Codec codec;
        private final byte[] value;

        /* synthetic */ ByteArrayValue(byte[] bArr, C43831 c43831) {
            this(bArr);
        }

        private ByteArrayValue(byte[] bArr) {
            this.value = (byte[]) ValidationUtils.assertNotNull(bArr, "value");
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public HeaderType getType() {
            return HeaderType.BYTE_ARRAY;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public byte[] getByteArray() {
            return this.value;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        void encodeValue(DataOutputStream dataOutputStream) throws IOException {
            Utils.writeBytes(dataOutputStream, this.value);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Arrays.equals(this.value, ((ByteArrayValue) obj).value);
        }

        public int hashCode() {
            return Arrays.hashCode(this.value);
        }

        public String toString() {
            return Base64.encodeAsString(this.value);
        }

        private String toStringDirect(byte[] bArr) {
            char[] cArr = new char[bArr.length];
            int length = bArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                cArr[i2] = (char) bArr[i];
                i++;
                i2++;
            }
            return new String(cArr);
        }
    }

    private static final class StringValue extends HeaderValue {
        private final String value;

        /* synthetic */ StringValue(String str, C43831 c43831) {
            this(str);
        }

        private StringValue(String str) {
            this.value = (String) ValidationUtils.assertNotNull(str, "value");
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public HeaderType getType() {
            return HeaderType.STRING;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public String getString() {
            return this.value;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        void encodeValue(DataOutputStream dataOutputStream) throws IOException {
            Utils.writeString(dataOutputStream, this.value);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.value.equals(((StringValue) obj).value);
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String toString() {
            return PunctuationConst.DOUBLE_QUOTES + this.value + '\"';
        }
    }

    private static final class TimestampValue extends HeaderValue {
        private final Date value;

        /* synthetic */ TimestampValue(Date date, C43831 c43831) {
            this(date);
        }

        private TimestampValue(Date date) {
            this.value = (Date) ValidationUtils.assertNotNull(date, "value");
        }

        static TimestampValue decode(ByteBuffer byteBuffer) {
            return new TimestampValue(new Date(byteBuffer.getLong()));
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public HeaderType getType() {
            return HeaderType.TIMESTAMP;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public long getTimestamp() {
            return this.value.getTime();
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        void encodeValue(DataOutputStream dataOutputStream) throws IOException {
            dataOutputStream.writeLong(this.value.getTime());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.value.equals(((TimestampValue) obj).value);
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String toString() {
            return this.value.toString();
        }
    }

    private static final class UuidValue extends HeaderValue {
        private final UUID value;

        /* synthetic */ UuidValue(UUID uuid, C43831 c43831) {
            this(uuid);
        }

        private UuidValue(UUID uuid) {
            this.value = (UUID) ValidationUtils.assertNotNull(uuid, "value");
        }

        static UuidValue decode(ByteBuffer byteBuffer) {
            return new UuidValue(new UUID(byteBuffer.getLong(), byteBuffer.getLong()));
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public HeaderType getType() {
            return HeaderType.UUID;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        public UUID getUuid() {
            return this.value;
        }

        @Override // com.tencent.cos.xml.model.tag.eventstreaming.HeaderValue
        void encodeValue(DataOutputStream dataOutputStream) throws IOException {
            dataOutputStream.writeLong(this.value.getMostSignificantBits());
            dataOutputStream.writeLong(this.value.getLeastSignificantBits());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.value.equals(((UuidValue) obj).value);
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String toString() {
            return this.value.toString();
        }
    }
}
