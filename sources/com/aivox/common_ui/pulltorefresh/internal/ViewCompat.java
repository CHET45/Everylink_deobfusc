package com.aivox.common_ui.pulltorefresh.internal;

import android.graphics.drawable.Drawable;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class ViewCompat {
    public static void postOnAnimation(View view2, Runnable runnable) {
        SDK16.postOnAnimation(view2, runnable);
    }

    public static void setBackground(View view2, Drawable drawable) {
        SDK16.setBackground(view2, drawable);
    }

    public static void setLayerType(View view2, int i) {
        SDK11.setLayerType(view2, i);
    }

    static class SDK11 {
        SDK11() {
        }

        public static void setLayerType(View view2, int i) {
            view2.setLayerType(i, null);
        }
    }

    static class SDK16 {
        SDK16() {
        }

        public static void postOnAnimation(View view2, Runnable runnable) {
            view2.postOnAnimation(runnable);
        }

        public static void setBackground(View view2, Drawable drawable) {
            view2.setBackground(drawable);
        }
    }
}
