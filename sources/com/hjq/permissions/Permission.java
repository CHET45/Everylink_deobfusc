package com.hjq.permissions;

/* JADX INFO: loaded from: classes3.dex */
public final class Permission {
    public static final String ACCEPT_HANDOVER = "android.permission.ACCEPT_HANDOVER";
    public static final String ACCESS_BACKGROUND_LOCATION = "android.permission.ACCESS_BACKGROUND_LOCATION";
    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    public static final String ACCESS_MEDIA_LOCATION = "android.permission.ACCESS_MEDIA_LOCATION";
    public static final String ACCESS_NOTIFICATION_POLICY = "android.permission.ACCESS_NOTIFICATION_POLICY";
    public static final String ACTIVITY_RECOGNITION = "android.permission.ACTIVITY_RECOGNITION";
    public static final String ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL";
    public static final String ANSWER_PHONE_CALLS = "android.permission.ANSWER_PHONE_CALLS";
    public static final String BIND_NOTIFICATION_LISTENER_SERVICE = "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE";
    public static final String BIND_VPN_SERVICE = "android.permission.BIND_VPN_SERVICE";
    public static final String BLUETOOTH_ADVERTISE = "android.permission.BLUETOOTH_ADVERTISE";
    public static final String BLUETOOTH_CONNECT = "android.permission.BLUETOOTH_CONNECT";
    public static final String BLUETOOTH_SCAN = "android.permission.BLUETOOTH_SCAN";
    public static final String BODY_SENSORS = "android.permission.BODY_SENSORS";
    public static final String BODY_SENSORS_BACKGROUND = "android.permission.BODY_SENSORS_BACKGROUND";
    public static final String CALL_PHONE = "android.permission.CALL_PHONE";
    public static final String CAMERA = "android.permission.CAMERA";
    public static final String GET_ACCOUNTS = "android.permission.GET_ACCOUNTS";
    public static final String GET_INSTALLED_APPS = "com.android.permission.GET_INSTALLED_APPS";
    public static final String MANAGE_EXTERNAL_STORAGE = "android.permission.MANAGE_EXTERNAL_STORAGE";
    public static final String NEARBY_WIFI_DEVICES = "android.permission.NEARBY_WIFI_DEVICES";
    public static final String NOTIFICATION_SERVICE = "android.permission.NOTIFICATION_SERVICE";
    public static final String PACKAGE_USAGE_STATS = "android.permission.PACKAGE_USAGE_STATS";
    public static final String PICTURE_IN_PICTURE = "android.permission.PICTURE_IN_PICTURE";
    public static final String POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS";
    public static final String PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS";
    public static final String READ_CALENDAR = "android.permission.READ_CALENDAR";
    public static final String READ_CALL_LOG = "android.permission.READ_CALL_LOG";
    public static final String READ_CONTACTS = "android.permission.READ_CONTACTS";
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final String READ_MEDIA_AUDIO = "android.permission.READ_MEDIA_AUDIO";
    public static final String READ_MEDIA_IMAGES = "android.permission.READ_MEDIA_IMAGES";
    public static final String READ_MEDIA_VIDEO = "android.permission.READ_MEDIA_VIDEO";
    public static final String READ_MEDIA_VISUAL_USER_SELECTED = "android.permission.READ_MEDIA_VISUAL_USER_SELECTED";
    public static final String READ_PHONE_NUMBERS = "android.permission.READ_PHONE_NUMBERS";
    public static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    public static final String READ_SMS = "android.permission.READ_SMS";
    public static final String RECEIVE_MMS = "android.permission.RECEIVE_MMS";
    public static final String RECEIVE_SMS = "android.permission.RECEIVE_SMS";
    public static final String RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
    public static final String RECORD_AUDIO = "android.permission.RECORD_AUDIO";
    public static final String REQUEST_IGNORE_BATTERY_OPTIMIZATIONS = "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS";
    public static final String REQUEST_INSTALL_PACKAGES = "android.permission.REQUEST_INSTALL_PACKAGES";
    public static final String SCHEDULE_EXACT_ALARM = "android.permission.SCHEDULE_EXACT_ALARM";
    public static final String SEND_SMS = "android.permission.SEND_SMS";
    public static final String SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW";
    public static final String USE_SIP = "android.permission.USE_SIP";
    public static final String WRITE_CALENDAR = "android.permission.WRITE_CALENDAR";
    public static final String WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG";
    public static final String WRITE_CONTACTS = "android.permission.WRITE_CONTACTS";
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    public static final String WRITE_SETTINGS = "android.permission.WRITE_SETTINGS";

    public static final class Group {
        public static final String[] STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
        public static final String[] CALENDAR = {Permission.READ_CALENDAR, Permission.WRITE_CALENDAR};
        public static final String[] CONTACTS = {Permission.READ_CONTACTS, Permission.WRITE_CONTACTS, Permission.GET_ACCOUNTS};
        public static final String[] BLUETOOTH = {"android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE"};
    }

