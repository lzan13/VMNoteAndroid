package com.vmloft.develop.app.vmnote.editor.model;

import com.vmloft.develop.app.vmnote.common.api.NoteApi;
import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.db.DBManager;

/**
 * Created by lzan13 on 2018/4/24.
 * Note 数据处理实现类
 */
public class EditorModelImpl implements IEditorModel {

    @Override
    public void saveNote(Note entity, Callback callback) {
        NoteApi.getInstance().postNote(entity, callback);
    }

    @Override
    public void loadNote(String noteId, Callback callback) {
        Note note = DBManager.getInstance().getNoteById(noteId);
        callback.onDone(note);
    }
}
