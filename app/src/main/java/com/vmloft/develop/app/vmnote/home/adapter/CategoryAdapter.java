package com.vmloft.develop.app.vmnote.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.bean.Category;
import com.vmloft.develop.library.tools.adapter.VMAdapter;
import com.vmloft.develop.library.tools.adapter.VMHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzan13 on 2018/4/26.
 * 数据展示列表适配器
 */
public class CategoryAdapter extends VMAdapter<Category, CategoryAdapter.CategoryHolder> {

    public CategoryAdapter(Context context, List<Category> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_note_layout, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = getItemData(position);
        holder.titleView.setText(category.getTitle());
    }

    static class CategoryHolder extends VMHolder {

        @BindView(R.id.text_title) TextView titleView;

        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
