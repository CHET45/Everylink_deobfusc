package com.aivox.base.http;

import com.aivox.base.common.BaseDataHandle;
import io.reactivex.functions.Function;

/* JADX INFO: loaded from: classes.dex */
public class PayLoad<T> implements Function<BaseResponse<T>, T> {
    @Override // io.reactivex.functions.Function
    public T apply(BaseResponse<T> baseResponse) {
        BaseDataHandle.getIns().setCode(baseResponse.status);
        BaseDataHandle.getIns().setMsg(baseResponse.msg);
        return baseResponse.data;
    }
}
