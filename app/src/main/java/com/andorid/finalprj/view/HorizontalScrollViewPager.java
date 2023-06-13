package com.andorid.finalprj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 水平方向滑动的ViewPager,避免出现左滑bug
 */

public class HorizontalScrollViewPager extends ViewPager {

    public HorizontalScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 起始位置
     */
    private float startX;
    private float startY;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //请求父层视图不拦截当前控件的事件
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true); //都把事件传给当前控件
                //记录起始坐标
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //记录新的坐标
                float endX = ev.getX();
                float endY = ev.getY();
                float distanceX = endX - startX;
                float distanceY = endY - startY;
                //判断滑动方向
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    //水平方向滑动
                    if (getCurrentItem() == 0 && distanceX > 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if ((getCurrentItem() == getAdapter().getCount() - 1) && distanceX < 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
                    //竖直方向滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
