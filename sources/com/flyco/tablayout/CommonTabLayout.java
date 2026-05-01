package com.flyco.tablayout;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class CommonTabLayout extends FrameLayout implements ValueAnimator.AnimatorUpdateListener {
    private static final int STYLE_BLOCK = 2;
    private static final int STYLE_NORMAL = 0;
    private static final int STYLE_TRIANGLE = 1;
    private static final int TEXT_BOLD_BOTH = 2;
    private static final int TEXT_BOLD_NONE = 0;
    private static final int TEXT_BOLD_WHEN_SELECT = 1;
    private Context mContext;
    private IndicatorPoint mCurrentP;
    private int mCurrentTab;
    private int mDividerColor;
    private float mDividerPadding;
    private Paint mDividerPaint;
    private float mDividerWidth;
    private FragmentChangeManager mFragmentChangeManager;
    private int mHeight;
    private int mIconGravity;
    private float mIconHeight;
    private float mIconMargin;
    private boolean mIconVisible;
    private float mIconWidth;
    private long mIndicatorAnimDuration;
    private boolean mIndicatorAnimEnable;
    private boolean mIndicatorBounceEnable;
    private int mIndicatorColor;
    private float mIndicatorCornerRadius;
    private GradientDrawable mIndicatorDrawable;
    private int mIndicatorGravity;
    private float mIndicatorHeight;
    private float mIndicatorMarginBottom;
    private float mIndicatorMarginLeft;
    private float mIndicatorMarginRight;
    private float mIndicatorMarginTop;
    private Rect mIndicatorRect;
    private int mIndicatorStyle;
    private float mIndicatorWidth;
    private SparseArray<Boolean> mInitSetMap;
    private OvershootInterpolator mInterpolator;
    private boolean mIsFirstDraw;
    private IndicatorPoint mLastP;
    private int mLastTab;
    private OnTabSelectListener mListener;
    private Paint mRectPaint;
    private int mTabCount;
    private ArrayList<CustomTabEntity> mTabEntitys;
    private float mTabPadding;
    private boolean mTabSpaceEqual;
    private float mTabWidth;
    private LinearLayout mTabsContainer;
    private boolean mTextAllCaps;
    private int mTextBold;
    private Paint mTextPaint;
    private int mTextSelectColor;
    private int mTextUnselectColor;
    private float mTextsize;
    private Paint mTrianglePaint;
    private Path mTrianglePath;
    private int mUnderlineColor;
    private int mUnderlineGravity;
    private float mUnderlineHeight;
    private ValueAnimator mValueAnimator;

    public CommonTabLayout(Context context) {
        this(context, null, 0);
    }

    public CommonTabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CommonTabLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTabEntitys = new ArrayList<>();
        this.mIndicatorRect = new Rect();
        this.mIndicatorDrawable = new GradientDrawable();
        this.mRectPaint = new Paint(1);
        this.mDividerPaint = new Paint(1);
        this.mTrianglePaint = new Paint(1);
        this.mTrianglePath = new Path();
        this.mIndicatorStyle = 0;
        this.mInterpolator = new OvershootInterpolator(1.5f);
        this.mIsFirstDraw = true;
        this.mTextPaint = new Paint(1);
        this.mInitSetMap = new SparseArray<>();
        this.mCurrentP = new IndicatorPoint();
        this.mLastP = new IndicatorPoint();
        setWillNotDraw(false);
        setClipChildren(false);
        setClipToPadding(false);
        this.mContext = context;
        LinearLayout linearLayout = new LinearLayout(context);
        this.mTabsContainer = linearLayout;
        addView(linearLayout);
        obtainAttributes(context, attributeSet);
        String attributeValue = attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
        if (!attributeValue.equals("-1") && !attributeValue.equals("-2")) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{android.R.attr.layout_height});
            this.mHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, -2);
            typedArrayObtainStyledAttributes.recycle();
        }
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(new PointEvaluator(), this.mLastP, this.mCurrentP);
        this.mValueAnimator = valueAnimatorOfObject;
        valueAnimatorOfObject.addUpdateListener(this);
    }

    private void obtainAttributes(Context context, AttributeSet attributeSet) {
        float f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1485R.styleable.CommonTabLayout);
        this.mIndicatorStyle = typedArrayObtainStyledAttributes.getInt(C1485R.styleable.CommonTabLayout_tl_indicator_style, 0);
        this.mIndicatorColor = typedArrayObtainStyledAttributes.getColor(C1485R.styleable.CommonTabLayout_tl_indicator_color, Color.parseColor(this.mIndicatorStyle == 2 ? "#4B6A87" : "#ffffff"));
        int i = C1485R.styleable.CommonTabLayout_tl_indicator_height;
        int i2 = this.mIndicatorStyle;
        if (i2 == 1) {
            f = 4.0f;
        } else {
            f = i2 == 2 ? -1 : 2;
        }
        this.mIndicatorHeight = typedArrayObtainStyledAttributes.getDimension(i, dp2px(f));
        this.mIndicatorWidth = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_indicator_width, dp2px(this.mIndicatorStyle == 1 ? 10.0f : -1.0f));
        this.mIndicatorCornerRadius = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_indicator_corner_radius, dp2px(this.mIndicatorStyle == 2 ? -1.0f : 0.0f));
        this.mIndicatorMarginLeft = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_indicator_margin_left, dp2px(0.0f));
        this.mIndicatorMarginTop = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_indicator_margin_top, dp2px(this.mIndicatorStyle == 2 ? 7.0f : 0.0f));
        this.mIndicatorMarginRight = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_indicator_margin_right, dp2px(0.0f));
        this.mIndicatorMarginBottom = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_indicator_margin_bottom, dp2px(this.mIndicatorStyle != 2 ? 0.0f : 7.0f));
        this.mIndicatorAnimEnable = typedArrayObtainStyledAttributes.getBoolean(C1485R.styleable.CommonTabLayout_tl_indicator_anim_enable, true);
        this.mIndicatorBounceEnable = typedArrayObtainStyledAttributes.getBoolean(C1485R.styleable.CommonTabLayout_tl_indicator_bounce_enable, true);
        this.mIndicatorAnimDuration = typedArrayObtainStyledAttributes.getInt(C1485R.styleable.CommonTabLayout_tl_indicator_anim_duration, -1);
        this.mIndicatorGravity = typedArrayObtainStyledAttributes.getInt(C1485R.styleable.CommonTabLayout_tl_indicator_gravity, 80);
        this.mUnderlineColor = typedArrayObtainStyledAttributes.getColor(C1485R.styleable.CommonTabLayout_tl_underline_color, Color.parseColor("#ffffff"));
        this.mUnderlineHeight = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_underline_height, dp2px(0.0f));
        this.mUnderlineGravity = typedArrayObtainStyledAttributes.getInt(C1485R.styleable.CommonTabLayout_tl_underline_gravity, 80);
        this.mDividerColor = typedArrayObtainStyledAttributes.getColor(C1485R.styleable.CommonTabLayout_tl_divider_color, Color.parseColor("#ffffff"));
        this.mDividerWidth = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_divider_width, dp2px(0.0f));
        this.mDividerPadding = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_divider_padding, dp2px(12.0f));
        this.mTextsize = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_textsize, sp2px(13.0f));
        this.mTextSelectColor = typedArrayObtainStyledAttributes.getColor(C1485R.styleable.CommonTabLayout_tl_textSelectColor, Color.parseColor("#ffffff"));
        this.mTextUnselectColor = typedArrayObtainStyledAttributes.getColor(C1485R.styleable.CommonTabLayout_tl_textUnselectColor, Color.parseColor("#AAffffff"));
        this.mTextBold = typedArrayObtainStyledAttributes.getInt(C1485R.styleable.CommonTabLayout_tl_textBold, 0);
        this.mTextAllCaps = typedArrayObtainStyledAttributes.getBoolean(C1485R.styleable.CommonTabLayout_tl_textAllCaps, false);
        this.mIconVisible = typedArrayObtainStyledAttributes.getBoolean(C1485R.styleable.CommonTabLayout_tl_iconVisible, true);
        this.mIconGravity = typedArrayObtainStyledAttributes.getInt(C1485R.styleable.CommonTabLayout_tl_iconGravity, 48);
        this.mIconWidth = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_iconWidth, dp2px(0.0f));
        this.mIconHeight = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_iconHeight, dp2px(0.0f));
        this.mIconMargin = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_iconMargin, dp2px(2.5f));
        this.mTabSpaceEqual = typedArrayObtainStyledAttributes.getBoolean(C1485R.styleable.CommonTabLayout_tl_tab_space_equal, true);
        this.mTabWidth = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_tab_width, dp2px(-1.0f));
        this.mTabPadding = typedArrayObtainStyledAttributes.getDimension(C1485R.styleable.CommonTabLayout_tl_tab_padding, (this.mTabSpaceEqual || this.mTabWidth > 0.0f) ? dp2px(0.0f) : dp2px(10.0f));
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setTabData(ArrayList<CustomTabEntity> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            throw new IllegalStateException("TabEntitys can not be NULL or EMPTY !");
        }
        this.mTabEntitys.clear();
        this.mTabEntitys.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void setTabData(ArrayList<CustomTabEntity> arrayList, FragmentActivity fragmentActivity, int i, ArrayList<Fragment> arrayList2) {
        this.mFragmentChangeManager = new FragmentChangeManager(fragmentActivity.getSupportFragmentManager(), i, arrayList2);
        setTabData(arrayList);
    }

    public void notifyDataSetChanged() {
        View viewInflate;
        this.mTabsContainer.removeAllViews();
        this.mTabCount = this.mTabEntitys.size();
        for (int i = 0; i < this.mTabCount; i++) {
            int i2 = this.mIconGravity;
            if (i2 == 3) {
                viewInflate = View.inflate(this.mContext, C1485R.layout.layout_tab_left, null);
            } else if (i2 == 5) {
                viewInflate = View.inflate(this.mContext, C1485R.layout.layout_tab_right, null);
            } else if (i2 == 80) {
                viewInflate = View.inflate(this.mContext, C1485R.layout.layout_tab_bottom, null);
            } else {
                viewInflate = View.inflate(this.mContext, C1485R.layout.layout_tab_top, null);
            }
            viewInflate.setTag(Integer.valueOf(i));
            addTab(i, viewInflate);
        }
        updateTabStyles();
    }

    private void addTab(int i, View view2) {
        ((TextView) view2.findViewById(C1485R.id.tv_tab_title)).setText(this.mTabEntitys.get(i).getTabTitle());
        ((ImageView) view2.findViewById(C1485R.id.iv_tab_icon)).setImageResource(this.mTabEntitys.get(i).getTabUnselectedIcon());
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.flyco.tablayout.CommonTabLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view3) {
                int iIntValue = ((Integer) view3.getTag()).intValue();
                if (CommonTabLayout.this.mCurrentTab == iIntValue) {
                    if (CommonTabLayout.this.mListener != null) {
                        CommonTabLayout.this.mListener.onTabReselect(iIntValue);
                    }
                } else {
                    CommonTabLayout.this.setCurrentTab(iIntValue);
                    if (CommonTabLayout.this.mListener != null) {
                        CommonTabLayout.this.mListener.onTabSelect(iIntValue);
                    }
                }
            }
        });
        LinearLayout.LayoutParams layoutParams = this.mTabSpaceEqual ? new LinearLayout.LayoutParams(0, -1, 1.0f) : new LinearLayout.LayoutParams(-2, -1);
        if (this.mTabWidth > 0.0f) {
            layoutParams = new LinearLayout.LayoutParams((int) this.mTabWidth, -1);
        }
        this.mTabsContainer.addView(view2, i, layoutParams);
    }

    private void updateTabStyles() {
        int i = 0;
        while (i < this.mTabCount) {
            View childAt = this.mTabsContainer.getChildAt(i);
            float f = this.mTabPadding;
            childAt.setPadding((int) f, 0, (int) f, 0);
            TextView textView = (TextView) childAt.findViewById(C1485R.id.tv_tab_title);
            textView.setTextColor(i == this.mCurrentTab ? this.mTextSelectColor : this.mTextUnselectColor);
            textView.setTextSize(0, this.mTextsize);
            if (this.mTextAllCaps) {
                textView.setText(textView.getText().toString().toUpperCase());
            }
            int i2 = this.mTextBold;
            if (i2 == 2) {
                textView.getPaint().setFakeBoldText(true);
            } else if (i2 == 0) {
                textView.getPaint().setFakeBoldText(false);
            }
            ImageView imageView = (ImageView) childAt.findViewById(C1485R.id.iv_tab_icon);
            if (this.mIconVisible) {
                imageView.setVisibility(0);
                CustomTabEntity customTabEntity = this.mTabEntitys.get(i);
                imageView.setImageResource(i == this.mCurrentTab ? customTabEntity.getTabSelectedIcon() : customTabEntity.getTabUnselectedIcon());
                float f2 = this.mIconWidth;
                int i3 = f2 <= 0.0f ? -2 : (int) f2;
                float f3 = this.mIconHeight;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i3, f3 > 0.0f ? (int) f3 : -2);
                int i4 = this.mIconGravity;
                if (i4 == 3) {
                    layoutParams.rightMargin = (int) this.mIconMargin;
                } else if (i4 == 5) {
                    layoutParams.leftMargin = (int) this.mIconMargin;
                } else if (i4 == 80) {
                    layoutParams.topMargin = (int) this.mIconMargin;
                } else {
                    layoutParams.bottomMargin = (int) this.mIconMargin;
                }
                imageView.setLayoutParams(layoutParams);
            } else {
                imageView.setVisibility(8);
            }
            i++;
        }
    }

    private void updateTabSelection(int i) {
        int i2 = 0;
        while (i2 < this.mTabCount) {
            View childAt = this.mTabsContainer.getChildAt(i2);
            boolean z = i2 == i;
            TextView textView = (TextView) childAt.findViewById(C1485R.id.tv_tab_title);
            textView.setTextColor(z ? this.mTextSelectColor : this.mTextUnselectColor);
            ImageView imageView = (ImageView) childAt.findViewById(C1485R.id.iv_tab_icon);
            CustomTabEntity customTabEntity = this.mTabEntitys.get(i2);
            imageView.setImageResource(z ? customTabEntity.getTabSelectedIcon() : customTabEntity.getTabUnselectedIcon());
            if (this.mTextBold == 1) {
                textView.getPaint().setFakeBoldText(z);
            }
            i2++;
        }
    }

    private void calcOffset() {
        View childAt = this.mTabsContainer.getChildAt(this.mCurrentTab);
        this.mCurrentP.left = childAt.getLeft();
        this.mCurrentP.right = childAt.getRight();
        View childAt2 = this.mTabsContainer.getChildAt(this.mLastTab);
        this.mLastP.left = childAt2.getLeft();
        this.mLastP.right = childAt2.getRight();
        if (this.mLastP.left == this.mCurrentP.left && this.mLastP.right == this.mCurrentP.right) {
            invalidate();
            return;
        }
        this.mValueAnimator.setObjectValues(this.mLastP, this.mCurrentP);
        if (this.mIndicatorBounceEnable) {
            this.mValueAnimator.setInterpolator(this.mInterpolator);
        }
        if (this.mIndicatorAnimDuration < 0) {
            this.mIndicatorAnimDuration = this.mIndicatorBounceEnable ? 500L : 250L;
        }
        this.mValueAnimator.setDuration(this.mIndicatorAnimDuration);
        this.mValueAnimator.start();
    }

    private void calcIndicatorRect() {
        View childAt = this.mTabsContainer.getChildAt(this.mCurrentTab);
        float left = childAt.getLeft();
        float right = childAt.getRight();
        this.mIndicatorRect.left = (int) left;
        this.mIndicatorRect.right = (int) right;
        if (this.mIndicatorWidth < 0.0f) {
            return;
        }
        this.mIndicatorRect.left = (int) (childAt.getLeft() + ((childAt.getWidth() - this.mIndicatorWidth) / 2.0f));
        this.mIndicatorRect.right = (int) (r0.left + this.mIndicatorWidth);
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        View childAt = this.mTabsContainer.getChildAt(this.mCurrentTab);
        IndicatorPoint indicatorPoint = (IndicatorPoint) valueAnimator.getAnimatedValue();
        this.mIndicatorRect.left = (int) indicatorPoint.left;
        this.mIndicatorRect.right = (int) indicatorPoint.right;
        if (this.mIndicatorWidth >= 0.0f) {
            this.mIndicatorRect.left = (int) (indicatorPoint.left + ((childAt.getWidth() - this.mIndicatorWidth) / 2.0f));
            this.mIndicatorRect.right = (int) (r4.left + this.mIndicatorWidth);
        }
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || this.mTabCount <= 0) {
            return;
        }
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        float f = this.mDividerWidth;
        if (f > 0.0f) {
            this.mDividerPaint.setStrokeWidth(f);
            this.mDividerPaint.setColor(this.mDividerColor);
            for (int i = 0; i < this.mTabCount - 1; i++) {
                View childAt = this.mTabsContainer.getChildAt(i);
                canvas.drawLine(childAt.getRight() + paddingLeft, this.mDividerPadding, childAt.getRight() + paddingLeft, height - this.mDividerPadding, this.mDividerPaint);
            }
        }
        if (this.mUnderlineHeight > 0.0f) {
            this.mRectPaint.setColor(this.mUnderlineColor);
            if (this.mUnderlineGravity == 80) {
                float f2 = height;
                canvas.drawRect(paddingLeft, f2 - this.mUnderlineHeight, this.mTabsContainer.getWidth() + paddingLeft, f2, this.mRectPaint);
            } else {
                canvas.drawRect(paddingLeft, 0.0f, this.mTabsContainer.getWidth() + paddingLeft, this.mUnderlineHeight, this.mRectPaint);
            }
        }
        if (this.mIndicatorAnimEnable) {
            if (this.mIsFirstDraw) {
                this.mIsFirstDraw = false;
                calcIndicatorRect();
            }
        } else {
            calcIndicatorRect();
        }
        int i2 = this.mIndicatorStyle;
        if (i2 == 1) {
            if (this.mIndicatorHeight > 0.0f) {
                this.mTrianglePaint.setColor(this.mIndicatorColor);
                this.mTrianglePath.reset();
                float f3 = height;
                this.mTrianglePath.moveTo(this.mIndicatorRect.left + paddingLeft, f3);
                this.mTrianglePath.lineTo((this.mIndicatorRect.left / 2) + paddingLeft + (this.mIndicatorRect.right / 2), f3 - this.mIndicatorHeight);
                this.mTrianglePath.lineTo(paddingLeft + this.mIndicatorRect.right, f3);
                this.mTrianglePath.close();
                canvas.drawPath(this.mTrianglePath, this.mTrianglePaint);
                return;
            }
            return;
        }
        if (i2 != 2) {
            if (this.mIndicatorHeight > 0.0f) {
                this.mIndicatorDrawable.setColor(this.mIndicatorColor);
                if (this.mIndicatorGravity == 80) {
                    this.mIndicatorDrawable.setBounds(((int) this.mIndicatorMarginLeft) + paddingLeft + this.mIndicatorRect.left, (height - ((int) this.mIndicatorHeight)) - ((int) this.mIndicatorMarginBottom), (paddingLeft + this.mIndicatorRect.right) - ((int) this.mIndicatorMarginRight), height - ((int) this.mIndicatorMarginBottom));
                } else {
                    this.mIndicatorDrawable.setBounds(((int) this.mIndicatorMarginLeft) + paddingLeft + this.mIndicatorRect.left, (int) this.mIndicatorMarginTop, (paddingLeft + this.mIndicatorRect.right) - ((int) this.mIndicatorMarginRight), ((int) this.mIndicatorHeight) + ((int) this.mIndicatorMarginTop));
                }
                this.mIndicatorDrawable.setCornerRadius(this.mIndicatorCornerRadius);
                this.mIndicatorDrawable.draw(canvas);
                return;
            }
            return;
        }
        if (this.mIndicatorHeight < 0.0f) {
            this.mIndicatorHeight = (height - this.mIndicatorMarginTop) - this.mIndicatorMarginBottom;
        }
        float f4 = this.mIndicatorHeight;
        if (f4 > 0.0f) {
            float f5 = this.mIndicatorCornerRadius;
            if (f5 < 0.0f || f5 > f4 / 2.0f) {
                this.mIndicatorCornerRadius = f4 / 2.0f;
            }
            this.mIndicatorDrawable.setColor(this.mIndicatorColor);
            this.mIndicatorDrawable.setBounds(((int) this.mIndicatorMarginLeft) + paddingLeft + this.mIndicatorRect.left, (int) this.mIndicatorMarginTop, (int) ((paddingLeft + this.mIndicatorRect.right) - this.mIndicatorMarginRight), (int) (this.mIndicatorMarginTop + this.mIndicatorHeight));
            this.mIndicatorDrawable.setCornerRadius(this.mIndicatorCornerRadius);
            this.mIndicatorDrawable.draw(canvas);
        }
    }

    public void setCurrentTab(int i) {
        this.mLastTab = this.mCurrentTab;
        this.mCurrentTab = i;
        updateTabSelection(i);
        FragmentChangeManager fragmentChangeManager = this.mFragmentChangeManager;
        if (fragmentChangeManager != null) {
            fragmentChangeManager.setFragments(i);
        }
        if (this.mIndicatorAnimEnable) {
            calcOffset();
        } else {
            invalidate();
        }
    }

    public void setIndicatorStyle(int i) {
        this.mIndicatorStyle = i;
        invalidate();
    }

    public void setTabPadding(float f) {
        this.mTabPadding = dp2px(f);
        updateTabStyles();
    }

    public void setTabSpaceEqual(boolean z) {
        this.mTabSpaceEqual = z;
        updateTabStyles();
    }

    public void setTabWidth(float f) {
        this.mTabWidth = dp2px(f);
        updateTabStyles();
    }

    public void setIndicatorColor(int i) {
        this.mIndicatorColor = i;
        invalidate();
    }

    public void setIndicatorHeight(float f) {
        this.mIndicatorHeight = dp2px(f);
        invalidate();
    }

    public void setIndicatorWidth(float f) {
        this.mIndicatorWidth = dp2px(f);
        invalidate();
    }

    public void setIndicatorCornerRadius(float f) {
        this.mIndicatorCornerRadius = dp2px(f);
        invalidate();
    }

    public void setIndicatorGravity(int i) {
        this.mIndicatorGravity = i;
        invalidate();
    }

    public void setIndicatorMargin(float f, float f2, float f3, float f4) {
        this.mIndicatorMarginLeft = dp2px(f);
        this.mIndicatorMarginTop = dp2px(f2);
        this.mIndicatorMarginRight = dp2px(f3);
        this.mIndicatorMarginBottom = dp2px(f4);
        invalidate();
    }

    public void setIndicatorAnimDuration(long j) {
        this.mIndicatorAnimDuration = j;
    }

    public void setIndicatorAnimEnable(boolean z) {
        this.mIndicatorAnimEnable = z;
    }

    public void setIndicatorBounceEnable(boolean z) {
        this.mIndicatorBounceEnable = z;
    }

    public void setUnderlineColor(int i) {
        this.mUnderlineColor = i;
        invalidate();
    }

    public void setUnderlineHeight(float f) {
        this.mUnderlineHeight = dp2px(f);
        invalidate();
    }

    public void setUnderlineGravity(int i) {
        this.mUnderlineGravity = i;
        invalidate();
    }

    public void setDividerColor(int i) {
        this.mDividerColor = i;
        invalidate();
    }

    public void setDividerWidth(float f) {
        this.mDividerWidth = dp2px(f);
        invalidate();
    }

    public void setDividerPadding(float f) {
        this.mDividerPadding = dp2px(f);
        invalidate();
    }

    public void setTextsize(float f) {
        this.mTextsize = sp2px(f);
        updateTabStyles();
    }

    public void setTextSelectColor(int i) {
        this.mTextSelectColor = i;
        updateTabStyles();
    }

    public void setTextUnselectColor(int i) {
        this.mTextUnselectColor = i;
        updateTabStyles();
    }

    public void setTextBold(int i) {
        this.mTextBold = i;
        updateTabStyles();
    }

    public void setIconVisible(boolean z) {
        this.mIconVisible = z;
        updateTabStyles();
    }

    public void setIconGravity(int i) {
        this.mIconGravity = i;
        notifyDataSetChanged();
    }

    public void setIconWidth(float f) {
        this.mIconWidth = dp2px(f);
        updateTabStyles();
    }

    public void setIconHeight(float f) {
        this.mIconHeight = dp2px(f);
        updateTabStyles();
    }

    public void setIconMargin(float f) {
        this.mIconMargin = dp2px(f);
        updateTabStyles();
    }

    public void setTextAllCaps(boolean z) {
        this.mTextAllCaps = z;
        updateTabStyles();
    }

    public int getTabCount() {
        return this.mTabCount;
    }

    public int getCurrentTab() {
        return this.mCurrentTab;
    }

    public int getIndicatorStyle() {
        return this.mIndicatorStyle;
    }

    public float getTabPadding() {
        return this.mTabPadding;
    }

    public boolean isTabSpaceEqual() {
        return this.mTabSpaceEqual;
    }

    public float getTabWidth() {
        return this.mTabWidth;
    }

    public int getIndicatorColor() {
        return this.mIndicatorColor;
    }

    public float getIndicatorHeight() {
        return this.mIndicatorHeight;
    }

    public float getIndicatorWidth() {
        return this.mIndicatorWidth;
    }

    public float getIndicatorCornerRadius() {
        return this.mIndicatorCornerRadius;
    }

    public float getIndicatorMarginLeft() {
        return this.mIndicatorMarginLeft;
    }

    public float getIndicatorMarginTop() {
        return this.mIndicatorMarginTop;
    }

    public float getIndicatorMarginRight() {
        return this.mIndicatorMarginRight;
    }

    public float getIndicatorMarginBottom() {
        return this.mIndicatorMarginBottom;
    }

    public long getIndicatorAnimDuration() {
        return this.mIndicatorAnimDuration;
    }

    public boolean isIndicatorAnimEnable() {
        return this.mIndicatorAnimEnable;
    }

    public boolean isIndicatorBounceEnable() {
        return this.mIndicatorBounceEnable;
    }

    public int getUnderlineColor() {
        return this.mUnderlineColor;
    }

    public float getUnderlineHeight() {
        return this.mUnderlineHeight;
    }

    public int getDividerColor() {
        return this.mDividerColor;
    }

    public float getDividerWidth() {
        return this.mDividerWidth;
    }

    public float getDividerPadding() {
        return this.mDividerPadding;
    }

    public float getTextsize() {
        return this.mTextsize;
    }

    public int getTextSelectColor() {
        return this.mTextSelectColor;
    }

    public int getTextUnselectColor() {
        return this.mTextUnselectColor;
    }

    public int getTextBold() {
        return this.mTextBold;
    }

    public boolean isTextAllCaps() {
        return this.mTextAllCaps;
    }

    public int getIconGravity() {
        return this.mIconGravity;
    }

    public float getIconWidth() {
        return this.mIconWidth;
    }

    public float getIconHeight() {
        return this.mIconHeight;
    }

    public float getIconMargin() {
        return this.mIconMargin;
    }

    public boolean isIconVisible() {
        return this.mIconVisible;
    }

    public ImageView getIconView(int i) {
        return (ImageView) this.mTabsContainer.getChildAt(i).findViewById(C1485R.id.iv_tab_icon);
    }

    public TextView getTitleView(int i) {
        return (TextView) this.mTabsContainer.getChildAt(i).findViewById(C1485R.id.tv_tab_title);
    }

    public void showMsg(int i, int i2) {
        int i3 = this.mTabCount;
        if (i >= i3) {
            i = i3 - 1;
        }
        MsgView msgView = (MsgView) this.mTabsContainer.getChildAt(i).findViewById(C1485R.id.rtv_msg_tip);
        if (msgView != null) {
            UnreadMsgUtils.show(msgView, i2);
            if (this.mInitSetMap.get(i) == null || !this.mInitSetMap.get(i).booleanValue()) {
                if (!this.mIconVisible) {
                    setMsgMargin(i, 2.0f, 2.0f);
                } else {
                    int i4 = this.mIconGravity;
                    setMsgMargin(i, 0.0f, (i4 == 3 || i4 == 5) ? 4.0f : 0.0f);
                }
                this.mInitSetMap.put(i, true);
            }
        }
    }

    public void showDot(int i) {
        int i2 = this.mTabCount;
        if (i >= i2) {
            i = i2 - 1;
        }
        showMsg(i, 0);
    }

    public void hideMsg(int i) {
        int i2 = this.mTabCount;
        if (i >= i2) {
            i = i2 - 1;
        }
        MsgView msgView = (MsgView) this.mTabsContainer.getChildAt(i).findViewById(C1485R.id.rtv_msg_tip);
        if (msgView != null) {
            msgView.setVisibility(8);
        }
    }

    public void setMsgMargin(int i, float f, float f2) {
        int i2 = this.mTabCount;
        if (i >= i2) {
            i = i2 - 1;
        }
        View childAt = this.mTabsContainer.getChildAt(i);
        MsgView msgView = (MsgView) childAt.findViewById(C1485R.id.rtv_msg_tip);
        if (msgView != null) {
            TextView textView = (TextView) childAt.findViewById(C1485R.id.tv_tab_title);
            this.mTextPaint.setTextSize(this.mTextsize);
            this.mTextPaint.measureText(textView.getText().toString());
            float fDescent = this.mTextPaint.descent() - this.mTextPaint.ascent();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) msgView.getLayoutParams();
            float intrinsicHeight = this.mIconHeight;
            float f3 = 0.0f;
            if (this.mIconVisible) {
                if (intrinsicHeight <= 0.0f) {
                    intrinsicHeight = this.mContext.getResources().getDrawable(this.mTabEntitys.get(i).getTabSelectedIcon()).getIntrinsicHeight();
                }
                f3 = this.mIconMargin;
            }
            int i3 = this.mIconGravity;
            if (i3 == 48 || i3 == 80) {
                marginLayoutParams.leftMargin = dp2px(f);
                int i4 = this.mHeight;
                marginLayoutParams.topMargin = i4 > 0 ? (((int) (((i4 - fDescent) - intrinsicHeight) - f3)) / 2) - dp2px(f2) : dp2px(f2);
            } else {
                marginLayoutParams.leftMargin = dp2px(f);
                int i5 = this.mHeight;
                marginLayoutParams.topMargin = i5 > 0 ? (((int) (i5 - Math.max(fDescent, intrinsicHeight))) / 2) - dp2px(f2) : dp2px(f2);
            }
            msgView.setLayoutParams(marginLayoutParams);
        }
    }

    public MsgView getMsgView(int i) {
        int i2 = this.mTabCount;
        if (i >= i2) {
            i = i2 - 1;
        }
        return (MsgView) this.mTabsContainer.getChildAt(i).findViewById(C1485R.id.rtv_msg_tip);
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.mListener = onTabSelectListener;
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mCurrentTab", this.mCurrentTab);
        return bundle;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.mCurrentTab = bundle.getInt("mCurrentTab");
            parcelable = bundle.getParcelable("instanceState");
            if (this.mCurrentTab != 0 && this.mTabsContainer.getChildCount() > 0) {
                updateTabSelection(this.mCurrentTab);
            }
        }
        super.onRestoreInstanceState(parcelable);
    }

    class IndicatorPoint {
        public float left;
        public float right;

        IndicatorPoint() {
        }
    }

    class PointEvaluator implements TypeEvaluator<IndicatorPoint> {
        PointEvaluator() {
        }

        @Override // android.animation.TypeEvaluator
        public IndicatorPoint evaluate(float f, IndicatorPoint indicatorPoint, IndicatorPoint indicatorPoint2) {
            float f2 = indicatorPoint.left + ((indicatorPoint2.left - indicatorPoint.left) * f);
            float f3 = indicatorPoint.right + (f * (indicatorPoint2.right - indicatorPoint.right));
            IndicatorPoint indicatorPoint3 = CommonTabLayout.this.new IndicatorPoint();
            indicatorPoint3.left = f2;
            indicatorPoint3.right = f3;
            return indicatorPoint3;
        }
    }

    protected int dp2px(float f) {
        return (int) ((f * this.mContext.getResources().getDisplayMetrics().density) + 0.5f);
    }

    protected int sp2px(float f) {
        return (int) ((f * this.mContext.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }
}
