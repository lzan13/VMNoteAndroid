package com.vmloft.develop.app.vmnote.home.presenter;

import com.vmloft.develop.app.vmnote.bean.AUser;
import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.common.ASignManager;
import com.vmloft.develop.app.vmnote.home.MainContract;
import com.vmloft.develop.app.vmnote.home.model.MainModelImpl;
import com.vmloft.develop.app.vmnote.home.MainContract.IMainView;
import com.vmloft.develop.app.vmnote.home.MainContract.IMainPresenter;

/**
 * Created by lzan13 on 2018/4/17.
 * 主界面逻辑层实现类
 */
public class MainPresenterImpl extends IMainPresenter<IMainView> {

    private MainContract.IMainModel mMainModel;

    public MainPresenterImpl() {
        mMainModel = new MainModelImpl();
    }

    @Override public void onLoadAccount() {
        mMainModel.loadAccount(new ACallback<AUser>(){
            @Override
            public void onSuccess(AUser user) {
                obtainView().loadAccountDone(user);
            }
        });
    }
}