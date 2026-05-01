package com.tencent.beacon.event.open;

import com.aivox.base.util.MyAnimationUtil;
import com.tencent.beacon.base.net.adapter.AbstractNetAdapter;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: loaded from: classes4.dex */
public class BeaconConfig {

    /* JADX INFO: renamed from: A */
    private final boolean f1616A;

    /* JADX INFO: renamed from: B */
    private final boolean f1617B;

    /* JADX INFO: renamed from: C */
    private final int f1618C;

    /* JADX INFO: renamed from: D */
    private final int f1619D;

    /* JADX INFO: renamed from: E */
    private final boolean f1620E;

    /* JADX INFO: renamed from: F */
    private final int f1621F;

    /* JADX INFO: renamed from: G */
    private final boolean f1622G;

    /* JADX INFO: renamed from: H */
    private final boolean f1623H;

    /* JADX INFO: renamed from: a */
    private final int f1624a;

    /* JADX INFO: renamed from: b */
    private final boolean f1625b;

    /* JADX INFO: renamed from: c */
    private final boolean f1626c;

    /* JADX INFO: renamed from: d */
    private final long f1627d;

    /* JADX INFO: renamed from: e */
    private final long f1628e;

    /* JADX INFO: renamed from: f */
    private final int f1629f;

    /* JADX INFO: renamed from: g */
    private final int f1630g;

    /* JADX INFO: renamed from: h */
    private final boolean f1631h;

    /* JADX INFO: renamed from: i */
    private final AbstractNetAdapter f1632i;

    /* JADX INFO: renamed from: j */
    private final String f1633j;

    /* JADX INFO: renamed from: k */
    private final String f1634k;

    /* JADX INFO: renamed from: l */
    private final boolean f1635l;

    /* JADX INFO: renamed from: m */
    private final boolean f1636m;

    /* JADX INFO: renamed from: n */
    private final boolean f1637n;

    /* JADX INFO: renamed from: o */
    private final String f1638o;

    /* JADX INFO: renamed from: p */
    private final String f1639p;

    /* JADX INFO: renamed from: q */
    private final String f1640q;

    /* JADX INFO: renamed from: r */
    private final String f1641r;

    /* JADX INFO: renamed from: s */
    private final String f1642s;

    /* JADX INFO: renamed from: t */
    private final String f1643t;

    /* JADX INFO: renamed from: u */
    private final String f1644u;

    /* JADX INFO: renamed from: v */
    private final String f1645v;

    /* JADX INFO: renamed from: w */
    private final String f1646w;

    /* JADX INFO: renamed from: x */
    private final String f1647x;

    /* JADX INFO: renamed from: y */
    private final boolean f1648y;

    /* JADX INFO: renamed from: z */
    private final boolean f1649z;

    public static final class Builder {

        /* JADX INFO: renamed from: d */
        private ScheduledExecutorService f1662d;

        /* JADX INFO: renamed from: f */
        private AbstractNetAdapter f1664f;

        /* JADX INFO: renamed from: k */
        private String f1669k;

        /* JADX INFO: renamed from: l */
        private String f1670l;

        /* JADX INFO: renamed from: a */
        private int f1659a = 10000;

        /* JADX INFO: renamed from: b */
        private boolean f1660b = true;

        /* JADX INFO: renamed from: c */
        private boolean f1661c = true;

        /* JADX INFO: renamed from: e */
        private boolean f1663e = true;

        /* JADX INFO: renamed from: g */
        private long f1665g = MyAnimationUtil.ANI_TIME_2000;

        /* JADX INFO: renamed from: h */
        private long f1666h = 5000;

        /* JADX INFO: renamed from: i */
        private int f1667i = 48;

        /* JADX INFO: renamed from: j */
        private int f1668j = 48;

        /* JADX INFO: renamed from: m */
        private boolean f1671m = false;

        /* JADX INFO: renamed from: n */
        private boolean f1672n = true;

        /* JADX INFO: renamed from: o */
        private boolean f1673o = true;

        /* JADX INFO: renamed from: p */
        private String f1674p = "";

        /* JADX INFO: renamed from: q */
        private String f1675q = "";

        /* JADX INFO: renamed from: r */
        private String f1676r = "";

        /* JADX INFO: renamed from: s */
        private String f1677s = "";

        /* JADX INFO: renamed from: t */
        private String f1678t = "";

        /* JADX INFO: renamed from: u */
        private String f1679u = "";

        /* JADX INFO: renamed from: v */
        private String f1680v = "";

        /* JADX INFO: renamed from: w */
        private String f1681w = "";

        /* JADX INFO: renamed from: x */
        private String f1682x = "";

        /* JADX INFO: renamed from: y */
        private String f1683y = "";

        /* JADX INFO: renamed from: z */
        private boolean f1684z = true;

