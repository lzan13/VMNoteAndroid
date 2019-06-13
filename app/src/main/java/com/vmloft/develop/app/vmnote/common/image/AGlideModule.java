package com.vmloft.develop.app.vmnote.common.image;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.vmloft.develop.app.vmnote.common.AConstants;

/**
 * Created by lzan13 on 2017/11/30.
 * 集成 Glide 加载图片自己实现的 GlideModule 类
 */
@GlideModule
public class AGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //        super.applyOptions(context, builder);
        int size = 200 * 1024 * 1024;
        String dir = AConstants.CACHE_IMAGES;
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, dir, size));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);

    }

    @Override
    public boolean isManifestParsingEnabled() {
        return super.isManifestParsingEnabled();
    }

}

