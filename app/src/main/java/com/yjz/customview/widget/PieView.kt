package com.yjz.customview.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.yjz.customview.util.Utils

/**
 *@author: YJZ
 *@date: 2019/5/15 14:51
 *@Des: 扇形图
 **/
class PieView(context: Context, attrs: AttributeSet) : View(context, attrs){

    /**
     * 创建画笔并开启抗锯齿
     */
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 半径
     */
    private val radius = Utils.dp2px(150f)
    /**
     *  角度
     */
    private val angleList = listOf(60f,80f,100f,120f)
    /**
     * 颜色
     */
    private val colorList = listOf("#ff0000","#00ff00","#0000ff","#ff00ff")
    /**
     *  弧度之间的空隙
     */
    private val gap = Utils.dp2px(2f)

    init {
        paint.strokeWidth = Utils.dp2px(3f)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val x = width / 2
        val y = height / 2
        // 弧度绘制的范围
        val rectF = RectF(x - radius,y  - radius,x + radius,y+radius)
        // 当前角度
        var currentAngle = 0f
        for (index in angleList.indices){
            // 保存状态
            canvas.save()
            paint.color = Color.parseColor(colorList[index])
            // 将绘制的行进行平移
            canvas.translate(
                Math.cos(Math.toRadians(((currentAngle + angleList[index]/2).toDouble()))).toFloat() * gap,
                Math.sin(Math.toRadians(((currentAngle + angleList[index]/2).toDouble()))).toFloat() * gap)
            // 画弧
            canvas.drawArc(rectF,currentAngle,angleList[index],true,paint)
            currentAngle += angleList[index]
            // 释放
            canvas.restore()
        }
    }
}