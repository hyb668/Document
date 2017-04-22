package org.alex.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:09
 * 简述：
 */

@SuppressWarnings("all")
public class ScreenUtil {
    private static Context context(){
        return BaseUtil.app();
    }
    private static final String TAG = "ScreenUtil";
    /**获得屏幕宽度*/
    public static int getScreenWidth()
    {
        if(context()==null){
            return 0;
        }
        WindowManager wm = (WindowManager) context().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    /**获得屏幕高度*/
    public static int getScreenHeight()
    {
        if(context() == null){
            return 0;
        }
        WindowManager wm = (WindowManager) context().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    /**获得状态栏的高度*/
    public static int getStatusBarHeight(Context context)
    {
        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
        return statusHeight;
    }
    /**获取当前屏幕截图, 包含状态栏
     * 得到Bitmap数据*/
    public static Bitmap getScreenshotWithStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }
    /**获取当前屏幕截图, 不包含状态栏
     *得到Bitmap数据
     */
    public static Bitmap getScreenshotWithoutStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
    /**数据转换: dp---->px*/
    public static float dp2px(float dp)
    {
        if (context() == null) {
            return -1;
        }
        return dp * context().getResources().getDisplayMetrics().density;
    }
    /**数据转换: px---->dp*/
    public static float px2dp(float px) {
        if (context() == null) {
            return -1;
        }
        return px / context().getResources().getDisplayMetrics().density;
    }
    /**
     * sp转px
     */
    public static int sp2px(float sp)  {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,  sp, context().getResources().getDisplayMetrics());
    }
    /**数据转换: dp---->int*/
    public static float dp2pxInt(float dp) {
        return (int)(dp2px(dp) + 0.5f);
    }
    /**数据转换: px---->int[向上取整]*/
    public static float px2dpCeilInt(Context context, float px) {
        return (int)(px2dp(px) + 0.5f);
    }
    /**Notice location[0] 为在屏幕上的x坐标值 location[1]为y轴 (这个屏幕是包括了状态栏的)*/
    public static int[] getLocationOnScreen(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }
    /**获取相对在它父窗口里的坐标。*/
    public static int[] getLocationInWindow(View view){
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }
	  /**
     * 获取文字的宽度
     *
     * @param paint
     * @param str
     * @return
     */
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    /**
     * 计算文字的高度
     *
     * @param paint
     * @return
     */
    public static int getTextHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int)Math.ceil(fm.descent - fm.ascent);
    }

    public static int getTextFullHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int)Math.ceil(fm.bottom - fm.top);
    }

    public static int getTextBaseLine(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int)Math.abs(fm.bottom);
    }
}
 