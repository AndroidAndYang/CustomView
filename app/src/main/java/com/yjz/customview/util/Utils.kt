package com.yjz.customview.util

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author: YJZ
 * @date: 2019/5/15 14:58
 * @Des:
 */
object Utils {

    /**
     * dp to px
     * @param dp 实际值
     * @return px
     */
    fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
    }

    /**
     * dp to px
     * @param dp 实际值
     * @return px
     */
    fun dp2sp(sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().displayMetrics)
    }

    /**
     * 适配camera的Z轴
     */
    fun getZForCamera(): Float {
        return -6 * Resources.getSystem().displayMetrics.density
    }
}
