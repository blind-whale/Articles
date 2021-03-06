package me.kaohongshu.article.model.retrofit.service.result;

import java.io.Serializable;
import java.util.List;

/**
 * Author: shichunxiang
 * Date: 2017/5/25 0025
 */

public class Result<T> implements Serializable {
    private int status;
    private int errorCode;
    private String errorInfo;
    private List<T> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
