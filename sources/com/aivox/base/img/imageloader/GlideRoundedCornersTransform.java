package com.aivox.base.img.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public class GlideRoundedCornersTransform extends CenterCrop {

    /* JADX INFO: renamed from: ID */
    private static final String f182ID = "GlideRoundedCornersTransform.1";
    private static final byte[] ID_BYTES = f182ID.getBytes(CHARSET);
    private static final int VERSION = 1;
    private CornerType mCornerType;
    private float mRadius;

    public enum CornerType {
        ALL,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        TOP_LEFT_BOTTOM_RIGHT,
        TOP_RIGHT_BOTTOM_LEFT,
        TOP_LEFT_TOP_RIGHT_BOTTOM_RIGHT,
        TOP_RIGHT_BOTTOM_RIGHT_BOTTOM_LEFT,
        TOP_LEFT_TOP_RIGHT
    }

    public GlideRoundedCornersTransform(float f, CornerType cornerType) {
        this.mRadius = f;
        this.mCornerType = cornerType;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.CenterCrop, com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    protected Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i2) {
        return roundCrop(bitmapPool, super.transform(bitmapPool, bitmap, i, i2));
    }

    private Bitmap roundCrop(BitmapPool bitmapPool, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapCreateBitmap = bitmapPool.get(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        if (bitmapCreateBitmap == null) {
            bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        drawRoundRect(canvas, paint, new Path(), width, height);
        return bitmapCreateBitmap;
    }

    /* JADX INFO: renamed from: com.aivox.base.img.imageloader.GlideRoundedCornersTransform$1 */
    static /* synthetic */ class C09061 {

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$base$img$imageloader$GlideRoundedCornersTransform$CornerType */
        static final /* synthetic */ int[] f183xbf297ae3;

        static {
            int[] iArr = new int[CornerType.values().length];
            f183xbf297ae3 = iArr;
            try {
                iArr[CornerType.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f183xbf297ae3[CornerType.TOP_LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f183xbf297ae3[CornerType.TOP_RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f183xbf297ae3[CornerType.BOTTOM_RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f183xbf297ae3[CornerType.BOTTOM_LEFT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f183xbf297ae3[CornerType.TOP.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f183xbf297ae3[CornerType.BOTTOM.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f183xbf297ae3[CornerType.LEFT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f183xbf297ae3[CornerType.RIGHT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f183xbf297ae3[CornerType.TOP_LEFT_BOTTOM_RIGHT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f183xbf297ae3[CornerType.TOP_RIGHT_BOTTOM_LEFT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f183xbf297ae3[CornerType.TOP_LEFT_TOP_RIGHT_BOTTOM_RIGHT.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f183xbf297ae3[CornerType.TOP_RIGHT_BOTTOM_RIGHT_BOTTOM_LEFT.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f183xbf297ae3[CornerType.TOP_LEFT_TOP_RIGHT.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    private void drawRoundRect(Canvas canvas, Paint paint, Path path, int i, int i2) {
        switch (C09061.f183xbf297ae3[this.mCornerType.ordinal()]) {
            case 1:
                float f = this.mRadius;
                drawPath(new float[]{f, f, f, f, f, f, f, f}, canvas, paint, path, i, i2);
                return;
            case 2:
                float f2 = this.mRadius;
                drawPath(new float[]{f2, f2, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, canvas, paint, path, i, i2);
                return;
            case 3:
                float f3 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, f3, f3, 0.0f, 0.0f, 0.0f, 0.0f}, canvas, paint, path, i, i2);
                return;
            case 4:
                float f4 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, 0.0f, 0.0f, f4, f4, 0.0f, 0.0f}, canvas, paint, path, i, i2);
                return;
            case 5:
                float f5 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f5, f5}, canvas, paint, path, i, i2);
                return;
            case 6:
                float f6 = this.mRadius;
                drawPath(new float[]{f6, f6, f6, f6, 0.0f, 0.0f, 0.0f, 0.0f}, canvas, paint, path, i, i2);
                return;
            case 7:
                float f7 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, 0.0f, 0.0f, f7, f7, f7, f7}, canvas, paint, path, i, i2);
                return;
            case 8:
                float f8 = this.mRadius;
                drawPath(new float[]{f8, f8, 0.0f, 0.0f, 0.0f, 0.0f, f8, f8}, canvas, paint, path, i, i2);
                return;
            case 9:
                float f9 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, f9, f9, f9, f9, 0.0f, 0.0f}, canvas, paint, path, i, i2);
                return;
            case 10:
                float f10 = this.mRadius;
                drawPath(new float[]{f10, f10, 0.0f, 0.0f, f10, f10, 0.0f, 0.0f}, canvas, paint, path, i, i2);
                return;
            case 11:
                float f11 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, f11, f11, 0.0f, 0.0f, f11, f11}, canvas, paint, path, i, i2);
                return;
            case 12:
                float f12 = this.mRadius;
                drawPath(new float[]{f12, f12, f12, f12, f12, f12, 0.0f, 0.0f}, canvas, paint, path, i, i2);
                return;
            case 13:
                float f13 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, f13, f13, f13, f13, f13, f13}, canvas, paint, path, i, i2);
                return;
            case 14:
                float f14 = this.mRadius;
                drawPath(new float[]{f14, f14, f14, f14, 0.0f, 0.0f, 0.0f, 0.0f}, canvas, paint, path, i, i2);
                return;
            default:
                throw new RuntimeException("RoundedCorners type not belong to CornerType");
        }
    }

    private void drawPath(float[] fArr, Canvas canvas, Paint paint, Path path, int i, int i2) {
        path.addRoundRect(new RectF(0.0f, 0.0f, i, i2), fArr, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    @Override // com.bumptech.glide.load.resource.bitmap.CenterCrop, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return obj instanceof GlideRoundedCornersTransform;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.CenterCrop, com.bumptech.glide.load.Key
    public int hashCode() {
        return f182ID.hashCode();
    }

    @Override // com.bumptech.glide.load.resource.bitmap.CenterCrop, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
