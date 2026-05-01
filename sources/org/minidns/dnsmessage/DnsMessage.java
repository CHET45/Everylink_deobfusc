package org.minidns.dnsmessage;

import com.microsoft.azure.storage.analytics.LogRecordStreamReader;
import com.tencent.beacon.pack.AbstractJceStruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.minidns.edns.Edns;
import org.minidns.record.Data;
import org.minidns.record.OPT;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class DnsMessage {
    private static final Logger LOGGER = Logger.getLogger(DnsMessage.class.getName());
    public final List<Record<? extends Data>> additionalSection;
    public final List<Record<? extends Data>> answerSection;
    private long answersMinTtlCache;
    public final boolean authenticData;
    public final boolean authoritativeAnswer;
    public final List<Record<? extends Data>> authoritySection;
    private byte[] byteCache;
    public final boolean checkingDisabled;
    private Edns edns;
    private transient Integer hashCodeCache;

    /* JADX INFO: renamed from: id */
    public final int f2046id;
    private DnsMessage normalizedVersionCache;
    public final OPCODE opcode;
    public final int optRrPosition;

    /* JADX INFO: renamed from: qr */
    public final boolean f2047qr;
    public final List<Question> questions;
    public final long receiveTimestamp;
    public final boolean recursionAvailable;
    public final boolean recursionDesired;
    public final RESPONSE_CODE responseCode;
    private String terminalOutputCache;
    private String toStringCache;
    public final boolean truncated;

    private enum SectionName {
        answer,
        authority,
        additional
    }

    public enum RESPONSE_CODE {
        NO_ERROR(0),
        FORMAT_ERR(1),
        SERVER_FAIL(2),
        NX_DOMAIN(3),
        NO_IMP(4),
        REFUSED(5),
        YXDOMAIN(6),
        YXRRSET(7),
        NXRRSET(8),
        NOT_AUTH(9),
        NOT_ZONE(10),
        BADVERS_BADSIG(16),
        BADKEY(17),
        BADTIME(18),
        BADMODE(19),
        BADNAME(20),
        BADALG(21),
        BADTRUNC(22),
        BADCOOKIE(23);

        private static final Map<Integer, RESPONSE_CODE> INVERSE_LUT = new HashMap(values().length);
        private final byte value;

        static {
            for (RESPONSE_CODE response_code : values()) {
                INVERSE_LUT.put(Integer.valueOf(response_code.value), response_code);
            }
        }

        RESPONSE_CODE(int i) {
            this.value = (byte) i;
        }

        public byte getValue() {
            return this.value;
        }

        public static RESPONSE_CODE getResponseCode(int i) throws IllegalArgumentException {
            if (i < 0 || i > 65535) {
                throw new IllegalArgumentException();
            }
            return INVERSE_LUT.get(Integer.valueOf(i));
        }
    }

    public enum OPCODE {
        QUERY,
        INVERSE_QUERY,
        STATUS,
        UNASSIGNED3,
        NOTIFY,
        UPDATE;

        private static final OPCODE[] INVERSE_LUT = new OPCODE[values().length];
        private final byte value = (byte) ordinal();

        static {
            for (OPCODE opcode : values()) {
                OPCODE[] opcodeArr = INVERSE_LUT;
                if (opcodeArr[opcode.getValue()] != null) {
                    throw new IllegalStateException();
                }
                opcodeArr[opcode.getValue()] = opcode;
            }
        }

        OPCODE() {
        }

        public byte getValue() {
            return this.value;
        }

        public static OPCODE getOpcode(int i) throws IllegalArgumentException {
            if (i < 0 || i > 15) {
                throw new IllegalArgumentException();
            }
            OPCODE[] opcodeArr = INVERSE_LUT;
            if (i >= opcodeArr.length) {
                return null;
            }
            return opcodeArr[i];
        }
    }

    protected DnsMessage(Builder builder) {
        this.answersMinTtlCache = -1L;
        this.f2046id = builder.f2048id;
        this.opcode = builder.opcode;
        this.responseCode = builder.responseCode;
        this.receiveTimestamp = builder.receiveTimestamp;
        this.f2047qr = builder.query;
        this.authoritativeAnswer = builder.authoritativeAnswer;
        this.truncated = builder.truncated;
        this.recursionDesired = builder.recursionDesired;
        this.recursionAvailable = builder.recursionAvailable;
        this.authenticData = builder.authenticData;
        this.checkingDisabled = builder.checkingDisabled;
        if (builder.questions == null) {
            this.questions = Collections.emptyList();
        } else {
            ArrayList arrayList = new ArrayList(builder.questions.size());
            arrayList.addAll(builder.questions);
            this.questions = Collections.unmodifiableList(arrayList);
        }
        if (builder.answerSection == null) {
            this.answerSection = Collections.emptyList();
        } else {
            ArrayList arrayList2 = new ArrayList(builder.answerSection.size());
            arrayList2.addAll(builder.answerSection);
            this.answerSection = Collections.unmodifiableList(arrayList2);
        }
        if (builder.authoritySection == null) {
            this.authoritySection = Collections.emptyList();
        } else {
            ArrayList arrayList3 = new ArrayList(builder.authoritySection.size());
            arrayList3.addAll(builder.authoritySection);
            this.authoritySection = Collections.unmodifiableList(arrayList3);
        }
        if (builder.additionalSection == null && builder.ednsBuilder == null) {
            this.additionalSection = Collections.emptyList();
        } else {
            int size = builder.additionalSection != null ? builder.additionalSection.size() : 0;
            ArrayList arrayList4 = new ArrayList(builder.ednsBuilder != null ? size + 1 : size);
            if (builder.additionalSection != null) {
                arrayList4.addAll(builder.additionalSection);
            }
            if (builder.ednsBuilder != null) {
                Edns ednsBuild = builder.ednsBuilder.build();
                this.edns = ednsBuild;
                arrayList4.add(ednsBuild.asRecord());
            }
            this.additionalSection = Collections.unmodifiableList(arrayList4);
        }
        int optRrPosition = getOptRrPosition(this.additionalSection);
        this.optRrPosition = optRrPosition;
        if (optRrPosition != -1) {
            do {
                optRrPosition++;
                if (optRrPosition >= this.additionalSection.size()) {
                    return;
                }
            } while (this.additionalSection.get(optRrPosition).type != Record.TYPE.OPT);
            throw new IllegalArgumentException("There must be only one OPT pseudo RR in the additional section");
        }
    }

    public DnsMessage(byte[] bArr) throws IOException {
        this.answersMinTtlCache = -1L;
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        this.f2046id = dataInputStream.readUnsignedShort();
        int unsignedShort = dataInputStream.readUnsignedShort();
        this.f2047qr = ((unsignedShort >> 15) & 1) == 1;
        this.opcode = OPCODE.getOpcode((unsignedShort >> 11) & 15);
        this.authoritativeAnswer = ((unsignedShort >> 10) & 1) == 1;
        this.truncated = ((unsignedShort >> 9) & 1) == 1;
        this.recursionDesired = ((unsignedShort >> 8) & 1) == 1;
        this.recursionAvailable = ((unsignedShort >> 7) & 1) == 1;
        this.authenticData = ((unsignedShort >> 5) & 1) == 1;
        this.checkingDisabled = ((unsignedShort >> 4) & 1) == 1;
        this.responseCode = RESPONSE_CODE.getResponseCode(unsignedShort & 15);
        this.receiveTimestamp = System.currentTimeMillis();
        int unsignedShort2 = dataInputStream.readUnsignedShort();
        int unsignedShort3 = dataInputStream.readUnsignedShort();
        int unsignedShort4 = dataInputStream.readUnsignedShort();
        int unsignedShort5 = dataInputStream.readUnsignedShort();
        this.questions = new ArrayList(unsignedShort2);
        for (int i = 0; i < unsignedShort2; i++) {
            this.questions.add(new Question(dataInputStream, bArr));
        }
        this.answerSection = new ArrayList(unsignedShort3);
        for (int i2 = 0; i2 < unsignedShort3; i2++) {
            this.answerSection.add(Record.parse(dataInputStream, bArr));
        }
        this.authoritySection = new ArrayList(unsignedShort4);
        for (int i3 = 0; i3 < unsignedShort4; i3++) {
            this.authoritySection.add(Record.parse(dataInputStream, bArr));
        }
        this.additionalSection = new ArrayList(unsignedShort5);
        for (int i4 = 0; i4 < unsignedShort5; i4++) {
            this.additionalSection.add(Record.parse(dataInputStream, bArr));
        }
        this.optRrPosition = getOptRrPosition(this.additionalSection);
    }

    private DnsMessage(DnsMessage dnsMessage) {
        this.answersMinTtlCache = -1L;
        this.f2046id = 0;
        this.f2047qr = dnsMessage.f2047qr;
        this.opcode = dnsMessage.opcode;
        this.authoritativeAnswer = dnsMessage.authoritativeAnswer;
        this.truncated = dnsMessage.truncated;
        this.recursionDesired = dnsMessage.recursionDesired;
        this.recursionAvailable = dnsMessage.recursionAvailable;
        this.authenticData = dnsMessage.authenticData;
        this.checkingDisabled = dnsMessage.checkingDisabled;
        this.responseCode = dnsMessage.responseCode;
        this.receiveTimestamp = dnsMessage.receiveTimestamp;
        this.questions = dnsMessage.questions;
        this.answerSection = dnsMessage.answerSection;
        this.authoritySection = dnsMessage.authoritySection;
        this.additionalSection = dnsMessage.additionalSection;
        this.optRrPosition = dnsMessage.optRrPosition;
    }

    private static int getOptRrPosition(List<Record<? extends Data>> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).type == Record.TYPE.OPT) {
                return i;
            }
        }
        return -1;
    }

    public byte[] toArray() {
        return (byte[]) serialize().clone();
    }

    public DatagramPacket asDatagram(InetAddress inetAddress, int i) {
        byte[] bArrSerialize = serialize();
        return new DatagramPacket(bArrSerialize, bArrSerialize.length, inetAddress, i);
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        writeTo(outputStream, true);
    }

    public void writeTo(OutputStream outputStream, boolean z) throws IOException {
        byte[] bArrSerialize = serialize();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        if (z) {
            dataOutputStream.writeShort(bArrSerialize.length);
        }
        dataOutputStream.write(bArrSerialize);
    }

    public ByteBuffer getInByteBuffer() {
        return ByteBuffer.wrap((byte[]) serialize().clone());
    }

    private byte[] serialize() {
        byte[] bArr = this.byteCache;
        if (bArr != null) {
            return bArr;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        int iCalculateHeaderBitmap = calculateHeaderBitmap();
        try {
            dataOutputStream.writeShort((short) this.f2046id);
            dataOutputStream.writeShort((short) iCalculateHeaderBitmap);
            List<Question> list = this.questions;
            if (list == null) {
                dataOutputStream.writeShort(0);
            } else {
                dataOutputStream.writeShort((short) list.size());
            }
            List<Record<? extends Data>> list2 = this.answerSection;
            if (list2 == null) {
                dataOutputStream.writeShort(0);
            } else {
                dataOutputStream.writeShort((short) list2.size());
            }
            List<Record<? extends Data>> list3 = this.authoritySection;
            if (list3 == null) {
                dataOutputStream.writeShort(0);
            } else {
                dataOutputStream.writeShort((short) list3.size());
            }
            List<Record<? extends Data>> list4 = this.additionalSection;
            if (list4 == null) {
                dataOutputStream.writeShort(0);
            } else {
                dataOutputStream.writeShort((short) list4.size());
            }
            List<Question> list5 = this.questions;
            if (list5 != null) {
                Iterator<Question> it = list5.iterator();
                while (it.hasNext()) {
                    dataOutputStream.write(it.next().toByteArray());
                }
            }
            List<Record<? extends Data>> list6 = this.answerSection;
            if (list6 != null) {
                Iterator<Record<? extends Data>> it2 = list6.iterator();
                while (it2.hasNext()) {
                    dataOutputStream.write(it2.next().toByteArray());
                }
            }
            List<Record<? extends Data>> list7 = this.authoritySection;
            if (list7 != null) {
                Iterator<Record<? extends Data>> it3 = list7.iterator();
                while (it3.hasNext()) {
                    dataOutputStream.write(it3.next().toByteArray());
                }
            }
            List<Record<? extends Data>> list8 = this.additionalSection;
            if (list8 != null) {
                Iterator<Record<? extends Data>> it4 = list8.iterator();
                while (it4.hasNext()) {
                    dataOutputStream.write(it4.next().toByteArray());
                }
            }
            dataOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.byteCache = byteArray;
            return byteArray;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    int calculateHeaderBitmap() {
        int value = this.f2047qr ? 32768 : 0;
        OPCODE opcode = this.opcode;
        if (opcode != null) {
            value += opcode.getValue() << AbstractJceStruct.STRUCT_END;
        }
        if (this.authoritativeAnswer) {
            value += 1024;
        }
        if (this.truncated) {
            value += 512;
        }
        if (this.recursionDesired) {
            value += 256;
        }
        if (this.recursionAvailable) {
            value += 128;
        }
        if (this.authenticData) {
            value += 32;
        }
        if (this.checkingDisabled) {
            value += 16;
        }
        RESPONSE_CODE response_code = this.responseCode;
        return response_code != null ? value + response_code.getValue() : value;
    }

    public Question getQuestion() {
        return this.questions.get(0);
    }

    public List<Question> copyQuestions() {
        ArrayList arrayList = new ArrayList(this.questions.size());
        arrayList.addAll(this.questions);
        return arrayList;
    }

    public List<Record<? extends Data>> copyAnswers() {
        ArrayList arrayList = new ArrayList(this.answerSection.size());
        arrayList.addAll(this.answerSection);
        return arrayList;
    }

    public List<Record<? extends Data>> copyAuthority() {
        ArrayList arrayList = new ArrayList(this.authoritySection.size());
        arrayList.addAll(this.authoritySection);
        return arrayList;
    }

    public Edns getEdns() {
        Edns edns = this.edns;
        if (edns != null) {
            return edns;
        }
        Record<OPT> optPseudoRecord = getOptPseudoRecord();
        if (optPseudoRecord == null) {
            return null;
        }
        Edns edns2 = new Edns(optPseudoRecord);
        this.edns = edns2;
        return edns2;
    }

    public Record<OPT> getOptPseudoRecord() {
        int i = this.optRrPosition;
        if (i == -1) {
            return null;
        }
        return (Record) this.additionalSection.get(i);
    }

    public boolean isDnssecOk() {
        Edns edns = getEdns();
        if (edns == null) {
            return false;
        }
        return edns.dnssecOk;
    }

    public String toString() {
        String str = this.toStringCache;
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder("DnsMessage");
        asBuilder().writeToStringBuilder(sb);
        String string = sb.toString();
        this.toStringCache = string;
        return string;
    }

    public String asTerminalOutput() {
        String str = this.terminalOutputCache;
        if (str != null) {
            return str;
        }
        StringBuilder sbAppend = new StringBuilder(";; ->>HEADER<<- opcode: ").append(this.opcode).append(", status: ").append(this.responseCode).append(", id: ").append(this.f2046id).append("\n;; flags:");
        if (!this.f2047qr) {
            sbAppend.append(" qr");
        }
        if (this.authoritativeAnswer) {
            sbAppend.append(" aa");
        }
        if (this.truncated) {
            sbAppend.append(" tr");
        }
        if (this.recursionDesired) {
            sbAppend.append(" rd");
        }
        if (this.recursionAvailable) {
            sbAppend.append(" ra");
        }
        if (this.authenticData) {
            sbAppend.append(" ad");
        }
        if (this.checkingDisabled) {
            sbAppend.append(" cd");
        }
        sbAppend.append("; QUERY: ").append(this.questions.size()).append(", ANSWER: ").append(this.answerSection.size()).append(", AUTHORITY: ").append(this.authoritySection.size()).append(", ADDITIONAL: ").append(this.additionalSection.size()).append("\n\n");
        Iterator<Record<? extends Data>> it = this.additionalSection.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Edns ednsFromRecord = Edns.fromRecord(it.next());
            if (ednsFromRecord != null) {
                sbAppend.append(";; OPT PSEUDOSECTION:\n; ").append(ednsFromRecord.asTerminalOutput());
                break;
            }
        }
        if (this.questions.size() != 0) {
            sbAppend.append(";; QUESTION SECTION:\n");
            Iterator<Question> it2 = this.questions.iterator();
            while (it2.hasNext()) {
                sbAppend.append(LogRecordStreamReader.FIELD_DELIMITER).append(it2.next().toString()).append('\n');
            }
        }
        if (this.authoritySection.size() != 0) {
            sbAppend.append("\n;; AUTHORITY SECTION:\n");
            Iterator<Record<? extends Data>> it3 = this.authoritySection.iterator();
            while (it3.hasNext()) {
                sbAppend.append(it3.next().toString()).append('\n');
            }
        }
        if (this.answerSection.size() != 0) {
            sbAppend.append("\n;; ANSWER SECTION:\n");
            Iterator<Record<? extends Data>> it4 = this.answerSection.iterator();
            while (it4.hasNext()) {
                sbAppend.append(it4.next().toString()).append('\n');
            }
        }
        if (this.additionalSection.size() != 0) {
            boolean z = false;
            for (Record<? extends Data> record : this.additionalSection) {
                if (record.type != Record.TYPE.OPT) {
                    if (!z) {
                        sbAppend.append("\n;; ADDITIONAL SECTION:\n");
                        z = true;
                    }
                    sbAppend.append(record.toString()).append('\n');
                }
            }
        }
        if (this.receiveTimestamp > 0) {
            sbAppend.append("\n;; WHEN: ").append(new Date(this.receiveTimestamp).toString());
        }
        String string = sbAppend.toString();
        this.terminalOutputCache = string;
        return string;
    }

    public <D extends Data> Set<D> getAnswersFor(Question question) {
        if (this.responseCode != RESPONSE_CODE.NO_ERROR) {
            return null;
        }
        HashSet hashSet = new HashSet(this.answerSection.size());
        for (Record<? extends Data> record : this.answerSection) {
            if (record.isAnswer(question) && !hashSet.add(record.getPayload())) {
                LOGGER.log(Level.WARNING, "DnsMessage contains duplicate answers. Record: " + record + "; DnsMessage: " + this);
            }
        }
        return hashSet;
    }

    public long getAnswersMinTtl() {
        long j = this.answersMinTtlCache;
        if (j >= 0) {
            return j;
        }
        this.answersMinTtlCache = Long.MAX_VALUE;
        Iterator<Record<? extends Data>> it = this.answerSection.iterator();
        while (it.hasNext()) {
            this.answersMinTtlCache = Math.min(this.answersMinTtlCache, it.next().ttl);
        }
        return this.answersMinTtlCache;
    }

    public Builder asBuilder() {
        return new Builder(this, null);
    }

    public DnsMessage asNormalizedVersion() {
        if (this.normalizedVersionCache == null) {
            this.normalizedVersionCache = new DnsMessage(this);
        }
        return this.normalizedVersionCache;
    }

    public Builder getResponseBuilder(RESPONSE_CODE response_code) {
        if (this.f2047qr) {
            throw new IllegalStateException();
        }
        return builder().setQrFlag(true).setResponseCode(response_code).setId(this.f2046id).setQuestion(getQuestion());
    }

    public int hashCode() {
        if (this.hashCodeCache == null) {
            this.hashCodeCache = Integer.valueOf(Arrays.hashCode(serialize()));
        }
        return this.hashCodeCache.intValue();
    }

    /* JADX INFO: renamed from: org.minidns.dnsmessage.DnsMessage$1 */
    static /* synthetic */ class C50751 {
        static final /* synthetic */ int[] $SwitchMap$org$minidns$dnsmessage$DnsMessage$SectionName;

        static {
            int[] iArr = new int[SectionName.values().length];
            $SwitchMap$org$minidns$dnsmessage$DnsMessage$SectionName = iArr;
            try {
                iArr[SectionName.answer.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$minidns$dnsmessage$DnsMessage$SectionName[SectionName.authority.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$minidns$dnsmessage$DnsMessage$SectionName[SectionName.additional.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private <D extends Data> List<Record<D>> filterSectionByType(boolean z, SectionName sectionName, Class<D> cls) {
        List<Record<? extends Data>> list;
        int i = C50751.$SwitchMap$org$minidns$dnsmessage$DnsMessage$SectionName[sectionName.ordinal()];
        if (i == 1) {
            list = this.answerSection;
        } else if (i == 2) {
            list = this.authoritySection;
        } else if (i == 3) {
            list = this.additionalSection;
        } else {
            throw new AssertionError("Unknown section name " + sectionName);
        }
        ArrayList arrayList = new ArrayList(z ? 1 : list.size());
        Iterator<Record<? extends Data>> it = list.iterator();
        while (it.hasNext()) {
            Object objIfPossibleAs = it.next().ifPossibleAs(cls);
            if (objIfPossibleAs != null) {
                arrayList.add(objIfPossibleAs);
                if (z) {
                    break;
                }
            }
        }
        return arrayList;
    }

    private <D extends Data> List<Record<D>> filterSectionByType(SectionName sectionName, Class<D> cls) {
        return filterSectionByType(false, sectionName, cls);
    }

    private <D extends Data> Record<D> getFirstOfType(SectionName sectionName, Class<D> cls) {
        List<Record<D>> listFilterSectionByType = filterSectionByType(true, sectionName, cls);
        if (listFilterSectionByType.isEmpty()) {
            return null;
        }
        return listFilterSectionByType.get(0);
    }

    public <D extends Data> List<Record<D>> filterAnswerSectionBy(Class<D> cls) {
        return filterSectionByType(SectionName.answer, cls);
    }

    public <D extends Data> List<Record<D>> filterAuthoritySectionBy(Class<D> cls) {
        return filterSectionByType(SectionName.authority, cls);
    }

    public <D extends Data> List<Record<D>> filterAdditionalSectionBy(Class<D> cls) {
        return filterSectionByType(SectionName.additional, cls);
    }

    public <D extends Data> Record<D> getFirstOfTypeFromAnswerSection(Class<D> cls) {
        return getFirstOfType(SectionName.answer, cls);
    }

    public <D extends Data> Record<D> getFirstOfTypeFromAuthoritySection(Class<D> cls) {
        return getFirstOfType(SectionName.authority, cls);
    }

    public <D extends Data> Record<D> getFirstOfTypeFromAdditionalSection(Class<D> cls) {
        return getFirstOfType(SectionName.additional, cls);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DnsMessage)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return Arrays.equals(serialize(), ((DnsMessage) obj).serialize());
    }

    public static Builder builder() {
        return new Builder((C50751) null);
    }

    public static final class Builder {
        private List<Record<? extends Data>> additionalSection;
        private List<Record<? extends Data>> answerSection;
        private boolean authenticData;
        private boolean authoritativeAnswer;
        private List<Record<? extends Data>> authoritySection;
        private boolean checkingDisabled;
        private Edns.Builder ednsBuilder;

        /* JADX INFO: renamed from: id */
        private int f2048id;
        private OPCODE opcode;
        private boolean query;
        private List<Question> questions;
        private long receiveTimestamp;
        private boolean recursionAvailable;
        private boolean recursionDesired;
        private RESPONSE_CODE responseCode;
        private boolean truncated;

        /* synthetic */ Builder(C50751 c50751) {
            this();
        }

        /* synthetic */ Builder(DnsMessage dnsMessage, C50751 c50751) {
            this(dnsMessage);
        }

        private Builder() {
            this.opcode = OPCODE.QUERY;
            this.responseCode = RESPONSE_CODE.NO_ERROR;
            this.receiveTimestamp = -1L;
        }

        private Builder(DnsMessage dnsMessage) {
            this.opcode = OPCODE.QUERY;
            this.responseCode = RESPONSE_CODE.NO_ERROR;
            this.receiveTimestamp = -1L;
            this.f2048id = dnsMessage.f2046id;
            this.opcode = dnsMessage.opcode;
            this.responseCode = dnsMessage.responseCode;
            this.query = dnsMessage.f2047qr;
            this.authoritativeAnswer = dnsMessage.authoritativeAnswer;
            this.truncated = dnsMessage.truncated;
            this.recursionDesired = dnsMessage.recursionDesired;
            this.recursionAvailable = dnsMessage.recursionAvailable;
            this.authenticData = dnsMessage.authenticData;
            this.checkingDisabled = dnsMessage.checkingDisabled;
            this.receiveTimestamp = dnsMessage.receiveTimestamp;
            ArrayList arrayList = new ArrayList(dnsMessage.questions.size());
            this.questions = arrayList;
            arrayList.addAll(dnsMessage.questions);
            ArrayList arrayList2 = new ArrayList(dnsMessage.answerSection.size());
            this.answerSection = arrayList2;
            arrayList2.addAll(dnsMessage.answerSection);
            ArrayList arrayList3 = new ArrayList(dnsMessage.authoritySection.size());
            this.authoritySection = arrayList3;
            arrayList3.addAll(dnsMessage.authoritySection);
            ArrayList arrayList4 = new ArrayList(dnsMessage.additionalSection.size());
            this.additionalSection = arrayList4;
            arrayList4.addAll(dnsMessage.additionalSection);
        }

        public Builder setId(int i) {
            this.f2048id = i & 65535;
            return this;
        }

        public Builder setOpcode(OPCODE opcode) {
            this.opcode = opcode;
            return this;
        }

        public Builder setResponseCode(RESPONSE_CODE response_code) {
            this.responseCode = response_code;
            return this;
        }

        public Builder setQrFlag(boolean z) {
            this.query = z;
            return this;
        }

        public Builder setAuthoritativeAnswer(boolean z) {
            this.authoritativeAnswer = z;
            return this;
        }

        public Builder setTruncated(boolean z) {
            this.truncated = z;
            return this;
        }

        public Builder setRecursionDesired(boolean z) {
            this.recursionDesired = z;
            return this;
        }

        public Builder setRecursionAvailable(boolean z) {
            this.recursionAvailable = z;
            return this;
        }

        public Builder setAuthenticData(boolean z) {
            this.authenticData = z;
            return this;
        }

        @Deprecated
        public Builder setCheckDisabled(boolean z) {
            this.checkingDisabled = z;
            return this;
        }

        public Builder setCheckingDisabled(boolean z) {
            this.checkingDisabled = z;
            return this;
        }

        public void copyFlagsFrom(DnsMessage dnsMessage) {
            this.query = dnsMessage.f2047qr;
            this.authoritativeAnswer = dnsMessage.authenticData;
            this.truncated = dnsMessage.truncated;
            this.recursionDesired = dnsMessage.recursionDesired;
            this.recursionAvailable = dnsMessage.recursionAvailable;
            this.authenticData = dnsMessage.authenticData;
            this.checkingDisabled = dnsMessage.checkingDisabled;
        }

        public Builder setReceiveTimestamp(long j) {
            this.receiveTimestamp = j;
            return this;
        }

        public Builder addQuestion(Question question) {
            if (this.questions == null) {
                this.questions = new ArrayList(1);
            }
            this.questions.add(question);
            return this;
        }

        public Builder setQuestions(List<Question> list) {
            this.questions = list;
            return this;
        }

        public Builder setQuestion(Question question) {
            ArrayList arrayList = new ArrayList(1);
            this.questions = arrayList;
            arrayList.add(question);
            return this;
        }

        public Builder addAnswer(Record<? extends Data> record) {
            if (this.answerSection == null) {
                this.answerSection = new ArrayList(1);
            }
            this.answerSection.add(record);
            return this;
        }

        public Builder addAnswers(Collection<Record<? extends Data>> collection) {
            if (this.answerSection == null) {
                this.answerSection = new ArrayList(collection.size());
            }
            this.answerSection.addAll(collection);
            return this;
        }

        public Builder setAnswers(Collection<Record<? extends Data>> collection) {
            ArrayList arrayList = new ArrayList(collection.size());
            this.answerSection = arrayList;
            arrayList.addAll(collection);
            return this;
        }

        public List<Record<? extends Data>> getAnswers() {
            List<Record<? extends Data>> list = this.answerSection;
            return list == null ? Collections.emptyList() : list;
        }

        public Builder addNameserverRecords(Record<? extends Data> record) {
            if (this.authoritySection == null) {
                this.authoritySection = new ArrayList(8);
            }
            this.authoritySection.add(record);
            return this;
        }

        public Builder setNameserverRecords(Collection<Record<? extends Data>> collection) {
            ArrayList arrayList = new ArrayList(collection.size());
            this.authoritySection = arrayList;
            arrayList.addAll(collection);
            return this;
        }

        public Builder setAdditionalResourceRecords(Collection<Record<? extends Data>> collection) {
            ArrayList arrayList = new ArrayList(collection.size());
            this.additionalSection = arrayList;
            arrayList.addAll(collection);
            return this;
        }

        public Builder addAdditionalResourceRecord(Record<? extends Data> record) {
            if (this.additionalSection == null) {
                this.additionalSection = new ArrayList();
            }
            this.additionalSection.add(record);
            return this;
        }

        public Builder addAdditionalResourceRecords(List<Record<? extends Data>> list) {
            if (this.additionalSection == null) {
                this.additionalSection = new ArrayList(list.size());
            }
            this.additionalSection.addAll(list);
            return this;
        }

        public List<Record<? extends Data>> getAdditionalResourceRecords() {
            List<Record<? extends Data>> list = this.additionalSection;
            return list == null ? Collections.emptyList() : list;
        }

        public Edns.Builder getEdnsBuilder() {
            if (this.ednsBuilder == null) {
                this.ednsBuilder = Edns.builder();
            }
            return this.ednsBuilder;
        }

        public DnsMessage build() {
            return new DnsMessage(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToStringBuilder(StringBuilder sb) {
            sb.append('(').append(this.f2048id).append(' ').append(this.opcode).append(' ').append(this.responseCode).append(' ');
            if (this.query) {
                sb.append("resp[qr=1]");
            } else {
                sb.append("query[qr=0]");
            }
            if (this.authoritativeAnswer) {
                sb.append(" aa");
            }
            if (this.truncated) {
                sb.append(" tr");
            }
            if (this.recursionDesired) {
                sb.append(" rd");
            }
            if (this.recursionAvailable) {
                sb.append(" ra");
            }
            if (this.authenticData) {
                sb.append(" ad");
            }
            if (this.checkingDisabled) {
                sb.append(" cd");
            }
            sb.append(")\n");
            List<Question> list = this.questions;
            if (list != null) {
                Iterator<Question> it = list.iterator();
                while (it.hasNext()) {
                    sb.append("[Q: ").append((Question) it.next()).append("]\n");
                }
            }
            List<Record<? extends Data>> list2 = this.answerSection;
            if (list2 != null) {
                Iterator<Record<? extends Data>> it2 = list2.iterator();
                while (it2.hasNext()) {
                    sb.append("[A: ").append((Record) it2.next()).append("]\n");
                }
            }
            List<Record<? extends Data>> list3 = this.authoritySection;
            if (list3 != null) {
                Iterator<Record<? extends Data>> it3 = list3.iterator();
                while (it3.hasNext()) {
                    sb.append("[N: ").append((Record) it3.next()).append("]\n");
                }
            }
            List<Record<? extends Data>> list4 = this.additionalSection;
            if (list4 != null) {
                for (Record<? extends Data> record : list4) {
                    sb.append("[X: ");
                    Edns ednsFromRecord = Edns.fromRecord(record);
                    if (ednsFromRecord != null) {
                        sb.append(ednsFromRecord.toString());
                    } else {
                        sb.append(record);
                    }
                    sb.append("]\n");
                }
            }
            if (sb.charAt(sb.length() - 1) == '\n') {
                sb.setLength(sb.length() - 1);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Builder of DnsMessage");
            writeToStringBuilder(sb);
            return sb.toString();
        }
    }
}
