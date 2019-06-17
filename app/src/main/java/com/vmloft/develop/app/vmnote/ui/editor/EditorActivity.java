package com.vmloft.develop.app.vmnote.ui.editor;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.base.AppMVPActivity;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.widget.MDPreviewView;
import com.vmloft.develop.app.vmnote.ui.editor.presenter.EditorPresenterImpl;
import com.vmloft.develop.app.vmnote.ui.editor.EditorContract.IEditorView;
import com.vmloft.develop.app.vmnote.ui.editor.EditorContract.IEditorPresenter;
import com.vmloft.develop.app.vmnote.common.router.ARouter;
import com.vmloft.develop.app.vmnote.common.editor.MarkdownEditable;
import com.vmloft.develop.library.tools.router.VMParams;
import com.vmloft.develop.library.tools.utils.VMEditor;
import com.vmloft.develop.library.tools.utils.VMStr;
import com.vmloft.develop.library.tools.widget.VMExpandableLayout;
import com.vmloft.develop.library.tools.widget.toast.VMToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lzan13 on 2018/4/24.
 * 笔记编辑展示界面
 */
public class EditorActivity extends AppMVPActivity<IEditorView, IEditorPresenter<IEditorView>> implements IEditorView {

    @BindView(R.id.editor_action_preview)
    ImageButton mPreviewBtn;
    @BindView(R.id.editor_action_undo)
    ImageButton mUndoBtn;
    @BindView(R.id.editor_action_redo)
    ImageButton mRedoBtn;
    @BindView(R.id.editor_action_more)
    ImageButton mMoreBtn;

    @BindView(R.id.editor_expand_layout)
    VMExpandableLayout mExpandableLayout;
    @BindView(R.id.editor_markdown_preview)
    MDPreviewView mPreviewView;
    @BindView(R.id.editor_content_et)
    EditText mInputView;
    @BindView(R.id.editor_edit_btn)
    FloatingActionButton mEditBtn;

    private VMEditor mEditor;
    private MarkdownEditable mMarkdownEditable;

    // 当前状态，预览时不可编辑，默认为展示状态
    private boolean isEditor = false;
    private boolean isPreview = false;
    private boolean isSave = true;

    /**
     * 初始化界面 layout_id
     */
    @Override
    protected int layoutId() {
        return R.layout.activity_editor;
    }

    @Override
    public IEditorPresenter<IEditorView> createPresenter() {
        return new EditorPresenterImpl();
    }

    /**
     * 初始化界面
     */
    @Override
    public void initUI() {
        super.initUI();
        setTopIcon(R.drawable.ic_done);
        if (mTopSpaceView != null) {
            mTopSpaceView.setVisibility(View.GONE);
        }

        refreshUI();
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        VMParams params = ARouter.getParams(mActivity);
        if (params != null) {
            mPresenter.onLoadData(params.str0);
        } else {
            mPresenter.onLoadData(null);
        }
    }

    /**
     * 加载笔记完成
     */
    @Override
    public void loadDataDone(Note note) {
        if (VMStr.isEmpty(note.getContent())) {
            editNote();
        } else {
            mInputView.setText(note.getContent());
        }

        // 初始化编辑框撤销和恢复
        mEditor = new VMEditor(mInputView) {
            @Override
            protected void onTextChanged(Editable editable) {
                isSave = false;
                mPresenter.onTextChanged();
            }
        };
        mEditor.setDefaultText(VMStr.isEmpty(note.getContent()) ? "" : note.getContent());

        // 初始化 Markdown 快捷输入
        mMarkdownEditable = new MarkdownEditable(mInputView);
    }

    /**
     * 保存笔记
     */
    private void saveNote() {
        isSave = true;
        isEditor = false;
        String content = mInputView.getText().toString();
        mPresenter.onSaveData(content);
        setTopIcon(R.drawable.vm_ic_arrow_left);
        refreshUI();
    }

    /**
     * 开始编辑笔记
     */
    private void editNote() {
        isEditor = true;
        isPreview = false;
        setTopIcon(R.drawable.ic_done);
        refreshUI();
    }

    private void previewNote() {
        if (isPreview) {
            isPreview = false;
        } else {
            isPreview = true;
            mPreviewView.parseMarkdown(mInputView.getText().toString());
        }
        refreshUI();
    }

    /**
     * 保存 Note 完成结果
     */
    @Override
    public void saveDataDone(Note note) {
        if (note == null) {
            // 同步到服务器失败，只保存在本地
        } else {
            // 同步到服务器成功
        }
    }

