package com.aivox.base.util;

import android.content.SharedPreferences;
import com.aivox.base.common.BaseDataHandle;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class SPUtil {
    public static final String ALI_TRANS_KEY = "ali_trans_key";
    public static final String ALI_TRANS_TOKEN = "ali_trans_token";
    public static final String AUDIO_BREAK_BECAUSE_SPP_BREAK = "audio_break_because_spp_break";
    public static final String AUDIO_FONT_SIZE = "audio_font_size";
    public static final String AZURE_DATA_STR = "azure_data_str";
    public static final String BREAK_AUDIO_TIMEOUT_TRANSLATING = "break_audio_timeout_translating";
    public static final String BREAK_SAVE_AUDIO_ID = "break_save_audio_id";
    public static final String CONNECTED_DEVICE_ADDRESS = "connected_device_address";
    public static final String CONNECTED_DEVICE_NAME = "connected_device_name";
    public static final String CONVERSATION_COUNT = "conversation_count";
    public static final String CONVERSATION_TIME_STAMP = "conversation_time_stamp";
    public static final String DEVICE_NOTICE_NAME = "device_notice_name";
    public static final String DO_NOT_SHOW_ACTIVITY_ID = "do_not_show_activity_id";
    public static final String DO_NOT_SHOW_ACTIVITY_ID_24H = "do_not_show_activity_id_24h";
    public static final String DO_NOT_SHOW_ACTIVITY_TIME_STAMP = "do_not_show_activity_time_stamp";
    private static final String FILE_NAME = "listenN`Talk_data";
    public static final String GLASS_AI_MODEL = "glass_ai_model";
    public static final String GLASS_BATTERY = "glass_battery";
    public static final String GLASS_COULD_BREAK = "glass_could_break";
    public static final String GLASS_IS_CHARGING = "glass_is_charging";
    public static final String HAS_BREAK_SAVE_AUDIO = "has_break_save_audio";
    public static final String HAS_NEW_VER = "has_new_ver";
    public static final String IS_CLOUD_FULL = "cloud_full";
    public static final String IS_TRANS_TIME_OUT = "is_trans_timeout";
    public static final String LAST_BLE_DEVICE_NAME = "last_ble_device_name";
    public static final String LAST_BLUETOOTH_DEVICE_NAME = "last_bluetooth_device_name";
    public static final String LAST_POLICY_VERSION = "last_policy_version";
    public static final String LATEST_MSG_NOTICE_ID = "latest_msg_notice_id";
    public static final String LATEST_SYS_NOTICE_ID = "latest_sys_notice_id";
    public static final String LATEST_TIME_NOTICE_TIME_STAMP = "latest_time_notice_time_stamp";
    public static final String LEFT_CURRENCY_TIME = "left_currency_trans_time";
    public static final String LOCAL_AI_AUDIO_ID = "local_ai_audio_id";
    public static final String LOGIN_ACCOUNT = "login_account";
    public static final String NICKNAME = "nickname";
    public static final String PAY_ORDER_NUMBER = "pay_order_number";
    public static final String PAY_PRODUCT_ID = "pay_product_id";
    public static final String PAY_PRODUCT_TYPE = "pay_product_type";
    public static final String PRESET_LANGUAGE_BILINGUAL_FROM = "preset_language_bilingual_from";
    public static final String PRESET_LANGUAGE_BILINGUAL_TO = "preset_language_bilingual_to";
    public static final String PRESET_LANGUAGE_TRANSCRIBE = "preset_language_transcribe";
    public static final String PRESET_LANGUAGE_TRANSCRIBE_TEMP = "preset_language_transcribe_temp";
    public static final String PRESET_LANGUAGE_TRANSLATE_FROM = "preset_language_translate_from";
    public static final String PRESET_LANGUAGE_TRANSLATE_FROM_TEMP = "preset_language_translate_from_temp";
    public static final String PRESET_LANGUAGE_TRANSLATE_TO = "preset_language_translate_to";
    public static final String PRESET_LANGUAGE_TRANSLATE_TO_TEMP = "preset_language_translate_to_temp";
    public static final String QCLOUD_BUCK_NAME = "qcloud_buck_name";
    public static final String QCLOUD_EXP = "qcloud_exp_time";
    public static final String QCLOUD_ID = "qcloud_access_id";
    public static final String QCLOUD_REGION = "qcloud_region";
    public static final String QCLOUD_SK = "qcloud_secret_key";
    public static final String QCLOUD_START = "qcloud_start_time";
    public static final String QCLOUD_TOKEN = "qcloud_access_token";
    public static final String RECENT_LANGUAGE_BILINGUAL_FROM = "recent_language_bilingual_from";
    public static final String RECENT_LANGUAGE_BILINGUAL_TO = "recent_language_bilingual_to";
    public static final String RECENT_LANGUAGE_FROM = "recent_language_from";
    public static final String RECENT_LANGUAGE_TO = "recent_language_to";
    public static final String RECENT_LANGUAGE_TRANSCRIBE = "recent_language_transcribe";
    public static final String RECORD_STATE = "record_state";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static String SCREEN_H = "screen_h";
    public static String SCREEN_W = "screen_w";
    public static final String SEND_RECORD_DURATION = "send_record_duration";
    public static final String SESSION_ID = "session_id";
    private static final String TAG = "SPUtil";
    public static final String TENCENT_TRANS_ID = "tencent_trans_id";
    public static final String TENCENT_TRANS_SI = "tencent_trans_si";
    public static final String TENCENT_TRANS_SK = "tencent_trans_sk";
    public static final String TOKEN = "token";
    public static final String TOKEN_EXPIRE = "token_expire";
    public static final String USER_AVATAR = "user_avatar";
    public static final String USER_ID = "user_id";
    public static final String USE_GUIDE_CHAT = "use_guide_chat";
    public static final String USE_GUIDE_HOME = "use_guide_home";
    public static final String USE_GUIDE_RECORD = "use_guide_record";

    private static SharedPreferences getSp() {
        return BaseAppUtils.getContext().getSharedPreferences(FILE_NAME, 0);
    }

    public static void putWithUid(String str, Object obj) {
        put(str + BaseDataHandle.getIns().getUid(), obj);
    }

    public static Object getWithUid(String str, Object obj) {
        return get(str + BaseDataHandle.getIns().getUid(), obj);
    }

    public static void put(String str, Object obj) {
        try {
            SharedPreferences.Editor editorEdit = getSp().edit();
            if (obj instanceof String) {
                editorEdit.putString(str, (String) obj);
            } else if (obj instanceof Integer) {
                editorEdit.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Boolean) {
                editorEdit.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Float) {
                editorEdit.putFloat(str, ((Float) obj).floatValue());
            } else if (obj instanceof Long) {
                editorEdit.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof Set) {
                editorEdit.putStringSet(str, (Set) obj);
            } else if (obj != null) {
                editorEdit.putString(str, obj.toString());
            }
            SharedPreferencesCompat.apply(editorEdit);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    public static Object get(String str, Object obj) {
        Object stringSet;
        try {
            SharedPreferences sp = getSp();
            if (obj instanceof String) {
                stringSet = sp.getString(str, (String) obj);
            } else if (obj instanceof Integer) {
                stringSet = Integer.valueOf(sp.getInt(str, ((Integer) obj).intValue()));
            } else if (obj instanceof Boolean) {
                stringSet = Boolean.valueOf(sp.getBoolean(str, ((Boolean) obj).booleanValue()));
            } else if (obj instanceof Float) {
                stringSet = Float.valueOf(sp.getFloat(str, ((Float) obj).floatValue()));
            } else if (obj instanceof Long) {
                stringSet = Long.valueOf(sp.getLong(str, ((Long) obj).longValue()));
            } else {
                if (!(obj instanceof Set)) {
                    return obj;
                }
                stringSet = sp.getStringSet(str, (Set) obj);
            }
            return stringSet;
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return obj;
        }
    }

    public static void remove(String str) {
        try {
            SharedPreferences.Editor editorEdit = getSp().edit();
            editorEdit.remove(str);
            SharedPreferencesCompat.apply(editorEdit);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    public static void clearUserSp() {
        put(USER_ID, "");
        put(USER_AVATAR, "");
        put(NICKNAME, "");
        put(TOKEN, "");
        put(TOKEN_EXPIRE, 0L);
        put(REFRESH_TOKEN, "");
    }

    public static boolean contains(String str) {
        return getSp().contains(str);
    }

    public static Map<String, ?> getAll() {
        return getSp().getAll();
    }

    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        private SharedPreferencesCompat() {
        }

        private static Method findApplyMethod() {
            try {
                return SharedPreferences.Editor.class.getMethod("apply", new Class[0]);
            } catch (NoSuchMethodException e) {
                BaseAppUtils.printErrorMsg(e);
                return null;
            }
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                Method method = sApplyMethod;
                if (method != null) {
                    method.invoke(editor, new Object[0]);
                    return;
                }
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
            }
            editor.commit();
        }
    }
}
