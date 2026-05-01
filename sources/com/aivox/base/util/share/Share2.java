package com.aivox.base.util.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.aivox.base.util.BaseAppUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class Share2 {
    private static final String TAG = "Share2";
    private Activity activity;
    private String componentClassName;
    private String componentPackageName;
    private String contentText;
    private String contentType;
    private boolean forcedUseSystemChooser;
    private int requestCode;
    private Uri shareFileUri;
    private ArrayList<Uri> shareFileUris;
    private String title;

    private Share2(Builder builder) {
        this.activity = builder.activity;
        this.contentType = builder.contentType;
        this.title = builder.title;
        this.shareFileUri = builder.shareFileUri;
        this.shareFileUris = builder.shareFileUris;
        this.contentText = builder.textContent;
        this.componentPackageName = builder.componentPackageName;
        this.componentClassName = builder.componentClassName;
        this.requestCode = builder.requestCode;
        this.forcedUseSystemChooser = builder.forcedUseSystemChooser;
    }

    public void shareBySystem() {
        if (checkShareParam()) {
            Intent intentCreateShareIntent = createShareIntent();
            if (intentCreateShareIntent == null) {
                Log.e(TAG, "shareBySystem cancel.");
                return;
            }
            if (this.title == null) {
                this.title = "";
            }
            if (this.forcedUseSystemChooser) {
                intentCreateShareIntent = Intent.createChooser(intentCreateShareIntent, this.title);
            }
            if (intentCreateShareIntent.resolveActivity(this.activity.getPackageManager()) != null) {
                try {
                    int i = this.requestCode;
                    if (i != -1) {
                        this.activity.startActivityForResult(intentCreateShareIntent, i);
                    } else {
                        this.activity.startActivity(intentCreateShareIntent);
                    }
                } catch (Exception e) {
                    BaseAppUtils.printErrorMsg(e);
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }
    }

    public void shareBySystem_multiple() {
        if (checkShareParam()) {
            Intent intentCreateShareIntent_multiple = createShareIntent_multiple();
            if (intentCreateShareIntent_multiple == null) {
                Log.e(TAG, "shareBySystem cancel.");
                return;
            }
            if (this.title == null) {
                this.title = "";
            }
            if (this.forcedUseSystemChooser) {
                intentCreateShareIntent_multiple = Intent.createChooser(intentCreateShareIntent_multiple, this.title);
            }
            if (intentCreateShareIntent_multiple.resolveActivity(this.activity.getPackageManager()) != null) {
                try {
                    int i = this.requestCode;
                    if (i != -1) {
                        this.activity.startActivityForResult(intentCreateShareIntent_multiple, i);
                    } else {
                        this.activity.startActivity(intentCreateShareIntent_multiple);
                    }
                } catch (Exception e) {
                    BaseAppUtils.printErrorMsg(e);
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }
    }

    private Intent createShareIntent_multiple() {
        Intent intent;
        intent = new Intent();
        intent.setAction("android.intent.action.SEND_MULTIPLE");
        intent.addFlags(268435456);
        intent.addCategory("android.intent.category.DEFAULT");
        if (!TextUtils.isEmpty(this.componentPackageName) && !TextUtils.isEmpty(this.componentClassName)) {
            intent.setComponent(new ComponentName(this.componentPackageName, this.componentClassName));
        }
        String str = this.contentType;
        str.hashCode();
        switch (str) {
            case "audio/*":
            case "*/*":
            case "video/*":
            case "image/*":
                intent.setAction("android.intent.action.SEND_MULTIPLE");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setType(this.contentType);
                intent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.shareFileUris);
                intent.addFlags(268435456);
                intent.addFlags(1);
                return intent;
            case "text/plain":
                intent.putExtra("android.intent.extra.TEXT", this.contentText);
                intent.setType("text/plain");
                return intent;
            default:
                Log.e(TAG, this.contentType + " is not support share type.");
                return null;
        }
    }

    private Intent createShareIntent() {
        Intent intent;
        intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addFlags(268435456);
        intent.addCategory("android.intent.category.DEFAULT");
        if (!TextUtils.isEmpty(this.componentPackageName) && !TextUtils.isEmpty(this.componentClassName)) {
            intent.setComponent(new ComponentName(this.componentPackageName, this.componentClassName));
        }
        String str = this.contentType;
        str.hashCode();
        switch (str) {
            case "audio/*":
            case "*/*":
            case "video/*":
            case "image/*":
                intent.setAction("android.intent.action.SEND");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setType(this.contentType);
                intent.putExtra("android.intent.extra.STREAM", this.shareFileUri);
                intent.addFlags(268435456);
                intent.addFlags(1);
                Log.d(TAG, "Share uri: " + this.shareFileUri.toString());
                return intent;
            case "text/plain":
                intent.putExtra("android.intent.extra.TEXT", this.contentText);
                intent.setType("text/plain");
                return intent;
            default:
                Log.e(TAG, this.contentType + " is not support share type.");
                return null;
        }
    }

    private boolean checkShareParam() {
        if (this.activity == null) {
            Log.e(TAG, "activity is null.");
            return false;
        }
        if (TextUtils.isEmpty(this.contentType)) {
            Log.e(TAG, "Share content type is empty.");
            return false;
        }
        if ("text/plain".equals(this.contentType)) {
            if (!TextUtils.isEmpty(this.contentText)) {
                return true;
            }
            Log.e(TAG, "Share text context is empty.");
            return false;
        }
        if (this.shareFileUri != null || this.shareFileUris != null) {
            return true;
        }
        Log.e(TAG, "Share file path is null.");
        return false;
    }

    public static class Builder {
        private Activity activity;
        private String componentClassName;
        private String componentPackageName;
        private Uri shareFileUri;
        private String textContent;
        private String title;
        private String contentType = ShareContentType.FILE;
        private ArrayList<Uri> shareFileUris = new ArrayList<>();
        private int requestCode = -1;
        private boolean forcedUseSystemChooser = true;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setContentType(String str) {
            this.contentType = str;
            return this;
        }

        public Builder setTitle(String str) {
            this.title = str;
            return this;
        }

        public Builder setShareFileUri(Uri uri) {
            this.shareFileUri = uri;
            return this;
        }

        public Builder setShareFileUris(ArrayList<Uri> arrayList) {
            this.shareFileUris = arrayList;
            return this;
        }

        public Builder setTextContent(String str) {
            this.textContent = str;
            return this;
        }

        public Builder setShareToComponent(String str, String str2) {
            this.componentPackageName = str;
            this.componentClassName = str2;
            return this;
        }

        public Builder setOnActivityResult(int i) {
            this.requestCode = i;
            return this;
        }

        public Builder forcedUseSystemChooser(boolean z) {
            this.forcedUseSystemChooser = z;
            return this;
        }

        public Share2 build() {
            return new Share2(this);
        }
    }
}
