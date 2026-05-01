package com.aivox.app.media;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.media.Image;
import android.util.Log;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class VideoDecoderUtils {
    private static final int COLOR_FormatI420 = 1;
    private static final int COLOR_FormatNV21 = 2;
    public static final int FILE_TypeI420 = 1;
    public static final int FILE_TypeNV21 = 2;
    private static final String TAG = "VideoUtils";
    private static final boolean VERBOSE = true;
    private DataCallBack callback;
    private int outputImageFileType = -1;

    public interface DataCallBack {
        void onFrame(byte[] bArr, int i, int i2);
    }

    public void setOutDataType(int i) {
        Log.d(TAG, "setOutDataType fileType : " + i);
        this.outputImageFileType = i;
    }

    public void setDataCallBack(DataCallBack dataCallBack) {
        this.callback = dataCallBack;
    }

    public void decodeFramesToImage(Image image, int i, int i2) {
        DataCallBack dataCallBack;
        Log.d(TAG, "image format: " + image.getFormat());
        int i3 = this.outputImageFileType;
        if (i3 != -1) {
            if (i3 == 1) {
                DataCallBack dataCallBack2 = this.callback;
                if (dataCallBack2 != null) {
                    dataCallBack2.onFrame(getDataFromImage(image, 1), i, i2);
                }
            } else if (i3 == 2 && (dataCallBack = this.callback) != null) {
                dataCallBack.onFrame(getDataFromImage(image, 2), i, i2);
            }
        }
        image.close();
    }

    private boolean isImageFormatSupported(Image image) {
        int format = image.getFormat();
        return format == 17 || format == 35 || format == 842094169;
    }

    private byte[] getDataFromImage(Image image, int i) {
        int i2;
        int i3 = i;
        int i4 = 2;
        int i5 = 1;
        if (i3 != 1 && i3 != 2) {
            throw new IllegalArgumentException("only support COLOR_FormatI420 and COLOR_FormatNV21");
        }
        if (!isImageFormatSupported(image)) {
            throw new RuntimeException("can't convert Image to byte array, format " + image.getFormat());
        }
        Rect cropRect = image.getCropRect();
        int format = image.getFormat();
        int iWidth = cropRect.width();
        int iHeight = cropRect.height();
        Image.Plane[] planes = image.getPlanes();
        int i6 = iWidth * iHeight;
        byte[] bArr = new byte[(ImageFormat.getBitsPerPixel(format) * i6) / 8];
        int i7 = 0;
        byte[] bArr2 = new byte[planes[0].getRowStride()];
        Log.v(TAG, "get data from " + planes.length + " planes");
        int i8 = 1;
        int i9 = 0;
        int i10 = 0;
        while (i9 < planes.length) {
            if (i9 == 0) {
                i8 = i5;
                i10 = i7;
            } else if (i9 != i5) {
                if (i9 == i4) {
                    if (i3 == i5) {
                        i10 = (int) (((double) i6) * 1.25d);
                        i8 = i5;
                    } else if (i3 == i4) {
                        i8 = i4;
                        i10 = i6;
                    }
                }
            } else if (i3 == i5) {
                i8 = i5;
                i10 = i6;
            } else if (i3 == i4) {
                i10 = i6 + 1;
                i8 = i4;
            }
            ByteBuffer buffer = planes[i9].getBuffer();
            int rowStride = planes[i9].getRowStride();
            int pixelStride = planes[i9].getPixelStride();
            Log.v(TAG, "pixelStride " + pixelStride);
            Log.v(TAG, "rowStride " + rowStride);
            Log.v(TAG, "width " + iWidth);
            Log.v(TAG, "height " + iHeight);
            Log.v(TAG, "buffer size " + buffer.remaining());
            int i11 = i9 == 0 ? 0 : 1;
            int i12 = iWidth >> i11;
            int i13 = iWidth;
            int i14 = iHeight >> i11;
            int i15 = iHeight;
            Image.Plane[] planeArr = planes;
            buffer.position(((cropRect.top >> i11) * rowStride) + ((cropRect.left >> i11) * pixelStride));
            for (int i16 = 0; i16 < i14; i16++) {
                if (pixelStride == 1 && i8 == 1) {
                    buffer.get(bArr, i10, i12);
                    i10 += i12;
                    i2 = i12;
                } else {
                    i2 = ((i12 - 1) * pixelStride) + 1;
                    buffer.get(bArr2, 0, i2);
                    for (int i17 = 0; i17 < i12; i17++) {
                        bArr[i10] = bArr2[i17 * pixelStride];
                        i10 += i8;
                    }
                }
                if (i16 < i14 - 1) {
                    buffer.position((buffer.position() + rowStride) - i2);
                }
            }
            Log.v(TAG, "Finished reading data from plane " + i9);
            i9++;
            i3 = i;
            iWidth = i13;
            iHeight = i15;
            planes = planeArr;
            i4 = 2;
            i5 = 1;
            i7 = 0;
        }
        return bArr;
    }
}
