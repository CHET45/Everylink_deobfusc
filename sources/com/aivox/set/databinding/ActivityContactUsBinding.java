package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.set.C1106R;
import com.zhy.view.flowlayout.TagFlowLayout;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityContactUsBinding extends ViewDataBinding {
    public final LoadingButton btnNext;
    public final CheckBox cbEmail;
    public final CheckBox cbSubmit;
    public final EditText etEmail;
    public final EditText etMsg;
    public final FrameLayout flContainer;
    public final ImageView ivFormatNotice;
    public final ImageView ivMsgEmpty;
    public final ImageView ivSubmitSuccess;
    public final LinearLayout llEmail;
    public final LinearLayout llMsg;
    public final LinearLayout llQuickSet;
    public final LinearLayout llStep1;
    public final LinearLayout llStep2;
    public final LinearLayout llSubmit;
    public final LinearLayout main;
    public final ProgressBar progress;
    public final RecyclerView rvFiles;
    public final ScrollView svStep3;
    public final TagFlowLayout tagFlow;
    public final HeadTitleLinearView titleView;
    public final TextView tvAccount;
    public final TextView tvEmail;
    public final TextView tvFiles;
    public final TextView tvMsg;
    public final TextView tvProgress;
    public final TextView tvStepMsg;
    public final TextView tvSubmitSuccess;
    public final TextView tvTags;
    public final TextView tvTextCount;
    public final TextView tvWarning;

    protected ActivityContactUsBinding(Object obj, View view2, int i, LoadingButton loadingButton, CheckBox checkBox, CheckBox checkBox2, EditText editText, EditText editText2, FrameLayout frameLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, LinearLayout linearLayout5, LinearLayout linearLayout6, LinearLayout linearLayout7, ProgressBar progressBar, RecyclerView recyclerView, ScrollView scrollView, TagFlowLayout tagFlowLayout, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7, TextView textView8, TextView textView9, TextView textView10) {
        super(obj, view2, i);
        this.btnNext = loadingButton;
        this.cbEmail = checkBox;
        this.cbSubmit = checkBox2;
        this.etEmail = editText;
        this.etMsg = editText2;
        this.flContainer = frameLayout;
        this.ivFormatNotice = imageView;
        this.ivMsgEmpty = imageView2;
        this.ivSubmitSuccess = imageView3;
        this.llEmail = linearLayout;
        this.llMsg = linearLayout2;
        this.llQuickSet = linearLayout3;
        this.llStep1 = linearLayout4;
        this.llStep2 = linearLayout5;
        this.llSubmit = linearLayout6;
        this.main = linearLayout7;
        this.progress = progressBar;
        this.rvFiles = recyclerView;
        this.svStep3 = scrollView;
        this.tagFlow = tagFlowLayout;
        this.titleView = headTitleLinearView;
        this.tvAccount = textView;
        this.tvEmail = textView2;
        this.tvFiles = textView3;
        this.tvMsg = textView4;
        this.tvProgress = textView5;
        this.tvStepMsg = textView6;
        this.tvSubmitSuccess = textView7;
        this.tvTags = textView8;
        this.tvTextCount = textView9;
        this.tvWarning = textView10;
    }

    public static ActivityContactUsBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityContactUsBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityContactUsBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_contact_us, viewGroup, z, obj);
    }

    public static ActivityContactUsBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityContactUsBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityContactUsBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_contact_us, null, false, obj);
    }

    public static ActivityContactUsBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityContactUsBinding bind(View view2, Object obj) {
        return (ActivityContactUsBinding) bind(obj, view2, C1106R.layout.activity_contact_us);
    }
}
