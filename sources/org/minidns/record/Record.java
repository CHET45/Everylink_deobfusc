package org.minidns.record;

import com.aivox.besota.sdk.message.GestureInfo;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Data;

/* JADX INFO: loaded from: classes4.dex */
public final class Record<D extends Data> {
    private transient byte[] bytes;
    public final CLASS clazz;
    public final int clazzValue;
    private transient Integer hashCodeCache;
    public final DnsName name;
    public final D payloadData;
    public final long ttl;
    public final TYPE type;
    public final boolean unicastQuery;

    public enum TYPE {
        UNKNOWN(-1),
        A(1, C5085A.class),
        NS(2, C5089NS.class),
        MD(3),
        MF(4),
        CNAME(5, CNAME.class),
        SOA(6, SOA.class),
        MB(7),
        MG(8),
        MR(9),
        NULL(10),
        WKS(11),
        PTR(12, PTR.class),
        HINFO(13),
        MINFO(14),
        MX(15, C5088MX.class),
        TXT(16, TXT.class),
        RP(17),
        AFSDB(18),
        X25(19),
        ISDN(20),
        RT(21),
        NSAP(22),
        NSAP_PTR(23),
        SIG(24),
        KEY(25),
        PX(26),
        GPOS(27),
        AAAA(28, AAAA.class),
        LOC(29),
        NXT(30),
        EID(31),
        NIMLOC(32),
        SRV(33, SRV.class),
        ATMA(34),
        NAPTR(35),
        KX(36),
        CERT(37),
        A6(38),
        DNAME(39, DNAME.class),
        SINK(40),
        OPT(41, OPT.class),
        APL(42),
        DS(43, C5086DS.class),
        SSHFP(44),
        IPSECKEY(45),
        RRSIG(46, RRSIG.class),
        NSEC(47, NSEC.class),
        DNSKEY(48, DNSKEY.class),
        DHCID(49),
        NSEC3(50, NSEC3.class),
        NSEC3PARAM(51, NSEC3PARAM.class),
        TLSA(52, TLSA.class),
        HIP(55),
        NINFO(56),
        RKEY(57),
        TALINK(58),
        CDS(59),
        CDNSKEY(60),
        OPENPGPKEY(61, OPENPGPKEY.class),
        CSYNC(62),
        SPF(99),
        UINFO(100),
        UID(101),
        GID(102),
        UNSPEC(103),
        NID(104),
        L32(105),
        L64(106),
        LP(107),
        EUI48(108),
        EUI64(109),
        TKEY(249),
        TSIG(250),
        IXFR(251),
        AXFR(252),
        MAILB(GestureInfo.LEFT_ALL),
        MAILA(GestureInfo.RIGHT_ALL),
        ANY(255),
        URI(256),
        CAA(257),
        TA(32768),
        DLV(32769, DLV.class);

        private final Class<?> dataClass;
        private final int value;
        private static final Map<Integer, TYPE> INVERSE_LUT = new HashMap();
        private static final Map<Class<?>, TYPE> DATA_LUT = new HashMap();

        static {
            for (TYPE type : values()) {
                INVERSE_LUT.put(Integer.valueOf(type.getValue()), type);
                Class<?> cls = type.dataClass;
                if (cls != null) {
                    DATA_LUT.put(cls, type);
                }
            }
        }

        TYPE(int i) {
            this(i, null);
        }

        TYPE(int i, Class cls) {
            this.value = i;
            this.dataClass = cls;
        }

        public int getValue() {
            return this.value;
        }

        public <D extends Data> Class<D> getDataClass() {
            return (Class<D>) this.dataClass;
        }

        public static TYPE getType(int i) {
            TYPE type = INVERSE_LUT.get(Integer.valueOf(i));
            return type == null ? UNKNOWN : type;
        }

        public static <D extends Data> TYPE getType(Class<D> cls) {
            return DATA_LUT.get(cls);
        }
    }

    public enum CLASS {
        IN(1),
        CH(3),
        HS(4),
        NONE(GestureInfo.RIGHT_ALL),
        ANY(255);

        private static final HashMap<Integer, CLASS> INVERSE_LUT = new HashMap<>();
        private final int value;

