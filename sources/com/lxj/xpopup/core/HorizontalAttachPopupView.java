package com.lxj.xpopup.core;

import android.content.Context;
import android.graphics.Rect;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScrollScaleAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;

/* JADX INFO: loaded from: classes3.dex */
public class HorizontalAttachPopupView extends AttachPopupView {
    public HorizontalAttachPopupView(Context context) {
        super(context);
    }

    @Override // com.lxj.xpopup.core.AttachPopupView, com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        this.defaultOffsetY = this.popupInfo.offsetY;
        this.defaultOffsetX = this.popupInfo.offsetX == 0 ? XPopupUtils.dp2px(getContext(), 2.0f) : this.popupInfo.offsetX;
    }

    @Override // com.lxj.xpopup.core.AttachPopupView
    public void doAttach() {
        int i;
        float f;
        float fHeight;
        boolean zIsLayoutRtl = XPopupUtils.isLayoutRtl(getContext());
        int measuredWidth = getPopupContentView().getMeasuredWidth();
        int measuredHeight = getPopupContentView().getMeasuredHeight();
        if (this.popupInfo.touchPoint != null) {
            if (XPopup.longClickPoint != null) {
                this.popupInfo.touchPoint = XPopup.longClickPoint;
            }
            this.isShowLeft = this.popupInfo.touchPoint.x > ((float) (XPopupUtils.getWindowWidth(getContext()) / 2));
            if (zIsLayoutRtl) {
                f = -(this.isShowLeft ? (XPopupUtils.getWindowWidth(getContext()) - this.popupInfo.touchPoint.x) + this.defaultOffsetX : ((XPopupUtils.getWindowWidth(getContext()) - this.popupInfo.touchPoint.x) - getPopupContentView().getMeasuredWidth()) - this.defaultOffsetX);
            } else {
                f = isShowLeftToTarget() ? (this.popupInfo.touchPoint.x - measuredWidth) - this.defaultOffsetX : this.popupInfo.touchPoint.x + this.defaultOffsetX;
            }
            fHeight = (this.popupInfo.touchPoint.y - (measuredHeight * 0.5f)) + this.defaultOffsetY;
        } else {
            int[] iArr = new int[2];
            this.popupInfo.getAtView().getLocationOnScreen(iArr);
            int i2 = iArr[0];
            Rect rect = new Rect(i2, iArr[1], this.popupInfo.getAtView().getMeasuredWidth() + i2, iArr[1] + this.popupInfo.getAtView().getMeasuredHeight());
            this.isShowLeft = (rect.left + rect.right) / 2 > XPopupUtils.getWindowWidth(getContext()) / 2;
            if (zIsLayoutRtl) {
                i = -(this.isShowLeft ? (XPopupUtils.getWindowWidth(getContext()) - rect.left) + this.defaultOffsetX : ((XPopupUtils.getWindowWidth(getContext()) - rect.right) - getPopupContentView().getMeasuredWidth()) - this.defaultOffsetX);
            } else {
                i = isShowLeftToTarget() ? (rect.left - measuredWidth) - this.defaultOffsetX : rect.right + this.defaultOffsetX;
            }
            f = i;
            fHeight = rect.top + ((rect.height() - measuredHeight) / 2) + this.defaultOffsetY;
        }
        getPopupContentView().setTranslationX(f);
        getPopupContentView().setTranslationY(fHeight);
        initAndStartAnimation();
    }

    private boolean isShowLeftToTarget() {
        return (this.isShowLeft || this.popupInfo.popupPosition == PopupPosition.Left) && this.popupInfo.popupPosition != PopupPosition.Right;
    }

    @Override // com.lxj.xpopup.core.AttachPopupView, com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        ScrollScaleAnimator scrollScaleAnimator;
        if (isShowLeftToTarget()) {
            scrollScaleAnimator = new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScrollAlphaFromRight);
        } else {
            scrollScaleAnimator = new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScrollAlphaFromLeft);
        }
        scrollScaleAnimator.isOnlyScaleX = true;
        return scrollScaleAnimator;
    }
}
