package com.vmloft.develop.app.vmnote.common.router;

import android.content.Context;
import android.os.Parcelable;

import com.vmloft.develop.app.vmnote.account.AccountActivity;
import com.vmloft.develop.app.vmnote.editor.EditorActivity;
import com.vmloft.develop.app.vmnote.home.MainActivity;
import com.vmloft.develop.app.vmnote.sign.SignActivity;
import com.vmloft.develop.app.vmnote.webpage.WebActivity;
import com.vmloft.develop.library.tools.router.VMRouter;

/**
 * Created by lzan13 on 2018/4/24.
 * 项目跳转导航路由器
 */
public class ARouter extends VMRouter {

    /**
     * 主界面
     */
    public static void goMain(Context context) {
        forward(context, MainActivity.class);
    }

    /**
     * 去往注册登录界面
     */
    public static void goSign(Context context) {
        forward(context, SignActivity.class);
    }

    /**
     * 去往笔记编辑界面
     */
    public static void goEditor(Context context, Parcelable params) {
        overlay(context, EditorActivity.class, params);
    }

    /**
     * 去往用户信息界面
     */
    public static void goAccount(Context context) {
        overlay(context, AccountActivity.class);
    }


    /**
     * ------------------- 公共跳转界面 -------------------
     *
     * 处理网页
     */
    public static void goWebPage(Context context, Parcelable params) {
        overlay(context, WebActivity.class, params);
    }
}
