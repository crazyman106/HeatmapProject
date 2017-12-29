package com.hxww.heatmapproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者：杨荣华
 * @date 创建时间：2017/12/6 11:15
 * @description 描述：
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyHolder> {
    private List<DateModel.DataBean> list = new ArrayList<>();
    private Context context;

    public DateAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DateModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        DateModel.DataBean bean = list.get(position);
        holder.tv.setText(bean.getBeginTime() + "~" + bean.getEndTime());
    }

    @Override
    public int getItemCount() {
        return list.isEmpty() ? 0 : list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
