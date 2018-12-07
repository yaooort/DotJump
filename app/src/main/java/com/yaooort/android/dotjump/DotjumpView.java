package com.yaooort.android.dotjump;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class DotjumpView extends View {


    // 画圆的画笔
    private Paint mRingPaint;
    // 圆颜色
    private int mRingColor;
    //绘制多少小红点
    private int dotCount = 5;
    //变大变小的百分比
    private float cuFx = 0f;
    //一周期的时间
    private int animatableTime = 3000;
    // 动画控制
    private ValueAnimator mValueAnimator;

    public DotjumpView(Context context) {
        super(context);
        initAttrs();
    }

    public DotjumpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public DotjumpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs() {
        mRingColor = 0xFFFA0A82;
        initVariable();
        initAnimation();
    }

    private void initAttrs(AttributeSet attrs) {
        mRingColor = 0xFFFA0A82;
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DotjumpView);
        mRingColor = a.getColor(R.styleable.DotjumpView_color_dot, mRingColor);
        dotCount = a.getInteger(R.styleable.DotjumpView_num_dot, dotCount);
        animatableTime = a.getInteger(R.styleable.DotjumpView_time_show, animatableTime);

        initVariable();
        initAnimation();
    }

    private void initVariable() {
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    private void initAnimation() {
        mValueAnimator = new ValueAnimator();
        mValueAnimator.setFloatValues(0f, dotCount);
        mValueAnimator.setTarget(this);
        mValueAnimator.setDuration(animatableTime);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 监听属性动画并进行重绘
                cuFx = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        // 设置动画无限循环
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float boundW = getMeasuredWidth();
        //平分区域
        float dot_w = boundW / ((dotCount + 2) * 2);
        //圆的半径
        float radius = dot_w / 2;
        mRingPaint.setColor(mRingColor);
        for (int i = 0; i < dotCount; i++) {
            float cra = radius;
            //起始点
            float startDot = (dot_w * (i * 2 + 3));
            if (i == Math.floor(cuFx)) {
                //变大
                cra = (float) (radius * (1.0f + cuFx - Math.floor(cuFx)));
            } else if (i == Math.floor(cuFx) - 1) {
                //变小
                cra = (float) (radius * (2.0f - (cuFx - Math.floor(cuFx))));
            } else if (Math.floor(cuFx) == 0 && i == dotCount - 1) {
                //变小
                cra = (float) (radius * (2.0f - (cuFx - Math.floor(cuFx))));
            }
            canvas.drawCircle(startDot, getMeasuredHeight() / 2, cra, mRingPaint);
        }
    }

    public void start() {
        // 启动动画
        if (mValueAnimator != null && !isRunning())
            mValueAnimator.start();
    }

    public void stop() {
        // 停止动画
        if (isRunning())
            mValueAnimator.end();
    }

    public boolean isRunning() {
        //　判断动画是否运行
        return mValueAnimator != null && mValueAnimator.isRunning();
    }
}
