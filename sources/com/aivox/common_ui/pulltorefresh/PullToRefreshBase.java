package com.aivox.common_ui.pulltorefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.pulltorefresh.internal.FlipLoadingLayout;
import com.aivox.common_ui.pulltorefresh.internal.LoadingLayout;
import com.aivox.common_ui.pulltorefresh.internal.RotateLoadingLayout;
import com.aivox.common_ui.pulltorefresh.internal.Utils;
import com.aivox.common_ui.pulltorefresh.internal.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
public abstract class PullToRefreshBase<T extends View> extends LinearLayout implements IPullToRefresh<T> {
    static final boolean DEBUG = false;
    static final int DEMO_SCROLL_INTERVAL = 225;
    static final float FRICTION = 2.0f;
    static final String LOG_TAG = "PullToRefresh";
    public static final int SMOOTH_SCROLL_DURATION_MS = 200;
    public static final int SMOOTH_SCROLL_LONG_DURATION_MS = 325;
    static final String STATE_CURRENT_MODE = "ptr_current_mode";
    static final String STATE_MODE = "ptr_mode";
    static final String STATE_SCROLLING_REFRESHING_ENABLED = "ptr_disable_scrolling";
    static final String STATE_SHOW_REFRESHING_VIEW = "ptr_show_refreshing_view";
    static final String STATE_STATE = "ptr_state";
    static final String STATE_SUPER = "ptr_super";
    static final boolean USE_HW_LAYERS = false;
    private Mode mCurrentMode;
    private PullToRefreshBase<T>.SmoothScrollRunnable mCurrentSmoothScrollRunnable;
    private boolean mFilterTouchEvents;
    private LoadingLayout mFooterLayout;
    private LoadingLayout mHeaderLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    private float mLastMotionX;
    private float mLastMotionY;
    private boolean mLayoutVisibilityChangesEnabled;
    private AnimationStyle mLoadingAnimationStyle;
    private Mode mMode;
    private OnPullEventListener<T> mOnPullEventListener;
    private OnRefreshListener<T> mOnRefreshListener;
    private OnRefreshListener2<T> mOnRefreshListener2;
    private boolean mOverScrollEnabled;
    T mRefreshableView;
    private FrameLayout mRefreshableViewWrapper;
    private Interpolator mScrollAnimationInterpolator;
    private boolean mScrollingWhileRefreshingEnabled;
    private boolean mShowViewWhileRefreshing;
    private State mState;
    private int mTouchSlop;

    public interface OnLastItemVisibleListener {
        void onLastItemVisible();
    }

    public interface OnPullEventListener<V extends View> {
        void onPullEvent(PullToRefreshBase<V> pullToRefreshBase, State state, Mode mode);
    }

    public interface OnRefreshListener<V extends View> {
        void onRefresh(PullToRefreshBase<V> pullToRefreshBase);
    }

    public interface OnRefreshListener2<V extends View> {
        void onPullDownToRefresh(PullToRefreshBase<V> pullToRefreshBase);

        void onPullUpToRefresh(PullToRefreshBase<V> pullToRefreshBase);
    }

