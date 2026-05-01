package top.zibin.luban.p042io;

import android.content.ContentResolver;
import android.net.Uri;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes5.dex */
public class ArrayPoolProvide {
    private static ArrayPoolProvide mInstance;
    private final HashSet<String> keyCache = new HashSet<>();
    private final ConcurrentHashMap<String, BufferedInputStreamWrap> bufferedLruCache = new ConcurrentHashMap<>();
    private final LruArrayPool arrayPool = new LruArrayPool(4194304);

    public byte[] get(int i) {
        return (byte[]) this.arrayPool.get(i, byte[].class);
    }

    public void put(byte[] bArr) {
        this.arrayPool.put(bArr);
    }

    public InputStream openInputStream(ContentResolver contentResolver, Uri uri) {
        try {
            try {
                BufferedInputStreamWrap bufferedInputStreamWrapWrapInputStream = this.bufferedLruCache.get(uri.toString());
                if (bufferedInputStreamWrapWrapInputStream != null) {
                    bufferedInputStreamWrapWrapInputStream.reset();
                } else {
                    bufferedInputStreamWrapWrapInputStream = wrapInputStream(contentResolver, uri);
                }
                return bufferedInputStreamWrapWrapInputStream;
            } catch (Exception unused) {
                return contentResolver.openInputStream(uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return wrapInputStream(contentResolver, uri);
        }
    }

    public InputStream openInputStream(String str) {
        try {
            BufferedInputStreamWrap bufferedInputStreamWrapWrapInputStream = this.bufferedLruCache.get(str);
            if (bufferedInputStreamWrapWrapInputStream != null) {
                bufferedInputStreamWrapWrapInputStream.reset();
            } else {
                bufferedInputStreamWrapWrapInputStream = wrapInputStream(str);
            }
            return bufferedInputStreamWrapWrapInputStream;
        } catch (Exception unused) {
            return wrapInputStream(str);
        }
    }

    private BufferedInputStreamWrap wrapInputStream(ContentResolver contentResolver, Uri uri) {
        BufferedInputStreamWrap bufferedInputStreamWrap = null;
        try {
            BufferedInputStreamWrap bufferedInputStreamWrap2 = new BufferedInputStreamWrap(contentResolver.openInputStream(uri));
            try {
                int iAvailable = bufferedInputStreamWrap2.available();
                if (iAvailable <= 0) {
                    iAvailable = 8388608;
                }
                bufferedInputStreamWrap2.mark(iAvailable);
                this.bufferedLruCache.put(uri.toString(), bufferedInputStreamWrap2);
                this.keyCache.add(uri.toString());
                return bufferedInputStreamWrap2;
            } catch (Exception e) {
                e = e;
                bufferedInputStreamWrap = bufferedInputStreamWrap2;
                e.printStackTrace();
                return bufferedInputStreamWrap;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    private BufferedInputStreamWrap wrapInputStream(String str) {
        BufferedInputStreamWrap bufferedInputStreamWrap = null;
        try {
            BufferedInputStreamWrap bufferedInputStreamWrap2 = new BufferedInputStreamWrap(new FileInputStream(str));
            try {
                int iAvailable = bufferedInputStreamWrap2.available();
                if (iAvailable <= 0) {
                    iAvailable = 8388608;
                }
                bufferedInputStreamWrap2.mark(iAvailable);
                this.bufferedLruCache.put(str, bufferedInputStreamWrap2);
                this.keyCache.add(str);
                return bufferedInputStreamWrap2;
            } catch (Exception e) {
                e = e;
                bufferedInputStreamWrap = bufferedInputStreamWrap2;
                e.printStackTrace();
                return bufferedInputStreamWrap;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void clearMemory() {
        for (String str : this.keyCache) {
            close(this.bufferedLruCache.get(str));
            this.bufferedLruCache.remove(str);
        }
        this.keyCache.clear();
        this.arrayPool.clearMemory();
    }

    public static ArrayPoolProvide getInstance() {
        if (mInstance == null) {
            synchronized (ArrayPoolProvide.class) {
                if (mInstance == null) {
                    mInstance = new ArrayPoolProvide();
                }
            }
        }
        return mInstance;
    }

    public static void close(Closeable closeable) {
        if (closeable instanceof Closeable) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }
}
