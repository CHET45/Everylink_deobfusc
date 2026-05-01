package org.minidns.dnsserverlookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.minidns.util.PlatformDetection;

/* JADX INFO: loaded from: classes4.dex */
public final class AndroidUsingExec extends AbstractDnsServerLookupMechanism {
    public static final DnsServerLookupMechanism INSTANCE = new AndroidUsingExec();
    public static final int PRIORITY = 999;
    private static final String PROP_DELIM = "]: [";

    private AndroidUsingExec() {
        super("AndroidUsingExec", 999);
    }

    @Override // org.minidns.dnsserverlookup.AbstractDnsServerLookupMechanism, org.minidns.dnsserverlookup.DnsServerLookupMechanism
    public List<String> getDnsServerAddresses() {
        try {
            Set<String> props = parseProps(new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("getprop").getInputStream(), StandardCharsets.UTF_8)), true);
            if (props.size() <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList(props.size());
            arrayList.addAll(props);
            return arrayList;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Exception in findDNSByExec", (Throwable) e);
            return null;
        }
    }

    @Override // org.minidns.dnsserverlookup.DnsServerLookupMechanism
    public boolean isAvailable() {
        return PlatformDetection.isAndroid();
    }

    protected static Set<String> parseProps(BufferedReader bufferedReader, boolean z) throws IOException {
        String hostAddress;
        HashSet hashSet = new HashSet(6);
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return hashSet;
            }
            int iIndexOf = line.indexOf(PROP_DELIM);
            if (iIndexOf != -1) {
                String strSubstring = line.substring(1, iIndexOf);
                int length = iIndexOf + PROP_DELIM.length();
                int length2 = line.length() - 1;
                if (length2 >= length) {
                    String strSubstring2 = line.substring(length, length2);
                    if (!strSubstring2.isEmpty() && (strSubstring.endsWith(".dns") || strSubstring.endsWith(".dns1") || strSubstring.endsWith(".dns2") || strSubstring.endsWith(".dns3") || strSubstring.endsWith(".dns4"))) {
                        InetAddress byName = InetAddress.getByName(strSubstring2);
                        if (byName != null && (hostAddress = byName.getHostAddress()) != null && hostAddress.length() != 0) {
                            hashSet.add(hostAddress);
                        }
                    }
                } else if (z) {
                    LOGGER.warning("Malformed property detected: \"" + line + '\"');
                }
            }
        }
    }
}
