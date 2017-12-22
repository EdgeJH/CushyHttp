package com.edge.cushyhttp;

/**
 * Created by user1 on 2017-12-23.
 */

public class Response {
    String data;
    int code;
    String contentType;
    String cookie;

    public Response(String data, int code, String contentType,String cookie) {
        this.data = data;
        this.code = code;
        this.cookie = cookie;
        this.contentType = contentType;
    }

    public String getCookie() {
        return cookie;
    }

    public String getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getContentType() {
        return contentType;
    }
}
