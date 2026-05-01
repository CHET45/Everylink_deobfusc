package com.aivox.base.httprx.rxweaver.core;

import com.aivox.base.httprx.rxweaver.func.Suppiler;
import com.aivox.base.httprx.rxweaver.retry.FlowableRetryDelay;
import com.aivox.base.httprx.rxweaver.retry.ObservableRetryDelay;
import com.aivox.base.httprx.rxweaver.retry.RetryConfig;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes.dex */
public class GlobalErrorTransformer<T> implements ObservableTransformer<T, T>, FlowableTransformer<T, T>, SingleTransformer<T, T>, MaybeTransformer<T, T>, CompletableTransformer {
    private static Suppiler<Scheduler> SCHEDULER_PROVIDER_DEFAULT = new Suppiler<Scheduler>() { // from class: com.aivox.base.httprx.rxweaver.core.GlobalErrorTransformer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.aivox.base.httprx.rxweaver.func.Suppiler
        public Scheduler call() {
            return AndroidSchedulers.mainThread();
        }
    };
    private Suppiler<Scheduler> downStreamSchedulerProvider;
    private Consumer<Throwable> globalDoOnErrorConsumer;
    private Function<Throwable, Observable<T>> globalOnErrorResume;
    private Function<T, Observable<T>> globalOnNextRetryInterceptor;
    private Function<Throwable, RetryConfig> retryConfigProvider;
    private Suppiler<Scheduler> upStreamSchedulerProvider;

    /* JADX WARN: Illegal instructions before constructor call */
    public GlobalErrorTransformer(Function<T, Observable<T>> function, Function<Throwable, Observable<T>> function2, Function<Throwable, RetryConfig> function3, Consumer<Throwable> consumer) {
        Suppiler<Scheduler> suppiler = SCHEDULER_PROVIDER_DEFAULT;
        this(suppiler, suppiler, function, function2, function3, consumer);
    }

    public GlobalErrorTransformer(Suppiler<Scheduler> suppiler, Suppiler<Scheduler> suppiler2, Function<T, Observable<T>> function, Function<Throwable, Observable<T>> function2, Function<Throwable, RetryConfig> function3, Consumer<Throwable> consumer) {
        this.upStreamSchedulerProvider = suppiler;
        this.downStreamSchedulerProvider = suppiler2;
        this.globalOnNextRetryInterceptor = function;
        this.globalOnErrorResume = function2;
        this.retryConfigProvider = function3;
        this.globalDoOnErrorConsumer = consumer;
    }

