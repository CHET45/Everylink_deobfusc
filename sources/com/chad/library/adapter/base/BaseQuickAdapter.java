package com.chad.library.adapter.base;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.aivox.base.common.Constant;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.chad.library.adapter.base.animation.SlideInBottomAnimation;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.chad.library.adapter.base.animation.SlideInRightAnimation;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseQuickAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K> {
    public static final int ALPHAIN = 1;
    public static final int EMPTY_VIEW = 1365;
    public static final int FOOTER_VIEW = 819;
    public static final int HEADER_VIEW = 273;
    public static final int LOADING_VIEW = 546;
    public static final int SCALEIN = 2;
    public static final int SLIDEIN_BOTTOM = 3;
    public static final int SLIDEIN_LEFT = 4;
    public static final int SLIDEIN_RIGHT = 5;
    protected static final String TAG = "BaseQuickAdapter";
    private boolean footerViewAsFlow;
    private boolean headerViewAsFlow;
    protected Context mContext;
    private BaseAnimation mCustomAnimation;
    protected List<T> mData;
    private int mDuration;
    private FrameLayout mEmptyLayout;
    private boolean mEnableLoadMoreEndClick;
    private boolean mFirstOnlyEnable;
    private boolean mFootAndEmptyEnable;
    private LinearLayout mFooterLayout;
    private boolean mHeadAndEmptyEnable;
    private LinearLayout mHeaderLayout;
    private Interpolator mInterpolator;
    private boolean mIsUseEmpty;
    private int mLastPosition;
    protected LayoutInflater mLayoutInflater;
    protected int mLayoutResId;
    private boolean mLoadMoreEnable;
    private LoadMoreView mLoadMoreView;
    private boolean mLoading;
    private MultiTypeDelegate<T> mMultiTypeDelegate;
    private boolean mNextLoadEnable;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private boolean mOpenAnimationEnable;
    private int mPreLoadNumber;
    private RecyclerView mRecyclerView;
    private RequestLoadMoreListener mRequestLoadMoreListener;
    private BaseAnimation mSelectAnimation;
    private SpanSizeLookup mSpanSizeLookup;
    private int mStartUpFetchPosition;
    private boolean mUpFetchEnable;
    private UpFetchListener mUpFetchListener;
    private boolean mUpFetching;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i);
    }

    public interface OnItemChildLongClickListener {
        boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i);
    }

    public interface OnItemClickListener {
        void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i);
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int i);
    }

    public interface UpFetchListener {
        void onUpFetch();
    }

    protected abstract void convert(K k, T t);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    protected boolean isFixedViewType(int i) {
        return i == 1365 || i == 273 || i == 819 || i == 546;
    }

    protected RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    private void checkNotNull() {
        if (getRecyclerView() == null) {
            throw new RuntimeException("please bind recyclerView first!");
        }
    }

    public void bindToRecyclerView(RecyclerView recyclerView) {
        if (getRecyclerView() != null) {
            throw new RuntimeException("Don't bind twice");
        }
        setRecyclerView(recyclerView);
        getRecyclerView().setAdapter(this);
    }

    @Deprecated
    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        openLoadMore(requestLoadMoreListener);
    }

    private void openLoadMore(RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
        this.mNextLoadEnable = true;
        this.mLoadMoreEnable = true;
        this.mLoading = false;
    }

    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener, RecyclerView recyclerView) {
        openLoadMore(requestLoadMoreListener);
        if (getRecyclerView() == null) {
            setRecyclerView(recyclerView);
        }
    }

    public void disableLoadMoreIfNotFullPage() {
        checkNotNull();
        disableLoadMoreIfNotFullPage(getRecyclerView());
    }

    public void disableLoadMoreIfNotFullPage(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager;
        setEnableLoadMore(false);
        if (recyclerView == null || (layoutManager = recyclerView.getLayoutManager()) == null) {
            return;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            recyclerView.postDelayed(new Runnable() { // from class: com.chad.library.adapter.base.BaseQuickAdapter.1
                @Override // java.lang.Runnable
                public void run() {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 != BaseQuickAdapter.this.getItemCount()) {
                        BaseQuickAdapter.this.setEnableLoadMore(true);
                    }
                }
            }, 50L);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            recyclerView.postDelayed(new Runnable() { // from class: com.chad.library.adapter.base.BaseQuickAdapter.2
                @Override // java.lang.Runnable
                public void run() {
                    int[] iArr = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(iArr);
                    if (BaseQuickAdapter.this.getTheBiggestNumber(iArr) + 1 != BaseQuickAdapter.this.getItemCount()) {
                        BaseQuickAdapter.this.setEnableLoadMore(true);
                    }
                }
            }, 50L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTheBiggestNumber(int[] iArr) {
        int i = -1;
        if (iArr != null && iArr.length != 0) {
            for (int i2 : iArr) {
                if (i2 > i) {
                    i = i2;
                }
            }
        }
        return i;
    }

    public void setUpFetchEnable(boolean z) {
        this.mUpFetchEnable = z;
    }

    public boolean isUpFetchEnable() {
        return this.mUpFetchEnable;
    }

    public void setStartUpFetchPosition(int i) {
        this.mStartUpFetchPosition = i;
    }

    private void autoUpFetch(int i) {
        UpFetchListener upFetchListener;
        if (!isUpFetchEnable() || isUpFetching() || i > this.mStartUpFetchPosition || (upFetchListener = this.mUpFetchListener) == null) {
            return;
        }
        upFetchListener.onUpFetch();
    }

    public boolean isUpFetching() {
        return this.mUpFetching;
    }

    public void setUpFetching(boolean z) {
        this.mUpFetching = z;
    }

    public void setUpFetchListener(UpFetchListener upFetchListener) {
        this.mUpFetchListener = upFetchListener;
    }

    public void setNotDoAnimationCount(int i) {
        this.mLastPosition = i;
    }

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        this.mLoadMoreView = loadMoreView;
    }

    public int getLoadMoreViewCount() {
        if (this.mRequestLoadMoreListener == null || !this.mLoadMoreEnable) {
            return 0;
        }
        return ((this.mNextLoadEnable || !this.mLoadMoreView.isLoadEndMoreGone()) && this.mData.size() != 0) ? 1 : 0;
    }

    public int getLoadMoreViewPosition() {
        return getHeaderLayoutCount() + this.mData.size() + getFooterLayoutCount();
    }

    public boolean isLoading() {
        return this.mLoading;
    }

    public void loadMoreEnd() {
        loadMoreEnd(false);
    }

    public void loadMoreEnd(boolean z) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        this.mLoading = false;
        this.mNextLoadEnable = false;
        this.mLoadMoreView.setLoadMoreEndGone(z);
        if (z) {
            notifyItemRemoved(getLoadMoreViewPosition());
        } else {
            this.mLoadMoreView.setLoadMoreStatus(4);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public void loadMoreComplete() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        this.mLoading = false;
        this.mNextLoadEnable = true;
        this.mLoadMoreView.setLoadMoreStatus(1);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    public void loadMoreFail() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        this.mLoading = false;
        this.mLoadMoreView.setLoadMoreStatus(3);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    public void setEnableLoadMore(boolean z) {
        int loadMoreViewCount = getLoadMoreViewCount();
        this.mLoadMoreEnable = z;
        int loadMoreViewCount2 = getLoadMoreViewCount();
        if (loadMoreViewCount == 1) {
            if (loadMoreViewCount2 == 0) {
                notifyItemRemoved(getLoadMoreViewPosition());
            }
        } else if (loadMoreViewCount2 == 1) {
            this.mLoadMoreView.setLoadMoreStatus(1);
            notifyItemInserted(getLoadMoreViewPosition());
        }
    }

    public boolean isLoadMoreEnable() {
        return this.mLoadMoreEnable;
    }

    public void setDuration(int i) {
        this.mDuration = i;
    }

    public BaseQuickAdapter(int i, List<T> list) {
        this.mNextLoadEnable = false;
        this.mLoadMoreEnable = false;
        this.mLoading = false;
        this.mLoadMoreView = new SimpleLoadMoreView();
        this.mEnableLoadMoreEndClick = false;
        this.mFirstOnlyEnable = true;
        this.mOpenAnimationEnable = false;
        this.mInterpolator = new LinearInterpolator();
        this.mDuration = Constant.EVENT.BLE_CONNECTED;
        this.mLastPosition = -1;
        this.mSelectAnimation = new AlphaInAnimation();
        this.mIsUseEmpty = true;
        this.mStartUpFetchPosition = 1;
        this.mPreLoadNumber = 1;
        this.mData = list == null ? new ArrayList<>() : list;
        if (i != 0) {
            this.mLayoutResId = i;
        }
    }

    public BaseQuickAdapter(List<T> list) {
        this(0, list);
    }

    public BaseQuickAdapter(int i) {
        this(i, null);
    }

    public void setNewData(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.mData = list;
        if (this.mRequestLoadMoreListener != null) {
            this.mNextLoadEnable = true;
            this.mLoadMoreEnable = true;
            this.mLoading = false;
            this.mLoadMoreView.setLoadMoreStatus(1);
        }
        this.mLastPosition = -1;
        notifyDataSetChanged();
    }

    @Deprecated
    public void add(int i, T t) {
        addData(i, t);
    }

    public void addData(int i, T t) {
        this.mData.add(i, t);
        notifyItemInserted(i + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public void addData(T t) {
        this.mData.add(t);
        notifyItemInserted(this.mData.size() + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public void remove(int i) {
        this.mData.remove(i);
        int headerLayoutCount = i + getHeaderLayoutCount();
        notifyItemRemoved(headerLayoutCount);
        compatibilityDataSizeChanged(0);
        notifyItemRangeChanged(headerLayoutCount, this.mData.size() - headerLayoutCount);
    }

    public void setData(int i, T t) {
        this.mData.set(i, t);
        notifyItemChanged(i + getHeaderLayoutCount());
    }

    public void addData(int i, Collection<? extends T> collection) {
        this.mData.addAll(i, collection);
        notifyItemRangeInserted(i + getHeaderLayoutCount(), collection.size());
        compatibilityDataSizeChanged(collection.size());
    }

    public void addData(Collection<? extends T> collection) {
        this.mData.addAll(collection);
        notifyItemRangeInserted((this.mData.size() - collection.size()) + getHeaderLayoutCount(), collection.size());
        compatibilityDataSizeChanged(collection.size());
    }

    public void replaceData(Collection<? extends T> collection) {
        List<T> list = this.mData;
        if (collection != list) {
            list.clear();
            this.mData.addAll(collection);
        }
        notifyDataSetChanged();
    }

    private void compatibilityDataSizeChanged(int i) {
        List<T> list = this.mData;
        if ((list == null ? 0 : list.size()) == i) {
            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return this.mData;
    }

    public T getItem(int i) {
        if (i < this.mData.size()) {
            return this.mData.get(i);
        }
        return null;
    }

    @Deprecated
    public int getHeaderViewsCount() {
        return getHeaderLayoutCount();
    }

    @Deprecated
    public int getFooterViewsCount() {
        return getFooterLayoutCount();
    }

    public int getHeaderLayoutCount() {
        LinearLayout linearLayout = this.mHeaderLayout;
        return (linearLayout == null || linearLayout.getChildCount() == 0) ? 0 : 1;
    }

    public int getFooterLayoutCount() {
        LinearLayout linearLayout = this.mFooterLayout;
        return (linearLayout == null || linearLayout.getChildCount() == 0) ? 0 : 1;
    }

    public int getEmptyViewCount() {
        FrameLayout frameLayout = this.mEmptyLayout;
        return (frameLayout == null || frameLayout.getChildCount() == 0 || !this.mIsUseEmpty || this.mData.size() != 0) ? 0 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int i = 1;
        if (getEmptyViewCount() != 1) {
            return getLoadMoreViewCount() + getHeaderLayoutCount() + this.mData.size() + getFooterLayoutCount();
        }
        if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
            i = 2;
        }
        return (!this.mFootAndEmptyEnable || getFooterLayoutCount() == 0) ? i : i + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (getEmptyViewCount() == 1) {
            boolean z = this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0;
            if (i != 0) {
                return i != 1 ? i != 2 ? EMPTY_VIEW : FOOTER_VIEW : z ? EMPTY_VIEW : FOOTER_VIEW;
            }
            if (z) {
                return 273;
            }
            return EMPTY_VIEW;
        }
        int headerLayoutCount = getHeaderLayoutCount();
        if (i < headerLayoutCount) {
            return 273;
        }
        int i2 = i - headerLayoutCount;
        int size = this.mData.size();
        if (i2 < size) {
            return getDefItemViewType(i2);
        }
        return i2 - size < getFooterLayoutCount() ? FOOTER_VIEW : LOADING_VIEW;
    }

    protected int getDefItemViewType(int i) {
        MultiTypeDelegate<T> multiTypeDelegate = this.mMultiTypeDelegate;
        if (multiTypeDelegate != null) {
            return multiTypeDelegate.getDefItemViewType(this.mData, i);
        }
        return super.getItemViewType(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public K onCreateViewHolder(ViewGroup viewGroup, int i) {
        K k;
        Context context = viewGroup.getContext();
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        if (i == 273) {
            k = (K) createBaseViewHolder(this.mHeaderLayout);
        } else if (i == 546) {
            k = (K) getLoadingView(viewGroup);
        } else if (i == 819) {
            k = (K) createBaseViewHolder(this.mFooterLayout);
        } else if (i == 1365) {
            k = (K) createBaseViewHolder(this.mEmptyLayout);
        } else {
            k = (K) onCreateDefViewHolder(viewGroup, i);
            bindViewClickListener(k);
        }
        k.setAdapter(this);
        return k;
    }

    private K getLoadingView(ViewGroup viewGroup) {
        K k = (K) createBaseViewHolder(getItemView(this.mLoadMoreView.getLayoutId(), viewGroup));
        k.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.BaseQuickAdapter.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (BaseQuickAdapter.this.mLoadMoreView.getLoadMoreStatus() == 3) {
                    BaseQuickAdapter.this.notifyLoadMoreToLoading();
                }
                if (BaseQuickAdapter.this.mEnableLoadMoreEndClick && BaseQuickAdapter.this.mLoadMoreView.getLoadMoreStatus() == 4) {
                    BaseQuickAdapter.this.notifyLoadMoreToLoading();
                }
            }
        });
        return k;
    }

    public void notifyLoadMoreToLoading() {
        if (this.mLoadMoreView.getLoadMoreStatus() == 2) {
            return;
        }
        this.mLoadMoreView.setLoadMoreStatus(1);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    public void enableLoadMoreEndClick(boolean z) {
        this.mEnableLoadMoreEndClick = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(K k) {
        super.onViewAttachedToWindow(k);
        int itemViewType = k.getItemViewType();
        if (itemViewType == 1365 || itemViewType == 273 || itemViewType == 819 || itemViewType == 546) {
            setFullSpan(k);
        } else {
            addAnimation(k);
        }
    }

    protected void setFullSpan(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams()).setFullSpan(true);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.chad.library.adapter.base.BaseQuickAdapter.4
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    int itemViewType = BaseQuickAdapter.this.getItemViewType(i);
                    if (itemViewType == 273 && BaseQuickAdapter.this.isHeaderViewAsFlow()) {
                        return 1;
                    }
                    if (itemViewType == 819 && BaseQuickAdapter.this.isFooterViewAsFlow()) {
                        return 1;
                    }
                    if (BaseQuickAdapter.this.mSpanSizeLookup != null) {
                        return BaseQuickAdapter.this.isFixedViewType(itemViewType) ? gridLayoutManager.getSpanCount() : BaseQuickAdapter.this.mSpanSizeLookup.getSpanSize(gridLayoutManager, i - BaseQuickAdapter.this.getHeaderLayoutCount());
                    }
                    if (BaseQuickAdapter.this.isFixedViewType(itemViewType)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    public void setHeaderViewAsFlow(boolean z) {
        this.headerViewAsFlow = z;
    }

    public boolean isHeaderViewAsFlow() {
        return this.headerViewAsFlow;
    }

    public void setFooterViewAsFlow(boolean z) {
        this.footerViewAsFlow = z;
    }

    public boolean isFooterViewAsFlow() {
        return this.footerViewAsFlow;
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(K k, int i) {
        autoUpFetch(i);
        autoLoadMore(i);
        int itemViewType = k.getItemViewType();
        if (itemViewType == 0) {
            convert(k, getItem(i - getHeaderLayoutCount()));
            return;
        }
        if (itemViewType != 273) {
            if (itemViewType == 546) {
                this.mLoadMoreView.convert(k);
            } else {
                if (itemViewType == 819 || itemViewType == 1365) {
                    return;
                }
                convert(k, getItem(i - getHeaderLayoutCount()));
            }
        }
    }

    private void bindViewClickListener(final BaseViewHolder baseViewHolder) {
        View view2;
        if (baseViewHolder == null || (view2 = baseViewHolder.itemView) == null) {
            return;
        }
        if (getOnItemClickListener() != null) {
            view2.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.BaseQuickAdapter.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view3) {
                    BaseQuickAdapter.this.getOnItemClickListener().onItemClick(BaseQuickAdapter.this, view3, baseViewHolder.getLayoutPosition() - BaseQuickAdapter.this.getHeaderLayoutCount());
                }
            });
        }
        if (getOnItemLongClickListener() != null) {
            view2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.BaseQuickAdapter.6
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view3) {
                    return BaseQuickAdapter.this.getOnItemLongClickListener().onItemLongClick(BaseQuickAdapter.this, view3, baseViewHolder.getLayoutPosition() - BaseQuickAdapter.this.getHeaderLayoutCount());
                }
            });
        }
    }

    public void setMultiTypeDelegate(MultiTypeDelegate<T> multiTypeDelegate) {
        this.mMultiTypeDelegate = multiTypeDelegate;
    }

    public MultiTypeDelegate<T> getMultiTypeDelegate() {
        return this.mMultiTypeDelegate;
    }

    protected K onCreateDefViewHolder(ViewGroup viewGroup, int i) {
        int layoutId = this.mLayoutResId;
        MultiTypeDelegate<T> multiTypeDelegate = this.mMultiTypeDelegate;
        if (multiTypeDelegate != null) {
            layoutId = multiTypeDelegate.getLayoutId(i);
        }
        return (K) createBaseViewHolder(viewGroup, layoutId);
    }

    protected K createBaseViewHolder(ViewGroup viewGroup, int i) {
        return (K) createBaseViewHolder(getItemView(i, viewGroup));
    }

    protected K createBaseViewHolder(View view2) {
        K k;
        Class instancedGenericKClass = null;
        for (Class<?> superclass = getClass(); instancedGenericKClass == null && superclass != null; superclass = superclass.getSuperclass()) {
            instancedGenericKClass = getInstancedGenericKClass(superclass);
        }
        if (instancedGenericKClass == null) {
            k = (K) new BaseViewHolder(view2);
        } else {
            k = (K) createGenericKInstance(instancedGenericKClass, view2);
        }
        return k != null ? k : (K) new BaseViewHolder(view2);
    }

    private K createGenericKInstance(Class cls, View view2) {
        try {
            if (cls.isMemberClass() && !Modifier.isStatic(cls.getModifiers())) {
                Constructor<T> declaredConstructor = cls.getDeclaredConstructor(getClass(), View.class);
                declaredConstructor.setAccessible(true);
                return (K) declaredConstructor.newInstance(this, view2);
            }
            Constructor<T> declaredConstructor2 = cls.getDeclaredConstructor(View.class);
            declaredConstructor2.setAccessible(true);
            return (K) declaredConstructor2.newInstance(view2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    private Class getInstancedGenericKClass(Class cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return null;
        }
        for (Type type : ((ParameterizedType) genericSuperclass).getActualTypeArguments()) {
            if (type instanceof Class) {
                Class cls2 = (Class) type;
                if (BaseViewHolder.class.isAssignableFrom(cls2)) {
                    return cls2;
                }
            }
        }
        return null;
    }

    public LinearLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    public LinearLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    public int addHeaderView(View view2) {
        return addHeaderView(view2, -1);
    }

    public int addHeaderView(View view2, int i) {
        return addHeaderView(view2, i, 1);
    }

    public int addHeaderView(View view2, int i, int i2) {
        int headerViewPosition;
        if (this.mHeaderLayout == null) {
            LinearLayout linearLayout = new LinearLayout(view2.getContext());
            this.mHeaderLayout = linearLayout;
            if (i2 == 1) {
                linearLayout.setOrientation(1);
                this.mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            } else {
                linearLayout.setOrientation(0);
                this.mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(-2, -1));
            }
        }
        int childCount = this.mHeaderLayout.getChildCount();
        if (i < 0 || i > childCount) {
            i = childCount;
        }
        this.mHeaderLayout.addView(view2, i);
        if (this.mHeaderLayout.getChildCount() == 1 && (headerViewPosition = getHeaderViewPosition()) != -1) {
            notifyItemInserted(headerViewPosition);
        }
        return i;
    }

    public int setHeaderView(View view2) {
        return setHeaderView(view2, 0, 1);
    }

    public int setHeaderView(View view2, int i) {
        return setHeaderView(view2, i, 1);
    }

    public int setHeaderView(View view2, int i, int i2) {
        LinearLayout linearLayout = this.mHeaderLayout;
        if (linearLayout == null || linearLayout.getChildCount() <= i) {
            return addHeaderView(view2, i, i2);
        }
        this.mHeaderLayout.removeViewAt(i);
        this.mHeaderLayout.addView(view2, i);
        return i;
    }

    public int addFooterView(View view2) {
        return addFooterView(view2, -1, 1);
    }

    public int addFooterView(View view2, int i) {
        return addFooterView(view2, i, 1);
    }

    public int addFooterView(View view2, int i, int i2) {
        int footerViewPosition;
        if (this.mFooterLayout == null) {
            LinearLayout linearLayout = new LinearLayout(view2.getContext());
            this.mFooterLayout = linearLayout;
            if (i2 == 1) {
                linearLayout.setOrientation(1);
                this.mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            } else {
                linearLayout.setOrientation(0);
                this.mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(-2, -1));
            }
        }
        int childCount = this.mFooterLayout.getChildCount();
        if (i < 0 || i > childCount) {
            i = childCount;
        }
        this.mFooterLayout.addView(view2, i);
        if (this.mFooterLayout.getChildCount() == 1 && (footerViewPosition = getFooterViewPosition()) != -1) {
            notifyItemInserted(footerViewPosition);
        }
        return i;
    }

    public int setFooterView(View view2) {
        return setFooterView(view2, 0, 1);
    }

    public int setFooterView(View view2, int i) {
        return setFooterView(view2, i, 1);
    }

    public int setFooterView(View view2, int i, int i2) {
        LinearLayout linearLayout = this.mFooterLayout;
        if (linearLayout == null || linearLayout.getChildCount() <= i) {
            return addFooterView(view2, i, i2);
        }
        this.mFooterLayout.removeViewAt(i);
        this.mFooterLayout.addView(view2, i);
        return i;
    }

    public void removeHeaderView(View view2) {
        int headerViewPosition;
        if (getHeaderLayoutCount() == 0) {
            return;
        }
        this.mHeaderLayout.removeView(view2);
        if (this.mHeaderLayout.getChildCount() != 0 || (headerViewPosition = getHeaderViewPosition()) == -1) {
            return;
        }
        notifyItemRemoved(headerViewPosition);
    }

    public void removeFooterView(View view2) {
        int footerViewPosition;
        if (getFooterLayoutCount() == 0) {
            return;
        }
        this.mFooterLayout.removeView(view2);
        if (this.mFooterLayout.getChildCount() != 0 || (footerViewPosition = getFooterViewPosition()) == -1) {
            return;
        }
        notifyItemRemoved(footerViewPosition);
    }

    public void removeAllHeaderView() {
        if (getHeaderLayoutCount() == 0) {
            return;
        }
        this.mHeaderLayout.removeAllViews();
        int headerViewPosition = getHeaderViewPosition();
        if (headerViewPosition != -1) {
            notifyItemRemoved(headerViewPosition);
        }
    }

    public void removeAllFooterView() {
        if (getFooterLayoutCount() == 0) {
            return;
        }
        this.mFooterLayout.removeAllViews();
        int footerViewPosition = getFooterViewPosition();
        if (footerViewPosition != -1) {
            notifyItemRemoved(footerViewPosition);
        }
    }

    private int getHeaderViewPosition() {
        return (getEmptyViewCount() != 1 || this.mHeadAndEmptyEnable) ? 0 : -1;
    }

    private int getFooterViewPosition() {
        int i = 1;
        if (getEmptyViewCount() == 1) {
            if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                i = 2;
            }
            if (this.mFootAndEmptyEnable) {
                return i;
            }
            return -1;
        }
        return getHeaderLayoutCount() + this.mData.size();
    }

    public void setEmptyView(int i, ViewGroup viewGroup) {
        setEmptyView(LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false));
    }

    public void setEmptyView(int i) {
        checkNotNull();
        setEmptyView(i, getRecyclerView());
    }

    public void setEmptyView(View view2) {
        boolean z;
        if (this.mEmptyLayout == null) {
            this.mEmptyLayout = new FrameLayout(view2.getContext());
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(-1, -1);
            ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
            if (layoutParams2 != null) {
                layoutParams.width = layoutParams2.width;
                layoutParams.height = layoutParams2.height;
            }
            this.mEmptyLayout.setLayoutParams(layoutParams);
            z = true;
        } else {
            z = false;
        }
        this.mEmptyLayout.removeAllViews();
        this.mEmptyLayout.addView(view2);
        this.mIsUseEmpty = true;
        if (z && getEmptyViewCount() == 1) {
            notifyItemInserted((!this.mHeadAndEmptyEnable || getHeaderLayoutCount() == 0) ? 0 : 1);
        }
    }

    public void setHeaderAndEmpty(boolean z) {
        setHeaderFooterEmpty(z, false);
    }

    public void setHeaderFooterEmpty(boolean z, boolean z2) {
        this.mHeadAndEmptyEnable = z;
        this.mFootAndEmptyEnable = z2;
    }

    public void isUseEmpty(boolean z) {
        this.mIsUseEmpty = z;
    }

    public View getEmptyView() {
        return this.mEmptyLayout;
    }

    @Deprecated
    public void setAutoLoadMoreSize(int i) {
        setPreLoadNumber(i);
    }

    public void setPreLoadNumber(int i) {
        if (i > 1) {
            this.mPreLoadNumber = i;
        }
    }

    private void autoLoadMore(int i) {
        if (getLoadMoreViewCount() != 0 && i >= getItemCount() - this.mPreLoadNumber && this.mLoadMoreView.getLoadMoreStatus() == 1) {
            this.mLoadMoreView.setLoadMoreStatus(2);
            if (this.mLoading) {
                return;
            }
            this.mLoading = true;
            if (getRecyclerView() != null) {
                getRecyclerView().post(new Runnable() { // from class: com.chad.library.adapter.base.BaseQuickAdapter.7
                    @Override // java.lang.Runnable
                    public void run() {
                        BaseQuickAdapter.this.mRequestLoadMoreListener.onLoadMoreRequested();
                    }
                });
            } else {
                this.mRequestLoadMoreListener.onLoadMoreRequested();
            }
        }
    }

    private void addAnimation(RecyclerView.ViewHolder viewHolder) {
        if (this.mOpenAnimationEnable) {
            if (!this.mFirstOnlyEnable || viewHolder.getLayoutPosition() > this.mLastPosition) {
                BaseAnimation baseAnimation = this.mCustomAnimation;
                if (baseAnimation == null) {
                    baseAnimation = this.mSelectAnimation;
                }
                for (Animator animator : baseAnimation.getAnimators(viewHolder.itemView)) {
                    startAnim(animator, viewHolder.getLayoutPosition());
                }
                this.mLastPosition = viewHolder.getLayoutPosition();
            }
        }
    }

    protected void startAnim(Animator animator, int i) {
        animator.setDuration(this.mDuration).start();
        animator.setInterpolator(this.mInterpolator);
    }

    protected View getItemView(int i, ViewGroup viewGroup) {
        return this.mLayoutInflater.inflate(i, viewGroup, false);
    }

    public void openLoadAnimation(int i) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = null;
        if (i == 1) {
            this.mSelectAnimation = new AlphaInAnimation();
            return;
        }
        if (i == 2) {
            this.mSelectAnimation = new ScaleInAnimation();
            return;
        }
        if (i == 3) {
            this.mSelectAnimation = new SlideInBottomAnimation();
        } else if (i == 4) {
            this.mSelectAnimation = new SlideInLeftAnimation();
        } else {
            if (i != 5) {
                return;
            }
            this.mSelectAnimation = new SlideInRightAnimation();
        }
    }

    public void openLoadAnimation(BaseAnimation baseAnimation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = baseAnimation;
    }

    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    public void isFirstOnly(boolean z) {
        this.mFirstOnlyEnable = z;
    }

    public View getViewByPosition(int i, int i2) {
        checkNotNull();
        return getViewByPosition(getRecyclerView(), i, i2);
    }

    public View getViewByPosition(RecyclerView recyclerView, int i, int i2) {
        BaseViewHolder baseViewHolder;
        if (recyclerView == null || (baseViewHolder = (BaseViewHolder) recyclerView.findViewHolderForLayoutPosition(i)) == null) {
            return null;
        }
        return baseViewHolder.getView(i2);
    }

    private int recursiveExpand(int i, List list) {
        int size = (i + list.size()) - 1;
        int size2 = list.size() - 1;
        int iRecursiveExpand = 0;
        while (size2 >= 0) {
            if (list.get(size2) instanceof IExpandable) {
                IExpandable iExpandable = (IExpandable) list.get(size2);
                if (iExpandable.isExpanded() && hasSubItems(iExpandable)) {
                    List<T> subItems = iExpandable.getSubItems();
                    int i2 = size + 1;
                    this.mData.addAll(i2, subItems);
                    iRecursiveExpand += recursiveExpand(i2, subItems);
                }
            }
            size2--;
            size--;
        }
        return iRecursiveExpand;
    }

    public int expand(int i, boolean z, boolean z2) {
        int headerLayoutCount = i - getHeaderLayoutCount();
        IExpandable expandableItem = getExpandableItem(headerLayoutCount);
        int size = 0;
        if (expandableItem == null) {
            return 0;
        }
        if (!hasSubItems(expandableItem)) {
            expandableItem.setExpanded(false);
            return 0;
        }
        if (!expandableItem.isExpanded()) {
            List<T> subItems = expandableItem.getSubItems();
            int i2 = headerLayoutCount + 1;
            this.mData.addAll(i2, subItems);
            int iRecursiveExpand = recursiveExpand(i2, subItems);
            expandableItem.setExpanded(true);
            size = iRecursiveExpand + subItems.size();
        }
        int headerLayoutCount2 = headerLayoutCount + getHeaderLayoutCount();
        if (z2) {
            if (z) {
                notifyItemChanged(headerLayoutCount2);
                notifyItemRangeInserted(headerLayoutCount2 + 1, size);
            } else {
                notifyDataSetChanged();
            }
        }
        return size;
    }

    public int expand(int i, boolean z) {
        return expand(i, z, true);
    }

    public int expand(int i) {
        return expand(i, true, true);
    }

    public int expandAll(int i, boolean z, boolean z2) {
        T item;
        int headerLayoutCount = i - getHeaderLayoutCount();
        int i2 = headerLayoutCount + 1;
        T item2 = i2 < this.mData.size() ? getItem(i2) : null;
        IExpandable expandableItem = getExpandableItem(headerLayoutCount);
        if (expandableItem == null || !hasSubItems(expandableItem)) {
            return 0;
        }
        int iExpand = expand(getHeaderLayoutCount() + headerLayoutCount, false, false);
        while (i2 < this.mData.size() && (item = getItem(i2)) != item2) {
            if (isExpandable(item)) {
                iExpand += expand(getHeaderLayoutCount() + i2, false, false);
            }
            i2++;
        }
        if (z2) {
            if (z) {
                notifyItemRangeInserted(headerLayoutCount + getHeaderLayoutCount() + 1, iExpand);
            } else {
                notifyDataSetChanged();
            }
        }
        return iExpand;
    }

    public int expandAll(int i, boolean z) {
        return expandAll(i, true, !z);
    }

    public void expandAll() {
        for (int size = (this.mData.size() - 1) + getHeaderLayoutCount(); size >= getHeaderLayoutCount(); size--) {
            expandAll(size, false, false);
        }
    }

    private int recursiveCollapse(int i) {
        T item = getItem(i);
        int iRecursiveCollapse = 0;
        if (!isExpandable(item)) {
            return 0;
        }
        IExpandable iExpandable = (IExpandable) item;
        if (iExpandable.isExpanded()) {
            List<T> subItems = iExpandable.getSubItems();
            for (int size = subItems.size() - 1; size >= 0; size--) {
                T t = subItems.get(size);
                int itemPosition = getItemPosition(t);
                if (itemPosition >= 0) {
                    if (t instanceof IExpandable) {
                        iRecursiveCollapse += recursiveCollapse(itemPosition);
                    }
                    this.mData.remove(itemPosition);
                    iRecursiveCollapse++;
                }
            }
        }
        return iRecursiveCollapse;
    }

    public int collapse(int i, boolean z, boolean z2) {
        int headerLayoutCount = i - getHeaderLayoutCount();
        IExpandable expandableItem = getExpandableItem(headerLayoutCount);
        if (expandableItem == null) {
            return 0;
        }
        int iRecursiveCollapse = recursiveCollapse(headerLayoutCount);
        expandableItem.setExpanded(false);
        int headerLayoutCount2 = headerLayoutCount + getHeaderLayoutCount();
        if (z2) {
            if (z) {
                notifyItemChanged(headerLayoutCount2);
                notifyItemRangeRemoved(headerLayoutCount2 + 1, iRecursiveCollapse);
            } else {
                notifyDataSetChanged();
            }
        }
        return iRecursiveCollapse;
    }

    public int collapse(int i) {
        return collapse(i, true, true);
    }

    public int collapse(int i, boolean z) {
        return collapse(i, z, true);
    }

    private int getItemPosition(T t) {
        List<T> list;
        if (t == null || (list = this.mData) == null || list.isEmpty()) {
            return -1;
        }
        return this.mData.indexOf(t);
    }

    private boolean hasSubItems(IExpandable iExpandable) {
        List<T> subItems;
        return (iExpandable == null || (subItems = iExpandable.getSubItems()) == null || subItems.size() <= 0) ? false : true;
    }

    public boolean isExpandable(T t) {
        return t != null && (t instanceof IExpandable);
    }

    private IExpandable getExpandableItem(int i) {
        T item = getItem(i);
        if (isExpandable(item)) {
            return (IExpandable) item;
        }
        return null;
    }

    public int getParentPosition(T t) {
        int itemPosition = getItemPosition(t);
        if (itemPosition == -1) {
            return -1;
        }
        int level = t instanceof IExpandable ? ((IExpandable) t).getLevel() : Integer.MAX_VALUE;
        if (level == 0) {
            return itemPosition;
        }
        if (level == -1) {
            return -1;
        }
        while (itemPosition >= 0) {
            T t2 = this.mData.get(itemPosition);
            if (t2 instanceof IExpandable) {
                IExpandable iExpandable = (IExpandable) t2;
                if (iExpandable.getLevel() >= 0 && iExpandable.getLevel() < level) {
                    return itemPosition;
                }
            }
            itemPosition--;
        }
        return -1;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        this.mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    public final OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    public final OnItemChildClickListener getOnItemChildClickListener() {
        return this.mOnItemChildClickListener;
    }

    public final OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return this.mOnItemChildLongClickListener;
    }
}
