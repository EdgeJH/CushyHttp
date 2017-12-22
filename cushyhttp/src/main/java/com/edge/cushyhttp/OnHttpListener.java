package com.edge.cushyhttp;

/**
 * Created by user1 on 2017-12-23.
 */

public interface OnHttpListener <T> {
     void onResonpse(T responseData,int responseCode);
    void onFailure(int code,String message);
}
