package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.aivox.app.C0726R;
import com.aivox.app.view.ConversationRecordIconView;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityConversationRecordingBinding extends ViewDataBinding {
    public final ConstraintLayout clBottom;
    public final ConstraintLayout clBtm;
    public final ConstraintLayout clConversation;
    public final ConstraintLayout clTop;
    public final Group gpGuide;
    public final Group groupLangSetS2s;
    public final ImageView ivMicBottomStart;
    public final ConversationRecordIconView ivMicLeft;
    public final ConversationRecordIconView ivMicRight;
    public final ImageView ivMicTopStart;
    public final ImageView ivSwitchLangF2f;
    public final ImageView ivSwitchLangS2s;
    public final ImageView ivWave;
    public final ImageView ivWaveBottom;
    public final ImageView ivWaveTop;
    public final LinearLayout llContent;
    public final LinearLayout llLangChangeFace;
    public final LinearLayout llLangSelected;
    public final LinearLayout llLangSetF2f;
    public final LinearLayout llNoti;
    public final LinearLayout llWave;

    @Bindable
    protected OnViewClickListener mClickListener;

    @Bindable
    protected Boolean mIsFaceMode;
    public final RecyclerView recyclerview;
    public final RelativeLayout rlBottom;
    public final RelativeLayout rlTop;
    public final RecyclerView rvBtm;
    public final RecyclerView rvTop;
    public final HeadTitleLinearView titleView;
    public final TextView tvLangMySideF2f;
    public final TextView tvLangMySideS2s;
    public final TextView tvLangOtherSideF2f;
    public final TextView tvLangOtherSideS2s;
    public final TextView tvLanguageFrom;
    public final TextView tvLanguageFromFace;
    public final TextView tvLanguageTo;
    public final TextView tvLanguageToFace;
    public final TextView tvNoti;
    public final TextView tvNoticeBtm;
    public final TextView tvNoticeTop;
    public final View viewNext;
    public final View viewPrev;
    public final View viewSkip;
    public final ViewPager vpGuide;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    public abstract void setIsFaceMode(Boolean bool);

    protected ActivityConversationRecordingBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, ConstraintLayout constraintLayout3, ConstraintLayout constraintLayout4, Group group, Group group2, ImageView imageView, ConversationRecordIconView conversationRecordIconView, ConversationRecordIconView conversationRecordIconView2, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, LinearLayout linearLayout5, LinearLayout linearLayout6, RecyclerView recyclerView, RelativeLayout relativeLayout, RelativeLayout relativeLayout2, RecyclerView recyclerView2, RecyclerView recyclerView3, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7, TextView textView8, TextView textView9, TextView textView10, TextView textView11, View view3, View view4, View view5, ViewPager viewPager) {
        super(obj, view2, i);
        this.clBottom = constraintLayout;
        this.clBtm = constraintLayout2;
        this.clConversation = constraintLayout3;
        this.clTop = constraintLayout4;
        this.gpGuide = group;
        this.groupLangSetS2s = group2;
        this.ivMicBottomStart = imageView;
        this.ivMicLeft = conversationRecordIconView;
        this.ivMicRight = conversationRecordIconView2;
        this.ivMicTopStart = imageView2;
        this.ivSwitchLangF2f = imageView3;
        this.ivSwitchLangS2s = imageView4;
        this.ivWave = imageView5;
        this.ivWaveBottom = imageView6;
        this.ivWaveTop = imageView7;
        this.llContent = linearLayout;
        this.llLangChangeFace = linearLayout2;
        this.llLangSelected = linearLayout3;
        this.llLangSetF2f = linearLayout4;
        this.llNoti = linearLayout5;
        this.llWave = linearLayout6;
        this.recyclerview = recyclerView;
        this.rlBottom = relativeLayout;
        this.rlTop = relativeLayout2;
        this.rvBtm = recyclerView2;
        this.rvTop = recyclerView3;
        this.titleView = headTitleLinearView;
        this.tvLangMySideF2f = textView;
        this.tvLangMySideS2s = textView2;
        this.tvLangOtherSideF2f = textView3;
        this.tvLangOtherSideS2s = textView4;
        this.tvLanguageFrom = textView5;
        this.tvLanguageFromFace = textView6;
        this.tvLanguageTo = textView7;
        this.tvLanguageToFace = textView8;
        this.tvNoti = textView9;
        this.tvNoticeBtm = textView10;
        this.tvNoticeTop = textView11;
        this.viewNext = view3;
        this.viewPrev = view4;
        this.viewSkip = view5;
        this.vpGuide = viewPager;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public Boolean getIsFaceMode() {
        return this.mIsFaceMode;
    }

    public static ActivityConversationRecordingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityConversationRecordingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityConversationRecordingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_conversation_recording, viewGroup, z, obj);
    }

    public static ActivityConversationRecordingBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityConversationRecordingBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityConversationRecordingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_conversation_recording, null, false, obj);
    }

    public static ActivityConversationRecordingBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityConversationRecordingBinding bind(View view2, Object obj) {
        return (ActivityConversationRecordingBinding) bind(obj, view2, C0726R.layout.activity_conversation_recording);
    }
}
