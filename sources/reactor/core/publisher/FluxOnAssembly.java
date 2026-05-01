package reactor.core.publisher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxOnAssembly;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxOnAssembly<T> extends InternalFluxOperator<T, T> implements Fuseable, AssemblyOp {
    final AssemblySnapshot snapshotStack;

    FluxOnAssembly(Flux<? extends T> flux, AssemblySnapshot assemblySnapshot) {
        super(flux);
        this.snapshotStack = assemblySnapshot;
    }

    @Override // reactor.core.Scannable
    public String stepName() {
        return this.snapshotStack.operatorAssemblyInformation();
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.ACTUAL_METADATA ? Boolean.valueOf(!this.snapshotStack.isCheckpoint) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    @Override // reactor.core.publisher.Flux
    public String toString() {
        return this.snapshotStack.operatorAssemblyInformation();
    }

    static void fillStacktraceHeader(StringBuilder sb, Class<?> cls, @Nullable String str) {
        sb.append("\nAssembly trace from producer [").append(cls.getName()).append("]");
        if (str != null) {
            sb.append(", described as [").append(str).append("]");
        }
        sb.append(" :\n");
    }

    static <T> CoreSubscriber<? super T> wrapSubscriber(CoreSubscriber<? super T> coreSubscriber, Flux<? extends T> flux, Publisher<?> publisher, @Nullable AssemblySnapshot assemblySnapshot) {
        if (assemblySnapshot == null) {
            return coreSubscriber;
        }
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new OnAssemblyConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, assemblySnapshot, flux, publisher);
        }
        return new OnAssemblySubscriber(coreSubscriber, assemblySnapshot, flux, publisher);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return wrapSubscriber(coreSubscriber, this.source, this, this.snapshotStack);
    }

    static class AssemblySnapshot {

        @Nullable
        final Supplier<String> assemblyInformationSupplier;
        String cached;

        @Nullable
        final String description;
        final boolean isCheckpoint;

        public boolean isLight() {
            return false;
        }

        AssemblySnapshot(@Nullable String str, Supplier<String> supplier) {
            this(str != null, str, supplier);
        }

        AssemblySnapshot(String str) {
            this.isCheckpoint = false;
            this.description = null;
            this.assemblyInformationSupplier = null;
            this.cached = str;
        }

        private AssemblySnapshot(boolean z, @Nullable String str, @Nullable Supplier<String> supplier) {
            this.isCheckpoint = z;
            this.description = str;
            this.assemblyInformationSupplier = supplier;
        }

        public boolean hasDescription() {
            return this.description != null;
        }

        @Nullable
        public String getDescription() {
            return this.description;
        }

        public boolean isCheckpoint() {
            return this.isCheckpoint;
        }

        public String lightPrefix() {
            return "";
        }

        String toAssemblyInformation() {
            if (this.cached == null) {
                Supplier<String> supplier = this.assemblyInformationSupplier;
                if (supplier == null) {
                    throw new IllegalStateException("assemblyInformation must either be supplied or resolvable");
                }
                this.cached = supplier.get();
            }
            return this.cached;
        }

        String operatorAssemblyInformation() {
            return Traces.extractOperatorAssemblyInformation(toAssemblyInformation());
        }
    }

    static final class CheckpointLightSnapshot extends AssemblySnapshot {
        @Override // reactor.core.publisher.FluxOnAssembly.AssemblySnapshot
        public boolean isLight() {
            return true;
        }

        CheckpointLightSnapshot(@Nullable String str) {
            super(true, str, null);
            this.cached = "checkpoint(\"" + (str == null ? "" : str) + "\")";
        }

        @Override // reactor.core.publisher.FluxOnAssembly.AssemblySnapshot
        public String lightPrefix() {
            return "checkpoint";
        }

        @Override // reactor.core.publisher.FluxOnAssembly.AssemblySnapshot
        String operatorAssemblyInformation() {
            return this.cached;
        }
    }

    static final class CheckpointHeavySnapshot extends AssemblySnapshot {
        CheckpointHeavySnapshot(@Nullable String str, Supplier<String> supplier) {
            super(true, str, supplier);
        }

        @Override // reactor.core.publisher.FluxOnAssembly.AssemblySnapshot
        public String lightPrefix() {
            return "checkpoint(" + (this.description == null ? "" : this.description) + ")";
        }
    }

    static final class MethodReturnSnapshot extends AssemblySnapshot {
        @Override // reactor.core.publisher.FluxOnAssembly.AssemblySnapshot
        public boolean isLight() {
            return true;
        }

        MethodReturnSnapshot(String str) {
            super(false, str, null);
            this.cached = str;
        }

        @Override // reactor.core.publisher.FluxOnAssembly.AssemblySnapshot
        String operatorAssemblyInformation() {
            return this.cached;
        }
    }

    static final class ObservedAtInformationNode implements Serializable {
        private static final long serialVersionUID = 1;

        /* JADX INFO: renamed from: id */
        final int f2156id;
        final String message;
        final String operator;

        @Nullable
        ObservedAtInformationNode parent;
        int occurrenceCounter = 0;
        Set<ObservedAtInformationNode> children = new LinkedHashSet();

        ObservedAtInformationNode(int i, String str, String str2) {
            this.f2156id = i;
            this.operator = str;
            this.message = str2;
        }

        void incrementCount() {
            this.occurrenceCounter++;
        }

        void addNode(ObservedAtInformationNode observedAtInformationNode) {
            if (this != observedAtInformationNode && this.children.add(observedAtInformationNode)) {
                observedAtInformationNode.parent = this;
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ObservedAtInformationNode observedAtInformationNode = (ObservedAtInformationNode) obj;
            return this.f2156id == observedAtInformationNode.f2156id && this.operator.equals(observedAtInformationNode.operator) && this.message.equals(observedAtInformationNode.message);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.f2156id), this.operator, this.message);
        }

        public String toString() {
            return this.operator + "{@" + this.f2156id + (this.children.isEmpty() ? "" : ", " + this.children.size() + " children") + '}';
        }
    }

    static final class OnAssemblyException extends RuntimeException {
        private static final long serialVersionUID = -6342981676020433721L;
        int maxOperatorSize;
        final Map<Integer, ObservedAtInformationNode> nodesPerId;
        final ObservedAtInformationNode root;

        @Override // java.lang.Throwable
        public Throwable fillInStackTrace() {
            return this;
        }

        OnAssemblyException(String str) {
            super(str);
            this.nodesPerId = new HashMap();
            this.root = new ObservedAtInformationNode(-1, Logger.ROOT_LOGGER_NAME, Logger.ROOT_LOGGER_NAME);
            this.maxOperatorSize = 0;
        }

        void add(Publisher<?> publisher, Publisher<?> publisher2, AssemblySnapshot assemblySnapshot) {
            if (assemblySnapshot.isCheckpoint()) {
                if (assemblySnapshot.isLight()) {
                    add(publisher, publisher2, assemblySnapshot.lightPrefix(), (String) Objects.requireNonNull(assemblySnapshot.getDescription()));
                    return;
                }
                String[] strArrExtractOperatorAssemblyInformationParts = Traces.extractOperatorAssemblyInformationParts(assemblySnapshot.toAssemblyInformation());
                if (strArrExtractOperatorAssemblyInformationParts.length > 0) {
                    add(publisher, publisher2, assemblySnapshot.lightPrefix(), strArrExtractOperatorAssemblyInformationParts[strArrExtractOperatorAssemblyInformationParts.length - 1]);
                    return;
                } else {
                    add(publisher, publisher2, assemblySnapshot.lightPrefix(), (String) Objects.requireNonNull(assemblySnapshot.getDescription()));
                    return;
                }
            }
            String[] strArrExtractOperatorAssemblyInformationParts2 = Traces.extractOperatorAssemblyInformationParts(assemblySnapshot.toAssemblyInformation());
            if (strArrExtractOperatorAssemblyInformationParts2.length > 0) {
                add(publisher, publisher2, strArrExtractOperatorAssemblyInformationParts2.length > 1 ? strArrExtractOperatorAssemblyInformationParts2[0] : "", strArrExtractOperatorAssemblyInformationParts2[strArrExtractOperatorAssemblyInformationParts2.length - 1]);
            }
        }

        private void add(Publisher<?> publisher, Publisher<?> publisher2, String str, String str2) {
            ObservedAtInformationNode observedAtInformationNode;
            Scannable scannableOrElse = Scannable.from(publisher2).parents().filter(new Predicate() { // from class: reactor.core.publisher.FluxOnAssembly$OnAssemblyException$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return FluxOnAssembly.OnAssemblyException.lambda$add$0((Scannable) obj);
                }
            }).findFirst().orElse(null);
            int iIdentityHashCode = System.identityHashCode(publisher2);
            int iIdentityHashCode2 = System.identityHashCode(scannableOrElse);
            synchronized (this.nodesPerId) {
                ObservedAtInformationNode observedAtInformationNode2 = this.nodesPerId.get(Integer.valueOf(iIdentityHashCode));
                if (observedAtInformationNode2 != null) {
                    observedAtInformationNode2.incrementCount();
                } else {
                    observedAtInformationNode2 = new ObservedAtInformationNode(iIdentityHashCode, str, str2);
                    this.nodesPerId.put(Integer.valueOf(iIdentityHashCode), observedAtInformationNode2);
                }
                if (scannableOrElse != null && (observedAtInformationNode = this.nodesPerId.get(Integer.valueOf(iIdentityHashCode2))) != null) {
                    observedAtInformationNode.addNode(observedAtInformationNode2);
                } else {
                    this.root.addNode(observedAtInformationNode2);
                }
                int length = observedAtInformationNode2.operator.length();
                if (length > this.maxOperatorSize) {
                    this.maxOperatorSize = length;
                }
            }
        }

        static /* synthetic */ boolean lambda$add$0(Scannable scannable) {
            return scannable instanceof AssemblyOp;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX INFO: renamed from: findPathToLeaves, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
        public void m1963x748b81a0(ObservedAtInformationNode observedAtInformationNode, final List<List<ObservedAtInformationNode>> list) {
            if (observedAtInformationNode.children.isEmpty()) {
                LinkedList linkedList = new LinkedList();
                while (observedAtInformationNode != null && observedAtInformationNode != this.root) {
                    linkedList.add(0, observedAtInformationNode);
                    observedAtInformationNode = observedAtInformationNode.parent;
                }
                list.add(linkedList);
                return;
            }
            observedAtInformationNode.children.forEach(new Consumer() { // from class: reactor.core.publisher.FluxOnAssembly$OnAssemblyException$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m1962x6319d3e3(list, (FluxOnAssembly.ObservedAtInformationNode) obj);
                }
            });
        }

        @Override // java.lang.Throwable
        public String getMessage() {
            synchronized (this.nodesPerId) {
                if (this.root.children.isEmpty()) {
                    return super.getMessage();
                }
                final StringBuilder sbAppend = new StringBuilder(super.getMessage()).append(System.lineSeparator()).append("Error has been observed at the following site(s):").append(System.lineSeparator());
                final ArrayList arrayList = new ArrayList();
                this.root.children.forEach(new Consumer() { // from class: reactor.core.publisher.FluxOnAssembly$OnAssemblyException$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.m1963x748b81a0(arrayList, (FluxOnAssembly.ObservedAtInformationNode) obj);
                    }
                });
                arrayList.forEach(new Consumer() { // from class: reactor.core.publisher.FluxOnAssembly$OnAssemblyException$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.m1965x95f71b22(sbAppend, (List) obj);
                    }
                });
                sbAppend.append("Original Stack Trace:");
                return sbAppend.toString();
            }
        }

        /* JADX INFO: renamed from: lambda$getMessage$4$reactor-core-publisher-FluxOnAssembly$OnAssemblyException */
        /* synthetic */ void m1965x95f71b22(final StringBuilder sb, List list) {
            list.forEach(new Consumer() { // from class: reactor.core.publisher.FluxOnAssembly$OnAssemblyException$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m1964x85414e61(sb, (FluxOnAssembly.ObservedAtInformationNode) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$getMessage$3$reactor-core-publisher-FluxOnAssembly$OnAssemblyException */
        /* synthetic */ void m1964x85414e61(StringBuilder sb, ObservedAtInformationNode observedAtInformationNode) {
            String str;
            boolean z = observedAtInformationNode.parent == null || observedAtInformationNode.parent == this.root;
            sb.append("\t");
            if (!z) {
                str = "|_";
            } else {
                str = "*_";
            }
            sb.append(str);
            char c = z ? '_' : ' ';
            for (int length = observedAtInformationNode.operator.length(); length < this.maxOperatorSize; length++) {
                sb.append(c);
            }
            sb.append(c);
            sb.append(observedAtInformationNode.operator);
            sb.append(" ⇢ ");
            sb.append(observedAtInformationNode.message);
            if (observedAtInformationNode.occurrenceCounter > 0) {
                sb.append(" (observed ").append(observedAtInformationNode.occurrenceCounter + 1).append(" times)");
            }
            sb.append(System.lineSeparator());
        }

        @Override // java.lang.Throwable
        public String toString() {
            String localizedMessage = getLocalizedMessage();
            if (localizedMessage == null) {
                return "The stacktrace should have been enhanced by Reactor, but there was no message in OnAssemblyException";
            }
            return "The stacktrace has been enhanced by Reactor, refer to additional information below: " + localizedMessage;
        }
    }

    static class OnAssemblySubscriber<T> implements InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Publisher<?> current;
        int fusionMode;
        final Publisher<?> parent;

        /* JADX INFO: renamed from: qs */
        Fuseable.QueueSubscription<T> f2157qs;

        /* JADX INFO: renamed from: s */
        Subscription f2158s;
        final AssemblySnapshot snapshotStack;

        OnAssemblySubscriber(CoreSubscriber<? super T> coreSubscriber, AssemblySnapshot assemblySnapshot, Publisher<?> publisher, Publisher<?> publisher2) {
            this.actual = coreSubscriber;
            this.snapshotStack = assemblySnapshot;
            this.parent = publisher;
            this.current = publisher2;
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2158s : attr == Scannable.Attr.ACTUAL_METADATA ? Boolean.valueOf(!this.snapshotStack.isCheckpoint) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        public String toString() {
            return this.snapshotStack.operatorAssemblyInformation();
        }

        @Override // reactor.core.Scannable
        public String stepName() {
            return toString();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable th) {
            this.actual.onError(fail(th));
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            this.actual.onComplete();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public final int requestFusion(int i) {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2157qs;
            if (queueSubscription == null) {
                return 0;
            }
            int iRequestFusion = queueSubscription.requestFusion(i);
            if (iRequestFusion != 0) {
                this.fusionMode = iRequestFusion;
            }
            return iRequestFusion;
        }

        final Throwable fail(Throwable th) {
            OnAssemblyException onAssemblyException;
            boolean zIsLight = this.snapshotStack.isLight();
            Throwable[] suppressed = th.getSuppressed();
            int length = suppressed.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    onAssemblyException = null;
                    break;
                }
                Throwable th2 = suppressed[i];
                if (th2 instanceof OnAssemblyException) {
                    onAssemblyException = (OnAssemblyException) th2;
                    break;
                }
                i++;
            }
            if (onAssemblyException == null) {
                if (zIsLight) {
                    onAssemblyException = new OnAssemblyException("");
                } else {
                    StringBuilder sb = new StringBuilder();
                    FluxOnAssembly.fillStacktraceHeader(sb, this.parent.getClass(), this.snapshotStack.getDescription());
                    sb.append(this.snapshotStack.toAssemblyInformation().replaceFirst("\\n$", ""));
                    onAssemblyException = new OnAssemblyException(sb.toString());
                }
                th = Exceptions.addSuppressed(th, onAssemblyException);
                StackTraceElement[] stackTrace = th.getStackTrace();
                if (stackTrace.length > 0) {
                    StackTraceElement[] stackTraceElementArr = new StackTraceElement[stackTrace.length];
                    int i2 = 0;
                    for (StackTraceElement stackTraceElement : stackTrace) {
                        String className = stackTraceElement.getClassName();
                        if (!className.startsWith("reactor.core.publisher.") || !className.contains("OnAssembly")) {
                            stackTraceElementArr[i2] = stackTraceElement;
                            i2++;
                        }
                    }
                    onAssemblyException.setStackTrace((StackTraceElement[]) Arrays.copyOf(stackTraceElementArr, i2));
                    th.setStackTrace(new StackTraceElement[]{stackTrace[0]});
                }
            }
            onAssemblyException.add(this.parent, this.current, this.snapshotStack);
            return th;
        }

        @Override // java.util.Collection
        public final boolean isEmpty() {
            try {
                return this.f2157qs.isEmpty();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                throw Exceptions.propagate(fail(th));
            }
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public final void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2158s, subscription)) {
                this.f2158s = subscription;
                this.f2157qs = Operators.m1969as(subscription);
                this.actual.onSubscribe(this);
            }
        }

        @Override // java.util.Collection
        public final int size() {
            return this.f2157qs.size();
        }

        @Override // java.util.Collection
        public final void clear() {
            this.f2157qs.clear();
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            this.f2158s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.f2158s.cancel();
        }

        @Override // java.util.Queue
        @Nullable
        public final T poll() {
            try {
                return this.f2157qs.poll();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                throw Exceptions.propagate(fail(th));
            }
        }
    }

    static final class OnAssemblyConditionalSubscriber<T> extends OnAssemblySubscriber<T> implements Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> actualCS;

        OnAssemblyConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, AssemblySnapshot assemblySnapshot, Publisher<?> publisher, Publisher<?> publisher2) {
            super(conditionalSubscriber, assemblySnapshot, publisher, publisher2);
            this.actualCS = conditionalSubscriber;
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            return this.actualCS.tryOnNext(t);
        }
    }
}
