package com.aivox.set.activity;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SpanUtils;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.http.UserService;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.FunctionRightsBean;
import com.aivox.common.model.PricePackageList;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.pay.PayHelper;
import com.aivox.common.util.AppUtils;
import com.aivox.common_ui.antishake.AntiShake;
import com.aivox.set.C1106R;
import com.aivox.set.databinding.ActivityVipJoinBinding;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class VipJoinActivity extends BaseFragmentActivity implements PayHelper.SubListener {
    private AudioService mApi;
    private ActivityVipJoinBinding mBinding;
    private int mCurIndex;
    private String mOrderNo;
    private SQLiteDataBaseManager manager;
    private String pickPriceUUid;
    private UserInfo userInfo;
    private final long MONTH_VIP_TIME = 2678400000L;
    private final List<PricePackageList> mPackageList = new ArrayList();

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mApi = new AudioService(this.context);
        SQLiteDataBaseManager sQLiteDataBaseManager = new SQLiteDataBaseManager(this);
        this.manager = sQLiteDataBaseManager;
        this.userInfo = sQLiteDataBaseManager.getUserInfo();
        this.mBinding = (ActivityVipJoinBinding) DataBindingUtil.setContentView(this, C1106R.layout.activity_vip_join);
        this.mBinding.tvAgreement.setText(new SpanUtils().setFlag(17).append(getString(C0874R.string.agree_purchase)).append(getString(C0874R.string.policy_dialog_policy)).setClickSpan(new ClickableSpan() { // from class: com.aivox.set.activity.VipJoinActivity.1
            @Override // android.text.style.ClickableSpan
            public void onClick(View view2) {
                if (!AntiShake.check(this)) {
                    VipJoinActivity vipJoinActivity = VipJoinActivity.this;
                    BaseAppUtils.startActivityForWeb(vipJoinActivity, vipJoinActivity.getString(C0874R.string.h5_path_privacy), ARouterUtils.getClass(MainAction.WEB));
                }
                ((TextView) view2).setHighlightColor(VipJoinActivity.this.getColor(R.color.transparent));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(VipJoinActivity.this.getColor(C0874R.color.txt_color_highlight));
                textPaint.setUnderlineText(true);
                textPaint.clearShadowLayer();
            }
        }).append(".").create());
        this.mBinding.tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        this.mBinding.tvAgreement.setHighlightColor(getResources().getColor(R.color.transparent));
        this.mBinding.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2567lambda$initView$0$comaivoxsetactivityVipJoinActivity(view2);
            }
        });
        new View.OnClickListener() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2568lambda$initView$1$comaivoxsetactivityVipJoinActivity(view2);
            }
        };
        this.mBinding.cvTrial.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2569lambda$initView$2$comaivoxsetactivityVipJoinActivity(view2);
            }
        });
        this.mBinding.vpvMonth.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2570lambda$initView$3$comaivoxsetactivityVipJoinActivity(view2);
            }
        });
        this.mBinding.vpvYear.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2571lambda$initView$4$comaivoxsetactivityVipJoinActivity(view2);
            }
        });
        loadData();
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2567lambda$initView$0$comaivoxsetactivityVipJoinActivity(View view2) {
        finish();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2568lambda$initView$1$comaivoxsetactivityVipJoinActivity(View view2) {
        String str;
        PayHelper payHelper = PayHelper.getInstance();
        String str2 = this.pickPriceUUid;
        if (this.mPackageList.size() == 1) {
            str = PayHelper.GOOGLE_SUB_MONTHLY_FIRST_FREE;
        } else if (this.mCurIndex == 1) {
            str = PayHelper.GOOGLE_SUB_ANNUALLY;
        } else {
            str = PayHelper.GOOGLE_SUB_MONTHLY;
        }
        payHelper.doThirdSub(this, str2, str, this);
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2569lambda$initView$2$comaivoxsetactivityVipJoinActivity(View view2) {
        checkPack(0);
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2570lambda$initView$3$comaivoxsetactivityVipJoinActivity(View view2) {
        checkPack(0);
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2571lambda$initView$4$comaivoxsetactivityVipJoinActivity(View view2) {
        checkPack(1);
    }

    private void checkPack(int i) {
        String str;
        this.mCurIndex = i;
        this.pickPriceUUid = this.mPackageList.get(i).getPriceUuid();
        PayHelper payHelper = PayHelper.getInstance();
        String str2 = this.pickPriceUUid;
        if (this.mPackageList.size() == 1) {
            str = PayHelper.GOOGLE_SUB_MONTHLY_FIRST_FREE;
        } else if (this.mCurIndex == 1) {
            str = PayHelper.GOOGLE_SUB_ANNUALLY;
        } else {
            str = PayHelper.GOOGLE_SUB_MONTHLY;
        }
        payHelper.doThirdSub(this, str2, str, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData() {
        DialogUtils.showLoadingDialog(this.context);
        this.mApi.getPackageList(2).subscribe(new Consumer() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                this.f$0.refresh((List) obj);
            }
        }, new Consumer() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2572lambda$loadData$5$comaivoxsetactivityVipJoinActivity((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$loadData$5$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2572lambda$loadData$5$comaivoxsetactivityVipJoinActivity(Throwable th) throws Exception {
        LogUtil.m338i("thr:" + th.getLocalizedMessage());
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda1
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.loadData();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh(List<PricePackageList> list) {
        LogUtil.m338i("result:" + list.size());
        this.mPackageList.addAll(list);
        if (list.size() > 1) {
            this.mBinding.viewCover.setVisibility(8);
            DialogUtils.hideLoadingDialog();
            PricePackageList pricePackageList = list.get(0);
            PricePackageList pricePackageList2 = list.get(1);
            this.mBinding.vpvMonth.setData(0, pricePackageList.getPrice().doubleValue());
            this.mBinding.vpvYear.setData(1, pricePackageList2.getPrice().doubleValue());
            return;
        }
        PayHelper.getInstance().getFirstFreePrice(this, new PayHelper.GetPriceListener() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda0
            @Override // com.aivox.common.pay.PayHelper.GetPriceListener
            public final void onPriceGet(String str) {
                this.f$0.m2577lambda$refresh$6$comaivoxsetactivityVipJoinActivity(str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$refresh$6$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2577lambda$refresh$6$comaivoxsetactivityVipJoinActivity(String str) {
        this.mBinding.viewCover.setVisibility(8);
        DialogUtils.hideLoadingDialog();
        this.mBinding.vpvMonth.setTrialData(str);
        this.mBinding.vpvYear.setVisibility(8);
        this.mBinding.cvTrial.setVisibility(0);
    }

    private void cancelPayment(int i) {
        DialogUtils.showLoadingDialog(this.context);
        this.mApi.closePayment(this.mOrderNo, i).subscribe(new Consumer() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2566lambda$cancelPayment$7$comaivoxsetactivityVipJoinActivity(obj);
            }
        }, new Consumer() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                VipJoinActivity.lambda$cancelPayment$8((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$cancelPayment$7$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2566lambda$cancelPayment$7$comaivoxsetactivityVipJoinActivity(Object obj) throws Exception {
        DialogUtils.hideLoadingDialog();
        DialogUtils.showDialogWithDefBtnAndSingleListener(this.context, Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.purchase_pay_fail), null, false, true);
    }

    static /* synthetic */ void lambda$cancelPayment$8(Throwable th) throws Exception {
        th.printStackTrace();
        DialogUtils.hideLoadingDialog();
        BaseAppUtils.printErrorMsg(th);
    }

    private void refreshUserinfo() {
        this.userInfo.setVip(true);
        this.userInfo.setVipExpire(DateUtil.local2UTC(DateUtil.getDateTimeNow() + 2678400000L, DateUtil.YYYY_MM_DD_T_HH_MM_SS));
        this.manager.insertUserInfo(this.userInfo);
    }

    @Override // com.aivox.common.pay.PayHelper.SubListener
    public void onSuccessGoogle(String str, String str2) {
        LogUtil.m336e("onSuccessGoogle : " + str2);
        this.mOrderNo = str;
        this.mApi.verifyGooglePay(str, str2).subscribe(new Consumer() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2574lambda$onSuccessGoogle$12$comaivoxsetactivityVipJoinActivity(obj);
            }
        }, new Consumer() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2575lambda$onSuccessGoogle$13$comaivoxsetactivityVipJoinActivity((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onSuccessGoogle$12$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2574lambda$onSuccessGoogle$12$comaivoxsetactivityVipJoinActivity(Object obj) throws Exception {
        refreshUserinfo();
        PayHelper.getInstance().onPayComplete();
        new UserService(this.context).getFunctionRights().doFinally(new Action() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2573lambda$onSuccessGoogle$10$comaivoxsetactivityVipJoinActivity();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj2) {
                DataHandle.getIns().setFunctionBean((FunctionRightsBean) obj2);
            }
        }, new Consumer() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj2) {
                ((Throwable) obj2).printStackTrace();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onSuccessGoogle$10$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2573lambda$onSuccessGoogle$10$comaivoxsetactivityVipJoinActivity() throws Exception {
        DialogUtils.hideLoadingDialog();
        DialogUtils.showDialogWithDefBtnAndSingleListener(this.context, "", Integer.valueOf(C0874R.string.subscribe_success_msg), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.set.activity.VipJoinActivity$$ExternalSyntheticLambda5
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m2576lambda$onSuccessGoogle$9$comaivoxsetactivityVipJoinActivity(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, false, true);
    }

    /* JADX INFO: renamed from: lambda$onSuccessGoogle$9$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2576lambda$onSuccessGoogle$9$comaivoxsetactivityVipJoinActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        finish();
    }

    /* JADX INFO: renamed from: lambda$onSuccessGoogle$13$com-aivox-set-activity-VipJoinActivity, reason: not valid java name */
    /* synthetic */ void m2575lambda$onSuccessGoogle$13$comaivoxsetactivityVipJoinActivity(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        th.printStackTrace();
        BaseAppUtils.printErrorMsg(th);
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.context);
        }
    }

    @Override // com.aivox.common.pay.PayHelper.SubListener
    public void onCancelGoogle(String str) {
        LogUtil.m336e("onCancelGoogle");
        this.mOrderNo = str;
        cancelPayment(Constant.PAY_TYPE_GOOGLE);
    }

    @Override // com.aivox.common.pay.PayHelper.SubListener
    public void onFailedGoogle(String str) {
        LogUtil.m336e("onFailedGoogle");
        this.mOrderNo = str;
        cancelPayment(Constant.PAY_TYPE_GOOGLE);
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(0, C0874R.anim.slide_out_to_bottom);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        PayHelper.getInstance().clear();
    }
}
