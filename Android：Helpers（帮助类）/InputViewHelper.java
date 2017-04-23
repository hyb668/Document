package com.gomejr.myf.framework.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.gomejr.myf.core.utils.LogTrack;
import com.gomejr.myf.framework.helper.callback.OnKeyBoardChangeListener;

import java.lang.reflect.Field;

/**
 * ���ߣ�Alex
 * ʱ�䣺2017/1/22 9:18
 * ������
 */
public class InputViewHelper {
    private OnKeyBoardChangeListener onKeyBoardChangeListener;
    private int keyboardHeight;
    /**
     * ScrollView  ���ֳ����� �߶�
     */
    private int originalHeight;
    private boolean isShowing;
    private ScrollView scrollView;
    /**
     * ScrollView  �����ĸ߶� -  ���ֳ����ĸ߶�
     */
    private int offset;
    private View baseLineView;

    /**
     * ���� �������� ���� ���� ������ �������
     *
     * @return
     */
    public InputViewHelper(Activity activity, OnKeyBoardChangeListener listener) {
        setOnKeyBoardChangeListener(activity, listener);
        isShowing = false;
        originalHeight = 0;
    }

    /**
     * ��������״̬������
     */
    private void setOnKeyBoardChangeListener(Activity activity, OnKeyBoardChangeListener listener) {
        isShowing = false;
        if (activity == null) {
            return;
        }
        this.onKeyBoardChangeListener = listener;

        final View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean shown = isKeyboardShown(rootView);
                        if (isShowing == shown) {
                            return;
                        }
                        isShowing = shown;
                        if (isKeyboardShown(rootView)) {
                            //960 75
                            LogTrack.e(keyboardHeight);
                            onKeyBoardChange(true, keyboardHeight);
                        } else {
                            onKeyBoardChange(false, 0);
                        }
                    }
                }, 100);

            }
        });
        return;
    }

    private void onKeyBoardChange(boolean isOpened, int height) {
        //LogTrack.e("onKeyBoardChangeListener " + onKeyBoardChangeListener);
        if (onKeyBoardChangeListener != null) {
            onKeyBoardChangeListener.onKeyBoardChange(isOpened, height);
        }

    }

    /**
     * �ж�������Ƿ��
     *
     * @param rootView ���ϲ㲼��
     * @return �򿪣�true�����أ�false
     */
    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        keyboardHeight = rootView.getBottom() - r.bottom + getStatusBarHeight(rootView.getContext());
        return keyboardHeight > softKeyboardHeight * dm.density;
    }

    private int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public InputViewHelper setScrollView(View view) {
        if (view == null || !(view instanceof ScrollView)) {
            return this;
        }
        scrollView = (ScrollView) view;
        scrollView.setFillViewport(false);
        originalHeight = getViewMeasuredHeight(scrollView);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                offset = scrollView.getHeight() - originalHeight;
            }
        }, 100);
        LogTrack.e("ԭʼ �߶� = " + originalHeight);
        return this;
    }

    /**
     * ���û��߿ؼ��� ���������߿ؼ�  ֮�� �� ��������
     * @param view ���߿ؼ�
     * @return
     */
    public InputViewHelper setBaseLineView(View view) {
        this.baseLineView = view;
        return this;
    }

    public void changeHeightByStatus(boolean isShowing) {
        if (scrollView == null) {
            return;
        }
        scrollView.setFillViewport(true);
        ViewGroup.LayoutParams params = baseLineView.getLayoutParams();
        LinearLayout.LayoutParams linearParams = null;
        RelativeLayout.LayoutParams relativeParams = null;
        if (params instanceof LinearLayout.LayoutParams) {
            linearParams = (LinearLayout.LayoutParams) params;
        } else if (params instanceof RelativeLayout.LayoutParams) {
            relativeParams = (RelativeLayout.LayoutParams) params;
        }
        if (linearParams != null) {
            //linearParams.height = isShowing ? originalHeight - offset : originalHeight;
            //LogTrack.e("offset = "+offset);
            int margin = offset * 2;
            linearParams.bottomMargin = isShowing ? margin : 0;
            LogTrack.e(offset);
            //scrollView.setLayoutParams(linearParams);
            baseLineView.setLayoutParams(linearParams);
            //baseLineView.setPadding(0, 0, 0, isShowing ? offset : 0);
        } else if (relativeParams != null) {
            //relativeParams.bottomMargin = isShowing ? keyboardHeight : 0;
            relativeParams.bottomMargin = isShowing ? offset : 0;
            //relativeParams.bottomMargin = isShowing ? keyboardHeight : 0;
            baseLineView.setLayoutParams(relativeParams);
        }
    }


    /**
     * �����������뷨�� �Զ�����
     */
    public boolean hiddenKeyPad(@NonNull Activity activity) {
        isShowing = false;
        if (activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }
        }
        return false;
    }

    /**
     * ��ȡ�ؼ��ĸ߶ȣ������ȡ�ĸ߶�Ϊ0�������¼���ߴ���ٷ��ظ߶�
     *
     * @param view
     * @return
     */
    public int getViewMeasuredHeight(View view) {
        measureView(view);
        return view.getMeasuredHeight();
    }


    /**
     * �����ؼ��ĳߴ�
     *
     * @param view
     */
    private void measureView(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }
}
