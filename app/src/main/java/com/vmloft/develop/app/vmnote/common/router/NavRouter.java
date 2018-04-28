package com.vmloft.develop.app.vmnote.common.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.vmloft.develop.app.vmnote.account.AccountActivity;
import com.vmloft.develop.app.vmnote.app.C;
import com.vmloft.develop.app.vmnote.editor.EditorActivity;
import com.vmloft.develop.app.vmnote.home.MainActivity;
import com.vmloft.develop.app.vmnote.sign.SignActivity;
import com.vmloft.develop.app.vmnote.webpage.WebActivity;

/**
 * Created by lzan13 on 2018/4/24.
 * 项目跳转导航路由器
 */
public class NavRouter {

    /**
     * 回到手机桌面
     */
    public static void goHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

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
    public static void goEditor(Context context, NavParams params) {
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
    public static void goWebPage(Context context, Parcelable parcelable) {
        overlay(context, WebActivity.class, parcelable);
    }

    /**
     * ------------------- 正常跳转，直接跳到下一个界面，当前界面处于 stop 状态 -------------------
     *
     * 最普通的跳转
     *
     * @param context 开始界面上下文
     * @param target 目标界面
     */
    private static void overlay(Context context, Class<? extends Activity> target) {
        Intent intent = new Intent(context, target);
        context.startActivity(intent);
        context = null;
    }

    /**
     * 带有可序列化参数跳转
     *
     * @param context 开始界面上下文
     * @param target 目标界面
     */
    private static void overlay(Context context, Class<? extends Activity> target,
            Parcelable parcelable) {
        Intent intent = new Intent(context, target);
        putParcelableExtra(intent, parcelable);
        context.startActivity(intent);
        context = null;
    }

    /**
     * 带有 flags
     *
     * @param context 开始界面上下文
     * @param target 目标界面
     * @param flags 条件
     */
    private static void overlay(Context context, Class<? extends Activity> target, int flags) {
        Intent intent = new Intent(context, target);
        intent.setFlags(flags);
        context.startActivity(intent);
        context = null;
    }

    /**
     * 带有可序列化参数，以及 flags
     *
     * @param context 开始界面上下文
     * @param target 目标界面
     */
    private static void overlay(Context context, Class<? extends Activity> target, int flags,
            Parcelable parcelable) {
        Intent intent = new Intent(context, target);
        putParcelableExtra(intent, parcelable);
        context.startActivity(intent);
        context = null;
    }

    /**
     * ---------------------------- 向前跳转，跳转结束会 finish 当前界面 ----------------------------
     *
     * 最普通的跳转
     *
     * @param context 上下文对象
     * @param targetClass 目标
     */
    private static void forward(Context context, Class<? extends Activity> targetClass) {
        Intent intent = new Intent(context, targetClass);
        context.startActivity(intent);
        if (isActivity(context)) {
            ((Activity) context).finish();
            context = null;
        }
    }

    /**
     * 带有序列化参数的跳转
     *
     * @param context 上下文对象
     * @param target 目标
     */
    private static void forward(Context context, Class<? extends Activity> target,
            Parcelable parcelable) {
        Intent intent = new Intent(context, target);
        putParcelableExtra(intent, parcelable);
        context.startActivity(intent);
        if (isActivity(context)) {
            ((Activity) context).finish();
            context = null;
        }
    }

    /**
     * 带有 flag 的跳转
     *
     * @param context 上下文对象
     * @param target 目标
     * @param flags 条件
     */
    private static void forward(Context context, Class<? extends Activity> target, int flags) {
        Intent intent = new Intent(context, target);
        setFlags(intent, flags);
        context.startActivity(intent);
        if (isActivity(context)) {
            ((Activity) context).finish();
            context = null;
        }
    }

    /**
     * 带有 flag 和序列化参数的跳转
     *
     * @param context 上下文对象
     * @param target 目标
     * @param flags 条件
     */
    private static void forward(Context context, Class<? extends Activity> target, int flags,
            Parcelable parcelable) {
        Intent intent = new Intent(context, target);
        setFlags(intent, flags);
        putParcelableExtra(intent, parcelable);
        context.startActivity(intent);
        if (isActivity(context)) {
            ((Activity) context).finish();
            context = null;
        }
    }

    /**
     * 获取序列化的参数
     */
    public static NavParams getParcelableExtra(Activity activity) {
        Parcelable parcelable = activity.getIntent().getParcelableExtra(C.NAV_EXT);
        return (NavParams) parcelable;
    }

    /**
     * 添加可序列化的参数对象
     */
    private static void putParcelableExtra(Intent intent, Parcelable parcelable) {
        if (intent == null) {
            return;
        }
        intent.putExtra(C.NAV_EXT, parcelable);
    }

    /**
     * 设置标记
     */
    private static void setFlags(Intent intent, int flags) {
        if (flags < 0) {
            return;
        }
        intent.setFlags(flags);
    }

    /**
     * 判断当前上下文是不是 activity
     *
     * @param context 上下文
     * @return
     */
    private static boolean isActivity(Context context) {
        if (context instanceof Activity) {
            return true;
        }
        return false;
    }
}
