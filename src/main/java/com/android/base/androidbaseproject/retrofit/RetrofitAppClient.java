package com.android.base.androidbaseproject.retrofit;

import com.android.base.androidbaseproject.BuildConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Retrofit app client
 * Created by secretqi on 2018/3/3.
 */

public final class RetrofitAppClient {

    private static RetrofitAppClient instance;
    public RetrofitAppClient(){

    }

    public static RetrofitAppClient getInstance() {
        if (null == instance) {
            instance = new RetrofitAppClient();
        }
        return instance;
    }

    public static final class Builder {

        private OkHttpClient okHttpClient;
        private OkHttpClient.Builder okHttpBuilder;
        private String baseUrl;

        public Builder() {
            this.okHttpBuilder = new OkHttpClient.Builder();
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder interceptor(Interceptor interceptor) {
            if (null != interceptor) {
                okHttpBuilder.addInterceptor(interceptor);
            }
            return this;
        }

        public Builder build() {
            okHttpClient = okHttpBuilder.build();
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                okHttpBuilder.addInterceptor(loggingInterceptor);
            }
            return this;
        }

        public Retrofit retrofit() {
            if (null == okHttpBuilder) {
                throw new RuntimeException("You must call build first");
            }
            return new Retrofit.Builder()
                    .baseUrl(this.baseUrl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

    }

}
