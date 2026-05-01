package com.lxj.xpopup.impl;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;

/* JADX INFO: loaded from: classes4.dex */
public class LoadingPopupView extends CenterPopupView {
    private CharSequence title;
    private TextView tv_title;

    public LoadingPopupView(Context context, int i) {
        super(context);
        this.bindLayoutId = i;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return this.bindLayoutId != 0 ? this.bindLayoutId : C2213R.layout._xpopup_center_impl_loading;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        this.tv_title = (TextView) findViewById(C2213R.id.tv_title);
        getPopupImplView().setElevation(10.0f);
        if (this.bindLayoutId == 0) {
            getPopupImplView().setBackground(XPopupUtils.createDrawable(Color.parseColor("#CF000000"), this.popupInfo.borderRadius));
        }
        setup();
    }

    protected void setup() {
        if (this.tv_title == null) {
            return;
        }
        post(new Runnable() { // from class: com.lxj.xpopup.impl.LoadingPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                TransitionManager.beginDelayedTransition(LoadingPopupView.this.centerPopupContainer, new TransitionSet().setDuration(LoadingPopupView.this.getAnimationDuration()).addTransition(new Fade()).addTransition(new ChangeBounds()));
                if (LoadingPopupView.this.title == null || LoadingPopupView.this.title.length() == 0) {
                    LoadingPopupView.this.tv_title.setVisibility(8);
                } else {
                    LoadingPopupView.this.tv_title.setVisibility(0);
                    LoadingPopupView.this.tv_title.setText(LoadingPopupView.this.title);
                }
            }
        });
    }

    public LoadingPopupView setTitle(CharSequence charSequence) {
        this.title = charSequence;
        setup();
        return this;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onDismiss() {
        super.onDismiss();
        TextView textView = this.tv_title;
        if (textView == null) {
            return;
        }
        textView.setText("");
        this.tv_title.setVisibility(8);
    }
}
