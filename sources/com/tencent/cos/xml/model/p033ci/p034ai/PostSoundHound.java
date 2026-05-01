package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class PostSoundHound {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public PostSoundHoundInput input;
    public PostSoundHoundOperation operation;
    public String tag = "SoundHound";

    public static class PostSoundHoundInput {
        public String object;
    }

    public static class PostSoundHoundOperation {
        public String jobLevel;
        public String userData;
    }
}
