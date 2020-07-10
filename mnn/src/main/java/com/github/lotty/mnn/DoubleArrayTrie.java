package com.github.lotty.mnn;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.TreeMap;

public class DoubleArrayTrie {
    private static AhoCorasickDoubleArrayTrie<String> mTable;

    public static void buildTrie(Context ctx) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        try {
            InputStream inputStream = ctx.getAssets().open("keys.dat");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (!TextUtils.isEmpty(line = br.readLine())) {
                Log.e("wh",line);
                map.put(line, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mTable = new AhoCorasickDoubleArrayTrie<String>();
        mTable.build(map);
    }

    public static void check(String target) {
        List<AhoCorasickDoubleArrayTrie.Hit<String>> wordList = mTable.parseText(target);
        for (int i = 0; i < wordList.size(); i++) {
            Log.e("wh", "hit --> " + wordList.get(i).value);
        }
    }

}
