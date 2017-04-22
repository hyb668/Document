package org.alex.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;

/**
 * 作者：Alex
 * 时间：2016/12/19 22 59
 * 简述：
 */
public class KeyPadUtil {
    private static KeyPadUtil instance;
    private KeyPadUtil() {
    }

    public static KeyPadUtil getInstance() {
        if (instance == null) {
            synchronized (KeyPadUtil.class) {
                instance = (instance == null) ? new KeyPadUtil() : instance;
            }
        }
        return instance;
    }
    /**
     * 可以隐藏输入法
     */
    public boolean hiddenKeyPad(@NonNull Activity activity) {
        if (activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }
        }
        return false;
    }
}
