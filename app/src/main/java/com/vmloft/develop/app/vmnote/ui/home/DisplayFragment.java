package com.vmloft.develop.app.vmnote.ui.home;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;

import android.view.Menu;
import android.view.MenuItem;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.base.AppActivity;
import com.vmloft.develop.app.vmnote.base.AppMVPFragment;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.router.ARouter;
import com.vmloft.develop.app.vmnote.ui.home.adapter.DisplayAdapter;
import com.vmloft.develop.app.vmnote.ui.home.adapter.NotesItemDecoration;
import com.vmloft.develop.app.vmnote.ui.home.presenter.DisplayPresenterImpl;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.IDisplayView;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.IDisplayPresenter;
import com.vmloft.develop.library.tools.adapter.VMAdapter;
import com.vmloft.develop.library.tools.adapter.VMEmptyWrapper;
import com.vmloft.develop.library.tools.router.VMParams;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMStr;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lzan13 on 2018/4/25.
 * Note 列表展示界面
 */
public class DisplayFragment extends AppMVPFragment<IDisplayView, IDisplayPresenter<IDisplayView>> implements IDisplayView {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private AppActivity mActivity;

    private LayoutManager mLayoutManager;
    private DisplayAdapter mAdapter;
    private VMEmptyWrapper mEmptyWrapper;
    private ActionMode mActionMode;

    private List<Note> mList = new ArrayList<>();
    private List<Note> mSelectedList  = new ArrayList<>();
    private boolean isEditable = false;

    /**
     * 初始化 Fragment 界面 layout_id
     *
     * @return 返回布局 id
     */
    @Override
    protected int layoutId() {
        return R.layout.fragment_note_all;
    }

    @Override
    public IDisplayPresenter<IDisplayView> createPresenter() {
        return new DisplayPresenterImpl();
    }

    /**
     * 初始化界面控件，将 Fragment 变量和 View 建立起映射关系
     */
    @Override
    protected void init() {
        super.init();

        initRecyclerView();

        presenter.onLoadData();
    }

    /**
     * 初始化加载数据列表
     */
    private void initRecyclerView() {
        // 下拉刷新控件初始化
        mRefreshLayout.setColorSchemeResources(R.color.app_accent);
        mRefreshLayout.setOnRefreshListener(() -> {
            presenter.onLoadData();
        });

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DisplayAdapter(mContext, mList);

        mEmptyWrapper = new VMEmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(R.layout.widget_empty_common_layout);
        mRecyclerView.addItemDecoration(new NotesItemDecoration(mList));
        mRecyclerView.setAdapter(mEmptyWrapper);

        mAdapter.setClickListener(new VMAdapter.IClickListener<Note>() {
            @Override
            public void onItemAction(int action, Note note) {
                if (isEditable) {
                    selectNote(note);
                } else {
                    VMParams params = new VMParams();
                    params.str0 = note.getId();
                    ARouter.goEditor(mContext, params);
                }
            }

            @Override
            public boolean onItemLongAction(int action, Note note) {
                startActionMode(note);
                return false;
            }

        });
    }

    /**
     * 加载数据完成
     */
    @Override
    public void loadDataDone(List<Note> list) {
        mRefreshLayout.setRefreshing(false);
        if (list != null) {
            mList.clear();
            mList.addAll(list);
        }
        refresh();
    }

    private void refresh() {
        mEmptyWrapper.refresh();
    }

    /**
     * 编辑模式中，点击选中或取消
     */
    private void selectNote(Note note) {
        if (isEditable && mActionMode != null) {
            note.setSelected(!note.isSelected());
            if (note.isSelected()) {
                mSelectedList.add(note);
            } else {
                mSelectedList.remove(note);
            }
        }
        if (mSelectedList.size() == 0) {
            stopActionMode();
            return;
        }
        mActionMode.setTitle(VMStr.byResArgs(R.string.note_selected, mSelectedList.size()));
        refresh();
    }

    /**
     * 开启 ActionMode 编辑模式
     */
    private void startActionMode(Note note) {
        if (mActionMode != null) {
            selectNote(note);
            return;
        }
        mActionMode = mActivity.startSupportActionMode(actionCallback);
        isEditable = true;
        note.setSelected(true);
        mSelectedList.clear();
        mSelectedList.add(note);
        mActionMode.setTitle(String.format(getString(R.string.note_selected), mSelectedList.size()));
        refresh();
    }

    /**
     * 停止 ActionMode 模式
     */
    private void stopActionMode() {
        VMLog.e("stopActionMode");
        isEditable = false;
        mActionMode.finish();
        mActionMode = null;
    }

    /**
     * 清除选中的数据
     */
    private void clearSelected() {
        for (Note note : mSelectedList) {
            note.setSelected(false);
        }
        mSelectedList.clear();
    }

    /**
     * 移动到回收站
     */
    private void moveToTrash() {
        presenter.onMoveToTrash(mSelectedList);
    }

    /**
     * ActionMode 回调
     */
    private ActionMode.Callback actionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.display_action, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_trash:
                    moveToTrash();
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            VMLog.e("ActionMode destroy");
            clearSelected();
            refresh();
            mActionMode = null;
            isEditable = false;
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppActivity) context;
    }
}
