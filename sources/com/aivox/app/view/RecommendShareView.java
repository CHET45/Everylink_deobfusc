package com.aivox.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.IncludeRecommendShareBinding;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common.util.StringUtil;
import com.aivox.common_ui.antishake.AntiShake;

/* JADX INFO: loaded from: classes.dex */
public class RecommendShareView extends BaseDialogViewWrapper implements OnViewClickListener {
    private final IncludeRecommendShareBinding mBinding;
    private MyOnClickListener myOnClickListener;

    public interface MyOnClickListener {
        void toShare(int i);
    }

    public RecommendShareView(Context context) {
        super(context);
        IncludeRecommendShareBinding includeRecommendShareBindingInflate = IncludeRecommendShareBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = includeRecommendShareBindingInflate;
        includeRecommendShareBindingInflate.setClickListener(this);
        if (StringUtil.isOversea()) {
            includeRecommendShareBindingInflate.cl1Share.setVisibility(8);
            includeRecommendShareBindingInflate.tvToshare.setVisibility(0);
        } else {
            includeRecommendShareBindingInflate.tvToshare.setVisibility(8);
            includeRecommendShareBindingInflate.cl1Share.setVisibility(0);
        }
    }

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    private void toShare(int i) {
        if (this.mDialog != null && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        MyOnClickListener myOnClickListener = this.myOnClickListener;
        if (myOnClickListener != null) {
            myOnClickListener.toShare(i);
        }
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(view2)) {
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
        if (id == C0726R.id.tv_toshare) {
            toShare(4);
        } else if (id == C0726R.id.cl_share_url) {
            toShare(0);
        } else if (id == C0726R.id.btn_close) {
            this.mDialog.dismiss();
        }
    }
}
