package com.yjz.customview.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yjz.customview.util.Utils

/**
 *@author: YJZ
 *@date: 2019/5/15 17:59
 *@Des:进度条带文字，主要是学习文字的测量
 **/
class SportView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val radius = Utils.dp2px(150f)

    private val circleColor = Color.parseColor("#90A4AE")
    private val progressColor = Color.parseColor("#FF4081")

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val x = (width / 2).toFloat()
        val y = (height / 2).toFloat()

        // 绘制圆
        paint.color = circleColor
        paint.strokeWidth = Utils.dp2px(15f)
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(x,y,radius,paint)

        // 绘制进度
        paint.color = progressColor
        // 设置绘制的进度条带圆形
        paint.strokeCap = Paint.Cap.ROUND
        val rectF = RectF(x - radius,y-radius,x+ radius,y+radius)
        canvas.drawArc(rectF,-90f,160f,false,paint)

        // 绘制文字
        paint.textAlign = Paint.Align.CENTER
        paint.style = Paint.Style.FILL
        paint.textSize = Utils.dp2px(100f)

        val textRect = Rect()
        paint.getTextBounds("45%",0,3,textRect)

        canvas.drawText("45%",0,3,x,y - (textRect.top + textRect.bottom) / 2 ,paint)

        canvas.drawLine(0f,y,width.toFloat(),y,paint)
    }
}