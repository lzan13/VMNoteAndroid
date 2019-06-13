package com.vmloft.develop.app.vmnote.common;

/**
 * Created by lzan13 on 2018/4/16.
 */

public class AError {
    public static final int NO_ERROR = 0;

    // 未知错误码
    public static final int UNKNOWN = -1;

    // 服务器问题
    public static final int SERVER = 100;

    // 网络不可用
    public static final int SYS_NETWORK = 101;

    // 超时
    public static final int SYS_TIMEOUT = 102;


    /**
     * 这些错误和服务器返回的操作错误码相对应
     */
    public static final int INVALID_PARAM = 1002;

    // permission error code
    public static final int TOKEN_INVALID = 2000;
    public static final int TOKEN_EXPIRED = 2001;
    public static final int NOT_PERMISSION = 2002;

    // account error code
    public static final int ACCOUNT_EXIST = 3000;
    public static final int ACCOUNT_NAME_EXIST = 3001;
    public static final int ACCOUNT_NOT_EXIST = 3002;
    public static final int ACCOUNT_DELETED = 3003;
    public static final int ACCOUNT_NO_ACTIVATED = 3004;
    public static final int INVALID_ACTIVATE_LINK = 3005;
    public static final int INVALID_PASSWORD = 3006;


    /**
     * 账户相关错误码
     */
    // 用户名已被占用
    public static final int USER_ALREADY_EXIST = 100;
    // 邮箱已被使用
    public static final int EMAIL_ALREADY_EXIST = 101;
    // 手机号已被占用
    public static final int PHONE_ALREADY_EXIST = 102;
    // 用户名密码不匹配
    public static final int USERNAME_PASSWORD_MISMATCH = 103;
    // 用户不存在
    public static final int USER_DOESNOT_EXIST = 104;

}
