package com.vmloft.develop.app.vmnote.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;

import android.view.Menu;
import android.view.MenuItem;
import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.base.AppMVPFragment;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.router.NavParams;
import com.vmloft.develop.app.vmnote.common.router.NavRouter;
import com.vmloft.develop.app.vmnote.home.adapter.DisplayAdapter;
import com.vmloft.develop.app.vmnote.home.adapter.NotesItemDecoration;
import com.vmloft.develop.app.vmnote.home.presenter.DisplayPresenterImpl;
import com.vmloft.develop.app.vmnote.home.MainContract.IDisplayView;
import com.vmloft.develop.app.vmnote.home.MainContract.IDisplayPresenter;
import com.vmloft.develop.library.tools.adapter.VMAdapter;
import com.vmloft.develop.library.tools.adapter.VMEmptyWrapper;
import com.vmloft.develop.library.tools.utils.VMLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzan13 on 2018/4/25.
 * Note 列表展示界面
 */
public class DisplayFragment extends AppMVPFragment<IDisplayView, IDisplayPresenter<IDisplayView>>
    implements IDisplayView {

    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private LayoutManager layoutManager;
    private DisplayAdapter adapter;
    private VMEmptyWrapper emptyWrapper;
    private ActionMode actionMode;

    private List<Note> noteList;
    private List<Note> selectedList;
    private boolean isEditable = false;

    /**
     * 初始化 Fragment 界面 layout_id
     *
     * @return 返回布局 id
     */
    @Override protected int initLayoutId() {
        return R.layout.fragment_note_all;
    }

    @Override public IDisplayPresenter<IDisplayView> createPresenter() {
        return new DisplayPresenterImpl();
    }

    /**
     * 初始化界面控件，将 Fragment 变量和 View 建立起映射关系
     */
    @Override protected void initView() {
        ButterKnife.bind(this, getView());

        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        noteList = new ArrayList<>();
        adapter = new DisplayAdapter(activity, noteList);

        emptyWrapper = new VMEmptyWrapper(adapter);
        emptyWrapper.setEmptyView(R.layout.widget_empty_common_layout);
        recyclerView.addItemDecoration(new NotesItemDecoration(noteList));
        recyclerView.setAdapter(emptyWrapper);

        adapter.setItemClickListener(new VMAdapter.ICListener() {
            @Override public void onItemAction(int action, Object object) {
                Note note = (Note) object;
                VMLog.d("clickItem %d, %s", action, note.getContent());
                if (isEditable) {
                    selectNote(note);
                } else {
                    NavParams params = new NavParams();
                    params.str0 = note.getId();
                    NavRouter.goEditor(activity, params);
                }
            }

            @Override public void onItemLongAction(int action, Object object) {
                startActionMode((Note) object);
            }
        });

        initRefreshLayout();
        /**
         * 本身这个方法是为了实现 Fragment 数据的懒加载而自动调用的，
         * 但是 Fragment 没有和 ViewPager 一起使用的情况下，不会执行，所以这里主动调用下
         */
        initData();
    }

    /**
     * 设置下拉刷新监听
     */
    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                presenter.onLoadAllNote();
            }
        });
    }

    /**
     * 加载数据
     */
    @Override protected void initData() {
        presenter.onLoadAllNote();
    }

    /**
     * 加载数据完成
     */
    @Override public void loadNoteDone(List<Note> list) {
        refreshLayout.setRefreshing(false);
        noteList.clear();
        noteList.addAll(list);
        refresh();
    }

    private void refresh() {
        emptyWrapper.refresh();
    }

    /**
     * 编辑模式中，点击选中或取消
     */
    private void selectNote(Note note) {
        if (isEditable && actionMode != null) {
            note.setSelected(!note.isSelected());
            if (note.isSelected()) {
                selectedList.add(note);
            } else {
                selectedList.remove(note);
            }
        }
        if (selectedList.size() == 0) {
            stopActionMode();
            return;
        }
        actionMode.setTitle(String.format(getString(R.string.note_selected), selectedList.size()));
        refresh();
    }

    /**
     * 开启 ActionMode 编辑模式
     */
    private void startActionMode(Note note) {
        if (actionMode != null) {
            selectNote(note);
            return;
        }
        actionMode = activity.startSupportActionMode(actionCallback);
        isEditable = true;
        note.setSelected(true);
        if (selectedList == null) {
            selectedList = new ArrayList<>();
        }
        selectedList.clear();
        selectedList.add(note);
        actionMode.setTitle(String.format(getString(R.string.note_selected), selectedList.size()));
        refresh();
    }

    /**
     * 停止 ActionMode 模式
     */
    private void stopActionMode() {
        VMLog.e("stopActionMode");
        isEditable = false;
        actionMode.finish();
        actionMode = null;
    }

    /**
     * 清除选中的数据
     */
    private void clearSelected() {
        for (Note note : selectedList) {
            note.setSelected(false);
        }
        selectedList.clear();
        selectedList = null;
    }

    private void moveToTrash() {
        presenter.onMoveToTrash(selectedList);
    }

    /**
     * ActionMode 回调
     */
    private ActionMode.Callback actionCallback = new ActionMode.Callback() {
        @Override public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.display_action, menu);
            return true;
        }

        @Override public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
            case R.id.action_trash:
                moveToTrash();
                break;
            }
            return false;
        }

        @Override public void onDestroyActionMode(ActionMode mode) {
            VMLog.e("ActionMode destroy");
            clearSelected();
            refresh();
            actionMode = null;
            isEditable = false;
        }
    };
}
