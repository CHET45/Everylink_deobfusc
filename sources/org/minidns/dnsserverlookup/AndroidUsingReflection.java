package org.minidns.dnsserverlookup;

import com.github.houbb.heaven.constant.MethodConst;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.minidns.util.PlatformDetection;

/* JADX INFO: loaded from: classes4.dex */
public class AndroidUsingReflection extends AbstractDnsServerLookupMechanism {
    public static final DnsServerLookupMechanism INSTANCE = new AndroidUsingReflection();
    public static final int PRIORITY = 1000;

    protected AndroidUsingReflection() {
        super("AndroidUsingReflection", 1000);
    }

    @Override // org.minidns.dnsserverlookup.AbstractDnsServerLookupMechanism, org.minidns.dnsserverlookup.DnsServerLookupMechanism
    public List<String> getDnsServerAddresses() {
        ArrayList arrayList;
        InetAddress byName;
        String hostAddress;
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod(MethodConst.GET_PREFIX, String.class);
            arrayList = new ArrayList(5);
            String[] strArr = {"net.dns1", "net.dns2", "net.dns3", "net.dns4"};
            for (int i = 0; i < 4; i++) {
                String str = (String) method.invoke(null, strArr[i]);
                if (str != null && str.length() != 0 && !arrayList.contains(str) && (byName = InetAddress.getByName(str)) != null && (hostAddress = byName.getHostAddress()) != null && hostAddress.length() != 0 && !arrayList.contains(hostAddress)) {
                    arrayList.add(hostAddress);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception in findDNSByReflection", (Throwable) e);
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }

    @Override // org.minidns.dnsserverlookup.DnsServerLookupMechanism
    public boolean isAvailable() {
        return PlatformDetection.isAndroid();
    }
}
