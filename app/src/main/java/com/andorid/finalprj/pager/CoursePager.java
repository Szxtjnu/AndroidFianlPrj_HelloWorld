package com.andorid.finalprj.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andorid.finalprj.R;
import com.andorid.finalprj.base.BasePager;
import com.andorid.finalprj.domain.CourseBean;
import com.andorid.finalprj.util.CacheUtils;
import com.andorid.finalprj.util.Constants;
import com.andorid.finalprj.util.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class CoursePager extends BasePager {
    private List<CourseBean> dataList;

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
        listView.setVisibility(View.VISIBLE);

        getDataFromNet();

    }


    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.HELLO_WORLD_COURSE_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("联网请求成功" + result);

                //设置缓存
                CacheUtils.putString(context, Constants.HELLO_WORLD_COURSE_URL, result);

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
        Gson gson = new Gson();
        dataList = gson.fromJson(json, new TypeToken<List<CourseBean>>(){}.getType());
        listView.setAdapter(new CourseListAdapter());
    }

    class CourseListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (dataList != null) {
                return dataList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (dataList != null && position < dataList.size()) {
                return dataList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_course_listview, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.iv_course_pic = convertView.findViewById(R.id.iv_course_pic);
                viewHolder.tv_course_title = convertView.findViewById(R.id.tv_course_title);
                viewHolder.tv_course_username = convertView.findViewById(R.id.tv_course_username);
                viewHolder.tv_course_describe = convertView.findViewById(R.id.tv_course_describe);
                viewHolder.tv_course_price = convertView.findViewById(R.id.tv_course_price);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            CourseBean courseBean = dataList.get(position);
            viewHolder.tv_course_title.setText(courseBean.getTitle());
            viewHolder.tv_course_price.setText(courseBean.getPrice());
            viewHolder.tv_course_username.setText(courseBean.getUser_name());
            viewHolder.tv_course_describe.setText(courseBean.getDiscribe());
            String imageUrl = courseBean.getPic_url();
            x.image().bind(viewHolder.iv_course_pic, imageUrl);

            return convertView;
        }

        class ViewHolder {
            ImageView iv_course_pic;
            TextView tv_course_title;
            TextView tv_course_username;
            TextView tv_course_describe;
            TextView tv_course_price;
        }
    }
}
