package com.yjz.customview.touch;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.OverScroller;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import com.yjz.customview.R;

/**
 * @author YJZ
 * @date 2019/8/26
 * @description
 */
public class ScaleLableImage extends AppCompatImageView implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {

    private Paint paint;
    private Bitmap bitmap;
    /**
     * 用于控制是否是放大还是缩小
     */
    private boolean isBigOrSmall;

    /**
     * 缩放大小的系数
     */
    private float smallScale = 0f;
    private float bigScale = 0f;

    /**
     * 初始XY的偏移
     */
    private float originalOffsetX;
    private float originalOffsetY;
    /**
     * 拖动时XY的偏移
     */
    private float offsetX;
    private float offsetY;
    /**
     * 滑动触摸帮助类
     */
    private final GestureDetectorCompat gestureDetector;

    /**
     * 惯性滑动：OverScroller与Scroller都可以做，但是Scroller没有OverScroller流畅
     */
    OverScroller overScroller;

    private ObjectAnimator scaleObjectAnimator;

    /**
     * 用于将图片放的更大，好展示效果
     */
    private float OVER_SCALE_FACTOR = 1.5f;

    /**
     * 用于控制属性动画的缩放
     */
    private float currentScale;

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    private ObjectAnimator getScaleObjectAnimator() {
        if (scaleObjectAnimator == null) {
            scaleObjectAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0, 1);
        }
        return scaleObjectAnimator;
    }

    public ScaleLableImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 可用于替代 new GestureDetectorCompat 这个类只需要实现自己关系的方法即可
        // new GestureDetector.SimpleOnGestureListener(){
        //     @Override
        //     public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //         return super.onScroll(e1, e2, distanceX, distanceY);
        //     }
        //     @Override
        //     public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //         return super.onFling(e1, e2, velocityX, velocityY);
        //     }
        //     @Override
        //     public boolean onDoubleTap(MotionEvent e) {
        //         return super.onDoubleTap(e);
        //     }
        // };
        gestureDetector = new GestureDetectorCompat(context,this);
        gestureDetector.setOnDoubleTapListener(this);
        overScroller = new OverScroller(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = (float) (getWidth() - bitmap.getWidth()) / 2;
        originalOffsetY = (float) (getHeight() - bitmap.getHeight()) / 2;

        // 计算缩放的比例
        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(offsetX, offsetY);
        float scale = smallScale + (bigScale - smallScale) * currentScale;
        canvas.scale(scale, scale, (float) getWidth() / 2, (float) getHeight() / 2);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.e("TAG","onSingleTapUp");
        return false;
    }

    /**
     * 手指按下然后拖动
     *
     * @param down      手指按下的事件
     * @param event     滑动事件
     * @param distanceX 上一次的X位置 - 当前的X位置
     * @param distanceY 上一次的Y - 当前的Y位置
     */
    @Override
    public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
        if (isBigOrSmall) {
            offsetX -= distanceX;
            // 对临界值做判断
            // 往右托
            offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2f);
            // 往左托
            offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2f);
            offsetY -= distanceY;
            // 往下移
            offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2f);
            // 往上移
            offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2f);
            invalidate();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.e("TAG","onLongPress");
    }

    /**
     * 惯性滑动
     *
     * @param down
     * @param event
     * @param velocityX x轴滑动的速度
     * @param velocityY y轴滑动的速度
     * @return
     */
    @Override
    public boolean onFling(MotionEvent down, MotionEvent event, float velocityX, float velocityY) {
        if (isBigOrSmall) {
            // overX Y 表示的超出的距离
            overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                    -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                    (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                    -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                    (int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                    100, 100);
            // 使用ViewCompat表示为低版本Android做兼容
            ViewCompat.postOnAnimation(this, this);
        }
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        isBigOrSmall = !isBigOrSmall;
        if (isBigOrSmall) {
            getScaleObjectAnimator().start();
        } else {
            offsetX = 0;
            offsetY = 0;
            getScaleObjectAnimator().reverse();
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }


    @Override
    public void run() {
        // overScroller.computeScrollOffset() 判断惯性滑动的最后一帧是否结束
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.getCurrX();
            offsetY = overScroller.getCurrY();
            invalidate();
            ViewCompat.postOnAnimation(this, this);
        }
    }
}
