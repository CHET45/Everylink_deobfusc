package org.minidns;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Data;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public final class RrSet {
    public final Record.CLASS clazz;
    public final DnsName name;
    public final Set<Record<? extends Data>> records;
    public final Record.TYPE type;

    private RrSet(DnsName dnsName, Record.TYPE type, Record.CLASS r3, Set<Record<? extends Data>> set) {
        this.name = dnsName;
        this.type = type;
        this.clazz = r3;
        this.records = Collections.unmodifiableSet(set);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) this.name).append('\t').append(this.clazz).append('\t').append(this.type).append('\n');
        Iterator<Record<? extends Data>> it = this.records.iterator();
        while (it.hasNext()) {
            sb.append(it.next()).append('\n');
        }
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Record.CLASS clazz;
        private DnsName name;
        Set<Record<? extends Data>> records;
        private Record.TYPE type;

        private Builder() {
            this.records = new LinkedHashSet(8);
        }

        public Builder addRecord(Record<? extends Data> record) {
            if (this.name == null) {
                this.name = record.name;
                this.type = record.type;
                this.clazz = record.clazz;
            } else if (!couldContain(record)) {
                throw new IllegalArgumentException("Can not add " + record + " to RRSet " + ((Object) this.name) + ' ' + this.type + ' ' + this.clazz);
            }
            this.records.add(record);
            return this;
        }

        public boolean couldContain(Record<? extends Data> record) {
            DnsName dnsName = this.name;
            if (dnsName == null) {
                return true;
            }
            return dnsName.equals(record.name) && this.type == record.type && this.clazz == record.clazz;
        }

        public boolean addIfPossible(Record<? extends Data> record) {
            if (!couldContain(record)) {
                return false;
            }
            addRecord(record);
            return true;
        }

        public RrSet build() {
            if (this.name == null) {
                throw new IllegalStateException();
            }
            return new RrSet(this.name, this.type, this.clazz, this.records);
        }
    }
}
