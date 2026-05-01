package com.lxj.xpopup.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.SmartDragLayout;

/* JADX INFO: loaded from: classes3.dex */
public class BottomPopupView extends BasePopupView {
    protected SmartDragLayout bottomPopupContainer;

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return 0;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        return null;
    }

    public BottomPopupView(Context context) {
        super(context);
        this.bottomPopupContainer = (SmartDragLayout) findViewById(C2213R.id.bottomPopupContainer);
    }

    protected void addInnerContent() {
        this.bottomPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.bottomPopupContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getInnerLayoutId() {
        return C2213R.layout._xpopup_bottom_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        if (this.bottomPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        this.bottomPopupContainer.setDuration(getAnimationDuration());
        this.bottomPopupContainer.enableDrag(this.popupInfo.enableDrag.booleanValue());
        this.bottomPopupContainer.dismissOnTouchOutside(this.popupInfo.isDismissOnTouchOutside.booleanValue());
        this.bottomPopupContainer.isThreeDrag(this.popupInfo.isThreeDrag);
        getPopupImplView().setTranslationX(this.popupInfo.offsetX);
        getPopupImplView().setTranslationY(this.popupInfo.offsetY);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), null);
        this.bottomPopupContainer.setOnCloseListener(new SmartDragLayout.OnCloseListener() { // from class: com.lxj.xpopup.core.BottomPopupView.1
            @Override // com.lxj.xpopup.widget.SmartDragLayout.OnCloseListener
            public void onOpen() {
            }

            @Override // com.lxj.xpopup.widget.SmartDragLayout.OnCloseListener
            public void onClose() {
                BottomPopupView.this.beforeDismiss();
                if (BottomPopupView.this.popupInfo.xPopupCallback != null) {
                    BottomPopupView.this.popupInfo.xPopupCallback.beforeDismiss(BottomPopupView.this);
                }
                BottomPopupView.this.doAfterDismiss();
            }

            @Override // com.lxj.xpopup.widget.SmartDragLayout.OnCloseListener
            public void onDrag(int i, float f, boolean z) {
                if (BottomPopupView.this.popupInfo == null) {
                    return;
                }
                if (BottomPopupView.this.popupInfo.xPopupCallback != null) {
                    BottomPopupView.this.popupInfo.xPopupCallback.onDrag(BottomPopupView.this, i, f, z);
                }
                if (!BottomPopupView.this.popupInfo.hasShadowBg.booleanValue() || BottomPopupView.this.popupInfo.hasBlurBg.booleanValue()) {
                    return;
                }
                BottomPopupView bottomPopupView = BottomPopupView.this;
                bottomPopupView.setBackgroundColor(bottomPopupView.shadowBgAnimator.calculateBgColor(f));
            }
        });
        this.bottomPopupContainer.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.core.BottomPopupView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                BottomPopupView.this.dismiss();
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doShowAnimation() {
        if (this.popupInfo.hasBlurBg.booleanValue() && this.blurAnimator != null) {
            this.blurAnimator.animateShow();
        }
        this.bottomPopupContainer.open();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doDismissAnimation() {
        if (this.popupInfo.hasBlurBg.booleanValue() && this.blurAnimator != null) {
            this.blurAnimator.animateDismiss();
        }
        this.bottomPopupContainer.close();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doAfterDismiss() {
        if (this.popupInfo != null && this.popupInfo.autoOpenSoftInput.booleanValue()) {
            KeyboardUtils.hideSoftInput(this);
        }
        this.handler.removeCallbacks(this.doAfterDismissTask);
        this.handler.postDelayed(this.doAfterDismissTask, 0L);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        if (this.popupInfo == null || this.popupStatus == PopupStatus.Dismissing) {
            return;
        }
        this.popupStatus = PopupStatus.Dismissing;
        if (this.popupInfo.autoOpenSoftInput.booleanValue()) {
            KeyboardUtils.hideSoftInput(this);
        }
        clearFocus();
        this.bottomPopupContainer.close();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getMaxWidth() {
        return this.popupInfo.maxWidth == 0 ? XPopupUtils.getWindowWidth(getContext()) : this.popupInfo.maxWidth;
    }
}
