package com.aivox.app.util;

import android.util.Base64;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.util.LogUtil;
import com.aivox.common.model.DataHandle;
import com.luck.picture.lib.config.PictureMimeType;
import com.microsoft.azure.storage.queue.QueueConstants;
import com.microsoft.azure.storage.table.TableConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class MultiAiClient {
    private static final String CONTENT_TYPE_IMAGE_BASE64 = "image_base64";
    private static final String CONTENT_TYPE_IMAGE_URL = "image_url";
    private static final String CONTENT_TYPE_TEXT = "text";
    public static final String EVENT_DONE = "done";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_MESSAGE = "message";
    private static volatile MultiAiClient instance;
    private final String TAG = getClass().getSimpleName();
    private final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(90, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS).build();

    private MultiAiClient() {
    }

    public static MultiAiClient getInstance() {
        if (instance == null) {
            synchronized (MultiAiClient.class) {
                if (instance == null) {
                    instance = new MultiAiClient();
                }
            }
        }
        return instance;
    }

    public void streamChatCompletion(final long j, List<Message> list, final Consumer<SSEEvent> consumer) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("model", DataHandle.getIns().getAiConfig().getModel());
        jSONObject.put("stream", true);
        if (BaseGlobalConfig.isMainland()) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE, "disabled");
            jSONObject.put("thinking", jSONObject2);
        }
        JSONArray jSONArray = new JSONArray();
        for (Message message : list) {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("role", message.getRole());
            if (message.getContent() instanceof String) {
                jSONObject3.put("content", message.getContent());
            } else if (message.getContent() instanceof List) {
                JSONArray jSONArray2 = new JSONArray();
                List<ContentPart> list2 = (List) message.getContent();
                JSONObject jSONObject4 = new JSONObject();
                for (ContentPart contentPart : list2) {
                    if (CONTENT_TYPE_TEXT.equals(contentPart.getType())) {
                        jSONObject4.put(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE, CONTENT_TYPE_TEXT);
                        jSONObject4.put(CONTENT_TYPE_TEXT, contentPart.getData());
                    } else if (CONTENT_TYPE_IMAGE_URL.equals(contentPart.getType())) {
                        jSONObject4.put(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE, CONTENT_TYPE_IMAGE_URL);
                        JSONObject jSONObject5 = new JSONObject();
                        jSONObject5.put("url", contentPart.getData());
                        jSONObject4.put(CONTENT_TYPE_IMAGE_URL, jSONObject5);
                    } else if (CONTENT_TYPE_IMAGE_BASE64.equals(contentPart.getType())) {
                        jSONObject4.put(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE, CONTENT_TYPE_IMAGE_URL);
                        JSONObject jSONObject6 = new JSONObject();
                        jSONObject6.put("url", contentPart.getData());
                        jSONObject4.put(CONTENT_TYPE_IMAGE_URL, jSONObject6);
                    }
                    jSONArray2.put(jSONObject4);
                }
                jSONObject3.put("content", jSONArray2);
            }
            jSONArray.put(jSONObject3);
        }
        LogUtil.m335d(this.TAG, "requestBody: " + jSONArray);
        jSONObject.put(QueueConstants.MESSAGES, jSONArray);
        this.client.newCall(new Request.Builder().url(DataHandle.getIns().getAiConfig().getBaseUrl() + "/chat/completions").addHeader("Authorization", "Bearer " + DataHandle.getIns().getAiConfig().getApiKey()).addHeader("Content-Type", "application/json").post(RequestBody.create(MediaType.parse("application/json"), jSONObject.toString())).build()).enqueue(new Callback() { // from class: com.aivox.app.util.MultiAiClient.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                consumer.accept(new SSEEvent("error", iOException.getMessage(), null, j));
            }

            /* JADX WARN: Code restructure failed: missing block: B:26:0x0094, code lost:
            
                r2.accept(new com.aivox.app.util.MultiAiClient.SSEEvent(com.aivox.app.util.MultiAiClient.EVENT_DONE, null, null, r3));
             */
            @Override // okhttp3.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onResponse(okhttp3.Call r13, okhttp3.Response r14) throws java.io.IOException {
                /*
                    Method dump skipped, instruction units count: 295
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.aivox.app.util.MultiAiClient.C08381.onResponse(okhttp3.Call, okhttp3.Response):void");
            }
        });
    }

    public String bitmapToBase64(String str) {
        File file = new File(str);
        if (!file.exists() || !file.isFile() || file.length() == 0) {
            LogUtil.m337e(this.TAG, "Invalid image file: " + str);
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                if (file.length() > 10485760) {
                    LogUtil.m337e(this.TAG, "Image file too large, may cause OOM");
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[4096];
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String str2 = "image/jpeg";
                String lowerCase = file.getName().toLowerCase();
                if (lowerCase.endsWith(".png")) {
                    str2 = PictureMimeType.PNG_Q;
                } else if (lowerCase.endsWith(".gif")) {
                    str2 = "image/gif";
                } else if (lowerCase.endsWith(".webp")) {
                    str2 = "image/webp";
                }
                String str3 = "data:" + str2 + ";base64," + Base64.encodeToString(byteArray, 2);
                fileInputStream.close();
                return str3;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            LogUtil.m337e(this.TAG, "bitmapToBase64 error: " + e.getLocalizedMessage());
            return null;
        } catch (OutOfMemoryError e2) {
            LogUtil.m337e(this.TAG, "OOM while processing image: " + e2.getLocalizedMessage());
            System.gc();
            return null;
        }
    }

    public void shutdown() {
        this.client.dispatcher().executorService().shutdown();
        this.client.connectionPool().evictAll();
    }

    public static class SSEEvent {
        private final String data;
        private final String fullMessage;
        private final long sessionId;
        private final String type;

        public SSEEvent(String str, String str2, String str3, long j) {
            this.type = str;
            this.data = str2;
            this.fullMessage = str3;
            this.sessionId = j;
        }

        public String getType() {
            return this.type;
        }

        public String getData() {
            return this.data;
        }

        public String getFullMessage() {
            return this.fullMessage;
        }

        public long getSessionId() {
            return this.sessionId;
        }
    }

    public static class Message {
        private final Object content;
        private final String role;

        public Message(String str, String str2) {
            this.role = str;
            this.content = str2;
        }

        public Message(String str, List<ContentPart> list) {
            this.role = str;
            this.content = list;
        }

        public String getRole() {
            return this.role;
        }

        public Object getContent() {
            return this.content;
        }
    }

    public static class ContentPart {
        private final String data;
        private final String type;

        public ContentPart(String str, String str2) {
            this.type = str;
            this.data = str2;
        }

        public String getType() {
            return this.type;
        }

        public String getData() {
            return this.data;
        }

        public static ContentPart text(String str) {
            return new ContentPart(MultiAiClient.CONTENT_TYPE_TEXT, str);
        }

        public static ContentPart imageUrl(String str) {
            return new ContentPart(MultiAiClient.CONTENT_TYPE_IMAGE_URL, str);
        }

        public static ContentPart imageBase64(String str) {
            return new ContentPart(MultiAiClient.CONTENT_TYPE_IMAGE_BASE64, str);
        }
    }
}
