package com.aivox.base.language;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public class CommSharedUtil {
    private static final String SHARED_PATH = "app_info";
    private static CommSharedUtil helper;
    private SharedPreferences sharedPreferences;

    private CommSharedUtil(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PATH, 0);
    }

    public static CommSharedUtil getInstance(Context context) {
        if (helper == null) {
            helper = new CommSharedUtil(context);
        }
        return helper;
    }

    public void putInt(String str, int i) {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        editorEdit.putInt(str, i);
        editorEdit.apply();
    }

    public int getInt(String str) {
        return this.sharedPreferences.getInt(str, 0);
    }

    public void putString(String str, String str2) {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        editorEdit.putString(str, str2);
        editorEdit.apply();
    }

    public String getString(String str) {
        return this.sharedPreferences.getString(str, null);
    }

    public void putBoolean(String str, boolean z) {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        editorEdit.putBoolean(str, z);
        editorEdit.apply();
    }

    public boolean getBoolean(String str, boolean z) {
        return this.sharedPreferences.getBoolean(str, z);
    }

    public int getInt(String str, int i) {
        return this.sharedPreferences.getInt(str, i);
    }

    public void remove(String str) {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        editorEdit.remove(str);
        editorEdit.apply();
    }
}
