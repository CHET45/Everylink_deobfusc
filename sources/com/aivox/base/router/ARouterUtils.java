package com.aivox.base.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

/* JADX INFO: loaded from: classes.dex */
public class ARouterUtils {
    public static void startWithActivity(Activity activity, String str, int i, Bundle bundle, int i2, NavigationCallback navigationCallback) {
        ARouter.getInstance().build(str).with(bundle).withFlags(i).navigation(activity, i2, navigationCallback);
    }

    public static void startWithActivity(Activity activity, String str, Bundle bundle, int i, NavigationCallback navigationCallback) {
        ARouter.getInstance().build(str).with(bundle).navigation(activity, i, navigationCallback);
    }

    public static void startWithActivity(Activity activity, String str, Bundle bundle, int i) {
        startWithActivity(activity, str, bundle, i, new NavigationCallbackImpl());
    }

    public static void startWithActivity(Activity activity, String str, int i) {
        startWithActivity(activity, str, null, i);
    }

    public static void startWithActivity(Activity activity, String str, Bundle bundle) {
        startWithActivity(activity, str, bundle, -1);
    }

    public static void startWithActivity(Activity activity, String str) {
        startWithActivity(activity, str, null, -1);
    }

    public static void startWithContext(Context context, String str, int i, Bundle bundle, NavigationCallback navigationCallback) {
        ARouter.getInstance().build(str).with(bundle).withFlags(i).navigation(context, navigationCallback);
    }

    public static void startWithContext(Context context, String str, Bundle bundle, NavigationCallback navigationCallback) {
        ARouter.getInstance().build(str).with(bundle).navigation(context, navigationCallback);
    }

    public static void startWithContext(Context context, String str, Bundle bundle) {
        startWithContext(context, str, bundle, new NavigationCallbackImpl());
    }

    public static void startWithContext(Context context, String str) {
        startWithContext(context, str, null);
    }

    public static <T> T startService(Class<? extends T> cls) {
        return (T) ARouter.getInstance().navigation(cls);
    }

    public static Fragment findFragment(String str) {
        return (Fragment) ARouter.getInstance().build(str).navigation();
    }

    public static Fragment findFragment(String str, Bundle bundle) {
        return (Fragment) ARouter.getInstance().build(str).with(bundle).navigation();
    }

    public static Class<?> getClass(String str) {
        Postcard postcardBuild = ARouter.getInstance().build(str);
        LogisticsCenter.completion(postcardBuild);
        return postcardBuild.getDestination();
    }
}
