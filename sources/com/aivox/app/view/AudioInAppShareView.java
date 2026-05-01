package com.aivox.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.AudioInAppShareViewBinding;
import com.aivox.base.C0874R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.http.HttpException;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.OtherUserInfo;
import com.aivox.common.util.AppUtils;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.KeyboardUtils;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class AudioInAppShareView extends BaseDialogViewWrapper implements OnViewClickListener {
    private final AudioInAppShareViewBinding mBinding;
    private final Context mContext;
    private OtherUserInfo mOtherUserInfo;
    private MyOnClickListener myOnClickListener;
    private int validPeriod;

    public interface MyOnClickListener {
        void toShare(int i, String str);
    }

    public AudioInAppShareView(final Context context) {
        super(context);
        this.validPeriod = 1;
        this.mContext = context;
        AudioInAppShareViewBinding audioInAppShareViewBindingInflate = AudioInAppShareViewBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = audioInAppShareViewBindingInflate;
        audioInAppShareViewBindingInflate.setClickListener(this);
        audioInAppShareViewBindingInflate.dtvTitle.setViewClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioInAppShareView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2414lambda$new$0$comaivoxappviewAudioInAppShareView(view2);
            }
        });
        audioInAppShareViewBindingInflate.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.aivox.app.view.AudioInAppShareView$$ExternalSyntheticLambda2
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return this.f$0.m2415lambda$new$1$comaivoxappviewAudioInAppShareView(context, textView, i, keyEvent);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-app-view-AudioInAppShareView, reason: not valid java name */
    /* synthetic */ void m2414lambda$new$0$comaivoxappviewAudioInAppShareView(View view2) {
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-app-view-AudioInAppShareView, reason: not valid java name */
    /* synthetic */ boolean m2415lambda$new$1$comaivoxappviewAudioInAppShareView(Context context, TextView textView, int i, KeyEvent keyEvent) {
        if (i != 3) {
            return false;
        }
        this.mBinding.etSearch.clearFocus();
        DialogUtils.showLoadingDialog(context);
        KeyboardUtils.hideSoftInput(this);
        searchUser();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchUser() {
        new AudioService(this.mContext).getOtherUserInfo(this.mBinding.etSearch.getEditableText().toString()).doFinally(new Action() { // from class: com.aivox.app.view.AudioInAppShareView$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Action
            public final void run() {
                DialogUtils.hideLoadingDialog();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.view.AudioInAppShareView$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2416lambda$searchUser$2$comaivoxappviewAudioInAppShareView((OtherUserInfo) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.view.AudioInAppShareView$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2417lambda$searchUser$3$comaivoxappviewAudioInAppShareView((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$searchUser$2$com-aivox-app-view-AudioInAppShareView, reason: not valid java name */
    /* synthetic */ void m2416lambda$searchUser$2$comaivoxappviewAudioInAppShareView(OtherUserInfo otherUserInfo) throws Exception {
        this.mBinding.btnContinue.setEnabled(true);
        this.mOtherUserInfo = otherUserInfo;
        this.mBinding.llUserInfo.setVisibility(0);
        this.mBinding.tvUserName.setText(otherUserInfo.getEmail());
        if (BaseStringUtil.isNotEmpty(otherUserInfo.getAvatar())) {
            this.mBinding.tvUserAvatar.setVisibility(8);
            this.mBinding.ivUserAvatar.setVisibility(0);
            ImageLoaderFactory.getLoader().displayImage(this.mBinding.ivUserAvatar, otherUserInfo.getAvatar());
        } else {
            this.mBinding.tvUserAvatar.setVisibility(0);
            this.mBinding.ivUserAvatar.setVisibility(8);
            this.mBinding.tvUserAvatar.setText(otherUserInfo.getEmail());
        }
    }

    /* JADX INFO: renamed from: lambda$searchUser$3$com-aivox-app-view-AudioInAppShareView, reason: not valid java name */
    /* synthetic */ void m2417lambda$searchUser$3$comaivoxappviewAudioInAppShareView(Throwable th) throws Exception {
        this.mBinding.btnContinue.setEnabled(false);
        this.mOtherUserInfo = null;
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mContext);
        } else {
            AppUtils.showError(this.mContext, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.view.AudioInAppShareView$$ExternalSyntheticLambda0
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.searchUser();
                }
            });
        }
    }

    private void toShare() {
        if (this.mDialog != null && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        MyOnClickListener myOnClickListener = this.myOnClickListener;
        if (myOnClickListener != null) {
            myOnClickListener.toShare(this.validPeriod, this.mOtherUserInfo.getUuid());
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
        } else if (id == C0726R.id.tv_day_90) {
            refreshValid(3);
        } else if (id == C0726R.id.btn_continue) {
            toShare();
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
