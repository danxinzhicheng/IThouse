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
 */

package com.danmo.commonapi.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danmo.commonapi.base.cache.CacheUtil;
import com.danmo.commonapi.converter.StringConverterFactory;

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
    protected static int currentParse;
    private static String currentBaseUrl;

    public BaseImpl(@NonNull Context context, String baseUrl, int currentParse) {
        mCacheUtil = new CacheUtil(context.getApplicationContext());
        if (null != mRetrofit && this.currentParse == currentParse && this.currentBaseUrl.equals(baseUrl)) {
            return;
        }
        initRetrofit(baseUrl, currentParse);
        this.mService = mRetrofit.create(getServiceClass());
        this.currentParse = currentParse;
        this.currentBaseUrl = baseUrl;
    }

    private Class<Service> getServiceClass() {
        return (Class<Service>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private void initRetrofit(String baseUrl, int currentParse) {

        // 设置 Log 拦截器，可以用于以后查看处理一些异常情况
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

        if (currentParse == Constant.PARSE_GSON) {
            Retrofit.Builder builder = new Retrofit.Builder();
            mRetrofit = builder.baseUrl(baseUrl)                         // 设置 base url
                    .client(client)                                     // 设置 client
                    .addConverterFactory(GsonConverterFactory.create()) // 设置 Json 转换工具
                    .build();
        } else if (currentParse == Constant.PARSE_XML) {
            Retrofit.Builder builder = new Retrofit.Builder();
            mRetrofit = builder.baseUrl(baseUrl)                         // 设置 base url
                    .client(client)                                     // 设置 client
                    .addConverterFactory(SimpleXmlConverterFactory.create())// 设置xml转换工具
                    .build();
        } else {
            Retrofit.Builder builder = new Retrofit.Builder();
            mRetrofit = builder.baseUrl(baseUrl)                         // 设置 base url
                    .client(client)                                     // 设置 client
                    .addConverterFactory(StringConverterFactory.create()) // 设置 string 转换工具
                    .build();
        }

    }


}



