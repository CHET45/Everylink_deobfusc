package com.github.piasy.rxandroidaudio;

import android.media.MediaPlayer;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
public final class RxAudioPlayer {
    private static final String TAG = "RxAudioPlayer";
    private MediaPlayer mPlayer;

    private RxAudioPlayer() {
    }

    public static RxAudioPlayer getInstance() {
        return RxAudioPlayerHolder.INSTANCE;
    }

    private MediaPlayer create(PlayConfig playConfig) throws IOException {
        stopPlay();
        int i = playConfig.mType;
        if (i == 1) {
            Log.d(TAG, "MediaPlayer to start play file: " + playConfig.mAudioFile.getName());
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(playConfig.mAudioFile.getAbsolutePath());
            return mediaPlayer;
        }
        if (i == 2) {
            Log.d(TAG, "MediaPlayer to start play: " + playConfig.mAudioResource);
            return MediaPlayer.create(playConfig.mContext, playConfig.mAudioResource);
        }
        if (i == 3) {
            Log.d(TAG, "MediaPlayer to start play: " + playConfig.mUrl);
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            mediaPlayer2.setDataSource(playConfig.mUrl);
            return mediaPlayer2;
        }
        if (i == 4) {
            Log.d(TAG, "MediaPlayer to start play uri: " + playConfig.mUri);
            MediaPlayer mediaPlayer3 = new MediaPlayer();
            mediaPlayer3.setDataSource(playConfig.mContext, playConfig.mUri);
            return mediaPlayer3;
        }
        throw new IllegalArgumentException("Unknown type: " + playConfig.mType);
    }

