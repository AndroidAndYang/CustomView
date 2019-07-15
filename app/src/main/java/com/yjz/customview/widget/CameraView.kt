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
 * @author YJZ
 * @date 2019/7/1
 * @description
 */
class CameraView(context: Context?, attributeSet: AttributeSet?) : View(context, attributeSet) {

    var camera = Camera()
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // camera 使用的 流程
    // 1. new Camera()
    // 2. camera.rotateX translate..
    // 3. camera.applyToCanvas(canvas)
    // 使用camera时,绘制从后往前画,是因为camera.applyToCanvas的关系,有个view的坐标和一个camera的坐标,导致位置不好计算

    init {
        // 适配Camera Z轴上面的定位点
        camera.setLocation(0f, 0f, Utils.getZForCamera())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        // 绘制上半部分
        // 4.平移回来
        canvas.translate(400f / 2 + 100, 400f / 2 + 100)
        canvas.rotate(-20f)
        // 3. 引用到Camera上
        camera.applyToCanvas(canvas)
        // 切割,clipRect会有锯齿,系统原因,更改不了
        canvas.clipRect(-400 / 2 * 2f, -400 / 2 * 2f, 400 / 2 * 2f, 0 * 2f)
        canvas.rotate(20f)
        // 2.将画好的图平移直原点
        canvas.translate(-(400f / 2 + 100), -(400f / 2 + 100))
        // 1.画图
        canvas.drawBitmap(BitmapUtils.getAvatar(resources, 400), 100f, 100f, paint)
        canvas.restore()

        // 绘制下半部分
        canvas.save()
        // 5.旋转X
        camera.rotateX(45f)
        // 4.从原点平移回来
        canvas.translate(400f / 2 + 100, 400f / 2 + 100)
        canvas.rotate(-20f)
        // 3. 引用到Camera上
        camera.applyToCanvas(canvas)
        // 切割
        canvas.clipRect(-400 / 2 * 2f, 0 * 2f, 400 / 2 * 2f, 400 / 2 * 2f)
        // 因为对长方形进行了rotate,所以切割是需要放大体积,放大的体积不会超过根号2 = 1.41421
        canvas.rotate(20f) // 进行偏移
        // 2.将画好的图平移直原点
        canvas.translate(-(400f / 2 + 100), -(400f / 2 + 100))
        // 1.画图
        canvas.drawBitmap(BitmapUtils.getAvatar(resources, 400), 100f, 100f, paint)
        canvas.restore()
    }
}
