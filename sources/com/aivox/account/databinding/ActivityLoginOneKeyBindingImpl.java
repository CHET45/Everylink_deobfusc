package com.aivox.account.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0707R;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public class ActivityLoginOneKeyBindingImpl extends ActivityLoginOneKeyBinding {
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
        sparseIntArray.put(C0707R.id.tv_slogan, 1);
        sparseIntArray.put(C0707R.id.btn_login, 2);
        sparseIntArray.put(C0707R.id.btn_login_phone, 3);
        sparseIntArray.put(C0707R.id.btn_login_email, 4);
        sparseIntArray.put(C0707R.id.cb_email, 5);
        sparseIntArray.put(C0707R.id.tv_policy, 6);
        sparseIntArray.put(C0707R.id.iv_google_login, 7);
    }

    public ActivityLoginOneKeyBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 8, sIncludes, sViewsWithIds));
    }

    private ActivityLoginOneKeyBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[2], (LoadingButton) objArr[4], (LoadingButton) objArr[3], (CheckBox) objArr[5], (ImageView) objArr[7], (LinearLayout) objArr[0], (TextView) objArr[6], (TextView) objArr[1]);
        this.mDirtyFlags = -1L;
        this.f112ll.setTag(null);
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
