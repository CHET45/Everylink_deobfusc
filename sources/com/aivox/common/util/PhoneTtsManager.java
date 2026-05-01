package com.aivox.common.util;

import com.aivox.base.common.MyEnum;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.LogUtil;
import com.microsoft.cognitiveservices.speech.Connection;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisOutputFormat;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class PhoneTtsManager {
    private static final String TAG = "PhoneTtsManager";
    private Connection connection;
    private ExecutorService executor;
    private SpeechConfig speechConfig;
    private SpeechSynthesizer synthesizer;
    private final String speechSubscriptionKey = "10aa9904c17e47ec8fd3320f00b54661";
    private final String serviceRegion = "eastus";

    public PhoneTtsManager(int i) {
        LogUtil.m337e(TAG, "init: " + i);
        try {
            this.executor = Executors.newSingleThreadExecutor();
            SpeechConfig speechConfigFromSubscription = SpeechConfig.fromSubscription("10aa9904c17e47ec8fd3320f00b54661", "eastus");
            this.speechConfig = speechConfigFromSubscription;
            speechConfigFromSubscription.setSpeechSynthesisLanguage(MyEnum.TTS_LANGUAGE_TYPE.getTtsLanguageName(MyEnum.TRANSLATE_LANGUAGE.getLanguage(i).name));
            this.speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Raw16Khz16BitMonoPcm);
            SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(this.speechConfig);
            this.synthesizer = speechSynthesizer;
            Connection connectionFromSpeechSynthesizer = Connection.fromSpeechSynthesizer(speechSynthesizer);
            this.connection = connectionFromSpeechSynthesizer;
            connectionFromSpeechSynthesizer.openConnection(true);
            LogUtil.m339i(TAG, "TTS initialized with persistent connection.");
        } catch (Exception e) {
            LogUtil.m337e(TAG, "Init Error: " + e.getMessage());
        }
    }

    public void speak(final String str) {
        LogUtil.m337e(TAG, "speak: " + str);
        if (BaseStringUtil.isEmpty(str)) {
            return;
        }
        this.executor.submit(new Runnable() { // from class: com.aivox.common.util.PhoneTtsManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2474lambda$speak$0$comaivoxcommonutilPhoneTtsManager(str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$speak$0$com-aivox-common-util-PhoneTtsManager, reason: not valid java name */
    /* synthetic */ void m2474lambda$speak$0$comaivoxcommonutilPhoneTtsManager(String str) {
        this.synthesizer.SpeakText(str);
    }

    public void stopAll() {
        SpeechSynthesizer speechSynthesizer = this.synthesizer;
        if (speechSynthesizer != null) {
            speechSynthesizer.StopSpeakingAsync();
        }
    }

    public void releaseAll() {
        SpeechConfig speechConfig = this.speechConfig;
        if (speechConfig != null) {
            speechConfig.close();
            this.speechConfig = null;
        }
        SpeechSynthesizer speechSynthesizer = this.synthesizer;
        if (speechSynthesizer != null) {
            speechSynthesizer.StopSpeakingAsync();
            this.synthesizer.close();
            this.synthesizer = null;
        }
        ExecutorService executorService = this.executor;
        if (executorService != null) {
            executorService.shutdownNow();
            this.executor = null;
        }
        Connection connection = this.connection;
        if (connection != null) {
            connection.close();
            this.connection = null;
        }
    }
}
