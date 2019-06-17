package com.vmloft.develop.app.vmnote.ui.editor.presenter;

import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.ui.editor.EditorContract.IEditorModel;
import com.vmloft.develop.app.vmnote.ui.editor.EditorContract.IEditorView;
import com.vmloft.develop.app.vmnote.ui.editor.EditorContract.IEditorPresenter;
import com.vmloft.develop.app.vmnote.ui.editor.model.EditorModelImpl;
import com.vmloft.develop.library.tools.utils.VMCrypto;
import com.vmloft.develop.library.tools.utils.VMDate;
import com.vmloft.develop.library.tools.utils.VMStr;

/**
 * Created by lzan13 on 2018/4/24.
 * Note 逻辑操作实现类
 */
public class EditorPresenterImpl extends IEditorPresenter<IEditorView> {

    private IEditorModel noteModel;

    // Note 如果不是新建，会直接获取
    private Note note;

    public EditorPresenterImpl() {
        noteModel = new EditorModelImpl();
    }

    @Override
    public void onTextChanged() {
        note.setIsSync(false);
    }

    /**
     * 保存笔记逻辑处理，这里将数据交给 M 层处理
     */
    @Override
    public void onSaveData(String content) {
        note.setContent(content);
        noteModel.saveData(note, new ACallback() {
            @Override
            public void onSuccess(Object object) {
                obtainView().saveDataDone((Note) object);
            }

            @Override
            public void onError(int code, String desc) {
                obtainView().saveDataDone(null);
            }
        });
    }

    /**
     * 加载数据逻辑处理，如果加载为空，这直接创建
     */
    @Override
    public void onLoadData(String noteId) {
        if (!VMStr.isEmpty(noteId)) {
            note = noteModel.loadData(noteId);
        }
        if (note == null) {
            this.note = new Note(VMCrypto.getObjectId());
            note.setCreateAt(VMDate.currentUTCDateTime());
            note.setUpdateAt(note.getCreateAt());
            note.setIsCreate(true);
            note.setIsSync(false);
        }
        obtainView().loadDataDone(note);
    }
}