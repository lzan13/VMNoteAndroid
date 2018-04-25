package com.vmloft.develop.app.vmnote.editor.presenter;

import com.vmloft.develop.app.vmnote.bean.Note;

/**
 * Created by lzan13 on 2018/4/24.
 * Note 逻辑处理接口
 */
public interface IEditorPresenter {

    /**
     * 保存处理
     */
    void onSave(Note entity);

    /**
     * 加载
     */
    void onLoad(String noteId);

    /**
     * 预览
     */
    void onPreview(Note entity);
}
