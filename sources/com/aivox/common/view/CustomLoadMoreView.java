package com.aivox.common.view;

import com.aivox.common.C0958R;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

/* JADX INFO: loaded from: classes.dex */
public class CustomLoadMoreView extends LoadMoreView {
    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    public int getLayoutId() {
        return C0958R.layout.load_more;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    protected int getLoadingViewId() {
        return C0958R.id.tv_loading;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    protected int getLoadFailViewId() {
        return C0958R.id.tv_loading_failed;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    protected int getLoadEndViewId() {
        return C0958R.id.tv_loading_end;
    }
}
