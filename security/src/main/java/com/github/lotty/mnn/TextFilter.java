package com.github.lotty.mnn;

import android.content.Context;
import android.util.Log;

import java.util.Set;

/**
 * @author lotty
 */
public class TextFilter {

    private volatile static boolean init = false;

    public static void init(Context ctx, String partner) {
        Context globalContext = ctx.getApplicationContext();
        TextBloomFilter.build(globalContext);
        XdConfig config = new XdConfig(partner, globalContext);
        config.update();
        init = true;
    }

    public static boolean check(String target) {
        boolean print = false;
        long start = System.currentTimeMillis();
        if (!init) {
            throw new IllegalStateException("please call init first!");
        }
        boolean hit = false;
        Set<String> generate = GramsGenerator.generate(target,start);
        for (String next : generate) {
            if (TextBloomFilter.match(next)) {
                if (!print) {
                    Log.e("wh", "命中耗时: " + (System.currentTimeMillis() - start));
                    print = true;
                }
                Log.e("wh", next);
                hit = true;
            }
        }
        return hit;
    }
}
