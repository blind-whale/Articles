package me.kaohongshu.article.util;

import android.app.usage.NetworkStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.FeatureInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Author: shichunxiang
 * Date: 2017/5/25 0025
 * 检测网络是否连接：
 * 当连接或断开时发送对应的广播
 */

public class NetUtil {
    public static final String ACTION_NETWORK_DISCONNECTED = "me.kaohongshu.article.util.action.network.disconnected";
    public static final String ACTION_NETWORK_CONNECTED = "me.kaohongshu.article.util.action.network.connected";

    private static final String NAME = "network";
    private static final String KEY_NAME = "is_connected";

    /**
     * 网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean flag=false;
        if (networkInfo != null && networkInfo.isConnected()) {
            flag= true;
        }
        saveNetworkCacheState(context,flag);
        return flag;
    }

    public static boolean getNetworkCacheState(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(KEY_NAME, true);
    }

    /**
     * 保存网络连接状态，当网络断开或网络状态发生改变时，发送本地广播通知
     * @param context
     * @param isConnected
     */
    public static void saveNetworkCacheState(Context context, boolean isConnected) {
        SharedPreferences sp= context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        if(!isConnected||(isConnected!=sp.getBoolean(KEY_NAME,true))){
            String action = null;
            if (isConnected) {
                action =ACTION_NETWORK_CONNECTED;
            }else{
                action=ACTION_NETWORK_DISCONNECTED;
            }
            sendLocalBroadcast(context,action);
        }
        sp.edit().putBoolean(KEY_NAME,isConnected)
                .commit();
    }

    /**
     * 当网络状态发生变化时，发送本地广播
     * @param context
     * @param action
     */
    private static void sendLocalBroadcast(Context context, String action) {
        LocalBroadcastManager localBM=LocalBroadcastManager.getInstance(context);
        localBM.sendBroadcast(new Intent(action));
    }

    /**
     * 注册广播，监听本地网络变化
     * @param receiver
     * @param context
     */
    public static void registerNetworkChangeReceiver(BroadcastReceiver receiver,Context context){
        IntentFilter filter= new IntentFilter();
        filter.addAction(ACTION_NETWORK_DISCONNECTED);
        filter.addAction(ACTION_NETWORK_CONNECTED);
        LocalBroadcastManager localBM=LocalBroadcastManager.getInstance(context);
        localBM.registerReceiver(receiver,filter);
    }

    /**
     * 注销注册的广播
     * @param receiver
     * @param context
     */
    public static void unregisterNetworkChangeReceiver(BroadcastReceiver receiver,Context context){
        LocalBroadcastManager localBM=LocalBroadcastManager.getInstance(context);
        localBM.unregisterReceiver(receiver);
    }

}
