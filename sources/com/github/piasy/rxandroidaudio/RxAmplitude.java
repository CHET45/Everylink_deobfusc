package com.github.piasy.rxandroidaudio;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
public final class RxAmplitude {
    static final int AMPLITUDE_MAX_LEVEL = 8;
    static final int AMPLITUDE_MAX_VALUE = 16385;
    private static final int DEFAULT_AMPLITUDE_INTERVAL = 200;
    static final String TAG = "RxAmplitude";
    final Random mRandom = new Random(System.nanoTime());

    private RxAmplitude() {
    }

    public static Observable<Integer> from(AudioRecorder audioRecorder) {
        return from(audioRecorder, 200L);
    }

    public static Observable<Integer> from(AudioRecorder audioRecorder, long j) {
        return new RxAmplitude().start(audioRecorder, j);
    }

    private Observable<Integer> start(final AudioRecorder audioRecorder, long j) {
        return Observable.interval(j, TimeUnit.MILLISECONDS).map(new Function() { // from class: com.github.piasy.rxandroidaudio.RxAmplitude$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m2656lambda$start$0$comgithubpiasyrxandroidaudioRxAmplitude(audioRecorder, (Long) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$start$0$com-github-piasy-rxandroidaudio-RxAmplitude, reason: not valid java name */
    /* synthetic */ Integer m2656lambda$start$0$comgithubpiasyrxandroidaudioRxAmplitude(AudioRecorder audioRecorder, Long l) throws Exception {
        int iNextInt;
        try {
            iNextInt = audioRecorder.getMaxAmplitude();
        } catch (RuntimeException e) {
            Log.i(TAG, "getMaxAmplitude fail: " + e.getMessage());
            iNextInt = this.mRandom.nextInt(16385);
        }
        return Integer.valueOf(iNextInt / 2048);
    }
}
