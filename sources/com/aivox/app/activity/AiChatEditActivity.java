package com.aivox.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityAiChatEditBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.util.AppUtils;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class AiChatEditActivity extends BaseFragmentActivity {
    private ActivityAiChatEditBinding mBinding;
    private int mId = 0;
    private String mContent = "";

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityAiChatEditBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_ai_chat_edit);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mId = extras.getInt(Constant.KEY_IDS);
            this.mContent = extras.getString("data");
        }
        this.mBinding.etContent.setText(this.mContent);
        this.mBinding.tvEditCancel.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.AiChatEditActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2063lambda$initView$0$comaivoxappactivityAiChatEditActivity(view2);
            }
        });
        this.mBinding.tvEditSave.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.AiChatEditActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2066lambda$initView$3$comaivoxappactivityAiChatEditActivity(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-AiChatEditActivity, reason: not valid java name */
    /* synthetic */ void m2063lambda$initView$0$comaivoxappactivityAiChatEditActivity(View view2) {
        finish();
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-app-activity-AiChatEditActivity, reason: not valid java name */
    /* synthetic */ void m2066lambda$initView$3$comaivoxappactivityAiChatEditActivity(View view2) {
        String string = this.mBinding.etContent.getText().toString();
        if (BaseStringUtil.isEmpty(string)) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.can_not_edit_to_empty));
        } else {
            DialogUtils.showLoadingDialog(this.context);
            new AudioService(this.context).editAiChat(this.mId, string).subscribe(new Consumer() { // from class: com.aivox.app.activity.AiChatEditActivity$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2064lambda$initView$1$comaivoxappactivityAiChatEditActivity(obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.AiChatEditActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2065lambda$initView$2$comaivoxappactivityAiChatEditActivity((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-AiChatEditActivity, reason: not valid java name */
    /* synthetic */ void m2064lambda$initView$1$comaivoxappactivityAiChatEditActivity(Object obj) throws Exception {
        DialogUtils.hideLoadingDialog();
        Intent intent = new Intent();
        intent.putExtra(Constant.KEY_IDS, this.mId);
        intent.putExtra("data", this.mBinding.etContent.getText().toString());
        setResult(-1, intent);
        finish();
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-app-activity-AiChatEditActivity, reason: not valid java name */
    /* synthetic */ void m2065lambda$initView$2$comaivoxappactivityAiChatEditActivity(Throwable th) throws Exception {
        AppUtils.checkHttpCode(this.context);
    }
}
