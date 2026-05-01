package com.github.houbb.heaven.util.p009id.impl;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.p009id.InterfaceC1503Id;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ThreadLocalRandom;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public class SnowFlakeId implements InterfaceC1503Id {
    private static final long datacenterIdBits = 5;
    private static final long maxDatacenterId = 31;
    private static final long maxWorkerId = 31;
    private static final long workerIdBits = 5;
    private long datacenterId;
    private long workerId;
    private long sequence = 0;
    private long lastTimestamp = -1;

    public SnowFlakeId() {
        long datacenterId = getDatacenterId(31L);
        this.datacenterId = datacenterId;
        this.workerId = getMaxWorkerId(datacenterId, 31L);
    }

    public SnowFlakeId(long j, long j2) {
        if (j > 31 || j < 0) {
            throw new RuntimeException(String.format("worker Id can't be greater than %d or less than 0", 31L));
        }
        if (j2 > 31 || j2 < 0) {
            throw new RuntimeException(String.format("datacenter Id can't be greater than %d or less than 0", 31L));
        }
        this.workerId = j;
        this.datacenterId = j2;
    }

    protected static long getMaxWorkerId(long j, long j2) {
        StringBuilder sb = new StringBuilder();
        sb.append(j);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtil.isNotEmpty(name)) {
            sb.append(name.split(PunctuationConst.f488AT)[0]);
        }
        return ((long) (sb.toString().hashCode() & 65535)) % (j2 + 1);
    }

    protected static long getDatacenterId(long j) {
        try {
            NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            if (byInetAddress == null) {
                return 1L;
            }
            byte[] hardwareAddress = byInetAddress.getHardwareAddress();
            if (hardwareAddress != null) {
                return (((((long) hardwareAddress[hardwareAddress.length - 1]) & 255) | ((((long) hardwareAddress[hardwareAddress.length - 2]) << 8) & 65280)) >> 6) % (j + 1);
            }
            return 0L;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized long nextId() {
        long jTimeGen;
        jTimeGen = timeGen();
        long j = this.lastTimestamp;
        if (jTimeGen < j) {
            long j2 = j - jTimeGen;
            if (j2 <= 5) {
                try {
                    wait(j2 << 1);
                    jTimeGen = timeGen();
                    if (jTimeGen < this.lastTimestamp) {
                        throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", Long.valueOf(j2)));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", Long.valueOf(j2)));
            }
        }
        long j3 = this.lastTimestamp;
        if (j3 == jTimeGen) {
            long j4 = (this.sequence + 1) & ((-1) ^ ((-1) << ((int) 12)));
            this.sequence = j4;
            if (j4 == 0) {
                jTimeGen = tilNextMillis(j3);
            }
        } else {
            this.sequence = ThreadLocalRandom.current().nextLong(1L, 3L);
        }
        this.lastTimestamp = jTimeGen;
        return ((jTimeGen - 1288834974657L) << ((int) 22)) | (this.datacenterId << ((int) 17)) | (this.workerId << ((int) 12)) | this.sequence;
    }

    protected long tilNextMillis(long j) {
        long jTimeGen = timeGen();
        while (jTimeGen <= j) {
            jTimeGen = timeGen();
        }
        return jTimeGen;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    @Override // com.github.houbb.heaven.util.p009id.InterfaceC1503Id
    public String genId() {
        return String.valueOf(nextId());
    }
}
