package com.alpha.utils

import android.content.res.Resources
import android.util.TypedValue


fun getZForCamera(inch: Float): Float {

    return inch * Resources.getSystem().displayMetrics.density

}


private val density = Resources.getSystem().displayMetrics.density


/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dp2px(dpValue: Float): Float {
    //         (int) (0.5f + dpValue * density);
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().displayMetrics)
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
private fun px2dip(pxValue: Float): Float {
    return pxValue / density
}

class PublicUtils {

}