package me.kaohongshu.article.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import java.util.List;

import me.kaohongshu.article.R;
import me.kaohongshu.article.model.DataManager;
import me.kaohongshu.article.model.retrofit.service.result.Result;
import me.kaohongshu.article.util.NetUtil;
import me.kaohongshu.article.util.ToastUtil;

/**
 * Author: shichunxiang
 * Date: 2017/5/26 0026
 */

public abstract class NetworkBaseActivity extends BaseActivity implements DataManager.ResultListener<Result> {
    public DataManager dataManager;
    NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = DataManager.getInstance(app);
        networkStateReceiver = new NetworkStateReceiver();
        NetUtil.registerNetworkChangeReceiver(networkStateReceiver, app);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetUtil.unregisterNetworkChangeReceiver(networkStateReceiver, app);
    }

    @Override
    public void onSuccess(final Result result, final int apiIndex) {
        if (result.getStatus() != 1) {
            onCustomError(result.getErrorCode(), result.getErrorInfo(), apiIndex);
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onLoadData(result.getData(), apiIndex);
            }
        });

    }

    @Override
    public void onFail(Throwable t, int code, String errorInfo, int apiIndex) {
        onCustomError(code, errorInfo, apiIndex);
    }

    public void onLoadData(List list, int apiIndex) {

    }

    public void onCustomError(int errorCode, String errorInfo, int apiIndex) {

    }

    class NetworkStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetUtil.ACTION_NETWORK_CONNECTED.equals(intent.getAction())) {
                ToastUtil.shortToast(getString(R.string.toast_network_info2), context);
            } else if (NetUtil.ACTION_NETWORK_DISCONNECTED.equals(intent.getAction())) {
                ToastUtil.shortToast(getString(R.string.toast_network_info1), context);
            }
        }
    }
}
