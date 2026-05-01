package com.tencent.aai;

import android.content.Context;
import com.tencent.aai.auth.AbsCredentialProvider;
import com.tencent.aai.auth.LocalCredentialProvider;
import com.tencent.aai.listener.AudioRecognizeResultListener;
import com.tencent.aai.listener.AudioRecognizeStateListener;
import com.tencent.aai.model.AudioRecognizeConfiguration;
import com.tencent.aai.model.AudioRecognizeRequest;
import com.tencent.aai.task.net.networktime.C2608a;
import com.tencent.aai.task.net.networktime.C2610c;
import com.tencent.aai.task.net.networktime.InterfaceC2609b;

/* JADX INFO: loaded from: classes4.dex */
public class AAIClient {
    private static final String TAG = "AAIClient";
    private final C2592a aaiImpl;
    private Context context;

    /* JADX INFO: renamed from: com.tencent.aai.AAIClient$a */
    public class C2591a implements InterfaceC2609b {
        public C2591a() {
        }

        @Override // com.tencent.aai.task.net.networktime.InterfaceC2609b
        /* JADX INFO: renamed from: a */
        public void mo876a(long j) {
            long jCurrentTimeMillis = 0;
            if (j != 0) {
                jCurrentTimeMillis = j - (System.currentTimeMillis() / 1000);
                String unused = AAIClient.TAG;
                String str = "onServiceTime: diffTime=" + jCurrentTimeMillis;
            }
            C2610c.m986c().m988a(jCurrentTimeMillis);
        }
    }

    public AAIClient(Context context, int i, int i2, String str, AbsCredentialProvider absCredentialProvider) {
        this.context = context.getApplicationContext();
        this.aaiImpl = new C2592a(i, i2, str, null, null, absCredentialProvider);
        initServiceTime();
    }

    public AAIClient(Context context, int i, int i2, String str, String str2, AbsCredentialProvider absCredentialProvider) {
        this.context = context.getApplicationContext();
        this.aaiImpl = new C2592a(i, i2, str, str2, null, absCredentialProvider);
        initServiceTime();
    }

    public AAIClient(Context context, int i, int i2, String str, String str2, String str3) {
        this.context = context.getApplicationContext();
        this.aaiImpl = new C2592a(i, i2, str, str2, str3, new LocalCredentialProvider(str2));
        initServiceTime();
    }

    public AAIClient(Context context, int i, int i2, String str, String str2, String str3, AbsCredentialProvider absCredentialProvider) {
        this.context = context.getApplicationContext();
        this.aaiImpl = new C2592a(i, i2, str, str2, str3, absCredentialProvider);
        initServiceTime();
    }

    public AAIClient(Context context, int i, String str, AbsCredentialProvider absCredentialProvider) {
        this(context, i, 0, str, absCredentialProvider);
    }

    private void initServiceTime() {
        if (C2610c.m986c().m987a() == -1) {
            C2610c.m986c().m988a(0L);
            C2608a c2608a = new C2608a();
            c2608a.m984a(new C2591a());
            c2608a.m985b();
        }
    }

    public boolean cancelAudioRecognize() {
        return this.aaiImpl.m878a();
    }

    public void release() {
        this.aaiImpl.m879b();
    }

    public void startAudioRecognize(AudioRecognizeRequest audioRecognizeRequest, AudioRecognizeResultListener audioRecognizeResultListener, AudioRecognizeStateListener audioRecognizeStateListener, AudioRecognizeConfiguration audioRecognizeConfiguration) {
        this.aaiImpl.m877a(audioRecognizeRequest, audioRecognizeResultListener, audioRecognizeStateListener, audioRecognizeConfiguration);
    }

    public boolean stopAudioRecognize() {
        return this.aaiImpl.m880c();
    }
}
