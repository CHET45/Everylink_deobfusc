package org.minidns.hla.srv;

/* JADX INFO: loaded from: classes4.dex */
public enum SrvType {
    xmpp_client(SrvService.xmpp_client, SrvProto.tcp),
    xmpp_server(SrvService.xmpp_server, SrvProto.tcp);

    public final SrvProto proto;
    public final SrvService service;

    SrvType(SrvService srvService, SrvProto srvProto) {
        this.service = srvService;
        this.proto = srvProto;
    }
}
