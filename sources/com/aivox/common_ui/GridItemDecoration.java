package com.aivox.common_ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/* JADX INFO: loaded from: classes.dex */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    Builder mBuilder;
    Paint mHorPaint;
    Paint mVerPaint;

    public GridItemDecoration(Builder builder) {
        init(builder);
    }

    void init(Builder builder) {
        this.mBuilder = builder;
        Paint paint = new Paint(1);
        this.mVerPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mVerPaint.setColor(builder.verColor);
        Paint paint2 = new Paint(1);
        this.mHorPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.mHorPaint.setColor(builder.horColor);
    }

    private void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        int spanCount = getSpanCount(recyclerView);
        if (spanCount == -1) {
            return;
        }
        int itemCount = recyclerView.getAdapter().getItemCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int adapterPosition = recyclerView.getChildViewHolder(childAt).getAdapterPosition();
            if (adapterPosition != -1 && (!isLastRaw(recyclerView, adapterPosition, spanCount, itemCount) || this.mBuilder.isShowLastDivider)) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                int left = childAt.getLeft() - layoutParams.leftMargin;
                int right = childAt.getRight() + layoutParams.rightMargin;
                canvas.drawRect(left, childAt.getBottom() + layoutParams.bottomMargin, right, this.mBuilder.dividerHorSize + r7, this.mHorPaint);
            }
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        GridSpanParams gridSpanParams;
        int childCount = recyclerView.getChildCount();
        int spanCount = getSpanCount(recyclerView);
        if (spanCount == -1) {
            return;
        }
        int itemCount = recyclerView.getAdapter().getItemCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int adapterPosition = recyclerView.getChildViewHolder(childAt).getAdapterPosition();
            if (adapterPosition != -1 && (gridSpanParams = getGridSpanParams(childAt, recyclerView, adapterPosition, spanCount)) != null && gridSpanParams.spanIndex + gridSpanParams.spanSize != spanCount) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                int top2 = childAt.getTop() - layoutParams.topMargin;
                int bottom = childAt.getBottom() + layoutParams.bottomMargin + this.mBuilder.dividerHorSize;
                int right = childAt.getRight() + layoutParams.rightMargin;
                int i2 = this.mBuilder.dividerVerSize + right;
                if (isLastRaw(recyclerView, adapterPosition, spanCount, itemCount) && !this.mBuilder.isShowLastDivider) {
                    bottom -= this.mBuilder.dividerHorSize;
                }
                canvas.drawRect(right, top2, i2, bottom, this.mVerPaint);
            }
        }
    }

    private static class GridSpanParams {
        int spanIndex;
        int spanSize;

        private GridSpanParams() {
            this.spanIndex = 0;
            this.spanSize = 1;
        }
    }

    private GridSpanParams getGridSpanParams(View view2, RecyclerView recyclerView, int i, int i2) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view2.getLayoutParams();
            if (layoutParams instanceof GridLayoutManager.LayoutParams) {
                GridLayoutManager.LayoutParams layoutParams2 = (GridLayoutManager.LayoutParams) layoutParams;
                GridSpanParams gridSpanParams = new GridSpanParams();
                gridSpanParams.spanIndex = layoutParams2.getSpanIndex();
                gridSpanParams.spanSize = layoutParams2.getSpanSize();
                return gridSpanParams;
            }
            GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            if (spanSizeLookup != null && i >= 0) {
                new GridSpanParams().spanSize = spanSizeLookup.getSpanSize(i);
            }
        }
        return null;
    }

    private int getSpanCount(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return -1;
    }

    private boolean isLastRaw(RecyclerView recyclerView, int i, int i2, int i3) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            return i >= 0 && i < i3 && spanSizeLookup.getSpanGroupIndex(i, i2) == spanSizeLookup.getSpanGroupIndex(i3 - 1, i2);
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) layoutManager).getOrientation() == 1) {
                if (i >= i3 - (i3 % i2)) {
                    return true;
                }
            } else if ((i + 1) % i2 == 0) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        super.onDraw(canvas, recyclerView, state);
        drawHorizontal(canvas, recyclerView);
        drawVertical(canvas, recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
        GridSpanParams gridSpanParams;
        super.getItemOffsets(rect, view2, recyclerView, state);
        int spanCount = getSpanCount(recyclerView);
        if (spanCount == -1) {
            return;
        }
        int itemCount = recyclerView.getAdapter().getItemCount();
        int viewLayoutPosition = ((RecyclerView.LayoutParams) view2.getLayoutParams()).getViewLayoutPosition();
        if (this.mBuilder.isExistHeadView) {
            viewLayoutPosition--;
        }
        if (this.mBuilder.isShowHeadDivider && viewLayoutPosition == -1) {
            rect.set(0, 0, 0, this.mBuilder.dividerHorSize);
        }
        if (viewLayoutPosition >= 0 && (gridSpanParams = getGridSpanParams(view2, recyclerView, viewLayoutPosition, spanCount)) != null) {
            int i = gridSpanParams.spanSize;
            int i2 = gridSpanParams.spanIndex;
            rect.set((this.mBuilder.dividerVerSize * i2) / spanCount, 0, this.mBuilder.dividerVerSize - (((i2 + i) * this.mBuilder.dividerVerSize) / spanCount), (!isLastRaw(recyclerView, viewLayoutPosition, spanCount, itemCount) || this.mBuilder.isShowLastDivider) ? this.mBuilder.dividerHorSize : 0);
            if (this.mBuilder.marginLeft == 0 && this.mBuilder.marginRight == 0) {
                return;
            }
            marginOffsets(rect, spanCount, i2, i);
        }
    }

    private void marginOffsets(Rect rect, int i, int i2, int i3) {
        if (this.mBuilder.marginLeft == 0 && this.mBuilder.marginRight == 0) {
            return;
        }
        int i4 = (this.mBuilder.marginLeft + this.mBuilder.marginRight) / i;
        rect.left += this.mBuilder.marginLeft - (i2 * i4);
        rect.right += ((i2 + i3) * i4) - this.mBuilder.marginLeft;
    }

    public static class Builder {

        /* JADX INFO: renamed from: c */
        private Context f266c;
        int dividerHorSize;
        int dividerVerSize;
        int horColor;
        int marginLeft;
        int marginRight;
        int verColor;
        boolean isShowLastDivider = false;
        boolean isExistHeadView = false;
        boolean isShowHeadDivider = false;

        public Builder(Context context) {
            this.f266c = context;
        }

        public Builder color(int i) {
            this.horColor = this.f266c.getResources().getColor(i);
            this.verColor = this.f266c.getResources().getColor(i);
            return this;
        }

        public Builder horColor(int i) {
            this.horColor = this.f266c.getResources().getColor(i);
            return this;
        }

        public Builder verColor(int i) {
            this.verColor = this.f266c.getResources().getColor(i);
            return this;
        }

        public Builder size(int i) {
            this.dividerHorSize = i;
            this.dividerVerSize = i;
            return this;
        }

        public Builder horSize(int i) {
            this.dividerHorSize = i;
            return this;
        }

        public Builder verSize(int i) {
            this.dividerVerSize = i;
            return this;
        }

        public Builder margin(int i, int i2) {
            this.marginLeft = i;
            this.marginRight = i2;
            return this;
        }

        public Builder showLastDivider(boolean z) {
            this.isShowLastDivider = z;
            return this;
        }

        public Builder showHeadDivider(boolean z) {
            this.isShowHeadDivider = z;
            return this;
        }

        public Builder isExistHead(boolean z) {
            this.isExistHeadView = z;
            return this;
        }

        public GridItemDecoration build() {
            return new GridItemDecoration(this);
        }
    }
}
