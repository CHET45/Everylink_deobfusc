package com.aivox.common.pay;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SpanUtils;
import com.aivox.common.base.BaseBottomSheetFragment;
import com.aivox.common.databinding.FragmentTimePurchaseBinding;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.PricePackageList;
import com.aivox.common.pay.PayHelper;
import com.aivox.common.util.AppUtils;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.ColorUtils;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class TimePurchaseFragment extends BaseBottomSheetFragment {
    private static final int TYPE_COMMON = 5;
    private static String mCancelReasons;
    private AudioService mApi;
    private FragmentTimePurchaseBinding mBinding;
    private String mPickPriceUUid;
    private String mValidUtil;
    private final List<PricePackageList> mList = new ArrayList();
    private final CompositeDisposable mDis = new CompositeDisposable();

    private TimePurchaseFragment() {
    }

    public static TimePurchaseFragment newInstance() {
        mCancelReasons = "";
        return new TimePurchaseFragment();
    }

    public static TimePurchaseFragment newInstance(String str) {
        mCancelReasons = str;
        return new TimePurchaseFragment();
    }

    @Override // com.aivox.common.base.BaseBottomSheetFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        this.mApi = new AudioService(this.mContext);
        FragmentTimePurchaseBinding fragmentTimePurchaseBindingInflate = FragmentTimePurchaseBinding.inflate(layoutInflater, viewGroup, false);
        this.mBinding = fragmentTimePurchaseBindingInflate;
        fragmentTimePurchaseBindingInflate.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m349x7cd3f41a(view2);
            }
        });
        this.mBinding.llTimePack.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m350x621562db(view2);
            }
        });
        this.mBinding.tvTimePack.setText(getString(C0874R.string.package_hours_3, "--"));
        if (BaseStringUtil.isNotEmpty(mCancelReasons)) {
            this.mBinding.tvTitle.setText(C0874R.string.recommendations);
            this.mBinding.tvMsg.setText(C0874R.string.vip_cancel_notice);
            this.mBinding.llCancelSub.setVisibility(0);
            this.mBinding.btnPayNow.setVisibility(8);
            this.mBinding.btnDoCancel.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m351x4756d19c(view2);
                }
            });
            this.mBinding.btnDoNotCancel.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m352x2c98405d(view2);
                }
            });
        }
        this.mBinding.btnPayNow.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m353x11d9af1e(view2);
            }
        });
        this.mBinding.tvAgreement.setText(new SpanUtils().setFlag(17).append(getString(C0874R.string.agree_purchase)).append(getString(C0874R.string.policy_dialog_policy)).setClickSpan(new ClickableSpan() { // from class: com.aivox.common.pay.TimePurchaseFragment.1
            @Override // android.text.style.ClickableSpan
            public void onClick(View view2) {
                if (!AntiShake.check(this)) {
                    BaseAppUtils.startActivityForWeb(TimePurchaseFragment.this.getActivity(), TimePurchaseFragment.this.getString(C0874R.string.h5_path_privacy), ARouterUtils.getClass(MainAction.WEB));
                }
                ((TextView) view2).setHighlightColor(ColorUtils.getColor(R.color.transparent));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(ColorUtils.getColor(C0874R.color.txt_color_highlight));
                textPaint.setUnderlineText(false);
                textPaint.clearShadowLayer();
            }
        }).create());
        this.mBinding.tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        this.mBinding.tvAgreement.setHighlightColor(this.mContext.getColor(R.color.transparent));
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$0$com-aivox-common-pay-TimePurchaseFragment */
    /* synthetic */ void m349x7cd3f41a(View view2) {
        doDismiss();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$1$com-aivox-common-pay-TimePurchaseFragment */
    /* synthetic */ void m350x621562db(View view2) {
        checkPack(true);
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$2$com-aivox-common-pay-TimePurchaseFragment */
    /* synthetic */ void m351x4756d19c(View view2) {
        m2464lambda$doCancelSub$6$comaivoxcommonpayTimePurchaseFragment(true);
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$3$com-aivox-common-pay-TimePurchaseFragment */
    /* synthetic */ void m352x2c98405d(View view2) {
        doDismiss();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$4$com-aivox-common-pay-TimePurchaseFragment */
    /* synthetic */ void m353x11d9af1e(View view2) {
        doGooglePay();
    }

    private void doGooglePay() {
        DialogUtils.showLoadingDialog(this.mContext);
        PayHelper.getInstance().doThirdPay(this.mActivity, this.mPickPriceUUid, PayHelper.GOOGLE_GOODS_3H, new C09852());
    }

    /* JADX INFO: renamed from: com.aivox.common.pay.TimePurchaseFragment$2 */
    class C09852 implements PayHelper.PayListener {
        C09852() {
        }

        @Override // com.aivox.common.pay.PayHelper.PayListener
        public void onSuccessGoogle(String str, String str2) {
            LogUtil.m336e("onSuccessGoogle : " + str2);
            TimePurchaseFragment.this.mApi.verifyGooglePay(str, str2).subscribe(new Consumer() { // from class: com.aivox.common.pay.TimePurchaseFragment$2$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m355xf8816b9a(obj);
                }
            }, new Consumer() { // from class: com.aivox.common.pay.TimePurchaseFragment$2$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m356x93222e1b((Throwable) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSuccessGoogle$1$com-aivox-common-pay-TimePurchaseFragment$2 */
        /* synthetic */ void m355xf8816b9a(Object obj) throws Exception {
            DialogUtils.hideLoadingDialog();
            if (BaseStringUtil.isNotEmpty(TimePurchaseFragment.mCancelReasons)) {
                TimePurchaseFragment.this.m2464lambda$doCancelSub$6$comaivoxcommonpayTimePurchaseFragment(false);
            }
            PayHelper.getInstance().onPayComplete();
            StringBuilder sbAppend = new StringBuilder().append(TimePurchaseFragment.this.getString(C0874R.string.thank_you_for_buying)).append(TimePurchaseFragment.this.getString(C0874R.string.hours_plan_3)).append(TimePurchaseFragment.this.getString(C0874R.string.valid_until)).append(" " + TimePurchaseFragment.this.mValidUtil);
            if (BaseStringUtil.isNotEmpty(TimePurchaseFragment.mCancelReasons)) {
                sbAppend.append("\n").append(TimePurchaseFragment.this.getString(C0874R.string.cancel_dialog_notice));
            }
            DialogUtils.showDialogWithBtnIds(TimePurchaseFragment.this.mContext, Integer.valueOf(C0874R.string.reminder), sbAppend.toString(), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.pay.TimePurchaseFragment$2$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m354x5de0a919(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, false, false, C0874R.string.sure, C0874R.string.confirm);
        }

        /* JADX INFO: renamed from: lambda$onSuccessGoogle$0$com-aivox-common-pay-TimePurchaseFragment$2 */
        /* synthetic */ void m354x5de0a919(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            TimePurchaseFragment.this.doDismiss();
            if (BaseStringUtil.isNotEmpty(TimePurchaseFragment.mCancelReasons)) {
                CancelSubNoticeFragment.newInstance().show(TimePurchaseFragment.this.mActivity.getSupportFragmentManager(), "CancelSubNoticeFragment");
            }
        }

        /* JADX INFO: renamed from: lambda$onSuccessGoogle$2$com-aivox-common-pay-TimePurchaseFragment$2 */
        /* synthetic */ void m356x93222e1b(Throwable th) throws Exception {
            DialogUtils.hideLoadingDialog();
            th.printStackTrace();
            BaseAppUtils.printErrorMsg(th);
            if (th instanceof HttpException) {
                AppUtils.checkHttpCode(TimePurchaseFragment.this.mContext);
            }
        }

        @Override // com.aivox.common.pay.PayHelper.PayListener
        public void onCancelGoogle(String str) {
            LogUtil.m336e("onCancelGoogle");
            TimePurchaseFragment.this.cancelPayment(Constant.PAY_TYPE_GOOGLE, "", str);
        }

        @Override // com.aivox.common.pay.PayHelper.PayListener
        public void onFailedGoogle(String str) {
            LogUtil.m336e("onFailedGoogle");
            TimePurchaseFragment.this.cancelPayment(Constant.PAY_TYPE_GOOGLE, "", str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: doCancelSub, reason: merged with bridge method [inline-methods] */
    public void m2464lambda$doCancelSub$6$comaivoxcommonpayTimePurchaseFragment(final boolean z) {
        DialogUtils.showLoadingDialog(this.mContext, "", false);
        new AudioService(this.mContext).cancelSubscription(mCancelReasons).subscribe(new Consumer() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2463lambda$doCancelSub$5$comaivoxcommonpayTimePurchaseFragment(z, obj);
            }
        }, new Consumer() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2465lambda$doCancelSub$7$comaivoxcommonpayTimePurchaseFragment(z, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$doCancelSub$5$com-aivox-common-pay-TimePurchaseFragment, reason: not valid java name */
    /* synthetic */ void m2463lambda$doCancelSub$5$comaivoxcommonpayTimePurchaseFragment(boolean z, Object obj) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (z) {
            doDismiss();
            CancelSubNoticeFragment.newInstance().show(this.mActivity.getSupportFragmentManager(), "CancelSubNoticeFragment");
        }
    }

    /* JADX INFO: renamed from: lambda$doCancelSub$7$com-aivox-common-pay-TimePurchaseFragment, reason: not valid java name */
    /* synthetic */ void m2465lambda$doCancelSub$7$comaivoxcommonpayTimePurchaseFragment(final boolean z, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mContext);
        } else {
            AppUtils.showError(this.mContext, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda9
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2464lambda$doCancelSub$6$comaivoxcommonpayTimePurchaseFragment(z);
                }
            });
        }
    }

    @Override // com.aivox.common.base.BaseBottomSheetFragment, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void checkPack(boolean z) {
        this.mPickPriceUUid = this.mList.get(0).getPriceUuid();
        if (BaseStringUtil.isNotEmpty(mCancelReasons) && z) {
            doGooglePay();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelPayment(int i, final String str, String str2) {
        DialogUtils.showLoadingDialog(this.mContext);
        this.mDis.add(this.mApi.closePayment(str2, i).subscribe(new Consumer() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2462lambda$cancelPayment$8$comaivoxcommonpayTimePurchaseFragment(str, obj);
            }
        }, new Consumer() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                TimePurchaseFragment.lambda$cancelPayment$9((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$cancelPayment$8$com-aivox-common-pay-TimePurchaseFragment, reason: not valid java name */
    /* synthetic */ void m2462lambda$cancelPayment$8$comaivoxcommonpayTimePurchaseFragment(String str, Object obj) throws Exception {
        DialogUtils.hideLoadingDialog();
        Context context = this.mContext;
        Integer numValueOf = Integer.valueOf(C0874R.string.reminder);
        if (BaseStringUtil.isEmpty(str)) {
            str = getString(C0874R.string.purchase_pay_fail);
        }
        DialogUtils.showDialogWithDefBtnAndSingleListener(context, numValueOf, str, null, false, true);
    }

    static /* synthetic */ void lambda$cancelPayment$9(Throwable th) throws Exception {
        th.printStackTrace();
        DialogUtils.hideLoadingDialog();
        BaseAppUtils.printErrorMsg(th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData() {
        DialogUtils.showLoadingDialog(this.mContext, "", false);
        this.mDis.add(this.mApi.getPackageList(5).doFinally(new Action() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                DialogUtils.hideLoadingDialog();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2466lambda$loadData$10$comaivoxcommonpayTimePurchaseFragment((List) obj);
            }
        }, new Consumer() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2467lambda$loadData$11$comaivoxcommonpayTimePurchaseFragment((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$loadData$10$com-aivox-common-pay-TimePurchaseFragment, reason: not valid java name */
    /* synthetic */ void m2466lambda$loadData$10$comaivoxcommonpayTimePurchaseFragment(List list) throws Exception {
        this.mList.addAll(list);
        for (PricePackageList pricePackageList : this.mList) {
            if (pricePackageList.getProductName().contains(ExifInterface.GPS_MEASUREMENT_3D)) {
                this.mBinding.tvTimePack.setText(getString(C0874R.string.package_hours_3, pricePackageList.getPrice().toString()));
                if (!pricePackageList.getValidUtil().isEmpty()) {
                    this.mValidUtil = DateUtil.utc2Local(pricePackageList.getValidUtil(), DateUtil.DATE_IN_TIME_PACK);
                    this.mBinding.tvValidUntil.setVisibility(0);
                    this.mBinding.tvValidUntil.setText("Valid until: " + this.mValidUtil);
                }
            }
        }
        checkPack(false);
    }

    /* JADX INFO: renamed from: lambda$loadData$11$com-aivox-common-pay-TimePurchaseFragment, reason: not valid java name */
    /* synthetic */ void m2467lambda$loadData$11$comaivoxcommonpayTimePurchaseFragment(Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mContext);
        } else {
            AppUtils.showError(this.mContext, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.common.pay.TimePurchaseFragment$$ExternalSyntheticLambda10
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.loadData();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDismiss() {
        EventBus.getDefault().post(new EventBean(15));
        dismiss();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        PayHelper.getInstance().clear();
        this.mDis.clear();
    }
}
