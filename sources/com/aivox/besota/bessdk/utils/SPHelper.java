package com.aivox.besota.bessdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public class SPHelper {
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("bes_app_config", 0);
    }

    public static void putPreference(Context context, String str, Object obj) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        if (obj instanceof Integer) {
            editorEdit.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Short) {
            editorEdit.putInt(str, ((Short) obj).shortValue());
        } else if (obj instanceof Byte) {
            editorEdit.putInt(str, ((Byte) obj).byteValue());
        } else if (obj instanceof Float) {
            editorEdit.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            editorEdit.putFloat(str, (float) ((Double) obj).doubleValue());
        } else if (obj instanceof Long) {
            editorEdit.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Boolean) {
            editorEdit.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof String) {
            editorEdit.putString(str, (String) obj);
        }
        editorEdit.commit();
    }

    public static Object getPreference(Context context, String str, Object obj) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        if ((obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte)) {
            return Integer.valueOf(sharedPreferences.getInt(str, ((Integer) obj).intValue()));
        }
        if ((obj instanceof Float) || (obj instanceof Double)) {
            return Float.valueOf(sharedPreferences.getFloat(str, ((Float) obj).floatValue()));
        }
        if (obj instanceof Long) {
            return Long.valueOf(sharedPreferences.getLong(str, ((Long) obj).longValue()));
        }
        if (obj instanceof Boolean) {
            return Boolean.valueOf(sharedPreferences.getBoolean(str, ((Boolean) obj).booleanValue()));
        }
        if (obj instanceof String) {
            return sharedPreferences.getString(str, (String) obj);
        }
        return null;
    }

    public static void removePreference(Context context, String str) {
        getSharedPreferences(context).edit().remove(str).commit();
    }
}
