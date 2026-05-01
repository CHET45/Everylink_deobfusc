package com.tencent.beacon.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class CommonStrategy extends AbstractJceStruct {
    static Map<String, String> cache_cloudParas;
    static ArrayList<ModuleStrategy> cache_moduleList;
    public Map<String, String> cloudParas;
    public ArrayList<ModuleStrategy> moduleList;
    public int queryInterval;
    public String url;

    public CommonStrategy() {
        this.moduleList = null;
        this.queryInterval = 0;
        this.url = "";
        this.cloudParas = null;
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void readFrom(C2755a c2755a) {
        if (cache_moduleList == null) {
            cache_moduleList = new ArrayList<>();
            cache_moduleList.add(new ModuleStrategy());
        }
        this.moduleList = (ArrayList) c2755a.m1800a(cache_moduleList, 0, true);
        this.queryInterval = c2755a.m1796a(this.queryInterval, 1, true);
        this.url = c2755a.m1801a(2, true);
        if (cache_cloudParas == null) {
            HashMap map = new HashMap();
            cache_cloudParas = map;
            map.put("", "");
        }
        this.cloudParas = (Map) c2755a.m1800a(cache_cloudParas, 3, false);
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void writeTo(C2756b c2756b) {
        c2756b.m1830a((Collection) this.moduleList, 0);
        c2756b.m1825a(this.queryInterval, 1);
        c2756b.m1829a(this.url, 2);
        Map<String, String> map = this.cloudParas;
        if (map != null) {
            c2756b.m1831a((Map) map, 3);
        }
    }

    public CommonStrategy(ArrayList<ModuleStrategy> arrayList, int i, String str, Map<String, String> map) {
        this.moduleList = arrayList;
        this.queryInterval = i;
        this.url = str;
        this.cloudParas = map;
    }
}
