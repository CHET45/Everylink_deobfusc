package com.tencent.aai.task;

import android.text.TextUtils;
import com.aivox.base.common.Constant;
import com.microsoft.azure.storage.table.TableConstants;
import com.tencent.aai.auth.AbsCredentialProvider;
import com.tencent.aai.exception.ClientException;
import com.tencent.aai.exception.ClientExceptionType;
import com.tencent.aai.exception.ServerException;
import com.tencent.aai.listener.AudioRecognizeResultListener;
import com.tencent.aai.listener.AudioRecognizeStateListener;
import com.tencent.aai.log.AAILogger;
import com.tencent.aai.model.AudioRecognizeConfiguration;
import com.tencent.aai.model.AudioRecognizeRequest;
import com.tencent.aai.model.AudioRecognizeResult;
import com.tencent.aai.task.config.C2603b;
import com.tencent.aai.task.listener.AudioRecognizeBufferListener;
import com.tencent.aai.task.listener.AudioRecognizerListener;
import com.tencent.aai.task.net.C2605a;
import com.tencent.aai.task.net.C2606b;
import com.tencent.aai.task.net.C2607c;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tencent.aai.task.b */
/* JADX INFO: loaded from: classes4.dex */
public class RunnableC2600b implements Runnable {

    /* JADX INFO: renamed from: a */
    public static OkHttpClient f934a;

    /* JADX INFO: renamed from: c */
    public final AudioRecognizeRequest f936c;

    /* JADX INFO: renamed from: d */
    public final AudioRecognizeConfiguration f937d;

    /* JADX INFO: renamed from: e */
    public final C2601c f938e;

    /* JADX INFO: renamed from: f */
    public WebSocket f939f;

    /* JADX INFO: renamed from: g */
    public final AbsCredentialProvider f940g;

    /* JADX INFO: renamed from: h */
    public final C2603b f941h;

    /* JADX INFO: renamed from: i */
    public AudioRecognizeResultListener f942i;

    /* JADX INFO: renamed from: j */
    public AudioRecognizeStateListener f943j;

    /* JADX INFO: renamed from: k */
    public final BlockingQueue<C2599a> f944k;

    /* JADX INFO: renamed from: o */
    public Map<String, AudioRecognizeResult> f948o;

    /* JADX INFO: renamed from: p */
    public Map<String, String> f949p;

    /* JADX INFO: renamed from: b */
    public String f935b = RunnableC2600b.class.getName();

    /* JADX INFO: renamed from: l */
    public boolean f945l = false;

    /* JADX INFO: renamed from: m */
    public boolean f946m = false;

    /* JADX INFO: renamed from: n */
    public volatile boolean f947n = false;

    /* JADX INFO: renamed from: q */
    public long f950q = 0;

    /* JADX INFO: renamed from: r */
    public long f951r = 0;

    /* JADX INFO: renamed from: s */
    public boolean f952s = true;

    /* JADX INFO: renamed from: t */
    public volatile boolean f953t = false;

    /* JADX INFO: renamed from: u */
    public volatile boolean f954u = false;

    /* JADX INFO: renamed from: v */
    public final Object f955v = new Object();

    /* JADX INFO: renamed from: com.tencent.aai.task.b$a */
    public class a extends WebSocketListener {

        /* JADX INFO: renamed from: a */
        public final /* synthetic */ AudioRecognizeRequest f956a;

        public a(AudioRecognizeRequest audioRecognizeRequest) {
            this.f956a = audioRecognizeRequest;
        }

        @Override // okhttp3.WebSocketListener
        public void onClosed(WebSocket webSocket, int i, String str) {
            super.onClosed(webSocket, i, str);
            AAILogger.info(RunnableC2600b.this.f935b, "WebSocketListener onClosed" + str);
        }

        @Override // okhttp3.WebSocketListener
        public void onClosing(WebSocket webSocket, int i, String str) {
            super.onClosing(webSocket, i, str);
            AAILogger.info(RunnableC2600b.this.f935b, "WebSocketListener onClosing" + str);
        }

