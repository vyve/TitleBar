package com.wxc.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 标题
 * Created by 王新超 on 2018/3/12.
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {
    private Context context;
    /**
     * 整个TitleBar
     */
    private LinearLayout titleBarView;
    /**
     * 左侧文字
     */
    private TextView leftTextView;
    /**
     * 右侧文字
     */
    private TextView rightTextView;
    /**
     * 左侧图片
     */
    private ImageView leftImgView;
    /**
     * 右侧图片
     */
    private ImageView rightImgView;
    /**
     * 标题文字
     */
    private TextView titleTextView;
    private RelativeLayout leftLy;
    private TitleBarLeftClick leftListener;
    private TitleBarRightClick rightListener;
    /**
     * 是否显示左侧整体View
     */
    private boolean hasLeftView;
    /**
     * 左侧图片资源id
     */
    private int leftImg;
    /**
     * 点击右侧 跳转的Activity
     */
    private Class<?> rightActivity;
    /**
     * 状态栏高度
     */
    private int statusBarHeight;
    /**
     * 是否是沉浸式状态栏
     */
    private boolean isImmersion;
    /**
     * 用来占据状态栏的View
     */
    private TextView statusView;
    /**
     * 状态栏颜色
     */
    private int statusColor;
    /**
     * 默认TitleBar背景
     */
    private int mDefaultBackgroundColor;
    /**
     * 默认字体颜色
     */
    private int mDefaultTitleTextColor;
    /**
     * 默认标题字体大小
     */
    private int mDefaultTitleTextSize;
    /**
     * 默认两边字体大小
     */
    private int mDefaultSidesTextSize;
    /**
     * 默认左边图片
     */
    private int mDefaultLeftImg;
    /**
     * 默认状态栏颜色
     */
    private int mDefaultStatusColor;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initView();

        getDefaultConfig();

        initData(attrs);
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.widget_titlebar, this);
        titleBarView = this.findViewById(R.id.titleBar);
        leftTextView = this.findViewById(R.id.titleBar_leftText);
        rightTextView = this.findViewById(R.id.titleBar_rightText);
        leftImgView = this.findViewById(R.id.titleBar_leftImg);
        rightImgView = this.findViewById(R.id.titleBar_rightImg);
        titleTextView = this.findViewById(R.id.titleBar_title);
        leftLy = this.findViewById(R.id.titleBar_leftLy);
        statusView = this.findViewById(R.id.titleBar_status);
        RelativeLayout rightLy = this.findViewById(R.id.titleBar_rightLy);

        leftLy.setOnClickListener(this);
        rightLy.setOnClickListener(this);
    }

    private void initData(AttributeSet attrs) {
        //状态栏高度
        statusBarHeight = getStatusBarHeight(context);
        //获取自定义配置
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        String titleText = array.getString(R.styleable.TitleBar_title);
        String leftText = array.getString(R.styleable.TitleBar_titleLeftText);
        String rightText = array.getString(R.styleable.TitleBar_titleRightText);
        int titleTextSize = array.getDimensionPixelSize(R.styleable.TitleBar_titleTextSize,mDefaultTitleTextSize);
        int titleSidesTextSize = array.getDimensionPixelSize(R.styleable.TitleBar_titleSidesTextSize,mDefaultSidesTextSize);
        leftImg = array.getResourceId(R.styleable.TitleBar_titleLeftImg, mDefaultLeftImg);
        int rightImg = array.getResourceId(R.styleable.TitleBar_titleRightImg, -1);
        hasLeftView = array.getBoolean(R.styleable.TitleBar_hasLeftView, true);
        boolean hasLeftTextView = array.getBoolean(R.styleable.TitleBar_hasLeftTextView, false);
        int titleBackground = array.getColor(R.styleable.TitleBar_titleBackground, mDefaultBackgroundColor);
        int titleTextColor = array.getColor(R.styleable.TitleBar_titleTextColor, mDefaultTitleTextColor);
        statusColor = array.getColor(R.styleable.TitleBar_statusColor, mDefaultStatusColor);
        isImmersion = array.getBoolean(R.styleable.TitleBar_isImmersion, isImmersion);
        array.recycle();

        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleTextSize);
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSidesTextSize);
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSidesTextSize);
        titleTextView.setText(titleText);
        titleTextView.setTextColor(titleTextColor);
        leftTextView.setTextColor(titleTextColor);
        rightTextView.setTextColor(titleTextColor);

        titleBarView.setBackgroundColor(titleBackground);

        if (rightText != null) {
            rightTextView.setVisibility(VISIBLE);
            rightImgView.setVisibility(GONE);
            rightTextView.setText(rightText);
        }
        if (rightImg != -1) {
            rightImgView.setVisibility(VISIBLE);
            rightTextView.setVisibility(GONE);
            rightImgView.setImageResource(rightImg);
        }

        if (hasLeftView) {
            leftLy.setVisibility(VISIBLE);
            leftImgView.setImageResource(leftImg);
            if (hasLeftTextView) {
                leftTextView.setVisibility(VISIBLE);
                leftTextView.setText(leftText);
            } else {
                leftTextView.setVisibility(GONE);
            }
        } else {
            leftLy.setVisibility(GONE);
        }

        setImmersion(isImmersion);


    }

    /**
     * 获取默认配置
     */
    private void getDefaultConfig() {
        mDefaultBackgroundColor = TitleBarOptions.getInstance().backgroundColor;
        mDefaultTitleTextColor = TitleBarOptions.getInstance().titleTextColor;
        mDefaultTitleTextSize = TitleBarOptions.getInstance().titleTextSize;
        mDefaultLeftImg = TitleBarOptions.getInstance().leftImg;
        mDefaultStatusColor=TitleBarOptions.getInstance().statusColor;
        mDefaultSidesTextSize = TitleBarOptions.getInstance().titleSidesTextSize;
        isImmersion = TitleBarOptions.getInstance().isImmersion;
    }

    /**
     * 设置是否是沉浸式状态栏
     */
    public void setImmersion(boolean immersion) {
        try {
            isImmersion = immersion;
            if (isImmersion) {
                statusView.setVisibility(VISIBLE);
                //沉浸式状态栏
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = ((Activity) context).getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                            | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(0x00000000);
                }

                statusView.setHeight(statusBarHeight);
                statusView.setBackgroundColor(statusColor);
            } else {
                statusView.setVisibility(GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    /**
     * 设置右侧View跳转的Activity
     * 在不需要传参数的情况下更简洁
     * 不要与setOnRightViewClickListener混用
     *
     * @param activity 目标Activity
     */
    public void setOnRightClickTo(Class<?> activity) {
        this.rightActivity = activity;
    }

    /**
     * 注册左侧布局点击事件
     */
    public void setOnLeftViewClickListener(TitleBarLeftClick listener) {
        this.leftListener = listener;
    }

    /**
     * 注册右侧布局点击事件
     */
    public void setOnRightViewClickListener(TitleBarRightClick listener) {
        this.rightListener = listener;
    }

    @Override
    public void onClick(View view) {
        try {
            int i = view.getId();
            if (i == R.id.titleBar_leftLy) {
                if (hasLeftView) {
                    if (leftListener != null) {
                        leftListener.onTitleBarClick();
                    } else if (leftImg == R.drawable.back) {
                        ((Activity) context).finish();
                    }
                }

            } else if (i == R.id.titleBar_rightLy) {
                if (rightListener != null) {
                    rightListener.onTitleBarClick();
                } else {
                    if (rightActivity != null) {
                        context.startActivity(new Intent(context, rightActivity));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public interface TitleBarLeftClick {
        /**
         * 左侧按钮点击
         */
        void onTitleBarClick();
    }

    public interface TitleBarRightClick {
        /**
         * 右侧按钮点击
         */
        void onTitleBarClick();
    }
}
