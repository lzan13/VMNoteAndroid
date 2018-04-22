package com.vmloft.develop.app.vmnote.db;

import com.vmloft.develop.app.vmnote.app.AppApplication;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.db.greendao.AccountDao;
import com.vmloft.develop.app.vmnote.db.greendao.DaoMaster;
import com.vmloft.develop.app.vmnote.db.greendao.DaoSession;

import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/20.
 * 项目数据库操作管理类
 */
public class DBManager {
    private String dbName = "vmnote.db";
    private static DBManager instance;
    private DaoMaster.DevOpenHelper openHelper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;


    private DBManager() {
        openHelper = new DaoMaster.DevOpenHelper(AppApplication.getContext(), dbName);
        daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }


    /**
     * ---------------------- 账户信息相关 ----------------------
     * 获取账户信息 Dao
     */
    private AccountDao getAccountDao() {
        return daoSession.getAccountDao();
    }

    /**
     * 保存账户信息
     */
    public void saveAccount(Account entity) {
        getAccountDao().save(entity);
    }

    /**
     * 删除一条账户信息
     */
    public void deleteAccount(Account entity) {
        getAccountDao().delete(entity);
    }

    /**
     * 更新账户信息
     */
    public void updateAccount(Account entity) {
        getAccountDao().update(entity);
    }

    /**
     * 根据名称查询账户信息
     */
    public Account getAccount(String account) {
        return getAccountDao().queryBuilder()
                .whereOr(AccountDao.Properties.Name.eq(account), AccountDao.Properties.Email.eq(account))
                .list()
                .get(0);
    }

    /**
     * ---------------------- 账户信息相关 ----------------------
     *
     */

}
