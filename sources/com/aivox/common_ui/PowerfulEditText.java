package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatEditText;

/* JADX INFO: loaded from: classes.dex */
public class PowerfulEditText extends AppCompatEditText {
    private static final String TAG = "PowerfulEditText";
    private static final int TYPE_CAN_CLEAR = 0;
    private static final int TYPE_CAN_WATCH_PWD = 1;
    private static final int TYPE_NORMAL = -1;
    private int eyeCloseResourseId;
    private boolean eyeOpen;
    private int eyeOpenResourseId;
    private int funcType;
    private int leftHeight;
    private int leftWidth;
    private Drawable mEyeOpenDrawable;
    private Drawable mRightDrawable;
    private OnRightClickListener onRightClickListener;
    private int rightHeight;
    private int rightWidth;

    /* JADX INFO: renamed from: ta */
    private TypedArray f267ta;
    private TextListener textListener;

    public interface OnRightClickListener {
        void onClick(EditText editText);
    }

    public interface TextListener {
        void afterTextChanged(Editable editable);

        void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3);

        void onTextChanged(CharSequence charSequence, int i, int i2, int i3);
    }

    public PowerfulEditText(Context context) {
        this(context, null);
    }

    public PowerfulEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.editTextStyle);
    }

    public PowerfulEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.eyeOpen = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.PowerfulEditText);
        this.f267ta = typedArrayObtainStyledAttributes;
        this.funcType = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.PowerfulEditText_funcType, -1);
        this.eyeCloseResourseId = this.f267ta.getResourceId(C1034R.styleable.PowerfulEditText_eyeClose, C1034R.mipmap.eye_close);
        this.eyeOpenResourseId = this.f267ta.getResourceId(C1034R.styleable.PowerfulEditText_eyeOpen, C1034R.mipmap.eye_open);
        init();
    }

    private void init() {
        Drawable drawable = getCompoundDrawables()[0];
        Drawable drawable2 = getCompoundDrawables()[2];
        this.mRightDrawable = drawable2;
        if (drawable2 == null) {
            int i = this.funcType;
            if (i == 0) {
                this.mRightDrawable = getResources().getDrawable(C1034R.drawable.ic_text_clear);
            } else if (i == 1) {
                this.mRightDrawable = getResources().getDrawable(this.eyeCloseResourseId);
                this.mEyeOpenDrawable = getResources().getDrawable(this.eyeOpenResourseId);
            }
        }
        if (drawable != null) {
            this.leftWidth = this.f267ta.getDimensionPixelOffset(C1034R.styleable.PowerfulEditText_leftDrawableWidth, drawable.getIntrinsicWidth());
            int dimensionPixelOffset = this.f267ta.getDimensionPixelOffset(C1034R.styleable.PowerfulEditText_leftDrawableHeight, drawable.getIntrinsicHeight());
            this.leftHeight = dimensionPixelOffset;
            drawable.setBounds(0, 0, this.leftWidth, dimensionPixelOffset);
        }
        if (this.mRightDrawable != null) {
            this.rightWidth = this.f267ta.getDimensionPixelOffset(C1034R.styleable.PowerfulEditText_rightDrawableWidth, this.mRightDrawable.getIntrinsicWidth());
            int dimensionPixelOffset2 = this.f267ta.getDimensionPixelOffset(C1034R.styleable.PowerfulEditText_rightDrawableWidth, this.mRightDrawable.getIntrinsicHeight());
            this.rightHeight = dimensionPixelOffset2;
            this.mRightDrawable.setBounds(0, 0, this.rightWidth, dimensionPixelOffset2);
            Drawable drawable3 = this.mEyeOpenDrawable;
            if (drawable3 != null) {
                drawable3.setBounds(0, 0, this.rightWidth, this.rightHeight);
            }
            if (this.funcType == 0) {
                String strTrim = getText().toString().trim();
                if (!TextUtils.isEmpty(strTrim)) {
                    setRightIconVisible(true);
                    setSelection(strTrim.length());
                } else {
                    setRightIconVisible(false);
                }
            } else {
                setRightIconVisible(true);
            }
            addTextChangedListener(new TextWatcher() { // from class: com.aivox.common_ui.PowerfulEditText.1
                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                    if (PowerfulEditText.this.funcType == 0) {
                        PowerfulEditText.this.setRightIconVisible(charSequence.length() > 0);
                    }
                    if (PowerfulEditText.this.textListener != null) {
                        PowerfulEditText.this.textListener.onTextChanged(charSequence, i2, i3, i4);
                    }
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                    if (PowerfulEditText.this.textListener != null) {
                        PowerfulEditText.this.textListener.beforeTextChanged(charSequence, i2, i3, i4);
                    }
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    if (PowerfulEditText.this.textListener != null) {
                        PowerfulEditText.this.textListener.afterTextChanged(editable);
                    }
                }
            });
        }
        this.f267ta.recycle();
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1 && getCompoundDrawables()[2] != null && motionEvent.getX() > getWidth() - getTotalPaddingRight() && motionEvent.getX() < getWidth() - getPaddingRight()) {
            OnRightClickListener onRightClickListener = this.onRightClickListener;
            if (onRightClickListener == null) {
                int i = this.funcType;
                if (i == 0) {
                    setText("");
                } else if (i == 1) {
                    if (this.eyeOpen) {
                        setTransformationMethod(PasswordTransformationMethod.getInstance());
                        this.eyeOpen = false;
                    } else {
                        setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        this.eyeOpen = true;
                    }
                    switchWatchPwdIcon();
                }
            } else {
                onRightClickListener.onClick(this);
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setRightIconVisible(boolean z) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], z ? this.mRightDrawable : null, getCompoundDrawables()[3]);
    }

    private void switchWatchPwdIcon() {
        if (this.eyeOpen) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], this.mEyeOpenDrawable, getCompoundDrawables()[3]);
        } else {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], this.mRightDrawable, getCompoundDrawables()[3]);
        }
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    public void addTextListener(TextListener textListener) {
        this.textListener = textListener;
    }
}
