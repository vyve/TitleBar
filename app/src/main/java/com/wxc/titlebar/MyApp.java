package com.wxc.titlebar;

import android.app.Application;

import com.wxc.library.TitleBarOptions;

/**
 * Created by 王新超 on 2018/6/19.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TitleBarOptions.getInstance()
                //.setBackground(0xffFF4081) //默认背景颜色
                .immersion(true) //设置是否是沉浸式状态栏
                .setStatusColor(0xffFF4081) //默认状态栏颜色(沉浸式状态栏才有效)
                .setTitleTextSize(DisplayUtils.dp2px(this,18))//默认标题的字体大小(单位为像素 px)
                .setTitleSidesTextSize(DisplayUtils.dp2px(this,12))//默认左右两边的字体大小(单位为像素 px)
                .setTitleTextColor(0xff303F9F) //默认标题的字体颜色
                .setLeftImg(R.mipmap.back)//默认左边图片资源
                .setBackgroundDrawable(R.mipmap.timg); //设置背景图片
    }
}
