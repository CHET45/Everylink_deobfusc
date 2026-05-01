package com.lxj.xpopup.core;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.BubbleLayout;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BubbleAttachPopupView extends BasePopupView {
    protected BubbleLayout bubbleContainer;
    float centerY;
    protected int defaultOffsetX;
    protected int defaultOffsetY;
    public boolean isShowLeft;
    public boolean isShowUp;
    float maxY;
    int overflow;
    float translationX;
    float translationY;

    public BubbleAttachPopupView(Context context) {
        super(context);
        this.defaultOffsetY = 0;
        this.defaultOffsetX = 0;
        this.translationX = 0.0f;
        this.translationY = 0.0f;
        this.maxY = XPopupUtils.getAppHeight(getContext());
        this.overflow = XPopupUtils.dp2px(getContext(), 10.0f);
        this.centerY = 0.0f;
        this.bubbleContainer = (BubbleLayout) findViewById(C2213R.id.bubbleContainer);
    }

    protected void addInnerContent() {
        this.bubbleContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.bubbleContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getInnerLayoutId() {
        return C2213R.layout._xpopup_bubble_attach_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        if (this.bubbleContainer.getChildCount() == 0) {
            addInnerContent();
        }
        if (this.popupInfo.getAtView() == null && this.popupInfo.touchPoint == null) {
            throw new IllegalArgumentException("atView() or watchView() must be called for BubbleAttachPopupView before show()！");
        }
        this.bubbleContainer.setElevation(XPopupUtils.dp2px(getContext(), 20.0f));
        this.bubbleContainer.setShadowRadius(XPopupUtils.dp2px(getContext(), 3.0f));
        this.defaultOffsetY = this.popupInfo.offsetY;
        this.defaultOffsetX = this.popupInfo.offsetX;
        this.bubbleContainer.setTranslationX(this.popupInfo.offsetX);
        this.bubbleContainer.setTranslationY(this.popupInfo.offsetY);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.BubbleAttachPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                BubbleAttachPopupView.this.doAttach();
            }
        });
    }

    public void doAttach() {
        int screenHeight;
        int i;
        float screenHeight2;
        int i2;
        this.maxY = XPopupUtils.getAppHeight(getContext()) - this.overflow;
        final boolean zIsLayoutRtl = XPopupUtils.isLayoutRtl(getContext());
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
                layoutParams.width = windowWidth;
            }
            getPopupContentView().setLayoutParams(layoutParams);
            getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.BubbleAttachPopupView.2
                @Override // java.lang.Runnable
                public void run() {
                    if (zIsLayoutRtl) {
                        BubbleAttachPopupView bubbleAttachPopupView = BubbleAttachPopupView.this;
                        bubbleAttachPopupView.translationX = -(bubbleAttachPopupView.isShowLeft ? ((XPopupUtils.getWindowWidth(BubbleAttachPopupView.this.getContext()) - BubbleAttachPopupView.this.popupInfo.touchPoint.x) - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) - BubbleAttachPopupView.this.defaultOffsetX : (XPopupUtils.getWindowWidth(BubbleAttachPopupView.this.getContext()) - BubbleAttachPopupView.this.popupInfo.touchPoint.x) + BubbleAttachPopupView.this.defaultOffsetX);
                    } else {
                        BubbleAttachPopupView bubbleAttachPopupView2 = BubbleAttachPopupView.this;
                        bubbleAttachPopupView2.translationX = bubbleAttachPopupView2.isShowLeft ? BubbleAttachPopupView.this.popupInfo.touchPoint.x + BubbleAttachPopupView.this.defaultOffsetX : (BubbleAttachPopupView.this.popupInfo.touchPoint.x - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) - BubbleAttachPopupView.this.defaultOffsetX;
                    }
                    if (BubbleAttachPopupView.this.popupInfo.isCenterHorizontal) {
                        if (BubbleAttachPopupView.this.isShowLeft) {
                            if (zIsLayoutRtl) {
                                BubbleAttachPopupView.this.translationX += BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth() / 2.0f;
                            } else {
                                BubbleAttachPopupView.this.translationX -= BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth() / 2.0f;
                            }
                        } else if (zIsLayoutRtl) {
                            BubbleAttachPopupView.this.translationX -= BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth() / 2.0f;
                        } else {
                            BubbleAttachPopupView.this.translationX += BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth() / 2.0f;
                        }
                    }
                    if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                        BubbleAttachPopupView bubbleAttachPopupView3 = BubbleAttachPopupView.this;
                        bubbleAttachPopupView3.translationY = (bubbleAttachPopupView3.popupInfo.touchPoint.y - BubbleAttachPopupView.this.getPopupContentView().getMeasuredHeight()) - BubbleAttachPopupView.this.defaultOffsetY;
                    } else {
                        BubbleAttachPopupView bubbleAttachPopupView4 = BubbleAttachPopupView.this;
                        bubbleAttachPopupView4.translationY = bubbleAttachPopupView4.popupInfo.touchPoint.y + BubbleAttachPopupView.this.defaultOffsetY;
                    }
                    if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                        BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.BOTTOM);
                    } else {
                        BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.TOP);
                    }
                    if (BubbleAttachPopupView.this.popupInfo.isCenterHorizontal) {
                        BubbleAttachPopupView.this.bubbleContainer.setLookPositionCenter(true);
                    } else if (BubbleAttachPopupView.this.isShowLeft) {
                        BubbleAttachPopupView.this.bubbleContainer.setLookPosition(XPopupUtils.dp2px(BubbleAttachPopupView.this.getContext(), 1.0f));
                    } else {
                        BubbleAttachPopupView.this.bubbleContainer.setLookPosition(BubbleAttachPopupView.this.bubbleContainer.getMeasuredWidth() - XPopupUtils.dp2px(BubbleAttachPopupView.this.getContext(), 1.0f));
                    }
                    BubbleAttachPopupView.this.bubbleContainer.invalidate();
                    BubbleAttachPopupView.this.getPopupContentView().setTranslationX(BubbleAttachPopupView.this.translationX);
                    BubbleAttachPopupView.this.getPopupContentView().setTranslationY(BubbleAttachPopupView.this.translationY);
                    BubbleAttachPopupView.this.initAndStartAnimation();
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
            this.isShowUp = true;
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
            layoutParams2.width = windowWidth2;
        }
        getPopupContentView().setLayoutParams(layoutParams2);
        getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.BubbleAttachPopupView.3
            @Override // java.lang.Runnable
            public void run() {
                if (zIsLayoutRtl) {
                    BubbleAttachPopupView bubbleAttachPopupView = BubbleAttachPopupView.this;
                    bubbleAttachPopupView.translationX = -(bubbleAttachPopupView.isShowLeft ? ((XPopupUtils.getWindowWidth(BubbleAttachPopupView.this.getContext()) - rect.left) - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) - BubbleAttachPopupView.this.defaultOffsetX : (XPopupUtils.getWindowWidth(BubbleAttachPopupView.this.getContext()) - rect.right) + BubbleAttachPopupView.this.defaultOffsetX);
                } else {
                    BubbleAttachPopupView bubbleAttachPopupView2 = BubbleAttachPopupView.this;
                    bubbleAttachPopupView2.translationX = bubbleAttachPopupView2.isShowLeft ? rect.left + BubbleAttachPopupView.this.defaultOffsetX : (rect.right - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) - BubbleAttachPopupView.this.defaultOffsetX;
                }
                if (BubbleAttachPopupView.this.popupInfo.isCenterHorizontal) {
                    if (BubbleAttachPopupView.this.isShowLeft) {
                        if (zIsLayoutRtl) {
                            BubbleAttachPopupView.this.translationX -= (rect.width() - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                        } else {
                            BubbleAttachPopupView.this.translationX += (rect.width() - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                        }
                    } else if (zIsLayoutRtl) {
                        BubbleAttachPopupView.this.translationX += (rect.width() - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                    } else {
                        BubbleAttachPopupView.this.translationX -= (rect.width() - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                    }
                }
                if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                    BubbleAttachPopupView.this.translationY = (rect.top - BubbleAttachPopupView.this.getPopupContentView().getMeasuredHeight()) - BubbleAttachPopupView.this.defaultOffsetY;
                } else {
                    BubbleAttachPopupView.this.translationY = rect.bottom + BubbleAttachPopupView.this.defaultOffsetY;
                }
                if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                    BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.BOTTOM);
                } else {
                    BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.TOP);
                }
                if (BubbleAttachPopupView.this.popupInfo.isCenterHorizontal) {
                    BubbleAttachPopupView.this.bubbleContainer.setLookPositionCenter(true);
                } else {
                    BubbleAttachPopupView.this.bubbleContainer.setLookPosition((rect.left + (rect.width() / 2)) - ((int) BubbleAttachPopupView.this.translationX));
                }
                BubbleAttachPopupView.this.bubbleContainer.invalidate();
                BubbleAttachPopupView.this.getPopupContentView().setTranslationX(BubbleAttachPopupView.this.translationX);
                BubbleAttachPopupView.this.getPopupContentView().setTranslationY(BubbleAttachPopupView.this.translationY);
                BubbleAttachPopupView.this.initAndStartAnimation();
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

    public BubbleAttachPopupView setBubbleBgColor(int i) {
        this.bubbleContainer.setBubbleColor(i);
        this.bubbleContainer.invalidate();
        return this;
    }

    public BubbleAttachPopupView setBubbleRadius(int i) {
        this.bubbleContainer.setBubbleRadius(i);
        this.bubbleContainer.invalidate();
        return this;
    }

    public BubbleAttachPopupView setArrowWidth(int i) {
        this.bubbleContainer.setLookWidth(i);
        this.bubbleContainer.invalidate();
        return this;
    }

    public BubbleAttachPopupView setArrowHeight(int i) {
        this.bubbleContainer.setLookLength(i);
        this.bubbleContainer.invalidate();
        return this;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        return new ScaleAlphaAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScaleAlphaFromCenter);
    }
}
