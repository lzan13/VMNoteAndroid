package com.vmloft.develop.app.vnotes.sign.model;

/**
 * Created by lzan13 on 2017/11/23.
 * 登录模块儿接口
 */
public interface ISign {
    /**
     * 保存账户，方便下次直接读取
     *
     * @param account 账户
     */
    void saveAccount(String account);

    /**
     * 获取账户
     */
    String getAccount();

    /**
     * 保存 token
     *
     * @param token 账户登录成功的 token
     */
    void saveToken(String token);
}