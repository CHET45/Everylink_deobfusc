package com.lxj.xpopup.impl;

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
import com.lxj.xpopup.animator.TranslateAnimator;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.util.XPopupUtils;

/* JADX INFO: loaded from: classes4.dex */
public class FullScreenPopupView extends BasePopupView {
    public ArgbEvaluator argbEvaluator;
    protected View contentView;
    int currColor;
    protected FrameLayout fullPopupContainer;
    Paint paint;
    Rect shadowRect;

    public FullScreenPopupView(Context context) {
        super(context);
        this.argbEvaluator = new ArgbEvaluator();
        this.paint = new Paint();
        this.currColor = 0;
        this.fullPopupContainer = (FrameLayout) findViewById(C2213R.id.fullPopupContainer);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getInnerLayoutId() {
        return C2213R.layout._xpopup_fullscreen_popup_view;
    }

    protected void addInnerContent() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.fullPopupContainer, false);
        this.contentView = viewInflate;
        this.fullPopupContainer.addView(viewInflate);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        if (this.fullPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        getPopupContentView().setTranslationX(this.popupInfo.offsetX);
        getPopupContentView().setTranslationY(this.popupInfo.offsetY);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.popupInfo == null || !this.popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        this.paint.setColor(this.currColor);
        Rect rect = new Rect(0, 0, getMeasuredWidth(), XPopupUtils.getStatusBarHeight());
        this.shadowRect = rect;
        canvas.drawRect(rect, this.paint);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doShowAnimation() {
        super.doShowAnimation();
        doStatusBarColorTransform(true);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doDismissAnimation() {
        super.doDismissAnimation();
        doStatusBarColorTransform(false);
    }

    public void doStatusBarColorTransform(boolean z) {
        if (this.popupInfo == null || !this.popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(this.argbEvaluator, Integer.valueOf(z ? 0 : getStatusBarBgColor()), Integer.valueOf(z ? getStatusBarBgColor() : 0));
        valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.impl.FullScreenPopupView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                FullScreenPopupView.this.currColor = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                FullScreenPopupView.this.postInvalidate();
            }
        });
        valueAnimatorOfObject.setDuration(getAnimationDuration()).start();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.TranslateFromBottom);
    }

    @Override // com.lxj.xpopup.core.BasePopupView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        if (this.popupInfo != null) {
            getPopupContentView().setTranslationX(this.popupInfo.offsetX);
        }
        if (this.popupInfo != null) {
            getPopupContentView().setTranslationY(this.popupInfo.offsetY);
        }
        super.onDetachedFromWindow();
    }
}
