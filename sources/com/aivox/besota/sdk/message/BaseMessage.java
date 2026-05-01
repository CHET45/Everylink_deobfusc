package com.aivox.besota.sdk.message;

import com.aivox.besota.sdk.utils.MessageID;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class BaseMessage implements Serializable {
    private boolean isPush;
    private MessageID messageId;
    private Object msgContent;

    public MessageID getMsgID() {
        return this.messageId;
    }

    public Object getMsgContent() {
        return this.msgContent;
    }

    public boolean isPush() {
        return this.isPush;
    }

    public void setPush(boolean z) {
        this.isPush = z;
    }

    public void setMessageId(MessageID messageID) {
        this.messageId = messageID;
    }

    public void setMsgContent(Object obj) {
        this.msgContent = obj;
    }
}
