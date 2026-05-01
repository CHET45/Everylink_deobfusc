package com.lzy.okgo.callback;

import com.lzy.okgo.convert.FileConvert;
import java.io.File;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public abstract class FileCallback extends AbsCallback<File> {
    private FileConvert convert;

    public FileCallback() {
        this(null);
    }

    public FileCallback(String str) {
        this(null, str);
    }

    public FileCallback(String str, String str2) {
        FileConvert fileConvert = new FileConvert(str, str2);
        this.convert = fileConvert;
        fileConvert.setCallback(this);
    }

    @Override // com.lzy.okgo.convert.Converter
    public File convertSuccess(Response response) throws Exception {
        File fileConvertSuccess = this.convert.convertSuccess(response);
        response.close();
        return fileConvertSuccess;
    }
}
