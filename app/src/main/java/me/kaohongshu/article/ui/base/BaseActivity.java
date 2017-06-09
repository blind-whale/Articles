package me.kaohongshu.article.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kaohongshu.article.App;
import me.kaohongshu.article.R;

/**
 * Author: shichunxiang
 * Date: 2017/5/26 0026
 */

public abstract class BaseActivity extends AppCompatActivity {
    public App app;

    @Nullable
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();
        int layoutId = getLayoutId();
        if (layoutId != -1) {
            setContentView(layoutId);
            ButterKnife.bind(this);
        }
    }

    public abstract int getLayoutId();
}
