package com.jieli.jl_bt_ota.interfaces;

import com.jieli.jl_bt_ota.model.base.BaseError;

/* JADX INFO: loaded from: classes3.dex */
public interface IActionCallback<T> {
    void onError(BaseError baseError);

    void onSuccess(T t);
}
