package com.aivox.common_ui.pulltorefresh;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import com.aivox.common_ui.pulltorefresh.internal.LoadingLayout;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class LoadingLayoutProxy implements ILoadingLayout {
    private final HashSet<LoadingLayout> mLoadingLayouts = new HashSet<>();

    LoadingLayoutProxy() {
    }

    public void addLayout(LoadingLayout loadingLayout) {
        if (loadingLayout != null) {
            this.mLoadingLayouts.add(loadingLayout);
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setLastUpdatedLabel(CharSequence charSequence) {
        Iterator<LoadingLayout> it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            it.next().setLastUpdatedLabel(charSequence);
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setLoadingDrawable(Drawable drawable) {
        Iterator<LoadingLayout> it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            it.next().setLoadingDrawable(drawable);
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setRefreshingLabel(CharSequence charSequence) {
        Iterator<LoadingLayout> it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            it.next().setRefreshingLabel(charSequence);
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setPullLabel(CharSequence charSequence) {
        Iterator<LoadingLayout> it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            it.next().setPullLabel(charSequence);
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setReleaseLabel(CharSequence charSequence) {
        Iterator<LoadingLayout> it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            it.next().setReleaseLabel(charSequence);
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setTextTypeface(Typeface typeface) {
        Iterator<LoadingLayout> it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            it.next().setTextTypeface(typeface);
        }
    }
}
