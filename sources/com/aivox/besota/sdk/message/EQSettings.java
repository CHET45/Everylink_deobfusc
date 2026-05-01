package com.aivox.besota.sdk.message;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class EQSettings implements Serializable {
    private int categoryId;
    private EQPayload eqPayload;
    private int status;

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int i) {
        this.categoryId = i;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public EQPayload getEqPayload() {
        return this.eqPayload;
    }

    public void setEqPayload(EQPayload eQPayload) {
        this.eqPayload = eQPayload;
    }

    public String toString() {
        return "\nEQSettings{\ncategoryId=" + this.categoryId + "\nstatus=" + this.status + "\neqPayload=" + this.eqPayload + '}';
    }
}
