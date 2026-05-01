package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.common_ui.AutoSwipeRefreshView;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentFileBinding extends ViewDataBinding {
    public final LinearLayout llTitle;
    public final LinearLayout llTitleEnter;
    public final AutoSwipeRefreshView refreshView;
    public final RecyclerView rvAudioList;
    public final TextView tvSync;

    protected FragmentFileBinding(Object obj, View view2, int i, LinearLayout linearLayout, LinearLayout linearLayout2, AutoSwipeRefreshView autoSwipeRefreshView, RecyclerView recyclerView, TextView textView) {
        super(obj, view2, i);
        this.llTitle = linearLayout;
        this.llTitleEnter = linearLayout2;
        this.refreshView = autoSwipeRefreshView;
        this.rvAudioList = recyclerView;
        this.tvSync = textView;
    }

    public static FragmentFileBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentFileBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentFileBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_file, viewGroup, z, obj);
    }

    public static FragmentFileBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentFileBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentFileBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_file, null, false, obj);
    }

    public static FragmentFileBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentFileBinding bind(View view2, Object obj) {
        return (FragmentFileBinding) bind(obj, view2, C0726R.layout.fragment_file);
    }
}
