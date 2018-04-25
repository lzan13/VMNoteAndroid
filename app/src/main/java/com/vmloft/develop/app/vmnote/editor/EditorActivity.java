package com.vmloft.develop.app.vmnote.editor;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.AppActivity;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.editor.presenter.IEditorPresenter;
import com.vmloft.develop.app.vmnote.editor.presenter.EditorPresenterImpl;
import com.vmloft.develop.app.vmnote.editor.view.IEditorView;
import com.vmloft.develop.app.vmnote.common.router.NavParams;
import com.vmloft.develop.app.vmnote.common.router.NavRouter;
import com.vmloft.develop.app.vmnote.common.editor.MarkdownEditable;
import com.vmloft.develop.library.tools.editor.VMEditor;
import com.vmloft.develop.library.tools.utils.VMCryptoUtil;
import com.vmloft.develop.library.tools.utils.VMStrUtil;
import com.vmloft.develop.library.tools.widget.VMExpandableLayout;
import com.vmloft.develop.library.tools.widget.VMToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lzan13 on 2018/4/24.
 * 笔记编辑展示界面
 */
public class EditorActivity extends AppActivity implements IEditorView {

    @BindView(R.id.layout_expandable) VMExpandableLayout expandableLayout;
    @BindView(R.id.edit_content) EditText contentEdit;

    private Toolbar toolbar;
    // 更多菜单项，主要是展开快捷按钮
    private MenuItem actionMore;

    private VMEditor editor;
    private MarkdownEditable markdownEditable;

    private IEditorView noteView;
    private IEditorPresenter notePresenter;

    // 是否已保存
    private boolean isSave = true;
    // Note id 只有不是新建才有此值
    private String noteId;
    private Note note;

    /**
     * 初始化界面 layout_id
     */
    @Override
    protected int initLayoutId() {
        return R.layout.activity_note_editor;
    }

    /**
     * 初始化界面
     */
    @Override
    public void init() {
        noteView = this;
        notePresenter = new EditorPresenterImpl(noteView);

        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_done);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        // 初始化编辑框撤销和恢复
        editor = new VMEditor(contentEdit) {
            @Override
            protected void onTextChanged(Editable editable) {
                isSave = false;
            }
        };
        // 初始化 Markdown 快捷输入
        markdownEditable = new MarkdownEditable(contentEdit);

        NavParams params = NavRouter.getParcelableExtra(activity);
        if (params != null) {
            noteId = params.str0;
            notePresenter.onLoad(noteId);
        } else {
            notePresenter.onLoad(null);
        }
    }

    @Override
    public void loadNoteDone(Note entity) {
        if (entity == null) {
            this.note = new Note(VMCryptoUtil.getObjectId());
        } else {
            this.note = entity;
            contentEdit.setText(entity.getContent());
        }
    }

    /**
     * 保存笔记，距离逻辑教给 P 层处理
     */
    private void saveNote() {
        String content = contentEdit.getText().toString();
        if (VMStrUtil.isEmpty(content)) {
            VMToast.make(R.string.not_null).showError();
            return;
        }
        note.setContent(content);
        notePresenter.onSave(note);
    }

    /**
     * 保存 Note 完成结果
     */
    @Override
    public void saveNoteDone(Note entity) {

    }

    /**
     * 界面控件点击事件
     */
    @OnClick({R.id.btn_shortcut_bold, R.id.btn_shortcut_code, R.id.btn_shortcut_code_block,
                     R.id.btn_shortcut_grid, R.id.btn_shortcut_header_1, R.id.btn_shortcut_header_2,
                     R.id.btn_shortcut_header_3, R.id.btn_shortcut_header_4,
                     R.id.btn_shortcut_header_5, R.id.btn_shortcut_italic, R.id.btn_shortcut_link,
                     R.id.btn_shortcut_list_bulleted, R.id.btn_shortcut_list_numbered,
                     R.id.btn_shortcut_minus, R.id.btn_shortcut_photo, R.id.btn_shortcut_quote,
                     R.id.btn_shortcut_strikethrough})
    public void onClick(View view) {
        markdownEditable.onClick(view);
    }

    /**
     * 菜单布局
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor, menu);
        actionMore = menu.findItem(R.id.action_more);
        return true;
    }

    /**
     * 菜单事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
        case R.id.action_undo:
            editor.undo();
            break;
        case R.id.action_redo:
            editor.redo();
            break;
        case R.id.action_more:
            if (expandableLayout.isExpanded()) {
                actionMore.setIcon(R.drawable.ic_expand_more);
            } else {
                actionMore.setIcon(R.drawable.ic_expand_less);
            }
            expandableLayout.toggle();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

}
