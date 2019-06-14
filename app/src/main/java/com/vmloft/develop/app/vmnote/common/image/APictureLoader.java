package com.vmloft.develop.app.vmnote.common.image;

import android.content.Context;
import android.widget.ImageView;

import com.vmloft.develop.library.tools.picker.IPictureLoader;

/**
 * Create by lzan13 on 2019/5/23 09:57
 *
 * 实现 IM 图片加载接口
 */
public class APictureLoader implements IPictureLoader {

    /**
     * 通过上下文对象加载图片
     *
     * @param context   上下文对象
     * @param options   加载配置
     * @param imageView 目标控件
     */
    @Override
    public void load(Context context, IPictureLoader.Options options, ImageView imageView) {
        ALoader.load(context, options, imageView);
    }
}
