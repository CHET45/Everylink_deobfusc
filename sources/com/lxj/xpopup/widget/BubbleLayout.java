package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import com.lxj.xpopup.util.XPopupUtils;

/* JADX INFO: loaded from: classes4.dex */
public class BubbleLayout extends FrameLayout {
    boolean isLookPositionCenter;
    private int mArrowDownLeftRadius;
    private int mArrowDownRightRadius;
    private int mArrowTopLeftRadius;
    private int mArrowTopRightRadius;
    private int mBottom;
    private int mBubbleBgRes;
    private int mBubbleBorderColor;
    private Paint mBubbleBorderPaint;
    private int mBubbleBorderSize;
    private int mBubbleColor;
    private Bitmap mBubbleImageBg;
    private Paint mBubbleImageBgBeforePaint;
    private RectF mBubbleImageBgDstRectF;
    private Paint mBubbleImageBgPaint;
    private Rect mBubbleImageBgSrcRect;
    private int mBubblePadding;
    private int mBubbleRadius;
    private int mHeight;
    private int mLDR;
    private int mLTR;
    private int mLeft;
    private Look mLook;
    private int mLookLength;
    private int mLookPosition;
    private int mLookWidth;
    private Paint mPaint;
    private Path mPath;
    private int mRDR;
    private int mRTR;
    private int mRight;
    private int mShadowColor;
    private int mShadowRadius;
    private int mShadowX;
    private int mShadowY;
    private int mTop;
    private int mWidth;

    public enum Look {
        LEFT(1),
        TOP(2),
        RIGHT(3),
        BOTTOM(4);

        int value;

        Look(int i) {
            this.value = i;
        }

        public static Look getType(int i) {
            Look look = BOTTOM;
            if (i == 1) {
                return LEFT;
            }
            if (i != 2) {
                return i != 3 ? look : RIGHT;
            }
            return TOP;
        }
    }

    public BubbleLayout(Context context) {
        this(context, null);
    }

    public BubbleLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLDR = -1;
        this.mBubbleBgRes = -1;
        this.mBubbleImageBg = null;
        this.mBubbleImageBgDstRectF = new RectF();
        this.mBubbleImageBgSrcRect = new Rect();
        this.mBubbleImageBgPaint = new Paint(5);
        this.mBubbleImageBgBeforePaint = new Paint(5);
        this.mBubbleBorderColor = ViewCompat.MEASURED_STATE_MASK;
        this.mBubbleBorderSize = 0;
        this.mBubbleBorderPaint = new Paint(5);
        setLayerType(1, null);
        setWillNotDraw(false);
        initAttr();
        Paint paint = new Paint(5);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPath = new Path();
        this.mBubbleImageBgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    /* JADX INFO: renamed from: com.lxj.xpopup.widget.BubbleLayout$1 */
    static /* synthetic */ class C22871 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look;

