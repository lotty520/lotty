package com.github.lotty.mnn;

import android.content.Context;
import android.util.Base64;

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
public class PlaintextBloomFilter {

    private static BloomFilter<String> sBloomFilter;

    public static void build(Context ctx) {
        String readFromAssets = readFromAssets(ctx);
        byte[] bloomBytes = Base64.decode(readFromAssets, Base64.NO_PADDING);
        ByteArrayInputStream is = new ByteArrayInputStream(bloomBytes);
        try {
            sBloomFilter = BloomFilter.readFrom(is, new PlaintextFunnel());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String readFromAssets(Context ctx) {
        StringBuilder originBloomData = new StringBuilder();
        try {
            InputStream inputStream = ctx.getAssets().open("keys.dat");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            char[] chars = new char[1024];
            while (br.read(chars) > 0) {
                originBloomData.append(String.valueOf(chars));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return originBloomData.toString();
    }

    public static boolean match(String target) {
        return sBloomFilter.mightContain(target);
    }

    private static class PlaintextFunnel implements Funnel<String> {
        private static final long serialVersionUID = 1L;

        @Override
        public void funnel(String from, PrimitiveSink into) {
            into.putString(from, Charsets.UTF_8);
        }
    }
}
