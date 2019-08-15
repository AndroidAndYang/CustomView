package com.yjz.customview._layout;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author YJZ
 * @date 2019/8/4
 * @description
 */
public class SquareImageView extends AppCompatImageView {

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 如果休要修改view的尺寸应该事在onMeasure方法中，这样就不会出现错乱问题
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int size = Math.max(measuredHeight, measuredWidth);
        setMeasuredDimension(size, size);
    }

    /*
     *    改写view的尺寸应该在onMeasure()方法中，如果绕过onMeasure()方法，直接
     * 在layout中改写的话，会出现布局显示错乱问题。因为修改了控件的大小，父view根本就不知道。
     */
    /*
     * 用于控制view的显示位置
     *
     * @param l view 显示的左边的起点位置
     * @param t view 显示的上边的起点位置
     * @param r view 显示的右边的起点位置
     * @param b view 显示的下边的起点位置
     */
    // @Override
    // public void layout(int l, int t, int r, int b) {
    //     int width = r - l;
    //     int height = b - t;
    //     int size = Math.max(width, height);
    //     super.layout(l, t, size, size);
    // }
}
