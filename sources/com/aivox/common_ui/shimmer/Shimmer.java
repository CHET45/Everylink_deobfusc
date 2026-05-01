package com.aivox.common_ui.shimmer;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import com.aivox.common_ui.shimmer.ShimmerViewHelper;

/* JADX INFO: loaded from: classes.dex */
public class Shimmer {
    public static final int ANIMATION_DIRECTION_LTR = 0;
    public static final int ANIMATION_DIRECTION_RTL = 1;
    private static final int DEFAULT_DIRECTION = 0;
    private static final long DEFAULT_DURATION = 1000;
    private static final int DEFAULT_REPEAT_COUNT = -1;
    private static final long DEFAULT_START_DELAY = 0;
    private ObjectAnimator animator;
    private Animator.AnimatorListener animatorListener;
    private int repeatCount = -1;
    private long duration = DEFAULT_DURATION;
    private long startDelay = 0;
    private int direction = 0;

    public int getRepeatCount() {
        return this.repeatCount;
    }

    public Shimmer setRepeatCount(int i) {
        this.repeatCount = i;
        return this;
    }

    public long getDuration() {
        return this.duration;
    }

    public Shimmer setDuration(long j) {
        this.duration = j;
        return this;
    }

    public long getStartDelay() {
        return this.startDelay;
    }

    public Shimmer setStartDelay(long j) {
        this.startDelay = j;
        return this;
    }

    public int getDirection() {
        return this.direction;
    }

    public Shimmer setDirection(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("The animation direction must be either ANIMATION_DIRECTION_LTR or ANIMATION_DIRECTION_RTL");
        }
        this.direction = i;
        return this;
    }

    public Animator.AnimatorListener getAnimatorListener() {
        return this.animatorListener;
    }

    public Shimmer setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animatorListener = animatorListener;
        return this;
    }

    public <V extends View & ShimmerViewBase> void start(final V v) {
        if (isAnimating()) {
            return;
        }
        final Runnable runnable = new Runnable() { // from class: com.aivox.common_ui.shimmer.Shimmer.1
            @Override // java.lang.Runnable
            public void run() {
                ((ShimmerViewBase) v).setShimmering(true);
                float width = v.getWidth();
                float width2 = 0.0f;
                if (Shimmer.this.direction == 1) {
                    width2 = v.getWidth();
                    width = 0.0f;
                }
                Shimmer.this.animator = ObjectAnimator.ofFloat(v, "gradientX", width2, width);
                Shimmer.this.animator.setRepeatCount(Shimmer.this.repeatCount);
                Shimmer.this.animator.setDuration(Shimmer.this.duration);
                Shimmer.this.animator.setStartDelay(Shimmer.this.startDelay);
                Shimmer.this.animator.addListener(new Animator.AnimatorListener() { // from class: com.aivox.common_ui.shimmer.Shimmer.1.1
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        ((ShimmerViewBase) v).setShimmering(false);
                        v.postInvalidateOnAnimation();
                        Shimmer.this.animator = null;
                    }
                });
                if (Shimmer.this.animatorListener != null) {
                    Shimmer.this.animator.addListener(Shimmer.this.animatorListener);
                }
                Shimmer.this.animator.start();
            }
        };
        V v2 = v;
        if (!v2.isSetUp()) {
            v2.setAnimationSetupCallback(new ShimmerViewHelper.AnimationSetupCallback() { // from class: com.aivox.common_ui.shimmer.Shimmer.2
                @Override // com.aivox.common_ui.shimmer.ShimmerViewHelper.AnimationSetupCallback
                public void onSetupAnimation(View view2) {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

    public void cancel() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    public boolean isAnimating() {
        ObjectAnimator objectAnimator = this.animator;
        return objectAnimator != null && objectAnimator.isRunning();
    }
}
