# DotJump
Android 自定义View实现小点跳动


### 跳动效果
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)

### Android效果图
![](https://raw.githubusercontent.com/yaooort/DotJump/master/app/lib/ia5df-r1k0a.gif)

> 代码比较简单，方便修改

```java
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
```

