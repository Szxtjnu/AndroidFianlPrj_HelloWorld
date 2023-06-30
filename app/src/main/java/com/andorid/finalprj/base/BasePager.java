package com.andorid.finalprj.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andorid.finalprj.R;

public class BasePager {

    public final Context context;
    public View rootView;
    public ImageView tv_title;
    public ImageButton ib_button;
    public FrameLayout fl_content;
    public EditText search_bar;

    public LinearLayout layout;
    public TextView textView;
    public Button button;
    public ListView listView;

    public BasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    private View initView() {
        View view = View.inflate(context, R.layout.base_pager, null);
        tv_title = (ImageView) view.findViewById(R.id.tv_title);
        ib_button = (ImageButton) view.findViewById(R.id.ib_menu);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        search_bar = (EditText) view.findViewById(R.id.search_bar);
        layout = (LinearLayout) view.findViewById(R.id.linear_layout);
        textView = (TextView) view.findViewById(R.id.show_username);
        button = (Button) view.findViewById(R.id.logout);
        listView = (ListView) view.findViewById(R.id.course_listview);
        return view;
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

}
