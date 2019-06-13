package com.vmloft.develop.app.vmnote.common.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.common.AConstants;

import java.io.File;

/**
 * Created by lzan13 on 2017/11/30.
 * 封装图片加载方法
 */
public class ALoader {

    /**
     * 加载图片
     *
     * @param context   上下文
     * @param options   加载图片配置
     * @param imageView 目标 view
     */
    public static void load(Context context, Options options, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        if (options.isCircle) {
            requestOptions.circleCrop();
        } else if (options.isRadius) {
            requestOptions.transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(options.radiusSize)));
        }
        if (options.isBlur) {
            requestOptions.transform(new BlurTransformation());
        }
        GlideApp.with(context)
                .load(options.url)
                .apply(requestOptions)
                .thumbnail(placeholder(context, options))
                .into(imageView);
    }

    /**
     * 统一处理占位图
     *
     * @param context 上下文对象
     * @param options 加载配置
     * @return
     */
    private static RequestBuilder<Drawable> placeholder(Context context, Options options) {
        int resId = R.drawable.img_default_background;
        RequestOptions requestOptions = new RequestOptions();
        if (options.isCircle) {
            requestOptions.circleCrop();
        } else if (options.isRadius) {
            requestOptions.transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(options.radiusSize)));
        }
        if (options.isBlur) {
            requestOptions.transform(new BlurTransformation());
        }

        return GlideApp.with(context).load(resId).apply(requestOptions);
    }

    public static void clear(Context context, ImageView imageView) {
        GlideApp.with(context).clear(imageView);
    }


    /**
     * 图片加载相关参数类
     */
    public static class Options {

        public Options(String url) {
            this.url = url;
        }

        // 图片地址
        public String url;

        // 是否为圆形
        public boolean isCircle;

        // 是否为圆角
        public boolean isRadius;
        // 圆角大小，需配合上个参数一起使用
        public int radiusSize;
        public int radiusLTSize; // 左上角
        public int radiusLBSize; // 左下角
        public int radiusRTSize; // 右上角
        public int radiusRBSize; // 右下角

        // 是否模糊
        public boolean isBlur;
    }
}
