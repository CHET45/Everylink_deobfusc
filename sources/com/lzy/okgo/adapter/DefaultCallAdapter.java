package com.lzy.okgo.adapter;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultCallAdapter<T> implements CallAdapter<Call<T>> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lzy.okgo.adapter.CallAdapter
    public <R> Call<T> adapt(Call<R> call) {
        return call;
    }

    public static <T> DefaultCallAdapter<T> create() {
        return new DefaultCallAdapter<>();
    }
}
