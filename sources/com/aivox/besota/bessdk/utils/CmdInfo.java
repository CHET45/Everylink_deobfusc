package com.aivox.besota.bessdk.utils;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class CmdInfo {
    public static final int HEAD_LEN = 4;
    short cmdType;
    short dataLen;
    byte[] extData;

    public CmdInfo(short s, byte[] bArr) {
        if (bArr != null) {
            this.dataLen = (short) bArr.length;
        }
        this.extData = bArr;
        this.cmdType = s;
    }

    public short getCmdType() {
        return this.cmdType;
    }

    public void setCmdType(short s) {
        this.cmdType = s;
    }

    public short getDataLen() {
        return this.dataLen;
    }

    public void setDataLen(short s) {
        this.dataLen = s;
    }

    public byte[] getExtData() {
        return this.extData;
    }

    public void setExtData(byte[] bArr) {
        this.extData = bArr;
    }

    public byte[] toBytes() {
        byte[] bArr;
        short s = this.cmdType;
        if (s != 0 && this.dataLen != 0 && (bArr = this.extData) != null) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length + 4);
            byteBufferAllocate.put((byte) (this.cmdType & 255));
            byteBufferAllocate.put((byte) ((this.cmdType >> 8) & 255));
            byteBufferAllocate.put((byte) (this.extData.length & 255));
            byteBufferAllocate.put((byte) ((this.extData.length >> 8) & 255));
            byteBufferAllocate.put(this.extData);
            return byteBufferAllocate.array();
        }
        if (s == 0 || this.dataLen != 0) {
            return null;
        }
        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(4);
        byteBufferAllocate2.put((byte) (this.cmdType & 255));
        byteBufferAllocate2.put((byte) ((this.cmdType >> 8) & 255));
        byteBufferAllocate2.put((byte) (this.dataLen & 255));
        byteBufferAllocate2.put((byte) ((this.dataLen >> 8) & 255));
        return byteBufferAllocate2.array();
    }
}
