package com.andorid.finalprj.fragment;

import android.annotation.SuppressLint;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andorid.finalprj.R;
import com.andorid.finalprj.activity.MainActivity;
import com.andorid.finalprj.base.BaseFragment;
import com.andorid.finalprj.base.BasePager;
import com.andorid.finalprj.domain.NewsCenterPagerBean2;
import com.andorid.finalprj.menudetailpager.NewsMenuDetailPager;
import com.andorid.finalprj.pager.HomePager;
import com.andorid.finalprj.util.DensityUtil;
import com.andorid.finalprj.util.LogUtil;

import org.w3c.dom.Text;

import java.util.List;

public class LeftMenuFragment extends BaseFragment {

    private LeftmenuFragmentAdapter adapter;
    private ListView listView;
    private List<NewsCenterPagerBean2.DetailPagerData> data;
    private int prePosition;

    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图被初始化了");
        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context,40),0,0);
        listView.setDividerHeight(0);//设置分割线高度为0
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(android.R.color.transparent);//  设置按下ListView Item不变色

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //记录点击的位置，变色
                prePosition = position;
                adapter.notifyDataSetChanged();

                //把左侧菜单关闭
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();

                //切换到对应的详情页面
                switchPager(prePosition);
            }
        });
        return listView;
    }

    private void switchPager(int position) {
        MainActivity mainActivity = (MainActivity) context;
        ContentFragment contentFragment = mainActivity.getContentFragment();
        HomePager homePager = (HomePager) contentFragment.getHomePager();
        homePager.switchPager(position);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("左侧菜单数据被初始化了");
    }

    /**
     * 接收数据
     * @param data
     */
    public void setData(List<NewsCenterPagerBean2.DetailPagerData> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            LogUtil.e("title"+data.get(i).getTitle());
        }

        adapter = new LeftmenuFragmentAdapter();

        listView.setAdapter(adapter);

        switchPager(prePosition);
    }
    class LeftmenuFragmentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
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
            @SuppressLint("ViewHolder") TextView textView = (TextView) View.inflate(context, R.layout.item_leftmenu, null);
            textView.setText(data.get(position).getTitle());
            textView.setEnabled(position == prePosition);
            return textView;
        }
    }



}