        static {
            for (CLASS r3 : values()) {
                INVERSE_LUT.put(Integer.valueOf(r3.getValue()), r3);
            }
        }

        CLASS(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        public static CLASS getClass(int i) {
            return INVERSE_LUT.get(Integer.valueOf(i));
        }
    }

    public static Record<Data> parse(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        Data data;
        DnsName dnsName = DnsName.parse(dataInputStream, bArr);
        TYPE type = TYPE.getType(dataInputStream.readUnsignedShort());
        int unsignedShort = dataInputStream.readUnsignedShort();
        CLASS r3 = CLASS.getClass(unsignedShort & 32767);
        boolean z = (32768 & unsignedShort) > 0;
        long unsignedShort2 = (((long) dataInputStream.readUnsignedShort()) << 16) + ((long) dataInputStream.readUnsignedShort());
        int unsignedShort3 = dataInputStream.readUnsignedShort();
        switch (C50901.$SwitchMap$org$minidns$record$Record$TYPE[type.ordinal()]) {
            case 1:
                data = SOA.parse(dataInputStream, bArr);
                break;
            case 2:
                data = SRV.parse(dataInputStream, bArr);
                break;
            case 3:
                data = C5088MX.parse(dataInputStream, bArr);
                break;
            case 4:
                data = AAAA.parse(dataInputStream);
                break;
            case 5:
                data = C5085A.parse(dataInputStream);
                break;
            case 6:
                data = C5089NS.parse(dataInputStream, bArr);
                break;
            case 7:
                data = CNAME.parse(dataInputStream, bArr);
                break;
            case 8:
                data = DNAME.parse(dataInputStream, bArr);
                break;
            case 9:
                data = PTR.parse(dataInputStream, bArr);
                break;
            case 10:
                data = TXT.parse(dataInputStream, unsignedShort3);
                break;
            case 11:
                data = OPT.parse(dataInputStream, unsignedShort3);
                break;
            case 12:
                data = DNSKEY.parse(dataInputStream, unsignedShort3);
                break;
            case 13:
                data = RRSIG.parse(dataInputStream, bArr, unsignedShort3);
                break;
            case 14:
                data = C5086DS.parse(dataInputStream, unsignedShort3);
                break;
            case 15:
                data = NSEC.parse(dataInputStream, bArr, unsignedShort3);
                break;
            case 16:
                data = NSEC3.parse(dataInputStream, unsignedShort3);
                break;
            case 17:
                data = NSEC3PARAM.parse(dataInputStream);
                break;
            case 18:
                data = TLSA.parse(dataInputStream, unsignedShort3);
                break;
            case 19:
                data = OPENPGPKEY.parse(dataInputStream, unsignedShort3);
                break;
            case 20:
                data = DLV.parse(dataInputStream, unsignedShort3);
                break;
            default:
                data = UNKNOWN.parse(dataInputStream, unsignedShort3, type);
                break;
        }
        return new Record<>(dnsName, type, r3, unsignedShort, unsignedShort2, data, z);
    }

