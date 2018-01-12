package com.danmo.ithouse.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.danmo.commonapi.CommonApi;

/**
 * Created by user on 2017/12/26.
 */

public class LoginUtilActivity extends Activity {

    private int REQUEST_CODE_LOGIN = 1;
    private static LoginCallback mCallback;

    public interface LoginCallback {
        void onLogined(Intent data);

        void onLoginFailed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, LoginMainActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }


    public static void checkLogin(Context context, LoginCallback callback) {
        //此处检查当前的登录状态
        boolean login = CommonApi.getSingleInstance().isLogin();
        if (login) {
            callback.onLogined(null);
        } else {
            mCallback = callback;
            Intent intent = new Intent(context, LoginUtilActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        finish();
        mCallback.onLogined(data);

//        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK && mCallback != null) {
//            boolean login = CommonApi.getSingleInstance().isLogin();
//            if (login) {
//                mCallback.onLogined(data);
//            } else {
//                mCallback.onLoginFailed();
//            }
//        }
//        mCallback = null;
    }

}
