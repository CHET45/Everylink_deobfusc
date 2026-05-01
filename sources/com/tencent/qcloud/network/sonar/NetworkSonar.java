package com.tencent.qcloud.network.sonar;

import android.content.Context;
import com.tencent.qcloud.network.sonar.dns.AndroidDnsServerLookup;
import com.tencent.qcloud.network.sonar.dns.DnsResult;
import com.tencent.qcloud.network.sonar.dns.DnsSonar;
import com.tencent.qcloud.network.sonar.http.HttpResult;
import com.tencent.qcloud.network.sonar.http.HttpSonar;
import com.tencent.qcloud.network.sonar.ping.PingResult;
import com.tencent.qcloud.network.sonar.ping.PingSonar;
import com.tencent.qcloud.network.sonar.traceroute.TracerouteResult;
import com.tencent.qcloud.network.sonar.traceroute.TracerouteSonar;
import com.tencent.qcloud.network.sonar.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.minidns.DnsClient;
import org.minidns.dnsserverlookup.AndroidUsingExec;
import org.minidns.dnsserverlookup.AndroidUsingReflection;
import org.minidns.dnsserverlookup.UnixUsingEtcResolvConf;

/* JADX INFO: loaded from: classes4.dex */
public class NetworkSonar {
    private static Context appContext;

    public static void sonar(Context context, SonarRequest sonarRequest, List<SonarType> list, NetworkSonarCallback networkSonarCallback) {
        SonarResult sonarResultSonarDns;
        synchronized (NetworkSonar.class) {
            if (appContext == null) {
                appContext = context;
                DnsClient.removeDNSServerLookupMechanism(AndroidUsingExec.INSTANCE);
                DnsClient.removeDNSServerLookupMechanism(AndroidUsingReflection.INSTANCE);
                DnsClient.removeDNSServerLookupMechanism(UnixUsingEtcResolvConf.INSTANCE);
                DnsClient.addDnsServerLookupMechanism(new AndroidDnsServerLookup(context));
            }
        }
        sonarRequest.setNetworkAvailable(Utils.isNetworkAvailable(context));
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SonarType sonarType = list.get(i);
            networkSonarCallback.onStart(sonarType);
            int i2 = C44851.$SwitchMap$com$tencent$qcloud$network$sonar$SonarType[sonarType.ordinal()];
            if (i2 == 1) {
                sonarResultSonarDns = sonarDns(sonarRequest);
                if (sonarResultSonarDns.isSuccess()) {
                    sonarRequest.setIp(((DnsResult) sonarResultSonarDns.getResult()).f1875ip);
                }
            } else {
                sonarResultSonarDns = i2 != 2 ? i2 != 3 ? i2 != 4 ? null : sonarHttp(sonarRequest) : sonarTraceroute(sonarRequest) : sonarPing(sonarRequest);
            }
            if (sonarResultSonarDns.isSuccess()) {
                networkSonarCallback.onSuccess(sonarResultSonarDns);
            } else {
                networkSonarCallback.onFail(sonarResultSonarDns);
            }
            arrayList.add(sonarResultSonarDns);
            if (i == list.size() - 1) {
                networkSonarCallback.onFinish(arrayList);
            }
        }
    }

    /* JADX INFO: renamed from: com.tencent.qcloud.network.sonar.NetworkSonar$1 */
    static /* synthetic */ class C44851 {
        static final /* synthetic */ int[] $SwitchMap$com$tencent$qcloud$network$sonar$SonarType;

        static {
            int[] iArr = new int[SonarType.values().length];
            $SwitchMap$com$tencent$qcloud$network$sonar$SonarType = iArr;
            try {
                iArr[SonarType.DNS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tencent$qcloud$network$sonar$SonarType[SonarType.PING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$tencent$qcloud$network$sonar$SonarType[SonarType.TRACEROUTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$tencent$qcloud$network$sonar$SonarType[SonarType.HTTP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static SonarResult<DnsResult> sonarDns(SonarRequest sonarRequest) {
        return new DnsSonar().start(sonarRequest);
    }

    public static SonarResult<PingResult> sonarPing(SonarRequest sonarRequest) {
        return new PingSonar().start(sonarRequest);
    }

    public static SonarResult<TracerouteResult> sonarTraceroute(SonarRequest sonarRequest) {
        return new TracerouteSonar().start(sonarRequest);
    }

    public static SonarResult<HttpResult> sonarHttp(SonarRequest sonarRequest) {
        return sonarHttp(sonarRequest, false);
    }

    public static SonarResult<HttpResult> sonarHttp(SonarRequest sonarRequest, boolean z) {
        return new HttpSonar(z).start(sonarRequest);
    }

    public static Context getAppContext() {
        return appContext;
    }
}
