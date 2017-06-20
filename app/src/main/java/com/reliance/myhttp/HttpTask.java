package com.reliance.myhttp;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

/**
 * Created by sunzhishuai on 17/3/29.
 * E-mail itzhishuaisun@sina.com
 */

public class HttpTask implements Runnable {
    private IHttpService httpService;
    public <T> HttpTask(T requestInfo, String url, IHttpListener httpListener){
        this.httpService = new JsonHttpService();
        this.httpService.setUrl(url);
        httpService.setHttpCallBack(httpListener);
        if(requestInfo!=null){
            String s = JSON.toJSONString(requestInfo);
            try {
                this.httpService.setRequestData(s.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e("error",e.toString());
            }
        }
    }
    @Override
    public void run() {
        httpService.execute();
//        while (true){
//            Log.e("httptask","executing");
//        }
    }



}
