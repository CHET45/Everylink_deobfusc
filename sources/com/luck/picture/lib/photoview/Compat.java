package com.luck.picture.lib.photoview;

import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
class Compat {
    Compat() {
    }

    public static void postOnAnimation(View view2, Runnable runnable) {
        postOnAnimationJellyBean(view2, runnable);
    }

    private static void postOnAnimationJellyBean(View view2, Runnable runnable) {
        view2.postOnAnimation(runnable);
    }
}
