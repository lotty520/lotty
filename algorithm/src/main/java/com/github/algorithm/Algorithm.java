package com.github.algorithm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * @author lotty
 */
public class Algorithm {
  private final static String URL1 = "http://10.59.49.227:8088/downloadByFileName?fileName=12300000000-2";
  private final static String URL2 = "http://10.59.49.227:8088/downloadByFileName?fileName=12300000000-3";
  private final static String URL3 = "http://10.59.49.227:8088/downloadByFileName?fileName=12300000000-4";
  private final static String URL4 = "http://10.59.49.227:8088/downloadByFileName?fileName=12300000000-7";

  public static void main(String[] args) {
  cal(URL1);
  cal(URL2);
  cal(URL3);
  cal(URL4);
  }

  private static void cal(String url){
    OkHttpClient httpClient = new OkHttpClient();
    Request re = new Request.Builder()
        .url(url)
        .get()
        .build();
    try {
      ResponseBody body = httpClient.newCall(re).execute().body();
      if (body != null) {
        String string = body.string();
        String[] split = string.split("\n");
        JSONArray data = new JSONArray();
        for (int i = 0; i < split.length; i++) {
          JSONObject json = JSON.parseObject(split[i]);
          if (json.containsKey("K210")) {
            JSONArray ar = json.getJSONArray("K210");
            if (ar != null && ar.size() > 0) {
              data.addAll(ar);
            }
          }
        }

        if (data == null || data.size() == 0) {
          System.out.println("数据异常");
          return;
        }
        System.out.println("数据总量：" + data.size());
        long cur = data.getJSONObject(0).getLong("acceler_time");
        for (int i = 1; i < data.size(); i++) {
          long time = data.getJSONObject(i).getLong("acceler_time");
          if (time - cur > 30) {
            System.out.println("before: " + cur + ",after: " + time + ",delta: " + (time - cur));
          }
          cur = time;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
