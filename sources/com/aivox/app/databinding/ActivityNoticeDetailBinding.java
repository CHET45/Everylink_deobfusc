package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityNoticeDetailBinding extends ViewDataBinding {
    public final TextView tvNoticeContent;
    public final TextView tvNoticeTitle;

    protected ActivityNoticeDetailBinding(Object obj, View view2, int i, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.tvNoticeContent = textView;
        this.tvNoticeTitle = textView2;
    }

    public static ActivityNoticeDetailBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNoticeDetailBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityNoticeDetailBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_notice_detail, viewGroup, z, obj);
    }

    public static ActivityNoticeDetailBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNoticeDetailBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityNoticeDetailBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_notice_detail, null, false, obj);
    }

    public static ActivityNoticeDetailBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNoticeDetailBinding bind(View view2, Object obj) {
        return (ActivityNoticeDetailBinding) bind(obj, view2, C0726R.layout.activity_notice_detail);
    }
}
