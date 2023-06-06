package com.andorid.finalprj.base;

import android.content.Context;
import android.view.View;

/**
 * 详情页面的基类
 */
public abstract class MenuDetailBasePager {
    public final Context context;
    //代表各个详情页面的视图
    public View rootView;

    public MenuDetailBasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    public abstract View initView();

    /**
     * 子页面需要绑定数据，联网请求数据的时候，重写该方法
     */
    public void initData() {

    }
}
