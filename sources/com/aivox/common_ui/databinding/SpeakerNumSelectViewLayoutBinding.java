package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class SpeakerNumSelectViewLayoutBinding extends ViewDataBinding {
    public final LoadingButton btnContinue;
    public final SeekBar sbSpeakerNum;
    public final TextView tvDialogMsg;
    public final TextView tvDialogTitle;
    public final TextView tvSpeakerNum;

    protected SpeakerNumSelectViewLayoutBinding(Object obj, View view2, int i, LoadingButton loadingButton, SeekBar seekBar, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.btnContinue = loadingButton;
        this.sbSpeakerNum = seekBar;
        this.tvDialogMsg = textView;
        this.tvDialogTitle = textView2;
        this.tvSpeakerNum = textView3;
    }

    public static SpeakerNumSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SpeakerNumSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (SpeakerNumSelectViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.speaker_num_select_view_layout, viewGroup, z, obj);
    }

    public static SpeakerNumSelectViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SpeakerNumSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (SpeakerNumSelectViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.speaker_num_select_view_layout, null, false, obj);
    }

    public static SpeakerNumSelectViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SpeakerNumSelectViewLayoutBinding bind(View view2, Object obj) {
        return (SpeakerNumSelectViewLayoutBinding) bind(obj, view2, C1034R.layout.speaker_num_select_view_layout);
    }
}
