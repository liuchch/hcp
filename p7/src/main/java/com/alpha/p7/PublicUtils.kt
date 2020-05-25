package com.alpha.p7

import android.content.res.Resources


fun getZForCamera(inch: Float): Float {

    return inch * Resources.getSystem().displayMetrics.density

}


class PublicUtils {

}