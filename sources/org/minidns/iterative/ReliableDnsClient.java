package org.minidns.iterative;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import org.minidns.AbstractDnsClient;
import org.minidns.DnsCache;
import org.minidns.DnsClient;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsqueryresult.DnsQueryResult;
import org.minidns.source.DnsDataSource;
import org.minidns.util.MultipleIoException;

/* JADX INFO: loaded from: classes4.dex */
public class ReliableDnsClient extends AbstractDnsClient {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final DnsClient dnsClient;
    private Mode mode;
    private final IterativeDnsClient recursiveDnsClient;

    public enum Mode {
        recursiveWithIterativeFallback,
        recursiveOnly,
        iterativeOnly
    }

    protected String isResponseAcceptable(DnsMessage dnsMessage) {
        return null;
    }

    @Override // org.minidns.AbstractDnsClient
    protected DnsMessage.Builder newQuestion(DnsMessage.Builder builder) {
        return builder;
    }

    public ReliableDnsClient(DnsCache dnsCache) {
        super(dnsCache);
        this.mode = Mode.recursiveWithIterativeFallback;
        this.recursiveDnsClient = new IterativeDnsClient(dnsCache) { // from class: org.minidns.iterative.ReliableDnsClient.1
            @Override // org.minidns.iterative.IterativeDnsClient, org.minidns.AbstractDnsClient
            protected DnsMessage.Builder newQuestion(DnsMessage.Builder builder) {
                return ReliableDnsClient.this.newQuestion(super.newQuestion(builder));
            }

            @Override // org.minidns.iterative.IterativeDnsClient, org.minidns.AbstractDnsClient
            protected boolean isResponseCacheable(Question question, DnsQueryResult dnsQueryResult) {
                return ReliableDnsClient.this.isResponseCacheable(question, dnsQueryResult) && super.isResponseCacheable(question, dnsQueryResult);
            }
        };
        this.dnsClient = new DnsClient(dnsCache) { // from class: org.minidns.iterative.ReliableDnsClient.2
            @Override // org.minidns.DnsClient, org.minidns.AbstractDnsClient
            protected DnsMessage.Builder newQuestion(DnsMessage.Builder builder) {
                return ReliableDnsClient.this.newQuestion(super.newQuestion(builder));
            }

            @Override // org.minidns.AbstractDnsClient
            protected boolean isResponseCacheable(Question question, DnsQueryResult dnsQueryResult) {
                return ReliableDnsClient.this.isResponseCacheable(question, dnsQueryResult) && super.isResponseCacheable(question, dnsQueryResult);
            }
        };
    }

    public ReliableDnsClient() {
        this(DEFAULT_CACHE);
    }

    @Override // org.minidns.AbstractDnsClient
    protected DnsQueryResult query(DnsMessage.Builder builder) throws IOException {
        DnsQueryResult dnsQueryResultQuery;
        String str;
        String str2;
        LinkedList linkedList = new LinkedList();
        String str3 = null;
        DnsQueryResult dnsQueryResultQuery2 = null;
        str3 = null;
        if (this.mode != Mode.iterativeOnly) {
            try {
                dnsQueryResultQuery = this.dnsClient.query(builder);
                if (dnsQueryResultQuery != null) {
                    try {
                        String strIsResponseAcceptable = isResponseAcceptable(dnsQueryResultQuery.response);
                        if (strIsResponseAcceptable == null) {
                            return dnsQueryResultQuery;
                        }
                        str3 = strIsResponseAcceptable;
                    } catch (IOException e) {
                        e = e;
                        linkedList.add(e);
                    }
                }
            } catch (IOException e2) {
                e = e2;
                dnsQueryResultQuery = null;
            }
            String str4 = str3;
            dnsQueryResultQuery2 = dnsQueryResultQuery;
            str = str4;
        } else {
            str = null;
        }
        if (this.mode == Mode.recursiveOnly) {
            return dnsQueryResultQuery2;
        }
        Level level = Level.FINE;
        if (LOGGER.isLoggable(level) && this.mode != Mode.iterativeOnly) {
            if (!linkedList.isEmpty()) {
                str2 = "Resolution fall back to iterative mode because: " + linkedList.get(0);
            } else if (dnsQueryResultQuery2 == null) {
                str2 = "Resolution fall back to iterative mode because:  DnsClient did not return a response";
            } else if (str != null) {
                str2 = "Resolution fall back to iterative mode because: " + str + ". Response:\n" + dnsQueryResultQuery2;
            } else {
                throw new AssertionError("This should never been reached");
            }
            LOGGER.log(level, str2);
        }
        try {
            dnsQueryResultQuery2 = this.recursiveDnsClient.query(builder);
        } catch (IOException e3) {
            linkedList.add(e3);
        }
        if (dnsQueryResultQuery2 == null) {
            MultipleIoException.throwIfRequired(linkedList);
        }
        return dnsQueryResultQuery2;
    }

    @Override // org.minidns.AbstractDnsClient
    protected boolean isResponseCacheable(Question question, DnsQueryResult dnsQueryResult) {
        return isResponseAcceptable(dnsQueryResult.response) == null;
    }

    @Override // org.minidns.AbstractDnsClient
    public void setDataSource(DnsDataSource dnsDataSource) {
        super.setDataSource(dnsDataSource);
        this.recursiveDnsClient.setDataSource(dnsDataSource);
        this.dnsClient.setDataSource(dnsDataSource);
    }

    public void setMode(Mode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("Mode must not be null.");
        }
        this.mode = mode;
    }

    public void setUseHardcodedDnsServers(boolean z) {
        this.dnsClient.setUseHardcodedDnsServers(z);
    }
}