        @Override // okhttp3.WebSocketListener
        public void onFailure(WebSocket webSocket, Throwable th, Response response) {
            super.onFailure(webSocket, th, response);
            if (!RunnableC2600b.this.f946m && RunnableC2600b.this.f942i != null) {
                if (response != null) {
                    AAILogger.info(RunnableC2600b.this.f935b, "WebSocketListener onFailure" + response.message());
                    RunnableC2600b.this.f942i.onFailure(this.f956a, new ClientException(ClientExceptionType.WEBSOCKET_NETWORK_FAILED, response.message()), null, null);
                } else {
                    AAILogger.info(RunnableC2600b.this.f935b, "WebSocketListener onFailure throwable" + th);
                    RunnableC2600b.this.f942i.onFailure(this.f956a, new ClientException(ClientExceptionType.WEBSOCKET_NETWORK_FAILED, th.toString()), null, null);
                }
                RunnableC2600b.this.f954u = true;
                if (RunnableC2600b.this.f953t) {
                    RunnableC2600b.this.m926h();
                }
            }
            RunnableC2600b.this.f938e.m945e();
            RunnableC2600b.this.m921c();
            if (response != null) {
                response.close();
            }
        }

        @Override // okhttp3.WebSocketListener
        public void onMessage(WebSocket webSocket, String str) {
            String str2;
            super.onMessage(webSocket, str);
            RunnableC2600b.this.f951r = System.currentTimeMillis();
            AAILogger.info(RunnableC2600b.this.f935b, "WebSocketListener onMessage String" + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                String unused = RunnableC2600b.this.f935b;
                String str3 = "jsonObject: " + jSONObject.toString();
                int i = jSONObject.getInt(TableConstants.ErrorConstants.ERROR_CODE);
                String string = jSONObject.getString("message");
                if (jSONObject.has("final") && TextUtils.equals(jSONObject.getString("final"), "1")) {
                    if (RunnableC2600b.this.f942i != null) {
                        RunnableC2600b.this.f942i.onSuccess(this.f956a, RunnableC2600b.this.m920b());
                        RunnableC2600b.this.f954u = true;
                    }
                    if (RunnableC2600b.this.f953t) {
                        RunnableC2600b.this.m926h();
                    }
                    RunnableC2600b.this.m921c();
                    return;
                }
                try {
                    if (i != 0) {
                        if (RunnableC2600b.this.f942i != null) {
                            str2 = str;
                            try {
                                RunnableC2600b.this.f942i.onFailure(this.f956a, null, new ServerException(i, string), str2);
                                RunnableC2600b.this.f938e.m945e();
                                RunnableC2600b.this.f954u = true;
                                if (RunnableC2600b.this.f953t) {
                                    RunnableC2600b.this.m926h();
                                }
                            } catch (JSONException e) {
                                e = e;
                            }
                        } else {
                            str2 = str;
                        }
                        RunnableC2600b.this.m921c();
                        return;
                    }
                    if (jSONObject.has("voice_id")) {
                        String string2 = jSONObject.getString("voice_id");
                        if (jSONObject.has("result")) {
                            JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                            String string3 = jSONObject2.getString("voice_text_str");
                            boolean zIsEmpty = TextUtils.isEmpty(string3);
                            int i2 = jSONObject2.getInt("slice_type");
                            int i3 = jSONObject2.getInt(Constant.KEY_INDEX);
                            AudioRecognizeResult audioRecognizeResult = new AudioRecognizeResult(string2, i3, string3, i, string, i2, jSONObject2.getInt("start_time"), jSONObject2.getInt("end_time"), str);
                            RunnableC2600b.this.f948o.put(i3 + "", audioRecognizeResult);
                            if (RunnableC2600b.this.f942i != null) {
                                RunnableC2600b.this.f942i.onSliceSuccess(this.f956a, audioRecognizeResult, audioRecognizeResult.getSeq());
                                if (i2 == 2) {
                                    if (!RunnableC2600b.this.f952s) {
                                        zIsEmpty = true;
                                    }
                                    RunnableC2600b.this.f942i.onSegmentSuccess(this.f956a, audioRecognizeResult, audioRecognizeResult.getSeq());
                                }
                            }
                            if (!zIsEmpty) {
                                RunnableC2600b.this.f952s = true;
                                RunnableC2600b.this.f950q = 0L;
                                AAILogger.info(RunnableC2600b.this.f935b, "current_silent_time=== " + RunnableC2600b.this.f950q);
                            } else if (RunnableC2600b.this.f952s) {
                                RunnableC2600b.this.f950q = System.currentTimeMillis();
                                RunnableC2600b.this.f952s = false;
                            }
                            if (RunnableC2600b.this.f949p.containsKey(i3 + "")) {
                                return;
                            }
                            RunnableC2600b.this.f949p.put(i3 + "", String.valueOf(RunnableC2600b.this.f949p.size()));
                            return;
                        }
                        return;
                    }
                    return;
                } catch (JSONException e2) {
                    e = e2;
                    str2 = str;
                }
            } catch (JSONException e3) {
                e = e3;
                str2 = str;
            }
            AAILogger.warn(RunnableC2600b.this.f935b, "result json Parse error" + e + "| json test=" + str2);
            e.printStackTrace();
        }

