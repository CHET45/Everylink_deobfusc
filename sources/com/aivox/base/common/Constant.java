package com.aivox.base.common;

import com.aivox.base.C0874R;
import com.blankj.utilcode.util.Utils;
import com.google.android.gms.auth.api.proxy.AuthApiStatusCodes;

/* JADX INFO: loaded from: classes.dex */
public class Constant {
    public static int AI_TYPE_MIND = 4;
    public static int AI_TYPE_OVERVIEW = 2;
    public static int AI_TYPE_SUMMARY = 1;
    public static int AI_TYPE_TALK = 3;
    public static final String AUDIO_BG = "audio_bg";
    public static final String AUDIO_ID = "audio_id";
    public static final int BAR_FADE_DURATION = 500;
    public static final int CHOOSE_AI_IMAGE_REQ_CODE = 10188;
    public static final int CLICK_DELAY_TIME = 500;
    public static final int CLICK_DELAY_TIME_2 = 2000;
    public static final int CLICK_TIME = 60000;
    public static final boolean CanChangeAudioSource = true;
    public static final boolean CanChangeSpeaker = true;
    public static final String CmdDownActiveEnd = "cmdd25";
    public static final String CmdDownActiveStart = "cmdd24";
    public static final String CmdDownBatteryLevel = "cmdd05";
    public static final String CmdDownCalling = "cmdd03";
    public static final String CmdDownCallingE = "cmdde3";
    public static final String CmdDownCallingF = "cmddf3";
    public static final String CmdDownCloseWiFi = "cmdd52";
    public static final String CmdDownEarphoneVersion = "cmdd06";
    public static final String CmdDownEndCall = "cmdd02";
    public static final String CmdDownFileList = "cmdd50";
    public static final String CmdDownHead = "cmdd";
    public static final String CmdDownHeartbeat = "cmdd00";
    public static final String CmdDownLeftEnd = "cmdd09";
    public static final String CmdDownLeftStart = "cmdd07";
    public static final String CmdDownNoCall = "cmdd04";
    public static final String CmdDownOpenWiFi = "cmdd51";
    public static final String CmdDownRequestMode = "cmdd21";
    public static final String CmdDownRightEnd = "cmdd10";
    public static final String CmdDownRightStart = "cmdd08";
    public static final String CmdDownStartCall = "cmdd01";
    public static final String CmdDownStartCallE = "cmdde1";
    public static final String CmdDownStartCallF = "cmddf1";
    public static final String CmdError = "cmdd99";
    public static final String CmdUpBatteryLevel = "cmdu05";
    public static final String CmdUpCallState = "cmdu04";
    public static final String CmdUpCloseWiFi = "cmdu52";
    public static final String CmdUpEarphoneVersion = "cmdu06";
    public static final String CmdUpEnd = "cmdu03";
    public static final String CmdUpEnterActive = "cmdu24";
    public static final String CmdUpEnterSwitch = "cmdu09";
    public static final String CmdUpExitActive = "cmdu25";
    public static final String CmdUpExitSwitch = "cmdu10";
    public static final String CmdUpFileList = "cmdu50";
    public static final String CmdUpHead = "cmdu";
    public static final String CmdUpHeartbeat = "cmdu00";
    public static final String CmdUpLeft = "cmdu07";
    public static final String CmdUpOpenWiFi = "cmdu51";
    public static final String CmdUpPause = "cmdu02";
    public static final String CmdUpPlayEnd = "cmdu12";
    public static final String CmdUpPlayStart = "cmdu11";
    public static final String CmdUpReadyClean = "cmdud1";
    public static final String CmdUpReadyDown = "cmduf1";
    public static final String CmdUpReadyToPhoneRecord = "cmdug1";
    public static final String CmdUpReadyUp = "cmdue1";
    public static final String CmdUpRequestMode = "cmdu21";
    public static final String CmdUpRight = "cmdu08";
    public static final String CmdUpStart = "cmdu01";
    public static final String DECRYPT_KEY = "y1j11vb0vb0fvhnc";
    public static final String DEVICE_NAME = "JVC AOC";
    public static final String DIVIDER = "-";
    public static final int FAKE_TRANSCRIBE_PROGRESS = 1008;
    public static int FOLDER_ID_ALL = 0;
    public static int FOLDER_ID_FAVORITE = 1;
    public static int FOLDER_ID_RECYCLE_BIN = 2;
    public static int FOLDER_ID_SHARE = 3;
    public static int FOLDER_TYPE_CUSTOM = 2;
    public static int FOLDER_TYPE_FAVORITE = 0;
    public static int FOLDER_TYPE_RECYCLE_BIN = 1;
    public static final int GLASS_LOW_BATTERY = 15;
    public static final int GLASS_WIFI_TIMEOUT = 30;
    public static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=com.aivox.everylink";
    public static final int HANDLER_NOTIFY = 1001;
    public static final int HANDLER_XUNFEI_ERROR = 2002;
    public static final int HANDLER_XUNFEI_MSG = 2000;
    public static final int HANDLER_XUNFEI_SEND = 2001;
    public static final String INSTAGRAM_URL = "https://www.instagram.com/litok.ai/";
    public static final String IS_LOCAL_AUDIO = "is_local_audio";
    public static final String KEY_CODE_INPUT_TYPE = "code_input_type";
    public static final String KEY_DATA = "data";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_EMAIL_INPUT_TYPE = "email_input_type";
    public static final String KEY_FLAG = "flag";
    public static final String KEY_IDS = "ids";
    public static final String KEY_INDEX = "index";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PWD = "pwd";
    public static final String KEY_PWD_INPUT_TYPE = "pwd_input_type";
    public static final String KEY_SHOW_MOVE_OUT = "show_move_out";
    public static final String KEY_TITLE = "title";
    public static final String KEY_VIP_LEVEL = "vip_level";
    public static final String LANG_FROM = "from";
    public static final String LANG_TO = "to";
    public static final String LOCAL_AUDIO_DURATION = "local_audio_duration";
    public static final String LOCAL_AUDIO_NAME = "local_audio_name";
    public static final String LOCAL_AUDIO_URL = "local_audio_url";
    public static final String MAC_DECRYPT_KEY = "AUBWSWFHYCKBRYJK";
    public static final int PAGE_SIZE_10 = 10;
    public static final int PAGE_SIZE_20 = 20;
    public static int PAY_TYPE_APPLE = 1;
    public static int PAY_TYPE_GOOGLE = 3;
    public static int PAY_TYPE_PAYPAL = 2;
    public static final int REFRESH_TIME_FLOATING_VIEW = 5000;
    public static final int REFRESH_TIME_SCROLL = 5000;
    public static final int REFRESH_TIME_TRANSFORM = 4000;
    public static final int REFRESH_TIME_UPLOAD = 1000;
    public static final int TRANSCRIBE_PROGRESS = 1006;
    public static final String TRANSCRIBE_TYPE = "transcribeType";
    public static int TYPE_AI_CHAT = 4;
    public static int TYPE_AUDIO = 0;
    public static int TYPE_AUDIO_IMG = 2;
    public static int TYPE_LOG = 3;
    public static int TYPE_USER_AVATAR = 1;
    public static final boolean isTimestampAuthClosed = true;

