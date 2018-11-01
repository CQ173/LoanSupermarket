package com.hackerfj.loansupermarket.view.widget;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hackerfj.loansupermarket.util.network.UrlUtil;
import com.youth.banner.loader.ImageLoader;



public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //用fresco加载图片简单用法，记得要写下面的createImageView方法
//        Glide.with(context).load(path).into(imageView);
        Uri uri = Uri.parse((String) UrlUtil.BANNER_URL +  path);
        imageView.setImageURI(uri);
    }
    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        return simpleDraweeView;
    }
}
