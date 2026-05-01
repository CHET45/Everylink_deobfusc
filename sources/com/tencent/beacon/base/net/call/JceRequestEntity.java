package com.tencent.beacon.base.net.call;

import android.text.TextUtils;
import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.beacon.pack.RequestPackageV2;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes4.dex */
public class JceRequestEntity {
    private static final String TAG = "JceRequestEntity";
    private final String appKey;
    private final byte[] content;
    private final String domain;
    private final Map<String, String> header;
    private final int port;
    private final int requestCmd;
    private final int responseCmd;
    private final RequestType type;
    private final String url;

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.call.JceRequestEntity$a */
    public static final class C2673a {

        /* JADX INFO: renamed from: a */
        private String f1340a;

        /* JADX INFO: renamed from: b */
        private int f1341b;

        /* JADX INFO: renamed from: c */
        private String f1342c;

        /* JADX INFO: renamed from: d */
        private int f1343d;

        /* JADX INFO: renamed from: e */
        private int f1344e;

        /* JADX INFO: renamed from: f */
        private RequestType f1345f;

        /* JADX INFO: renamed from: g */
        private String f1346g;

        /* JADX INFO: renamed from: h */
        private Map<String, String> f1347h = new ConcurrentHashMap(5);

        /* JADX INFO: renamed from: i */
        private Map<String, String> f1348i = new LinkedHashMap(10);

        /* JADX INFO: renamed from: j */
        private byte[] f1349j;

        /* JADX INFO: renamed from: k */
        private AbstractJceStruct f1350k;

        /* JADX INFO: renamed from: a */
        public C2673a m1373a(RequestType requestType) {
            this.f1345f = requestType;
            return this;
        }

        /* JADX INFO: renamed from: b */
        public C2673a m1381b(String str) {
            this.f1340a = str;
            return this;
        }

        /* JADX INFO: renamed from: a */
        public C2673a m1376a(String str, int i) {
            this.f1346g = str;
            this.f1341b = i;
            return this;
        }

        /* JADX INFO: renamed from: b */
        public C2673a m1380b(int i) {
            this.f1344e = i;
            return this;
        }

        /* JADX INFO: renamed from: b */
        public C2673a m1382b(String str, String str2) {
            if (str2 == null) {
                str2 = "";
            }
            this.f1348i.put(str, str2);
            return this;
        }

        /* JADX INFO: renamed from: a */
        public C2673a m1377a(String str, String str2) {
            this.f1347h.put(str, str2);
            return this;
        }

        /* JADX INFO: renamed from: a */
        public C2673a m1372a(int i) {
            this.f1343d = i;
            return this;
        }

        /* JADX INFO: renamed from: a */
        public C2673a m1378a(Map<String, String> map) {
            if (map != null) {
                this.f1348i.putAll(map);
            }
            return this;
        }

        /* JADX INFO: renamed from: a */
        public C2673a m1375a(String str) {
            this.f1342c = str;
            return this;
        }

        /* JADX INFO: renamed from: a */
        public C2673a m1374a(AbstractJceStruct abstractJceStruct) {
            this.f1350k = abstractJceStruct;
            return this;
        }

        /* JADX INFO: renamed from: a */
        public JceRequestEntity m1379a() {
            if (TextUtils.isEmpty(this.f1340a) && TextUtils.isEmpty(this.f1346g)) {
                throw new IllegalArgumentException("url || domain == null");
            }
            if (!TextUtils.isEmpty(this.f1342c)) {
                C2671c c2671cM1351d = C2671c.m1351d();
                this.f1347h.putAll(C2669d.m1333a());
                if (this.f1345f == RequestType.EVENT) {
                    this.f1349j = c2671cM1351d.f1328f.m1166c().mo1161a((RequestPackageV2) this.f1350k);
                } else {
                    AbstractJceStruct abstractJceStruct = this.f1350k;
                    this.f1349j = c2671cM1351d.f1327e.m1158c().mo1161a(C2669d.m1332a(this.f1343d, abstractJceStruct == null ? "".getBytes() : abstractJceStruct.toByteArray(), this.f1348i, this.f1342c));
                }
                return new JceRequestEntity(this.f1345f, this.f1340a, this.f1346g, this.f1341b, this.f1342c, this.f1349j, this.f1347h, this.f1343d, this.f1344e);
            }
            throw new IllegalArgumentException("appKey == null");
        }
    }

    public static C2673a builder() {
        return new C2673a();
    }

    public String getAppKey() {
        return this.appKey;
    }

    public byte[] getContent() {
        return this.content;
    }

    public String getDomain() {
        return this.domain;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public int getPort() {
        return this.port;
    }

    public int getRequestCmd() {
        return this.requestCmd;
    }

    public int getResponseCmd() {
        return this.responseCmd;
    }

    public RequestType getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public String toString() {
        return "JceRequestEntity{type=" + this.type + ", url='" + this.url + "', domain='" + this.domain + "', port=" + this.port + ", appKey='" + this.appKey + "', content.length=" + this.content.length + ", header=" + this.header + ", requestCmd=" + this.requestCmd + ", responseCmd=" + this.responseCmd + '}';
    }

    private JceRequestEntity(RequestType requestType, String str, String str2, int i, String str3, byte[] bArr, Map<String, String> map, int i2, int i3) {
        this.type = requestType;
        this.url = str;
        this.domain = str2;
        this.port = i;
        this.appKey = str3;
        this.content = bArr;
        this.header = map;
        this.requestCmd = i2;
        this.responseCmd = i3;
    }
}
