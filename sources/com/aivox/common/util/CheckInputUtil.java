package com.aivox.common.util;

import com.aivox.base.C0874R;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.RegexUtil;
import com.aivox.base.util.ToastUtil;

/* JADX INFO: loaded from: classes.dex */
public class CheckInputUtil {
    public static boolean checkAccount(boolean z, String str, String str2) {
        if (BaseStringUtil.isEmpty(str)) {
            ToastUtil.showShort(Integer.valueOf(z ? C0874R.string.login_2sendcode_empty_account_phone : C0874R.string.login_2sendcode_empty_account_email));
            return false;
        }
        if ((!z || (RegexUtil.isNumber(str) && (!str2.equals("86") || RegexUtil.isMobile(str)))) && (z || RegexUtil.isEmail(str))) {
            return true;
        }
        ToastUtil.showTextToast(BaseAppUtils.getContext(), BaseAppUtils.getContext().getResources().getString(C0874R.string.login_2sendcode_error_account_phone));
        return false;
    }

    public static boolean checkLoginByPwd(boolean z, String str, String str2, String str3) {
        if (!checkAccount(z, str, str3)) {
            return false;
        }
        if (BaseStringUtil.isEmpty(str2)) {
            ToastUtil.showTextToast(BaseAppUtils.getContext(), Integer.valueOf(C0874R.string.login_loginbypwd_empty_pwd));
            return false;
        }
        if (RegexUtil.isPwd(str2)) {
            return true;
        }
        ToastUtil.showShort(Integer.valueOf(C0874R.string.login_loginbypwd_error_pwd));
        return false;
    }

    public static boolean checkRegister(boolean z, String str, String str2, String str3, String str4, boolean z2, String str5) {
        if (checkAccount(z, str, str5)) {
            if (BaseStringUtil.isEmpty(str2) || BaseStringUtil.isEmpty(str3)) {
                ToastUtil.showTextToast(BaseAppUtils.getContext(), Integer.valueOf(C0874R.string.register_empty_pwd1));
            } else {
                if (!str2.equals(str3)) {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.register_error_pwd_diff));
                    return false;
                }
                if (!RegexUtil.isPwd(str2)) {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.register_error_pwd1));
                    return false;
                }
                if (!RegexUtil.isPwd(str3)) {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.register_error_pwd2));
                    return false;
                }
                if (BaseStringUtil.isEmpty(str4)) {
                    ToastUtil.showTextToast(BaseAppUtils.getContext(), Integer.valueOf(C0874R.string.register_hint_code));
                    return false;
                }
                if (z2) {
                    return true;
                }
                ToastUtil.showTextToast(BaseAppUtils.getContext(), Integer.valueOf(C0874R.string.register_protocal_toast));
                return false;
            }
        }
        return false;
    }

    public static boolean checkForget(boolean z, String str, String str2, String str3, String str4) {
        if (!checkAccount(z, str, str4)) {
            return false;
        }
        if (BaseStringUtil.isEmpty(str2)) {
            ToastUtil.showTextToast(BaseAppUtils.getContext(), Integer.valueOf(C0874R.string.forget_empty_pwd));
            return false;
        }
        if (!RegexUtil.isPwd(str2)) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.forget_error_pwd));
            return false;
        }
        if (!BaseStringUtil.isEmpty(str3)) {
            return true;
        }
        ToastUtil.showTextToast(BaseAppUtils.getContext(), Integer.valueOf(C0874R.string.forget_empty_code));
        return false;
    }

    public static boolean changePwd(boolean z, String str, String str2, String str3, String str4, String str5) {
        if (!checkAccount(z, str, str5)) {
            return false;
        }
        if (BaseStringUtil.isEmpty(str2)) {
            ToastUtil.showTextToast(BaseAppUtils.getContext(), Integer.valueOf(C0874R.string.forget_empty_pwd));
            return false;
        }
        if (!str2.equals(str3)) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.register_error_pwd_diff));
            return false;
        }
        if (!RegexUtil.isPwd(str2)) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.forget_error_pwd));
            return false;
        }
        if (!RegexUtil.isPwd(str3)) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.register_error_pwd2));
            return false;
        }
        if (!BaseStringUtil.isEmpty(str4)) {
            return true;
        }
        ToastUtil.showTextToast(BaseAppUtils.getContext(), Integer.valueOf(C0874R.string.forget_empty_code));
        return false;
    }
}
