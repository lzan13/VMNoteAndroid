package com.vmloft.develop.app.vmnote.common.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.library.tools.picker.IPictureLoader;


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
    public static void load(Context context, IPictureLoader.Options options, ImageView imageView) {
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
    private static RequestBuilder<Drawable> placeholder(Context context, IPictureLoader.Options options) {
        int resId = R.drawable.img_default_note;
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

}
