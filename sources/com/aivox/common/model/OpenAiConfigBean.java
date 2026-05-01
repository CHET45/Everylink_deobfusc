package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class OpenAiConfigBean implements Serializable {
    private String openApiKey;

    public String getOpenApiKey() {
        return this.openApiKey;
    }

    public void setOpenApiKey(String str) {
        this.openApiKey = str;
    }
}
