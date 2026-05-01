package com.jieli.jl_bt_ota.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes3.dex */
public class FileUtil {
    public static final int FILE_TYPE_PIC = 1;
    public static final int FILE_TYPE_UNKNOWN = 0;
    public static final int FILE_TYPE_VIDEO = 2;

    /* JADX INFO: renamed from: a */
    private static final String f784a = "FileUtil";

    /* JADX INFO: renamed from: a */
    private static String m827a(Context context) {
        File externalFilesDir;
        return (Build.VERSION.SDK_INT < 29 || context == null || (externalFilesDir = context.getExternalFilesDir(null)) == null) ? Environment.getExternalStorageDirectory().getPath() : externalFilesDir.getPath();
    }

    public static boolean bitmapToFile(Bitmap bitmap, String str, int i) throws Throwable {
        FileOutputStream fileOutputStream;
        if (bitmap != null && !TextUtils.isEmpty(str)) {
            FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    fileOutputStream = new FileOutputStream(str);
                    try {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, i, fileOutputStream);
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    } catch (IOException e2) {
                        e = e2;
                        fileOutputStream2 = fileOutputStream;
                        e.printStackTrace();
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.flush();
                                fileOutputStream2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = fileOutputStream2;
                }
            } catch (IOException e5) {
                e = e5;
            }
        }
        return false;
    }

    public static boolean bytesToFile(byte[] bArr, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        if (bArr == null || TextUtils.isEmpty(str)) {
            return false;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(str);
                try {
                    fileOutputStream.write(bArr);
                    try {
                        fileOutputStream.close();
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return true;
                    }
                } catch (IOException e2) {
                    e = e2;
                    fileOutputStream2 = fileOutputStream;
                    e.printStackTrace();
                    if (fileOutputStream2 == null) {
                        return false;
                    }
                    try {
                        fileOutputStream2.close();
                        return false;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        return false;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = fileOutputStream2;
        }
    }

    public static boolean checkFileExist(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:104:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0073 A[Catch: IOException -> 0x0090, TRY_ENTER, TRY_LEAVE, TryCatch #2 {IOException -> 0x0090, blocks: (B:24:0x0044, B:50:0x0073, B:62:0x008c), top: B:80:0x001c }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x008c A[Catch: IOException -> 0x0090, TRY_ENTER, TRY_LEAVE, TryCatch #2 {IOException -> 0x0090, blocks: (B:24:0x0044, B:50:0x0073, B:62:0x008c), top: B:80:0x001c }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0069 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0082 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v13, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v16, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.FileOutputStream] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:65:0x0091 -> B:79:0x00aa). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void copyFromAssetsToSdcard(android.content.Context r3, boolean r4, java.lang.String r5, java.lang.String r6) throws java.lang.Throwable {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 != 0) goto Laa
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 != 0) goto Laa
            java.io.File r0 = new java.io.File
            r0.<init>(r6)
            if (r4 != 0) goto L1b
            if (r4 != 0) goto Laa
            boolean r4 = r0.exists()
            if (r4 != 0) goto Laa
        L1b:
            r4 = 0
            android.content.res.Resources r3 = r3.getResources()     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5e java.io.FileNotFoundException -> L77
            android.content.res.AssetManager r3 = r3.getAssets()     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5e java.io.FileNotFoundException -> L77
            java.io.InputStream r3 = r3.open(r5)     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5e java.io.FileNotFoundException -> L77
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L55 java.io.FileNotFoundException -> L57
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L55 java.io.FileNotFoundException -> L57
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r4]     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4c java.io.FileNotFoundException -> L4e
        L31:
            r0 = 0
            int r1 = r3.read(r6, r0, r4)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4c java.io.FileNotFoundException -> L4e
            if (r1 < 0) goto L3c
            r5.write(r6, r0, r1)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4c java.io.FileNotFoundException -> L4e
            goto L31
        L3c:
            r5.close()     // Catch: java.io.IOException -> L40
            goto L44
        L40:
            r4 = move-exception
            r4.printStackTrace()
        L44:
            r3.close()     // Catch: java.io.IOException -> L90
            goto Laa
        L49:
            r4 = move-exception
            goto L95
        L4c:
            r4 = move-exception
            goto L64
        L4e:
            r4 = move-exception
            goto L7d
        L50:
            r5 = move-exception
            r2 = r5
            r5 = r4
            r4 = r2
            goto L95
        L55:
            r5 = move-exception
            goto L61
        L57:
            r5 = move-exception
            goto L7a
        L59:
            r3 = move-exception
            r5 = r4
            r4 = r3
            r3 = r5
            goto L95
        L5e:
            r3 = move-exception
            r5 = r3
            r3 = r4
        L61:
            r2 = r5
            r5 = r4
            r4 = r2
        L64:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L49
            if (r5 == 0) goto L71
            r5.close()     // Catch: java.io.IOException -> L6d
            goto L71
        L6d:
            r4 = move-exception
            r4.printStackTrace()
        L71:
            if (r3 == 0) goto Laa
            r3.close()     // Catch: java.io.IOException -> L90
            goto Laa
        L77:
            r3 = move-exception
            r5 = r3
            r3 = r4
        L7a:
            r2 = r5
            r5 = r4
            r4 = r2
        L7d:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L49
            if (r5 == 0) goto L8a
            r5.close()     // Catch: java.io.IOException -> L86
            goto L8a
        L86:
            r4 = move-exception
            r4.printStackTrace()
        L8a:
            if (r3 == 0) goto Laa
            r3.close()     // Catch: java.io.IOException -> L90
            goto Laa
        L90:
            r3 = move-exception
            r3.printStackTrace()
            goto Laa
        L95:
            if (r5 == 0) goto L9f
            r5.close()     // Catch: java.io.IOException -> L9b
            goto L9f
        L9b:
            r5 = move-exception
            r5.printStackTrace()
        L9f:
            if (r3 == 0) goto La9
            r3.close()     // Catch: java.io.IOException -> La5
            goto La9
        La5:
            r3 = move-exception
            r3.printStackTrace()
        La9:
            throw r4
        Laa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.util.FileUtil.copyFromAssetsToSdcard(android.content.Context, boolean, java.lang.String, java.lang.String):void");
    }

    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            if (file.delete()) {
                JL_Log.m848i(f784a, "delete file success!");
                return;
            }
            return;
        }
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null || fileArrListFiles.length == 0) {
                if (file.delete()) {
                    JL_Log.m848i(f784a, "delete empty file success!");
                    return;
                }
                return;
            }
            for (File file2 : fileArrListFiles) {
                deleteFile(file2);
            }
            if (file.delete()) {
                JL_Log.m848i(f784a, "delete empty file success!");
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0090 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x009d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v11, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v13, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.io.FileInputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getBytes(java.lang.String r6) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r6 == 0) goto La6
            boolean r1 = r6.isEmpty()
            if (r1 == 0) goto Lb
            goto La6
        Lb:
            java.io.File r1 = new java.io.File
            r1.<init>(r6)
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4f java.io.FileNotFoundException -> L6a
            r6.<init>(r1)     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4f java.io.FileNotFoundException -> L6a
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42 java.io.FileNotFoundException -> L46
            r1.<init>()     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42 java.io.FileNotFoundException -> L46
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch: java.io.IOException -> L3b java.io.FileNotFoundException -> L3d java.lang.Throwable -> L8a
        L1e:
            int r3 = r6.read(r2)     // Catch: java.io.IOException -> L3b java.io.FileNotFoundException -> L3d java.lang.Throwable -> L8a
            r4 = -1
            if (r3 == r4) goto L2a
            r4 = 0
            r1.write(r2, r4, r3)     // Catch: java.io.IOException -> L3b java.io.FileNotFoundException -> L3d java.lang.Throwable -> L8a
            goto L1e
        L2a:
            r1.close()     // Catch: java.io.IOException -> L2e
            goto L32
        L2e:
            r0 = move-exception
            r0.printStackTrace()
        L32:
            byte[] r0 = r1.toByteArray()
            r6.close()     // Catch: java.io.IOException -> L85
            goto L89
        L3b:
            r2 = move-exception
            goto L53
        L3d:
            r2 = move-exception
            goto L6e
        L3f:
            r1 = move-exception
            goto L8e
        L42:
            r1 = move-exception
            r2 = r1
            r1 = r0
            goto L53
        L46:
            r1 = move-exception
            r2 = r1
            r1 = r0
            goto L6e
        L4a:
            r6 = move-exception
            r1 = r0
            r0 = r6
            r6 = r1
            goto L8b
        L4f:
            r6 = move-exception
            r2 = r6
            r6 = r0
            r1 = r6
        L53:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L8a
            if (r1 == 0) goto L64
            r1.close()     // Catch: java.io.IOException -> L5c
            goto L60
        L5c:
            r0 = move-exception
            r0.printStackTrace()
        L60:
            byte[] r0 = r1.toByteArray()
        L64:
            if (r6 == 0) goto L89
            r6.close()     // Catch: java.io.IOException -> L85
            goto L89
        L6a:
            r6 = move-exception
            r2 = r6
            r6 = r0
            r1 = r6
        L6e:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L8a
            if (r1 == 0) goto L7f
            r1.close()     // Catch: java.io.IOException -> L77
            goto L7b
        L77:
            r0 = move-exception
            r0.printStackTrace()
        L7b:
            byte[] r0 = r1.toByteArray()
        L7f:
            if (r6 == 0) goto L89
            r6.close()     // Catch: java.io.IOException -> L85
            goto L89
        L85:
            r6 = move-exception
            r6.printStackTrace()
        L89:
            return r0
        L8a:
            r0 = move-exception
        L8b:
            r5 = r1
            r1 = r0
            r0 = r5
        L8e:
            if (r0 == 0) goto L9b
            r0.close()     // Catch: java.io.IOException -> L94
            goto L98
        L94:
            r2 = move-exception
            r2.printStackTrace()
        L98:
            r0.toByteArray()
        L9b:
            if (r6 == 0) goto La5
            r6.close()     // Catch: java.io.IOException -> La1
            goto La5
        La1:
            r6 = move-exception
            r6.printStackTrace()
        La5:
            throw r1
        La6:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.util.FileUtil.getBytes(java.lang.String):byte[]");
    }

    public static byte[] getFromRaw(Context context, int i) throws Throwable {
        byte[] bArr;
        IOException e;
        InputStream inputStream = null;
        bArr = null;
        byte[] bArr2 = null;
        inputStream = null;
        try {
            try {
                InputStream inputStreamOpenRawResource = context.getResources().openRawResource(i);
                try {
                    Runtime runtime = Runtime.getRuntime();
                    int iFreeMemory = 512000;
                    if (runtime != null && runtime.freeMemory() < 512000) {
                        iFreeMemory = (int) runtime.freeMemory();
                    }
                    byte[] bArr3 = new byte[iFreeMemory];
                    byte[] bArr4 = new byte[1024];
                    int i2 = 0;
                    while (true) {
                        int i3 = inputStreamOpenRawResource.read(bArr4, 0, 1024);
                        if (i3 < 0) {
                            break;
                        }
                        int i4 = i2 + i3;
                        if (i4 <= iFreeMemory) {
                            System.arraycopy(bArr4, 0, bArr3, i2, i3);
                            i2 = i4;
                        }
                        e.printStackTrace();
                        return bArr2;
                    }
                    if (i2 > 0) {
                        bArr2 = new byte[i2];
                        System.arraycopy(bArr3, 0, bArr2, 0, i2);
                    }
                    try {
                        inputStreamOpenRawResource.close();
                        return bArr2;
                    } catch (IOException e2) {
                        e = e2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    byte[] bArr5 = bArr2;
                    inputStream = inputStreamOpenRawResource;
                    bArr = bArr5;
                    e.printStackTrace();
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e4) {
                            bArr2 = bArr;
                            e = e4;
                        }
                    }
                    return bArr;
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStreamOpenRawResource;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e6) {
            e = e6;
            bArr = null;
        }
    }

    public static int judgeFileType(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        if (str.endsWith(".png") || str.endsWith(".PNG") || str.endsWith(".JPEG") || str.endsWith(".jpeg") || str.endsWith(".jpg") || str.endsWith(".JPG")) {
            return 1;
        }
        return (str.endsWith(".mov") || str.endsWith(".MOV") || str.endsWith(".mp4") || str.endsWith(".MP4") || str.endsWith(PictureMimeType.AVI) || str.endsWith(".AVI")) ? 2 : 0;
    }

    public static String splicingFilePath(Context context, String str, String str2, String str3, String str4) {
        String strM827a = m827a(context);
        if (!TextUtils.isEmpty(str)) {
            String str5 = File.separator;
            if (str.contains(str5)) {
                for (String str6 : str.split(str5)) {
                    if (!TextUtils.isEmpty(str6)) {
                        strM827a = strM827a + File.separator + str6;
                        File file = new File(strM827a);
                        if (!file.exists() && file.mkdir()) {
                            JL_Log.m852w(f784a, "create root dir success! path : " + strM827a);
                        }
                    }
                }
            } else {
                strM827a = strM827a + str5 + str;
                File file2 = new File(strM827a);
                if (!file2.exists() && file2.mkdir()) {
                    JL_Log.m852w(f784a, "create root dir success! path : " + strM827a);
                }
            }
            if (TextUtils.isEmpty(str2)) {
                return strM827a;
            }
            StringBuilder sbAppend = new StringBuilder().append(strM827a);
            String str7 = File.separator;
            String string = sbAppend.append(str7).append(str2).toString();
            File file3 = new File(string);
            if (!file3.exists() && file3.mkdir()) {
                JL_Log.m852w(f784a, "create one dir success!");
            }
            if (TextUtils.isEmpty(str3)) {
                return string;
            }
            String str8 = string + str7 + str3;
            File file4 = new File(str8);
            if (!file4.exists() && file4.mkdir()) {
                JL_Log.m852w(f784a, "create two dir success!");
            }
            if (TextUtils.isEmpty(str4)) {
                return str8;
            }
            strM827a = str8 + str7 + str4;
            File file5 = new File(strM827a);
            if (!file5.exists() && file5.mkdir()) {
                JL_Log.m852w(f784a, "create three sub dir success!");
            }
        }
        return strM827a;
    }
}
