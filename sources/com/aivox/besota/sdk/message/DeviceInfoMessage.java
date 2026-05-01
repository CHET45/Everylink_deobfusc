package com.aivox.besota.sdk.message;

import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.utils.MessageID;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class DeviceInfoMessage extends BaseMessage {
    private LinkedList<? extends HmDevice> deviceList;

    public DeviceInfoMessage(LinkedList<? extends HmDevice> linkedList) {
        this.deviceList = linkedList;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public MessageID getMsgID() {
        return MessageID.DEVICE_INFO;
    }

    @Override // com.aivox.besota.sdk.message.BaseMessage
    public LinkedList<? extends HmDevice> getMsgContent() {
        return this.deviceList;
    }
}
