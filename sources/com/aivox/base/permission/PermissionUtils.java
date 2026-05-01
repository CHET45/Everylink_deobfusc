package com.aivox.base.permission;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import com.aivox.base.C0874R;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PermissionUtils {
    public static final String BLUETOOTH_ADVERTISE = "android.permission.BLUETOOTH_ADVERTISE";
    public static final String BLUETOOTH_CONNECT = "android.permission.BLUETOOTH_CONNECT";
    public static final String BLUETOOTH_SCAN = "android.permission.BLUETOOTH_SCAN";
    public static final String[] CALENDAR = Permission.Group.CALENDAR;
    public static final String CAMERA = "android.permission.CAMERA";
    public static final String LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    public static final String READ_AUDIO = "android.permission.READ_MEDIA_AUDIO";
    public static final String READ_IMAGE = "android.permission.READ_MEDIA_IMAGES";
    public static final String READ_VIDEO = "android.permission.READ_MEDIA_VIDEO";
    public static final String RECORD = "android.permission.RECORD_AUDIO";
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    private final PermissionCallback mCallback;
    private final Context mContext;

    private PermissionUtils(Activity activity, PermissionCallback permissionCallback) {
        this.mCallback = permissionCallback;
        this.mContext = activity;
    }

    public static PermissionUtils getIns(Activity activity, PermissionCallback permissionCallback) {
        return new PermissionUtils(activity, permissionCallback);
    }

    public void request(String... strArr) {
        final List<String> denied = XXPermissions.getDenied(this.mContext, strArr);
        if (!denied.isEmpty()) {
            DialogUtils.showDialogWithDefBtn(this.mContext, getPermissionStr(strArr[0]), getPermissionMsg(strArr[0]), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.base.permission.PermissionUtils$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2432lambda$request$0$comaivoxbasepermissionPermissionUtils(denied, context, dialogBuilder, dialog, i, i2, editText);
                }
            }, true, true);
        } else {
            this.mCallback.granted(true);
        }
    }

    /* JADX INFO: renamed from: lambda$request$0$com-aivox-base-permission-PermissionUtils, reason: not valid java name */
    /* synthetic */ void m2432lambda$request$0$comaivoxbasepermissionPermissionUtils(List list, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        XXPermissions.with(this.mContext).permission((List<String>) list).request(new OnPermissionCallback() { // from class: com.aivox.base.permission.PermissionUtils.1
            @Override // com.hjq.permissions.OnPermissionCallback
            public void onGranted(List<String> list2, boolean z) {
                Log.i("tag", "all granted:" + z);
                if (PermissionUtils.this.mCallback != null) {
                    PermissionUtils.this.mCallback.granted(z);
                }
            }

            @Override // com.hjq.permissions.OnPermissionCallback
            public void onDenied(List<String> list2, boolean z) {
                if (PermissionUtils.this.mCallback != null) {
                    Exception exc = new Exception("权限未获取");
                    PermissionUtils.this.mCallback.requestError(exc);
                    BaseAppUtils.printErrorMsg(exc);
                }
            }
        });
    }

    private String getPermissionStr(String str) {
        str.hashCode();
        switch (str) {
            case "android.permission.READ_MEDIA_IMAGES":
            case "android.permission.READ_MEDIA_AUDIO":
            case "android.permission.READ_MEDIA_VIDEO":
            case "android.permission.WRITE_EXTERNAL_STORAGE":
                return this.mContext.getString(C0874R.string.storage_permission);
            case "android.permission.CAMERA":
                return this.mContext.getString(C0874R.string.camera_permission);
            case "android.permission.RECORD_AUDIO":
                return this.mContext.getString(C0874R.string.record_permission);
            default:
                return this.mContext.getString(C0874R.string.default_permission);
        }
    }

    private String getPermissionMsg(String str) {
        str.hashCode();
        switch (str) {
            case "android.permission.READ_MEDIA_IMAGES":
            case "android.permission.READ_MEDIA_AUDIO":
            case "android.permission.READ_MEDIA_VIDEO":
            case "android.permission.WRITE_EXTERNAL_STORAGE":
                return this.mContext.getString(C0874R.string.storage_permission_msg);
            case "android.permission.CAMERA":
                return this.mContext.getString(C0874R.string.camera_permission_msg);
            case "android.permission.RECORD_AUDIO":
                return this.mContext.getString(C0874R.string.record_permission_msg);
            default:
                return this.mContext.getString(C0874R.string.default_permission_msg);
        }
    }
}
