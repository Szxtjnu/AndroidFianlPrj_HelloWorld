package com.andorid.finalprj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.andorid.finalprj.R;

/**
 * 自定义下拉刷新的ListView
 */
public class RefreshListView extends ListView {
    /**
     * 下拉刷新和顶部轮播图
     */
    private ListView headerView;

    private View ll_pulldown_refresh;
    private ImageView iv_arrow;
    private ProgressBar pb_status;
    private int high;
    private float endY;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
    }

    private void initHeaderView(Context context) {
        LinearLayout headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);
        ll_pulldown_refresh = headerView.findViewById(R.id.ll_pulldown_refresh);
        iv_arrow = headerView.findViewById(R.id.iv_rowdots);
        pb_status = headerView.findViewById(R.id.pb_status);

        ll_pulldown_refresh.measure(0, 0);
        high = ll_pulldown_refresh.getMeasuredHeight();
        ll_pulldown_refresh.setPadding(0, -high, 0, 0);

        //添加ListView
        RefreshListView.this.addHeaderView(headerView);
    }

    private float startY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = ev.getY();
                }
                endY = ev.getY();
                float distanceY = endY - startY;
                if (distanceY > 0) {
                    int paddingTOP = (int) (-high + distanceY);
                    ll_pulldown_refresh.setPadding(0, paddingTOP, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                break;
        }
        return super.onTouchEvent(ev);
    }
}
