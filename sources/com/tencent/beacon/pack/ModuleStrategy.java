package com.tencent.beacon.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class ModuleStrategy extends AbstractJceStruct {
    static Map<String, String> cache_detail;
    static ArrayList<String> cache_preventEventCode;
    static ArrayList<String> cache_sampleEvent;
    public static Object cache_sms;
    public Map<String, String> detail;
    public byte mId;
    public byte onOff;
    public ArrayList<String> preventEventCode;
    public ArrayList<String> sampleEvent;
    public Object sms;
    public String url;

    public ModuleStrategy() {
        this.mId = (byte) 0;
        this.onOff = (byte) 0;
        this.url = "";
        this.detail = null;
        this.preventEventCode = null;
        this.sms = null;
        this.sampleEvent = null;
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void readFrom(C2755a c2755a) {
        this.mId = c2755a.m1793a(this.mId, 0, true);
        this.onOff = c2755a.m1793a(this.onOff, 1, true);
        this.url = c2755a.m1801a(2, true);
        if (cache_detail == null) {
            HashMap map = new HashMap();
            cache_detail = map;
            map.put("", "");
        }
        this.detail = (Map) c2755a.m1800a(cache_detail, 3, true);
        if (cache_preventEventCode == null) {
            ArrayList<String> arrayList = new ArrayList<>();
            cache_preventEventCode = arrayList;
            arrayList.add("");
        }
        this.preventEventCode = (ArrayList) c2755a.m1800a(cache_preventEventCode, 4, false);
        Object obj = cache_sms;
        if (obj != null) {
            this.sms = c2755a.m1800a(obj, 5, false);
        }
        if (cache_sampleEvent == null) {
            ArrayList<String> arrayList2 = new ArrayList<>();
            cache_sampleEvent = arrayList2;
            arrayList2.add("");
        }
        this.sampleEvent = (ArrayList) c2755a.m1800a(cache_sampleEvent, 6, false);
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void writeTo(C2756b c2756b) {
        c2756b.m1821a(this.mId, 0);
        c2756b.m1821a(this.onOff, 1);
        c2756b.m1829a(this.url, 2);
        c2756b.m1831a((Map) this.detail, 3);
        ArrayList<String> arrayList = this.preventEventCode;
        if (arrayList != null) {
            c2756b.m1830a((Collection) arrayList, 4);
        }
        Object obj = this.sms;
        if (obj != null) {
            c2756b.m1828a(obj, 5);
        }
        ArrayList<String> arrayList2 = this.sampleEvent;
        if (arrayList2 != null) {
            c2756b.m1830a((Collection) arrayList2, 6);
        }
    }

    public ModuleStrategy(byte b, byte b2, String str, Map<String, String> map, ArrayList<String> arrayList, Object obj, ArrayList<String> arrayList2) {
        this.mId = b;
        this.onOff = b2;
        this.url = str;
        this.detail = map;
        this.preventEventCode = arrayList;
        this.sms = obj;
        this.sampleEvent = arrayList2;
    }
}
