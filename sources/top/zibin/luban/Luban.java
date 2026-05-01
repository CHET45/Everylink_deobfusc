package top.zibin.luban;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import top.zibin.luban.p042io.ArrayPoolProvide;

/* JADX INFO: loaded from: classes5.dex */
public class Luban implements Handler.Callback {
    private static final String DEFAULT_DISK_CACHE_DIR = "luban_disk_cache";
    private static final String KEY_SOURCE = "source";
    private static final int MSG_COMPRESS_ERROR = 2;
    private static final int MSG_COMPRESS_START = 1;
    private static final int MSG_COMPRESS_SUCCESS = 0;
    private static final String TAG = "Luban";
    private boolean focusAlpha;
    private boolean isUseIOBufferPool;
    private OnCompressListener mCompressListener;
    private CompressionPredicate mCompressionPredicate;
    private Handler mHandler;
    private int mLeastCompressSize;
    private OnNewCompressListener mNewCompressListener;
    private OnRenameListener mRenameListener;
    private List<InputStreamProvider> mStreamProviders;
    private String mTargetDir;

    private Luban(Builder builder) {
        this.mTargetDir = builder.mTargetDir;
        this.focusAlpha = builder.focusAlpha;
        this.isUseIOBufferPool = builder.isUseBufferPool;
        this.mRenameListener = builder.mRenameListener;
        this.mStreamProviders = builder.mStreamProviders;
        this.mCompressListener = builder.mCompressListener;
        this.mNewCompressListener = builder.mNewCompressListener;
        this.mLeastCompressSize = builder.mLeastCompressSize;
        this.mCompressionPredicate = builder.mCompressionPredicate;
        this.mHandler = new Handler(Looper.getMainLooper(), this);
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    private File getImageCacheFile(Context context, String str) {
        if (TextUtils.isEmpty(this.mTargetDir)) {
            this.mTargetDir = getImageCacheDir(context).getAbsolutePath();
        }
        StringBuilder sbAppend = new StringBuilder().append(this.mTargetDir).append("/").append(System.currentTimeMillis()).append((int) (Math.random() * 1000.0d));
        if (TextUtils.isEmpty(str)) {
            str = ".jpg";
        }
        return new File(sbAppend.append(str).toString());
    }

    private File getImageCustomFile(Context context, String str) {
        if (TextUtils.isEmpty(this.mTargetDir)) {
            this.mTargetDir = getImageCacheDir(context).getAbsolutePath();
        }
        return new File(this.mTargetDir + "/" + str);
    }

    private File getImageCacheDir(Context context) {
        return getImageCacheDir(context, DEFAULT_DISK_CACHE_DIR);
    }

    private static File getImageCacheDir(Context context, String str) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir != null) {
            File file = new File(externalCacheDir, str);
            if (file.mkdirs() || (file.exists() && file.isDirectory())) {
                return file;
            }
            return null;
        }
        if (Log.isLoggable(TAG, 6)) {
            Log.e(TAG, "default disk cache dir is null");
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launch(final Context context) {
        List<InputStreamProvider> list = this.mStreamProviders;
        if (list == null || list.size() == 0) {
            OnCompressListener onCompressListener = this.mCompressListener;
            if (onCompressListener != null) {
                onCompressListener.onError(-1, new NullPointerException("image file cannot be null"));
            }
            OnNewCompressListener onNewCompressListener = this.mNewCompressListener;
            if (onNewCompressListener != null) {
                onNewCompressListener.onError("", new NullPointerException("image file cannot be null"));
                return;
            }
            return;
        }
        Iterator<InputStreamProvider> it = this.mStreamProviders.iterator();
        while (it.hasNext()) {
            final InputStreamProvider next = it.next();
            AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() { // from class: top.zibin.luban.Luban.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Luban.this.mHandler.sendMessage(Luban.this.mHandler.obtainMessage(1));
                        File fileCompress = Luban.this.compress(context, next);
                        Message messageObtainMessage = Luban.this.mHandler.obtainMessage(0);
                        messageObtainMessage.arg1 = next.getIndex();
                        messageObtainMessage.obj = fileCompress;
                        Bundle bundle = new Bundle();
                        bundle.putString(Luban.KEY_SOURCE, next.getPath());
                        messageObtainMessage.setData(bundle);
                        Luban.this.mHandler.sendMessage(messageObtainMessage);
                    } catch (Exception unused) {
                        Message messageObtainMessage2 = Luban.this.mHandler.obtainMessage(2);
                        messageObtainMessage2.arg1 = next.getIndex();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString(Luban.KEY_SOURCE, next.getPath());
                        messageObtainMessage2.setData(bundle2);
                        Luban.this.mHandler.sendMessage(messageObtainMessage2);
                    }
                }
            });
            it.remove();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File get(InputStreamProvider inputStreamProvider, Context context) throws IOException {
        try {
            return new Engine(inputStreamProvider, getImageCacheFile(context, Checker.SINGLE.extSuffix(inputStreamProvider)), this.focusAlpha).compress();
        } finally {
            inputStreamProvider.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<File> get(Context context) throws IOException {
        ArrayList arrayList = new ArrayList();
        Iterator<InputStreamProvider> it = this.mStreamProviders.iterator();
        while (it.hasNext()) {
            arrayList.add(compress(context, it.next()));
            it.remove();
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File compress(Context context, InputStreamProvider inputStreamProvider) throws IOException {
        try {
            return compressReal(context, inputStreamProvider);
        } finally {
            inputStreamProvider.close();
        }
    }

    private File compressReal(Context context, InputStreamProvider inputStreamProvider) throws IOException {
        File imageCacheFile = getImageCacheFile(context, Checker.SINGLE.extSuffix(inputStreamProvider));
        String path = Checker.isContent(inputStreamProvider.getPath()) ? LubanUtils.getPath(context, Uri.parse(inputStreamProvider.getPath())) : inputStreamProvider.getPath();
        OnRenameListener onRenameListener = this.mRenameListener;
        if (onRenameListener != null) {
            imageCacheFile = getImageCustomFile(context, onRenameListener.rename(path));
        }
        CompressionPredicate compressionPredicate = this.mCompressionPredicate;
        if (compressionPredicate != null) {
            if (compressionPredicate.apply(path) && Checker.SINGLE.needCompress(this.mLeastCompressSize, path)) {
                return new Engine(inputStreamProvider, imageCacheFile, this.focusAlpha).compress();
            }
            return new File(path);
        }
        if (Checker.SINGLE.needCompress(this.mLeastCompressSize, path)) {
            return new Engine(inputStreamProvider, imageCacheFile, this.focusAlpha).compress();
        }
        return new File(path);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            OnCompressListener onCompressListener = this.mCompressListener;
            if (onCompressListener != null) {
                onCompressListener.onSuccess(message.arg1, (File) message.obj);
            }
            OnNewCompressListener onNewCompressListener = this.mNewCompressListener;
            if (onNewCompressListener == null) {
                return false;
            }
            onNewCompressListener.onSuccess(message.getData().getString(KEY_SOURCE), (File) message.obj);
            return false;
        }
        if (i == 1) {
            OnCompressListener onCompressListener2 = this.mCompressListener;
            if (onCompressListener2 != null) {
                onCompressListener2.onStart();
            }
            OnNewCompressListener onNewCompressListener2 = this.mNewCompressListener;
            if (onNewCompressListener2 == null) {
                return false;
            }
            onNewCompressListener2.onStart();
            return false;
        }
        if (i != 2) {
            return false;
        }
        OnCompressListener onCompressListener3 = this.mCompressListener;
        if (onCompressListener3 != null) {
            onCompressListener3.onError(message.arg1, (Throwable) message.obj);
        }
        OnNewCompressListener onNewCompressListener3 = this.mNewCompressListener;
        if (onNewCompressListener3 == null) {
            return false;
        }
        onNewCompressListener3.onError(message.getData().getString(KEY_SOURCE), (Throwable) message.obj);
        return false;
    }

    public static class Builder {
        private Context context;
        private boolean focusAlpha;
        private OnCompressListener mCompressListener;
        private CompressionPredicate mCompressionPredicate;
        private OnNewCompressListener mNewCompressListener;
        private OnRenameListener mRenameListener;
        private String mTargetDir;
        private boolean isUseBufferPool = true;
        private int mLeastCompressSize = 100;
        private List<InputStreamProvider> mStreamProviders = new ArrayList();

        @Deprecated
        public Builder putGear(int i) {
            return this;
        }

        Builder(Context context) {
            this.context = context;
        }

        private Luban build() {
            return new Luban(this);
        }

        public Builder load(InputStreamProvider inputStreamProvider) {
            this.mStreamProviders.add(inputStreamProvider);
            return this;
        }

        public <T> Builder load(List<T> list) {
            int i = -1;
            for (T t : list) {
                i++;
                if (t instanceof String) {
                    load((String) t, i);
                } else if (t instanceof File) {
                    load((File) t, i);
                } else if (t instanceof Uri) {
                    load((Uri) t, i);
                } else {
                    throw new IllegalArgumentException("Incoming data type exception, it must be String, File, Uri or Bitmap");
                }
            }
            return this;
        }

        public Builder load(File file) {
            load(file, 0);
            return this;
        }

        private Builder load(final File file, final int i) {
            this.mStreamProviders.add(new InputStreamAdapter() { // from class: top.zibin.luban.Luban.Builder.1
                @Override // top.zibin.luban.InputStreamAdapter
                public InputStream openInternal() {
                    return ArrayPoolProvide.getInstance().openInputStream(file.getAbsolutePath());
                }

                @Override // top.zibin.luban.InputStreamProvider
                public int getIndex() {
                    return i;
                }

                @Override // top.zibin.luban.InputStreamProvider
                public String getPath() {
                    return file.getAbsolutePath();
                }
            });
            return this;
        }

        public Builder load(String str) {
            load(str, 0);
            return this;
        }

        private Builder load(final String str, final int i) {
            this.mStreamProviders.add(new InputStreamAdapter() { // from class: top.zibin.luban.Luban.Builder.2
                @Override // top.zibin.luban.InputStreamAdapter
                public InputStream openInternal() {
                    return ArrayPoolProvide.getInstance().openInputStream(str);
                }

                @Override // top.zibin.luban.InputStreamProvider
                public int getIndex() {
                    return i;
                }

                @Override // top.zibin.luban.InputStreamProvider
                public String getPath() {
                    return str;
                }
            });
            return this;
        }

        public Builder load(Uri uri) {
            load(uri, 0);
            return this;
        }

        private Builder load(final Uri uri, final int i) {
            this.mStreamProviders.add(new InputStreamAdapter() { // from class: top.zibin.luban.Luban.Builder.3
                @Override // top.zibin.luban.InputStreamAdapter
                public InputStream openInternal() throws IOException {
                    return Builder.this.isUseBufferPool ? ArrayPoolProvide.getInstance().openInputStream(Builder.this.context.getContentResolver(), uri) : Builder.this.context.getContentResolver().openInputStream(uri);
                }

                @Override // top.zibin.luban.InputStreamProvider
                public int getIndex() {
                    return i;
                }

                @Override // top.zibin.luban.InputStreamProvider
                public String getPath() {
                    return Checker.isContent(uri.toString()) ? uri.toString() : uri.getPath();
                }
            });
            return this;
        }

        public Builder setRenameListener(OnRenameListener onRenameListener) {
            this.mRenameListener = onRenameListener;
            return this;
        }

        public Builder setCompressListener(OnCompressListener onCompressListener) {
            this.mCompressListener = onCompressListener;
            return this;
        }

        public Builder setCompressListener(OnNewCompressListener onNewCompressListener) {
            this.mNewCompressListener = onNewCompressListener;
            return this;
        }

        public Builder setTargetDir(String str) {
            this.mTargetDir = str;
            return this;
        }

        @Deprecated
        public Builder setFocusAlpha(boolean z) {
            this.focusAlpha = z;
            return this;
        }

        public Builder isUseIOBufferPool(boolean z) {
            this.isUseBufferPool = z;
            return this;
        }

        public Builder ignoreBy(int i) {
            this.mLeastCompressSize = i;
            return this;
        }

        public Builder filter(CompressionPredicate compressionPredicate) {
            this.mCompressionPredicate = compressionPredicate;
            return this;
        }

        public void launch() {
            build().launch(this.context);
        }

        public File get(String str) throws IOException {
            return get(str, 0);
        }

        public File get(final String str, final int i) throws IOException {
            return build().get(new InputStreamAdapter() { // from class: top.zibin.luban.Luban.Builder.4
                @Override // top.zibin.luban.InputStreamAdapter
                public InputStream openInternal() {
                    return ArrayPoolProvide.getInstance().openInputStream(str);
                }

                @Override // top.zibin.luban.InputStreamProvider
                public int getIndex() {
                    return i;
                }

                @Override // top.zibin.luban.InputStreamProvider
                public String getPath() {
                    return str;
                }
            }, this.context);
        }

        public List<File> get() throws IOException {
            return build().get(this.context);
        }
    }
}
