package com.vmloft.develop.app.vmnote.common.api;

import com.vmloft.develop.app.vmnote.bean.BaseResult;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.bean.Category;
import com.vmloft.develop.app.vmnote.bean.Note;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lzan13 on 2017/11/24.
 * 项目 Api 接口服务类
 */
public interface ApiService {


    /**
     * --------------------------------- Account Api ---------------------------------
     */
    /**
     * 注册账户
     */
    @FormUrlEncoded
    @POST("accounts")
    Observable<BaseResult<Account>> createAccount(@Field("account") String account,
            @Field("password") String password);

    /**
     * 认证账户
     */
    @FormUrlEncoded
    @POST("accounts/auth")
    Observable<BaseResult<Account>> authAccount(@Field("account") String account,
            @Field("password") String password);

    /**
     * 获取账户信息
     */
    @GET("accounts/{id}/info")
    Observable<BaseResult<Account>> getAccount(@Path("id") String id);

    /**
     * --------------------------------- Note Api ---------------------------------
     * 发布新笔记
     */
    @FormUrlEncoded
    @POST("notes")
    Observable<BaseResult<Note>> createNote(@Field("id") String id,
            @Field("content") String content);

    /**
     * 更新笔记
     */
    @PUT("notes/{id}")
    Observable<BaseResult<Note>> updateNote(@Path("id") String id, @Body() Note note);

    /**
     * 获取全部笔记，客户端暂时不需要此接口，因为要实现增量更新，有单独的同步接口
     */
    @GET("notes")
    Observable<BaseResult<List<Note>>> getAllNote();

    /**
     * 获取笔记数量，客户端暂时用不到此接口
     */
    //    @GET("notes/count")
    //    Observable<BaseResult<Note>> getAllNote();

    /**
     * 同步笔记，考虑到客户端流量问题，这里设计成增量更新方式
     */
    @GET("notes/sync")
    Observable<BaseResult<List<Note>>> syncNote(@Query("limit") int limit,
            @Query("syncKey") Long time);

    /**
     * --------------------------------- Trash Api ---------------------------------
     * 将笔记移到回收站
     */
    @POST("trash/add/{id}")
    Observable<BaseResult<Note>> addNoteToTrash(@Path("id") String id);

    /**
     * 将笔记从回收站还原
     */
    @POST("trash/restore/{id}")
    Observable<BaseResult<Note>> restoreNoteFromTrash(@Path("id") String id);

    /**
     * 永远删除笔记
     */
    @DELETE("trash/remove/{id}")
    Observable<BaseResult<Account>> removeNoteForever(@Path("id") String id);

    /**
     * 清空回收站笔记
     */
    @DELETE("trash/clear")
    Observable<BaseResult<Account>> clearNoteForTrash();

    /**
     * 获取回收站笔记
     */
    @GET("trash")
    Observable<BaseResult<List<Note>>> getTrash(@Query("page") int page, @Query("limit") int limit);


    /**
     * --------------------------------- Category Api ---------------------------------
     */

    /**
     * Create category
     */
    @FormUrlEncoded
    @POST("categorys")
    Observable<BaseResult<Category>> createCategory(@Field("id") String id,
            @Field("title") String title);

    /**
     * Update category
     */
    @FormUrlEncoded
    @POST("categorys/{id}")
    Observable<BaseResult<Category>> updateCategory(@Path("id") String id,
            @Field("title") String title);

    /**
     * Delete category
     */
    @DELETE("categorys/{id}")
    Observable<BaseResult> deleteCategory(@Path("id") String id);


    /**
     * Get all category
     */
    @DELETE("categorys}")
    Observable<BaseResult<List<Category>>> getAllCategory();

    /**
     * Delete category
     */
    @DELETE("categorys/{id}")
    Observable<BaseResult<Category>> getCategoryById(@Path("id") String id);


}

