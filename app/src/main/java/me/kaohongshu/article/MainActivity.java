package me.kaohongshu.article;

import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.kaohongshu.article.model.ImageManager;
import me.kaohongshu.article.model.retrofit.service.bean.Article;
import me.kaohongshu.article.ui.adapter.ArticleAdapter;
import me.kaohongshu.article.ui.base.SwipeBaseActivity;

public class MainActivity extends SwipeBaseActivity {


    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    ArticleAdapter adapter;
    List<Article> articles = new ArrayList<Article>();
    LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();
        initView();
        initDrawer();

        loadData(1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private boolean isLoading = false;

    private void initView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new ArticleAdapter(this, articles);
        recyclerView.setAdapter(adapter);
    }

    private void initDrawer() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(mDrawerToggle);

        // 去除侧边栏滚动条
        NavigationMenuView menuView = (NavigationMenuView) navView.getChildAt(0);
        menuView.setVerticalScrollBarEnabled(false);

        View headerView = navView.inflateHeaderView(R.layout.drawer_header);
        ImageView drawerIcon = (ImageView) headerView.findViewById(R.id.drawer_header_icon);
        ImageManager.loadCircleImag(app, R.drawable.turbo, drawerIcon);
    }

    @Override
    public int findLastVisibleItemPosition() {
        return llm.findLastVisibleItemPosition();
    }

    public void loadData(int page) {
        dataManager.getArticleList(page, this, 1);
    }

    @Override
    public void onLoadData(List lists, int apiIndex) {
        super.onLoadData(lists, apiIndex);
        List<Article> list = (List<Article>) lists;
        if (list.size() != 0) {
            for (Article article : list) {
                if (TextUtils.isEmpty(article.getThumb_image()) && !TextUtils.isEmpty(article.getImages())) {
                    String[] temp = article.getImages().split(",");
                    article.setThumb_image(temp[0].replace("[u'", "").replace("']", ""));
                }
            }
            if (page == 1) {
                articles.clear();
            }
            articles.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCustomError(int errorCode, String errorInfo, int apiIndex) {
        super.onCustomError(errorCode, errorInfo, apiIndex);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
