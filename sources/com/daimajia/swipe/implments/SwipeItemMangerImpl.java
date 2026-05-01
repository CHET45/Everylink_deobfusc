package com.daimajia.swipe.implments;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SwipeItemMangerImpl implements SwipeItemMangerInterface {
    protected BaseAdapter mBaseAdapter;
    protected RecyclerView.Adapter mRecyclerAdapter;
    private Attributes.Mode mode = Attributes.Mode.Single;
    public final int INVALID_POSITION = -1;
    protected int mOpenPosition = -1;
    protected Set<Integer> mOpenPositions = new HashSet();
    protected Set<SwipeLayout> mShownLayouts = new HashSet();

    public abstract void bindView(View view2, int i);

    public abstract void initialize(View view2, int i);

    public abstract void updateConvertView(View view2, int i);

    public SwipeItemMangerImpl(BaseAdapter baseAdapter) {
        if (baseAdapter == null) {
            throw new IllegalArgumentException("Adapter can not be null");
        }
        if (!(baseAdapter instanceof SwipeItemMangerInterface)) {
            throw new IllegalArgumentException("adapter should implement the SwipeAdapterInterface");
        }
        this.mBaseAdapter = baseAdapter;
    }

    public SwipeItemMangerImpl(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("Adapter can not be null");
        }
        if (!(adapter instanceof SwipeItemMangerInterface)) {
            throw new IllegalArgumentException("adapter should implement the SwipeAdapterInterface");
        }
        this.mRecyclerAdapter = adapter;
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public Attributes.Mode getMode() {
        return this.mode;
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void setMode(Attributes.Mode mode) {
        this.mode = mode;
        this.mOpenPositions.clear();
        this.mShownLayouts.clear();
        this.mOpenPosition = -1;
    }

    public int getSwipeLayoutId(int i) {
        SpinnerAdapter spinnerAdapter = this.mBaseAdapter;
        if (spinnerAdapter != null) {
            return ((SwipeAdapterInterface) spinnerAdapter).getSwipeLayoutResourceId(i);
        }
        Object obj = this.mRecyclerAdapter;
        if (obj != null) {
            return ((SwipeAdapterInterface) obj).getSwipeLayoutResourceId(i);
        }
        return -1;
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void openItem(int i) {
        if (this.mode == Attributes.Mode.Multiple) {
            if (!this.mOpenPositions.contains(Integer.valueOf(i))) {
                this.mOpenPositions.add(Integer.valueOf(i));
            }
        } else {
            this.mOpenPosition = i;
        }
        BaseAdapter baseAdapter = this.mBaseAdapter;
        if (baseAdapter != null) {
            baseAdapter.notifyDataSetChanged();
            return;
        }
        RecyclerView.Adapter adapter = this.mRecyclerAdapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeItem(int i) {
        if (this.mode == Attributes.Mode.Multiple) {
            this.mOpenPositions.remove(Integer.valueOf(i));
        } else if (this.mOpenPosition == i) {
            this.mOpenPosition = -1;
        }
        BaseAdapter baseAdapter = this.mBaseAdapter;
        if (baseAdapter != null) {
            baseAdapter.notifyDataSetChanged();
            return;
        }
        RecyclerView.Adapter adapter = this.mRecyclerAdapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeAllExcept(SwipeLayout swipeLayout) {
        for (SwipeLayout swipeLayout2 : this.mShownLayouts) {
            if (swipeLayout2 != swipeLayout) {
                swipeLayout2.close();
            }
        }
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeAllItems() {
        if (this.mode == Attributes.Mode.Multiple) {
            this.mOpenPositions.clear();
        } else {
            this.mOpenPosition = -1;
        }
        Iterator<SwipeLayout> it = this.mShownLayouts.iterator();
        while (it.hasNext()) {
            it.next().close();
        }
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void removeShownLayouts(SwipeLayout swipeLayout) {
        this.mShownLayouts.remove(swipeLayout);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public List<Integer> getOpenItems() {
        return this.mode == Attributes.Mode.Multiple ? new ArrayList(this.mOpenPositions) : Arrays.asList(Integer.valueOf(this.mOpenPosition));
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public List<SwipeLayout> getOpenLayouts() {
        return new ArrayList(this.mShownLayouts);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public boolean isOpen(int i) {
        if (this.mode == Attributes.Mode.Multiple) {
            return this.mOpenPositions.contains(Integer.valueOf(i));
        }
        return this.mOpenPosition == i;
    }

    class ValueBox {
        OnLayoutListener onLayoutListener;
        int position;
        SwipeMemory swipeMemory;

        ValueBox(int i, SwipeMemory swipeMemory, OnLayoutListener onLayoutListener) {
            this.swipeMemory = swipeMemory;
            this.onLayoutListener = onLayoutListener;
            this.position = i;
        }
    }

    class OnLayoutListener implements SwipeLayout.OnLayout {
        private int position;

        OnLayoutListener(int i) {
            this.position = i;
        }

        public void setPosition(int i) {
            this.position = i;
        }

        @Override // com.daimajia.swipe.SwipeLayout.OnLayout
        public void onLayout(SwipeLayout swipeLayout) {
            if (SwipeItemMangerImpl.this.isOpen(this.position)) {
                swipeLayout.open(false, false);
            } else {
                swipeLayout.close(false, false);
            }
        }
    }

    class SwipeMemory extends SimpleSwipeListener {
        private int position;

        SwipeMemory(int i) {
            this.position = i;
        }

        @Override // com.daimajia.swipe.SimpleSwipeListener, com.daimajia.swipe.SwipeLayout.SwipeListener
        public void onClose(SwipeLayout swipeLayout) {
            if (SwipeItemMangerImpl.this.mode == Attributes.Mode.Multiple) {
                SwipeItemMangerImpl.this.mOpenPositions.remove(Integer.valueOf(this.position));
            } else {
                SwipeItemMangerImpl.this.mOpenPosition = -1;
            }
        }

        @Override // com.daimajia.swipe.SimpleSwipeListener, com.daimajia.swipe.SwipeLayout.SwipeListener
        public void onStartOpen(SwipeLayout swipeLayout) {
            if (SwipeItemMangerImpl.this.mode == Attributes.Mode.Single) {
                SwipeItemMangerImpl.this.closeAllExcept(swipeLayout);
            }
        }

        @Override // com.daimajia.swipe.SimpleSwipeListener, com.daimajia.swipe.SwipeLayout.SwipeListener
        public void onOpen(SwipeLayout swipeLayout) {
            if (SwipeItemMangerImpl.this.mode == Attributes.Mode.Multiple) {
                SwipeItemMangerImpl.this.mOpenPositions.add(Integer.valueOf(this.position));
                return;
            }
            SwipeItemMangerImpl.this.closeAllExcept(swipeLayout);
            SwipeItemMangerImpl.this.mOpenPosition = this.position;
        }

        public void setPosition(int i) {
            this.position = i;
        }
    }
}
