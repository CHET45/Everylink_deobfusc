package com.aivox.besota.sdk.message;

import com.aivox.besota.sdk.utils.MessageID;

/* JADX INFO: loaded from: classes.dex */
public class A2DPConnectionMessage extends BaseMessage {
    private A2DPConnection a2dpConnection;

    public A2DPConnectionMessage(A2DPConnection a2DPConnection) {
        this.a2dpConnection = a2DPConnection;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public MessageID getMsgID() {
        return MessageID.A2DP_STATUS;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public A2DPConnection getMsgContent() {
        return this.a2dpConnection;
    }
}
