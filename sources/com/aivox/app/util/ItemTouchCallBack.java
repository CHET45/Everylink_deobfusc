package com.aivox.app.util;

import android.util.Log;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;

/* JADX INFO: loaded from: classes.dex */
public class ItemTouchCallBack extends ItemTouchHelper.Callback {
    private static final String TAG = "ItemTouchCallBack";
    private boolean isSwipe;
    private OnItemTouchListener onItemTouchListener;

    public interface OnItemTouchListener {
        void onFinished();

        void onMove(int i, int i2);

        void onSelected(int i);

        void onSwiped(int i);
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
        return true;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
    }

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i(TAG, recyclerView.getChildCount() + "getMovementFlags" + viewHolder.getAdapterPosition());
        if (viewHolder.itemView instanceof LinearLayout) {
            return 0;
        }
        return makeMovementFlags(3, 12);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        Log.i(TAG, "onMove");
        this.onItemTouchListener.onMove(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
        return true;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
        super.onSelectedChanged(viewHolder, i);
        Log.i(TAG, "onSelectedChangedooo" + i);
        if (viewHolder == null || (viewHolder.itemView instanceof LinearLayout) || viewHolder.getAbsoluteAdapterPosition() == -1) {
            return;
        }
        this.isSwipe = i == 1;
        if (i == 2) {
            Log.i(TAG, "onSelectedChanged" + i + viewHolder.getAbsoluteAdapterPosition());
            this.onItemTouchListener.onSelected(viewHolder.getAbsoluteAdapterPosition());
            viewHolder.itemView.setScaleX(1.03f);
            viewHolder.itemView.setScaleY(1.03f);
            viewHolder.itemView.getLayoutParams().height = Math.min(viewHolder.itemView.getHeight(), ConvertUtils.dp2px(200.0f));
        }
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i(TAG, "clearView");
        viewHolder.itemView.setScaleX(1.0f);
        viewHolder.itemView.setScaleY(1.0f);
        viewHolder.itemView.getLayoutParams().height = -2;
        if (this.isSwipe) {
            this.onItemTouchListener.onSelected(viewHolder.getAbsoluteAdapterPosition());
        }
        this.onItemTouchListener.onFinished();
        super.clearView(recyclerView, viewHolder);
    }
}
