package com.vmloft.develop.app.vmnote.editor;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.base.AppMVPActivity;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.widget.MDPreviewView;
import com.vmloft.develop.app.vmnote.editor.presenter.EditorPresenterImpl;
import com.vmloft.develop.app.vmnote.editor.EditorContract.IEditorView;
import com.vmloft.develop.app.vmnote.editor.EditorContract.IEditorPresenter;
import com.vmloft.develop.app.vmnote.common.router.NavParams;
import com.vmloft.develop.app.vmnote.common.router.NavRouter;
import com.vmloft.develop.app.vmnote.common.editor.MarkdownEditable;
import com.vmloft.develop.library.tools.editor.VMEditor;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMStrUtil;
import com.vmloft.develop.library.tools.widget.VMExpandableLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lzan13 on 2018/4/24.
 * 笔记编辑展示界面
 */
public class EditorActivity extends AppMVPActivity<IEditorView, IEditorPresenter<IEditorView>>
    implements IEditorView {

    @BindView(R.id.layout_expandable) VMExpandableLayout expandableLayout;
    @BindView(R.id.preview_markdown) MDPreviewView previewView;
    @BindView(R.id.edit_content) EditText inputView;
    @BindView(R.id.fab_edit) FloatingActionButton editFab;

    private Toolbar toolbar;
    private MenuItem moreItem;

    private VMEditor editor;
    private MarkdownEditable markdownEditable;

    // 当前状态，预览时不可编辑，默认为展示状态
    private boolean isEditor = false;
    private boolean isPreview = false;
    private boolean isSave = true;

    /**
     * 初始化界面 layout_id
     */
    @Override protected int initLayoutId() {
        return R.layout.activity_editor;
    }

    @Override public IEditorPresenter<IEditorView> createPresenter() {
        return new EditorPresenterImpl();
    }

    /**
     * 初始化界面
     */
    @Override public void init() {
        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onFinish();
            }
        });

        NavParams params = NavRouter.getParcelableExtra(activity);
        if (params != null) {
            presenter.onLoadNote(params.str0);
        } else {
            presenter.onLoadNote(null);
        }

        checkCommonUI();
    }

    /**
     * 加载笔记完成
     */
    @Override public void loadNoteDone(Note entity) {
        if (VMStrUtil.isEmpty(entity.getContent())) {
            editNote();
        } else {
            inputView.setText(entity.getContent());
        }

        // 初始化编辑框撤销和恢复
        editor = new VMEditor(inputView) {
            @Override protected void onTextChanged(Editable editable) {
                isSave = false;
                presenter.onTextChanged();
            }
        };
        editor.setDefaultText(VMStrUtil.isEmpty(entity.getContent()) ? "" : entity.getContent());

        // 初始化 Markdown 快捷输入
        markdownEditable = new MarkdownEditable(inputView);
    }

    /**
     * 保存笔记，逻辑给 P 层处理
     */
    private void saveNote() {
        isSave = true;
        isEditor = false;
        String content = inputView.getText().toString();
        presenter.onSaveNote(content);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        checkCommonUI();
    }

    /**
     * 开始编辑笔记
     */
    private void editNote() {
        isEditor = true;
        isPreview = false;
        toolbar.setNavigationIcon(R.drawable.ic_done);
        checkCommonUI();
    }

    private void previewNote() {
        if (isPreview) {
            isPreview = false;
        } else {
            isPreview = true;
            previewView.parseMarkdown(inputView.getText().toString());
        }
        checkCommonUI();
    }

    /**
     * 保存 Note 完成结果
     */
    @Override public void saveNoteDone(Note entity) {
        if (entity == null) {
            // 同步到服务器失败，只保存在本地
        } else {
            // 同步到服务器成功
        }
    }

    /**
     * 根据不同的状态，检测公共 UI 部分设置
     */
    private void checkCommonUI() {
        supportInvalidateOptionsMenu();
        inputView.setEnabled(isEditor);
        if (isEditor) {
            editFab.setVisibility(View.GONE);
        } else {
            editFab.setVisibility(View.VISIBLE);
            expandableLayout.collapse();
        }

        if (isPreview) {
            previewView.setVisibility(View.VISIBLE);
            inputView.setVisibility(View.GONE);
        } else {
            previewView.setVisibility(View.GONE);
            inputView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 界面控件点击事件
     */
    @OnClick({
        R.id.btn_shortcut_bold, R.id.btn_shortcut_italic, R.id.btn_shortcut_link,
        R.id.btn_shortcut_list_bulleted, R.id.btn_shortcut_list_numbered, R.id.btn_shortcut_minus,
        R.id.btn_shortcut_quote, R.id.btn_shortcut_code, R.id.btn_shortcut_code_block,
        R.id.btn_shortcut_grid, R.id.btn_shortcut_header_2, R.id.btn_shortcut_header_3,
        R.id.btn_shortcut_header_4, R.id.btn_shortcut_photo, R.id.btn_shortcut_strikethrough,
        R.id.fab_edit
    }) public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shortcut_link:
                shortcutLink();
                return;
            case R.id.btn_shortcut_grid:
                shortcutGrid();
                return;
            case R.id.fab_edit:
                editNote();
                break;
        }
        markdownEditable.onClick(view);
    }

    /**
     * 插入链接快捷键
     */
    private void shortcutLink() {
        View view = LayoutInflater.from(this).inflate(R.layout.widget_insert_link_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity).setView(view)
            .setTitle(R.string.insert_link_title)
            .show();

        final TextInputLayout titleHint = view.findViewById(R.id.input_link_title_hint);
        final TextInputLayout urlHint = view.findViewById(R.id.input_link_url_hint);
        final EditText titleEdit = view.findViewById(R.id.edit_link_title);
        final EditText urlEdit = view.findViewById(R.id.edit_link_url);

        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String titleStr = titleEdit.getText().toString().trim();
                String urlStr = urlEdit.getText().toString().trim();

                if (VMStrUtil.isEmpty(titleStr)) {
                    titleHint.setError(getString(R.string.not_null));
                    return;
                }
                if (VMStrUtil.isEmpty(urlStr)) {
                    urlHint.setError(getString(R.string.not_null));
                    return;
                }

                if (titleHint.isErrorEnabled()) {
                    titleHint.setErrorEnabled(false);
                }
                if (urlHint.isErrorEnabled()) {
                    urlHint.setErrorEnabled(false);
                }
                markdownEditable.perform(R.id.btn_shortcut_link, titleStr, urlStr);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 插入表格快捷键
     */
    private void shortcutGrid() {
        View view = LayoutInflater.from(this).inflate(R.layout.widget_insert_grid_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity).setView(view)
            .setTitle(R.string.insert_grid_title)
            .show();
        dialog.setCanceledOnTouchOutside(false);

        final TextInputLayout rowNumberHint = view.findViewById(R.id.input_grid_row_hint);
        final TextInputLayout colNumberHint = view.findViewById(R.id.input_grid_col_hint);
        final EditText rowEdit = view.findViewById(R.id.edit_grid_row);
        final EditText colEdit = view.findViewById(R.id.edit_grid_col);

        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String rowStr = rowEdit.getText().toString().trim();
                String colStr = colEdit.getText().toString().trim();

                if (VMStrUtil.isEmpty(rowStr)) {
                    rowNumberHint.setError(getString(R.string.not_null));
                    return;
                }
                if (VMStrUtil.isEmpty(colStr)) {
                    colNumberHint.setError(getString(R.string.not_null));
                    return;
                }

                if (rowNumberHint.isErrorEnabled()) {
                    rowNumberHint.setErrorEnabled(false);
                }
                if (colNumberHint.isErrorEnabled()) {
                    colNumberHint.setErrorEnabled(false);
                }
                markdownEditable.perform(R.id.btn_shortcut_grid, rowStr, colStr);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 菜单布局
     */
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        VMLog.d("onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.editor_normal, menu);
        return true;
    }

    /**
     * 根据不同的状态切换菜单布局
     */
    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (isPreview) {
            getMenuInflater().inflate(R.menu.editor_preview, menu);
        } else {
            if (isEditor) {
                getMenuInflater().inflate(R.menu.editor_editing, menu);
                moreItem = menu.findItem(R.id.action_more);
            } else {
                getMenuInflater().inflate(R.menu.editor_normal, menu);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 菜单事件
     */
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_preview:
                previewNote();
                break;
            case R.id.action_undo:
                editor.undo();
                break;
            case R.id.action_redo:
                editor.redo();
                break;
            case R.id.action_more:
                if (expandableLayout.isExpanded()) {
                    moreItem.setIcon(R.drawable.ic_expand_more);
                } else {
                    moreItem.setIcon(R.drawable.ic_expand_less);
                }
                expandableLayout.toggle();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 退出之前先检查是否有保存，这里不再弹出提示要保存，直接保存
     */
    @Override public void onFinish() {
        if (isEditor && !isSave) {
            saveNote();
        } else {
            super.onFinish();
        }
    }

    @Override public void onBackPressed() {
        onFinish();
    }
}