        @Override // okhttp3.WebSocketListener
        public void onMessage(WebSocket webSocket, ByteString byteString) {
            super.onMessage(webSocket, byteString);
            AAILogger.info(RunnableC2600b.this.f935b, "WebSocketListener onMessage ByteString" + byteString.utf8());
        }

        @Override // okhttp3.WebSocketListener
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            RunnableC2600b.this.f951r = System.currentTimeMillis();
            if (!RunnableC2600b.this.f946m) {
                RunnableC2600b.this.f939f = webSocket;
                AAILogger.info(RunnableC2600b.this.f935b, "WebSocketListener onOpen" + response.message());
                return;
            }
            AAILogger.warn(RunnableC2600b.this.f935b, "recognition is stopped before socket open");
            RunnableC2600b.this.f939f.close(4102, "recognition is stopped before socket open");
            synchronized (RunnableC2600b.this) {
                RunnableC2600b.this.f939f = null;
                response.close();
                RunnableC2600b.this.m921c();
            }
        }
    }

    /* JADX INFO: renamed from: com.tencent.aai.task.b$b */
    public class b implements AudioRecognizerListener {
        public b() {
        }

        @Override // com.tencent.aai.task.listener.AudioRecognizerListener
        public void audioDatas(short[] sArr, int i) {
            RunnableC2600b.this.m919a(sArr, i);
        }

        @Override // com.tencent.aai.task.listener.AudioRecognizerListener
        public void onError(ClientException clientException) {
            RunnableC2600b.this.m915a(clientException);
        }

        @Override // com.tencent.aai.task.listener.AudioRecognizerListener
        public void onFinish() {
            RunnableC2600b.this.m924f();
        }

        @Override // com.tencent.aai.task.listener.AudioRecognizerListener
        public void onStart() {
            RunnableC2600b.this.m923e();
        }

        @Override // com.tencent.aai.task.listener.AudioRecognizerListener
        public void onVoiceDb(float f) {
            RunnableC2600b.this.m913a(f);
        }

        @Override // com.tencent.aai.task.listener.AudioRecognizerListener
        public void onVolume(int i) {
            RunnableC2600b.this.m914a(i);
        }
    }

    /* JADX INFO: renamed from: com.tencent.aai.task.b$c */
    public class c implements AudioRecognizeBufferListener {
        public c() {
        }

        @Override // com.tencent.aai.task.listener.AudioRecognizeBufferListener
        public boolean onSliceComplete(AudioPcmData audioPcmData) {
            C2599a c2599a = new C2599a(0, audioPcmData);
            try {
                if (!RunnableC2600b.this.f945l) {
                    RunnableC2600b.this.f944k.put(c2599a);
                    AAILogger.info("AudioRecognizeTask", "put a slice Complete Message ");
                    return true;
                }
            } catch (InterruptedException unused) {
                AAILogger.warn("AudioRecognizeTask", "the blocking queue is interrupted while waiting..");
            }
            AAILogger.warn("AudioRecognizeTask", "isCancel ====" + RunnableC2600b.this.f945l + "----audioMessage.getCompressData().length ===" + c2599a.m885b().length);
            return false;
        }
    }

    /* JADX INFO: renamed from: com.tencent.aai.task.b$d */
    public class d {

        /* JADX INFO: renamed from: a */
        public String f960a = d.class.getName();

        /* JADX INFO: renamed from: b */
        public C2599a f961b;

        public d(C2599a c2599a) {
            this.f961b = c2599a;
        }

        /* JADX INFO: renamed from: a */
        public C2605a m929a() {
            C2605a c2605a = new C2605a();
            if (this.f961b != null) {
                byte[] bArrM885b = RunnableC2600b.this.f937d.isCompress() ? this.f961b.m885b() : this.f961b.m884a();
                if (bArrM885b != null && bArrM885b.length > 0) {
                    c2605a.m974a(bArrM885b);
                }
            }
            c2605a.m973a(RunnableC2600b.this.f946m);
            return c2605a;
        }

        /* JADX INFO: renamed from: b */
        public void m930b() {
            String str;
            String str2;
            C2605a c2605aM929a = m929a();
            AAILogger.info(this.f960a, "wss request start");
            if (RunnableC2600b.this.f939f != null) {
                byte[] bArrM975a = c2605aM929a.m975a();
                if (c2605aM929a.m976b()) {
                    RunnableC2600b.this.f939f.send("{\"type\": \"end\"}");
                    return;
                } else {
                    if (bArrM975a == null) {
                        return;
                    }
                    RunnableC2600b.this.f939f.send(ByteString.m1928of(bArrM975a));
                    str = this.f960a;
                    str2 = "websocket send data ..." + bArrM975a.length;
                }
            } else {
                str = this.f960a;
                str2 = "websocket is connectiong...";
            }
            AAILogger.info(str, str2);
        }
    }

    public RunnableC2600b(AudioRecognizeRequest audioRecognizeRequest, AudioRecognizeConfiguration audioRecognizeConfiguration, C2601c c2601c, C2603b c2603b, OkHttpClient okHttpClient, AbsCredentialProvider absCredentialProvider) {
        this.f936c = audioRecognizeRequest;
        this.f937d = audioRecognizeConfiguration;
        this.f938e = c2601c;
        this.f941h = c2603b;
        f934a = okHttpClient;
        this.f940g = absCredentialProvider;
        this.f948o = new HashMap();
        this.f949p = new HashMap();
        this.f944k = new LinkedBlockingDeque();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x004a, code lost:
    
        if (r7.f938e.m943c() != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0072, code lost:
    
        if (r7.f938e.m943c() != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0074, code lost:
    
        r7.f938e.m945e();
        m925g();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007d, code lost:
    
        r7.f950q = 0;
        r7.f951r = java.lang.System.currentTimeMillis();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0085, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:?, code lost:
    
        return;
     */
    /* JADX INFO: renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m912a() {
        /*
            r7 = this;
            java.lang.String r0 = r7.f935b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "静音检测开关=== "
            r1.<init>(r2)
            com.tencent.aai.task.c r2 = r7.f938e
            boolean r2 = r2.m942b()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.tencent.aai.log.AAILogger.info(r0, r1)
            com.tencent.aai.task.c r0 = r7.f938e
            boolean r0 = r0.m942b()
            if (r0 != 0) goto L23
            return
        L23:
            long r0 = java.lang.System.currentTimeMillis()
            long r2 = r7.f950q
            long r0 = r0 - r2
            com.tencent.aai.task.c r2 = r7.f938e
            int r2 = r2.m939a()
            long r2 = (long) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 0
            if (r0 <= 0) goto L4d
            long r3 = r7.f950q
            int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r0 <= 0) goto L4d
            com.tencent.aai.listener.AudioRecognizeStateListener r0 = r7.f943j
            if (r0 == 0) goto L44
            r0.onSilentDetectTimeOut()
        L44:
            com.tencent.aai.task.c r0 = r7.f938e
            boolean r0 = r0.m943c()
            if (r0 == 0) goto L7d
            goto L74
        L4d:
            long r3 = java.lang.System.currentTimeMillis()
            long r5 = r7.f951r
            long r3 = r3 - r5
            com.tencent.aai.task.c r0 = r7.f938e
            int r0 = r0.m939a()
            long r5 = (long) r0
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 <= 0) goto L85
            long r3 = r7.f951r
            int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r0 <= 0) goto L85
            com.tencent.aai.listener.AudioRecognizeStateListener r0 = r7.f943j
            if (r0 == 0) goto L6c
            r0.onSilentDetectTimeOut()
        L6c:
            com.tencent.aai.task.c r0 = r7.f938e
            boolean r0 = r0.m943c()
            if (r0 == 0) goto L7d
        L74:
            com.tencent.aai.task.c r0 = r7.f938e
            r0.m945e()
            r7.m925g()
            goto L85
        L7d:
            r7.f950q = r1
            long r0 = java.lang.System.currentTimeMillis()
            r7.f951r = r0
        L85:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.aai.task.RunnableC2600b.m912a():void");
    }

    /* JADX INFO: renamed from: a */
    public final void m913a(float f) {
        AudioRecognizeStateListener audioRecognizeStateListener = this.f943j;
        if (audioRecognizeStateListener != null) {
            audioRecognizeStateListener.onVoiceDb(f);
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m914a(int i) {
        AudioRecognizeStateListener audioRecognizeStateListener = this.f943j;
        if (audioRecognizeStateListener != null) {
            audioRecognizeStateListener.onVoiceVolume(this.f936c, i);
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m915a(ClientException clientException) {
        AAILogger.error("AudioRecognizeTask", "handle on error:" + clientException.toString());
        AudioRecognizeResultListener audioRecognizeResultListener = this.f942i;
        if (audioRecognizeResultListener != null) {
            audioRecognizeResultListener.onFailure(this.f936c, clientException, null, null);
            this.f954u = true;
            if (this.f953t) {
                m926h();
            }
        }
        m921c();
    }

    /* JADX INFO: renamed from: a */
    public void m916a(AudioRecognizeResultListener audioRecognizeResultListener) {
        this.f942i = audioRecognizeResultListener;
    }

    /* JADX INFO: renamed from: a */
    public void m917a(AudioRecognizeStateListener audioRecognizeStateListener) {
        this.f943j = audioRecognizeStateListener;
    }

    /* JADX INFO: renamed from: a */
    public void m918a(AudioRecognizeRequest audioRecognizeRequest) {
        this.f950q = 0L;
        this.f952s = true;
        String strM977a = C2606b.m977a();
        AAILogger.info(this.f935b, "voiceId = " + strM977a);
        try {
            String strM981a = C2607c.m981a(C2607c.m982a(strM977a, audioRecognizeRequest, this.f941h), this.f941h, this.f940g);
            AAILogger.info(this.f935b, strM981a);
            Request.Builder builder = new Request.Builder();
            builder.url(strM981a);
            if (this.f941h.m967c() != null) {
                builder.header(com.tencent.cloud.stream.tts.core.p032ws.Constant.HEADER_TOKEN, this.f941h.m967c());
            }
            builder.removeHeader("User-Agent").addHeader("User-Agent", audioRecognizeRequest.getExtraUserAgent().length() > 0 ? String.format("Android-sdk-%s-%s", "v3.1.22", audioRecognizeRequest.getExtraUserAgent()) : String.format("Android-sdk-%s", "v3.1.22"));
            Request requestBuild = builder.build();
            AAILogger.info(this.f935b, "prepare send websocket connect." + strM981a);
            f934a.newWebSocket(requestBuild, new a(audioRecognizeRequest));
        } catch (UnsupportedEncodingException e) {
            AudioRecognizeResultListener audioRecognizeResultListener = this.f942i;
            if (audioRecognizeResultListener != null) {
                audioRecognizeResultListener.onFailure(null, new ClientException(ClientExceptionType.UNKNOWN_ERROR, e.toString()), null, null);
                this.f954u = true;
                if (this.f953t) {
                    m926h();
                }
            }
            m921c();
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m919a(short[] sArr, int i) {
        AudioRecognizeStateListener audioRecognizeStateListener = this.f943j;
        if (audioRecognizeStateListener != null) {
            audioRecognizeStateListener.onNextAudioData(sArr, i);
        }
    }

    /* JADX INFO: renamed from: b */
    public final String m920b() {
        StringBuilder sb = new StringBuilder();
        int size = this.f949p.size();
        String[] strArr = new String[size];
        for (Map.Entry<String, String> entry : this.f949p.entrySet()) {
            strArr[Integer.parseInt(entry.getValue())] = entry.getKey();
        }
        for (int i = 0; i < size; i++) {
            sb.append(this.f948o.get(strArr[i]).getText());
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: c */
    public boolean m921c() {
        m925g();
        this.f938e.m945e();
        AAILogger.debug("AudioRecognizeTask", "handle on cancel.");
        this.f945l = true;
        AAILogger.info("AudioRecognizeTask", "the audio recognize is on cancel..");
        m922d();
        AAILogger.debug("AudioRecognizeTask", "the cancel is over..");
        return true;
    }

    /* JADX INFO: renamed from: d */
    public void m922d() {
        this.f942i = null;
        synchronized (this) {
            WebSocket webSocket = this.f939f;
            if (webSocket != null) {
                webSocket.close(4101, "user cancel recognize");
                this.f939f.cancel();
                this.f939f = null;
                AAILogger.info(this.f935b, "disConnectWebsocket socket is close");
            }
        }
    }

    /* JADX INFO: renamed from: e */
    public final void m923e() {
        AAILogger.debug("AudioRecognizeTask", "handle start record");
        this.f945l = false;
        AudioRecognizeStateListener audioRecognizeStateListener = this.f943j;
        if (audioRecognizeStateListener != null) {
            audioRecognizeStateListener.onStartRecord(this.f936c);
        }
    }

    /* JADX INFO: renamed from: f */
    public final void m924f() {
        this.f953t = true;
        if (this.f954u || this.f939f == null) {
            m926h();
        }
    }

    /* JADX INFO: renamed from: g */
    public void m925g() {
        this.f946m = true;
    }

    /* JADX INFO: renamed from: h */
    public final void m926h() {
        AAILogger.debug("AudioRecognizeTask", "handle stop record");
        if (this.f943j != null) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.f943j.onStopRecord(this.f936c);
        }
    }

    /* JADX INFO: renamed from: i */
    public Object m927i() {
        synchronized (this.f955v) {
            try {
                try {
                    this.f938e.m941a(new b());
                    this.f938e.m940a(new c());
                    this.f938e.m944d();
                    if (this.f937d.isCompress()) {
                        this.f936c.setVoice_format(10);
                    } else {
                        this.f936c.setVoice_format(1);
                    }
                    m918a(this.f936c);
                } catch (ClientException e) {
                    m915a(e);
                    return -1;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    /* JADX INFO: renamed from: j */
    public boolean m928j() {
        m925g();
        this.f938e.m945e();
        AAILogger.info("AudioRecognizeTask", "the audio recognize task is ready to finish.");
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0062 A[Catch: all -> 0x008b, TryCatch #1 {, blocks: (B:20:0x0058, B:22:0x005c, B:26:0x006c, B:27:0x006f, B:29:0x0073, B:33:0x007f, B:34:0x0089, B:31:0x007b, B:23:0x0062, B:25:0x0066), top: B:43:0x0058 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r5 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "current thread id = "
            r0.<init>(r1)
            java.lang.Thread r1 = java.lang.Thread.currentThread()
            long r1 = r1.getId()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "AudioRecognizeTask"
            com.tencent.aai.log.AAILogger.info(r1, r0)
            java.lang.Object r0 = r5.m927i()
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            if (r0 == 0) goto L30
            java.lang.String r0 = "AudioRecognizeTask"
            java.lang.String r1 = "record thread start failed.."
            com.tencent.aai.log.AAILogger.error(r0, r1)
            return
        L30:
            boolean r0 = r5.f947n
            if (r0 != 0) goto L8e
            r0 = 0
            okhttp3.WebSocket r1 = r5.f939f     // Catch: java.lang.InterruptedException -> L4b
            if (r1 == 0) goto L49
            java.util.concurrent.BlockingQueue<com.tencent.aai.task.a> r1 = r5.f944k     // Catch: java.lang.InterruptedException -> L4b
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: java.lang.InterruptedException -> L4b
            r3 = 40
            java.lang.Object r1 = r1.poll(r3, r2)     // Catch: java.lang.InterruptedException -> L4b
            com.tencent.aai.task.a r1 = (com.tencent.aai.task.C2599a) r1     // Catch: java.lang.InterruptedException -> L4b
            r5.m912a()     // Catch: java.lang.InterruptedException -> L4c
            goto L53
        L49:
            r1 = r0
            goto L53
        L4b:
            r1 = r0
        L4c:
            java.lang.String r2 = "AudioRecognizeTask"
            java.lang.String r3 = "the blocking queue poll() is interrupted while waiting.."
            com.tencent.aai.log.AAILogger.warn(r2, r3)
        L53:
            java.lang.Object r2 = r5.f955v
            monitor-enter(r2)
            if (r1 == 0) goto L62
            boolean r3 = r5.f945l     // Catch: java.lang.Throwable -> L8b
            if (r3 != 0) goto L62
            com.tencent.aai.task.b$d r0 = new com.tencent.aai.task.b$d     // Catch: java.lang.Throwable -> L8b
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L8b
            goto L6c
        L62:
            boolean r1 = r5.f946m     // Catch: java.lang.Throwable -> L8b
            if (r1 == 0) goto L6f
            com.tencent.aai.task.b$d r1 = new com.tencent.aai.task.b$d     // Catch: java.lang.Throwable -> L8b
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L8b
            r0 = r1
        L6c:
            r0.m930b()     // Catch: java.lang.Throwable -> L8b
        L6f:
            boolean r0 = r5.f946m     // Catch: java.lang.Throwable -> L8b
            if (r0 == 0) goto L7b
            java.util.concurrent.BlockingQueue<com.tencent.aai.task.a> r0 = r5.f944k     // Catch: java.lang.Throwable -> L8b
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L8b
            if (r0 != 0) goto L7f
        L7b:
            boolean r0 = r5.f945l     // Catch: java.lang.Throwable -> L8b
            if (r0 == 0) goto L89
        L7f:
            r0 = 1
            r5.f947n = r0     // Catch: java.lang.Throwable -> L8b
            java.lang.String r0 = "AudioRecognizeTask"
            java.lang.String r1 = "the audio recognize task is finished.."
            com.tencent.aai.log.AAILogger.info(r0, r1)     // Catch: java.lang.Throwable -> L8b
        L89:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L8b
            goto L30
        L8b:
            r0 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L8b
            throw r0
        L8e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.aai.task.RunnableC2600b.run():void");
    }
}
