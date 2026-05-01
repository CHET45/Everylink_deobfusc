package com.aivox.common_ui.group;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class GroupRecyclerView extends RecyclerView {
    private boolean isCenter;
    private boolean isHasHeader;
    protected int mChildItemOffset;
    private int mGroupHeight;
    private int mGroutBackground;
    private GroupItemDecoration mItemDecoration;
    private OnGroupChangeListener mListener;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mTextColor;
    private int mTextSize;

    public interface OnGroupChangeListener {
        void onGroupChange(int i, String str);
    }

    public GroupRecyclerView(Context context) {
        super(context);
    }

    public GroupRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public GroupRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.GroupRecyclerView);
        this.mTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(C1034R.styleable.GroupRecyclerView_group_text_size, 16);
        this.mGroupHeight = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.GroupRecyclerView_group_height, 52.0f);
        this.mChildItemOffset = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.GroupRecyclerView_group_child_offset, 20.0f);
        this.mTextColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.GroupRecyclerView_group_text_color, -1);
        this.mGroutBackground = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.GroupRecyclerView_group_background, Integer.MIN_VALUE);
        this.isCenter = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.GroupRecyclerView_group_center, false);
        this.isHasHeader = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.GroupRecyclerView_group_has_header, true);
        this.mPaddingLeft = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.GroupRecyclerView_group_padding_left, 16.0f);
        this.mPaddingRight = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.GroupRecyclerView_group_padding_right, 16.0f);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter instanceof GroupRecyclerAdapter) {
            super.setAdapter(adapter);
            return;
        }
        throw new IllegalStateException("Adapter must instanceof GroupRecyclerAdapter or extends GroupRecyclerAdapter");
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (itemDecoration instanceof GroupItemDecoration) {
            super.addItemDecoration(itemDecoration);
            GroupItemDecoration groupItemDecoration = (GroupItemDecoration) itemDecoration;
            this.mItemDecoration = groupItemDecoration;
            groupItemDecoration.setTextSize(this.mTextSize);
            this.mItemDecoration.setBackground(this.mGroutBackground);
            this.mItemDecoration.setTextColor(this.mTextColor);
            this.mItemDecoration.setGroupHeight(this.mGroupHeight);
            this.mItemDecoration.setPadding(this.mPaddingLeft, this.mPaddingRight);
            this.mItemDecoration.setCenter(this.isCenter);
            this.mItemDecoration.setHasHeader(this.isHasHeader);
            this.mItemDecoration.setChildItemOffset(this.mChildItemOffset);
            return;
        }
        throw new IllegalStateException("ItemDecoration must instanceof GroupItemDecoration or extends GroupItemDecoration");
    }

    public void notifyDataSetChanged() {
        this.mItemDecoration.notifyDataSetChanged((GroupRecyclerAdapter) getAdapter());
    }

    public void setOnGroupChangeListener(OnGroupChangeListener onGroupChangeListener) {
        this.mListener = onGroupChangeListener;
    }
}
