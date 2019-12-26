package com.github.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

public class Permission {

  public static boolean hasPms(Context context, String permission) {
    return context.checkCallingOrSelfPermission(permission)
        == PackageManager.PERMISSION_GRANTED;
  }
}
