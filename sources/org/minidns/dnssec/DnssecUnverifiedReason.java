package org.minidns.dnssec;

import java.util.Collections;
import java.util.List;
import kotlin.UByte;
import org.minidns.constants.DnssecConstants;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.record.DNSKEY;
import org.minidns.record.Data;
import org.minidns.record.RRSIG;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public abstract class DnssecUnverifiedReason {
    public abstract String getReasonString();

    public String toString() {
        return getReasonString();
    }

    public int hashCode() {
        return getReasonString().hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof DnssecUnverifiedReason) && ((DnssecUnverifiedReason) obj).getReasonString().equals(getReasonString());
    }

    public static class AlgorithmNotSupportedReason extends DnssecUnverifiedReason {
        private final String algorithm;
        private final Record<? extends Data> record;
        private final Record.TYPE type;

        public AlgorithmNotSupportedReason(byte b, Record.TYPE type, Record<? extends Data> record) {
            this.algorithm = Integer.toString(b & UByte.MAX_VALUE);
            this.type = type;
            this.record = record;
        }

        @Override // org.minidns.dnssec.DnssecUnverifiedReason
        public String getReasonString() {
            return this.type.name() + " algorithm " + this.algorithm + " required to verify " + ((Object) this.record.name) + " is unknown or not supported by platform";
        }
    }

    public static class AlgorithmExceptionThrownReason extends DnssecUnverifiedReason {
        private final int algorithmNumber;
        private final String kind;
        private final Exception reason;
        private final Record<? extends Data> record;

        public AlgorithmExceptionThrownReason(DnssecConstants.DigestAlgorithm digestAlgorithm, String str, Record<? extends Data> record, Exception exc) {
            this.algorithmNumber = digestAlgorithm.value;
            this.kind = str;
            this.record = record;
            this.reason = exc;
        }

        @Override // org.minidns.dnssec.DnssecUnverifiedReason
        public String getReasonString() {
            return this.kind + " algorithm " + this.algorithmNumber + " threw exception while verifying " + ((Object) this.record.name) + ": " + this.reason;
        }
    }

    public static class ConflictsWithSep extends DnssecUnverifiedReason {
        private final Record<DNSKEY> record;

        public ConflictsWithSep(Record<DNSKEY> record) {
            this.record = record;
        }

        @Override // org.minidns.dnssec.DnssecUnverifiedReason
        public String getReasonString() {
            return "Zone " + this.record.name.ace + " is in list of known SEPs, but DNSKEY from response mismatches!";
        }
    }

    public static class NoTrustAnchorReason extends DnssecUnverifiedReason {
        private final DnsName zone;

        public NoTrustAnchorReason(DnsName dnsName) {
            this.zone = dnsName;
        }

        @Override // org.minidns.dnssec.DnssecUnverifiedReason
        public String getReasonString() {
            return "No trust anchor was found for zone " + ((Object) this.zone) + ". Try enabling DLV";
        }
    }

    public static class NoSecureEntryPointReason extends DnssecUnverifiedReason {
        private final DnsName zone;

        public NoSecureEntryPointReason(DnsName dnsName) {
            this.zone = dnsName;
        }

        @Override // org.minidns.dnssec.DnssecUnverifiedReason
        public String getReasonString() {
            return "No secure entry point was found for zone " + ((Object) this.zone);
        }
    }

    public static class NoRootSecureEntryPointReason extends DnssecUnverifiedReason {
        @Override // org.minidns.dnssec.DnssecUnverifiedReason
        public String getReasonString() {
            return "No secure entry point was found for the root zone (\"Did you forget to configure a root SEP?\")";
        }
    }

    public static class NoSignaturesReason extends DnssecUnverifiedReason {
        private final Question question;

        public NoSignaturesReason(Question question) {
            this.question = question;
        }

        @Override // org.minidns.dnssec.DnssecUnverifiedReason
        public String getReasonString() {
            return "No signatures were attached to answer on question for " + this.question.type + " at " + ((Object) this.question.name);
        }
    }

    public static class NoActiveSignaturesReason extends DnssecUnverifiedReason {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final List<RRSIG> outdatedRrSigs;
        private final Question question;

        public NoActiveSignaturesReason(Question question, List<RRSIG> list) {
            this.question = question;
            this.outdatedRrSigs = Collections.unmodifiableList(list);
        }

        @Override // org.minidns.dnssec.DnssecUnverifiedReason
        public String getReasonString() {
            return "No currently active signatures were attached to answer on question for " + this.question.type + " at " + ((Object) this.question.name);
        }

        public List<RRSIG> getOutdatedRrSigs() {
            return this.outdatedRrSigs;
        }
    }

    public static class NSECDoesNotMatchReason extends DnssecUnverifiedReason {
        private final Question question;
        private final Record<? extends Data> record;

        public NSECDoesNotMatchReason(Question question, Record<? extends Data> record) {
            this.question = question;
            this.record = record;
        }

        @Override // org.minidns.dnssec.DnssecUnverifiedReason
        public String getReasonString() {
            return "NSEC " + ((Object) this.record.name) + " does nat match question for " + this.question.type + " at " + ((Object) this.question.name);
        }
    }
}
