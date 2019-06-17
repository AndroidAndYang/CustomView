package com.yjz.customview.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yjz.customview.util.Utils

/**
 *@author: YJZ
 *@date: 2019/5/16 18:30
 *@Des:
 **/
class CustomText(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.strokeWidth = Utils.dp2px(2f)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.textSize = Utils.dp2px(12f)
        canvas.drawText("Hello World!", 0f, 200f, paint)
        // paint.getFontSpacing() 获取推荐行距
        canvas.drawText("Hello World!", 0f, 200f + paint.fontSpacing, paint)
        // 获取文字绘制范围，结果保存在rect中
        val rect = Rect()
        paint.getTextBounds("Hello World!", 0, "Hello World!".length, rect)

        val path = Path()
        path.moveTo(200f, 200f)
        path.lineTo(400f, 400f)
        path.lineTo(600f, 200f)
        path.lineTo(800f, 400f)

        paint.style = Paint.Style.STROKE
        paint.pathEffect = CornerPathEffect(50f)
        paint.textSize = Utils.dp2px(36f)
        canvas.drawPath(path, paint)

        //
        val str = "Hello World! Hello World! Hello World! Hello World! Hello World!"
//        val floatArr = fLO

//        paint.breakText(str,0,str.length,true,300f,floatArr)

        // 沿着path路径绘制文字
        canvas.drawTextOnPath("Hello World!", path, 5f, 10f, paint)
    }
}