package com.danmo.ithouse.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.base.cache.DataCache;
import com.danmo.commonapi.bean.user.UserDetail;
import com.danmo.commonapi.event.user.GetMeEvent;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.LoginUtilActivity;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.util.GlideRoundTransform;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

/**
 * 我
 */

public class UserFragment extends BaseFragment implements View.OnClickListener {
    private Button btnLogin, btnRegister;
    private DataCache mCache;
    private View viewHead, viewHeadLogined;

    private ImageView ivAvatar;
    private TextView tvName;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_userinfo;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

        viewHead = root.findViewById(R.id.userinfo_head);
        viewHeadLogined = root.findViewById(R.id.userinfo_head_logined);
        ivAvatar = viewHeadLogined.findViewById(R.id.iv_userinfo_pic);
        tvName = viewHeadLogined.findViewById(R.id.tv_userinfo_name);

        btnLogin = root.findViewById(R.id.btn_login);
//        btnRegister = holder.get(R.id.btn_register);
        btnLogin.setOnClickListener(this);
        mCache = new DataCache(mContext);
        handlerHeaderView(CommonApi.getSingleInstance().isLogin());
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            LoginUtilActivity.checkLogin(mContext, new LoginUtilActivity.LoginCallback() {
                @Override
                public void onLogined(Intent intent) {

                    if (intent != null) {
                        Bundle bundle = intent.getExtras();
                        Map<String, String> serializableMap = (Map) bundle
                                .get("map");
                        viewHead.setVisibility(View.GONE);
                        viewHeadLogined.setVisibility(View.VISIBLE);

                        tvName.setText(serializableMap.get("name"));
                        Glide.with(mContext)
                                .load(serializableMap.get("iconurl"))
                                .transform(new GlideRoundTransform(mContext, 25))
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .into(ivAvatar);
                    }
                }

                @Override
                public void onLoginFailed() {
                }
            });
        }
    }

    // 如果收到此状态说明用户已经登录成功了
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMe(GetMeEvent event) {
        if (event.isOk()) {
            UserDetail me = event.getBean();
            mCache.saveMe(me);
            handlerHeaderView(CommonApi.getSingleInstance().isLogin());
        }
    }

    private void handlerHeaderView(Boolean isLogined) {
        if (isLogined) {
            viewHead.setVisibility(View.GONE);
            viewHeadLogined.setVisibility(View.VISIBLE);

            UserDetail me = mCache.getMe();
            if (me == null) {
//                CommonApi.getSingleInstance().getMe();   // 重新加载
                return;
            }
            tvName.setText(me.getLogin());
            Glide.with(mContext)
                    .load(me.getAvatar_url())
                    .transform(new GlideRoundTransform(mContext, 25))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(ivAvatar);
        } else {
            viewHead.setVisibility(View.VISIBLE);
            viewHeadLogined.setVisibility(View.GONE);
            mCache.removeMe();
        }
    }
}
