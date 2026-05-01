package com.aivox.app.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityWebBinding;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.common.Constant;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.BitmapUtil;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.WebViewHelper;
import com.aivox.common.base.BaseFragmentActivity;
import com.alibaba.fastjson.JSONObject;
import com.microsoft.azure.storage.table.TableConstants;

/* JADX INFO: loaded from: classes.dex */
public class WebActivity extends BaseFragmentActivity {
    private static Handler handler;
    private ActivityWebBinding mBinding;
    private final WebViewHelper.WebViewCallback mWebViewCallback = new WebViewHelper.WebViewCallback() { // from class: com.aivox.app.activity.WebActivity.1
        @Override // com.aivox.base.util.WebViewHelper.WebViewCallback
        public void onPageStarted(String str) {
            WebActivity.sendHandlerMessage(4, str);
        }

        @Override // com.aivox.base.util.WebViewHelper.WebViewCallback
        public void onPageFinished(String str) {
            WebActivity.sendHandlerMessage(1, str);
            WebActivity.this.postToken();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void shareImg(String str, String str2, String str3, int i) {
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        DialogUtils.showLoadingDialog(this.context);
        ActivityWebBinding activityWebBinding = (ActivityWebBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_web);
        this.mBinding = activityWebBinding;
        activityWebBinding.titleView.setOnBackListener(new View.OnClickListener() { // from class: com.aivox.app.activity.WebActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2254lambda$initView$0$comaivoxappactivityWebActivity(view2);
            }
        });
        initHandler();
        String stringExtra = getIntent().getStringExtra("urlOrPath");
        if (stringExtra != null && !stringExtra.startsWith("https")) {
            stringExtra = BaseGlobalConfig.getH5Host() + stringExtra;
        }
        String str = stringExtra;
        LogUtil.m335d(this.TAG, str);
        initWb();
        WebViewHelper.getIns().setWebViewCallback(this.mWebViewCallback);
        if (BaseStringUtil.isEmpty(str)) {
            WebViewHelper.getIns().showWebView(this.mBinding.webview, getIntent().getStringExtra("data"), this);
        } else {
            WebViewHelper.getIns().showWebView(this.mBinding.webview, str, 0, this, true);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-WebActivity, reason: not valid java name */
    /* synthetic */ void m2254lambda$initView$0$comaivoxappactivityWebActivity(View view2) {
        doBack();
    }

    private void initHandler() {
        handler = new Handler() { // from class: com.aivox.app.activity.WebActivity.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    WebActivity.this.mBinding.titleView.setTitle(message.obj.toString());
                    DialogUtils.hideLoadingDialog();
                }
                super.handleMessage(message);
            }
        };
    }

    public static void sendHandlerMessage(int i, Object obj) {
        Handler handler2 = handler;
        if (handler2 == null) {
            return;
        }
        handler.sendMessage(handler2.obtainMessage(i, obj));
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        doBack();
    }

    private void doBack() {
        if (this.mBinding.webview.canGoBack()) {
            this.mBinding.webview.goBack();
        } else {
            finish();
        }
    }

    private void initWb() {
        WebSettings settings = this.mBinding.webview.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setGeolocationEnabled(true);
        settings.setMixedContentMode(0);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        this.mBinding.webview.addJavascriptInterface(new JsCall(), "JsCall");
    }

    public class JsCall {
        public JsCall() {
        }

        @JavascriptInterface
        public void sharePic(String str) {
            LogUtil.m334d("sharePic" + str);
            BitmapUtil.saveImageToGallery(WebActivity.this, BitmapUtil.convertStringToIcon(str), System.currentTimeMillis() + ".png");
        }

        @JavascriptInterface
        public void shareInvitation(String str) {
            LogUtil.m334d("shareInvitation" + str);
            JSONObject object = JSONObject.parseObject(str);
            WebActivity.this.shareImg(object.getString(Constant.KEY_TITLE), object.getString("description"), object.getString("url"), object.getInteger(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE).intValue());
        }
    }

    public void postToken() {
        this.mBinding.webview.loadUrl("javascript:syncAuthor(\"token\",\"" + SPUtil.get(SPUtil.TOKEN, "") + "\")");
    }
}
