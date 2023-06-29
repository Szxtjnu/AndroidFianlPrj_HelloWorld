package com.andorid.finalprj.pager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andorid.finalprj.R;
import com.andorid.finalprj.activity.LoginActivity;
import com.andorid.finalprj.activity.MainActivity;
import com.andorid.finalprj.base.BasePager;
import com.andorid.finalprj.util.DBHelper;

public class MinePager extends BasePager {

    private boolean isUserLoggedIn;
    private String username;
    private DBHelper dbHelper;

    public MinePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        dbHelper = new DBHelper(context);
        tv_title.setVisibility(View.INVISIBLE);
        ib_button.setVisibility(View.INVISIBLE);
        search_bar.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);




        boolean isUserLoggedIn = dbHelper.isUserLoggedIn();
        if (isUserLoggedIn) {
            String username = dbHelper.getLoggedInUsername();
            textView.setText(username);
            button.setText("点击退出登录");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.updateLoginStatus(username, 0);
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
            textView.setText("您尚未登录，请登录");
            button.setText("点击登录");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            });
        }


    }

}
