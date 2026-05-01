package com.aivox.common.base;

import android.app.Application;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.language.MultiLanguageUtil;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.common.http.ApiService;
import com.aivox.common.http.RetrofitServiceManager;
import com.aivox.common.p003db.DaoMaster;
import com.aivox.common.p003db.DaoSession;
import com.aivox.common.p003db.helper.DbOpenHelper;
import com.aivox.common.util.BaseUtil;
import com.aivox.common.util.IsForeBackGroundActivityCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.github.houbb.opencc4j.util.ZhTwConverterUtil;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class AppApplication extends Application {
    private static AppApplication ins = null;
    private static boolean mSDKReady = false;
    private DaoSession daoSession;

    public static AppApplication getIns() {
        return ins;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        ins = this;
        LogUtil.m338i("AppApplication_onCreate");
        Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.aivox.common.base.AppApplication$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AppApplication.lambda$onCreate$0();
            }
        });
        BaseAppUtils.init(this);
        initLanguage();
        initRetrofit();
        initNetwork();
        initARouter();
        initGreenDAO();
        registerActivityLifecycleCallbacks(new IsForeBackGroundActivityCallback());
    }

    static /* synthetic */ void lambda$onCreate$0() {
        ZhTwConverterUtil.toTraditional("你好");
        ZhConverterUtil.toSimple("你好");
    }

    private void initGreenDAO() {
        this.daoSession = new DaoMaster(new DbOpenHelper(this, "kuone_local.db").getWritableDatabase()).newSession();
    }

    public DaoSession getDaoSession() {
        return this.daoSession;
    }

    private void initARouter() {
        if (BaseGlobalConfig.isDebug()) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void initNetwork() {
        /*
            r3 = this;
            com.aivox.common.receive.NetworkReceiver r0 = com.aivox.common.receive.NetworkReceiver.getInstance()
            boolean r1 = com.aivox.base.util.NetUtil.isNetworkConnected()
            r2 = 0
            if (r1 == 0) goto L29
            boolean r1 = com.aivox.base.util.NetUtil.is3G()
            if (r1 == 0) goto L1a
            com.aivox.common.receive.NetworkReceiver r1 = com.aivox.common.receive.NetworkReceiver.getInstance()
            java.util.Objects.requireNonNull(r1)
            r2 = 1
            goto L30
        L1a:
            boolean r1 = com.aivox.base.util.NetUtil.isWifi()
            if (r1 == 0) goto L29
            com.aivox.common.receive.NetworkReceiver r1 = com.aivox.common.receive.NetworkReceiver.getInstance()
            java.util.Objects.requireNonNull(r1)
            r2 = 2
            goto L30
        L29:
            com.aivox.common.receive.NetworkReceiver r1 = com.aivox.common.receive.NetworkReceiver.getInstance()
            java.util.Objects.requireNonNull(r1)
        L30:
            r0.oldState = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.base.AppApplication.initNetwork():void");
    }

    public void initRetrofit() {
        BaseUtil.setApiService((ApiService) RetrofitServiceManager.getInstance().create(ApiService.class));
    }

    private void initLanguage() {
        MultiLanguageUtil.init(this);
    }

    public static void initSDK() {
        if (mSDKReady) {
            return;
        }
        RxJavaPlugins.setErrorHandler(new Consumer() { // from class: com.aivox.common.base.AppApplication$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Throwable th = (Throwable) obj;
                LogUtil.m338i("RxJava : " + th.toString() + "\n" + th.getLocalizedMessage());
            }
        });
        mSDKReady = true;
    }
}
