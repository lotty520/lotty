package com.github.hook;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.github.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ContentResolverHooker {

    private static Class ctxImpl;
    private static Class RESOLVER = ContentResolver.class;
    private static Class contentRootClass;

    private static Field cRField;

    public static void init(Context impl) {
        if (impl != null) {
            try {
                ctxImpl = Class.forName("android.app.ContextImpl");
                contentRootClass = Class.forName("android.content.ContentInterface");
                cRField = ctxImpl.getDeclaredField("mContentResolver");
                cRField.setAccessible(true);
                hookAboveQ(impl);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }


    }

    public static void hookAboveQ(Context impl) {
        try {
            Reflection.exempt("Landroid/content/ContentResolver;");
            Field wrapperField = RESOLVER.getDeclaredField("mWrapped");
            ContentResolver realAppContentResolver = (ContentResolver) cRField.get(impl);
            wrapperField.setAccessible(true);
            Object o = wrapperField.get(realAppContentResolver);
            if (o != null) {
                wrapperField.set(realAppContentResolver, Proxy.newProxyInstance(RESOLVER.getClassLoader(), new Class[]{contentRootClass}, new ContentResolverInvocationHandler(o)));
            } else {
                // TODO: 2019-12-12 需要做深拷贝,不然会产生循环调用
                wrapperField.set(realAppContentResolver, Proxy.newProxyInstance(RESOLVER.getClassLoader(), new Class[]{contentRootClass}, new ContentResolverInvocationHandler(null)));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void hook(Context impl) {
        hookAboveQ(impl);
    }

    static class ContentResolverInvocationHandler implements InvocationHandler {

        private Object real;

        public ContentResolverInvocationHandler(Object real) {
            this.real = real;
        }

        public ContentResolverInvocationHandler() {
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e("wh", "--->" + method.getName());
            Object invoke = method.invoke(real, args);
            if (invoke == null) {
                Log.e("wh", "invoke is null.");
            }
            return invoke;
        }
    }
}
