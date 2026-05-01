package com.aivox.common.socket;

/* JADX INFO: loaded from: classes.dex */
public class DeviceProtocol {
    public static final byte DEVICE_ID = 1;
    public static final byte START = -86;

    public interface MSG_ID_BT {
        public static final byte ACK_DEVICE_INFO = 17;
        public static final byte ACK_RECORD_DELETE = 113;
        public static final byte ACK_RECORD_LIST = 33;
        public static final byte ACK_RECORD_PAUSE = 65;
        public static final byte ACK_RECORD_PLAY = 49;
        public static final byte ACK_RECORD_START_OR_STOP = 81;
        public static final byte ACK_RECORD_STATE = -79;
        public static final byte ACK_RECORD_UPLOAD = 97;
        public static final byte ACK_RTC = -111;
        public static final byte ACK_USE_HISTORY = -95;
        public static final byte ACK_VOICE = -127;
        public static final byte ACK_WIFI = -31;
        public static final byte CMD_DEVICE_INFO = 16;
        public static final byte CMD_RECORD_DELETE = 112;
        public static final byte CMD_RECORD_LIST = 32;
        public static final byte CMD_RECORD_PAUSE = 64;
        public static final byte CMD_RECORD_PLAY = 48;
        public static final byte CMD_RECORD_START_OR_STOP = 80;
        public static final byte CMD_RECORD_UPLOAD = 96;
        public static final byte CMD_RTC = -112;
        public static final byte CMD_USE_HISTORY = -96;
        public static final byte CMD_VOLUME = -128;
        public static final byte CMD_WIFI = -32;
        public static final byte PUSH_RECORD_STATE = -80;
    }

    public interface MSG_ID_WIFI {
        public static final byte ACK_DEVICE_INFO = 17;
        public static final byte ACK_RECORD_DELETE = 113;
        public static final byte ACK_RECORD_LIST = 33;
        public static final byte ACK_RECORD_PAUSE = 65;
        public static final byte ACK_RECORD_PLAY = 49;
        public static final byte ACK_RECORD_START_OR_STOP = 81;
        public static final byte ACK_RECORD_STATE = -79;
        public static final byte ACK_RECORD_STOP = 82;
        public static final byte ACK_RECORD_UPLOAD = 97;
        public static final byte ACK_RTC = -111;
        public static final byte ACK_USE_HISTORY = -95;
        public static final byte ACK_VOICE = -127;
        public static final byte ACK_WIFI = -31;
        public static final byte CMD_APP_CONNECTED = 1;
        public static final byte CMD_AUDIO_STREAM = -63;
        public static final byte CMD_DEVICE_INFO = 16;
        public static final byte CMD_RECORD_DELETE = 112;
        public static final byte CMD_RECORD_LIST = 32;
        public static final byte CMD_RECORD_PAUSE = 64;
        public static final byte CMD_RECORD_PLAY = 48;
        public static final byte CMD_RECORD_START_OR_STOP = 80;
        public static final byte CMD_RECORD_UPLOAD = 96;
        public static final byte CMD_RTC = -112;
        public static final byte CMD_USE_HISTORY = -96;
        public static final byte CMD_VOLUME = -128;
        public static final byte CMD_WIFI = -32;
        public static final byte PUSH_AUDIO_STREAM = -64;
        public static final byte PUSH_RECORD_STATE = -80;
        public static final byte PUSH_THIRD_CONNECT_STATE = -62;
    }

    public interface MSG_ID_WIFI_JSON {
        public static final int ACK_CHECK_LIMIT = 30009;
        public static final int ACK_DEVICE_INFO = 30011;
        public static final int ACK_DEVICE_RECORD_START = 30002;

        @Deprecated
        public static final int ACK_PHONE_RECORD_DELETE = 30028;
        public static final int ACK_PHONE_RECORD_PAUSE = 30022;

        @Deprecated
        public static final int ACK_PHONE_RECORD_RESUME = 30023;
        public static final int ACK_PHONE_RECORD_START = 30021;
        public static final int ACK_PHONE_RECORD_STOP = 30024;
        public static final int ACK_PHONE_VERIFIED = 30005;
        public static final int ACK_RECORD_DELETE = 30017;
        public static final int ACK_RECORD_LIST = 30015;
        public static final int ACK_RECORD_STOP_JSON = 30003;
        public static final int ACK_REMOVE_LIMIT = 30019;
        public static final int ACK_START_RECORDING = 30031;
        public static final int ACK_STOP_RECORDING = 30032;
        public static final int ACK_THIRD_BREAK = 40015;
        public static final int CMD_CHECK_LIMIT = 20009;
        public static final int CMD_DEVICE_INFO = 20005;
        public static final int CMD_DEVICE_RECORD_START = 20003;
        public static final int CMD_DEVICE_RECORD_STOP = 20004;
        public static final int CMD_INSERT_IMG = 20016;
        public static final int CMD_INSERT_MARK = 20015;

        @Deprecated
        public static final int CMD_PHONE_RECORD_PAUSE = 20011;

        @Deprecated
        public static final int CMD_PHONE_RECORD_RESUME = 20012;
        public static final int CMD_PHONE_RECORD_START = 20002;

        @Deprecated
        public static final int CMD_PHONE_RECORD_STOP = 20013;
        public static final int CMD_PHONE_VERIFIED = 20001;
        public static final int CMD_PUSH_TEXT = 20017;
        public static final int CMD_RECORD_DELETE = 20007;
        public static final int CMD_RECORD_LIST = 20006;
        public static final int CMD_REMOVE_LIMIT = 20019;
        public static final int CMD_START_RECORDING = 20021;
        public static final int CMD_STOP_RECORDING = 20022;
        public static final int PING = 9;
        public static final int PONG = 10;
        public static final int PULL_CURRENT_ACCOUNT_IS_LOGGED_OUT = 40008;
        public static final int PULL_DEVICE_STREAM_TIMEOUT_AND_RECONNECT_FAIL = 40003;

        @Deprecated
        public static final int PULL_OSS_UPLOAD_PROGRESS = 30026;
        public static final int PULL_POINT_LESS = 40002;
        public static final int PULL_RECORD_STOP_ING = 30013;
        public static final int PULL_SERVICE_DID_NOT_RECEIVE_STREAM = 40004;
        public static final int PULL_SERVICE_MULTIPART_UPLOAD_ERROR = 40006;
        public static final int PULL_SERVICE_MULTIPART_UPLOAD_RECONNECT_FAIL = 40007;
        public static final int PULL_SERVICE_MULTIPART_UPLOAD_RECONNECT_SUCCESS = 30025;
        public static final int PULL_STORAGE_LESS = 40001;
        public static final int PULL_TRANSCRIBE_INFO = 30006;
        public static final int PULL_TRANSCRIBE_SERVICE_ARREARS = 40005;
        public static final int PUSH_DEVICE_CONNECT_STATE = 10001;

        @Deprecated
        public static final int PUSH_PHONE_RECORD_PUSH_STREAM = 20010;
        public static final int PUSH_THIRD_CONNECT_STATE_JSON = 30004;
    }
}
