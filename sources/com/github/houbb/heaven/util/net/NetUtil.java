package com.github.houbb.heaven.util.net;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.util.regex.RegexUtil;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class NetUtil {
    public static final String LOCALHOST = "127.0.0.1";
    private static final String LOCAL_HOST;

    private NetUtil() {
    }

    static {
        try {
            LOCAL_HOST = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static String getLocalHost() {
        return LOCAL_HOST;
    }

    public static boolean isReachable(String str) {
        return isReachable(str, 5000);
    }

    public static boolean isReachable(String str, int i) {
        try {
            return InetAddress.getByName(str).isReachable(i);
        } catch (Exception unused) {
            return false;
        }
    }

    public static void assertOnLine() {
        try {
            InetAddress.getByName("translate.google.cn");
        } catch (UnknownHostException unused) {
            throw new RuntimeException("The net work is broken, check your network or set isCommentWhenNetworkBroken=true.");
        }
    }

    public static String getLocalIp() {
        InetAddress inetAddressFindLocalAddress = findLocalAddress();
        if (inetAddressFindLocalAddress == null) {
            return null;
        }
        String hostAddress = inetAddressFindLocalAddress.getHostAddress();
        if (RegexUtil.isIp(hostAddress)) {
            return hostAddress;
        }
        return null;
    }

    private static InetAddress findLocalAddress() {
        InetAddress inetAddressFindAvailableAddress;
        HashMap map = new HashMap();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement.isUp() && networkInterfaceNextElement.supportsMulticast()) {
                    String name = networkInterfaceNextElement.getName();
                    if (name.startsWith("bond0")) {
                        InetAddress inetAddressFindAvailableAddress2 = findAvailableAddress(networkInterfaceNextElement);
                        if (inetAddressFindAvailableAddress2 != null) {
                            return inetAddressFindAvailableAddress2;
                        }
                    } else {
                        map.put(name, networkInterfaceNextElement);
                    }
                }
            }
            for (String str : "bond0,eth0,em0,en0,em1,br0,eth1,em2,en1,eth2,em3,en2,eth3,em4,en3".split(PunctuationConst.COMMA)) {
                NetworkInterface networkInterface = (NetworkInterface) map.get(str);
                if (networkInterface != null && (inetAddressFindAvailableAddress = findAvailableAddress(networkInterface)) != null) {
                    return inetAddressFindAvailableAddress;
                }
            }
            return null;
        } catch (SocketException e) {
            throw new CommonRuntimeException(e);
        }
    }

    private static InetAddress findAvailableAddress(NetworkInterface networkInterface) {
        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
        while (inetAddresses.hasMoreElements()) {
            InetAddress inetAddressNextElement = inetAddresses.nextElement();
            if (!(inetAddressNextElement instanceof Inet6Address) && !inetAddressNextElement.isLoopbackAddress()) {
                return inetAddressNextElement;
            }
        }
        return null;
    }
}
