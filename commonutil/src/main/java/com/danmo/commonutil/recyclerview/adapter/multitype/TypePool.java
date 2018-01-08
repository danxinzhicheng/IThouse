package com.danmo.commonutil.recyclerview.adapter.multitype;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * 类型池
 */
public interface TypePool {

    public void register(@NonNull Class<?> clazz, @NonNull BaseViewProvider provider);

    public int indexOf(@NonNull final Class<?> clazz);

    public List<BaseViewProvider> getProviders();

    public BaseViewProvider getProviderByIndex(int index);

    public <T extends BaseViewProvider> T getProviderByClass(@NonNull final Class<?> clazz);
}
