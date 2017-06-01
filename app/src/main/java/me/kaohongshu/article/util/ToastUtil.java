package me.kaohongshu.article.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Author: shichunxiang
 * Date: 2017/5/26 0026
 */

public class ToastUtil {
    public static void shortToast(String info,Context context){
        Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
    }
}
