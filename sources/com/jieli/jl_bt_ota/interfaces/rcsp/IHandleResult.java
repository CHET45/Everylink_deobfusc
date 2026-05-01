package com.jieli.jl_bt_ota.interfaces.rcsp;

import com.jieli.jl_bt_ota.model.base.CommandBase;

/* JADX INFO: loaded from: classes3.dex */
public interface IHandleResult<T> {
    T handleResult(CommandBase commandBase);

    int hasResult(CommandBase commandBase);
}
