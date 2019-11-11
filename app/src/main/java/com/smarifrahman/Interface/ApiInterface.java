package com.smarifrahman.Interface;

import com.smarifrahman.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/json") //Here, `json` is the PATH PARAMETER which will concat with base url
    Call<UserInfo> getMyIp();
}
