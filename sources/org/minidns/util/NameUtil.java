package org.minidns.util;

import org.minidns.dnsname.DnsName;

/* JADX INFO: loaded from: classes4.dex */
public final class NameUtil {
    public static boolean idnEquals(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equals(str2) || DnsName.from(str).compareTo(DnsName.from(str2)) == 0;
    }
}
