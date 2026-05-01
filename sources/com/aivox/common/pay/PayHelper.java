package com.aivox.common.pay;

import android.app.Activity;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.http.HttpException;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.PayOrderResultBean;
import com.aivox.common.pay.PayHelper;
import com.aivox.common.util.AppUtils;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PendingPurchasesParams;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryProductDetailsResult;
import com.android.billingclient.api.QueryPurchasesParams;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ThreadUtils;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PayHelper {
    public static final String GOOGLE_GOODS_3H = "litok_3hrs";
    public static final String GOOGLE_SUB_ANNUALLY = "litok_annually_auto";
    public static final String GOOGLE_SUB_MONTHLY = "litok_monthly_auto";
    public static final String GOOGLE_SUB_MONTHLY_FIRST_FREE = "litok_monthly_auto_first_month_free";
    private static final String TAG = "PAY_HELPER";
    private BillingClient billingClient;
    private boolean isSub;
    private Activity mActivity;
    private AudioService mApi;
    private final CompositeDisposable mDis = new CompositeDisposable();
    private String mGoogleProductId;
    private String mOrderNo;
    private PayListener mPayListener;
    private SubListener mSubListener;

    public interface GetPriceListener {
        void onPriceGet(String str);
    }

    public interface PayListener {
        void onCancelGoogle(String str);

        void onFailedGoogle(String str);

        void onSuccessGoogle(String str, String str2);
    }

    public interface SubListener {
        void onCancelGoogle(String str);

        void onFailedGoogle(String str);

        void onSuccessGoogle(String str, String str2);
    }

    static /* synthetic */ void lambda$checkBreakOrder$7(BillingResult billingResult, List list) {
    }

    static /* synthetic */ void lambda$getFirstFreePrice$0(BillingResult billingResult, List list) {
    }

    private static final class InstanceHolder {
        static final PayHelper mInstance = new PayHelper();

        private InstanceHolder() {
        }
    }

    public static PayHelper getInstance() {
        return InstanceHolder.mInstance;
    }

    public void doThirdPay(Activity activity, String str, String str2, PayListener payListener) {
        this.mActivity = activity;
        this.mGoogleProductId = str2;
        this.mPayListener = payListener;
        this.mApi = new AudioService(activity);
        m2451lambda$createOrder$2$comaivoxcommonpayPayHelper(str);
    }

    public void doThirdSub(Activity activity, String str, String str2, SubListener subListener) {
        this.mActivity = activity;
        this.mGoogleProductId = str2;
        this.mSubListener = subListener;
        this.mApi = new AudioService(activity);
        this.isSub = true;
        m2451lambda$createOrder$2$comaivoxcommonpayPayHelper(str);
    }

    public void getFirstFreePrice(Activity activity, GetPriceListener getPriceListener) {
        this.mActivity = activity;
        BillingClient billingClientBuild = BillingClient.newBuilder(activity).setListener(new PurchasesUpdatedListener() { // from class: com.aivox.common.pay.PayHelper$$ExternalSyntheticLambda8
            @Override // com.android.billingclient.api.PurchasesUpdatedListener
            public final void onPurchasesUpdated(BillingResult billingResult, List list) {
                PayHelper.lambda$getFirstFreePrice$0(billingResult, list);
            }
        }).enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()).enableAutoServiceReconnection().build();
        billingClientBuild.startConnection(new C09811(billingClientBuild, getPriceListener));
    }

    /* JADX INFO: renamed from: com.aivox.common.pay.PayHelper$1 */
    class C09811 implements BillingClientStateListener {
        final /* synthetic */ BillingClient val$client;
        final /* synthetic */ GetPriceListener val$listener;

        C09811(BillingClient billingClient, GetPriceListener getPriceListener) {
            this.val$client = billingClient;
            this.val$listener = getPriceListener;
        }

        @Override // com.android.billingclient.api.BillingClientStateListener
        public void onBillingSetupFinished(BillingResult billingResult) {
            if (billingResult.getResponseCode() == 0) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(PayHelper.GOOGLE_SUB_MONTHLY).setProductType("subs").build());
                QueryProductDetailsParams queryProductDetailsParamsBuild = QueryProductDetailsParams.newBuilder().setProductList(arrayList).build();
                final BillingClient billingClient = this.val$client;
                final GetPriceListener getPriceListener = this.val$listener;
                billingClient.queryProductDetailsAsync(queryProductDetailsParamsBuild, new ProductDetailsResponseListener() { // from class: com.aivox.common.pay.PayHelper$1$$ExternalSyntheticLambda0
                    @Override // com.android.billingclient.api.ProductDetailsResponseListener
                    public final void onProductDetailsResponse(BillingResult billingResult2, QueryProductDetailsResult queryProductDetailsResult) {
                        PayHelper.C09811.lambda$onBillingSetupFinished$1(getPriceListener, billingClient, billingResult2, queryProductDetailsResult);
                    }
                });
                return;
            }
            final GetPriceListener getPriceListener2 = this.val$listener;
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.common.pay.PayHelper$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    getPriceListener2.onPriceGet("");
                }
            });
            this.val$client.endConnection();
        }

        static /* synthetic */ void lambda$onBillingSetupFinished$1(final GetPriceListener getPriceListener, BillingClient billingClient, BillingResult billingResult, QueryProductDetailsResult queryProductDetailsResult) {
            ProductDetails.SubscriptionOfferDetails subscriptionOfferDetails;
            List<ProductDetails> productDetailsList = queryProductDetailsResult.getProductDetailsList();
            if (billingResult.getResponseCode() == 0 && CollectionUtils.isNotEmpty(productDetailsList) && (subscriptionOfferDetails = productDetailsList.get(0).getSubscriptionOfferDetails().get(0)) != null) {
                final String formattedPrice = subscriptionOfferDetails.getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice();
                ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.common.pay.PayHelper$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        getPriceListener.onPriceGet(formattedPrice);
                    }
                });
                billingClient.endConnection();
            }
        }

        @Override // com.android.billingclient.api.BillingClientStateListener
        public void onBillingServiceDisconnected() {
            LogUtil.m336e("onBillingServiceDisconnected");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: createOrder, reason: merged with bridge method [inline-methods] */
    public void m2451lambda$createOrder$2$comaivoxcommonpayPayHelper(final String str) {
        DialogUtils.showLoadingDialog(this.mActivity, "", false);
        this.mDis.add(this.mApi.createOrder(str).subscribe(new Consumer() { // from class: com.aivox.common.pay.PayHelper$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2450lambda$createOrder$1$comaivoxcommonpayPayHelper((String) obj);
            }
        }, new Consumer() { // from class: com.aivox.common.pay.PayHelper$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2452lambda$createOrder$3$comaivoxcommonpayPayHelper(str, (Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$createOrder$1$com-aivox-common-pay-PayHelper, reason: not valid java name */
    /* synthetic */ void m2450lambda$createOrder$1$comaivoxcommonpayPayHelper(String str) throws Exception {
        LogUtil.m336e(str);
        this.mOrderNo = str;
        SPUtil.put(SPUtil.PAY_ORDER_NUMBER, str);
        SPUtil.put(SPUtil.PAY_PRODUCT_TYPE, this.isSub ? "subs" : "inapp");
        SPUtil.put(SPUtil.PAY_PRODUCT_ID, this.mGoogleProductId);
        payOrder();
    }

    /* JADX INFO: renamed from: lambda$createOrder$3$com-aivox-common-pay-PayHelper, reason: not valid java name */
    /* synthetic */ void m2452lambda$createOrder$3$comaivoxcommonpayPayHelper(final String str, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.common.pay.PayHelper$$ExternalSyntheticLambda2
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2451lambda$createOrder$2$comaivoxcommonpayPayHelper(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void payOrder() {
        this.mDis.add(this.mApi.payOrder(this.mOrderNo, Constant.PAY_TYPE_GOOGLE).subscribe(new Consumer() { // from class: com.aivox.common.pay.PayHelper$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2454lambda$payOrder$4$comaivoxcommonpayPayHelper((PayOrderResultBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.common.pay.PayHelper$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2455lambda$payOrder$5$comaivoxcommonpayPayHelper((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$payOrder$4$com-aivox-common-pay-PayHelper, reason: not valid java name */
    /* synthetic */ void m2454lambda$payOrder$4$comaivoxcommonpayPayHelper(PayOrderResultBean payOrderResultBean) throws Exception {
        LogUtil.m336e(payOrderResultBean.getOrderId());
        doGooglePay();
    }

    /* JADX INFO: renamed from: lambda$payOrder$5$com-aivox-common-pay-PayHelper, reason: not valid java name */
    /* synthetic */ void m2455lambda$payOrder$5$comaivoxcommonpayPayHelper(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.common.pay.PayHelper$$ExternalSyntheticLambda7
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.payOrder();
                }
            });
        }
    }

    private void doGooglePay() {
        LogUtil.m336e(this.mGoogleProductId);
        BillingClient billingClientBuild = BillingClient.newBuilder(this.mActivity).setListener(new PurchasesUpdatedListener() { // from class: com.aivox.common.pay.PayHelper$$ExternalSyntheticLambda6
            @Override // com.android.billingclient.api.PurchasesUpdatedListener
            public final void onPurchasesUpdated(BillingResult billingResult, List list) {
                this.f$0.m2453lambda$doGooglePay$6$comaivoxcommonpayPayHelper(billingResult, list);
            }
        }).enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()).enableAutoServiceReconnection().build();
        this.billingClient = billingClientBuild;
        billingClientBuild.startConnection(new C09822());
    }

    /* JADX INFO: renamed from: lambda$doGooglePay$6$com-aivox-common-pay-PayHelper, reason: not valid java name */
    /* synthetic */ void m2453lambda$doGooglePay$6$comaivoxcommonpayPayHelper(BillingResult billingResult, List list) {
        if (billingResult.getResponseCode() == 0 && list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Purchase purchase = (Purchase) it.next();
                if (this.isSub) {
                    this.mSubListener.onSuccessGoogle(this.mOrderNo, purchase.getPurchaseToken());
                } else {
                    this.mPayListener.onSuccessGoogle(this.mOrderNo, purchase.getPurchaseToken());
                }
            }
        } else if (billingResult.getResponseCode() == 1) {
            if (this.isSub) {
                this.mSubListener.onCancelGoogle(this.mOrderNo);
            } else {
                this.mPayListener.onCancelGoogle(this.mOrderNo);
            }
            onPayComplete();
        } else {
            if (this.isSub) {
                this.mSubListener.onCancelGoogle(this.mOrderNo);
            } else {
                this.mPayListener.onCancelGoogle(this.mOrderNo);
            }
            onPayComplete();
        }
        this.billingClient.endConnection();
    }

    /* JADX INFO: renamed from: com.aivox.common.pay.PayHelper$2 */
    class C09822 implements BillingClientStateListener {
        C09822() {
        }

        @Override // com.android.billingclient.api.BillingClientStateListener
        public void onBillingSetupFinished(BillingResult billingResult) {
            LogUtil.m336e("onBillingSetupFinished");
            if (billingResult.getResponseCode() == 0) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(PayHelper.this.mGoogleProductId).setProductType(PayHelper.this.isSub ? "subs" : "inapp").build());
                PayHelper.this.billingClient.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder().setProductList(arrayList).build(), new ProductDetailsResponseListener() { // from class: com.aivox.common.pay.PayHelper$2$$ExternalSyntheticLambda0
                    @Override // com.android.billingclient.api.ProductDetailsResponseListener
                    public final void onProductDetailsResponse(BillingResult billingResult2, QueryProductDetailsResult queryProductDetailsResult) {
                        this.f$0.m2457lambda$onBillingSetupFinished$2$comaivoxcommonpayPayHelper$2(billingResult2, queryProductDetailsResult);
                    }
                });
            }
        }

        /* JADX INFO: renamed from: lambda$onBillingSetupFinished$2$com-aivox-common-pay-PayHelper$2, reason: not valid java name */
        /* synthetic */ void m2457lambda$onBillingSetupFinished$2$comaivoxcommonpayPayHelper$2(BillingResult billingResult, QueryProductDetailsResult queryProductDetailsResult) {
            LogUtil.m336e(billingResult.toString());
            final List<ProductDetails> productDetailsList = queryProductDetailsResult.getProductDetailsList();
            if (billingResult.getResponseCode() != 0) {
                if (billingResult.getResponseCode() == -2) {
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.common.pay.PayHelper$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ToastUtil.showLong(Integer.valueOf(C0874R.string.update_google_play));
                        }
                    });
                }
                if (PayHelper.this.isSub) {
                    PayHelper.this.mSubListener.onFailedGoogle(PayHelper.this.mOrderNo);
                } else {
                    PayHelper.this.mPayListener.onFailedGoogle(PayHelper.this.mOrderNo);
                }
                PayHelper.this.billingClient.endConnection();
                return;
            }
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.common.pay.PayHelper$2$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2456lambda$onBillingSetupFinished$1$comaivoxcommonpayPayHelper$2(productDetailsList);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onBillingSetupFinished$1$com-aivox-common-pay-PayHelper$2, reason: not valid java name */
        /* synthetic */ void m2456lambda$onBillingSetupFinished$1$comaivoxcommonpayPayHelper$2(List list) {
            if (CollectionUtils.isNotEmpty(list)) {
                ProductDetails productDetails = (ProductDetails) list.get(0);
                ArrayList arrayList = new ArrayList();
                if (PayHelper.this.isSub) {
                    String offerToken = productDetails.getSubscriptionOfferDetails().get(0).getOfferToken();
                    LogUtil.m336e("selectedOfferToken : " + offerToken);
                    arrayList.add(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).setOfferToken(offerToken).build());
                } else {
                    arrayList.add(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).build());
                }
                PayHelper.this.billingClient.launchBillingFlow(PayHelper.this.mActivity, BillingFlowParams.newBuilder().setProductDetailsParamsList(arrayList).setObfuscatedAccountId(PayHelper.this.mOrderNo).build());
            }
        }

        @Override // com.android.billingclient.api.BillingClientStateListener
        public void onBillingServiceDisconnected() {
            LogUtil.m336e("onBillingServiceDisconnected");
        }
    }

    public void onPayComplete() {
        SPUtil.put(SPUtil.PAY_ORDER_NUMBER, "");
    }

    public void checkBreakOrder(Activity activity) {
        String str = (String) SPUtil.get(SPUtil.PAY_ORDER_NUMBER, "");
        if (BaseStringUtil.isEmpty(str)) {
            return;
        }
        String str2 = (String) SPUtil.get(SPUtil.PAY_PRODUCT_TYPE, "");
        String str3 = (String) SPUtil.get(SPUtil.PAY_PRODUCT_ID, "");
        this.mOrderNo = str;
        this.mActivity = activity;
        this.mApi = new AudioService(this.mActivity);
        BillingClient billingClientBuild = BillingClient.newBuilder(this.mActivity).setListener(new PurchasesUpdatedListener() { // from class: com.aivox.common.pay.PayHelper$$ExternalSyntheticLambda3
            @Override // com.android.billingclient.api.PurchasesUpdatedListener
            public final void onPurchasesUpdated(BillingResult billingResult, List list) {
                PayHelper.lambda$checkBreakOrder$7(billingResult, list);
            }
        }).enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()).enableAutoServiceReconnection().build();
        billingClientBuild.startConnection(new C09833(str2, billingClientBuild, str3));
    }

    /* JADX INFO: renamed from: com.aivox.common.pay.PayHelper$3 */
    class C09833 implements BillingClientStateListener {
        final /* synthetic */ BillingClient val$client;
        final /* synthetic */ String val$productId;
        final /* synthetic */ String val$productType;

        @Override // com.android.billingclient.api.BillingClientStateListener
        public void onBillingServiceDisconnected() {
        }

        C09833(String str, BillingClient billingClient, String str2) {
            this.val$productType = str;
            this.val$client = billingClient;
            this.val$productId = str2;
        }

        @Override // com.android.billingclient.api.BillingClientStateListener
        public void onBillingSetupFinished(BillingResult billingResult) {
            if (billingResult.getResponseCode() != 0) {
                return;
            }
            QueryPurchasesParams queryPurchasesParamsBuild = QueryPurchasesParams.newBuilder().setProductType(this.val$productType).build();
            final BillingClient billingClient = this.val$client;
            final String str = this.val$productId;
            billingClient.queryPurchasesAsync(queryPurchasesParamsBuild, new PurchasesResponseListener() { // from class: com.aivox.common.pay.PayHelper$3$$ExternalSyntheticLambda0
                @Override // com.android.billingclient.api.PurchasesResponseListener
                public final void onQueryPurchasesResponse(BillingResult billingResult2, List list) {
                    this.f$0.m2459lambda$onBillingSetupFinished$2$comaivoxcommonpayPayHelper$3(str, billingClient, billingResult2, list);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onBillingSetupFinished$2$com-aivox-common-pay-PayHelper$3, reason: not valid java name */
        /* synthetic */ void m2459lambda$onBillingSetupFinished$2$comaivoxcommonpayPayHelper$3(String str, BillingClient billingClient, BillingResult billingResult, List list) {
            try {
                if (billingResult.getResponseCode() == 0 && CollectionUtils.isNotEmpty(list)) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Purchase purchase = (Purchase) it.next();
                            if (purchase.getProducts().contains(str) && purchase.getPurchaseState() == 1 && !purchase.isAcknowledged()) {
                                PayHelper.this.mApi.verifyGooglePay(PayHelper.this.mOrderNo, purchase.getPurchaseToken()).subscribe(new Consumer() { // from class: com.aivox.common.pay.PayHelper$3$$ExternalSyntheticLambda1
                                    @Override // io.reactivex.functions.Consumer
                                    public final void accept(Object obj) throws Exception {
                                        this.f$0.m2458lambda$onBillingSetupFinished$0$comaivoxcommonpayPayHelper$3(obj);
                                    }
                                }, new Consumer() { // from class: com.aivox.common.pay.PayHelper$3$$ExternalSyntheticLambda2
                                    @Override // io.reactivex.functions.Consumer
                                    public final void accept(Object obj) throws Exception {
                                        PayHelper.C09833.lambda$onBillingSetupFinished$1((Throwable) obj);
                                    }
                                });
                                break;
                            }
                        } else {
                            PayHelper.this.onPayComplete();
                            break;
                        }
                    }
                } else {
                    PayHelper.this.onPayComplete();
                }
            } finally {
                billingClient.endConnection();
            }
        }

        /* JADX INFO: renamed from: lambda$onBillingSetupFinished$0$com-aivox-common-pay-PayHelper$3, reason: not valid java name */
        /* synthetic */ void m2458lambda$onBillingSetupFinished$0$comaivoxcommonpayPayHelper$3(Object obj) throws Exception {
            PayHelper.this.onPayComplete();
        }

        static /* synthetic */ void lambda$onBillingSetupFinished$1(Throwable th) throws Exception {
            th.printStackTrace();
            LogUtil.m336e("掉单恢复服务器验证失败: " + th.getMessage());
        }
    }

    public void clear() {
        this.mDis.clear();
        BillingClient billingClient = this.billingClient;
        if (billingClient == null || !billingClient.isReady()) {
            return;
        }
        this.billingClient.endConnection();
    }
}
