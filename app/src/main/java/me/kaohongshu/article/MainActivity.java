package me.kaohongshu.article;

import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.kaohongshu.article.model.retrofit.service.bean.Article;
import me.kaohongshu.article.ui.adapter.ArticleAdapter;
import me.kaohongshu.article.ui.base.NetworkBaseActivity;
import me.kaohongshu.article.util.ToastUtil;

public class MainActivity extends NetworkBaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    ArticleAdapter adapter;
    List<Article> articles = new ArrayList<Article>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();
        initView();

        refreshLayout.setRefreshing(true);
        pageNum = 0;
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white, null));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        }
        setSupportActionBar(toolbar);
    }

    private boolean isLoading = false;

    private void initView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new ArticleAdapter(this, articles);
        recyclerView.setAdapter(adapter);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_dark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                loadData();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisiblePosition = llm.findLastVisibleItemPosition();
                if (lastVisiblePosition + 1 == adapter.getItemCount()) {
                    if (refreshLayout.isRefreshing()) {
//                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        loadData();
                    }
                }
            }
        });
    }

    private int pageNum = 0;

    public void loadData() {
        pageNum++;
        dataManager.getArticleList(pageNum, this, 1);
        if (isLoading) isLoading = false;
    }

    @Override
    public void onLoadData(Object o, int apiIndex) {
        super.onLoadData(o, apiIndex);
        if (o == null) {
            pageNum--;
        }
        List<Article> list = (List<Article>) o;
        if (list.size() == 0) {
            pageNum--;
        } else {
            if (pageNum == 1) {
                articles.clear();
            }
            articles.addAll(list);
            adapter.notifyDataSetChanged();
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onCustomError(int errorCode, String errorInfo, int apiIndex) {
        super.onCustomError(errorCode, errorInfo, apiIndex);
        refreshLayout.setRefreshing(false);
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
