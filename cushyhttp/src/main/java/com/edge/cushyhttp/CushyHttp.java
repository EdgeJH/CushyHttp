package com.edge.cushyhttp;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * Created by user1 on 2017-12-22.
 */

public class CushyHttp<T> implements ResultListener {
    private HashMap<String,Object> settingMap;
    private HashMap<String,String> headerMap;
    private Object post;
    private Class<T> responseType;
    private OnHttpListener<T> onHttpListener;
    private String cookie;
    @SuppressWarnings("unchecked")
    private CushyHttp(Builder<T> builder,Class<T> type) {
        this.settingMap = builder.settingMap;
        this.headerMap = builder.headerMap;
        this.post = builder.post;
        this.responseType = type;
    }

    public static class Builder<T>{
        HashMap<String,Object> settingMap=new HashMap<>();
        HashMap<String,String> headerMap=new HashMap<>();
        Object post;
        private Class<T> responseType;

        public Builder(Class<T> responseType) {
            this.responseType = responseType;
        }

        public Builder<T> setRequestMethod (String method){
            settingMap.put("method",method);
            return this;
        }
        public Builder<T> setUrl(String url){
            settingMap.put("url",url);
            return this;
        }
        public Builder<T> setRequestHeader(String key, String value){
            headerMap.put(key,value);
            return this;
        }
        public Builder<T> setContentsType(int type){
            switch (type){
                case CushyField.JSON:
                    headerMap.put("Content-Type","application/json");
                    break;
                case CushyField.XML:
                    headerMap.put("Content-Type","application/xml");
                    break;
            }
            return this;
        }
        public Builder setPostData(Object postData){
            this.post = postData;
            return this;
        }
        public CushyHttp<T> build(){
            return new CushyHttp<T>(this,responseType);
        }
    }

    public  void startConnection(OnHttpListener<T> onHttpListener){
        this.onHttpListener =onHttpListener;
        CushyBase cushyBase = new CushyBase(settingMap,headerMap,post,this);
        cushyBase.execute();
    }
    @Override
    public void sendResult(Response response) {
       if (response.getCode()==HttpURLConnection.HTTP_OK){
           cookie =response.getCookie();
           Gson gson = new Gson();
           T responseClass;
           if (responseType.getName().equals("org.json.JSONObject")){
               try {
                   responseClass = (T) new JSONObject(response.getData());
                   onHttpListener.onResonpse(responseClass,response.getCode());
               } catch (JSONException e) {
                   e.printStackTrace();
                   onHttpListener.onFailure(500,"json파싱에 오류가 생겼습니다");
               }

           } else if (responseType.getName().equals("org.json.JSONArray")){
               try {
                   responseClass = (T) new JSONArray(response.getData());
                   onHttpListener.onResonpse(responseClass,response.getCode());
               } catch (JSONException e) {
                   e.printStackTrace();
                   onHttpListener.onFailure(500,"json파싱에 오류가 생겼습니다");
               }

           }else {
               responseClass = gson.fromJson(response.getData(), responseType);
               onHttpListener.onResonpse(responseClass,response.getCode());
           }

       } else {
           onHttpListener.onFailure(response.getCode(),response.getData());
       }
    }

    public String getCookie() {
        return cookie;
    }
}
