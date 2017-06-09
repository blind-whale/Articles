package me.kaohongshu.article.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;

import java.security.MessageDigest;

/**
 * Author: shichunxiang
 * Date: 2017/6/7 0007
 */

public class ImageManager {

    public static void loadImg(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void loadRoundImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().transform(new GlideRoundTransform(context, 5)))
                .into(imageView);
    }


    public static void loadCircleImag(Context context, Object o, ImageView imageView) {
        Glide.with(context)
                .load(o)
                .apply(new RequestOptions().circleCrop())
                .into(imageView);
    }

    public static class GlideRoundTransform extends BitmapTransformation {
        float radius;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, float radius) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * radius;
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;
            Bitmap origin = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (origin == null) {
                origin = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(origin);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectf = new RectF(0, 0, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectf, radius, radius, paint);
            return origin;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }
    }
}
