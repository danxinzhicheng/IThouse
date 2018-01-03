package com.danmo.ithouse.fragment;

import android.view.View;
import android.widget.Button;

import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.LoginActivity;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;

/**
 * 我
 */

public class UserFragment extends BaseFragment {
    private Button btnLogin, btnRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_userinfo;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

        btnLogin = root.findViewById(R.id.btn_login);
//        btnRegister = holder.get(R.id.btn_register);
//
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.start(mContext);
            }
        });

    }
}
