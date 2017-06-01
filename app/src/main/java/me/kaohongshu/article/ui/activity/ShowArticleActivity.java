package me.kaohongshu.article.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.style.ImageSpan;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.HashMap;

import me.kaohongshu.article.R;
import me.kaohongshu.article.model.retrofit.service.bean.Article;
import me.kaohongshu.article.ui.base.WebviewBaseActivity;

/**
 * Author: shichunxiang
 * Date: 2017/5/27 0027
 */

public class ShowArticleActivity extends WebviewBaseActivity {
    public static String EXTRA_ARTICLE = "article";

    Article article;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        article=(Article)getIntent().getSerializableExtra(EXTRA_ARTICLE);

        initView();
        initToobar();
    }

    private void initToobar() {
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(article.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(android.R.color.white));
        }else{
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_article;
    }

    private void initView(){
        webView.loadUrl(article.getBody());
    }

}
