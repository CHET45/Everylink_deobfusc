package com.aivox.base.http;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.DataUtil;
import com.aivox.base.util.LogUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;

/* JADX INFO: loaded from: classes.dex */
public class TcpClient {
    private static TcpClient mSocketClient;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private Socket mSocket;
    private SocketThread mSocketThread;
    String TAG_log = "tag";
    private boolean isStop = false;
    Handler uiHandler = new Handler() { // from class: com.aivox.base.http.TcpClient.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == -1) {
                if (TcpClient.this.onDataReceiveListener != null) {
                    TcpClient.this.onDataReceiveListener.onConnectFail();
                    TcpClient.this.disconnect();
                    return;
                }
                return;
            }
            if (i == 1) {
                if (TcpClient.this.onDataReceiveListener != null) {
                    TcpClient.this.onDataReceiveListener.onConnectSuccess();
                }
            } else {
                if (i != 100) {
                    return;
                }
                Bundle data = message.getData();
                byte[] byteArray = data.getByteArray("data");
                int i2 = data.getInt("size");
                int i3 = data.getInt("requestCode");
                if (TcpClient.this.onDataReceiveListener != null) {
                    TcpClient.this.onDataReceiveListener.onDataReceive(byteArray, i2, i3);
                }
            }
        }
    };
    private OnDataReceiveListener onDataReceiveListener = null;
    private int requestCode = -1;

    public interface OnDataReceiveListener {
        void onConnectFail();

        void onConnectSuccess();

        void onDataReceive(byte[] bArr, int i, int i2);
    }

    private TcpClient() {
    }

    public static TcpClient getInstance() {
        if (mSocketClient == null) {
            synchronized (TcpClient.class) {
                mSocketClient = new TcpClient();
            }
        }
        return mSocketClient;
    }

    private class SocketThread extends Thread {

        /* JADX INFO: renamed from: ip */
        private String f181ip;
        private int port;

        public SocketThread(String str, int i) {
            this.f181ip = str;
            this.port = i;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Log.d(TcpClient.this.TAG_log, "SocketThread start ");
            super.run();
            try {
                if (TcpClient.this.mSocket != null) {
                    TcpClient.this.mSocket.close();
                    TcpClient.this.mSocket = null;
                }
                TcpClient.this.mSocket = new Socket(InetAddress.getByName(this.f181ip), this.port);
                if (TcpClient.this.isConnect()) {
                    TcpClient tcpClient = TcpClient.this;
                    tcpClient.mOutputStream = tcpClient.mSocket.getOutputStream();
                    TcpClient tcpClient2 = TcpClient.this;
                    tcpClient2.mInputStream = tcpClient2.mSocket.getInputStream();
                    TcpClient.this.isStop = false;
                    TcpClient.this.uiHandler.sendEmptyMessage(1);
                    Log.d(TcpClient.this.TAG_log, "SocketThread connect over");
                    while (TcpClient.this.isConnect() && !TcpClient.this.isStop && !isInterrupted()) {
                        try {
                            byte[] bArr = new byte[1024];
                            if (TcpClient.this.mInputStream == null) {
                                return;
                            }
                            int i = TcpClient.this.mInputStream.read(bArr);
                            if (i > 0) {
                                LogUtil.m338i("收到tcp数据：" + DataUtil.bytes2Hex(bArr) + ";" + i);
                                Message message = new Message();
                                message.what = 100;
                                Bundle bundle = new Bundle();
                                bundle.putByteArray("data", bArr);
                                bundle.putInt("size", i);
                                bundle.putInt("requestCode", TcpClient.this.requestCode);
                                message.setData(bundle);
                                TcpClient.this.uiHandler.sendMessage(message);
                            }
                            Log.i(TcpClient.this.TAG_log, "SocketThread read listening");
                        } catch (IOException e) {
                            TcpClient.this.uiHandler.sendEmptyMessage(-1);
                            Log.e(TcpClient.this.TAG_log, "SocketThread read io exception = " + e.getMessage());
                            BaseAppUtils.printErrorMsg(e);
                            return;
                        }
                    }
                    return;
                }
                TcpClient.this.uiHandler.sendEmptyMessage(-1);
                Log.e(TcpClient.this.TAG_log, "SocketThread connect fail");
            } catch (IOException e2) {
                TcpClient.this.uiHandler.sendEmptyMessage(-1);
                Log.e(TcpClient.this.TAG_log, "SocketThread connect io exception = " + e2.getMessage());
                BaseAppUtils.printErrorMsg(e2);
            }
        }
    }

    public void connect(String str, int i) {
        SocketThread socketThread = new SocketThread(str, i);
        this.mSocketThread = socketThread;
        socketThread.start();
    }

    public boolean isConnect() {
        Socket socket = this.mSocket;
        if (socket != null) {
            return socket.isConnected();
        }
        return false;
    }

    public void disconnect() {
        this.isStop = true;
        try {
            OutputStream outputStream = this.mOutputStream;
            if (outputStream != null) {
                outputStream.close();
            }
            InputStream inputStream = this.mInputStream;
            if (inputStream != null) {
                inputStream.close();
            }
            Socket socket = this.mSocket;
            if (socket != null) {
                socket.close();
                this.mSocket = null;
            }
        } catch (IOException e) {
            BaseAppUtils.printErrorMsg(e);
        }
        SocketThread socketThread = this.mSocketThread;
        if (socketThread != null) {
            socketThread.interrupt();
        }
    }

    public void sendByteCmd(final byte[] bArr, int i) {
        this.requestCode = i;
        new Thread(new Runnable() { // from class: com.aivox.base.http.TcpClient.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TcpClient.this.mOutputStream != null) {
                        TcpClient.this.mOutputStream.write(bArr);
                        TcpClient.this.mOutputStream.flush();
                    }
                } catch (IOException e) {
                    BaseAppUtils.printErrorMsg(e);
                }
            }
        }).start();
    }

    public void sendStrCmds(String str, int i) {
        sendByteCmd(str.getBytes(), i);
    }

    public void sendChsPrtCmds(String str, int i) {
        try {
            sendByteCmd(str.getBytes("GB2312"), i);
        } catch (UnsupportedEncodingException e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    public void setOnDataReceiveListener(OnDataReceiveListener onDataReceiveListener) {
        this.onDataReceiveListener = onDataReceiveListener;
    }
}
