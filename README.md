# TitleBar
## 用法：
#### 第一步：
##### 1、工程根目录build.gradle中
    allprojects {
      repositories {
        maven {
            url "https://jitpack.io"
        }
     }
    }
##### 2、项目build.gradle中
    dependencies {
	        implementation 'com.github.593476071:TitleBar:1.0.4'//选择最新版本
	}
#### 第二步（不是必须）：
##### 开发者可选择在Application中初始化一些默认配置。
##### 注意：在Application中设置的字体大小单位为像素（px）。
      TitleBarOptions.getInstance()
                .setBackground(0xffFF4081) //默认背景颜色
                .immersion(true) //设置是否是沉浸式状态栏
                .setStatusColor(0xffFF4081) //默认状态栏颜色(沉浸式状态栏才有效)
                .setTitleTextSize(DisplayUtils.dp2px(this,18))//默认标题的字体大小(单位为像素 px)
                .setTitleSidesTextSize(DisplayUtils.dp2px(this,12))//默认左右两边的字体大小(单位为像素 px)
                .setTitleTextColor(0xff303F9F) //默认标题的字体颜色
                .setLeftImg(R.mipmap.back); //默认左边和右边文字的大小
#### 第三步：
##### 在布局中使用
##### 注意：在布局中设置的属性优先级大于在Application中的设置的属性。
    <com.wxc.library.TitleBar
        app:title="首页"
        app:hasLeftTextView="true"
        app:titleLeftText="哈哈"
        app:isImmersion="false"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
## 自定义属性说明
    <declare-styleable name="TitleBar">
        <!--标题-->
        <attr name="title" format="string" />
        <!--右侧文本-->
        <attr name="titleRightText" format="string" />
        <!--右侧图标-->
        <attr name="titleRightImg" format="reference"/>
        <!--左侧图标-->
        <attr name="titleLeftImg" format="reference"/>
        <!--是否有返回按钮-->
        <attr name="hasLeftView" format="boolean" />
        <!--是否有返回文字-->
        <attr name="hasLeftTextView" format="boolean"/>
        <!--背景颜色-->
        <attr name="titleBackground" format="color" />
        <!--文本颜色-->
        <attr name="titleTextColor" format="color" />
        <!--左侧文本-->
        <attr name="titleLeftText" format="string"/>
        <!--标题文字大小-->
        <attr name="titleTextSize" format="dimension"/>
        <!--两边字体大小-->
        <attr name="titleSidesTextSize" format="dimension"/>
        <!--是否是沉浸式-->
        <attr name="isImmersion" format="boolean"/>
        <!--状态栏颜色-->
        <attr name="statusColor" format="color"/>
    </declare-styleable>
