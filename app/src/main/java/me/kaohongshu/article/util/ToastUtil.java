package me.kaohongshu.article.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Author: shichunxiang
 * Date: 2017/5/26 0026
 */

public class ToastUtil {
    public static void shortToast(String info,Context context){
        Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
    }

    public static void shortToast(View view,String info){
        Snackbar.make(view,info,Snackbar.LENGTH_SHORT).show();
    }
}