        static {
            int[] iArr = new int[Look.values().length];
            $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look = iArr;
            try {
                iArr[Look.BOTTOM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[Look.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[Look.LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[Look.RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void initPadding() {
        int i = this.mBubblePadding + this.mShadowRadius;
        int i2 = C22871.$SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[this.mLook.ordinal()];
        if (i2 == 1) {
            setPadding(i, i, this.mShadowX + i, this.mLookLength + i + this.mShadowY);
            return;
        }
        if (i2 == 2) {
            setPadding(i, this.mLookLength + i, this.mShadowX + i, this.mShadowY + i);
        } else if (i2 == 3) {
            setPadding(this.mLookLength + i, i, this.mShadowX + i, this.mShadowY + i);
        } else {
            if (i2 != 4) {
                return;
            }
            setPadding(i, i, this.mLookLength + i + this.mShadowX, this.mShadowY + i);
        }
    }

    private void initAttr() {
        this.mLook = Look.BOTTOM;
        this.mLookPosition = 0;
        this.mLookWidth = XPopupUtils.dp2px(getContext(), 10.0f);
        this.mLookLength = XPopupUtils.dp2px(getContext(), 9.0f);
        this.mShadowRadius = 0;
        this.mShadowX = 0;
        this.mShadowY = 0;
        this.mBubbleRadius = XPopupUtils.dp2px(getContext(), 8.0f);
        this.mLTR = -1;
        this.mRTR = -1;
        this.mRDR = -1;
        this.mLDR = -1;
        this.mArrowTopLeftRadius = XPopupUtils.dp2px(getContext(), 3.0f);
        this.mArrowTopRightRadius = XPopupUtils.dp2px(getContext(), 3.0f);
        this.mArrowDownLeftRadius = XPopupUtils.dp2px(getContext(), 6.0f);
        this.mArrowDownRightRadius = XPopupUtils.dp2px(getContext(), 6.0f);
        this.mBubblePadding = XPopupUtils.dp2px(getContext(), 4.0f);
        this.mShadowColor = -12303292;
        this.mBubbleColor = Color.parseColor("#3b3c3d");
        this.mBubbleBorderColor = 0;
        this.mBubbleBorderSize = 0;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mWidth = i;
        this.mHeight = i2;
        initData();
    }

    @Override // android.view.View
    public void invalidate() {
        initData();
        super.invalidate();
    }

    @Override // android.view.View
    public void postInvalidate() {
        initData();
        super.postInvalidate();
    }

    private void initData() {
        int i;
        int i2;
        initPadding();
        if (this.isLookPositionCenter) {
            if (this.mLook == Look.LEFT || this.mLook == Look.RIGHT) {
                i = this.mHeight / 2;
                i2 = this.mLookLength;
            } else {
                i = this.mWidth / 2;
                i2 = this.mLookWidth;
            }
            this.mLookPosition = i - (i2 / 2);
        }
        this.mPaint.setShadowLayer(this.mShadowRadius, this.mShadowX, this.mShadowY, this.mShadowColor);
        this.mBubbleBorderPaint.setColor(this.mBubbleBorderColor);
        this.mBubbleBorderPaint.setStrokeWidth(this.mBubbleBorderSize);
        this.mBubbleBorderPaint.setStyle(Paint.Style.STROKE);
        int i3 = this.mShadowRadius;
        int i4 = this.mShadowX;
        this.mLeft = i3 + (i4 < 0 ? -i4 : 0) + (this.mLook == Look.LEFT ? this.mLookLength : 0);
        int i5 = this.mShadowRadius;
        int i6 = this.mShadowY;
        this.mTop = i5 + (i6 < 0 ? -i6 : 0) + (this.mLook == Look.TOP ? this.mLookLength : 0);
        int i7 = this.mWidth - this.mShadowRadius;
        int i8 = this.mShadowX;
        this.mRight = (i7 + (i8 > 0 ? -i8 : 0)) - (this.mLook == Look.RIGHT ? this.mLookLength : 0);
        int i9 = this.mHeight - this.mShadowRadius;
        int i10 = this.mShadowY;
        this.mBottom = (i9 + (i10 > 0 ? -i10 : 0)) - (this.mLook == Look.BOTTOM ? this.mLookLength : 0);
        this.mPaint.setColor(this.mBubbleColor);
        this.mPath.reset();
        int i11 = this.mLookPosition;
        int i12 = this.mLookLength + i11;
        int i13 = this.mBottom;
        if (i12 > i13) {
            i11 = i13 - this.mLookWidth;
        }
        int iMax = Math.max(i11, this.mShadowRadius);
        int i14 = this.mLookPosition;
        int i15 = this.mLookLength + i14;
        int i16 = this.mRight;
        if (i15 > i16) {
            i14 = i16 - this.mLookWidth;
        }
        int iMax2 = Math.max(i14, this.mShadowRadius);
        int i17 = C22871.$SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[this.mLook.ordinal()];
        if (i17 == 1) {
            if (iMax2 >= getLDR() + this.mArrowDownRightRadius) {
                this.mPath.moveTo(iMax2 - r1, this.mBottom);
                Path path = this.mPath;
                int i18 = this.mArrowDownRightRadius;
                int i19 = this.mLookWidth;
                int i20 = this.mLookLength;
                path.rCubicTo(i18, 0.0f, ((i19 / 2.0f) - this.mArrowTopRightRadius) + i18, i20, (i19 / 2.0f) + i18, i20);
            } else {
                this.mPath.moveTo(iMax2 + (this.mLookWidth / 2.0f), this.mBottom + this.mLookLength);
            }
            int i21 = this.mLookWidth + iMax2;
            int rdr = this.mRight - getRDR();
            int i22 = this.mArrowDownLeftRadius;
            if (i21 < rdr - i22) {
                Path path2 = this.mPath;
                float f = this.mArrowTopLeftRadius;
                int i23 = this.mLookWidth;
                int i24 = this.mLookLength;
                path2.rCubicTo(f, 0.0f, i23 / 2.0f, -i24, (i23 / 2.0f) + i22, -i24);
                this.mPath.lineTo(this.mRight - getRDR(), this.mBottom);
            }
            Path path3 = this.mPath;
            int i25 = this.mRight;
            path3.quadTo(i25, this.mBottom, i25, r4 - getRDR());
            this.mPath.lineTo(this.mRight, this.mTop + getRTR());
            this.mPath.quadTo(this.mRight, this.mTop, r1 - getRTR(), this.mTop);
            this.mPath.lineTo(this.mLeft + getLTR(), this.mTop);
            Path path4 = this.mPath;
            int i26 = this.mLeft;
            path4.quadTo(i26, this.mTop, i26, r4 + getLTR());
            this.mPath.lineTo(this.mLeft, this.mBottom - getLDR());
            if (iMax2 >= getLDR() + this.mArrowDownRightRadius) {
                this.mPath.quadTo(this.mLeft, this.mBottom, r1 + getLDR(), this.mBottom);
            } else {
                this.mPath.quadTo(this.mLeft, this.mBottom, iMax2 + (this.mLookWidth / 2.0f), r3 + this.mLookLength);
            }
        } else if (i17 == 2) {
            if (iMax2 >= getLTR() + this.mArrowDownLeftRadius) {
                this.mPath.moveTo(iMax2 - r1, this.mTop);
                Path path5 = this.mPath;
                int i27 = this.mArrowDownLeftRadius;
                int i28 = this.mLookWidth;
                int i29 = this.mLookLength;
                path5.rCubicTo(i27, 0.0f, ((i28 / 2.0f) - this.mArrowTopLeftRadius) + i27, -i29, (i28 / 2.0f) + i27, -i29);
            } else {
                this.mPath.moveTo(iMax2 + (this.mLookWidth / 2.0f), this.mTop - this.mLookLength);
            }
            int i30 = this.mLookWidth + iMax2;
            int rtr = this.mRight - getRTR();
            int i31 = this.mArrowDownRightRadius;
            if (i30 < rtr - i31) {
                Path path6 = this.mPath;
                float f2 = this.mArrowTopRightRadius;
                int i32 = this.mLookWidth;
                int i33 = this.mLookLength;
                path6.rCubicTo(f2, 0.0f, i32 / 2.0f, i33, (i32 / 2.0f) + i31, i33);
                this.mPath.lineTo(this.mRight - getRTR(), this.mTop);
            }
            Path path7 = this.mPath;
            int i34 = this.mRight;
            path7.quadTo(i34, this.mTop, i34, r4 + getRTR());
            this.mPath.lineTo(this.mRight, this.mBottom - getRDR());
            this.mPath.quadTo(this.mRight, this.mBottom, r1 - getRDR(), this.mBottom);
            this.mPath.lineTo(this.mLeft + getLDR(), this.mBottom);
            Path path8 = this.mPath;
            int i35 = this.mLeft;
            path8.quadTo(i35, this.mBottom, i35, r4 - getLDR());
            this.mPath.lineTo(this.mLeft, this.mTop + getLTR());
            if (iMax2 >= getLTR() + this.mArrowDownLeftRadius) {
                this.mPath.quadTo(this.mLeft, this.mTop, r1 + getLTR(), this.mTop);
            } else {
                this.mPath.quadTo(this.mLeft, this.mTop, iMax2 + (this.mLookWidth / 2.0f), r3 - this.mLookLength);
            }
        } else if (i17 == 3) {
            if (iMax >= getLTR() + this.mArrowDownRightRadius) {
                this.mPath.moveTo(this.mLeft, iMax - r2);
                Path path9 = this.mPath;
                int i36 = this.mArrowDownRightRadius;
                int i37 = this.mLookLength;
                int i38 = this.mLookWidth;
                path9.rCubicTo(0.0f, i36, -i37, ((i38 / 2.0f) - this.mArrowTopRightRadius) + i36, -i37, (i38 / 2.0f) + i36);
            } else {
                this.mPath.moveTo(this.mLeft - this.mLookLength, iMax + (this.mLookWidth / 2.0f));
            }
            int i39 = this.mLookWidth + iMax;
            int ldr = this.mBottom - getLDR();
            int i40 = this.mArrowDownLeftRadius;
            if (i39 < ldr - i40) {
                Path path10 = this.mPath;
                float f3 = this.mArrowTopLeftRadius;
                int i41 = this.mLookLength;
                int i42 = this.mLookWidth;
                path10.rCubicTo(0.0f, f3, i41, i42 / 2.0f, i41, (i42 / 2.0f) + i40);
                this.mPath.lineTo(this.mLeft, this.mBottom - getLDR());
            }
            this.mPath.quadTo(this.mLeft, this.mBottom, r2 + getLDR(), this.mBottom);
            this.mPath.lineTo(this.mRight - getRDR(), this.mBottom);
            Path path11 = this.mPath;
            int i43 = this.mRight;
            path11.quadTo(i43, this.mBottom, i43, r4 - getRDR());
            this.mPath.lineTo(this.mRight, this.mTop + getRTR());
            this.mPath.quadTo(this.mRight, this.mTop, r2 - getRTR(), this.mTop);
            this.mPath.lineTo(this.mLeft + getLTR(), this.mTop);
            if (iMax >= getLTR() + this.mArrowDownRightRadius) {
                Path path12 = this.mPath;
                int i44 = this.mLeft;
                path12.quadTo(i44, this.mTop, i44, r3 + getLTR());
            } else {
                this.mPath.quadTo(this.mLeft, this.mTop, r2 - this.mLookLength, iMax + (this.mLookWidth / 2.0f));
            }
        } else if (i17 == 4) {
            if (iMax >= getRTR() + this.mArrowDownLeftRadius) {
                this.mPath.moveTo(this.mRight, iMax - r2);
                Path path13 = this.mPath;
                int i45 = this.mArrowDownLeftRadius;
                int i46 = this.mLookLength;
                int i47 = this.mLookWidth;
                path13.rCubicTo(0.0f, i45, i46, ((i47 / 2.0f) - this.mArrowTopLeftRadius) + i45, i46, (i47 / 2.0f) + i45);
            } else {
                this.mPath.moveTo(this.mRight + this.mLookLength, iMax + (this.mLookWidth / 2.0f));
            }
            int i48 = this.mLookWidth + iMax;
            int rdr2 = this.mBottom - getRDR();
            int i49 = this.mArrowDownRightRadius;
            if (i48 < rdr2 - i49) {
                Path path14 = this.mPath;
                float f4 = this.mArrowTopRightRadius;
                int i50 = this.mLookLength;
                int i51 = this.mLookWidth;
                path14.rCubicTo(0.0f, f4, -i50, i51 / 2.0f, -i50, (i51 / 2.0f) + i49);
                this.mPath.lineTo(this.mRight, this.mBottom - getRDR());
            }
            this.mPath.quadTo(this.mRight, this.mBottom, r2 - getRDR(), this.mBottom);
            this.mPath.lineTo(this.mLeft + getLDR(), this.mBottom);
            Path path15 = this.mPath;
            int i52 = this.mLeft;
            path15.quadTo(i52, this.mBottom, i52, r4 - getLDR());
            this.mPath.lineTo(this.mLeft, this.mTop + getLTR());
            this.mPath.quadTo(this.mLeft, this.mTop, r2 + getLTR(), this.mTop);
            this.mPath.lineTo(this.mRight - getRTR(), this.mTop);
            if (iMax >= getRTR() + this.mArrowDownLeftRadius) {
                Path path16 = this.mPath;
                int i53 = this.mRight;
                path16.quadTo(i53, this.mTop, i53, r3 + getRTR());
            } else {
                this.mPath.quadTo(this.mRight, this.mTop, r2 + this.mLookLength, iMax + (this.mLookWidth / 2.0f));
            }
        }
        this.mPath.close();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.mPath, this.mPaint);
        if (this.mBubbleImageBg != null) {
            this.mPath.computeBounds(this.mBubbleImageBgDstRectF, true);
            int iSaveLayer = canvas.saveLayer(this.mBubbleImageBgDstRectF, null, 31);
            canvas.drawPath(this.mPath, this.mBubbleImageBgBeforePaint);
            float fWidth = this.mBubbleImageBgDstRectF.width() / this.mBubbleImageBgDstRectF.height();
            if (fWidth > (this.mBubbleImageBg.getWidth() * 1.0f) / this.mBubbleImageBg.getHeight()) {
                int height = (int) ((this.mBubbleImageBg.getHeight() - (this.mBubbleImageBg.getWidth() / fWidth)) / 2.0f);
                this.mBubbleImageBgSrcRect.set(0, height, this.mBubbleImageBg.getWidth(), ((int) (this.mBubbleImageBg.getWidth() / fWidth)) + height);
            } else {
                int width = (int) ((this.mBubbleImageBg.getWidth() - (this.mBubbleImageBg.getHeight() * fWidth)) / 2.0f);
                this.mBubbleImageBgSrcRect.set(width, 0, ((int) (this.mBubbleImageBg.getHeight() * fWidth)) + width, this.mBubbleImageBg.getHeight());
            }
            canvas.drawBitmap(this.mBubbleImageBg, this.mBubbleImageBgSrcRect, this.mBubbleImageBgDstRectF, this.mBubbleImageBgPaint);
            canvas.restoreToCount(iSaveLayer);
        }
        if (this.mBubbleBorderSize != 0) {
            canvas.drawPath(this.mPath, this.mBubbleBorderPaint);
        }
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public Path getPath() {
        return this.mPath;
    }

    public Look getLook() {
        return this.mLook;
    }

    public int getLookPosition() {
        return this.mLookPosition;
    }

    public int getLookWidth() {
        return this.mLookWidth;
    }

    public int getLookLength() {
        return this.mLookLength;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public int getShadowRadius() {
        return this.mShadowRadius;
    }

    public int getShadowX() {
        return this.mShadowX;
    }

    public int getShadowY() {
        return this.mShadowY;
    }

    public int getBubbleRadius() {
        return this.mBubbleRadius;
    }

    public int getBubbleColor() {
        return this.mBubbleColor;
    }

    public void setBubbleColor(int i) {
        this.mBubbleColor = i;
    }

    public void setLook(Look look) {
        this.mLook = look;
        initPadding();
    }

    public void setLookPosition(int i) {
        this.mLookPosition = i;
    }

    public void setLookPositionCenter(boolean z) {
        this.isLookPositionCenter = z;
    }

    public void setLookWidth(int i) {
        this.mLookWidth = i;
    }

    public void setLookLength(int i) {
        this.mLookLength = i;
        initPadding();
    }

    public void setShadowColor(int i) {
        this.mShadowColor = i;
    }

    public void setShadowRadius(int i) {
        this.mShadowRadius = i;
    }

    public void setShadowX(int i) {
        this.mShadowX = i;
    }

    public void setShadowY(int i) {
        this.mShadowY = i;
    }

    public void setBubbleRadius(int i) {
        this.mBubbleRadius = i;
    }

    public int getLTR() {
        int i = this.mLTR;
        return i == -1 ? this.mBubbleRadius : i;
    }

    public void setLTR(int i) {
        this.mLTR = i;
    }

    public int getRTR() {
        int i = this.mRTR;
        return i == -1 ? this.mBubbleRadius : i;
    }

    public void setRTR(int i) {
        this.mRTR = i;
    }

    public int getRDR() {
        int i = this.mRDR;
        return i == -1 ? this.mBubbleRadius : i;
    }

    public void setRDR(int i) {
        this.mRDR = i;
    }

    public int getLDR() {
        int i = this.mLDR;
        return i == -1 ? this.mBubbleRadius : i;
    }

    public void setLDR(int i) {
        this.mLDR = i;
    }

    public int getArrowTopLeftRadius() {
        return this.mArrowTopLeftRadius;
    }

    public void setArrowTopLeftRadius(int i) {
        this.mArrowTopLeftRadius = i;
    }

    public int getArrowTopRightRadius() {
        return this.mArrowTopRightRadius;
    }

    public void setArrowTopRightRadius(int i) {
        this.mArrowTopRightRadius = i;
    }

    public int getArrowDownLeftRadius() {
        return this.mArrowDownLeftRadius;
    }

    public void setArrowDownLeftRadius(int i) {
        this.mArrowDownLeftRadius = i;
    }

    public int getArrowDownRightRadius() {
        return this.mArrowDownRightRadius;
    }

    public void setArrowDownRightRadius(int i) {
        this.mArrowDownRightRadius = i;
    }

    public void setBubblePadding(int i) {
        this.mBubblePadding = i;
    }

    public void setBubbleImageBg(Bitmap bitmap) {
        this.mBubbleImageBg = bitmap;
    }

    public void setBubbleImageBgRes(int i) {
        this.mBubbleImageBg = BitmapFactory.decodeResource(getResources(), i);
    }

    public void setBubbleBorderSize(int i) {
        this.mBubbleBorderSize = i;
    }

    public void setBubbleBorderColor(int i) {
        this.mBubbleBorderColor = i;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mLookPosition", this.mLookPosition);
        bundle.putInt("mLookWidth", this.mLookWidth);
        bundle.putInt("mLookLength", this.mLookLength);
        bundle.putInt("mShadowColor", this.mShadowColor);
        bundle.putInt("mShadowRadius", this.mShadowRadius);
        bundle.putInt("mShadowX", this.mShadowX);
        bundle.putInt("mShadowY", this.mShadowY);
        bundle.putInt("mBubbleRadius", this.mBubbleRadius);
        bundle.putInt("mLTR", this.mLTR);
        bundle.putInt("mRTR", this.mRTR);
        bundle.putInt("mRDR", this.mRDR);
        bundle.putInt("mLDR", this.mLDR);
        bundle.putInt("mBubblePadding", this.mBubblePadding);
        bundle.putInt("mArrowTopLeftRadius", this.mArrowTopLeftRadius);
        bundle.putInt("mArrowTopRightRadius", this.mArrowTopRightRadius);
        bundle.putInt("mArrowDownLeftRadius", this.mArrowDownLeftRadius);
        bundle.putInt("mArrowDownRightRadius", this.mArrowDownRightRadius);
        bundle.putInt("mWidth", this.mWidth);
        bundle.putInt("mHeight", this.mHeight);
        bundle.putInt("mLeft", this.mLeft);
        bundle.putInt("mTop", this.mTop);
        bundle.putInt("mRight", this.mRight);
        bundle.putInt("mBottom", this.mBottom);
        bundle.putInt("mBubbleBgRes", this.mBubbleBgRes);
        bundle.putInt("mBubbleBorderColor", this.mBubbleBorderColor);
        bundle.putInt("mBubbleBorderSize", this.mBubbleBorderSize);
        return bundle;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.mLookPosition = bundle.getInt("mLookPosition");
            this.mLookWidth = bundle.getInt("mLookWidth");
            this.mLookLength = bundle.getInt("mLookLength");
            this.mShadowColor = bundle.getInt("mShadowColor");
            this.mShadowRadius = bundle.getInt("mShadowRadius");
            this.mShadowX = bundle.getInt("mShadowX");
            this.mShadowY = bundle.getInt("mShadowY");
            this.mBubbleRadius = bundle.getInt("mBubbleRadius");
            this.mLTR = bundle.getInt("mLTR");
            this.mRTR = bundle.getInt("mRTR");
            this.mRDR = bundle.getInt("mRDR");
            this.mLDR = bundle.getInt("mLDR");
            this.mBubblePadding = bundle.getInt("mBubblePadding");
            this.mArrowTopLeftRadius = bundle.getInt("mArrowTopLeftRadius");
            this.mArrowTopRightRadius = bundle.getInt("mArrowTopRightRadius");
            this.mArrowDownLeftRadius = bundle.getInt("mArrowDownLeftRadius");
            this.mArrowDownRightRadius = bundle.getInt("mArrowDownRightRadius");
            this.mWidth = bundle.getInt("mWidth");
            this.mHeight = bundle.getInt("mHeight");
            this.mLeft = bundle.getInt("mLeft");
            this.mTop = bundle.getInt("mTop");
            this.mRight = bundle.getInt("mRight");
            this.mBottom = bundle.getInt("mBottom");
            int i = bundle.getInt("mBubbleBgRes");
            this.mBubbleBgRes = i;
            if (i != -1) {
                this.mBubbleImageBg = BitmapFactory.decodeResource(getResources(), this.mBubbleBgRes);
            }
            this.mBubbleBorderSize = bundle.getInt("mBubbleBorderSize");
            this.mBubbleBorderColor = bundle.getInt("mBubbleBorderColor");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }
}
