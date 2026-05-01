package com.aivox.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.AudioShareViewBinding;
import com.aivox.base.C0874R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common.model.DataHandle;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.antishake.AntiShake;

/* JADX INFO: loaded from: classes.dex */
public class AudioShareView extends BaseDialogViewWrapper implements OnViewClickListener {
    private boolean allowSave;
    private boolean isDeleteAfterRead;
    private boolean isEncrypt;
    private AudioShareViewBinding mBinding;
    private Context mContext;
    private MyOnClickListener myOnClickListener;
    private int validPeriod;

    public interface MyOnClickListener {
        void toShare(int i, boolean z, boolean z2, int i2, boolean z3);
    }

    public AudioShareView(Context context) {
        super(context);
        this.isEncrypt = true;
        this.allowSave = false;
        this.validPeriod = 1;
        AudioShareViewBinding audioShareViewBindingInflate = AudioShareViewBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = audioShareViewBindingInflate;
        audioShareViewBindingInflate.setClickListener(this);
        this.mBinding.dtvTitle.setViewClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioShareView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2418lambda$new$0$comaivoxappviewAudioShareView(view2);
            }
        });
        this.mContext = context;
        initView();
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-app-view-AudioShareView, reason: not valid java name */
    /* synthetic */ void m2418lambda$new$0$comaivoxappviewAudioShareView(View view2) {
        this.mDialog.dismiss();
    }

    private void initView() {
        this.mBinding.dtvTitle.setTitle(this.mContext.getString(C0874R.string.dialog_share_link));
    }

    private void toShare(int i) {
        if (this.mDialog != null && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        MyOnClickListener myOnClickListener = this.myOnClickListener;
        if (myOnClickListener != null) {
            myOnClickListener.toShare(i, this.isEncrypt, this.isDeleteAfterRead, this.validPeriod, this.allowSave);
        }
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(view2)) {
            return;
        }
        if (this.myOnClickListener == null) {
            LogUtil.m336e("clickListener is null");
            return;
        }
        int id = view2.getId();
        if (id == C0726R.id.cl_wechat) {
            toShare(1);
            return;
        }
        if (id == C0726R.id.cl_circle) {
            toShare(2);
            return;
        }
        if (id == C0726R.id.cl_qq) {
            toShare(3);
            return;
        }
        if (id == C0726R.id.cl_line) {
            toShare(5);
            return;
        }
        if (id == C0726R.id.cl_share_url) {
            toShare(0);
            return;
        }
        if (id == C0726R.id.cl_encrypt) {
            this.isEncrypt = !this.isEncrypt;
            this.mBinding.ivEncrypt.setImageResource(this.isEncrypt ? C1034R.drawable.check_box_select_checked : C1034R.drawable.check_box_select);
            return;
        }
        if (id == C0726R.id.cl_delete_after_read) {
            this.isDeleteAfterRead = !this.isDeleteAfterRead;
            this.mBinding.ivDeleteAfterRead.setImageResource(this.isDeleteAfterRead ? C1034R.drawable.check_box_select_checked : C1034R.drawable.check_box_select);
            return;
        }
        if (id == C0726R.id.cl_allow_save) {
            if (DataHandle.getIns().isVip()) {
                this.allowSave = !this.allowSave;
                this.mBinding.ivAllowSave.setImageResource(this.allowSave ? C1034R.drawable.check_box_select_checked : C1034R.drawable.check_box_select);
                return;
            } else {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.vip_feature_notice));
                return;
            }
        }
        if (id == C0726R.id.tv_day_1) {
            refreshValid(0);
            return;
        }
        if (id == C0726R.id.tv_day_7) {
            refreshValid(1);
            return;
        }
        if (id == C0726R.id.tv_day_30) {
            refreshValid(2);
            return;
        }
        if (id == C0726R.id.tv_day_90) {
            refreshValid(3);
        } else if (id == C0726R.id.tv_share) {
            toShare(0);
        } else if (id == C0726R.id.tv_copy_link) {
            toShare(4);
        }
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public void refreshValid(int i) {
        this.validPeriod = i;
        this.mBinding.tvDay1.setTextColor(getResources().getColor(i == 0 ? C0874R.color.txt_color_primary : C0874R.color.txt_color_tertiary));
        this.mBinding.tvDay7.setTextColor(getResources().getColor(i == 1 ? C0874R.color.txt_color_primary : C0874R.color.txt_color_tertiary));
        this.mBinding.tvDay30.setTextColor(getResources().getColor(i == 2 ? C0874R.color.txt_color_primary : C0874R.color.txt_color_tertiary));
        this.mBinding.tvDay90.setTextColor(getResources().getColor(i == 3 ? C0874R.color.txt_color_primary : C0874R.color.txt_color_tertiary));
        this.mBinding.tvDay1.setBackgroundResource(i == 0 ? C1034R.drawable.bg_tag_selected : C1034R.drawable.bg_tag_normal);
        this.mBinding.tvDay7.setBackgroundResource(i == 1 ? C1034R.drawable.bg_tag_selected : C1034R.drawable.bg_tag_normal);
        this.mBinding.tvDay30.setBackgroundResource(i == 2 ? C1034R.drawable.bg_tag_selected : C1034R.drawable.bg_tag_normal);
        this.mBinding.tvDay90.setBackgroundResource(i == 3 ? C1034R.drawable.bg_tag_selected : C1034R.drawable.bg_tag_normal);
    }
}
