package com.andorid.finalprj.menudetailpager.tabledetailpager;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.andorid.finalprj.R;
import com.andorid.finalprj.base.BasePager;
import com.andorid.finalprj.base.MenuDetailBasePager;
import com.andorid.finalprj.domain.NewsCenterPagerBean2;
import com.andorid.finalprj.domain.TabDetailPagerBean;
import com.andorid.finalprj.util.CacheUtils;
import com.andorid.finalprj.util.Constants;
import com.andorid.finalprj.util.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class TableDetailPager extends MenuDetailBasePager {

    private ViewPager viewPager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private ListView listView;
    private List<TabDetailPagerBean.DataBean.NewsData> news;

    private TabDetailPagerListAdapter adapter;


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

    private int prePosition;

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
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
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

        ll_point_group.removeAllViews();

        for (int i = 0; i < topnews.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(8.0F), DensityUtil.dip2px(8.0F));
            if (i == 0) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(8);
            }
            imageView.setLayoutParams(params);
            ll_point_group.addView(imageView);
        }

        //监听页面的改变，设置focus点的变化
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当页面被选中后，首先需要设置文本
                tv_title.setText(topnews.get(position).getTitle());
                //对应页面的点变成focus
                ll_point_group.getChildAt(prePosition).setEnabled(false);
                ll_point_group.getChildAt(position).setEnabled(true);
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_title.setText(topnews.get(prePosition).getTitle());


        news = bean.getData().getNews();
        adapter = new TabDetailPagerListAdapter();
        listView.setAdapter(adapter);

    }

    class TabDetailPagerListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_tab_detail_pager, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            TabDetailPagerBean.DataBean.NewsData newsData = news.get(position);
            String imageUrl = Constants.BASE_URL + newsData.getListimage();
            x.image().bind(viewHolder.iv_icon, imageUrl);
            viewHolder.tv_title.setText(newsData.getTitle());
            viewHolder.tv_time.setText(newsData.getPubdate());

            return convertView;
        }
    }

    /**
     * 初始化ViewList中的布局
     */
    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }

    private TabDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json, TabDetailPagerBean.class);
    }
}
