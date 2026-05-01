package com.jieli.jl_bt_ota.tool;

import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.interfaces.CommandCallback;
import com.jieli.jl_bt_ota.interfaces.IActionCallback;
import com.jieli.jl_bt_ota.interfaces.rcsp.IHandleResult;
import com.jieli.jl_bt_ota.model.OTAError;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.model.base.CommandBase;

/* JADX INFO: loaded from: classes3.dex */
public class CmdResultCallback<T> implements CommandCallback {
    protected final IActionCallback<T> callback;
    protected final String funcName;
    protected final IHandleResult<T> handle;

    public CmdResultCallback(String str, IActionCallback<T> iActionCallback, IHandleResult<T> iHandleResult) {
        if (iHandleResult == null) {
            throw new NullPointerException("IHandleResult is null.");
        }
        this.funcName = str;
        this.callback = iActionCallback;
        this.handle = iHandleResult;
    }

    @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
    public void onCommandResponse(CommandBase commandBase) {
        if (commandBase.getStatus() != 0) {
            onErrCode(OTAError.buildError(ErrorCode.SUB_ERR_RESPONSE_BAD_STATUS, commandBase.getStatus(), "status = " + commandBase.getStatus()));
            return;
        }
        int iHasResult = this.handle.hasResult(commandBase);
        if (iHasResult != 0) {
            onErrCode(OTAError.buildError(ErrorCode.SUB_ERR_RESPONSE_BAD_RESULT, iHasResult, "result = " + iHasResult));
            return;
        }
        T tHandleResult = this.handle.handleResult(commandBase);
        IActionCallback<T> iActionCallback = this.callback;
        if (iActionCallback != null) {
            iActionCallback.onSuccess(tHandleResult);
        }
    }

    @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
    public void onErrCode(BaseError baseError) {
        IActionCallback<T> iActionCallback = this.callback;
        if (iActionCallback != null) {
            iActionCallback.onError(baseError);
        }
    }
}
