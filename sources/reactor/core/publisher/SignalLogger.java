package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.logging.Level;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class SignalLogger<IN> implements SignalPeek<IN> {
    static final int AFTER_TERMINATE = 1;
    static final int ALL = 510;
    static final int CANCEL = 2;
    static final int CONTEXT_PARENT = 256;
    static final AtomicLong IDS = new AtomicLong(1);
    static final String LOG_TEMPLATE = "{}({})";
    static final String LOG_TEMPLATE_FUSEABLE = "| {}({})";
    static final int ON_COMPLETE = 8;
    static final int ON_ERROR = 16;
    static final int ON_NEXT = 32;
    static final int ON_SUBSCRIBE = 64;
    static final int REQUEST = 4;
    static final int SUBSCRIBE = 128;
    final boolean fuseable;

    /* JADX INFO: renamed from: id */
    final long f2294id;
    final Level level;
    final Logger log;
    final String operatorLine;
    final int options;
    final CorePublisher<IN> source;

    SignalLogger(CorePublisher<IN> corePublisher, @Nullable String str, Level level, boolean z, SignalType... signalTypeArr) {
        this(corePublisher, str, level, z, new Function() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Loggers.getLogger((String) obj);
            }
        }, signalTypeArr);
    }

    SignalLogger(CorePublisher<IN> corePublisher, @Nullable String str, Level level, boolean z, Function<String, Logger> function, @Nullable SignalType... signalTypeArr) {
        String str2;
        this.source = (CorePublisher) Objects.requireNonNull(corePublisher, "source");
        long andIncrement = IDS.getAndIncrement();
        this.f2294id = andIncrement;
        this.fuseable = corePublisher instanceof Fuseable;
        if (z) {
            this.operatorLine = Traces.extractOperatorAssemblyInformation(Traces.callSiteSupplierFactory.get().get());
        } else {
            this.operatorLine = null;
        }
        boolean z2 = str == null || str.isEmpty() || str.endsWith(".");
        if (z2 && str == null) {
            str = "reactor.";
        }
        if (z2) {
            if (corePublisher instanceof Mono) {
                str2 = str + "Mono." + corePublisher.getClass().getSimpleName().replace("Mono", "");
            } else if (corePublisher instanceof ParallelFlux) {
                str2 = str + "Parallel." + corePublisher.getClass().getSimpleName().replace("Parallel", "");
            } else {
                str2 = str + "Flux." + corePublisher.getClass().getSimpleName().replace("Flux", "");
            }
            str = str2 + "." + andIncrement;
        }
        this.log = function.apply(str);
        this.level = level;
        if (signalTypeArr == null || signalTypeArr.length == 0) {
            this.options = 510;
            return;
        }
        int i = 0;
        for (SignalType signalType : signalTypeArr) {
            if (signalType == SignalType.CANCEL) {
                i |= 2;
            } else if (signalType == SignalType.CURRENT_CONTEXT) {
                i |= 256;
            } else if (signalType == SignalType.ON_SUBSCRIBE) {
                i |= 64;
            } else if (signalType == SignalType.REQUEST) {
                i |= 4;
            } else if (signalType == SignalType.ON_NEXT) {
                i |= 32;
            } else if (signalType == SignalType.ON_ERROR) {
                i |= 16;
            } else if (signalType == SignalType.ON_COMPLETE) {
                i |= 8;
            } else if (signalType == SignalType.SUBSCRIBE) {
                i |= 128;
            } else if (signalType == SignalType.AFTER_TERMINATE) {
                i |= 1;
            }
        }
        this.options = i;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.source;
        }
        return null;
    }

    void log(SignalType signalType, Object obj) {
        String str = this.fuseable ? LOG_TEMPLATE_FUSEABLE : LOG_TEMPLATE;
        if (this.operatorLine != null) {
            str = str + " " + this.operatorLine;
        }
        if (this.level == Level.FINEST) {
            this.log.trace(str, signalType, obj);
            return;
        }
        if (this.level == Level.FINE) {
            this.log.debug(str, signalType, obj);
            return;
        }
        if (this.level == Level.INFO) {
            this.log.info(str, signalType, obj);
        } else if (this.level == Level.WARNING) {
            this.log.warn(str, signalType, obj);
        } else if (this.level == Level.SEVERE) {
            this.log.error(str, signalType, obj);
        }
    }

    void safeLog(SignalType signalType, Object obj) {
        if (obj instanceof Fuseable.QueueSubscription) {
            obj = String.valueOf(obj);
            if (this.log.isDebugEnabled()) {
                this.log.debug("A Fuseable Subscription has been passed to the logging framework, this is generally a sign of a misplaced log(), eg. 'window(2).log()' instead of 'window(2).flatMap(w -> w.log())'");
            }
        }
        try {
            log(signalType, obj);
        } catch (UnsupportedOperationException e) {
            log(signalType, String.valueOf(obj));
            if (this.log.isDebugEnabled()) {
                this.log.debug("UnsupportedOperationException has been raised by the logging framework, does your log() placement make sense? eg. 'window(2).log()' instead of 'window(2).flatMap(w -> w.log())'", e);
            }
        }
    }

    static String subscriptionAsString(@Nullable Subscription subscription) {
        if (subscription == null) {
            return "null subscription";
        }
        StringBuilder sb = new StringBuilder();
        if (subscription instanceof Fuseable.SynchronousSubscription) {
            sb.append("[Synchronous Fuseable] ");
        } else if (subscription instanceof Fuseable.QueueSubscription) {
            sb.append("[Fuseable] ");
        }
        Class<?> cls = subscription.getClass();
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            canonicalName = cls.getName();
        }
        sb.append(canonicalName.replaceFirst(cls.getPackage().getName() + ".", ""));
        return sb.toString();
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Consumer<? super Subscription> onSubscribeCall() {
        if ((this.options & 64) != 64) {
            return null;
        }
        if (this.level != Level.INFO || this.log.isInfoEnabled()) {
            return new Consumer() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m4298lambda$onSubscribeCall$0$reactorcorepublisherSignalLogger((Subscription) obj);
                }
            };
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$onSubscribeCall$0$reactor-core-publisher-SignalLogger, reason: not valid java name */
    /* synthetic */ void m4298lambda$onSubscribeCall$0$reactorcorepublisherSignalLogger(Subscription subscription) {
        log(SignalType.ON_SUBSCRIBE, subscriptionAsString(subscription));
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Consumer<? super Context> onCurrentContextCall() {
        if ((this.options & 256) != 256) {
            return null;
        }
        if ((this.level == Level.FINE && this.log.isDebugEnabled()) || (this.level == Level.FINEST && this.log.isTraceEnabled())) {
            return new Consumer() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m1975xccc442c5((Context) obj);
                }
            };
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$onCurrentContextCall$1$reactor-core-publisher-SignalLogger */
    /* synthetic */ void m1975xccc442c5(Context context) {
        log(SignalType.CURRENT_CONTEXT, context);
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Consumer<? super IN> onNextCall() {
        if ((this.options & 32) != 32) {
            return null;
        }
        if (this.level != Level.INFO || this.log.isInfoEnabled()) {
            return new Consumer() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m4296lambda$onNextCall$2$reactorcorepublisherSignalLogger(obj);
                }
            };
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$onNextCall$2$reactor-core-publisher-SignalLogger, reason: not valid java name */
    /* synthetic */ void m4296lambda$onNextCall$2$reactorcorepublisherSignalLogger(Object obj) {
        safeLog(SignalType.ON_NEXT, obj);
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Consumer<? super Throwable> onErrorCall() {
        boolean z = this.level == Level.FINE && this.log.isDebugEnabled();
        boolean z2 = this.level == Level.FINEST && this.log.isTraceEnabled();
        boolean z3 = (this.level == Level.FINE || this.level == Level.FINEST || !this.log.isErrorEnabled()) ? false : true;
        if ((this.options & 16) != 16) {
            return null;
        }
        if (!z3 && !z && !z2) {
            return null;
        }
        final String str = this.fuseable ? LOG_TEMPLATE_FUSEABLE : LOG_TEMPLATE;
        if (this.operatorLine != null) {
            str = str + " " + this.operatorLine;
        }
        if (z2) {
            return new Consumer() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m4293lambda$onErrorCall$3$reactorcorepublisherSignalLogger(str, (Throwable) obj);
                }
            };
        }
        if (z) {
            return new Consumer() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m4294lambda$onErrorCall$4$reactorcorepublisherSignalLogger(str, (Throwable) obj);
                }
            };
        }
        return new Consumer() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m4295lambda$onErrorCall$5$reactorcorepublisherSignalLogger(str, (Throwable) obj);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$onErrorCall$3$reactor-core-publisher-SignalLogger, reason: not valid java name */
    /* synthetic */ void m4293lambda$onErrorCall$3$reactorcorepublisherSignalLogger(String str, Throwable th) {
        this.log.trace(str, SignalType.ON_ERROR, th, this.source);
        this.log.trace("", th);
    }

    /* JADX INFO: renamed from: lambda$onErrorCall$4$reactor-core-publisher-SignalLogger, reason: not valid java name */
    /* synthetic */ void m4294lambda$onErrorCall$4$reactorcorepublisherSignalLogger(String str, Throwable th) {
        this.log.debug(str, SignalType.ON_ERROR, th, this.source);
        this.log.debug("", th);
    }

    /* JADX INFO: renamed from: lambda$onErrorCall$5$reactor-core-publisher-SignalLogger, reason: not valid java name */
    /* synthetic */ void m4295lambda$onErrorCall$5$reactorcorepublisherSignalLogger(String str, Throwable th) {
        this.log.error(str, SignalType.ON_ERROR, th, this.source);
        this.log.error("", th);
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Runnable onCompleteCall() {
        if ((this.options & 8) != 8) {
            return null;
        }
        if (this.level != Level.INFO || this.log.isInfoEnabled()) {
            return new Runnable() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m4292lambda$onCompleteCall$6$reactorcorepublisherSignalLogger();
                }
            };
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$onCompleteCall$6$reactor-core-publisher-SignalLogger, reason: not valid java name */
    /* synthetic */ void m4292lambda$onCompleteCall$6$reactorcorepublisherSignalLogger() {
        log(SignalType.ON_COMPLETE, "");
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Runnable onAfterTerminateCall() {
        if ((this.options & 1) != 1) {
            return null;
        }
        if (this.level != Level.INFO || this.log.isInfoEnabled()) {
            return new Runnable() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m1974x18f366fa();
                }
            };
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$onAfterTerminateCall$7$reactor-core-publisher-SignalLogger */
    /* synthetic */ void m1974x18f366fa() {
        log(SignalType.AFTER_TERMINATE, "");
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public LongConsumer onRequestCall() {
        if ((this.options & 4) != 4) {
            return null;
        }
        if (this.level != Level.INFO || this.log.isInfoEnabled()) {
            return new LongConsumer() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda6
                @Override // java.util.function.LongConsumer
                public final void accept(long j) {
                    this.f$0.m4297lambda$onRequestCall$8$reactorcorepublisherSignalLogger(j);
                }
            };
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$onRequestCall$8$reactor-core-publisher-SignalLogger, reason: not valid java name */
    /* synthetic */ void m4297lambda$onRequestCall$8$reactorcorepublisherSignalLogger(long j) {
        log(SignalType.REQUEST, Long.MAX_VALUE == j ? "unbounded" : Long.valueOf(j));
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Runnable onCancelCall() {
        if ((this.options & 2) != 2) {
            return null;
        }
        if (this.level != Level.INFO || this.log.isInfoEnabled()) {
            return new Runnable() { // from class: reactor.core.publisher.SignalLogger$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m4291lambda$onCancelCall$9$reactorcorepublisherSignalLogger();
                }
            };
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$onCancelCall$9$reactor-core-publisher-SignalLogger, reason: not valid java name */
    /* synthetic */ void m4291lambda$onCancelCall$9$reactorcorepublisherSignalLogger() {
        log(SignalType.CANCEL, "");
    }

    public String toString() {
        return "/loggers/" + this.log.getName() + "/" + this.f2294id;
    }
}
