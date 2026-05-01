package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.RoundedCornerBitmap;
import com.aivox.common_ui.SettingTileView;

/* JADX INFO: loaded from: classes.dex */
public class FragmentMainMeBindingImpl extends FragmentMainMeBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback23;
    private final View.OnClickListener mCallback24;
    private final View.OnClickListener mCallback25;
    private final View.OnClickListener mCallback26;
    private final View.OnClickListener mCallback27;
    private final View.OnClickListener mCallback28;
    private final View.OnClickListener mCallback29;
    private final View.OnClickListener mCallback30;
    private final View.OnClickListener mCallback31;
    private final View.OnClickListener mCallback32;
    private final View.OnClickListener mCallback33;
    private final View.OnClickListener mCallback34;
    private final View.OnClickListener mCallback35;
    private final View.OnClickListener mCallback36;
    private final View.OnClickListener mCallback37;
    private final View.OnClickListener mCallback38;
    private final View.OnClickListener mCallback39;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.tv_head, 18);
        sparseIntArray.put(C0726R.id.iv_head, 19);
        sparseIntArray.put(C0726R.id.tv_name, 20);
        sparseIntArray.put(C0726R.id.iv_arrow, 21);
        sparseIntArray.put(C0726R.id.cl_device_feature, 22);
        sparseIntArray.put(C0726R.id.tv_device_name, 23);
        sparseIntArray.put(C0726R.id.ll_left_time_title, 24);
        sparseIntArray.put(C0726R.id.tv_device_left_time, 25);
        sparseIntArray.put(C0726R.id.iv_device_infinity, 26);
        sparseIntArray.put(C0726R.id.tv_device_left_time_msg, 27);
        sparseIntArray.put(C0726R.id.tv_device_exp_date, 28);
        sparseIntArray.put(C0726R.id.tv_device_left_exp_msg, 29);
        sparseIntArray.put(C0726R.id.rv_device_rights, 30);
        sparseIntArray.put(C0726R.id.cl_time_left, 31);
        sparseIntArray.put(C0726R.id.tv_time_left, 32);
        sparseIntArray.put(C0726R.id.tv_time_left_msg, 33);
        sparseIntArray.put(C0726R.id.pb_time, 34);
        sparseIntArray.put(C0726R.id.stv_transcribe, 35);
        sparseIntArray.put(C0726R.id.stv_translate, 36);
    }

    public FragmentMainMeBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 37, sIncludes, sViewsWithIds));
    }

    private FragmentMainMeBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[22], (ConstraintLayout) objArr[1], (ConstraintLayout) objArr[31], (ImageView) objArr[21], (ImageView) objArr[26], (RoundedCornerBitmap) objArr[19], (LinearLayout) objArr[24], (ProgressBar) objArr[34], (RecyclerView) objArr[30], (SettingTileView) objArr[12], (SettingTileView) objArr[9], (SettingTileView) objArr[4], (SettingTileView) objArr[7], (SettingTileView) objArr[11], (SettingTileView) objArr[15], (SettingTileView) objArr[17], (SettingTileView) objArr[8], (SettingTileView) objArr[13], (SettingTileView) objArr[16], (SettingTileView) objArr[14], (SettingTileView) objArr[10], (SettingTileView) objArr[35], (SettingTileView) objArr[36], (SettingTileView) objArr[6], (SettingTileView) objArr[5], (SettingTileView) objArr[3], (TextView) objArr[28], (TextView) objArr[29], (TextView) objArr[25], (TextView) objArr[27], (TextView) objArr[23], (TextView) objArr[2], (TextView) objArr[18], (TextView) objArr[20], (TextView) objArr[32], (TextView) objArr[33]);
        this.mDirtyFlags = -1L;
        this.clHead.setTag(null);
        LinearLayout linearLayout = (LinearLayout) objArr[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.stvAboutUs.setTag(null);
        this.stvActivityH5.setTag(null);
        this.stvBenefitsCode.setTag(null);
        this.stvCheckUpdate.setTag(null);
        this.stvContactUs.setTag(null);
        this.stvCooperation.setTag(null);
        this.stvGuide.setTag(null);
        this.stvMembership.setTag(null);
        this.stvMyCard.setTag(null);
        this.stvNewConnect.setTag(null);
        this.stvRecommend.setTag(null);
        this.stvStorageUsage.setTag(null);
        this.stvTxtPrivacy.setTag(null);
        this.stvTxtSize.setTag(null);
        this.stvVipFeature.setTag(null);
        this.tvDoPurchase.setTag(null);
        setRootTag(view2);
        this.mCallback27 = new OnClickListener(this, 5);
        this.mCallback39 = new OnClickListener(this, 17);
        this.mCallback23 = new OnClickListener(this, 1);
        this.mCallback35 = new OnClickListener(this, 13);
        this.mCallback30 = new OnClickListener(this, 8);
        this.mCallback28 = new OnClickListener(this, 6);
        this.mCallback36 = new OnClickListener(this, 14);
        this.mCallback24 = new OnClickListener(this, 2);
        this.mCallback32 = new OnClickListener(this, 10);
        this.mCallback31 = new OnClickListener(this, 9);
        this.mCallback25 = new OnClickListener(this, 3);
        this.mCallback37 = new OnClickListener(this, 15);
        this.mCallback33 = new OnClickListener(this, 11);
        this.mCallback29 = new OnClickListener(this, 7);
        this.mCallback38 = new OnClickListener(this, 16);
        this.mCallback26 = new OnClickListener(this, 4);
        this.mCallback34 = new OnClickListener(this, 12);
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
        if (1 != i) {
            return false;
        }
        setClickListener((OnViewClickListener) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.FragmentMainMeBinding
    public void setClickListener(OnViewClickListener onViewClickListener) {
        this.mClickListener = onViewClickListener;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(1);
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
            this.clHead.setOnClickListener(this.mCallback23);
            this.stvAboutUs.setOnClickListener(this.mCallback34);
            this.stvActivityH5.setOnClickListener(this.mCallback31);
            this.stvBenefitsCode.setOnClickListener(this.mCallback26);
            this.stvCheckUpdate.setOnClickListener(this.mCallback29);
            this.stvContactUs.setOnClickListener(this.mCallback33);
            this.stvCooperation.setOnClickListener(this.mCallback37);
            this.stvGuide.setOnClickListener(this.mCallback39);
            this.stvMembership.setOnClickListener(this.mCallback30);
            this.stvMyCard.setOnClickListener(this.mCallback35);
            this.stvNewConnect.setOnClickListener(this.mCallback38);
            this.stvRecommend.setOnClickListener(this.mCallback36);
            this.stvStorageUsage.setOnClickListener(this.mCallback32);
            this.stvTxtPrivacy.setOnClickListener(this.mCallback28);
            this.stvTxtSize.setOnClickListener(this.mCallback27);
            this.stvVipFeature.setOnClickListener(this.mCallback25);
            this.tvDoPurchase.setOnClickListener(this.mCallback24);
        }
    }

    @Override // com.aivox.app.generated.callback.OnClickListener.Listener
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
            case 8:
                OnViewClickListener onViewClickListener8 = this.mClickListener;
                if (onViewClickListener8 != null) {
                    onViewClickListener8.onViewClick(view2);
                }
                break;
            case 9:
                OnViewClickListener onViewClickListener9 = this.mClickListener;
                if (onViewClickListener9 != null) {
                    onViewClickListener9.onViewClick(view2);
                }
                break;
            case 10:
                OnViewClickListener onViewClickListener10 = this.mClickListener;
                if (onViewClickListener10 != null) {
                    onViewClickListener10.onViewClick(view2);
                }
                break;
            case 11:
                OnViewClickListener onViewClickListener11 = this.mClickListener;
                if (onViewClickListener11 != null) {
                    onViewClickListener11.onViewClick(view2);
                }
                break;
            case 12:
                OnViewClickListener onViewClickListener12 = this.mClickListener;
                if (onViewClickListener12 != null) {
                    onViewClickListener12.onViewClick(view2);
                }
                break;
            case 13:
                OnViewClickListener onViewClickListener13 = this.mClickListener;
                if (onViewClickListener13 != null) {
                    onViewClickListener13.onViewClick(view2);
                }
                break;
            case 14:
                OnViewClickListener onViewClickListener14 = this.mClickListener;
                if (onViewClickListener14 != null) {
                    onViewClickListener14.onViewClick(view2);
                }
                break;
            case 15:
                OnViewClickListener onViewClickListener15 = this.mClickListener;
                if (onViewClickListener15 != null) {
                    onViewClickListener15.onViewClick(view2);
                }
                break;
            case 16:
                OnViewClickListener onViewClickListener16 = this.mClickListener;
                if (onViewClickListener16 != null) {
                    onViewClickListener16.onViewClick(view2);
                }
                break;
            case 17:
                OnViewClickListener onViewClickListener17 = this.mClickListener;
                if (onViewClickListener17 != null) {
                    onViewClickListener17.onViewClick(view2);
                }
                break;
        }
    }
}
