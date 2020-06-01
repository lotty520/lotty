package com.github.lotty.util;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author shensky
 * @date 2018/1/15
 */

public class TaskCenter {
    private static final String TAG = "wh";
    private static TaskCenter instance;
    private Socket socket;
    private Thread thread;
    private OnServerConnectedCallbackBlock connectedCallback;
    private OnServerDisconnectedCallbackBlock disconnectedCallback;
    private OnReceiveCallbackBlock receivedCallback;

    private TaskCenter() {
    }

    public static TaskCenter sharedCenter() {
        if (instance == null) {
            synchronized (TaskCenter.class) {
                if (instance == null) {
                    instance = new TaskCenter();
                }
            }
        }
        return instance;
    }

    /**
     * 通过IP地址(域名)和端口进行连接
     *
     * @param ipAddress IP地址(域名)
     * @param port      端口
     */
    public void sendInstructions(final String ipAddress, final int port, final byte[] body) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ipAddress, port);
                    socket.setTcpNoDelay(true);
                    if (socket.isConnected()) {
                        if (connectedCallback != null) {
                            connectedCallback.callback();
                        }
                        ByteBuffer buffer = ByteBuffer.allocate(body.length);
                        buffer.order(ByteOrder.BIG_ENDIAN);
                        buffer.put(body);
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write(buffer.array());
                        outputStream.flush();
                        InputStream inputStream = socket.getInputStream();
                        try {
                            byte[] bt = new byte[1024];
                            int available = inputStream.available();
                            Log.e(TAG, "需要读取数据长度:" + available);
                            if (available <= 0) {
                                return;
                            }
                            int length = inputStream.read(bt);
                            Log.e(TAG, "结束读取数据");
                            if (length > 0) {
                                byte[] bs = new byte[length];
                                System.arraycopy(bt, 0, bs, 0, length);
                                String str = SysUtil.bytes2Hex(bs);
                                Log.e(TAG, "接收成功: " + str);
                                if (receivedCallback != null) {
                                    receivedCallback.callback(str);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e(TAG, "连接失败");
                        if (disconnectedCallback != null) {
                            disconnectedCallback.callback(new IOException("连接失败"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "连接异常");
                    if (disconnectedCallback != null) {
                        disconnectedCallback.callback(e);
                    }
                } finally {
                    disconnect();
                }
            }
        });
        thread.start();
    }

    /**
     * 断开连接
     */
    private void disconnect() {
        if (socket.isConnected()) {
            try {
                socket.close();
                if (socket.isClosed()) {
                    if (disconnectedCallback != null) {
                        disconnectedCallback.callback(new IOException("断开连接"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setConnectedCallback(OnServerConnectedCallbackBlock connectedCallback) {
        this.connectedCallback = connectedCallback;
    }

    public void setDisconnectedCallback(OnServerDisconnectedCallbackBlock disconnectedCallback) {
        this.disconnectedCallback = disconnectedCallback;
    }

    public void setReceivedCallback(OnReceiveCallbackBlock receivedCallback) {
        this.receivedCallback = receivedCallback;
    }

    /**
     * 移除回调
     */
    private void removeCallback() {
        connectedCallback = null;
        disconnectedCallback = null;
        receivedCallback = null;
    }

    /**
     * 回调声明
     */
    public interface OnServerConnectedCallbackBlock {
        void callback();
    }

    public interface OnServerDisconnectedCallbackBlock {
        void callback(IOException e);
    }

    public interface OnReceiveCallbackBlock {
        void callback(String receicedMessage);
    }
}
