package com.aivox.app.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import com.aivox.app.C0726R;
import com.chad.library.C1376R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class RippleAnimationView extends RelativeLayout {
    private static final int DEFAULT_DURATION_TIME = 2500;
    private static final int DEFAULT_FILL_TYPE = 0;
    private static final int DEFAULT_RIPPLE_COUNT = 5;
    private static final float DEFAULT_SCALE = 5.0f;
    private boolean animationRunning;
    private AnimatorSet animatorSet;
    public Paint paint;
    private int rippleAmount;
    private int rippleColor;
    private int rippleDuration;
    private float rippleRadius;
    private float rippleScale;
    public float rippleStrokeWidth;
    private int rippleType;
    private ArrayList<RippleCircleView> rippleViewList;
    private TypedArray typedArray;

    public RippleAnimationView(Context context) {
        super(context);
        this.animationRunning = false;
        this.rippleViewList = new ArrayList<>();
    }

    public RippleAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.animationRunning = false;
        this.rippleViewList = new ArrayList<>();
        init(context, attributeSet);
    }

    public RippleAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.animationRunning = false;
        this.rippleViewList = new ArrayList<>();
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0726R.styleable.RippleAnimationView);
        this.typedArray = typedArrayObtainStyledAttributes;
        this.rippleType = typedArrayObtainStyledAttributes.getInt(C0726R.styleable.RippleAnimationView_ripple_anim_type, 0);
        this.rippleAmount = this.typedArray.getInt(C0726R.styleable.RippleAnimationView_ripple_anim_amount, 5);
        this.rippleScale = this.typedArray.getFloat(C0726R.styleable.RippleAnimationView_ripple_anim_scale, DEFAULT_SCALE);
        this.rippleRadius = this.typedArray.getDimension(C0726R.styleable.RippleAnimationView_ripple_anim_radius, getResources().getDimension(C1376R.dimen.def_height));
        this.rippleDuration = this.typedArray.getInt(C0726R.styleable.RippleAnimationView_ripple_anim_duration, DEFAULT_DURATION_TIME);
        this.rippleStrokeWidth = this.typedArray.getDimension(C0726R.styleable.RippleAnimationView_ripple_anim_strokeWidth, getResources().getDimension(C1376R.dimen.def_height));
        this.typedArray.recycle();
        int i = this.rippleDuration / this.rippleAmount;
        Paint paint = new Paint();
        this.paint = paint;
        paint.setAntiAlias(true);
        if (this.rippleType == 0) {
            this.rippleStrokeWidth = 0.0f;
            this.paint.setStyle(Paint.Style.FILL);
        } else {
            this.paint.setStyle(Paint.Style.STROKE);
        }
        this.paint.setColor(this.rippleColor);
        float f = this.rippleRadius;
        float f2 = this.rippleStrokeWidth;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) ((f + f2) * 2.0f), (int) ((f + f2) * 2.0f));
        int i2 = -1;
        layoutParams.addRule(13, -1);
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (i3 < this.rippleAmount) {
            RippleCircleView rippleCircleView = new RippleCircleView(this, context);
            addView(rippleCircleView, layoutParams);
            this.rippleViewList.add(rippleCircleView);
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(rippleCircleView, "ScaleX", 1.0f, this.rippleScale);
            objectAnimatorOfFloat.setRepeatCount(i2);
            objectAnimatorOfFloat.setRepeatMode(1);
            long j = i3 * i;
            objectAnimatorOfFloat.setStartDelay(j);
            objectAnimatorOfFloat.setDuration(this.rippleDuration);
            arrayList.add(objectAnimatorOfFloat);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(rippleCircleView, "ScaleY", 1.0f, this.rippleScale);
            objectAnimatorOfFloat2.setRepeatCount(-1);
            objectAnimatorOfFloat2.setRepeatMode(1);
            objectAnimatorOfFloat2.setStartDelay(j);
            objectAnimatorOfFloat2.setDuration(this.rippleDuration);
            arrayList.add(objectAnimatorOfFloat2);
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(rippleCircleView, "Alpha", 1.0f, 0.0f);
            objectAnimatorOfFloat3.setRepeatCount(-1);
            objectAnimatorOfFloat3.setRepeatMode(1);
            objectAnimatorOfFloat3.setStartDelay(j);
            objectAnimatorOfFloat3.setDuration(this.rippleDuration);
            arrayList.add(objectAnimatorOfFloat3);
            i3++;
            i2 = -1;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        this.animatorSet = animatorSet;
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        this.animatorSet.playTogether(arrayList);
    }

    public void setRippleColor(int i) {
        this.rippleColor = i;
    }

    public void startRippleAnimation() {
        if (isRippleRunning()) {
            return;
        }
        Iterator<RippleCircleView> it = this.rippleViewList.iterator();
        while (it.hasNext()) {
            it.next().setVisibility(0);
        }
        this.animatorSet.start();
        this.animationRunning = true;
    }

    public void stopRippleAnimation() {
        if (isRippleRunning()) {
            Collections.reverse(this.rippleViewList);
            Iterator<RippleCircleView> it = this.rippleViewList.iterator();
            while (it.hasNext()) {
                it.next().setVisibility(4);
            }
            this.animatorSet.end();
            this.animationRunning = false;
        }
    }

    public boolean isRippleRunning() {
        return this.animationRunning;
    }

    public static void ripple_anim_color(RippleAnimationView rippleAnimationView, int i) {
        rippleAnimationView.paint.setColor(i);
    }
}
