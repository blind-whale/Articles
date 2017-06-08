package me.kaohongshu.article.model.retrofit.service.bean;


/**
 * Author: shichunxiang
 * Date: 2017/5/25 0025
 */

public class Article extends Bean {

    /**
     * article_id : 5581
     * title : 2017 春季最酷的 30 个 Android 库
     * created_at : 2017-05-25 10:18:15
     * thumb_image : http://aimg2.tuicool.com/2qA3au2.png!index
     * category : null
     * is_hot : 0
     * tag : 安卓开发,
     * body : vAvqI3e.html
     * web_name : 开源中国翻译文章

     * origin_url : https://www.oschina.net/translate/30-new-android-libraries-released-in-the-spring-of-2017
     */

    private int article_id;
    private String title;
    private String created_at;
    private String thumb_image;
    private String images;
    private String category;
    private String is_hot;
    private String tag;
    private String body;
    private String web_name;
    private String web_logo;
    private String origin_url;
    private String des;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getWeb_name() {
        return web_name;
    }

    public void setWeb_name(String web_name) {
        this.web_name = web_name;
    }

    public String getWeb_logo() {
        return web_logo;
    }

    public void setWeb_logo(String web_logo) {
        this.web_logo = web_logo;
    }

    public String getOrigin_url() {
        return origin_url;
    }

    public void setOrigin_url(String origin_url) {
        this.origin_url = origin_url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Article{" +
                "article_id=" + article_id +
                ", title='" + title + '\'' +
                ", created_at='" + created_at + '\'' +
                ", thumb_image='" + thumb_image + '\'' +
                ", category=" + category +
                ", is_hot='" + is_hot + '\'' +
                ", tag='" + tag + '\'' +
                ", body='" + body + '\'' +
                ", web_name='" + web_name + '\'' +
                ", origin_url='" + origin_url + '\'' +
                '}';
    }
}
