package com.danmo.ithouse.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danmo.commonapi.bean.newest.NewestItem;
import com.danmo.commonutil.TimeUtil;
import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.multitype.BaseViewProvider;
import com.danmo.ithouse.R;
import com.danmo.ithouse.activity.NewsDetailActivity;
import com.danmo.ithouse.base.BaseApplication;
import com.danmo.ithouse.realm.NewsHistoryBean;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 资讯列表数据绑定
 */

public class NewestListProvider extends BaseViewProvider<NewestItem> {
    private Map<String, Integer> mapClicked = new HashMap<>();

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
        if (mapClicked.containsKey(bean.newsid)) {
            title.setTextColor(mContext.getResources().getColor(R.color.diy_gray2));
        } else {
            title.setTextColor(mContext.getResources().getColor(R.color.diy_black));
        }
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.start(mContext, NewsDetailActivity.TYPE_LIST, bean.newsid);
                title.setTextColor(mContext.getResources().getColor(R.color.diy_gray2));
                mapClicked.put(bean.newsid, position);

                if (realmResults != null && realmResults.size() >= 20) {
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

    }
}
