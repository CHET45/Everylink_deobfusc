package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.constant.SystemConst;
import java.io.IOException;
import java.net.ServerSocket;

/* JADX INFO: loaded from: classes3.dex */
public final class OsUtil {
    private static final String ARCH = System.getProperty("sun.arch.data.model");

    /* JADX INFO: renamed from: OS */
    private static final String f542OS = System.getProperty(SystemConst.OS_NAME).toLowerCase();

    private OsUtil() {
    }

    public static int getFreePort(int i) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(i);
            try {
                int localPort = serverSocket.getLocalPort();
                serverSocket.close();
                return localPort;
            } finally {
            }
        } catch (IOException unused) {
            return getFreePort();
        }
    }

    public static int getFreePort() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
        try {
            int localPort = serverSocket.getLocalPort();
            serverSocket.close();
            return localPort;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    serverSocket.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public static boolean isBusyPort(int i) {
        try {
            new ServerSocket(i).close();
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isWindows() {
        return f542OS.contains("win");
    }

    public static boolean isWindowsXP() {
        String str = f542OS;
        return str.contains("win") && str.contains("xp");
    }

    public static boolean isMac() {
        return f542OS.contains("mac");
    }

    public static boolean isUnix() {
        String str = f542OS;
        return str.contains("nix") || str.contains("nux") || str.contains("aix");
    }

    public static boolean isSolaris() {
        return f542OS.contains("sunos");
    }

    public static boolean is64() {
        return "64".equals(ARCH);
    }

    public static boolean is32() {
        return "32".equals(ARCH);
    }
}
