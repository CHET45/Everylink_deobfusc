package com.aivox.base.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class GalleryUtils {
    private static final String TAG = "GalleryUtils";

    public static void saveImageToGallery(Context context, File file, String str) {
        saveImageQ(context, file, str, getMimeTypeFromFileName(str));
    }

    public static void saveVideoToGallery(Context context, File file, String str) {
        saveVideoQ(context, file, str, getMimeTypeFromFileName(str));
    }

    private static void saveImageQ(Context context, File file, String str, String str2) {
        Uri uri;
        String str3;
        Uri uriInsert;
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", str);
        contentValues.put("mime_type", str2);
        contentValues.put("is_pending", (Integer) 1);
        if (str2.startsWith("image/")) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            str3 = Environment.DIRECTORY_PICTURES;
        } else if (str2.startsWith("video/")) {
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            str3 = Environment.DIRECTORY_MOVIES;
        } else {
            uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
            str3 = Environment.DIRECTORY_DOWNLOADS;
        }
        contentValues.put("relative_path", str3);
        ContentResolver contentResolver = context.getContentResolver();
        try {
            uriInsert = contentResolver.insert(uri, contentValues);
            try {
                if (uriInsert == null) {
                    throw new IOException("Failed to create new MediaStore entry for " + str);
                }
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
                    try {
                        if (outputStreamOpenOutputStream == null) {
                            throw new IOException("Failed to get output stream for URI: " + uriInsert);
                        }
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int i = fileInputStream.read(bArr);
                            if (i <= 0) {
                                break;
                            } else {
                                outputStreamOpenOutputStream.write(bArr, 0, i);
                            }
                        }
                        if (outputStreamOpenOutputStream != null) {
                            outputStreamOpenOutputStream.close();
                        }
                        fileInputStream.close();
                        contentValues.clear();
                        contentValues.put("is_pending", (Integer) 0);
                        contentResolver.update(uriInsert, contentValues, null, null);
                        Log.i(TAG, "File saved to gallery: " + uriInsert);
                    } finally {
                    }
                } finally {
                }
            } catch (IOException e) {
                e = e;
                Log.e(TAG, "Failed to save file to gallery: ", e);
                if (uriInsert != null) {
                    contentResolver.delete(uriInsert, null, null);
                }
            }
        } catch (IOException e2) {
            e = e2;
            uriInsert = null;
        }
    }

    private static void saveVideoQ(Context context, File file, String str, String str2) {
        Uri uriInsert;
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", str);
        contentValues.put("mime_type", str2);
        contentValues.put("relative_path", Environment.DIRECTORY_MOVIES);
        contentValues.put("is_pending", (Integer) 1);
        ContentResolver contentResolver = context.getContentResolver();
        try {
            uriInsert = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
        } catch (IOException e) {
            e = e;
            uriInsert = null;
        }
        try {
            if (uriInsert == null) {
                throw new IOException("Failed to create new MediaStore entry for video");
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
                try {
                    if (outputStreamOpenOutputStream == null) {
                        throw new IOException("Failed to get output stream for URI: " + uriInsert);
                    }
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i <= 0) {
                            break;
                        } else {
                            outputStreamOpenOutputStream.write(bArr, 0, i);
                        }
                    }
                    if (outputStreamOpenOutputStream != null) {
                        outputStreamOpenOutputStream.close();
                    }
                    fileInputStream.close();
                    contentValues.clear();
                    contentValues.put("is_pending", (Integer) 0);
                    contentResolver.update(uriInsert, contentValues, null, null);
                    Log.i(TAG, "Video saved to gallery: " + uriInsert);
                } finally {
                }
            } finally {
            }
        } catch (IOException e2) {
            e = e2;
            Log.e(TAG, "Failed to save video to gallery: ", e);
            if (uriInsert != null) {
                contentResolver.delete(uriInsert, null, null);
            }
        }
    }

    private static String getMimeTypeFromFileName(String str) {
        String lowerCase;
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf <= 0) {
            lowerCase = "";
        } else {
            lowerCase = str.substring(iLastIndexOf + 1).toLowerCase();
        }
        lowerCase.hashCode();
        switch (lowerCase) {
            case "3gp":
                return "video/3gpp";
            case "gif":
                return "image/gif";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "mp4":
                return "video/mp4";
            case "png":
                return PictureMimeType.PNG_Q;
            default:
                return "application/octet-stream";
        }
    }

    private static Bitmap.CompressFormat getCompressFormat(String str) {
        int iHashCode = str.hashCode();
        if (iHashCode == -1487394660) {
            str.equals("image/jpeg");
        } else if (iHashCode == -879258763 && str.equals(PictureMimeType.PNG_Q)) {
            return Bitmap.CompressFormat.PNG;
        }
        return Bitmap.CompressFormat.JPEG;
    }
}