    public Observable<Boolean> play(final PlayConfig playConfig) {
        if (!playConfig.isArgumentValid()) {
            return Observable.error(new IllegalArgumentException(""));
        }
        return Observable.create(new ObservableOnSubscribe() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f$0.m2659lambda$play$0$comgithubpiasyrxandroidaudioRxAudioPlayer(playConfig, observableEmitter);
            }
        }).doOnError(new Consumer() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2660lambda$play$1$comgithubpiasyrxandroidaudioRxAudioPlayer((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$play$0$com-github-piasy-rxandroidaudio-RxAudioPlayer, reason: not valid java name */
    /* synthetic */ void m2659lambda$play$0$comgithubpiasyrxandroidaudioRxAudioPlayer(PlayConfig playConfig, ObservableEmitter observableEmitter) throws Exception {
        MediaPlayer mediaPlayerCreate = create(playConfig);
        setMediaPlayerListener(mediaPlayerCreate, observableEmitter);
        mediaPlayerCreate.setVolume(playConfig.mLeftVolume, playConfig.mRightVolume);
        mediaPlayerCreate.setAudioStreamType(playConfig.mStreamType);
        mediaPlayerCreate.setLooping(playConfig.mLooping);
        if (playConfig.needPrepare()) {
            mediaPlayerCreate.prepare();
        }
        mediaPlayerCreate.start();
        this.mPlayer = mediaPlayerCreate;
        observableEmitter.onNext(true);
    }

    /* JADX INFO: renamed from: lambda$play$1$com-github-piasy-rxandroidaudio-RxAudioPlayer, reason: not valid java name */
    /* synthetic */ void m2660lambda$play$1$comgithubpiasyrxandroidaudioRxAudioPlayer(Throwable th) throws Exception {
        stopPlay();
    }

    public Observable<Boolean> prepare(final PlayConfig playConfig) {
        if (!playConfig.isArgumentValid() || !playConfig.isLocalSource()) {
            return Observable.error(new IllegalArgumentException(""));
        }
        return Observable.create(new ObservableOnSubscribe() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda4
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f$0.m2661lambda$prepare$2$comgithubpiasyrxandroidaudioRxAudioPlayer(playConfig, observableEmitter);
            }
        }).doOnError(new Consumer() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2662lambda$prepare$3$comgithubpiasyrxandroidaudioRxAudioPlayer((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$prepare$2$com-github-piasy-rxandroidaudio-RxAudioPlayer, reason: not valid java name */
    /* synthetic */ void m2661lambda$prepare$2$comgithubpiasyrxandroidaudioRxAudioPlayer(PlayConfig playConfig, ObservableEmitter observableEmitter) throws Exception {
        MediaPlayer mediaPlayerCreate = create(playConfig);
        setMediaPlayerListener(mediaPlayerCreate, observableEmitter);
        mediaPlayerCreate.setVolume(playConfig.mLeftVolume, playConfig.mRightVolume);
        mediaPlayerCreate.setAudioStreamType(playConfig.mStreamType);
        mediaPlayerCreate.setLooping(playConfig.mLooping);
        if (playConfig.needPrepare()) {
            mediaPlayerCreate.prepare();
        }
        this.mPlayer = mediaPlayerCreate;
        observableEmitter.onNext(true);
    }

    /* JADX INFO: renamed from: lambda$prepare$3$com-github-piasy-rxandroidaudio-RxAudioPlayer, reason: not valid java name */
    /* synthetic */ void m2662lambda$prepare$3$comgithubpiasyrxandroidaudioRxAudioPlayer(Throwable th) throws Exception {
        stopPlay();
    }

    public void pause() {
        this.mPlayer.pause();
    }

    public void resume() {
        this.mPlayer.start();
    }

    public boolean playNonRxy(PlayConfig playConfig, MediaPlayer.OnCompletionListener onCompletionListener, MediaPlayer.OnErrorListener onErrorListener) {
        if (!playConfig.isArgumentValid()) {
            return false;
        }
        try {
            MediaPlayer mediaPlayerCreate = create(playConfig);
            setMediaPlayerListener(mediaPlayerCreate, onCompletionListener, onErrorListener);
            mediaPlayerCreate.setVolume(playConfig.mLeftVolume, playConfig.mRightVolume);
            mediaPlayerCreate.setAudioStreamType(playConfig.mStreamType);
            mediaPlayerCreate.setLooping(playConfig.mLooping);
            if (playConfig.needPrepare()) {
                mediaPlayerCreate.prepare();
            }
            mediaPlayerCreate.start();
            this.mPlayer = mediaPlayerCreate;
            return true;
        } catch (IOException | RuntimeException e) {
            Log.w(TAG, "startPlay fail, IllegalArgumentException: " + e.getMessage());
            stopPlay();
            return false;
        }
    }

    public synchronized boolean stopPlay() {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null) {
            return false;
        }
        mediaPlayer.setOnCompletionListener(null);
        this.mPlayer.setOnErrorListener(null);
        try {
            this.mPlayer.stop();
            this.mPlayer.reset();
            this.mPlayer.release();
        } catch (IllegalStateException e) {
            Log.w(TAG, "stopPlay fail, IllegalStateException: " + e.getMessage());
        }
        this.mPlayer = null;
        return true;
    }

    public int progress() {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition() / 1000;
        }
        return 0;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mPlayer;
    }

    private void setMediaPlayerListener(MediaPlayer mediaPlayer, final ObservableEmitter<Boolean> observableEmitter) {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda10
            @Override // android.media.MediaPlayer.OnCompletionListener
            public final void onCompletion(MediaPlayer mediaPlayer2) {
                this.f$0.m538xa883b94f(observableEmitter, mediaPlayer2);
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda11
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer2, int i, int i2) {
                return this.f$0.m539x1dfddf90(observableEmitter, mediaPlayer2, i, i2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setMediaPlayerListener$5$com-github-piasy-rxandroidaudio-RxAudioPlayer */
    /* synthetic */ void m538xa883b94f(final ObservableEmitter observableEmitter, MediaPlayer mediaPlayer) {
        Log.d(TAG, "OnCompletionListener::onCompletion");
        Observable<Long> observableTimer = Observable.timer(50L, TimeUnit.MILLISECONDS);
        Consumer<? super Long> consumer = new Consumer() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2657lambda$null$4$comgithubpiasyrxandroidaudioRxAudioPlayer(observableEmitter, (Long) obj);
            }
        };
        observableEmitter.getClass();
        observableTimer.subscribe(consumer, new Consumer() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                observableEmitter.onError((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$null$4$com-github-piasy-rxandroidaudio-RxAudioPlayer, reason: not valid java name */
    /* synthetic */ void m2657lambda$null$4$comgithubpiasyrxandroidaudioRxAudioPlayer(ObservableEmitter observableEmitter, Long l) throws Exception {
        stopPlay();
        observableEmitter.onComplete();
    }

    /* JADX INFO: renamed from: lambda$setMediaPlayerListener$6$com-github-piasy-rxandroidaudio-RxAudioPlayer */
    /* synthetic */ boolean m539x1dfddf90(ObservableEmitter observableEmitter, MediaPlayer mediaPlayer, int i, int i2) {
        Log.d(TAG, "OnErrorListener::onError" + i + ", " + i2);
        observableEmitter.onError(new Throwable("Player error: " + i + ", " + i2));
        stopPlay();
        return true;
    }

    private void setMediaPlayerListener(MediaPlayer mediaPlayer, final MediaPlayer.OnCompletionListener onCompletionListener, final MediaPlayer.OnErrorListener onErrorListener) {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda1
            @Override // android.media.MediaPlayer.OnCompletionListener
            public final void onCompletion(MediaPlayer mediaPlayer2) {
                this.f$0.m540x7e6c5253(onCompletionListener, mediaPlayer2);
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda2
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer2, int i, int i2) {
                return this.f$0.m537xab8a0e19(onErrorListener, mediaPlayer2, i, i2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setMediaPlayerListener$9$com-github-piasy-rxandroidaudio-RxAudioPlayer */
    /* synthetic */ void m540x7e6c5253(final MediaPlayer.OnCompletionListener onCompletionListener, final MediaPlayer mediaPlayer) {
        Log.d(TAG, "OnCompletionListener::onCompletion");
        Observable.timer(50L, TimeUnit.MILLISECONDS).subscribe(new Consumer() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2658lambda$null$7$comgithubpiasyrxandroidaudioRxAudioPlayer(onCompletionListener, mediaPlayer, (Long) obj);
            }
        }, new Consumer() { // from class: com.github.piasy.rxandroidaudio.RxAudioPlayer$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Log.d(RxAudioPlayer.TAG, "OnCompletionListener::onError, " + ((Throwable) obj).getMessage());
            }
        });
    }

    /* JADX INFO: renamed from: lambda$null$7$com-github-piasy-rxandroidaudio-RxAudioPlayer, reason: not valid java name */
    /* synthetic */ void m2658lambda$null$7$comgithubpiasyrxandroidaudioRxAudioPlayer(MediaPlayer.OnCompletionListener onCompletionListener, MediaPlayer mediaPlayer, Long l) throws Exception {
        stopPlay();
        onCompletionListener.onCompletion(mediaPlayer);
    }

    /* JADX INFO: renamed from: lambda$setMediaPlayerListener$10$com-github-piasy-rxandroidaudio-RxAudioPlayer */
    /* synthetic */ boolean m537xab8a0e19(MediaPlayer.OnErrorListener onErrorListener, MediaPlayer mediaPlayer, int i, int i2) {
        Log.d(TAG, "OnErrorListener::onError" + i + ", " + i2);
        onErrorListener.onError(mediaPlayer, i, i2);
        stopPlay();
        return true;
    }

    private static class RxAudioPlayerHolder {
        private static final RxAudioPlayer INSTANCE = new RxAudioPlayer();

        private RxAudioPlayerHolder() {
        }
    }
}
