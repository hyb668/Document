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
 * ���ߣ�Alex
 * ʱ�䣺2016��11��24��
 * ������
 */
public class ChildLayout extends RelativeLayout
{
    /**
     * ��ָ ���µ� ����
     */
    private float firstX, firstY;
    /**
     * ��ָ  ÿ�β�������������֮�������
     */
    private float lastX, lastY;
    /**
     * ÿ�λ�������׽���ģ������ľ��룬���� ���»���Ϊ��
     */
    private float distanceX, distanceY;
    /**
     * ����ָ ���� �� ̧�� �ܻ������룬���� ���»���Ϊ��
     */
    private float distanceTotalX, distanceTotalY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            firstX = event.getRawX();
            firstY = event.getRawY();
            lastX = event.getRawX();
            lastY = event.getRawY();
            /*��ָ�� ������ �ֻ���Ļ*/
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
