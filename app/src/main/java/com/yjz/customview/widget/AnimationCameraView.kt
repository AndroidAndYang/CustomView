package com.yjz.customview.widget

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.yjz.customview.util.BitmapUtils
import com.yjz.customview.util.Utils

/**
 *@author: YJZ
 *@date: 2019/7/11 9:51
 *@Des:
 **/
class AnimationCameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera()
    private val bitmapWidth = 400
    private val location = 200

    init {
        // 对Camera进行适配
        camera.setLocation(0f, 0f, Utils.getZForCamera())
    }

    private var topFlip = 0f
    private var bottomFlip = 0f
    private var flipRotation = 0f

    fun getTopFlip(): Float {
        return topFlip
    }

    fun setTopFlip(topFlip: Float) {
        this.topFlip = topFlip
        invalidate()
    }

    fun getBottomFlip(): Float {
        return bottomFlip
    }

    fun setBottomFlip(bottomFlip: Float) {
        this.bottomFlip = bottomFlip
        invalidate()
    }

    fun getFlipRotation(): Float {
        return flipRotation
    }

    fun setFlipRotation(flipRotation: Float) {
        this.flipRotation = flipRotation
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制上半部分
        canvas.save()
        canvas.translate((location + bitmapWidth.toFloat() / 2), (location + bitmapWidth.toFloat() / 2))
        canvas.rotate(-flipRotation) // 进行偏移
        camera.applyToCanvas(canvas)
        // 因为对长方形进行了rotate,所以切割是需要放大体积,放大的体积不会超过根号2 = 1.41421
        canvas.clipRect(
            -bitmapWidth.toFloat() / 2 * 2f,
            -bitmapWidth.toFloat() / 2 * 2f,
            bitmapWidth.toFloat() / 2 * 2f,
            0f
        )
        canvas.rotate(flipRotation) // 进行偏移
        canvas.translate(-(location + bitmapWidth.toFloat() / 2), -(location + bitmapWidth.toFloat() / 2))
        canvas.drawBitmap(BitmapUtils.getAvatar(resources, bitmapWidth), location.toFloat(), location.toFloat(), paint)
        canvas.restore()

        // 绘制下半部分
        canvas.save()
        canvas.translate((location + bitmapWidth.toFloat() / 2), (location + bitmapWidth.toFloat() / 2))
        canvas.rotate(-flipRotation)
        camera.rotateX(bottomFlip)
        camera.applyToCanvas(canvas)
        // 因为对长方形进行了rotate,所以切割是需要放大体积,放大的体积不会超过根号2 = 1.41421
        canvas.clipRect(
            -bitmapWidth.toFloat() / 2 * 2f,
            0f * 2f,
            bitmapWidth.toFloat() / 2 * 2f,
            bitmapWidth.toFloat() / 2 * 2f
        )
        canvas.rotate(flipRotation)
        canvas.translate(-(location + bitmapWidth.toFloat() / 2), -(location + bitmapWidth.toFloat() / 2))
        canvas.drawBitmap(BitmapUtils.getAvatar(resources, bitmapWidth), location.toFloat(), location.toFloat(), paint)
        canvas.restore()
    }
}