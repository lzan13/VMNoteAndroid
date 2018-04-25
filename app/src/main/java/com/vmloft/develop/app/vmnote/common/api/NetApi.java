package com.vmloft.develop.app.vmnote.common.api;

import android.text.TextUtils;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.app.VError;
import com.vmloft.develop.app.vmnote.bean.BaseResult;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMStrUtil;
import com.vmloft.develop.library.tools.widget.VMToast;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lzan13 on 2017/11/24.
 * 网络请求接口管理类
 */
public class NetApi {

    private static NetApi instance;

    private String baseUrl = "http://vmnote.melove.net/api/v1/";
    private OkHttpClient client;
    private Retrofit retrofit;
    private ApiService apiService;

    /**
     * 获取单例类实例
     */
    public static NetApi getInstance() {
        if (instance == null) {
            instance = new NetApi();
        }
        return instance;
    }

    /**
     * 私有化实例方法
     */
    NetApi() {
        // 实例化 OkHttpClient，如果不自己创建 Retrofit 也会创建一个默认的
        client = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new RequestInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        // 实例化 Retrofit
        retrofit = new Retrofit.Builder().client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 创建 Retrofit 接口实例
        apiService = createApi(ApiService.class);
    }

    /**
     * 为 POST 请求添加公共参数
     */
    private Request addPostFromParams(Request request) {
        FormBody.Builder builder = new FormBody.Builder();
        FormBody formBody = (FormBody) request.body();
        for (int i = 0; i < formBody.size(); i++) {
            builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
        }
        builder.addEncoded("device", "lz_mi5").build();
        return request.newBuilder().post(builder.build()).build();
    }

    /**
     * 为 POST 请求添加公共参数，多参数情况（参数包含文件时）
     */
    private Request addPostMultipartParams(Request request) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("device", "lz_mi5");
        MultipartBody body = (MultipartBody) request.body();
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }
        return request.newBuilder().post(builder.build()).build();
    }

    /**
     * 为 GET 请求添加公共参数
     */
    private Request addGetParams(Request request) {
        HttpUrl.Builder builder = request.url().newBuilder();
        builder.addQueryParameter("device", "lz_mi5");
        return request.newBuilder().url(builder.build()).build();
    }

    /**
     * 自定义拦截器，用户添加公共参数操作
     */
    private class RequestInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (request.method().equals("POST")) {
                // POST 请求有两种请求体形式 1.FormBody:表单形式，2.MultipartBody:多参数表单（包含文件）
                if (request.body() instanceof FormBody) {
                    addPostFromParams(request);
                } else if (request.body() instanceof MultipartBody) {
                    addPostMultipartParams(request);
                }
            } else if (request.method().equals("GET")) {
                addGetParams(request);
            }
            // 通过拦截器添加 token
            String token = SPManager.getInstance().getToken();
            if (!TextUtils.isEmpty(token)) {
                request = request.newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
            }
            return chain.proceed(request);
        }
    }

    /**
     * 根据 API 接口类名创建指定的 Retrofit
     */
    public <T> T createApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    public ApiService accountApi() {
        return createApi(ApiService.class);
    }

    public ApiService noteApi() {
        return createApi(ApiService.class);
    }

    /**
     * 请求结果错误的情况处理
     *
     * @param result 请求结果，包含错误信息
     * @param callback 回调
     */
    public void parseError(BaseResult result, Callback callback) {
        String msg = result.getMessage();
        int code = result.getCode();
        switch (code) {
        case VError.UNKNOWN:
            break;
        case VError.SERVER:
            break;
        case VError.SYS_NETWORK:
            break;
        case VError.SYS_TIMEOUT:
            break;
        case VError.INVALID_PARAM:
            break;
        case VError.TOKEN_INVALID:
            break;
        case VError.TOKEN_EXPIRED:
            break;
        case VError.NOT_PERMISSION:
            break;
        case VError.ACCOUNT_EXIST:
            break;
        case VError.ACCOUNT_NAME_EXIST:
            break;
        case VError.ACCOUNT_NOT_EXIST:
            break;
        case VError.ACCOUNT_DELETED:
            break;
        case VError.ACCOUNT_NO_ACTIVATED:
            break;
        case VError.INVALID_ACTIVATE_LINK:
            break;
        case VError.INVALID_PASSWORD:
            break;
        default:
            break;
        }
        VMLog.e(msg);
        VMToast.make(msg).showError();
        callback.onError(code, msg);
    }

    /**
     * 请求出现异常错误处理
     *
     * @param e 异常
     * @param callback 回调
     */
    public void parseThrowable(Throwable e, Callback callback) {
        int code;
        String msg = null;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            code = httpException.code();
            if (code == 500 || code == 404) {
                code = VError.SERVER;
                msg = VMStrUtil.strByResId(R.string.err_server);
            }
        } else if (e instanceof ConnectException) {
            code = VError.SYS_NETWORK;
            msg = VMStrUtil.strByResId(R.string.err_network_unusable);
        } else if (e instanceof SocketTimeoutException) {
            code = VError.SYS_TIMEOUT;
            msg = VMStrUtil.strByResId(R.string.err_network_timeout);
        } else {
            code = VError.UNKNOWN;
            msg = VMStrUtil.strByResId(R.string.err_unknown) + e.getMessage();
        }
        VMLog.e(msg);
        VMToast.make(msg).showError();
        callback.onError(code, msg);
    }

}

