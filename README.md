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
	        implementation 'com.github.593476071:TitleBar:1.0.7'//选择最新版本
	}
#### 第二步（不是必须）：
##### 开发者可选择在Application中初始化一些默认配置。
##### 注意：在Application中设置的字体大小单位为像素（px）。
      TitleBarOptions.getInstance()
                //.setBackground(0xffFF4081) //默认背景颜色
                .immersion(true) //设置是否是沉浸式状态栏
                .setStatusColor(0xffFF4081) //默认状态栏颜色(沉浸式状态栏才有效)
                .setTitleTextSize(DisplayUtils.dp2px(this,18))//默认标题的字体大小(单位为像素 px)
                .setTitleSidesTextSize(DisplayUtils.dp2px(this,12))//默认左右两边的字体大小(单位为像素 px)
                .setTitleTextColor(0xff303F9F) //默认标题的字体颜色
                .setLeftImg(R.mipmap.back)//默认左边图片资源
                .setBackgroundDrawable(R.mipmap.timg); //设置背景图片
#### 第三步：
##### 在布局中使用
##### 注意：1、在布局中设置的属性优先级大于在Application中的设置的属性。
##### 	2、在设置backgroundDrawable 和 backgroundColor时，drawable无论在什么情况下优先级始终大于color
##### 		eg：如果在application中设置里默认drawable，则在布局里设置状态栏color和背景color都将无效。
##### 		解决方案：在布局中添加属性 app:colorHighPriority="true" ，这样color将会覆盖drawable。
##### 	3、属性titleHeight的值不带单位，默认是dp值，意思为除去状态栏剩下的标题的高度。
     <com.wxc.library.TitleBar
        android:id="@+id/titleBar"
        app:title="首页"
        app:titleBackground="#0f0"
        app:hasLeftTextView="true"
        app:titleLeftText="返回"
        app:isImmersion="true"
        app:titleTextSize="20dp"
        app:titleHeight="100"
        app:statusColor="@color/colorAccent"
        app:colorHighPriority="true"
        app:contentLayout="@layout/layout_title"
        android:layout_width="match_parent"
        android:layout_height="100dp"/>
## 自定义布局
#### 新建布局文件layout_title.xml
##### 注意：新建的布局文件的跟布局高度不能超过自定义添加的app:titleHeight="100"的高度，控件默认高度为35。
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
      android:layout_width="match_parent"
      android:layout_height="match_parent">

    	<TextView
          android:layout_width="match_parent"
          android:layout_height="30dp"
          android:background="#0f0"
          android:gravity="center"
          android:text="哈哈哈哈哈哈" />
	</LinearLayout>
#### 添加属性 app:contentLayout="@layout/layout_title"
#### 在页面中获取该布局文件，自定义处理事件。
	View contentLayout = titleBar.getContentLayout();
        contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"哈哈",Toast.LENGTH_SHORT).show();
            }
        });
## 自定义属性说明
    <declare-styleable name="TitleBar">
        <!--标题-->
        <attr name="title" format="string" />
        <!--除去状态栏以后的布局高度  单位是   dp-->
        <attr name="titleHeight" format="integer"/>
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
        <!--背景图片-->
        <attr name="titleBackgroundDrawable" format="reference"/>
        <!--自定义布局-->
        <attr name="contentLayout" format="reference"/>
        <!--color的优先级是否比drawable高-->
        <attr name="colorHighPriority" format="boolean"/>
    </declare-styleable>

