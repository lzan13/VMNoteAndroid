package com.vmloft.develop.app.vmnote.home.presenter;

import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.home.MainContract.ITrashModel;
import com.vmloft.develop.app.vmnote.home.MainContract.ITrashView;
import com.vmloft.develop.app.vmnote.home.MainContract.ITrashPresenter;
import com.vmloft.develop.app.vmnote.home.model.TrashModelImpl;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/28.
 */

public class TrashPresenterImpl extends ITrashPresenter<ITrashView> {

    ITrashModel trashModel;

    public TrashPresenterImpl() {
        trashModel = new TrashModelImpl();
    }

    @Override
    public void onLoadTrashNote() {
        List<Note> list = trashModel.loadTrashNote();
        obtainView().loadTrashNoteDone(list);
    }
}
