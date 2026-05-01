package com.aivox.base.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* JADX INFO: loaded from: classes.dex */
public class WebViewHelper {
    private static WebViewHelper ins;
    private static int loadProgress;
    public static WebViewCallback webViewCallback;

    public interface WebViewCallback {
        void onPageFinished(String str);

        void onPageStarted(String str);
    }

    public static WebViewHelper getIns() {
        if (ins == null) {
            ins = new WebViewHelper();
        }
        return ins;
    }

    public static void clear(WebView webView) {
        webView.loadUrl("about:blank");
    }

    public void setWebViewCallback(WebViewCallback webViewCallback2) {
        webViewCallback = webViewCallback2;
    }

    public void showWebView(WebView webView, String str, Activity activity) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(2);
        settings.setDomStorageEnabled(true);
        try {
            webView.addJavascriptInterface(new SLJavascriptInterface(activity), "webtest");
            webView.setWebViewClient(new SLWebViewClient(activity, 1));
            webView.setOnKeyListener(new SLKeyListener());
            webView.setWebChromeClient(new SLWebChromeClient(activity, 0));
            webView.loadData(str, "text/html;charset=UTF-8", null);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    private static String getHtmlData(String str) {
        return "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto;}</style></head><body>" + str + "</body></html>";
    }

    public void showWebView(WebView webView, String str, int i, Activity activity, boolean z) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(2);
        settings.setDomStorageEnabled(true);
        if (z) {
            webView.addJavascriptInterface(new SLJavascriptInterface(activity), "webtest");
        }
        webView.setWebViewClient(new SLWebViewClient(activity, i));
        webView.setOnKeyListener(new SLKeyListener());
        webView.setWebChromeClient(new SLWebChromeClient(activity, i));
        webView.loadUrl(str);
    }

    public static class SLWebChromeClient extends WebChromeClient {
        private Activity con;
        private int from;

        public SLWebChromeClient(Activity activity, int i) {
            this.con = activity;
            this.from = i;
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            LogUtil.m338i("onProgressChanged:" + i);
            int unused = WebViewHelper.loadProgress = i;
        }

        @Override // android.webkit.WebChromeClient
        public void onShowCustomView(View view2, WebChromeClient.CustomViewCallback customViewCallback) {
            LogUtil.m338i("==onShowCustomView==");
            super.onShowCustomView(view2, customViewCallback);
        }

        @Override // android.webkit.WebChromeClient
        public void onHideCustomView() {
            LogUtil.m338i("==onHideCustomView==");
            super.onHideCustomView();
        }

        @Override // android.webkit.WebChromeClient
        public View getVideoLoadingProgressView() {
            LogUtil.m338i("==getVideoLoadingProgressView==");
            return super.getVideoLoadingProgressView();
        }
    }

    public static class SLJavascriptInterface {
        private Context context;

        public SLJavascriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public String toString() {
            return "webtest";
        }

        @JavascriptInterface
        public void openImage(String str) {
            Log.i("songe", "被点击的图片地址为：" + str);
        }

        @JavascriptInterface
        public void openBrowser(String str) {
            LogUtil.m338i("点击打开了系统浏览器" + str);
        }
    }

    public static class SLKeyListener implements View.OnKeyListener {
        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view2, int i, KeyEvent keyEvent) {
            if (i == 4 && keyEvent.getRepeatCount() == 0) {
                LogUtil.m338i("=SLKeyListener=1");
                return false;
            }
            LogUtil.m338i("=SLKeyListener=2");
            return false;
        }
    }

    public static class SLWebViewClient extends WebViewClient {
        private Activity con;
        private int from;
        private boolean isFirstClick = true;
        private int mDensity;

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i, String str, String str2) {
        }

        public SLWebViewClient(Activity activity, int i) {
            this.con = activity;
            this.from = i;
            this.mDensity = DensityUtil.px2dp(activity, ((Integer) SPUtil.get(SPUtil.SCREEN_W, 720)).intValue());
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (this.isFirstClick) {
                this.isFirstClick = false;
            }
            return false;
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            webView.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(webView, str, bitmap);
            LogUtil.m338i("onPageStarted : " + str);
            if (WebViewHelper.webViewCallback != null) {
                WebViewHelper.webViewCallback.onPageStarted(str);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            webView.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(webView, str);
            if (WebViewHelper.loadProgress < 100) {
                return;
            }
            this.isFirstClick = true;
            LogUtil.m338i("onPageFinished : " + str + " title : " + webView.getTitle());
            if (WebViewHelper.webViewCallback != null) {
                WebViewHelper.webViewCallback.onPageFinished(this.from == 0 ? webView.getTitle() : "详情");
            }
            webView.loadUrl("javascript:(" + ("function ReSizeImages(){\tvar maxWidth = " + (this.mDensity - 20) + ";\tvar imgs = document.getElementsByTagName(\"img\");\tfor (var i = 0; i < imgs.length; i++) {\t\tvar w = imgs[i].getAttribute(\"width\");\t\tvar h = imgs[i].getAttribute(\"height\");\t\tif (w != null && w != \"\" && h != null && h != \"\") {\t\t\tif (w > maxWidth) {\t\t\t\timgs[i].setAttribute(\"width\", maxWidth);\t\t\t\timgs[i].setAttribute(\"height\", maxWidth * h / w);\t\t\t}\t\t} else {\t\t\tvar img;\t\t\tfor (var i = 0; i < document.images.length; i++) {\t\t\t\timg = document.images[i];\t\t\t\tif (img.width > maxWidth) {\t\t\t\t\timg.width = maxWidth;\t\t\t\t}\t\t\t}\t\t}\t}}") + ")()");
            webView.loadUrl("javascript:(function ReSizeTxt(){var txts=document.getElementsByTagName(\"font\");for(var i=0;i<txts.length;i++){txts[i].size=3}})()");
            webView.loadUrl("javascript:(function(){  var timeOutEvent=0;  var objs = document.getElementsByTagName(\"img\");   for(var i=0;i<objs.length;i++){\t\tobjs[i].onmousedown=function (){var url=this.src;timeOutEvent=setTimeout(function longPress(){window.webtest.openImage(url); },500);return false;}\t }})()");
        }
    }
}
