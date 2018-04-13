package com.vmloft.develop.app.vnotes.sign.view;

/**
 * Created by lzan13 on 2017/11/23.
 */
public interface ISignView {

    /**
     * 初始化方法
     *
     * @param email
     */
    void init(String email);

    /**
     * 处理结果
     *
     * @param result 处理结果
     * @param code 状态码
     */
    void onSignResult(boolean result, int code);

    /**
     * 更新进度状态
     *
     * @param isShow 是否显示
     */
    void onProgressState(boolean isShow);
}
