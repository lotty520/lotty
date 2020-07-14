package com.github.lotty.mnn;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.github.lotty.mnn.common.HttpClient;
import com.github.lotty.mnn.common.SdkThreadPool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lotty
 */
public class XdConfig {

    private String partner;
    private String localVersion;
    private Context context;

    public XdConfig(String partner, Context ctx) {
        SharedPreferences preferences = ctx.getSharedPreferences("xd_text_filter_sp", Context.MODE_PRIVATE);
        localVersion = preferences.getString("xd_filter_version", "");
        this.partner = partner;
        this.context = ctx;
    }

    public void update() {
        fetchFilter();
    }

    private void fetchFilter() {
        SdkThreadPool.execute(new FilterTask(partner, localVersion, context));
    }

    private static class FilterTask implements Runnable {

        String localVersion;
        String partner;
        Context ctx;

        public FilterTask(String localVersion, String partner, Context ctx) {
            this.localVersion = localVersion;
            this.partner = partner;
            this.ctx = ctx;
        }

        @Override
        public void run() {
            String response = HttpClient.requestBloomFilter("url", partner, localVersion);
            try {
                JSONObject json = new JSONObject(response);
                boolean success = json.optBoolean("success");
                if (success) {
                    int code = json.optInt("code");
                    if (200 == code) {
                        JSONObject data = json.optJSONObject("data");
                        if (data != null) {
                            String version = data.optString("version");
                            String filter = data.optString("filter");
                            if (!TextUtils.isEmpty(version) && !TextUtils.isEmpty(filter)) {
                                saveFilter(version, filter);
                                TextBloomFilter.update(filter);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void saveFilter(String version, String filter) {
            SharedPreferences preferences = ctx.getSharedPreferences("xd_text_filter_sp", Context.MODE_PRIVATE);
            preferences.edit().putString("xd_filter_version", version).apply();
            preferences.edit().putString("xd_filter_content", filter).apply();
        }
    }
}
