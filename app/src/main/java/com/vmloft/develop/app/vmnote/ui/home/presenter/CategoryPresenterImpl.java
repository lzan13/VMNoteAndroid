package com.vmloft.develop.app.vmnote.ui.home.presenter;

import com.vmloft.develop.app.vmnote.bean.Category;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.ICategoryModel;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.ICategoryPresenter;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.ICategoryView;
import com.vmloft.develop.app.vmnote.ui.home.model.CategoryModelImpl;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/25.
 * 笔记分类列表逻辑处理实现类
 */
public class CategoryPresenterImpl extends ICategoryPresenter<ICategoryView> {

    private ICategoryModel categoryModel;

    public CategoryPresenterImpl() {
        categoryModel = new CategoryModelImpl();
    }

    @Override
    public void onLoadAllCategory() {
        List<Category> list = categoryModel.loadAllCategory();
        obtainView().loadCategoryDone(list);
    }
}
