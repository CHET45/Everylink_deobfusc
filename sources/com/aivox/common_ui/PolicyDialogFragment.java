package com.aivox.common_ui;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.aivox.base.C0874R;
import com.aivox.base.util.LogUtil;
import com.blankj.utilcode.util.ColorUtils;

/* JADX INFO: loaded from: classes.dex */
public class PolicyDialogFragment extends DialogFragment {
    private DialogListener listener;

    public interface DialogListener {
        void onCancel();

        void onConfirm();

        void onPrivacyPolicyClick();

        void onServiceAgreementClick();
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.listener = dialogListener;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCancelable(false);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        Window window;
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null || (window = dialog.getWindow()) == null) {
            return;
        }
        window.setLayout((int) (((double) getResources().getDisplayMetrics().widthPixels) * 0.8d), -2);
        window.setBackgroundDrawable(new ColorDrawable(0));
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View viewInflate = requireActivity().getLayoutInflater().inflate(C1034R.layout.policy_dialog_layout, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(C1034R.id.tv_dialog_title);
        TextView textView2 = (TextView) viewInflate.findViewById(C1034R.id.tv_dialog_subtitle);
        LoadingButton loadingButton = (LoadingButton) viewInflate.findViewById(C1034R.id.btn_dialog_cancel);
        LoadingButton loadingButton2 = (LoadingButton) viewInflate.findViewById(C1034R.id.btn_dialog_confirm);
        textView.setText(getString(C0874R.string.policy_dialog_policy));
        String str = getString(C0874R.string.policy_dialog_msg_1) + getString(C0874R.string.set_notice_privacy) + getString(C0874R.string.and) + getString(C0874R.string.set_notice_service) + getString(C0874R.string.policy_dialog_msg_2);
        String string = getString(C0874R.string.set_notice_privacy);
        String string2 = getString(C0874R.string.set_notice_service);
        LogUtil.m336e(str);
        LogUtil.m336e(string);
        LogUtil.m336e(string2);
        SpannableString spannableString = new SpannableString(str);
        ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.aivox.common_ui.PolicyDialogFragment.1
            @Override // android.text.style.ClickableSpan
            public void onClick(View view2) {
                PolicyDialogFragment.this.listener.onPrivacyPolicyClick();
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(ColorUtils.getColor(C0874R.color.txt_color_highlight));
                textPaint.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() { // from class: com.aivox.common_ui.PolicyDialogFragment.2
            @Override // android.text.style.ClickableSpan
            public void onClick(View view2) {
                PolicyDialogFragment.this.listener.onServiceAgreementClick();
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(ColorUtils.getColor(C0874R.color.txt_color_highlight));
                textPaint.setUnderlineText(false);
            }
        };
        int iIndexOf = str.indexOf(string);
        spannableString.setSpan(clickableSpan, iIndexOf, string.length() + iIndexOf, 33);
        int iIndexOf2 = str.indexOf(string2);
        spannableString.setSpan(clickableSpan2, iIndexOf2, string2.length() + iIndexOf2, 33);
        textView2.setText(spannableString);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        textView2.setHighlightColor(0);
        builder.setView(viewInflate);
        AlertDialog alertDialogCreate = builder.create();
        loadingButton.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.PolicyDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2509lambda$onCreateDialog$0$comaivoxcommon_uiPolicyDialogFragment(view2);
            }
        });
        loadingButton2.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.PolicyDialogFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2510lambda$onCreateDialog$1$comaivoxcommon_uiPolicyDialogFragment(view2);
            }
        });
        if (alertDialogCreate.getWindow() != null) {
            alertDialogCreate.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        return alertDialogCreate;
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$0$com-aivox-common_ui-PolicyDialogFragment, reason: not valid java name */
    /* synthetic */ void m2509lambda$onCreateDialog$0$comaivoxcommon_uiPolicyDialogFragment(View view2) {
        DialogListener dialogListener = this.listener;
        if (dialogListener != null) {
            dialogListener.onCancel();
        }
        dismiss();
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$1$com-aivox-common_ui-PolicyDialogFragment, reason: not valid java name */
    /* synthetic */ void m2510lambda$onCreateDialog$1$comaivoxcommon_uiPolicyDialogFragment(View view2) {
        DialogListener dialogListener = this.listener;
        if (dialogListener != null) {
            dialogListener.onConfirm();
        }
        dismiss();
    }
}
