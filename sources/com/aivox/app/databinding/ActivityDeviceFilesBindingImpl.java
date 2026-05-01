package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public class ActivityDeviceFilesBindingImpl extends ActivityDeviceFilesBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        return true;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.title_view, 1);
        sparseIntArray.put(C0726R.id.iv_close_x, 2);
        sparseIntArray.put(C0726R.id.ll_wifi_info, 3);
        sparseIntArray.put(C0726R.id.tv_wifi_name, 4);
        sparseIntArray.put(C0726R.id.tv_wifi_pwd, 5);
        sparseIntArray.put(C0726R.id.tv_wifi_notice, 6);
        sparseIntArray.put(C0726R.id.btn_connect, 7);
        sparseIntArray.put(C0726R.id.rv_device_files, 8);
    }

    public ActivityDeviceFilesBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 9, sIncludes, sViewsWithIds));
    }

    private ActivityDeviceFilesBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[7], (ImageView) objArr[2], (LinearLayout) objArr[3], (LinearLayout) objArr[0], (RecyclerView) objArr[8], (HeadTitleLinearView) objArr[1], (TextView) objArr[4], (TextView) objArr[6], (TextView) objArr[5]);
        this.mDirtyFlags = -1L;
        this.main.setTag(null);
        setRootTag(view2);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
