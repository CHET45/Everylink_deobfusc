package com.aivox.base.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import org.videolan.libvlc.MediaDiscoverer;

/* JADX INFO: loaded from: classes.dex */
public class MyAnimationUtil {
    public static final long ANI_TIME_2000 = 2000;
    public static final long ANI_TIME_350 = 350;
    public static final long ANI_TIME_500 = 500;
    public static final long ANI_TIME_750 = 700;
    public static final long ANI_TIME_800 = 800;

    public static synchronized void animationBottomIn(final View view2, long j) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, ((Integer) SPUtil.get(SPUtil.SCREEN_H, Integer.valueOf(MediaDiscoverer.Event.Started))).intValue(), 0.0f);
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.aivox.base.util.MyAnimationUtil.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view2.setVisibility(0);
            }
        });
    }

    public static synchronized void animationBottomIn(View view2, long j, Animation.AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, ((Integer) SPUtil.get(SPUtil.SCREEN_H, Integer.valueOf(MediaDiscoverer.Event.Started))).intValue(), 0.0f);
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(animationListener);
    }

    public static synchronized void animationBottomOut(final View view2, long j) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, ((Integer) SPUtil.get(SPUtil.SCREEN_H, Integer.valueOf(MediaDiscoverer.Event.Started))).intValue());
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.aivox.base.util.MyAnimationUtil.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view2.setVisibility(8);
            }
        });
    }

    public static synchronized void animationTopIn(final View view2, long j) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, -((Integer) SPUtil.get(SPUtil.SCREEN_H, Integer.valueOf(MediaDiscoverer.Event.Started))).intValue(), 0.0f);
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.aivox.base.util.MyAnimationUtil.3
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view2.setVisibility(0);
            }
        });
    }

    public static synchronized void animationTopOut(final View view2, long j) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, -((Integer) SPUtil.get(SPUtil.SCREEN_H, Integer.valueOf(MediaDiscoverer.Event.Started))).intValue());
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.aivox.base.util.MyAnimationUtil.4
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view2.setVisibility(8);
            }
        });
    }

    public static synchronized void animationRightIn(final View view2, long j) {
        TranslateAnimation translateAnimation = new TranslateAnimation(((Integer) SPUtil.get(SPUtil.SCREEN_H, 720)).intValue(), 0.0f, 0.0f, 0.0f);
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.aivox.base.util.MyAnimationUtil.5
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view2.setVisibility(0);
            }
        });
    }

    public static synchronized void animationRightIn(View view2, long j, Animation.AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(((Integer) SPUtil.get(SPUtil.SCREEN_H, 720)).intValue(), 0.0f, 0.0f, 0.0f);
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(animationListener);
    }

    public static synchronized void animationRightOut(final View view2, long j) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, ((Integer) SPUtil.get(SPUtil.SCREEN_H, 720)).intValue(), 0.0f, 0.0f);
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.aivox.base.util.MyAnimationUtil.6
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view2.setVisibility(8);
            }
        });
    }

    public static synchronized void animationRightOut(View view2, long j, Animation.AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, ((Integer) SPUtil.get(SPUtil.SCREEN_H, 720)).intValue(), 0.0f, 0.0f);
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(animationListener);
    }

    public static synchronized void animationLeftIn(final View view2, long j) {
        TranslateAnimation translateAnimation = new TranslateAnimation(-((Integer) SPUtil.get(SPUtil.SCREEN_H, 720)).intValue(), 0.0f, 0.0f, 0.0f);
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.aivox.base.util.MyAnimationUtil.7
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view2.setVisibility(0);
            }
        });
    }

    public static synchronized void animationLeftIn(View view2, long j, Animation.AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(-((Integer) SPUtil.get(SPUtil.SCREEN_H, 720)).intValue(), 0.0f, 0.0f, 0.0f);
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(animationListener);
    }

    public static synchronized void animationLeftOut(View view2, Animation.AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, -((Integer) SPUtil.get(SPUtil.SCREEN_H, 720)).intValue(), 0.0f, 0.0f);
        translateAnimation.setDuration(350L);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(animationListener);
    }

    public static synchronized void animationLeftOut(final View view2) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, -((Integer) SPUtil.get(SPUtil.SCREEN_H, 720)).intValue(), 0.0f, 0.0f);
        translateAnimation.setDuration(350L);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.aivox.base.util.MyAnimationUtil.8
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view2.setVisibility(8);
            }
        });
    }

    public static synchronized void animationLeftOut(final View view2, long j) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, -((Integer) SPUtil.get(SPUtil.SCREEN_H, 720)).intValue(), 0.0f, 0.0f);
        translateAnimation.setDuration(j);
        view2.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.aivox.base.util.MyAnimationUtil.9
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view2.setVisibility(8);
            }
        });
    }

    public static synchronized void scaleAnimationIn(View view2) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, 1.0f, 0.5f);
        scaleAnimation.setDuration(500L);
        view2.setAnimation(scaleAnimation);
        scaleAnimation.startNow();
    }

    public static synchronized void alphaAnimationIn(View view2, long j, Animation.AnimationListener animationListener) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(j);
        view2.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(animationListener);
    }

    public static synchronized void alphaAnimationOut(View view2, long j, Animation.AnimationListener animationListener) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(j);
        view2.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(animationListener);
    }

    public static synchronized void alphaAnimationIn(long j, Animation.AnimationListener animationListener, View... viewArr) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(j);
        for (View view2 : viewArr) {
            view2.startAnimation(alphaAnimation);
        }
        alphaAnimation.setAnimationListener(animationListener);
    }

    public static synchronized void alphaAnimationOut(long j, Animation.AnimationListener animationListener, View... viewArr) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(j);
        for (View view2 : viewArr) {
            view2.startAnimation(alphaAnimation);
        }
        alphaAnimation.setAnimationListener(animationListener);
    }

    public static synchronized void alphaAnimationRepeat(View view2, long j, Animation.AnimationListener animationListener) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(j);
        alphaAnimation.setRepeatCount(10000);
        alphaAnimation.setAnimationListener(animationListener);
        view2.startAnimation(alphaAnimation);
    }

    public static synchronized void scaleAnimationIn(View view2, long j, Animation.AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setAnimationListener(animationListener);
        scaleAnimation.setDuration(j);
        view2.startAnimation(scaleAnimation);
    }

    public static synchronized void scaleAnimationOut(View view2, long j, Animation.AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setAnimationListener(animationListener);
        scaleAnimation.setDuration(j);
        view2.startAnimation(scaleAnimation);
    }

    public static synchronized void scaleAnimationOut(final View view2, long j) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.aivox.base.util.MyAnimationUtil.10
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view2.setVisibility(8);
            }
        });
        scaleAnimation.setDuration(j);
        view2.startAnimation(scaleAnimation);
    }
}
