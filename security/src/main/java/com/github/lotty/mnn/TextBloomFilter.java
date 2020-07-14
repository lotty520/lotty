package com.github.lotty.mnn;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.github.lotty.mnn.common.IoUtil;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author lotty
 */
public class TextBloomFilter {

    private static BloomFilter<String> sBloomFilter;

    public static boolean build(Context ctx) {
        boolean buildSuccess = true;
        if (!buildFromCache(ctx)) {
            buildSuccess = buildFromAssets(ctx);
        }
        return buildSuccess;
    }

    private static boolean buildFromCache(Context ctx) {
        SharedPreferences preferences = ctx.getSharedPreferences("xd_text_filter_sp", Context.MODE_PRIVATE);
        String localFilterContent = preferences.getString("xd_filter_content", "");
        return !TextUtils.isEmpty(localFilterContent) && buildFromString(localFilterContent);
    }

    private static boolean buildFromAssets(Context ctx) {
        String readFromAssets = readFromAssets(ctx);
        return !TextUtils.isEmpty(readFromAssets) && buildFromString(readFromAssets);
    }

    private static boolean buildFromString(String encodeBloom) {
        boolean buildOver = false;
        byte[] bloomBytes = Base64.decode(encodeBloom, Base64.NO_PADDING);
        ByteArrayInputStream is = new ByteArrayInputStream(bloomBytes);
        try {
            synchronized (TextBloomFilter.class) {
                sBloomFilter = BloomFilter.readFrom(is, new PlaintextFunnel());
            }
            buildOver = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(is);
        }
        return buildOver;
    }

    private static String readFromAssets(Context ctx) {
        StringBuilder originBloomData = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = ctx.getAssets().open("words.so");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] chars = new char[1024];
            while (bufferedReader.read(chars) > 0) {
                originBloomData.append(String.valueOf(chars));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(inputStream);
            IoUtil.close(bufferedReader);
        }
        return originBloomData.toString();
    }

    public static void update(String encodeFilter) {
        byte[] bloomBytes = Base64.decode(encodeFilter, Base64.NO_PADDING);
        ByteArrayInputStream is = new ByteArrayInputStream(bloomBytes);
        try {
            synchronized (TextBloomFilter.class) {
                BloomFilter<String> filter = BloomFilter.readFrom(is, new PlaintextFunnel());
                sBloomFilter = filter;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(is);
        }
    }


    public static boolean match(String target) {
        if (sBloomFilter != null) {
            return sBloomFilter.mightContain(target);
        }
        return false;
    }

    private static class PlaintextFunnel implements Funnel<String> {
        private static final long serialVersionUID = 1L;

        @Override
        public void funnel(String from, PrimitiveSink into) {
            into.putString(from, Charsets.UTF_8);
        }
    }
}
