package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityWebBinding extends ViewDataBinding {
    public final HeadTitleLinearView titleView;
    public final WebView webview;

    protected ActivityWebBinding(Object obj, View view2, int i, HeadTitleLinearView headTitleLinearView, WebView webView) {
        super(obj, view2, i);
        this.titleView = headTitleLinearView;
        this.webview = webView;
    }

    public static ActivityWebBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityWebBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityWebBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_web, viewGroup, z, obj);
    }

    public static ActivityWebBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityWebBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityWebBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_web, null, false, obj);
    }

    public static ActivityWebBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityWebBinding bind(View view2, Object obj) {
        return (ActivityWebBinding) bind(obj, view2, C0726R.layout.activity_web);
    }
}
