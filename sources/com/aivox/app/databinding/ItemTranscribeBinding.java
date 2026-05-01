package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.view.EditTextInList;
import com.aivox.common.model.Transcribe;
import com.aivox.common_ui.RoundCornerContainer;
import com.aivox.common_ui.RoundedCornerBitmap;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemTranscribeBinding extends ViewDataBinding {
    public final CheckBox cbSelect;

    /* JADX INFO: renamed from: cl */
    public final ConstraintLayout f131cl;
    public final ConstraintLayout clMark;
    public final ConstraintLayout clTotal;
    public final RoundedCornerBitmap ivHead;
    public final ImageView ivImgTag;
    public final ImageView ivPlaying;
    public final ImageView ivTrump;
    public final View lineContent;
    public final LinearLayout llSpeakerInfo;
    public final LinearLayout llTimeAndInfo;

    @Bindable
    protected Transcribe mXmlmodel;
    public final RoundCornerContainer rcSpeakerAvatarText;
    public final RecyclerView rcvImgs;
    public final EditTextInList tvContent;
    public final TextView tvNoteMarks;
    public final TextView tvNoteMarksSimple;
    public final TextView tvSpeakerName;
    public final TextView tvSpeakerNameText;
    public final TextView tvTime;
    public final TextView tvTranslateResult;

    public abstract void setXmlmodel(Transcribe transcribe);

    protected ItemTranscribeBinding(Object obj, View view2, int i, CheckBox checkBox, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, ConstraintLayout constraintLayout3, RoundedCornerBitmap roundedCornerBitmap, ImageView imageView, ImageView imageView2, ImageView imageView3, View view3, LinearLayout linearLayout, LinearLayout linearLayout2, RoundCornerContainer roundCornerContainer, RecyclerView recyclerView, EditTextInList editTextInList, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6) {
        super(obj, view2, i);
        this.cbSelect = checkBox;
        this.f131cl = constraintLayout;
        this.clMark = constraintLayout2;
        this.clTotal = constraintLayout3;
        this.ivHead = roundedCornerBitmap;
        this.ivImgTag = imageView;
        this.ivPlaying = imageView2;
        this.ivTrump = imageView3;
        this.lineContent = view3;
        this.llSpeakerInfo = linearLayout;
        this.llTimeAndInfo = linearLayout2;
        this.rcSpeakerAvatarText = roundCornerContainer;
        this.rcvImgs = recyclerView;
        this.tvContent = editTextInList;
        this.tvNoteMarks = textView;
        this.tvNoteMarksSimple = textView2;
        this.tvSpeakerName = textView3;
        this.tvSpeakerNameText = textView4;
        this.tvTime = textView5;
        this.tvTranslateResult = textView6;
    }

    public Transcribe getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemTranscribeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTranscribeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemTranscribeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_transcribe, viewGroup, z, obj);
    }

    public static ItemTranscribeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTranscribeBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemTranscribeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_transcribe, null, false, obj);
    }

    public static ItemTranscribeBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTranscribeBinding bind(View view2, Object obj) {
        return (ItemTranscribeBinding) bind(obj, view2, C0726R.layout.item_transcribe);
    }
}
