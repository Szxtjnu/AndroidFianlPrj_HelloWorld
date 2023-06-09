package com.andorid.finalprj.menudetailpager.tabledetailpager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.andorid.finalprj.R;
import com.andorid.finalprj.base.MenuDetailBasePager;
import com.andorid.finalprj.domain.NewsCenterPagerBean2;
import com.andorid.finalprj.domain.TabDetailPagerBean;
import com.andorid.finalprj.util.CacheUtils;
import com.andorid.finalprj.util.Constants;
import com.andorid.finalprj.util.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class TableDetailPager extends MenuDetailBasePager {

    private ViewPager viewPager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private ListView listView;


    private final NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData;
    private String url;
    //顶部新闻的数据/顶部轮播图
    private List<TabDetailPagerBean.DataBean.TopnewsData> topnews;

    public TableDetailPager(Context context, NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData) {
        super(context);
        this.childrenData = childrenData;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.table_detail_pager, null);
        viewPager = view.findViewById(R.id.table_detail_viewpager);
        tv_title = view.findViewById(R.id.table_detail_tvTitle);
        ll_point_group = view.findViewById(R.id.table_detail_ll_point_group);
        listView = view.findViewById(R.id.table_detail_list_view);
        return view;
    }

    public void initData() {
        super.initData();
        url = Constants.BASE_URL + childrenData.getUrl();
        String saveJson = CacheUtils.getString(context, url);
        if (!TextUtils.isEmpty(saveJson)) {
            processData(saveJson);
        }
//        LogUtil.e(childrenData.getTitle() + "的联网地址=====" + url);

        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(context, url, result);
                LogUtil.e(childrenData.getTitle() + "页面数据请求成功=====");
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(childrenData.getTitle() + "页面数据请求失败=====" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(childrenData.getTitle() + "页面数据请求onCancelled=====" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e(childrenData.getTitle() + "页面数据请求onFinished=====");
            }
        });
    }

    private void processData(String json) {
        TabDetailPagerBean bean = parsedJson(json);
        LogUtil.e(bean.getData().getNews().get(0).getTitle());

        //顶部轮播图数据
        topnews = bean.getData().getTopnews();

        //设置ViewPager的适配器
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return topnews.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setBackgroundResource(R.drawable.home_scroll_default);
                container.addView(imageView);

                TabDetailPagerBean.DataBean.TopnewsData topnewsData = topnews.get(position);
                String topimageurl = Constants.BASE_URL + topnewsData.getTopimage();
                x.image().bind(imageView, topimageurl);

                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
    }

    private TabDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json, TabDetailPagerBean.class);
    }
}
