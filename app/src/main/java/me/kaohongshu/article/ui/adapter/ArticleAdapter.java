package me.kaohongshu.article.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.kaohongshu.article.R;
import me.kaohongshu.article.model.DataManager;
import me.kaohongshu.article.model.retrofit.service.bean.Article;
import me.kaohongshu.article.ui.activity.ShowArticleActivity;

/**
 * Author: shichunxiang
 * Date: 2017/5/26 0026
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE = 1;
    private static final int FOOTER_TYPE = 2;
    List<Article> articles;
    Context mContext;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.articles = articles;
        mContext = context;
    }

    public void updateArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int getItemViewType(int position) {
        return position + 1 == getItemCount() ? FOOTER_TYPE : ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
            return new ArticleHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false);
            return new FooterHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ArticleHolder) {
            ArticleHolder holder = (ArticleHolder) viewHolder;
            final Article article = articles.get(position);
            holder.titleTv.setText(article.getTitle().trim());
            holder.timeTv.setText(article.getCreated_at().trim());
            holder.webnameTv.setText(article.getWeb_name().trim());
            DataManager.loadImg(mContext, article.getThumb_image(), holder.img);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ShowArticleActivity.class);
                    intent.putExtra(ShowArticleActivity.EXTRA_ARTICLE, article);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return articles.size() == 0 ? 0 : articles.size() + 1;
    }

    public static class ArticleHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView titleTv;
        TextView webnameTv;
        TextView timeTv;

        public ArticleHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv);
            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
            webnameTv = (TextView) itemView.findViewById(R.id.webname_tv);
            timeTv = (TextView) itemView.findViewById(R.id.time_tv);

        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }
}
