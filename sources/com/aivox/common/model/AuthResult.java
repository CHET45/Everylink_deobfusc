package com.aivox.common.model;

import android.text.TextUtils;
import androidx.core.provider.FontsContractCompat;
import com.aivox.base.util.FileUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.util.PlaceholderUtil;
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenRequest;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AuthResult {
    private String alipayOpenId;
    private String authCode;
    private String memo;
    private String result;
    private String resultCode;
    private String resultStatus;

    public AuthResult(Map<String, String> map, boolean z) {
        if (map == null) {
            return;
        }
        for (String str : map.keySet()) {
            if (TextUtils.equals(str, "resultStatus")) {
                this.resultStatus = map.get(str);
            } else if (TextUtils.equals(str, "result")) {
                this.result = map.get(str);
            } else if (TextUtils.equals(str, FileUtils.MEMO)) {
                this.memo = map.get(str);
            }
        }
        for (String str2 : this.result.split(PunctuationConst.AND)) {
            if (str2.startsWith("alipay_open_id")) {
                this.alipayOpenId = removeBrackets(getValue("alipay_open_id=", str2), z);
            } else if (str2.startsWith(SaveAccountLinkingTokenRequest.TOKEN_TYPE_AUTH_CODE)) {
                this.authCode = removeBrackets(getValue("auth_code=", str2), z);
            } else if (str2.startsWith(FontsContractCompat.Columns.RESULT_CODE)) {
                this.resultCode = removeBrackets(getValue("result_code=", str2), z);
            }
        }
    }

    private String removeBrackets(String str, boolean z) {
        if (!z || TextUtils.isEmpty(str)) {
            return str;
        }
        if (str.startsWith(PunctuationConst.DOUBLE_QUOTES)) {
            str = str.replaceFirst(PunctuationConst.DOUBLE_QUOTES, "");
        }
        return str.endsWith(PunctuationConst.DOUBLE_QUOTES) ? str.substring(0, str.length() - 1) : str;
    }

    public String toString() {
        return "authCode={" + this.authCode + "}; resultStatus={" + this.resultStatus + "}; memo={" + this.memo + "}; result={" + this.result + PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX;
    }

    private String getValue(String str, String str2) {
        return str2.substring(str.length(), str2.length());
    }

    public String getResultStatus() {
        return this.resultStatus;
    }

    public String getMemo() {
        return this.memo;
    }

    public String getResult() {
        return this.result;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public String getAuthCode() {
        return this.authCode;
    }

    public String getAlipayOpenId() {
        return this.alipayOpenId;
    }
}
