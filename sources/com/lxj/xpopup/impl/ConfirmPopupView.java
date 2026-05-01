package com.lxj.xpopup.impl;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;

/* JADX INFO: loaded from: classes4.dex */
public class ConfirmPopupView extends CenterPopupView implements View.OnClickListener {
    OnCancelListener cancelListener;
    CharSequence cancelText;
    OnConfirmListener confirmListener;
    CharSequence confirmText;
    CharSequence content;
    View divider1;
    View divider2;
    EditText et_input;
    CharSequence hint;
    public boolean isHideCancel;
    CharSequence title;
    TextView tv_cancel;
    TextView tv_confirm;
    TextView tv_content;
    TextView tv_title;

    public ConfirmPopupView(Context context, int i) {
        super(context);
        this.isHideCancel = false;
        this.bindLayoutId = i;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return this.bindLayoutId != 0 ? this.bindLayoutId : C2213R.layout._xpopup_center_impl_confirm;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        this.tv_title = (TextView) findViewById(C2213R.id.tv_title);
        this.tv_content = (TextView) findViewById(C2213R.id.tv_content);
        this.tv_cancel = (TextView) findViewById(C2213R.id.tv_cancel);
        this.tv_confirm = (TextView) findViewById(C2213R.id.tv_confirm);
        this.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        this.et_input = (EditText) findViewById(C2213R.id.et_input);
        this.divider1 = findViewById(C2213R.id.xpopup_divider1);
        this.divider2 = findViewById(C2213R.id.xpopup_divider2);
        this.tv_cancel.setOnClickListener(this);
        this.tv_confirm.setOnClickListener(this);
        if (!TextUtils.isEmpty(this.title)) {
            this.tv_title.setText(this.title);
        } else {
            this.tv_title.setVisibility(8);
        }
        if (!TextUtils.isEmpty(this.content)) {
            this.tv_content.setText(this.content);
        } else {
            this.tv_content.setVisibility(8);
        }
        if (!TextUtils.isEmpty(this.cancelText)) {
            this.tv_cancel.setText(this.cancelText);
        }
        if (!TextUtils.isEmpty(this.confirmText)) {
            this.tv_confirm.setText(this.confirmText);
        }
        if (this.isHideCancel) {
            this.tv_cancel.setVisibility(8);
            View view2 = this.divider2;
            if (view2 != null) {
                view2.setVisibility(8);
            }
        }
        applyTheme();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        this.tv_title.setTextColor(getResources().getColor(C2213R.color._xpopup_content_color));
        this.tv_content.setTextColor(getResources().getColor(C2213R.color._xpopup_content_color));
        this.tv_cancel.setTextColor(Color.parseColor("#666666"));
        this.tv_confirm.setTextColor(XPopup.getPrimaryColor());
        View view2 = this.divider1;
        if (view2 != null) {
            view2.setBackgroundColor(getResources().getColor(C2213R.color._xpopup_list_divider));
        }
        View view3 = this.divider2;
        if (view3 != null) {
            view3.setBackgroundColor(getResources().getColor(C2213R.color._xpopup_list_divider));
        }
    }

    public TextView getTitleTextView() {
        return (TextView) findViewById(C2213R.id.tv_title);
    }

    public TextView getContentTextView() {
        return (TextView) findViewById(C2213R.id.tv_content);
    }

    public TextView getCancelTextView() {
        return (TextView) findViewById(C2213R.id.tv_cancel);
    }

    public TextView getConfirmTextView() {
        return (TextView) findViewById(C2213R.id.tv_confirm);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        this.tv_title.setTextColor(getResources().getColor(C2213R.color._xpopup_white_color));
        this.tv_content.setTextColor(getResources().getColor(C2213R.color._xpopup_white_color));
        this.tv_cancel.setTextColor(getResources().getColor(C2213R.color._xpopup_white_color));
        this.tv_confirm.setTextColor(getResources().getColor(C2213R.color._xpopup_white_color));
        View view2 = this.divider1;
        if (view2 != null) {
            view2.setBackgroundColor(getResources().getColor(C2213R.color._xpopup_list_dark_divider));
        }
        View view3 = this.divider2;
        if (view3 != null) {
            view3.setBackgroundColor(getResources().getColor(C2213R.color._xpopup_list_dark_divider));
        }
    }

    public ConfirmPopupView setListener(OnConfirmListener onConfirmListener, OnCancelListener onCancelListener) {
        this.cancelListener = onCancelListener;
        this.confirmListener = onConfirmListener;
        return this;
    }

    public ConfirmPopupView setTitleContent(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        this.title = charSequence;
        this.content = charSequence2;
        this.hint = charSequence3;
        return this;
    }

    public ConfirmPopupView setCancelText(CharSequence charSequence) {
        this.cancelText = charSequence;
        return this;
    }

    public ConfirmPopupView setConfirmText(CharSequence charSequence) {
        this.confirmText = charSequence;
        return this;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view2) {
        if (view2 == this.tv_cancel) {
            OnCancelListener onCancelListener = this.cancelListener;
            if (onCancelListener != null) {
                onCancelListener.onCancel();
            }
            dismiss();
            return;
        }
        if (view2 == this.tv_confirm) {
            OnConfirmListener onConfirmListener = this.confirmListener;
            if (onConfirmListener != null) {
                onConfirmListener.onConfirm();
            }
            if (this.popupInfo.autoDismiss.booleanValue()) {
                dismiss();
            }
        }
    }
}
