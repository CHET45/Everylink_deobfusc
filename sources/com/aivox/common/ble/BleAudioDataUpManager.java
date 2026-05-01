package com.aivox.common.ble;

import com.aivox.base.common.Constant;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.libOpus.utils.OpusUtils;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/* JADX INFO: loaded from: classes.dex */
public class BleAudioDataUpManager {
    private static BleAudioDataUpManager mInstance;
    private BlockingQueue<byte[]> downDataQueue;
    long encoder;
    private ExecutorService mExecutor;
    OpusUtils opusUtils;
    private Timer timer;
    private BlockingQueue<byte[]> upDataQueue;
    private BlockingQueue<byte[]> waitSendQueue;
    int BUFFER_LENGTH = 40;
    int DEFAULT_AUDIO_SAMPLE_RATE = 8000;
    int DEFAULT_OPUS_CHANNEL = 2;
    private volatile boolean isOver = false;
    private int msTimeInterval = 10;

    private BleAudioDataUpManager() {
        if (this.encoder == 0) {
            OpusUtils instant = OpusUtils.INSTANCE.getInstant();
            this.opusUtils = instant;
            this.encoder = instant.createEncoder(this.DEFAULT_AUDIO_SAMPLE_RATE, this.DEFAULT_OPUS_CHANNEL, 0);
            this.waitSendQueue = new LinkedBlockingDeque();
            this.upDataQueue = new LinkedBlockingDeque();
            this.downDataQueue = new LinkedBlockingDeque();
        }
    }

    public static BleAudioDataUpManager getInstance() {
        if (mInstance == null) {
            mInstance = new BleAudioDataUpManager();
        }
        return mInstance;
    }

    public void startEncoder(String str) {
        this.isOver = false;
        initEncodeThread();
        start();
    }

    private void initEncodeThread() {
        if (this.mExecutor == null) {
            this.mExecutor = Executors.newSingleThreadExecutor();
        }
        this.mExecutor.submit(new Runnable() { // from class: com.aivox.common.ble.BleAudioDataUpManager.1
            @Override // java.lang.Runnable
            public void run() {
                while (!BleAudioDataUpManager.this.isOver) {
                    BleAudioDataUpManager.this.encodeData();
                }
            }
        });
    }

    public void setOver(boolean z) {
        this.isOver = z;
    }

    public void encodeData() {
        if (this.upDataQueue.isEmpty() && this.downDataQueue.isEmpty()) {
            return;
        }
        try {
            byte[] dataToEncode = getDataToEncode();
            byte[] bArr = new byte[this.BUFFER_LENGTH];
            this.opusUtils.encode(this.encoder, BaseStringUtil.toShortArrayLittle(dataToEncode), 0, bArr);
            this.waitSendQueue.put(bArr);
        } catch (InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void stopAll() {
        this.isOver = true;
        stop();
        ExecutorService executorService = this.mExecutor;
        if (executorService != null) {
            executorService.shutdown();
            this.mExecutor = null;
        }
        BlockingQueue<byte[]> blockingQueue = this.waitSendQueue;
        if (blockingQueue != null) {
            blockingQueue.clear();
        }
        BlockingQueue<byte[]> blockingQueue2 = this.upDataQueue;
        if (blockingQueue2 != null) {
            blockingQueue2.clear();
        }
        BlockingQueue<byte[]> blockingQueue3 = this.downDataQueue;
        if (blockingQueue3 != null) {
            blockingQueue3.clear();
        }
    }

    public void destroy() {
        stopAll();
        OpusUtils opusUtils = this.opusUtils;
        if (opusUtils != null) {
            opusUtils.destroyEncoder(this.encoder);
        }
        this.encoder = 0L;
        mInstance = null;
    }

    public static byte[][] splitByteArray(byte[] bArr, int i) {
        int iCeil = (int) Math.ceil(((double) bArr.length) / ((double) i));
        byte[][] bArr2 = new byte[iCeil][];
        for (int i2 = 0; i2 < iCeil; i2++) {
            int i3 = i2 * i;
            int iMin = Math.min(i3 + i, bArr.length) - i3;
            byte[] bArr3 = new byte[iMin];
            System.arraycopy(bArr, i3, bArr3, 0, iMin);
            bArr2[i2] = bArr3;
        }
        return bArr2;
    }

    public void start() {
        stop();
        Timer timer = new Timer();
        this.timer = timer;
        timer.schedule(new TimerTask() { // from class: com.aivox.common.ble.BleAudioDataUpManager.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                BleAudioDataUpManager.this.sendData();
            }
        }, 0L, 10 * ((long) this.msTimeInterval));
    }

    public void stop() {
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
            this.timer = null;
        }
    }

