package com.andorid.finalprj.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.andorid.finalprj.R;
import com.andorid.finalprj.activity.MainActivity;
import com.andorid.finalprj.base.BasePager;
import com.andorid.finalprj.base.MenuDetailBasePager;
import com.andorid.finalprj.domain.NewsCenterPagerBean2;
import com.andorid.finalprj.fragment.LeftMenuFragment;
import com.andorid.finalprj.menudetailpager.InteracMenuDetailPager;
import com.andorid.finalprj.menudetailpager.NewsMenuDetailPager;
import com.andorid.finalprj.menudetailpager.PhotosMenuDetailPager;
import com.andorid.finalprj.menudetailpager.TopicMenuDetailPager;
import com.andorid.finalprj.util.CacheUtils;
import com.andorid.finalprj.util.Constants;
import com.andorid.finalprj.util.LogUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class HomePager extends BasePager {

    //左侧菜单对应的数据集合
    private List<NewsCenterPagerBean2.DetailPagerData> data;
    private ArrayList<MenuDetailBasePager> detailBasePagers;

    public HomePager(Context context) {
        super(context);
    }



    @Override
    public void initData() {
        super.initData();

        tv_title.setVisibility(View.INVISIBLE);
        ib_button.setVisibility(View.VISIBLE);
        search_bar.setVisibility(View.VISIBLE);
        ib_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
            }
        });

        //联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.HELLO_WORLD_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("联网请求成功" + result);

                //设置缓存
                CacheUtils.putString(context, Constants.HELLO_WORLD_PAGER_URL, result);

                //设置适配器
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("联网请求失败" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String json) {
        NewsCenterPagerBean2 bean2 = parsedJson2(json);
//        String title = bean.getData().get(0).getChildren().get(1).getTitle();
//        LogUtil.e("使用Gson解析json数据成功==" + title);

        String title2 = bean2.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用Gson解析json数据成功-title2--------------==" + title2);


        //给左侧菜单传递数据
        data = bean2.getData();
        MainActivity mainActivity = (MainActivity) context;
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();

        /*
          通过detailBasePagers来创建子页面并且传递数据
         */
        detailBasePagers = new ArrayList<>();
        detailBasePagers.add(new NewsMenuDetailPager(context, data.get(0)));
        detailBasePagers.add(new TopicMenuDetailPager(context));
        detailBasePagers.add(new PhotosMenuDetailPager(context));
        detailBasePagers.add(new InteracMenuDetailPager(context));

        //把数据传递到左侧菜单之中
        leftMenuFragment.setData(data);


    }

    /**
     * 使用Android系统自带的API解析Json数据
     *
     * @param json
     * @return
     */
    private NewsCenterPagerBean2 parsedJson2(String json) {
        NewsCenterPagerBean2 bean2 = new NewsCenterPagerBean2();
        try {
            JSONObject object = new JSONObject(json);

            int retcode = object.optInt("retcode");
            bean2.setRetcode(retcode);

            JSONArray data = object.optJSONArray("data");

            if (data != null && data.length() > 0) {
                List<NewsCenterPagerBean2.DetailPagerData> detailPagerDatas = new ArrayList<>();
                bean2.setData(detailPagerDatas);
                //for循环 解析每条数据
                for (int i = 0; i < data.length(); i++) {

                    JSONObject jsonObject = (JSONObject) data.opt(i);
                    NewsCenterPagerBean2.DetailPagerData detailPagerData = new NewsCenterPagerBean2.DetailPagerData();
                    //添加到集合之中
                    detailPagerDatas.add(detailPagerData);


                    int id = jsonObject.optInt("id");
                    int type = jsonObject.optInt("type");
                    String title = jsonObject.optString("title");
                    String url = jsonObject.optString("url");
                    String url1 = jsonObject.optString("url1");
                    String dayurl = jsonObject.optString("dayurl");
                    String excurl = jsonObject.optString("excurl");
                    String weekurl = jsonObject.optString("weekurl");

                    detailPagerData.setId(id);
                    detailPagerData.setType(type);
                    detailPagerData.setTitle(title);
                    detailPagerData.setUrl(url);
                    detailPagerData.setUrl1(url1);
                    detailPagerData.setDayurl(dayurl);
                    detailPagerData.setExcurl(excurl);
                    detailPagerData.setWeekurl(weekurl);


                    JSONArray children = jsonObject.optJSONArray("children");
                    if (children != null && children.length() > 0) {
                        List<NewsCenterPagerBean2.DetailPagerData.ChildrenData> childrenDatas = new ArrayList<>();
                        detailPagerData.setChildren(childrenDatas);
                        for (int j = 0; j < children.length(); j++) {
                            JSONObject childrenItem = (JSONObject) children.get(j);

                            NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData = new NewsCenterPagerBean2.DetailPagerData.ChildrenData();
                            childrenDatas.add(childrenData);

                            int childrenId = childrenItem.optInt("id");
                            int childrenType = childrenItem.optInt("type");
                            String childrenTitle = childrenItem.optString("title");
                            String childrenUrl = childrenItem.optString("url");

                            childrenData.setId(childrenId);
                            childrenData.setUrl(childrenUrl);
                            childrenData.setType(childrenType);
                            childrenData.setTitle(childrenTitle);

                        }
                    }
                }
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return bean2;
    }

//    private NewsCenterPagerBean2 parsedJson(String json) {
//        return new Gson().fromJson(json, NewsCenterPagerBean2.class);
//    }

    /**
     * 根据位置切换详情页面
     *
     * @param position
     */

    public void switchPager(int position) {
        tv_title.setVisibility(View.VISIBLE);
        ib_button.setVisibility(View.VISIBLE);
        search_bar.setVisibility(View.INVISIBLE);

        switch (position) {
            case 0:
                tv_title.setBackgroundResource(R.drawable.text_news);
                break;
            case 1:
                tv_title.setBackgroundResource(R.drawable.text_topic);
                break;
            case 2:
                tv_title.setBackgroundResource(R.drawable.text_photo);
                break;
            case 3:
                tv_title.setBackgroundResource(R.drawable.text_interac);
                break;
        }

        fl_content.removeAllViews();

        MenuDetailBasePager detailBasePager = detailBasePagers.get(position);
        View rootView = detailBasePager.rootView;
        detailBasePager.initData();//初始化处理
        fl_content.addView(rootView);
    }
}
