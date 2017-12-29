package com.hxww.heatmapproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者：杨荣华
 * @date 创建时间：2017/12/6 11:15
 * @description 描述：
 */

public class DotAdapter extends RecyclerView.Adapter<DotAdapter.MyHolder> {
    private List<LocationModel.DataBean> list = new ArrayList<>();
    private Context context;

    public DotAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<LocationModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dot, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.isEmpty()?0:list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public MyHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }
    }
}
