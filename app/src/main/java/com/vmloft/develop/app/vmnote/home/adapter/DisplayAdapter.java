package com.vmloft.develop.app.vmnote.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.library.tools.adapter.VMAdapter;
import com.vmloft.develop.library.tools.adapter.VMHolder;
import com.vmloft.develop.library.tools.utils.VMColor;
import com.vmloft.develop.library.tools.utils.VMDate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzan13 on 2018/4/26.
 * 数据展示列表适配器
 */
public class DisplayAdapter extends VMAdapter<Note, DisplayAdapter.DisplayHolder> {

    public DisplayAdapter(Context context, List<Note> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public DisplayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_note_layout, parent, false);
        return new DisplayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        final Note note = getItemData(position);
        holder.itemView.setSelected(note.isSelected());
        int indexEnter = note.getContent().indexOf("\n");
        if (indexEnter == -1) {
            indexEnter = note.getContent().length();
        }
        if (indexEnter > 30) {
            indexEnter = 30;
        }
        String title = note.getContent().substring(0, indexEnter);
        holder.titleView.setText(title);

        long milliTime = VMDate.milliFormUTC(note.getUpdateAt());
        String dateTime = VMDate.getRelativeTime(milliTime);
        String content = dateTime + " " + note.getContent().replace("\n", " ");
        SpannableString ss = new SpannableString(content);
        ss.setSpan(new ForegroundColorSpan(VMColor.byRes(R.color.colorAccent)), 0, dateTime.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.contentView.setText(ss);
        // 设置同步图标
        if (note.getIsSync()) {
            holder.syncIcon.setVisibility(View.GONE);
        } else {
            holder.syncIcon.setVisibility(View.VISIBLE);
        }
    }

    static class DisplayHolder extends VMHolder {

        @BindView(R.id.text_title) TextView titleView;
        @BindView(R.id.text_content) TextView contentView;
        @BindView(R.id.img_sync_icon) ImageView syncIcon;

        public DisplayHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
