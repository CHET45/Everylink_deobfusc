package com.github.gzuliyujiang.wheelview.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes3.dex */
@Retention(RetentionPolicy.SOURCE)
public @interface ScrollState {
    public static final int DRAGGING = 1;
    public static final int IDLE = 0;
    public static final int SCROLLING = 2;
}
