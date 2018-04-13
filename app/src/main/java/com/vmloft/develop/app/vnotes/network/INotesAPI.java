package com.vmloft.develop.app.vnotes.network;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lzan13 on 2017/11/24.
 */
public interface INotesAPI {

    @POST("accounts/")
    Call<ResponseBody> createAccount(@Body RequestBody body);

    @GET("accounts/{name}")
    Call<ResponseBody> getAccount(@Path("name") String name);

}

