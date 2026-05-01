package com.aivox.common_ui.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;
import com.aivox.common_ui.pulltorefresh.internal.EmptyViewMethodAccessor;

/* JADX INFO: loaded from: classes.dex */
public class PullToRefreshGridView extends PullToRefreshAdapterViewBase<GridView> {
    public PullToRefreshGridView(Context context) {
        super(context);
    }

    public PullToRefreshGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PullToRefreshGridView(Context context, PullToRefreshBase.Mode mode) {
        super(context, mode);
    }

    public PullToRefreshGridView(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    public final PullToRefreshBase.Orientation getPullToRefreshScrollDirection() {
        return PullToRefreshBase.Orientation.VERTICAL;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    public final GridView createRefreshableView(Context context, AttributeSet attributeSet) {
        InternalGridViewSDK9 internalGridViewSDK9 = new InternalGridViewSDK9(context, attributeSet);
        internalGridViewSDK9.setId(C1034R.id.gridview);
        return internalGridViewSDK9;
    }

    class InternalGridView extends GridView implements EmptyViewMethodAccessor {
        public InternalGridView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.widget.AdapterView, com.aivox.common_ui.pulltorefresh.internal.EmptyViewMethodAccessor
        public void setEmptyView(View view2) {
            PullToRefreshGridView.this.setEmptyView(view2);
        }

        @Override // com.aivox.common_ui.pulltorefresh.internal.EmptyViewMethodAccessor
        public void setEmptyViewInternal(View view2) {
            super.setEmptyView(view2);
        }
    }

    final class InternalGridViewSDK9 extends InternalGridView {
        public InternalGridViewSDK9(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.view.View
        protected boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            boolean zOverScrollBy = super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
            OverscrollHelper.overScrollBy(PullToRefreshGridView.this, i, i3, i2, i4, z);
            return zOverScrollBy;
        }
    }
}