        /* JADX INFO: renamed from: A */
        private boolean f1650A = true;

        /* JADX INFO: renamed from: B */
        private boolean f1651B = true;

        /* JADX INFO: renamed from: C */
        private boolean f1652C = false;

        /* JADX INFO: renamed from: D */
        private int f1653D = 2;

        /* JADX INFO: renamed from: E */
        private int f1654E = 0;

        /* JADX INFO: renamed from: F */
        private boolean f1655F = true;

        /* JADX INFO: renamed from: G */
        private int f1656G = -1;

        /* JADX INFO: renamed from: H */
        private boolean f1657H = true;

        /* JADX INFO: renamed from: I */
        private boolean f1658I = false;

        public Builder auditEnable(boolean z) {
            this.f1660b = z;
            return this;
        }

        public Builder bidEnable(boolean z) {
            this.f1661c = z;
            return this;
        }

        public BeaconConfig build() {
            ScheduledExecutorService scheduledExecutorService = this.f1662d;
            if (scheduledExecutorService != null) {
                AbstractC2616b.m1002a(scheduledExecutorService);
            }
            return new BeaconConfig(this);
        }

        @Deprecated
        public Builder eventReportEnable(boolean z) {
            C2695c.m1468b("eventReportEnable is deprecated!", new Object[0]);
            return this;
        }

        public Builder maxDBCount(int i) {
            this.f1659a = i;
            return this;
        }

        public Builder pagePathEnable(boolean z) {
            this.f1673o = z;
            return this;
        }

        public Builder qmspEnable(boolean z) {
            this.f1672n = z;
            return this;
        }

        public Builder setAndroidID(String str) {
            this.f1674p = str;
            return this;
        }

        public Builder setCompressType(int i) {
            this.f1653D = i;
            return this;
        }

        public Builder setConfigHost(String str) {
            this.f1670l = str;
            return this;
        }

        public Builder setExecutorService(ScheduledExecutorService scheduledExecutorService) {
            this.f1662d = scheduledExecutorService;
            return this;
        }

        public Builder setForceEnableAtta(boolean z) {
            this.f1671m = z;
            return this;
        }

        public Builder setHighPerformanceMode(boolean z) {
            this.f1652C = z;
            return this;
        }

        public Builder setHttpAdapter(AbstractNetAdapter abstractNetAdapter) {
            this.f1664f = abstractNetAdapter;
            return this;
        }

        public Builder setImei(String str) {
            this.f1675q = str;
            return this;
        }

        public Builder setImei2(String str) {
            this.f1676r = str;
            return this;
        }

        public Builder setImsi(String str) {
            this.f1677s = str;
            return this;
        }

        public Builder setIsSocketMode(boolean z) {
            this.f1663e = z;
            return this;
        }

        public Builder setLongConnectionType(int i) {
            this.f1656G = i;
            return this;
        }

        public Builder setMac(String str) {
            this.f1680v = str;
            return this;
        }

        public Builder setMeid(String str) {
            this.f1678t = str;
            return this;
        }

        public Builder setModel(String str) {
            this.f1679u = str;
            return this;
        }

        public Builder setNeedAttaReport(boolean z) {
            this.f1651B = z;
            return this;
        }

        public Builder setNeedInitOstar(boolean z) {
            this.f1684z = z;
            return this;
        }

        public Builder setNeedLifeCycleListener(boolean z) {
            this.f1655F = z;
            return this;
        }

        public Builder setNeedReportRqdEvent(boolean z) {
            this.f1650A = z;
            return this;
        }

        public Builder setNormalPollingTime(long j) {
            this.f1666h = j;
            return this;
        }

        public Builder setNormalUploadNum(int i) {
            this.f1668j = i;
            return this;
        }

        public Builder setOaid(String str) {
            this.f1683y = str;
            return this;
        }

        public Builder setRealtimePollingTime(long j) {
            this.f1665g = j;
            return this;
        }

        public Builder setRealtimeUploadNum(int i) {
            this.f1667i = i;
            return this;
        }

        public Builder setRetryAfterQuicFailImme(boolean z) {
            this.f1657H = z;
            return this;
        }

        public Builder setRsaPubKeyType(int i) {
            this.f1654E = i;
            return this;
        }

        public Builder setUploadHost(String str) {
            this.f1669k = str;
            return this;
        }

        public Builder setUseAccurateDpi(boolean z) {
            this.f1658I = z;
            return this;
        }

        public Builder setWifiMacAddress(String str) {
            this.f1681w = str;
            return this;
        }

        public Builder setWifiSSID(String str) {
            this.f1682x = str;
            return this;
        }
    }

