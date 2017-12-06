package com.danmo.commonutil.behavior;


public interface AnimateHelper {
    public static int STATE_SHOW = 1;
    public static int STATE_HIDE = 0;

    void show();

    void hide();

    void setStartY(float y);

    void setMode(int modeBottom);

    int getState();
}
