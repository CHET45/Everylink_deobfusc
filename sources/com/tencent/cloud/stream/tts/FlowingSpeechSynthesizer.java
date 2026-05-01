package com.tencent.cloud.stream.tts;

import android.text.TextUtils;
import com.aivox.base.util.SPUtil;
import com.google.gson.Gson;
import com.tencent.cloud.stream.tts.core.exception.SynthesizerException;
import com.tencent.cloud.stream.tts.core.exception.SynthesizerExceptionType;
import com.tencent.cloud.stream.tts.core.p032ws.Connection;
import com.tencent.cloud.stream.tts.core.p032ws.ConnectionProfile;
import com.tencent.cloud.stream.tts.core.p032ws.Credential;
import com.tencent.cloud.stream.tts.core.p032ws.SpeechClient;
import com.tencent.cloud.stream.tts.core.p032ws.StateMachine;
import com.tencent.cloud.stream.tts.core.utils.AAILogger;
import com.tencent.cloud.stream.tts.core.utils.SignBuilder;
import com.tencent.cloud.stream.tts.core.utils.SignHelper;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes4.dex */
public class FlowingSpeechSynthesizer extends StateMachine {
    public static final String TAG = "FlowingSpeechSynthesizer";
    private SpeechClient client;
    protected Connection conn;
    private Credential credential;
    protected AtomicLong lastSendTime;
    private FlowingSpeechSynthesizerListener listener;
    private FlowingSpeechSynthesizerRequest request;
    private final String sessionId;
    private final CountDownLatch startLatch;
    private final CountDownLatch stopLatch;

