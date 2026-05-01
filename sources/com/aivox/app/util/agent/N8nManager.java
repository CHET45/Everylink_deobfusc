package com.aivox.app.util.agent;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import com.aivox.app.util.agent.GlassDelegate;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.JsonSyntaxException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class N8nManager extends BaseChatManager<GlassDelegate> {
    private static final String TAG = "N8nManager";
    private static volatile N8nManager instance = null;
    private static final String token = "sk-glass-live-5f4dcc3b5aa765d61d8327deb882cf99";
    private GlassDelegate delegate;
    private String question;
    private Long sessionId;
    private String webHook = "https://everylink66.app.n8n.cloud/webhook/ai-glass-chat";
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public enum AIModel {
        CLAUDE,
        GEMINI,
        GPT,
        GROK
    }

    private N8nManager() {
    }

    public static N8nManager getInstance() {
        if (instance == null) {
            synchronized (N8nManager.class) {
                if (instance == null) {
                    instance = new N8nManager();
                }
            }
        }
        return instance;
    }

    @Override // com.aivox.app.util.agent.BaseChatManager
    public Long getSessionId() {
        return this.sessionId;
    }

    @Override // com.aivox.app.util.agent.BaseChatManager
    public void setSessionId(Long l) {
        this.sessionId = l;
    }

    @Override // com.aivox.app.util.agent.BaseChatManager
    public void setDelegate(GlassDelegate glassDelegate) {
        this.delegate = glassDelegate;
    }

    /* JADX INFO: renamed from: com.aivox.app.util.agent.N8nManager$3 */
    static /* synthetic */ class C08583 {
        static final /* synthetic */ int[] $SwitchMap$com$aivox$app$util$agent$N8nManager$AIModel;

        static {
            int[] iArr = new int[AIModel.values().length];
            $SwitchMap$com$aivox$app$util$agent$N8nManager$AIModel = iArr;
            try {
                iArr[AIModel.GEMINI.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aivox$app$util$agent$N8nManager$AIModel[AIModel.CLAUDE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aivox$app$util$agent$N8nManager$AIModel[AIModel.GROK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void changeModel(AIModel aIModel) {
        int i = C08583.$SwitchMap$com$aivox$app$util$agent$N8nManager$AIModel[aIModel.ordinal()];
        if (i == 1) {
            this.webHook = "https://everylink66.app.n8n.cloud/webhook/ai-glass-chat-gemini";
            return;
        }
        if (i == 2) {
            this.webHook = "https://everylink66.app.n8n.cloud/webhook/ai-glass-chat-claude";
        } else if (i == 3) {
            this.webHook = "https://everylink66.app.n8n.cloud/webhook/ai-glass-chat-grok";
        } else {
            this.webHook = "https://everylink66.app.n8n.cloud/webhook/ai-glass-chat";
        }
    }

    @Override // com.aivox.app.util.agent.BaseChatManager
    public void chat(String str, ChatCallback chatCallback) {
        this.question = str;
        HashMap map = new HashMap();
        map.put("text", this.question);
        map.put(SPUtil.SESSION_ID, this.sessionId);
        map.put(SPUtil.USER_ID, SPUtil.get(SPUtil.USER_ID, ""));
        sendToN8n(map, chatCallback);
    }

    private void sendToN8n(Map<String, Object> map, final ChatCallback chatCallback) {
        printLog(map);
        this.client.newCall(new Request.Builder().url(this.webHook).addHeader("Authorization", "Bearer sk-glass-live-5f4dcc3b5aa765d61d8327deb882cf99").addHeader("Content-Type", "application/json").post(createJsonBody(this.gson.toJson(map))).build()).enqueue(new Callback() { // from class: com.aivox.app.util.agent.N8nManager.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Log.e(N8nManager.TAG, "[N8n] 网络错误: " + iOException.getMessage());
                N8nManager.this.postError(chatCallback, iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    N8nManager.this.postError(chatCallback, new IOException("Unexpected code " + response));
                } else {
                    N8nManager.this.handleN8nResponse(response.body().string(), chatCallback);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleN8nResponse(String str, final ChatCallback chatCallback) {
        LogUtil.m334d("[N8n] 收到响应: " + str);
        try {
            final N8nResponse n8nResponse = (N8nResponse) this.gson.fromJson(str, N8nResponse.class);
            if (n8nResponse == null) {
                postError(chatCallback, new Exception("Empty Response"));
                return;
            }
            if (n8nResponse.getCmd() != null) {
                Log.i(TAG, "[N8n] 解析到控制指令 CMD: " + n8nResponse.getCmd());
                this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.agent.N8nManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2404lambda$handleN8nResponse$0$comaivoxapputilagentN8nManager(n8nResponse);
                    }
                });
                postSuccess(chatCallback, n8nResponse.getReason() != null ? n8nResponse.getReason() : "指令已执行");
                return;
            }
            if ("glass_get_location".equals(n8nResponse.getAction())) {
                Log.i(TAG, "[N8n] 收到定位请求，正在获取 GPS...");
                this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.agent.N8nManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2406lambda$handleN8nResponse$2$comaivoxapputilagentN8nManager(n8nResponse, chatCallback);
                    }
                });
                return;
            }
            if ("glass_image_recognition".equals(n8nResponse.getAction())) {
                Log.i(TAG, "[N8n] 收到视觉识别请求，正在调用摄像头...");
                this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.agent.N8nManager$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2407lambda$handleN8nResponse$3$comaivoxapputilagentN8nManager(chatCallback);
                    }
                });
                return;
            }
            if ("glass_create_event".equals(n8nResponse.getAction())) {
                Log.i(TAG, "[N8n] 创建日程请求，正在调用日历...");
                this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.agent.N8nManager$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2408lambda$handleN8nResponse$4$comaivoxapputilagentN8nManager(n8nResponse, chatCallback);
                    }
                });
            }
            if (n8nResponse.getReply() == null) {
                Log.w(TAG, "[N8n] ⚠️ 无法识别的响应格式: " + str);
                postError(chatCallback, new Exception("Unknown response format"));
            } else {
                postSuccess(chatCallback, n8nResponse.getReply());
            }
        } catch (JsonSyntaxException unused) {
            Log.w(TAG, "[N8n] 收到纯文本响应: " + str);
            postSuccess(chatCallback, str);
        }
    }

    /* JADX INFO: renamed from: lambda$handleN8nResponse$0$com-aivox-app-util-agent-N8nManager, reason: not valid java name */
    /* synthetic */ void m2404lambda$handleN8nResponse$0$comaivoxapputilagentN8nManager(N8nResponse n8nResponse) {
        if (this.delegate != null) {
            if (n8nResponse.getCmd().intValue() == 8) {
                this.delegate.glassTakePhoto();
            } else {
                this.delegate.glassDeviceControl(n8nResponse.getCmd().intValue());
            }
        }
    }

    /* JADX INFO: renamed from: lambda$handleN8nResponse$2$com-aivox-app-util-agent-N8nManager, reason: not valid java name */
    /* synthetic */ void m2406lambda$handleN8nResponse$2$comaivoxapputilagentN8nManager(final N8nResponse n8nResponse, final ChatCallback chatCallback) {
        GlassDelegate glassDelegate = this.delegate;
        if (glassDelegate != null) {
            glassDelegate.glassGetLocation(new GlassDelegate.LocationCallback() { // from class: com.aivox.app.util.agent.N8nManager$$ExternalSyntheticLambda0
                @Override // com.aivox.app.util.agent.GlassDelegate.LocationCallback
                public final void onLocationResult(double d, double d2) {
                    this.f$0.m2405lambda$handleN8nResponse$1$comaivoxapputilagentN8nManager(n8nResponse, chatCallback, d, d2);
                }
            });
        } else {
            postError(chatCallback, new Exception("Delegate is null, cannot get location"));
        }
    }

    /* JADX INFO: renamed from: lambda$handleN8nResponse$1$com-aivox-app-util-agent-N8nManager, reason: not valid java name */
    /* synthetic */ void m2405lambda$handleN8nResponse$1$comaivoxapputilagentN8nManager(N8nResponse n8nResponse, ChatCallback chatCallback, double d, double d2) {
        Log.i(TAG, "[N8n] 获取到坐标: " + d + ", " + d2 + "，正在回传..." + GsonUtils.toJson(n8nResponse));
        HashMap map = new HashMap();
        map.put("text", this.question);
        map.put(SPUtil.SESSION_ID, this.sessionId);
        map.put(SPUtil.USER_ID, SPUtil.get(SPUtil.USER_ID, ""));
        map.put("latitude", Double.valueOf(d));
        map.put("longitude", Double.valueOf(d2));
        map.put("AskLocation", Boolean.valueOf(n8nResponse.getAskLocation()));
        map.put("sortDistance", Boolean.valueOf(n8nResponse.getSortDistance()));
        map.put("keyword", n8nResponse.getKeyword());
        sendToN8n(map, chatCallback);
    }

    /* JADX INFO: renamed from: com.aivox.app.util.agent.N8nManager$2 */
    class C08572 implements GlassDelegate.ImageCallback {
        final /* synthetic */ ChatCallback val$callback;

        C08572(ChatCallback chatCallback) {
            this.val$callback = chatCallback;
        }

        @Override // com.aivox.app.util.agent.GlassDelegate.ImageCallback
        public void onImageCaptured(final Bitmap bitmap) {
            if (bitmap == null) {
                N8nManager.this.postSuccess(this.val$callback, "相机启动失败或用户取消");
            } else {
                final ChatCallback chatCallback = this.val$callback;
                new Thread(new Runnable() { // from class: com.aivox.app.util.agent.N8nManager$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2409lambda$onImageCaptured$0$comaivoxapputilagentN8nManager$2(bitmap, chatCallback);
                    }
                }).start();
            }
        }

        /* JADX INFO: renamed from: lambda$onImageCaptured$0$com-aivox-app-util-agent-N8nManager$2, reason: not valid java name */
        /* synthetic */ void m2409lambda$onImageCaptured$0$comaivoxapputilagentN8nManager$2(Bitmap bitmap, ChatCallback chatCallback) {
            N8nManager.this.processImageAndUpload(bitmap, chatCallback);
        }

        @Override // com.aivox.app.util.agent.GlassDelegate.ImageCallback
        public void onFailed() {
            N8nManager.this.postSuccess(this.val$callback, "用户取消拍照");
        }
    }

    /* JADX INFO: renamed from: lambda$handleN8nResponse$3$com-aivox-app-util-agent-N8nManager, reason: not valid java name */
    /* synthetic */ void m2407lambda$handleN8nResponse$3$comaivoxapputilagentN8nManager(ChatCallback chatCallback) {
        GlassDelegate glassDelegate = this.delegate;
        if (glassDelegate != null) {
            glassDelegate.glassImageRecognition(new C08572(chatCallback));
        } else {
            postError(chatCallback, new Exception("Delegate is null, cannot capture image"));
        }
    }

    /* JADX WARN: Type inference failed for: r4v5, types: [java.time.ZonedDateTime] */
    /* JADX INFO: renamed from: lambda$handleN8nResponse$4$com-aivox-app-util-agent-N8nManager, reason: not valid java name */
    /* synthetic */ void m2408lambda$handleN8nResponse$4$comaivoxapputilagentN8nManager(N8nResponse n8nResponse, ChatCallback chatCallback) {
        if (!n8nResponse.getError()) {
            if (this.delegate != null) {
                this.delegate.glassCalendarEvent(n8nResponse.getSummary(), Long.valueOf(LocalDateTime.parse(n8nResponse.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
                return;
            }
            return;
        }
        postError(chatCallback, new Exception(n8nResponse.getReply()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processImageAndUpload(Bitmap bitmap, ChatCallback chatCallback) {
        Log.i(TAG, "[N8n] 捕获图片成功，开始压缩转码 Base64...");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String str = "data:image/jpeg;base64," + Base64.encodeToString(byteArray, 2);
        HashMap map = new HashMap();
        map.put("text", this.question);
        map.put("image_base64", str);
        map.put(SPUtil.SESSION_ID, this.sessionId);
        map.put(SPUtil.USER_ID, SPUtil.get(SPUtil.USER_ID, ""));
        Log.i(TAG, "[N8n] 图片数据准备完毕 (Size: " + byteArray.length + " bytes)，发送中...");
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        sendToN8n(map, chatCallback);
    }

    @Override // com.aivox.app.util.agent.BaseChatManager
    protected void postSuccess(final ChatCallback chatCallback, final String str) {
        this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.agent.N8nManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                N8nManager.lambda$postSuccess$5(chatCallback, str);
            }
        });
    }

    static /* synthetic */ void lambda$postSuccess$5(ChatCallback chatCallback, String str) {
        if (chatCallback != null) {
            chatCallback.onSuccess(str);
        }
    }

    @Override // com.aivox.app.util.agent.BaseChatManager
    protected void postError(final ChatCallback chatCallback, final Throwable th) {
        this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.agent.N8nManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                N8nManager.lambda$postError$6(chatCallback, th);
            }
        });
    }

    static /* synthetic */ void lambda$postError$6(ChatCallback chatCallback, Throwable th) {
        if (chatCallback != null) {
            chatCallback.onError(th);
        }
    }

    private void printLog(Map<String, Object> map) {
        HashMap map2 = new HashMap(map);
        if (map.containsKey("image_base64")) {
            Object obj = map.get("image_base64");
            if (obj instanceof String) {
                map2.put("image_base64", "[Base64 Data Length: " + ((String) obj).length() + "]");
            } else {
                map2.put("image_base64", "[Base64 Data]");
            }
        }
        Log.d(TAG, "[N8n] 发送请求: " + map2);
    }
}
