package com.reliance.myhttp;

import java.io.InputStream;

/**
 * Created by sunzhishuai on 17/3/29.
 * E-mail itzhishuaisun@sina.com
 */

/**
 * http回调
 */
public interface IHttpListener {
    /**
     * 返回成功时候调用
     * @param inputStream http 返回数据流
     */
    void onSuccess(InputStream inputStream);

    /**
     * 请求失败时候调用
     */
    void onError();
}