    @Override // io.reactivex.ObservableTransformer
    public ObservableSource<T> apply(Observable<T> observable) {
        return observable.observeOn(this.upStreamSchedulerProvider.call()).flatMap(new Function<T, ObservableSource<T>>() { // from class: com.aivox.base.httprx.rxweaver.core.GlobalErrorTransformer.3
            @Override // io.reactivex.functions.Function
            public ObservableSource<T> apply(T t) throws Exception {
                return (ObservableSource) GlobalErrorTransformer.this.globalOnNextRetryInterceptor.apply(t);
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() { // from class: com.aivox.base.httprx.rxweaver.core.GlobalErrorTransformer.2
            @Override // io.reactivex.functions.Function
            public ObservableSource<? extends T> apply(Throwable th) throws Exception {
                return (ObservableSource) GlobalErrorTransformer.this.globalOnErrorResume.apply(th);
            }
        }).retryWhen(new ObservableRetryDelay(this.retryConfigProvider)).doOnError(this.globalDoOnErrorConsumer).observeOn(this.downStreamSchedulerProvider.call());
    }

    @Override // io.reactivex.CompletableTransformer
    public CompletableSource apply(Completable completable) {
        return completable.observeOn(this.upStreamSchedulerProvider.call()).onErrorResumeNext(new Function<Throwable, CompletableSource>() { // from class: com.aivox.base.httprx.rxweaver.core.GlobalErrorTransformer.4
            @Override // io.reactivex.functions.Function
            public CompletableSource apply(Throwable th) throws Exception {
                return ((Observable) GlobalErrorTransformer.this.globalOnErrorResume.apply(th)).ignoreElements();
            }
        }).retryWhen(new FlowableRetryDelay(this.retryConfigProvider)).doOnError(this.globalDoOnErrorConsumer).observeOn(this.downStreamSchedulerProvider.call());
    }

    @Override // io.reactivex.FlowableTransformer
    public Publisher<T> apply(Flowable<T> flowable) {
        return flowable.observeOn(this.upStreamSchedulerProvider.call()).flatMap(new Function<T, Publisher<T>>() { // from class: com.aivox.base.httprx.rxweaver.core.GlobalErrorTransformer.6
            @Override // io.reactivex.functions.Function
            public Publisher<T> apply(T t) throws Exception {
                return ((Observable) GlobalErrorTransformer.this.globalOnNextRetryInterceptor.apply(t)).toFlowable(BackpressureStrategy.BUFFER);
            }
        }).onErrorResumeNext(new Function<Throwable, Publisher<T>>() { // from class: com.aivox.base.httprx.rxweaver.core.GlobalErrorTransformer.5
            @Override // io.reactivex.functions.Function
            public Publisher<T> apply(Throwable th) throws Exception {
                return ((Observable) GlobalErrorTransformer.this.globalOnErrorResume.apply(th)).toFlowable(BackpressureStrategy.BUFFER);
            }
        }).retryWhen(new FlowableRetryDelay(this.retryConfigProvider)).doOnError(this.globalDoOnErrorConsumer).observeOn(this.downStreamSchedulerProvider.call());
    }

    @Override // io.reactivex.MaybeTransformer
    public MaybeSource<T> apply(Maybe<T> maybe) {
        return maybe.observeOn(this.upStreamSchedulerProvider.call()).flatMap(new Function<T, MaybeSource<T>>() { // from class: com.aivox.base.httprx.rxweaver.core.GlobalErrorTransformer.8
            @Override // io.reactivex.functions.Function
            public MaybeSource<T> apply(T t) throws Exception {
                return ((Observable) GlobalErrorTransformer.this.globalOnNextRetryInterceptor.apply(t)).firstElement();
            }
        }).onErrorResumeNext(new Function<Throwable, MaybeSource<T>>() { // from class: com.aivox.base.httprx.rxweaver.core.GlobalErrorTransformer.7
            @Override // io.reactivex.functions.Function
            public MaybeSource<T> apply(Throwable th) throws Exception {
                return ((Observable) GlobalErrorTransformer.this.globalOnErrorResume.apply(th)).firstElement();
            }
        }).retryWhen(new FlowableRetryDelay(this.retryConfigProvider)).doOnError(this.globalDoOnErrorConsumer).observeOn(this.downStreamSchedulerProvider.call());
    }

    @Override // io.reactivex.SingleTransformer
    public SingleSource<T> apply(Single<T> single) {
        return single.observeOn(this.upStreamSchedulerProvider.call()).flatMap(new Function<T, SingleSource<T>>() { // from class: com.aivox.base.httprx.rxweaver.core.GlobalErrorTransformer.10
            @Override // io.reactivex.functions.Function
            public SingleSource<T> apply(T t) throws Exception {
                return ((Observable) GlobalErrorTransformer.this.globalOnNextRetryInterceptor.apply(t)).firstOrError();
            }
        }).onErrorResumeNext(new Function<Throwable, SingleSource<T>>() { // from class: com.aivox.base.httprx.rxweaver.core.GlobalErrorTransformer.9
            @Override // io.reactivex.functions.Function
            public SingleSource<T> apply(Throwable th) throws Exception {
                return ((Observable) GlobalErrorTransformer.this.globalOnErrorResume.apply(th)).firstOrError();
            }
        }).retryWhen(new FlowableRetryDelay(this.retryConfigProvider)).doOnError(this.globalDoOnErrorConsumer).observeOn(this.downStreamSchedulerProvider.call());
    }
}
