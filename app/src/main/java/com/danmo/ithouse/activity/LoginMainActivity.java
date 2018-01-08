package com.danmo.ithouse.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.event.login.LoginEvent;
import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by user on 2017/12/26.
 */

public class LoginMainActivity extends BaseActivity implements View.OnClickListener {

    private TextInputEditText etLoginName, etLoginPasswd;
    private TextView tvForgetPasswd, tvLoginRegister;
    private ImageView ivLoginWeixin, ivLoginQQ, ivLoginWeibo, ivLoginTaobao;
    private Button btnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        super.initViews();
        etLoginName = findViewById(R.id.et_login_name);
        etLoginPasswd = findViewById(R.id.et_login_password);
        tvForgetPasswd = findViewById(R.id.tv_forget_password);
        tvLoginRegister = findViewById(R.id.tv_login_register);
        ivLoginQQ = findViewById(R.id.iv_login_qq);
        ivLoginTaobao = findViewById(R.id.iv_login_taobao);
        ivLoginWeibo = findViewById(R.id.iv_login_weibo);
        ivLoginWeixin = findViewById(R.id.iv_login_weixin);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);
        tvForgetPasswd.setOnClickListener(this);
        tvLoginRegister.setOnClickListener(this);
        ivLoginQQ.setOnClickListener(this);
        ivLoginTaobao.setOnClickListener(this);
        ivLoginWeibo.setOnClickListener(this);
        ivLoginWeixin.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(LoginEvent event) {
        if (event.isOk()) {
            CommonApi.getSingleInstance().getMe();//EventBus回调到需要用户信息的页面，比如：UserFragment
            toastShort("登录成功");
            setResult(Activity.RESULT_OK);
            finish();
        } else {
            String msg = "请重试";
            switch (event.getCode()) {
                case -1:
                    msg = "请检查网络连接";
                    break;
                case 400:
                case 401:
                    msg = "请检查用户名和密码是否正确";
                    break;
            }
            toastShort("登录失败：" + msg);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            String name = etLoginName.getText().toString();
            String passwd = etLoginPasswd.getText().toString();
            if (TextUtils.isEmpty(name)) {
                toastShort("请输入用户名");
                return;
            }
            if (TextUtils.isEmpty(passwd)) {
                toastShort("请输入密码");
                return;
            }
            CommonApi.getSingleInstance().login(name, passwd);
        }
    }
}
