package com.lzy.okgo.callback;

import android.graphics.Bitmap;
import com.lzy.okgo.convert.BitmapConvert;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BitmapCallback extends AbsCallback<Bitmap> {
    @Override // com.lzy.okgo.convert.Converter
    public Bitmap convertSuccess(Response response) throws Exception {
        Bitmap bitmapConvertSuccess = BitmapConvert.create().convertSuccess(response);
        response.close();
        return bitmapConvertSuccess;
    }
}
