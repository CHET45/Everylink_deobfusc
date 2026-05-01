package com.aivox.common.util.whisper;

import android.media.AudioRecord;
import android.util.Log;
import com.azure.core.util.tracing.Tracer;
import com.github.houbb.heaven.constant.FileOptionConst;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p036io.CloseableKt;
import kotlin.p036io.FilesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.ExecutorsKt;

/* JADX INFO: compiled from: Recorder.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0003\u001c\u001d\u001eB\u0005¢\u0006\u0002\u0010\u0002J0\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0016\u0010\u0013\u001a\u0012\u0012\b\u0012\u00060\u0015j\u0002`\u0016\u0012\u0004\u0012\u00020\u00100\u0014H\u0086@¢\u0006\u0002\u0010\u0017J&\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u001a2\u0016\u0010\u0013\u001a\u0012\u0012\b\u0012\u00060\u0015j\u0002`\u0016\u0012\u0004\u0012\u00020\u00100\u0014J\u0006\u0010\u001b\u001a\u00020\u0010R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, m1901d2 = {"Lcom/aivox/common/util/whisper/Recorder;", "", "()V", "audioStream", "Lcom/aivox/common/util/whisper/Recorder$AudioStreamThread;", "filePath", "", "getFilePath", "()Ljava/lang/String;", "setFilePath", "(Ljava/lang/String;)V", "recorder", "Lcom/aivox/common/util/whisper/Recorder$AudioRecordThread;", Tracer.SCOPE_KEY, "Lkotlinx/coroutines/CoroutineScope;", "startRecording", "", "outputFile", "Ljava/io/File;", "onError", "Lkotlin/Function1;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "(Ljava/io/File;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startStreaming", "onDataReceived", "Lcom/aivox/common/util/whisper/Recorder$AudioDataReceivedListener;", "stopRecording", "AudioDataReceivedListener", "AudioRecordThread", "AudioStreamThread", "common_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class Recorder {
    private AudioStreamThread audioStream;
    private String filePath;
    private AudioRecordThread recorder;
    private final CoroutineScope scope;

    /* JADX INFO: compiled from: Recorder.kt */
    @Metadata(m1900d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, m1901d2 = {"Lcom/aivox/common/util/whisper/Recorder$AudioDataReceivedListener;", "", "onAudioDataReceived", "", "data", "", "common_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public interface AudioDataReceivedListener {
        void onAudioDataReceived(byte[] data);
    }

    public Recorder() {
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Intrinsics.checkNotNullExpressionValue(executorServiceNewSingleThreadExecutor, "newSingleThreadExecutor(...)");
        this.scope = CoroutineScopeKt.CoroutineScope(ExecutorsKt.from(executorServiceNewSingleThreadExecutor));
        this.filePath = "";
    }

    public final String getFilePath() {
        return this.filePath;
    }

    public final void setFilePath(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.filePath = str;
    }

    /* JADX INFO: renamed from: com.aivox.common.util.whisper.Recorder$startRecording$2 */
    /* JADX INFO: compiled from: Recorder.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.aivox.common.util.whisper.Recorder$startRecording$2", m1911f = "Recorder.kt", m1912i = {}, m1913l = {}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C10032 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1<Exception, Unit> $onError;
        final /* synthetic */ File $outputFile;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C10032(File file, Function1<? super Exception, Unit> function1, Continuation<? super C10032> continuation) {
            super(2, continuation);
            this.$outputFile = file;
            this.$onError = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return Recorder.this.new C10032(this.$outputFile, this.$onError, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10032) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Recorder.this.recorder = new AudioRecordThread(this.$outputFile, this.$onError);
            AudioRecordThread audioRecordThread = Recorder.this.recorder;
            if (audioRecordThread == null) {
                return null;
            }
            audioRecordThread.start();
            return Unit.INSTANCE;
        }
    }

    public final Object startRecording(File file, Function1<? super Exception, Unit> function1, Continuation<? super Unit> continuation) {
        return BuildersKt.withContext(this.scope.getCoroutineContext(), new C10032(file, function1, null), continuation);
    }

    public final void startStreaming(AudioDataReceivedListener onDataReceived, Function1<? super Exception, Unit> onError) {
        Intrinsics.checkNotNullParameter(onDataReceived, "onDataReceived");
        Intrinsics.checkNotNullParameter(onError, "onError");
        if (this.audioStream == null) {
            AudioStreamThread audioStreamThread = new AudioStreamThread(onDataReceived, onError, this.filePath);
            this.audioStream = audioStreamThread;
            audioStreamThread.start();
            return;
        }
        Log.i("Recorder whisper", "AudioStreamThread is already running");
    }

    public final void stopRecording() throws InterruptedException {
        AudioRecordThread audioRecordThread = this.recorder;
        if (audioRecordThread != null) {
            audioRecordThread.stopRecording();
        }
        AudioStreamThread audioStreamThread = this.audioStream;
        if (audioStreamThread != null) {
            audioStreamThread.stopRecording();
        }
        BuildersKt__BuildersKt.runBlocking$default(null, new C10041(null), 1, null);
    }

    /* JADX INFO: renamed from: com.aivox.common.util.whisper.Recorder$stopRecording$1 */
    /* JADX INFO: compiled from: Recorder.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.aivox.common.util.whisper.Recorder$stopRecording$1", m1911f = "Recorder.kt", m1912i = {}, m1913l = {}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C10041 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C10041(Continuation<? super C10041> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return Recorder.this.new C10041(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10041) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                AudioStreamThread audioStreamThread = Recorder.this.audioStream;
                if (audioStreamThread != null) {
                    audioStreamThread.join();
                }
                Recorder.this.audioStream = null;
                AudioRecordThread audioRecordThread = Recorder.this.recorder;
                if (audioRecordThread != null) {
                    audioRecordThread.join();
                }
                Recorder.this.recorder = null;
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: compiled from: Recorder.kt */
    @Metadata(m1900d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\u0010\tJ\b\u0010\f\u001a\u00020\bH\u0017J\u0006\u0010\r\u001a\u00020\bR\u001e\u0010\u0004\u001a\u0012\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, m1901d2 = {"Lcom/aivox/common/util/whisper/Recorder$AudioRecordThread;", "Ljava/lang/Thread;", "outputFile", "Ljava/io/File;", "onError", "Lkotlin/Function1;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "", "(Ljava/io/File;Lkotlin/jvm/functions/Function1;)V", "quit", "Ljava/util/concurrent/atomic/AtomicBoolean;", "run", "stopRecording", "common_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    private static final class AudioRecordThread extends Thread {
        private final Function1<Exception, Unit> onError;
        private final File outputFile;
        private AtomicBoolean quit;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AudioRecordThread(File outputFile, Function1<? super Exception, Unit> onError) {
            super("AudioRecorder");
            Intrinsics.checkNotNullParameter(outputFile, "outputFile");
            Intrinsics.checkNotNullParameter(onError, "onError");
            this.outputFile = outputFile;
            this.onError = onError;
            this.quit = new AtomicBoolean(false);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                int minBufferSize = AudioRecord.getMinBufferSize(16000, 16, 2) * 4;
                int i = minBufferSize / 2;
                short[] sArr = new short[i];
                AudioRecord audioRecord = new AudioRecord(1, 16000, 16, 2, minBufferSize);
                try {
                    audioRecord.startRecording();
                    ArrayList arrayList = new ArrayList();
                    while (!this.quit.get()) {
                        int i2 = audioRecord.read(sArr, 0, i);
                        if (i2 <= 0) {
                            throw new RuntimeException("audioRecord.read returned " + i2);
                        }
                        for (int i3 = 0; i3 < i2; i3++) {
                            arrayList.add(Short.valueOf(sArr[i3]));
                        }
                    }
                    audioRecord.stop();
                    RiffWaveHelperKt.encodeWaveFile(this.outputFile, CollectionsKt.toShortArray(arrayList));
                    audioRecord.release();
                } catch (Throwable th) {
                    audioRecord.release();
                    throw th;
                }
            } catch (Exception e) {
                this.onError.invoke(e);
            }
        }

        public final void stopRecording() {
            this.quit.set(true);
        }
    }

    /* JADX INFO: compiled from: Recorder.kt */
    @Metadata(m1900d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\u0010\u001a\u00020\bH\u0017J\u0006\u0010\u0011\u001a\u00020\bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0004\u001a\u0012\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, m1901d2 = {"Lcom/aivox/common/util/whisper/Recorder$AudioStreamThread;", "Ljava/lang/Thread;", "onDataReceived", "Lcom/aivox/common/util/whisper/Recorder$AudioDataReceivedListener;", "onError", "Lkotlin/Function1;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "", "filePath", "", "(Lcom/aivox/common/util/whisper/Recorder$AudioDataReceivedListener;Lkotlin/jvm/functions/Function1;Ljava/lang/String;)V", "file", "Ljava/io/File;", "quit", "Ljava/util/concurrent/atomic/AtomicBoolean;", "run", "stopRecording", "common_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    private static final class AudioStreamThread extends Thread {
        private final File file;
        private final String filePath;
        private final AudioDataReceivedListener onDataReceived;
        private final Function1<Exception, Unit> onError;
        private final AtomicBoolean quit;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AudioStreamThread(AudioDataReceivedListener onDataReceived, Function1<? super Exception, Unit> onError, String filePath) {
            super("AudioStreamer");
            Intrinsics.checkNotNullParameter(onDataReceived, "onDataReceived");
            Intrinsics.checkNotNullParameter(onError, "onError");
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            this.onDataReceived = onDataReceived;
            this.onError = onError;
            this.filePath = filePath;
            this.quit = new AtomicBoolean(false);
            this.file = new File(filePath);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws IOException {
            int minBufferSize = AudioRecord.getMinBufferSize(16000, 16, 2) * 4;
            int i = minBufferSize / 2;
            byte[] bArr = new byte[i];
            AudioRecord audioRecord = new AudioRecord(1, 16000, 16, 2, minBufferSize);
            if (!this.file.exists()) {
                this.file.createNewFile();
                FilesKt.writeBytes(this.file, new byte[44]);
            }
            try {
                if (audioRecord.getState() != 1) {
                    Log.e("Recorder whisper", "AudioRecord initialization failed");
                    return;
                }
                try {
                    audioRecord.startRecording();
                    FileOutputStream fileOutputStream = new FileOutputStream(this.file, true);
                    while (!this.quit.get()) {
                        int i2 = audioRecord.read(bArr, 0, i);
                        if (i2 > 0) {
                            this.onDataReceived.onAudioDataReceived(bArr);
                            fileOutputStream.write(bArr);
                        } else {
                            throw new RuntimeException("audioRecord.read returned " + i2);
                        }
                    }
                    RandomAccessFile randomAccessFile = new RandomAccessFile(this.file, FileOptionConst.READ_WRITE);
                    try {
                        randomAccessFile.write(RiffWaveHelperKt.headerBytes((int) this.file.length()), 0, 44);
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(randomAccessFile, null);
                    } finally {
                    }
                } catch (Exception e) {
                    this.onError.invoke(e);
                }
            } finally {
                audioRecord.release();
            }
        }

        public final void stopRecording() {
            this.quit.set(true);
        }
    }
}
