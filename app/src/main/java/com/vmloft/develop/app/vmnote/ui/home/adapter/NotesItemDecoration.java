package com.vmloft.develop.app.vmnote.ui.home.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.library.tools.utils.VMColor;
import com.vmloft.develop.library.tools.utils.VMDate;
import com.vmloft.develop.library.tools.utils.VMDimen;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/26.
 * RecyclerView 装饰控件
 */
public class NotesItemDecoration extends RecyclerView.ItemDecoration {

    // 数据源
    private List<Note> notes;

    // 画笔
    private Paint paint;
    // 画笔颜色
    private int decorationColor;
    private int overTitleBackground;
    private int childTitleBackground;
    private int titleColor;
    // 画笔文字大小
    private int titleSize;
    // 分类标题高度
    private int titleHeight;
    private float titleTextHeight;
    private int itemPadding;

    public NotesItemDecoration(List<Note> list) {
        super();
        notes = list;

        decorationColor = VMColor.byRes(R.color.item_decoration);
        overTitleBackground = VMColor.byRes(R.color.item_over_title_bg);
        childTitleBackground = VMColor.byRes(R.color.item_child_title_bg);
        titleColor = VMColor.byRes(R.color.item_child_title);
        titleSize = VMDimen.getDimenPixel(R.dimen.vm_size_12);
        titleHeight = VMDimen.getDimenPixel(R.dimen.vm_dimen_32);
        itemPadding = VMDimen.getDimenPixel(R.dimen.vm_padding_large);

        paint = new Paint();
        paint.setTextSize(titleSize);
        paint.setAntiAlias(true);

        titleTextHeight = VMDimen.getTextHeight(paint);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (notes.size() == 0) {
            return;
        }
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position > -1) {
                if (position == 0) {
                    drawItemTitle(c, parent, child);
                    drawDecoration(c, parent, child);
                } else {
                    Note previousNote = notes.get(position - 1);
                    Note nextNote = notes.get(position);
                    String previousDate = VMDate.long2DateNoDay(VMDate.milliFormUTC(previousNote.getUpdateAt()));
                    String nextDate = VMDate.long2DateNoDay(VMDate.milliFormUTC(nextNote.getUpdateAt()));
                    if (!nextDate.equals(previousDate)) {
                        drawItemTitle(c, parent, child);
                    }
                    drawDecoration(c, parent, child);
                }
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (notes.size() == 0) {
            return;
        }
        int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        if (position > -1) {
            if (position == 0 && parent.getChildAt(0).getTop() >= 0) {
                paint.setColor(childTitleBackground);
            } else {
                paint.setColor(overTitleBackground);
            }
            Note note = notes.get(position);
            String dateStr = VMDate.long2DateNoDay(VMDate.milliFormUTC(note.getUpdateAt()));
            c.drawRect(0, 0, parent.getRight(), parent.getPaddingTop() + titleHeight, paint);
            paint.setColor(titleColor);
            c.drawText(dateStr, itemPadding, titleHeight - titleHeight / 2 + titleTextHeight / 3, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (notes.size() == 0) {
            return;
        }
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > -1) {
            if (position == 0) {
                outRect.set(0, titleHeight, 0, 0);
            } else {
                Note previousNote = notes.get(position - 1);
                Note nextNote = notes.get(position);
                String previousDate = VMDate.long2DateNoDay(VMDate.milliFormUTC(previousNote.getUpdateAt()));
                String nextDate = VMDate.long2DateNoDay(VMDate.milliFormUTC(nextNote.getUpdateAt()));
                if (!nextDate.equals(previousDate)) {
                    outRect.set(0, titleHeight, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

    private void drawItemTitle(Canvas canvas, RecyclerView parent, View child) {
        int left = parent.getLeft();
        int right = parent.getRight();
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        int position = params.getViewLayoutPosition();
        Note note = notes.get(position);
        String dateStr = VMDate.long2DateNoDay(VMDate.milliFormUTC(note.getUpdateAt()));

        // 绘制 title 背景
        paint.setColor(childTitleBackground);
        canvas.drawRect(left, child.getTop() - params.topMargin - titleHeight, right, child.getTop() - params.topMargin, paint);

        // 绘制 title
        paint.setColor(titleColor);
        canvas.drawText(dateStr, itemPadding, child.getTop() - params.topMargin - titleHeight / 2 + titleTextHeight / 3, paint);
    }

    private void drawDecoration(Canvas canvas, RecyclerView parent, View child) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        int left = parent.getLeft();
        int right = parent.getRight();
        paint.setColor(decorationColor);
        paint.setStrokeWidth(2.0f);
        canvas.drawLine(left + itemPadding, child.getTop() - params.topMargin, right - itemPadding, child
            .getTop() - params.topMargin, paint);
    }
}
