package com.aivox.common.http;

import android.content.Context;
import android.util.Base64;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.http.ObjectLoader;
import com.aivox.base.http.PayLoad;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.common.model.LoginBean;
import com.aivox.common.model.RefreshBean;
import com.github.houbb.heaven.constant.PunctuationConst;
import io.reactivex.Observable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class AuthService extends ObjectLoader {
    private IAuthService api;

    public AuthService(Context context) {
        super(context);
    }

    private IAuthService getApi() {
        if (this.api == null) {
            this.api = (IAuthService) new RetrofitServiceManager().create(IAuthService.class);
        }
        return this.api;
    }

    public Observable<LoginBean> loginByPwd(String str, String str2) {
        this.map = new HashMap<>();
        this.map.put("username", str);
        this.map.put("password", str2);
        return observe(getApi().loginByPwd(getReqBody())).map(new PayLoad());
    }

    public Observable<LoginBean> loginByPwdPhone(String str, String str2) {
        this.map = new HashMap<>();
        this.map.put("username", str);
        this.map.put("password", str2);
        return observe(getApi().loginByPwdPhone(getReqBody())).map(new PayLoad());
    }

    public Observable<LoginBean> loginBySmS(String str, String str2) {
        this.map = new HashMap<>();
        this.map.put("username", str);
        this.map.put("verificationCode", str2);
        return observe(getApi().loginBySmS(getReqBody())).map(new PayLoad());
    }

    public Observable<LoginBean> loginByCode(String str, String str2) {
        this.map = new HashMap<>();
        this.map.put("username", str);
        this.map.put("verificationCode", str2);
        return observe(getApi().loginByCode(getReqBody())).map(new PayLoad());
    }

    public Observable<LoginBean> loginByGoogle(String str) {
        this.map = new HashMap<>();
        this.map.put("credential", str);
        return observe(getApi().loginByGoogle(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> logout() {
        return observe(getApi().logout()).map(new PayLoad());
    }

    public Observable<Object> sendSmsCode(String str, int i) {
        this.map = new HashMap<>();
        this.map.put("username", str);
        this.map.put("smsType", Integer.valueOf(i));
        return observe(getApi().sendSmsCode(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> sendPhoneSmsCode(String str, int i) {
        this.map = new HashMap<>();
        this.map.put("username", str);
        this.map.put("smsType", Integer.valueOf(i));
        this.map.put("signature", getSignature(str));
        return observe(getApi().sendPhoneSmsCode(getReqBody())).map(new PayLoad());
    }

    public Observable<Integer> checkEmailExist(String str) {
        return observe(getApi().checkEmailExist(str)).map(new PayLoad());
    }

    public Observable<RefreshBean> refreshToken() {
        return observe(getApi().refreshToken()).map(new PayLoad());
    }

    public Observable<Object> logoff(String str) {
        this.map = new HashMap<>();
        this.map.put("verificationCode", str);
        return observe(getApi().logoff(getReqBody())).map(new PayLoad());
    }

    private String getSignature(String str) {
        String str2 = "smalink@" + BaseAppUtils.getVersionName() + PunctuationConst.f488AT + str;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(BaseGlobalConfig.getSmsKey().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Base64.encodeToString(mac.doFinal(str2.getBytes(StandardCharsets.UTF_8)), 2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
