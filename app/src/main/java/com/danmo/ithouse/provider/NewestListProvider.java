package com.danmo.ithouse.provider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.bean.newest.NewestItem;
import com.danmo.commonutil.DensityUtil;
import com.danmo.commonutil.TimeUtil;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.commonutil.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.NewsDetailActivity;
import com.danmo.ithouse.base.BaseApplication;
import com.danmo.ithouse.realm.NewsHistoryBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 资讯列表数据绑定
 */

public class NewestListProvider extends BaseViewProvider<NewestItem> {
    private Map<String, Integer> mMapClicked = new HashMap<>();

    public NewestListProvider(@NonNull Context context) {
        super(context, R.layout.item_fragment_newest);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, final NewestItem bean) {
        final int position = holder.getLayoutPosition();
        holder.setText(R.id.item_title, bean.title);
        holder.setText(R.id.item_commentcount, bean.commentcount + mContext.getString(R.string.comment_text));

        //todo 时间判断待fix
        try {
            if (TimeUtil.IsToday(bean.postdate)) {
                String[] ss = bean.postdate.split("\\s+");
                String str = ss[1];
                holder.setText(R.id.item_postdate, str);
            } else if (TimeUtil.IsYesterday(bean.postdate)) {
                String[] ss = bean.postdate.split("\\s+");
                String str = ss[1];
                holder.setText(R.id.item_postdate, mContext.getString(R.string.yesterday) + str);
            } else {
                String[] ss = bean.postdate.split("\\s+");
                String str = ss[1];
                holder.setText(R.id.item_postdate, str);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 加载头像
        ImageView imageView = holder.get(R.id.item_image);
        String url = bean.image;
        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);

        final TextView title = holder.get(R.id.item_title);

        final RealmResults realmResults = BaseApplication.sRealm.where(NewsHistoryBean.class).findAll();
        if (mMapClicked.containsKey(bean.newsid)) {
            title.setTextColor(mContext.getResources().getColor(R.color.diy_gray2));
        } else {
            title.setTextColor(mContext.getResources().getColor(R.color.diy_black));
        }

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.start(mContext, NewsDetailActivity.TYPE_LIST, bean.newsid);
                title.setTextColor(mContext.getResources().getColor(R.color.diy_gray2));
                mMapClicked.put(bean.newsid, position);

                if (realmResults != null && realmResults.size() >= 2) {
                    BaseApplication.sRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realmResults.deleteFirstFromRealm();
                        }
                    });
                }

                final NewsHistoryBean newsHistoryBean = new NewsHistoryBean();
                newsHistoryBean.setImage(bean.image).setNewsid(bean.newsid).setTitle(bean.title).setUrl(bean.url);
                //存入本地数据库
                BaseApplication.sRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        BaseApplication.sRealm.copyToRealmOrUpdate(newsHistoryBean);
                    }
                });
            }
        }, R.id.item_container);

        holder.get(R.id.item_container).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "分享被长按的文章", Toast.LENGTH_SHORT).show();
                showDialog(bean.title);
                return true;
            }
        });

    }

    Dialog dialog = null;

    private void showDialog(String shareTxt) {
        dialog = new Dialog(mContext);
        dialog.setCancelable(true);
        View dialogView = View.inflate(mContext, R.layout.dialog_share_layout, null);

        dialog.setContentView(dialogView);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = DensityUtil.dip2px(mContext,350); // 宽度
        lp.height = DensityUtil.dip2px(mContext,260); // 高度
        dialogWindow.setAttributes(lp);

        initDialogView(dialogView, shareTxt);
        dialog.show();
    }

    private void dismissDialog() {
        dialog.dismiss();
    }

    private void initDialogView(View view, String shareTxt) {

        RecyclerView mRecyclerView = view.findViewById(R.id.dialog_share_recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(mContext, R.layout.dialog_share_layout_item);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addDatas(buildShareDatas(shareTxt));

    }

    private List<ShareBean> buildShareDatas(String shareTxt) {
        List<ShareBean> list = new ArrayList<>();
        list.add(new ShareBean(R.drawable.share_weixin, "微信", shareTxt, SHARE_MEDIA.WEIXIN));
        list.add(new ShareBean(R.drawable.share_weixincircle, "朋友圈", shareTxt, SHARE_MEDIA.WEIXIN_CIRCLE));
        list.add(new ShareBean(R.drawable.share_weibo, "微博", shareTxt, SHARE_MEDIA.SINA));
        list.add(new ShareBean(R.drawable.share_qq, "QQ好友", shareTxt, SHARE_MEDIA.QQ));
        list.add(new ShareBean(R.drawable.share_qzone, "QQ空间", shareTxt, SHARE_MEDIA.QZONE));
        list.add(new ShareBean(R.drawable.share_pocket, "Pocket", shareTxt, SHARE_MEDIA.POCKET));
        list.add(new ShareBean(R.drawable.share_sms, "短信", shareTxt, SHARE_MEDIA.SMS));
        list.add(new ShareBean(R.drawable.share_email, "邮件", shareTxt, SHARE_MEDIA.EMAIL));
        list.add(new ShareBean(R.drawable.share_copy, "复制链接", shareTxt, null));
        list.add(new ShareBean(R.drawable.share_system, "系统分享", shareTxt, null));
        return list;
    }

    class ShareBean {
        public ShareBean(int res, String name, String shareTxt, SHARE_MEDIA platform) {
            this.res = res;
            this.name = name;
            this.shareTxt = shareTxt;
            this.platform = platform;
        }

        int res;
        String name;
        String shareTxt;
        SHARE_MEDIA platform;
    }

    class RecyclerViewAdapter extends SingleTypeAdapter<ShareBean> {

        public RecyclerViewAdapter(@NonNull Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void convert(int position, RecyclerViewHolder holder, final ShareBean bean) {
            ImageView icon = holder.get(R.id.share_item_icon);
            icon.setImageResource(bean.res);

            TextView name = holder.get(R.id.share_item_name);
            name.setText(bean.name);

            holder.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.platform == null && bean.name.equals("复制链接")) {
                        android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) mContext.getSystemService(
                                Context.CLIPBOARD_SERVICE);
                        clipboardManager.setText(bean.shareTxt);
                        Toast.makeText(mContext, "已复制到剪贴板中", Toast.LENGTH_SHORT).show();
                    } else if (bean.platform == null && bean.name.equals("系统分享")) {
                        Intent textIntent = new Intent(Intent.ACTION_SEND);
                        textIntent.setType("text/plain");
                        textIntent.putExtra(Intent.EXTRA_TEXT, bean.shareTxt);
                        mContext.startActivity(Intent.createChooser(textIntent, "分享"));
                    } else {
                        new ShareAction((Activity) mContext)
                                .setPlatform(bean.platform)//传入平台
                                .withText(bean.shareTxt)//分享内容
                                .setCallback(umShareListener)//回调监听器
                                .share();

                    }
                    dismissDialog();
                }
            });
        }

        private UMShareListener umShareListener = new UMShareListener() {
            /**
             * @descrption 分享开始的回调
             * @param platform 平台类型
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            /**
             * @descrption 分享成功的回调
             * @param platform 平台类型
             */
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();
            }

            /**
             * @descrption 分享失败的回调
             * @param platform 平台类型
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(mContext, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
            }

            /**
             * @descrption 分享取消的回调
             * @param platform 平台类型
             */
            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();

            }
        };
    }
}
