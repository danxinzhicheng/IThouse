package com.danmo.commonutil.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;


public class ByeBurgerFloatButtonBehavior extends ByeBurgerBehavior {

    public ByeBurgerFloatButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (canInit) {
            mAnimateHelper = ScaleAnimateHelper.get(child);
            canInit = false;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }


    @Override
    protected void onNestPreScrollInit(View child) {

    }
}