    /* JADX INFO: renamed from: org.minidns.record.Record$1 */
    static /* synthetic */ class C50901 {
        static final /* synthetic */ int[] $SwitchMap$org$minidns$record$Record$TYPE;

        static {
            int[] iArr = new int[TYPE.values().length];
            $SwitchMap$org$minidns$record$Record$TYPE = iArr;
            try {
                iArr[TYPE.SOA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.SRV.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.MX.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.AAAA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.A.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.NS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.CNAME.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.DNAME.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.PTR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.TXT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.OPT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.DNSKEY.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.RRSIG.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.DS.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.NSEC.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.NSEC3.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.NSEC3PARAM.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.TLSA.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.OPENPGPKEY.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.DLV.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[TYPE.UNKNOWN.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
        }
    }

    public Record(DnsName dnsName, TYPE type, CLASS r13, long j, D d, boolean z) {
        this(dnsName, type, r13, r13.getValue() + (z ? 32768 : 0), j, d, z);
    }

    public Record(String str, TYPE type, CLASS r11, long j, D d, boolean z) {
        this(DnsName.from(str), type, r11, j, d, z);
    }

    public Record(String str, TYPE type, int i, long j, D d) {
        this(DnsName.from(str), type, CLASS.NONE, i, j, d, false);
    }

    public Record(DnsName dnsName, TYPE type, int i, long j, D d) {
        this(dnsName, type, CLASS.NONE, i, j, d, false);
    }

    private Record(DnsName dnsName, TYPE type, CLASS r3, int i, long j, D d, boolean z) {
        this.name = dnsName;
        this.type = type;
        this.clazz = r3;
        this.clazzValue = i;
        this.ttl = j;
        this.payloadData = d;
        this.unicastQuery = z;
    }

    public void toOutputStream(OutputStream outputStream) throws IOException {
        if (this.payloadData == null) {
            throw new IllegalStateException("Empty Record has no byte representation");
        }
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        this.name.writeToStream(dataOutputStream);
        dataOutputStream.writeShort(this.type.getValue());
        dataOutputStream.writeShort(this.clazzValue);
        dataOutputStream.writeInt((int) this.ttl);
        dataOutputStream.writeShort(this.payloadData.length());
        this.payloadData.toOutputStream(dataOutputStream);
    }

    public byte[] toByteArray() {
        if (this.bytes == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(this.name.size() + 10 + this.payloadData.length());
            try {
                toOutputStream(new DataOutputStream(byteArrayOutputStream));
                this.bytes = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
        return (byte[]) this.bytes.clone();
    }

    public String toString() {
        return this.name.getRawAce() + ".\t" + this.ttl + '\t' + this.clazz + '\t' + this.type + '\t' + this.payloadData;
    }

    public boolean isAnswer(Question question) {
        return (question.type == this.type || question.type == TYPE.ANY) && (question.clazz == this.clazz || question.clazz == CLASS.ANY) && question.name.equals(this.name);
    }

    public boolean isUnicastQuery() {
        return this.unicastQuery;
    }

    public D getPayload() {
        return this.payloadData;
    }

    public long getTtl() {
        return this.ttl;
    }

    public Question getQuestion() {
        int i = C50901.$SwitchMap$org$minidns$record$Record$TYPE[this.type.ordinal()];
        if (i == 11) {
            return null;
        }
        if (i == 13) {
            return new Question(this.name, ((RRSIG) this.payloadData).typeCovered, this.clazz);
        }
        return new Question(this.name, this.type, this.clazz);
    }

    public DnsMessage.Builder getQuestionMessage() {
        Question question = getQuestion();
        if (question == null) {
            return null;
        }
        return question.asMessageBuilder();
    }

    public int hashCode() {
        if (this.hashCodeCache == null) {
            this.hashCodeCache = Integer.valueOf(((((((this.name.hashCode() + 37) * 37) + this.type.hashCode()) * 37) + this.clazz.hashCode()) * 37) + this.payloadData.hashCode());
        }
        return this.hashCodeCache.intValue();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Record)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Record record = (Record) obj;
        return this.name.equals(record.name) && this.type == record.type && this.clazz == record.clazz && this.payloadData.equals(record.payloadData);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <E extends Data> Record<E> ifPossibleAs(Class<E> cls) {
        if (this.type.dataClass == cls) {
            return this;
        }
        return null;
    }

    /* JADX INFO: renamed from: as */
    public <E extends Data> Record<E> m1955as(Class<E> cls) {
        Record<E> recordIfPossibleAs = ifPossibleAs(cls);
        if (recordIfPossibleAs != null) {
            return recordIfPossibleAs;
        }
        throw new IllegalArgumentException("The instance " + this + " can not be cast to a Record with" + cls);
    }

    public static <E extends Data> void filter(Collection<Record<E>> collection, Class<E> cls, Collection<Record<? extends Data>> collection2) {
        Iterator<Record<? extends Data>> it = collection2.iterator();
        while (it.hasNext()) {
            Record<E> recordIfPossibleAs = it.next().ifPossibleAs(cls);
            if (recordIfPossibleAs != null) {
                collection.add(recordIfPossibleAs);
            }
        }
    }

    public static <E extends Data> List<Record<E>> filter(Class<E> cls, Collection<Record<? extends Data>> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        filter(arrayList, cls, collection);
        return arrayList;
    }
}
