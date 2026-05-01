package org.minidns.dnsserverlookup;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.minidns.util.PlatformDetection;

/* JADX INFO: loaded from: classes4.dex */
public final class UnixUsingEtcResolvConf extends AbstractDnsServerLookupMechanism {
    public static final DnsServerLookupMechanism INSTANCE = new UnixUsingEtcResolvConf();
    private static final Logger LOGGER = Logger.getLogger(UnixUsingEtcResolvConf.class.getName());
    private static final Pattern NAMESERVER_PATTERN = Pattern.compile("^nameserver\\s+(.*)$");
    public static final int PRIORITY = 2000;
    private static final String RESOLV_CONF_FILE = "/etc/resolv.conf";
    private static List<String> cached;
    private static long lastModified;

    private UnixUsingEtcResolvConf() {
        super("UnixUsingEtcResolvConf", 2000);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r6v0, types: [long] */
    @Override // org.minidns.dnsserverlookup.AbstractDnsServerLookupMechanism, org.minidns.dnsserverlookup.DnsServerLookupMechanism
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<java.lang.String> getDnsServerAddresses() throws java.lang.Throwable {
        /*
            r9 = this;
            java.lang.String r0 = "Could not close reader"
            java.io.File r1 = new java.io.File
            java.lang.String r2 = "/etc/resolv.conf"
            r1.<init>(r2)
            boolean r2 = r1.exists()
            r3 = 0
            if (r2 != 0) goto L11
            return r3
        L11:
            long r4 = r1.lastModified()
            long r6 = org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.lastModified
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 != 0) goto L20
            java.util.List<java.lang.String> r2 = org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.cached
            if (r2 == 0) goto L20
            return r2
        L20:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L76 java.io.IOException -> L78
            java.io.InputStreamReader r7 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L76 java.io.IOException -> L78
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L76 java.io.IOException -> L78
            r8.<init>(r1)     // Catch: java.lang.Throwable -> L76 java.io.IOException -> L78
            java.nio.charset.Charset r1 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L76 java.io.IOException -> L78
            r7.<init>(r8, r1)     // Catch: java.lang.Throwable -> L76 java.io.IOException -> L78
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L76 java.io.IOException -> L78
        L36:
            java.lang.String r1 = r6.readLine()     // Catch: java.io.IOException -> L74 java.lang.Throwable -> L92
            if (r1 == 0) goto L55
            java.util.regex.Pattern r7 = org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.NAMESERVER_PATTERN     // Catch: java.io.IOException -> L74 java.lang.Throwable -> L92
            java.util.regex.Matcher r1 = r7.matcher(r1)     // Catch: java.io.IOException -> L74 java.lang.Throwable -> L92
            boolean r7 = r1.matches()     // Catch: java.io.IOException -> L74 java.lang.Throwable -> L92
            if (r7 == 0) goto L36
            r7 = 1
            java.lang.String r1 = r1.group(r7)     // Catch: java.io.IOException -> L74 java.lang.Throwable -> L92
            java.lang.String r1 = r1.trim()     // Catch: java.io.IOException -> L74 java.lang.Throwable -> L92
            r2.add(r1)     // Catch: java.io.IOException -> L74 java.lang.Throwable -> L92
            goto L36
        L55:
            r6.close()     // Catch: java.io.IOException -> L59
            goto L61
        L59:
            r1 = move-exception
            java.util.logging.Logger r6 = org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.LOGGER
            java.util.logging.Level r7 = java.util.logging.Level.WARNING
            r6.log(r7, r0, r1)
        L61:
            boolean r0 = r2.isEmpty()
            if (r0 == 0) goto L6f
            java.util.logging.Logger r0 = org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.LOGGER
            java.lang.String r1 = "Could not find any nameservers in /etc/resolv.conf"
            r0.fine(r1)
            return r3
        L6f:
            org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.cached = r2
            org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.lastModified = r4
            return r2
        L74:
            r1 = move-exception
            goto L7a
        L76:
            r1 = move-exception
            goto L94
        L78:
            r1 = move-exception
            r6 = r3
        L7a:
            java.util.logging.Logger r2 = org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.LOGGER     // Catch: java.lang.Throwable -> L92
            java.util.logging.Level r4 = java.util.logging.Level.WARNING     // Catch: java.lang.Throwable -> L92
            java.lang.String r5 = "Could not read from /etc/resolv.conf"
            r2.log(r4, r5, r1)     // Catch: java.lang.Throwable -> L92
            if (r6 == 0) goto L91
            r6.close()     // Catch: java.io.IOException -> L89
            goto L91
        L89:
            r1 = move-exception
            java.util.logging.Logger r2 = org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.LOGGER
            java.util.logging.Level r4 = java.util.logging.Level.WARNING
            r2.log(r4, r0, r1)
        L91:
            return r3
        L92:
            r1 = move-exception
            r3 = r6
        L94:
            if (r3 == 0) goto La2
            r3.close()     // Catch: java.io.IOException -> L9a
            goto La2
        L9a:
            r2 = move-exception
            java.util.logging.Logger r3 = org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.LOGGER
            java.util.logging.Level r4 = java.util.logging.Level.WARNING
            r3.log(r4, r0, r2)
        La2:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.minidns.dnsserverlookup.UnixUsingEtcResolvConf.getDnsServerAddresses():java.util.List");
    }

    @Override // org.minidns.dnsserverlookup.DnsServerLookupMechanism
    public boolean isAvailable() {
        if (PlatformDetection.isAndroid()) {
            return false;
        }
        try {
            return new File(RESOLV_CONF_FILE).exists();
        } catch (SecurityException e) {
            LOGGER.log(Level.FINE, "Access to /etc/resolv.conf not possible", (Throwable) e);
            return false;
        }
    }
}
