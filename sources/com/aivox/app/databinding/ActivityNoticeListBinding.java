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
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityNoticeListBinding extends ViewDataBinding {
    public final LinearLayout llEmpty;
    public final RecyclerView rvNotification;
    public final HeadTitleLinearView titleView;
    public final TextView tvNoticeMsg;
    public final TextView tvNoticeSys;

    protected ActivityNoticeListBinding(Object obj, View view2, int i, LinearLayout linearLayout, RecyclerView recyclerView, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.llEmpty = linearLayout;
        this.rvNotification = recyclerView;
        this.titleView = headTitleLinearView;
        this.tvNoticeMsg = textView;
        this.tvNoticeSys = textView2;
    }

    public static ActivityNoticeListBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNoticeListBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityNoticeListBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_notice_list, viewGroup, z, obj);
    }

    public static ActivityNoticeListBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNoticeListBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityNoticeListBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_notice_list, null, false, obj);
    }

    public static ActivityNoticeListBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNoticeListBinding bind(View view2, Object obj) {
        return (ActivityNoticeListBinding) bind(obj, view2, C0726R.layout.activity_notice_list);
    }
}
