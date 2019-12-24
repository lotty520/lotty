package com.github.component.ptr;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DefaultBodyView implements BodyView {

  private RecyclerView mView;

  public DefaultBodyView(Context ctx) {
    mView = new RecyclerView(ctx);
    mView.setLayoutManager(new LinearLayoutManager(ctx, RecyclerView.VERTICAL, false));
  }

  @Override public View getView() {
    return mView;
  }
}
