package com.aivox.besota.sdk.message;

import com.aivox.besota.sdk.utils.MessageID;

/* JADX INFO: loaded from: classes.dex */
public class OTAInfoMessage extends BaseMessage {
    private OTAInfo otaInfo;

    public OTAInfoMessage(OTAInfo oTAInfo) {
        this.otaInfo = oTAInfo;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public MessageID getMsgID() {
        return MessageID.OTA_STATUS;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public OTAInfo getMsgContent() {
        return this.otaInfo;
    }
}
