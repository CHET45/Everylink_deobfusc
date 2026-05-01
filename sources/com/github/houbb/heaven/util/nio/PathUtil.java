package com.github.houbb.heaven.util.nio;

import com.github.houbb.heaven.constant.FileTypeConst;
import com.github.houbb.heaven.constant.PathConst;
import com.github.houbb.heaven.constant.SystemConst;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class PathUtil {
    public static final Path ROOT_PATH = Paths.get("/", new String[0]);

    private PathUtil() {
    }

    public static String getRelativePath(Path path, Path path2) {
        String string = path2.toString();
        if (ObjectUtil.isNull(path) || path.toString().length() <= 1) {
            return string;
        }
        String string2 = path.toString();
        return string.startsWith(string2) ? string.substring(string2.length() + 1) : string;
    }

    public static Path getPublicParentPath(List<Path> list) {
        if (list.size() == 1) {
            return getParentPath(list.get(0));
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Path> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(CollectionUtil.toStringList(getParentPaths(it.next())));
        }
        return Paths.get(getMaxLength(retainAll(arrayList)), new String[0]);
    }

    private static String getMaxLength(List<String> list) {
        String str = "";
        for (String str2 : list) {
            if (str2.length() > str.length()) {
                str = str2;
            }
        }
        return str;
    }

    public static List<Path> getParentPaths(Path path) {
        if (ObjectUtil.isNull(path)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Path parent = path.getParent(); ObjectUtil.isNotNull(parent); parent = parent.getParent()) {
            arrayList.add(parent);
        }
        if (CollectionUtil.isEmpty(arrayList)) {
            arrayList.add(ROOT_PATH);
        }
        return arrayList;
    }

    public static Path getParentPath(Path path) {
        Path parent = path.getParent();
        return ObjectUtil.isNull(parent) ? ROOT_PATH : parent;
    }

    public static List<String> retainAll(List<List<String>> list) {
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        List<String> list2 = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            list2.retainAll(list.get(i));
        }
        return list2;
    }

    public static List<Path> getPathList(Path path) {
        final ArrayList arrayList = new ArrayList();
        try {
            if (Files.isDirectory(path, new LinkOption[0])) {
                Files.walkFileTree(path, new SimpleFileVisitor<Path>() { // from class: com.github.houbb.heaven.util.nio.PathUtil.1
                    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                    public FileVisitResult visitFile(Path path2, BasicFileAttributes basicFileAttributes) throws IOException {
                        arrayList.add(path2);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                    public FileVisitResult postVisitDirectory(Path path2, IOException iOException) throws IOException {
                        arrayList.add(path2);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                arrayList.add(path);
            }
            return arrayList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Path> getPathList(String str, String str2) {
        LinkedList linkedList = new LinkedList();
        try {
            DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(Paths.get(str, new String[0]), str2);
            try {
                Iterator<Path> it = directoryStreamNewDirectoryStream.iterator();
                while (it.hasNext()) {
                    linkedList.add(it.next());
                }
                if (directoryStreamNewDirectoryStream != null) {
                    directoryStreamNewDirectoryStream.close();
                }
                return linkedList;
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Path> getDirFileNames(String str, String str2) {
        LinkedList linkedList = new LinkedList();
        try {
            DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(Paths.get(str, new String[0]), str2);
            try {
                Iterator<Path> it = directoryStreamNewDirectoryStream.iterator();
                while (it.hasNext()) {
                    linkedList.add(it.next().getFileName());
                }
                if (directoryStreamNewDirectoryStream != null) {
                    directoryStreamNewDirectoryStream.close();
                }
                return linkedList;
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Path> getAllDirFileNames(String str) {
        return getDirFileNames(str, FileTypeConst.Glob.ALL);
    }

    public static List<String> getDirFileNameStrs(String str, String str2) {
        LinkedList linkedList = new LinkedList();
        try {
            DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(Paths.get(str, new String[0]), str2);
            try {
                Iterator<Path> it = directoryStreamNewDirectoryStream.iterator();
                while (it.hasNext()) {
                    linkedList.add(it.next().getFileName().toString());
                }
                if (directoryStreamNewDirectoryStream != null) {
                    directoryStreamNewDirectoryStream.close();
                }
                return linkedList;
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPath() {
        return System.getProperty(SystemConst.USER_DIR);
    }

    public static String getRootPath() {
        return Class.class.getClass().getResource("/").getPath();
    }

    public static String getAppRootPath() {
        return new File("").getAbsolutePath();
    }

    public static String getAppResourcesPath() {
        return getAppRootPath() + PathConst.SRC_MAIN_RESOURCES_PATH;
    }

    public static String getAppTestResourcesPath() {
        return getAppRootPath() + "/src/test/resources";
    }

    public static String getRootPath(Class cls) {
        return cls.getResource("/").toString().replace(PathConst.FILE_PATH_PREFIX, "").replace("target/classes/", "src/main/java/");
    }

    public static String getPath(Class cls) {
        return cls.getResource("").toString().replace(PathConst.FILE_PATH_PREFIX, "").replace("target/classes/", "src/main/java/");
    }

    public static String packageToPath(String str) {
        return str.replaceAll("\\.", "/");
    }

    public static List<String> readAllLines(String str) {
        return readAllLines(str, "UTF-8");
    }

    public static List<String> readAllLines(String str, String str2) {
        return readAllLines(str, str2, 0, Integer.MAX_VALUE);
    }

    public static List<String> readAllLines(String str, String str2, int i, int i2) {
        ArgUtil.notEmpty(str, "pathStr");
        ArgUtil.notEmpty(str2, "charset");
        ArgUtil.assertTrue(i2 >= i, "endIndex >= startIndex");
        try {
            List<String> allLines = Files.readAllLines(Paths.get(str, new String[0]), Charset.forName(str2));
            int size = allLines.size();
            if (i2 > size) {
                i2 = size;
            }
            return allLines.subList(i, i2);
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static void writeLines(String str, String... strArr) {
        writeLines(str, Guavas.newArrayList(strArr), "UTF-8", new OpenOption[0]);
    }

    public static void writeLines(String str, Collection<String> collection) {
        writeLines(str, collection, "UTF-8", new OpenOption[0]);
    }

    public static void appendLines(String str, String... strArr) {
        writeLines(str, Guavas.newArrayList(strArr), "UTF-8", StandardOpenOption.APPEND);
    }

    public static void appendLines(String str, Collection<String> collection) {
        writeLines(str, collection, "UTF-8", StandardOpenOption.APPEND);
    }

    public static void writeLines(String str, Collection<String> collection, String str2, OpenOption... openOptionArr) {
        ArgUtil.notEmpty(str, "pathStr");
        ArgUtil.notEmpty(str2, "charset");
        ArgUtil.notEmpty(collection, "lines");
        try {
            Files.write(Paths.get(str, new String[0]), collection, Charset.forName(str2), openOptionArr);
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }
}
