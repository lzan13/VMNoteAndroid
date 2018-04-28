package com.vmloft.develop.app.vmnote.home.model;

import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.app.vmnote.home.MainContract;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/25.
 * 笔记列表数据操作实现类
 */
public class DisplayModelImpl implements MainContract.IDisplayModel {

    /**
     * 加载笔记列表
     */
    @Override
    public List<Note> loadAllNote() {
        return DBManager.getInstance().loadAllNote();
    }
}
