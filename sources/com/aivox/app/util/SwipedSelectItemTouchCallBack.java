package com.aivox.app.util;

import android.util.Log;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class SwipedSelectItemTouchCallBack extends ItemTouchHelper.Callback {
    private static final String TAG = "SwipedSelectItemTouchCallBack";
    private boolean mIsSwiping = false;
    private OnItemSwipeListener mTouchListener;

    public interface OnItemSwipeListener {
        void onSwipeSelected(int i);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public float getSwipeEscapeVelocity(float f) {
        return 999999.0f;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return 10.0f;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isLongPressDragEnabled() {
        return false;
    }

    public void setOnItemTouchListener(OnItemSwipeListener onItemSwipeListener) {
        this.mTouchListener = onItemSwipeListener;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i(TAG, "getMovementFlags: count = " + recyclerView.getChildCount() + " position = " + viewHolder.getAdapterPosition());
        if (viewHolder.itemView instanceof LinearLayout) {
            return 0;
        }
        return makeMovementFlags(0, 12);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        Log.i(TAG, "onMove");
        return true;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        Log.i(TAG, "onSwiped");
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
        super.onSelectedChanged(viewHolder, i);
        Log.i(TAG, "onSelectedChanged: actionState = " + i);
        if (viewHolder == null || i != 1) {
            return;
        }
        this.mIsSwiping = true;
        Log.i(TAG, i + "onSelectedChanged onSelected" + viewHolder.getAbsoluteAdapterPosition());
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i(TAG, "clearView");
        if (this.mIsSwiping && viewHolder != null) {
            this.mTouchListener.onSwipeSelected(viewHolder.getAbsoluteAdapterPosition());
            this.mIsSwiping = false;
        }
        super.clearView(recyclerView, viewHolder);
    }
}
