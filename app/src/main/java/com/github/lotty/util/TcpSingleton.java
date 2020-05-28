package com.github.lotty.util;

import android.util.Log;

import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * @author lotty
 */
public class TcpSingleton {

    private final static String IP = "192.186.4.1";
    private final static int PORT = 8080;

    public static void connect() {
        //连接参数设置(IP,端口号),这也是一个连接的唯一标识,不同连接,该参数中的两个值至少有其一不一样
        ConnectionInfo info = new ConnectionInfo(IP, PORT);
        //调用OkSocket,开启这次连接的通道,调用通道的连接方法进行连接.
        OkSocket.open(info).connect();
    }

    public static void request(String cmd, final DataCallback callback) {
        //连接参数设置(IP,端口号),这也是一个连接的唯一标识,不同连接,该参数中的两个值至少有其一不一样
        ConnectionInfo info = new ConnectionInfo(IP, PORT);
        //调用OkSocket,开启这次连接的通道,拿到通道Manager
        IConnectionManager manager = OkSocket.open(info);
        manager.registerReceiver(new SocketActionAdapter() {
            @Override
            public void onSocketIOThreadStart(String action) {
                super.onSocketIOThreadStart(action);
                Log.e("wh", "start ::action: " + action);
            }

            @Override
            public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
                super.onSocketConnectionSuccess(info, action);
                Log.e("wh", "collect ::action: " + action + ", info: " + info.getIp() + ", " + info.getPort());
            }

            @Override
            public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
                super.onSocketReadResponse(info, action, data);
                byte[] bodyBytes = data.getBodyBytes();
                byte[] headBytes = data.getHeadBytes();
                Log.e("wh", "read body---> " + action + ", " + SysUtil.bytes2Hex(bodyBytes));
                Log.e("wh", "read head---> " + action + ", " + SysUtil.bytes2Hex(headBytes));
                if (callback != null) {
                    callback.onGet(SysUtil.bytes2Hex(bodyBytes));
                }
            }

            @Override
            public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
                super.onSocketWriteResponse(info, action, data);
                Log.e("wh", "write body---> " + action + ", " + SysUtil.bytes2Hex(data.parse()));
            }
        });
        //调用通道进行连接
        manager.connect();
        manager.send(new SendInfo(cmd));
    }

    public static void connectWithCallback(SocketActionAdapter callback) {
        //连接参数设置(IP,端口号),这也是一个连接的唯一标识,不同连接,该参数中的两个值至少有其一不一样
        ConnectionInfo info = new ConnectionInfo(IP, PORT);
        //调用OkSocket,开启这次连接的通道,拿到通道Manager
        IConnectionManager manager = OkSocket.open(info);
        //注册Socket行为监听器,SocketActionAdapter是回调的Simple类,其他回调方法请参阅类文档
        manager.registerReceiver(callback);
        //调用通道进行连接
        manager.connect();
    }

    public interface DataCallback {
        void onGet(String data);
    }

    public static class SendInfo implements ISendable {
        String cmd;

        public SendInfo(String cmd) {
            this.cmd = cmd;
        }

        @Override
        public byte[] parse() {
            byte[] body = cmd.getBytes(Charset.defaultCharset());
            ByteBuffer bb = ByteBuffer.allocate(4+ body.length);
            bb.order(ByteOrder.BIG_ENDIAN);
            bb.putInt(body.length);
            bb.put(body);
            return bb.array();
        }
    }

}
