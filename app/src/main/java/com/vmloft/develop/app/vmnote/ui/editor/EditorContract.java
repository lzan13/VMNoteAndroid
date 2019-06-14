package com.vmloft.develop.app.vmnote.ui.editor;

import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.base.APresenter;
import com.vmloft.develop.app.vmnote.bean.Note;

/**
 * Created by lzan13 on 2018/4/26.
 * 笔记编辑 MVP 相关接口契约类
 */
public class EditorContract {


    public interface IEditorModel {

        /**
         * 保存 Note
         */
        void saveData(Note note, ACallback callback);

        /**
         * 加载 Note
         */
        Note loadData(String noteId);
    }

    public interface IEditorView {

        void loadDataDone(Note note);

        void saveDataDone(Note note);
    }

    public abstract static class IEditorPresenter<V> extends APresenter<V> {

        public abstract void onTextChanged();
        /**
         * 保存处理
         */
        public abstract void onSaveData(String content);

        /**
         * 加载
         */
        public abstract void onLoadData(String noteId);

    }


}
