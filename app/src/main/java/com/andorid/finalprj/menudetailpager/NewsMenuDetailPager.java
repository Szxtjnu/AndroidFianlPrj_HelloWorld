package com.andorid.finalprj.menudetailpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.andorid.finalprj.R;
import com.andorid.finalprj.activity.MainActivity;
import com.andorid.finalprj.base.MenuDetailBasePager;
import com.andorid.finalprj.domain.NewsCenterPagerBean2;
import com.andorid.finalprj.menudetailpager.tabledetailpager.TableDetailPager;
import com.andorid.finalprj.util.LogUtil;
import com.andorid.indicator.TabPageIndicator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class NewsMenuDetailPager extends MenuDetailBasePager {

    @ViewInject(R.id.tablePagerIndicator)
    private TabPageIndicator tabPageIndicator;

    @ViewInject(R.id.newsmenu_detail_viewpager)
    private ViewPager viewPager;

    private List<NewsCenterPagerBean2.DetailPagerData.ChildrenData> children;
    private ArrayList<TableDetailPager> tableDetailPagers;


    public NewsMenuDetailPager(Context context, NewsCenterPagerBean2.DetailPagerData detailPagerData) {
        super(context);
        children = detailPagerData.getChildren();

    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.newsmenu_detail_pager, null);
        x.view().inject(NewsMenuDetailPager.this, view);
        return view;

    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻详情页面被初始化了");

        //准备页面的数据
        tableDetailPagers = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            tableDetailPagers.add(new TableDetailPager(context, children.get(i)));
        }

        viewPager.setAdapter(new MyNewsMenuDetailPagerAdapter());

        tabPageIndicator.setViewPager(viewPager);

        tabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_MARGIN);
                } else {
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void isEnableSlidingMenu(int touchModeFullscreen) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchModeFullscreen);
    }


    class MyNewsMenuDetailPagerAdapter extends PagerAdapter {

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tableDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TableDetailPager tableDetailPager = tableDetailPagers.get(position);
            View rootView = tableDetailPager.rootView;
            tableDetailPager.initData();//初始化数据
            container.addView(rootView);
            return rootView;
        }
    }

}