    public BeaconConfig(Builder builder) {
        this.f1624a = builder.f1659a;
        this.f1625b = builder.f1660b;
        this.f1626c = builder.f1661c;
        this.f1627d = builder.f1665g;
        this.f1628e = builder.f1666h;
        this.f1629f = builder.f1667i;
        this.f1630g = builder.f1668j;
        this.f1631h = builder.f1663e;
        this.f1632i = builder.f1664f;
        this.f1633j = builder.f1669k;
        this.f1634k = builder.f1670l;
        this.f1635l = builder.f1671m;
        this.f1636m = builder.f1672n;
        this.f1637n = builder.f1673o;
        this.f1638o = builder.f1674p;
        this.f1639p = builder.f1675q;
        this.f1640q = builder.f1676r;
        this.f1641r = builder.f1677s;
        this.f1642s = builder.f1678t;
        this.f1643t = builder.f1679u;
        this.f1644u = builder.f1680v;
        this.f1645v = builder.f1681w;
        this.f1646w = builder.f1682x;
        this.f1647x = builder.f1683y;
        this.f1648y = builder.f1684z;
        this.f1649z = builder.f1650A;
        this.f1616A = builder.f1651B;
        this.f1617B = builder.f1652C;
        this.f1618C = builder.f1653D;
        this.f1619D = builder.f1654E;
        this.f1620E = builder.f1655F;
        this.f1621F = builder.f1656G;
        this.f1622G = builder.f1657H;
        this.f1623H = builder.f1658I;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getAndroidID() {
        return this.f1638o;
    }

    public int getCompressType() {
        return this.f1618C;
    }

    public String getConfigHost() {
        return this.f1634k;
    }

    public AbstractNetAdapter getHttpAdapter() {
        return this.f1632i;
    }

    public String getImei() {
        return this.f1639p;
    }

    public String getImei2() {
        return this.f1640q;
    }

    public String getImsi() {
        return this.f1641r;
    }

    public int getLongConnectionType() {
        return this.f1621F;
    }

    public String getMac() {
        return this.f1644u;
    }

    public int getMaxDBCount() {
        return this.f1624a;
    }

    public String getMeid() {
        return this.f1642s;
    }

    public String getModel() {
        return this.f1643t;
    }

    public long getNormalPollingTIme() {
        return this.f1628e;
    }

    public int getNormalUploadNum() {
        return this.f1630g;
    }

    public String getOaid() {
        return this.f1647x;
    }

    public long getRealtimePollingTime() {
        return this.f1627d;
    }

    public int getRealtimeUploadNum() {
        return this.f1629f;
    }

    public boolean getRetryAfterQuicFailImme() {
        return this.f1622G;
    }

    public int getRsaPubKeyType() {
        return this.f1619D;
    }

    public String getUploadHost() {
        return this.f1633j;
    }

    public String getWifiMacAddress() {
        return this.f1645v;
    }

    public String getWifiSSID() {
        return this.f1646w;
    }

    public boolean isAuditEnable() {
        return this.f1625b;
    }

    public boolean isBidEnable() {
        return this.f1626c;
    }

    public boolean isEnableQmsp() {
        return this.f1636m;
    }

    public boolean isForceEnableAtta() {
        return this.f1635l;
    }

    public boolean isHighPerformanceMode() {
        return this.f1617B;
    }

    public boolean isNeedInitOstar() {
        return this.f1648y;
    }

    public boolean isNeedLifeCycleListener() {
        return this.f1620E;
    }

    public boolean isPagePathEnable() {
        return this.f1637n;
    }

    public boolean isSocketMode() {
        return this.f1631h;
    }

    public boolean isUseAccurateDpi() {
        return this.f1623H;
    }

    public boolean needAttaReport() {
        return this.f1616A;
    }

    public boolean needReportRqdEvent() {
        return this.f1649z;
    }

    public String toString() {
        return "BeaconConfig{maxDBCount=" + this.f1624a + ", auditEnable=" + this.f1625b + ", bidEnable=" + this.f1626c + ", realtimePollingTime=" + this.f1627d + ", normalPollingTIme=" + this.f1628e + ", normalUploadNum=" + this.f1630g + ", realtimeUploadNum=" + this.f1629f + ", httpAdapter=" + this.f1632i + ", uploadHost='" + this.f1633j + "', configHost='" + this.f1634k + "', forceEnableAtta=" + this.f1635l + ", enableQmsp=" + this.f1636m + ", pagePathEnable=" + this.f1637n + ", androidID='" + this.f1638o + "', imei='" + this.f1639p + "', imei2='" + this.f1640q + "', imsi='" + this.f1641r + "', meid='" + this.f1642s + "', model='" + this.f1643t + "', mac='" + this.f1644u + "', wifiMacAddress='" + this.f1645v + "', wifiSSID='" + this.f1646w + "', oaid='" + this.f1647x + "', needInitQ='" + this.f1648y + "'}";
    }
}
