package com.tencent.cos.xml.common;

import com.aivox.common.socket.DeviceProtocol;
import com.jieli.jl_bt_ota.constant.BluetoothConstant;
import com.microsoft.azure.storage.StorageErrorCodeStrings;
import com.tencent.qcloud.track.BuildConfig;

/* JADX INFO: loaded from: classes4.dex */
public enum ClientErrorCode {
    UNKNOWN(-10000, "Unknown Error"),
    INVALID_ARGUMENT(10000, "InvalidArgument"),
    INVALID_CREDENTIALS(10001, "InvalidCredentials"),
    BAD_REQUEST(10002, "BadRequest"),
    SINK_SOURCE_NOT_FOUND(10003, "SinkSourceNotFound"),
    ETAG_NOT_FOUND(BuildConfig.VERSION_CODE, "ETagNotFound"),
    INTERNAL_ERROR(BluetoothConstant.RECEIVE_OTA_CMD_TIMEOUT, StorageErrorCodeStrings.INTERNAL_ERROR),
    SERVERERROR(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_PHONE_VERIFIED, "ServerError"),
    IO_ERROR(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_PHONE_RECORD_START, "IOError"),
    POOR_NETWORK(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_DEVICE_RECORD_START, "NetworkError"),
    USER_CANCELLED(30000, "UserCancelled"),
    ALREADY_FINISHED(30001, "AlreadyFinished"),
    DUPLICATE_TASK(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_DEVICE_RECORD_START, "DuplicateTask"),
    KMS_ERROR(40000, "KMSError");

    private int code;
    private String errorMsg;

    ClientErrorCode(int i, String str) {
        this.code = i;
        this.errorMsg = str;
    }

    /* JADX INFO: renamed from: to */
    public static ClientErrorCode m1857to(int i) {
        for (ClientErrorCode clientErrorCode : values()) {
            if (clientErrorCode.code == i) {
                return clientErrorCode;
            }
        }
        throw new IllegalArgumentException("not error code defined");
    }

    public void setErrorMsg(String str) {
        this.errorMsg = str;
    }

    public int getCode() {
        return this.code;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
}
