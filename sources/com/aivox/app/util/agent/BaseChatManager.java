package com.aivox.app.util.agent;

import android.os.Handler;
import android.os.Looper;
import com.aivox.app.util.agent.GlassDelegate;
import com.google.gson.Gson;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseChatManager<D extends GlassDelegate> {
    protected D delegate;
    protected Long sessionId;
    protected final String TAG = getClass().getSimpleName();
    protected final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build();
    protected final Gson gson = new Gson();
    protected final Handler mainHandler = new Handler(Looper.getMainLooper());

    public abstract void chat(String str, ChatCallback chatCallback);

    protected BaseChatManager() {
    }

    public void setSessionId(Long l) {
        this.sessionId = l;
    }

    public Long getSessionId() {
        return this.sessionId;
    }

    public void setDelegate(D d) {
        this.delegate = d;
    }

    protected void postSuccess(final ChatCallback chatCallback, final String str) {
        if (chatCallback == null) {
            return;
        }
        this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.agent.BaseChatManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                chatCallback.onSuccess(str);
            }
        });
    }

    protected void postError(final ChatCallback chatCallback, final Throwable th) {
        if (chatCallback == null) {
            return;
        }
        this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.agent.BaseChatManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                chatCallback.onError(th);
            }
        });
    }

    protected RequestBody createJsonBody(String str) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
    }
}
