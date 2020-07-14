package com.github.lotty.mnn.common;

import java.io.Closeable;
import java.io.IOException;

public class IoUtil {

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
