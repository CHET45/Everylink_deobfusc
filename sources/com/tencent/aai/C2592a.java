package com.tencent.aai;

import com.tencent.aai.asr.C2593a;
import com.tencent.aai.auth.AbsCredentialProvider;
import com.tencent.aai.config.ClientConfiguration;
import com.tencent.aai.listener.AudioRecognizeResultListener;
import com.tencent.aai.listener.AudioRecognizeStateListener;
import com.tencent.aai.model.AudioRecognizeConfiguration;
import com.tencent.aai.model.AudioRecognizeRequest;
import com.tencent.aai.task.C2604d;
import com.tencent.aai.task.config.C2602a;
import com.tencent.aai.task.config.C2603b;

/* JADX INFO: renamed from: com.tencent.aai.a */
/* JADX INFO: loaded from: classes4.dex */
public class C2592a {

    /* JADX INFO: renamed from: a */
    public final C2604d f924a;

    public C2592a(int i, int i2, String str, String str2, String str3, AbsCredentialProvider absCredentialProvider) {
        C2603b c2603b = new C2603b(i, i2, str, str2, str3);
        C2593a.m881a();
        this.f924a = new C2604d(c2603b, new C2602a(ClientConfiguration.getAudioRecognizeConnectTimeout(), ClientConfiguration.getAudioRecognizeReadTimeout(), ClientConfiguration.getAudioRecognizeWriteTimeout()), absCredentialProvider);
    }

    /* JADX INFO: renamed from: a */
    public void m877a(AudioRecognizeRequest audioRecognizeRequest, AudioRecognizeResultListener audioRecognizeResultListener, AudioRecognizeStateListener audioRecognizeStateListener, AudioRecognizeConfiguration audioRecognizeConfiguration) {
        if (audioRecognizeConfiguration == null) {
            audioRecognizeConfiguration = new AudioRecognizeConfiguration.Builder().build();
        }
        m878a();
        this.f924a.m968a(audioRecognizeRequest, audioRecognizeResultListener, audioRecognizeStateListener, audioRecognizeConfiguration);
    }

    /* JADX INFO: renamed from: a */
    public boolean m878a() {
        return this.f924a.m969a();
    }

    /* JADX INFO: renamed from: b */
    public void m879b() {
        C2593a.m882b();
        this.f924a.m971c();
    }

    /* JADX INFO: renamed from: c */
    public boolean m880c() {
        return this.f924a.m972d();
    }
}
