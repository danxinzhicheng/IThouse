package com.danmo.ithouse.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.danmo.ithouse.activity.LoginMainActivity;
import com.danmo.ithouse.base.BaseFragment;
import com.danmo.ithouse.base.ViewHolder;
import com.danmo.ithouse.util.GlideRoundTransform;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

/**
 * 我
 */

public class UserFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "UserFragment";
    private Button btnLogin;
    private DataCache mCache;
    private View viewHead, viewHeadLogined;
    private ImageView ivLoginWeixin, ivLoginQQ, ivLoginWeibo, ivLoginTaobao;
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

        ivLoginQQ = root.findViewById(R.id.iv_login_qq);
        ivLoginTaobao = root.findViewById(R.id.iv_login_taobao);
        ivLoginWeibo = root.findViewById(R.id.iv_login_weibo);
        ivLoginWeixin = root.findViewById(R.id.iv_login_weixin);

        ivLoginQQ.setOnClickListener(this);
        ivLoginTaobao.setOnClickListener(this);
        ivLoginWeibo.setOnClickListener(this);
        ivLoginWeixin.setOnClickListener(this);

        btnLogin = root.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        mCache = new DataCache(mContext);
        handlerHeaderView(CommonApi.getSingleInstance().isLogin());
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            LoginMainActivity.startActivityForResult(UserFragment.this);
        } else if (v == ivLoginWeixin) {
            authorizationLogin(SHARE_MEDIA.WEIXIN);

        } else if (v == ivLoginQQ) {
            authorizationLogin(SHARE_MEDIA.QQ);

        } else if (v == ivLoginWeibo) {
            authorizationLogin(SHARE_MEDIA.SINA);
        } else if (v == ivLoginTaobao) {

        }
    }

    //一键授权
    private void authorizationLogin(SHARE_MEDIA share_media) {
        UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.i(TAG, "onComplete " + "授权完成");

                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");

                //拿到信息去请求登录接口。。。

                viewHead.setVisibility(View.GONE);
                viewHeadLogined.setVisibility(View.VISIBLE);

                tvName.setText(name);
                Glide.with(mContext)
                        .load(iconurl)
                        .transform(new GlideRoundTransform(mContext, 25))
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(ivAvatar);
                //保存登录信息
                //设置登陆状态
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle bundle = data.getExtras();
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
            //保存登录信息
            //设置登陆状态

        }


    }

    // 如果收到此状态说明用户已经登录成功了
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMe(GetMeEvent event) {
        if (event.isOk()) {
            UserDetail me = event.getBean();
            mCache.saveMe(me);//保存登陆信息
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
