package com.vmloft.develop.app.vmnote.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.base.AppMVPFragment;
import com.vmloft.develop.app.vmnote.bean.Category;
import com.vmloft.develop.app.vmnote.home.MainContract.ICategoryPresenter;
import com.vmloft.develop.app.vmnote.home.MainContract.ICategoryView;
import com.vmloft.develop.app.vmnote.home.adapter.CategoryAdapter;
import com.vmloft.develop.app.vmnote.home.presenter.CategoryPresenterImpl;
import com.vmloft.develop.library.tools.adapter.VMEmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzan13 on 2018/4/25.
 * Note 列表展示界面
 */
public class CategoryFragment
    extends AppMVPFragment<ICategoryView, ICategoryPresenter<ICategoryView>>
    implements ICategoryView {

    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private LayoutManager layoutManager;
    private CategoryAdapter adapter;
    private VMEmptyWrapper emptyWrapper;

    private List<Category> categoryList = new ArrayList<>();

    /**
     * 初始化 Fragment 界面 layout_id
     *
     * @return 返回布局 id
     */
    @Override protected int initLayoutId() {
        return R.layout.fragment_note_books;
    }

    @Override public ICategoryPresenter<ICategoryView> createPresenter() {
        return new CategoryPresenterImpl();
    }

    /**
     * 初始化界面控件，将 Fragment 变量和 View 建立起映射关系
     */
    @Override protected void initView() {
        ButterKnife.bind(this, getView());

        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter(activity, categoryList);
        emptyWrapper = new VMEmptyWrapper(adapter);
        emptyWrapper.setEmptyView(R.layout.widget_empty_common_layout);
        recyclerView.setAdapter(emptyWrapper);

        initRefreshListener();

        /**
         * 本身这个方法是为了实现 Fragment 数据的懒加载而自动调用的，
         * 但是 Fragment 没有和 ViewPager 一起使用的情况下，不会执行，所以这里主动调用下
         */
        initData();
    }

    /**
     * 加载数据
     */
    @Override protected void initData() {
        presenter.onLoadAllCategory();
    }

    /**
     * 加载数据完成
     */
    @Override public void loadCategoryDone(List<Category> list) {
        refreshLayout.setRefreshing(false);
        categoryList.clear();
        categoryList.addAll(list);
        refresh();
    }

    private void refresh() {
        if (adapter == null) {
            adapter = new CategoryAdapter(activity, categoryList);
            emptyWrapper = new VMEmptyWrapper(adapter);
            emptyWrapper.setEmptyView(R.layout.widget_empty_common_layout);
            recyclerView.setAdapter(emptyWrapper);
        }
        adapter.refresh();
    }

    /**
     * 设置下拉刷新监听
     */
    private void initRefreshListener() {
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                presenter.onLoadAllCategory();
            }
        });
    }
}
