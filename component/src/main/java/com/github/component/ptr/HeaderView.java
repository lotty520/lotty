package com.github.component.ptr;

import android.view.View;

/**
 * @author lotty
 */
public interface HeaderView {

    /**
     * 滑动距离达到最小刷新距离,即将离开，提示松手刷新
     */
    void onReadyToFinish();

    void onStart();

    /**
     * 滑动距离达到最小刷新距离，即将进入，提示松手刷新
     */
    void onReadyToStart();

    View getView();

}
