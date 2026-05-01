package com.aivox.besota.sdk.message;

import com.aivox.besota.sdk.utils.MessageID;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class AncStatusMessage extends BaseMessage {
    private LinkedList<AncSettings> ancSettingsList;

    public AncStatusMessage(LinkedList<AncSettings> linkedList) {
        this.ancSettingsList = linkedList;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public MessageID getMsgID() {
        return MessageID.ANC_STATUS;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public LinkedList<AncSettings> getMsgContent() {
        return this.ancSettingsList;
    }
}
