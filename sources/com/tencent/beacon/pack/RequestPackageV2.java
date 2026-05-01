package com.tencent.beacon.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class RequestPackageV2 extends AbstractJceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static Map<String, String> cache_common;
    static ArrayList<EventRecordV2> cache_events = new ArrayList<>();
    public int platformId = 0;
    public String mainAppKey = "";
    public String appVersion = "";
    public String sdkVersion = "";
    public ArrayList<EventRecordV2> events = null;
    public String packageName = "";
    public Map<String, String> common = null;
    public String model = "";
    public String osVersion = "";
    public String reserved = "";
    public String sdkId = "";

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void readFrom(C2755a c2755a) {
        this.platformId = c2755a.m1796a(this.platformId, 0, true);
        this.mainAppKey = c2755a.m1801a(1, true);
        this.appVersion = c2755a.m1801a(2, true);
        this.sdkVersion = c2755a.m1801a(3, true);
        this.events = (ArrayList) c2755a.m1800a(cache_events, 4, true);
        this.packageName = c2755a.m1801a(5, false);
        this.common = (Map) c2755a.m1800a(cache_common, 6, false);
        this.model = c2755a.m1801a(7, false);
        this.osVersion = c2755a.m1801a(8, false);
        this.reserved = c2755a.m1801a(9, false);
        this.sdkId = c2755a.m1801a(10, false);
    }

    public String toString() {
        return "RequestPackageV2{platformId=" + this.platformId + ", mainAppKey='" + this.mainAppKey + "', appVersion='" + this.appVersion + "', sdkVersion='" + this.sdkVersion + "', packageName='" + this.packageName + "', model='" + this.model + "', osVersion='" + this.osVersion + "', reserved='" + this.reserved + "', sdkId='" + this.sdkId + "'} " + super.toString();
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void writeTo(C2756b c2756b) {
        c2756b.m1825a(this.platformId, 0);
        c2756b.m1829a(this.mainAppKey, 1);
        c2756b.m1829a(this.appVersion, 2);
        c2756b.m1829a(this.sdkVersion, 3);
        c2756b.m1830a((Collection) this.events, 4);
        String str = this.packageName;
        if (str != null) {
            c2756b.m1829a(str, 5);
        }
        Map<String, String> map = this.common;
        if (map != null) {
            c2756b.m1831a((Map) map, 6);
        }
        String str2 = this.model;
        if (str2 != null) {
            c2756b.m1829a(str2, 7);
        }
        String str3 = this.osVersion;
        if (str3 != null) {
            c2756b.m1829a(str3, 8);
        }
        String str4 = this.reserved;
        if (str4 != null) {
            c2756b.m1829a(str4, 9);
        }
        String str5 = this.sdkId;
        if (str5 != null) {
            c2756b.m1829a(str5, 10);
        }
    }

    static {
        cache_events.add(new EventRecordV2());
        HashMap map = new HashMap();
        cache_common = map;
        map.put("", "");
    }
}
