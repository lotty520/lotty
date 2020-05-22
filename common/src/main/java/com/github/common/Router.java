package com.github.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由工具类
 * <p>
 * Created by Robin on 4/28/17.
 */
public class Router {
  private static final String ACTION_NAVIGATION = "com.github.lotty.action.NAVIGATION";
  private static final int NO_REQ_CODE = -1;

  private Context from;
  private Class<? extends Activity> to;

  private String action;
  private Uri uri;

  private Map<String, String> data = new HashMap<>();
  private Bundle bundle;
  private Bundle options;

  private int reqCode;

  private Router(Builder builder) {
    to = builder.targetActivity;
    from = builder.from;
    action = builder.action != null ? builder.action : ACTION_NAVIGATION;
    uri = builder.uri;
    data = builder.data;
    bundle = builder.bundle;
    options = builder.options;
    reqCode = builder.reqCode == 0 ? NO_REQ_CODE : builder.reqCode;
  }

  /**
   * 创建 Router 构建器
   */
  public static Builder from(Context context) {
    return new Builder(context);
  }

  /**
   * 创建 Router 构建器
   *
   * @param activity 当前页面
   */
  public static Builder from(Activity activity) {
    return new Builder(activity);
  }

  private void go() {
    Intent intent = new Intent();
    if (to != null) {
      intent.setClass(from, to);
    }
    intent.setAction(action);
    if (uri != null) {
      intent.setData(uri);
    }
    if (bundle != null) {
      intent.putExtras(bundle);
    }
    if (data != null && !data.isEmpty()) {
      for (Map.Entry<String, String> entry : data.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        if (!TextUtils.isEmpty(key)) {
          intent.putExtra(key, value);
        }
      }
    }
    if (from instanceof Activity) {
      startByActivity(intent);
    } else {
      startByContext(intent);
    }
  }

  private void startByActivity(Intent intent) {
    Activity from = (Activity) this.from;
    from.startActivityForResult(intent, reqCode, options);
  }

  private void startByContext(Intent intent) {
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    from.startActivity(intent, options);
  }

  public static class Builder {
    private Context from;
    private Class<? extends Activity> targetActivity;

    private String action;
    private Uri uri;

    private Map<String, String> data = new HashMap<>();
    private Bundle bundle;
    private Bundle options;

    private int reqCode;

    public Builder(Context context) {
      if (context instanceof Activity) {
        this.from = context;
      } else {
        this.from = context.getApplicationContext();
      }
    }

    /**
     * 设置目标页面
     *
     * @param toActivityClass 目标页面
     */
    public Builder to(Class<? extends Activity> toActivityClass) {
      this.targetActivity = toActivityClass;
      return this;
    }

    /**
     * 添加 extra 参数
     *
     * @param key 参数的 key
     * @param value 参数的 value
     */
    public Builder with(String key, String value) {
      data.put(key, value);
      return this;
    }

    /**
     * 添加 extra bundle 参数
     *
     * @param bundle bundle 数据
     */
    public Builder with(Bundle bundle) {
      this.bundle = bundle;
      return this;
    }

    /**
     * 添加 extra 数据，Map 中的每一个 entry 作为键值对添加到 intent extra 中
     *
     * @param data Map 数据
     */
    public Builder with(Map<String, String> data) {
      if (data != null && !data.isEmpty()) {
        this.data.putAll(data);
      }
      return this;
    }

    /**
     * 设置 startActivity 的 request code 不能设置为 0
     *
     * @param requestcode startActivity 的 request code
     */
    public Builder reqcode(int requestcode) {
      this.reqCode = requestcode;
      return this;
    }

    /**
     * 设置 intent action
     *
     * @param intentAction intent action
     */
    public Builder action(String intentAction) {
      this.action = intentAction;
      return this;
    }

    /**
     * 设置目标页 uri
     *
     * @param uri 目标页 uri
     */
    public Builder uri(Uri uri) {
      this.uri = uri;
      return this;
    }

    /**
     * 设置 Activity 启动选项
     *
     * @param options Activity 启动选项
     */
    public Builder options(Bundle options) {
      this.options = options;
      return this;
    }

    /**
     * 执行跳转
     */
    public void go() {
      Router router = new Router(this);
      router.go();
    }
  }
}
