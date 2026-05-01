package com.aivox.common_ui.pulltorefresh;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;
import com.aivox.common_ui.pulltorefresh.internal.EmptyViewMethodAccessor;

/* JADX INFO: loaded from: classes.dex */
public class PullToRefreshExpandableListView extends PullToRefreshAdapterViewBase<ExpandableListView> {
    public PullToRefreshExpandableListView(Context context) {
        super(context);
    }

    public PullToRefreshExpandableListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PullToRefreshExpandableListView(Context context, PullToRefreshBase.Mode mode) {
        super(context, mode);
    }

    public PullToRefreshExpandableListView(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    public final PullToRefreshBase.Orientation getPullToRefreshScrollDirection() {
        return PullToRefreshBase.Orientation.VERTICAL;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase
    public ExpandableListView createRefreshableView(Context context, AttributeSet attributeSet) {
        InternalExpandableListViewSDK9 internalExpandableListViewSDK9 = new InternalExpandableListViewSDK9(context, attributeSet);
        internalExpandableListViewSDK9.setId(R.id.list);
        return internalExpandableListViewSDK9;
    }

    class InternalExpandableListView extends ExpandableListView implements EmptyViewMethodAccessor {
        public InternalExpandableListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.widget.AdapterView, com.aivox.common_ui.pulltorefresh.internal.EmptyViewMethodAccessor
        public void setEmptyView(View view2) {
            PullToRefreshExpandableListView.this.setEmptyView(view2);
        }

        @Override // com.aivox.common_ui.pulltorefresh.internal.EmptyViewMethodAccessor
        public void setEmptyViewInternal(View view2) {
            super.setEmptyView(view2);
        }
    }

    final class InternalExpandableListViewSDK9 extends InternalExpandableListView {
        public InternalExpandableListViewSDK9(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.view.View
        protected boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            boolean zOverScrollBy = super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
            OverscrollHelper.overScrollBy(PullToRefreshExpandableListView.this, i, i3, i2, i4, z);
            return zOverScrollBy;
        }
    }
}
