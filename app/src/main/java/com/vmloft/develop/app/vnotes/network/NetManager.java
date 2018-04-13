package com.vmloft.develop.app.vnotes.network;

import com.vmloft.develop.library.tools.VMCallback;
import com.vmloft.develop.library.tools.utils.VMLog;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lzan13 on 2017/11/24.
 */
public class NetManager {

    private static NetManager instance;

    private String baseUrl = "http://192.168.1.100:52157/api/v1/";
    private Retrofit retrofit;
    private INotesAPI notesAPI;

    private NetManager() {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).build();
        notesAPI = retrofit.create(INotesAPI.class);
    }

    public static NetManager getInstance() {
        if (instance == null) {
            instance = new NetManager();
        }
        return instance;
    }

    /**
     * 创建账户
     *
     * @param object 包含请求参数的
     * @param callback 回调函数
     */
    public void createAccount(JSONObject object, final VMCallback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), object.toString());
        Call<ResponseBody> call = notesAPI.createAccount(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                prateResponse(response, callback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                prateError(t, callback);
            }
        });
    }

    /**
     * 获取账户信息
     *
     * @param account 账户
     * @param callback 回调
     */
    public void getAccount(String account, final VMCallback callback) {
        Call<ResponseBody> call = notesAPI.getAccount(account);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                prateResponse(response, callback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                prateError(t, callback);
            }
        });
    }

    /**
     * 通用的解析请求结果方法
     *
     * @param response 请求结果
     * @param callback 回调函数
     */
    private void prateResponse(Response<ResponseBody> response, VMCallback callback) {
        try {
            if (response.isSuccessful()) {
                VMLog.i("request success: %s", response.body().string());
                callback.onDone(response.body().string());
            } else {
                String errorDesc = response.errorBody().string();
                VMLog.i("request error - %s", errorDesc);
                callback.onError(response.code(), errorDesc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prateError(Throwable t, VMCallback callback) {
        VMLog.i("request failure %s \n t: %s", t.getMessage(), t.toString());
        callback.onError(-1, t.getMessage());
    }

}

