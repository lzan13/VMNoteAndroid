package com.vmloft.develop.app.vmnote.ui.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.base.AppMVPFragment;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.ITrashView;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.ITrashPresenter;
import com.vmloft.develop.app.vmnote.ui.home.presenter.TrashPresenterImpl;
import com.vmloft.develop.app.vmnote.ui.home.adapter.DisplayAdapter;
import com.vmloft.develop.library.tools.adapter.VMEmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lzan13 on 2018/4/25.
 * Note 列表展示界面
 */
public class TrashFragment extends AppMVPFragment<ITrashView, ITrashPresenter<ITrashView>> implements ITrashView {

    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private LayoutManager layoutManager;
    private DisplayAdapter adapter;
    private VMEmptyWrapper emptyWrapper;

    private List<Note> notes = new ArrayList<>();

    /**
     * 初始化 Fragment 界面 layout_id
     *
     * @return 返回布局 id
     */
    @Override protected int layoutId() {
        return R.layout.fragment_note_trash;
    }

    @Override public ITrashPresenter<ITrashView> createPresenter() {
        return new TrashPresenterImpl();
    }

    /**
     * 初始化界面控件，将 Fragment 变量和 View 建立起映射关系
     */
    @Override
    protected void init() {
        super.init();

        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DisplayAdapter(mContext, notes);

        emptyWrapper = new VMEmptyWrapper(adapter);
        emptyWrapper.setEmptyView(R.layout.widget_empty_common_layout);

        recyclerView.setAdapter(emptyWrapper);

        initRefreshListener();

        presenter.onLoadTrashNote();
    }

    /**
     * 加载数据完成
     */
    @Override public void loadTrashNoteDone(List<Note> list) {
        refreshLayout.setRefreshing(false);
        refresh(list);
    }

    private void refresh(List<Note> list) {
        if (adapter == null) {
            adapter = new DisplayAdapter(mContext, notes);
            emptyWrapper = new VMEmptyWrapper(adapter);
            emptyWrapper.setEmptyView(R.layout.widget_empty_common_layout);
            recyclerView.setAdapter(emptyWrapper);
        }
        adapter.refresh(list);
    }

    /**
     * 设置下拉刷新监听
     */
    private void initRefreshListener() {
        refreshLayout.setColorSchemeResources(R.color.app_accent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                presenter.onLoadTrashNote();
            }
        });
    }
}
