package com.vmloft.develop.app.vmnote.ui.editor.model;

import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.ui.editor.EditorContract;

/**
 * Created by lzan13 on 2018/4/24.
 * Note 数据处理实现类
 */
public class EditorModelImpl implements EditorContract.IEditorModel {

    @Override
    public void saveData(final Note entity, ACallback callback) {
//        NoteApi.getInstance().postNote(entity, new ACallback() {
//            @Override
//            public void onDone(Object object) {
//                // 上传到服务器成功，更新本地
//                Note note = (Note) object;
//                note.setIsSync(true);
//                note.setIsCreate(false);
//                DBManager.getInstance().insertNote(note);
//            }
//
//            @Override
//            public void onError(int code, String desc) {
//                // 上传到服务器失败，只保存本地，并置为未同步
//                entity.setIsSync(false);
//                DBManager.getInstance().insertNote(entity);
//            }
//        });
    }

    @Override
    public Note loadData(String noteId) {
//        return DBManager.getInstance().getNoteById(noteId);
        return null;
    }
}
