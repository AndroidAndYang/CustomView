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
 * @Des: 使用camera绘制时,只需要记住反者就行
 **/
public class FancyFlipView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static float IMAGE_WIDTH  = Utils.INSTANCE.dp2px(200f);
    private static float PADDING  = Utils.INSTANCE.dp2px(100f);

    private Camera camera = new Camera();


    float topFlip = 0f;
    float bottomFlip = 0f;
    float flipRotation = 0f;

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

    public FancyFlipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制上半部分
        canvas.save();
        canvas.translate(PADDING  + IMAGE_WIDTH  / 2, PADDING  + IMAGE_WIDTH  / 2);
        canvas.rotate(-flipRotation);
        // 这里进行save操作是因为做动画会一直重绘VIEW对camera.rotateX()进行改变.所以需要进行一个save和restore的操作
        camera.save();
        camera.rotateX(topFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        // * 1.44 是因为clipRect 截取时会超出图片原有的大小,所以要进行放大,但是放大的倍数不会超过根号2
        canvas.clipRect(-(PADDING + IMAGE_WIDTH / 2) * 1.44f, - (IMAGE_WIDTH / 2)* 1.44f, (PADDING + IMAGE_WIDTH / 2) * 1.44f, 0);
        canvas.rotate(flipRotation);
        canvas.translate(-(PADDING  + IMAGE_WIDTH  / 2), -(PADDING  + IMAGE_WIDTH  / 2));
        canvas.drawBitmap(BitmapUtils.INSTANCE.getAvatar(getResources(), (int) IMAGE_WIDTH ), PADDING , PADDING , paint);
        canvas.restore();

        // 绘制下半部分
        canvas.save();
        // 7.移会直原来的位置
        canvas.translate(PADDING  + IMAGE_WIDTH  / 2, PADDING  + IMAGE_WIDTH  / 2);
        // 6.将画布旋转回来
        canvas.rotate(-flipRotation);
        camera.save();
        // 5. 将camera进行旋转
        camera.rotateX(bottomFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        // 4.裁剪部分图片
        canvas.clipRect(-(PADDING + IMAGE_WIDTH / 2) * 1.44f, 0 * 1.44f, (PADDING + IMAGE_WIDTH / 2) * 1.44f, IMAGE_WIDTH / 2 * 1.44f);
        // 3.将画布进行旋转
        canvas.rotate(flipRotation);
        // 2.平移到原点
        canvas.translate(-(PADDING  + IMAGE_WIDTH  / 2), -(PADDING  + IMAGE_WIDTH  / 2));
        // 1.绘制bitmap
        canvas.drawBitmap(BitmapUtils.INSTANCE.getAvatar(getResources(), (int) IMAGE_WIDTH ), PADDING , PADDING , paint);
        canvas.restore();
    }
}
