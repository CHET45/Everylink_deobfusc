package org.minidns.constants;

import com.aivox.base.common.Constant;
import com.alibaba.fastjson.asm.Opcodes;
import com.jieli.jl_bt_ota.constant.Command;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.videolan.libvlc.MediaDiscoverer;
import org.videolan.libvlc.RendererDiscoverer;

/* JADX INFO: loaded from: classes4.dex */
public class DnsRootServer {
    private static final Map<Character, Inet4Address> IPV4_ROOT_SERVER_MAP = new HashMap();
    private static final Map<Character, Inet6Address> IPV6_ROOT_SERVER_MAP = new HashMap();
    protected static final Inet4Address[] IPV4_ROOT_SERVERS = {rootServerInet4Address('a', Opcodes.IFNULL, 41, 0, 4), rootServerInet4Address('b', Opcodes.CHECKCAST, Command.CMD_OTA_EXIT_UPDATE_MODE, 79, Constant.EVENT.AUDIO_UPLOAD_PROGRESS), rootServerInet4Address('c', Opcodes.CHECKCAST, 33, 4, 12), rootServerInet4Address('d', 199, 7, 91, 13), rootServerInet4Address('e', Opcodes.CHECKCAST, Constant.EVENT.AUDIO_UPLOAD_FAILED, Command.CMD_OTA_GET_DEVICE_REFRESH_FIRMWARE_STATUS, 10), rootServerInet4Address('f', Opcodes.CHECKCAST, 5, 5, 241), rootServerInet4Address('g', Opcodes.CHECKCAST, 112, 36, 4), rootServerInet4Address('h', Opcodes.IFNULL, 97, 190, 53), rootServerInet4Address('i', Opcodes.CHECKCAST, 36, Opcodes.LCMP, 17), rootServerInet4Address('j', Opcodes.CHECKCAST, 58, 128, 30), rootServerInet4Address('k', Opcodes.INSTANCEOF, 0, 14, 129), rootServerInet4Address('l', 199, 7, 83, 42), rootServerInet4Address('m', Constant.EVENT.AUDIO_UPLOAD_COMPLETE, 12, 27, 33)};
    protected static final Inet6Address[] IPV6_ROOT_SERVERS = {rootServerInet6Address('a', 8193, RendererDiscoverer.Event.ItemDeleted, 47678, 0, 0, 0, 2, 48), rootServerInet6Address('b', 8193, MediaDiscoverer.Event.Started, 132, 0, 0, 0, 0, 11), rootServerInet6Address('c', 8193, MediaDiscoverer.Event.Started, 2, 0, 0, 0, 0, 12), rootServerInet6Address('d', 8193, MediaDiscoverer.Event.Started, 45, 0, 0, 0, 0, 13), rootServerInet6Address('f', 8193, MediaDiscoverer.Event.Started, 47, 0, 0, 0, 0, 15), rootServerInet6Address('h', 8193, MediaDiscoverer.Event.Started, 1, 0, 0, 0, 0, 83), rootServerInet6Address('i', 8193, 2046, 0, 0, 0, 0, 0, 83), rootServerInet6Address('j', 8193, RendererDiscoverer.Event.ItemDeleted, 3111, 0, 0, 0, 2, 48), rootServerInet6Address('l', 8193, MediaDiscoverer.Event.Started, 3, 0, 0, 0, 0, 66), rootServerInet6Address('m', 8193, 3523, 0, 0, 0, 0, 0, 53)};

    private static Inet4Address rootServerInet4Address(char c, int i, int i2, int i3, int i4) {
        try {
            Inet4Address inet4Address = (Inet4Address) InetAddress.getByAddress(c + ".root-servers.net", new byte[]{(byte) i, (byte) i2, (byte) i3, (byte) i4});
            IPV4_ROOT_SERVER_MAP.put(Character.valueOf(c), inet4Address);
            return inet4Address;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static Inet6Address rootServerInet6Address(char c, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        try {
            Inet6Address inet6Address = (Inet6Address) InetAddress.getByAddress(c + ".root-servers.net", new byte[]{(byte) (i >> 8), (byte) i, (byte) (i2 >> 8), (byte) i2, (byte) (i3 >> 8), (byte) i3, (byte) (i4 >> 8), (byte) i4, (byte) (i5 >> 8), (byte) i5, (byte) (i6 >> 8), (byte) i6, (byte) (i7 >> 8), (byte) i7, (byte) (i8 >> 8), (byte) i8});
            IPV6_ROOT_SERVER_MAP.put(Character.valueOf(c), inet6Address);
            return inet6Address;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static Inet4Address getRandomIpv4RootServer(Random random) {
        Inet4Address[] inet4AddressArr = IPV4_ROOT_SERVERS;
        return inet4AddressArr[random.nextInt(inet4AddressArr.length)];
    }

    public static Inet6Address getRandomIpv6RootServer(Random random) {
        Inet6Address[] inet6AddressArr = IPV6_ROOT_SERVERS;
        return inet6AddressArr[random.nextInt(inet6AddressArr.length)];
    }

    public static Inet4Address getIpv4RootServerById(char c) {
        return IPV4_ROOT_SERVER_MAP.get(Character.valueOf(c));
    }

    public static Inet6Address getIpv6RootServerById(char c) {
        return IPV6_ROOT_SERVER_MAP.get(Character.valueOf(c));
    }
}
