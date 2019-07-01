package com.yjz.customview.util

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.yjz.customview.R
import android.content.res.Resources


/**
 *@author: YJZ
 *@date: 2019/6/6 15:27
 *@Des:
 **/
object BitmapUtils {

    fun measureBitmap(context: Context, resourceId: Int): Array<Int> {
        val options = BitmapFactory.Options()
        // 只读模式，不加载原图
        options.inJustDecodeBounds = true
        // 此时创建的Bitmap是为空的
        BitmapFactory.decodeResource(context.resources, resourceId, options)
        return arrayOf(options.outWidth, options.outHeight)
    }

    fun getAvatar(res: Resources, width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(res, R.drawable.avatar_rengwuxian, options)
    }

}