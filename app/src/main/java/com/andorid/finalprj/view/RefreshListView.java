package com.andorid.finalprj.view;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andorid.finalprj.R;

/**
 * 自定义下拉刷新的ListView
 */
public class RefreshListView extends ListView {
    /**
     * 下拉刷新和顶部轮播图
     */
    private LinearLayout headerView;
    private Animation upAnimation;
    private Animation downAnimation;
    private View ll_pulldown_refresh;
    private ImageView iv_arrow;
    private ProgressBar pb_status;
    private int high;
    private float endY;
    public static final int PULL_DOWN_REFRESH = 0;
    public static final int RELEASE_REFRESH = 1;
    public static final int REFRESHING = 2;
    private int currentStatus = PULL_DOWN_REFRESH;
    private View footerView;
    private int measuredHeight;
    private boolean isLoadMore = false;
    private View topNewsView;
    private int listViewOnScreenY = -1;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();
        initFooterView(context);
    }

    private void initFooterView(Context context) {
        footerView = View.inflate(context, R.layout.refresh_footer, null);
        footerView.measure(0, 0);
        measuredHeight = footerView.getMeasuredHeight();

        footerView.setPadding(0, -measuredHeight, 0, 0);

        addFooterView(footerView);

        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
                    if (getLastVisiblePosition() == getCount() - 1) {
                        footerView.setPadding(8, 8, 8, 8);
                        isLoadMore = true;
                        if (mOnRefreshListener != null) {
                            mOnRefreshListener.onLoadMore();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }


    private void initAnimation() {
        upAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_in);
        upAnimation.setDuration(300);
        upAnimation.setFillAfter(true);

        downAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_out);
        downAnimation.setDuration(100);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView(Context context) {
        headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);
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

                //判断顶部轮播图是否完全显示，当轮播图完全显示的时候，才会触发下拉刷新的功能
                if (!isDisplayTopNews()) {
                    break;
                }

                if (currentStatus == REFRESHING) {
                    break;
                }

                endY = ev.getY();
                float distanceY = endY - startY;
                if (distanceY > 0) {
                    int paddingTOP = (int) (-high + distanceY);

                    if (paddingTOP < 0 && currentStatus != PULL_DOWN_REFRESH) {
                        currentStatus = PULL_DOWN_REFRESH;
                        refreshViewState();
                    } else if (paddingTOP > 0 && currentStatus != RELEASE_REFRESH) {
                        currentStatus = RELEASE_REFRESH;
                        refreshViewState();
                    }

                    ll_pulldown_refresh.setPadding(0, paddingTOP, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (currentStatus == PULL_DOWN_REFRESH) {
                    ll_pulldown_refresh.setPadding(0, -high, 0, 0);
                } else if (currentStatus == RELEASE_REFRESH) {
                    currentStatus = REFRESHING;
                    refreshViewState();
                    ll_pulldown_refresh.setPadding(0, 0, 0, 0);

                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isDisplayTopNews() {
        int[] location = new int[2];
        if (listViewOnScreenY == -1) {
            getLocationOnScreen(location);
            listViewOnScreenY = location[1];
        }
        topNewsView.getLocationOnScreen(location);
        int topNewsViewOnScreenY = location[1];

        return listViewOnScreenY <= topNewsViewOnScreenY;
    }

    private void refreshViewState() {
        switch (currentStatus) {
            case PULL_DOWN_REFRESH:
                iv_arrow.startAnimation(downAnimation);
                break;
            case RELEASE_REFRESH:
                iv_arrow.startAnimation(upAnimation);
                break;
            case REFRESHING:
                iv_arrow.setVisibility(GONE);
                pb_status.setVisibility(VISIBLE);
                iv_arrow.clearAnimation();
                break;
        }
    }

    public void onRefreshFinish(boolean success) {
        if (isLoadMore) {
            isLoadMore = false;
            footerView.setPadding(0, -high, 0, 0);
        } else {
            currentStatus = PULL_DOWN_REFRESH;
            iv_arrow.clearAnimation();
            pb_status.setVisibility(GONE);
            iv_arrow.setVisibility(INVISIBLE);
            ll_pulldown_refresh.setPadding(0, -high, 0, 0);
            if (!success) {
                Toast.makeText(getContext(), "数据请求失败，请检查网络设置", Toast.LENGTH_LONG).show();
            }
        }


    }

    /**
     * 添加顶部轮播图
     * @param topNewsView
     */
    public void addTopNewsView(View topNewsView) {
        if (topNewsView != null) {
            this.topNewsView = topNewsView;
            headerView.addView(topNewsView);
        }

    }

    public interface OnRefreshListener {
        public void onPullDownRefresh();

        public void onLoadMore();
    }

    private OnRefreshListener mOnRefreshListener;

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }
}