    /**
     * 根据不同的状态，检测公共 UI 部分设置
     */
    private void refreshUI() {
        mInputView.setEnabled(isEditor);
        if (isEditor) {
            setTopIcon(R.drawable.ic_done);
            mEditBtn.setVisibility(View.INVISIBLE);
            mExpandableLayout.setVisibility(View.GONE);
        } else {
            setTopIcon(R.drawable.vm_ic_arrow_left);
            mEditBtn.setVisibility(View.VISIBLE);
            mExpandableLayout.setVisibility(View.VISIBLE);
        }
        if (isPreview) {
            mPreviewView.setVisibility(View.VISIBLE);
            mInputView.setVisibility(View.GONE);
            mPreviewBtn.setImageResource(R.drawable.ic_edit_eye_off);
            mUndoBtn.setVisibility(View.GONE);
            mRedoBtn.setVisibility(View.GONE);
        } else {
            mPreviewView.setVisibility(View.GONE);
            mInputView.setVisibility(View.VISIBLE);
            mPreviewBtn.setImageResource(R.drawable.ic_edit_eye);
            mUndoBtn.setVisibility(View.VISIBLE);
            mRedoBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 界面控件点击事件
     */
    @OnClick({
            R.id.editor_action_preview,
            R.id.editor_action_undo,
            R.id.editor_action_redo,
            R.id.editor_action_more,
            R.id.editor_shortcut_header_equal_btn,
            R.id.editor_shortcut_header_1_btn,
            R.id.editor_shortcut_header_2_btn,
            R.id.editor_shortcut_header_3_btn,
            R.id.editor_shortcut_header_4_btn,
            R.id.editor_shortcut_header_5_btn,
            R.id.editor_shortcut_bold_btn,
            R.id.editor_shortcut_italic_btn,
            R.id.editor_shortcut_unordered_list_btn,
            R.id.editor_shortcut_ordered_list_btn,
            R.id.editor_shortcut_minus_btn,
            R.id.editor_shortcut_quote_btn,
            R.id.editor_shortcut_code_btn,
            R.id.editor_shortcut_code_block_btn,
            R.id.editor_shortcut_link_btn,
            R.id.editor_shortcut_grid_btn,
            R.id.editor_shortcut_photo_btn,
            R.id.editor_shortcut_strickout_btn,
            R.id.editor_edit_btn
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editor_action_preview:
                previewNote();
                break;
            case R.id.editor_action_undo:
                mEditor.undo();
                break;
            case R.id.editor_action_redo:
                mEditor.redo();
                break;
            case R.id.editor_action_more:
                if (mExpandableLayout.isExpanded()) {
                    mMoreBtn.setImageResource(R.drawable.ic_expand_more);
                } else {
                    mMoreBtn.setImageResource(R.drawable.ic_expand_less);
                }
                mExpandableLayout.toggle();
                break;
            case R.id.editor_shortcut_link_btn:
                shortcutLink();
                return;
            case R.id.editor_shortcut_grid_btn:
                shortcutGrid();
                return;
            case R.id.editor_shortcut_photo_btn:
                shortcutPicture();
                break;
            case R.id.editor_edit_btn:
                editNote();
                break;
        }
        mMarkdownEditable.onClick(view);
    }

    /**
     * 插入链接快捷键
     */
    private void shortcutLink() {
        View view = LayoutInflater.from(this).inflate(R.layout.widget_insert_link_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(mActivity).setView(view)
                .setTitle(R.string.link_insert_title)
                .show();

        final TextInputLayout titleHint = view.findViewById(R.id.input_link_title_hint);
        final TextInputLayout urlHint = view.findViewById(R.id.input_link_url_hint);
        final EditText titleEdit = view.findViewById(R.id.edit_link_title);
        final EditText urlEdit = view.findViewById(R.id.edit_link_url);

        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            dialog.dismiss();
        });
        view.findViewById(R.id.btn_ok).setOnClickListener(v -> {
            String titleStr = titleEdit.getText().toString().trim();
            String urlStr = urlEdit.getText().toString().trim();

            if (VMStr.isEmpty(titleStr)) {
                titleHint.setError(getString(R.string.not_null));
                return;
            }
            if (VMStr.isEmpty(urlStr)) {
                urlHint.setError(getString(R.string.not_null));
                return;
            }

            if (titleHint.isErrorEnabled()) {
                titleHint.setErrorEnabled(false);
            }
            if (urlHint.isErrorEnabled()) {
                urlHint.setErrorEnabled(false);
            }
            mMarkdownEditable.perform(R.id.editor_shortcut_link_btn, titleStr, urlStr);
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * 插入表格快捷键
     */
    private void shortcutGrid() {
        View view = LayoutInflater.from(this).inflate(R.layout.widget_insert_grid_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(mActivity).setView(view)
                .setTitle(R.string.grid_insert_title)
                .show();
        dialog.setCanceledOnTouchOutside(false);

        final TextInputLayout rowNumberHint = view.findViewById(R.id.input_grid_row_hint);
        final TextInputLayout colNumberHint = view.findViewById(R.id.input_grid_col_hint);
        final EditText rowEdit = view.findViewById(R.id.edit_grid_row);
        final EditText colEdit = view.findViewById(R.id.edit_grid_col);

        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            dialog.dismiss();
        });
        view.findViewById(R.id.btn_ok).setOnClickListener(v -> {
            String rowStr = rowEdit.getText().toString().trim();
            String colStr = colEdit.getText().toString().trim();

            if (VMStr.isEmpty(rowStr)) {
                rowNumberHint.setError(getString(R.string.not_null));
                return;
            }
            if (VMStr.isEmpty(colStr)) {
                colNumberHint.setError(getString(R.string.not_null));
                return;
            }

            if (rowNumberHint.isErrorEnabled()) {
                rowNumberHint.setErrorEnabled(false);
            }
            if (colNumberHint.isErrorEnabled()) {
                colNumberHint.setErrorEnabled(false);
            }
            mMarkdownEditable.perform(R.id.editor_shortcut_grid_btn, rowStr, colStr);
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * 快捷插入图片
     */
    private void shortcutPicture() {
        VMToast.make(mActivity, "暂不支持插入图片").error();
    }

    /**
     * 退出之前先检查是否有保存，这里不再弹出提示要保存，直接保存
     */
    @Override
    public void onFinish() {
        if (isEditor && !isSave) {
            saveNote();
        } else {
            super.onFinish();
        }
    }

    @Override
    public void onBackPressed() {
        onFinish();
    }

}
