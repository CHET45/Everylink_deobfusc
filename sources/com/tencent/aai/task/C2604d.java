package com.tencent.aai.task;

import com.tencent.aai.audio.data.PcmAudioDataSource;
import com.tencent.aai.auth.AbsCredentialProvider;
import com.tencent.aai.exception.ClientException;
import com.tencent.aai.exception.ClientExceptionType;
import com.tencent.aai.listener.AudioRecognizeResultListener;
import com.tencent.aai.listener.AudioRecognizeStateListener;
import com.tencent.aai.model.AudioRecognizeConfiguration;
import com.tencent.aai.model.AudioRecognizeRequest;
import com.tencent.aai.task.C2601c;
import com.tencent.aai.task.config.C2602a;
import com.tencent.aai.task.config.C2603b;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import okhttp3.OkHttpClient;

/* JADX INFO: renamed from: com.tencent.aai.task.d */
/* JADX INFO: loaded from: classes4.dex */
public final class C2604d {

    /* JADX INFO: renamed from: a */
    public static volatile OkHttpClient f993a;

    /* JADX INFO: renamed from: b */
    public static volatile int f994b;

    /* JADX INFO: renamed from: c */
    public String f995c = C2604d.class.getName();

    /* JADX INFO: renamed from: d */
    public RunnableC2600b f996d;

    /* JADX INFO: renamed from: e */
    public final ExecutorService f997e;

    /* JADX INFO: renamed from: f */
    public final AbsCredentialProvider f998f;

    /* JADX INFO: renamed from: g */
    public C2603b f999g;

    /* JADX INFO: renamed from: h */
    public C2602a f1000h;

    /* JADX INFO: renamed from: com.tencent.aai.task.d$a */
    public class a implements HostnameVerifier {
        public a() {
        }

        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            return HttpsURLConnection.getDefaultHostnameVerifier().verify("asr.cloud.tencent.com", sSLSession);
        }
    }

    public C2604d(C2603b c2603b, C2602a c2602a, AbsCredentialProvider absCredentialProvider) {
        this.f999g = c2603b;
        this.f1000h = c2602a;
        this.f998f = absCredentialProvider;
        f993a = m970b();
        this.f997e = Executors.newSingleThreadExecutor();
    }

    /* JADX INFO: renamed from: a */
    public void m968a(AudioRecognizeRequest audioRecognizeRequest, AudioRecognizeResultListener audioRecognizeResultListener, AudioRecognizeStateListener audioRecognizeStateListener, AudioRecognizeConfiguration audioRecognizeConfiguration) {
        synchronized (this) {
            PcmAudioDataSource pcmAudioDataSource = audioRecognizeRequest.getPcmAudioDataSource();
            if (pcmAudioDataSource == null) {
                audioRecognizeResultListener.onFailure(audioRecognizeRequest, new ClientException(ClientExceptionType.AUDIO_SOURCE_DATA_NULL), null, null);
                return;
            }
            RunnableC2600b runnableC2600b = new RunnableC2600b(audioRecognizeRequest, audioRecognizeConfiguration, new C2601c.b().m946a(audioRecognizeConfiguration.getAudioFlowSilenceTimeOut()).m950b(audioRecognizeConfiguration.getMinVolumeCallbackTime()).m951b(audioRecognizeConfiguration.getSilentDetectTimeOut()).m952c(audioRecognizeConfiguration.getSliceTime()).m948a(audioRecognizeConfiguration.isSilentDetectTimeOutAutoStop()).m947a(pcmAudioDataSource).m949a(), this.f999g, f993a, this.f998f);
            this.f996d = runnableC2600b;
            runnableC2600b.m916a(audioRecognizeResultListener);
            this.f996d.m917a(audioRecognizeStateListener);
            this.f997e.submit(this.f996d);
        }
    }

    /* JADX INFO: renamed from: a */
    public boolean m969a() {
        synchronized (this) {
            RunnableC2600b runnableC2600b = this.f996d;
            if (runnableC2600b == null) {
                return false;
            }
            runnableC2600b.m921c();
            this.f996d = null;
            return true;
        }
    }

    /* JADX INFO: renamed from: b */
    public final OkHttpClient m970b() {
        OkHttpClient okHttpClient;
        synchronized (C2604d.class) {
            f994b++;
            if (f993a == null) {
                OkHttpClient.Builder builderHostnameVerifier = new OkHttpClient.Builder().followRedirects(false).followSslRedirects(false).retryOnConnectionFailure(true).cache(null).hostnameVerifier(new a());
                long jM963a = this.f1000h.m963a();
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                builderHostnameVerifier.connectTimeout(jM963a, timeUnit).readTimeout(this.f1000h.m964b(), timeUnit).writeTimeout(this.f1000h.m964b(), timeUnit);
                f993a = builderHostnameVerifier.build();
            }
            okHttpClient = f993a;
        }
        return okHttpClient;
    }

    /* JADX INFO: renamed from: c */
    public void m971c() {
        synchronized (this) {
            m969a();
            this.f997e.shutdown();
        }
        synchronized (C2604d.class) {
            f994b--;
            if (f993a != null && f994b < 1) {
                f993a = null;
            }
        }
    }

    /* JADX INFO: renamed from: d */
    public boolean m972d() {
        synchronized (this) {
            RunnableC2600b runnableC2600b = this.f996d;
            if (runnableC2600b == null) {
                return false;
            }
            runnableC2600b.m928j();
            this.f996d = null;
            return true;
        }
    }
}
