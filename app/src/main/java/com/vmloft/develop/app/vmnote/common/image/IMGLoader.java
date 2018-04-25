package com.vmloft.develop.app.vmnote.common.image;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.C;

import java.io.File;

/**
 * Created by lzan13 on 2017/11/30.
 * 封装图片加载方法
 */
public class IMGLoader {

    /**
     * 默认情况下加载头像
     *
     * @param context 上下文
     * @param url 地址
     * @param imageView 显示控件
     */
    public static void loadAvatar(Context context, String url, ImageView imageView) {
        int defaultId = R.mipmap.ic_launcher_round;
        GlideApp.with(context)
                .load(parseURL(url))
                .circleCrop()
                .error(defaultId)
                .placeholder(defaultId)
                .override(256, 256)
                .into(imageView);
    }

    //    /**
    //     * 加载大头像
    //     */
    //    public static void loadBigAvatar(Context context, String url, ImageView imageView) {
    //        int defaultId = R.mipmap.ic_launcher;
    //        GlideApp.with(context)
    //                .load(parseURL(url))
    //                .fitCenter()
    //                .error(defaultId)
    //                .placeholder(defaultId)
    //                .into(imageView);
    //    }
    //
    //    /**
    //     * 加载列表项图标
    //     */
    //    public static void loadItemIcon(Context context, String url, ImageView imageView) {
    //        int defaultId = R.drawable.img_default;
    //        GlideApp.with(context)
    //                .load(parseURL(url))
    //                .error(defaultId)
    //                .placeholder(defaultId)
    //                .transform(new MultiTransformation<Bitmap>(new CenterCrop(), new RoundTransformation(context, 10)))
    //                .override(256, 256)
    //                .into(imageView);
    //
    //    }
    //
    //    /**
    //     * 加载刚选择头像时本地图片
    //     */
    //    public static void loadLocalAvatar(Context context, String path, ImageView imageView) {
    //        GlideApp.with(context)
    //                .load(path)
    //                .circleCrop()
    //                .error(R.drawable.img_avatar_default)
    //                .placeholder(R.drawable.img_avatar_default)
    //                .override(512, 512)
    //                .into(imageView);
    //    }
    //
    //
    //    /**
    //     * 加载图片缩略图显示
    //     *
    //     * @param context 上下文
    //     * @param url 图片地址
    //     * @param imageView 显示控件
    //     */
    //    public static void loadSmallLogo(Context context, String url, ImageView imageView, int id) {
    //        GlideApp.with(context)
    //                .load(parseURL(parseURL(url)))
    //                .error(id)
    //                .placeholder(id)
    //                .transform(new MultiTransformation<Bitmap>(new CenterCrop(), new RoundTransformation(context, 10)))
    //                .override(237, 237)
    //                .into(imageView);
    //    }
    //
    //    /**
    //     * 加载图标
    //     */
    //    public static void loadIcon(Context context, String url, ImageView imageView) {
    //        int defaultId = R.drawable.ic_channel;
    //        GlideApp.with(context)
    //                .load(parseURL(url))
    //                .fitCenter()
    //                .error(defaultId)
    //                .placeholder(defaultId)
    //                .override(237, 237)
    //                .into(imageView);
    //    }
    //
    //    /**
    //     * 加载图片缩略图显示
    //     *
    //     * @param context 上下文
    //     * @param url 图片地址
    //     * @param imageView 显示控件
    //     */
    //    public static void loadImageThumb(Context context, String url, ImageView imageView) {
    //        GlideApp.with(context)
    //                .load(parseURL(url))
    //                .error(R.drawable.img_default)
    //                .placeholder(R.drawable.img_default)
    //                .override(512, 512)
    //                .centerCrop()
    //                .into(imageView);
    //    }

    /**
     * 加载完整图片显示
     */
    public static void loadBigPhoto(Context context, String url, ImageView imageView) {
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        GlideApp.with(context)
                .load(parseURL(url))
                .fitCenter()
                .transition(transitionOptions)
                .error(R.drawable.img_default_background)
                .placeholder(R.drawable.img_default_background)
                .into(imageView);
    }
    //
    //    /**
    //     * 加载广告
    //     */
    //    public static void loadADS(Context context, String url, ImageView imageView) {
    //        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
    //        GlideApp.with(context)
    //                .load(parseURL(url))
    //                .centerCrop()
    //                .error(R.drawable.img_splash)
    //                .placeholder(R.drawable.img_splash)
    //                .transition(transitionOptions)
    //                .into(imageView);
    //    }

    /**
     * 解析 url，判端是不是本地路径
     */
    public static String parseURL(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        File file = new File(url);
        if (file.exists()) {
            return url;
        }
        String remoteUrl;
        if (url.contains("http://") || url.contains("https://")) {
            remoteUrl = url;
        } else {
            remoteUrl = C.ONLINE_UPLOAD_URL + url;
        }
        return remoteUrl;
    }

    public static void clear(Context context, ImageView imageView) {
        GlideApp.with(context).clear(imageView);
    }

}
