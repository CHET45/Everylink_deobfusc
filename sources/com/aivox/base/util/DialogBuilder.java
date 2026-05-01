package com.aivox.base.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aivox.base.C0874R;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.blankj.utilcode.util.KeyboardUtils;

/* JADX INFO: loaded from: classes.dex */
public class DialogBuilder {
    public static final int DELETE_ON_LEFT = 0;
    public static final int DELETE_ON_RIGHT = 1;
    public static final int WITHOUT_DELETE = -1;
    private DialogCancelListener mCancelListener;
    private boolean mCancelOutside;
    private boolean mCancelable;
    private final Context mContext;
    private int mDeleteBtnPos;
    private Dialog mDialog;
    private final int mDialogId;
    private String mEditMessage;
    private boolean mEditTextEnable;
    private String mEditTextHintMessage;
    private int mEditType;
    private boolean mHideButtons;
    private boolean mHideMessage;
    private String mImgUrl;
    private Object mLeftBtnContent;
    private DialogButtonClickListener mLeftBtnListener;
    private DialogButtonClickWithCheckBoxListener mLeftBtnWithCheckBoxListener;
    private Object mMessage;
    private Object mRightBtnContent;
    private DialogButtonClickListener mRightBtnListener;
    private DialogButtonClickWithCheckBoxListener mRightBtnWithCheckBoxListener;
    private boolean mShowCheckBox;
    private boolean mShowCloseBtn;
    private Object mTitle;
    private int mTitleSize;

    public interface DialogButtonClickListener {
        public static final int BUTTON_LEFT = 0;
        public static final int BUTTON_RIGHT = 1;

