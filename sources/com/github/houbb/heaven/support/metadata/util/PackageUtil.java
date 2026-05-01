package com.github.houbb.heaven.support.metadata.util;

import com.github.houbb.heaven.constant.CharConst;
import com.github.houbb.heaven.constant.FileProtocolConst;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.support.metadata.constant.PackageConst;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;

/* JADX INFO: loaded from: classes3.dex */
public final class PackageUtil {
    public static String getPackageName(Class cls) {
        return cls.getPackage().getName();
    }

    public static String getSlimPackageName(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        String[] strArrSplit = str.split("\\.");
        ArrayList arrayList = new ArrayList(strArrSplit.length);
        for (int i = 0; i < strArrSplit.length - 1; i++) {
            arrayList.add(strArrSplit[i].charAt(0) + "");
        }
        arrayList.add(strArrSplit[strArrSplit.length - 1]);
        return StringUtil.join(arrayList, ".");
    }

    public static boolean isSamePackage(String str, Class cls) {
        return str.equals(getPackageName(cls));
    }

    public static boolean isJavaLangPackage(Class cls) {
        return PackageConst.JAVA_LANG.equals(getPackageName(cls));
    }

    public static Set<String> scanPackageClassNameSet(String str) {
        ArgUtil.notEmpty(str, "packageNames");
        Set<String> setNewHashSet = Guavas.newHashSet();
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(str.replace(CharConst.DOT, '/'));
            while (resources.hasMoreElements()) {
                URL urlNextElement = resources.nextElement();
                String protocol = urlNextElement.getProtocol();
                if ("file".equals(protocol)) {
                    File file = new File(URLDecoder.decode(urlNextElement.getFile(), "UTF-8"));
                    if (file.isDirectory()) {
                        File[] fileArrListFiles = file.listFiles();
                        if (ArrayUtil.isNotEmpty(fileArrListFiles)) {
                            for (File file2 : fileArrListFiles) {
                                recursiveFile(str, file2, setNewHashSet);
                            }
                        }
                    }
                } else if (FileProtocolConst.JAR.equals(protocol)) {
                    Enumeration<JarEntry> enumerationEntries = ((JarURLConnection) urlNextElement.openConnection()).getJarFile().entries();
                    enumerationEntries.nextElement();
                    while (enumerationEntries.hasMoreElements()) {
                        JarEntry jarEntryNextElement = enumerationEntries.nextElement();
                        jarEntryNextElement.isDirectory();
                        System.out.println("jar " + jarEntryNextElement.getName());
                    }
                } else {
                    System.err.println("Not support protocol: " + protocol);
                }
            }
            return setNewHashSet;
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    private static void recursiveFile(String str, File file, Set<String> set) {
        if (file.isFile()) {
            set.add(str + "." + file.getName().split("\\.")[0]);
            return;
        }
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            String str2 = str + "." + file.getName();
            if (ArrayUtil.isNotEmpty(fileArrListFiles)) {
                for (File file2 : fileArrListFiles) {
                    recursiveFile(str2, file2, set);
                }
            }
        }
    }

    public static void main(String[] strArr) {
        System.out.println(getSlimPackageName("com.github.houbb.StringUtil"));
        System.out.println(getSlimPackageName("StringUtil"));
    }
}
