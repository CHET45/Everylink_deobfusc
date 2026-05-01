package com.aivox.base.util.share;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
@Retention(RetentionPolicy.SOURCE)
public @interface ShareContentType {
    public static final String AUDIO = "audio/*";
    public static final String FILE = "*/*";
    public static final String IMAGE = "image/*";
    public static final String TEXT = "text/plain";
    public static final String VIDEO = "video/*";
}
