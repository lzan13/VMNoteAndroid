package com.vmloft.develop.app.vmnote.home.model;

import com.vmloft.develop.app.vmnote.common.api.NoteApi;
import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.app.SPManager;
import com.vmloft.develop.app.vmnote.bean.BaseResult;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.library.tools.utils.VMDateUtil;
import com.vmloft.develop.library.tools.utils.VMLog;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/17.
 * 主界面数据处理层实现类
 */
public class MainModelImpl implements IMainModel {

    private int page = 1;
    private int limit = 2;
    private long syncTime = 0l;

    @Override
    public void syncNote(Callback callback) {
        String syncKey = SPManager.getInstance().getSyncKey();
        syncTime = VMDateUtil.getMilliFormUTC(syncKey);
        sync(callback);
    }

    /**
     * 增量同步，递归调用
     */
    private void sync(final Callback callback) {
        VMLog.d("Sync note page: %d", page);
        NoteApi.getInstance().syncNote(page, limit, syncTime, new Callback() {
            @Override
            public void onDone(Object object) {
                BaseResult<List<Note>> result = (BaseResult<List<Note>>) object;
                List<Note> list = result.getData();
                if (list.size() > 0) {
                    Note note = list.get(list.size() - 1);
                    DBManager.getInstance().insertNoteList(list);
                    SPManager.getInstance().putSyncKey(note.getUpdateAt());
                    syncTime = VMDateUtil.getMilliFormUTC(note.getUpdateAt());
                    if (result.getResultCount() < result.getTotalCount() && list.size() == limit) {
                        page++;
                        sync(callback);
                    } else {
                        resetSyncConfig();
                        callback.onDone(list);
                    }
                } else {
                    resetSyncConfig();
                    callback.onDone(list);
                }
            }

            @Override
            public void onError(int code, String desc) {
                resetSyncConfig();
                callback.onError(code, desc);
            }
        });
    }

    private void resetSyncConfig() {
        page = 1;
    }

}
