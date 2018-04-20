package com.vmloft.develop.app.vmnote.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.vmloft.develop.app.vmnote.home.MainActivity;
import com.vmloft.develop.app.vmnote.sign.SignActivity;

/**
 * Created by lzan13 on 2017/11/23.
 *
 * Activity 导航管理类，app 内所有的界面跳转都由此类进行路由
 */
public class NavManager {

    public static void goMain(Context context) {
        forward(context, MainActivity.class);
    }


    public static void goSignIn(Context context) {
        forward(context, SignActivity.class);
    }

    public static void goSignUp(Context context) {

    }

    /**
     * 向前跳转
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

    private static boolean isActivity(Context context) {
        if (context instanceof Activity) {
            return true;
        }
        return false;
    }

}
