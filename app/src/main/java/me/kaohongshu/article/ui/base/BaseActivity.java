package me.kaohongshu.article.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.kaohongshu.article.App;

/**
 * Author: shichunxiang
 * Date: 2017/5/26 0026
 */

public abstract class BaseActivity extends AppCompatActivity {
    public App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();
        int layoutId = getLayoutId();
        if (layoutId != -1) {
            setContentView(layoutId);
        }
    }

    public abstract int getLayoutId();
}
