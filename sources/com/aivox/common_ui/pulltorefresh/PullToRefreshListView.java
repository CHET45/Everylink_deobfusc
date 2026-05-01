package com.aivox.common_ui.pulltorefresh;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;
import com.aivox.common_ui.pulltorefresh.internal.EmptyViewMethodAccessor;
import com.aivox.common_ui.pulltorefresh.internal.LoadingLayout;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {
    private View footerView;
    private LoadingLayout mFooterLoadingView;
    private LoadingLayout mHeaderLoadingView;
    private boolean mListViewExtrasEnabled;
    private FrameLayout mLvFooterLoadingFrame;
    private ProgressBar progressBar;
    private TextView textView;

    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PullToRefreshListView(Context context, PullToRefreshBase.Mode mode) {
        super(context, mode);
    }

    public PullToRefreshListView(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    public final PullToRefreshBase.Orientation getPullToRefreshScrollDirection() {
        return PullToRefreshBase.Orientation.VERTICAL;
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshAdapterViewBase, com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected void onRefreshing(boolean z) {
        LoadingLayout footerLayout;
        int count;
        int scrollY;
        LoadingLayout loadingLayout;
        LoadingLayout loadingLayout2;
        ListAdapter adapter = ((ListView) this.mRefreshableView).getAdapter();
        if (!this.mListViewExtrasEnabled || !getShowViewWhileRefreshing() || adapter == null || adapter.isEmpty()) {
            super.onRefreshing(z);
            return;
        }
        super.onRefreshing(false);
        int i = C10591.f281x5281aad0[getCurrentMode().ordinal()];
        if (i == 1 || i == 2) {
            footerLayout = getFooterLayout();
            LoadingLayout loadingLayout3 = this.mFooterLoadingView;
            LoadingLayout loadingLayout4 = this.mHeaderLoadingView;
            count = ((ListView) this.mRefreshableView).getCount() - 1;
            scrollY = getScrollY() - getFooterSize();
            loadingLayout = loadingLayout3;
            loadingLayout2 = loadingLayout4;
        } else {
            footerLayout = getHeaderLayout();
            loadingLayout = this.mHeaderLoadingView;
            loadingLayout2 = this.mFooterLoadingView;
            scrollY = getScrollY() + getHeaderSize();
            count = 0;
        }
        footerLayout.reset();
        footerLayout.hideAllViews();
        loadingLayout2.setVisibility(8);
        loadingLayout.setVisibility(0);
        loadingLayout.refreshing();
        if (z) {
            disableLoadingLayoutVisibilityChanges();
            setHeaderScroll(scrollY);
            ((ListView) this.mRefreshableView).setSelection(count);
            smoothScrollTo(0);
        }
    }

    /* JADX INFO: renamed from: com.aivox.common_ui.pulltorefresh.PullToRefreshListView$1 */
    static /* synthetic */ class C10591 {

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$Mode */
        static final /* synthetic */ int[] f281x5281aad0;

        static {
            int[] iArr = new int[PullToRefreshBase.Mode.values().length];
            f281x5281aad0 = iArr;
            try {
                iArr[PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f281x5281aad0[PullToRefreshBase.Mode.PULL_FROM_END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f281x5281aad0[PullToRefreshBase.Mode.PULL_FROM_START.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshAdapterViewBase, com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected void onReset() {
        LoadingLayout footerLayout;
        LoadingLayout loadingLayout;
        int i;
        if (!this.mListViewExtrasEnabled) {
            super.onReset();
            return;
        }
        int i2 = C10591.f281x5281aad0[getCurrentMode().ordinal()];
        int i3 = 1;
        if (i2 == 1 || i2 == 2) {
            footerLayout = getFooterLayout();
            loadingLayout = this.mFooterLoadingView;
            int count = ((ListView) this.mRefreshableView).getCount() - 1;
            int footerSize = getFooterSize();
            i3 = Math.abs(((ListView) this.mRefreshableView).getLastVisiblePosition() - count) <= 1 ? 1 : 0;
            i = count;
            i = footerSize;
        } else {
            footerLayout = getHeaderLayout();
            loadingLayout = this.mHeaderLoadingView;
            i = -getHeaderSize();
            if (Math.abs(((ListView) this.mRefreshableView).getFirstVisiblePosition()) > 1) {
                i3 = 0;
            }
        }
        if (loadingLayout.getVisibility() == 0) {
            footerLayout.showInvisibleViews();
            loadingLayout.setVisibility(8);
            if (i3 != 0 && getState() != PullToRefreshBase.State.MANUAL_REFRESHING) {
                ((ListView) this.mRefreshableView).setSelection(i);
                setHeaderScroll(i);
            }
        }
        super.onReset();
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected LoadingLayoutProxy createLoadingLayoutProxy(boolean z, boolean z2) {
        LoadingLayoutProxy loadingLayoutProxyCreateLoadingLayoutProxy = super.createLoadingLayoutProxy(z, z2);
        if (this.mListViewExtrasEnabled) {
            PullToRefreshBase.Mode mode = getMode();
            if (z && mode.showHeaderLoadingLayout()) {
                loadingLayoutProxyCreateLoadingLayoutProxy.addLayout(this.mHeaderLoadingView);
            }
            if (z2 && mode.showFooterLoadingLayout()) {
                loadingLayoutProxyCreateLoadingLayoutProxy.addLayout(this.mFooterLoadingView);
            }
        }
        return loadingLayoutProxyCreateLoadingLayoutProxy;
    }

    protected ListView createListView(Context context, AttributeSet attributeSet) {
        return new InternalListViewSDK9(context, attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    public ListView createRefreshableView(Context context, AttributeSet attributeSet) {
        ListView listViewCreateListView = createListView(context, attributeSet);
        listViewCreateListView.setId(R.id.list);
        return listViewCreateListView;
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshAdapterViewBase, com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected void handleStyledAttributes(TypedArray typedArray) {
        super.handleStyledAttributes(typedArray);
        boolean z = typedArray.getBoolean(C1034R.styleable.PullToRefresh_ptrListViewExtrasEnabled, true);
        this.mListViewExtrasEnabled = z;
        if (z) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2, 1);
            FrameLayout frameLayout = new FrameLayout(getContext());
            LoadingLayout loadingLayoutCreateLoadingLayout = createLoadingLayout(getContext(), PullToRefreshBase.Mode.PULL_FROM_START, typedArray);
            this.mHeaderLoadingView = loadingLayoutCreateLoadingLayout;
            loadingLayoutCreateLoadingLayout.setVisibility(8);
            frameLayout.addView(this.mHeaderLoadingView, layoutParams);
            ((ListView) this.mRefreshableView).addHeaderView(frameLayout, null, false);
            this.mLvFooterLoadingFrame = new FrameLayout(getContext());
            LoadingLayout loadingLayoutCreateLoadingLayout2 = createLoadingLayout(getContext(), PullToRefreshBase.Mode.PULL_FROM_END, typedArray);
            this.mFooterLoadingView = loadingLayoutCreateLoadingLayout2;
            loadingLayoutCreateLoadingLayout2.setVisibility(8);
            this.mLvFooterLoadingFrame.addView(this.mFooterLoadingView, layoutParams);
            if (typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrScrollingWhileRefreshingEnabled)) {
                return;
            }
            setScrollingWhileRefreshingEnabled(true);
        }
    }

    final class InternalListViewSDK9 extends InternalListView {
        public InternalListViewSDK9(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.view.View
        protected boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            boolean zOverScrollBy = super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
            OverscrollHelper.overScrollBy(PullToRefreshListView.this, i, i3, i2, i4, z);
            return zOverScrollBy;
        }
    }

    protected class InternalListView extends ListView implements EmptyViewMethodAccessor {
        private boolean mAddedLvFooter;

        public InternalListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mAddedLvFooter = false;
        }

        @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            try {
                super.dispatchDraw(canvas);
            } catch (IndexOutOfBoundsException e) {
                BaseAppUtils.printErrorMsg(e);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            try {
                return super.dispatchTouchEvent(motionEvent);
            } catch (IndexOutOfBoundsException e) {
                BaseAppUtils.printErrorMsg(e);
                return false;
            }
        }

        @Override // android.widget.AdapterView
        public void setAdapter(ListAdapter listAdapter) {
            if (PullToRefreshListView.this.mLvFooterLoadingFrame != null && !this.mAddedLvFooter) {
                addFooterView(PullToRefreshListView.this.mLvFooterLoadingFrame, null, false);
                this.mAddedLvFooter = true;
            }
            super.setAdapter(listAdapter);
        }

        @Override // android.widget.AdapterView, com.aivox.common_ui.pulltorefresh.internal.EmptyViewMethodAccessor
        public void setEmptyView(View view2) {
            PullToRefreshListView.this.setEmptyView(view2);
        }

        @Override // com.aivox.common_ui.pulltorefresh.internal.EmptyViewMethodAccessor
        public void setEmptyViewInternal(View view2) {
            super.setEmptyView(view2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void withLoadMoreView() {
        if (this.footerView == null) {
            View viewInflate = inflate(getContext(), C1034R.layout.layout_load_more, null);
            this.footerView = viewInflate;
            this.textView = (TextView) viewInflate.findViewById(C1034R.id.text);
            this.progressBar = (ProgressBar) this.footerView.findViewById(C1034R.id.progress);
        }
        ((ListView) getRefreshableView()).removeFooterView(this.footerView);
        ((ListView) getRefreshableView()).setFooterDividersEnabled(false);
        setLoadMoreViewTextLoading();
        ((ListView) getRefreshableView()).addFooterView(this.footerView);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void updateLoadMoreViewText(List list) {
        if (((ListView) getRefreshableView()).getAdapter().getCount() == 0 || list.isEmpty()) {
            setLoadMoreViewTextNoData();
        } else if (list.size() < 10) {
            setLoadMoreViewTextNoMoreData();
        }
    }

    public void setLoadMoreViewTextLoading() {
        if (this.progressBar.getVisibility() == 0) {
            return;
        }
        this.textView.setText(C1034R.string.pull_to_refresh_refreshing_label1);
        this.progressBar.setVisibility(0);
    }

    public void setLoadMoreViewTextError() {
        this.textView.setText(C1034R.string.pull_to_refresh_net_error_label);
        this.progressBar.setVisibility(8);
    }

    public void setLoadMoreViewTextNoData() {
        this.textView.setText(C1034R.string.pull_to_refresh_no_data_label);
        this.progressBar.setVisibility(8);
    }

    public void setLoadMoreViewTextNoMoreData() {
        this.textView.setText(C1034R.string.pull_to_refresh_no_more_data_label);
        this.progressBar.setVisibility(8);
    }
}
