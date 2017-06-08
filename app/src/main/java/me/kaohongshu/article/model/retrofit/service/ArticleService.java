package me.kaohongshu.article.model.retrofit.service;


import java.util.List;

import me.kaohongshu.article.model.retrofit.service.bean.Article;
import me.kaohongshu.article.model.retrofit.service.result.Result;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author: shichunxiang
 * Date: 2017/5/25 0025
 */

public interface ArticleService {
    String end_point = "http://101.200.34.13:8080/tuicool/";

    @GET("getArticles")
    Call<Result<Article>> loadArticleList(@Query("page") int page);

}
