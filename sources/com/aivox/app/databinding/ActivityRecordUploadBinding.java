package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityRecordUploadBinding extends ViewDataBinding {
    public final LoadingButton btnFinish;
    public final ProgressBar pbProgress;
    public final HeadTitleLinearView titleView;
    public final TextView tvFileName;
    public final TextView tvProgress;

    protected ActivityRecordUploadBinding(Object obj, View view2, int i, LoadingButton loadingButton, ProgressBar progressBar, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.btnFinish = loadingButton;
        this.pbProgress = progressBar;
        this.titleView = headTitleLinearView;
        this.tvFileName = textView;
        this.tvProgress = textView2;
    }

    public static ActivityRecordUploadBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordUploadBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityRecordUploadBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_record_upload, viewGroup, z, obj);
    }

    public static ActivityRecordUploadBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordUploadBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityRecordUploadBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_record_upload, null, false, obj);
    }

    public static ActivityRecordUploadBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordUploadBinding bind(View view2, Object obj) {
        return (ActivityRecordUploadBinding) bind(obj, view2, C0726R.layout.activity_record_upload);
    }
}
