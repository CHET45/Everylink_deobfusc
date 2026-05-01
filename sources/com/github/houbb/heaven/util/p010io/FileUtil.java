package com.github.houbb.heaven.util.p010io;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.support.handler.IMapHandler;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.p010io.filewalker.AllFileVisitor;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.microsoft.azure.storage.Constants;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public final class FileUtil {
    private FileUtil() {
    }

    public static String getFileContent(String str) {
        return getFileContent(str, "UTF-8");
    }

    public static String getFileContent(String str, String str2) {
        File file = new File(str);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    String fileContent = getFileContent(fileInputStream, str2);
                    fileInputStream.close();
                    return fileContent;
                } finally {
                }
            } catch (IOException e) {
                throw new CommonRuntimeException(e);
            }
        } else {
            throw new CommonRuntimeException(str + " is not exists!");
        }
    }

    public static String getFileContent(InputStream inputStream) {
        return getFileContent(inputStream, "UTF-8");
    }

    public static String getFileContent(File file, String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                String fileContent = getFileContent(fileInputStream, str);
                fileInputStream.close();
                return fileContent;
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static String getFileContent(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                String fileContent = getFileContent(fileInputStream);
                fileInputStream.close();
                return fileContent;
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static String getFileContent(InputStream inputStream, String str) {
        return getFileContent(inputStream, 0, Integer.MAX_VALUE, Charset.forName(str));
    }

    public static String getFileContent(InputStream inputStream, int i, int i2, Charset charset) {
        try {
            try {
                int iMin = Math.min(i2, inputStream.available());
                int i3 = 0;
                int iMax = Math.max(0, i);
                inputStream.skip(iMax);
                int i4 = iMin - iMax;
                byte[] bArr = new byte[i4];
                while (i3 < i4 && i3 != -1) {
                    i3 += inputStream.read(bArr, i3, i4 - i3);
                }
                return new String(bArr, charset);
            } catch (IOException e) {
                throw new CommonRuntimeException(e);
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static String getSuffix(String str) {
        return str.substring(str.lastIndexOf(46) + 1);
    }

    public static List<String> getFileContentEachLine(String str, int i) {
        return getFileContentEachLine(new File(str), i);
    }

    public static List<String> getFileContentEachLine(String str) {
        return getFileContentEachLine(new File(str), 0);
    }

    public static List<String> getFileContentEachLineTrim(String str, int i) {
        List<String> fileContentEachLine = getFileContentEachLine(str, i);
        LinkedList linkedList = new LinkedList();
        Iterator<String> it = fileContentEachLine.iterator();
        while (it.hasNext()) {
            linkedList.add(it.next().trim());
        }
        return linkedList;
    }

    public static List<String> getFileContentEachLine(File file) {
        return getFileContentEachLine(file, 0);
    }

    public static List<String> getFileContentEachLine(File file, int i) {
        LinkedList linkedList = new LinkedList();
        if (!file.exists()) {
            return linkedList;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    int i2 = 0;
                    while (i2 < i) {
                        i2++;
                        try {
                            bufferedReader.readLine();
                        } finally {
                        }
                    }
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            bufferedReader.close();
                            inputStreamReader.close();
                            fileInputStream.close();
                            return linkedList;
                        }
                        if (!Objects.equals("", line)) {
                            linkedList.add(line);
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    @Deprecated
    public static List<String> getFileContentEachLine(File file, int i, int i2, String str) {
        LinkedList linkedList = new LinkedList();
        if (!file.exists()) {
            return linkedList;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, str);
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    int i3 = 0;
                    while (i3 < i) {
                        i3++;
                        try {
                            bufferedReader.readLine();
                        } finally {
                        }
                    }
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null || i3 >= i2) {
                            break;
                        }
                        i3++;
                        linkedList.add(line);
                    }
                    bufferedReader.close();
                    inputStreamReader.close();
                    fileInputStream.close();
                    return linkedList;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static List<String> readAllLines(File file, String str, int i, int i2, boolean z) {
        ArgUtil.notNull(file, "file");
        ArgUtil.notEmpty(str, "charset");
        if (!file.exists()) {
            throw new CommonRuntimeException("File not exists!");
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                List<String> allLines = readAllLines(fileInputStream, str, i, i2, z);
                fileInputStream.close();
                return allLines;
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static List<String> readAllLines(InputStream inputStream, String str, int i, int i2, boolean z) {
        ArgUtil.notNull(inputStream, "inputStream");
        ArgUtil.notEmpty(str, "charset");
        LinkedList linkedList = new LinkedList();
        try {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, str);
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    int i3 = 0;
                    while (i3 < i) {
                        i3++;
                        try {
                            bufferedReader.readLine();
                        } finally {
                        }
                    }
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null || i3 >= i2) {
                            break;
                        }
                        i3++;
                        if (!z || !StringUtil.isEmpty(line)) {
                            linkedList.add(line);
                        }
                    }
                    bufferedReader.close();
                    inputStreamReader.close();
                    return linkedList;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            inputStreamReader.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
            } catch (IOException e) {
                throw new CommonRuntimeException(e);
            }
        } finally {
            try {
                inputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static List<String> readAllLines(InputStream inputStream, String str, int i, int i2) {
        return readAllLines(inputStream, str, i, i2, true);
    }

    public static List<String> readAllLines(InputStream inputStream, String str, int i) {
        return readAllLines(inputStream, str, i, Integer.MAX_VALUE);
    }

    public static List<String> readAllLines(InputStream inputStream, String str) {
        return readAllLines(inputStream, str, 0);
    }

    public static List<String> readAllLines(InputStream inputStream) {
        return readAllLines(inputStream, "UTF-8");
    }

    public static List<String> readAllLines(String str, String str2, boolean z) {
        return readAllLines(new File(str), str2, 0, Integer.MAX_VALUE, z);
    }

    public static List<String> readAllLines(File file, String str, boolean z) {
        return readAllLines(file, str, 0, Integer.MAX_VALUE, z);
    }

    public static List<String> readAllLines(File file, String str) {
        return readAllLines(file, str, false);
    }

    public static List<String> readAllLines(File file) {
        return readAllLines(file, "UTF-8");
    }

    public static List<String> readAllLines(String str, String str2) {
        return readAllLines(str, str2, false);
    }

    public static List<String> readAllLines(String str) {
        return readAllLines(str, "UTF-8");
    }

    public static void copyDir(String str, String str2) throws IOException {
        String[] list = new File(str).list();
        if (!new File(str2).exists()) {
            new File(str2).mkdir();
        }
        if (ArrayUtil.isNotEmpty(list)) {
            for (String str3 : list) {
                if (new File(str + File.separator + str3).isDirectory()) {
                    copyDir(str + File.separator + str3, str2 + File.separator + str3);
                }
                if (new File(str + File.separator + str3).isFile()) {
                    copyFile(str + File.separator + str3, str2 + File.separator + str3);
                }
            }
        }
    }

    public static void copyFile(String str, String str2) throws IOException {
        File file = new File(str);
        File file2 = new File(str2);
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                byte[] bArr = new byte[2097152];
                while (fileInputStream.read(bArr) != -1) {
                    fileOutputStream.write(bArr);
                }
                fileOutputStream.close();
                fileInputStream.close();
            } finally {
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    fileInputStream.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public static void write(String str, CharSequence charSequence, OpenOption... openOptionArr) {
        write(str, Collections.singletonList(charSequence), openOptionArr);
    }

    public static void write(String str, Iterable<? extends CharSequence> iterable, OpenOption... openOptionArr) {
        write(str, iterable, "UTF-8", openOptionArr);
    }

    public static void write(String str, Iterable<? extends CharSequence> iterable, String str2, OpenOption... openOptionArr) {
        try {
            ArgUtil.notNull(iterable, "charSequences");
            CharsetEncoder charsetEncoderNewEncoder = Charset.forName(str2).newEncoder();
            Path path = Paths.get(str, new String[0]);
            Path parent = path.getParent();
            if (parent != null) {
                File file = parent.toFile();
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(path.getFileSystem().provider().newOutputStream(path, openOptionArr), charsetEncoderNewEncoder));
            try {
                Iterator<? extends CharSequence> it = iterable.iterator();
                while (it.hasNext()) {
                    bufferedWriter.append(it.next());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static boolean createFile(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        if (exists(str, new LinkOption[0])) {
            return true;
        }
        File file = new File(str);
        File parentFile = file.getParentFile();
        if (parentFile != null && notExists(parentFile) && !parentFile.mkdirs()) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static boolean exists(String str, LinkOption... linkOptionArr) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        return Files.exists(Paths.get(str, new String[0]), linkOptionArr);
    }

    public static boolean notExists(String str, LinkOption... linkOptionArr) {
        return !exists(str, linkOptionArr);
    }

    public static boolean notExists(File file) {
        ArgUtil.notNull(file, "file");
        return !file.exists();
    }

    public static boolean isEmpty(String str) {
        return StringUtil.isEmpty(str) || new File(str).length() <= 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static byte[] getFileBytes(File file) {
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr;
        ArgUtil.notNull(file, "file");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream(1024);
                try {
                    bArr = new byte[1024];
                } finally {
                }
            } finally {
            }
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    fileInputStream.close();
                    return byteArray;
                }
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static byte[] getFileBytes(String str) {
        ArgUtil.notNull(str, "filePath");
        return getFileBytes(new File(str));
    }

    public static void createFile(String str, byte[] bArr) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(createFileAssertSuccess(str));
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                try {
                    bufferedOutputStream.write(bArr);
                    bufferedOutputStream.close();
                    fileOutputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static File createFileAssertSuccess(String str) {
        ArgUtil.notEmpty(str, "filePath");
        File file = new File(str);
        if (file.exists()) {
            return file;
        }
        File parentFile = file.getParentFile();
        if (notExists(parentFile) && !parentFile.mkdirs()) {
            throw new CommonRuntimeException("Parent file create fail " + str);
        }
        try {
            if (file.createNewFile()) {
                return file;
            }
            throw new CommonRuntimeException("Create new file fail for path " + str);
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static void deleteFile(File file) {
        ArgUtil.notNull(file, "file");
        if (file.exists() && !file.delete()) {
            throw new CommonRuntimeException("Delete file fail for path " + file.getAbsolutePath());
        }
    }

    public static void deleteFile(String str) {
        ArgUtil.notEmpty(str, "filePath");
        deleteFile(new File(str));
    }

    public static void deleteFileRecursive(String str) {
        ArgUtil.notEmpty(str, "filePath");
        File file = new File(str);
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                deleteFileRecursive(file2.getAbsolutePath());
            }
        }
        file.delete();
    }

    public static void deleteFileRecursive(File file) {
        ArgUtil.notNull(file, "file");
        deleteFileRecursive(file.getAbsolutePath());
    }

    public static File createTempFile(String str, String str2) {
        try {
            ArgUtil.notEmpty(str, Constants.QueryConstants.PREFIX);
            ArgUtil.notEmpty(str2, "suffix");
            return File.createTempFile(str, str2);
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static File createTempFile(String str) {
        try {
            ArgUtil.notEmpty(str, "fileName");
            String[] strArrSplit = str.split("\\.");
            return File.createTempFile(strArrSplit[0], strArrSplit[1]);
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static boolean isImage(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        return str.endsWith(".png") || str.endsWith(".jpeg") || str.endsWith(".jpg") || str.endsWith(".gif");
    }

    public static <K, V> Map<K, V> readToMap(InputStream inputStream, String str, IMapHandler<K, V, String> iMapHandler) {
        return MapUtil.toMap(readAllLines(inputStream, str), iMapHandler);
    }

    public static <K, V> Map<K, V> readToMap(String str, String str2, IMapHandler<K, V, String> iMapHandler) {
        return MapUtil.toMap(readAllLines(str, str2), iMapHandler);
    }

    public static <K, V> Map<K, V> readToMap(String str, IMapHandler<K, V, String> iMapHandler) {
        return readToMap(str, "UTF-8", iMapHandler);
    }

    public static Map<String, String> readToMap(String str) {
        return readToMap(str, " ");
    }

    public static Map<String, String> readToMap(String str, final String str2) {
        return readToMap(str, new IMapHandler<String, String, String>() { // from class: com.github.houbb.heaven.util.io.FileUtil.1
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

    public static String getFileName(String str) {
        if (StringUtil.isEmptyTrim(str)) {
            return "";
        }
        String name = new File(str).getName();
        return name.substring(0, name.lastIndexOf(46));
    }

    public static String getDirPath(String str) {
        return Paths.get(str, new String[0]).getParent().toAbsolutePath().toString() + File.separator;
    }

    public static String trimWindowsSpecialChars(String str) {
        return StringUtil.isEmpty(str) ? str : str.replaceAll("[?/\\\\*<>|:\"]", "");
    }

    public static boolean rename(String str, String str2) {
        return new File(str).renameTo(new File(str2));
    }

    public static void merge(String str, String... strArr) {
        ArgUtil.notEmpty(str, "result");
        ArgUtil.notEmpty(strArr, "sources");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            try {
                for (String str2 : strArr) {
                    fileOutputStream.write(getFileBytes(str2));
                }
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static void merge(String str, byte[]... bArr) {
        ArgUtil.notEmpty(str, "result");
        ArgUtil.notEmpty(bArr, "byteArrays");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            try {
                for (byte[] bArr2 : bArr) {
                    fileOutputStream.write(bArr2);
                }
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static void merge(String str, List<byte[]> list) {
        ArgUtil.notEmpty(str, "result");
        ArgUtil.notEmpty(list, "byteArrayList");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            try {
                Iterator<byte[]> it = list.iterator();
                while (it.hasNext()) {
                    fileOutputStream.write(it.next());
                }
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static void write(String str, byte[] bArr) {
        ArgUtil.notEmpty(str, "filePath");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            try {
                fileOutputStream.write(bArr);
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static String escapeWindowsSpecial(String str) {
        return StringUtil.isEmpty(str) ? str : str.replaceAll("[\"<>/\\\\|:*?]", "");
    }

    public static boolean createDir(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (file.isDirectory()) {
            return file.mkdirs();
        }
        return false;
    }

    public static void truncate(String str) {
        write(str, "", StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void append(String str, String str2) {
        write(str, str2, StandardOpenOption.APPEND);
    }

    public static void append(String str, Collection<String> collection) {
        write(str, collection, StandardOpenOption.APPEND);
    }

    public static String fileToBase64(String str) {
        File file = new File(str);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[(int) file.length()];
                fileInputStream.read(bArr);
                String strReplaceAll = Base64.getEncoder().encodeToString(bArr).replaceAll("\r", "").replaceAll("\n", "");
                fileInputStream.close();
                return strReplaceAll;
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void base64ToFile(String str, String str2) {
        createFile(str2);
        if (str.contains(PunctuationConst.COMMA)) {
            str = str.split(PunctuationConst.COMMA)[1];
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            try {
                fileOutputStream.write(Base64.getDecoder().decode(str));
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedReader getBufferedReader(String str) {
        try {
            ArgUtil.notEmpty(str, "sourceFilePath");
            return new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(str, new String[0]), new OpenOption[0])));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeBufferReader(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static BufferedWriter getBufferedWriter(String str) {
        try {
            ArgUtil.notEmpty(str, "filePath");
            return new BufferedWriter(new FileWriter(str));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeBufferedWriter(BufferedWriter bufferedWriter) {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void sortedUnique(String str, String str2) throws IOException {
        try {
            BufferedReader bufferedReader = getBufferedReader(str);
            try {
                BufferedWriter bufferedWriter = getBufferedWriter(str2);
                try {
                    Object obj = null;
                    for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                        if (!line.equals(obj)) {
                            bufferedWriter.write(line);
                            bufferedWriter.newLine();
                        }
                        obj = line;
                    }
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isMdFile(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        return str.endsWith(".md") || str.endsWith(".markdown");
    }

    public static List<Path> getMdFilePathList(Path path, boolean z) {
        try {
            if (z) {
                MarkdownFileWalker markdownFileWalker = new MarkdownFileWalker();
                Files.walkFileTree(path, markdownFileWalker);
                return markdownFileWalker.getPathList();
            }
            File[] fileArrListFiles = path.toFile().listFiles(new FilenameFilter() { // from class: com.github.houbb.heaven.util.io.FileUtil.2
                @Override // java.io.FilenameFilter
                public boolean accept(File file, String str) {
                    return FileUtil.isMdFile(str);
                }
            });
            if (ArrayUtil.isNotEmpty(fileArrListFiles)) {
                ArrayList arrayList = new ArrayList();
                for (File file : fileArrListFiles) {
                    arrayList.add(file.toPath());
                }
                return arrayList;
            }
            return Collections.emptyList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Path> getAllFilePathList(String str) {
        try {
            AllFileVisitor allFileVisitor = new AllFileVisitor();
            Files.walkFileTree(Paths.get(str, new String[0]), allFileVisitor);
            return allFileVisitor.getPathList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAllFileList(File file, List<File> list) {
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (ArrayUtil.isNotEmpty(fileArrListFiles)) {
                for (File file2 : fileArrListFiles) {
                    getAllFileList(file2, list);
                }
                return;
            }
            return;
        }
        list.add(file);
    }

    public static String buildFullPath(String str, String str2) {
        if (str2.startsWith("/")) {
            str2 = str2.substring(1);
        }
        if (!str.endsWith("/")) {
            return str + "/" + str2;
        }
        return str + str2;
    }

    public static void main(String[] strArr) {
        System.out.println(fileToBase64("C:\\Users\\dh\\Pictures\\Screenshots\\config.png"));
    }
}
