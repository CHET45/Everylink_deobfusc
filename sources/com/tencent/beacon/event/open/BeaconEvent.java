package com.tencent.beacon.event.open;

import android.text.TextUtils;
import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.beacon.event.p031c.C2732d;
import com.tencent.beacon.p015a.p018c.C2630c;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class BeaconEvent {

    /* JADX INFO: renamed from: a */
    private String f1685a;

    /* JADX INFO: renamed from: b */
    private String f1686b;

    /* JADX INFO: renamed from: c */
    private EventType f1687c;

    /* JADX INFO: renamed from: d */
    private boolean f1688d;

    /* JADX INFO: renamed from: e */
    private Map<String, String> f1689e;

    public static final class Builder {

        /* JADX INFO: renamed from: a */
        private String f1690a;

        /* JADX INFO: renamed from: b */
        private String f1691b;

        /* JADX INFO: renamed from: c */
        private EventType f1692c;

        /* JADX INFO: renamed from: d */
        private boolean f1693d;

        /* JADX INFO: renamed from: e */
        private Map<String, String> f1694e;

        /* synthetic */ Builder(C2743a c2743a) {
            this();
        }

        public BeaconEvent build() {
            String strM1642b = C2732d.m1642b(this.f1691b);
            if (TextUtils.isEmpty(this.f1690a)) {
                this.f1690a = C2630c.m1059c().m1072e();
            }
            C2732d.m1639a(strM1642b, this.f1694e);
            return new BeaconEvent(this.f1690a, strM1642b, this.f1692c, this.f1693d, this.f1694e, null);
        }

        public Builder withAppKey(String str) {
            this.f1690a = str;
            return this;
        }

        public Builder withCode(String str) {
            this.f1691b = str;
            return this;
        }

        public Builder withIsSucceed(boolean z) {
            this.f1693d = z;
            return this;
        }

        public Builder withParams(Map<String, String> map) {
            if (map != null) {
                this.f1694e.putAll(map);
            }
            return this;
        }

        public Builder withType(EventType eventType) {
            this.f1692c = eventType;
            return this;
        }

        /* synthetic */ Builder(BeaconEvent beaconEvent, C2743a c2743a) {
            this(beaconEvent);
        }

        public Builder withParams(String str, String str2) {
            this.f1694e.put(str, str2);
            return this;
        }

        private Builder() {
            this.f1692c = EventType.NORMAL;
            this.f1693d = true;
            this.f1694e = new HashMap();
        }

        private Builder(BeaconEvent beaconEvent) {
            this.f1692c = EventType.NORMAL;
            this.f1693d = true;
            this.f1694e = new HashMap();
            this.f1690a = beaconEvent.f1685a;
            this.f1691b = beaconEvent.f1686b;
            this.f1692c = beaconEvent.f1687c;
            this.f1693d = beaconEvent.f1688d;
            this.f1694e.putAll(beaconEvent.f1689e);
        }
    }

    /* synthetic */ BeaconEvent(String str, String str2, EventType eventType, boolean z, Map map, C2743a c2743a) {
        this(str, str2, eventType, z, map);
    }

    public static Builder builder() {
        return new Builder((C2743a) null);
    }

    public static Builder newBuilder(BeaconEvent beaconEvent) {
        return new Builder(beaconEvent, null);
    }

    public String getAppKey() {
        return this.f1685a;
    }

    public String getCode() {
        return this.f1686b;
    }

    public String getLogidPrefix() {
        switch (C2743a.f1703a[this.f1687c.ordinal()]) {
            case 1:
            case 2:
                return "N";
            case 3:
            case 4:
                return "I";
            case 5:
            case 6:
                return BoolUtil.f541Y;
            default:
                return "";
        }
    }

    public Map<String, String> getParams() {
        return this.f1689e;
    }

    public EventType getType() {
        return this.f1687c;
    }

    public boolean isSucceed() {
        return this.f1688d;
    }

    public void setAppKey(String str) {
        this.f1685a = str;
    }

    public void setCode(String str) {
        this.f1686b = str;
    }

    public void setParams(Map<String, String> map) {
        this.f1689e = map;
    }

    public void setSucceed(boolean z) {
        this.f1688d = z;
    }

    public void setType(EventType eventType) {
        this.f1687c = eventType;
    }

    public String toString() {
        return super.toString();
    }

    private BeaconEvent(String str, String str2, EventType eventType, boolean z, Map<String, String> map) {
        this.f1685a = str;
        this.f1686b = str2;
        this.f1687c = eventType;
        this.f1688d = z;
        this.f1689e = map;
    }
}
