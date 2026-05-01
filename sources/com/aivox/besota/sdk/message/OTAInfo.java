package com.aivox.besota.sdk.message;

import com.aivox.besota.sdk.utils.OTAChannel;
import com.aivox.besota.sdk.utils.OTAStatus;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class OTAInfo implements Serializable {
    private OTAChannel otaChannel;
    private OTAStatus otaStatus;

    public OTAChannel getOtaChannel() {
        return this.otaChannel;
    }

    public void setOtaChannel(OTAChannel oTAChannel) {
        this.otaChannel = oTAChannel;
    }

    public OTAStatus getOtaStatus() {
        return this.otaStatus;
    }

    public void setOtaStatus(OTAStatus oTAStatus) {
        this.otaStatus = oTAStatus;
    }

    public String toString() {
        return "OTAInfo{otaChannel=" + this.otaChannel + ", otaStatus=" + this.otaStatus + '}';
    }
}
