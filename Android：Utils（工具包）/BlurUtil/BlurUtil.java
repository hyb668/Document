package github.alex.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

/**
 * 图片模糊化的 工具类
 * 作者：Alex
 * 时间：2016年08月13日    09:55
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 * 致谢： https://github.com/wl9739/BlurredView
 */

public class BlurUtil {

    /**
     * 以代码的方式添加待模糊的图片
     *
     * @param drawable 待模糊的图片
     */
    public static void blurImageView(ImageView imageView, Drawable drawable) {
        blurImageView(imageView, drawable, 5);
    }

    /**
     * 以代码的方式添加待模糊的图片
     *
     * @param drawable 待模糊的图片
     * @param level    模糊的等级 (0, 25]
     */
    public static void blurImageView(ImageView imageView, Drawable drawable, @FloatRange(from = 0.0, to = 25.0) float level) {
        if ((imageView == null) || (drawable == null)) {
            return;
        }
        Bitmap originBitmap = drawable2Bitmap(drawable);
        Bitmap blurredBitmap = bitmap2blur(imageView.getContext(), originBitmap, level);
        imageView.setImageBitmap(blurredBitmap);
    }

    /**
     * 以代码的方式添加待模糊的图片
     *
     * @param bitmap 待模糊的图片
     */
    public static void blurImageView(ImageView imageView, Bitmap bitmap) {
        blurImageView(imageView, bitmap, 5);
    }

    /**
     * 以代码的方式添加待模糊的图片
     *
     * @param bitmap 待模糊的图片
     * @param level  模糊的等级 (0, 25]
     */
    public static void blurImageView(ImageView imageView, Bitmap bitmap, @FloatRange(from = 0.0, to = 25.0) float level) {
        if ((imageView == null) || (bitmap == null)) {
            return;
        }

        Bitmap blurredBitmap = bitmap2blur(imageView.getContext(), bitmap, level);
        imageView.setImageBitmap(blurredBitmap);
    }

    /**
     * 将Drawable对象转化为Bitmap对象
     *
     * @param drawable Drawable对象
     * @return 对应的Bitmap对象
     */
    private static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            /* Single color bitmap will be created of 1x1 pixel*/
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将 bitmap 模糊化
     *
     * @param context 上下文对象
     * @param bitmap  需要模糊的图片
     * @param level   模糊的等级 (0, 25]
     * @param scale   图片的压缩 比例
     * @return 模糊处理后的图片
     */
    public static Bitmap bitmap2blur(Context context, Bitmap bitmap, float level, float scale) {
        if ((level <= 0) || (level > 25)) {
            level = 5;
        }
        if (scale < 0.1 || scale > 1.0) {
            scale = 0.8F;
        }
        /*计算图片缩小后的长宽*/
        int width = Math.round(bitmap.getWidth() * scale);
        int height = Math.round(bitmap.getHeight() * scale);

        /*将缩小后的图片做为预渲染的图片*/
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        /*创建一张渲染后的输出图片*/
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        /*创建RenderScript内核对象*/
        RenderScript rs = RenderScript.create(context);
        /*创建一个模糊效果的RenderScript的工具对象*/
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        /*由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        * 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。*/
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        /*设置渲染的模糊程度, 25f是最大模糊度*/
        blurScript.setRadius(level);
        /*设置blurScript对象的输入内存*/
        blurScript.setInput(tmpIn);
        /*将输出数据保存到输出内存中*/
        blurScript.forEach(tmpOut);

        /*将数据填充到Allocation中*/
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    /**
     * 将 bitmap 模糊化
     *
     * @param context 上下文对象
     * @param bitmap  需要模糊的图片
     * @param level   模糊的等级 (0, 25]
     * @return 模糊处理后的图片
     */
    public static Bitmap bitmap2blur(Context context, Bitmap bitmap, float level) {
        return bitmap2blur(context, bitmap, level, 0.8F);
    }
}
