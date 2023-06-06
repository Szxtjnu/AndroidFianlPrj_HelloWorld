package com.andorid.finalprj.menudetailpager.tabledetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.andorid.finalprj.base.MenuDetailBasePager;

public class TableDetailPager extends MenuDetailBasePager {

    private TextView textView;

    public TableDetailPager(Context context) {
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

    public void initData() {
        super.initData();
    }
}
