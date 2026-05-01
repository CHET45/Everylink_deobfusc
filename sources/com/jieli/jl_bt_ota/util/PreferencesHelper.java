package com.jieli.jl_bt_ota.util;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public class PreferencesHelper {

    /* JADX INFO: renamed from: a */
    private static String f802a = "ji_bluetooth_ota_preferences";

    public static String getPreferencesName() {
        return f802a;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(f802a, 0);
    }

    public static void putBooleanValue(Context context, String str, boolean z) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(f802a, 0).edit();
        editorEdit.putBoolean(str, z);
        editorEdit.apply();
    }

    public static void putIntValue(Context context, String str, int i) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(f802a, 0).edit();
        editorEdit.putInt(str, i);
        editorEdit.apply();
    }

    public static void putLongValue(Context context, String str, long j) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(f802a, 0).edit();
        editorEdit.putLong(str, j);
        editorEdit.apply();
    }

    public static void putStringSetValue(Context context, String str, Set<String> set) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(f802a, 0).edit();
        editorEdit.putStringSet(str, set);
        editorEdit.apply();
    }

    public static void putStringValue(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(f802a, 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.apply();
    }

    public static void remove(Context context, String str) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(f802a, 0).edit();
        editorEdit.remove(str);
        editorEdit.apply();
    }

    public void setPreferencesName(String str) {
        f802a = str;
    }
}
