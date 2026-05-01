package com.github.houbb.heaven.util.p010io;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.support.handler.IMapHandler;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.p009id.impl.Ids;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class StreamUtil {
    private StreamUtil() {
    }

    @Deprecated
    public static String toString(InputStream inputStream, String str) {
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                bArr = new byte[1024];
            } finally {
            }
        } catch (Exception e) {
            throw new CommonRuntimeException(e);
        }
        while (true) {
            int i = inputStream.read(bArr);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                String str2 = new String(byteArrayOutputStream.toByteArray(), str);
                byteArrayOutputStream.close();
                return str2;
            }
            throw new CommonRuntimeException(e);
        }
    }

    @Deprecated
    public static String toString(InputStream inputStream) {
        return toString(inputStream, "UTF-8");
    }

    public static String getFileContent(String str) {
        return getFileContent(str, "UTF-8");
    }

    public static String getFileContent(String str, String str2) {
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr;
        try {
            InputStream inputStream = getInputStream(str);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    bArr = new byte[1024];
                } finally {
                }
            } finally {
            }
            while (true) {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
            String str3 = new String(byteArrayOutputStream.toByteArray(), str2);
            byteArrayOutputStream.close();
            if (inputStream != null) {
                inputStream.close();
            }
            return str3;
        } catch (Exception e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static byte[] getFileBytes(String str) {
        return inputStreamToBytes(getInputStream(str));
    }

    public static InputStream getInputStream(String str) {
        InputStream resourceAsStream;
        try {
            try {
                return new URL(str).openStream();
            } catch (MalformedURLException unused) {
                resourceAsStream = new FileInputStream(str);
                return resourceAsStream;
            } catch (IOException e) {
                throw new CommonRuntimeException(e);
            }
        } catch (Exception unused2) {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader == null) {
                contextClassLoader = StreamUtil.class.getClassLoader();
            }
            resourceAsStream = contextClassLoader.getResourceAsStream(str);
            if (resourceAsStream == null) {
                throw new CommonRuntimeException("Could not find file: " + str);
            }
            return resourceAsStream;
        }
    }

    public static void closeStream(Closeable closeable) {
        if (ObjectUtil.isNotNull(closeable)) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new CommonRuntimeException(e);
            }
        }
    }

    public static List<String> readAllLines(String str) {
        return readAllLines(StreamUtil.class.getResourceAsStream(str), "UTF-8", true);
    }

    public static List<String> readAllLines(InputStream inputStream) {
        return readAllLines(inputStream, "UTF-8", true);
    }

    public static List<String> readAllLines(InputStream inputStream, String str, boolean z) {
        try {
            ArrayList arrayList = new ArrayList();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(str)));
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                if (!StringUtil.isEmpty(line) || !z) {
                    arrayList.add(line);
                }
            }
            return arrayList;
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static String getFileContent(String str, int i, int i2) {
        return getFileContent(str, i, i2, StandardCharsets.UTF_8);
    }

    public static String getFileContent(String str, int i, int i2, Charset charset) {
        try {
            InputStream resourceAsStream = StreamUtil.class.getResourceAsStream(str);
            try {
                String fileContent = FileUtil.getFileContent(resourceAsStream, i, i2, charset);
                if (resourceAsStream != null) {
                    resourceAsStream.close();
                }
                return fileContent;
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static File inputStreamToFile(InputStream inputStream, boolean z) {
        try {
            if (ObjectUtil.isNull(inputStream)) {
                return null;
            }
            try {
                File fileCreateTempFile = File.createTempFile(Ids.uuid32(), "temp");
                if (z) {
                    fileCreateTempFile.deleteOnExit();
                }
                Files.copy(inputStream, fileCreateTempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return fileCreateTempFile;
            } catch (IOException e) {
                throw new CommonRuntimeException(e);
            }
        } finally {
            closeStream(inputStream);
        }
    }

    public static File inputStreamToFile(InputStream inputStream) {
        return inputStreamToFile(inputStream, false);
    }

    public static byte[] inputStreamToBytes(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                bArr = new byte[1024];
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
        while (true) {
            int i = inputStream.read(bArr);
            if (-1 != i) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            }
            throw new CommonRuntimeException(e);
        }
    }

    public static String inputStreamToString(InputStream inputStream, String str) {
        return new String(inputStreamToBytes(inputStream), Charset.forName(str));
    }

    public static String inputStreamToString(InputStream inputStream) {
        return inputStreamToString(inputStream, "UTF-8");
    }

    public static <K, V> Map<K, V> readToMap(String str, String str2, IMapHandler<K, V, String> iMapHandler) {
        return FileUtil.readToMap(StreamUtil.class.getResourceAsStream(str), str2, iMapHandler);
    }

    public static <K, V> Map<K, V> readToMap(String str, IMapHandler<K, V, String> iMapHandler) {
        return readToMap(str, "UTF-8", iMapHandler);
    }

    public static Map<String, String> readToMap(String str, final String str2) {
        return readToMap(str, new IMapHandler<String, String, String>() { // from class: com.github.houbb.heaven.util.io.StreamUtil.1
            @Override // com.github.houbb.heaven.support.handler.IMapHandler
            public String getKey(String str3) {
                return str3.split(str2)[0];
            }

            @Override // com.github.houbb.heaven.support.handler.IMapHandler
            public String getValue(String str3) {
                return str3.split(str2)[1];
            }
        });
    }

    public static void write(Collection<String> collection, OutputStream outputStream, String str) {
        try {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName(str)));
                try {
                    Iterator<String> it = collection.iterator();
                    while (it.hasNext()) {
                        bufferedWriter.write(it.next());
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            bufferedWriter.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
            } catch (IOException e2) {
                throw new CommonRuntimeException(e2);
            }
        } catch (Throwable th4) {
            try {
                outputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            throw th4;
        }
    }

    public static void write(Collection<String> collection, OutputStream outputStream) {
        write(collection, outputStream, "UTF-8");
    }
}
