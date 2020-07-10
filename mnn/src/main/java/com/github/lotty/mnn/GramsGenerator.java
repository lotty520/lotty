package com.github.lotty.mnn;

import android.text.TextUtils;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * @author lotty
 */
public class GramsGenerator {

    private final static int MIN_NG = 1;

    public static Set<String> generate(String plaintext) {
        Set<String> grams = new LinkedHashSet<>();

        if (!TextUtils.isEmpty(plaintext)) {
            int length = plaintext.length();
            if (length > MIN_NG) {
                for (int i = MIN_NG; i < length; i++) {
                    Set<String> iGrams = generate(i, plaintext);
                    grams.addAll(iGrams);
                }
            }
        }
        return grams;
    }

    public static Set<String> generate(int n, String plaintext) {
        Set<String> grams = new LinkedHashSet<>();
        //"\\s+"是能匹配任何空白字符，包括空格、制表符、换页符等等, 等价于[\f\n\r\t\v]
        String[] sentences = plaintext.split("\\s+");
        for (int i = 0; i < sentences.length; i++) {
            String sentence = sentences[i].trim();
            for (int j = 0; j < sentence.length() - n + 1; j++) {
                grams.add(sentence.substring(j, j + n));
            }
        }
        return grams;
    }

}
