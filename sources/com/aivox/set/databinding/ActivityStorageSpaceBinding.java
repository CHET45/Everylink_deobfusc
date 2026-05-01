package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityStorageSpaceBinding extends ViewDataBinding {
    public final ConstraintLayout clStorageAudio;
    public final ConstraintLayout clStorageTotal;
    public final ConstraintLayout clTempStorage;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final HeadTitleLinearView titleView;
    public final TextView tvClearAudio;
    public final TextView tvClearCache;
    public final TextView tvStorage;
    public final TextView tvStorageAudioDesc;
    public final TextView tvStorageCache;
    public final TextView tvStorageCacheDesc;
    public final TextView tvStorageSizeAudio;
    public final TextView tvStorageSizeCache;
    public final TextView tvStorageSizeCloud;
    public final TextView tvStorageUsedPercent;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected ActivityStorageSpaceBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, ConstraintLayout constraintLayout3, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7, TextView textView8, TextView textView9, TextView textView10) {
        super(obj, view2, i);
        this.clStorageAudio = constraintLayout;
        this.clStorageTotal = constraintLayout2;
        this.clTempStorage = constraintLayout3;
        this.titleView = headTitleLinearView;
        this.tvClearAudio = textView;
        this.tvClearCache = textView2;
        this.tvStorage = textView3;
        this.tvStorageAudioDesc = textView4;
        this.tvStorageCache = textView5;
        this.tvStorageCacheDesc = textView6;
        this.tvStorageSizeAudio = textView7;
        this.tvStorageSizeCache = textView8;
        this.tvStorageSizeCloud = textView9;
        this.tvStorageUsedPercent = textView10;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static ActivityStorageSpaceBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityStorageSpaceBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityStorageSpaceBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_storage_space, viewGroup, z, obj);
    }

    public static ActivityStorageSpaceBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityStorageSpaceBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityStorageSpaceBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_storage_space, null, false, obj);
    }

    public static ActivityStorageSpaceBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityStorageSpaceBinding bind(View view2, Object obj) {
        return (ActivityStorageSpaceBinding) bind(obj, view2, C1106R.layout.activity_storage_space);
    }
}
