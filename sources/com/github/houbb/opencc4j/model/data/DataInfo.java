package com.github.houbb.opencc4j.model.data;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class DataInfo {
    private Map<String, List<String>> dataMap;
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Map<String, List<String>> getDataMap() {
        return this.dataMap;
    }

    public void setDataMap(Map<String, List<String>> map) {
        this.dataMap = map;
    }
}
