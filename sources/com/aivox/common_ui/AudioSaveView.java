package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.AudioSaveViewLayoutBinding;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SizeUtils;

/* JADX INFO: loaded from: classes.dex */
public class AudioSaveView extends BaseDialogViewWrapper {

    public interface ButtonClickListener {
        void onButtonClick();
    }

    public AudioSaveView(Context context, String str, final ButtonClickListener buttonClickListener) {
        super(context);
        final AudioSaveViewLayoutBinding audioSaveViewLayoutBindingInflate = AudioSaveViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        audioSaveViewLayoutBindingInflate.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.AudioSaveView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2489lambda$new$0$comaivoxcommon_uiAudioSaveView(buttonClickListener, view2);
            }
        });
        KeyboardUtils.registerSoftInputChangedListener(ActivityUtils.getTopActivity(), new KeyboardUtils.OnSoftInputChangedListener() { // from class: com.aivox.common_ui.AudioSaveView.1
            @Override // com.blankj.utilcode.util.KeyboardUtils.OnSoftInputChangedListener
            public void onSoftInputChanged(final int i) {
                audioSaveViewLayoutBindingInflate.etFilename.postDelayed(new Runnable() { // from class: com.aivox.common_ui.AudioSaveView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        audioSaveViewLayoutBindingInflate.getRoot().setPadding(SizeUtils.dp2px(20.0f), 0, SizeUtils.dp2px(20.0f), i);
                    }
                }, 1000L);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common_ui-AudioSaveView, reason: not valid java name */
    /* synthetic */ void m2489lambda$new$0$comaivoxcommon_uiAudioSaveView(ButtonClickListener buttonClickListener, View view2) {
        buttonClickListener.onButtonClick();
        this.mDialog.dismiss();
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }
}
