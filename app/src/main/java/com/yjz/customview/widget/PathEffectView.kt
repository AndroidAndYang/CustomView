package com.yjz.customview.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yjz.customview.util.Utils

/**
 *@author: YJZ
 *@date: 2019/5/16 15:28
 *@Des:
 **/
class PathEffectView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.style = Paint.Style.STROKE
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(300f,300f,200f,paint)

        val path = Path()
        paint.strokeWidth = 20f
        path.moveTo(200f,200f)
        path.lineTo(300f,300f)
        path.lineTo(400f,200f)
        path.lineTo(500f,300f)

        // DashPathEffect 使用虚线来绘制线条
        val dashPathEffect = DashPathEffect(FloatArray(20) {10f}, Utils.dp2px(10f))

        // CornerPathEffect 将所有的拐角变成圆角
        // paint.strokeWidth = 10f 看拐角需要将画笔宽度放大，不然不好看
        val cornerPathEffect = CornerPathEffect(20f)

        // 把线条进行随机的偏离
        val discretePathEffect = DiscretePathEffect(20f, 10f)

        // PathDashPathEffect 这个类比DashPathEffect 多个path,顾名思义就是使用path来做虚线
        val dash = Path()
        dash.addRect(0f,0f,10f,10f,Path.Direction.CW)
        val pathDashPathEffect = PathDashPathEffect(dash, 40f, 0f, PathDashPathEffect.Style.TRANSLATE)

        // SumPathEffect 组合效果的PathEffect,分别按照两种PathEffect分别对目标进行绘制
        val sumPathEffect = SumPathEffect(dashPathEffect, discretePathEffect)

        // ComposePathEffect 和SumPathEffect 不一样，ComposePathEffect先对一个path进行PathEffect,然后再对另外一个进行PathEffect
        val composePathEffect = ComposePathEffect(dashPathEffect, discretePathEffect)

        paint.pathEffect = composePathEffect

        canvas.drawPath(path,paint)
    }
}