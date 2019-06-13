package com.vmloft.develop.app.vmnote.home.model;

import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.home.MainContract;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/28.
 */
public class TrashModelImpl implements MainContract.ITrashModel {

    @Override
    public List<Note> loadTrashNote() {
//        return DBManager.getInstance().loadTrashNote();
        return null;
    }
}
