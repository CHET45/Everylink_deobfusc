package com.aivox.common_ui.group;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class GroupItemDecoration<Group, Child> extends RecyclerView.ItemDecoration {
    protected boolean isCenter;
    protected boolean isHasHeader;
    protected Paint mBackgroundPaint;
    protected int mChildItemOffset;
    protected Map<Integer, Group> mGroup = new HashMap();
    protected int mGroupHeight;
    protected int mGroutBackground;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected float mTextBaseLine;
    protected Paint mTextPaint;

    public GroupItemDecoration() {
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.mBackgroundPaint = paint;
        paint.setColor(-657416);
        this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mBackgroundPaint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.mTextPaint = paint2;
        paint2.setColor(-13290187);
        this.mTextPaint.setAntiAlias(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        super.onDraw(canvas, recyclerView, state);
        onDrawGroup(canvas, recyclerView);
    }

    protected void onDrawGroup(Canvas canvas, RecyclerView recyclerView) {
        float measuredWidth;
        int paddingLeft = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        recyclerView.getChildCount();
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View childAt = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            if (this.mGroup.containsKey(Integer.valueOf(layoutParams.getViewLayoutPosition()))) {
                int top2 = childAt.getTop() - layoutParams.topMargin;
                int i2 = this.mGroupHeight;
                int i3 = top2 - i2;
                int i4 = i2 + i3;
                float f = i3;
                canvas.drawRect(paddingLeft, f, width, i4, this.mBackgroundPaint);
                String string = this.mGroup.get(Integer.valueOf(layoutParams.getViewLayoutPosition())).toString();
                float f2 = f + this.mTextBaseLine;
                if (this.isCenter) {
                    measuredWidth = (recyclerView.getMeasuredWidth() / 2) - getTextX(string);
                } else {
                    measuredWidth = this.mPaddingLeft;
                }
                canvas.drawText(string, measuredWidth, f2, this.mTextPaint);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        super.onDrawOver(canvas, recyclerView, state);
        onDrawOverGroup(canvas, recyclerView);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onDrawOverGroup(android.graphics.Canvas r13, androidx.recyclerview.widget.RecyclerView r14) {
        /*
            r12 = this;
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r14.getLayoutManager()
            androidx.recyclerview.widget.LinearLayoutManager r0 = (androidx.recyclerview.widget.LinearLayoutManager) r0
            int r0 = r0.findFirstVisibleItemPosition()
            r1 = -1
            if (r0 != r1) goto Le
            return
        Le:
            java.lang.Object r1 = r12.getCroup(r0)
            if (r1 != 0) goto L15
            return
        L15:
            java.lang.String r2 = r1.toString()
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 == 0) goto L20
            return
        L20:
            int r3 = r0 + 1
            java.lang.Object r3 = r12.getCroup(r3)
            if (r3 == 0) goto L57
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L57
            androidx.recyclerview.widget.RecyclerView$ViewHolder r0 = r14.findViewHolderForAdapterPosition(r0)
            android.view.View r0 = r0.itemView
            int r1 = r0.getTop()
            int r3 = r0.getMeasuredHeight()
            int r1 = r1 + r3
            int r3 = r12.mGroupHeight
            if (r1 >= r3) goto L57
            r13.save()
            int r1 = r0.getTop()
            int r0 = r0.getMeasuredHeight()
            int r1 = r1 + r0
            int r0 = r12.mGroupHeight
            int r1 = r1 - r0
            float r0 = (float) r1
            r1 = 0
            r13.translate(r1, r0)
            r0 = 1
            goto L58
        L57:
            r0 = 0
        L58:
            int r1 = r14.getPaddingLeft()
            int r3 = r14.getWidth()
            int r4 = r14.getPaddingRight()
            int r3 = r3 - r4
            int r4 = r14.getPaddingTop()
            int r5 = r12.mGroupHeight
            int r5 = r5 + r4
            float r7 = (float) r1
            float r1 = (float) r4
            float r9 = (float) r3
            float r10 = (float) r5
            android.graphics.Paint r11 = r12.mBackgroundPaint
            r6 = r13
            r8 = r1
            r6.drawRect(r7, r8, r9, r10, r11)
            float r3 = r12.mTextBaseLine
            float r1 = r1 + r3
            boolean r3 = r12.isCenter
            if (r3 == 0) goto L8b
            int r14 = r14.getMeasuredWidth()
            int r14 = r14 / 2
            float r14 = (float) r14
            float r3 = r12.getTextX(r2)
            float r14 = r14 - r3
            goto L8e
        L8b:
            int r14 = r12.mPaddingLeft
            float r14 = (float) r14
        L8e:
            android.graphics.Paint r3 = r12.mTextPaint
            r13.drawText(r2, r14, r1, r3)
            if (r0 == 0) goto L98
            r13.restore()
        L98:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common_ui.group.GroupItemDecoration.onDrawOverGroup(android.graphics.Canvas, androidx.recyclerview.widget.RecyclerView):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view2, recyclerView, state);
        getItemOffsets(rect, view2, recyclerView, recyclerView.getChildViewHolder(view2).getAdapterPosition());
    }

    protected void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, int i) {
        if (this.mGroup.containsKey(Integer.valueOf(i))) {
            rect.set(0, this.mGroupHeight, 0, this.mGroup.containsKey(Integer.valueOf(i + 1)) ? 0 : this.mChildItemOffset);
        } else {
            rect.set(0, 0, 0, this.mGroup.containsKey(Integer.valueOf(i + 1)) ? 0 : this.mChildItemOffset);
        }
    }

    protected Group getCroup(int i) {
        while (i >= 0) {
            if (this.mGroup.containsKey(Integer.valueOf(i))) {
                return this.mGroup.get(Integer.valueOf(i));
            }
            i--;
        }
        return null;
    }

    public void notifyDataSetChanged(GroupRecyclerAdapter<Group, Child> groupRecyclerAdapter) {
        int childCount;
        this.mGroup.clear();
        if (groupRecyclerAdapter == null) {
            return;
        }
        int i = 0;
        for (int i2 = 0; i2 < groupRecyclerAdapter.getGroupCount(); i2++) {
            if (i2 == 0) {
                this.mGroup.put(Integer.valueOf(this.isHasHeader ? 1 : 0), groupRecyclerAdapter.getGroup(i2));
                childCount = groupRecyclerAdapter.getChildCount(i2) + (this.isHasHeader ? 1 : 0);
            } else {
                this.mGroup.put(Integer.valueOf(i), groupRecyclerAdapter.getGroup(i2));
                childCount = groupRecyclerAdapter.getChildCount(i2);
            }
            i += childCount;
        }
    }

    public void setChildItemOffset(int i) {
        this.mChildItemOffset = i;
    }

    public void setBackground(int i) {
        this.mBackgroundPaint.setColor(i);
    }

    public void setTextColor(int i) {
        this.mTextPaint.setColor(i);
    }

    public void setTextSize(float f) {
        this.mTextPaint.setTextSize(f);
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        this.mTextBaseLine = ((this.mGroupHeight / 2) - fontMetrics.descent) + ((fontMetrics.bottom - fontMetrics.top) / 2.0f);
    }

    public void setGroupHeight(int i) {
        this.mGroupHeight = i;
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        this.mTextBaseLine = ((this.mGroupHeight / 2) - fontMetrics.descent) + ((fontMetrics.bottom - fontMetrics.top) / 2.0f);
    }

    public void setPadding(int i, int i2) {
        this.mPaddingLeft = i;
        this.mPaddingRight = i2;
    }

    public void setCenter(boolean z) {
        this.isCenter = z;
    }

    public void setHasHeader(boolean z) {
        this.isHasHeader = z;
    }

    protected float getTextX(String str) {
        this.mTextPaint.getTextBounds(str, 0, str.length(), new Rect());
        return r0.width() / 2;
    }

    protected float getTextLenghtPx(String str) {
        this.mTextPaint.getTextBounds(str, 0, str.length(), new Rect());
        return r0.width();
    }
}
