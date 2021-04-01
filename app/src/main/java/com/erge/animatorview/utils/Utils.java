package com.erge.animatorview.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by erge 2019-07-06 10:57
 */
public class Utils {

    public static float px2dp(float px) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, Resources.getSystem().getDisplayMetrics());
    }

    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float px2sp(float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return pxValue / fontScale;
    }

    public static float sp2px(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return spValue * fontScale ;
    }

    /**
     * 获取屏幕宽高：高度不包含虚拟导航栏高度
     *
     * @return
     */
    public static Rect getScreen(Context context) {
        Rect rect = new Rect();
        if (context != null) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            rect = new Rect(0, 0, metrics.widthPixels, metrics.heightPixels);
        }

        return rect;
    }

}
