package com.tencent.qcloud.core.util;

import android.text.TextUtils;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes4.dex */
public class DomainSwitchUtils {
    public static String DOMAIN_MYQCLOUD = "myqcloud.com";
    public static String DOMAIN_TENCENTCI = "tencentci.cn";
    public static String DOMAIN_TENCENTCOS = "tencentcos.cn";

    public static boolean isMyqcloudUrl(String str) {
        return isMyqcloudCosUrl(str) || isMyqcloudCiUrl(str);
    }

    public static boolean isMyqcloudCosUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return (!Pattern.compile(".*\\.cos\\..*\\.myqcloud\\.com").matcher(str).matches() || Pattern.compile("service\\.cos\\.myqcloud\\.com").matcher(str).matches() || Pattern.compile("cos\\..*\\.myqcloud\\.com").matcher(str).matches() || Pattern.compile(".*\\.cos\\.accelerate\\.myqcloud\\.com").matcher(str).matches()) ? false : true;
    }

    public static boolean isMyqcloudCiUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile(".*\\.ci\\..*\\.myqcloud\\.com").matcher(str).matches() || Pattern.compile("ci\\..*\\.myqcloud\\.com").matcher(str).matches();
    }
}
