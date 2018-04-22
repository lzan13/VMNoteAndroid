package com.vmloft.develop.app.vmnote.api;

import com.vmloft.develop.app.vmnote.bean.BaseResult;
import com.vmloft.develop.app.vmnote.bean.Account;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lzan13 on 2017/11/24.
 * 项目 Api 接口服务类
 */
public interface ApiService {


    /**
     * --------------------------------- 账户相关接口 ---------------------------------
     */
    /**
     * 注册账户
     */
    @FormUrlEncoded
    @POST("accounts/")
    Observable<BaseResult<Account>> createAccount(@Field("account") String account, @Field("password") String password);

    /**
     * 认证账户
     */
    @FormUrlEncoded
    @POST("accounts/auth/")
    Observable<BaseResult<Account>> authAccount(@Field("account") String account, @Field("password") String password);

    /**
     * 获取账户信息
     */
    @GET("accounts/{name}")
    Observable<BaseResult<Account>> getAccount(@Path("name") String name);

}

