package com.aivox.common.util.whisper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.aivox.base.C0874R;
import com.aivox.common.speech2text.ICommonTransCallback;
import com.aivox.common.util.whisper.Recorder;
import com.azure.core.util.tracing.Tracer;
import com.microsoft.azure.storage.table.TableConstants;
import com.whispercpp.whisper.WhisperContext;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: WhisperService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001QB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u00103\u001a\u0002042\u0006\u00105\u001a\u000206H\u0002J\u000e\u00107\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J\u0016\u00108\u001a\u0002092\u0006\u0010%\u001a\u00020\u001bH\u0082@¢\u0006\u0002\u0010:J\u0016\u0010;\u001a\u0002092\u0006\u0010%\u001a\u00020\u001bH\u0083@¢\u0006\u0002\u0010:J\u0010\u0010<\u001a\u0002092\b\b\u0002\u0010%\u001a\u00020\u001bJ\u0012\u0010=\u001a\u00020>2\b\u0010?\u001a\u0004\u0018\u00010@H\u0016J\"\u0010A\u001a\u00020\u00042\b\u0010?\u001a\u0004\u0018\u00010@2\u0006\u0010B\u001a\u00020\u00042\u0006\u0010C\u001a\u00020\u0004H\u0016J\u0012\u0010D\u001a\u00020\u00152\b\u0010?\u001a\u0004\u0018\u00010@H\u0016J\u0016\u0010E\u001a\u00020\u00042\u0006\u0010F\u001a\u00020\u001bH\u0082@¢\u0006\u0002\u0010:J\u000e\u0010G\u001a\u000209H\u0082@¢\u0006\u0002\u0010HJ\u001e\u0010I\u001a\u0002092\u0006\u0010J\u001a\u00020\u00042\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J\u0006\u0010L\u001a\u000209J\u0006\u0010M\u001a\u00020NJ\b\u0010O\u001a\u000209H\u0002J\u0006\u0010P\u001a\u00020NR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00060\rR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082D¢\u0006\u0002\n\u0000R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0017\"\u0004\b\"\u0010\u0019R\u001a\u0010#\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0017\"\u0004\b$\u0010\u0019R\u000e\u0010%\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020/X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020'X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u000102X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006R"}, m1901d2 = {"Lcom/aivox/common/util/whisper/WhisperService;", "Landroid/app/Service;", "()V", "MAX_AUDIO_SEC", "", "SAMPLE_RATE", "audioData0", "", "", "audioData1", "audioData2", "baseChunkSize", "binder", "Lcom/aivox/common/util/whisper/WhisperService$WhisperBinder;", "callback", "Lcom/aivox/common/speech2text/ICommonTransCallback;", "getCallback", "()Lcom/aivox/common/speech2text/ICommonTransCallback;", "setCallback", "(Lcom/aivox/common/speech2text/ICommonTransCallback;)V", "canTranscribe", "", "getCanTranscribe", "()Z", "setCanTranscribe", "(Z)V", "channelId", "", "filePath", "getFilePath", "()Ljava/lang/String;", "setFilePath", "(Ljava/lang/String;)V", "isStop", "setStop", "isStreaming", "setStreaming", "language", "lastProcessedTimestamp", "", "listPoolCount", "messageId", "Ljava/util/concurrent/atomic/AtomicInteger;", "needNewList", "recorder", "Lcom/aivox/common/util/whisper/Recorder;", Tracer.SCOPE_KEY, "Lkotlinx/coroutines/CoroutineScope;", "streamingStartTime", "whisperContext", "Lcom/whispercpp/whisper/WhisperContext;", "byteToFloat", "", "bytes", "", "getListFromPool", "loadBaseModel", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadData", "loadModel", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onStartCommand", "flags", "startId", "onUnbind", "printMessage", NotificationCompat.CATEGORY_MESSAGE, "printSystemInfo", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processBufferedAudioChunks", "msgId", "audioData", "release", "startStream", "Lkotlinx/coroutines/Job;", "startStreaming", "stopStream", "WhisperBinder", "common_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class WhisperService extends Service {
    private int MAX_AUDIO_SEC;
    private int SAMPLE_RATE;
    private List<Float> audioData0;
    private List<Float> audioData1;
    private List<Float> audioData2;
    private final int baseChunkSize;
    private ICommonTransCallback callback;
    private boolean canTranscribe;
    private String filePath;
    private boolean isStop;
    private boolean isStreaming;
    private String language;
    private long lastProcessedTimestamp;
    private int listPoolCount;
    private final AtomicInteger messageId;
    private boolean needNewList;
    private final Recorder recorder;
    private final CoroutineScope scope;
    private long streamingStartTime;
    private WhisperContext whisperContext;
    private final String channelId = "WhisperService";
    private final WhisperBinder binder = new WhisperBinder();

    /* JADX INFO: renamed from: com.aivox.common.util.whisper.WhisperService$loadData$1 */
    /* JADX INFO: compiled from: WhisperService.kt */
    @Metadata(m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.aivox.common.util.whisper.WhisperService", m1911f = "WhisperService.kt", m1912i = {0, 0, 1}, m1913l = {115, 118, 123}, m1914m = "loadData", m1915n = {"this", "language", "this"}, m1916s = {"L$0", "L$1", "L$0"})
    static final class C10061 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C10061(Continuation<? super C10061> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return WhisperService.this.loadData(null, this);
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 1;
    }

    public WhisperService() {
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Intrinsics.checkNotNullExpressionValue(executorServiceNewSingleThreadExecutor, "newSingleThreadExecutor(...)");
        this.scope = CoroutineScopeKt.CoroutineScope(ExecutorsKt.from(executorServiceNewSingleThreadExecutor));
        this.recorder = new Recorder();
        this.language = "en";
        this.MAX_AUDIO_SEC = 8;
        this.SAMPLE_RATE = 16000;
        this.baseChunkSize = 15360;
        this.isStop = true;
        this.messageId = new AtomicInteger(0);
        this.needNewList = true;
        List<Float> listSynchronizedList = Collections.synchronizedList(new ArrayList());
        Intrinsics.checkNotNullExpressionValue(listSynchronizedList, "synchronizedList(...)");
        this.audioData0 = listSynchronizedList;
        List<Float> listSynchronizedList2 = Collections.synchronizedList(new ArrayList());
        Intrinsics.checkNotNullExpressionValue(listSynchronizedList2, "synchronizedList(...)");
        this.audioData1 = listSynchronizedList2;
        List<Float> listSynchronizedList3 = Collections.synchronizedList(new ArrayList());
        Intrinsics.checkNotNullExpressionValue(listSynchronizedList3, "synchronizedList(...)");
        this.audioData2 = listSynchronizedList3;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        NotificationChannel notificationChannel = new NotificationChannel(this.channelId, getString(C0874R.string.app_name), 3);
        WhisperService whisperService = this;
        Intent intent2 = new Intent(whisperService, (Class<?>) WhisperService.class);
        intent2.putExtra(TableConstants.ErrorConstants.ERROR_CODE, 123);
        PendingIntent service = PendingIntent.getService(whisperService, 1001, intent2, 67108864);
        Object systemService = getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        ((NotificationManager) systemService).createNotificationChannel(notificationChannel);
        Notification notificationBuild = new NotificationCompat.Builder(whisperService, this.channelId).setOngoing(true).setSmallIcon(C0874R.mipmap.icon_logo).setContentTitle(getString(C0874R.string.aivox_is_running_in_the_background)).setPriority(4).setCategory(NotificationCompat.CATEGORY_SERVICE).setChannelId(this.channelId).setContentIntent(service).build();
        Intrinsics.checkNotNullExpressionValue(notificationBuild, "build(...)");
        startForeground(1, notificationBuild);
        return this.binder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public final boolean getCanTranscribe() {
        return this.canTranscribe;
    }

    public final void setCanTranscribe(boolean z) {
        this.canTranscribe = z;
    }

    /* JADX INFO: renamed from: isStreaming, reason: from getter */
    public final boolean getIsStreaming() {
        return this.isStreaming;
    }

    public final void setStreaming(boolean z) {
        this.isStreaming = z;
    }

    public final String getFilePath() {
        return this.filePath;
    }

    public final void setFilePath(String str) {
        this.filePath = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object printSystemInfo(Continuation<? super Unit> continuation) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("System Info: %s\n", Arrays.copyOf(new Object[]{WhisperContext.INSTANCE.getSystemInfo()}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        Object objPrintMessage = printMessage(str, continuation);
        return objPrintMessage == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objPrintMessage : Unit.INSTANCE;
    }

    public static /* synthetic */ void loadModel$default(WhisperService whisperService, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "en";
        }
        whisperService.loadModel(str);
    }

    public final void loadModel(String language) {
        Intrinsics.checkNotNullParameter(language, "language");
        this.canTranscribe = false;
        this.language = language;
        BuildersKt__Builders_commonKt.launch$default(this.scope, Dispatchers.getIO(), null, new C10071(language, null), 2, null);
    }

    /* JADX INFO: renamed from: com.aivox.common.util.whisper.WhisperService$loadModel$1 */
    /* JADX INFO: compiled from: WhisperService.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.aivox.common.util.whisper.WhisperService$loadModel$1", m1911f = "WhisperService.kt", m1912i = {}, m1913l = {109}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C10071 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $language;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C10071(String str, Continuation<? super C10071> continuation) {
            super(2, continuation);
            this.$language = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WhisperService.this.new C10071(this.$language, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10071) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (WhisperService.this.loadData(this.$language, this) == coroutine_suspended) {
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v6, types: [com.aivox.common.util.whisper.WhisperService] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadData(java.lang.String r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) throws java.lang.Throwable {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.aivox.common.util.whisper.WhisperService.C10061
            if (r0 == 0) goto L14
            r0 = r9
            com.aivox.common.util.whisper.WhisperService$loadData$1 r0 = (com.aivox.common.util.whisper.WhisperService.C10061) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            com.aivox.common.util.whisper.WhisperService$loadData$1 r0 = new com.aivox.common.util.whisper.WhisperService$loadData$1
            r0.<init>(r9)
        L19:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 0
            r6 = 1
            if (r2 == 0) goto L52
            if (r2 == r6) goto L44
            if (r2 == r4) goto L3a
            if (r2 != r3) goto L32
            kotlin.ResultKt.throwOnFailure(r9)
            goto Lb0
        L32:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3a:
            java.lang.Object r8 = r0.L$0
            com.aivox.common.util.whisper.WhisperService r8 = (com.aivox.common.util.whisper.WhisperService) r8
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Exception -> L42
            goto L73
        L42:
            r9 = move-exception
            goto L84
        L44:
            java.lang.Object r8 = r0.L$1
            java.lang.String r8 = (java.lang.String) r8
            java.lang.Object r2 = r0.L$0
            com.aivox.common.util.whisper.WhisperService r2 = (com.aivox.common.util.whisper.WhisperService) r2
            kotlin.ResultKt.throwOnFailure(r9)
            r9 = r8
            r8 = r2
            goto L66
        L52:
            kotlin.ResultKt.throwOnFailure(r9)
            r0.L$0 = r7
            r0.L$1 = r8
            r0.label = r6
            java.lang.String r9 = "Loading data...\n"
            java.lang.Object r9 = r7.printMessage(r9, r0)
            if (r9 != r1) goto L64
            return r1
        L64:
            r9 = r8
            r8 = r7
        L66:
            r0.L$0 = r8     // Catch: java.lang.Exception -> L42
            r0.L$1 = r5     // Catch: java.lang.Exception -> L42
            r0.label = r4     // Catch: java.lang.Exception -> L42
            java.lang.Object r9 = r8.loadBaseModel(r9, r0)     // Catch: java.lang.Exception -> L42
            if (r9 != r1) goto L73
            return r1
        L73:
            r8.canTranscribe = r6     // Catch: java.lang.Exception -> L42
            org.greenrobot.eventbus.EventBus r9 = org.greenrobot.eventbus.EventBus.getDefault()     // Catch: java.lang.Exception -> L42
            com.aivox.common.model.EventBean r2 = new com.aivox.common.model.EventBean     // Catch: java.lang.Exception -> L42
            r4 = 81
            r2.<init>(r4)     // Catch: java.lang.Exception -> L42
            r9.post(r2)     // Catch: java.lang.Exception -> L42
            goto Lb0
        L84:
            java.lang.String r2 = "PackageManagerCompat"
            r4 = r9
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            android.util.Log.w(r2, r4)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r9 = r9.getLocalizedMessage()
            java.lang.StringBuilder r9 = r2.append(r9)
            r2 = 10
            java.lang.StringBuilder r9 = r9.append(r2)
            java.lang.String r9 = r9.toString()
            r0.L$0 = r5
            r0.L$1 = r5
            r0.label = r3
            java.lang.Object r8 = r8.printMessage(r9, r0)
            if (r8 != r1) goto Lb0
            return r1
        Lb0:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.util.whisper.WhisperService.loadData(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: renamed from: com.aivox.common.util.whisper.WhisperService$printMessage$2 */
    /* JADX INFO: compiled from: WhisperService.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.aivox.common.util.whisper.WhisperService$printMessage$2", m1911f = "WhisperService.kt", m1912i = {}, m1913l = {}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C10082 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Integer>, Object> {
        final /* synthetic */ String $msg;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C10082(String str, Continuation<? super C10082> continuation) {
            super(2, continuation);
            this.$msg = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C10082(this.$msg, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Integer> continuation) {
            return ((C10082) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boxing.boxInt(Log.d("WhisperService", this.$msg));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object printMessage(String str, Continuation<? super Integer> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new C10082(str, null), continuation);
    }

    /* JADX INFO: renamed from: com.aivox.common.util.whisper.WhisperService$loadBaseModel$2 */
    /* JADX INFO: compiled from: WhisperService.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.aivox.common.util.whisper.WhisperService$loadBaseModel$2", m1911f = "WhisperService.kt", m1912i = {}, m1913l = {133, 134, 145, 147}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C10052 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $language;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C10052(String str, Continuation<? super C10052> continuation) {
            super(2, continuation);
            this.$language = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WhisperService.this.new C10052(this.$language, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10052) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0059  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r15) throws java.lang.Throwable {
            /*
                Method dump skipped, instruction units count: 248
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.util.whisper.WhisperService.C10052.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object loadBaseModel(String str, Continuation<? super Unit> continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new C10052(str, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    public final ICommonTransCallback getCallback() {
        return this.callback;
    }

    public final void setCallback(ICommonTransCallback iCommonTransCallback) {
        this.callback = iCommonTransCallback;
    }

    /* JADX INFO: renamed from: isStop, reason: from getter */
    public final boolean getIsStop() {
        return this.isStop;
    }

    public final void setStop(boolean z) {
        this.isStop = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<Float> getListFromPool() {
        int i = this.listPoolCount;
        int i2 = i == 2 ? 0 : i + 1;
        this.listPoolCount = i2;
        if (i2 == 1) {
            this.audioData1.clear();
            return this.audioData1;
        }
        if (i2 == 2) {
            this.audioData2.clear();
            return this.audioData2;
        }
        this.audioData0.clear();
        return this.audioData0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r3v0, types: [T, java.util.List] */
    public final void startStreaming() {
        if (!this.isStreaming) {
            this.isStreaming = true;
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = getListFromPool();
            if (this.isStop) {
                this.messageId.set(0);
                this.lastProcessedTimestamp = System.currentTimeMillis();
                this.streamingStartTime = System.currentTimeMillis();
            } else {
                this.isStop = true;
            }
            Recorder.AudioDataReceivedListener audioDataReceivedListener = new Recorder.AudioDataReceivedListener() { // from class: com.aivox.common.util.whisper.WhisperService$startStreaming$onDataReceived$1
                /* JADX WARN: Type inference failed for: r1v4, types: [T, java.util.List] */
                @Override // com.aivox.common.util.whisper.Recorder.AudioDataReceivedListener
                public void onAudioDataReceived(byte[] data) {
                    Intrinsics.checkNotNullParameter(data, "data");
                    if (this.this$0.needNewList) {
                        objectRef.element = this.this$0.getListFromPool();
                        int iIncrementAndGet = this.this$0.messageId.incrementAndGet();
                        this.this$0.needNewList = false;
                        this.this$0.processBufferedAudioChunks(iIncrementAndGet, objectRef.element);
                    }
                    objectRef.element.addAll(ArraysKt.toList(this.this$0.byteToFloat(data)));
                    if (objectRef.element.size() >= this.this$0.MAX_AUDIO_SEC * this.this$0.SAMPLE_RATE) {
                        this.this$0.needNewList = true;
                    }
                }
            };
            String str = this.filePath;
            if (str != null) {
                Recorder recorder = this.recorder;
                Intrinsics.checkNotNull(str);
                recorder.setFilePath(str);
                this.recorder.startStreaming(audioDataReceivedListener, new Function1<Exception, Unit>() { // from class: com.aivox.common.util.whisper.WhisperService.startStreaming.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Exception exc) {
                        invoke2(exc);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Exception e) {
                        Intrinsics.checkNotNullParameter(e, "e");
                        Log.e("WhisperService", "Error during streaming: " + e.getLocalizedMessage(), e);
                        WhisperService.this.setStreaming(false);
                    }
                });
            }
            Log.d("WhisperService", "Start trans");
            return;
        }
        Log.i("WhisperService", "Streaming is already active.");
    }

    /* JADX INFO: renamed from: com.aivox.common.util.whisper.WhisperService$processBufferedAudioChunks$1 */
    /* JADX INFO: compiled from: WhisperService.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.aivox.common.util.whisper.WhisperService$processBufferedAudioChunks$1", m1911f = "WhisperService.kt", m1912i = {0, 0, 0}, m1913l = {287}, m1914m = "invokeSuspend", m1915n = {"textChunk", "startTime", "lastTime"}, m1916s = {"L$0", "J$0", "I$0"})
    static final class C10091 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<Float> $audioData;
        final /* synthetic */ Ref.ObjectRef<String> $lastText;
        final /* synthetic */ int $msgId;
        int I$0;
        long J$0;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C10091(int i, List<Float> list, Ref.ObjectRef<String> objectRef, Continuation<? super C10091> continuation) {
            super(2, continuation);
            this.$msgId = i;
            this.$audioData = list;
            this.$lastText = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WhisperService.this.new C10091(this.$msgId, this.$audioData, this.$lastText, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10091) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0036  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00b2  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00c2  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00d7  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x0151  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0159  */
        /* JADX WARN: Type inference failed for: r9v10, types: [T, java.lang.Object, java.lang.String] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x004f -> B:64:0x014b). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0060 -> B:64:0x014b). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x0096 -> B:29:0x0097). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x009b -> B:35:0x00a3). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r17) throws java.lang.Throwable {
            /*
                Method dump skipped, instruction units count: 359
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.util.whisper.WhisperService.C10091.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processBufferedAudioChunks(int msgId, List<Float> audioData) {
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = "";
        BuildersKt__Builders_commonKt.launch$default(this.scope, Dispatchers.getIO(), null, new C10091(msgId, audioData, objectRef, null), 2, null);
    }

    /* JADX INFO: renamed from: com.aivox.common.util.whisper.WhisperService$startStream$1 */
    /* JADX INFO: compiled from: WhisperService.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.aivox.common.util.whisper.WhisperService$startStream$1", m1911f = "WhisperService.kt", m1912i = {}, m1913l = {}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C10111 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C10111(Continuation<? super C10111> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WhisperService.this.new C10111(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10111) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (!WhisperService.this.getIsStreaming()) {
                Log.d("WhisperService", "Starting streaming...");
                WhisperService.this.startStreaming();
            }
            return Unit.INSTANCE;
        }
    }

    public final Job startStream() {
        return BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C10111(null), 3, null);
    }

    /* JADX INFO: renamed from: com.aivox.common.util.whisper.WhisperService$stopStream$1 */
    /* JADX INFO: compiled from: WhisperService.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.aivox.common.util.whisper.WhisperService$stopStream$1", m1911f = "WhisperService.kt", m1912i = {}, m1913l = {}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C10131 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C10131(Continuation<? super C10131> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WhisperService.this.new C10131(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10131) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (WhisperService.this.getIsStreaming()) {
                Log.d("WhisperService", "Stopping streaming...");
                WhisperService.this.recorder.stopRecording();
                WhisperService.this.setStreaming(false);
                Log.d("WhisperService", "Streaming stopped");
            }
            return Unit.INSTANCE;
        }
    }

    public final Job stopStream() {
        return BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C10131(null), 3, null);
    }

    /* JADX INFO: renamed from: com.aivox.common.util.whisper.WhisperService$release$1 */
    /* JADX INFO: compiled from: WhisperService.kt */
    @Metadata(m1900d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, m1901d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    @DebugMetadata(m1910c = "com.aivox.common.util.whisper.WhisperService$release$1", m1911f = "WhisperService.kt", m1912i = {}, m1913l = {356}, m1914m = "invokeSuspend", m1915n = {}, m1916s = {})
    static final class C10101 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C10101(Continuation<? super C10101> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WhisperService.this.new C10101(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10101) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Log.d("WhisperService", "Whisper release start");
                WhisperContext whisperContext = WhisperService.this.whisperContext;
                if (whisperContext != null) {
                    this.label = 1;
                    if (whisperContext.release(this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            WhisperService.this.whisperContext = null;
            Log.d("WhisperService", "Whisper released");
            return Unit.INSTANCE;
        }
    }

    public final void release() {
        if (this.whisperContext == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C10101(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float[] byteToFloat(byte[] bytes) {
        int length = bytes.length / 2;
        short[] sArr = new short[length];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(sArr);
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            short s = sArr[i];
            if (s != 0) {
                fArr[i] = s / 32767.0f;
            }
        }
        return fArr;
    }

    /* JADX INFO: compiled from: WhisperService.kt */
    @Metadata(m1900d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m1901d2 = {"Lcom/aivox/common/util/whisper/WhisperService$WhisperBinder;", "Landroid/os/Binder;", "(Lcom/aivox/common/util/whisper/WhisperService;)V", NotificationCompat.CATEGORY_SERVICE, "Lcom/aivox/common/util/whisper/WhisperService;", "getService", "()Lcom/aivox/common/util/whisper/WhisperService;", "common_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public final class WhisperBinder extends Binder {
        private final WhisperService service;

        public WhisperBinder() {
            this.service = WhisperService.this;
        }

        public final WhisperService getService() {
            return this.service;
        }
    }
}
