package com.lxj.xpopup.core;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.github.houbb.heaven.util.lang.BoolUtil;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.FuckRomUtils;
import com.lxj.xpopup.util.XPopupUtils;
import org.videolan.libvlc.MediaDiscoverer;

/* JADX INFO: loaded from: classes3.dex */
public class FullScreenDialog extends Dialog {
    BasePopupView contentView;

    public FullScreenDialog(Context context) {
        super(context, C2213R.style._XPopup_TransparentDialog);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        BasePopupView basePopupView;
        super.onCreate(bundle);
        if (getWindow() == null || (basePopupView = this.contentView) == null || basePopupView.popupInfo == null) {
            return;
        }
        if (this.contentView.popupInfo.enableShowWhenAppBackground) {
            getWindow().setType(2038);
        }
        if (this.contentView.popupInfo.keepScreenOn) {
            getWindow().addFlags(128);
        }
        getWindow().setBackgroundDrawable(null);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setFlags(16777216, 16777216);
        getWindow().setSoftInputMode(16);
        getWindow().getDecorView().setSystemUiVisibility(MediaDiscoverer.Event.Started);
        getWindow().setLayout(-1, Math.max(XPopupUtils.getAppHeight(getContext()), XPopupUtils.getScreenHeight(getContext())));
        if (isFuckVIVORoom()) {
            getWindow().getDecorView().setTranslationY(-XPopupUtils.getStatusBarHeight());
        }
        setWindowFlag(201326592, false);
        getWindow().setStatusBarColor(0);
        int navigationBarColor = getNavigationBarColor();
        if (navigationBarColor != 0) {
            getWindow().setNavigationBarColor(navigationBarColor);
        }
        getWindow().addFlags(Integer.MIN_VALUE);
        if (!this.contentView.popupInfo.hasNavigationBar.booleanValue()) {
            hideNavigationBar();
        }
        if (!this.contentView.popupInfo.isRequestFocus) {
            getWindow().setFlags(8, 8);
        }
        autoSetStatusBarMode();
        setContentView(this.contentView);
    }

    private int getNavigationBarColor() {
        return this.contentView.popupInfo.navigationBarColor == 0 ? XPopup.getNavigationBarColor() : this.contentView.popupInfo.navigationBarColor;
    }

    public boolean isFuckVIVORoom() {
        boolean z = Build.MODEL.contains(BoolUtil.f541Y) || Build.MODEL.contains("y");
        if (FuckRomUtils.isVivo()) {
            return (Build.VERSION.SDK_INT == 26 || Build.VERSION.SDK_INT == 27) && z;
        }
        return false;
    }

    public boolean isActivityStatusBarLightMode() {
        return (((Activity) this.contentView.getContext()).getWindow().getDecorView().getSystemUiVisibility() & 8192) != 0;
    }

    public void setWindowFlag(int i, boolean z) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (z) {
            attributes.flags = i | attributes.flags;
        } else {
            attributes.flags = (~i) & attributes.flags;
        }
        getWindow().setAttributes(attributes);
    }

    public void autoSetStatusBarMode() {
        if (!this.contentView.popupInfo.hasStatusBar.booleanValue()) {
            getWindow().getDecorView().setSystemUiVisibility(((ViewGroup) getWindow().getDecorView()).getSystemUiVisibility() | 1284);
        }
        View decorView = getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(isActivityStatusBarLightMode() ? systemUiVisibility | 8192 : systemUiVisibility & (-8193));
    }

    public void hideNavigationBar() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            int id = childAt.getId();
            if (id != -1 && "navigationBarBackground".equals(getResNameById(id))) {
                childAt.setVisibility(4);
            }
        }
        viewGroup.setSystemUiVisibility(viewGroup.getSystemUiVisibility() | 4610);
    }

    private String getResNameById(int i) {
        try {
            return getContext().getResources().getResourceEntryName(i);
        } catch (Exception unused) {
            return "";
        }
    }

    public FullScreenDialog setContent(BasePopupView basePopupView) {
        if (basePopupView.getParent() != null) {
            ((ViewGroup) basePopupView.getParent()).removeView(basePopupView);
        }
        this.contentView = basePopupView;
        return this;
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (isFuckVIVORoom()) {
            motionEvent.setLocation(motionEvent.getX(), motionEvent.getY() + XPopupUtils.getStatusBarHeight());
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
