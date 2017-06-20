package com.reliance.myhttp;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by sunzhishuai on 17/3/29.
 * E-mail itzhishuaisun@sina.com
 */

public class JsonHttpLisntenr<M> implements IHttpListener {
    private Class<M> respondsClass;
    private IDataListener<M> jsonListener;
    Handler handler = new Handler(Looper.myLooper());

    public JsonHttpLisntenr(Class<M> respondsClass, IDataListener<M> jsonListener) {
        this.respondsClass = respondsClass;
        this.jsonListener = jsonListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String streamString = getStreamString(inputStream);
        final M responds = JSON.parseObject(streamString, respondsClass);
        handler.post(new Runnable() {
            @Override
            public void run() {
                jsonListener.onSuccess(responds);
            }
        });
    }

    public static String getStreamString(InputStream tInputStream) {
        if (tInputStream != null) {
            try {
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
                StringBuffer tStringBuffer = new StringBuffer();
                String sTempOneLine = new String("");
                while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                    tStringBuffer.append(sTempOneLine);
                }
                return tStringBuffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error",e.toString());
            }
        }
        return null;
    }
    @Override
    public void onError() {
        jsonListener.onError();
    }
}
