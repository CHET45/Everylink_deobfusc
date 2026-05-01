package com.aivox.base.router;

import android.util.Log;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.utils.Consts;

/* JADX INFO: loaded from: classes.dex */
public class NavigationCallbackImpl implements NavigationCallback {
    @Override // com.alibaba.android.arouter.facade.callback.NavigationCallback
    public void onFound(Postcard postcard) {
        Log.d(Consts.SDK_NAME, "已找到：" + postcard.getPath());
    }

    @Override // com.alibaba.android.arouter.facade.callback.NavigationCallback
    public void onLost(Postcard postcard) {
        Log.d(Consts.SDK_NAME, "未找到：" + postcard.getPath());
    }

    @Override // com.alibaba.android.arouter.facade.callback.NavigationCallback
    public void onArrival(Postcard postcard) {
        Log.d(Consts.SDK_NAME, "已跳转：" + postcard.getPath());
    }

    @Override // com.alibaba.android.arouter.facade.callback.NavigationCallback
    public void onInterrupt(Postcard postcard) {
        Log.d(Consts.SDK_NAME, "被拦截：" + postcard.getPath());
    }
}
