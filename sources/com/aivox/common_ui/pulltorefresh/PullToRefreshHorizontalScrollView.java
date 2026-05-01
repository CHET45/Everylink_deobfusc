package com.aivox.common_ui.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;

/* JADX INFO: loaded from: classes.dex */
public class PullToRefreshHorizontalScrollView extends PullToRefreshBase<HorizontalScrollView> {
    public PullToRefreshHorizontalScrollView(Context context) {
        super(context);
    }

    public PullToRefreshHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PullToRefreshHorizontalScrollView(Context context, PullToRefreshBase.Mode mode) {
        super(context, mode);
    }

    public PullToRefreshHorizontalScrollView(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    public final PullToRefreshBase.Orientation getPullToRefreshScrollDirection() {
        return PullToRefreshBase.Orientation.HORIZONTAL;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    public HorizontalScrollView createRefreshableView(Context context, AttributeSet attributeSet) {
        InternalHorizontalScrollViewSDK9 internalHorizontalScrollViewSDK9 = new InternalHorizontalScrollViewSDK9(context, attributeSet);
        internalHorizontalScrollViewSDK9.setId(C1034R.id.scrollview);
        return internalHorizontalScrollViewSDK9;
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected boolean isReadyForPullStart() {
        return ((HorizontalScrollView) this.mRefreshableView).getScrollX() == 0;
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    protected boolean isReadyForPullEnd() {
        View childAt = ((HorizontalScrollView) this.mRefreshableView).getChildAt(0);
        return childAt != null && ((HorizontalScrollView) this.mRefreshableView).getScrollX() >= childAt.getWidth() - getWidth();
    }

    final class InternalHorizontalScrollViewSDK9 extends HorizontalScrollView {
        public InternalHorizontalScrollViewSDK9(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.view.View
        protected boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            boolean zOverScrollBy = super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
            OverscrollHelper.overScrollBy(PullToRefreshHorizontalScrollView.this, i, i3, i2, i4, getScrollRange(), z);
            return zOverScrollBy;
        }

        private int getScrollRange() {
            if (getChildCount() > 0) {
                return Math.max(0, getChildAt(0).getWidth() - ((getWidth() - getPaddingLeft()) - getPaddingRight()));
            }
            return 0;
        }
    }
}
