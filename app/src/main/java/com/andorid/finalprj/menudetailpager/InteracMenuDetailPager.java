package com.andorid.finalprj.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.andorid.finalprj.base.MenuDetailBasePager;
import com.andorid.finalprj.util.LogUtil;

public class InteracMenuDetailPager extends MenuDetailBasePager {
    private TextView textView;

    public InteracMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("互动详情页面被初始化了");
        textView.setText("这是互动页面");
    }
}
