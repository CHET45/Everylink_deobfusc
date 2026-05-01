package com.lxj.xpopup.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.util.XPopupUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PositionPopupView extends BasePopupView {
    FrameLayout positionPopupContainer;

    public PositionPopupView(Context context) {
        super(context);
        this.positionPopupContainer = (FrameLayout) findViewById(C2213R.id.positionPopupContainer);
        this.positionPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.positionPopupContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getInnerLayoutId() {
        return C2213R.layout._xpopup_position_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.PositionPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                if (PositionPopupView.this.popupInfo == null) {
                    return;
                }
                if (PositionPopupView.this.popupInfo.isCenterHorizontal) {
                    PositionPopupView.this.positionPopupContainer.setTranslationX((!XPopupUtils.isLayoutRtl(PositionPopupView.this.getContext()) ? XPopupUtils.getWindowWidth(PositionPopupView.this.getContext()) - PositionPopupView.this.positionPopupContainer.getMeasuredWidth() : -(XPopupUtils.getWindowWidth(PositionPopupView.this.getContext()) - PositionPopupView.this.positionPopupContainer.getMeasuredWidth())) / 2.0f);
                } else {
                    PositionPopupView.this.positionPopupContainer.setTranslationX(PositionPopupView.this.popupInfo.offsetX);
                }
                PositionPopupView.this.positionPopupContainer.setTranslationY(PositionPopupView.this.popupInfo.offsetY);
                PositionPopupView.this.initAndStartAnimation();
            }
        });
    }

    protected void initAndStartAnimation() {
        initAnimator();
        doShowAnimation();
        doAfterShow();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        return new ScaleAlphaAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScaleAlphaFromCenter);
    }
}
