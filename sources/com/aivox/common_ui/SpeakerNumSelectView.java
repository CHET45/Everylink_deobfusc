package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import com.aivox.base.C0874R;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.SpeakerNumSelectViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class SpeakerNumSelectView extends BaseDialogViewWrapper {

    public interface OnNumSelectListener {
        void onNumSelect(int i);
    }

    public SpeakerNumSelectView(final Context context, final OnNumSelectListener onNumSelectListener) {
        super(context);
        final SpeakerNumSelectViewLayoutBinding speakerNumSelectViewLayoutBindingInflate = SpeakerNumSelectViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        speakerNumSelectViewLayoutBindingInflate.sbSpeakerNum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.aivox.common_ui.SpeakerNumSelectView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (i == 0) {
                    speakerNumSelectViewLayoutBindingInflate.tvSpeakerNum.setText(context.getString(C0874R.string.speaker_num_auto));
                } else {
                    speakerNumSelectViewLayoutBindingInflate.tvSpeakerNum.setText(context.getString(C0874R.string.speaker_num, Integer.valueOf(i)));
                }
            }
        });
        speakerNumSelectViewLayoutBindingInflate.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.SpeakerNumSelectView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2514lambda$new$0$comaivoxcommon_uiSpeakerNumSelectView(onNumSelectListener, speakerNumSelectViewLayoutBindingInflate, view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common_ui-SpeakerNumSelectView, reason: not valid java name */
    /* synthetic */ void m2514lambda$new$0$comaivoxcommon_uiSpeakerNumSelectView(OnNumSelectListener onNumSelectListener, SpeakerNumSelectViewLayoutBinding speakerNumSelectViewLayoutBinding, View view2) {
        onNumSelectListener.onNumSelect(speakerNumSelectViewLayoutBinding.sbSpeakerNum.getProgress());
        this.mDialog.dismiss();
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }
}
