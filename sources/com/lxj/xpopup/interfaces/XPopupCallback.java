package com.lxj.xpopup.interfaces;

import com.lxj.xpopup.core.BasePopupView;

/* JADX INFO: loaded from: classes4.dex */
public interface XPopupCallback {
    void beforeDismiss(BasePopupView basePopupView);

    void beforeShow(BasePopupView basePopupView);

    boolean onBackPressed(BasePopupView basePopupView);

    void onCreated(BasePopupView basePopupView);

    void onDismiss(BasePopupView basePopupView);

    void onDrag(BasePopupView basePopupView, int i, float f, boolean z);

    void onKeyBoardStateChanged(BasePopupView basePopupView, int i);

    void onShow(BasePopupView basePopupView);
}
