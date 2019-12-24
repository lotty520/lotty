package com.github.hook;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class WifiManagerHooker {

  private static Class iWifiManager;
  private static Field serviceField;

  public static void init(Context context) {
    try {
      iWifiManager = Class.forName("android.net.wifi.IWifiManager");
      serviceField = WifiManager.class.getDeclaredField("mService");
      serviceField.setAccessible(true);
      hook(context);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  public static void hook(Context context) {
    try {

      // real wifi
      WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

      Object realIwm = serviceField.get(wifi);
      serviceField.set(wifi,
          Proxy.newProxyInstance(iWifiManager.getClassLoader(), new Class[] { iWifiManager },
              new IWMInvocationHandler(realIwm)));
      Log.e("wh", "wifiManager hook success");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static class IWMInvocationHandler implements InvocationHandler {

    private Object real;

    public IWMInvocationHandler(Object real) {
      this.real = real;
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Log.e("wh", "method invoke " + method.getName());
      // TODO: 2019-12-23 real logic
      return method.invoke(real, args);
    }
  }
}
