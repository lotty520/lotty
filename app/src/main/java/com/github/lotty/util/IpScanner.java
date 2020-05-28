package com.github.lotty.util;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author kalshen
 * @date 2017/7/5 0005
 * ip 扫描类
 */

public class IpScanner {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private OnScanListener listener;

    /**
     * 获取局域网中的 存在的ip地址及对应的mac
     */
    public void startScan() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramPacket dp = new DatagramPacket(new byte[0], 0, 0);
                try {
                    DatagramSocket socket = new DatagramSocket();
                    dp.setAddress(InetAddress.getByName("192.186.4.1"));
                    socket.send(dp);
                    socket.close();
                    execCatForArp();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 执行 cat命令 查找android 设备arp表
     * arp表 包含ip地址和对应的mac地址
     */
    private void execCatForArp() {
        try {
            Process exec = Runtime.getRuntime().exec("cat proc/net/arp");
            InputStream is = exec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("00:00:00:00:00:00") && !line.contains("IP")) {
                    String[] split = line.split("\\s+");
                    if (split.length > 3) {
                        final String mac = split[3];
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (listener != null) {
                                    listener.onScan(mac);
                                }
                            }
                        });
                        return;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setOnScanListener(OnScanListener listener) {
        this.listener = listener;
    }

    public interface OnScanListener {
        /**
         * 扫描回调
         *
         * @param mac
         */
        void onScan(String mac);
    }

}
