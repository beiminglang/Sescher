package com.elyria.sescher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elyria.sescher.R;
import com.elyria.sescher.func.bean.Bean;

import java.util.List;

/**
 * Created by wang.lichen on 2017/10/28.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.Holder>  {
    private Context mContext ;
    private List<Bean> beanList;
    private Bean item;
    public ResultAdapter(Context context ,List<Bean> bean) {
        mContext = context;
        this.beanList = bean;
    }
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        item = beanList.get(position);
        holder.result.setText(item.getResult());
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_result,parent,false));
    }
    

    @Override
    public int getItemCount() {
        return beanList ==null?0:beanList.size();
    }

    public void setData(List<Bean> result) {
        if (beanList !=null && beanList.size()>0) {
            beanList.clear();
        }
        beanList = result;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView result;
        public Holder(View itemView) {
            super(itemView);
            result = itemView.findViewById(R.id.result);
        }
    }
}
