package com.andorid.finalprj.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.andorid.finalprj.R;
import com.andorid.finalprj.base.BasePager;

public class CoursePager extends BasePager {

    public CoursePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        tv_title.setVisibility(View.VISIBLE);
        ib_button.setVisibility(View.INVISIBLE);
        search_bar.setVisibility(View.INVISIBLE);
        tv_title.setBackgroundResource(R.drawable.text_course);

        TextView textView = new TextView(context);
        textView.setText("这是课程页面");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);

        fl_content.addView(textView);
    }
}
