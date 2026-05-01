package org.minidns.edns;

import android.support.v4.media.session.PlaybackStateCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Data;
import org.minidns.record.OPT;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class Edns {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int FLAG_DNSSEC_OK = 32768;
    public final boolean dnssecOk;
    public final int extendedRcode;
    public final int flags;
    private Record<OPT> optRecord;
    private String terminalOutputCache;
    public final int udpPayloadSize;
    public final List<EdnsOption> variablePart;
    public final int version;

    public enum OptionCode {
        UNKNOWN(-1, UnknownEdnsOption.class),
        NSID(3, Nsid.class);

        private static Map<Integer, OptionCode> INVERSE_LUT = new HashMap(values().length);
        public final int asInt;
        public final Class<? extends EdnsOption> clazz;

        static {
            for (OptionCode optionCode : values()) {
                INVERSE_LUT.put(Integer.valueOf(optionCode.asInt), optionCode);
            }
        }

        OptionCode(int i, Class cls) {
            this.asInt = i;
            this.clazz = cls;
        }

        public static OptionCode from(int i) {
            OptionCode optionCode = INVERSE_LUT.get(Integer.valueOf(i));
            return optionCode == null ? UNKNOWN : optionCode;
        }
    }

    public Edns(Record<OPT> record) {
        this.udpPayloadSize = record.clazzValue;
        this.extendedRcode = (int) ((record.ttl >> 8) & 255);
        this.version = (int) ((record.ttl >> 16) & 255);
        this.flags = ((int) record.ttl) & 65535;
        this.dnssecOk = (record.ttl & PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID) > 0;
        this.variablePart = ((OPT) record.payloadData).variablePart;
        this.optRecord = record;
    }

    public Edns(Builder builder) {
        this.udpPayloadSize = builder.udpPayloadSize;
        this.extendedRcode = builder.extendedRcode;
        this.version = builder.version;
        int i = builder.dnssecOk ? 32768 : 0;
        this.dnssecOk = builder.dnssecOk;
        this.flags = i;
        if (builder.variablePart == null) {
            this.variablePart = Collections.emptyList();
        } else {
            this.variablePart = builder.variablePart;
        }
    }

    public <O extends EdnsOption> O getEdnsOption(OptionCode optionCode) {
        Iterator<EdnsOption> it = this.variablePart.iterator();
        while (it.hasNext()) {
            O o = (O) it.next();
            if (o.getOptionCode().equals(optionCode)) {
                return o;
            }
        }
        return null;
    }

    public Record<OPT> asRecord() {
        if (this.optRecord == null) {
            this.optRecord = new Record<>(DnsName.ROOT, Record.TYPE.OPT, this.udpPayloadSize, ((long) this.flags) | ((long) (this.extendedRcode << 8)) | ((long) (this.version << 16)), new OPT(this.variablePart));
        }
        return this.optRecord;
    }

    public String asTerminalOutput() {
        if (this.terminalOutputCache == null) {
            StringBuilder sb = new StringBuilder("EDNS: version: ");
            sb.append(this.version).append(", flags:");
            if (this.dnssecOk) {
                sb.append(" do");
            }
            sb.append("; udp: ").append(this.udpPayloadSize);
            if (!this.variablePart.isEmpty()) {
                sb.append('\n');
                Iterator<EdnsOption> it = this.variablePart.iterator();
                while (it.hasNext()) {
                    EdnsOption next = it.next();
                    sb.append(next.getOptionCode()).append(": ");
                    sb.append(next.asTerminalOutput());
                    if (it.hasNext()) {
                        sb.append('\n');
                    }
                }
            }
            this.terminalOutputCache = sb.toString();
        }
        return this.terminalOutputCache;
    }

    public String toString() {
        return asTerminalOutput();
    }

    public static Edns fromRecord(Record<? extends Data> record) {
        if (record.type != Record.TYPE.OPT) {
            return null;
        }
        return new Edns((Record<OPT>) record);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean dnssecOk;
        private int extendedRcode;
        private int udpPayloadSize;
        private List<EdnsOption> variablePart;
        private int version;

        private Builder() {
        }

        public Builder setUdpPayloadSize(int i) {
            if (i > 65535) {
                throw new IllegalArgumentException("UDP payload size must not be greater than 65536, was " + i);
            }
            this.udpPayloadSize = i;
            return this;
        }

        public Builder setDnssecOk(boolean z) {
            this.dnssecOk = z;
            return this;
        }

        public Builder setDnssecOk() {
            this.dnssecOk = true;
            return this;
        }

        public Builder addEdnsOption(EdnsOption ednsOption) {
            if (this.variablePart == null) {
                this.variablePart = new ArrayList(4);
            }
            this.variablePart.add(ednsOption);
            return this;
        }

        public Edns build() {
            return new Edns(this);
        }
    }
}
