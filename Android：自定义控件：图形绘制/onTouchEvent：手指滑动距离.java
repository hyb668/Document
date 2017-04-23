package org.alex.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import org.alex.util.LogUtil;

/**
 * 作者：Alex
 * 时间：2016年11月24日
 * 简述：
 */
public class ChildLayout extends RelativeLayout
{
    /**
     * 手指 落下的 坐标
     */
    private float firstX, firstY;
    /**
     * 手指  每次产生滑动，滑动之后的坐标
     */
    private float lastX, lastY;
    /**
     * 每次滑动，捕捉到的，滑动的距离，向右 向下滑动为正
     */
    private float distanceX, distanceY;
    /**
     * 从手指 落下 到 抬起， 总滑动距离，向右 向下滑动为正
     */
    private float distanceTotalX, distanceTotalY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            firstX = event.getRawX();
            firstY = event.getRawY();
            lastX = event.getRawX();
            lastY = event.getRawY();
            /*手指刚 触摸到 手机屏幕*/
            startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL | ViewCompat.SCROLL_AXIS_VERTICAL);
        } else if (MotionEvent.ACTION_MOVE == event.getAction()) {
            distanceX = event.getRawX() - lastX;
            distanceY = event.getRawY() - lastY;

            LogUtil.e("distanceX = " + distanceX + " distanceY = " + distanceY);
   

            lastX = event.getRawX();
            lastY = event.getRawY();
        } else if (MotionEvent.ACTION_UP == event.getAction()) {
            distanceTotalX = lastX - firstX;
            distanceTotalY = lastY - firstY;
            LogUtil.e("distanceTotalX = " + distanceTotalX + " distanceTotalY = " + distanceTotalY);
        }
        return super.onTouchEvent(event);
    }

    
}