        void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText);
    }

    public interface DialogButtonClickWithCheckBoxListener {
        public static final int BUTTON_LEFT = 0;
        public static final int BUTTON_RIGHT = 1;

        void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, CheckBox checkBox);
    }

    public interface DialogCancelListener {
        void onCancelClick(Dialog dialog);
    }

    public DialogBuilder(Context context) {
        this(context, 0);
    }

    private DialogBuilder(Context context, int i) {
        this.mEditType = 0;
        this.mContext = context;
        this.mDialogId = i;
    }

    public DialogBuilder setCancelOutside(boolean z) {
        this.mCancelOutside = z;
        return this;
    }

    public DialogBuilder setTitleImg(String str) {
        this.mImgUrl = str;
        return this;
    }

    public DialogBuilder setTitle(Object obj) {
        this.mTitle = obj;
        return this;
    }

    public DialogBuilder setTitleSize(int i) {
        this.mTitleSize = i;
        return this;
    }

    public DialogBuilder setMessage(Object obj) {
        this.mMessage = obj;
        return this;
    }

    public DialogBuilder hideMessage() {
        this.mHideMessage = true;
        return this;
    }

    public DialogBuilder setEditTextEnable() {
        this.mEditTextEnable = true;
        return this;
    }

    public DialogBuilder setEditText(String str) {
        this.mEditMessage = str;
        return this;
    }

    public DialogBuilder setEditTextHint(String str) {
        this.mEditTextHintMessage = str;
        return this;
    }

    public DialogBuilder setEditTextInputType(int i) {
        this.mEditType = i;
        return this;
    }

    public DialogBuilder setIvCloseShow(boolean z) {
        this.mShowCloseBtn = z;
        return this;
    }

    public DialogBuilder setShowCheckBox(boolean z) {
        this.mShowCheckBox = z;
        return this;
    }

    public DialogBuilder setNoButtons() {
        this.mHideButtons = true;
        return this;
    }

    public DialogBuilder setButtons(Object obj, Object obj2, DialogButtonClickListener dialogButtonClickListener, DialogButtonClickListener dialogButtonClickListener2) {
        this.mLeftBtnContent = obj;
        this.mRightBtnContent = obj2;
        this.mLeftBtnListener = dialogButtonClickListener;
        this.mRightBtnListener = dialogButtonClickListener2;
        return this;
    }

    public DialogBuilder setButtonsWithCheckBox(Object obj, Object obj2, DialogButtonClickWithCheckBoxListener dialogButtonClickWithCheckBoxListener, DialogButtonClickWithCheckBoxListener dialogButtonClickWithCheckBoxListener2) {
        this.mLeftBtnContent = obj;
        this.mRightBtnContent = obj2;
        this.mLeftBtnWithCheckBoxListener = dialogButtonClickWithCheckBoxListener;
        this.mRightBtnWithCheckBoxListener = dialogButtonClickWithCheckBoxListener2;
        return this;
    }

    public DialogBuilder setCancelable(boolean z) {
        this.mCancelable = z;
        return this;
    }

    public DialogBuilder setCancelListener(DialogCancelListener dialogCancelListener) {
        this.mCancelListener = dialogCancelListener;
        return this;
    }

    public DialogBuilder setDeleteBtnPos(int i) {
        this.mDeleteBtnPos = i;
        return this;
    }

    public DialogBuilder create() {
        Dialog dialog = new Dialog(this.mContext, C0874R.style.SelfDefineDialogStyle);
        dialog.setContentView(C0874R.layout.dilog_layout_white);
        dialog.setCanceledOnTouchOutside(this.mCancelOutside);
        dialog.setCancelable(this.mCancelable);
        this.mDialog = dialog;
        Button button = (Button) getView(C0874R.id.left);
        Button button2 = (Button) getView(C0874R.id.right);
        TextView textView = (TextView) getView(C0874R.id.title);
        LinearLayout linearLayout = (LinearLayout) getView(C0874R.id.message_layout);
        LinearLayout linearLayout2 = (LinearLayout) getView(C0874R.id.ll_check_box);
        TextView textView2 = (TextView) getView(C0874R.id.message);
        final EditText editText = (EditText) getView(C0874R.id.edittext_id);
        ImageView imageView = (ImageView) getView(C0874R.id.iv_cancel);
        final CheckBox checkBox = (CheckBox) getView(C0874R.id.cb_dialog);
        textView2.setAutoLinkMask(15);
        Object obj = this.mMessage;
        if ((obj instanceof String) && BaseStringUtil.isEmpty((String) obj)) {
            textView2.setVisibility(8);
            linearLayout.setVisibility(8);
        } else {
            textView2.setVisibility(0);
            textView2.setText(parseParam(this.mMessage));
        }
        if (this.mHideMessage) {
            textView2.setVisibility(8);
        }
        Object obj2 = this.mTitle;
        if ((obj2 instanceof String) && BaseStringUtil.isEmpty((String) obj2)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(8);
            textView.setText(parseParam(this.mTitle));
        }
        int i = this.mTitleSize;
        if (i != 0) {
            textView.setTextSize(i);
        }
        int i2 = this.mDeleteBtnPos;
        if (i2 == 0) {
            button.setBackgroundResource(C0874R.drawable.bg_dialog_btn_warning);
            button.setTextColor(this.mContext.getResources().getColor(C0874R.color.txt_color_secondary));
            button2.setBackgroundResource(C0874R.drawable.bg_dialog_btn_stroke);
            button2.setTextColor(this.mContext.getResources().getColor(C0874R.color.txt_color_primary));
        } else if (i2 == 1) {
            button2.setBackgroundResource(C0874R.drawable.bg_dialog_btn_warning);
            button2.setTextColor(this.mContext.getResources().getColor(C0874R.color.txt_color_secondary));
            button.setBackgroundResource(C0874R.drawable.bg_dialog_btn_stroke);
            button.setTextColor(this.mContext.getResources().getColor(C0874R.color.txt_color_primary));
        }
        if (this.mEditTextEnable) {
            editText.setVisibility(0);
            if (BaseStringUtil.isNotEmpty(this.mEditMessage)) {
                editText.setText(parseParam(this.mEditMessage));
                editText.setSelection(this.mEditMessage.length());
            }
            if (BaseStringUtil.isNotEmpty(this.mEditTextHintMessage)) {
                editText.setHint(parseParam(this.mEditTextHintMessage));
            }
            editText.setInputType(this.mEditType);
            KeyboardUtils.showSoftInput(editText);
        }
        if (BaseStringUtil.isNotEmpty(this.mImgUrl)) {
            ImageLoaderFactory.getLoader().displayImage((ImageView) getView(C0874R.id.img), this.mImgUrl);
            textView.setVisibility(8);
            getView(C0874R.id.imgView).setVisibility(0);
        }
        if (this.mHideButtons) {
            getView(C0874R.id.line2).setVisibility(8);
            getView(C0874R.id.btns_layout).setVisibility(8);
        }
        if (this.mShowCloseBtn) {
            imageView.setVisibility(0);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.base.util.DialogBuilder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2433lambda$create$0$comaivoxbaseutilDialogBuilder(view2);
                }
            });
        }
        if (this.mShowCheckBox) {
            linearLayout2.setVisibility(0);
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.base.util.DialogBuilder$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    checkBox.setChecked(!r0.isChecked());
                }
            });
        }
        Object obj3 = this.mLeftBtnContent;
        if (obj3 != null) {
            button.setText(parseParam(obj3));
            button.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.base.util.DialogBuilder$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2434lambda$create$2$comaivoxbaseutilDialogBuilder(editText, checkBox, view2);
                }
            });
            if ("".equals(this.mLeftBtnContent)) {
                button.setVisibility(8);
                getView(C0874R.id.center).setVisibility(8);
            }
        }
        Object obj4 = this.mRightBtnContent;
        if (obj4 != null) {
            button2.setText(parseParam(obj4));
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.base.util.DialogBuilder$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2435lambda$create$3$comaivoxbaseutilDialogBuilder(editText, checkBox, view2);
                }
            });
            if ("".equals(this.mRightBtnContent)) {
                button2.setVisibility(8);
                getView(C0874R.id.center).setVisibility(8);
            }
        }
        if (this.mCancelListener != null) {
            this.mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.aivox.base.util.DialogBuilder$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    this.f$0.m2436lambda$create$4$comaivoxbaseutilDialogBuilder(dialogInterface);
                }
            });
        }
        return this;
    }

    /* JADX INFO: renamed from: lambda$create$0$com-aivox-base-util-DialogBuilder, reason: not valid java name */
    /* synthetic */ void m2433lambda$create$0$comaivoxbaseutilDialogBuilder(View view2) {
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$create$2$com-aivox-base-util-DialogBuilder, reason: not valid java name */
    /* synthetic */ void m2434lambda$create$2$comaivoxbaseutilDialogBuilder(EditText editText, CheckBox checkBox, View view2) {
        KeyboardUtils.hideSoftInput(view2);
        this.mDialog.dismiss();
        if (this.mLeftBtnListener != null) {
            if (editText != null && editText.getVisibility() == 0) {
                this.mLeftBtnListener.onButtonClick(this.mContext, this, this.mDialog, this.mDialogId, 0, editText);
            } else {
                this.mLeftBtnListener.onButtonClick(this.mContext, this, this.mDialog, this.mDialogId, 0, null);
            }
        }
        DialogButtonClickWithCheckBoxListener dialogButtonClickWithCheckBoxListener = this.mLeftBtnWithCheckBoxListener;
        if (dialogButtonClickWithCheckBoxListener != null) {
            dialogButtonClickWithCheckBoxListener.onButtonClick(this.mContext, this, this.mDialog, this.mDialogId, 0, checkBox);
        }
    }

    /* JADX INFO: renamed from: lambda$create$3$com-aivox-base-util-DialogBuilder, reason: not valid java name */
    /* synthetic */ void m2435lambda$create$3$comaivoxbaseutilDialogBuilder(EditText editText, CheckBox checkBox, View view2) {
        KeyboardUtils.hideSoftInput(view2);
        this.mDialog.dismiss();
        if (this.mRightBtnListener != null) {
            if (editText != null && editText.getVisibility() == 0) {
                this.mRightBtnListener.onButtonClick(this.mContext, this, this.mDialog, this.mDialogId, 1, editText);
            } else {
                this.mRightBtnListener.onButtonClick(this.mContext, this, this.mDialog, this.mDialogId, 1, null);
            }
        }
        DialogButtonClickWithCheckBoxListener dialogButtonClickWithCheckBoxListener = this.mRightBtnWithCheckBoxListener;
        if (dialogButtonClickWithCheckBoxListener != null) {
            dialogButtonClickWithCheckBoxListener.onButtonClick(this.mContext, this, this.mDialog, this.mDialogId, 1, checkBox);
        }
    }

    /* JADX INFO: renamed from: lambda$create$4$com-aivox-base-util-DialogBuilder, reason: not valid java name */
    /* synthetic */ void m2436lambda$create$4$comaivoxbaseutilDialogBuilder(DialogInterface dialogInterface) {
        this.mCancelListener.onCancelClick(this.mDialog);
    }

    public DialogBuilder show() {
        WindowManager.LayoutParams attributes = this.mDialog.getWindow().getAttributes();
        attributes.dimAmount = 0.4f;
        this.mDialog.getWindow().setAttributes(attributes);
        this.mDialog.show();
        return this;
    }

    private <T extends View> T getView(int i) {
        return (T) this.mDialog.findViewById(i);
    }

    private String parseParam(Object obj) {
        try {
            if (obj instanceof Integer) {
                return this.mContext.getString(((Integer) obj).intValue());
            }
            if (!(obj instanceof String)) {
                return "";
            }
            return obj.toString();
        } catch (Resources.NotFoundException e) {
            BaseAppUtils.printErrorMsg(e);
            return "";
        }
    }

    public boolean isShow() {
        Dialog dialog = this.mDialog;
        return dialog != null && dialog.isShowing();
    }

    public void setDismiss() {
        hideDialog();
    }

    private void hideDialog() {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            if (dialog.isShowing()) {
                Context baseContext = ((ContextWrapper) this.mDialog.getContext()).getBaseContext();
                if (baseContext instanceof Activity) {
                    Activity activity = (Activity) baseContext;
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        this.mDialog.dismiss();
                    }
                } else {
                    this.mDialog.dismiss();
                }
            }
            this.mDialog = null;
        }
    }
}
