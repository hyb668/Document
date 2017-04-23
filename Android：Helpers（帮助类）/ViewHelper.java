package org.alex.helper;

import android.support.annotation.NonNull;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.alex.util.LogTrack;

/**
 * 作者：Alex
 * 时间：2016/11/8 15:31
 * 简述：
 */
@SuppressWarnings("all")
public class ViewHelper {
    private static ViewHelper instance;
    private ViewHelper() {
    }

    public static ViewHelper getInstance() {
        if (instance == null) {
            synchronized (ViewHelper.class) {
                instance = (instance == null) ? new ViewHelper() : instance;
            }
        }
        return instance;
    }

    /**
     * 给文本控件设置文本
     *
     * @param view
     * @param text
     */
    public ViewHelper setText(@NonNull View view, @NonNull String text) {
        if (view == null) {
            LogTrack.w("\nview 为空 ", true);
            return this;
        }
        if (text == null) {
            LogTrack.w("\n文本 为空", true);
            return this;
        }
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        } else {
            LogTrack.w("\nview 不能被强转成 TextView  或 Button 或 EditText", true);
        }
        if (view instanceof EditText) {
            ((EditText) view).setSelection(text.length());
        }
        return this;
    }

    /**
     * 设置  输入框  输入文字可见性
     *
     * @param editText
     * @param isVisible true-可见； false-不可见
     * @return
     */
    public ViewHelper setInputVisible(EditText editText, boolean isVisible) {
        if (view == null || !(view instanceof EditText)) {
            return this;
        }
        EditText editText = (EditText) view;
        if (editText == null) {
            return this;
        }
        editText.setTransformationMethod(isVisible ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        editText.setSelection(editText.getText().length());
        return this;
    }

}
