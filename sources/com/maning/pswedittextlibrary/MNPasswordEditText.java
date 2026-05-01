package com.maning.pswedittextlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes4.dex */
public class MNPasswordEditText extends EditText {
    private static final String TAG = "MNPasswordEditText";
    private int backgroundColor;
    private int borderColor;
    private float borderRadius;
    private int borderSelectedColor;
    private float borderWidth;
    private Bitmap coverBitmap;
    private int coverBitmapID;
    private float coverBitmapWidth;
    private int coverCirclrColor;
    private float coverCirclrRadius;
    private String coverText;
    private int editTextStyle;
    private GradientDrawable gradientDrawable;
    private int inputMode;
    private float itemMargin;
    private Context mContext;
    private Paint mPaintLine;
    private Paint mPaintText;
    private int maxLength;
    private OnTextChangeListener onTextChangeListener;
    private int textColor;

    public interface OnTextChangeListener {
        void onTextChange(String str, boolean z);
    }

    public MNPasswordEditText(Context context) {
        this(context, null);
    }

    public MNPasswordEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MNPasswordEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.gradientDrawable = new GradientDrawable();
        this.mContext = context;
        initAttrs(attributeSet, i);
        init();
    }

    private void initAttrs(AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, C2329R.styleable.MNPasswordEditText, i, 0);
        this.backgroundColor = typedArrayObtainStyledAttributes.getColor(C2329R.styleable.MNPasswordEditText_mnPsw_background_color, Color.parseColor("#FFFFFF"));
        this.borderColor = typedArrayObtainStyledAttributes.getColor(C2329R.styleable.MNPasswordEditText_mnPsw_border_color, Color.parseColor("#FF0000"));
        this.borderSelectedColor = typedArrayObtainStyledAttributes.getColor(C2329R.styleable.MNPasswordEditText_mnPsw_border_selected_color, 0);
        this.textColor = typedArrayObtainStyledAttributes.getColor(C2329R.styleable.MNPasswordEditText_mnPsw_text_color, Color.parseColor("#FF0000"));
        this.borderRadius = typedArrayObtainStyledAttributes.getDimension(C2329R.styleable.MNPasswordEditText_mnPsw_border_radius, dip2px(6.0f));
        this.borderWidth = typedArrayObtainStyledAttributes.getDimension(C2329R.styleable.MNPasswordEditText_mnPsw_border_width, dip2px(1.0f));
        this.itemMargin = typedArrayObtainStyledAttributes.getDimension(C2329R.styleable.MNPasswordEditText_mnPsw_item_margin, dip2px(10.0f));
        this.inputMode = typedArrayObtainStyledAttributes.getInt(C2329R.styleable.MNPasswordEditText_mnPsw_mode, 1);
        this.editTextStyle = typedArrayObtainStyledAttributes.getInt(C2329R.styleable.MNPasswordEditText_mnPsw_style, 1);
        this.coverBitmapID = typedArrayObtainStyledAttributes.getResourceId(C2329R.styleable.MNPasswordEditText_mnPsw_cover_bitmap_id, -1);
        String string = typedArrayObtainStyledAttributes.getString(C2329R.styleable.MNPasswordEditText_mnPsw_cover_text);
        this.coverText = string;
        if (TextUtils.isEmpty(string)) {
            this.coverText = "密";
        }
        this.coverCirclrColor = typedArrayObtainStyledAttributes.getColor(C2329R.styleable.MNPasswordEditText_mnPsw_cover_circle_color, Color.parseColor("#FF0000"));
        this.coverCirclrRadius = typedArrayObtainStyledAttributes.getDimension(C2329R.styleable.MNPasswordEditText_mnPsw_cover_circle_radius, 0.0f);
        this.coverBitmapWidth = typedArrayObtainStyledAttributes.getDimension(C2329R.styleable.MNPasswordEditText_mnPsw_cover_bitmap_width, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void init() {
        this.maxLength = getMaxLength();
        setCursorVisible(false);
        setTextColor(0);
        setFocusableInTouchMode(true);
        setOnLongClickListener(new View.OnLongClickListener() { // from class: com.maning.pswedittextlibrary.MNPasswordEditText.1
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                return true;
            }
        });
        Paint paint = new Paint(1);
        this.mPaintText = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaintText.setColor(this.textColor);
        this.mPaintText.setTextSize(getTextSize());
        Paint paint2 = new Paint(1);
        this.mPaintLine = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.mPaintLine.setColor(this.borderColor);
        this.mPaintLine.setStrokeWidth(this.borderWidth);
        if (this.inputMode == 2) {
            if (this.coverBitmapID == -1) {
                throw new NullPointerException("遮盖图片为空");
            }
            this.coverBitmap = BitmapFactory.decodeResource(getContext().getResources(), this.coverBitmapID);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmapDrawableToBitmap;
        super.onDraw(canvas);
        int measuredWidth = getMeasuredWidth();
        float measuredHeight = getMeasuredHeight();
        float f = this.itemMargin;
        float f2 = (measuredWidth - ((r3 - 1) * f)) / this.maxLength;
        int i = this.editTextStyle;
        if (i == 1) {
            this.gradientDrawable.setStroke((int) this.borderWidth, this.borderColor);
            this.gradientDrawable.setCornerRadius(this.borderRadius);
            this.gradientDrawable.setColor(this.backgroundColor);
            setBackground(this.gradientDrawable);
            f2 = measuredWidth / this.maxLength;
            for (int i2 = 1; i2 < this.maxLength; i2++) {
                float f3 = f2 * i2;
                canvas.drawLine(f3, 0.0f, f3, measuredHeight, this.mPaintLine);
            }
            f = 0.0f;
        } else if (i == 2) {
            this.gradientDrawable.setStroke((int) this.borderWidth, this.borderColor);
            this.gradientDrawable.setCornerRadius(this.borderRadius);
            this.gradientDrawable.setColor(this.backgroundColor);
            int i3 = (int) f2;
            int i4 = (int) measuredHeight;
            Bitmap bitmapDrawableToBitmap2 = drawableToBitmap(this.gradientDrawable, i3, i4);
            int i5 = this.borderSelectedColor;
            if (i5 != 0) {
                this.gradientDrawable.setStroke((int) this.borderWidth, i5);
                bitmapDrawableToBitmap = drawableToBitmap(this.gradientDrawable, i3, i4);
            } else {
                bitmapDrawableToBitmap = null;
            }
            for (int i6 = 0; i6 < this.maxLength; i6++) {
                float f4 = i6;
                float f5 = (f2 * f4) + (f4 * f);
                if (bitmapDrawableToBitmap == null) {
                    canvas.drawBitmap(bitmapDrawableToBitmap2, f5, 0.0f, this.mPaintLine);
                } else if (getText().length() == i6) {
                    canvas.drawBitmap(bitmapDrawableToBitmap, f5, 0.0f, this.mPaintLine);
                } else {
                    canvas.drawBitmap(bitmapDrawableToBitmap2, f5, 0.0f, this.mPaintLine);
                }
            }
        } else if (i == 3) {
            for (int i7 = 0; i7 < this.maxLength; i7++) {
                if (this.borderSelectedColor != 0) {
                    if (getText().length() == i7) {
                        this.mPaintLine.setColor(this.borderSelectedColor);
                    } else {
                        this.mPaintLine.setColor(this.borderColor);
                    }
                } else {
                    this.mPaintLine.setColor(this.borderColor);
                }
                float f6 = i7;
                float f7 = (f2 * f6) + (this.itemMargin * f6);
                float f8 = measuredHeight - this.borderWidth;
                canvas.drawLine(f7, f8, f7 + f2, f8, this.mPaintLine);
            }
        }
        String string = getText().toString();
        for (int i8 = 0; i8 < this.maxLength; i8++) {
            if (!TextUtils.isEmpty(string) && i8 < string.length()) {
                int i9 = this.inputMode;
                if (i9 == 1) {
                    float f9 = f2 * 0.5f * 0.5f;
                    float f10 = measuredHeight / 2.0f;
                    if (f9 > f10) {
                        f9 = measuredHeight * 0.5f * 0.5f;
                    }
                    float f11 = this.coverCirclrRadius;
                    if (f11 > 0.0f) {
                        f9 = f11;
                    }
                    float f12 = i8;
                    this.mPaintText.setColor(this.coverCirclrColor);
                    canvas.drawCircle((f2 / 2.0f) + (f2 * f12) + (f12 * f), f10, f9, this.mPaintText);
                } else if (i9 == 2) {
                    float f13 = 0.5f * f2;
                    float f14 = this.coverBitmapWidth;
                    if (f14 > 0.0f) {
                        f13 = f14;
                    }
                    float f15 = i8;
                    float f16 = ((f2 - f13) / 2.0f) + (f2 * f15) + (f15 * f);
                    float f17 = (measuredHeight - f13) / 2.0f;
                    int i10 = (int) f13;
                    canvas.drawBitmap(Bitmap.createScaledBitmap(this.coverBitmap, i10, i10, true), f16, f17, this.mPaintText);
                } else if (i9 == 3) {
                    float fontWidth = getFontWidth(this.mPaintText, this.coverText);
                    float fontHeight = getFontHeight(this.mPaintText, this.coverText);
                    float f18 = i8;
                    this.mPaintText.setColor(this.textColor);
                    canvas.drawText(this.coverText, ((f2 - fontWidth) / 2.0f) + (f2 * f18) + (f18 * f), ((fontHeight + measuredHeight) / 2.0f) - 6.0f, this.mPaintText);
                } else {
                    String strValueOf = String.valueOf(string.charAt(i8));
                    float f19 = i8;
                    float fontWidth2 = ((f2 - getFontWidth(this.mPaintText, strValueOf)) / 2.0f) + (f2 * f19) + (f19 * f);
                    float fontHeight2 = (getFontHeight(this.mPaintText, strValueOf) + measuredHeight) / 2.0f;
                    this.mPaintText.setColor(this.textColor);
                    canvas.drawText(strValueOf, fontWidth2, fontHeight2, this.mPaintText);
                }
            }
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int i, int i2) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, i, i2);
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        invalidate();
        if (this.onTextChangeListener != null) {
            if (getText().toString().length() == getMaxLength()) {
                this.onTextChangeListener.onTextChange(getText().toString(), true);
            } else {
                this.onTextChangeListener.onTextChange(getText().toString(), false);
            }
        }
    }

    public float getFontWidth(Paint paint, String str) {
        paint.getTextBounds(str, 0, str.length(), new Rect());
        return r0.width();
    }

    public float getFontHeight(Paint paint, String str) {
        paint.getTextBounds(str, 0, str.length(), new Rect());
        return r0.height();
    }

    public int getMaxLength() {
        int iIntValue;
        Exception e;
        try {
            iIntValue = 0;
            for (InputFilter inputFilter : getFilters()) {
                try {
                    Class<?> cls = inputFilter.getClass();
                    if (cls.getName().equals("android.text.InputFilter$LengthFilter")) {
                        for (Field field : cls.getDeclaredFields()) {
                            if (field.getName().equals("mMax")) {
                                field.setAccessible(true);
                                iIntValue = ((Integer) field.get(inputFilter)).intValue();
                            }
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return iIntValue;
                }
            }
        } catch (Exception e3) {
            iIntValue = 0;
            e = e3;
        }
        return iIntValue;
    }

    private int dip2px(float f) {
        return (int) ((f * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }
}
