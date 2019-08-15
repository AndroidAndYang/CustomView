package com.yjz.customview._layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.yjz.customview.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: YJZ
 * @date: 2019/8/6 11:24
 * @Des: 标签布局
 **/
public class TagLayout extends ViewGroup {

    private List<Rect> childrenBounds = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static final int MARGIN = (int)Utils.INSTANCE.dp2px(10f);

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getChildCount() < 0) {
            return;
        }

        // 记录使用的宽度
        int widthUsed = 0;
        // 记录使用的高度
        int heightUsed = 0;
        // 每一行中最大的高度
        int lineMaxHeight = 0;
        // 记录每一行的宽度使用
        int lineWidthUsed = 0;

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);

        // 遍历该View下的所有子view
        for(int i = 0; i< getChildCount();i++){
            // 获取子view
            View child = getChildAt(i);
            // 测量子view
            // widthUsed传0表示的是父view不控制子view的大小，这样就可以避免在子view宽度不够时换行问题。
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec,heightUsed + MARGIN);
            // 判断是否超过父view的宽度，超过则换行
            if (widthSpecMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.getMeasuredWidth() > widthSpecSize){
                // 换行，宽度使用从0开始，如果设置了margin则从margin值开始
                lineWidthUsed = MARGIN;
                // 高度位置需要加一个子view的高度，达到换行的效果，如果设置了margin则加上margin
                heightUsed += child.getMeasuredHeight() + MARGIN;
                lineMaxHeight = 0;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec,heightUsed);
            }

            // 如果是第一個，设置了margin的话
            if (i == 0){
                lineWidthUsed += MARGIN;
            }

            // 添加到集合中
            childrenBounds.add(new Rect(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(),
                    heightUsed + child.getMeasuredHeight()));

            // 累加使用的宽度
            lineWidthUsed += child.getMeasuredWidth() + MARGIN;

            // 这里使用max是因为这里是for循环操作，如果不记录最大值，当最后一行 宽度小
            // 于width MATCH_PARENT的时候，就会出现每一行的显示都和最后一行的宽度一样
            widthUsed = Math.max(widthUsed,lineWidthUsed);

            // 计算一行中最高的那一个View
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());
        }

        // view的最大宽度
        int width = widthUsed;
        // 因为默认第一行不计入高度，所以heightUsed为从第二行开始的高度算起的高度。加上lineMaxHeight即为整个内容的高度
        int height = lineMaxHeight + heightUsed;
        // 将view的最大宽度和高度设置进去
        setMeasuredDimension(width,height);
    }

    // 对ViewGroup中的子view放置具体的位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() < 0) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            Rect rect = childrenBounds.get(i);
            View childView = getChildAt(i);
            if (i == 0){
                childView.layout(rect.left, rect.top, rect.right, rect.bottom);
            }else {
                childView.layout(rect.left, rect.top, rect.right, rect.bottom);
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
