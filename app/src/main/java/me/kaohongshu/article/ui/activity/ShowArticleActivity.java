package me.kaohongshu.article.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.flexbox.FlexboxLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import me.kaohongshu.article.R;
import me.kaohongshu.article.model.ImageManager;
import me.kaohongshu.article.model.retrofit.service.bean.Article;
import me.kaohongshu.article.ui.base.WebviewBaseActivity;
import me.kaohongshu.article.util.ToastUtil;

/**
 * Author: shichunxiang
 * Date: 2017/5/27 0027
 */

public class ShowArticleActivity extends WebviewBaseActivity {
    public static String EXTRA_ARTICLE = "article";

    Article article;

    @BindView(R.id.iv_webicon)
    ImageView ivWebIcon;
    @BindView(R.id.tv_webname)
    TextView tvWebName;
    @BindView(R.id.fl_tag)
    FlexboxLayout flTag;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        article = (Article) getIntent().getSerializableExtra(EXTRA_ARTICLE);

        initView();
        initToobar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(article.getOrigin_url()));
                startActivity(intent);
                break;
            case R.id.item2:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("kaohongshu", article.getOrigin_url()));
                ToastUtil.shortToast("文章地址已经复制到剪贴板", app);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToobar() {
        tvWebName.setText(article.getWeb_name());
        ImageManager.loadCircleImag(app, article.getWeb_logo(), ivWebIcon);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_article;
    }

    private void initView() {
        tvTitle.setText(article.getTitle());
        webView.loadUrl(article.getBody());
        updateTags();
    }

    private void updateTags() {
        String tags = article.getTag();
        if (TextUtils.isEmpty(tags)) return;
        String[] tagArray = tags.split(",");
        flTag.removeAllViews();
        int margin= (int) (Resources.getSystem().getDisplayMetrics().density*8);
        for (String tag : tagArray) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_flexbox_tag, null);
            FlexboxLayout.LayoutParams param = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT);
            param.topMargin=margin/2;
            param.bottomMargin=margin/2;
            param.leftMargin=margin/2;
            param.rightMargin=margin/2;
            tv.setText(tag);
            tv.setLayoutParams(param);
            flTag.addView(tv);
        }
    }

    @OnClick(R.id.iv_add_collection)
    public void addToCollection() {

    }

    @OnClick(R.id.iv_share)
    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TITLE, article.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, article.getOrigin_url());
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

}