    public void resetIntervalIfNeeded(int i) {
        int i2;
        if (this.isOver || (i2 = this.msTimeInterval) == i || i <= 0) {
            return;
        }
        if (this.timer != null) {
            if (i2 > i) {
                sendData();
            }
            this.msTimeInterval = i;
            start();
            return;
        }
        this.msTimeInterval = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendData() {
        try {
            if (this.waitSendQueue.isEmpty()) {
                return;
            }
            CommonServiceUtils.getInstance().sendData(addHeader(copyMutiByteArray(this.waitSendQueue.take(), this.waitSendQueue.take(), this.waitSendQueue.take(), this.waitSendQueue.take(), this.waitSendQueue.take(), this.waitSendQueue.take(), this.waitSendQueue.take(), this.waitSendQueue.take(), this.waitSendQueue.take(), this.waitSendQueue.take())));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private byte[] copyMutiByteArray(byte[]... bArr) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length * this.BUFFER_LENGTH);
        for (byte[] bArr2 : bArr) {
            byteBufferAllocate.put(bArr2);
        }
        return byteBufferAllocate.array();
    }

    public void appendUpTTSData(byte[] bArr) {
        for (byte[] bArr2 : splitByteArray(processData(bArr), this.BUFFER_LENGTH * 4)) {
            try {
                this.upDataQueue.put(bArr2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendDownTTSData(byte[] bArr) {
        for (byte[] bArr2 : splitByteArray(processData(bArr), this.BUFFER_LENGTH * 4)) {
            try {
                this.downDataQueue.put(bArr2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] processData(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length = bArr.length;
        int i = 0;
        while (true) {
            int i2 = i + 4;
            if (i2 <= length) {
                byteArrayOutputStream.write(bArr[i]);
                byteArrayOutputStream.write(bArr[i + 1]);
                i = i2;
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private byte[] getDataToEncode() {
        /*
            r6 = this;
            java.util.concurrent.BlockingQueue<byte[]> r0 = r6.upDataQueue
            boolean r0 = r0.isEmpty()
            r1 = 0
            if (r0 == 0) goto L12
            java.util.concurrent.BlockingQueue<byte[]> r0 = r6.downDataQueue
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L12
            return r1
        L12:
            r0 = 0
            java.util.concurrent.BlockingQueue<byte[]> r2 = r6.upDataQueue     // Catch: java.lang.InterruptedException -> L47
            boolean r2 = r2.isEmpty()     // Catch: java.lang.InterruptedException -> L47
            if (r2 != 0) goto L25
            java.util.concurrent.BlockingQueue<byte[]> r2 = r6.upDataQueue     // Catch: java.lang.InterruptedException -> L47
            java.lang.Object r2 = r2.take()     // Catch: java.lang.InterruptedException -> L47
            byte[] r2 = (byte[]) r2     // Catch: java.lang.InterruptedException -> L47
            int r3 = r2.length     // Catch: java.lang.InterruptedException -> L47
            goto L2c
        L25:
            int r2 = r6.BUFFER_LENGTH     // Catch: java.lang.InterruptedException -> L47
            int r2 = r2 * 4
            byte[] r2 = new byte[r2]     // Catch: java.lang.InterruptedException -> L47
            r3 = r0
        L2c:
            java.util.concurrent.BlockingQueue<byte[]> r4 = r6.downDataQueue     // Catch: java.lang.InterruptedException -> L45
            boolean r4 = r4.isEmpty()     // Catch: java.lang.InterruptedException -> L45
            if (r4 != 0) goto L3e
            java.util.concurrent.BlockingQueue<byte[]> r4 = r6.downDataQueue     // Catch: java.lang.InterruptedException -> L45
            java.lang.Object r4 = r4.take()     // Catch: java.lang.InterruptedException -> L45
            byte[] r4 = (byte[]) r4     // Catch: java.lang.InterruptedException -> L45
            int r5 = r4.length     // Catch: java.lang.InterruptedException -> L45
            goto L59
        L3e:
            int r4 = r6.BUFFER_LENGTH     // Catch: java.lang.InterruptedException -> L45
            int r4 = r4 * 4
            byte[] r4 = new byte[r4]     // Catch: java.lang.InterruptedException -> L45
            goto L58
        L45:
            r2 = move-exception
            goto L49
        L47:
            r2 = move-exception
            r3 = r0
        L49:
            int r4 = r6.BUFFER_LENGTH
            int r5 = r4 * 4
            byte[] r5 = new byte[r5]
            int r4 = r4 * 4
            byte[] r4 = new byte[r4]
            r2.printStackTrace()
            r2 = r4
            r4 = r5
        L58:
            r5 = r0
        L59:
            if (r3 != 0) goto L5e
            if (r5 != 0) goto L5e
            return r1
        L5e:
            int r1 = r2.length
            r3 = 160(0xa0, float:2.24E-43)
            if (r1 >= r3) goto L6c
            int r1 = r2.length
            int r1 = 160 - r1
            byte[] r1 = new byte[r1]
            byte[] r2 = r6.appendByteArray(r2, r1)
        L6c:
            int r1 = r4.length
            if (r1 >= r3) goto L77
            int r1 = r4.length
            int r3 = r3 - r1
            byte[] r1 = new byte[r3]
            byte[] r4 = r6.appendByteArray(r4, r1)
        L77:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            r1.<init>()
        L7c:
            int r3 = r6.BUFFER_LENGTH
            int r3 = r3 * 4
            if (r0 >= r3) goto La1
            int r3 = r0 + 1
            int r5 = r4.length
            if (r3 >= r5) goto L91
            r5 = r4[r0]
            r1.write(r5)
            r5 = r4[r3]
            r1.write(r5)
        L91:
            int r5 = r2.length
            if (r3 >= r5) goto L9e
            r5 = r2[r0]
            r1.write(r5)
            r3 = r2[r3]
            r1.write(r3)
        L9e:
            int r0 = r0 + 2
            goto L7c
        La1:
            byte[] r0 = r1.toByteArray()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.ble.BleAudioDataUpManager.getDataToEncode():byte[]");
    }

    private byte[] addHeader(byte[] bArr) {
        if (bArr == null) {
            return bArr;
        }
        byte[] bytes = "OPUS".getBytes();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bytes.length + 2 + bArr.length);
        byteBufferAllocate.put(bytes);
        byteBufferAllocate.put(intToByteArray16(Constant.EVENT.BLE_GLASS_ASK_AI));
        byteBufferAllocate.put(bArr);
        return byteBufferAllocate.array();
    }

    public static byte[] intToByteArray16(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255)};
    }

    private byte[] appendByteArray(byte[] bArr, byte[] bArr2) {
        if (bArr2 == null) {
            return bArr;
        }
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }
}
