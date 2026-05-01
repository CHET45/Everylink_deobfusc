package com.tencent.qcloud.network.sonar.dns;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import org.minidns.dnsserverlookup.AbstractDnsServerLookupMechanism;
import org.minidns.util.PlatformDetection;

/* JADX INFO: loaded from: classes4.dex */
public class AndroidDnsServerLookup extends AbstractDnsServerLookupMechanism {
    public static final int PRIORITY = 1000;
    private final Context context;

    public AndroidDnsServerLookup(Context context) {
        super("AndroidDnsServerLookup", 1000);
        this.context = context.getApplicationContext();
    }

    @Override // org.minidns.dnsserverlookup.DnsServerLookupMechanism
    public boolean isAvailable() {
        return PlatformDetection.isAndroid();
    }

    @Override // org.minidns.dnsserverlookup.AbstractDnsServerLookupMechanism, org.minidns.dnsserverlookup.DnsServerLookupMechanism
    public List<String> getDnsServerAddresses() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService("connectivity");
            LinkProperties linkProperties = connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork());
            ArrayList arrayList = new ArrayList();
            Iterator<InetAddress> it = linkProperties.getDnsServers().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getHostAddress());
            }
            return arrayList;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception in findDNSByReflection", (Throwable) e);
            return null;
        }
    }
}
