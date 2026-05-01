package com.aivox.common_ui.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;

/* JADX INFO: loaded from: classes.dex */
public class PullToRefreshScrollView extends PullToRefreshBase<ScrollView> {
    private View footerView;
    private ProgressBar progressBar;
    private TextView textView;

    public PullToRefreshScrollView(Context context) {
        super(context);
    }

    public PullToRefreshScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PullToRefreshScrollView(Context context, PullToRefreshBase.Mode mode) {
        super(context, mode);
    }

    public PullToRefreshScrollView(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    public final PullToRefreshBase.Orientation getPullToRefreshScrollDirection() {
        return PullToRefreshBase.Orientation.VERTICAL;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    public ScrollView createRefreshableView(Context context, AttributeSet attributeSet) {
        InternalScrollViewSDK9 internalScrollViewSDK9 = new InternalScrollViewSDK9(context, attributeSet);
        internalScrollViewSDK9.setId(C1034R.id.scrollview);
        return internalScrollViewSDK9;
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected boolean isReadyForPullStart() {
        return ((ScrollView) this.mRefreshableView).getScrollY() == 0;
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected boolean isReadyForPullEnd() {
        View childAt = ((ScrollView) this.mRefreshableView).getChildAt(0);
        return childAt != null && ((ScrollView) this.mRefreshableView).getScrollY() >= childAt.getHeight() - getHeight();
    }

    final class InternalScrollViewSDK9 extends ScrollView {
        public InternalScrollViewSDK9(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.view.View
        protected boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            boolean zOverScrollBy = super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
            OverscrollHelper.overScrollBy(PullToRefreshScrollView.this, i, i3, i2, i4, getScrollRange(), z);
            return zOverScrollBy;
        }

        private int getScrollRange() {
            if (getChildCount() > 0) {
                return Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
            }
            return 0;
        }
    }

    public void withLoadMoreView() {
        if (this.footerView == null) {
            View viewInflate = inflate(getContext(), C1034R.layout.layout_load_more, null);
            this.footerView = viewInflate;
            this.textView = (TextView) viewInflate.findViewById(C1034R.id.text);
            this.progressBar = (ProgressBar) this.footerView.findViewById(C1034R.id.progress);
        }
        setLoadMoreViewTextLoading();
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
