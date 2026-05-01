package com.aivox.base.img.imageloader;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class S3BucketDataFetcher implements DataFetcher<InputStream> {
    private static final String TAG = "S3BucketDataFetcher";
    private InputStream inputStream;
    private final String model;

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
    }

    public S3BucketDataFetcher(String str) {
        this.model = str;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void loadData(Priority priority, DataFetcher.DataCallback<? super InputStream> dataCallback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder builderNewBuilder = HttpUrl.parse("https://api.litok.ai/serviceVideoApp/getPreSignUrl").newBuilder();
        builderNewBuilder.addQueryParameter("targetUrl", this.model);
        try {
            Response responseExecute = okHttpClient.newCall(new Request.Builder().url(builderNewBuilder.build().toString()).build()).execute();
            try {
                if (!responseExecute.isSuccessful()) {
                    throw new IOException("Unexpected code " + responseExecute);
                }
                InputStream inputStreamByteStream = okHttpClient.newCall(new Request.Builder().url(new JSONObject(responseExecute.body().string()).getString("data")).build()).execute().body().byteStream();
                this.inputStream = inputStreamByteStream;
                dataCallback.onDataReady(inputStreamByteStream);
                if (responseExecute != null) {
                    responseExecute.close();
                }
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
        try {
            this.inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
