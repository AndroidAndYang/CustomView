package com.yjz.customview.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yjz.customview.util.Utils

/**
 * @author: YJZ
 * @date: 2019/5/15 14:53
 * @Des: 仪表盘
 */
class DashboardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val radius = Utils.dp2px(150f)
    private val angle = 120
    private val number : Int = 20
    private var dashPathEffect : PathDashPathEffect
    private val length = Utils.dp2px(100f)

    init {
        paint.strokeWidth = Utils.dp2px(2f)
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#000000")

        val x = width / 2
        val y = height / 2

        // 弧度的Path
        val arcPath = Path()
        val arc = RectF(x - radius,y  - radius,x + radius,y+radius)
        arcPath.addArc(arc,(90 + angle/2).toFloat(), (360 - angle).toFloat())

        // 测量弧度的长度
        val pathMeasure = PathMeasure(arcPath, false)
        val length = pathMeasure.length

        // 画刻度线
        val dash = Path()
        val rectOval = RectF(0f,0f,Utils.dp2px(2f),Utils.dp2px(10f))
        dash.addRect(rectOval,Path.Direction.CW)

        //  (length - Utils.dp2px(2f)) / 20 通过弧度的长度除以数量（number） 获取每个刻度之间的距离
        dashPathEffect = PathDashPathEffect(dash,(length - Utils.dp2px(2f)) / number,0f,PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val x = width / 2
        val y = height / 2

        val rectF = RectF(x - radius,y  - radius,x + radius,y+radius)
        // 弧度
        canvas.drawArc(rectF, (90 + angle/2).toFloat(), (360 - angle).toFloat(),false,paint)
        // 画刻度线，但是它会把之前的弧度覆盖掉
        paint.pathEffect = dashPathEffect

        // 因为画的弧度被dashPathEffect覆盖掉了，所有需要在画一次
        canvas.drawArc(rectF, (90 + angle/2).toFloat(), (360 - angle).toFloat(),false,paint)
        paint.pathEffect = null

        // 画线
        canvas.drawLine(
            x.toFloat(),
            y.toFloat(),
            (Math.cos(Math.toRadians(getAngleFromMark(10).toDouble()))).toFloat() * length + x,
            (Math.sin(Math.toRadians(getAngleFromMark(10).toDouble()))).toFloat() * length + y,
            paint)
    }

    /**
     * 获取某个刻度的角度
     * @param mark 刻度
     */
    private fun getAngleFromMark(mark: Int): Int {
        return (90f + angle.toFloat() / 2 + (360 - angle.toFloat()) / 20 * mark).toInt()
    }
}
