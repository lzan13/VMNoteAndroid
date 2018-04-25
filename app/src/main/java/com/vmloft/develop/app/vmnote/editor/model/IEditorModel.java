package com.vmloft.develop.app.vmnote.editor.model;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.bean.Note;

/**
 * Created by lzan13 on 2018/4/24.
 * Note 数据操作接口
 */
public interface IEditorModel {

    /**
     * 保存 Note
     */
    void saveNote(Note entity, Callback callback);

    /**
     * 加载 Note
     */
    void loadNote(String noteId, Callback callback);
}
