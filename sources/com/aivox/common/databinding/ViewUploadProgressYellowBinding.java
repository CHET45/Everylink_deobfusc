package com.aivox.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common.C0958R;
import com.aivox.common_ui.CircularProgressView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ViewUploadProgressYellowBinding extends ViewDataBinding {
    public final CircularProgressView progressCircular;

    /* JADX INFO: renamed from: tv */
    public final TextView f202tv;
    public final TextView tvSub;

    protected ViewUploadProgressYellowBinding(Object obj, View view2, int i, CircularProgressView circularProgressView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.progressCircular = circularProgressView;
        this.f202tv = textView;
        this.tvSub = textView2;
    }

    public static ViewUploadProgressYellowBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewUploadProgressYellowBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ViewUploadProgressYellowBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.view_upload_progress_yellow, viewGroup, z, obj);
    }

    public static ViewUploadProgressYellowBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewUploadProgressYellowBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ViewUploadProgressYellowBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.view_upload_progress_yellow, null, false, obj);
    }

    public static ViewUploadProgressYellowBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewUploadProgressYellowBinding bind(View view2, Object obj) {
        return (ViewUploadProgressYellowBinding) bind(obj, view2, C0958R.layout.view_upload_progress_yellow);
    }
}
