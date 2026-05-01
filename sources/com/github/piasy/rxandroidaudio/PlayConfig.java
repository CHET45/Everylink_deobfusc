package com.github.piasy.rxandroidaudio;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes3.dex */
public class PlayConfig {
    static final int TYPE_FILE = 1;
    static final int TYPE_RES = 2;
    static final int TYPE_URI = 4;
    static final int TYPE_URL = 3;
    final File mAudioFile;
    final int mAudioResource;
    final Context mContext;
    final float mLeftVolume;
    final boolean mLooping;
    final float mRightVolume;
    final int mStreamType;
    final int mType;
    final Uri mUri;
    final String mUrl;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private PlayConfig(Builder builder) {
        this.mType = builder.mType;
        this.mContext = builder.mContext;
        this.mAudioResource = builder.mAudioResource;
        this.mAudioFile = builder.mAudioFile;
        this.mStreamType = builder.mStreamType;
        this.mLooping = builder.mLooping;
        this.mLeftVolume = builder.mLeftVolume;
        this.mRightVolume = builder.mRightVolume;
        this.mUri = builder.mUri;
        this.mUrl = builder.mUrl;
    }

    public static Builder file(File file) {
        Builder builder = new Builder();
        builder.mAudioFile = file;
        builder.mType = 1;
        return builder;
    }

    public static Builder url(String str) {
        Builder builder = new Builder();
        builder.mUrl = str;
        builder.mType = 3;
        return builder;
    }

    public static Builder res(Context context, int i) {
        Builder builder = new Builder();
        builder.mContext = context;
        builder.mAudioResource = i;
        builder.mType = 2;
        return builder;
    }

    public static Builder uri(Context context, Uri uri) {
        Builder builder = new Builder();
        builder.mContext = context;
        builder.mUri = uri;
        builder.mType = 4;
        return builder;
    }

    boolean isArgumentValid() {
        int i = this.mType;
        if (i == 1) {
            File file = this.mAudioFile;
            return file != null && file.exists();
        }
        if (i == 2) {
            return this.mAudioResource > 0 && this.mContext != null;
        }
        if (i != 3) {
            return i == 4 && this.mUri != null;
        }
        return !TextUtils.isEmpty(this.mUrl);
    }

    boolean isLocalSource() {
        int i = this.mType;
        return i == 1 || i == 2;
    }

    boolean needPrepare() {
        int i = this.mType;
        return i == 1 || i == 3 || i == 4;
    }

    public static class Builder {
        File mAudioFile;
        int mAudioResource;
        Context mContext;
        boolean mLooping;
        int mType;
        Uri mUri;
        String mUrl;
        int mStreamType = 3;
        float mLeftVolume = 1.0f;
        float mRightVolume = 1.0f;

        public Builder streamType(int i) {
            this.mStreamType = i;
            return this;
        }

        public Builder looping(boolean z) {
            this.mLooping = z;
            return this;
        }

        public Builder leftVolume(float f) {
            this.mLeftVolume = f;
            return this;
        }

        public Builder rightVolume(float f) {
            this.mRightVolume = f;
            return this;
        }

        public PlayConfig build() {
            return new PlayConfig(this);
        }
    }
}
