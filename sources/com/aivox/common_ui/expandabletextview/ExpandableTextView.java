package com.aivox.common_ui.expandabletextview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.aivox.base.C0874R;
import com.aivox.base.util.DensityUtil;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableTextView extends LinearLayout implements View.OnClickListener {
    private static final int DEFAULT_ANIM_DURATION = 200;
    private static final int MAX_COLLAPSED_LINES = 5;
    private int collapseExpandTextColor;
    private float collapseExpandTextSize;
    private int contentTextColor;
    private float contentTextSize;
    private int drawableGrarity;
    private int grarity;
    private boolean mAnimating;
    private int mAnimationDuration;
    private Drawable mCollapseDrawable;
    private boolean mCollapsed;
    private int mCollapsedHeight;
    private SparseBooleanArray mCollapsedStatus;
    private Drawable mExpandDrawable;
    private OnExpandStateChangeListener mListener;
    private int mMarginBetweenTxtAndBottom;
    private int mMaxCollapsedLines;
    private int mPosition;
    private boolean mRelayout;
    private int mTextHeightWithMaxLines;
    protected AlignTextView mTvContent;
    protected TextView mTvExpandCollapse;
    private String textCollapse;
    private String textExpand;

    public interface OnExpandStateChangeListener {
        void onExpandStateChanged(TextView textView, boolean z);
    }

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCollapsed = true;
        init(attributeSet);
    }

    public ExpandableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCollapsed = true;
        init(attributeSet);
    }

    @Override // android.widget.LinearLayout
    public void setOrientation(int i) {
        if (i == 0) {
            throw new IllegalArgumentException("ExpandableTextView only supports Vertical Orientation.");
        }
        super.setOrientation(i);
    }

    private void init(AttributeSet attributeSet) {
        this.mCollapsedStatus = new SparseBooleanArray();
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C1034R.styleable.ExpandableTextView);
        this.mMaxCollapsedLines = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.ExpandableTextView_maxCollapsedLines, 5);
        this.mAnimationDuration = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.ExpandableTextView_animDuration, 200);
        this.mExpandDrawable = typedArrayObtainStyledAttributes.getDrawable(C1034R.styleable.ExpandableTextView_expandDrawable);
        this.mCollapseDrawable = typedArrayObtainStyledAttributes.getDrawable(C1034R.styleable.ExpandableTextView_collapseDrawable);
        this.textCollapse = typedArrayObtainStyledAttributes.getString(C1034R.styleable.ExpandableTextView_textCollapse);
        this.textExpand = typedArrayObtainStyledAttributes.getString(C1034R.styleable.ExpandableTextView_textExpand);
        if (this.mExpandDrawable == null) {
            this.mExpandDrawable = ContextCompat.getDrawable(getContext(), C1034R.mipmap.f268up);
        }
        if (this.mCollapseDrawable == null) {
            this.mCollapseDrawable = ContextCompat.getDrawable(getContext(), C1034R.mipmap.down);
        }
        if (TextUtils.isEmpty(this.textCollapse)) {
            this.textCollapse = "展开";
        }
        if (TextUtils.isEmpty(this.textExpand)) {
            this.textExpand = "收起";
        }
        this.contentTextColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.ExpandableTextView_contentTextColor, ContextCompat.getColor(getContext(), C0874R.color.gray));
        this.contentTextSize = typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.ExpandableTextView_contentTextSize, DensityUtil.sp2px(getContext(), 14.0f));
        this.collapseExpandTextColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.ExpandableTextView_collapseExpandTextColor, ContextCompat.getColor(getContext(), C0874R.color.colorPrimary));
        this.collapseExpandTextSize = typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.ExpandableTextView_collapseExpandTextSize, DensityUtil.sp2px(getContext(), 14.0f));
        this.grarity = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.ExpandableTextView_collapseExpandGrarity, 3);
        this.drawableGrarity = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.ExpandableTextView_drawableGrarity, 5);
        typedArrayObtainStyledAttributes.recycle();
        setOrientation(1);
        setVisibility(8);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViews();
    }

    private void findViews() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1034R.layout.item_expand_collapse, this);
        AlignTextView alignTextView = (AlignTextView) findViewById(C1034R.id.expandable_text);
        this.mTvContent = alignTextView;
        alignTextView.setOnClickListener(this);
        this.mTvExpandCollapse = (TextView) findViewById(C1034R.id.expand_collapse);
        setDrawbleAndText();
        this.mTvExpandCollapse.setOnClickListener(this);
        this.mTvContent.setTextColor(this.contentTextColor);
        this.mTvContent.getPaint().setTextSize(this.contentTextSize);
        this.mTvExpandCollapse.setTextColor(this.collapseExpandTextColor);
        this.mTvExpandCollapse.getPaint().setTextSize(this.collapseExpandTextSize);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = this.grarity;
        this.mTvExpandCollapse.setLayoutParams(layoutParams);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view2) {
        ValueAnimator valueAnimatorOfInt;
        if (this.mTvExpandCollapse.getVisibility() != 0) {
            return;
        }
        this.mCollapsed = !this.mCollapsed;
        setDrawbleAndText();
        SparseBooleanArray sparseBooleanArray = this.mCollapsedStatus;
        if (sparseBooleanArray != null) {
            sparseBooleanArray.put(this.mPosition, this.mCollapsed);
        }
        this.mAnimating = true;
        Log.i("tag", "mCollapsed1:" + this.mCollapsed + ";height:" + getHeight() + ":mCollapsedHeight:" + this.mCollapsedHeight);
        if (this.mCollapsed) {
            new ValueAnimator();
            valueAnimatorOfInt = ValueAnimator.ofInt(getHeight(), this.mCollapsedHeight);
        } else {
            this.mCollapsedHeight = getHeight();
            new ValueAnimator();
            valueAnimatorOfInt = ValueAnimator.ofInt(getHeight(), (getHeight() + this.mTextHeightWithMaxLines) - this.mTvContent.getHeight());
        }
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.aivox.common_ui.expandabletextview.ExpandableTextView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                ExpandableTextView.this.mTvContent.setMaxHeight(iIntValue - ExpandableTextView.this.mMarginBetweenTxtAndBottom);
                ExpandableTextView.this.getLayoutParams().height = iIntValue;
                ExpandableTextView.this.requestLayout();
            }
        });
        valueAnimatorOfInt.addListener(new Animator.AnimatorListener() { // from class: com.aivox.common_ui.expandabletextview.ExpandableTextView.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ExpandableTextView.this.mAnimating = false;
                if (ExpandableTextView.this.mListener != null) {
                    ExpandableTextView.this.mListener.onExpandStateChanged(ExpandableTextView.this.mTvContent, !ExpandableTextView.this.mCollapsed);
                }
            }
        });
        valueAnimatorOfInt.setDuration(this.mAnimationDuration);
        valueAnimatorOfInt.start();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mAnimating;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (!this.mRelayout || getVisibility() == 8) {
            super.onMeasure(i, i2);
            return;
        }
        this.mRelayout = false;
        this.mTvExpandCollapse.setVisibility(8);
        this.mTvContent.setMaxLines(Integer.MAX_VALUE);
        super.onMeasure(i, i2);
        int lineCount = this.mTvContent.getLineCount();
        int i3 = this.mMaxCollapsedLines;
        if (lineCount > i3 && i3 != 0) {
            this.mTextHeightWithMaxLines = getRealTextViewHeight(this.mTvContent);
            if (this.mCollapsed) {
                this.mTvContent.setMaxLines(this.mMaxCollapsedLines);
            }
            this.mTvExpandCollapse.setVisibility(0);
            super.onMeasure(i, i2);
            if (this.mCollapsed) {
                this.mTvContent.post(new Runnable() { // from class: com.aivox.common_ui.expandabletextview.ExpandableTextView.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ExpandableTextView expandableTextView = ExpandableTextView.this;
                        expandableTextView.mMarginBetweenTxtAndBottom = expandableTextView.getHeight() - ExpandableTextView.this.mTvContent.getHeight();
                    }
                });
                this.mCollapsedHeight = getMeasuredHeight();
            }
        }
    }

    private static int getRealTextViewHeight(TextView textView) {
        return textView.getLayout().getLineTop(textView.getLineCount()) + textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
    }

    private void setDrawbleAndText() {
        if (3 == this.drawableGrarity) {
            this.mTvExpandCollapse.setCompoundDrawablesWithIntrinsicBounds(this.mCollapsed ? this.mCollapseDrawable : this.mExpandDrawable, (Drawable) null, (Drawable) null, (Drawable) null);
        } else {
            this.mTvExpandCollapse.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.mCollapsed ? this.mCollapseDrawable : this.mExpandDrawable, (Drawable) null);
        }
        this.mTvExpandCollapse.setText(this.mCollapsed ? "收起" : "展开");
    }

    public void setOnExpandStateChangeListener(OnExpandStateChangeListener onExpandStateChangeListener) {
        this.mListener = onExpandStateChangeListener;
    }

    public void setText(CharSequence charSequence) {
        this.mRelayout = true;
        this.mTvContent.setText(charSequence);
        setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
    }

    public void setText(CharSequence charSequence, int i) {
        this.mPosition = i;
        this.mCollapsed = this.mCollapsedStatus.get(i, true);
        clearAnimation();
        setDrawbleAndText();
        this.mTvExpandCollapse.setText(this.mCollapsed ? "收起" : "展开");
        setText(charSequence);
        getLayoutParams().height = -2;
        requestLayout();
    }

    public CharSequence getText() {
        AlignTextView alignTextView = this.mTvContent;
        if (alignTextView == null) {
            return "";
        }
        return alignTextView.getText();
    }
}
