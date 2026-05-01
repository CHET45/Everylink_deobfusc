package com.tencent.cos.xml.model.tag.eventstreaming;

import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public class JSONInput implements Serializable {
    private String type;

    public JSONInput(String str) {
        this.type = str;
    }

    public JSONInput(JSONType jSONType) {
        this.type = jSONType.toString();
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public JSONInput withType(String str) {
        setType(str);
        return this;
    }

    public void setType(JSONType jSONType) {
        setType(jSONType == null ? null : jSONType.toString());
    }

    public JSONInput withType(JSONType jSONType) {
        setType(jSONType);
        return this;
    }
}
