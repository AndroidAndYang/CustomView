package com.yjz.customview.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yjz.customview.R

/**
 *@author: YJZ
 *@date: 2019/5/16 14:32
 *@Des:
 **/
class ShaderView(context: Context?, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()

    init{
        /**
         * LinearGradient 线性渐变
         *
         * x0 x1 y0 y1 渐变的两个端点的位置
         * color0 color1 端点的颜色
         */
          // paint.shader = LinearGradient(
          //   100f, 100f, 500f, 500f, Color.parseColor("#2196F3"),
          //     Color.parseColor("#E91E63"), Shader.TileMode.CLAMP)

        /**
         * 辐射渐变
         *
         * centerX Y 辐射中心的坐标
         * radius 半径
         * centerColor 中心颜色
         * edgeColor  辐射边缘的颜色
         * tileMode 辐射范围之外的着色模式。
         *
         */
         // paint.shader = RadialGradient(300f,300f,200f,Color.parseColor("#2196F3"),
         //    Color.parseColor("#E91E63"),Shader.TileMode.CLAMP)

        /**
         * 可以绘制圆形
         */
        // paint.shader = BitmapShader(
        //     BitmapFactory.decodeResource(resources, R.drawable.batman),
        //     Shader.TileMode.CLAMP,
        //     Shader.TileMode.CLAMP
        // )

        /**
         * ComposeShader 混合着色器，两个shader一起用
         *
         * ComposeShader 在硬件加速的情况下是不支持两个BitmapShader的，所以这里需要关闭硬件加速才能看到效果。
         *
         */
        paint.shader = ComposeShader(BitmapShader(
            BitmapFactory.decodeResource(resources, R.drawable.batman),
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        ),BitmapShader(
            BitmapFactory.decodeResource(resources, R.drawable.batman_logo),
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        ),PorterDuff.Mode.DST_IN)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(300f,300f,200f,paint)
    }
}