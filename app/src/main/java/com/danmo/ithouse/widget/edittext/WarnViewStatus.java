package com.danmo.ithouse.widget.edittext;

/**
 * Created by caihan on 2016/9/16.
 * 警告语显示监听
 */
public interface WarnViewStatus {
    /**
     * 展示警告语
     *
     * @param msgs
     */
    void show(String... msgs);

    /**
     * 隐藏警告语
     */
    void hide();
}
