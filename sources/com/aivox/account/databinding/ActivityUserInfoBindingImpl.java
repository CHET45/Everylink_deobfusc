package com.aivox.account.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0706BR;
import com.aivox.account.C0707R;
import com.aivox.account.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.RoundedCornerBitmap;
import com.aivox.common_ui.SettingTileView;

/* JADX INFO: loaded from: classes.dex */
public class ActivityUserInfoBindingImpl extends ActivityUserInfoBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback1;
    private final View.OnClickListener mCallback2;
    private final View.OnClickListener mCallback3;
    private final View.OnClickListener mCallback4;
    private final View.OnClickListener mCallback5;
    private final View.OnClickListener mCallback6;
    private final View.OnClickListener mCallback7;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0707R.id.title_view, 8);
        sparseIntArray.put(C0707R.id.tv_head, 9);
        sparseIntArray.put(C0707R.id.iv_head, 10);
        sparseIntArray.put(C0707R.id.cl_center, 11);
        sparseIntArray.put(C0707R.id.stv_phone, 12);
    }

    public ActivityUserInfoBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 13, sIncludes, sViewsWithIds));
    }

    private ActivityUserInfoBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LinearLayout) objArr[11], (ConstraintLayout) objArr[1], (RoundedCornerBitmap) objArr[10], (SettingTileView) objArr[3], (SettingTileView) objArr[4], (SettingTileView) objArr[2], (SettingTileView) objArr[12], (SettingTileView) objArr[5], (HeadTitleLinearView) objArr[8], (TextView) objArr[7], (TextView) objArr[9], (LoadingButton) objArr[6]);
        this.mDirtyFlags = -1L;
        this.clHead.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.stvBirthday.setTag(null);
        this.stvGender.setTag(null);
        this.stvNickname.setTag(null);
        this.stvPwd.setTag(null);
        this.tvDeleteAccount.setTag(null);
        this.tvSignOut.setTag(null);
        setRootTag(view2);
        this.mCallback7 = new OnClickListener(this, 7);
        this.mCallback5 = new OnClickListener(this, 5);
        this.mCallback6 = new OnClickListener(this, 6);
        this.mCallback3 = new OnClickListener(this, 3);
        this.mCallback4 = new OnClickListener(this, 4);
        this.mCallback1 = new OnClickListener(this, 1);
        this.mCallback2 = new OnClickListener(this, 2);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
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
    public boolean setVariable(int i, Object obj) {
        if (C0706BR.clickListener != i) {
            return false;
        }
        setClickListener((OnViewClickListener) obj);
        return true;
    }

    @Override // com.aivox.account.databinding.ActivityUserInfoBinding
    public void setClickListener(OnViewClickListener onViewClickListener) {
        this.mClickListener = onViewClickListener;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(C0706BR.clickListener);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        OnViewClickListener onViewClickListener = this.mClickListener;
        if ((j & 2) != 0) {
            this.clHead.setOnClickListener(this.mCallback1);
            this.stvBirthday.setOnClickListener(this.mCallback3);
            this.stvGender.setOnClickListener(this.mCallback4);
            this.stvNickname.setOnClickListener(this.mCallback2);
            this.stvPwd.setOnClickListener(this.mCallback5);
            this.tvDeleteAccount.setOnClickListener(this.mCallback7);
            this.tvSignOut.setOnClickListener(this.mCallback6);
        }
    }

    @Override // com.aivox.account.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int i, View view2) {
        switch (i) {
            case 1:
                OnViewClickListener onViewClickListener = this.mClickListener;
                if (onViewClickListener != null) {
                    onViewClickListener.onViewClick(view2);
                }
                break;
            case 2:
                OnViewClickListener onViewClickListener2 = this.mClickListener;
                if (onViewClickListener2 != null) {
                    onViewClickListener2.onViewClick(view2);
                }
                break;
            case 3:
                OnViewClickListener onViewClickListener3 = this.mClickListener;
                if (onViewClickListener3 != null) {
                    onViewClickListener3.onViewClick(view2);
                }
                break;
            case 4:
                OnViewClickListener onViewClickListener4 = this.mClickListener;
                if (onViewClickListener4 != null) {
                    onViewClickListener4.onViewClick(view2);
                }
                break;
            case 5:
                OnViewClickListener onViewClickListener5 = this.mClickListener;
                if (onViewClickListener5 != null) {
                    onViewClickListener5.onViewClick(view2);
                }
                break;
            case 6:
                OnViewClickListener onViewClickListener6 = this.mClickListener;
                if (onViewClickListener6 != null) {
                    onViewClickListener6.onViewClick(view2);
                }
                break;
            case 7:
                OnViewClickListener onViewClickListener7 = this.mClickListener;
                if (onViewClickListener7 != null) {
                    onViewClickListener7.onViewClick(view2);
                }
                break;
        }
    }
}
