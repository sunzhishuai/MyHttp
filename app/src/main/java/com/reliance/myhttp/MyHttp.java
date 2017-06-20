package com.reliance.myhttp;

/**
 * Created by sunzhishuai on 17/3/29.
 * E-mail itzhishuaisun@sina.com
 */

import java.util.concurrent.FutureTask;

/**
 * 暴露api 给调用层
 */
public class MyHttp {

    public static <T,M>void sendRequest(T requestinfo,String url,Class<M> responds
    ,IDataListener<M> iDatalistener){
        JsonHttpLisntenr<M> mJsonHttpLisntenr = new JsonHttpLisntenr<>(responds,iDatalistener);
        HttpTask httpTask = new HttpTask(requestinfo, url, mJsonHttpLisntenr);
        ThreadPoolManager.getInstance().execute(new FutureTask<Runnable>(httpTask,null));
    }


}
