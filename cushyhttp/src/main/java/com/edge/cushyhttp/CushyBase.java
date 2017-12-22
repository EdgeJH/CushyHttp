package com.edge.cushyhttp;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by user1 on 2017-12-22.
 */

public class CushyBase extends AsyncTask<Void,Void,Response> {
    private HashMap<String,Object> settingMap;
    private HashMap<String,String> headerSetting;
    private Object post;
    private HttpURLConnection urlConnection=null;
    private boolean getMethod=false;
    private ResultListener resultListener;
    public CushyBase(HashMap<String, Object> settingMap, HashMap<String, String> headerSetting,Object post,ResultListener resultListener) {
        this.settingMap = settingMap;
        this.headerSetting = headerSetting;
        this.post = post;
        this.resultListener = resultListener;
    }

    @Override
    protected Response doInBackground(Void... voids) {
        setGetMethod();
        try {
            HttpURLConnection urlConnection =startConnection();
            if (urlConnection != null) {
                urlConnection.connect();
            } else {
                return new Response("url 주소를 확인해주세요",HttpURLConnection.HTTP_NOT_FOUND,"","");
            }
            if (!getMethod){
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                bufferedWriter.write(getPostData());
                bufferedWriter.flush();
                bufferedWriter.close();
            }
            int code =urlConnection.getResponseCode();
            if (code==HttpURLConnection.HTTP_OK){
                InputStream is = urlConnection.getInputStream();

                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8")); //문자열 셋 세팅
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                String cookie = urlConnection.getHeaderField("Set-Cookie");
                return new Response(builder.toString(),urlConnection.getResponseCode(),urlConnection.getContentType(),cookie);
            } else {
                return new Response(urlConnection.getResponseMessage(),urlConnection.getResponseCode(),urlConnection.getContentType(),"");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Response(e.getMessage(),HttpURLConnection.HTTP_NOT_FOUND,"","");
        }
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        resultListener.sendResult(response);
    }

    private void setGetMethod(){
        if (settingMap.get("method").equals(CushyField.POST)){
            getMethod = false;
        } else {
            getMethod =true;
        }
    }
    private HttpURLConnection startConnection(){
        try {
            URL url = new URL((String) settingMap.get("url"));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod((String) settingMap.get("method"));
            urlConnection.setDoInput(true);
            setHeader();
            if (!getMethod){
                urlConnection.setDoOutput(true);
            }
            return urlConnection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setHeader(){
        for (String key: headerSetting.keySet()){
            urlConnection.setRequestProperty(key,headerSetting.get(key));
        }
    }

    private String getPostData(){
        Gson gson = new Gson();
        return gson.toJson(post);
    }

}
