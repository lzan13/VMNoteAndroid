package com.vmloft.develop.app.vmnote.common.api;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.bean.BaseResult;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.app.vmnote.common.RXUtils;
import com.vmloft.develop.library.tools.utils.VMLog;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lzan13 on 2018/4/20.
 * 账户相关 api 接口
 */
public class NoteApi extends NetApi {

    private static NoteApi instance;

    NoteApi() {
        super();
    }

    public static NoteApi getInstance() {
        if (instance == null) {
            instance = new NoteApi();
        }
        return instance;
    }

    /**
     * --------------------------------- 笔记相关接口 ---------------------------------
     * 发布笔记
     */
    public void postNote(final Note entity, final Callback callback) {
        NetApi.getInstance()
                .noteApi()
                .postNote(entity.getId(), entity.getContent())
                .compose(RXUtils.<BaseResult<Note>>threadScheduler())
                .subscribe(new Observer<BaseResult<Note>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<Note> result) {
                        VMLog.d(result.toString());
                        if (result.getCode() == 0) {
                            Note note = result.getData();
                            note.setIsSync(true);
                            note.setIsCreate(false);
                            DBManager.getInstance().insertNote(note);
                            callback.onDone(note);
                        } else {
                            // 上传到服务器失败，只保存本地，并置为未同步
                            entity.setIsSync(false);
                            entity.setIsCreate(true);
                            DBManager.getInstance().insertNote(entity);
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
     * 更新笔记
     */
    public void updateNoteContent(final Note entity, final Callback callback) {
        NetApi.getInstance()
                .noteApi()
                .updateNote(entity.getId(), entity.getContent())
                .compose(RXUtils.<BaseResult<Note>>threadScheduler())
                .subscribe(new Observer<BaseResult<Note>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<Note> result) {
                        VMLog.d(result.toString());
                        if (result.getCode() == 0) {
                            Note note = result.getData();
                            note.setIsSync(true);
                            DBManager.getInstance().updateNote(note);
                            callback.onDone(note);
                        } else {
                            // 上传到服务器失败，只保存本地，并置为未同步
                            entity.setIsSync(true);
                            DBManager.getInstance().updateNote(entity);
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
     * 更新笔记
     */
    public void updateNote(final Note entity, final Callback callback) {
        NetApi.getInstance()
                .noteApi()
                .updateNote(entity.getId(), entity)
                .compose(RXUtils.<BaseResult<Note>>threadScheduler())
                .subscribe(new Observer<BaseResult<Note>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<Note> result) {
                        VMLog.d(result.toString());
                        if (result.getCode() == 0) {
                            Note note = result.getData();
                            note.setIsSync(true);
                            DBManager.getInstance().updateNote(note);
                            callback.onDone(note);
                        } else {
                            // 上传到服务器失败，只保存本地，并置为未同步
                            entity.setIsSync(true);
                            DBManager.getInstance().updateNote(entity);
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
     * 获取全部笔记，客户端暂时不需要此接口，因为要实现增量更新，有单独的同步接口
     */
    public void getAllNote(final Callback callback) {
        NetApi.getInstance()
                .noteApi()
                .getAllNote()
                .compose(RXUtils.<BaseResult<List<Note>>>threadScheduler())
                .subscribe(new Observer<BaseResult<List<Note>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<List<Note>> result) {
                        VMLog.d(result.toString());
                        if (result.getCode() == 0) {
                            List<Note> list = result.getData();
                            DBManager.getInstance().insertNoteList(list);
                            callback.onDone(list);
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
     * 获取笔记数量，客户端暂时用不到此接口
     */
    //    @GET("/notes/count")
    //    public void getAllNote();

    /**
     * 同步笔记，考虑到客户端流量问题，这里设计成增量更新方式
     */
    public void syncNote(int page, int limit, Long time, final Callback callback) {
        NetApi.getInstance()
                .noteApi()
                .syncNote(page, limit, time)
                .compose(RXUtils.<BaseResult<List<Note>>>threadScheduler())
                .subscribe(new Observer<BaseResult<List<Note>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<List<Note>> result) {
                        VMLog.d(result.toString());
                        if (result.getCode() == 0) {
                            callback.onDone(result);
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
     * --------------------------------- 回收站及删除相关接口 ---------------------------------
     * 将笔记移到回收站
     */
    public void addNoteToTrash(final Note entity, final Callback callback) {
        NetApi.getInstance()
                .noteApi()
                .addNoteToTrash(entity.getId())
                .compose(RXUtils.<BaseResult<Note>>threadScheduler())
                .subscribe(new Observer<BaseResult<Note>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<Note> result) {
                        VMLog.d(result.toString());
                        if (result.getCode() == 0) {
                            Note note = result.getData();
                            note.setIsSync(true);
                            DBManager.getInstance().updateNote(note);
                            callback.onDone(note);
                        } else {
                            entity.setIsSync(false);
                            entity.setDeleted(true);
                            DBManager.getInstance().updateNote(entity);
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
     * 将笔记从回收站还原
     */
    public void restoreNoteFromTrash(final Note entity, final Callback callback) {
        NetApi.getInstance()
                .noteApi()
                .restoreNoteFromTrash(entity.getId())
                .compose(RXUtils.<BaseResult<Note>>threadScheduler())
                .subscribe(new Observer<BaseResult<Note>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<Note> result) {
                        VMLog.d(result.toString());
                        if (result.getCode() == 0) {
                            Note note = result.getData();
                            note.setIsSync(false);
                            DBManager.getInstance().updateNote(note);
                            callback.onDone(note);
                        } else {
                            entity.setIsSync(false);
                            entity.setDeleted(false);
                            DBManager.getInstance().updateNote(entity);
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
     * 永远删除笔记
     */
    public void removeNoteForever(final Note entity, final Callback callback) {
        NetApi.getInstance()
                .noteApi()
                .removeNoteForever(entity.getId())
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
                            DBManager.getInstance().updateAccount(account);
                            DBManager.getInstance().deleteNote(entity);
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

                    }
                });
    }

    /**
     * 清空回收站笔记
     */
    public void clearNoteForTrash(final List<Note> list, final Callback callback) {
        NetApi.getInstance()
                .noteApi()
                .clearNoteForTrash()
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
                            DBManager.getInstance().updateAccount(account);
                            DBManager.getInstance().deleteNoteList(list);
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

                    }
                });
    }

    /**
     * 获取回收站笔记
     */
    public void getTrash(int page, int limit, final Callback callback) {
        NetApi.getInstance()
                .noteApi()
                .getTrash(page, limit)
                .compose(RXUtils.<BaseResult<List<Note>>>threadScheduler())
                .subscribe(new Observer<BaseResult<List<Note>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<List<Note>> result) {
                        VMLog.d(result.toString());
                        if (result.getCode() == 0) {
                            List<Note> list = result.getData();
                            DBManager.getInstance().insertNoteList(list);
                            callback.onDone(list);
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


}
