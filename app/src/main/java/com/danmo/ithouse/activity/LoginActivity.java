package com.danmo.ithouse.activity;

import android.content.Context;
import android.content.Intent;

import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseActivity;

/**
 * Created by user on 2017/12/26.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
