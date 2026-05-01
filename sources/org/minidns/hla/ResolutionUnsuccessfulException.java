package org.minidns.hla;

import org.minidns.MiniDnsException;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;

/* JADX INFO: loaded from: classes4.dex */
public class ResolutionUnsuccessfulException extends MiniDnsException {
    private static final long serialVersionUID = 1;
    public final Question question;
    public final DnsMessage.RESPONSE_CODE responseCode;

    public ResolutionUnsuccessfulException(Question question, DnsMessage.RESPONSE_CODE response_code) {
        super("Asking for " + question + " yielded an error response " + response_code);
        this.question = question;
        this.responseCode = response_code;
    }
}