    public enum AudioStreamType {
        SINGLE_MIX,
        NORMAL,
        LEFT,
        RIGHT
    }

    public interface DotType {
        public static final int NOTICE_MESSAGE = 2;
        public static final int NOTICE_SYSTEM = 1;
        public static final int UPDATE = 0;
    }

    public interface EVENT {
        public static final int ACK_DEVICE_CONNECT_STATE = 51;
        public static final int ACK_DEVICE_INFO = 27;
        public static final int ACK_RECORD_DELETE = 34;
        public static final int ACK_RECORD_LIST = 28;
        public static final int ACK_RECORD_PAUSE = 30;
        public static final int ACK_RECORD_PLAY = 29;
        public static final int ACK_RECORD_START_OR_STOP = 31;
        public static final int ACK_RECORD_STOP = 54;
        public static final int ACK_RECORD_STOP_ING = 55;
        public static final int ACK_RECORD_UPLOAD = 35;
        public static final int ACK_RTC = 33;
        public static final int ACK_THIRD_CONNECT_STATE = 52;
        public static final int ACK_USE_HISTORY = 36;
        public static final int ACK_VOICE = 37;
        public static final int ACTION_CONNECTION_STATE_CHANGED_A2DP_CONNECTED = 37;
        public static final int ACTION_CONNECTION_STATE_CHANGED_A2DP_CONNECTING = 39;
        public static final int ACTION_CONNECTION_STATE_CHANGED_A2DP_DISCONNECTED = 38;
        public static final int ACTION_DISCOVERY_FINISHED = 26;
        public static final int ACTION_DISCOVERY_STARTED = 24;
        public static final int ACTION_FOUND = 25;
        public static final int AI_CHAT_LIST_MODE_CHANGED = 82;
        public static final int AI_CHAT_STATUS_CHECK = 83;
        public static final int AI_SUMMARY_GENERATED = 79;
        public static final int AI_TITLE_GENERATED = 80;
        public static final int AUDIO_SYNC_END = 12;
        public static final int AUDIO_SYNC_ING = 11;
        public static final int AUDIO_TRANS_COMPLETE = 212;
        public static final int AUDIO_TRANS_FAILED = 213;
        public static final int AUDIO_TRANS_PROGRESS = 211;
        public static final int AUDIO_TRANS_START = 210;
        public static final int AUDIO_UPLOAD_COMPLETE = 202;
        public static final int AUDIO_UPLOAD_FAILED = 203;
        public static final int AUDIO_UPLOAD_PROGRESS = 201;
        public static final int AUDIO_UPLOAD_START = 200;
        public static final int BLE_BROKEN_AND_TRY_RECONNECT = 306;
        public static final int BLE_CLOSE_WIFI = 352;
        public static final int BLE_CONNECTED = 300;
        public static final int BLE_DEVICE_ACTIVE_SUCCESS = 313;
        public static final int BLE_DEVICE_POWER = 302;
        public static final int BLE_DEVICE_VERSION = 305;
        public static final int BLE_DISCONNECTED = 301;
        public static final int BLE_EARPHONE_EFFECT = 303;
        public static final int BLE_EARPHONE_END_RECORD_LEFT = 310;
        public static final int BLE_EARPHONE_END_RECORD_RIGHT = 312;
        public static final int BLE_EARPHONE_START_RECORD_LEFT = 309;
        public static final int BLE_EARPHONE_START_RECORD_RIGHT = 311;
        public static final int BLE_FILE_LIST = 350;
        public static final int BLE_GLASS_ASK_AI = 400;
        public static final int BLE_GLASS_BLOCK_AI = 408;
        public static final int BLE_GLASS_BT_VERSION = 450;
        public static final int BLE_GLASS_CLOSE_AP = 402;
        public static final int BLE_GLASS_CMD = 403;
        public static final int BLE_GLASS_CMD_RESPONSE = 404;
        public static final int BLE_GLASS_CREATE_CALENDAR_EVENT = 420;
        public static final int BLE_GLASS_DELETE_CHAT = 414;
        public static final int BLE_GLASS_DOWNLOAD_PROGRESS = 409;
        public static final int BLE_GLASS_GET_DEVICE_INFO = 418;
        public static final int BLE_GLASS_GET_PRODUCT_MODEL = 419;
        public static final int BLE_GLASS_HARDWARE_INFO = 405;
        public static final int BLE_GLASS_HIDE_TAB = 411;
        public static final int BLE_GLASS_HOME_RECOGNITION = 416;
        public static final int BLE_GLASS_HOME_TAB = 407;
        public static final int BLE_GLASS_IMAGE_UPDATE = 406;
        public static final int BLE_GLASS_IS_SYNC = 410;
        public static final int BLE_GLASS_START_RECOGNITION = 413;
        public static final int BLE_GLASS_START_RECOGNITION_TAKE_PHOTO = 417;
        public static final int BLE_GLASS_SWITCH_LANGUAGE = 412;
        public static final int BLE_GLASS_VIDEO_RECORDING_STATUS = 415;
        public static final int BLE_GLASS_WIFI_VERSION = 451;
        public static final int BLE_NOT_PHONE_STATE = 315;
        public static final int BLE_ON_PHONE_STATE = 314;
        public static final int BLE_OPEN_WIFI = 351;
        public static final int BLE_PHONE_CALL_END = 304;
        public static final int BLE_SHOW_CONNECT_DIALOG = 320;
        public static final int BLUETOOTH_DEVICE_CONNECTED = 307;
        public static final int BLUETOOTH_DEVICE_DISCONNECTED = 308;
        public static final int BLUETOOTH_STATE_OFF = 10;
        public static final int BLUETOOTH_STATE_ON = 9;
        public static final int BREAK_BY_PHONE_AND_SAVE = 72;
        public static final int CONNECT_SERVICE_ERROR = 59;
        public static final int DEVICE_CONNECTED = 1;
        public static final int DEVICE_CONNECTING = 3;
        public static final int DEVICE_DISCONNECT = 2;
        public static final int FAVORITE_CHANGED = 502;
        public static final int FOLDER_CHANGED = 501;
        public static final int FOLDER_REFRESH = 500;
        public static final int FONT_SIZE_CHANGED = 84;
        public static final int NETWORK_CHANGED = 45;
        public static final int NETWORK_DISCONNECT = 47;
        public static final int NETWORK_RECONNECT = 46;
        public static final int PAUSE_BT_RECORD = 22;
        public static final int PAY_SUCCESS = 18;
        public static final int PAY_SUCCESS_WECHAT = 17;
        public static final int PLAY_BT_RECORD = 21;
        public static final int POINT_LESS = 63;
        public static final int PONG = 67;
        public static final int POPUP_AUDIO_TXT_TOUCH = 19;
        public static final int POPUP_TOUCH = 16;
        public static final int READY_TO_START_RECORD = 23;
        public static final int REAL_TIME_TRANS_TIME_OUT = 76;
        public static final int RECORDING_BREAK_BY_PHONE = 71;
        public static final int RECORD_DELETE = 105;
        public static final int RECORD_EDIT = 108;
        public static final int RECORD_ING = 102;
        public static final int RECORD_ING_TRANSLATE = 109;
        public static final int RECORD_PAUSE = 103;
        public static final int RECORD_PAUSE_ANSWER_PHONE = 110;
        public static final int RECORD_SAVE = 106;
        public static final int RECORD_SAVE_CANCEL = 107;
        public static final int RECORD_START = 101;
        public static final int RECORD_STOP = 104;
        public static final int RECYCLE_NUM_CHANGED = 503;
        public static final int REFRESH_RECORDINFO_REMOVE = 75;
        public static final int REFRESH_RECORD_INFO = 77;
        public static final int REFRESH_RECORD_LIST = 50;
        public static final int REFRESH_RECORD_TIME = 48;
        public static final int REFRESH_USER_INFO = 49;
        public static final int SPP_CONNECTED = 40;
        public static final int STATE_MACHINE_STATEGO_ERROR = 68;
        public static final int STORAGE_LESS = 62;
        public static final int TIMESTAMP_PAY_SUCCESS = 20;
        public static final int TO_REFRESH_MEMO_LIST = 69;
        public static final int TO_REFRESH_MEMO_LIST_AFTER_DELETE = 70;
        public static final int TRANS_STATE_CHANGE = 74;
        public static final int UPDATE_USERINFO = 15;
        public static final int UPLOAD_PROGRESS = 14;
        public static final int WHISPER_READY = 81;
        public static final int WIFI_CONNECT_RESULT = 41;
        public static final int WS_CONNECT_ERROR = 73;
    }

