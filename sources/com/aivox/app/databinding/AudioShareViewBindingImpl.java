package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.DialogTitleItemView;
import com.aivox.common_ui.DialogTitleView;

/* JADX INFO: loaded from: classes.dex */
public class AudioShareViewBindingImpl extends AudioShareViewBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback40;
    private final View.OnClickListener mCallback41;
    private final View.OnClickListener mCallback42;
    private final View.OnClickListener mCallback43;
    private final View.OnClickListener mCallback44;
    private final View.OnClickListener mCallback45;
    private final View.OnClickListener mCallback46;
    private final View.OnClickListener mCallback47;
    private final View.OnClickListener mCallback48;
    private final View.OnClickListener mCallback49;
    private final View.OnClickListener mCallback50;
    private final View.OnClickListener mCallback51;
    private final View.OnClickListener mCallback52;
    private final View.OnClickListener mCallback53;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.dtv_title, 15);
        sparseIntArray.put(C0726R.id.dtiv_item_title, 16);
        sparseIntArray.put(C0726R.id.cl_content, 17);
        sparseIntArray.put(C0726R.id.iv_encrypt, 18);
        sparseIntArray.put(C0726R.id.tv_encrypt, 19);
        sparseIntArray.put(C0726R.id.iv_delete_after_read, 20);
        sparseIntArray.put(C0726R.id.tv_delete_after_read, 21);
        sparseIntArray.put(C0726R.id.dtiv_time_title, 22);
        sparseIntArray.put(C0726R.id.hsv_valid_time, 23);
        sparseIntArray.put(C0726R.id.ll_copy_link, 24);
        sparseIntArray.put(C0726R.id.tv_link, 25);
        sparseIntArray.put(C0726R.id.iv_allow_save, 26);
        sparseIntArray.put(C0726R.id.tv_allow_save, 27);
        sparseIntArray.put(C0726R.id.group_content, 28);
        sparseIntArray.put(C0726R.id.ll_share, 29);
        sparseIntArray.put(C0726R.id.iv_wx, 30);
        sparseIntArray.put(C0726R.id.file_share_wechat, 31);
        sparseIntArray.put(C0726R.id.iv_qq, 32);
        sparseIntArray.put(C0726R.id.file_share_qq, 33);
        sparseIntArray.put(C0726R.id.iv_circle, 34);
        sparseIntArray.put(C0726R.id.file_share_circle, 35);
        sparseIntArray.put(C0726R.id.iv_line, 36);
        sparseIntArray.put(C0726R.id.file_share_line, 37);
        sparseIntArray.put(C0726R.id.iv_share_url, 38);
        sparseIntArray.put(C0726R.id.tv_share_url, 39);
    }

    public AudioShareViewBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 40, sIncludes, sViewsWithIds));
    }

    private AudioShareViewBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[8], (ConstraintLayout) objArr[12], (ConstraintLayout) objArr[17], (ConstraintLayout) objArr[2], (ConstraintLayout) objArr[1], (ConstraintLayout) objArr[13], (ConstraintLayout) objArr[11], (ConstraintLayout) objArr[14], (ConstraintLayout) objArr[10], (DialogTitleItemView) objArr[16], (DialogTitleItemView) objArr[22], (DialogTitleView) objArr[15], (TextView) objArr[35], (TextView) objArr[37], (TextView) objArr[33], (TextView) objArr[31], (Group) objArr[28], (HorizontalScrollView) objArr[23], (ImageView) objArr[26], (ImageView) objArr[34], (ImageView) objArr[20], (ImageView) objArr[18], (ImageView) objArr[36], (ImageView) objArr[32], (ImageView) objArr[38], (ImageView) objArr[30], (LinearLayout) objArr[24], (LinearLayout) objArr[29], (TextView) objArr[27], (TextView) objArr[7], (TextView) objArr[3], (TextView) objArr[5], (TextView) objArr[4], (TextView) objArr[6], (TextView) objArr[21], (TextView) objArr[19], (TextView) objArr[25], (TextView) objArr[9], (TextView) objArr[39]);
        this.mDirtyFlags = -1L;
        this.clAllowSave.setTag(null);
        this.clCircle.setTag(null);
        this.clDeleteAfterRead.setTag(null);
        this.clEncrypt.setTag(null);
        this.clLine.setTag(null);
        this.clQq.setTag(null);
        this.clShareUrl.setTag(null);
        this.clWechat.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvCopyLink.setTag(null);
        this.tvDay1.setTag(null);
        this.tvDay30.setTag(null);
        this.tvDay7.setTag(null);
        this.tvDay90.setTag(null);
        this.tvShare.setTag(null);
        setRootTag(view2);
        this.mCallback47 = new OnClickListener(this, 8);
        this.mCallback43 = new OnClickListener(this, 4);
        this.mCallback42 = new OnClickListener(this, 3);
        this.mCallback50 = new OnClickListener(this, 11);
        this.mCallback48 = new OnClickListener(this, 9);
        this.mCallback44 = new OnClickListener(this, 5);
        this.mCallback51 = new OnClickListener(this, 12);
        this.mCallback49 = new OnClickListener(this, 10);
        this.mCallback45 = new OnClickListener(this, 6);
        this.mCallback52 = new OnClickListener(this, 13);
        this.mCallback40 = new OnClickListener(this, 1);
        this.mCallback46 = new OnClickListener(this, 7);
        this.mCallback41 = new OnClickListener(this, 2);
        this.mCallback53 = new OnClickListener(this, 14);
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

    @Override // com.aivox.app.databinding.AudioShareViewBinding
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
            this.clAllowSave.setOnClickListener(this.mCallback47);
            this.clCircle.setOnClickListener(this.mCallback51);
            this.clDeleteAfterRead.setOnClickListener(this.mCallback41);
            this.clEncrypt.setOnClickListener(this.mCallback40);
            this.clLine.setOnClickListener(this.mCallback52);
            this.clQq.setOnClickListener(this.mCallback50);
            this.clShareUrl.setOnClickListener(this.mCallback53);
            this.clWechat.setOnClickListener(this.mCallback49);
            this.tvCopyLink.setOnClickListener(this.mCallback46);
            this.tvDay1.setOnClickListener(this.mCallback42);
            this.tvDay30.setOnClickListener(this.mCallback44);
            this.tvDay7.setOnClickListener(this.mCallback43);
            this.tvDay90.setOnClickListener(this.mCallback45);
            this.tvShare.setOnClickListener(this.mCallback48);
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
        }
    }
}
