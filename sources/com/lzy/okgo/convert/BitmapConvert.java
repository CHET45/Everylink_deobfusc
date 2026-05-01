package com.lzy.okgo.convert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class BitmapConvert implements Converter<Bitmap> {
    public static BitmapConvert create() {
        return ConvertHolder.convert;
    }

    private static class ConvertHolder {
        private static BitmapConvert convert = new BitmapConvert();

        private ConvertHolder() {
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.lzy.okgo.convert.Converter
    public Bitmap convertSuccess(Response response) throws Exception {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }
}
