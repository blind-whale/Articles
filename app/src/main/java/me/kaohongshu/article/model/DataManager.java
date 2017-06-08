package me.kaohongshu.article.model;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.List;

import me.kaohongshu.article.App;
import me.kaohongshu.article.model.retrofit.HttpHelper;
import me.kaohongshu.article.model.retrofit.service.ArticleService;
import me.kaohongshu.article.model.retrofit.service.bean.Article;
import me.kaohongshu.article.model.retrofit.service.result.Result;
import me.kaohongshu.article.util.LogUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: shichunxiang
 * Date: 2017/5/25 0025
 */

public class DataManager {
    private static DataManager dataManager;
    private App mContext;
    private HttpHelper httpHelper;
    private DataManager(App context){
        mContext=context;
        httpHelper=new HttpHelper(context);
    }

    public static DataManager getInstance(App context){
        if(dataManager==null){
            synchronized (DataManager.class){
                if(dataManager==null){
                    dataManager = new DataManager(context);
                }
            }
        }
        return dataManager;
    }

    public void getArticleList(int page, final ResultListener<Result> listener,final int apiIndex){
        ArticleService service = httpHelper.getApiService(ArticleService.class);
        Call<Result<Article>> call = service.loadArticleList(page);
        call.enqueue(new Callback<Result<Article>>() {
            @Override
            public void onResponse(Call<Result<Article>> call, Response<Result<Article>> response) {
                if(response.isSuccessful()){
                    Result<Article> result = response.body();
                    if(result.getStatus()==1){
                        LogUtil.e("retrofit","loadArticleList success: "+(result.getData()==null?"0":result.getData().size()));
                    }else{
                        LogUtil.e("retrofit","loadArticleList fail; "+ result.getErrorInfo());
                    }
                    listener.onSuccess(result,apiIndex);
                }else{
                    int code = response.code();
                    try {
                        listener.onFail(null,code,response.errorBody()==null?null:response.errorBody().string(),apiIndex);
                    } catch (IOException e) {
                        e.printStackTrace();
                        listener.onFail(null,code,null,apiIndex);
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<Article>> call, Throwable t) {
                LogUtil.e("retrofit",t.toString());
                t.printStackTrace();
            }
        });
    }

    public static interface ResultListener<T>{
        void onSuccess(T result,int apiIndex);
        void onFail(Throwable t,int code,String errorInfo,int apiIndex);
    }

}
