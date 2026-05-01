package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.common_ui.PowerfulEditText;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivitySearchAndMoveBinding extends ViewDataBinding {
    public final PowerfulEditText etSearch;
    public final ImageView ivApply;
    public final ImageView ivClose;
    public final LinearLayout llSearch;
    public final LinearLayout main;
    public final RecyclerView rvList;
    public final TextView tvTitle;

    protected ActivitySearchAndMoveBinding(Object obj, View view2, int i, PowerfulEditText powerfulEditText, ImageView imageView, ImageView imageView2, LinearLayout linearLayout, LinearLayout linearLayout2, RecyclerView recyclerView, TextView textView) {
        super(obj, view2, i);
        this.etSearch = powerfulEditText;
        this.ivApply = imageView;
        this.ivClose = imageView2;
        this.llSearch = linearLayout;
        this.main = linearLayout2;
        this.rvList = recyclerView;
        this.tvTitle = textView;
    }

    public static ActivitySearchAndMoveBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySearchAndMoveBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivitySearchAndMoveBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_search_and_move, viewGroup, z, obj);
    }

    public static ActivitySearchAndMoveBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySearchAndMoveBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivitySearchAndMoveBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_search_and_move, null, false, obj);
    }

    public static ActivitySearchAndMoveBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySearchAndMoveBinding bind(View view2, Object obj) {
        return (ActivitySearchAndMoveBinding) bind(obj, view2, C0726R.layout.activity_search_and_move);
    }
}
