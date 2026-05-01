package org.minidns.dnssec;

import com.github.houbb.heaven.constant.CharConst;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.minidns.MiniDnsException;

/* JADX INFO: loaded from: classes4.dex */
public final class DnssecResultNotAuthenticException extends MiniDnsException {
    private static final long serialVersionUID = 1;
    private final Set<DnssecUnverifiedReason> unverifiedReasons;

    private DnssecResultNotAuthenticException(String str, Set<DnssecUnverifiedReason> set) {
        super(str);
        if (set.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.unverifiedReasons = Collections.unmodifiableSet(set);
    }

    public static DnssecResultNotAuthenticException from(Set<DnssecUnverifiedReason> set) {
        StringBuilder sb = new StringBuilder("DNSSEC result not authentic. Reasons: ");
        Iterator<DnssecUnverifiedReason> it = set.iterator();
        while (it.hasNext()) {
            sb.append(it.next()).append(CharConst.DOT);
        }
        return new DnssecResultNotAuthenticException(sb.toString(), set);
    }

    public Set<DnssecUnverifiedReason> getUnverifiedReasons() {
        return this.unverifiedReasons;
    }
}
