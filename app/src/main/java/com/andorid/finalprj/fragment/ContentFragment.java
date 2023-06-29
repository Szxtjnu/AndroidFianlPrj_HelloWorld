package com.andorid.finalprj.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.andorid.finalprj.R;
import com.andorid.finalprj.activity.MainActivity;
import com.andorid.finalprj.adapter.ContentFragmentAdapter;
import com.andorid.finalprj.base.BaseFragment;
import com.andorid.finalprj.base.BasePager;
import com.andorid.finalprj.menudetailpager.NewsMenuDetailPager;
import com.andorid.finalprj.pager.CoursePager;
import com.andorid.finalprj.pager.DiscoverPager;
import com.andorid.finalprj.pager.HomePager;
import com.andorid.finalprj.pager.MinePager;
import com.andorid.finalprj.util.LogUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

@SuppressLint("NonConstantResourceId")
public class ContentFragment extends BaseFragment {


    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.view_pager)
    private RadioGroup rg_main;

    private ArrayList<BasePager> basePagers;


    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图被初始化了");
        View view = View.inflate(context, R.layout.content_fragment, null);

        //把视图注入到框架中，让类和view关联
        x.view().inject(ContentFragment.this, view);


        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文数据被初始化了");

        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(context));
        basePagers.add(new DiscoverPager(context));
        basePagers.add(new CoursePager(context));
        basePagers.add(new MinePager(context));

        rg_main.check(R.id.rb_home);

        //设置ViewPager的适配器
        viewPager.setAdapter(new ContentFragmentAdapter(basePagers));

        //RadioGroup监听器
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        viewPager.setCurrentItem(0);
                        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    case R.id.rb_discover:
                        viewPager.setCurrentItem(1);
                        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                        break;
                    case R.id.rb_course:
                        viewPager.setCurrentItem(2);
                        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                        break;
                    case R.id.rb_mine:
                        viewPager.setCurrentItem(3);
                        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                        break;
                }
            }
        });

        //ViewPager监听器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * GroupButton随着ViewPager滑动进行更改
             * @param position Position index of the new selected page.
             */
            @Override
            public void onPageSelected(int position) {
                basePagers.get(position).initData();
                switch (position) {
                    case 0:
                        rg_main.check(R.id.rb_home);
                        break;
                    case 1:
                        rg_main.check(R.id.rb_discover);
                        break;
                    case 2:
                        rg_main.check(R.id.rb_course);
                        break;
                    case 3:
                        rg_main.check(R.id.rb_mine);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rg_main.check(R.id.rb_home);
        basePagers.get(0).initData();

    }

    private void isEnableSlidingMenu(int touchModeFullscreen) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchModeFullscreen);
    }

    /**
     * 得到主界面
     * @return
     */
    public HomePager getHomePager() {
        return (HomePager) basePagers.get(0);
    }
}
