package com.andorid.finalprj.activity;


import android.app.Activity;

import androidx.core.view.*;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.andorid.finalprj.R;
import com.andorid.finalprj.fragment.ContentFragment;
import com.andorid.finalprj.fragment.LeftMenuFragment;
import com.andorid.finalprj.util.DensityUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        initFragment();
        initSlidingMenu();
    }

    private void initSlidingMenu(){

        //设置主页面
        setContentView(R.layout.activity_main);

        //设置左侧菜单
        setBehindContentView(R.layout.activity_left_menu);

        //设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();

        //设置显示的模式
        slidingMenu.setMode(SlidingMenu.LEFT);

        //设置滑动模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        DisplayMetrics outmetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outmetrics);
        int screeWidth = outmetrics.widthPixels;
        int screeHeight = outmetrics.heightPixels;
        //设置主页占据的宽度
        slidingMenu.setBehindOffset((int) (screeWidth *0.625));

        //初始化Fragment
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_main_content, new ContentFragment(), MAIN_CONTENT_TAG);
        ft.replace(R.id.fl_leftmenu, new LeftMenuFragment(), LEFTMENU_TAG);
        ft.commit();
    }

    /**
     * 得到左侧菜单的Fragment
     * @return
     */
    public LeftMenuFragment getLeftMenuFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        LeftMenuFragment leftMenuFragment = (LeftMenuFragment) fragmentManager.findFragmentByTag(LEFTMENU_TAG);
        return leftMenuFragment;
    }

    /**
     * 得到正文Fragment
     * @return
     */
    public ContentFragment getContentFragment() {
        return (ContentFragment) getSupportFragmentManager().findFragmentByTag(MAIN_CONTENT_TAG);
    }

}