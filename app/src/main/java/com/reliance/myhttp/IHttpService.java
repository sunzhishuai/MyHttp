package com.reliance.myhttp;

/**
 * Created by sunzhishuai on 17/3/29.
 * E-mail itzhishuaisun@sina.com
 */

public interface IHttpService {
    /**
     * 设置
     * @param url 请求地址
     */
    void setUrl(String url);

    /**
     * 设置请求参数
     * @param requestData 请求内容
     */
    void setRequestData(byte[] requestData);

    /**
     * 设置数据
     * @param iHttpListener 网路处理回调
     */
    void setHttpCallBack(IHttpListener iHttpListener);

    /**
     * 执行
     */
    void execute();
}
