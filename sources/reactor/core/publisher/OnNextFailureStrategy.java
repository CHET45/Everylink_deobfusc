package reactor.core.publisher;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import reactor.core.Exceptions;
import reactor.core.publisher.OnNextFailureStrategy;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
interface OnNextFailureStrategy extends BiFunction<Throwable, Object, Throwable>, BiPredicate<Throwable, Object> {
    public static final String KEY_ON_NEXT_ERROR_STRATEGY = "reactor.onNextError.localStrategy";
    public static final OnNextFailureStrategy STOP = new OnNextFailureStrategy() { // from class: reactor.core.publisher.OnNextFailureStrategy.1
        @Override // reactor.core.publisher.OnNextFailureStrategy, java.util.function.BiPredicate
        public boolean test(Throwable th, @Nullable Object obj) {
            return false;
        }

        @Override // reactor.core.publisher.OnNextFailureStrategy
        public Throwable process(Throwable th, @Nullable Object obj, Context context) {
            Exceptions.throwIfFatal(th);
            IllegalStateException illegalStateException = new IllegalStateException("STOP strategy cannot process errors");
            illegalStateException.addSuppressed(th);
            return illegalStateException;
        }
    };
    public static final OnNextFailureStrategy RESUME_DROP = new ResumeDropStrategy(null);

    @Nullable
    Throwable process(Throwable th, @Nullable Object obj, Context context);

    @Override // java.util.function.BiPredicate
    boolean test(Throwable th, @Nullable Object obj);

    @Override // java.util.function.BiFunction
    @Nullable
    default Throwable apply(Throwable th, @Nullable Object obj) {
        return process(th, obj, Context.empty());
    }

    static OnNextFailureStrategy stop() {
        return STOP;
    }

    static OnNextFailureStrategy resumeDrop() {
        return RESUME_DROP;
    }

    static OnNextFailureStrategy resumeDropIf(Predicate<Throwable> predicate) {
        return new ResumeDropStrategy(predicate);
    }

    static OnNextFailureStrategy resume(BiConsumer<Throwable, Object> biConsumer) {
        return new ResumeStrategy(null, biConsumer);
    }

    static OnNextFailureStrategy resumeIf(Predicate<Throwable> predicate, BiConsumer<Throwable, Object> biConsumer) {
        return new ResumeStrategy(predicate, biConsumer);
    }

    public static final class ResumeStrategy implements OnNextFailureStrategy {
        final BiConsumer<Throwable, Object> errorConsumer;
        final Predicate<Throwable> errorPredicate;

        ResumeStrategy(@Nullable Predicate<Throwable> predicate, BiConsumer<Throwable, Object> biConsumer) {
            this.errorPredicate = predicate;
            this.errorConsumer = biConsumer;
        }

        @Override // reactor.core.publisher.OnNextFailureStrategy, java.util.function.BiPredicate
        public boolean test(Throwable th, @Nullable Object obj) {
            Predicate<Throwable> predicate = this.errorPredicate;
            return predicate == null || predicate.test(th);
        }

        @Override // reactor.core.publisher.OnNextFailureStrategy
        @Nullable
        public Throwable process(Throwable th, @Nullable Object obj, Context context) {
            Predicate<Throwable> predicate = this.errorPredicate;
            if (predicate == null) {
                Exceptions.throwIfFatal(th);
            } else if (!predicate.test(th)) {
                Exceptions.throwIfFatal(th);
                return th;
            }
            try {
                this.errorConsumer.accept(th, obj);
                return null;
            } catch (Throwable th2) {
                return Exceptions.addSuppressed(th2, th);
            }
        }
    }

    public static final class ResumeDropStrategy implements OnNextFailureStrategy {
        final Predicate<Throwable> errorPredicate;

        ResumeDropStrategy(@Nullable Predicate<Throwable> predicate) {
            this.errorPredicate = predicate;
        }

        @Override // reactor.core.publisher.OnNextFailureStrategy, java.util.function.BiPredicate
        public boolean test(Throwable th, @Nullable Object obj) {
            Predicate<Throwable> predicate = this.errorPredicate;
            return predicate == null || predicate.test(th);
        }

        @Override // reactor.core.publisher.OnNextFailureStrategy
        @Nullable
        public Throwable process(Throwable th, @Nullable Object obj, Context context) {
            Predicate<Throwable> predicate = this.errorPredicate;
            if (predicate == null) {
                Exceptions.throwIfFatal(th);
            } else if (!predicate.test(th)) {
                Exceptions.throwIfFatal(th);
                return th;
            }
            if (obj != null) {
                try {
                    Operators.onNextDropped(obj, context);
                } catch (Throwable th2) {
                    return Exceptions.addSuppressed(th2, th);
                }
            }
            Operators.onErrorDropped(th, context);
            return null;
        }
    }

    public static final class LambdaOnNextErrorStrategy implements OnNextFailureStrategy {
        private final BiPredicate<? super Throwable, Object> delegatePredicate;
        private final BiFunction<? super Throwable, Object, ? extends Throwable> delegateProcessor;

        static /* synthetic */ boolean lambda$new$0(Throwable th, Object obj) {
            return true;
        }

        public LambdaOnNextErrorStrategy(BiFunction<? super Throwable, Object, ? extends Throwable> biFunction) {
            this.delegateProcessor = biFunction;
            if (biFunction instanceof BiPredicate) {
                this.delegatePredicate = (BiPredicate) biFunction;
            } else {
                this.delegatePredicate = new BiPredicate() { // from class: reactor.core.publisher.OnNextFailureStrategy$LambdaOnNextErrorStrategy$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiPredicate
                    public final boolean test(Object obj, Object obj2) {
                        return OnNextFailureStrategy.LambdaOnNextErrorStrategy.lambda$new$0((Throwable) obj, obj2);
                    }
                };
            }
        }

        @Override // reactor.core.publisher.OnNextFailureStrategy, java.util.function.BiPredicate
        public boolean test(Throwable th, @Nullable Object obj) {
            return this.delegatePredicate.test(th, obj);
        }

        @Override // reactor.core.publisher.OnNextFailureStrategy
        @Nullable
        public Throwable process(Throwable th, @Nullable Object obj, Context context) {
            return this.delegateProcessor.apply(th, obj);
        }
    }
}
