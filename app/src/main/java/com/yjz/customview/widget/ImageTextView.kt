package com.yjz.customview.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.yjz.customview.R
import com.yjz.customview.util.Utils

/**
 *@author: YJZ
 *@date: 2019/5/15 17:58
 *@Des: 带图片的TextView
 **/
class ImageTextView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.head)

    internal var cutWidth = FloatArray(1)
    var text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean justo sem, sollicitudin in maximus a, vulputate id magna. Nulla non quam a massa sollicitudin commodo fermentum et est. Suspendisse potenti. Praesent dolor dui, dignissim quis tellus tincidunt, porttitor vulputate nisl. Aenean tempus lobortis finibus. Quisque nec nisl laoreet, placerat metus sit amet, consectetur est. Donec nec quam tortor. Aenean aliquet dui in enim venenatis, sed luctus ipsum maximus. Nam feugiat nisi rhoncus lacus facilisis pellentesque nec vitae lorem. Donec et risus eu ligula dapibus lobortis vel vulputate turpis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In porttitor, risus aliquam rutrum finibus, ex mi ultricies arcu, quis ornare lectus tortor nec metus. Donec ultricies metus at magna cursus congue. Nam eu sem eget enim pretium venenatis. Duis nibh ligula, lacinia ac nisi vestibulum, vulputate lacinia tortor."

    init {
        paint.isSubpixelText = true
        paint.textSize = Utils.dp2sp(15f)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        Log.e("TAG", " width = ${bitmap.width}")

        val x = bitmap.width.toFloat()

        var index = paint.breakText(text, 0, text.length, true, width.toFloat() - x, null)
        canvas.drawText(text, 0, index, x, paint.fontSpacing, paint)

        var oldIndex = index
        index = paint.breakText(text, index, text.length, true, width.toFloat() - x, null)
        canvas.drawText(text, oldIndex, index + oldIndex, x, paint.fontSpacing * 2, paint)

        oldIndex += index
        index = paint.breakText(text, oldIndex, text.length, true, width.toFloat() - x, null)
        canvas.drawText(text, oldIndex, index + oldIndex + index, x, paint.fontSpacing * 3, paint)
    }
}
