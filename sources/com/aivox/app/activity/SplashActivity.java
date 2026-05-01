package com.aivox.app.activity;

import com.aivox.app.C0726R;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.AccountAction;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ScreenUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AuthService;
import com.aivox.common.model.RefreshBean;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class SplashActivity extends BaseFragmentActivity {
    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        LogUtil.m338i("FirstAct--onCreate");
        setContentView(C0726R.layout.activity_splash);
        ScreenUtil.getDisplayMetrics(this);
        initLaunchLogo();
    }

    private void initLaunchLogo() {
        if (BaseAppUtils.isLogin()) {
            if (System.currentTimeMillis() + 604800000 > ((Long) SPUtil.get(SPUtil.TOKEN_EXPIRE, 0L)).longValue()) {
                new AuthService(this.context).refreshToken().doFinally(new Action() { // from class: com.aivox.app.activity.SplashActivity$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Action
                    public final void run() throws Exception {
                        this.f$0.m2253lambda$initLaunchLogo$0$comaivoxappactivitySplashActivity();
                    }
                }).subscribe(new Consumer() { // from class: com.aivox.app.activity.SplashActivity$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        SplashActivity.lambda$initLaunchLogo$1((RefreshBean) obj);
                    }
                }, new Consumer() { // from class: com.aivox.app.activity.SplashActivity$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ((Throwable) obj).printStackTrace();
                    }
                });
                return;
            } else {
                ARouterUtils.startWithActivity(this, MainAction.MAIN);
                finish();
                return;
            }
        }
        ARouterUtils.startWithActivity(this, AccountAction.ONE_KEY_LOGIN);
        finish();
    }

    /* JADX INFO: renamed from: lambda$initLaunchLogo$0$com-aivox-app-activity-SplashActivity, reason: not valid java name */
    /* synthetic */ void m2253lambda$initLaunchLogo$0$comaivoxappactivitySplashActivity() throws Exception {
        ARouterUtils.startWithActivity(this, MainAction.MAIN);
        finish();
    }

    static /* synthetic */ void lambda$initLaunchLogo$1(RefreshBean refreshBean) throws Exception {
        SPUtil.put(SPUtil.TOKEN, refreshBean.getNewToken());
        SPUtil.put(SPUtil.TOKEN_EXPIRE, Long.valueOf(refreshBean.getNewTokenExpire()));
        SPUtil.put(SPUtil.REFRESH_TOKEN, refreshBean.getNewRefreshToken());
    }
}
