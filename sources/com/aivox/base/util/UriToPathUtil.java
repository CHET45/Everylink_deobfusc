package com.aivox.base.util;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class UriToPathUtil {

    public interface UriToPathListener {
        void convertEnd(String str);

        void convertEnd(List<String> list);

        void convertStart();
    }

    public static Uri toUri(Context context, String str) {
        return FileProvider.getUriForFile(context, context.getApplicationInfo().packageName + ".fileprovider", new File(str));
    }

    public static void getFileAbsolutePaths(Activity activity, ArrayList<Uri> arrayList, UriToPathListener uriToPathListener) {
        if (uriToPathListener != null) {
            uriToPathListener.convertStart();
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<Uri> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(getFileAbsolutePath(activity, it.next()));
        }
        if (uriToPathListener != null) {
            uriToPathListener.convertEnd(arrayList2);
        }
    }

    public static void getFileAbsolutePath(Context context, Uri uri, UriToPathListener uriToPathListener) {
        if (uriToPathListener != null) {
            uriToPathListener.convertStart();
        }
        String fileAbsolutePath = getFileAbsolutePath(context, uri);
        if (uriToPathListener != null) {
            uriToPathListener.convertEnd(fileAbsolutePath);
        }
    }

    public static String getFileAbsolutePath(Context context, Uri uri) {
        Uri uri2 = null;
        if (context != null && uri != null) {
            if (Build.VERSION.SDK_INT < 29 && DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                    if ("primary".equalsIgnoreCase(strArrSplit[0])) {
                        return Environment.getExternalStorageDirectory() + "/" + strArrSplit[1];
                    }
                } else {
                    if (isDownloadsDocument(uri)) {
                        return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
                    }
                    if (isMediaDocument(uri)) {
                        String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                        String str = strArrSplit2[0];
                        if (PictureMimeType.MIME_TYPE_PREFIX_IMAGE.equals(str)) {
                            uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if (PictureMimeType.MIME_TYPE_PREFIX_VIDEO.equals(str)) {
                            uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(str)) {
                            uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        return getDataColumn(context, uri2, "_id=?", new String[]{strArrSplit2[1]});
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= 29) {
                return uriToFileApiQ(context, uri);
            }
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, null, null);
            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x003b A[PHI: r8
  0x003b: PHI (r8v3 android.database.Cursor) = (r8v2 android.database.Cursor), (r8v4 android.database.Cursor) binds: [B:20:0x0039, B:13:0x002e] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getDataColumn(android.content.Context r8, android.net.Uri r9, java.lang.String r10, java.lang.String[] r11) throws java.lang.Throwable {
        /*
            r0 = 1
            java.lang.String[] r3 = new java.lang.String[r0]
            r0 = 0
            java.lang.String r7 = "_data"
            r3[r0] = r7
            r0 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L38
            r6 = 0
            r2 = r9
            r4 = r10
            r5 = r11
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L38
            if (r8 == 0) goto L2e
            boolean r9 = r8.moveToFirst()     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L39
            if (r9 == 0) goto L2e
            int r9 = r8.getColumnIndexOrThrow(r7)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L39
            java.lang.String r9 = r8.getString(r9)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L39
            if (r8 == 0) goto L2a
            r8.close()
        L2a:
            return r9
        L2b:
            r9 = move-exception
            r0 = r8
            goto L32
        L2e:
            if (r8 == 0) goto L3e
            goto L3b
        L31:
            r9 = move-exception
        L32:
            if (r0 == 0) goto L37
            r0.close()
        L37:
            throw r9
        L38:
            r8 = r0
        L39:
            if (r8 == 0) goto L3e
        L3b:
            r8.close()
        L3e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.base.util.UriToPathUtil.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private static String getFileFromContentUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] strArr = {"_data", "_display_name"};
        Cursor cursorQuery = context.getContentResolver().query(uri, strArr, null, null, null);
        if (cursorQuery != null) {
            cursorQuery.moveToFirst();
            try {
                return cursorQuery.getString(cursorQuery.getColumnIndex(strArr[0]));
            } catch (Exception unused) {
                return "";
            } finally {
                cursorQuery.close();
            }
        }
        return "";
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0067 A[PHI: r1
  0x0067: PHI (r1v3 java.io.File) = (r1v2 java.io.File), (r1v2 java.io.File), (r1v4 java.io.File) binds: [B:6:0x0021, B:8:0x0035, B:16:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String uriToFileApiQ(android.content.Context r8, android.net.Uri r9) {
        /*
            java.lang.String r0 = r9.getScheme()
            java.lang.String r1 = "file"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L16
            java.io.File r8 = new java.io.File
            java.lang.String r9 = r9.getPath()
            r8.<init>(r9)
            goto L68
        L16:
            java.lang.String r0 = r9.getScheme()
            java.lang.String r1 = "content"
            boolean r0 = r0.equals(r1)
            r1 = 0
            if (r0 == 0) goto L67
            android.content.ContentResolver r0 = r8.getContentResolver()
            r6 = 0
            r7 = 0
            r4 = 0
            r5 = 0
            r2 = r0
            r3 = r9
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)
            boolean r3 = r2.moveToFirst()
            if (r3 == 0) goto L67
            java.lang.String r3 = "_display_name"
            int r3 = r2.getColumnIndex(r3)
            java.lang.String r2 = r2.getString(r3)
            java.io.InputStream r9 = r0.openInputStream(r9)     // Catch: java.io.IOException -> L63
            java.io.File r0 = new java.io.File     // Catch: java.io.IOException -> L63
            java.lang.String r3 = "audioOther"
            java.lang.String r8 = com.aivox.base.util.FileUtils.getAppPath(r8, r3)     // Catch: java.io.IOException -> L63
            r0.<init>(r8, r2)     // Catch: java.io.IOException -> L63
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch: java.io.IOException -> L63
            r8.<init>(r0)     // Catch: java.io.IOException -> L63
            android.os.FileUtils.copy(r9, r8)     // Catch: java.io.IOException -> L63
            r8.close()     // Catch: java.io.IOException -> L60
            r9.close()     // Catch: java.io.IOException -> L60
            r8 = r0
            goto L68
        L60:
            r8 = move-exception
            r1 = r0
            goto L64
        L63:
            r8 = move-exception
        L64:
            r8.printStackTrace()
        L67:
            r8 = r1
        L68:
            if (r8 == 0) goto L6f
            java.lang.String r8 = r8.getAbsolutePath()
            return r8
        L6f:
            java.lang.String r8 = ""
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.base.util.UriToPathUtil.uriToFileApiQ(android.content.Context, android.net.Uri):java.lang.String");
    }
}
