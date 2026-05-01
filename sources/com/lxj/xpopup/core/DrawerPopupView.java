package com.lxj.xpopup.core;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.PopupDrawerLayout;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DrawerPopupView extends BasePopupView {
    public ArgbEvaluator argbEvaluator;
    int currColor;
    int defaultColor;
    protected FrameLayout drawerContentContainer;
    protected PopupDrawerLayout drawerLayout;
    float mFraction;
    Paint paint;
    Rect shadowRect;

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doDismissAnimation() {
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        return null;
    }

    public DrawerPopupView(Context context) {
        super(context);
        this.mFraction = 0.0f;
        this.paint = new Paint();
        this.argbEvaluator = new ArgbEvaluator();
        this.currColor = 0;
        this.defaultColor = 0;
        this.drawerLayout = (PopupDrawerLayout) findViewById(C2213R.id.drawerLayout);
        this.drawerContentContainer = (FrameLayout) findViewById(C2213R.id.drawerContentContainer);
        this.drawerContentContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.drawerContentContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public View getPopupImplView() {
        return this.drawerContentContainer.getChildAt(0);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getInnerLayoutId() {
        return C2213R.layout._xpopup_drawer_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        this.drawerLayout.setBgAnimator(this.shadowBgAnimator);
        this.drawerLayout.enableShadow = this.popupInfo.hasShadowBg.booleanValue();
        this.drawerLayout.isDismissOnTouchOutside = this.popupInfo.isDismissOnTouchOutside.booleanValue();
        this.drawerLayout.setOnCloseListener(new PopupDrawerLayout.OnCloseListener() { // from class: com.lxj.xpopup.core.DrawerPopupView.1
            @Override // com.lxj.xpopup.widget.PopupDrawerLayout.OnCloseListener
            public void onOpen() {
            }

            @Override // com.lxj.xpopup.widget.PopupDrawerLayout.OnCloseListener
            public void onClose() {
                DrawerPopupView.this.beforeDismiss();
                if (DrawerPopupView.this.popupInfo != null && DrawerPopupView.this.popupInfo.xPopupCallback != null) {
                    DrawerPopupView.this.popupInfo.xPopupCallback.beforeDismiss(DrawerPopupView.this);
                }
                DrawerPopupView.this.doAfterDismiss();
            }

            @Override // com.lxj.xpopup.widget.PopupDrawerLayout.OnCloseListener
            public void onDrag(int i, float f, boolean z) {
                DrawerPopupView.this.drawerLayout.isDrawStatusBarShadow = DrawerPopupView.this.popupInfo.hasStatusBarShadow.booleanValue();
                if (DrawerPopupView.this.popupInfo != null && DrawerPopupView.this.popupInfo.xPopupCallback != null) {
                    DrawerPopupView.this.popupInfo.xPopupCallback.onDrag(DrawerPopupView.this, i, f, z);
                }
                DrawerPopupView.this.mFraction = f;
                DrawerPopupView.this.postInvalidate();
            }
        });
        getPopupImplView().setTranslationX(this.popupInfo.offsetX);
        getPopupImplView().setTranslationY(this.popupInfo.offsetY);
        this.drawerLayout.setDrawerPosition(this.popupInfo.popupPosition == null ? PopupPosition.Left : this.popupInfo.popupPosition);
        this.drawerLayout.enableDrag = this.popupInfo.enableDrag.booleanValue();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.popupInfo == null || !this.popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        if (this.shadowRect == null) {
            this.shadowRect = new Rect(0, 0, getMeasuredWidth(), XPopupUtils.getStatusBarHeight());
        }
        this.paint.setColor(((Integer) this.argbEvaluator.evaluate(this.mFraction, Integer.valueOf(this.defaultColor), Integer.valueOf(getStatusBarBgColor()))).intValue());
        canvas.drawRect(this.shadowRect, this.paint);
    }

    public void doStatusBarColorTransform(boolean z) {
        if (this.popupInfo == null || !this.popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(this.argbEvaluator, Integer.valueOf(z ? 0 : getStatusBarBgColor()), Integer.valueOf(z ? getStatusBarBgColor() : 0));
        valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.core.DrawerPopupView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                DrawerPopupView.this.currColor = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                DrawerPopupView.this.postInvalidate();
            }
        });
        valueAnimatorOfObject.setDuration(getAnimationDuration()).start();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doShowAnimation() {
        this.drawerLayout.open();
        doStatusBarColorTransform(true);
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
        doStatusBarColorTransform(false);
        this.drawerLayout.close();
    }
}
