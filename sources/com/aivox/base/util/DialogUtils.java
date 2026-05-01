package com.aivox.base.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import com.aivox.base.C0874R;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.base.view.dialog.LoadingDialog;
import com.aivox.base.view.dialog.ProgressDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/* JADX INFO: loaded from: classes.dex */
public class DialogUtils {
    private static LoadingDialog loadingDialog;
    private static DialogBuilder mBuilder;
    private static ProgressDialog mProgressDialog;

    static /* synthetic */ void lambda$showToRechargeStorageDialog$0(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$showToRechargeStorageDialog$1(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    private static DialogBuilder getBuilder(Context context) {
        DialogBuilder dialogBuilder = mBuilder;
        if (dialogBuilder != null && dialogBuilder.isShow()) {
            mBuilder.setDismiss();
        }
        DialogBuilder dialogBuilder2 = new DialogBuilder(context);
        mBuilder = dialogBuilder2;
        return dialogBuilder2;
    }

    public static LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }

    public static BottomSheetDialog showBottomSheetDialog(Context context, BaseDialogViewWrapper baseDialogViewWrapper) {
        return showBottomSheetDialog(context, baseDialogViewWrapper, C0874R.style.BottomSheetDialogWhiteNav);
    }

    public static BottomSheetDialog showBottomSheetDialog(Context context, BaseDialogViewWrapper baseDialogViewWrapper, int i) {
        return showBottomSheetDialog(context, baseDialogViewWrapper, i, null);
    }

    public static BottomSheetDialog showBottomSheetDialog(Context context, BaseDialogViewWrapper baseDialogViewWrapper, int i, DialogInterface.OnDismissListener onDismissListener) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, i);
        baseDialogViewWrapper.setDialog(bottomSheetDialog);
        bottomSheetDialog.setContentView(baseDialogViewWrapper);
        ((ViewGroup) baseDialogViewWrapper.getParent()).setBackground(null);
        WindowManager.LayoutParams attributes = bottomSheetDialog.getWindow().getAttributes();
        attributes.dimAmount = 0.4f;
        bottomSheetDialog.getWindow().setAttributes(attributes);
        bottomSheetDialog.show();
        bottomSheetDialog.setOnDismissListener(onDismissListener);
        return bottomSheetDialog;
    }

    public static BottomSheetDialog getBottomSheetDialog(Context context) {
        return new BottomSheetDialog(context, C0874R.style.BottomSheetDialogWhiteNav);
    }

    public static void showEditText(Context context, Object obj, String str, DialogBuilder.DialogButtonClickListener dialogButtonClickListener, DialogBuilder.DialogButtonClickListener dialogButtonClickListener2, boolean z) {
        if (BaseAppUtils.isContextExisted(context)) {
            mBuilder = getBuilder(context).setCancelOutside(true).setTitle(obj).setEditTextEnable().setEditText(BaseStringUtil.isEmpty(str) ? "" : str).setEditTextHint(BaseStringUtil.isEmpty(str) ? "" : str).setEditTextInputType(z ? 128 : 0).setMessage(str).setButtons(Integer.valueOf(C0874R.string.cancel), Integer.valueOf(C0874R.string.sure), dialogButtonClickListener, dialogButtonClickListener2).create().show();
        }
    }

    public static void showMsgNoTitle(Context context, Object obj, DialogBuilder.DialogButtonClickListener dialogButtonClickListener, DialogBuilder.DialogButtonClickListener dialogButtonClickListener2, boolean z, Object obj2, Object obj3, boolean z2) {
        if (BaseAppUtils.isContextExisted(context)) {
            DialogBuilder cancelOutside = getBuilder(context).setTitle(obj).setTitleSize(18).hideMessage().setCancelable(z2).setCancelOutside(z2);
            if (!z) {
                obj3 = "";
            }
            if (!z) {
                dialogButtonClickListener = null;
            }
            mBuilder = cancelOutside.setButtons(obj3, obj2, dialogButtonClickListener, dialogButtonClickListener2).create().show();
        }
    }

    public static void showMsgNoTitle(Context context, Object obj, DialogBuilder.DialogButtonClickListener dialogButtonClickListener, DialogBuilder.DialogButtonClickListener dialogButtonClickListener2, boolean z, Object obj2, Object obj3) {
        showMsgNoTitle(context, obj, dialogButtonClickListener, dialogButtonClickListener2, z, obj2, obj3, true);
    }

    public static void showMsgImg(Context context, String str, String str2, DialogBuilder.DialogButtonClickListener dialogButtonClickListener, boolean z) {
        if (BaseAppUtils.isContextExisted(context)) {
            mBuilder = getBuilder(context).setTitleImg(str).setMessage(str2).setButtons(z ? Integer.valueOf(C0874R.string.cancel) : "", Integer.valueOf(C0874R.string.sure), null, dialogButtonClickListener).create().show();
        }
    }

    public static void showMsgNoBtn(Context context, Object obj, Object obj2, boolean z) {
        if (BaseAppUtils.isContextExisted(context)) {
            mBuilder = getBuilder(context).setTitle(obj).setMessage(obj2).setCancelable(z).setCancelOutside(z).setNoButtons().create().show();
        }
    }

    public static void dismissAll() {
        DialogBuilder dialogBuilder = mBuilder;
        if (dialogBuilder == null || !dialogBuilder.isShow()) {
            return;
        }
        mBuilder.setDismiss();
    }

    public static void showDialogWithDefBtnAndSingleListener(Context context, Object obj, Object obj2, DialogBuilder.DialogButtonClickListener dialogButtonClickListener, boolean z, boolean z2) {
        generateAndShowDialogByBuilder(context, obj, obj2, (DialogBuilder.DialogButtonClickListener) null, dialogButtonClickListener, z, z2, false, C0874R.string.cancel, C0874R.string.sure, -1);
    }

    public static void showDialogWithDefBtn(Context context, Object obj, Object obj2, DialogBuilder.DialogButtonClickListener dialogButtonClickListener, DialogBuilder.DialogButtonClickListener dialogButtonClickListener2, boolean z, boolean z2) {
        generateAndShowDialogByBuilder(context, obj, obj2, dialogButtonClickListener, dialogButtonClickListener2, z, z2, false, C0874R.string.cancel, C0874R.string.sure, -1);
    }

    public static void showDialogWithBtnIds(Context context, Object obj, Object obj2, DialogBuilder.DialogButtonClickListener dialogButtonClickListener, DialogBuilder.DialogButtonClickListener dialogButtonClickListener2, boolean z, boolean z2, int i, int i2) {
        generateAndShowDialogByBuilder(context, obj, obj2, dialogButtonClickListener, dialogButtonClickListener2, z, z2, false, i, i2, -1);
    }

    public static void showDialogWithBtnIdsWithCheckBox(Context context, Object obj, Object obj2, DialogBuilder.DialogButtonClickWithCheckBoxListener dialogButtonClickWithCheckBoxListener, DialogBuilder.DialogButtonClickWithCheckBoxListener dialogButtonClickWithCheckBoxListener2, boolean z, boolean z2, int i, int i2) {
        generateAndShowDialogByBuilder(context, obj, obj2, dialogButtonClickWithCheckBoxListener, dialogButtonClickWithCheckBoxListener2, z, z2, false, i, i2, -1);
    }

    public static void showDeleteDialog(Context context, Object obj, Object obj2, DialogBuilder.DialogButtonClickListener dialogButtonClickListener, DialogBuilder.DialogButtonClickListener dialogButtonClickListener2, boolean z, boolean z2, int i, int i2, int i3) {
        generateAndShowDialogByBuilder(context, obj, obj2, dialogButtonClickListener, dialogButtonClickListener2, z, z2, !z2, i, i2, i3);
    }

    private static void generateAndShowDialogByBuilder(Context context, Object obj, Object obj2, DialogBuilder.DialogButtonClickListener dialogButtonClickListener, DialogBuilder.DialogButtonClickListener dialogButtonClickListener2, boolean z, boolean z2, boolean z3, int i, int i2, int i3) {
        if (BaseAppUtils.isContextExisted(context)) {
            DialogBuilder cancelOutside = getBuilder(context).setTitle(obj).setMessage(obj2).setDeleteBtnPos(i3).setIvCloseShow(z3).setCancelable(z2).setCancelOutside(z2);
            String strValueOf = z ? Integer.valueOf(i) : "";
            Integer numValueOf = Integer.valueOf(i2);
            if (!z) {
                dialogButtonClickListener = null;
            }
            mBuilder = cancelOutside.setButtons(strValueOf, numValueOf, dialogButtonClickListener, dialogButtonClickListener2).create().show();
        }
    }

    private static void generateAndShowDialogByBuilder(Context context, Object obj, Object obj2, DialogBuilder.DialogButtonClickWithCheckBoxListener dialogButtonClickWithCheckBoxListener, DialogBuilder.DialogButtonClickWithCheckBoxListener dialogButtonClickWithCheckBoxListener2, boolean z, boolean z2, boolean z3, int i, int i2, int i3) {
        if (BaseAppUtils.isContextExisted(context)) {
            DialogBuilder cancelOutside = getBuilder(context).setTitle(obj).setMessage(obj2).setDeleteBtnPos(i3).setIvCloseShow(z3).setCancelable(z2).setShowCheckBox(true).setCancelOutside(z2);
            String strValueOf = z ? Integer.valueOf(i) : "";
            Integer numValueOf = Integer.valueOf(i2);
            if (!z) {
                dialogButtonClickWithCheckBoxListener = null;
            }
            mBuilder = cancelOutside.setButtonsWithCheckBox(strValueOf, numValueOf, dialogButtonClickWithCheckBoxListener, dialogButtonClickWithCheckBoxListener2).create().show();
        }
    }

    public static void showLoadingDialog(Context context) {
        if (BaseAppUtils.isContextExisted(context)) {
            showLoadingDialog(context, context.getResources().getString(C0874R.string.loading_ing));
        }
    }

    public static void showLoadingDialog(Context context, String str, boolean z) {
        if (loadingDialog != null) {
            hideLoadingDialog();
        }
        LoadingDialog loadingDialog2 = new LoadingDialog(context);
        loadingDialog = loadingDialog2;
        loadingDialog2.showDialog(context, str, z, null);
    }

    public static void showLoadingDialog(Context context, String str) {
        showLoadingDialog(context, str, true);
    }

    public static void hideLoadingDialog(Activity activity) {
        if (loadingDialog == null || activity.isDestroyed()) {
            return;
        }
        loadingDialog.dismissDialog();
    }

    public static void hideLoadingDialog() {
        LoadingDialog loadingDialog2 = loadingDialog;
        if (loadingDialog2 != null) {
            loadingDialog2.dismissDialog();
        }
    }

    public static boolean isLoadingDialogShowing() {
        LoadingDialog loadingDialog2 = loadingDialog;
        return loadingDialog2 != null && loadingDialog2.isShowing();
    }

    public static void showToRechargeStorageDialog(Activity activity) {
        showDialogWithDefBtn(activity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.me_storage_expire), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.base.util.DialogUtils$$ExternalSyntheticLambda0
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                DialogUtils.lambda$showToRechargeStorageDialog$0(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.base.util.DialogUtils$$ExternalSyntheticLambda1
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                DialogUtils.lambda$showToRechargeStorageDialog$1(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, false, true);
    }

    public static void showProgressDialog(Context context, String str) {
        showProgressDialog(context, str, null);
    }

    public static void showProgressDialog(Context context, String str, DialogInterface.OnCancelListener onCancelListener) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing() || activity.isDestroyed()) {
                LogUtil.m336e("activity 没有在活跃状态:show");
                return;
            }
        }
        try {
            ProgressDialog progressDialog = mProgressDialog;
            if (progressDialog != null && progressDialog.getDialog() != null && mProgressDialog.getDialog().getContext().hashCode() != context.hashCode()) {
                LogUtil.m338i("不是当前activity创建的dialog，删除dialog");
                hideProgressDialog();
            }
            if (mProgressDialog == null) {
                ProgressDialog progressDialog2 = new ProgressDialog();
                mProgressDialog = progressDialog2;
                progressDialog2.showDialog(context, str, onCancelListener);
            }
            mProgressDialog.showDialogView();
            mProgressDialog.setMessage(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setProgressDialogText(Context context, String str) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing() || activity.isDestroyed()) {
                LogUtil.m336e("activity 没有在活跃状态: progress");
                return;
            }
        }
        try {
            ProgressDialog progressDialog = mProgressDialog;
            if (progressDialog != null) {
                progressDialog.setMessage(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog() {
        ProgressDialog progressDialog = mProgressDialog;
        if (progressDialog != null) {
            progressDialog.hideDialog();
        }
        mProgressDialog = null;
    }
}
