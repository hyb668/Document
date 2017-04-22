package org.alex.util;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

/**
 * 作者：Alex
 * 时间：2016年09月03日    14:17
 * 简述：
 */

public class FontUtil {
    public static Typeface typeface;
    public static void initFormAssets(String fontPath) {
        try {
            typeface = Typeface.createFromAsset(BaseUtil.app().getAssets(), fontPath);
        } catch (Exception e) {
            LogUtil.w("初始化失败，请检查fontsPath是否错误");
        }
    }

    /**
     * 初始化
     *
     * @param fontPath 字体包存放路径（例如：sdcard/font.ttf）
     */
    public static void initFormFile(String fontPath) {
        try {
            typeface = Typeface.createFromFile(fontPath);
        } catch (Exception e) {
            LogUtil.w("初始化失败，请检查fontsPath是否错误");
        }
    }

    /**
     * 初始化
     *
     * @param fontFile 字体包文件
     */
    public static void initFormFile(File fontFile) {
        try {
            typeface = Typeface.createFromFile(fontFile);
        } catch (Exception e) {
            LogUtil.e("初始化失败，请检查fontFile是否是字体文件");
        }
    }

    /**
     * 更改字体
     *
     * @param view View
     */
    public static void setFontTypeface(View view) {
        setFontTypeface(view, typeface);
    }

    /**
     * 更改字体
     *
     * @param view View
     */
    public static void setFontTypeface(View view, Typeface typeface) {
        if (typeface == null) {
            LogUtil.w("字体为空");
            return;
        }
        if (view == null) {
            LogUtil.w("控件为空");
            return;
        }
        try {
            if (view instanceof ViewGroup) {
                setFontTypeface((ViewGroup) view, typeface);
            } else if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            } else if (view instanceof Button) {
                ((Button) view).setTypeface(typeface);
            } else if (view instanceof EditText) {
                ((EditText) view).setTypeface(typeface);
            }
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
    }

    /**
     * 更换字体
     *
     * @param viewGroup ViewGroup
     * @param typeface  字体
     */
    private static void setFontTypeface(ViewGroup viewGroup, Typeface typeface) {
        try {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View v = viewGroup.getChildAt(i);
                if (v instanceof ViewGroup) {
                    setFontTypeface((ViewGroup) v, typeface);
                } else if ((v != null) && (v instanceof View)) {
                    setFontTypeface(v, typeface);
                }
            }
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
    }
}

