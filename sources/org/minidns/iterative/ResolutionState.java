package org.minidns.iterative;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.iterative.IterativeClientException;

/* JADX INFO: loaded from: classes4.dex */
public class ResolutionState {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final HashMap<InetAddress, Set<Question>> map = new HashMap<>();
    private final IterativeDnsClient recursiveDnsClient;
    private int steps;

    ResolutionState(IterativeDnsClient iterativeDnsClient) {
        this.recursiveDnsClient = iterativeDnsClient;
    }

    void recurse(InetAddress inetAddress, DnsMessage dnsMessage) throws IterativeClientException.MaxIterativeStepsReached, IterativeClientException.LoopDetected {
        Question question = dnsMessage.getQuestion();
        if (!this.map.containsKey(inetAddress)) {
            this.map.put(inetAddress, new HashSet());
        } else if (this.map.get(inetAddress).contains(question)) {
            throw new IterativeClientException.LoopDetected(inetAddress, question);
        }
        int i = this.steps + 1;
        this.steps = i;
        if (i > this.recursiveDnsClient.maxSteps) {
            throw new IterativeClientException.MaxIterativeStepsReached();
        }
        this.map.get(inetAddress).add(question);
    }

    void decrementSteps() {
        this.steps--;
    }
}
