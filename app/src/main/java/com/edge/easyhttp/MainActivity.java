package com.edge.easyhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.edge.cushyhttp.CushyField;
import com.edge.cushyhttp.CushyHttp;
import com.edge.cushyhttp.OnHttpListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CushyHttp<JSONObject> cushyHttp = new CushyHttp.Builder<>(JSONObject.class)
                .setRequestMethod(CushyField.GET)
                .setContentsType(CushyField.JSON)
                .setUrl(" http://apis.skplanetx.com/weather/forecast/3days?lon=126.937230&village=&county=&foretxt=&lat=37.477560&city=&version=1")
                .setRequestHeader("appKey"," 29df61f0-847d-3ef7-a837-1070542a2123")
                .build();
        cushyHttp.startConnection(new OnHttpListener<JSONObject>() {
            @Override
            public void onResonpse(JSONObject responseData, int responseCode) {
                Log.d("aaaa",responseData.toString());
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }


}
