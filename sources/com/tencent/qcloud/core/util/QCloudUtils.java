package com.tencent.qcloud.core.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.github.houbb.heaven.constant.FileOptionConst;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes4.dex */
public class QCloudUtils {
    public static boolean isNotEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }

    public static long getUriContentLength(Uri uri, ContentResolver contentResolver) {
        long j;
        String scheme = uri.getScheme();
        if ("content".equals(scheme)) {
            Cursor cursorQuery = contentResolver.query(uri, null, null, null, null);
            if (cursorQuery != null) {
                try {
                    j = cursorQuery.moveToFirst() ? cursorQuery.getLong(cursorQuery.getColumnIndex("_size")) : -1L;
                } finally {
                    cursorQuery.close();
                }
            } else {
                j = -1;
            }
            return j == -1 ? getUriContentLengthByRead(uri, contentResolver) : j;
        }
        if ("file".equals(scheme)) {
            return new File(uri.getPath()).length();
        }
        return -1L;
    }

    public static boolean doesUriFileExist(Uri uri, ContentResolver contentResolver) {
        try {
            contentResolver.openFileDescriptor(uri, FileOptionConst.READ).close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
            return true;
        }
    }

    private static long getUriContentLengthByRead(Uri uri, ContentResolver contentResolver) {
        InputStream inputStreamOpenInputStream = null;
        try {
            try {
                inputStreamOpenInputStream = contentResolver.openInputStream(uri);
                byte[] bArr = new byte[8192];
                long j = 0;
                while (true) {
                    int i = inputStreamOpenInputStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    j += (long) i;
                }
                return j;
            } catch (Exception e) {
                e.printStackTrace();
                if (inputStreamOpenInputStream == null) {
                    return -1L;
                }
                try {
                    inputStreamOpenInputStream.close();
                    return -1L;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return -1L;
                }
            }
        } finally {
            if (inputStreamOpenInputStream != null) {
                try {
                    inputStreamOpenInputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    private static long getUriContentLengthBySkip(Uri uri, ContentResolver contentResolver) {
        InputStream inputStreamOpenInputStream = null;
        try {
            try {
                inputStreamOpenInputStream = contentResolver.openInputStream(uri);
                long j = 0;
                while (true) {
                    long jSkip = inputStreamOpenInputStream.skip(8192);
                    if (jSkip == -1) {
                        break;
                    }
                    j += jSkip;
                }
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return j;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return -1L;
            }
        } catch (Throwable th) {
            if (inputStreamOpenInputStream != null) {
                try {
                    inputStreamOpenInputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static byte[] toBytes(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byteArray = null;
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            byteArray = byteArrayOutputStream.toByteArray();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
            return byteArray;
        }
    }

    public static Object toObject(byte[] bArr) {
        Object object = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
            return object;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return object;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0020 -> B:29:0x0023). Please report as a decompilation issue!!! */
    public static void writeToFile(String str, byte[] bArr) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                try {
                    fileOutputStream2.write(bArr);
                    fileOutputStream2.close();
                } catch (IOException e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static byte[] readBytesFromFile(String str) {
        byte[] bArr = null;
        try {
            File file = new File(str);
            bArr = new byte[(int) file.length()];
            new FileInputStream(file).read(bArr);
            return bArr;
        } catch (IOException e) {
            e.printStackTrace();
            return bArr;
        }
    }
}
