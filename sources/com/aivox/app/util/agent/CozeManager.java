package com.aivox.app.util.agent;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.aivox.app.util.agent.GlassDelegate;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.PlaceholderUtil;
import com.google.gson.reflect.TypeToken;
import com.microsoft.azure.storage.queue.QueueConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class CozeManager extends BaseChatManager<GlassDelegate> {
    private static volatile CozeManager instance;
    private String currentConversationId;
    private GlassDelegate delegate;
    private Long sessionId;
    private final String region = "https://api.coze.cn";
    private String apiKey = "sat_tLpfX0EVF6I3EXFNVVWZuElIq1bMAM9aaCFSXVHdJ0LigjxMoC6faOEyC4JtLrVi";
    private final String botId = "7586615103211634707";
    private final String userId = "android_glass_user_";
    private final ExecutorService backgroundExecutor = Executors.newCachedThreadPool();

    /* JADX INFO: Access modifiers changed from: private */
    interface CreateConvCallback {
        void onResult(String str, Exception exc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface ProcessCallback {
        void onFinished(List<Map<String, Object>> list);
    }

    private CozeManager() {
    }

    public static CozeManager getInstance() {
        if (instance == null) {
            synchronized (CozeManager.class) {
                if (instance == null) {
                    instance = new CozeManager();
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

    public void setApiKey(String str) {
        this.apiKey = str;
    }

    private Map<String, String> getHeaders() {
        HashMap map = new HashMap();
        map.put("Authorization", "Bearer " + this.apiKey);
        map.put("Content-Type", "application/json");
        map.put("Accept", "application/json");
        return map;
    }

    @Override // com.aivox.app.util.agent.BaseChatManager
    public void chat(final String str, final ChatCallback chatCallback) {
        if (this.currentConversationId != null) {
            Log.d(this.TAG, "[Coze] 复用会话 ID: " + this.currentConversationId);
            performChat(this.currentConversationId, str, chatCallback);
        } else {
            Log.d(this.TAG, "[Coze] 无会话 ID，正在新建会话...");
            createConversation(new CreateConvCallback() { // from class: com.aivox.app.util.agent.CozeManager$$ExternalSyntheticLambda3
                @Override // com.aivox.app.util.agent.CozeManager.CreateConvCallback
                public final void onResult(String str2, Exception exc) {
                    this.f$0.m2396lambda$chat$0$comaivoxapputilagentCozeManager(str, chatCallback, str2, exc);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$chat$0$com-aivox-app-util-agent-CozeManager, reason: not valid java name */
    /* synthetic */ void m2396lambda$chat$0$comaivoxapputilagentCozeManager(String str, ChatCallback chatCallback, String str2, Exception exc) {
        if (str2 != null) {
            Log.d(this.TAG, "[Coze] 会话创建成功: " + str2);
            this.currentConversationId = str2;
            performChat(str2, str, chatCallback);
        } else {
            if (exc == null) {
                exc = new Exception("Failed to create conversation");
            }
            chatCallback.onError(exc);
        }
    }

    private void createConversation(final CreateConvCallback createConvCallback) {
        HashMap map = new HashMap();
        map.put(QueueConstants.MESSAGES, new ArrayList());
        this.client.newCall(buildPostRequest("https://api.coze.cn/v1/conversation/create", this.gson.toJson(map))).enqueue(new Callback() { // from class: com.aivox.app.util.agent.CozeManager.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                createConvCallback.onResult(null, iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String strString = response.body().string();
                    LogUtil.m335d(CozeManager.this.TAG, "[Coze] 创建会话: " + strString);
                    CozeResponse cozeResponse = (CozeResponse) CozeManager.this.gson.fromJson(strString, new TypeToken<CozeResponse<ConversationData>>() { // from class: com.aivox.app.util.agent.CozeManager.1.1
                    }.getType());
                    if (cozeResponse != null && cozeResponse.getCode() == 0 && cozeResponse.getData() != null) {
                        createConvCallback.onResult(((ConversationData) cozeResponse.getData()).getId(), null);
                    } else {
                        Log.e(CozeManager.this.TAG, "Create Conversation Error: " + strString);
                        createConvCallback.onResult(null, new Exception("Code: " + (cozeResponse != null ? cozeResponse.getCode() : -1)));
                    }
                } catch (Exception e) {
                    createConvCallback.onResult(null, e);
                }
            }
        });
    }

    private void performChat(String str, String str2, final ChatCallback chatCallback) {
        HashMap map = new HashMap();
        map.put("role", "user");
        map.put("content", str2);
        map.put("content_type", "text");
        ArrayList arrayList = new ArrayList();
        arrayList.add(map);
        HashMap map2 = new HashMap();
        map2.put("bot_id", "7586615103211634707");
        map2.put(SPUtil.USER_ID, "android_glass_user_" + SPUtil.get(SPUtil.USER_ID, ""));
        map2.put("conversation_id", str);
        map2.put("additional_messages", arrayList);
        map2.put("stream", false);
        this.client.newCall(buildPostRequest("https://api.coze.cn/v3/chat", this.gson.toJson(map2))).enqueue(new Callback() { // from class: com.aivox.app.util.agent.CozeManager.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                chatCallback.onError(iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    CozeResponse cozeResponse = (CozeResponse) CozeManager.this.gson.fromJson(response.body().string(), new TypeToken<CozeResponse<CozeChatData>>() { // from class: com.aivox.app.util.agent.CozeManager.2.1
                    }.getType());
                    if (cozeResponse != null && cozeResponse.getCode() == 0 && cozeResponse.getData() != null) {
                        CozeManager.this.pollStatus(((CozeChatData) cozeResponse.getData()).getConversationId(), ((CozeChatData) cozeResponse.getData()).getId(), chatCallback);
                    } else {
                        chatCallback.onError(new Exception(cozeResponse != null ? cozeResponse.getMsg() : "Unknown error"));
                    }
                } catch (Exception e) {
                    chatCallback.onError(e);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pollStatus(String str, String str2, ChatCallback chatCallback) {
        this.client.newCall(new Request.Builder().url("https://api.coze.cn/v3/chat/retrieve?chat_id=" + str2 + "&conversation_id=" + str).headers(Headers.m1925of(getHeaders())).get().build()).enqueue(new C08493(chatCallback, str, str2));
    }

    /* JADX INFO: renamed from: com.aivox.app.util.agent.CozeManager$3 */
    class C08493 implements Callback {
        final /* synthetic */ ChatCallback val$callback;
        final /* synthetic */ String val$chatId;
        final /* synthetic */ String val$conversationId;

        C08493(ChatCallback chatCallback, String str, String str2) {
            this.val$callback = chatCallback;
            this.val$conversationId = str;
            this.val$chatId = str2;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            this.val$callback.onError(iOException);
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0082  */
        @Override // okhttp3.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onResponse(okhttp3.Call r7, okhttp3.Response r8) throws java.io.IOException {
            /*
                Method dump skipped, instruction units count: 304
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aivox.app.util.agent.CozeManager.C08493.onResponse(okhttp3.Call, okhttp3.Response):void");
        }

        /* JADX INFO: renamed from: lambda$onResponse$1$com-aivox-app-util-agent-CozeManager$3, reason: not valid java name */
        /* synthetic */ void m2401lambda$onResponse$1$comaivoxapputilagentCozeManager$3(final String str, final String str2, final ChatCallback chatCallback, List list) {
            CozeManager.this.submitToolOutputs(str, str2, list, new ValueCallback() { // from class: com.aivox.app.util.agent.CozeManager$3$$ExternalSyntheticLambda0
                @Override // com.aivox.app.util.agent.ValueCallback
                public final void onResult(Object obj) {
                    this.f$0.m2400lambda$onResponse$0$comaivoxapputilagentCozeManager$3(str, str2, chatCallback, (Boolean) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onResponse$0$com-aivox-app-util-agent-CozeManager$3, reason: not valid java name */
        /* synthetic */ void m2400lambda$onResponse$0$comaivoxapputilagentCozeManager$3(String str, String str2, ChatCallback chatCallback, Boolean bool) {
            if (Boolean.TRUE.equals(bool)) {
                CozeManager.this.pollStatus(str, str2, chatCallback);
            } else {
                chatCallback.onError(new Exception("Submit tool outputs failed"));
            }
        }

        /* JADX INFO: renamed from: lambda$onResponse$2$com-aivox-app-util-agent-CozeManager$3, reason: not valid java name */
        /* synthetic */ void m2402lambda$onResponse$2$comaivoxapputilagentCozeManager$3(String str, String str2, ChatCallback chatCallback) {
            CozeManager.this.pollStatus(str, str2, chatCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processToolCalls(CozeChatData cozeChatData, final ProcessCallback processCallback) {
        if (cozeChatData.getRequiredAction() == null || cozeChatData.getRequiredAction().getSubmitToolOutputs() == null || cozeChatData.getRequiredAction().getSubmitToolOutputs().getToolCalls().isEmpty()) {
            processCallback.onFinished(new ArrayList());
            return;
        }
        final List<ToolCall> toolCalls = cozeChatData.getRequiredAction().getSubmitToolOutputs().getToolCalls();
        final ArrayList arrayList = new ArrayList();
        final CountDownLatch countDownLatch = new CountDownLatch(toolCalls.size());
        this.backgroundExecutor.execute(new Runnable() { // from class: com.aivox.app.util.agent.CozeManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2399lambda$processToolCalls$3$comaivoxapputilagentCozeManager(toolCalls, arrayList, countDownLatch, processCallback);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$processToolCalls$3$com-aivox-app-util-agent-CozeManager, reason: not valid java name */
    /* synthetic */ void m2399lambda$processToolCalls$3$comaivoxapputilagentCozeManager(List list, final List list2, final CountDownLatch countDownLatch, ProcessCallback processCallback) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ToolCall toolCall = (ToolCall) it.next();
            final String id = toolCall.getId();
            final String name = toolCall.getFunction() != null ? toolCall.getFunction().getName() : "";
            final String arguments = toolCall.getFunction() != null ? toolCall.getFunction().getArguments() : StringUtil.EMPTY_JSON;
            Log.d(this.TAG, "[Coze] 🔌 Invoke Client Tool: " + name);
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.aivox.app.util.agent.CozeManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2398lambda$processToolCalls$2$comaivoxapputilagentCozeManager(list2, id, countDownLatch, name, arguments);
                }
            });
        }
        try {
            countDownLatch.await();
            processCallback.onFinished(list2);
        } catch (InterruptedException unused) {
            processCallback.onFinished(new ArrayList());
        }
    }

    /* JADX WARN: Type inference failed for: r10v9, types: [java.time.ZonedDateTime] */
    /* JADX INFO: renamed from: lambda$processToolCalls$2$com-aivox-app-util-agent-CozeManager, reason: not valid java name */
    /* synthetic */ void m2398lambda$processToolCalls$2$comaivoxapputilagentCozeManager(final List list, final String str, final CountDownLatch countDownLatch, String str2, String str3) {
        int iIntValue;
        if (this.delegate == null) {
            appendOutput(list, str, "Delegate not set");
            countDownLatch.countDown();
        }
        str2.hashCode();
        iIntValue = 0;
        switch (str2) {
            case "create_calendar_event":
                try {
                    Map map = (Map) this.gson.fromJson(str3, new TypeToken<Map<String, Object>>() { // from class: com.aivox.app.util.agent.CozeManager.6
                    }.getType());
                    if (map.containsKey("summary") && map.containsKey("time")) {
                        String str4 = (String) map.get("summary");
                        String str5 = (String) map.get("time");
                        if (!TextUtils.isEmpty(str4) && !TextUtils.isEmpty(str5)) {
                            this.delegate.glassCalendarEvent(str4, Long.valueOf(LocalDateTime.parse(str5, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
                            appendOutput(list, str, "{\"status\": \"success\"}");
                            countDownLatch.countDown();
                        }
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                appendOutput(list, str, "{\"error\": \"Create calendar event failed\"}");
                countDownLatch.countDown();
                break;
            case "glass_image_recognition":
                this.delegate.glassImageRecognition(new C08515(list, str, countDownLatch));
                break;
            case "glass_take_photo":
                this.delegate.glassTakePhoto();
                appendOutput(list, str, "{\"status\": \"success\"}");
                countDownLatch.countDown();
                break;
            case "glass_get_location":
                this.delegate.glassGetLocation(new GlassDelegate.LocationCallback() { // from class: com.aivox.app.util.agent.CozeManager$$ExternalSyntheticLambda1
                    @Override // com.aivox.app.util.agent.GlassDelegate.LocationCallback
                    public final void onLocationResult(double d, double d2) {
                        this.f$0.m2397lambda$processToolCalls$1$comaivoxapputilagentCozeManager(list, str, countDownLatch, d, d2);
                    }
                });
                break;
            case "glass_device_control":
                try {
                    Map map2 = (Map) this.gson.fromJson(str3, new TypeToken<Map<String, Object>>() { // from class: com.aivox.app.util.agent.CozeManager.4
                    }.getType());
                    if (map2.containsKey("cmd")) {
                        iIntValue = ((Number) map2.get("cmd")).intValue();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.delegate.glassDeviceControl(iIntValue);
                appendOutput(list, str, "{\"status\": \"success\", \"cmd\": " + iIntValue + PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
                countDownLatch.countDown();
                break;
            default:
                Log.w(this.TAG, "⚠️ Unknown Tool: " + str2);
                appendOutput(list, str, "Unknown tool");
                countDownLatch.countDown();
                break;
        }
    }

    /* JADX INFO: renamed from: lambda$processToolCalls$1$com-aivox-app-util-agent-CozeManager, reason: not valid java name */
    /* synthetic */ void m2397lambda$processToolCalls$1$comaivoxapputilagentCozeManager(List list, String str, CountDownLatch countDownLatch, double d, double d2) {
        String str2 = "{\"location\": \"" + d2 + PunctuationConst.COMMA + d + "\"}";
        Log.d(this.TAG, "[Coze] 📍 Submitting Location JSON: " + str2);
        appendOutput(list, str, str2);
        countDownLatch.countDown();
    }

    /* JADX INFO: renamed from: com.aivox.app.util.agent.CozeManager$5 */
    class C08515 implements GlassDelegate.ImageCallback {
        final /* synthetic */ String val$callId;
        final /* synthetic */ CountDownLatch val$latch;
        final /* synthetic */ List val$outputs;

        C08515(List list, String str, CountDownLatch countDownLatch) {
            this.val$outputs = list;
            this.val$callId = str;
            this.val$latch = countDownLatch;
        }

        @Override // com.aivox.app.util.agent.GlassDelegate.ImageCallback
        public void onImageCaptured(Bitmap bitmap) {
            if (bitmap == null) {
                CozeManager.this.appendOutput(this.val$outputs, this.val$callId, "{\"error\": \"No image captured\"}");
                this.val$latch.countDown();
                return;
            }
            CozeManager cozeManager = CozeManager.this;
            final List list = this.val$outputs;
            final String str = this.val$callId;
            final CountDownLatch countDownLatch = this.val$latch;
            cozeManager.uploadImageToCoze(bitmap, new ValueCallback() { // from class: com.aivox.app.util.agent.CozeManager$5$$ExternalSyntheticLambda0
                @Override // com.aivox.app.util.agent.ValueCallback
                public final void onResult(Object obj) {
                    this.f$0.m2403lambda$onImageCaptured$0$comaivoxapputilagentCozeManager$5(list, str, countDownLatch, (String) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onImageCaptured$0$com-aivox-app-util-agent-CozeManager$5, reason: not valid java name */
        /* synthetic */ void m2403lambda$onImageCaptured$0$comaivoxapputilagentCozeManager$5(List list, String str, CountDownLatch countDownLatch, String str2) {
            CozeManager.this.appendOutput(list, str, str2 != null ? "{\"image\": \"" + str2 + "\"}" : "{\"error\": \"Upload failed\"}");
            countDownLatch.countDown();
        }

        @Override // com.aivox.app.util.agent.GlassDelegate.ImageCallback
        public void onFailed() {
            CozeManager.this.appendOutput(this.val$outputs, this.val$callId, "{\"error\": \"Image capture failed\"}");
            this.val$latch.countDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void appendOutput(List<Map<String, Object>> list, String str, String str2) {
        HashMap map = new HashMap();
        map.put("tool_call_id", str);
        map.put("output", str2);
        list.add(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void submitToolOutputs(String str, String str2, List<Map<String, Object>> list, final ValueCallback<Boolean> valueCallback) {
        HashMap map = new HashMap();
        map.put("chat_id", str2);
        map.put("conversation_id", str);
        map.put("tool_outputs", list);
        this.client.newCall(buildPostRequest("https://api.coze.cn/v3/chat/submit_tool_outputs", this.gson.toJson(map))).enqueue(new Callback() { // from class: com.aivox.app.util.agent.CozeManager.7
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                valueCallback.onResult(false);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String strString = response.body().string();
                    Log.d(CozeManager.this.TAG, "[Coze] 📤 Submitting Tool Outputs: " + strString);
                    CozeResponse cozeResponse = (CozeResponse) CozeManager.this.gson.fromJson(strString, new TypeToken<CozeResponse<Object>>() { // from class: com.aivox.app.util.agent.CozeManager.7.1
                    }.getType());
                    valueCallback.onResult(Boolean.valueOf(cozeResponse != null && cozeResponse.getCode() == 0));
                } catch (Exception unused) {
                    valueCallback.onResult(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadImageToCoze(Bitmap bitmap, final ValueCallback<String> valueCallback) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        this.client.newCall(new Request.Builder().url("https://api.coze.cn/v1/files/upload").headers(Headers.m1925of(getHeaders())).header("Authorization", "Bearer " + this.apiKey).post(new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", "glass_cam.jpg", RequestBody.create(MediaType.parse("image/jpeg"), byteArrayOutputStream.toByteArray())).build()).build()).enqueue(new Callback() { // from class: com.aivox.app.util.agent.CozeManager.8
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                valueCallback.onResult(null);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String strString = response.body().string();
                    Log.d(CozeManager.this.TAG, "[Coze] 📤 Uploading Image: " + strString);
                    CozeResponse cozeResponse = (CozeResponse) CozeManager.this.gson.fromJson(strString, new TypeToken<CozeResponse<FileData>>() { // from class: com.aivox.app.util.agent.CozeManager.8.1
                    }.getType());
                    if (cozeResponse != null && cozeResponse.getCode() == 0 && cozeResponse.getData() != null) {
                        valueCallback.onResult(((FileData) cozeResponse.getData()).getId());
                    } else {
                        valueCallback.onResult(null);
                    }
                } catch (Exception unused) {
                    valueCallback.onResult(null);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchBotReply(String str, String str2, final ChatCallback chatCallback) {
        this.client.newCall(new Request.Builder().url("https://api.coze.cn/v3/chat/message/list?chat_id=" + str2 + "&conversation_id=" + str).headers(Headers.m1925of(getHeaders())).get().build()).enqueue(new Callback() { // from class: com.aivox.app.util.agent.CozeManager.9
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                chatCallback.onError(iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                String content;
                try {
                    CozeResponse cozeResponse = (CozeResponse) CozeManager.this.gson.fromJson(response.body().string(), new TypeToken<CozeResponse<List<MessageData>>>() { // from class: com.aivox.app.util.agent.CozeManager.9.1
                    }.getType());
                    if (cozeResponse != null && cozeResponse.getData() != null) {
                        int size = ((List) cozeResponse.getData()).size() - 1;
                        while (true) {
                            if (size < 0) {
                                content = null;
                                break;
                            }
                            MessageData messageData = (MessageData) ((List) cozeResponse.getData()).get(size);
                            if ("assistant".equals(messageData.getRole()) && "answer".equals(messageData.getType())) {
                                content = messageData.getContent();
                                break;
                            }
                            size--;
                        }
                        chatCallback.onSuccess(content);
                        return;
                    }
                    chatCallback.onError(new Exception("Parse Error or No Data"));
                } catch (Exception e) {
                    chatCallback.onError(e);
                }
            }
        });
    }

    private Request buildPostRequest(String str, String str2) {
        return new Request.Builder().url(str).headers(Headers.m1925of(getHeaders())).post(createJsonBody(str2)).build();
    }
}
