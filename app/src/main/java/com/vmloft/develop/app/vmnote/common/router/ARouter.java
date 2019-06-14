package com.vmloft.develop.app.vmnote.common.router;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.vmloft.develop.app.vmnote.ui.editor.EditorActivity;
import com.vmloft.develop.app.vmnote.ui.home.MainActivity;
import com.vmloft.develop.app.vmnote.ui.settings.MeSettingsActivity;
import com.vmloft.develop.app.vmnote.ui.settings.SettingsActivity;
import com.vmloft.develop.app.vmnote.ui.sign.SignActivity;
import com.vmloft.develop.app.vmnote.ui.web.WebActivity;
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
        int flags = Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK;
        forward(context, MainActivity.class, flags);
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
     * 去往用户信息设置界面
     */
    public static void goMeSettings(Context context) {
        overlay(context, MeSettingsActivity.class);
    }

    /**
     * 设置界面
     */
    public static void goSettings(Context context) {
        overlay(context, SettingsActivity.class);
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
