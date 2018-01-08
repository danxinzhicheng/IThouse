package com.danmo.commonutil.recyclerview.adapter.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 带有头部和底部的适配器
 * 使用步骤：
 * 1. 创建实体类 Bean
 * 2. 创建对应的 provider 并继承自 BaseViewProvider， 在对应的 provider 的 onBindView 里面处理内容
 * 3. 使用 adapter.register(bean, provider.class) 来将数据实体和 provider 对应起来
 * 4. 注册 Header 或者 Footer
 * 5. 将数据 data 使用 ArrayList<Object> 类型存储起来， 使用 adapter.addDatas(data) 添加数据
 * 6. 大功告成
 */
public class HeaderFooterAdapter extends RecyclerView.Adapter<RecyclerViewHolder>
        implements TypePool {
    private List<Object> mItems = new ArrayList<>();
    private MultiTypePool mTypePool;

    private boolean hasHeader = false;
    private boolean hasFooter = false;
    private boolean hasMid = false;

    public HeaderFooterAdapter() {
        mTypePool = new MultiTypePool();
    }

    @Override
    public int getItemCount() {
        assert mItems != null;
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        assert mItems != null;
        Object item = mItems.get(position);
        int index = mTypePool.indexOf(item.getClass());
        return index;
    }

    @Override
    public void register(@NonNull Class<?> clazz, @NonNull BaseViewProvider provider) {
        mTypePool.register(clazz, provider);
    }

    public void registerHeader(@NonNull Object object, @NonNull BaseViewProvider provider) {
        mTypePool.register(object.getClass(), provider);
        if (hasHeader) {
            mItems.set(0, object);
        } else {
            mItems.add(0, object);
        }
        hasHeader = true;
        notifyDataSetChanged();
    }

    public void unRegisterHeader() {
        if (!hasHeader) return;
        mItems.remove(0);
        hasHeader = false;
        notifyDataSetChanged();
    }

    public void registerFooter(@NonNull Object object, @NonNull BaseViewProvider provider) {
        if (hasFooter) return;
        mTypePool.register(object.getClass(), provider);
        mItems.add(object);
        hasFooter = true;
        notifyDataSetChanged();
    }

    public void unRegisterFooter() {
        if (!hasFooter) return;
        mItems.remove(mItems.size() - 1);
        hasFooter = false;
        notifyDataSetChanged();
    }

    public void registerMiddle(@NonNull Object object, @NonNull BaseViewProvider provider) {
        if (hasMid) {
            if (!hasHeader) {
                mTypePool.register(object.getClass(), provider);
                mItems.set(0, object);
            } else {
                mTypePool.register(object.getClass(), provider);
                mItems.set(1, object);
            }
        } else {
            if (!hasHeader) {
                mTypePool.register(object.getClass(), provider);
                mItems.add(0, object);
            } else {
                mTypePool.register(object.getClass(), provider);
                mItems.add(1, object);
            }
        }
        hasMid = true;
        notifyDataSetChanged();
    }

    public void unRegisterMiddle() {
        if (!hasMid) return;
        if (hasHeader) {
            mItems.remove(1);
        } else {
            mItems.remove(0);
        }
        hasMid = false;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int indexViewType) {
        BaseViewProvider provider = getProviderByIndex(indexViewType);
        return provider.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        assert mItems != null;
        Object item = mItems.get(position);
        BaseViewProvider provider = getProviderByClass(item.getClass());
        provider.onBindView(holder, item);
    }

    @Override
    public int indexOf(@NonNull Class<?> clazz) {
        return mTypePool.indexOf(clazz);
    }

    @Override
    public List<BaseViewProvider> getProviders() {
        return mTypePool.getProviders();
    }

    @Override
    public BaseViewProvider getProviderByIndex(int index) {
        return mTypePool.getProviderByIndex(index);
    }

    @Override
    public <T extends BaseViewProvider> T getProviderByClass(@NonNull Class<?> clazz) {
        return mTypePool.getProviderByClass(clazz);
    }

    public void addDatas(List<?> items) {
        if (hasFooter) {
            mItems.addAll(mItems.size() - 1, items);
        } else {
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取纯数据 (不包含 Header 和 Footer)
     */
    public List<Object> getDatas() {
        int startIndex = 0;
        int endIndex = mItems.size();
        if (hasHeader) {
            startIndex++;
        }
        if (hasFooter) {
            endIndex--;
        }
        return mItems.subList(startIndex, endIndex);
    }

    /**
     * 获取全部数据 (包含 Header 和 Footer)
     */
    public List<Object> getFullDatas() {
        return mItems;
    }

    public void clearDatas() {
        int startIndex = 0;
        int endIndex = mItems.size();
        if (hasHeader) {
            startIndex++;
        }
        if (hasMid) {
            startIndex++;
        }
        if (hasFooter) {
            endIndex--;
        }

        for (int i = endIndex - 1; i >= startIndex; i--) {
            mItems.remove(i);
        }
        notifyDataSetChanged();
    }
}
