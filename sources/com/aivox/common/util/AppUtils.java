package com.aivox.common.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;
import androidx.fragment.app.FragmentActivity;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.BaseDataHandle;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.common.Constant;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.AccountAction;
import com.aivox.base.router.action.SetAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.http.ApiService;
import com.aivox.common.http.RetrofitServiceManager;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.pay.TimePurchaseFragment;
import com.aivox.common.socket.WebSocketManager;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/* JADX INFO: loaded from: classes.dex */
public class AppUtils {

    public interface ResponseCancelCallback {
        void cancelCallback();
    }

    public interface ResponseErrorCallback {
        void callback();
    }

    public static boolean checkHttpCode(Context context) {
        String codeMsg;
        int code = BaseDataHandle.getIns().getCode();
        LogUtil.m338i("code:" + code);
        if (code == Constant.SeverErrorCode.UN_LOGIN.code || code == Constant.SeverErrorCode.TICKET_INVALID.code || code == Constant.SeverErrorCode.JWT_SIGNATURE_ERROR.code || code == Constant.SeverErrorCode.JWT_EXPIRE_ERROR.code || code == Constant.SeverErrorCode.USER_NOT_FOUND.code || code == Constant.SeverErrorCode.NEED_LOGIN_AGAIN.code) {
            showLogoutDialog(context, Integer.valueOf(C0874R.string.login_time_out));
            return false;
        }
        if (code == Constant.SeverErrorCode.NORMAL_USER_PERMISSION_DENIED.code) {
            showVipSubDialog(context);
            return true;
        }
        if (!BaseStringUtil.isNotEmpty(BaseDataHandle.getIns().getMsg()) || code == Constant.SeverErrorCode.SUCCESS.code) {
            return true;
        }
        Integer numValueOf = Integer.valueOf(C0874R.string.reminder);
        if (BaseStringUtil.isEmpty(Constant.SeverErrorCode.getCodeMsg(code))) {
            codeMsg = BaseDataHandle.getIns().getMsg();
        } else {
            codeMsg = Constant.SeverErrorCode.getCodeMsg(code);
        }
        DialogUtils.showDialogWithBtnIds(context, numValueOf, codeMsg, null, null, false, false, C0874R.string.sure, C0874R.string.sure);
        return true;
    }

    public static boolean fileTransTimeCheck(final FragmentActivity fragmentActivity, AudioInfoBean audioInfoBean, final boolean z) {
        if (audioInfoBean == null || audioInfoBean.getAudioInfo() == null) {
            return true;
        }
        if (((Integer) SPUtil.get(SPUtil.LEFT_CURRENCY_TIME, 0)).intValue() <= 0) {
            DialogUtils.showDialogWithBtnIds(fragmentActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.trans_time_exhaust), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.util.AppUtils$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    AppUtils.jumpToPurchase(fragmentActivity, z);
                }
            }, true, true, C0874R.string.know_and_continue, z ? C0874R.string.to_recharge : C0874R.string.join_vip);
            return false;
        }
        if (audioInfoBean.getAudioInfo().getAudioTimeDuration().intValue() <= ((Integer) SPUtil.get(SPUtil.LEFT_CURRENCY_TIME, 0)).intValue()) {
            return true;
        }
        DialogUtils.showDialogWithBtnIds(fragmentActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.trans_time_not_enough), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.util.AppUtils$$ExternalSyntheticLambda1
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                AppUtils.jumpToPurchase(fragmentActivity, z);
            }
        }, true, true, C0874R.string.know_and_continue, z ? C0874R.string.to_recharge : C0874R.string.join_vip);
        return false;
    }

    public static void showVipSubDialog(final Context context) {
        DialogUtils.showDialogWithBtnIds(context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.usage_limit_exceeded), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.util.AppUtils$$ExternalSyntheticLambda5
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context2, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                AppUtils.jumpToPurchase((FragmentActivity) context, false);
            }
        }, true, true, C0874R.string.cancel, C0874R.string.confirm);
    }

    public static void jumpToPurchase(FragmentActivity fragmentActivity, boolean z) {
        if (z) {
            TimePurchaseFragment.newInstance().show(fragmentActivity.getSupportFragmentManager(), "TimePurchaseFragment");
        } else {
            ARouterUtils.startWithContext(fragmentActivity, SetAction.SET_VIP);
            fragmentActivity.overridePendingTransition(C0874R.anim.slide_in_from_bottom, 0);
        }
    }

    public static void showError(Context context, Throwable th, ResponseErrorCallback responseErrorCallback) {
        showError(context, th, responseErrorCallback, null);
    }

    public static void showError(Context context, Throwable th, final ResponseErrorCallback responseErrorCallback, final ResponseCancelCallback responseCancelCallback) {
        BaseAppUtils.printErrorMsg(th);
        LogUtils.m499e(th.toString());
        DialogUtils.showDialogWithBtnIds(context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.service_error), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.util.AppUtils$$ExternalSyntheticLambda3
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context2, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                AppUtils.lambda$showError$3(responseCancelCallback, context2, dialogBuilder, dialog, i, i2, editText);
            }
        }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.util.AppUtils$$ExternalSyntheticLambda4
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context2, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                AppUtils.lambda$showError$4(responseErrorCallback, context2, dialogBuilder, dialog, i, i2, editText);
            }
        }, true, true, C0874R.string.cancel, C0874R.string.wifi_connect_bt_search_fail_retry);
    }

    static /* synthetic */ void lambda$showError$3(ResponseCancelCallback responseCancelCallback, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        if (responseCancelCallback != null) {
            responseCancelCallback.cancelCallback();
        }
    }

    static /* synthetic */ void lambda$showError$4(ResponseErrorCallback responseErrorCallback, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        if (responseErrorCallback != null) {
            responseErrorCallback.callback();
        }
    }

    public static void showLogoutDialog(final Context context, Object obj) {
        DialogUtils.showDialogWithDefBtnAndSingleListener(context, Integer.valueOf(C0874R.string.reminder), obj, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.util.AppUtils$$ExternalSyntheticLambda2
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context2, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                AppUtils.lambda$showLogoutDialog$5(context, context2, dialogBuilder, dialog, i, i2, editText);
            }
        }, false, false);
    }

    static /* synthetic */ void lambda$showLogoutDialog$5(Context context, Context context2, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        if (context.getClass() != ARouterUtils.getClass(AccountAction.ONE_KEY_LOGIN)) {
            logout(context);
        }
    }

    public static void logout(Context context) {
        CodeCountDownManager.getInstance().stopTicking();
        GoogleSignIn.getClient(context, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(BaseGlobalConfig.getGoogleServiceClientId()).requestEmail().build()).signOut();
        WebSocketManager.stopService(context);
        SPUtil.clearUserSp();
        BaseDataHandle.getIns().setUid("");
        BaseUtil.setApiService((ApiService) new RetrofitServiceManager().create(ApiService.class));
        AppManager.getAppManager().finishAllActivity();
        ARouterUtils.startWithContext(context, AccountAction.ONE_KEY_LOGIN);
        DeviceFileSyncManager.getInstance().release();
    }
}
