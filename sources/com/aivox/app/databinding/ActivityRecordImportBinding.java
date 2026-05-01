package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.PowerfulEditText;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityRecordImportBinding extends ViewDataBinding {
    public final Barrier barrierCancel;
    public final ConstraintLayout clTop;
    public final PowerfulEditText etSearchImport;
    public final ImageView ivSearch;
    public final LinearLayout llEmpty;
    public final LinearLayout llSearchLayout;
    public final RecyclerView recyclerview;
    public final RelativeLayout refreshLayout;
    public final ScrollView svImportNotice;
    public final HeadTitleLinearView titleView;

    /* JADX INFO: renamed from: tv */
    public final TextView f121tv;
    public final TextView tvDoImport;
    public final TextView tvSearchCancel;

    protected ActivityRecordImportBinding(Object obj, View view2, int i, Barrier barrier, ConstraintLayout constraintLayout, PowerfulEditText powerfulEditText, ImageView imageView, LinearLayout linearLayout, LinearLayout linearLayout2, RecyclerView recyclerView, RelativeLayout relativeLayout, ScrollView scrollView, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.barrierCancel = barrier;
        this.clTop = constraintLayout;
        this.etSearchImport = powerfulEditText;
        this.ivSearch = imageView;
        this.llEmpty = linearLayout;
        this.llSearchLayout = linearLayout2;
        this.recyclerview = recyclerView;
        this.refreshLayout = relativeLayout;
        this.svImportNotice = scrollView;
        this.titleView = headTitleLinearView;
        this.f121tv = textView;
        this.tvDoImport = textView2;
        this.tvSearchCancel = textView3;
    }

    public static ActivityRecordImportBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordImportBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityRecordImportBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_record_import, viewGroup, z, obj);
    }

    public static ActivityRecordImportBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordImportBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityRecordImportBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_record_import, null, false, obj);
    }

    public static ActivityRecordImportBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordImportBinding bind(View view2, Object obj) {
        return (ActivityRecordImportBinding) bind(obj, view2, C0726R.layout.activity_record_import);
    }
}
