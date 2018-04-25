package com.vmloft.develop.app.vmnote.common.api;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.bean.BaseResult;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.app.vmnote.common.RXUtils;
import com.vmloft.develop.library.tools.utils.VMLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lzan13 on 2018/4/20.
 * 账户相关 api 接口
 */
public class AccountApi extends NetApi {

    private static AccountApi instance;

    AccountApi() {
        super();
    }

    public static AccountApi getInstance() {
        if (instance == null) {
            instance = new AccountApi();
        }
        return instance;
    }

    /**
     * 创建账户
     */
    public void createAccount(Account entity, final Callback callback) {
        SPManager.getInstance().putAccount(entity.getEmail());
        NetApi.getInstance()
                .accountApi()
                .createAccount(entity.getEmail(), entity.getPassword())
                .compose(RXUtils.<BaseResult<Account>>threadScheduler())
                .subscribe(new Observer<BaseResult<Account>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<Account> result) {
                        VMLog.d(result.toString());
                        if (result.getCode() == 0) {
                            callback.onDone(result.getData());
                        } else {
                            NetApi.getInstance().parseError(result, callback);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetApi.getInstance().parseThrowable(e, callback);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 认证账户
     */
    public void authAccount(Account entity, final Callback callback) {
        SPManager.getInstance().putAccount(entity.getEmail());
        NetApi.getInstance()
                .accountApi()
                .authAccount(entity.getEmail(), entity.getPassword())
                .compose(RXUtils.<BaseResult<Account>>threadScheduler())
                .subscribe(new Observer<BaseResult<Account>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<Account> result) {
                        VMLog.d(result.toString());
                        if (result.getCode() == 0) {
                            Account account = result.getData();
                            DBManager.getInstance().insertAccount(account);
                            SPManager.getInstance().putToken(account.getToken());
                            callback.onDone(account);
                        } else {
                            NetApi.getInstance().parseError(result, callback);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetApi.getInstance().parseThrowable(e, callback);
                    }

                    @Override
                    public void onComplete() {
                        VMLog.d("authAccount complete");
                    }
                });
    }
}
