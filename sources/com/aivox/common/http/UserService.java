package com.aivox.common.http;

import android.content.Context;
import com.aivox.base.http.ObjectLoader;
import com.aivox.base.http.PayLoad;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.model.AllTransTimeBean;
import com.aivox.common.model.DeviceRightsBean;
import com.aivox.common.model.FunctionRightsBean;
import com.aivox.common.model.IssuesTagBean;
import com.aivox.common.model.LeftTimeBean;
import com.aivox.common.model.PresetLanguageBean;
import com.aivox.common.model.UserInfo;
import io.reactivex.Observable;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class UserService extends ObjectLoader {
    private IUserService api;

    public UserService(Context context) {
        super(context);
    }

    private IUserService getApi() {
        if (this.api == null) {
            this.api = (IUserService) new RetrofitServiceManager().create(IUserService.class);
        }
        return this.api;
    }

    public Observable<Object> createPwd(String str) {
        this.map = new HashMap<>();
        this.map.put("password", str);
        return observe(getApi().createPwd(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> changePwd(String str, String str2) {
        this.map = new HashMap<>();
        this.map.put("password", str);
        this.map.put("verifyCode", str2);
        return observe(getApi().changePwd(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> forgetPwd(String str, String str2, String str3) {
        this.map = new HashMap<>();
        this.map.put("password", str);
        this.map.put("verifyCode", str2);
        this.map.put("email", str3);
        return observe(getApi().forgetPwd(getReqBody())).map(new PayLoad());
    }

    public Observable<UserInfo> modifyUserName(String str) {
        this.map = new HashMap<>();
        if (BaseStringUtil.isNotEmpty(str)) {
            this.map.put(SPUtil.NICKNAME, str);
        }
        return observe(getApi().setUserInfo(getReqBody())).map(new PayLoad());
    }

    public Observable<UserInfo> modifyUserInfo(String str, String str2, String str3, int i) {
        this.map = new HashMap<>();
        if (BaseStringUtil.isNotEmpty(str)) {
            this.map.put("avatar", str);
        }
        if (BaseStringUtil.isNotEmpty(str3)) {
            this.map.put("birthday", str3);
        }
        if (BaseStringUtil.isNotEmpty(str2)) {
            this.map.put(SPUtil.NICKNAME, str2);
        }
        this.map.put("gender", Integer.valueOf(i));
        return observe(getApi().setUserInfo(getReqBody())).map(new PayLoad());
    }

    public Observable<UserInfo> getUserInfo() {
        return observe(getApi().getUserInfo().map(new PayLoad()));
    }

    public Observable<Object> changeEmail(String str, String str2) {
        this.map = new HashMap<>();
        this.map.put("email", str);
        this.map.put("verificationCode", str2);
        return observe(getApi().changeEmail(getReqBody())).map(new PayLoad());
    }

    public Observable<PresetLanguageBean> setAsrLanguage(int i) {
        this.map = new HashMap<>();
        this.map.put("defaultAsrLanguage", Integer.valueOf(i));
        return observe(getApi().setAsrLanguage(getReqBody())).map(new PayLoad());
    }

    public Observable<PresetLanguageBean> setTranslateLanguage(int i, int i2) {
        this.map = new HashMap<>();
        this.map.put("defaultTranslateOrigin", Integer.valueOf(i));
        this.map.put("defaultTranslateTarget", Integer.valueOf(i2));
        return observe(getApi().setTranslateLanguage(getReqBody())).map(new PayLoad());
    }

    public Observable<PresetLanguageBean> getPresetLanguage() {
        return observe(getApi().getPresetLanguage()).map(new PayLoad());
    }

    public Observable<FunctionRightsBean> getFunctionRights() {
        return observe(getApi().getFunctionRights()).map(new PayLoad());
    }

    public Observable<LeftTimeBean> getLeftTime() {
        return observe(getApi().getLeftTime()).map(new PayLoad());
    }

    public Observable<List<DeviceRightsBean>> getDeviceRights() {
        return observe(getApi().getDeviceRights()).map(new PayLoad());
    }

    public Observable<AllTransTimeBean> getAllRecordTimeList() {
        return observe(getApi().getAllTransTimeList()).map(new PayLoad());
    }

    public Observable<Boolean> checkCloudSpace(long j) {
        return observe(getApi().checkCloudSpace(j)).map(new PayLoad());
    }

    public Observable<Object> saveNotifyEmail(String str) {
        this.map = new HashMap<>();
        this.map.put("email", str);
        return observe(getApi().saveNotifyEmail(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> userFeedback(String str, String str2, List<Integer> list, List<String> list2, String str3) {
        this.map = new HashMap<>();
        this.map.put("email", str);
        this.map.put("question", str2);
        this.map.put("issueTypeList", list);
        this.map.put("urlList", list2);
        this.map.put("url", str3);
        return observe(getApi().userFeedback(getReqBody())).map(new PayLoad());
    }

    public Observable<List<IssuesTagBean>> getIssuesTagList() {
        return observe(getApi().getIssuesTagList()).map(new PayLoad());
    }
}
