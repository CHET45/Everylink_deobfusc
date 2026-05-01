package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.AutoSwipeRefreshView;
import com.aivox.common_ui.HomeEnterView;
import com.google.android.material.appbar.AppBarLayout;

/* JADX INFO: loaded from: classes.dex */
public class FragmentHomeBindingImpl extends FragmentHomeBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback81;
    private final View.OnClickListener mCallback82;
    private final View.OnClickListener mCallback83;
    private final View.OnClickListener mCallback84;
    private final View.OnClickListener mCallback85;
    private final View.OnClickListener mCallback86;
    private final View.OnClickListener mCallback87;
    private final View.OnClickListener mCallback88;
    private final View.OnClickListener mCallback89;
    private final View.OnClickListener mCallback90;
    private final View.OnClickListener mCallback91;
    private final View.OnClickListener mCallback92;
    private final View.OnClickListener mCallback93;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.ll_title, 14);
        sparseIntArray.put(C0726R.id.tv_home_title, 15);
        sparseIntArray.put(C0726R.id.iv_device, 16);
        sparseIntArray.put(C0726R.id.ll_title_enter, 17);
        sparseIntArray.put(C0726R.id.cl_default, 18);
        sparseIntArray.put(C0726R.id.appbar, 19);
        sparseIntArray.put(C0726R.id.gl_enter, 20);
        sparseIntArray.put(C0726R.id.hev_shorthand, 21);
        sparseIntArray.put(C0726R.id.hev_translate, 22);
        sparseIntArray.put(C0726R.id.hev_bilingual, 23);
        sparseIntArray.put(C0726R.id.hev_device, 24);
        sparseIntArray.put(C0726R.id.hev_device_connect, 25);
        sparseIntArray.put(C0726R.id.ll_audio_list, 26);
        sparseIntArray.put(C0726R.id.refresh_view, 27);
        sparseIntArray.put(C0726R.id.rv_audio_list, 28);
        sparseIntArray.put(C0726R.id.sv_glass, 29);
        sparseIntArray.put(C0726R.id.tv_device_name, 30);
        sparseIntArray.put(C0726R.id.ll_update, 31);
        sparseIntArray.put(C0726R.id.tv_battery, 32);
        sparseIntArray.put(C0726R.id.iv_battery, 33);
        sparseIntArray.put(C0726R.id.iv_device_icon, 34);
        sparseIntArray.put(C0726R.id.tv_common_functions, 35);
        sparseIntArray.put(C0726R.id.tv_basic_functions, 36);
        sparseIntArray.put(C0726R.id.gl_glass, 37);
        sparseIntArray.put(C0726R.id.iv_asr, 38);
        sparseIntArray.put(C0726R.id.iv_translate, 39);
        sparseIntArray.put(C0726R.id.iv_rtsp, 40);
        sparseIntArray.put(C0726R.id.iv_manual, 41);
    }

    public FragmentHomeBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 42, sIncludes, sViewsWithIds));
    }

    private FragmentHomeBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (AppBarLayout) objArr[19], (ConstraintLayout) objArr[10], (CoordinatorLayout) objArr[18], (ConstraintLayout) objArr[13], (ConstraintLayout) objArr[12], (ConstraintLayout) objArr[11], (GridLayout) objArr[20], (GridLayout) objArr[37], (HomeEnterView) objArr[23], (HomeEnterView) objArr[24], (HomeEnterView) objArr[25], (HomeEnterView) objArr[21], (HomeEnterView) objArr[22], (ImageView) objArr[38], (ImageView) objArr[33], (ImageView) objArr[16], (ImageView) objArr[34], (ImageView) objArr[3], (ImageView) objArr[4], (ImageView) objArr[1], (ImageView) objArr[2], (ImageView) objArr[41], (ImageView) objArr[40], (ImageView) objArr[5], (ImageView) objArr[39], (LinearLayout) objArr[26], (ConstraintLayout) objArr[6], (LinearLayout) objArr[9], (LinearLayout) objArr[7], (LinearLayout) objArr[14], (LinearLayout) objArr[17], (LinearLayout) objArr[31], (LinearLayout) objArr[8], (AutoSwipeRefreshView) objArr[27], (RecyclerView) objArr[28], (ScrollView) objArr[29], (TextView) objArr[36], (TextView) objArr[32], (TextView) objArr[35], (TextView) objArr[30], (TextView) objArr[15]);
        this.mDirtyFlags = -1L;
        this.clAsr.setTag(null);
        this.clManual.setTag(null);
        this.clRtsp.setTag(null);
        this.clTranslate.setTag(null);
        this.ivHomeEnterBilingual.setTag(null);
        this.ivHomeEnterDevice.setTag(null);
        this.ivHomeEnterShorthand.setTag(null);
        this.ivHomeEnterTranslate.setTag(null);
        this.ivSearch.setTag(null);
        this.llDevice.setTag(null);
        this.llImageRecognition.setTag(null);
        this.llPhotoCapture.setTag(null);
        this.llVideoRecording.setTag(null);
        LinearLayout linearLayout = (LinearLayout) objArr[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setRootTag(view2);
        this.mCallback87 = new OnClickListener(this, 7);
        this.mCallback86 = new OnClickListener(this, 6);
        this.mCallback82 = new OnClickListener(this, 2);
        this.mCallback90 = new OnClickListener(this, 10);
        this.mCallback88 = new OnClickListener(this, 8);
        this.mCallback83 = new OnClickListener(this, 3);
        this.mCallback91 = new OnClickListener(this, 11);
        this.mCallback89 = new OnClickListener(this, 9);
        this.mCallback84 = new OnClickListener(this, 4);
        this.mCallback92 = new OnClickListener(this, 12);
        this.mCallback85 = new OnClickListener(this, 5);
        this.mCallback81 = new OnClickListener(this, 1);
        this.mCallback93 = new OnClickListener(this, 13);
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

    @Override // com.aivox.app.databinding.FragmentHomeBinding
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
            this.clAsr.setOnClickListener(this.mCallback90);
            this.clManual.setOnClickListener(this.mCallback93);
            this.clRtsp.setOnClickListener(this.mCallback92);
            this.clTranslate.setOnClickListener(this.mCallback91);
            this.ivHomeEnterBilingual.setOnClickListener(this.mCallback83);
            this.ivHomeEnterDevice.setOnClickListener(this.mCallback84);
            this.ivHomeEnterShorthand.setOnClickListener(this.mCallback81);
            this.ivHomeEnterTranslate.setOnClickListener(this.mCallback82);
            this.ivSearch.setOnClickListener(this.mCallback85);
            this.llDevice.setOnClickListener(this.mCallback86);
            this.llImageRecognition.setOnClickListener(this.mCallback89);
            this.llPhotoCapture.setOnClickListener(this.mCallback87);
            this.llVideoRecording.setOnClickListener(this.mCallback88);
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
        }
    }
}
