package com.github.gzuliyujiang.wheelview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import androidx.core.view.ViewCompat;
import com.github.gzuliyujiang.wheelview.C1494R;
import com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener;
import com.github.gzuliyujiang.wheelview.contract.TextProvider;
import com.github.gzuliyujiang.wheelview.contract.WheelFormatter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class WheelView extends View implements Runnable {

    @Deprecated
    public static final int SCROLL_STATE_DRAGGING = 1;

    @Deprecated
    public static final int SCROLL_STATE_IDLE = 0;

    @Deprecated
    public static final int SCROLL_STATE_SCROLLING = 2;
    protected boolean atmosphericEnabled;
    private final Camera camera;
    protected int currentPosition;
    protected int curtainColor;
    protected int curtainCorner;
    protected boolean curtainEnabled;
    protected float curtainRadius;
    protected boolean curvedEnabled;
    protected int curvedIndicatorSpace;
    protected int curvedMaxAngle;
    protected boolean cyclicEnabled;
    protected List<?> data;
    protected Object defaultItem;
    protected int defaultItemPosition;
    private int downPointYCoordinate;
    private int drawnCenterXCoordinate;
    private int drawnCenterYCoordinate;
    private int drawnItemCount;
    protected WheelFormatter formatter;
    private int halfDrawnItemCount;
    private int halfItemHeight;
    private int halfWheelHeight;
    private final Handler handler;
    protected int indicatorColor;
    protected boolean indicatorEnabled;
    protected float indicatorSize;
    private boolean isClick;
    private boolean isForceFinishScroll;
    private int itemHeight;
    protected int itemSpace;
    private int lastPointYCoordinate;
    private int lastScrollPosition;
    private final Matrix matrixDepth;
    private final Matrix matrixRotate;
    private int maxFlingYCoordinate;
    protected String maxWidthText;
    private final int maximumVelocity;
    private int minFlingYCoordinate;
    private final int minimumVelocity;
    private OnWheelChangedListener onWheelChangedListener;
    private final Paint paint;
    private final Rect rectCurrentItem;
    private final Rect rectDrawn;
    private final Rect rectIndicatorFoot;
    private final Rect rectIndicatorHead;
    protected boolean sameWidthEnabled;
    private int scrollOffsetYCoordinate;
    private final Scroller scroller;
    protected boolean selectedTextBold;
    protected int selectedTextColor;
    protected float selectedTextSize;
    protected int textAlign;
    protected int textColor;
    private int textMaxHeight;
    private int textMaxWidth;
    protected float textSize;
    private final int touchSlop;
    private VelocityTracker tracker;
    protected int visibleItemCount;
    private int wheelCenterXCoordinate;
    private int wheelCenterYCoordinate;

    private boolean isPositionInRange(int position, int itemCount) {
        return position >= 0 && position < itemCount;
    }

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, C1494R.attr.WheelStyle);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.data = new ArrayList();
        this.curvedMaxAngle = 90;
        this.handler = new Handler();
        this.paint = new Paint(69);
        this.rectDrawn = new Rect();
        this.rectIndicatorHead = new Rect();
        this.rectIndicatorFoot = new Rect();
        this.rectCurrentItem = new Rect();
        this.camera = new Camera();
        this.matrixRotate = new Matrix();
        this.matrixDepth = new Matrix();
        initAttrs(context, attrs, defStyleAttr, C1494R.style.WheelDefault);
        initTextPaint();
        updateVisibleItemCount();
        this.scroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.minimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.maximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.touchSlop = viewConfiguration.getScaledTouchSlop();
        if (isInEditMode()) {
            setData(generatePreviewData());
        }
    }

    private void initTextPaint() {
        this.paint.setColor(this.textColor);
        this.paint.setTextSize(this.textSize);
        this.paint.setFakeBoldText(false);
        this.paint.setStyle(Paint.Style.FILL);
    }

    public void setStyle(int style) {
        initAttrs(getContext(), null, C1494R.attr.WheelStyle, style);
        initTextPaint();
        updatePaintTextAlign();
        computeTextWidthAndHeight();
        computeFlingLimitYCoordinate();
        computeIndicatorRect();
        computeCurrentItemRect();
        requestLayout();
        invalidate();
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        float f = context.getResources().getDisplayMetrics().density;
        float f2 = context.getResources().getDisplayMetrics().scaledDensity;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, C1494R.styleable.WheelView, defStyleAttr, defStyleRes);
        this.visibleItemCount = typedArrayObtainStyledAttributes.getInt(C1494R.styleable.WheelView_wheel_visibleItemCount, 5);
        this.sameWidthEnabled = typedArrayObtainStyledAttributes.getBoolean(C1494R.styleable.WheelView_wheel_sameWidthEnabled, false);
        this.maxWidthText = typedArrayObtainStyledAttributes.getString(C1494R.styleable.WheelView_wheel_maxWidthText);
        this.textColor = typedArrayObtainStyledAttributes.getColor(C1494R.styleable.WheelView_wheel_itemTextColor, -7829368);
        this.selectedTextColor = typedArrayObtainStyledAttributes.getColor(C1494R.styleable.WheelView_wheel_itemTextColorSelected, ViewCompat.MEASURED_STATE_MASK);
        this.textSize = typedArrayObtainStyledAttributes.getDimension(C1494R.styleable.WheelView_wheel_itemTextSize, f2 * 15.0f);
        this.selectedTextSize = typedArrayObtainStyledAttributes.getDimension(C1494R.styleable.WheelView_wheel_itemTextSizeSelected, this.textSize);
        this.selectedTextBold = typedArrayObtainStyledAttributes.getBoolean(C1494R.styleable.WheelView_wheel_itemTextBoldSelected, false);
        this.textAlign = typedArrayObtainStyledAttributes.getInt(C1494R.styleable.WheelView_wheel_itemTextAlign, 0);
        this.itemSpace = typedArrayObtainStyledAttributes.getDimensionPixelSize(C1494R.styleable.WheelView_wheel_itemSpace, (int) (20.0f * f));
        this.cyclicEnabled = typedArrayObtainStyledAttributes.getBoolean(C1494R.styleable.WheelView_wheel_cyclicEnabled, false);
        this.indicatorEnabled = typedArrayObtainStyledAttributes.getBoolean(C1494R.styleable.WheelView_wheel_indicatorEnabled, true);
        this.indicatorColor = typedArrayObtainStyledAttributes.getColor(C1494R.styleable.WheelView_wheel_indicatorColor, -3552823);
        float f3 = f * 1.0f;
        this.indicatorSize = typedArrayObtainStyledAttributes.getDimension(C1494R.styleable.WheelView_wheel_indicatorSize, f3);
        this.curvedIndicatorSpace = typedArrayObtainStyledAttributes.getDimensionPixelSize(C1494R.styleable.WheelView_wheel_curvedIndicatorSpace, (int) f3);
        this.curtainEnabled = typedArrayObtainStyledAttributes.getBoolean(C1494R.styleable.WheelView_wheel_curtainEnabled, false);
        this.curtainColor = typedArrayObtainStyledAttributes.getColor(C1494R.styleable.WheelView_wheel_curtainColor, -1);
        this.curtainCorner = typedArrayObtainStyledAttributes.getInt(C1494R.styleable.WheelView_wheel_curtainCorner, 0);
        this.curtainRadius = typedArrayObtainStyledAttributes.getDimension(C1494R.styleable.WheelView_wheel_curtainRadius, 0.0f);
        this.atmosphericEnabled = typedArrayObtainStyledAttributes.getBoolean(C1494R.styleable.WheelView_wheel_atmosphericEnabled, false);
        this.curvedEnabled = typedArrayObtainStyledAttributes.getBoolean(C1494R.styleable.WheelView_wheel_curvedEnabled, false);
        this.curvedMaxAngle = typedArrayObtainStyledAttributes.getInteger(C1494R.styleable.WheelView_wheel_curvedMaxAngle, 90);
        typedArrayObtainStyledAttributes.recycle();
    }

    protected List<?> generatePreviewData() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("贵州穿青人");
        arrayList.add("大定府羡民");
        arrayList.add("不在五十六个民族之内");
        arrayList.add("已识别待定民族");
        arrayList.add("穿青山魈人马");
        arrayList.add("李裕江");
        return arrayList;
    }

    private void updateVisibleItemCount() {
        int i = this.visibleItemCount;
        if (i < 2) {
            throw new ArithmeticException("Visible item count can not be less than 2");
        }
        if (i % 2 == 0) {
            this.visibleItemCount = i + 1;
        }
        int i2 = this.visibleItemCount + 2;
        this.drawnItemCount = i2;
        this.halfDrawnItemCount = i2 / 2;
    }

    private void computeTextWidthAndHeight() {
        this.textMaxHeight = 0;
        this.textMaxWidth = 0;
        if (this.sameWidthEnabled) {
            this.textMaxWidth = (int) this.paint.measureText(formatItem(0));
        } else if (!TextUtils.isEmpty(this.maxWidthText)) {
            this.textMaxWidth = (int) this.paint.measureText(this.maxWidthText);
        } else {
            int itemCount = getItemCount();
            for (int i = 0; i < itemCount; i++) {
                this.textMaxWidth = Math.max(this.textMaxWidth, (int) this.paint.measureText(formatItem(i)));
            }
        }
        Paint.FontMetrics fontMetrics = this.paint.getFontMetrics();
        this.textMaxHeight = (int) (fontMetrics.bottom - fontMetrics.top);
    }

    public int getItemCount() {
        return this.data.size();
    }

    public <T> T getItem(int i) {
        int i2;
        int size = this.data.size();
        if (size != 0 && (i2 = (i + size) % size) >= 0 && i2 <= size - 1) {
            return (T) this.data.get(i2);
        }
        return null;
    }

    public int getPosition(Object item) {
        if (item == null) {
            return 0;
        }
        return this.data.indexOf(item);
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public <T> T getCurrentItem() {
        return (T) getItem(this.currentPosition);
    }

    public int getVisibleItemCount() {
        return this.visibleItemCount;
    }

    public void setVisibleItemCount(int count) {
        this.visibleItemCount = count;
        updateVisibleItemCount();
        requestLayout();
    }

    public boolean isCyclicEnabled() {
        return this.cyclicEnabled;
    }

    public void setCyclicEnabled(boolean isCyclic) {
        this.cyclicEnabled = isCyclic;
        computeFlingLimitYCoordinate();
        invalidate();
    }

    public void setOnWheelChangedListener(OnWheelChangedListener listener) {
        this.onWheelChangedListener = listener;
    }

    public void setFormatter(WheelFormatter formatter) {
        this.formatter = formatter;
    }

    public List<?> getData() {
        return this.data;
    }

    public void setData(List<?> newData) {
        setData(newData, 0);
    }

    public void setData(List<?> newData, Object defaultValue) {
        setData(newData, findPosition(defaultValue));
    }

    public void setData(List<?> newData, int defaultPosition) {
        if (newData == null) {
            newData = new ArrayList<>();
        }
        this.data = newData;
        notifyDataSetChanged(defaultPosition);
    }

    public void setDefaultValue(Object value) {
        setDefaultPosition(findPosition(value));
    }

    public void setDefaultPosition(int position) {
        notifyDataSetChanged(position);
    }

    private int findPosition(Object value) {
        WheelFormatter wheelFormatter;
        if (value == null) {
            return 0;
        }
        int i = 0;
        for (Object obj : this.data) {
            if (obj != null) {
                if (obj.equals(value) || (((wheelFormatter = this.formatter) != null && wheelFormatter.formatItem(obj).equals(this.formatter.formatItem(value))) || (((obj instanceof TextProvider) && ((TextProvider) obj).provideText().equals(value.toString())) || obj.toString().equals(value.toString())))) {
                    return i;
                }
                i++;
            }
        }
        return 0;
    }

    public boolean isSameWidthEnabled() {
        return this.sameWidthEnabled;
    }

    public void setSameWidthEnabled(boolean sameWidthEnabled) {
        this.sameWidthEnabled = sameWidthEnabled;
        computeTextWidthAndHeight();
        requestLayout();
        invalidate();
    }

    public String getMaxWidthText() {
        return this.maxWidthText;
    }

    public void setMaxWidthText(String text) {
        if (text == null) {
            throw new NullPointerException("Maximum width text can not be null!");
        }
        this.maxWidthText = text;
        computeTextWidthAndHeight();
        requestLayout();
        invalidate();
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int color) {
        this.textColor = color;
        invalidate();
    }

    public int getSelectedTextColor() {
        return this.selectedTextColor;
    }

    public void setSelectedTextColor(int color) {
        this.selectedTextColor = color;
        computeCurrentItemRect();
        invalidate();
    }

    public float getTextSize() {
        return this.textSize;
    }

    public void setTextSize(float size) {
        this.textSize = size;
        computeTextWidthAndHeight();
        requestLayout();
        invalidate();
    }

    public float getSelectedTextSize() {
        return this.selectedTextSize;
    }

    public void setSelectedTextSize(float size) {
        this.selectedTextSize = size;
        computeTextWidthAndHeight();
        requestLayout();
        invalidate();
    }

    public boolean getSelectedTextBold() {
        return this.selectedTextBold;
    }

    public void setSelectedTextBold(boolean bold) {
        this.selectedTextBold = bold;
        computeTextWidthAndHeight();
        requestLayout();
        invalidate();
    }

    public int getItemSpace() {
        return this.itemSpace;
    }

    public void setItemSpace(int space) {
        this.itemSpace = space;
        requestLayout();
        invalidate();
    }

    public boolean isIndicatorEnabled() {
        return this.indicatorEnabled;
    }

    public void setIndicatorEnabled(boolean indicatorEnabled) {
        this.indicatorEnabled = indicatorEnabled;
        computeIndicatorRect();
        invalidate();
    }

    public float getIndicatorSize() {
        return this.indicatorSize;
    }

    public void setIndicatorSize(float size) {
        this.indicatorSize = size;
        computeIndicatorRect();
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorColor(int color) {
        this.indicatorColor = color;
        invalidate();
    }

    public int getCurvedIndicatorSpace() {
        return this.curvedIndicatorSpace;
    }

    public void setCurvedIndicatorSpace(int space) {
        this.curvedIndicatorSpace = space;
        computeIndicatorRect();
        invalidate();
    }

    public boolean isCurtainEnabled() {
        return this.curtainEnabled;
    }

    public void setCurtainEnabled(boolean curtainEnabled) {
        this.curtainEnabled = curtainEnabled;
        if (curtainEnabled) {
            this.indicatorEnabled = false;
        }
        computeCurrentItemRect();
        invalidate();
    }

    public int getCurtainColor() {
        return this.curtainColor;
    }

    public void setCurtainColor(int color) {
        this.curtainColor = color;
        invalidate();
    }

    public int getCurtainCorner() {
        return this.curtainCorner;
    }

    public void setCurtainCorner(int curtainCorner) {
        this.curtainCorner = curtainCorner;
        invalidate();
    }

    public float getCurtainRadius() {
        return this.curtainRadius;
    }

    public void setCurtainRadius(float curtainRadius) {
        this.curtainRadius = curtainRadius;
        invalidate();
    }

    public boolean isAtmosphericEnabled() {
        return this.atmosphericEnabled;
    }

    public void setAtmosphericEnabled(boolean atmosphericEnabled) {
        this.atmosphericEnabled = atmosphericEnabled;
        invalidate();
    }

    public boolean isCurvedEnabled() {
        return this.curvedEnabled;
    }

    public void setCurvedEnabled(boolean isCurved) {
        this.curvedEnabled = isCurved;
        requestLayout();
        invalidate();
    }

    public int getCurvedMaxAngle() {
        return this.curvedMaxAngle;
    }

    public void setCurvedMaxAngle(int curvedMaxAngle) {
        this.curvedMaxAngle = curvedMaxAngle;
        requestLayout();
        invalidate();
    }

    public int getTextAlign() {
        return this.textAlign;
    }

    public void setTextAlign(int align) {
        this.textAlign = align;
        updatePaintTextAlign();
        computeDrawnCenterCoordinate();
        invalidate();
    }

    private void updatePaintTextAlign() {
        int i = this.textAlign;
        if (i == 1) {
            this.paint.setTextAlign(Paint.Align.LEFT);
        } else if (i == 2) {
            this.paint.setTextAlign(Paint.Align.RIGHT);
        } else {
            this.paint.setTextAlign(Paint.Align.CENTER);
        }
    }

    public Typeface getTypeface() {
        return this.paint.getTypeface();
    }

    public void setTypeface(Typeface typeface) {
        if (typeface == null) {
            return;
        }
        this.paint.setTypeface(typeface);
        computeTextWidthAndHeight();
        requestLayout();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDataSetChanged(int position) {
        int iMax = Math.max(Math.min(position, getItemCount() - 1), 0);
        this.scrollOffsetYCoordinate = 0;
        this.defaultItem = getItem(iMax);
        this.defaultItemPosition = iMax;
        this.currentPosition = iMax;
        updatePaintTextAlign();
        computeFlingLimitYCoordinate();
        computeIndicatorRect();
        computeCurrentItemRect();
        requestLayout();
        invalidate();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int i = this.textMaxWidth;
        int i2 = this.textMaxHeight;
        int i3 = this.visibleItemCount;
        int i4 = (i2 * i3) + (this.itemSpace * (i3 - 1));
        if (this.curvedEnabled) {
            i4 = (int) (((double) (i4 * 2)) / 3.141592653589793d);
        }
        setMeasuredDimension(measureSize(mode, size, i + getPaddingLeft() + getPaddingRight()), measureSize(mode2, size2, i4 + getPaddingTop() + getPaddingBottom()));
    }

    private int measureSize(int mode, int sizeExpect, int sizeActual) {
        return mode == 1073741824 ? sizeExpect : mode == Integer.MIN_VALUE ? Math.min(sizeActual, sizeExpect) : sizeActual;
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        this.rectDrawn.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        this.wheelCenterXCoordinate = this.rectDrawn.centerX();
        this.wheelCenterYCoordinate = this.rectDrawn.centerY();
        computeDrawnCenterCoordinate();
        this.halfWheelHeight = this.rectDrawn.height() / 2;
        int iHeight = this.rectDrawn.height() / this.visibleItemCount;
        this.itemHeight = iHeight;
        this.halfItemHeight = iHeight / 2;
        computeFlingLimitYCoordinate();
        computeIndicatorRect();
        computeCurrentItemRect();
    }

    private void computeDrawnCenterCoordinate() {
        int i = this.textAlign;
        if (i == 1) {
            this.drawnCenterXCoordinate = this.rectDrawn.left;
        } else if (i == 2) {
            this.drawnCenterXCoordinate = this.rectDrawn.right;
        } else {
            this.drawnCenterXCoordinate = this.wheelCenterXCoordinate;
        }
        this.drawnCenterYCoordinate = (int) (this.wheelCenterYCoordinate - ((this.paint.ascent() + this.paint.descent()) / 2.0f));
    }

    private void computeFlingLimitYCoordinate() {
        int i = this.defaultItemPosition;
        int i2 = this.itemHeight;
        int i3 = i * i2;
        this.minFlingYCoordinate = this.cyclicEnabled ? Integer.MIN_VALUE : ((-i2) * (getItemCount() - 1)) + i3;
        if (this.cyclicEnabled) {
            i3 = Integer.MAX_VALUE;
        }
        this.maxFlingYCoordinate = i3;
    }

    private void computeIndicatorRect() {
        if (this.indicatorEnabled) {
            int i = this.curvedEnabled ? this.curvedIndicatorSpace : 0;
            int i2 = (int) (this.indicatorSize / 2.0f);
            int i3 = this.wheelCenterYCoordinate;
            int i4 = this.halfItemHeight;
            int i5 = i3 + i4 + i;
            int i6 = (i3 - i4) - i;
            this.rectIndicatorHead.set(this.rectDrawn.left, i5 - i2, this.rectDrawn.right, i5 + i2);
            this.rectIndicatorFoot.set(this.rectDrawn.left, i6 - i2, this.rectDrawn.right, i6 + i2);
        }
    }

    private void computeCurrentItemRect() {
        if (this.curtainEnabled || this.selectedTextColor != 0) {
            this.rectCurrentItem.set(this.rectDrawn.left, this.wheelCenterYCoordinate - this.halfItemHeight, this.rectDrawn.right, this.wheelCenterYCoordinate + this.halfItemHeight);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        OnWheelChangedListener onWheelChangedListener = this.onWheelChangedListener;
        if (onWheelChangedListener != null) {
            onWheelChangedListener.onWheelScrolled(this, this.scrollOffsetYCoordinate);
        }
        if (this.itemHeight - this.halfDrawnItemCount <= 0) {
            return;
        }
        drawCurtain(canvas);
        drawIndicator(canvas);
        drawAllItem(canvas);
    }

    private void drawAllItem(Canvas canvas) {
        int i = (this.scrollOffsetYCoordinate * (-1)) / this.itemHeight;
        int i2 = this.halfDrawnItemCount;
        int i3 = i - i2;
        int i4 = this.defaultItemPosition + i3;
        int i5 = i2 * (-1);
        while (i4 < this.defaultItemPosition + i3 + this.drawnItemCount) {
            initTextPaint();
            boolean z = i4 == (this.defaultItemPosition + i3) + (this.drawnItemCount / 2);
            int i6 = this.drawnCenterYCoordinate;
            int i7 = this.itemHeight;
            int i8 = (i5 * i7) + i6 + (this.scrollOffsetYCoordinate % i7);
            int iAbs = Math.abs(i6 - i8);
            float fComputeDegree = computeDegree(i8, (((this.drawnCenterYCoordinate - iAbs) - this.rectDrawn.top) * 1.0f) / (this.drawnCenterYCoordinate - this.rectDrawn.top));
            float fComputeYCoordinateAtAngle = computeYCoordinateAtAngle(fComputeDegree);
            if (this.curvedEnabled) {
                int i9 = this.wheelCenterXCoordinate;
                int i10 = this.textAlign;
                if (i10 == 1) {
                    i9 = this.rectDrawn.left;
                } else if (i10 == 2) {
                    i9 = this.rectDrawn.right;
                }
                float f = this.wheelCenterYCoordinate - fComputeYCoordinateAtAngle;
                this.camera.save();
                this.camera.rotateX(fComputeDegree);
                this.camera.getMatrix(this.matrixRotate);
                this.camera.restore();
                float f2 = -i9;
                float f3 = -f;
                this.matrixRotate.preTranslate(f2, f3);
                float f4 = i9;
                this.matrixRotate.postTranslate(f4, f);
                this.camera.save();
                this.camera.translate(0.0f, 0.0f, computeDepth(fComputeDegree));
                this.camera.getMatrix(this.matrixDepth);
                this.camera.restore();
                this.matrixDepth.preTranslate(f2, f3);
                this.matrixDepth.postTranslate(f4, f);
                this.matrixRotate.postConcat(this.matrixDepth);
            }
            computeAndSetAtmospheric(iAbs);
            drawItemRect(canvas, i4, z, this.curvedEnabled ? this.drawnCenterYCoordinate - fComputeYCoordinateAtAngle : i8);
            i4++;
            i5++;
        }
    }

    private void drawItemRect(Canvas canvas, int dataPosition, boolean isCenterItem, float drawCenterYCoordinate) {
        int i = this.selectedTextColor;
        if (i == 0) {
            canvas.save();
            canvas.clipRect(this.rectDrawn);
            if (this.curvedEnabled) {
                canvas.concat(this.matrixRotate);
            }
            drawItemText(canvas, dataPosition, drawCenterYCoordinate);
            canvas.restore();
            return;
        }
        if (this.textSize == this.selectedTextSize && !this.selectedTextBold) {
            canvas.save();
            if (this.curvedEnabled) {
                canvas.concat(this.matrixRotate);
            }
            canvas.clipOutRect(this.rectCurrentItem);
            drawItemText(canvas, dataPosition, drawCenterYCoordinate);
            canvas.restore();
            this.paint.setColor(this.selectedTextColor);
            canvas.save();
            if (this.curvedEnabled) {
                canvas.concat(this.matrixRotate);
            }
            canvas.clipRect(this.rectCurrentItem);
            drawItemText(canvas, dataPosition, drawCenterYCoordinate);
            canvas.restore();
            return;
        }
        if (!isCenterItem) {
            canvas.save();
            if (this.curvedEnabled) {
                canvas.concat(this.matrixRotate);
            }
            drawItemText(canvas, dataPosition, drawCenterYCoordinate);
            canvas.restore();
            return;
        }
        this.paint.setColor(i);
        this.paint.setTextSize(this.selectedTextSize);
        this.paint.setFakeBoldText(this.selectedTextBold);
        canvas.save();
        if (this.curvedEnabled) {
            canvas.concat(this.matrixRotate);
        }
        drawItemText(canvas, dataPosition, drawCenterYCoordinate);
        canvas.restore();
    }

    private void drawItemText(Canvas canvas, int dataPosition, float drawCenterYCoordinate) {
        int length;
        int measuredWidth = getMeasuredWidth();
        float fMeasureText = this.paint.measureText("...");
        String strObtainItemText = obtainItemText(dataPosition);
        boolean z = false;
        while ((this.paint.measureText(strObtainItemText) + fMeasureText) - measuredWidth > 0.0f && (length = strObtainItemText.length()) > 1) {
            strObtainItemText = strObtainItemText.substring(0, length - 1);
            z = true;
        }
        if (z) {
            strObtainItemText = strObtainItemText + "...";
        }
        canvas.drawText(strObtainItemText, this.drawnCenterXCoordinate, drawCenterYCoordinate, this.paint);
    }

    private float computeDegree(int drawnItemCenterYCoordinate, float ratio) {
        int i = this.drawnCenterYCoordinate;
        int i2 = drawnItemCenterYCoordinate > i ? 1 : drawnItemCenterYCoordinate < i ? -1 : 0;
        float f = -(1.0f - ratio);
        int i3 = this.curvedMaxAngle;
        return clamp(f * i3 * i2, -i3, i3);
    }

    private float clamp(float value, float min, float max) {
        return value < min ? min : Math.min(value, max);
    }

    private String obtainItemText(int drawnDataPosition) {
        int itemCount = getItemCount();
        if (this.cyclicEnabled) {
            if (itemCount != 0) {
                int i = drawnDataPosition % itemCount;
                if (i < 0) {
                    i += itemCount;
                }
                return formatItem(i);
            }
        } else if (isPositionInRange(drawnDataPosition, itemCount)) {
            return formatItem(drawnDataPosition);
        }
        return "";
    }

    public String formatItem(int position) {
        return formatItem(getItem(position));
    }

    public String formatItem(Object item) {
        if (item == null) {
            return "";
        }
        if (item instanceof TextProvider) {
            return ((TextProvider) item).provideText();
        }
        WheelFormatter wheelFormatter = this.formatter;
        if (wheelFormatter != null) {
            return wheelFormatter.formatItem(item);
        }
        return item.toString();
    }

    private void computeAndSetAtmospheric(int abs) {
        if (this.atmosphericEnabled) {
            this.paint.setAlpha(Math.max((int) ((((r0 - abs) * 1.0f) / this.drawnCenterYCoordinate) * 255.0f), 0));
        }
    }

    private void drawCurtain(Canvas canvas) {
        float[] fArr;
        if (this.curtainEnabled) {
            this.paint.setColor(this.curtainColor);
            this.paint.setStyle(Paint.Style.FILL);
            if (this.curtainRadius > 0.0f) {
                Path path = new Path();
                int i = this.curtainCorner;
                if (i == 1) {
                    float f = this.curtainRadius;
                    fArr = new float[]{f, f, f, f, f, f, f, f};
                } else if (i == 2) {
                    float f2 = this.curtainRadius;
                    fArr = new float[]{f2, f2, f2, f2, 0.0f, 0.0f, 0.0f, 0.0f};
                } else if (i == 3) {
                    float f3 = this.curtainRadius;
                    fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, f3, f3, f3, f3};
                } else if (i == 4) {
                    float f4 = this.curtainRadius;
                    fArr = new float[]{f4, f4, 0.0f, 0.0f, 0.0f, 0.0f, f4, f4};
                } else if (i == 5) {
                    float f5 = this.curtainRadius;
                    fArr = new float[]{0.0f, 0.0f, f5, f5, f5, f5, 0.0f, 0.0f};
                } else {
                    fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
                }
                path.addRoundRect(new RectF(this.rectCurrentItem), fArr, Path.Direction.CCW);
                canvas.drawPath(path, this.paint);
                return;
            }
            canvas.drawRect(this.rectCurrentItem, this.paint);
        }
    }

    private void drawIndicator(Canvas canvas) {
        if (this.indicatorEnabled) {
            this.paint.setColor(this.indicatorColor);
            this.paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(this.rectIndicatorHead, this.paint);
            canvas.drawRect(this.rectIndicatorFoot, this.paint);
        }
    }

    private float computeYCoordinateAtAngle(float degree) {
        return (sinDegree(degree) / sinDegree(this.curvedMaxAngle)) * this.halfWheelHeight;
    }

    private float sinDegree(float degree) {
        return (float) Math.sin(Math.toRadians(degree));
    }

    private int computeDepth(float degree) {
        return (int) (((double) this.halfWheelHeight) - (Math.cos(Math.toRadians(degree)) * ((double) this.halfWheelHeight)));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            int action = event.getAction();
            if (action == 0) {
                handleActionDown(event);
            } else if (action == 1) {
                handleActionUp(event);
            } else if (action == 2) {
                handleActionMove(event);
            } else if (action == 3) {
                handleActionCancel(event);
            }
        }
        if (this.isClick) {
            performClick();
        }
        return true;
    }

    private void handleActionDown(MotionEvent event) {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        obtainOrClearTracker();
        this.tracker.addMovement(event);
        if (!this.scroller.isFinished()) {
            this.scroller.abortAnimation();
            this.isForceFinishScroll = true;
        }
        int y = (int) event.getY();
        this.lastPointYCoordinate = y;
        this.downPointYCoordinate = y;
    }

    private void handleActionMove(MotionEvent event) {
        int iComputeDistanceToEndPoint = computeDistanceToEndPoint(this.scroller.getFinalY() % this.itemHeight);
        if (Math.abs(this.downPointYCoordinate - event.getY()) < this.touchSlop && iComputeDistanceToEndPoint > 0) {
            this.isClick = true;
            return;
        }
        this.isClick = false;
        VelocityTracker velocityTracker = this.tracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(event);
        }
        OnWheelChangedListener onWheelChangedListener = this.onWheelChangedListener;
        if (onWheelChangedListener != null) {
            onWheelChangedListener.onWheelScrollStateChanged(this, 1);
        }
        float y = event.getY() - this.lastPointYCoordinate;
        if (Math.abs(y) < 1.0f) {
            return;
        }
        this.scrollOffsetYCoordinate = (int) (this.scrollOffsetYCoordinate + y);
        this.lastPointYCoordinate = (int) event.getY();
        invalidate();
    }

    private void handleActionUp(MotionEvent event) {
        int yVelocity;
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        if (this.isClick) {
            return;
        }
        VelocityTracker velocityTracker = this.tracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(event);
            this.tracker.computeCurrentVelocity(1000, this.maximumVelocity);
            yVelocity = (int) this.tracker.getYVelocity();
        } else {
            yVelocity = 0;
        }
        this.isForceFinishScroll = false;
        if (Math.abs(yVelocity) > this.minimumVelocity) {
            this.scroller.fling(0, this.scrollOffsetYCoordinate, 0, yVelocity, 0, 0, this.minFlingYCoordinate, this.maxFlingYCoordinate);
            int iComputeDistanceToEndPoint = computeDistanceToEndPoint(this.scroller.getFinalY() % this.itemHeight);
            Scroller scroller = this.scroller;
            scroller.setFinalY(scroller.getFinalY() + iComputeDistanceToEndPoint);
        } else {
            this.scroller.startScroll(0, this.scrollOffsetYCoordinate, 0, computeDistanceToEndPoint(this.scrollOffsetYCoordinate % this.itemHeight));
        }
        if (!this.cyclicEnabled) {
            int finalY = this.scroller.getFinalY();
            int i = this.maxFlingYCoordinate;
            if (finalY > i) {
                this.scroller.setFinalY(i);
            } else {
                int finalY2 = this.scroller.getFinalY();
                int i2 = this.minFlingYCoordinate;
                if (finalY2 < i2) {
                    this.scroller.setFinalY(i2);
                }
            }
        }
        this.handler.post(this);
        cancelTracker();
    }

    private void handleActionCancel(MotionEvent event) {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        cancelTracker();
    }

    private void obtainOrClearTracker() {
        VelocityTracker velocityTracker = this.tracker;
        if (velocityTracker == null) {
            this.tracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }

    private void cancelTracker() {
        VelocityTracker velocityTracker = this.tracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.tracker = null;
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    private int computeDistanceToEndPoint(int remainder) {
        int i;
        if (Math.abs(remainder) <= this.halfItemHeight) {
            return remainder * (-1);
        }
        if (this.scrollOffsetYCoordinate < 0) {
            i = -this.itemHeight;
        } else {
            i = this.itemHeight;
        }
        return i - remainder;
    }

    @Override // java.lang.Runnable
    public void run() {
        OnWheelChangedListener onWheelChangedListener;
        if (this.itemHeight == 0) {
            return;
        }
        int itemCount = getItemCount();
        if (itemCount == 0) {
            OnWheelChangedListener onWheelChangedListener2 = this.onWheelChangedListener;
            if (onWheelChangedListener2 != null) {
                onWheelChangedListener2.onWheelScrollStateChanged(this, 0);
                return;
            }
            return;
        }
        if (this.scroller.isFinished() && !this.isForceFinishScroll) {
            int iComputePosition = computePosition(itemCount);
            if (iComputePosition < 0) {
                iComputePosition += itemCount;
            }
            this.currentPosition = iComputePosition;
            OnWheelChangedListener onWheelChangedListener3 = this.onWheelChangedListener;
            if (onWheelChangedListener3 != null) {
                onWheelChangedListener3.onWheelSelected(this, iComputePosition);
                this.onWheelChangedListener.onWheelScrollStateChanged(this, 0);
            }
            postInvalidate();
            return;
        }
        if (this.scroller.computeScrollOffset()) {
            OnWheelChangedListener onWheelChangedListener4 = this.onWheelChangedListener;
            if (onWheelChangedListener4 != null) {
                onWheelChangedListener4.onWheelScrollStateChanged(this, 2);
            }
            this.scrollOffsetYCoordinate = this.scroller.getCurrY();
            int iComputePosition2 = computePosition(itemCount);
            int i = this.lastScrollPosition;
            if (i != iComputePosition2) {
                if (iComputePosition2 == 0 && i == itemCount - 1 && (onWheelChangedListener = this.onWheelChangedListener) != null) {
                    onWheelChangedListener.onWheelLoopFinished(this);
                }
                this.lastScrollPosition = iComputePosition2;
            }
            postInvalidate();
            this.handler.postDelayed(this, 20L);
        }
    }

    private int computePosition(int itemCount) {
        return (((this.scrollOffsetYCoordinate * (-1)) / this.itemHeight) + this.defaultItemPosition) % itemCount;
    }

    public void scrollTo(final int position) {
        post(new Runnable() { // from class: com.github.gzuliyujiang.wheelview.widget.WheelView.1
            @Override // java.lang.Runnable
            public void run() {
                WheelView.this.notifyDataSetChanged(position);
            }
        });
    }

    public final void smoothScrollTo(final int position) {
        if (isInEditMode()) {
            scrollTo(position);
            return;
        }
        int i = this.currentPosition - position;
        int i2 = this.scrollOffsetYCoordinate;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(i2, (i * this.itemHeight) + i2);
        valueAnimatorOfInt.setDuration(300L);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.github.gzuliyujiang.wheelview.widget.WheelView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                WheelView.this.scrollOffsetYCoordinate = ((Integer) animation.getAnimatedValue()).intValue();
                WheelView.this.invalidate();
            }
        });
        valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.github.gzuliyujiang.wheelview.widget.WheelView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                WheelView.this.scrollTo(position);
            }
        });
        valueAnimatorOfInt.start();
    }
}
