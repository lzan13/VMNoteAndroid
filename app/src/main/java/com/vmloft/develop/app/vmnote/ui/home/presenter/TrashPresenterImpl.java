package com.vmloft.develop.app.vmnote.ui.home.presenter;

import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.ITrashModel;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.ITrashView;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.ITrashPresenter;
import com.vmloft.develop.app.vmnote.ui.home.model.TrashModelImpl;

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
    public void onLoadData() {
        List<Note> list = trashModel.loadData();
        obtainView().loadDataDone(list);
    }


}
