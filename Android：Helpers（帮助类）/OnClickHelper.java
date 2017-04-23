package com.gomejr.myf.core.helpers;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ScrollView;

import com.gomejr.myf.core.utils.LogTrack;

/**
 * 作者：Alex
 * 时间：2016/11/9 11:09
 * 简述：自动绑定 点击事件；
 * 控件 没有 id 的，不会 绑定点击事件；
 * 本身是 ListView ScrollView 等 控件容器，不会绑定点击事件；
 * 如果 View 的 tag 以 ignore 开头 不会 绑定；
 * 如果 ViewGroup 的 tag 以 ignore 开头， 其本身 和  其子控件都 不会 绑定；
 */
public class OnClickHelper {
    private static OnClickHelper instance;

    private OnClickHelper() {
    }

    public static OnClickHelper getInstance() {
        if (instance == null) {
            Class var0 = OnClickHelper.class;
            synchronized (OnClickHelper.class) {
                instance = instance == null ? new OnClickHelper() : instance;
            }
        }

        return instance;
    }

    public void onBindClickListener(@NonNull View contentView, @NonNull View.OnClickListener onClickListener) {
        this.setOnClickListener(contentView, onClickListener);
    }

    public void onBindClickListener(@NonNull Activity activity, View.OnClickListener onClickListener) {
        this.setOnClickListener(activity.findViewById(android.R.id.content), onClickListener);
    }

    private void setOnClickListener(@NonNull View view, @NonNull View.OnClickListener onClickListener) {
        if (view != null) {
            if (view instanceof ViewGroup) {
                this.setOnClickListener((ViewGroup) view, onClickListener);
            }

            Object tag = view.getTag();
            if (view instanceof ViewGroup) {
                setOnClickListener((ViewGroup) view, onClickListener);
            } else if (!(view instanceof ViewGroup) && !isIgnore(tag) && (view.getId() > 0)) {
                LogTrack.e("view = " + view.getClass().getName());
                view.setOnClickListener(onClickListener);
            }

        }
    }

    private void setOnClickListener(@NonNull ViewGroup viewGroup, @NonNull View.OnClickListener onClickListener) {
        if (viewGroup != null) {
            if (!this.isRootViewGroup(viewGroup) && viewGroup.getId() > 0) {
                viewGroup.setOnClickListener(onClickListener);
            }

            Object tag = viewGroup.getTag();
            if (!this.isIgnore(tag)) {
                for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                    View view = viewGroup.getChildAt(i);
                    if (view instanceof ViewGroup) {
                        this.setOnClickListener((ViewGroup) view, onClickListener);
                    } else if (view != null && view instanceof View) {
                        this.setOnClickListener(view, onClickListener);
                    }
                }

            }
        }
    }

    public void setOnClickListener(View view, View.OnClickListener listener, @IdRes int... id) {
        for (int i = 0; id != null && i < id.length; ++i) {
            View child = view.findViewById(id[i]);
            if (child != null) {
                child.setOnClickListener(listener);
            }
        }

    }

    private boolean isIgnore(Object tag) {
        String ignore = "ignore";
        return tag != null && tag.toString().length() >= ignore.length() && ignore.equalsIgnoreCase(tag.toString().substring(0, ignore.length()));
    }

    private boolean isRootViewGroup(View view) {
        if (view instanceof ContentFrameLayout) {
            return true;
        }
        if (view instanceof ScrollView) {
            return true;
        }
        if (view instanceof AdapterView) {
            return true;
        }
        if (view instanceof EditText) {
            return true;
        }
        if (view instanceof android.support.v4.widget.DrawerLayout) {
            return true;
        }
        return false;
    }
}
