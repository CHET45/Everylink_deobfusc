package com.aivox.besota.sdk.message;

import com.aivox.besota.sdk.utils.MessageID;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GestureMessage extends BaseMessage {
    private List<GestureInfo> gestureInfoList;

    public GestureMessage(List<GestureInfo> list) {
        this.gestureInfoList = list;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public MessageID getMsgID() {
        return MessageID.GESTURE_STATUS;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public List<GestureInfo> getMsgContent() {
        return this.gestureInfoList;
    }
}
