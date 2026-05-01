package com.lxj.xpopup.util;

import com.hjq.permissions.Permission;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes4.dex */
public final class PermissionConstants {
    public static final String ACTIVITY_RECOGNITION = "ACTIVITY_RECOGNITION";
    public static final String CALENDAR = "CALENDAR";
    public static final String CAMERA = "CAMERA";
    public static final String CONTACTS = "CONTACTS";
    public static final String LOCATION = "LOCATION";
    public static final String MICROPHONE = "MICROPHONE";
    public static final String PHONE = "PHONE";
    public static final String SENSORS = "SENSORS";
    public static final String SMS = "SMS";
    public static final String STORAGE = "STORAGE";
    private static final String[] GROUP_CALENDAR = {Permission.READ_CALENDAR, Permission.WRITE_CALENDAR};
    private static final String[] GROUP_CAMERA = {"android.permission.CAMERA"};
    private static final String[] GROUP_CONTACTS = {Permission.READ_CONTACTS, Permission.WRITE_CONTACTS, Permission.GET_ACCOUNTS};
    private static final String[] GROUP_LOCATION = {"android.permission.ACCESS_FINE_LOCATION", Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_BACKGROUND_LOCATION};
    private static final String[] GROUP_MICROPHONE = {"android.permission.RECORD_AUDIO"};
    private static final String[] GROUP_PHONE = {Permission.READ_PHONE_STATE, Permission.READ_PHONE_NUMBERS, Permission.CALL_PHONE, Permission.READ_CALL_LOG, Permission.WRITE_CALL_LOG, Permission.ADD_VOICEMAIL, Permission.USE_SIP, Permission.PROCESS_OUTGOING_CALLS, Permission.ANSWER_PHONE_CALLS};
    private static final String[] GROUP_PHONE_BELOW_O = {Permission.READ_PHONE_STATE, Permission.READ_PHONE_NUMBERS, Permission.CALL_PHONE, Permission.READ_CALL_LOG, Permission.WRITE_CALL_LOG, Permission.ADD_VOICEMAIL, Permission.USE_SIP, Permission.PROCESS_OUTGOING_CALLS};
    private static final String[] GROUP_SENSORS = {Permission.BODY_SENSORS};
    private static final String[] GROUP_SMS = {Permission.SEND_SMS, Permission.RECEIVE_SMS, Permission.READ_SMS, Permission.RECEIVE_WAP_PUSH, Permission.RECEIVE_MMS};
    private static final String[] GROUP_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final String[] GROUP_ACTIVITY_RECOGNITION = {Permission.ACTIVITY_RECOGNITION};

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionGroup {
    }

    public static String[] getPermissions(String str) {
        if (str == null) {
            return new String[0];
        }
        str.hashCode();
        switch (str) {
            case "LOCATION":
                return GROUP_LOCATION;
            case "SENSORS":
                return GROUP_SENSORS;
            case "STORAGE":
                return GROUP_STORAGE;
            case "SMS":
                return GROUP_SMS;
            case "PHONE":
                return GROUP_PHONE;
            case "ACTIVITY_RECOGNITION":
                return GROUP_ACTIVITY_RECOGNITION;
            case "CONTACTS":
                return GROUP_CONTACTS;
            case "CALENDAR":
                return GROUP_CALENDAR;
            case "MICROPHONE":
                return GROUP_MICROPHONE;
            case "CAMERA":
                return GROUP_CAMERA;
            default:
                return new String[]{str};
        }
    }
}
