package com.vmloft.develop.app.vmnote.app;

/**
 * Created by lzan13 on 2018/4/16.
 */

public class VError {
    public static int NO_ERROR = 0;

    // 未知错误
    public static int UNKNOWN = 1;

    // 服务器问题
    public static int SERVER = 100;

    // 网络不可用
    public static int SYS_NETWORK = 101;

    // 超时
    public static int SYS_TIMEOUT = 102;


    /**
     * 这些错误和服务器返回的操作错误码相对应
     */
    public static int INVALID_PARAM = 1002;

    // permission error code
    public static int TOKEN_INVALID = 2000;
    public static int TOKEN_EXPIRED = 2001;
    public static int NOT_PERMISSION = 2002;

    // account error code
    public static int ACCOUNT_EXIST = 3000;
    public static int ACCOUNT_NAME_EXIST = 3001;
    public static int ACCOUNT_NOT_EXIST = 3002;
    public static int ACCOUNT_DELETED = 3003;
    public static int ACCOUNT_NO_ACTIVATED = 3004;
    public static int INVALID_ACTIVATE_LINK = 3005;
    public static int INVALID_PASSWORD = 3006;

}
