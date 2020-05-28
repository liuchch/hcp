package com.alpha.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

    private static float density = Resources.getSystem().getDisplayMetrics().density;


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dp2px(float dpValue) {
//         (int) (0.5f + dpValue * density);
       return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    private static float px2dip(float pxValue) {
        return (pxValue / density);
    }


    float[] a = new float[1];
}
