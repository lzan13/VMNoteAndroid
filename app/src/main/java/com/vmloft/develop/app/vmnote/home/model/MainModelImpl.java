package com.vmloft.develop.app.vmnote.home.model;

import com.vmloft.develop.app.vmnote.common.api.AccountApi;
import com.vmloft.develop.app.vmnote.common.api.NoteApi;
import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.bean.BaseResult;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.app.vmnote.home.MainContract.IMainModel;
import com.vmloft.develop.library.tools.utils.VMDate;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/17.
 * 主界面数据处理层实现类
 */
public class MainModelImpl implements IMainModel {

    private int limit = 2;
    private long syncTime = 0l;

    /**
     * 同步账户信息
     */
    @Override public void syncAccount(final Callback callback) {
        String id = SPManager.getInstance().getAccountId();
        AccountApi.getInstance().getAccount(id, new Callback() {
            @Override public void onDone(Object object) {
                callback.onDone(object);
            }

            @Override public void onError(int code, String desc) {
                callback.onError(code, desc);
            }
        });
    }

    @Override public void syncData(Callback callback) {
        String syncKey = SPManager.getInstance().getSyncKey();
        syncTime = VMDate.milliFormUTC(syncKey);
        sync(callback);
    }

    @Override public void syncLocalToServer(Callback callback) {
        List<Note> list = DBManager.getInstance().loadSyncNote();
        NoteApi.getInstance().syncLocalToServer(list, new Callback() {
            @Override public void onDone(Object object) {
                List<Note> syncNotes = (List<Note>) object;
                DBManager.getInstance().insertNoteList(syncNotes);
            }
        });
    }

    /**
     * 增量同步，递归调用
     */
    private void sync(final Callback callback) {
        NoteApi.getInstance().syncNote(limit, syncTime, new Callback() {
            @Override public void onDone(Object object) {
                BaseResult<List<Note>> result = (BaseResult<List<Note>>) object;
                List<Note> list = result.getData();
                if (list.size() > 0) {
                    Note note = list.get(list.size() - 1);
                    DBManager.getInstance().insertNoteList(list);
                    SPManager.getInstance().putSyncKey(note.getUpdateAt());
                    syncTime = VMDate.milliFormUTC(note.getUpdateAt());
                    if (result.getResultCount() < result.getTotalCount() && list.size() == limit) {
                        sync(callback);
                    } else {
                        callback.onDone(list);
                    }
                } else {
                    callback.onDone(list);
                }
            }

            @Override public void onError(int code, String desc) {
                callback.onError(code, desc);
            }
        });
    }
}
