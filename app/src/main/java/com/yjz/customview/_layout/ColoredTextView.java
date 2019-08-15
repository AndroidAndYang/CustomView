package com.yjz.customview._layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.RequiresApi;
import com.yjz.customview.util.Utils;

import java.util.Random;

/**
 * @author: YJZ
 * @date: 2019/8/14 10:13
 * @Des: 随机改变大小的TextView
 **/
public class ColoredTextView extends androidx.appcompat.widget.AppCompatTextView {

    public static final int[] TEXT_SIZE= {16,22,28};

    private static final int[] COLORS = {
            Color.parseColor("#E91E63"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#795548")
    };

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final Random random = new Random();
    private static final int CORNER_RADIUS = (int) Utils.INSTANCE.dp2px(4);
    private static final int X_PADDING = (int) Utils.INSTANCE.dp2px(16);
    private static final int Y_PADDING = (int) Utils.INSTANCE.dp2px(8);

    {
        setTextColor(Color.WHITE);
        paint.setColor(COLORS[random.nextInt(COLORS.length)]);
        paint.setTextSize(TEXT_SIZE[random.nextInt(3)]);
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING);
    }

    public ColoredTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0f, 0f, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS,paint);
        super.onDraw(canvas);
    }
}
