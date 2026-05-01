package com.whispercpp.whisper;

import android.content.res.AssetManager;
import com.azure.core.util.tracing.Tracer;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.ExecutorsKt;

/* JADX INFO: compiled from: LibWhisper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(m1900d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000bJ\b\u0010\r\u001a\u00020\u000eH\u0004J\u0011\u0010\u000f\u001a\u00020\u000eH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0010J-\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\b2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, m1901d2 = {"Lcom/whispercpp/whisper/WhisperContext;", "", "ptr", "", "(J)V", Tracer.SCOPE_KEY, "Lkotlinx/coroutines/CoroutineScope;", "benchGgmlMulMat", "", "nthreads", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "benchMemory", "finalize", "", "release", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "transcribeData", "data", "", "language", "printTimestamp", "", "([FLjava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "lib_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class WhisperContext {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private long ptr;
    private final CoroutineScope scope;

    public /* synthetic */ WhisperContext(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    private WhisperContext(long j) {
        this.ptr = j;
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Intrinsics.checkNotNullExpressionValue(executorServiceNewSingleThreadExecutor, "newSingleThreadExecutor(...)");
        this.scope = CoroutineScopeKt.CoroutineScope(ExecutorsKt.from(executorServiceNewSingleThreadExecutor));
    }

    public static /* synthetic */ Object transcribeData$default(WhisperContext whisperContext, float[] fArr, String str, boolean z, Continuation continuation, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "en";
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return whisperContext.transcribeData(fArr, str, z, continuation);
    }

    /* JADX INFO: renamed from: com.whispercpp.whisper.WhisperContext$transcribeData$2 */
    /* JADX INFO: compiled from: LibWhisper.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.whispercpp.whisper.WhisperContext$transcribeData$2", m1911f = "LibWhisper.kt", m1912i = {}, m1913l = {}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C45492 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
        final /* synthetic */ float[] $data;
        final /* synthetic */ String $language;
        final /* synthetic */ boolean $printTimestamp;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C45492(String str, float[] fArr, boolean z, Continuation<? super C45492> continuation) {
            super(2, continuation);
            this.$language = str;
            this.$data = fArr;
            this.$printTimestamp = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WhisperContext.this.new C45492(this.$language, this.$data, this.$printTimestamp, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
            return ((C45492) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (WhisperContext.this.ptr == 0) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            WhisperLib.INSTANCE.fullTranscribe(WhisperContext.this.ptr, WhisperCpuConfig.INSTANCE.getPreferredThreadCount(), this.$language, this.$data);
            int textSegmentCount = WhisperLib.INSTANCE.getTextSegmentCount(WhisperContext.this.ptr);
            boolean z = this.$printTimestamp;
            WhisperContext whisperContext = WhisperContext.this;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < textSegmentCount; i++) {
                if (!z) {
                    sb.append(WhisperLib.INSTANCE.getTextSegment(whisperContext.ptr, i));
                } else {
                    sb.append(("[" + LibWhisperKt.toTimestamp$default(WhisperLib.INSTANCE.getTextSegmentT0(whisperContext.ptr, i), false, 2, null) + " --> " + LibWhisperKt.toTimestamp$default(WhisperLib.INSTANCE.getTextSegmentT1(whisperContext.ptr, i), false, 2, null) + "]") + ": " + WhisperLib.INSTANCE.getTextSegment(whisperContext.ptr, i) + "\n");
                }
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
            return string;
        }
    }

    public final Object transcribeData(float[] fArr, String str, boolean z, Continuation<? super String> continuation) {
        return BuildersKt.withContext(this.scope.getCoroutineContext(), new C45492(str, fArr, z, null), continuation);
    }

    /* JADX INFO: renamed from: com.whispercpp.whisper.WhisperContext$benchMemory$2 */
    /* JADX INFO: compiled from: LibWhisper.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.whispercpp.whisper.WhisperContext$benchMemory$2", m1911f = "LibWhisper.kt", m1912i = {}, m1913l = {}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C45462 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
        final /* synthetic */ int $nthreads;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C45462(int i, Continuation<? super C45462> continuation) {
            super(2, continuation);
            this.$nthreads = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C45462(this.$nthreads, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
            return ((C45462) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return WhisperLib.INSTANCE.benchMemcpy(this.$nthreads);
        }
    }

    public final Object benchMemory(int i, Continuation<? super String> continuation) {
        return BuildersKt.withContext(this.scope.getCoroutineContext(), new C45462(i, null), continuation);
    }

    /* JADX INFO: renamed from: com.whispercpp.whisper.WhisperContext$benchGgmlMulMat$2 */
    /* JADX INFO: compiled from: LibWhisper.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.whispercpp.whisper.WhisperContext$benchGgmlMulMat$2", m1911f = "LibWhisper.kt", m1912i = {}, m1913l = {}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C45452 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
        final /* synthetic */ int $nthreads;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C45452(int i, Continuation<? super C45452> continuation) {
            super(2, continuation);
            this.$nthreads = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C45452(this.$nthreads, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
            return ((C45452) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return WhisperLib.INSTANCE.benchGgmlMulMat(this.$nthreads);
        }
    }

    public final Object benchGgmlMulMat(int i, Continuation<? super String> continuation) {
        return BuildersKt.withContext(this.scope.getCoroutineContext(), new C45452(i, null), continuation);
    }

    /* JADX INFO: renamed from: com.whispercpp.whisper.WhisperContext$release$2 */
    /* JADX INFO: compiled from: LibWhisper.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.whispercpp.whisper.WhisperContext$release$2", m1911f = "LibWhisper.kt", m1912i = {}, m1913l = {}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C45482 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C45482(Continuation<? super C45482> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WhisperContext.this.new C45482(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C45482) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (WhisperContext.this.ptr != 0) {
                WhisperLib.INSTANCE.freeContext(WhisperContext.this.ptr);
                WhisperContext.this.ptr = 0L;
            }
            return Unit.INSTANCE;
        }
    }

    public final Object release(Continuation<? super Unit> continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.scope.getCoroutineContext(), new C45482(null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.whispercpp.whisper.WhisperContext$finalize$1 */
    /* JADX INFO: compiled from: LibWhisper.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.whispercpp.whisper.WhisperContext$finalize$1", m1911f = "LibWhisper.kt", m1912i = {}, m1913l = {66}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C45471 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C45471(Continuation<? super C45471> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WhisperContext.this.new C45471(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C45471) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (WhisperContext.this.release(this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    protected final void finalize() throws InterruptedException {
        BuildersKt__BuildersKt.runBlocking$default(null, new C45471(null), 1, null);
    }

    /* JADX INFO: compiled from: LibWhisper.kt */
    @Metadata(m1900d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\bJ\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\b¨\u0006\u000f"}, m1901d2 = {"Lcom/whispercpp/whisper/WhisperContext$Companion;", "", "()V", "createContextFromAsset", "Lcom/whispercpp/whisper/WhisperContext;", "assetManager", "Landroid/content/res/AssetManager;", "assetPath", "", "createContextFromFile", "filePath", "createContextFromInputStream", "stream", "Ljava/io/InputStream;", "getSystemInfo", "lib_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final WhisperContext createContextFromFile(String filePath) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            long jInitContext = WhisperLib.INSTANCE.initContext(filePath);
            if (jInitContext == 0) {
                throw new RuntimeException("Couldn't create context with path " + filePath);
            }
            return new WhisperContext(jInitContext, null);
        }

        public final WhisperContext createContextFromInputStream(InputStream stream) {
            Intrinsics.checkNotNullParameter(stream, "stream");
            long jInitContextFromInputStream = WhisperLib.INSTANCE.initContextFromInputStream(stream);
            if (jInitContextFromInputStream == 0) {
                throw new RuntimeException("Couldn't create context from input stream");
            }
            return new WhisperContext(jInitContextFromInputStream, null);
        }

        public final WhisperContext createContextFromAsset(AssetManager assetManager, String assetPath) {
            Intrinsics.checkNotNullParameter(assetManager, "assetManager");
            Intrinsics.checkNotNullParameter(assetPath, "assetPath");
            long jInitContextFromAsset = WhisperLib.INSTANCE.initContextFromAsset(assetManager, assetPath);
            if (jInitContextFromAsset == 0) {
                throw new RuntimeException("Couldn't create context from asset " + assetPath);
            }
            return new WhisperContext(jInitContextFromAsset, null);
        }

        public final String getSystemInfo() {
            return WhisperLib.INSTANCE.getSystemInfo();
        }
    }
}
