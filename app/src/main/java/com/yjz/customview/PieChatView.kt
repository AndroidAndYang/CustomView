package com.yjz.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.View
import com.yjz.customview.util.Utils
import android.R.attr.radius


/**
 * @author: YJZ
 * @date: 2019/6/24 11:13
 * @Des:
 */
class PieChatView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    /**
     * 扇形 paint
     */
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 长度线 Paint
     */
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 文字描述 Paint
     */
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 弧度绘制的范围
     */
    private var rectF = RectF()

    private var pieWidth: Int = 0
    private var pieHeight: Int = 0

    /**
     * 半径
     */
    private var radius: Int = 0

    /**
     *  角度
     */
    private val pieDataList = arrayListOf(
        PieceDataHolder(10, "#ff0000", "A"),
        PieceDataHolder(20, "#00ff00", "B"),
        PieceDataHolder(30, "#ffff00", "C"),
        PieceDataHolder(40, "#000000", "D")
    )

    private val leftAngelList = arrayListOf<PieceDataHolder>()

    private val rightAngelList = arrayListOf<PieceDataHolder>()

    /**
     *  超出扇形图的长度
     */
    private val overLineLength = Utils.dp2px(12f)

    init {
        paint.strokeWidth = Utils.dp2px(3f)

        linePaint.strokeWidth = Utils.dp2px(1f)
        linePaint.style = Paint.Style.STROKE
        linePaint.color = Color.parseColor("#000000")

        textPaint.textSize = Utils.dp2px(14f)
        textPaint.color = Color.parseColor("#000000")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 当前角度
        var currentAngle = 0f

        // 总和
        val sum = pieDataList.sumBy { it.value }

        for (index in pieDataList.indices) {
            paint.color = Color.parseColor(pieDataList[index].color)
            // 画弧
            val sweepAngel = pieDataList[index].value.toFloat() / sum * 360

            val rotateAngel = currentAngle + sweepAngel / 2
            pieDataList[index].radius = rotateAngel
            if (rotateAngel > 0 && rotateAngel <= 90) {
                rightAngelList.add(pieDataList[index])
            } else if (rotateAngel > 270 && rotateAngel <= 360) {
                rightAngelList.add(pieDataList[index])
            } else {
                leftAngelList.add(pieDataList[index])
            }

            canvas.drawArc(rectF, currentAngle, sweepAngel, true, paint)

            currentAngle += sweepAngel

            // 将绘制的行进行平移
            // canvas.save()
            // canvas.translate(
            //      Math.cos(Math.toRadians(((currentAngle + pieDataList[index]/2).toDouble()))).toFloat() * overLineLength,
            //      Math.sin(Math.toRadians(((currentAngle + pieDataList[index]/2).toDouble()))).toFloat() * overLineLength)
            // canvas.restore()
        }

        drawLeftLineAndText(canvas)

        drawRightLineAndText(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.pieWidth = w
        this.pieHeight = h
        radius = Math.min(pieWidth, pieHeight) / 4
        rectF.set(
            pieWidth.toFloat() / 2 - radius,
            pieHeight.toFloat() / 2 - radius,
            pieWidth.toFloat() / 2 + radius,
            pieHeight.toFloat() / 2 + radius
        )
    }

    /**
     * 绘制右边的线条和文字
     */
    private fun drawRightLineAndText(canvas: Canvas) {

        val size = rightAngelList.size

        if (size > 0) {

            val pieRect = rectF.bottom - rectF.top

            val itemHeight = pieRect / size

            for (index in rightAngelList.indices) {

                val path = Path()
                path.close()
                val rotateAngel = rightAngelList[index].radius

                // 计算当前扇形中心出来的直线
                val startX =
                    (width / 2 + (radius) * Math.cos(Math.toRadians(rotateAngel.toDouble()))).toFloat()
                val startY =
                    (height / 2 + (radius) * Math.sin(Math.toRadians(rotateAngel.toDouble()))).toFloat()

                // 移动到扇形的圆圈线上
                path.moveTo(startX, startY)

                // 计算当前扇形中心出来的直线

                var endX = 0f
                var endY = 0f


                if (startY > pieRect / 2) {
                    // 表示在上方
                    endX =
                        (width / 2 + (overLineLength + radius) * Math.cos(Math.toRadians(rotateAngel.toDouble()))).toFloat()
                    endY =
                        (height / 2 + (overLineLength + radius) * Math.sin(Math.toRadians(rotateAngel.toDouble()))).toFloat()
                } else {
                    // 表示在下方
                }

                path.lineTo(endX, endY)

                canvas.drawPath(path, linePaint)
            }
        }
    }

    /**
     * 绘制左边的线条和文字
     */
    private fun drawLeftLineAndText(canvas: Canvas) {
        if (leftAngelList.size > 0) {
            for (index in leftAngelList.indices) {
                val path = Path()
                path.close()

                val rotateAngel = leftAngelList[index].radius

                // 计算当前扇形中心出来的直线
                val startX =
                    (width / 2 + (radius) * Math.cos(Math.toRadians(rotateAngel.toDouble()))).toFloat()
                val startY =
                    (height / 2 + (radius) * Math.sin(Math.toRadians(rotateAngel.toDouble()))).toFloat()

                path.moveTo(startX, startY)

                // 计算当前扇形中心出来的直线
                var endX =
                    (width / 2 + (overLineLength + radius) * Math.cos(Math.toRadians(rotateAngel.toDouble()))).toFloat()
                var endY =
                    (height / 2 + (overLineLength + radius) * Math.sin(Math.toRadians(rotateAngel.toDouble()))).toFloat()

                path.lineTo(endX, endY)

                canvas.drawPath(path, linePaint)
            }
        }
    }

    class PieceDataHolder {
        /**
         * 每块扇形的值的大小
         */
        var value: Int
        /**
         * 扇形的颜色
         */
        var color: String
        /**
         * 每块的标记
         */
        var marker: String

        var radius: Float = 0f

        constructor(value: Int, color: String, marker: String) {
            this.value = value
            this.color = color
            this.marker = marker
        }
    }

    fun e(content: String) {
        Log.e("TAG", content)
    }
}
