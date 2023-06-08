package com.andorid.finalprj.menudetailpager.tabledetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.andorid.finalprj.R;
import com.andorid.finalprj.base.MenuDetailBasePager;
import com.andorid.finalprj.domain.NewsCenterPagerBean2;
import com.andorid.finalprj.util.Constants;
import com.andorid.finalprj.util.LogUtil;

public class TableDetailPager extends MenuDetailBasePager {

    private final NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData;
    private String url;

    public TableDetailPager(Context context, NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData) {
        super(context);
        this.childrenData = childrenData;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.table_detail_pager, null);
        return view;
    }

    public void initData() {
        super.initData();
        url = Constants.BASE_URL + childrenData.getUrl();
        LogUtil.e(childrenData.getTitle() + "的联网地址=====" + url);

    }
}
