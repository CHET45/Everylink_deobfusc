package com.lxj.xpopup.core;

import android.graphics.PointF;
import android.view.View;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.enums.PopupType;
import com.lxj.xpopup.interfaces.XPopupCallback;

/* JADX INFO: loaded from: classes3.dex */
public class PopupInfo {
    public int maxHeight;
    public int maxWidth;
    public int offsetX;
    public int offsetY;
    public int popupHeight;
    public int popupWidth;
    public XPopupCallback xPopupCallback;
    public PopupType popupType = null;
    public Boolean isDismissOnBackPressed = true;
    public Boolean isDismissOnTouchOutside = true;
    public Boolean autoDismiss = true;
    public Boolean hasShadowBg = true;
    public Boolean hasBlurBg = false;
    public View atView = null;
    public View watchView = null;
    public PopupAnimation popupAnimation = null;
    public PopupAnimator customAnimator = null;
    public PointF touchPoint = null;
    public float borderRadius = 15.0f;
    public Boolean autoOpenSoftInput = false;
    public Boolean isMoveUpToKeyboard = true;
    public PopupPosition popupPosition = null;
    public Boolean hasStatusBarShadow = false;
    public Boolean hasStatusBar = true;
    public Boolean hasNavigationBar = true;
    public int navigationBarColor = 0;
    public Boolean enableDrag = true;
    public boolean isCenterHorizontal = false;
    public boolean isRequestFocus = true;
    public boolean autoFocusEditText = true;
    public boolean isClickThrough = false;
    public boolean isDarkTheme = false;
    public boolean enableShowWhenAppBackground = false;
    public boolean isThreeDrag = false;
    public boolean isDestroyOnDismiss = false;
    public boolean positionByWindowCenter = false;
    public boolean isViewMode = false;
    public boolean keepScreenOn = false;
    public int shadowBgColor = 0;
    public int animationDuration = -1;
    public int statusBarBgColor = 0;

    public View getAtView() {
        return this.atView;
    }
}
