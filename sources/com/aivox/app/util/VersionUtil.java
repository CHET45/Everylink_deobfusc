package com.aivox.app.util;

import android.text.TextUtils;
import com.aivox.base.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class VersionUtil {
    public static boolean hasNewVersion(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && str.contains(".") && str2.contains(".")) {
            String[] strArrSplit = str.split("\\.");
            String[] strArrSplit2 = str2.split("\\.");
            if (strArrSplit.length != strArrSplit2.length || strArrSplit.length != 3) {
                return true;
            }
            for (int i = 0; i < 3; i++) {
                try {
                    if (Integer.parseInt(strArrSplit[i]) > Integer.parseInt(strArrSplit2[i])) {
                        return false;
                    }
                    if (Integer.parseInt(strArrSplit[i]) < Integer.parseInt(strArrSplit2[i])) {
                        return true;
                    }
                } catch (NumberFormatException unused) {
                    LogUtil.m334d("解析眼镜版本号出错");
                }
            }
        }
        return false;
    }
}
