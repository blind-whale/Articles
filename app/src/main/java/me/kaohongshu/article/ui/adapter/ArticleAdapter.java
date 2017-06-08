package me.kaohongshu.article.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kaohongshu.article.R;
import me.kaohongshu.article.model.DataManager;
import me.kaohongshu.article.model.ImageManager;
import me.kaohongshu.article.model.retrofit.service.bean.Article;
import me.kaohongshu.article.ui.activity.ShowArticleActivity;

/**
 * Author: shichunxiang
 * Date: 2017/5/26 0026
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int FOOTER_TYPE = 0;
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;
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
        if (position + 1 == getItemCount()) return FOOTER_TYPE;
        return ITEM_TYPE1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == FOOTER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false);
            return new FooterHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_type1, parent, false);
            return new HolderType1(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof HolderType1) {
            Article article = articles.get(position);
            HolderType1 holderType1 = (HolderType1) viewHolder;
            holderType1.initView(mContext, article);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size() == 0 ? 0 : articles.size() + 1;
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    public static class HolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_webicon)
        ImageView webIcon;
        @BindView(R.id.tv_webname)
        TextView webName;
        @BindView(R.id.tv_category)
        TextView category;
        @BindView(R.id.tv_author)
        TextView author;
        @BindView(R.id.tv_dot)
        TextView dot;
        @BindView(R.id.tv_date)
        TextView date;
        @BindView(R.id.tv_des)
        TextView des;
        @BindView(R.id.iv_thumbimage)
        ImageView thumbimage;
        @BindView(R.id.tv_title)
        TextView title;

        public HolderType1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void initView(final Context context, final Article article) {
            ImageManager.loadRoundImage(context, article.getWeb_logo(), webIcon);
            webName.setText(article.getWeb_name());
            String temp = "";
            if (!TextUtils.isEmpty(article.getTag())) {
                temp = article.getTag();
                if (temp.endsWith(","))
                    temp = temp.substring(0, temp.length() - 1);
                dot.setVisibility(View.VISIBLE);
            } else {
                dot.setVisibility(View.GONE);
            }

            author.setText(temp);
            date.setText(article.getCreated_at());
            if (!TextUtils.isEmpty(article.getCategory()))
                category.setText(article.getCategory());
            if (!TextUtils.isEmpty(article.getDes()))
                des.setText(article.getDes());
            title.setText(article.getTitle());
            ImageManager.loadImg(context, article.getThumb_image(), thumbimage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowArticleActivity.class);
                    intent.putExtra(ShowArticleActivity.EXTRA_ARTICLE, article);
                    context.startActivity(intent);
                }
            });
        }
    }
}
