package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityFolderSelectBinding extends ViewDataBinding {
    public final LoadingButton btnSave;
    public final ListView lvFolder;
    public final HeadTitleLinearView titleView;

    protected ActivityFolderSelectBinding(Object obj, View view2, int i, LoadingButton loadingButton, ListView listView, HeadTitleLinearView headTitleLinearView) {
        super(obj, view2, i);
        this.btnSave = loadingButton;
        this.lvFolder = listView;
        this.titleView = headTitleLinearView;
    }

    public static ActivityFolderSelectBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFolderSelectBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityFolderSelectBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_folder_select, viewGroup, z, obj);
    }

    public static ActivityFolderSelectBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFolderSelectBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityFolderSelectBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_folder_select, null, false, obj);
    }

    public static ActivityFolderSelectBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFolderSelectBinding bind(View view2, Object obj) {
        return (ActivityFolderSelectBinding) bind(obj, view2, C0726R.layout.activity_folder_select);
    }
}
