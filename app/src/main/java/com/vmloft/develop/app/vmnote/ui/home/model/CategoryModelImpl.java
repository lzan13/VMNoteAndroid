package com.vmloft.develop.app.vmnote.ui.home.model;

import com.vmloft.develop.app.vmnote.bean.Category;
import com.vmloft.develop.app.vmnote.ui.home.MainContract;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/25.
 * 笔记列表数据操作实现类
 */
public class CategoryModelImpl implements MainContract.ICategoryModel {

    /**
     * 加载笔记列表
     */
    @Override
    public List<Category> loadAllCategory() {
//        return DBManager.getInstance().loadAllCategory();
        return null;
    }
}
