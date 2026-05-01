package com.tencent.cos.xml.transfer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.tencent.cos.xml.model.CosXmlResult;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
final class TaskStateMonitor implements Runnable {
    public static final int MESSAGE_RELEASE_LOOP = 3;
    public static final int MESSAGE_TASK_CONSTRAINT = 5;
    public static final int MESSAGE_TASK_INIT = 4;
    public static final int MESSAGE_TASK_MANUAL = 2;
    public static final int MESSAGE_TASK_RESULT = 1;
    private static final String TAG = "TaskStateMonitor";
    private static TaskStateMonitor monitor;
    private static Handler taskHandler;
    private Looper looper;
    private volatile boolean isRunning = false;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private TaskStateMonitor() {
    }

    public static TaskStateMonitor getInstance() {
        synchronized (TaskStateMonitor.class) {
            if (monitor == null) {
                monitor = new TaskStateMonitor();
            }
            monitor.monitor();
        }
        return monitor;
    }

    private void monitor() {
        if (this.isRunning) {
            return;
        }
        this.executorService.submit(this);
        this.isRunning = true;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this) {
            Looper looperMyLooper = Looper.myLooper();
            this.looper = looperMyLooper;
            if (looperMyLooper != null) {
                notifyAll();
            }
        }
        if (this.looper == null) {
            Looper.prepare();
            synchronized (this) {
                this.looper = Looper.myLooper();
                notifyAll();
            }
        }
        try {
            setMessageQueue();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException | InvocationTargetException e) {
            e.printStackTrace();
        }
        taskHandler = new Handler(getLooper()) { // from class: com.tencent.cos.xml.transfer.TaskStateMonitor.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i != 1) {
                    if (i == 2) {
                        StructMsg structMsg = (StructMsg) message.obj;
                        TaskStateMonitor.this.stateTransform(structMsg.cosxmlTask, structMsg.transferState, structMsg.exception, null, false);
                        return;
                    } else if (i == 3) {
                        TaskStateMonitor.this.releaseLooper();
                        return;
                    } else if (i == 4) {
                        StructMsg structMsg2 = (StructMsg) message.obj;
                        TaskStateMonitor.this.stateTransform(structMsg2.cosxmlTask, structMsg2.transferState, structMsg2.exception, structMsg2.result, true);
                        return;
                    } else if (i != 5) {
                        return;
                    }
                }
                StructMsg structMsg3 = (StructMsg) message.obj;
                TaskStateMonitor.this.stateTransform(structMsg3.cosxmlTask, structMsg3.transferState, structMsg3.exception, structMsg3.result, false);
            }
        };
        Looper.loop();
    }

    private void setMessageQueue() throws IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException, InvocationTargetException {
        Field declaredField = Looper.class.getDeclaredField("mQueue");
        declaredField.setAccessible(true);
        for (Constructor<?> constructor : Class.forName("android.os.MessageQueue").getDeclaredConstructors()) {
            constructor.setAccessible(true);
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            int length = parameterTypes.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (parameterTypes[i].getName().equalsIgnoreCase(TypedValues.Custom.S_BOOLEAN)) {
                    declaredField.set(this.looper, constructor.newInstance(true));
                    break;
                }
                i++;
            }
        }
    }

    public Looper getLooper() {
        if (!Thread.currentThread().isAlive()) {
            return null;
        }
        synchronized (this) {
            while (Thread.currentThread().isAlive() && this.looper == null) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                }
            }
        }
        return this.looper;
    }

    public void quitSafely() {
        taskHandler.removeCallbacksAndMessages(null);
        Looper looper = getLooper();
        if (looper != null) {
            looper.quitSafely();
        }
        this.isRunning = false;
    }

    protected void stateTransform(COSXMLTask cOSXMLTask, TransferState transferState, Exception exc, CosXmlResult cosXmlResult, boolean z) {
        cOSXMLTask.updateState(transferState, exc, cosXmlResult, z);
    }

    protected void sendStateMessage(COSXMLTask cOSXMLTask, TransferState transferState, Exception exc, CosXmlResult cosXmlResult, int i) {
        Handler handler = taskHandler;
        if (handler == null) {
            return;
        }
        Message messageObtainMessage = handler.obtainMessage();
        messageObtainMessage.what = i;
        StructMsg structMsg = new StructMsg();
        structMsg.cosxmlTask = cOSXMLTask;
        structMsg.transferState = transferState;
        structMsg.exception = exc;
        structMsg.result = cosXmlResult;
        messageObtainMessage.obj = structMsg;
        taskHandler.sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseLooper() {
        quitSafely();
    }

    private class StructMsg {
        COSXMLTask cosxmlTask;
        Exception exception;
        CosXmlResult result;
        volatile TransferState transferState;

        private StructMsg() {
        }
    }
}
