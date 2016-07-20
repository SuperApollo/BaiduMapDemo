package com.example.myapplication.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.activity.MyApplication;
import com.example.myapplication.bean.Datas;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Apollo} on 2016/6/21 20:36.
 */
public class MyFragment extends Fragment {

    private TextView tv_show_fg;
    private PullToRefreshListView pullToRefreshListView;
    List<Datas> datases = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = MyApplication.getContext();
        View view = inflater.inflate(R.layout.fragment_mine,null);
        tv_show_fg = (TextView) view.findViewById(R.id.tv_show_fg);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setSendButtonListener(new MainActivity.SendButtonListener() {
            @Override
            public void onClicked(String input) {
                tv_show_fg.setText(input);
                Toast.makeText(context,"回调成功",Toast.LENGTH_SHORT).show();
            }

        });
        init(view);
        return view;
    }

    private void init(View view) {
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.lv_refresh);
        addData();
        MyAdpater myAdpater = new MyAdpater();
        pullToRefreshListView.setAdapter(myAdpater);
    }

    private void addData() {
        for (int i=0;i<20;i++){
            Datas datas = new Datas(R.drawable.icon_item,"标题"+i,"嘿嘿嘿嘿嘿嘿,分佛我就放我家覅偶尔发哦我手机佛山将诶哦功夫我就佛为技术的");
            datases.add(datas);
        }
    }

    class MyAdpater extends BaseAdapter{

        @Override
        public int getCount() {
            return datases.size();
        }

        @Override
        public Object getItem(int position) {
            return datases.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_listview,null);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_item_icon);
                holder.tvTittle = (TextView) convertView.findViewById(R.id.tv_item_tittle);
                holder.tvBody = (TextView) convertView.findViewById(R.id.tv_item_body);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            Datas datas = (Datas) getItem(position);
            holder.ivIcon.setImageResource(datas.getIconId());
            holder.tvTittle.setText(datas.getTittle());
            holder.tvBody.setText(datas.getBody());

            return convertView;
        }
    }

    class ViewHolder{
        ImageView ivIcon;
        TextView tvTittle;
        TextView tvBody;
    }
}
