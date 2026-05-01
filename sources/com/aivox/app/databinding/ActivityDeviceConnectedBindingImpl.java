package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.EarphonePowerView;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.SettingTileView;

/* JADX INFO: loaded from: classes.dex */
public class ActivityDeviceConnectedBindingImpl extends ActivityDeviceConnectedBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

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
        sparseIntArray.put(C0726R.id.iv_earphone_icon, 2);
        sparseIntArray.put(C0726R.id.tv_mac, 3);
        sparseIntArray.put(C0726R.id.ep_left, 4);
        sparseIntArray.put(C0726R.id.ep_pack, 5);
        sparseIntArray.put(C0726R.id.ep_right, 6);
        sparseIntArray.put(C0726R.id.ll_eq, 7);
        sparseIntArray.put(C0726R.id.stv_eq, 8);
        sparseIntArray.put(C0726R.id.ll_nc, 9);
        sparseIntArray.put(C0726R.id.tv_nc_normal, 10);
        sparseIntArray.put(C0726R.id.tv_nc_open, 11);
        sparseIntArray.put(C0726R.id.tv_nc_trans, 12);
        sparseIntArray.put(C0726R.id.ll_settings, 13);
        sparseIntArray.put(C0726R.id.stv_anti_shake, 14);
        sparseIntArray.put(C0726R.id.ll_info, 15);
        sparseIntArray.put(C0726R.id.stv_instruction, 16);
        sparseIntArray.put(C0726R.id.stv_files, 17);
        sparseIntArray.put(C0726R.id.stv_version, 18);
        sparseIntArray.put(C0726R.id.ll_glass_current, 19);
        sparseIntArray.put(C0726R.id.stv_firmware, 20);
        sparseIntArray.put(C0726R.id.stv_wifi, 21);
        sparseIntArray.put(C0726R.id.tv_available_version, 22);
        sparseIntArray.put(C0726R.id.ll_glass_new, 23);
        sparseIntArray.put(C0726R.id.stv_firmware_new, 24);
        sparseIntArray.put(C0726R.id.stv_wifi_new, 25);
        sparseIntArray.put(C0726R.id.btn_update, 26);
        sparseIntArray.put(C0726R.id.btn_disconnect, 27);
    }

    public ActivityDeviceConnectedBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 28, sIncludes, sViewsWithIds));
    }

    private ActivityDeviceConnectedBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[27], (LoadingButton) objArr[26], (EarphonePowerView) objArr[4], (EarphonePowerView) objArr[5], (EarphonePowerView) objArr[6], (ImageView) objArr[2], (LinearLayout) objArr[7], (LinearLayout) objArr[19], (LinearLayout) objArr[23], (LinearLayout) objArr[15], (LinearLayout) objArr[9], (LinearLayout) objArr[13], (SettingTileView) objArr[14], (SettingTileView) objArr[8], (SettingTileView) objArr[17], (SettingTileView) objArr[20], (SettingTileView) objArr[24], (SettingTileView) objArr[16], (SettingTileView) objArr[18], (SettingTileView) objArr[21], (SettingTileView) objArr[25], (HeadTitleLinearView) objArr[1], (TextView) objArr[22], (TextView) objArr[3], (TextView) objArr[10], (TextView) objArr[11], (TextView) objArr[12]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
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
