package com.lzy.okgo.cache;

import android.content.ContentValues;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.utils.OkLogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public class CacheEntity<T> implements Serializable {
    public static final long CACHE_NEVER_EXPIRE = -1;
    private static final long serialVersionUID = -4337711009801627866L;
    private T data;

    /* JADX INFO: renamed from: id */
    private long f889id;
    private boolean isExpire;
    private String key;
    private long localExpire;
    private HttpHeaders responseHeaders;

    public long getId() {
        return this.f889id;
    }

    public void setId(long j) {
        this.f889id = j;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    public void setResponseHeaders(HttpHeaders httpHeaders) {
        this.responseHeaders = httpHeaders;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t) {
        this.data = t;
    }

    public long getLocalExpire() {
        return this.localExpire;
    }

    public void setLocalExpire(long j) {
        this.localExpire = j;
    }

    public boolean isExpire() {
        return this.isExpire;
    }

    public void setExpire(boolean z) {
        this.isExpire = z;
    }

    public boolean checkExpire(CacheMode cacheMode, long j, long j2) {
        return cacheMode == CacheMode.DEFAULT ? getLocalExpire() < j2 : j != -1 && getLocalExpire() + j < j2;
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0058: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:25:0x0058 */
    public static <T> ContentValues getContentValues(CacheEntity<T> cacheEntity) throws Throwable {
        ObjectOutputStream objectOutputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2;
        ObjectOutputStream objectOutputStream2;
        ContentValues contentValues = new ContentValues();
        contentValues.put(CacheHelper.KEY, cacheEntity.getKey());
        contentValues.put(CacheHelper.LOCAL_EXPIRE, Long.valueOf(cacheEntity.getLocalExpire()));
        HttpHeaders responseHeaders = cacheEntity.getResponseHeaders();
        ObjectOutputStream objectOutputStream3 = null;
        try {
            try {
                if (responseHeaders != null) {
                    try {
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        try {
                            objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
                        } catch (IOException e) {
                            e = e;
                            objectOutputStream2 = null;
                        } catch (Throwable th) {
                            th = th;
                            if (objectOutputStream3 != null) {
                                try {
                                    objectOutputStream3.close();
                                } catch (IOException e2) {
                                    OkLogger.m864e(e2);
                                    throw th;
                                }
                            }
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            throw th;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        byteArrayOutputStream = null;
                        objectOutputStream2 = null;
                    } catch (Throwable th2) {
                        th = th2;
                        byteArrayOutputStream = null;
                    }
                    try {
                        objectOutputStream2.writeObject(responseHeaders);
                        objectOutputStream2.flush();
                        contentValues.put(CacheHelper.HEAD, byteArrayOutputStream.toByteArray());
                    } catch (IOException e4) {
                        e = e4;
                        OkLogger.m864e(e);
                        if (objectOutputStream2 != null) {
                            objectOutputStream2.close();
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                    }
                } else {
                    byteArrayOutputStream = null;
                    objectOutputStream2 = null;
                }
                if (objectOutputStream2 != null) {
                    objectOutputStream2.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (Throwable th3) {
                th = th3;
                objectOutputStream3 = objectOutputStream;
            }
        } catch (IOException e5) {
            OkLogger.m864e(e5);
        }
        T data = cacheEntity.getData();
        try {
            if (data != null) {
                try {
                    byteArrayOutputStream2 = new ByteArrayOutputStream();
                    try {
                        try {
                            ObjectOutputStream objectOutputStream4 = new ObjectOutputStream(byteArrayOutputStream2);
                            try {
                                objectOutputStream4.writeObject(data);
                                objectOutputStream4.flush();
                                contentValues.put("data", byteArrayOutputStream2.toByteArray());
                                objectOutputStream3 = objectOutputStream4;
                            } catch (IOException e6) {
                                e = e6;
                                objectOutputStream3 = objectOutputStream4;
                                OkLogger.m864e(e);
                                if (objectOutputStream3 != null) {
                                    objectOutputStream3.close();
                                }
                                if (byteArrayOutputStream2 != null) {
                                    byteArrayOutputStream2.close();
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                objectOutputStream3 = objectOutputStream4;
                                if (objectOutputStream3 != null) {
                                    try {
                                        objectOutputStream3.close();
                                    } catch (IOException e7) {
                                        OkLogger.m864e(e7);
                                        throw th;
                                    }
                                }
                                if (byteArrayOutputStream2 != null) {
                                    byteArrayOutputStream2.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                        }
                    } catch (IOException e8) {
                        e = e8;
                    }
                } catch (IOException e9) {
                    e = e9;
                    byteArrayOutputStream2 = null;
                } catch (Throwable th6) {
                    th = th6;
                    byteArrayOutputStream2 = null;
                }
            } else {
                byteArrayOutputStream2 = null;
            }
            if (objectOutputStream3 != null) {
                objectOutputStream3.close();
            }
            if (byteArrayOutputStream2 != null) {
                byteArrayOutputStream2.close();
            }
        } catch (IOException e10) {
            OkLogger.m864e(e10);
        }
        return contentValues;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0075 A[Catch: IOException -> 0x0071, TRY_LEAVE, TryCatch #3 {IOException -> 0x0071, blocks: (B:27:0x006d, B:31:0x0075), top: B:93:0x006d }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00d9 A[Catch: IOException -> 0x00d5, TRY_LEAVE, TryCatch #2 {IOException -> 0x00d5, blocks: (B:69:0x00d1, B:73:0x00d9), top: B:91:0x00d1 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00d1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <T> com.lzy.okgo.cache.CacheEntity<T> parseCursorToBean(android.database.Cursor r6) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lzy.okgo.cache.CacheEntity.parseCursorToBean(android.database.Cursor):com.lzy.okgo.cache.CacheEntity");
    }

    public String toString() {
        return "CacheEntity{id=" + this.f889id + ", key='" + this.key + "', responseHeaders=" + this.responseHeaders + ", data=" + this.data + ", localExpire=" + this.localExpire + '}';
    }
}
