package me.kaohongshu.article.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import me.kaohongshu.article.R;

/**
 * Author: shichunxiang
 * Date: 2017/6/7 0007
 * Des:下拉刷新、加载更多功能
 */

public abstract class SwipeBaseActivity extends NetworkBaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    public int page = 1;
    boolean isLoadingMore = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRefreshLayout();
        refreshLayout.setRefreshing(true);
    }

    public void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_dark);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = findLastVisibleItemPosition();
                if (lastPosition + 1 == recyclerView.getAdapter().getItemCount()) {
                    if (refreshLayout.isRefreshing()) return;
                    if (!isLoadingMore) {
                        onLoadMore();
                    }
                }
            }
        });
    }

    public abstract int findLastVisibleItemPosition();

    @Override
    public void onRefresh() {
        page = 1;
        loadData(page);
    }

    public void onLoadMore() {
        isLoadingMore = true;
        page++;
        loadData(page);
    }

    public void loadData(int page) {

    }

    @Override
    public void onLoadData(List list, int apiIndex) {
        super.onLoadData(list, apiIndex);
        if (isLoadingMore) {
            if (list == null || list.size() == 0) {
                page--;
            }
            isLoadingMore = false;
        }else{
            refreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onCustomError(int errorCode, String errorInfo, int apiIndex) {
        super.onCustomError(errorCode, errorInfo, apiIndex);
        if (isLoadingMore) {
            isLoadingMore = false;
            page--;
        }else{
            refreshLayout.setRefreshing(false);
        }
    }
}
