package com.aivox.common_ui.bgabanner.transformer;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

/* JADX INFO: loaded from: classes.dex */
public abstract class BGAPageTransformer implements ViewPager.PageTransformer {
    public abstract void handleInvisiblePage(View view2, float f);

    public abstract void handleLeftPage(View view2, float f);

    public abstract void handleRightPage(View view2, float f);

    @Override // androidx.viewpager.widget.ViewPager.PageTransformer
    public void transformPage(View view2, float f) {
        if (f < -1.0f) {
            handleInvisiblePage(view2, f);
            return;
        }
        if (f <= 0.0f) {
            handleLeftPage(view2, f);
        } else if (f <= 1.0f) {
            handleRightPage(view2, f);
        } else {
            handleInvisiblePage(view2, f);
        }
    }

    /* JADX INFO: renamed from: com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer$1 */
    static /* synthetic */ class C10411 {

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$bgabanner$transformer$TransitionEffect */
        static final /* synthetic */ int[] f269xe683ed39;

        static {
            int[] iArr = new int[TransitionEffect.values().length];
            f269xe683ed39 = iArr;
            try {
                iArr[TransitionEffect.Default.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f269xe683ed39[TransitionEffect.Alpha.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f269xe683ed39[TransitionEffect.Rotate.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f269xe683ed39[TransitionEffect.Cube.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f269xe683ed39[TransitionEffect.Flip.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f269xe683ed39[TransitionEffect.Accordion.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f269xe683ed39[TransitionEffect.ZoomFade.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f269xe683ed39[TransitionEffect.Fade.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f269xe683ed39[TransitionEffect.ZoomCenter.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f269xe683ed39[TransitionEffect.ZoomStack.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f269xe683ed39[TransitionEffect.Stack.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f269xe683ed39[TransitionEffect.Depth.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f269xe683ed39[TransitionEffect.Zoom.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public static BGAPageTransformer getPageTransformer(TransitionEffect transitionEffect) {
        switch (C10411.f269xe683ed39[transitionEffect.ordinal()]) {
            case 1:
                return new DefaultPageTransformer();
            case 2:
                return new AlphaPageTransformer();
            case 3:
                return new RotatePageTransformer();
            case 4:
                return new CubePageTransformer();
            case 5:
                return new FlipPageTransformer();
            case 6:
                return new AccordionPageTransformer();
            case 7:
                return new ZoomFadePageTransformer();
            case 8:
                return new FadePageTransformer();
            case 9:
                return new ZoomCenterPageTransformer();
            case 10:
                return new ZoomStackPageTransformer();
            case 11:
                return new StackPageTransformer();
            case 12:
                return new DepthPageTransformer();
            case 13:
                return new ZoomPageTransformer();
            default:
                return new DefaultPageTransformer();
        }
    }
}
