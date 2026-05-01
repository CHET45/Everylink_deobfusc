package com.hjq.permissions;

import android.app.Activity;
import android.content.Context;

/* JADX INFO: loaded from: classes3.dex */
class PermissionDelegateImplV34 extends PermissionDelegateImplV33 {
    PermissionDelegateImplV34() {
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV33, com.hjq.permissions.PermissionDelegateImplV31, com.hjq.permissions.PermissionDelegateImplV30, com.hjq.permissions.PermissionDelegateImplV29, com.hjq.permissions.PermissionDelegateImplV28, com.hjq.permissions.PermissionDelegateImplV26, com.hjq.permissions.PermissionDelegateImplV23, com.hjq.permissions.PermissionDelegateImplV21, com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isGrantedPermission(Context context, String str) {
        if (PermissionUtils.equalsPermission(str, Permission.READ_MEDIA_VISUAL_USER_SELECTED)) {
            return PermissionUtils.checkSelfPermission(context, Permission.READ_MEDIA_VISUAL_USER_SELECTED);
        }
        if (PermissionUtils.equalsPermission(str, "android.permission.READ_MEDIA_IMAGES") && !PermissionUtils.checkSelfPermission(context, "android.permission.READ_MEDIA_IMAGES")) {
            return PermissionUtils.checkSelfPermission(context, Permission.READ_MEDIA_VISUAL_USER_SELECTED);
        }
        if (PermissionUtils.equalsPermission(str, "android.permission.READ_MEDIA_VIDEO") && !PermissionUtils.checkSelfPermission(context, "android.permission.READ_MEDIA_VIDEO")) {
            return PermissionUtils.checkSelfPermission(context, Permission.READ_MEDIA_VISUAL_USER_SELECTED);
        }
        return super.isGrantedPermission(context, str);
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV33, com.hjq.permissions.PermissionDelegateImplV31, com.hjq.permissions.PermissionDelegateImplV30, com.hjq.permissions.PermissionDelegateImplV29, com.hjq.permissions.PermissionDelegateImplV28, com.hjq.permissions.PermissionDelegateImplV26, com.hjq.permissions.PermissionDelegateImplV23, com.hjq.permissions.PermissionDelegateImplV21, com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isDoNotAskAgainPermission(Activity activity, String str) {
        if (PermissionUtils.equalsPermission(str, Permission.READ_MEDIA_VISUAL_USER_SELECTED)) {
            return (PermissionUtils.checkSelfPermission(activity, str) || PermissionUtils.shouldShowRequestPermissionRationale(activity, str)) ? false : true;
        }
        return super.isDoNotAskAgainPermission(activity, str);
    }
}
