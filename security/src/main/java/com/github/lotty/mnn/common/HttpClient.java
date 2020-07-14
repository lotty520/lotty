package com.github.lotty.mnn.common;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.google.common.base.Charsets;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


public class HttpClient {


    public static String requestBloomFilter(String url, String partnerCode, String localVersion) {
        Map<String, Object> params = new ArrayMap<>(3);
        params.put("device", 1);
        params.put("partnerCode", partnerCode);
        params.put("version", localVersion);
        return post(url, params);
    }

    private static String post(String url, Map<String, Object> params) {
        StringBuilder postBody = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String value = entry.getValue().toString();
            if (!TextUtils.isEmpty(value)) {
                try {
                    value = URLEncoder.encode(value, Charsets.UTF_8.name());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            postBody.append(entry.getKey())
                    .append("=")
                    .append(value)
                    .append("&");
        }
        if (postBody.length() > 0) {
            postBody.deleteCharAt(postBody.length() - 1);
        }
        try {
            URL path = new URL(url);
            return connect(path, "POST", postBody.toString().getBytes(Charsets.UTF_8.name()));
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String connect(URL url, String method, byte[] body) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        StringBuilder result = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接超时
            conn.setConnectTimeout(5000);
            // 设置读取超时
            conn.setReadTimeout(5000);
            // 提交参数
            conn.setDoOutput(true);
            conn.setRequestMethod(method);
            outputStream = conn.getOutputStream();
            outputStream.write(body);
            outputStream.flush();
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8.name());
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
            }
            conn.disconnect();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(outputStream);
            IoUtil.close(inputStream);
            IoUtil.close(inputStreamReader);
            IoUtil.close(bufferedReader);
        }
        return result.toString();
    }

}
