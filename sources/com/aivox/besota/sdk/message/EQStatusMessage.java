package com.aivox.besota.sdk.message;

import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class EQStatusMessage extends BaseMessage {
    private LinkedList<EQSettings> eqList;

    public EQStatusMessage(LinkedList<EQSettings> linkedList) {
        this.eqList = linkedList;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public LinkedList<EQSettings> getMsgContent() {
        return this.eqList;
    }
}
