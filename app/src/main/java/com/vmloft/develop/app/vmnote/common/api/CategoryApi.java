package com.vmloft.develop.app.vmnote.common.api;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.bean.BaseResult;
import com.vmloft.develop.app.vmnote.bean.Category;
import com.vmloft.develop.app.vmnote.common.RXUtils;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.library.tools.utils.VMLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lzan13 on 2018/4/20.
 * Category api 接口
 */
public class CategoryApi extends NetApi {

    private static CategoryApi instance;

    CategoryApi() {
        super();
    }

    public static CategoryApi getInstance() {
        if (instance == null) {
            instance = new CategoryApi();
        }
        return instance;
    }

    public ApiService categoryApi() {
        return createApi(ApiService.class);
    }

    /**
     * Create category
     */
    public void createCategory(Category entity, final Callback callback) {
        categoryApi().createCategory(entity.getId(), entity.getTitle())
            .compose(RXUtils.<BaseResult<Category>>threadScheduler())
            .subscribe(new Observer<BaseResult<Category>>() {
                @Override public void onSubscribe(Disposable d) {
                }

                @Override public void onNext(BaseResult<Category> result) {
                    VMLog.d(result.toString());
                    if (result.getCode() == 0) {
                        callback.onDone(result.getData());
                    } else {
                        NetApi.getInstance().parseError(result, callback);
                    }
                }

                @Override public void onError(Throwable e) {
                    NetApi.getInstance().parseThrowable(e, callback);
                }

                @Override public void onComplete() {
                }
            });
    }
}