    interface OnSmoothScrollFinishedListener {
        void onSmoothScrollFinished();
    }

    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }

    protected abstract T createRefreshableView(Context context, AttributeSet attributeSet);

    public abstract Orientation getPullToRefreshScrollDirection();

    protected int getPullToRefreshScrollDuration() {
        return 200;
    }

    protected int getPullToRefreshScrollDurationLonger() {
        return SMOOTH_SCROLL_LONG_DURATION_MS;
    }

    protected void handleStyledAttributes(TypedArray typedArray) {
    }

    protected abstract boolean isReadyForPullEnd();

    protected abstract boolean isReadyForPullStart();

    protected void onPtrRestoreInstanceState(Bundle bundle) {
    }

    protected void onPtrSaveInstanceState(Bundle bundle) {
    }

    public PullToRefreshBase(Context context) {
        super(context);
        this.mIsBeingDragged = false;
        this.mState = State.RESET;
        this.mMode = Mode.getDefault();
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = false;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = AnimationStyle.getDefault();
        init(context, null);
    }

    public PullToRefreshBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsBeingDragged = false;
        this.mState = State.RESET;
        this.mMode = Mode.getDefault();
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = false;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = AnimationStyle.getDefault();
        init(context, attributeSet);
    }

    public PullToRefreshBase(Context context, Mode mode) {
        super(context);
        this.mIsBeingDragged = false;
        this.mState = State.RESET;
        this.mMode = Mode.getDefault();
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = false;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = AnimationStyle.getDefault();
        this.mMode = mode;
        init(context, null);
    }

    public PullToRefreshBase(Context context, Mode mode, AnimationStyle animationStyle) {
        super(context);
        this.mIsBeingDragged = false;
        this.mState = State.RESET;
        this.mMode = Mode.getDefault();
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = false;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        AnimationStyle.getDefault();
        this.mMode = mode;
        this.mLoadingAnimationStyle = animationStyle;
        init(context, null);
    }

    @Override // android.view.ViewGroup
    public void addView(View view2, int i, ViewGroup.LayoutParams layoutParams) {
        View refreshableView = getRefreshableView();
        if (refreshableView instanceof ViewGroup) {
            ((ViewGroup) refreshableView).addView(view2, i, layoutParams);
            return;
        }
        throw new UnsupportedOperationException("Refreshable View is not a ViewGroup so can't addView");
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final boolean demo() {
        if (this.mMode.showHeaderLoadingLayout() && isReadyForPullStart()) {
            smoothScrollToAndBack((-getHeaderSize()) * 2);
            return true;
        }
        if (!this.mMode.showFooterLoadingLayout() || !isReadyForPullEnd()) {
            return false;
        }
        smoothScrollToAndBack(getFooterSize() * 2);
        return true;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final Mode getCurrentMode() {
        return this.mCurrentMode;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final boolean getFilterTouchEvents() {
        return this.mFilterTouchEvents;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final ILoadingLayout getLoadingLayoutProxy() {
        return getLoadingLayoutProxy(true, true);
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final ILoadingLayout getLoadingLayoutProxy(boolean z, boolean z2) {
        return createLoadingLayoutProxy(z, z2);
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final Mode getMode() {
        return this.mMode;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final T getRefreshableView() {
        return this.mRefreshableView;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final boolean getShowViewWhileRefreshing() {
        return this.mShowViewWhileRefreshing;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final State getState() {
        return this.mState;
    }

    public final boolean isDisableScrollingWhileRefreshing() {
        return !isScrollingWhileRefreshingEnabled();
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final boolean isPullToRefreshEnabled() {
        return this.mMode.permitsPullToRefresh();
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final boolean isPullToRefreshOverScrollEnabled() {
        return this.mOverScrollEnabled && OverscrollHelper.isAndroidOverScrollEnabled(this.mRefreshableView);
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final boolean isRefreshing() {
        return this.mState == State.REFRESHING || this.mState == State.MANUAL_REFRESHING;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final boolean isScrollingWhileRefreshingEnabled() {
        return this.mScrollingWhileRefreshingEnabled;
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        float f;
        float f2;
        if (!isPullToRefreshEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = false;
            return false;
        }
        if (action != 0 && this.mIsBeingDragged) {
            return true;
        }
        if (action != 0) {
            if (action == 2) {
                if (!this.mScrollingWhileRefreshingEnabled && isRefreshing()) {
                    return true;
                }
                if (isReadyForPull()) {
                    float y = motionEvent.getY();
                    float x = motionEvent.getX();
                    if (C10584.f279x6b884803[getPullToRefreshScrollDirection().ordinal()] == 1) {
                        f = x - this.mLastMotionX;
                        f2 = y - this.mLastMotionY;
                    } else {
                        f = y - this.mLastMotionY;
                        f2 = x - this.mLastMotionX;
                    }
                    float fAbs = Math.abs(f);
                    if (fAbs > this.mTouchSlop && (!this.mFilterTouchEvents || fAbs > Math.abs(f2))) {
                        if (this.mMode.showHeaderLoadingLayout() && f >= 1.0f && isReadyForPullStart()) {
                            this.mLastMotionY = y;
                            this.mLastMotionX = x;
                            this.mIsBeingDragged = true;
                            if (this.mMode == Mode.BOTH) {
                                this.mCurrentMode = Mode.PULL_FROM_START;
                            }
                        } else if (this.mMode.showFooterLoadingLayout() && f <= -1.0f && isReadyForPullEnd()) {
                            this.mLastMotionY = y;
                            this.mLastMotionX = x;
                            this.mIsBeingDragged = true;
                            if (this.mMode == Mode.BOTH) {
                                this.mCurrentMode = Mode.PULL_FROM_END;
                            }
                        }
                    }
                }
            }
        } else if (isReadyForPull()) {
            float y2 = motionEvent.getY();
            this.mInitialMotionY = y2;
            this.mLastMotionY = y2;
            float x2 = motionEvent.getX();
            this.mInitialMotionX = x2;
            this.mLastMotionX = x2;
            this.mIsBeingDragged = false;
        }
        return this.mIsBeingDragged;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final void onRefreshComplete() {
        if (isRefreshing()) {
            setState(State.RESET, new boolean[0]);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0044  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.isPullToRefreshEnabled()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            boolean r0 = r4.mScrollingWhileRefreshingEnabled
            r2 = 1
            if (r0 != 0) goto L14
            boolean r0 = r4.isRefreshing()
            if (r0 == 0) goto L14
            return r2
        L14:
            int r0 = r5.getAction()
            if (r0 != 0) goto L21
            int r0 = r5.getEdgeFlags()
            if (r0 == 0) goto L21
            return r1
        L21:
            int r0 = r5.getAction()
            if (r0 == 0) goto L74
            if (r0 == r2) goto L44
            r3 = 2
            if (r0 == r3) goto L30
            r5 = 3
            if (r0 == r5) goto L44
            goto L8b
        L30:
            boolean r0 = r4.mIsBeingDragged
            if (r0 == 0) goto L8b
            float r0 = r5.getY()
            r4.mLastMotionY = r0
            float r5 = r5.getX()
            r4.mLastMotionX = r5
            r4.pullEvent()
            return r2
        L44:
            boolean r5 = r4.mIsBeingDragged
            if (r5 == 0) goto L8b
            r4.mIsBeingDragged = r1
            com.aivox.common_ui.pulltorefresh.PullToRefreshBase$State r5 = r4.mState
            com.aivox.common_ui.pulltorefresh.PullToRefreshBase$State r0 = com.aivox.common_ui.pulltorefresh.PullToRefreshBase.State.RELEASE_TO_REFRESH
            if (r5 != r0) goto L62
            com.aivox.common_ui.pulltorefresh.PullToRefreshBase$OnRefreshListener<T extends android.view.View> r5 = r4.mOnRefreshListener
            if (r5 != 0) goto L58
            com.aivox.common_ui.pulltorefresh.PullToRefreshBase$OnRefreshListener2<T extends android.view.View> r5 = r4.mOnRefreshListener2
            if (r5 == 0) goto L62
        L58:
            com.aivox.common_ui.pulltorefresh.PullToRefreshBase$State r5 = com.aivox.common_ui.pulltorefresh.PullToRefreshBase.State.REFRESHING
            boolean[] r0 = new boolean[r2]
            r0[r1] = r2
            r4.setState(r5, r0)
            return r2
        L62:
            boolean r5 = r4.isRefreshing()
            if (r5 == 0) goto L6c
            r4.smoothScrollTo(r1)
            return r2
        L6c:
            com.aivox.common_ui.pulltorefresh.PullToRefreshBase$State r5 = com.aivox.common_ui.pulltorefresh.PullToRefreshBase.State.RESET
            boolean[] r0 = new boolean[r1]
            r4.setState(r5, r0)
            return r2
        L74:
            boolean r0 = r4.isReadyForPull()
            if (r0 == 0) goto L8b
            float r0 = r5.getY()
            r4.mInitialMotionY = r0
            r4.mLastMotionY = r0
            float r5 = r5.getX()
            r4.mInitialMotionX = r5
            r4.mLastMotionX = r5
            return r2
        L8b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common_ui.pulltorefresh.PullToRefreshBase.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final void setScrollingWhileRefreshingEnabled(boolean z) {
        this.mScrollingWhileRefreshingEnabled = z;
    }

    public void setDisableScrollingWhileRefreshing(boolean z) {
        setScrollingWhileRefreshingEnabled(!z);
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final void setFilterTouchEvents(boolean z) {
        this.mFilterTouchEvents = z;
    }

    public void setLastUpdatedLabel(CharSequence charSequence) {
        getLoadingLayoutProxy().setLastUpdatedLabel(charSequence);
    }

    public void setLoadingDrawable(Drawable drawable) {
        getLoadingLayoutProxy().setLoadingDrawable(drawable);
    }

    public void setLoadingDrawable(Drawable drawable, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setLoadingDrawable(drawable);
    }

    @Override // android.view.View
    public void setLongClickable(boolean z) {
        getRefreshableView().setLongClickable(z);
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final void setMode(Mode mode) {
        if (mode != this.mMode) {
            this.mMode = mode;
            updateUIForMode();
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public void setOnPullEventListener(OnPullEventListener<T> onPullEventListener) {
        this.mOnPullEventListener = onPullEventListener;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final void setOnRefreshListener(OnRefreshListener<T> onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
        this.mOnRefreshListener2 = null;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final void setOnRefreshListener(OnRefreshListener2<T> onRefreshListener2) {
        this.mOnRefreshListener2 = onRefreshListener2;
        this.mOnRefreshListener = null;
    }

    public void setPullLabel(CharSequence charSequence) {
        getLoadingLayoutProxy().setPullLabel(charSequence);
    }

    public void setPullLabel(CharSequence charSequence, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setPullLabel(charSequence);
    }

    public final void setPullToRefreshEnabled(boolean z) {
        setMode(z ? Mode.getDefault() : Mode.DISABLED);
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final void setPullToRefreshOverScrollEnabled(boolean z) {
        this.mOverScrollEnabled = z;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final void setRefreshing() {
        setRefreshing(true);
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final void setRefreshing(boolean z) {
        if (isRefreshing()) {
            return;
        }
        setState(State.MANUAL_REFRESHING, z);
    }

    public void setRefreshingLabel(CharSequence charSequence) {
        getLoadingLayoutProxy().setRefreshingLabel(charSequence);
    }

    public void setRefreshingLabel(CharSequence charSequence, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setRefreshingLabel(charSequence);
    }

    public void setReleaseLabel(CharSequence charSequence) {
        setReleaseLabel(charSequence, Mode.BOTH);
    }

    public void setReleaseLabel(CharSequence charSequence, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setReleaseLabel(charSequence);
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public void setScrollAnimationInterpolator(Interpolator interpolator) {
        this.mScrollAnimationInterpolator = interpolator;
    }

    @Override // com.aivox.common_ui.pulltorefresh.IPullToRefresh
    public final void setShowViewWhileRefreshing(boolean z) {
        this.mShowViewWhileRefreshing = z;
    }

    final void setState(State state, boolean... zArr) {
        this.mState = state;
        int i = C10584.f280xfe0a7904[this.mState.ordinal()];
        if (i == 1) {
            onReset();
        } else if (i == 2) {
            onPullToRefresh();
        } else if (i == 3) {
            onReleaseToRefresh();
        } else if (i == 4 || i == 5) {
            onRefreshing(zArr[0]);
        }
        OnPullEventListener<T> onPullEventListener = this.mOnPullEventListener;
        if (onPullEventListener != null) {
            onPullEventListener.onPullEvent(this, this.mState, this.mCurrentMode);
        }
    }

    protected final void addViewInternal(View view2, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view2, i, layoutParams);
    }

    protected final void addViewInternal(View view2, ViewGroup.LayoutParams layoutParams) {
        super.addView(view2, -1, layoutParams);
    }

    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray typedArray) {
        LoadingLayout loadingLayoutCreateLoadingLayout = this.mLoadingAnimationStyle.createLoadingLayout(context, mode, getPullToRefreshScrollDirection(), typedArray);
        loadingLayoutCreateLoadingLayout.setVisibility(4);
        return loadingLayoutCreateLoadingLayout;
    }

    protected LoadingLayoutProxy createLoadingLayoutProxy(boolean z, boolean z2) {
        LoadingLayoutProxy loadingLayoutProxy = new LoadingLayoutProxy();
        if (z && this.mMode.showHeaderLoadingLayout()) {
            loadingLayoutProxy.addLayout(this.mHeaderLayout);
        }
        if (z2 && this.mMode.showFooterLoadingLayout()) {
            loadingLayoutProxy.addLayout(this.mFooterLayout);
        }
        return loadingLayoutProxy;
    }

    protected final void disableLoadingLayoutVisibilityChanges() {
        this.mLayoutVisibilityChangesEnabled = false;
    }

    protected final LoadingLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    protected final int getFooterSize() {
        return this.mFooterLayout.getContentSize();
    }

    protected final LoadingLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    protected final int getHeaderSize() {
        return this.mHeaderLayout.getContentSize();
    }

    protected FrameLayout getRefreshableViewWrapper() {
        return this.mRefreshableViewWrapper;
    }

    protected void onPullToRefresh() {
        int i = C10584.f278x5281aad0[this.mCurrentMode.ordinal()];
        if (i == 1) {
            this.mFooterLayout.pullToRefresh();
        } else {
            if (i != 2) {
                return;
            }
            this.mHeaderLayout.pullToRefresh();
        }
    }

    protected void onRefreshing(boolean z) {
        if (this.mMode.showHeaderLoadingLayout()) {
            this.mHeaderLayout.refreshing();
        }
        if (this.mMode.showFooterLoadingLayout()) {
            this.mFooterLayout.refreshing();
        }
        if (z) {
            if (this.mShowViewWhileRefreshing) {
                OnSmoothScrollFinishedListener onSmoothScrollFinishedListener = new OnSmoothScrollFinishedListener() { // from class: com.aivox.common_ui.pulltorefresh.PullToRefreshBase.1
                    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase.OnSmoothScrollFinishedListener
                    public void onSmoothScrollFinished() {
                        PullToRefreshBase.this.callRefreshListener();
                    }
                };
                int i = C10584.f278x5281aad0[this.mCurrentMode.ordinal()];
                if (i == 1 || i == 3) {
                    smoothScrollTo(getFooterSize(), onSmoothScrollFinishedListener);
                    return;
                } else {
                    smoothScrollTo(-getHeaderSize(), onSmoothScrollFinishedListener);
                    return;
                }
            }
            smoothScrollTo(0);
            return;
        }
        callRefreshListener();
    }

    protected void onReleaseToRefresh() {
        int i = C10584.f278x5281aad0[this.mCurrentMode.ordinal()];
        if (i == 1) {
            this.mFooterLayout.releaseToRefresh();
        } else {
            if (i != 2) {
                return;
            }
            this.mHeaderLayout.releaseToRefresh();
        }
    }

    protected void onReset() {
        this.mIsBeingDragged = false;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mHeaderLayout.reset();
        this.mFooterLayout.reset();
        smoothScrollTo(0);
    }

    @Override // android.view.View
    protected final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            setMode(Mode.mapIntToValue(bundle.getInt(STATE_MODE, 0)));
            this.mCurrentMode = Mode.mapIntToValue(bundle.getInt(STATE_CURRENT_MODE, 0));
            this.mScrollingWhileRefreshingEnabled = bundle.getBoolean(STATE_SCROLLING_REFRESHING_ENABLED, false);
            this.mShowViewWhileRefreshing = bundle.getBoolean(STATE_SHOW_REFRESHING_VIEW, true);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
            State stateMapIntToValue = State.mapIntToValue(bundle.getInt(STATE_STATE, 0));
            if (stateMapIntToValue == State.REFRESHING || stateMapIntToValue == State.MANUAL_REFRESHING) {
                setState(stateMapIntToValue, true);
            }
            onPtrRestoreInstanceState(bundle);
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override // android.view.View
    protected final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        onPtrSaveInstanceState(bundle);
        bundle.putInt(STATE_STATE, this.mState.getIntValue());
        bundle.putInt(STATE_MODE, this.mMode.getIntValue());
        bundle.putInt(STATE_CURRENT_MODE, this.mCurrentMode.getIntValue());
        bundle.putBoolean(STATE_SCROLLING_REFRESHING_ENABLED, this.mScrollingWhileRefreshingEnabled);
        bundle.putBoolean(STATE_SHOW_REFRESHING_VIEW, this.mShowViewWhileRefreshing);
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        return bundle;
    }

    @Override // android.view.View
    protected final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        refreshLoadingViewsSize();
        refreshRefreshableViewSize(i, i2);
        post(new Runnable() { // from class: com.aivox.common_ui.pulltorefresh.PullToRefreshBase.2
            @Override // java.lang.Runnable
            public void run() {
                PullToRefreshBase.this.requestLayout();
            }
        });
    }

    protected final void refreshLoadingViewsSize() {
        int maximumPullScroll = (int) (getMaximumPullScroll() * 1.2f);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int i = C10584.f279x6b884803[getPullToRefreshScrollDirection().ordinal()];
        if (i == 1) {
            if (this.mMode.showHeaderLoadingLayout()) {
                this.mHeaderLayout.setWidth(maximumPullScroll);
                paddingLeft = -maximumPullScroll;
            } else {
                paddingLeft = 0;
            }
            if (this.mMode.showFooterLoadingLayout()) {
                this.mFooterLayout.setWidth(maximumPullScroll);
                paddingRight = -maximumPullScroll;
            } else {
                paddingRight = 0;
            }
        } else if (i == 2) {
            if (this.mMode.showHeaderLoadingLayout()) {
                this.mHeaderLayout.setHeight(maximumPullScroll);
                paddingTop = -maximumPullScroll;
            } else {
                paddingTop = 0;
            }
            if (this.mMode.showFooterLoadingLayout()) {
                this.mFooterLayout.setHeight(maximumPullScroll);
                paddingBottom = -maximumPullScroll;
            } else {
                paddingBottom = 0;
            }
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    protected final void refreshRefreshableViewSize(int i, int i2) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mRefreshableViewWrapper.getLayoutParams();
        int i3 = C10584.f279x6b884803[getPullToRefreshScrollDirection().ordinal()];
        if (i3 == 1) {
            if (layoutParams.width != i) {
                layoutParams.width = i;
                this.mRefreshableViewWrapper.requestLayout();
                return;
            }
            return;
        }
        if (i3 == 2 && layoutParams.height != i2) {
            layoutParams.height = i2;
            this.mRefreshableViewWrapper.requestLayout();
        }
    }

    protected final void setHeaderScroll(int i) {
        int maximumPullScroll = getMaximumPullScroll();
        int iMin = Math.min(maximumPullScroll, Math.max(-maximumPullScroll, i));
        if (this.mLayoutVisibilityChangesEnabled) {
            if (iMin < 0) {
                this.mHeaderLayout.setVisibility(0);
            } else if (iMin > 0) {
                this.mFooterLayout.setVisibility(0);
            } else {
                this.mHeaderLayout.setVisibility(4);
                this.mFooterLayout.setVisibility(4);
            }
        }
        int i2 = C10584.f279x6b884803[getPullToRefreshScrollDirection().ordinal()];
        if (i2 == 1) {
            scrollTo(iMin, 0);
        } else {
            if (i2 != 2) {
                return;
            }
            scrollTo(0, iMin);
        }
    }

    protected final void smoothScrollTo(int i) {
        smoothScrollTo(i, getPullToRefreshScrollDuration());
    }

    protected final void smoothScrollTo(int i, OnSmoothScrollFinishedListener onSmoothScrollFinishedListener) {
        smoothScrollTo(i, getPullToRefreshScrollDuration(), 0L, onSmoothScrollFinishedListener);
    }

    protected final void smoothScrollToLonger(int i) {
        smoothScrollTo(i, getPullToRefreshScrollDurationLonger());
    }

    protected void updateUIForMode() {
        LinearLayout.LayoutParams loadingLayoutLayoutParams = getLoadingLayoutLayoutParams();
        if (this == this.mHeaderLayout.getParent()) {
            removeView(this.mHeaderLayout);
        }
        if (this.mMode.showHeaderLoadingLayout()) {
            addViewInternal(this.mHeaderLayout, 0, loadingLayoutLayoutParams);
        }
        if (this == this.mFooterLayout.getParent()) {
            removeView(this.mFooterLayout);
        }
        if (this.mMode.showFooterLoadingLayout()) {
            addViewInternal(this.mFooterLayout, loadingLayoutLayoutParams);
        }
        refreshLoadingViewsSize();
        this.mCurrentMode = this.mMode != Mode.BOTH ? this.mMode : Mode.PULL_FROM_START;
    }

    private void addRefreshableView(Context context, T t) {
        FrameLayout frameLayout = new FrameLayout(context);
        this.mRefreshableViewWrapper = frameLayout;
        frameLayout.addView(t, -1, -1);
        addViewInternal(this.mRefreshableViewWrapper, new LinearLayout.LayoutParams(-1, -1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callRefreshListener() {
        OnRefreshListener<T> onRefreshListener = this.mOnRefreshListener;
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh(this);
            return;
        }
        if (this.mOnRefreshListener2 != null) {
            if (this.mCurrentMode == Mode.PULL_FROM_START) {
                this.mOnRefreshListener2.onPullDownToRefresh(this);
            } else if (this.mCurrentMode == Mode.PULL_FROM_END) {
                this.mOnRefreshListener2.onPullUpToRefresh(this);
            }
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (C10584.f279x6b884803[getPullToRefreshScrollDirection().ordinal()] == 1) {
            setOrientation(0);
        } else {
            setOrientation(1);
        }
        setGravity(17);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.PullToRefresh);
        if (typedArrayObtainStyledAttributes.hasValue(C1034R.styleable.PullToRefresh_ptrMode)) {
            this.mMode = Mode.mapIntToValue(typedArrayObtainStyledAttributes.getInteger(C1034R.styleable.PullToRefresh_ptrMode, 0));
        }
        if (typedArrayObtainStyledAttributes.hasValue(C1034R.styleable.PullToRefresh_ptrAnimationStyle)) {
            this.mLoadingAnimationStyle = AnimationStyle.mapIntToValue(typedArrayObtainStyledAttributes.getInteger(C1034R.styleable.PullToRefresh_ptrAnimationStyle, 0));
        }
        T t = (T) createRefreshableView(context, attributeSet);
        this.mRefreshableView = t;
        addRefreshableView(context, t);
        this.mHeaderLayout = createLoadingLayout(context, Mode.PULL_FROM_START, typedArrayObtainStyledAttributes);
        this.mFooterLayout = createLoadingLayout(context, Mode.PULL_FROM_END, typedArrayObtainStyledAttributes);
        if (typedArrayObtainStyledAttributes.hasValue(C1034R.styleable.PullToRefresh_ptrRefreshableViewBackground)) {
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(C1034R.styleable.PullToRefresh_ptrRefreshableViewBackground);
            if (drawable != null) {
                this.mRefreshableView.setBackgroundDrawable(drawable);
            }
        } else if (typedArrayObtainStyledAttributes.hasValue(C1034R.styleable.PullToRefresh_ptrAdapterViewBackground)) {
            Utils.warnDeprecation("ptrAdapterViewBackground", "ptrRefreshableViewBackground");
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(C1034R.styleable.PullToRefresh_ptrAdapterViewBackground);
            if (drawable2 != null) {
                this.mRefreshableView.setBackgroundDrawable(drawable2);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(C1034R.styleable.PullToRefresh_ptrOverScroll)) {
            this.mOverScrollEnabled = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.PullToRefresh_ptrOverScroll, true);
        }
        if (typedArrayObtainStyledAttributes.hasValue(C1034R.styleable.PullToRefresh_ptrScrollingWhileRefreshingEnabled)) {
            this.mScrollingWhileRefreshingEnabled = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.PullToRefresh_ptrScrollingWhileRefreshingEnabled, false);
        }
        handleStyledAttributes(typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
        updateUIForMode();
    }

    private boolean isReadyForPull() {
        int i = C10584.f278x5281aad0[this.mMode.ordinal()];
        if (i == 1) {
            return isReadyForPullEnd();
        }
        if (i == 2) {
            return isReadyForPullStart();
        }
        if (i != 4) {
            return false;
        }
        return isReadyForPullEnd() || isReadyForPullStart();
    }

    private void pullEvent() {
        float f;
        float f2;
        int iRound;
        int footerSize;
        if (C10584.f279x6b884803[getPullToRefreshScrollDirection().ordinal()] == 1) {
            f = this.mInitialMotionX;
            f2 = this.mLastMotionX;
        } else {
            f = this.mInitialMotionY;
            f2 = this.mLastMotionY;
        }
        if (C10584.f278x5281aad0[this.mCurrentMode.ordinal()] == 1) {
            iRound = Math.round(Math.max(f - f2, 0.0f) / 2.0f);
            footerSize = getFooterSize();
        } else {
            iRound = Math.round(Math.min(f - f2, 0.0f) / 2.0f);
            footerSize = getHeaderSize();
        }
        setHeaderScroll(iRound);
        if (iRound == 0 || isRefreshing()) {
            return;
        }
        float fAbs = Math.abs(iRound) / footerSize;
        if (C10584.f278x5281aad0[this.mCurrentMode.ordinal()] == 1) {
            this.mFooterLayout.onPull(fAbs);
        } else {
            this.mHeaderLayout.onPull(fAbs);
        }
        if (this.mState != State.PULL_TO_REFRESH && footerSize >= Math.abs(iRound)) {
            setState(State.PULL_TO_REFRESH, new boolean[0]);
        } else {
            if (this.mState != State.PULL_TO_REFRESH || footerSize >= Math.abs(iRound)) {
                return;
            }
            setState(State.RELEASE_TO_REFRESH, new boolean[0]);
        }
    }

    private LinearLayout.LayoutParams getLoadingLayoutLayoutParams() {
        if (C10584.f279x6b884803[getPullToRefreshScrollDirection().ordinal()] == 1) {
            return new LinearLayout.LayoutParams(-2, -1);
        }
        return new LinearLayout.LayoutParams(-1, -2);
    }

    private int getMaximumPullScroll() {
        if (C10584.f279x6b884803[getPullToRefreshScrollDirection().ordinal()] == 1) {
            return Math.round(getWidth() / 2.0f);
        }
        return Math.round(getHeight() / 2.0f);
    }

    private final void smoothScrollTo(int i, long j) {
        smoothScrollTo(i, j, 0L, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void smoothScrollTo(int i, long j, long j2, OnSmoothScrollFinishedListener onSmoothScrollFinishedListener) {
        int scrollX;
        PullToRefreshBase<T>.SmoothScrollRunnable smoothScrollRunnable = this.mCurrentSmoothScrollRunnable;
        if (smoothScrollRunnable != null) {
            smoothScrollRunnable.stop();
        }
        if (C10584.f279x6b884803[getPullToRefreshScrollDirection().ordinal()] == 1) {
            scrollX = getScrollX();
        } else {
            scrollX = getScrollY();
        }
        int i2 = scrollX;
        if (i2 != i) {
            if (this.mScrollAnimationInterpolator == null) {
                this.mScrollAnimationInterpolator = new DecelerateInterpolator();
            }
            PullToRefreshBase<T>.SmoothScrollRunnable smoothScrollRunnable2 = new SmoothScrollRunnable(i2, i, j, onSmoothScrollFinishedListener);
            this.mCurrentSmoothScrollRunnable = smoothScrollRunnable2;
            if (j2 > 0) {
                postDelayed(smoothScrollRunnable2, j2);
            } else {
                post(smoothScrollRunnable2);
            }
        }
    }

    private final void smoothScrollToAndBack(int i) {
        smoothScrollTo(i, 200L, 0L, new OnSmoothScrollFinishedListener() { // from class: com.aivox.common_ui.pulltorefresh.PullToRefreshBase.3
            @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase.OnSmoothScrollFinishedListener
            public void onSmoothScrollFinished() {
                PullToRefreshBase.this.smoothScrollTo(0, 200L, 225L, null);
            }
        });
    }

    public enum AnimationStyle {
        ROTATE,
        FLIP;

        static AnimationStyle getDefault() {
            return ROTATE;
        }

        static AnimationStyle mapIntToValue(int i) {
            if (i != 1) {
                return ROTATE;
            }
            return FLIP;
        }

        LoadingLayout createLoadingLayout(Context context, Mode mode, Orientation orientation, TypedArray typedArray) {
            if (C10584.f277x10fe417a[ordinal()] != 2) {
                return new RotateLoadingLayout(context, mode, orientation, typedArray);
            }
            return new FlipLoadingLayout(context, mode, orientation, typedArray);
        }
    }

    /* JADX INFO: renamed from: com.aivox.common_ui.pulltorefresh.PullToRefreshBase$4 */
    static /* synthetic */ class C10584 {

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$AnimationStyle */
        static final /* synthetic */ int[] f277x10fe417a;

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$Mode */
        static final /* synthetic */ int[] f278x5281aad0;

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$Orientation */
        static final /* synthetic */ int[] f279x6b884803;

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$State */
        static final /* synthetic */ int[] f280xfe0a7904;

        static {
            int[] iArr = new int[AnimationStyle.values().length];
            f277x10fe417a = iArr;
            try {
                iArr[AnimationStyle.ROTATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f277x10fe417a[AnimationStyle.FLIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[Mode.values().length];
            f278x5281aad0 = iArr2;
            try {
                iArr2[Mode.PULL_FROM_END.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f278x5281aad0[Mode.PULL_FROM_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f278x5281aad0[Mode.MANUAL_REFRESH_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f278x5281aad0[Mode.BOTH.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr3 = new int[State.values().length];
            f280xfe0a7904 = iArr3;
            try {
                iArr3[State.RESET.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f280xfe0a7904[State.PULL_TO_REFRESH.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f280xfe0a7904[State.RELEASE_TO_REFRESH.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f280xfe0a7904[State.REFRESHING.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f280xfe0a7904[State.MANUAL_REFRESHING.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f280xfe0a7904[State.OVERSCROLLING.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
            int[] iArr4 = new int[Orientation.values().length];
            f279x6b884803 = iArr4;
            try {
                iArr4[Orientation.HORIZONTAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f279x6b884803[Orientation.VERTICAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v1 com.aivox.common_ui.pulltorefresh.PullToRefreshBase$Mode, still in use, count: 1, list:
  (r0v1 com.aivox.common_ui.pulltorefresh.PullToRefreshBase$Mode) from 0x0038: SPUT (r0v1 com.aivox.common_ui.pulltorefresh.PullToRefreshBase$Mode) (LINE:1373) com.aivox.common_ui.pulltorefresh.PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH com.aivox.common_ui.pulltorefresh.PullToRefreshBase$Mode
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
    	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
    	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class Mode {
        DISABLED(0),
        PULL_FROM_START(1),
        PULL_FROM_END(2),
        BOTH(3),
        MANUAL_REFRESH_ONLY(4);

        private int mIntValue;
        public static Mode PULL_DOWN_TO_REFRESH = new Mode(1);
        public static Mode PULL_UP_TO_REFRESH = new Mode(2);

        public static Mode valueOf(String str) {
            return (Mode) Enum.valueOf(Mode.class, str);
        }

        public static Mode[] values() {
            return (Mode[]) $VALUES.clone();
        }

        static {
        }

        static Mode mapIntToValue(int i) {
            for (Mode mode : values()) {
                if (i == mode.getIntValue()) {
                    return mode;
                }
            }
            return getDefault();
        }

        static Mode getDefault() {
            return PULL_FROM_START;
        }

        private Mode(int i) {
            this.mIntValue = i;
        }

        boolean permitsPullToRefresh() {
            return (this == DISABLED || this == MANUAL_REFRESH_ONLY) ? false : true;
        }

        public boolean showHeaderLoadingLayout() {
            return this == PULL_FROM_START || this == BOTH;
        }

        public boolean showFooterLoadingLayout() {
            return this == PULL_FROM_END || this == BOTH || this == MANUAL_REFRESH_ONLY;
        }

        int getIntValue() {
            return this.mIntValue;
        }
    }

    public enum State {
        RESET(0),
        PULL_TO_REFRESH(1),
        RELEASE_TO_REFRESH(2),
        REFRESHING(8),
        MANUAL_REFRESHING(9),
        OVERSCROLLING(16);

        private int mIntValue;

        static State mapIntToValue(int i) {
            for (State state : values()) {
                if (i == state.getIntValue()) {
                    return state;
                }
            }
            return RESET;
        }

        State(int i) {
            this.mIntValue = i;
        }

        int getIntValue() {
            return this.mIntValue;
        }
    }

    final class SmoothScrollRunnable implements Runnable {
        private final long mDuration;
        private final Interpolator mInterpolator;
        private OnSmoothScrollFinishedListener mListener;
        private final int mScrollFromY;
        private final int mScrollToY;
        private boolean mContinueRunning = true;
        private long mStartTime = -1;
        private int mCurrentY = -1;

        public SmoothScrollRunnable(int i, int i2, long j, OnSmoothScrollFinishedListener onSmoothScrollFinishedListener) {
            this.mScrollFromY = i;
            this.mScrollToY = i2;
            this.mInterpolator = PullToRefreshBase.this.mScrollAnimationInterpolator;
            this.mDuration = j;
            this.mListener = onSmoothScrollFinishedListener;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mStartTime == -1) {
                this.mStartTime = System.currentTimeMillis();
            } else {
                int iRound = this.mScrollFromY - Math.round((this.mScrollFromY - this.mScrollToY) * this.mInterpolator.getInterpolation(Math.max(Math.min(((System.currentTimeMillis() - this.mStartTime) * 1000) / this.mDuration, 1000L), 0L) / 1000.0f));
                this.mCurrentY = iRound;
                PullToRefreshBase.this.setHeaderScroll(iRound);
            }
            if (this.mContinueRunning && this.mScrollToY != this.mCurrentY) {
                ViewCompat.postOnAnimation(PullToRefreshBase.this, this);
                return;
            }
            OnSmoothScrollFinishedListener onSmoothScrollFinishedListener = this.mListener;
            if (onSmoothScrollFinishedListener != null) {
                onSmoothScrollFinishedListener.onSmoothScrollFinished();
            }
        }

        public void stop() {
            this.mContinueRunning = false;
            PullToRefreshBase.this.removeCallbacks(this);
        }
    }
}
