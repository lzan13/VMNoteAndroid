package com.vmloft.develop.app.vmnote.editor.presenter;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.editor.EditorContract.IEditorModel;
import com.vmloft.develop.app.vmnote.editor.EditorContract.IEditorView;
import com.vmloft.develop.app.vmnote.editor.EditorContract.IEditorPresenter;
import com.vmloft.develop.app.vmnote.editor.model.EditorModelImpl;
import com.vmloft.develop.library.tools.utils.VMCryptoUtil;
import com.vmloft.develop.library.tools.utils.VMDateUtil;
import com.vmloft.develop.library.tools.utils.VMStrUtil;

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
    public void onSaveNote(String content) {
        note.setContent(content);
        noteModel.saveNote(note, new Callback() {
            @Override
            public void onDone(Object object) {
                obtainView().saveNoteDone((Note) object);
            }

            @Override
            public void onError(int code, String desc) {
                obtainView().saveNoteDone(null);
            }
        });
    }

    /**
     * 加载数据逻辑处理，如果加载为空，这直接创建
     */
    @Override
    public void onLoadNote(String noteId) {
        if (!VMStrUtil.isEmpty(noteId)) {
            note = noteModel.loadNote(noteId);
        }
        if (note == null) {
            this.note = new Note(VMCryptoUtil.getObjectId());
            note.setCreateAt(VMDateUtil.currentUTCDateTime());
            note.setUpdateAt(note.getCreateAt());
            note.setIsCreate(true);
            note.setIsSync(false);
        }
        obtainView().loadNoteDone(note);
    }

}
