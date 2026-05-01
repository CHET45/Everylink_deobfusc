package com.aivox.common_ui.pulltorefresh;

import android.os.Bundle;
import android.view.LayoutInflater;

/* JADX INFO: loaded from: classes.dex */
public class PullToRefreshListFragment extends PullToRefreshBaseListFragment<PullToRefreshListView> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBaseListFragment
    public PullToRefreshListView onCreatePullToRefreshListView(LayoutInflater layoutInflater, Bundle bundle) {
        return new PullToRefreshListView(getActivity());
    }
}
