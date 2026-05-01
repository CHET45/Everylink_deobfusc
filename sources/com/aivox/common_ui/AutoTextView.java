package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.aivox.base.C0874R;

/* JADX INFO: loaded from: classes.dex */
public class AutoTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private int mColor;
    private Context mContext;
    private float mHeight;
    private Rotate3dAnimation mInDown;
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutDown;
    private Rotate3dAnimation mOutUp;

    public AutoTextView(Context context) {
        this(context, null);
    }

    public AutoTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.auto3d);
        this.mHeight = typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.auto3d_textSize, C0874R.dimen.text_size_12);
        this.mColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.auto3d_textColor, getResources().getColor(C0874R.color.white));
        typedArrayObtainStyledAttributes.recycle();
        this.mContext = context;
        init();
    }

    private void init() {
        setFactory(this);
        this.mInUp = createAnim(-90.0f, 0.0f, true, true);
        this.mOutUp = createAnim(0.0f, 90.0f, false, true);
        this.mInDown = createAnim(90.0f, 0.0f, true, false);
        this.mOutDown = createAnim(0.0f, -90.0f, false, false);
        setInAnimation(this.mInUp);
        setOutAnimation(this.mOutUp);
    }

    private Rotate3dAnimation createAnim(float f, float f2, boolean z, boolean z2) {
        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(f, f2, z, z2);
        rotate3dAnimation.setDuration(800L);
        rotate3dAnimation.setFillAfter(false);
        rotate3dAnimation.setInterpolator(new AccelerateInterpolator());
        return rotate3dAnimation;
    }

    @Override // android.widget.ViewSwitcher.ViewFactory
    public View makeView() {
        TextView textView = new TextView(this.mContext);
        textView.setGravity(17);
        textView.setTextColor(this.mColor);
        textView.setMaxLines(1);
        return textView;
    }

    public void previous() {
        Animation inAnimation = getInAnimation();
        Rotate3dAnimation rotate3dAnimation = this.mInDown;
        if (inAnimation != rotate3dAnimation) {
            setInAnimation(rotate3dAnimation);
        }
        Animation outAnimation = getOutAnimation();
        Rotate3dAnimation rotate3dAnimation2 = this.mOutDown;
        if (outAnimation != rotate3dAnimation2) {
            setOutAnimation(rotate3dAnimation2);
        }
    }

    public void next() {
        Animation inAnimation = getInAnimation();
        Rotate3dAnimation rotate3dAnimation = this.mInUp;
        if (inAnimation != rotate3dAnimation) {
            setInAnimation(rotate3dAnimation);
        }
        Animation outAnimation = getOutAnimation();
        Rotate3dAnimation rotate3dAnimation2 = this.mOutUp;
        if (outAnimation != rotate3dAnimation2) {
            setOutAnimation(rotate3dAnimation2);
        }
    }

    class Rotate3dAnimation extends Animation {
        private Camera mCamera;
        private float mCenterX;
        private float mCenterY;
        private final float mFromDegrees;
        private final float mToDegrees;
        private final boolean mTurnIn;
        private final boolean mTurnUp;

        public Rotate3dAnimation(float f, float f2, boolean z, boolean z2) {
            this.mFromDegrees = f;
            this.mToDegrees = f2;
            this.mTurnIn = z;
            this.mTurnUp = z2;
        }

        @Override // android.view.animation.Animation
        public void initialize(int i, int i2, int i3, int i4) {
            super.initialize(i, i2, i3, i4);
            this.mCamera = new Camera();
            this.mCenterY = AutoTextView.this.getHeight() / 2;
            this.mCenterX = AutoTextView.this.getWidth() / 2;
        }

        @Override // android.view.animation.Animation
        protected void applyTransformation(float f, Transformation transformation) {
            float f2 = this.mFromDegrees;
            float f3 = f2 + ((this.mToDegrees - f2) * f);
            float f4 = this.mCenterX;
            float f5 = this.mCenterY;
            Camera camera = this.mCamera;
            int i = this.mTurnUp ? 1 : -1;
            Matrix matrix = transformation.getMatrix();
            camera.save();
            if (this.mTurnIn) {
                camera.translate(0.0f, i * this.mCenterY * (f - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, i * this.mCenterY * f, 0.0f);
            }
            camera.rotateX(f3);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-f4, -f5);
            matrix.postTranslate(f4, f5);
        }
    }
}
