package com.app.hubert.guide.core;

import android.app.Activity;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnPageChangedListener;
import com.app.hubert.guide.model.GuidePage;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Builder {
    Activity activity;
    boolean alwaysShow;
    View anchor;
    Fragment fragment;
    String label;
    OnGuideChangedListener onGuideChangedListener;
    OnPageChangedListener onPageChangedListener;
    androidx.fragment.app.Fragment v4Fragment;
    int showCounts = 1;
    List<GuidePage> guidePages = new ArrayList();

    public Builder(Activity activity) {
        this.activity = activity;
    }

    public Builder(Fragment fragment) {
        this.fragment = fragment;
        this.activity = fragment.getActivity();
    }

    public Builder(androidx.fragment.app.Fragment fragment) {
        this.v4Fragment = fragment;
        this.activity = fragment.getActivity();
    }

    public Builder anchor(View view2) {
        this.anchor = view2;
        return this;
    }

    public Builder setShowCounts(int i) {
        this.showCounts = i;
        return this;
    }

    public Builder alwaysShow(boolean z) {
        this.alwaysShow = z;
        return this;
    }

    public Builder addGuidePage(GuidePage guidePage) {
        this.guidePages.add(guidePage);
        return this;
    }

    public Builder setOnGuideChangedListener(OnGuideChangedListener onGuideChangedListener) {
        this.onGuideChangedListener = onGuideChangedListener;
        return this;
    }

    public Builder setOnPageChangedListener(OnPageChangedListener onPageChangedListener) {
        this.onPageChangedListener = onPageChangedListener;
        return this;
    }

    public Builder setLabel(String str) {
        this.label = str;
        return this;
    }

    public Controller build() {
        check();
        return new Controller(this);
    }

    public Controller show() {
        check();
        Controller controller = new Controller(this);
        controller.show();
        return controller;
    }

    private void check() {
        if (TextUtils.isEmpty(this.label)) {
            throw new IllegalArgumentException("the param 'label' is missing, please call setLabel()");
        }
        if (this.activity == null) {
            if (this.fragment != null || this.v4Fragment != null) {
                throw new IllegalStateException("activity is null, please make sure that fragment is showing when call NewbieGuide");
            }
        }
    }
}
