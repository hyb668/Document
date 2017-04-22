package org.alex.util;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import org.alex.view.MeiZuToast;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:54
 * 简述：
 */

@SuppressWarnings("all")
public class ToastUtil {
    private static final String readMe = "必须在MainActivity的onDestory方法调用ToastUtil.destroy();回收资源";
    private static int gravityNone = -100;
    private static MeiZuToast meiZuToast;

    public static void shortNormal(Object msg) {
        show(gravityNone, Toast.LENGTH_SHORT, msg);
    }

    public static void shortCenter(Object msg) {
        show(Gravity.CENTER, Toast.LENGTH_SHORT, msg);
    }

    public static void shortTop(Object msg) {
        show(Gravity.TOP, Toast.LENGTH_SHORT, msg);
    }

    public static void shortTopInThread(final Object msg) {
        showInThread(Gravity.TOP, Toast.LENGTH_SHORT, msg);
    }

    public static void longNormal(Object msg) {
        show(gravityNone, Toast.LENGTH_LONG, msg);
    }

    public static void longCenter(Object msg) {
        show(Gravity.CENTER, Toast.LENGTH_LONG, msg);
    }

    public static void longTop(Object msg) {
        show(Gravity.TOP, Toast.LENGTH_LONG, msg);
    }

    public static void longTopInThread(final Object text) {
        showInThread(Gravity.TOP, Toast.LENGTH_LONG, text);
    }

    public static void shortInThread(final Object msg) {
        showInThread(gravityNone, Toast.LENGTH_SHORT, msg);
    }

    public static void shortCenterInThread(final Object msg) {
        showInThread(Gravity.CENTER, Toast.LENGTH_SHORT, msg);
    }

    public static void longInThread(final Object msg) {
        showInThread(gravityNone, Toast.LENGTH_LONG, msg);
    }

    public static void longCenterInThread(final Object msg) {
        showInThread(Gravity.CENTER, Toast.LENGTH_LONG, msg);
    }

    @SuppressWarnings("deprecation")
    public static void show(int gravity, int duration, Object msg) {
        String text = (msg == null) ? " " : msg.toString();
        meiZuToast = (meiZuToast == null) ? MeiZuToast.makeText(text, duration) : meiZuToast;
        if (gravity == Gravity.CENTER) {
            meiZuToast.setGravity(gravity, 0, -100);
        } else if (gravity == Gravity.TOP) {
            meiZuToast.setGravity(gravity, 0, 100);
        }
        meiZuToast.setText(text);
        meiZuToast.show();
    }

    private static void showInThread(final int gravity, final int duration, final Object msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                     @Override
                                                     public void run() {
                                                         show(gravity, duration, msg);
                                                     }
                                                 }
        );
    }

    public static void cancel() {
        if (meiZuToast != null) {
            meiZuToast.cancel();
        }
    }

    public static void destroy() {
        if (meiZuToast != null) {
            meiZuToast.cancel();
        }
        meiZuToast = null;
    }
}

