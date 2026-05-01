package com.aivox.common_ui.pulltorefresh;

import android.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import androidx.fragment.app.ListFragment;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;

/* JADX INFO: loaded from: classes.dex */
abstract class PullToRefreshBaseListFragment<T extends PullToRefreshBase<? extends AbsListView>> extends ListFragment {
    private T mPullToRefreshListView;

    protected abstract T onCreatePullToRefreshListView(LayoutInflater layoutInflater, Bundle bundle);

    PullToRefreshBaseListFragment() {
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewOnCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        ListView listView = (ListView) viewOnCreateView.findViewById(R.id.list);
        ViewGroup viewGroup2 = (ViewGroup) listView.getParent();
        int iIndexOfChild = viewGroup2.indexOfChild(listView);
        viewGroup2.removeViewAt(iIndexOfChild);
        T t = (T) onCreatePullToRefreshListView(layoutInflater, bundle);
        this.mPullToRefreshListView = t;
        viewGroup2.addView(t, iIndexOfChild, listView.getLayoutParams());
        return viewOnCreateView;
    }

    public final T getPullToRefreshListView() {
        return this.mPullToRefreshListView;
    }
}