    public interface PAGE_CHANGED {
        public static final int WEBVIEW_CLICK = 3;
        public static final int WEBVIEW_LOADED = 1;
        public static final int WEBVIEW_LOADING = 2;
        public static final int WEBVIEW_START = 4;
    }

    public interface RecordState {
        public static final int IDLE = 0;
        public static final int PAUSE = 2;
        public static final int RECORDING = 1;
        public static final int STOP = 3;
    }

    public interface SOCKET_CODE {
        public static final int CHILD_ACCOUNT_IS_DELETED = 30010;
        public static final int CONNECT_THIRD_ABNORMAL = 14;
        public static final int CONNECT_THIRD_SUCCESS = 1;
        public static final int DISCONNECT_THIRD = 15;
        public static final int LOGIN_ABNORMAL = 406;
    }

    public interface SmsType {
        public static final int CHANGE_EMAIL = 2;
        public static final int CHANGE_PWD = 4;
        public static final int DELETE = 5;
        public static final int FORGET_PWD = 6;
        public static final int LOGIN = 1;
    }

    public interface TranscribeType {
        public static final int BILINGUAL = 3;
        public static final int EARPHONE_TRANSLATE_BOTH = 104;
        public static final int EARPHONE_TRANSLATE_DOWN = 106;
        public static final int EARPHONE_TRANSLATE_UP = 105;
        public static final int EARPHONE_TRANSLATE_WITH_TTS = 107;
        public static final int EARPHONE_TRANS_BOTH = 101;
        public static final int EARPHONE_TRANS_DOWN = 103;
        public static final int EARPHONE_TRANS_UP = 102;
        public static final int RECORD = 0;
        public static final int SHORTHAND = 1;
        public static final int TRANSLATE = 2;
    }

