package com.yjz.customview.animation;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.yjz.customview.util.BitmapUtils;
import com.yjz.customview.util.Utils;

/**
 * @author: YJZ
 * @date: 2019/7/11 19:19
 * @Des:
 **/
public class FancyFlipView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Camera camera = new Camera();

    private float imgWidth = Utils.INSTANCE.dp2px(200f);

    private float margin = Utils.INSTANCE.dp2px(100f);

    private float topFlip = 0f;
    private float bottomFlip = 0f;
    private float flipRotation = 0f;

    {
        camera.setLocation(0, 0, Utils.INSTANCE.getZForCamera());
    }

    public float getTopFlip() {
        return topFlip;
    }

    public void setTopFlip(float topFlip) {
        this.topFlip = topFlip;
        invalidate();
    }

    public float getBottomFlip() {
        return bottomFlip;
    }

    public void setBottomFlip(float bottomFlip) {
        this.bottomFlip = bottomFlip;
        invalidate();
    }

    public float getFlipRotation() {
        return flipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        this.flipRotation = flipRotation;
        invalidate();
    }

    public FancyFlipView(Context context) {
        super(context);
    }

    public FancyFlipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FancyFlipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制上半部分
        canvas.save();
        canvas.translate((margin + imgWidth / 2),(margin + imgWidth / 2));
        canvas.rotate(-flipRotation);
        camera.rotateX(topFlip);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-(margin + imgWidth / 2) * 1.6f, - (imgWidth / 2)* 1.6f, (margin + imgWidth / 2) * 1.6f, 0);
        canvas.rotate(flipRotation);
        canvas.translate(-(margin + imgWidth / 2),-(margin + imgWidth / 2));
        canvas.drawBitmap(BitmapUtils.INSTANCE.getAvatar(getResources(), (int)imgWidth), margin, margin,paint);
        canvas.restore();

        // 绘制下半部分
        canvas.save();
        canvas.translate((margin + imgWidth / 2),(margin + imgWidth / 2));
        canvas.rotate(-flipRotation);
        camera.rotateX(bottomFlip);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-(margin + imgWidth / 2) * 1.6f, 0 * 1.6f, (margin + imgWidth / 2) * 1.6f, imgWidth / 2 * 1.6f);
        canvas.rotate(flipRotation);
        canvas.translate(-(margin + imgWidth / 2),-(margin + imgWidth / 2));
        canvas.drawBitmap(BitmapUtils.INSTANCE.getAvatar(getResources(), (int)imgWidth), margin, margin,paint);
        canvas.restore();
    }
}
