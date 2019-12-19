package com.github.component.ptr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.component.R;

/**
 * @author lotty
 */
public class DefaultHeaderView implements HeaderView {

    private View realView;

    private ImageView content;
    private TextView hint;

    private Context ctx;

    public DefaultHeaderView(Context ctx) {
        this.ctx = ctx;
        realView = LayoutInflater.from(ctx).inflate(R.layout.default_header_layout, null);
        content = realView.findViewById(R.id.ptr_header_content);
        hint = realView.findViewById(R.id.ptr_header_hint);
    }

    @Override
    public void onReadyToFinish() {
        Log.e("wh","onReadyToFinish");
        hint.setText("下拉刷新");
        content.setImageResource(R.drawable.ptr_header_down);
    }

    @Override
    public void onRefreshStart() {
        Log.e("wh","onRefreshStart");
        hint.setText("刷新中");
        content.setImageResource(R.drawable.ptr_header_loading);
    }

    @Override
    public void onRefreshFinish() {
        // TODO: 2019-12-18 end Animation
    }

    @Override
    public void onReadyToStart() {
        Log.e("wh","onReadyToStart");
        hint.setText("松开刷新");
        content.setImageResource(R.drawable.ptr_header_up);
    }

    @Override
    public View getView() {
        return realView;
    }
}
