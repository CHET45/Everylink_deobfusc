package com.aivox.common.model;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GoogleLocation {
    List<GoogleLocationResult> results = new ArrayList();
    String status;

    public List<GoogleLocationResult> getResults() {
        return this.results;
    }

    public void setResults(List<GoogleLocationResult> list) {
        this.results = list;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
