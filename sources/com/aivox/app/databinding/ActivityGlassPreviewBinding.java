package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityGlassPreviewBinding extends ViewDataBinding {
    public final ConstraintLayout clParent;

    /* JADX INFO: renamed from: fl */
    public final FrameLayout f119fl;
    public final ImageView ivBack;

    /* JADX INFO: renamed from: sv */
    public final SurfaceView f120sv;

    protected ActivityGlassPreviewBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, FrameLayout frameLayout, ImageView imageView, SurfaceView surfaceView) {
        super(obj, view2, i);
        this.clParent = constraintLayout;
        this.f119fl = frameLayout;
        this.ivBack = imageView;
        this.f120sv = surfaceView;
    }

    public static ActivityGlassPreviewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityGlassPreviewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityGlassPreviewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_glass_preview, viewGroup, z, obj);
    }

    public static ActivityGlassPreviewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityGlassPreviewBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityGlassPreviewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_glass_preview, null, false, obj);
    }

    public static ActivityGlassPreviewBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityGlassPreviewBinding bind(View view2, Object obj) {
        return (ActivityGlassPreviewBinding) bind(obj, view2, C0726R.layout.activity_glass_preview);
    }
}
