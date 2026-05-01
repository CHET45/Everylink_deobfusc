package com.jakewharton.retrofit2.adapter.rxjava2;

import io.reactivex.Scheduler;
import java.lang.reflect.Type;
import retrofit2.CallAdapter;

/* JADX INFO: loaded from: classes3.dex */
final class RxJava2CallAdapter implements CallAdapter<Object> {
    private final boolean isBody;
    private final boolean isCompletable;
    private final boolean isFlowable;
    private final boolean isMaybe;
    private final boolean isResult;
    private final boolean isSingle;
    private final Type responseType;
    private final Scheduler scheduler;

    RxJava2CallAdapter(Type type, Scheduler scheduler, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.responseType = type;
        this.scheduler = scheduler;
        this.isResult = z;
        this.isBody = z2;
        this.isFlowable = z3;
        this.isSingle = z4;
        this.isMaybe = z5;
        this.isCompletable = z6;
    }

    @Override // retrofit2.CallAdapter
    public Type responseType() {
        return this.responseType;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001e  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002d  */
    @Override // retrofit2.CallAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <R> java.lang.Object adapt(retrofit2.Call<R> r2) {
        /*
            r1 = this;
            com.jakewharton.retrofit2.adapter.rxjava2.CallObservable r0 = new com.jakewharton.retrofit2.adapter.rxjava2.CallObservable
            r0.<init>(r2)
            boolean r2 = r1.isResult
            if (r2 == 0) goto L10
            com.jakewharton.retrofit2.adapter.rxjava2.ResultObservable r2 = new com.jakewharton.retrofit2.adapter.rxjava2.ResultObservable
            r2.<init>(r0)
        Le:
            r0 = r2
            goto L1a
        L10:
            boolean r2 = r1.isBody
            if (r2 == 0) goto L1a
            com.jakewharton.retrofit2.adapter.rxjava2.BodyObservable r2 = new com.jakewharton.retrofit2.adapter.rxjava2.BodyObservable
            r2.<init>(r0)
            goto Le
        L1a:
            io.reactivex.Scheduler r2 = r1.scheduler
            if (r2 == 0) goto L22
            io.reactivex.Observable r0 = r0.subscribeOn(r2)
        L22:
            boolean r2 = r1.isFlowable
            if (r2 == 0) goto L2d
            io.reactivex.BackpressureStrategy r2 = io.reactivex.BackpressureStrategy.LATEST
            io.reactivex.Flowable r2 = r0.toFlowable(r2)
            return r2
        L2d:
            boolean r2 = r1.isSingle
            if (r2 == 0) goto L36
            io.reactivex.Single r2 = r0.singleOrError()
            return r2
        L36:
            boolean r2 = r1.isMaybe
            if (r2 == 0) goto L3f
            io.reactivex.Maybe r2 = r0.singleElement()
            return r2
        L3f:
            boolean r2 = r1.isCompletable
            if (r2 == 0) goto L48
            io.reactivex.Completable r2 = r0.ignoreElements()
            return r2
        L48:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapter.adapt(retrofit2.Call):java.lang.Object");
    }
}
