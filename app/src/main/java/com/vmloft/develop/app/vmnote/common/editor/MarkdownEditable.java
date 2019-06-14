package com.vmloft.develop.app.vmnote.common.editor;

import android.view.View;
import android.widget.EditText;

import com.vmloft.develop.app.vmnote.R;

/**
 * Created by lzan13 on 2018/4/24.
 *
 * Markdown 相关格式快捷输入操作类
 */
public class MarkdownEditable implements View.OnClickListener {
    private EditText editText;

    public MarkdownEditable(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onClick(View v) {
        if (editText == null) {
            return;
        }
        perform(v.getId());
    }

    public void perform(int id, Object... param) {
        switch (id) {
            case R.id.editor_shortcut_header_1_btn://
                H(1);
                break;
            case R.id.editor_shortcut_header_2_btn://
                H(2);
                break;
            case R.id.editor_shortcut_header_3_btn://
                H(3);
                break;
            case R.id.editor_shortcut_header_4_btn://
                H(4);
                break;
            case R.id.editor_shortcut_header_5_btn://
                H(5);
                break;
            case R.id.editor_shortcut_bold_btn:
                performBold();
                break;
            case R.id.editor_shortcut_italic_btn://斜体
                performItalic();
                break;
            case R.id.editor_shortcut_unordered_list_btn://列表
                performList("* ");
                break;
            case R.id.editor_shortcut_ordered_list_btn://数字列表
                performList("1. ");
                break;
            case R.id.editor_shortcut_minus_btn://分割线
                performMinus();
                break;
            case R.id.editor_shortcut_quote_btn://引用
                performQuote();
                break;
            case R.id.editor_shortcut_code_btn:
                performInlineCode();
                break;
            case R.id.editor_shortcut_code_block_btn:
                performCodeBlock();
                break;
            case R.id.editor_shortcut_link_btn://链接
                performInsertLink(param);
                break;
            case R.id.editor_shortcut_grid_btn:
                performInsertTable(param);
                break;
            case R.id.editor_shortcut_photo_btn://图片
                performInsertPhoto(param);
                break;
            case R.id.editor_shortcut_strickout_btn://删除线
                performStrickout();
                break;
        }
    }


    /**
     * 标题
     *
     * @param level 标题级别 H1-6
     */
    public final void H(int level) {
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        StringBuilder result = new StringBuilder();
        String substring = source.substring(selectionStart, selectionEnd);
        if (!hasNewLine(source, selectionStart)) {
            result.append("\n");
        }
        for (int i = 0; i < level; i++) {
            result.append("#");
        }
        result.append(" ").append(substring);

        editText.getText().replace(selectionStart, selectionEnd, result.toString());
        editText.setSelection(selectionStart + result.length());
        completed();
    }

    /**
     * 加粗标签
     */
    private void performBold() {
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        String substring = source.substring(selectionStart, selectionEnd);
        String result = " **" + substring + "** ";
        editText.getText().replace(selectionStart, selectionEnd, result);
        editText.setSelection(result.length() + selectionStart - 3);
        completed();
    }


    /**
     * 斜体字标签
     */
    private void performItalic() {
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        String substring = source.substring(selectionStart, selectionEnd);
        String result = " *" + substring + "* ";
        editText.getText().replace(selectionStart, selectionEnd, result);
        editText.setSelection(result.length() + selectionStart - 2);
        completed();
    }

    /**
     * 处理列表项，包括无序列表和有序列表
     */
    private void performList(String tag) {
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        String substring = source.substring(0, selectionStart);
        int line = substring.lastIndexOf(10);


        if (line != -1) {
            selectionStart = line + 1;
        } else {
            selectionStart = 0;
        }
        substring = source.substring(selectionStart, selectionEnd);

        String[] split = substring.split("\n");
        StringBuffer stringBuffer = new StringBuffer();

        if (split != null && split.length > 0) {
            for (String s : split) {
                if (s.length() == 0 && stringBuffer.length() != 0) {
                    stringBuffer.append("\n");
                    continue;
                }
                if (!s.trim().startsWith(tag)) {
                    //不是空行或者已经是序号开头
                    if (stringBuffer.length() > 0) {
                        stringBuffer.append("\n");
                    }
                    stringBuffer.append(tag).append(s);
                } else {
                    if (stringBuffer.length() > 0) {
                        stringBuffer.append("\n");
                    }
                    stringBuffer.append(s);
                }
            }
        }

        if (stringBuffer.length() == 0) {
            stringBuffer.append(tag);
        }
        editText.getText().replace(selectionStart, selectionEnd, stringBuffer.toString());
        editText.setSelection(stringBuffer.length() + selectionStart);
        completed();
    }

    /**
     * 间隔线
     */
    public void performMinus() {
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();

        String result;
        if (hasNewLine(source, selectionStart)) {
            //已经单独一行
            result = "-------\n";
        } else {
            //同一行，加上换行
            result = "\n-------\n";
        }
        editText.getText().replace(selectionStart, selectionStart, result);
        editText.setSelection(result.length() + selectionStart);
        completed();
    }

    /**
     * 引用
     */
    public void performQuote() {
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        String substring = source.substring(selectionStart, selectionEnd);

        String result;
        if (hasNewLine(source, selectionStart)) {
            result = "> " + substring;
        } else {
            result = "\n> " + substring;

        }
        editText.getText().replace(selectionStart, selectionEnd, result);
        editText.setSelection(result.length() + selectionStart);
        completed();
    }

    /**
     * 行内代码
     */
    private void performInlineCode() {
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        String substring = source.substring(selectionStart, selectionEnd);
        String result = " `" + substring + "` ";
        editText.getText().replace(selectionStart, selectionEnd, result);
        editText.setSelection(result.length() + selectionStart - 2);
        completed();
    }

    /**
     * 代码块
     */
    private void performCodeBlock() {
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();

        int selectionEnd = editText.getSelectionEnd();
        String substring = source.substring(selectionStart, selectionEnd);

        String result;
        if (hasNewLine(source, selectionStart)) {
            result = "```\n" + substring + "\n```\n";
        } else {
            result = "\n```\n" + substring + "\n```\n";
        }

        editText.getText().replace(selectionStart, selectionEnd, result);
        editText.setSelection(result.length() + selectionStart - 5);
        completed();
    }

    /**
     * 插入链接
     */
    private void performInsertLink(Object[] param) {
        int selectionStart = editText.getSelectionStart();
        String result;
        if (param == null || param.length == 0) {
            result = "[]()\n";
            editText.getText().insert(selectionStart, result);
            editText.setSelection(selectionStart + 1);
        } else if (param.length == 1) {
            result = "[" + param[0] + "](" + param[0] + ")\n";
            editText.getText().insert(selectionStart, result);
            editText.setSelection(selectionStart + result.length());
        } else {
            result = "[" + param[0] + "](" + param[1] + ")\n";
            editText.getText().insert(selectionStart, result);
            editText.setSelection(selectionStart + result.length());
        }

        completed();
    }

    /**
     * 插入表格，参数可以设置表格 行 列
     */
    private void performInsertTable(Object... param) {
        int row;
        int column;
        int i;
        if (param == null || param.length < 2) {
            row = 3;
            column = 3;
        } else {
            try {
                row = Integer.parseInt(param[0].toString());
                column = Integer.parseInt(param[1].toString());
            } catch (NumberFormatException e) {
                row = 3;
                column = 3;
            }
        }
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();
        StringBuilder stringBuilder = new StringBuilder();

        if (!hasNewTwoLine(source, selectionStart)) {
            if (hasNewLine(source, selectionStart)) {
                stringBuilder.append("\n");
            } else {
                stringBuilder.append("\n\n");
            }
        }
        stringBuilder.append("|");
        for (i = 0; i < column; i++) {
            stringBuilder.append(" Header |");
        }
        stringBuilder.append("\n|");
        for (i = 0; i < column; i++) {
            stringBuilder.append(":----------:|");
        }
        stringBuilder.append("\n");
        for (int i2 = 0; i2 < row; i2++) {
            stringBuilder.append("|");
            for (i = 0; i < column; i++) {
                stringBuilder.append("            |");
            }
            stringBuilder.append("\n");
        }
        String result = stringBuilder.toString();
        editText.getText().insert(selectionStart, result);
        editText.setSelection(selectionStart + result.length());
        completed();
    }

    /**
     * 插入图片
     */
    private void performInsertPhoto(Object[] param) {
        if (param == null || param.length == 0) {
            param = new Object[]{""};
        }
//        Object[] temp = param;
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();

        String result;
        if (hasNewLine(source, selectionStart)) {
            result = "![image](" + param[0] + ")";
        } else {
            result = "\n" + "![image](" + param[0] + ")";
        }
        editText.getText().insert(selectionStart, result);
        if (param == null || param[0].toString().length() == 0) {
            editText.setSelection(result.length() + selectionStart - 1);
        } else {
            editText.setSelection(result.length() + selectionStart);
        }
        completed();
    }

    /**
     * 删除线
     */
    private void performStrickout() {
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        String substring = source.substring(selectionStart, selectionEnd);

        String result = " ~~" + substring + "~~ ";
        editText.getText().replace(selectionStart, selectionEnd, result);
        editText.setSelection(result.length() + selectionStart - 3);
        completed();
    }

    protected void completed() {

    }

    private boolean hasNewLine(String source, int selectionStart) {
        if (source.isEmpty()) {
            return true;
        }
        source = source.substring(0, selectionStart);
        if (source.isEmpty()) {
            return true;
        }
        return source.charAt(source.length() - 1) == 10;
    }

    private boolean hasNewTwoLine(String source, int selectionStart) {
        source = source.substring(0, selectionStart);
        return source.length() >= 2 && source.charAt(source.length() - 1) == 10 && source.charAt(source
                .length() - 2) == 10;
    }

    private boolean isEmptyLine(String source, int selectionStart) {
        if (source.isEmpty()) {
            return true;
        }
        if (selectionStart == source.length()) {
            return hasNewLine(source, selectionStart);
        }

        String startStr = source.substring(0, selectionStart);
        //最后一行
        return source.charAt(startStr.length() - 1) == 10 && source.charAt(startStr.length()) == 10;

    }


}
