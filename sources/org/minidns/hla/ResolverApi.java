package org.minidns.hla;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import org.minidns.AbstractDnsClient;
import org.minidns.DnsClient;
import org.minidns.dnslabel.DnsLabel;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.hla.srv.SrvProto;
import org.minidns.hla.srv.SrvService;
import org.minidns.hla.srv.SrvServiceProto;
import org.minidns.hla.srv.SrvType;
import org.minidns.iterative.ReliableDnsClient;
import org.minidns.record.Data;
import org.minidns.record.PTR;
import org.minidns.record.Record;
import org.minidns.record.SRV;

/* JADX INFO: loaded from: classes4.dex */
public class ResolverApi {
    public static final ResolverApi INSTANCE = new ResolverApi(new ReliableDnsClient());
    private final AbstractDnsClient dnsClient;

    public ResolverApi(AbstractDnsClient abstractDnsClient) {
        this.dnsClient = abstractDnsClient;
    }

    public final <D extends Data> ResolverResult<D> resolve(String str, Class<D> cls) throws IOException {
        return resolve(DnsName.from(str), cls);
    }

    public final <D extends Data> ResolverResult<D> resolve(DnsName dnsName, Class<D> cls) throws IOException {
        return resolve(new Question(dnsName, Record.TYPE.getType(cls)));
    }

    public <D extends Data> ResolverResult<D> resolve(Question question) throws IOException {
        return new ResolverResult<>(question, this.dnsClient.query(question), null);
    }

    public SrvResolverResult resolveSrv(SrvType srvType, String str) throws IOException {
        return resolveSrv(srvType.service, srvType.proto, DnsName.from(str));
    }

    public SrvResolverResult resolveSrv(SrvType srvType, DnsName dnsName) throws IOException {
        return resolveSrv(srvType.service, srvType.proto, dnsName);
    }

    public SrvResolverResult resolveSrv(SrvService srvService, SrvProto srvProto, String str) throws IOException {
        return resolveSrv(srvService.dnsLabel, srvProto.dnsLabel, DnsName.from(str));
    }

    public SrvResolverResult resolveSrv(SrvService srvService, SrvProto srvProto, DnsName dnsName) throws IOException {
        return resolveSrv(srvService.dnsLabel, srvProto.dnsLabel, dnsName);
    }

    public SrvResolverResult resolveSrv(DnsLabel dnsLabel, DnsLabel dnsLabel2, DnsName dnsName) throws IOException {
        return resolveSrv(dnsName, new SrvServiceProto(dnsLabel, dnsLabel2));
    }

    public SrvResolverResult resolveSrv(String str) throws IOException {
        return resolveSrv(DnsName.from(str));
    }

    public ResolverResult<PTR> reverseLookup(CharSequence charSequence) throws IOException {
        return reverseLookup(InetAddress.getByName(charSequence.toString()));
    }

    public ResolverResult<PTR> reverseLookup(InetAddress inetAddress) throws IOException {
        if (inetAddress instanceof Inet4Address) {
            return reverseLookup((Inet4Address) inetAddress);
        }
        if (inetAddress instanceof Inet6Address) {
            return reverseLookup((Inet6Address) inetAddress);
        }
        throw new IllegalArgumentException("The given InetAddress '" + inetAddress + "' is neither of type Inet4Address or Inet6Address");
    }

    public ResolverResult<PTR> reverseLookup(Inet4Address inet4Address) throws IOException {
        return resolve(DnsClient.getReverseIpLookupQuestionFor(inet4Address));
    }

    public ResolverResult<PTR> reverseLookup(Inet6Address inet6Address) throws IOException {
        return resolve(DnsClient.getReverseIpLookupQuestionFor(inet6Address));
    }

    public SrvResolverResult resolveSrv(DnsName dnsName) throws IOException {
        int labelCount = dnsName.getLabelCount();
        if (labelCount < 3) {
            throw new IllegalArgumentException();
        }
        DnsLabel label = dnsName.getLabel(labelCount - 1);
        int i = labelCount - 2;
        return resolveSrv(dnsName.stripToLabels(i), new SrvServiceProto(label, dnsName.getLabel(i)));
    }

    public SrvResolverResult resolveSrv(DnsName dnsName, SrvServiceProto srvServiceProto) throws IOException {
        return new SrvResolverResult(resolve(DnsName.from(srvServiceProto.service, srvServiceProto.proto, dnsName), SRV.class), srvServiceProto, this);
    }

    public final AbstractDnsClient getClient() {
        return this.dnsClient;
    }
}
