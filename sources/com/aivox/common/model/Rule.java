package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Rule implements Serializable {
    String firstgearpoint;
    String firstgearstorage;

    public String toString() {
        return "Rule{firstgearstorage='" + this.firstgearstorage + "', firstgearpoint='" + this.firstgearpoint + "'}";
    }

    public String getFirstgearstorage() {
        return this.firstgearstorage;
    }

    public void setFirstgearstorage(String str) {
        this.firstgearstorage = str;
    }

    public String getFirstgearpoint() {
        return this.firstgearpoint;
    }

    public void setFirstgearpoint(String str) {
        this.firstgearpoint = str;
    }
}
