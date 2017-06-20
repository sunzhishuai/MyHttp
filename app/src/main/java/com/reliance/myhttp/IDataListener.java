package com.reliance.myhttp;

/**
 * Created by sunzhishuai on 17/3/29.
 * E-mail itzhishuaisun@sina.com
 */

/**
 * 相应类型回调
 * @param <M>
 */
public interface IDataListener<M> {
    void onSuccess(M responds);
    void onError();
}
