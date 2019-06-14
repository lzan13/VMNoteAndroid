package com.vmloft.develop.app.vmnote.common.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.vmloft.develop.library.tools.utils.VMTheme;
import com.vmloft.develop.library.tools.utils.VMUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Create by lzan13 on 2019/04/08
 *
 * 项目工具类
 */
public class AUtils extends VMUtils {

    /**
     * 设置状态栏为黑色图标和文字
     */
    public static void setDarkStatusBar(Activity activity, boolean dark) {
        VMTheme.setDarkStatusBar(activity, dark);
    }
}
