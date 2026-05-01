package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentMainFileBinding extends ViewDataBinding {
    public final LoadingButton btnDeleteAll;
    public final FrameLayout flContainer;
    public final ImageView ivCreateFolder;
    public final ImageView ivDeleteFolder;
    public final ImageView ivEditFolder;
    public final ImageView ivImportFile;
    public final ImageView ivMoveFolder;
    public final ImageView ivSort;
    public final LinearLayout llSearch;
    public final LinearLayout llToolbar;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final RelativeLayout rlToolbar;
    public final RecyclerView rvTabs;
    public final TextView tvFolderCur;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected FragmentMainFileBinding(Object obj, View view2, int i, LoadingButton loadingButton, FrameLayout frameLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, LinearLayout linearLayout, LinearLayout linearLayout2, RelativeLayout relativeLayout, RecyclerView recyclerView, TextView textView) {
        super(obj, view2, i);
        this.btnDeleteAll = loadingButton;
        this.flContainer = frameLayout;
        this.ivCreateFolder = imageView;
        this.ivDeleteFolder = imageView2;
        this.ivEditFolder = imageView3;
        this.ivImportFile = imageView4;
        this.ivMoveFolder = imageView5;
        this.ivSort = imageView6;
        this.llSearch = linearLayout;
        this.llToolbar = linearLayout2;
        this.rlToolbar = relativeLayout;
        this.rvTabs = recyclerView;
        this.tvFolderCur = textView;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static FragmentMainFileBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainFileBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentMainFileBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_main_file, viewGroup, z, obj);
    }

    public static FragmentMainFileBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainFileBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentMainFileBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_main_file, null, false, obj);
    }

    public static FragmentMainFileBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainFileBinding bind(View view2, Object obj) {
        return (FragmentMainFileBinding) bind(obj, view2, C0726R.layout.fragment_main_file);
    }
}