    public Credential getCredential() {
        return this.credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public FlowingSpeechSynthesizerRequest getRequest() {
        return this.request;
    }

    public void setRequest(FlowingSpeechSynthesizerRequest request) {
        this.request = request;
    }

    public SpeechClient getClient() {
        return this.client;
    }

    public void setClient(SpeechClient client) {
        this.client = client;
    }

    public FlowingSpeechSynthesizerListener getListener() {
        return this.listener;
    }

    public void setListener(FlowingSpeechSynthesizerListener listener) {
        this.listener = listener;
    }

    public FlowingSpeechSynthesizer(SpeechClient client, Credential credential, FlowingSpeechSynthesizerRequest request, FlowingSpeechSynthesizerListener listener) throws SynthesizerException {
        String string = UUID.randomUUID().toString();
        this.sessionId = string;
        this.lastSendTime = new AtomicLong(-1L);
        AAILogger.m1853d(TAG, "init");
        if (client == null) {
            throw new SynthesizerException(SynthesizerExceptionType.CLIENT_CANNOT_BE_NULL);
        }
        if (credential == null) {
            throw new SynthesizerException(SynthesizerExceptionType.CREDENTIAL_CANNOT_BE_NULL);
        }
        if (request == null) {
            throw new SynthesizerException(SynthesizerExceptionType.REQUEST_CANNOT_BE_NULL);
        }
        if (listener == null) {
            throw new SynthesizerException(SynthesizerExceptionType.LISTENER_CANNOT_BE_NULL);
        }
        if (TextUtils.isEmpty(credential.getAppid())) {
            throw new SynthesizerException(SynthesizerExceptionType.APPID_IS_EMPTY);
        }
        if (TextUtils.isEmpty(credential.getSecretId())) {
            throw new SynthesizerException(SynthesizerExceptionType.SECRETID_IS_EMPTY);
        }
        if (TextUtils.isEmpty(credential.getSecretKey())) {
            throw new SynthesizerException(SynthesizerExceptionType.SECRETKEY_IS_EMPTY);
        }
        if (request.getSessionId() == null) {
            request.setSessionId(string);
        }
        request.setAction("TextToStreamAudioWSv2");
        request.setAppId(Integer.valueOf(credential.getAppid()));
        this.request = request;
        this.credential = credential;
        this.client = client;
        this.listener = listener;
        this.stopLatch = new CountDownLatch(1);
        this.startLatch = new CountDownLatch(1);
        listener.setSpeechSynthesizer(this);
    }

    public void start() throws Exception {
        start(60000L);
    }

    public void start(long milliSeconds) throws Exception {
        String str = TAG;
        AAILogger.m1853d(str, "start");
        this.state.checkStart();
        AAILogger.m1853d(str, String.format("sessionId:%s,start change state from %s.", this.sessionId, this.state));
        this.state = this.state.start();
        AAILogger.m1853d(str, String.format("sessionId:%s,start change state to %s.", this.sessionId, this.state));
        this.request.setSecretid(this.credential.getSecretId());
        this.request.setTimestamp(Long.valueOf(System.currentTimeMillis() / 1000));
        this.request.setExpired(Long.valueOf((System.currentTimeMillis() / 1000) + 86400));
        Map<String, Object> treeMap = this.request.toTreeMap();
        String str2 = TtsConstant.DEFAULT_TTS_V2_SIGN_PREFIX + SignHelper.createUrl(treeMap);
        AAILogger.m1853d(str, str2);
        String strBase64_hmac_sha1 = SignBuilder.base64_hmac_sha1(str2, this.credential.getSecretKey());
        String str3 = TtsConstant.DEFAULT_TTS_V2_REQ_URL + SignHelper.createUrl(SignHelper.encode(treeMap)) + "&Signature=" + URLEncoder.encode(strBase64_hmac_sha1, "UTF-8");
        AAILogger.m1853d(str, str3);
        this.conn = this.client.connect(new ConnectionProfile(strBase64_hmac_sha1, str3, TtsConstant.DEFAULT_HOST, this.credential.getToken()), this.listener);
        if (this.startLatch.await(milliSeconds, TimeUnit.MILLISECONDS)) {
            return;
        }
        String str4 = String.format("timeout after %d ms waiting for start confirmation.sessionId:%s,state:%s", Long.valueOf(milliSeconds), this.sessionId, this.state);
        AAILogger.m1854e(str, str4);
        throw new SynthesizerException(SynthesizerExceptionType.START_SYNTHESIZER_FAIL, str4);
    }

    public void process(String text) throws Exception {
        if (this.state == StateMachine.State.STATE_COMPLETE) {
            AAILogger.m1855i(TAG, String.format("state is %s stop send", StateMachine.State.STATE_COMPLETE));
            return;
        }
        if (this.lastSendTime.get() != -1) {
            long jCurrentTimeMillis = System.currentTimeMillis() - this.lastSendTime.get();
            if (jCurrentTimeMillis > 5000) {
                AAILogger.m1856w(TAG, String.format("too large binary send interval: %d million second", Long.valueOf(jCurrentTimeMillis)));
            }
        }
        this.state.checkSend();
        if (this.conn == null) {
            String str = String.format("Connection is null, current_task_id: %s, state: %s", this.sessionId, this.state);
            AAILogger.m1854e(TAG, str);
            throw new SynthesizerException(SynthesizerExceptionType.SEND_TEXT_FAIL, str);
        }
        try {
            this.conn.sendText(newWsRequestMessage(text, TtsConstant.getFlowingSpeechSynthesizer_ACTION_SYNTHESIS()));
            this.lastSendTime.set(System.currentTimeMillis());
        } catch (Exception e) {
            String str2 = String.format("fail to send text,current_task_id: %s, state: %s, e: %s", this.sessionId, this.state, e.getLocalizedMessage());
            AAILogger.m1854e(TAG, str2);
            throw new SynthesizerException(SynthesizerExceptionType.SEND_TEXT_FAIL, str2);
        }
    }

    private String newWsRequestMessage(String text, String action) {
        HashMap map = new HashMap();
        map.put(SPUtil.SESSION_ID, this.request.getSessionId());
        map.put("message_id", UUID.randomUUID().toString());
        map.put("data", text);
        map.put("action", action);
        return new Gson().toJson(map);
    }

    public void stop() throws Exception {
        stop(500L);
    }

    public void stop(long milliSeconds) throws Exception {
        String str = TAG;
        AAILogger.m1853d(str, "stop");
        if (this.state == StateMachine.State.STATE_COMPLETE) {
            AAILogger.m1855i(str, String.format("state is %s stop message is discarded", StateMachine.State.STATE_COMPLETE));
            return;
        }
        this.state.checkStop();
        this.state = this.state.stopSend();
        if (this.conn != null) {
            String strNewWsRequestMessage = newWsRequestMessage("", TtsConstant.getFlowingSpeechSynthesizer_ACTION_COMPLETE());
            AAILogger.m1853d(str, strNewWsRequestMessage);
            this.conn.sendText(strNewWsRequestMessage);
            if (milliSeconds > 0) {
                if (this.stopLatch.await(milliSeconds, TimeUnit.MILLISECONDS)) {
                    return;
                }
                AAILogger.m1854e(str, String.format("timeout after %d ms waiting for stop confirmation.sessionId: %s, state: %s", Long.valueOf(milliSeconds), this.sessionId, this.state));
                return;
            }
            this.stopLatch.await();
        }
    }

    public void cancel() {
        AAILogger.m1853d(TAG, "cancel");
        SpeechClient speechClient = this.client;
        if (speechClient != null) {
            speechClient.cancel();
        }
    }

    void markReady() {
        String str = TAG;
        AAILogger.m1853d(str, String.format("sessionId:%s,markReady change state from %s", this.sessionId, this.state));
        this.state = this.state.send();
        AAILogger.m1853d(str, String.format("sessionId:%s, markReady change state to %s", this.sessionId, this.state));
        CountDownLatch countDownLatch = this.startLatch;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    void markComplete() {
        String str = TAG;
        AAILogger.m1853d(str, String.format("sessionId:%s, markComplete change state from %s", this.sessionId, this.state));
        this.state = this.state.complete();
        AAILogger.m1853d(str, String.format("sessionId: %s, markComplete change state to %s", this.sessionId, this.state));
        SpeechClient speechClient = this.client;
        if (speechClient != null) {
            speechClient.disconnect();
        }
        CountDownLatch countDownLatch = this.stopLatch;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    void markFail() {
        String str = TAG;
        AAILogger.m1853d(str, String.format("sessionId:%s, markFail change state from %s", this.sessionId, this.state));
        this.state = this.state.fail();
        AAILogger.m1853d(str, String.format("sessionId:%s, markFail change state to %s ", this.sessionId, this.state));
        CountDownLatch countDownLatch = this.startLatch;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
        CountDownLatch countDownLatch2 = this.stopLatch;
        if (countDownLatch2 != null) {
            countDownLatch2.countDown();
        }
    }

    void markClosed() {
        String str = TAG;
        AAILogger.m1853d(str, String.format("sessionId:%s, markClosed change state from %s", this.sessionId, this.state));
        this.state = this.state.closed();
        AAILogger.m1853d(str, String.format("sessionId:%s, markClosed change state to %s", this.sessionId, this.state));
        CountDownLatch countDownLatch = this.startLatch;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
        CountDownLatch countDownLatch2 = this.stopLatch;
        if (countDownLatch2 != null) {
            countDownLatch2.countDown();
        }
    }

    public static String version() {
        return BuildConfig.SDK_VERSION;
    }
}
