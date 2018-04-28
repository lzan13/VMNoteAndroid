package com.vmloft.develop.app.vmnote.home.presenter;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.app.vmnote.home.MainContract;
import com.vmloft.develop.app.vmnote.home.model.MainModelImpl;
import com.vmloft.develop.app.vmnote.home.MainContract.IMainView;
import com.vmloft.develop.app.vmnote.home.MainContract.IMainPresenter;

/**
 * Created by lzan13 on 2018/4/17.
 * 主界面逻辑层实现类
 */
public class MainPresenterImpl extends IMainPresenter<IMainView> {

    private MainContract.IMainModel mainModel;

    public MainPresenterImpl() {
        mainModel = new MainModelImpl();
    }

    @Override public void loadAccount() {
        String accountName = SPManager.getInstance().getAccountName();
        obtainView().loadAccountDone(DBManager.getInstance().getAccount(accountName));
    }

    /**
     * 同步处理
     */
    @Override public void syncData() {
        mainModel.syncAccount(new Callback() {
            @Override public void onDone(Object object) {
                loadAccount();
            }
        });
        mainModel.syncData(new Callback() {
            @Override public void onDone(Object object) {
                obtainView().syncDataDone();
            }

            @Override public void onError(int code, String desc) {
                obtainView().syncDataError(code, desc);
            }
        });
    }

    @Override public void syncLocalToServer() {
        mainModel.syncLocalToServer(new Callback() {
            @Override public void onDone(Object object) {
                obtainView().syncDataDone();
            }

            @Override public void onError(int code, String desc) {
                obtainView().syncDataDone();
            }
        });
    }
}