package com.aivox.base.util;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ClipboardHelper {
    public static final String TAG = "ClipboardHelper";
    private static volatile ClipboardHelper mInstance;
    private ClipboardManager mClipboardManager;
    private Context mContext;

    private ClipboardHelper(Context context) {
        this.mContext = context;
        this.mClipboardManager = (ClipboardManager) context.getSystemService("clipboard");
    }

    public static ClipboardHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ClipboardHelper.class) {
                if (mInstance == null) {
                    mInstance = new ClipboardHelper(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public boolean hasPrimaryClip() {
        return this.mClipboardManager.hasPrimaryClip();
    }

    public String getClipText() {
        ClipData primaryClip;
        if (hasPrimaryClip() && (primaryClip = this.mClipboardManager.getPrimaryClip()) != null && this.mClipboardManager.getPrimaryClipDescription().hasMimeType("text/plain")) {
            return primaryClip.getItemAt(0).getText().toString();
        }
        return null;
    }

    public String getClipText(Context context) {
        return getClipText(context, 0);
    }

    public String getClipText(Context context, int i) {
        ClipData primaryClip;
        if (hasPrimaryClip() && (primaryClip = this.mClipboardManager.getPrimaryClip()) != null && primaryClip.getItemCount() > i) {
            return primaryClip.getItemAt(i).coerceToText(context).toString();
        }
        return null;
    }

    public void copyText(String str, String str2) {
        this.mClipboardManager.setPrimaryClip(ClipData.newPlainText(str, str2));
    }

    public void copyHtmlText(String str, String str2, String str3) {
        this.mClipboardManager.setPrimaryClip(ClipData.newHtmlText(str, str2, str3));
    }

    public void copyIntent(String str, Intent intent) {
        this.mClipboardManager.setPrimaryClip(ClipData.newIntent(str, intent));
    }

    public void copyUri(ContentResolver contentResolver, String str, Uri uri) {
        this.mClipboardManager.setPrimaryClip(ClipData.newUri(contentResolver, str, uri));
    }

    public void copyMultiple(String str, String str2, List<ClipData.Item> list) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("argument: items error");
        }
        int size = list.size();
        ClipData clipData = new ClipData(str, new String[]{str2}, list.get(0));
        for (int i = 1; i < size; i++) {
            clipData.addItem(list.get(i));
        }
        this.mClipboardManager.setPrimaryClip(clipData);
    }

    public void copyMultiple(String str, String[] strArr, List<ClipData.Item> list) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("argument: items error");
        }
        int size = list.size();
        ClipData clipData = new ClipData(str, strArr, list.get(0));
        for (int i = 1; i < size; i++) {
            clipData.addItem(list.get(i));
        }
        this.mClipboardManager.setPrimaryClip(clipData);
    }

    public CharSequence coercePrimaryClipToText() {
        if (hasPrimaryClip()) {
            return this.mClipboardManager.getPrimaryClip().getItemAt(0).coerceToText(this.mContext);
        }
        return null;
    }

    public CharSequence coercePrimaryClipToStyledText() {
        if (hasPrimaryClip()) {
            return this.mClipboardManager.getPrimaryClip().getItemAt(0).coerceToStyledText(this.mContext);
        }
        return null;
    }

    public CharSequence coercePrimaryClipToHtmlText() {
        if (hasPrimaryClip()) {
            return this.mClipboardManager.getPrimaryClip().getItemAt(0).coerceToHtmlText(this.mContext);
        }
        return null;
    }

    public String getPrimaryClipMimeType() {
        if (hasPrimaryClip()) {
            return this.mClipboardManager.getPrimaryClipDescription().getMimeType(0);
        }
        return null;
    }

    public String getClipMimeType(ClipData clipData) {
        return clipData.getDescription().getMimeType(0);
    }

    public String getClipMimeType(ClipDescription clipDescription) {
        return clipDescription.getMimeType(0);
    }

    public void clearClip() {
        this.mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, ""));
    }

    public ClipData getClipData() {
        if (hasPrimaryClip()) {
            return this.mClipboardManager.getPrimaryClip();
        }
        return null;
    }
}
