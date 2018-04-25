package com.vmloft.develop.app.vmnote.home.presenter;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.home.model.IMainModel;
import com.vmloft.develop.app.vmnote.home.model.MainModelImpl;
import com.vmloft.develop.app.vmnote.home.view.IMainView;

/**
 * Created by lzan13 on 2018/4/17.
 * 主界面逻辑层实现类
 */
public class MainPresenterImpl implements IMainPresenter {

    private IMainView mainView;
    private IMainModel mainModel;

    public MainPresenterImpl(IMainView view) {
        mainView = view;
        mainModel = new MainModelImpl();
    }

    /**
     * 同步处理
     */
    @Override
    public void onSync() {
        mainModel.syncNote(new Callback() {
            @Override
            public void onDone(Object object) {
                mainView.syncDone();
            }

            @Override
            public void onError(int code, String desc) {
                mainView.syncError(code, desc);
            }
        });
    }

}