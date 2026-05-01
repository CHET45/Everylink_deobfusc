package com.github.houbb.heaven.util.codec;

import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CodecSupport {
    public static final String PREFERRED_ENCODING = "UTF-8";

    public static byte[] toBytes(char[] cArr) {
        return toBytes(new String(cArr), "UTF-8");
    }

    public static byte[] toBytes(char[] cArr, String str) throws CodecException {
        return toBytes(new String(cArr), str);
    }

    public static byte[] toBytes(String str) {
        return toBytes(str, "UTF-8");
    }

    public static byte[] toBytes(String str, String str2) throws CodecException {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(str2);
        } catch (UnsupportedEncodingException e) {
            throw new CodecException("Unable to convert source [" + str + "] to byte array using encoding '" + str2 + PunctuationConst.SINGLE_QUOTES, e);
        }
    }

    public static String toString(byte[] bArr) {
        return toString(bArr, "UTF-8");
    }

    public static String toString(byte[] bArr, String str) throws CodecException {
        if (bArr == null) {
            return null;
        }
        try {
            return new String(bArr, str);
        } catch (UnsupportedEncodingException e) {
            throw new CodecException("Unable to convert byte array to String with encoding '" + str + "'.", e);
        }
    }

    public static char[] toChars(byte[] bArr) {
        return toChars(bArr, "UTF-8");
    }

    public static char[] toChars(byte[] bArr, String str) throws CodecException {
        return toString(bArr, str).toCharArray();
    }

    protected boolean isByteSource(Object obj) {
        return (obj instanceof byte[]) || (obj instanceof char[]) || (obj instanceof String) || (obj instanceof File) || (obj instanceof InputStream);
    }

    protected byte[] toBytes(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Argument for byte conversion cannot be null.");
        }
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        if (obj instanceof char[]) {
            return toBytes((char[]) obj);
        }
        if (obj instanceof String) {
            return toBytes((String) obj);
        }
        if (obj instanceof File) {
            return toBytes((File) obj);
        }
        if (obj instanceof InputStream) {
            return toBytes((InputStream) obj);
        }
        return objectToBytes(obj);
    }

    protected String toString(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Argument for String conversion cannot be null.");
        }
        if (obj instanceof byte[]) {
            return toString((byte[]) obj);
        }
        if (obj instanceof char[]) {
            return new String((char[]) obj);
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        return objectToString(obj);
    }

    protected byte[] toBytes(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File argument cannot be null.");
        }
        try {
            return toBytes((InputStream) new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new CodecException("Unable to acquire InputStream for file [" + file + "]", e);
        }
    }

    protected byte[] toBytes(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream argument cannot be null.");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        byte[] bArr = new byte[512];
        while (true) {
            try {
                try {
                    int i = inputStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i);
                } catch (IOException e) {
                    throw new CodecException(e);
                }
            } finally {
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            inputStream.close();
        } catch (IOException unused) {
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException unused2) {
        }
        return byteArray;
    }

    protected byte[] objectToBytes(Object obj) {
        throw new CodecException("The " + getClass().getName() + " implementation only supports conversion to byte[] if the source is of type byte[], char[], String,  File or InputStream.  The instance provided as a method argument is of type [" + obj.getClass().getName() + "].  If you would like to convert this argument type to a byte[], you can 1) convert the argument to one of the supported types yourself and then use that as the method argument or 2) subclass " + getClass().getName() + "and override the objectToBytes(Object o) method.");
    }

    protected String objectToString(Object obj) {
        return obj.toString();
    }

    public static char[] toChars(String str) {
        if (str == null) {
            return null;
        }
        return str.toCharArray();
    }

    public static String toString(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        return new String(cArr);
    }
}
