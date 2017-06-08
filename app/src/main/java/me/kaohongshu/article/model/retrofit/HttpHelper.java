package me.kaohongshu.article.model.retrofit;

import android.app.DownloadManager;
import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import me.kaohongshu.article.App;
import me.kaohongshu.article.util.LogUtil;
import me.kaohongshu.article.util.NetUtil;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: shichunxiang
 * Date: 2017/5/25 0025
 */

public class HttpHelper {
    private static final int DEFAULT_TIMEOUT = 30;
    private HashMap<String, Object> mServiceMap;
    private Context mContext;

    public HttpHelper(App context) {
        mServiceMap = new HashMap<String, Object>();
        mContext = context;
    }

    public <T> T getApiService(Class<T> serviceClass) {
        String className = serviceClass.getName();
        if (mServiceMap.containsKey(className)) {
            return (T) mServiceMap.get(className);
        } else {
            T service = createService(serviceClass);
            mServiceMap.put(className, service);
            return service;
        }
    }

    private <T> T createService(Class<T> serviceClass) {
        return createService(serviceClass, configureOkHttpClient());
    }

    private <T> T createService(Class<T> serviceClass, OkHttpClient client) {
        String end_point = "";
        try {
            Field field = serviceClass.getField("end_point");
            end_point = (String) field.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(end_point)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(Rx)
                .build();
        return retrofit.create(serviceClass);

    }

    private OkHttpClient configureOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //Timeout
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //cache
        File cacheDirectory = mContext.getCacheDir();
        builder.cache(new Cache(cacheDirectory, 10 * 1024 * 1024));
        //Interceptor
        builder.addNetworkInterceptor(new LogInterceptor())
                .addInterceptor(new CacheControlInterceptor());
        return builder.build();
    }

    private class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            LogUtil.e("okhttp", request.toString());
            LogUtil.e("okhttp", request.headers() == null ? "null" : request.headers().toString());
            Response response = chain.proceed(request);
            if (response.isSuccessful()) {
                MediaType contentType = response.body().contentType();
                String body = response.body().string();
                response = response.newBuilder()
                        .body(ResponseBody.create(contentType, body))
                        .build();
                LogUtil.e("okhttp", body);
            } else {
                LogUtil.e("okhttp", response.message());
            }
            return response;
        }
    }

    private class CacheControlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkConnected(mContext)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);
            if (NetUtil.isNetworkConnected(mContext)) {
                int maxAge = 60 * 60;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
}
