package com.andorid.finalprj.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.andorid.finalprj.R;
import com.andorid.finalprj.SplashActivity;
import com.andorid.finalprj.util.CacheUtils;
import com.andorid.finalprj.util.DensityUtil;

import java.util.ArrayList;

public class GuideActivity extends Activity {

    private static final String TAG = GuideActivity.class.getSimpleName();
    private ViewPager viewPager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;
    private ImageView tv_focus_dot;
    private ArrayList<ImageView> imageViews;
    private int leftMax;
    private ImageButton btn_login;
    private ImageButton btn_register;
    private int widthdpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        btn_start_main = (Button) findViewById(R.id.btn_start_main);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        tv_focus_dot = (ImageView) findViewById(R.id.tv_focus_dot);
        btn_login = (ImageButton) findViewById(R.id.btn_login);
        btn_register = (ImageButton) findViewById(R.id.btn_register);
        //准备数据
        int[] ids = new int[]{
                R.drawable.guide_page1,
                R.drawable.guide_page2,
                R.drawable.guide_page3,
                R.drawable.guide_page4,
        };

        widthdpi = DensityUtil.dip2px(this, 10);

        imageViews = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);

            //设置背景
            imageView.setBackgroundResource(ids[i]);

            //添加到集合之中
            imageViews.add(imageView);

            //创建点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi, widthdpi);
            if (i != 0) {
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);

            // 添加到线性布局之中
            ll_point_group.addView(point);
        }

        //设置ViewPager的适配器
        viewPager.setAdapter(new MyPagerAdapter());

        //根据View的生命周期，当视图执行到layout或者onDraw的时候，视图的高和宽，边距都可以得到
        tv_focus_dot.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        //得到屏幕滑动的百分比
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存曾经进入过主页面
                CacheUtils.putBoolean(GuideActivity.this, SplashActivity.START_MAIN, true);
                //要跳转到主页面
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                //要关闭当前的引导页面
                finish();
            }
        });
    }


    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 当页面滚动了会回调这个方法
         *
         * @param position             当前这个滑动页面的位置
         * @param positionOffset       页面滑动的百分比
         * @param positionOffsetPixels 滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            int leftMargin = (int) ((position * leftMax) + (positionOffset * leftMax));

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_focus_dot.getLayoutParams();
            params.leftMargin = leftMargin;
            tv_focus_dot.setLayoutParams(params);
        }

        /**
         * 当页面被选中的时候回调这个方法
         *
         * @param position 被选中页面的对应的位置
         */
        @Override
        public void onPageSelected(int position) {
            if (position == imageViews.size() - 1) {
                btn_start_main.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.VISIBLE);
                btn_register.setVisibility(View.VISIBLE);
            } else {
                btn_start_main.setVisibility(View.INVISIBLE);
                btn_login.setVisibility(View.INVISIBLE);
                btn_register.setVisibility(View.INVISIBLE);
            }
        }

        /**
         * 当ViewPager页面滑动状态发生变化的时候回调这个方法
         *
         * @param state The new scroll state.
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            //执行不止一次
            tv_focus_dot.getViewTreeObserver().removeOnGlobalLayoutListener(MyOnGlobalLayoutListener.this);

            leftMax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();

        }
    }


    class MyPagerAdapter extends PagerAdapter {
        /**
         * 返回数据的总个数
         *
         * @return 数据的总个数
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }


        /**
         * 判断
         *
         * @param view 当前创建的视图
         * @param o    instantiateItem返回的结果值
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        /**
         * 作用 getView
         *
         * @param container ViewPager
         * @param position  要创建页面的位置
         * @return 返回和创建当前页面有关系的值
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            //添加到容器中
            container.addView(imageView);
            return imageView;
        }

        /**
         * 销毁页面
         *
         * @param container ViewPager
         * @param position  要销毁页面的位置
         * @param object    要销毁的页面
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

    }

}