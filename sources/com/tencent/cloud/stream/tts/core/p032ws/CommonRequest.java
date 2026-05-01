package com.tencent.cloud.stream.tts.core.p032ws;

import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes4.dex */
public class CommonRequest {
    private Map<String, Object> extendParam;

    public Map<String, Object> getExtendParam() {
        return this.extendParam;
    }

    public void setExtendParam(Map<String, Object> extendParam) {
        this.extendParam = extendParam;
    }

    public void set(String key, Object value) {
        if (this.extendParam == null) {
            this.extendParam = new HashMap();
        }
        this.extendParam.put(key, value);
    }

    public Map<String, Object> toTreeMap() {
        TreeMap treeMap = new TreeMap();
        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (!field.getName().equals("extendParam") && field.get(this) != null && field.getAnnotation(SerializedName.class) != null) {
                    treeMap.put(((SerializedName) field.getAnnotation(SerializedName.class)).value(), field.get(this));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> map = this.extendParam;
        if (map != null) {
            for (String str : map.keySet()) {
                treeMap.put(str, this.extendParam.get(str));
            }
        }
        return treeMap;
    }
}
