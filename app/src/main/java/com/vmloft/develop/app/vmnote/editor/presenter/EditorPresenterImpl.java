package com.vmloft.develop.app.vmnote.editor.presenter;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.editor.model.IEditorModel;
import com.vmloft.develop.app.vmnote.editor.model.EditorModelImpl;
import com.vmloft.develop.app.vmnote.editor.view.IEditorView;
import com.vmloft.develop.library.tools.utils.VMStrUtil;

/**
 * Created by lzan13 on 2018/4/24.
 * Note 逻辑操作实现类
 */
public class EditorPresenterImpl implements IEditorPresenter {
    private IEditorView noteView;
    private IEditorModel noteModel;

    public EditorPresenterImpl(IEditorView view) {
        noteView = view;
        init();
    }

    private void init() {
        noteModel = new EditorModelImpl();
    }

    @Override
    public void onSave(Note entity) {
        noteModel.saveNote(entity, new Callback() {
            @Override
            public void onDone(Object object) {
                noteView.saveNoteDone((Note) object);
            }

            @Override
            public void onError(int code, String desc) {
            }
        });
    }

    @Override
    public void onLoad(String noteId) {
        if (VMStrUtil.isEmpty(noteId)) {
            noteView.loadNoteDone(null);
            return;
        }
        noteModel.loadNote(noteId, new Callback() {
            @Override
            public void onDone(Object object) {
                noteView.loadNoteDone((Note) object);
            }
        });
    }

    @Override
    public void onPreview(Note entity) {

    }
}
