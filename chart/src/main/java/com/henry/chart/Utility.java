package com.henry.chart;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 *
 * @author Henry
 */
public class Utility {

    public static float getDensity(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

    public static int convertPixelToDp(Context context, float px){
        int dp = (int)(px / getDensity(context));
        return dp;
    }

    public static int convertDpToPixel(Context context, float dp){
        int px = (int)(dp * getDensity(context));
        return px;
    }
}
