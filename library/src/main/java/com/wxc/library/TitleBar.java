package com.wxc.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
    private RelativeLayout titleBarView;
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
    /**
     * 默认TitleBar背景
     */
    private static final int DEFAULT_BACKGROUND = 0x00000000;
    /**
     * 默认字体颜色
     */
    private static final int DEFAULT_TEXT_COLOR = 0xffffffff;
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
        RelativeLayout rightLy = this.findViewById(R.id.titleBar_rightLy);

        leftLy.setOnClickListener(this);
        rightLy.setOnClickListener(this);
    }

    private void initData(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        String titleText = array.getString(R.styleable.TitleBar_title);
        String leftText = array.getString(R.styleable.TitleBar_titleLeftText);
        String rightText = array.getString(R.styleable.TitleBar_titleRightText);
        leftImg = array.getResourceId(R.styleable.TitleBar_titleLeftImg, R.drawable.back);
        int rightImg = array.getResourceId(R.styleable.TitleBar_titleRightImg, -1);
        hasLeftView = array.getBoolean(R.styleable.TitleBar_hasLeftView, true);
        boolean hasLeftTextView = array.getBoolean(R.styleable.TitleBar_hasLeftTextView, false);
        int titleBackground = array.getColor(R.styleable.TitleBar_titleBackground, DEFAULT_BACKGROUND);
        int titleTextColor = array.getColor(R.styleable.TitleBar_titleTextColor, DEFAULT_TEXT_COLOR);
        array.recycle();

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
