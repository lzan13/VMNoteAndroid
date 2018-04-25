package com.vmloft.develop.app.vmnote.common.db;

import com.vmloft.develop.app.vmnote.app.AppApplication;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.db.greendao.AccountDao;
import com.vmloft.develop.app.vmnote.common.db.greendao.DaoMaster;
import com.vmloft.develop.app.vmnote.common.db.greendao.DaoSession;
import com.vmloft.develop.app.vmnote.common.db.greendao.NoteDao;

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
    public void insertAccount(Account entity) {
        getAccountDao().insertOrReplace(entity);
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
        List<Account> list = getAccountDao().queryBuilder()
                .whereOr(AccountDao.Properties.Name.eq(account), AccountDao.Properties.Email.eq(account))
                .list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * ---------------------- 笔记相关 ----------------------
     */
    private NoteDao getNoteDao() {
        return daoSession.getNoteDao();
    }

    /**
     * 保存笔记
     */
    public void insertNote(Note entity) {
        getNoteDao().insertOrReplace(entity);
    }

    /**
     * 保存笔记集合
     */
    public void insertNoteList(List<Note> noteList) {
        getNoteDao().insertOrReplaceInTx(noteList);
    }

    /**
     * 删除笔记
     */
    public void deleteNote(Note entity) {
        getNoteDao().delete(entity);
    }


    /**
     * 删除多条笔记
     */
    public void deleteNoteList(List<Note> list) {
        getNoteDao().deleteInTx(list);
    }

    /**
     * 更新笔记
     */
    public void updateNote(Note entity) {
        getNoteDao().update(entity);
    }

    /**
     * 根据笔记 id 获取单条笔记
     */
    public Note getNoteById(String id) {
        return getNoteDao().load(id);
    }

    //    public List<Note> getNoteList() {
    //        return getNoteDao().queryBuilder().where();
    //    }

    /**
     * 获取所有笔记
     */
    public List<Note> loadAllNote() {
        return getNoteDao().loadAll();
    }


}
