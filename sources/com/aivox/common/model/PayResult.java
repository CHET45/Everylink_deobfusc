package com.aivox.common.model;

import android.text.TextUtils;
import com.aivox.base.util.FileUtils;
import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PayResult {
    private String memo;
    private String result;
    private String resultStatus;

    public PayResult(Map<String, String> map) {
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
    }

    public String toString() {
        return "resultStatus={" + this.resultStatus + "};memo={" + this.memo + "};result={" + this.result + PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX;
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
}
