package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.PowerfulEditText;

/* JADX INFO: loaded from: classes.dex */
public class ActivityRecordInfoBindingImpl extends ActivityRecordInfoBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback72;
    private final View.OnClickListener mCallback73;
    private final View.OnClickListener mCallback74;
    private final View.OnClickListener mCallback75;
    private final View.OnClickListener mCallback76;
    private final View.OnClickListener mCallback77;
    private final View.OnClickListener mCallback78;
    private final View.OnClickListener mCallback79;
    private final View.OnClickListener mCallback80;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.cl_main, 10);
        sparseIntArray.put(C0726R.id.cl_toolbar, 11);
        sparseIntArray.put(C0726R.id.rl_top, 12);
        sparseIntArray.put(C0726R.id.imgBack, 13);
        sparseIntArray.put(C0726R.id.cl_search, 14);
        sparseIntArray.put(C0726R.id.ll_search, 15);
        sparseIntArray.put(C0726R.id.et_search, 16);
        sparseIntArray.put(C0726R.id.rl_edit, 17);
        sparseIntArray.put(C0726R.id.tv_cancel, 18);
        sparseIntArray.put(C0726R.id.tv_save, 19);
        sparseIntArray.put(C0726R.id.tv_edit_title, 20);
        sparseIntArray.put(C0726R.id.cl_main_in, 21);
        sparseIntArray.put(C0726R.id.ll_noti, 22);
        sparseIntArray.put(C0726R.id.tv_noti, 23);
        sparseIntArray.put(C0726R.id.fragment_container, 24);
        sparseIntArray.put(C0726R.id.ll_btm_notify, 25);
        sparseIntArray.put(C0726R.id.iv_loading, 26);
        sparseIntArray.put(C0726R.id.tv_msg, 27);
        sparseIntArray.put(C0726R.id.cl_bottom, 28);
        sparseIntArray.put(C0726R.id.ll_time, 29);
        sparseIntArray.put(C0726R.id.tv_record_time_cur, 30);
        sparseIntArray.put(C0726R.id.sb_progress, 31);
        sparseIntArray.put(C0726R.id.tv_record_time_total, 32);
        sparseIntArray.put(C0726R.id.rl_note_mark, 33);
        sparseIntArray.put(C0726R.id.iv_note_mark, 34);
        sparseIntArray.put(C0726R.id.tv_note_mark, 35);
        sparseIntArray.put(C0726R.id.cl_ai_summary, 36);
        sparseIntArray.put(C0726R.id.rv_scenes, 37);
        sparseIntArray.put(C0726R.id.ll_ai_talk, 38);
        sparseIntArray.put(C0726R.id.et_ai_talk, 39);
        sparseIntArray.put(C0726R.id.vp_guide, 40);
        sparseIntArray.put(C0726R.id.view_skip, 41);
        sparseIntArray.put(C0726R.id.view_prev, 42);
        sparseIntArray.put(C0726R.id.view_next, 43);
        sparseIntArray.put(C0726R.id.gp_guide, 44);
    }

    public ActivityRecordInfoBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 45, sIncludes, sViewsWithIds));
    }

    private ActivityRecordInfoBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[36], (ConstraintLayout) objArr[28], (ConstraintLayout) objArr[10], (ConstraintLayout) objArr[21], (ConstraintLayout) objArr[14], (ConstraintLayout) objArr[11], (EditText) objArr[39], (PowerfulEditText) objArr[16], (FrameLayout) objArr[24], (Group) objArr[44], (ImageView) objArr[13], (ImageView) objArr[9], (ImageView) objArr[4], (ImageView) objArr[26], (ImageView) objArr[34], (ImageView) objArr[3], (ImageView) objArr[2], (ImageView) objArr[8], (LinearLayout) objArr[38], (LinearLayout) objArr[25], (LinearLayout) objArr[22], (LinearLayout) objArr[15], (LinearLayout) objArr[29], (LinearLayout) objArr[1], (ImageView) objArr[6], (ImageView) objArr[7], (ConstraintLayout) objArr[17], (RelativeLayout) objArr[33], (ConstraintLayout) objArr[12], (RecyclerView) objArr[37], (SeekBar) objArr[31], (TextView) objArr[18], (TextView) objArr[20], (TextView) objArr[27], (TextView) objArr[35], (TextView) objArr[23], (TextView) objArr[30], (TextView) objArr[32], (TextView) objArr[19], (TextView) objArr[5], (View) objArr[43], (View) objArr[42], (View) objArr[41], (ViewPager) objArr[40]);
        this.mDirtyFlags = -1L;
        this.ivAiTalkSend.setTag(null);
        this.ivBottomMore.setTag(null);
        this.ivOperateAi.setTag(null);
        this.ivSearch.setTag(null);
        this.ivSpeed.setTag(null);
        this.llyBack.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.playStartIv.setTag(null);
        this.playStopIv.setTag(null);
        this.tvSearchCancel.setTag(null);
        setRootTag(view2);
        this.mCallback79 = new OnClickListener(this, 8);
        this.mCallback74 = new OnClickListener(this, 3);
        this.mCallback76 = new OnClickListener(this, 5);
        this.mCallback75 = new OnClickListener(this, 4);
        this.mCallback77 = new OnClickListener(this, 6);
        this.mCallback72 = new OnClickListener(this, 1);
        this.mCallback80 = new OnClickListener(this, 9);
        this.mCallback78 = new OnClickListener(this, 7);
        this.mCallback73 = new OnClickListener(this, 2);
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

    @Override // com.aivox.app.databinding.ActivityRecordInfoBinding
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
            this.ivAiTalkSend.setOnClickListener(this.mCallback80);
            this.ivBottomMore.setOnClickListener(this.mCallback75);
            this.ivOperateAi.setOnClickListener(this.mCallback74);
            this.ivSearch.setOnClickListener(this.mCallback73);
            this.ivSpeed.setOnClickListener(this.mCallback79);
            this.llyBack.setOnClickListener(this.mCallback72);
            this.playStartIv.setOnClickListener(this.mCallback77);
            this.playStopIv.setOnClickListener(this.mCallback78);
            this.tvSearchCancel.setOnClickListener(this.mCallback76);
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
        }
    }
}
