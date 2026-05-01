package com.aivox.besota.sdk.message;

import com.aivox.besota.sdk.utils.MessageID;

/* JADX INFO: loaded from: classes.dex */
public class MyBudsMessage extends BaseMessage {
    private MyBudsInfo budsInfo;

    public MyBudsMessage(MyBudsInfo myBudsInfo) {
        this.budsInfo = myBudsInfo;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public MessageID getMsgID() {
        return MessageID.FIND_ME_STATUS;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public MyBudsInfo getMsgContent() {
        return this.budsInfo;
    }
}
