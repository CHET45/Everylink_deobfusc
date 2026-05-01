package com.aivox.common_ui.pulltorefresh.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.pulltorefresh.ILoadingLayout;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;

/* JADX INFO: loaded from: classes.dex */
public abstract class LoadingLayout extends FrameLayout implements ILoadingLayout {
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    static final String LOG_TAG = "PullToRefresh-LoadingLayout";
    protected final ImageView mHeaderImage;
    protected final ProgressBar mHeaderProgress;
    private final TextView mHeaderText;
    private FrameLayout mInnerLayout;
    protected final PullToRefreshBase.Mode mMode;
    private CharSequence mPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;
    protected final PullToRefreshBase.Orientation mScrollDirection;
    private final TextView mSubHeaderText;
    private boolean mUseIntrinsicAnimation;

    protected abstract int getDefaultDrawableResId();

    protected abstract void onLoadingDrawableSet(Drawable drawable);

    protected abstract void onPullImpl(float f);

    protected abstract void pullToRefreshImpl();

    protected abstract void refreshingImpl();

    protected abstract void releaseToRefreshImpl();

    protected abstract void resetImpl();

    public LoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation orientation, TypedArray typedArray) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        Drawable drawable;
        super(context);
        this.mMode = mode;
        this.mScrollDirection = orientation;
        if (C10621.f285x6b884803[orientation.ordinal()] == 1) {
            LayoutInflater.from(context).inflate(C1034R.layout.pull_to_refresh_header_horizontal, this);
        } else {
            LayoutInflater.from(context).inflate(C1034R.layout.pull_to_refresh_header_vertical, this);
        }
        FrameLayout frameLayout = (FrameLayout) findViewById(C1034R.id.fl_inner);
        this.mInnerLayout = frameLayout;
        this.mHeaderText = (TextView) frameLayout.findViewById(C1034R.id.text);
        this.mHeaderProgress = (ProgressBar) this.mInnerLayout.findViewById(C1034R.id.progress);
        this.mSubHeaderText = (TextView) this.mInnerLayout.findViewById(C1034R.id.pull_to_refresh_sub_text);
        this.mHeaderImage = (ImageView) this.mInnerLayout.findViewById(C1034R.id.pull_to_refresh_image);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mInnerLayout.getLayoutParams();
        if (C10621.f284x5281aad0[mode.ordinal()] == 1) {
            layoutParams.gravity = orientation == PullToRefreshBase.Orientation.VERTICAL ? 48 : 3;
            this.mPullLabel = context.getString(C1034R.string.pull_to_refresh_from_bottom_pull_label);
            this.mRefreshingLabel = context.getString(C1034R.string.pull_to_refresh_from_bottom_refreshing_label);
            this.mReleaseLabel = context.getString(C1034R.string.pull_to_refresh_from_bottom_release_label);
        } else {
            layoutParams.gravity = orientation == PullToRefreshBase.Orientation.VERTICAL ? 80 : 5;
            this.mPullLabel = context.getString(C1034R.string.pull_to_refresh_pull_label1);
            this.mRefreshingLabel = context.getString(C1034R.string.pull_to_refresh_refreshing_label1);
            this.mReleaseLabel = context.getString(C1034R.string.pull_to_refresh_release_label1);
        }
        if (typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrHeaderBackground) && (drawable = typedArray.getDrawable(C1034R.styleable.PullToRefresh_ptrHeaderBackground)) != null) {
            ViewCompat.setBackground(this, drawable);
        }
        if (typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrHeaderTextAppearance)) {
            TypedValue typedValue = new TypedValue();
            typedArray.getValue(C1034R.styleable.PullToRefresh_ptrHeaderTextAppearance, typedValue);
            setTextAppearance(typedValue.data);
        }
        if (typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrSubHeaderTextAppearance)) {
            TypedValue typedValue2 = new TypedValue();
            typedArray.getValue(C1034R.styleable.PullToRefresh_ptrSubHeaderTextAppearance, typedValue2);
            setSubTextAppearance(typedValue2.data);
        }
        if (typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrHeaderTextColor) && (colorStateList2 = typedArray.getColorStateList(C1034R.styleable.PullToRefresh_ptrHeaderTextColor)) != null) {
            setTextColor(colorStateList2);
        }
        if (typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrHeaderSubTextColor) && (colorStateList = typedArray.getColorStateList(C1034R.styleable.PullToRefresh_ptrHeaderSubTextColor)) != null) {
            setSubTextColor(colorStateList);
        }
        Drawable drawable2 = typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrDrawable) ? typedArray.getDrawable(C1034R.styleable.PullToRefresh_ptrDrawable) : null;
        if (C10621.f284x5281aad0[mode.ordinal()] != 1) {
            if (typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrDrawableStart)) {
                drawable2 = typedArray.getDrawable(C1034R.styleable.PullToRefresh_ptrDrawableStart);
            } else if (typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrDrawableTop)) {
                Utils.warnDeprecation("ptrDrawableTop", "ptrDrawableStart");
                drawable2 = typedArray.getDrawable(C1034R.styleable.PullToRefresh_ptrDrawableTop);
            }
        } else if (typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrDrawableEnd)) {
            drawable2 = typedArray.getDrawable(C1034R.styleable.PullToRefresh_ptrDrawableEnd);
        } else if (typedArray.hasValue(C1034R.styleable.PullToRefresh_ptrDrawableBottom)) {
            Utils.warnDeprecation("ptrDrawableBottom", "ptrDrawableEnd");
            drawable2 = typedArray.getDrawable(C1034R.styleable.PullToRefresh_ptrDrawableBottom);
        }
        setLoadingDrawable(drawable2 == null ? context.getResources().getDrawable(getDefaultDrawableResId()) : drawable2);
        reset();
    }

    /* JADX INFO: renamed from: com.aivox.common_ui.pulltorefresh.internal.LoadingLayout$1 */
    static /* synthetic */ class C10621 {

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$Mode */
        static final /* synthetic */ int[] f284x5281aad0;

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$Orientation */
        static final /* synthetic */ int[] f285x6b884803;

        static {
            int[] iArr = new int[PullToRefreshBase.Mode.values().length];
            f284x5281aad0 = iArr;
            try {
                iArr[PullToRefreshBase.Mode.PULL_FROM_END.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f284x5281aad0[PullToRefreshBase.Mode.PULL_FROM_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[PullToRefreshBase.Orientation.values().length];
            f285x6b884803 = iArr2;
            try {
                iArr2[PullToRefreshBase.Orientation.HORIZONTAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f285x6b884803[PullToRefreshBase.Orientation.VERTICAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public final void setHeight(int i) {
        getLayoutParams().height = i;
        requestLayout();
    }

    public final void setWidth(int i) {
        getLayoutParams().width = i;
        requestLayout();
    }

    public final int getContentSize() {
        if (C10621.f285x6b884803[this.mScrollDirection.ordinal()] == 1) {
            return this.mInnerLayout.getWidth();
        }
        return this.mInnerLayout.getHeight();
    }

    public final void hideAllViews() {
        if (this.mHeaderText.getVisibility() == 0) {
            this.mHeaderText.setVisibility(4);
        }
        if (this.mHeaderProgress.getVisibility() == 0) {
            this.mHeaderProgress.setVisibility(4);
        }
        if (this.mHeaderImage.getVisibility() == 0) {
            this.mHeaderImage.setVisibility(4);
        }
        if (this.mSubHeaderText.getVisibility() == 0) {
            this.mSubHeaderText.setVisibility(4);
        }
    }

    public final void onPull(float f) {
        if (this.mUseIntrinsicAnimation) {
            return;
        }
        onPullImpl(f);
    }

    public final void pullToRefresh() {
        TextView textView = this.mHeaderText;
        if (textView != null) {
            textView.setText(this.mPullLabel);
        }
        pullToRefreshImpl();
    }

    public final void refreshing() {
        TextView textView = this.mHeaderText;
        if (textView != null) {
            textView.setText(this.mRefreshingLabel);
        }
        if (this.mUseIntrinsicAnimation) {
            ((AnimationDrawable) this.mHeaderImage.getDrawable()).start();
        } else {
            refreshingImpl();
        }
        TextView textView2 = this.mSubHeaderText;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
    }

    public final void releaseToRefresh() {
        TextView textView = this.mHeaderText;
        if (textView != null) {
            textView.setText(this.mReleaseLabel);
        }
        releaseToRefreshImpl();
    }

    public final void reset() {
        TextView textView = this.mHeaderText;
        if (textView != null) {
            textView.setText(this.mPullLabel);
        }
        this.mHeaderImage.setVisibility(0);
        if (this.mUseIntrinsicAnimation) {
            ((AnimationDrawable) this.mHeaderImage.getDrawable()).stop();
        } else {
            resetImpl();
        }
        TextView textView2 = this.mSubHeaderText;
        if (textView2 != null) {
            if (TextUtils.isEmpty(textView2.getText())) {
                this.mSubHeaderText.setVisibility(8);
            } else {
                this.mSubHeaderText.setVisibility(0);
            }
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setLastUpdatedLabel(CharSequence charSequence) {
        setSubHeaderText(charSequence);
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public final void setLoadingDrawable(Drawable drawable) {
        this.mHeaderImage.setImageDrawable(drawable);
        this.mUseIntrinsicAnimation = drawable instanceof AnimationDrawable;
        onLoadingDrawableSet(drawable);
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setPullLabel(CharSequence charSequence) {
        this.mPullLabel = charSequence;
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setRefreshingLabel(CharSequence charSequence) {
        this.mRefreshingLabel = charSequence;
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setReleaseLabel(CharSequence charSequence) {
        this.mReleaseLabel = charSequence;
    }

    @Override // com.aivox.common_ui.pulltorefresh.ILoadingLayout
    public void setTextTypeface(Typeface typeface) {
        this.mHeaderText.setTypeface(typeface);
    }

    public final void showInvisibleViews() {
        if (4 == this.mHeaderText.getVisibility()) {
            this.mHeaderText.setVisibility(0);
        }
        if (4 == this.mHeaderProgress.getVisibility()) {
            this.mHeaderProgress.setVisibility(0);
        }
        if (4 == this.mHeaderImage.getVisibility()) {
            this.mHeaderImage.setVisibility(0);
        }
        if (4 == this.mSubHeaderText.getVisibility()) {
            this.mSubHeaderText.setVisibility(0);
        }
    }

    private void setSubHeaderText(CharSequence charSequence) {
        if (this.mSubHeaderText != null) {
            if (TextUtils.isEmpty(charSequence)) {
                this.mSubHeaderText.setVisibility(8);
                return;
            }
            this.mSubHeaderText.setText(charSequence);
            if (8 == this.mSubHeaderText.getVisibility()) {
                this.mSubHeaderText.setVisibility(0);
            }
        }
    }

    private void setSubTextAppearance(int i) {
        TextView textView = this.mSubHeaderText;
        if (textView != null) {
            textView.setTextAppearance(getContext(), i);
        }
    }

    private void setSubTextColor(ColorStateList colorStateList) {
        TextView textView = this.mSubHeaderText;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    private void setTextAppearance(int i) {
        TextView textView = this.mHeaderText;
        if (textView != null) {
            textView.setTextAppearance(getContext(), i);
        }
        TextView textView2 = this.mSubHeaderText;
        if (textView2 != null) {
            textView2.setTextAppearance(getContext(), i);
        }
    }

    private void setTextColor(ColorStateList colorStateList) {
        TextView textView = this.mHeaderText;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
        TextView textView2 = this.mSubHeaderText;
        if (textView2 != null) {
            textView2.setTextColor(colorStateList);
        }
    }
}
