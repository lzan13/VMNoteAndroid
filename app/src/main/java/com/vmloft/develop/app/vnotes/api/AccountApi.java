package com.vmloft.develop.app.vnotes.api;

import com.vmloft.develop.app.vnotes.bean.BaseResult;
import com.vmloft.develop.app.vnotes.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lzan13 on 2017/11/24.
 */
public interface AccountApi {

    /**
     * 注册账户
     */
    @FormUrlEncoded
    @POST("accounts/")
    Observable<BaseResult<UserBean>> createAccount(@Field("account") String account, @Field("password") String password);

    /**
     * 认证账户
     */
    @FormUrlEncoded
    @POST("accounts/auth/")
    Observable<BaseResult<UserBean>> authAccount(@Field("account") String account, @Field("password") String password);

    /**
     * 获取账户信息
     */
    @GET("accounts/{name}")
    Observable<BaseResult<UserBean>> getAccount(@Path("name") String name);

}

