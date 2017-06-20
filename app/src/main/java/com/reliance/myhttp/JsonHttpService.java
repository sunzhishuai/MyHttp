package com.reliance.myhttp;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sunzhishuai on 17/3/29.
 * E-mail itzhishuaisun@sina.com
 */

public class JsonHttpService implements IHttpService {
    private IHttpService httpServicer;
    private HttpClient httpClient = new DefaultHttpClient();
    private String url;
    private HttpRequestBase httpRequestBase;
    private byte[] requestData;
    private IHttpListener iHttpListener;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    @Override
    public void setHttpCallBack(IHttpListener iHttpListener) {
        this.iHttpListener = iHttpListener;
    }

    /**
     * 子线程
     */
    @Override
    public void execute() {
        try {
            //城市名称
            String city = URLEncoder.encode("重庆", "GB2312");
            System.out.println(city);
            // api url ：北向URL
            String locationUrl = "http://php.weather.sina.com.cn/xml.php?city=" + city + "&password=DJOYnieT8234jlsK&day=0";
            // http body 消息体
            String reqBody = "";
            // http method
            String method = "POST";
            // http head : Content-Type 消息类型
            String contentType = "application/json;charset=UTF-8";
            // 设定连接的相关参数
            URL url = new URL(locationUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", contentType);
            //写入请求消息体
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8");
            out.write(reqBody);
            out.flush();
            out.close();
            // 获取服务端的反馈
            String strLine = "";
            StringBuilder strResponse = new StringBuilder();
            Map<String, List<String>> rspHeaders = connection.getHeaderFields();
            Set<String> rspHeadNames = rspHeaders.keySet();
            for (String key : rspHeadNames) {
                //rspHeaders中的http状态码和描述的键为null
                if (null != key) {
                    strResponse.append(key + ": ");
                }
                strResponse.append(new String(rspHeaders.get(key).get(0)
                        .getBytes("iso-8859-1"), "UTF-8")
                        + "\n");
            }

            int code = connection.getResponseCode();
            // String status = connection.getResponseMessage();
            InputStream in;

            // 判断http状态码
            if (code == 200) {
                in = connection.getInputStream();
            } else {
                in = connection.getErrorStream();
            }
            if (null != in) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in));
                while ((strLine = reader.readLine()) != null) {
                    strResponse.append("\n" + strLine);
                }
            }
            Log.e("strResponse",strResponse.toString());

        } catch (Exception e) {

        }

    }
//        httpRequestBase = new HttpPost(url);
//        if (requestData != null) {
//            //设置请求参数
//            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestData);
//            ((HttpPost) httpRequestBase).setEntity(byteArrayEntity);
//        }
//        try {
//            this.httpClient.execute(httpRequestBase, new HttpRespondsHandle());
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("error",e.toString());
//        }
}

//public class HttpRespondsHandle extends BasicResponseHandler {
//
//    @Override
//    public String handleResponse(HttpResponse response) throws ClientProtocolException {
////        int statusCode = response.getStatusLine().getStatusCode();
////        if (statusCode == 200) {
////            HttpEntity entity = response.getEntity();
////            if (iHttpListener != null) {
////                try {
////                    iHttpListener.onSuccess(entity.getContent());
////                } catch (IOException e) {
////                    e.printStackTrace();
////                    Log.e("error", e.toString());
////                }
////            }
////        } else {
////            if (iHttpListener != null) {
////                iHttpListener.onError();
////            }
////        }
////        Log.e("error", "------");
//        return null;
//    }
//
//}
