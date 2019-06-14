package com.vmloft.develop.app.vmnote.ui.home.model;

import com.vmloft.develop.app.vmnote.bean.AUser;
import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.common.ASignManager;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.IMainModel;


/**
 * Created by lzan13 on 2018/4/17.
 * 主界面数据处理层实现类
 */
public class MainModelImpl implements IMainModel {

    /**
     * 同步账户信息
     */
    @Override
    public void loadAccount(final ACallback<AUser> callback) {
        AUser user = ASignManager.getInstance().getCurrentUser();
        callback.onSuccess(user);
    }

}
