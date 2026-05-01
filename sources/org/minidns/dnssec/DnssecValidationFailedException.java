package org.minidns.dnssec;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.record.Data;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class DnssecValidationFailedException extends IOException {
    private static final long serialVersionUID = 5413184667629832742L;

    public DnssecValidationFailedException(Question question, String str) {
        super("Validation of request to " + question + " failed: " + str);
    }

    public DnssecValidationFailedException(String str) {
        super(str);
    }

    public DnssecValidationFailedException(String str, Throwable th) {
        super(str, th);
    }

    public DnssecValidationFailedException(Record<? extends Data> record, String str) {
        super("Validation of record " + record + " failed: " + str);
    }

    public DnssecValidationFailedException(List<Record<? extends Data>> list, String str) {
        super("Validation of " + list.size() + " " + list.get(0).type + " record" + (list.size() > 1 ? "s" : "") + " failed: " + str);
    }

    public static class DataMalformedException extends DnssecValidationFailedException {
        private static final long serialVersionUID = 1;
        private final byte[] data;

        public DataMalformedException(IOException iOException, byte[] bArr) {
            super("Malformed data", iOException);
            this.data = bArr;
        }

        public DataMalformedException(String str, IOException iOException, byte[] bArr) {
            super(str, iOException);
            this.data = bArr;
        }

        public byte[] getData() {
            return this.data;
        }
    }

    public static class DnssecInvalidKeySpecException extends DnssecValidationFailedException {
        private static final long serialVersionUID = 1;

        public DnssecInvalidKeySpecException(InvalidKeySpecException invalidKeySpecException) {
            super("Invalid key spec", invalidKeySpecException);
        }

        public DnssecInvalidKeySpecException(String str, InvalidKeySpecException invalidKeySpecException, byte[] bArr) {
            super(str, invalidKeySpecException);
        }
    }

    public static class AuthorityDoesNotContainSoa extends DnssecValidationFailedException {
        private static final long serialVersionUID = 1;
        private final DnsMessage response;

        public AuthorityDoesNotContainSoa(DnsMessage dnsMessage) {
            super("Autority does not contain SOA");
            this.response = dnsMessage;
        }

        public DnsMessage getResponse() {
            return this.response;
        }
    }
}