    public enum SeverErrorCode {
        SUCCESS(0, C0874R.string.server_error_code_0),
        FAILED(9999, C0874R.string.server_error_code_9999),
        PARAMETER_ERROR(1000, C0874R.string.server_error_code_1000),
        OPERATION_FREQUENTLY(1001, C0874R.string.server_error_code_1001),
        ILLEGAL_REQUEST(1002, C0874R.string.server_error_code_1002),
        UN_LOGIN(Constant.HANDLER_XUNFEI_SEND, C0874R.string.server_error_code_2001),
        TICKET_INVALID(Constant.HANDLER_XUNFEI_ERROR, C0874R.string.server_error_code_2002),
        JWT_SIGNATURE_ERROR(2003, C0874R.string.server_error_code_2003),
        JWT_EXPIRE_ERROR(2004, C0874R.string.server_error_code_2004),
        SYSTEM_ERROR_BLACK_IP(2005, C0874R.string.server_error_code_2005),
        USER_NOT_FOUND(2006, C0874R.string.server_error_code_2006),
        USER_ALREADY_REGISTERED(2007, C0874R.string.server_error_code_2007),
        USERNAME_PASSWORD_ERROR(2008, C0874R.string.server_error_code_2008),
        USER_STATUS_ERROR(2009, C0874R.string.server_error_code_2009),
        EMAIL_HAS_BEEN_REGISTERED(2010, C0874R.string.server_error_code_2010),
        VERIFICATION_CODE_FREQUENTLY(2011, C0874R.string.server_error_code_2011),
        VERIFICATION_CODE_SEND_FAIL(2012, C0874R.string.server_error_code_2012),
        VERIFICATION_CODE_WRONG(2013, C0874R.string.server_error_code_2013),
        UNABLE_TO_REGISTER(2014, C0874R.string.server_error_code_2014),
        THE_KEY_NONENTITY(2015, C0874R.string.server_error_code_2015),
        THE_KEY_ERR(2016, C0874R.string.server_error_code_2016),
        IOS_IDENTITY_ERROR(2017, C0874R.string.server_error_code_2017),
        GOOGLE_IDENTITY_ERROR(2018, C0874R.string.server_error_code_2018),
        USER_NOT_EXIST(2019, C0874R.string.server_error_code_2019),
        NEED_LOGIN_AGAIN(2020, C0874R.string.server_error_code_2020),
        USER_BIRTHDAY_ERROR(2101, C0874R.string.server_error_code_2101),
        USER_IS_MYSELF(2102, C0874R.string.server_error_code_2102),
        THE_AUDIO_INEXISTENCE(AuthApiStatusCodes.AUTH_API_ACCESS_FORBIDDEN, C0874R.string.server_error_code_3001),
        THE_AUDIO_NOT_SYNC(AuthApiStatusCodes.AUTH_API_CLIENT_ERROR, C0874R.string.server_error_code_3002),
        SPEAKER_NAME_TOO_LONG(3101, C0874R.string.server_error_code_3101),
        AUDIO_TRANS_DELETE_ERROR(3102, C0874R.string.server_error_code_3102),
        FILE_STATE_IS_RECYCLE_BIN(3201, C0874R.string.server_error_code_3201),
        SAVE_AUDIO_FAIL_NOT(3203, C0874R.string.server_error_code_3203),
        SAVE_AUDIO_FAIL(3204, C0874R.string.server_error_code_3204),
        THE_UPLOADED_FILE_IS_TOO_LARGE(3205, C0874R.string.server_error_code_3205),
        FILE_STATE_IS_NOT_RECYCLE_BIN(3206, C0874R.string.server_error_code_3206),
        THE_AUDIO_IS_BEING_TRANSCRIBED(3301, C0874R.string.server_error_code_3301),
        THE_AUDIO_TRANSCRIBED_INEXISTENCE(3302, C0874R.string.server_error_code_3302),
        THE_AUDIO_IS_NOT_TRANSCRIBED(3303, C0874R.string.server_error_code_3303),
        ASR_LANGUAGE_NOT_EXIST(3304, C0874R.string.server_error_code_3304),
        ASR_COUNT_TOO_MUCH(3305, C0874R.string.server_error_code_3305),
        RECORD_TIME_NOT_ENOUGH(3306, C0874R.string.server_error_code_3306),
        ASR_ERROR(3307, C0874R.string.server_error_code_3307),
        ASR_BUSY(3308, C0874R.string.server_error_code_3308),
        ASR_PROCESS_ERROR(3309, C0874R.string.server_error_code_3309),
        ASR_PROCESS_TIMEOUT(3310, C0874R.string.server_error_code_3310),
        ASR_PROCESS_102_ERROR(3311, C0874R.string.server_error_code_3311),
        ASR_PROCESS_103_ERROR(3312, C0874R.string.server_error_code_3312),
        ASR_SEPARATE_ERROR(3315, C0874R.string.server_error_code_3315),
        TRANSLATION_LANGUAGE_NOT_EXIST(3401, C0874R.string.server_error_code_3401),
        THE_AUDIO_IS_IN_TRANSLATING(3402, C0874R.string.server_error_code_3402),
        TRANSLATION_ARRIVAL_LIMIT(3403, C0874R.string.server_error_code_3403),
        TRANSLATION_ERROR(3404, C0874R.string.server_error_code_3404),
        TRANSLATION_LANG_ERROR(3405, C0874R.string.server_error_code_3405),
        INSUFFICIENT_CLOUD_SPACE(3501, C0874R.string.server_error_code_3501),
        THE_AUDIO_CLOUD_INEXISTENCE(3502, C0874R.string.server_error_code_3502),
        ADD_RECORD_TIME_ERR(3601, C0874R.string.server_error_code_3601),
        VIDEO_REMARK_NULL(3701, C0874R.string.server_error_code_3701),
        VIP_SAVE_SHARE(3801, C0874R.string.server_error_code_3801),
        SHARE_VIDEO_FILE_TOO_MUCH(3802, C0874R.string.server_error_code_3802),
        SHARE_ALREADY_EXPIRE(3803, C0874R.string.server_error_code_3803),
        SHARE_CONTENT_NULL(3804, C0874R.string.server_error_code_3804),
        SHARE_LINK_EXPIRE(3805, C0874R.string.server_error_code_3805),
        SHARE_ALREADY_CLOSE(3806, C0874R.string.server_error_code_3806),
        SHARE_PASSWORD_ERROR(3807, C0874R.string.server_error_code_3807),
        SHARE_STATUS_NOT_SAVE(3808, C0874R.string.server_error_code_3808),
        USER_IS_NOT_SAVE_SHARE(3809, C0874R.string.server_error_code_3809),
        THE_AUDIO_ALREADY_SYNC(3901, C0874R.string.server_error_code_3901),
        PRICE_PACKAGE_EXPIRED(4001, C0874R.string.server_error_code_4001),
        UNIFIED_ORDER_FREQUENTLY(4002, C0874R.string.server_error_code_4002),
        CHARGE_ORDER_GENERATION_FAILURE(4003, C0874R.string.server_error_code_4003),
        CHARGE_ORDER_NOT_EXIST(4004, C0874R.string.server_error_code_4004),
        PAYMENT_STATUS_ERROR(4005, C0874R.string.server_error_code_4005),
        CHARGE_ORDER_ERROR(4006, C0874R.string.server_error_code_4006),
        CHARGE_ORDER_CREATE_ERROR(4007, C0874R.string.server_error_code_4007),
        CHARGE_ORDER_EXPIRE_ERROR(4008, C0874R.string.server_error_code_4008),
        CHARGE_ORDER_CAPTURE_ERROR(4009, C0874R.string.server_error_code_4009),
        SUBSCRIPTION_ERROR(4010, C0874R.string.server_error_code_4010),
        SUBSCRIPTION_CANCEL_ERROR(4011, C0874R.string.server_error_code_4011),
        PAYPAL_ACCESS_TOKEN_ERROR(4012, C0874R.string.server_error_code_4012),
        PAYMENT_ERROR(4013, C0874R.string.server_error_code_4013),
        PRIVILEGE_OUT_OF_DATE(5001, C0874R.string.server_error_code_5001),
        PRIVILEGE_FULLY_CONVERTED(5002, C0874R.string.server_error_code_5002),
        PRIVILEGE_OLD_USER(5003, C0874R.string.server_error_code_5003),
        NUMBER_FOLDERS_NOT_INCREASED(6101, C0874R.string.server_error_code_6101),
        FOLDER_COLOR_NOT_EXIST(6102, C0874R.string.server_error_code_6102),
        SYSTEM_FOLDERS_NOT_EXIST(6103, C0874R.string.server_error_code_6103),
        RECYCLE_FOLDERS_NOT_EXIST(6104, C0874R.string.server_error_code_6104),
        TAG_ID_IS_NULL(6105, C0874R.string.server_error_code_6105),
        TAG_IS_NULL(6106, C0874R.string.server_error_code_6106),
        UPDATE_TAG_NAME_FAIL(6107, C0874R.string.server_error_code_6107),
        UPDATE_TAG_ORDER_FAIL(6108, C0874R.string.server_error_code_6108),
        TAG_SAME_NAME_NOT_EXIST(6109, C0874R.string.server_error_code_6109),
        TAG_SORT_IS_NULL(6110, C0874R.string.server_error_code_6110),
        TAG_BATCH_SIZE_TOO_MUCH(6111, C0874R.string.server_error_code_6111),
        AUDIO_EXISTS_IN_MANY_SYSTEM_FOLDERS(6112, C0874R.string.server_error_code_6112),
        AUDIO_EXISTS_IN_SYSTEM_FOLDERS(6113, C0874R.string.server_error_code_6113),
        AUDIO_AI_SUMMARY_LIMIT(7001, C0874R.string.server_error_code_7001),
        AUDIO_AI_SUMMARY_TOO_LONG(7002, C0874R.string.server_error_code_7002),
        AUDIO_AI_SUMMARY_IS_NULL(7003, C0874R.string.server_error_code_7003),
        AUDIO_AI_SUMMARY_TOO_SHORT(7004, C0874R.string.server_error_code_7003),
        AUDIO_AI_IN_PROGRESS(7006, C0874R.string.server_error_code_7006),
        AUDIO_MARK_NOT_NULL(7101, C0874R.string.server_error_code_7101),
        AUDIO_MARK_IS_NULL(7102, C0874R.string.server_error_code_7102),
        NORMAL_USER_PERMISSION_DENIED(7201, C0874R.string.server_error_code_7201);

        public final int code;
        public final int contentRes;

        SeverErrorCode(int i, int i2) {
            this.code = i;
            this.contentRes = i2;
        }

        public static String getCodeMsg(int i) {
            for (SeverErrorCode severErrorCode : values()) {
                if (severErrorCode.code == i) {
                    return Utils.getApp().getString(severErrorCode.contentRes);
                }
            }
            return "";
        }
    }
}
