package com.aivox.account.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0707R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.LoginEditText;

/* JADX INFO: loaded from: classes.dex */
public class ActivityPhoneInputBindingImpl extends ActivityPhoneInputBinding {
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
        sparseIntArray.put(C0707R.id.title_view, 1);
        sparseIntArray.put(C0707R.id.et_phone, 2);
        sparseIntArray.put(C0707R.id.btn_send_code, 3);
        sparseIntArray.put(C0707R.id.tv_pwd_login, 4);
    }

    public ActivityPhoneInputBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 5, sIncludes, sViewsWithIds));
    }

    private ActivityPhoneInputBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[3], (LoginEditText) objArr[2], (ConstraintLayout) objArr[0], (HeadTitleLinearView) objArr[1], (TextView) objArr[4]);
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
