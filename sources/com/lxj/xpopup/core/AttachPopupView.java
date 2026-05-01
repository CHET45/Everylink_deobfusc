package com.lxj.xpopup.core;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScrollScaleAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AttachPopupView extends BasePopupView {
    protected FrameLayout attachPopupContainer;
    float centerY;
    protected int defaultOffsetX;
    protected int defaultOffsetY;
    public boolean isShowLeft;
    public boolean isShowUp;
    float maxY;
    int overflow;
    float translationX;
    float translationY;

    public AttachPopupView(Context context) {
        super(context);
        this.defaultOffsetY = 0;
        this.defaultOffsetX = 0;
        this.translationX = 0.0f;
        this.translationY = 0.0f;
        this.maxY = XPopupUtils.getAppHeight(getContext());
        this.overflow = XPopupUtils.dp2px(getContext(), 10.0f);
        this.centerY = 0.0f;
        this.attachPopupContainer = (FrameLayout) findViewById(C2213R.id.attachPopupContainer);
    }

    protected void addInnerContent() {
        this.attachPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.attachPopupContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getInnerLayoutId() {
        return C2213R.layout._xpopup_attach_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        if (this.attachPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        if (this.popupInfo.getAtView() == null && this.popupInfo.touchPoint == null) {
            throw new IllegalArgumentException("atView() or watchView() must be called for AttachPopupView before show()！");
        }
        this.defaultOffsetY = this.popupInfo.offsetY == 0 ? XPopupUtils.dp2px(getContext(), 2.0f) : this.popupInfo.offsetY;
        this.defaultOffsetX = this.popupInfo.offsetX;
        this.attachPopupContainer.setTranslationX(this.popupInfo.offsetX);
        this.attachPopupContainer.setTranslationY(this.popupInfo.offsetY);
        applyBg();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.AttachPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                AttachPopupView.this.doAttach();
            }
        });
    }

    protected void applyBg() {
        Drawable.ConstantState constantState;
        if (this.isCreated) {
            return;
        }
        if (getPopupImplView().getBackground() != null && (constantState = getPopupImplView().getBackground().getConstantState()) != null) {
            this.attachPopupContainer.setBackground(constantState.newDrawable(getResources()));
            getPopupImplView().setBackground(null);
        }
        this.attachPopupContainer.setElevation(XPopupUtils.dp2px(getContext(), 20.0f));
    }

    public void doAttach() {
        int screenHeight;
        int i;
        float screenHeight2;
        int i2;
        this.maxY = XPopupUtils.getAppHeight(getContext()) - this.overflow;
        final boolean zIsLayoutRtl = XPopupUtils.isLayoutRtl(getContext());
        if (this.popupInfo == null) {
            return;
        }
        if (this.popupInfo.touchPoint != null) {
            if (XPopup.longClickPoint != null) {
                this.popupInfo.touchPoint = XPopup.longClickPoint;
            }
            this.centerY = this.popupInfo.touchPoint.y;
            if (this.popupInfo.touchPoint.y + getPopupContentView().getMeasuredHeight() > this.maxY) {
                this.isShowUp = this.popupInfo.touchPoint.y > ((float) (XPopupUtils.getScreenHeight(getContext()) / 2));
            } else {
                this.isShowUp = false;
            }
            this.isShowLeft = this.popupInfo.touchPoint.x < ((float) (XPopupUtils.getWindowWidth(getContext()) / 2));
            ViewGroup.LayoutParams layoutParams = getPopupContentView().getLayoutParams();
            if (isShowUpToTarget()) {
                screenHeight2 = this.popupInfo.touchPoint.y - XPopupUtils.getStatusBarHeight();
                i2 = this.overflow;
            } else {
                screenHeight2 = XPopupUtils.getScreenHeight(getContext()) - this.popupInfo.touchPoint.y;
                i2 = this.overflow;
            }
            int i3 = (int) (screenHeight2 - i2);
            int windowWidth = (int) ((this.isShowLeft ? XPopupUtils.getWindowWidth(getContext()) - this.popupInfo.touchPoint.x : this.popupInfo.touchPoint.x) - this.overflow);
            if (getPopupContentView().getMeasuredHeight() > i3) {
                layoutParams.height = i3;
            }
            if (getPopupContentView().getMeasuredWidth() > windowWidth) {
                layoutParams.width = Math.max(windowWidth, getPopupWidth());
            }
            getPopupContentView().setLayoutParams(layoutParams);
            getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.AttachPopupView.2
                @Override // java.lang.Runnable
                public void run() {
                    if (zIsLayoutRtl) {
                        AttachPopupView attachPopupView = AttachPopupView.this;
                        attachPopupView.translationX = -(attachPopupView.isShowLeft ? ((XPopupUtils.getWindowWidth(AttachPopupView.this.getContext()) - AttachPopupView.this.popupInfo.touchPoint.x) - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX : (XPopupUtils.getWindowWidth(AttachPopupView.this.getContext()) - AttachPopupView.this.popupInfo.touchPoint.x) + AttachPopupView.this.defaultOffsetX);
                    } else {
                        AttachPopupView attachPopupView2 = AttachPopupView.this;
                        attachPopupView2.translationX = attachPopupView2.isShowLeft ? AttachPopupView.this.popupInfo.touchPoint.x + AttachPopupView.this.defaultOffsetX : (AttachPopupView.this.popupInfo.touchPoint.x - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX;
                    }
                    if (AttachPopupView.this.popupInfo.isCenterHorizontal) {
                        if (AttachPopupView.this.isShowLeft) {
                            if (zIsLayoutRtl) {
                                AttachPopupView.this.translationX += AttachPopupView.this.getPopupContentView().getMeasuredWidth() / 2.0f;
                            } else {
                                AttachPopupView.this.translationX -= AttachPopupView.this.getPopupContentView().getMeasuredWidth() / 2.0f;
                            }
                        } else if (zIsLayoutRtl) {
                            AttachPopupView.this.translationX -= AttachPopupView.this.getPopupContentView().getMeasuredWidth() / 2.0f;
                        } else {
                            AttachPopupView.this.translationX += AttachPopupView.this.getPopupContentView().getMeasuredWidth() / 2.0f;
                        }
                    }
                    if (AttachPopupView.this.isShowUpToTarget()) {
                        AttachPopupView attachPopupView3 = AttachPopupView.this;
                        attachPopupView3.translationY = (attachPopupView3.popupInfo.touchPoint.y - AttachPopupView.this.getPopupContentView().getMeasuredHeight()) - AttachPopupView.this.defaultOffsetY;
                    } else {
                        AttachPopupView attachPopupView4 = AttachPopupView.this;
                        attachPopupView4.translationY = attachPopupView4.popupInfo.touchPoint.y + AttachPopupView.this.defaultOffsetY;
                    }
                    AttachPopupView.this.getPopupContentView().setTranslationX(AttachPopupView.this.translationX);
                    AttachPopupView.this.getPopupContentView().setTranslationY(AttachPopupView.this.translationY);
                    AttachPopupView.this.initAndStartAnimation();
                }
            });
            return;
        }
        int[] iArr = new int[2];
        this.popupInfo.getAtView().getLocationOnScreen(iArr);
        int i4 = iArr[0];
        final Rect rect = new Rect(i4, iArr[1], this.popupInfo.getAtView().getMeasuredWidth() + i4, iArr[1] + this.popupInfo.getAtView().getMeasuredHeight());
        int i5 = (rect.left + rect.right) / 2;
        boolean z = ((float) (rect.bottom + getPopupContentView().getMeasuredHeight())) > this.maxY;
        this.centerY = (rect.top + rect.bottom) / 2;
        if (z) {
            int statusBarHeight = (rect.top - XPopupUtils.getStatusBarHeight()) - this.overflow;
            if (getPopupContentView().getMeasuredHeight() > statusBarHeight) {
                this.isShowUp = ((float) statusBarHeight) > this.maxY - ((float) rect.bottom);
            } else {
                this.isShowUp = true;
            }
        } else {
            this.isShowUp = false;
        }
        this.isShowLeft = i5 < XPopupUtils.getWindowWidth(getContext()) / 2;
        ViewGroup.LayoutParams layoutParams2 = getPopupContentView().getLayoutParams();
        if (isShowUpToTarget()) {
            screenHeight = rect.top - XPopupUtils.getStatusBarHeight();
            i = this.overflow;
        } else {
            screenHeight = XPopupUtils.getScreenHeight(getContext()) - rect.bottom;
            i = this.overflow;
        }
        int i6 = screenHeight - i;
        int windowWidth2 = (this.isShowLeft ? XPopupUtils.getWindowWidth(getContext()) - rect.left : rect.right) - this.overflow;
        if (getPopupContentView().getMeasuredHeight() > i6) {
            layoutParams2.height = i6;
        }
        if (getPopupContentView().getMeasuredWidth() > windowWidth2) {
            layoutParams2.width = Math.max(windowWidth2, getPopupWidth());
        }
        getPopupContentView().setLayoutParams(layoutParams2);
        getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.AttachPopupView.3
            @Override // java.lang.Runnable
            public void run() {
                if (zIsLayoutRtl) {
                    AttachPopupView attachPopupView = AttachPopupView.this;
                    attachPopupView.translationX = -(attachPopupView.isShowLeft ? ((XPopupUtils.getWindowWidth(AttachPopupView.this.getContext()) - rect.left) - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX : (XPopupUtils.getWindowWidth(AttachPopupView.this.getContext()) - rect.right) + AttachPopupView.this.defaultOffsetX);
                } else {
                    AttachPopupView attachPopupView2 = AttachPopupView.this;
                    attachPopupView2.translationX = attachPopupView2.isShowLeft ? rect.left + AttachPopupView.this.defaultOffsetX : (rect.right - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX;
                }
                if (AttachPopupView.this.popupInfo.isCenterHorizontal) {
                    if (AttachPopupView.this.isShowLeft) {
                        if (zIsLayoutRtl) {
                            AttachPopupView.this.translationX -= (rect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                        } else {
                            AttachPopupView.this.translationX += (rect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                        }
                    } else if (zIsLayoutRtl) {
                        AttachPopupView.this.translationX += (rect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                    } else {
                        AttachPopupView.this.translationX -= (rect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                    }
                }
                if (AttachPopupView.this.isShowUpToTarget()) {
                    AttachPopupView.this.translationY = (rect.top - AttachPopupView.this.getPopupContentView().getMeasuredHeight()) - AttachPopupView.this.defaultOffsetY;
                } else {
                    AttachPopupView.this.translationY = rect.bottom + AttachPopupView.this.defaultOffsetY;
                }
                AttachPopupView.this.getPopupContentView().setTranslationX(AttachPopupView.this.translationX);
                AttachPopupView.this.getPopupContentView().setTranslationY(AttachPopupView.this.translationY);
                AttachPopupView.this.initAndStartAnimation();
            }
        });
    }

    protected void initAndStartAnimation() {
        initAnimator();
        doShowAnimation();
        doAfterShow();
    }

    protected boolean isShowUpToTarget() {
        return this.popupInfo.positionByWindowCenter ? this.centerY > ((float) (XPopupUtils.getAppHeight(getContext()) / 2)) : (this.isShowUp || this.popupInfo.popupPosition == PopupPosition.Top) && this.popupInfo.popupPosition != PopupPosition.Bottom;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        ScrollScaleAnimator scrollScaleAnimator;
        if (isShowUpToTarget()) {
            scrollScaleAnimator = new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), this.isShowLeft ? PopupAnimation.ScrollAlphaFromLeftBottom : PopupAnimation.ScrollAlphaFromRightBottom);
        } else {
            scrollScaleAnimator = new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), this.isShowLeft ? PopupAnimation.ScrollAlphaFromLeftTop : PopupAnimation.ScrollAlphaFromRightTop);
        }
        return scrollScaleAnimator;
    }
}
