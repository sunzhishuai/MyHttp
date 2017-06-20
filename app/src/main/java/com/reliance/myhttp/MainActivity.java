package com.reliance.myhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        for (int i = 0; i < 20; i++) {
            User sunzs = new User("sunzs", "123456");
            MyHttp.sendRequest(sunzs, "http://59.110.16.81:81/api/profile/login", Responds.class, new IDataListener<Responds>() {
                @Override
                public void onSuccess(Responds responds) {
                    Log.e(TAG,responds.toString());
                }

                @Override
                public void onError() {
                    Log.e(TAG,"error");
                }
            });
        }
    }


}
