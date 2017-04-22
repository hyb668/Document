package org.alex.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

/**
 * 作者：Alex
 * 时间：2016年11月06日
 * 简述：
 */
@SuppressWarnings("all")
public class ContextUtil {
    private static Context context() {
        return BaseUtil.app();
    }

    @ColorInt
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(context(), id);
    }

    public static final Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(context(), id);
    }
}
