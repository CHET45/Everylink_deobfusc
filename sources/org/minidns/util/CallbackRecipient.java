package org.minidns.util;

/* JADX INFO: loaded from: classes4.dex */
public interface CallbackRecipient<V, E> {
    CallbackRecipient<V, E> onError(ExceptionCallback<E> exceptionCallback);

    CallbackRecipient<V, E> onSuccess(SuccessCallback<V> successCallback);
}
