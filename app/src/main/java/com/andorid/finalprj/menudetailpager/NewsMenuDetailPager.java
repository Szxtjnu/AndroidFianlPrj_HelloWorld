package com.andorid.finalprj.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.andorid.finalprj.R;
import com.andorid.finalprj.base.MenuDetailBasePager;
import com.andorid.finalprj.util.LogUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class NewsMenuDetailPager extends MenuDetailBasePager {

    @ViewInject(R.id.newsmenu_detail_viewpager)
    private ViewPager viewPager;



    public NewsMenuDetailPager(Context context) {
        super(context);
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
    }
}
