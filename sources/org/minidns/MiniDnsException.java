package org.minidns;

import java.io.IOException;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsqueryresult.DnsQueryResult;

/* JADX INFO: loaded from: classes4.dex */
public abstract class MiniDnsException extends IOException {
    private static final long serialVersionUID = 1;

    protected MiniDnsException(String str) {
        super(str);
    }

    public static class IdMismatch extends MiniDnsException {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = 1;
        private final DnsMessage request;
        private final DnsMessage response;

        public IdMismatch(DnsMessage dnsMessage, DnsMessage dnsMessage2) {
            super(getString(dnsMessage, dnsMessage2));
            this.request = dnsMessage;
            this.response = dnsMessage2;
        }

        public DnsMessage getRequest() {
            return this.request;
        }

        public DnsMessage getResponse() {
            return this.response;
        }

        private static String getString(DnsMessage dnsMessage, DnsMessage dnsMessage2) {
            return "The response's ID doesn't matches the request ID. Request: " + dnsMessage.f2046id + ". Response: " + dnsMessage2.f2046id;
        }
    }

    public static class NullResultException extends MiniDnsException {
        private static final long serialVersionUID = 1;
        private final DnsMessage request;

        public NullResultException(DnsMessage dnsMessage) {
            super("The request yielded a 'null' result while resolving.");
            this.request = dnsMessage;
        }

        public DnsMessage getRequest() {
            return this.request;
        }
    }

    public static class ErrorResponseException extends MiniDnsException {
        private static final long serialVersionUID = 1;
        private final DnsMessage request;
        private final DnsQueryResult result;

        public ErrorResponseException(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult) {
            super("Received " + dnsQueryResult.response.responseCode + " error response\n" + dnsQueryResult);
            this.request = dnsMessage;
            this.result = dnsQueryResult;
        }

        public DnsMessage getRequest() {
            return this.request;
        }

        public DnsQueryResult getResult() {
            return this.result;
        }
    }

    public static class NoQueryPossibleException extends MiniDnsException {
        private static final long serialVersionUID = 1;
        private final DnsMessage request;

        public NoQueryPossibleException(DnsMessage dnsMessage) {
            super("No DNS server could be queried");
            this.request = dnsMessage;
        }

        public DnsMessage getRequest() {
            return this.request;
        }
    }
}
