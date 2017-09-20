/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-08 01:01:18
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.danmo.commonapi.base;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * 实现类，具体实现在此处
 *
 * @param <Service>
 */
public class BaseImpl<Service> {
    private static Retrofit mRetrofit;
    protected CacheUtil mCacheUtil;
    protected Service mService;

    public BaseImpl(@NonNull Context context) {
        mCacheUtil = new CacheUtil(context.getApplicationContext());
        initRetrofit();
        this.mService = mRetrofit.create(getServiceClass());
    }

    private Class<Service> getServiceClass() {
        return (Class<Service>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private void initRetrofit() {
        if (null != mRetrofit)
            return;

        // 设置 Log 拦截器，可以用于以后处理一些异常情况
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 配置 client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)                // 设置拦截器
                .retryOnConnectionFailure(true)             // 是否重试
                .connectTimeout(5, TimeUnit.SECONDS)        // 连接超时事件
                .readTimeout(5, TimeUnit.SECONDS)           // 读取超时时间
                .build();

        // 配置 Retrofit

        Retrofit.Builder builder = new Retrofit.Builder();
        if (Constant.isNeedGsonParse) {
            mRetrofit = builder.baseUrl(Constant.BASE_URL)                         // 设置 base url
                    .client(client)                                     // 设置 client
                    .addConverterFactory(SimpleXmlConverterFactory.create())// 设置xml转换工具
                    .addConverterFactory(GsonConverterFactory.create()) // 设置 Json 转换工具
                    .build();
        } else {
            mRetrofit = builder.baseUrl(Constant.BASE_URL)                         // 设置 base url
                    .client(client)                                   // 设置 client
                    .addConverterFactory(SimpleXmlConverterFactory.create())// 设置xml转换工具
                    .build();
        }
    }
}



