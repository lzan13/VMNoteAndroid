package com.vmloft.develop.app.vmnote.editor.view;

import com.vmloft.develop.app.vmnote.bean.Note;

/**
 * Created by lzan13 on 2018/4/24.
 * Note 相关 UI 接口
 */
public interface IEditorView {

    void loadNoteDone(Note entity);

    void saveNoteDone(Note entity);
}
