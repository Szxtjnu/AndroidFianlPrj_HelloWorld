package com.andorid.finalprj.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.andorid.finalprj.R;
import com.andorid.finalprj.base.BasePager;

public class MinePager extends BasePager {

    private boolean isUserLoggedIn;

    public MinePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        isUserLoggedIn = checkUserLoginStatus();

        View view;


        tv_title.setVisibility(View.INVISIBLE);
        ib_button.setVisibility(View.INVISIBLE);
        search_bar.setVisibility(View.INVISIBLE);



    }

    private boolean checkUserLoginStatus() {
        return false;
    }
}
