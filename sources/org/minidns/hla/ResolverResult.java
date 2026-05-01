package org.minidns.hla;

import java.util.Collections;
import java.util.Set;
import org.minidns.MiniDnsException;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsqueryresult.DnsQueryResult;
import org.minidns.dnssec.DnssecResultNotAuthenticException;
import org.minidns.dnssec.DnssecUnverifiedReason;
import org.minidns.record.Data;

/* JADX INFO: loaded from: classes4.dex */
public class ResolverResult<D extends Data> {
    protected final DnsMessage answer;
    private final Set<D> data;
    private DnssecResultNotAuthenticException dnssecResultNotAuthenticException;
    private final boolean isAuthenticData;
    protected final Question question;
    private ResolutionUnsuccessfulException resolutionUnsuccessfulException;
    private final DnsMessage.RESPONSE_CODE responseCode;
    protected final DnsQueryResult result;
    protected final Set<DnssecUnverifiedReason> unverifiedReasons;

    ResolverResult(Question question, DnsQueryResult dnsQueryResult, Set<DnssecUnverifiedReason> set) throws MiniDnsException.NullResultException {
        if (dnsQueryResult == null) {
            throw new MiniDnsException.NullResultException(question.asMessageBuilder().build());
        }
        this.result = dnsQueryResult;
        DnsMessage dnsMessage = dnsQueryResult.response;
        this.question = question;
        this.responseCode = dnsMessage.responseCode;
        this.answer = dnsMessage;
        Set<D> answersFor = dnsMessage.getAnswersFor(question);
        if (answersFor == null) {
            this.data = Collections.emptySet();
        } else {
            this.data = Collections.unmodifiableSet(answersFor);
        }
        if (set == null) {
            this.unverifiedReasons = null;
            this.isAuthenticData = false;
        } else {
            Set<DnssecUnverifiedReason> setUnmodifiableSet = Collections.unmodifiableSet(set);
            this.unverifiedReasons = setUnmodifiableSet;
            this.isAuthenticData = setUnmodifiableSet.isEmpty();
        }
    }

    public boolean wasSuccessful() {
        return this.responseCode == DnsMessage.RESPONSE_CODE.NO_ERROR;
    }

    public Set<D> getAnswers() {
        throwIseIfErrorResponse();
        return this.data;
    }

    public Set<D> getAnswersOrEmptySet() {
        return this.data;
    }

    public DnsMessage.RESPONSE_CODE getResponseCode() {
        return this.responseCode;
    }

    public boolean isAuthenticData() {
        throwIseIfErrorResponse();
        return this.isAuthenticData;
    }

    public Set<DnssecUnverifiedReason> getUnverifiedReasons() {
        throwIseIfErrorResponse();
        return this.unverifiedReasons;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void throwIfErrorResponse() throws ResolutionUnsuccessfulException {
        ResolutionUnsuccessfulException resolutionUnsuccessfulException = getResolutionUnsuccessfulException();
        if (resolutionUnsuccessfulException != null) {
            throw resolutionUnsuccessfulException;
        }
    }

    public ResolutionUnsuccessfulException getResolutionUnsuccessfulException() {
        if (wasSuccessful()) {
            return null;
        }
        if (this.resolutionUnsuccessfulException == null) {
            this.resolutionUnsuccessfulException = new ResolutionUnsuccessfulException(this.question, this.responseCode);
        }
        return this.resolutionUnsuccessfulException;
    }

    public DnssecResultNotAuthenticException getDnssecResultNotAuthenticException() {
        if (!wasSuccessful() || this.isAuthenticData) {
            return null;
        }
        if (this.dnssecResultNotAuthenticException == null) {
            this.dnssecResultNotAuthenticException = DnssecResultNotAuthenticException.from(getUnverifiedReasons());
        }
        return this.dnssecResultNotAuthenticException;
    }

    public DnsMessage getRawAnswer() {
        return this.answer;
    }

    public DnsQueryResult getDnsQueryResult() {
        return this.result;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append("\nQuestion: ").append(this.question).append("\nResponse Code: ").append(this.responseCode).append('\n');
        if (this.responseCode == DnsMessage.RESPONSE_CODE.NO_ERROR) {
            if (this.isAuthenticData) {
                sb.append("Results verified via DNSSEC\n");
            }
            if (hasUnverifiedReasons()) {
                sb.append(this.unverifiedReasons).append('\n');
            }
            sb.append(this.answer.answerSection);
        }
        return sb.toString();
    }

    boolean hasUnverifiedReasons() {
        Set<DnssecUnverifiedReason> set = this.unverifiedReasons;
        return (set == null || set.isEmpty()) ? false : true;
    }

    protected void throwIseIfErrorResponse() {
        ResolutionUnsuccessfulException resolutionUnsuccessfulException = getResolutionUnsuccessfulException();
        if (resolutionUnsuccessfulException != null) {
            throw new IllegalStateException("Can not perform operation because the DNS resolution was unsuccessful", resolutionUnsuccessfulException);
        }
    }
}
