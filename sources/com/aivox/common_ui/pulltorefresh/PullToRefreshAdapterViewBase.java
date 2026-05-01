package com.aivox.common_ui.pulltorefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;
import com.aivox.common_ui.pulltorefresh.internal.EmptyViewMethodAccessor;
import com.aivox.common_ui.pulltorefresh.internal.IndicatorLayout;

/* JADX INFO: loaded from: classes.dex */
public abstract class PullToRefreshAdapterViewBase<T extends AbsListView> extends PullToRefreshBase<T> implements AbsListView.OnScrollListener {
    private View mEmptyView;
    private IndicatorLayout mIndicatorIvBottom;
    private IndicatorLayout mIndicatorIvTop;
    private boolean mLastItemVisible;
    private PullToRefreshBase.OnLastItemVisibleListener mOnLastItemVisibleListener;
    private AbsListView.OnScrollListener mOnScrollListener;
    private boolean mScrollEmptyView;
    private boolean mShowIndicator;

    private static FrameLayout.LayoutParams convertEmptyViewLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return null;
        }
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            layoutParams2.gravity = ((LinearLayout.LayoutParams) layoutParams).gravity;
            return layoutParams2;
        }
        layoutParams2.gravity = 17;
        return layoutParams2;
    }

    public PullToRefreshAdapterViewBase(Context context) {
        super(context);
        this.mScrollEmptyView = true;
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mScrollEmptyView = true;
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, PullToRefreshBase.Mode mode) {
        super(context, mode);
        this.mScrollEmptyView = true;
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
        this.mScrollEmptyView = true;
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public boolean getShowIndicator() {
        return this.mShowIndicator;
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public final void onScroll(AbsListView absListView, int i, int i2, int i3) {
        if (this.mOnLastItemVisibleListener != null) {
            this.mLastItemVisible = i3 > 0 && i + i2 >= i3 + (-1);
        }
        if (getShowIndicatorInternal()) {
            updateIndicatorViewsVisibility();
        }
        AbsListView.OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScroll(absListView, i, i2, i3);
        }
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public final void onScrollStateChanged(AbsListView absListView, int i) {
        PullToRefreshBase.OnLastItemVisibleListener onLastItemVisibleListener;
        if (i == 0 && (onLastItemVisibleListener = this.mOnLastItemVisibleListener) != null && this.mLastItemVisible) {
            onLastItemVisibleListener.onLastItemVisible();
        }
        AbsListView.OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChanged(absListView, i);
        }
    }

    public void setAdapter(ListAdapter listAdapter) {
        ((AdapterView) this.mRefreshableView).setAdapter(listAdapter);
    }

    public final void setEmptyView(View view2) {
        FrameLayout refreshableViewWrapper = getRefreshableViewWrapper();
        if (view2 != null) {
            view2.setClickable(true);
            ViewParent parent = view2.getParent();
            if (parent != null && (parent instanceof ViewGroup)) {
                ((ViewGroup) parent).removeView(view2);
            }
            FrameLayout.LayoutParams layoutParamsConvertEmptyViewLayoutParams = convertEmptyViewLayoutParams(view2.getLayoutParams());
            if (layoutParamsConvertEmptyViewLayoutParams != null) {
                refreshableViewWrapper.addView(view2, layoutParamsConvertEmptyViewLayoutParams);
            } else {
                refreshableViewWrapper.addView(view2);
            }
        }
        if (this.mRefreshableView instanceof EmptyViewMethodAccessor) {
            ((EmptyViewMethodAccessor) this.mRefreshableView).setEmptyViewInternal(view2);
        } else {
            ((AbsListView) this.mRefreshableView).setEmptyView(view2);
        }
        this.mEmptyView = view2;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        ((AbsListView) this.mRefreshableView).setOnItemClickListener(onItemClickListener);
    }

    public final void setOnLastItemVisibleListener(PullToRefreshBase.OnLastItemVisibleListener onLastItemVisibleListener) {
        this.mOnLastItemVisibleListener = onLastItemVisibleListener;
    }

    public final void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public final void setScrollEmptyView(boolean z) {
        this.mScrollEmptyView = z;
    }

    public void setShowIndicator(boolean z) {
        this.mShowIndicator = z;
        if (getShowIndicatorInternal()) {
            addIndicatorViews();
        } else {
            removeIndicatorViews();
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected void onPullToRefresh() {
        super.onPullToRefresh();
        if (getShowIndicatorInternal()) {
            int i = C10541.f276x5281aad0[getCurrentMode().ordinal()];
            if (i == 1) {
                this.mIndicatorIvBottom.pullToRefresh();
            } else {
                if (i != 2) {
                    return;
                }
                this.mIndicatorIvTop.pullToRefresh();
            }
        }
    }

    /* JADX INFO: renamed from: com.aivox.common_ui.pulltorefresh.PullToRefreshAdapterViewBase$1 */
    static /* synthetic */ class C10541 {

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$Mode */
        static final /* synthetic */ int[] f276x5281aad0;

        static {
            int[] iArr = new int[PullToRefreshBase.Mode.values().length];
            f276x5281aad0 = iArr;
            try {
                iArr[PullToRefreshBase.Mode.PULL_FROM_END.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f276x5281aad0[PullToRefreshBase.Mode.PULL_FROM_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected void onRefreshing(boolean z) {
        super.onRefreshing(z);
        if (getShowIndicatorInternal()) {
            updateIndicatorViewsVisibility();
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected void onReleaseToRefresh() {
        super.onReleaseToRefresh();
        if (getShowIndicatorInternal()) {
            int i = C10541.f276x5281aad0[getCurrentMode().ordinal()];
            if (i == 1) {
                this.mIndicatorIvBottom.releaseToRefresh();
            } else {
                if (i != 2) {
                    return;
                }
                this.mIndicatorIvTop.releaseToRefresh();
            }
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected void onReset() {
        super.onReset();
        if (getShowIndicatorInternal()) {
            updateIndicatorViewsVisibility();
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected void handleStyledAttributes(TypedArray typedArray) {
        this.mShowIndicator = typedArray.getBoolean(C1034R.styleable.PullToRefresh_ptrShowIndicator, !isPullToRefreshOverScrollEnabled());
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        View view2 = this.mEmptyView;
        if (view2 == null || this.mScrollEmptyView) {
            return;
        }
        view2.scrollTo(-i, -i2);
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected void updateUIForMode() {
        super.updateUIForMode();
        if (getShowIndicatorInternal()) {
            addIndicatorViews();
        } else {
            removeIndicatorViews();
        }
    }

    private void addIndicatorViews() {
        IndicatorLayout indicatorLayout;
        IndicatorLayout indicatorLayout2;
        PullToRefreshBase.Mode mode = getMode();
        FrameLayout refreshableViewWrapper = getRefreshableViewWrapper();
        if (mode.showHeaderLoadingLayout() && this.mIndicatorIvTop == null) {
            this.mIndicatorIvTop = new IndicatorLayout(getContext(), PullToRefreshBase.Mode.PULL_FROM_START);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.rightMargin = getResources().getDimensionPixelSize(C1034R.dimen.indicator_right_padding);
            layoutParams.gravity = 53;
            refreshableViewWrapper.addView(this.mIndicatorIvTop, layoutParams);
        } else if (!mode.showHeaderLoadingLayout() && (indicatorLayout = this.mIndicatorIvTop) != null) {
            refreshableViewWrapper.removeView(indicatorLayout);
            this.mIndicatorIvTop = null;
        }
        if (mode.showFooterLoadingLayout() && this.mIndicatorIvBottom == null) {
            this.mIndicatorIvBottom = new IndicatorLayout(getContext(), PullToRefreshBase.Mode.PULL_FROM_END);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
            layoutParams2.rightMargin = getResources().getDimensionPixelSize(C1034R.dimen.indicator_right_padding);
            layoutParams2.gravity = 85;
            refreshableViewWrapper.addView(this.mIndicatorIvBottom, layoutParams2);
            return;
        }
        if (mode.showFooterLoadingLayout() || (indicatorLayout2 = this.mIndicatorIvBottom) == null) {
            return;
        }
        refreshableViewWrapper.removeView(indicatorLayout2);
        this.mIndicatorIvBottom = null;
    }

    private boolean getShowIndicatorInternal() {
        return this.mShowIndicator && isPullToRefreshEnabled();
    }

    private boolean isFirstItemVisible() {
        View childAt;
        Adapter adapter = ((AbsListView) this.mRefreshableView).getAdapter();
        if (adapter == null || adapter.isEmpty()) {
            return true;
        }
        return ((AbsListView) this.mRefreshableView).getFirstVisiblePosition() <= 1 && (childAt = ((AbsListView) this.mRefreshableView).getChildAt(0)) != null && childAt.getTop() >= ((AbsListView) this.mRefreshableView).getTop();
    }

    private boolean isLastItemVisible() {
        Adapter adapter = ((AbsListView) this.mRefreshableView).getAdapter();
        if (adapter == null || adapter.isEmpty()) {
            return true;
        }
        int count = ((AbsListView) this.mRefreshableView).getCount();
        int lastVisiblePosition = ((AbsListView) this.mRefreshableView).getLastVisiblePosition();
        if (lastVisiblePosition >= count - 2) {
            View childAt = ((AbsListView) this.mRefreshableView).getChildAt(lastVisiblePosition - ((AbsListView) this.mRefreshableView).getFirstVisiblePosition());
            return childAt != null && childAt.getBottom() <= ((AbsListView) this.mRefreshableView).getBottom();
        }
        return false;
    }

    private void removeIndicatorViews() {
        if (this.mIndicatorIvTop != null) {
            getRefreshableViewWrapper().removeView(this.mIndicatorIvTop);
            this.mIndicatorIvTop = null;
        }
        if (this.mIndicatorIvBottom != null) {
            getRefreshableViewWrapper().removeView(this.mIndicatorIvBottom);
            this.mIndicatorIvBottom = null;
        }
    }

    private void updateIndicatorViewsVisibility() {
        if (this.mIndicatorIvTop != null) {
            if (!isRefreshing() && isReadyForPullStart()) {
                if (!this.mIndicatorIvTop.isVisible()) {
                    this.mIndicatorIvTop.show();
                }
            } else if (this.mIndicatorIvTop.isVisible()) {
                this.mIndicatorIvTop.hide();
            }
        }
        if (this.mIndicatorIvBottom != null) {
            if (!isRefreshing() && isReadyForPullEnd()) {
                if (this.mIndicatorIvBottom.isVisible()) {
                    return;
                }
                this.mIndicatorIvBottom.show();
            } else if (this.mIndicatorIvBottom.isVisible()) {
                this.mIndicatorIvBottom.hide();
            }
        }
    }
}
