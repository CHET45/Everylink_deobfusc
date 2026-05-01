package org.minidns.iterative;

import java.net.InetAddress;
import org.minidns.MiniDnsException;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.dnsqueryresult.DnsQueryResult;

/* JADX INFO: loaded from: classes4.dex */
public abstract class IterativeClientException extends MiniDnsException {
    private static final long serialVersionUID = 1;

    protected IterativeClientException(String str) {
        super(str);
    }

    public static class LoopDetected extends IterativeClientException {
        private static final long serialVersionUID = 1;
        public final InetAddress address;
        public final Question question;

        public LoopDetected(InetAddress inetAddress, Question question) {
            super("Resolution loop detected: We already asked " + inetAddress + " about " + question);
            this.address = inetAddress;
            this.question = question;
        }
    }

    public static class MaxIterativeStepsReached extends IterativeClientException {
        private static final long serialVersionUID = 1;

        public MaxIterativeStepsReached() {
            super("Maxmimum steps reached");
        }
    }

    public static class NotAuthoritativeNorGlueRrFound extends IterativeClientException {
        private static final long serialVersionUID = 1;
        private final DnsName authoritativeZone;
        private final DnsMessage request;
        private final DnsQueryResult result;

        public NotAuthoritativeNorGlueRrFound(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult, DnsName dnsName) {
            super("Did not receive an authoritative answer, nor did the result contain any glue records");
            this.request = dnsMessage;
            this.result = dnsQueryResult;
            this.authoritativeZone = dnsName;
        }

        public DnsMessage getRequest() {
            return this.request;
        }

        public DnsQueryResult getResult() {
            return this.result;
        }

        public DnsName getAuthoritativeZone() {
            return this.authoritativeZone;
        }
    }
}
