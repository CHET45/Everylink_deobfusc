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

/* JADX INFO: loaded from: classes.dex */
public class ActivityDeviceScanBindingImpl extends ActivityDeviceScanBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

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
        sparseIntArray.put(C0726R.id.tv_sub_title, 2);
        sparseIntArray.put(C0726R.id.tv_earphone_steps, 3);
        sparseIntArray.put(C0726R.id.tv_register_device, 4);
        sparseIntArray.put(C0726R.id.ll_connect_device, 5);
        sparseIntArray.put(C0726R.id.rcv_devices, 6);
        sparseIntArray.put(C0726R.id.ll_btm_notify, 7);
        sparseIntArray.put(C0726R.id.iv_loading, 8);
        sparseIntArray.put(C0726R.id.tv_msg, 9);
        sparseIntArray.put(C0726R.id.tv_more_devices, 10);
        sparseIntArray.put(C0726R.id.rcv_more_devices, 11);
        sparseIntArray.put(C0726R.id.ll_connect_fail, 12);
        sparseIntArray.put(C0726R.id.tv_connect, 13);
    }

    public ActivityDeviceScanBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 14, sIncludes, sViewsWithIds));
    }

    private ActivityDeviceScanBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ImageView) objArr[8], (LinearLayout) objArr[7], (LinearLayout) objArr[5], (LinearLayout) objArr[12], (RecyclerView) objArr[6], (RecyclerView) objArr[11], (HeadTitleLinearView) objArr[1], (TextView) objArr[13], (TextView) objArr[3], (TextView) objArr[10], (TextView) objArr[9], (TextView) objArr[4], (TextView) objArr[2]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) objArr[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
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