    private Permission() {
    }

    static boolean isSpecialPermission(String str) {
        return PermissionUtils.equalsPermission(str, MANAGE_EXTERNAL_STORAGE) || PermissionUtils.equalsPermission(str, REQUEST_INSTALL_PACKAGES) || PermissionUtils.equalsPermission(str, SYSTEM_ALERT_WINDOW) || PermissionUtils.equalsPermission(str, WRITE_SETTINGS) || PermissionUtils.equalsPermission(str, NOTIFICATION_SERVICE) || PermissionUtils.equalsPermission(str, PACKAGE_USAGE_STATS) || PermissionUtils.equalsPermission(str, SCHEDULE_EXACT_ALARM) || PermissionUtils.equalsPermission(str, BIND_NOTIFICATION_LISTENER_SERVICE) || PermissionUtils.equalsPermission(str, ACCESS_NOTIFICATION_POLICY) || PermissionUtils.equalsPermission(str, REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) || PermissionUtils.equalsPermission(str, BIND_VPN_SERVICE) || PermissionUtils.equalsPermission(str, PICTURE_IN_PICTURE);
    }

    static int getPermissionFromAndroidVersion(String str) {
        if (isSpecialPermission(str)) {
            return getSpecialPermissionFromAndroidVersion(str);
        }
        return getDangerPermissionFromAndroidVersion(str);
    }

    static int getSpecialPermissionFromAndroidVersion(String str) {
        if (PermissionUtils.equalsPermission(str, SCHEDULE_EXACT_ALARM)) {
            return 31;
        }
        if (PermissionUtils.equalsPermission(str, MANAGE_EXTERNAL_STORAGE)) {
            return 30;
        }
        if (PermissionUtils.equalsPermission(str, REQUEST_INSTALL_PACKAGES) || PermissionUtils.equalsPermission(str, PICTURE_IN_PICTURE)) {
            return 26;
        }
        if (PermissionUtils.equalsPermission(str, SYSTEM_ALERT_WINDOW) || PermissionUtils.equalsPermission(str, WRITE_SETTINGS) || PermissionUtils.equalsPermission(str, REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) || PermissionUtils.equalsPermission(str, ACCESS_NOTIFICATION_POLICY)) {
            return 23;
        }
        if (PermissionUtils.equalsPermission(str, PACKAGE_USAGE_STATS)) {
            return 21;
        }
        if (PermissionUtils.equalsPermission(str, NOTIFICATION_SERVICE)) {
            return 19;
        }
        if (PermissionUtils.equalsPermission(str, BIND_NOTIFICATION_LISTENER_SERVICE)) {
            return 18;
        }
        PermissionUtils.equalsPermission(str, BIND_VPN_SERVICE);
        return 14;
    }

    static int getDangerPermissionFromAndroidVersion(String str) {
        if (PermissionUtils.equalsPermission(str, READ_MEDIA_VISUAL_USER_SELECTED)) {
            return 34;
        }
        if (PermissionUtils.equalsPermission(str, POST_NOTIFICATIONS) || PermissionUtils.equalsPermission(str, NEARBY_WIFI_DEVICES) || PermissionUtils.equalsPermission(str, BODY_SENSORS_BACKGROUND) || PermissionUtils.equalsPermission(str, "android.permission.READ_MEDIA_IMAGES") || PermissionUtils.equalsPermission(str, "android.permission.READ_MEDIA_VIDEO") || PermissionUtils.equalsPermission(str, "android.permission.READ_MEDIA_AUDIO")) {
            return 33;
        }
        if (PermissionUtils.equalsPermission(str, "android.permission.BLUETOOTH_SCAN") || PermissionUtils.equalsPermission(str, "android.permission.BLUETOOTH_CONNECT") || PermissionUtils.equalsPermission(str, "android.permission.BLUETOOTH_ADVERTISE")) {
            return 31;
        }
        if (PermissionUtils.equalsPermission(str, ACCESS_BACKGROUND_LOCATION) || PermissionUtils.equalsPermission(str, ACTIVITY_RECOGNITION) || PermissionUtils.equalsPermission(str, ACCESS_MEDIA_LOCATION)) {
            return 29;
        }
        if (PermissionUtils.equalsPermission(str, ACCEPT_HANDOVER)) {
            return 28;
        }
        return (PermissionUtils.equalsPermission(str, ANSWER_PHONE_CALLS) || PermissionUtils.equalsPermission(str, READ_PHONE_NUMBERS)) ? 26 : 23;
    }

    static boolean isMustRegisterInManifestFile(String str) {
        return (PermissionUtils.equalsPermission(str, NOTIFICATION_SERVICE) || PermissionUtils.equalsPermission(str, BIND_NOTIFICATION_LISTENER_SERVICE) || PermissionUtils.equalsPermission(str, BIND_VPN_SERVICE) || PermissionUtils.equalsPermission(str, PICTURE_IN_PICTURE)) ? false : true;
    }
}
