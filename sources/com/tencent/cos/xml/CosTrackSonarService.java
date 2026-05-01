package com.tencent.cos.xml;

import android.content.Context;
import com.tencent.cos.xml.CosTrackService;
import com.tencent.qcloud.core.logger.COSLogger;
import com.tencent.qcloud.network.sonar.NetworkSonar;
import com.tencent.qcloud.network.sonar.NetworkSonarCallback;
import com.tencent.qcloud.network.sonar.SonarRequest;
import com.tencent.qcloud.network.sonar.SonarResult;
import com.tencent.qcloud.network.sonar.SonarType;
import com.tencent.qcloud.network.sonar.dns.DnsResult;
import com.tencent.qcloud.network.sonar.ping.PingResult;
import com.tencent.qcloud.network.sonar.traceroute.TracerouteResult;
import com.tencent.qcloud.track.QCloudTrackService;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class CosTrackSonarService {
    private static final String TAG = "CosTrackSonarService";
    private Context applicationContext;
    private final ScheduledExecutorService sonarScheduler = Executors.newScheduledThreadPool(1);

    public void setContext(Context context) {
        this.applicationContext = context;
    }

    public static boolean isIncludeSonar() {
        try {
            Class.forName("com.tencent.qcloud.network.sonar.NetworkSonar");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public void periodicSonar() {
        this.sonarScheduler.scheduleWithFixedDelay(new Runnable() { // from class: com.tencent.cos.xml.CosTrackSonarService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2694lambda$periodicSonar$0$comtencentcosxmlCosTrackSonarService();
            }
        }, 3L, 10L, TimeUnit.MINUTES);
    }

    /* JADX INFO: renamed from: lambda$periodicSonar$0$com-tencent-cos-xml-CosTrackSonarService, reason: not valid java name */
    /* synthetic */ void m2694lambda$periodicSonar$0$comtencentcosxmlCosTrackSonarService() {
        CosTrackService.SonarHost sonarHost = CosTrackService.getInstance().getSonarHosts().get();
        if (sonarHost == null || sonarHost.getHost() == null || (System.currentTimeMillis() - CosTrackService.getInstance().getSonarHosts().getSonarHostsAddTimestamp()) / 60000 > 15) {
            return;
        }
        HashMap map = new HashMap();
        map.put("region", sonarHost.getRegion());
        map.put("bucket", sonarHost.getBucket());
        sonar(CosTrackService.EVENT_CODE_TRACK_COS_SDK_SONAR, sonarHost.getHost(), map, true);
    }

    public void failSonar(String str, String str2, String str3, String str4) {
        if (str == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("region", str2);
        map.put("bucket", str3);
        map.put("client_trace_id", str4);
        sonar(CosTrackService.EVENT_CODE_TRACK_COS_SDK_SONAR_FAILURE, str, map, false);
    }

    private void sonar(final String str, String str2, Map<String, String> map, final boolean z) {
        SonarRequest sonarRequest;
        if (CosTrackService.getInstance().isCloseReport() || !isIncludeSonar()) {
            return;
        }
        try {
            final HashMap map2 = new HashMap(map);
            map2.put("host", str2);
            ArrayList arrayList = new ArrayList();
            if (z) {
                arrayList.add(SonarType.PING);
                long jCurrentTimeMillis = System.currentTimeMillis();
                String hostAddress = InetAddress.getByName(str2).getHostAddress();
                map2.put("dns_ip", hostAddress);
                map2.put("dns_lookupTime", String.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
                sonarRequest = new SonarRequest(str2, hostAddress);
            } else {
                arrayList.add(SonarType.DNS);
                arrayList.add(SonarType.PING);
                arrayList.add(SonarType.TRACEROUTE);
                sonarRequest = new SonarRequest(str2);
            }
            NetworkSonar.sonar(this.applicationContext, sonarRequest, arrayList, new NetworkSonarCallback() { // from class: com.tencent.cos.xml.CosTrackSonarService.1
                @Override // com.tencent.qcloud.network.sonar.NetworkSonarCallback
                public void onFail(SonarResult sonarResult) {
                }

                @Override // com.tencent.qcloud.network.sonar.NetworkSonarCallback
                public void onStart(SonarType sonarType) {
                }

                @Override // com.tencent.qcloud.network.sonar.NetworkSonarCallback
                public void onSuccess(SonarResult sonarResult) {
                }

                @Override // com.tencent.qcloud.network.sonar.NetworkSonarCallback
                public void onFinish(List<SonarResult> list) {
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    for (SonarResult sonarResult : list) {
                        if (sonarResult != null && sonarResult.isSuccess() && sonarResult.getResult() != null) {
                            int i = C27732.$SwitchMap$com$tencent$qcloud$network$sonar$SonarType[sonarResult.getType().ordinal()];
                            if (i == 1) {
                                DnsResult dnsResult = (DnsResult) sonarResult.getResult();
                                map2.put("dns_ip", dnsResult.f1875ip);
                                map2.put("dns_lookupTime", String.valueOf(dnsResult.lookupTime));
                                map2.put("dns_a", dnsResult.f1874a);
                                map2.put("dns_cname", dnsResult.cname);
                                map2.put("dns_result", dnsResult.response);
                            } else if (i == 2) {
                                PingResult pingResult = (PingResult) sonarResult.getResult();
                                map2.put("ping_ip", pingResult.f1876ip);
                                map2.put("ping_size", String.valueOf(pingResult.size));
                                map2.put("ping_interval", String.valueOf(pingResult.interval));
                                map2.put("ping_count", String.valueOf(pingResult.count));
                                map2.put("ping_loss", String.valueOf(pingResult.getLoss()));
                                map2.put("ping_response_num", String.valueOf(pingResult.getResponseNum()));
                                map2.put("ping_avg", String.valueOf(pingResult.avg));
                                map2.put("ping_max", String.valueOf(pingResult.max));
                                map2.put("ping_min", String.valueOf(pingResult.min));
                                map2.put("ping_stddev", String.valueOf(pingResult.stddev));
                            } else if (i == 3) {
                                TracerouteResult tracerouteResult = (TracerouteResult) sonarResult.getResult();
                                map2.put("traceroute_ip", tracerouteResult.getTargetIp());
                                map2.put("traceroute_status", tracerouteResult.getCommandStatus().getName());
                                map2.put("traceroute_hop_count", String.valueOf(tracerouteResult.getHopCount()));
                                map2.put("traceroute_total_delay", String.valueOf(tracerouteResult.getTotalDelay()));
                                map2.put("traceroute_avg_loss_rate", String.valueOf(tracerouteResult.getLossRate()));
                                map2.put("traceroute_nodes", tracerouteResult.getNodeResultsString());
                            }
                        }
                    }
                    if (map2.containsKey("dns_ip") || map2.containsKey("ping_ip") || map2.containsKey("traceroute_ip")) {
                        QCloudTrackService.getInstance().report(str, map2);
                        if (z) {
                            return;
                        }
                        COSLogger.iProbe("FailSonar", map2.toString());
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: renamed from: com.tencent.cos.xml.CosTrackSonarService$2 */
    static /* synthetic */ class C27732 {
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
        }
    }
}
