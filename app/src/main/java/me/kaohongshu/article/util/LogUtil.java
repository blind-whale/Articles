package me.kaohongshu.article.util;

import android.util.Log;

import me.kaohongshu.article.BuildConfig;

/**
 * Author: shichunxiang
 * Date: 2017/5/25 0025
 */

public class LogUtil {
    public static void e(String tag,String info){
        if(BuildConfig.DEBUG){
            Log.e(tag,info);
        }
    }
}
