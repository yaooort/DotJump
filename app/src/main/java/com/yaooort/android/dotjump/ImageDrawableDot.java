package com.yaooort.android.dotjump;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.DecelerateInterpolator;


public class ImageDrawableDot extends Drawable implements Animatable {
    // 画圆的画笔
    private Paint mRingPaint;
    // 圆颜色
    private int mRingColor;
    //绘制多少小红点
    private int dotCount;
    //变大变小的百分比
    private float cuFx = 0f;
    // 动画控制
    private ValueAnimator mValueAnimator;

    public ImageDrawableDot(int numDot) {
        dotCount = numDot;
        mRingColor = 0xFFFA0A82;
        initVariable();
        initAnimation();
    }

    private void initAnimation() {
        mValueAnimator = new ValueAnimator();
        mValueAnimator.setFloatValues(0f, dotCount);
        mValueAnimator.setTarget(this);
        long animatableTime = dotCount*300;
        mValueAnimator.setDuration(animatableTime);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 监听属性动画并进行重绘
                cuFx = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });
        // 设置动画无限循环
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    private void initVariable() {
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bound = getBounds();
        int boundW = bound.width();
        //平分区域
        int dot_w = (boundW / 2) / (dotCount + 2);
        //圆的半径
        float radius = dot_w / 4;
        Rect rect = new Rect();
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
            canvas.drawCircle(startDot, bound.centerY() - (rect.height() * 2), cra, mRingPaint);
        }
    }



    @Override
    public void setAlpha(int alpha) {
        //　设置透明度
        mRingPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        //　设置颜色过滤
        mRingPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        //　设置颜色格式
        return getOpacityFromColor(this.mRingPaint.getColor());
    }

    /**
     * Gets the opacity from a color. Inspired by Android ColorDrawable.
     * @param color
     * @return opacity expressed by one of PixelFormat constants
     */
    public static int getOpacityFromColor(int color) {
        int colorAlpha = color >>> 24;
        if (colorAlpha == 255) {
            return PixelFormat.OPAQUE;
        } else if (colorAlpha == 0) {
            return PixelFormat.TRANSPARENT;
        } else {
            return PixelFormat.TRANSLUCENT;
        }
    }

    @Override
    public void start() {
        // 启动动画
        if (mValueAnimator != null)
            mValueAnimator.start();
    }

    @Override
    public void stop() {
        // 停止动画
        if (mValueAnimator != null)
            mValueAnimator.end();
    }

    @Override
    public boolean isRunning() {
        //　判断动画是否运行
        return mValueAnimator != null && mValueAnimator.isRunning();
    }
}
