package com.vmloft.develop.app.vmnote.ui.home.model;

import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.ui.home.MainContract;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/28.
 */
public class TrashModelImpl implements MainContract.ITrashModel {

    @Override
    public List<Note> loadData() {
//        return DBManager.getInstance().loadData();
        return null;
    }
}
