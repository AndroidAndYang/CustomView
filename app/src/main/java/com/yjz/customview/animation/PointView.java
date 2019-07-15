package com.yjz.customview.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.yjz.customview.util.Utils;

/**
 * @author YJZ
 * @date 2019/7/15
 * @description 当属性不是基本属性时做属性动画
 */
public class PointView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Point point = new Point();

    {
        point.set((int) Utils.INSTANCE.dp2px(100), (int) Utils.INSTANCE.dp2px(100));
        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(Utils.INSTANCE.dp2px(10));
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(point.x, point.y, paint);
    }
}
